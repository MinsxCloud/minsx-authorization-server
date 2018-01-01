package com.minsx.authorization.plugin.geetest;

import com.minsx.authorization.common.util.RequestUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class GeeTestUtil {

    public static int verify(ServletRequest request) {
        int gtResult = 0;
        try {
            GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                    GeetestConfig.isnewfailback());
            String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
            String validate = request.getParameter(GeetestLib.fn_geetest_validate);
            String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
            Object StatusSessionKey = RequestUtil.getCurrentHttpServletRequest().getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
            if (StatusSessionKey == null) {
                return 0;
            }
            int gt_server_status_code = (Integer) StatusSessionKey;
            //自定义参数,可选择添加
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("client_type", "web");
            param.put("ip_address", request.getRemoteAddr());
            if (gt_server_status_code == 1) {
                gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            } else {
                System.out.println("failback:use your own server captcha validate");
                gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gtResult;
    }

}
