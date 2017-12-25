/*! minsx v1.0.0 Copyright © 2016-2017 minsx.com All rights reserved*/
let instance = axios.create();
Minsx = {
    Util: {
        /**
         * Get Param value in URL by key
         */
        getUrlParam: function (key) {
            const reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
            const r = window.location.search.substr(1).match(reg);
            if (r !== null) return unescape(r[2]);
            return null;
        }
    },
    Json: {
        /**
         * Parse JSON to String
         */
        parseToString: function (JsonObject) {
            return JSON.stringify(JsonObject);
        },

        /**
         * Parse String to JSON
         */
        parseToJson: function (str) {
            return JSON.parse(str);
        }
    },
    String: {
        /**
         * Format String eg:
         * alert(Minsx.String.format("lastName:{0} firstName:{1}","Joker","John"));
         */
        format: function () {
            const args = arguments;
            return args[0].replace(/{(\d+)}/g, function (t, n) {
                return "undefined" !== typeof args[parseInt(n) + 1] ? args[parseInt(n) + 1] : t;
            });
        }
    },
    Http: {
        get(url, param) {
            return instance.get(url,{params: param});
        },
        delete(url, param) {
            return instance.delete(url, {params: param});
        },
        post(url, body, param) {
            return instance.post(url, body,  {params: param});
        },
        postJson(url, body, param) {
            let option = {
                headers: {'Content-Type': 'application/json'}
            };
            if (param) option.params = param;
            return instance.post(url, body, option);
        },
        postWithOption(url, body, option) {
            return instance.post(url, body,  option);
        },
        put(url, body, param) {
            return instance.put(url,body,{params: param});
        },
        putJson(url, body, param) {
            let option = {
                headers: {'Content-Type': 'application/json'}
            };
            if (param) option.params = param;
            return instance.put(url, body, option);
        }
    },
    Cookie: {
        set: function () {
            const args = arguments;
            if (args.length === 2) {
                document.cookie = args[0] + "=" + escape(args[1]);
            } else if (args.length === 3) {
                document.cookie = args[0] + "=" + escape(args[1]) + ";path=" + args[2];
            } else if (args.length === 4) {
                document.cookie = args[0] + "=" + escape(args[1]) + ";path=" + args[2] + ";domain=" + args[3];
            }
        },
        get: function (key) {
            let arr, reg = new RegExp("(^| )" + key + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg)) {
                return unescape(arr[2]);
            } else {
                return null;
            }
        },
        remove: function (key) {
            const cval = Minsx.Cookie.get(key);
            if (cval !== null) {
                const exp = new Date();
                exp.setTime(exp.getTime() - 1);
                document.cookie = key + "=" + cval + ";expires=" + exp.toGMTString();
            }
        }
    }
}


