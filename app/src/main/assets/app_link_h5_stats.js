;(function(config, global){
    global.MtaLinkH5=global.MtaLinkH5||{};

    function hack(){
        //从标签读取配置
        var eles = document.getElementsByName('MtaLinkH5'),
          conf = {'autoReport':1, 'version':0, 'page':{}},
          page_conf = {'title':''};

        if(0 == eles.length){
            var tags = document.getElementsByTagName('script');
            for(var i=0; i<tags.length; i++){
                if(typeof tags[i].attributes['name'] !== 'undefined' && tags[i].attributes['name'].nodeValue == 'MtaLinkH5'){
                    eles = [];
                    eles.push(tags[i]);
                    break;
                }
            }
        }
        eles.length > 0 && function(){
            for(var name in conf){
                if(typeof eles[0].attributes[name] !== 'undefined'){

                    conf[name] = parseInt(eles[0].attributes[name].nodeValue);
                }
            }
        }()

        //配置从外部读取
        typeof _mta_config === "object" && function(){
            for(var p in _mta_config){
                _mta_config.hasOwnProperty(p)&&(page_conf[p]=_mta_config[p]);
            }
        }();
        conf.page = page_conf;
        conf.url_flag = 'tencentMtaHyb';

        //从ua头取读配置
        var ua = (window.navigator.userAgent).match(/TencentMTA\/([0-9.]{1,})/);
        conf.version = 1
        if(ua != null && ua.length === 2){
            conf.version = ua[1];
        }

        return conf;
    }

    MtaLinkH5.versionControl = function() {
        if(config.version <= 0){
            return false;
        }
        return true;
    }

    //事件上报
    MtaLinkH5.eventStats = function (event_id, event_param){
        if(!this.versionControl()) return false;
        var report = {};
        report['id'] = event_id;
        report['param'] = {};
        if(typeof event_param === 'object'){
            for(var i in event_param){
                if(!Array.isArray(event_param[i])){
                    report['param'][i] = event_param[i];
                }
            }
        }

        pushData('trackKVEvent', report);
    }

    //访问上报
    MtaLinkH5.pageBasicStats = function (){
        if(!this.versionControl()) return false;
        if(!config.page.title) return false;
        var pushJson = {}
        for(
          var arr = [getPageInfo(), {'title' : config.page.title}, getReferer(), {'timestamp' : new Date().getTime()}],l = arr.length,i=0;
          i<l;
          i++
        ){
            for(var k in arr[i]){
                pushJson[k] = typeof arr[i][k] == 'undefined' ? '' : arr[i][k];
            }
        }
        pushData('trackPage', pushJson);
    }


    // MtaLinkH5.setPageTitle = function(title){
    //     if(!this.versionControl()) return false;
    //     var url = getPageInfo()
    //     var pushJson = {
    //         title:title
    //     }
    //     pushData('setPageName', pushJson);
    // }

    MtaLinkH5.setLoginUin = function (uin){
        if(!this.versionControl()) return false;
        pushData('onUserLogin', {'uin':uin});
    }

    //iframe
    function loadURL(url) {
        var iFrame;
        var elem = document.body;
        iFrame = document.createElement("iframe");
        iFrame.setAttribute("src", url);
        iFrame.setAttribute("style", "display:none;");
        iFrame.setAttribute("height", "0px");
        iFrame.setAttribute("width", "0px");
        iFrame.setAttribute("frameborder", "0");
        console.log(iFrame)
        elem.appendChild(iFrame);
        iFrame.parentNode.removeChild(iFrame);
        iFrame = null;
    };

    //上报
    function pushData(funName, args) {
        var cmd = {
            methodName : funName,
            args : args
        }
        var jsonStr = JSON.stringify(cmd);
        var url = config.url_flag + ":" + jsonStr;
        loadURL(url);
    };


    function getReferer(){
        var url=getPageInfo(document.referrer);
        return {
            rdm:url.dm,
            rurl:url.path,
            rarg:url.search,
            rhash:url.hash
        }
    }

    function getPageInfo(url){
        var title,host,path,search,hash,param={};
        if(url===undefined){
            var loc=window.location;
            host=loc.host;path=loc.pathname;search=loc.search.substr(1);hash=loc.hash;
        }

        else{
            var ret=url.match(/\w+:\/\/((?:[\w-]+\.)+\w+)(?:\:\d+)?(\/[^\?\\\"\'\|\:<>]*)?(?:\?([^\'\"\\<>#]*))?(?:#(\w+))?/i)||[];
            host=ret[1];path=ret[2];search=ret[3];hash=ret[4];
        }
        if( undefined !== hash ){
            hash = hash.replace(/\"|\'|\<|\>/ig,"M");
        }
        search&&function(){
            var arr=search.split('&');
            for(var i=0,l=arr.length;i<l;i++){
                if(arr[i].indexOf('=') != -1){
                    var pos = arr[i].indexOf('=');
                    var k = arr[i].slice(0, pos);
                    var v = arr[i].slice(pos + 1);
                    param[k] = v;
                }
            }
        }();
        return {dm:host,path:path,search:search,hash:hash}
    }

    var init = function (){
        config = hack();
        !!config.autoReport && !!config.page.title &&MtaLinkH5.pageBasicStats();
    }()
})({}, this);