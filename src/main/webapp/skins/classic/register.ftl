<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="B3log ${symphonyLabel} - ${registerLabel}">
        <meta name="keywords" content="${registerLabel},B3log ${symphonyLabel}"/>
        <meta name="description" content="${registerLabel} B3log ${symphonyLabel}"/>
        </@head>
        <link type="text/css" rel="stylesheet" href="/css/index.css" />
    </head>
    <body>
        <#include "header.ftl">
        <div class="main">
            <div class="wrapper fn-clear register content-reset">
                <div class="form fn-left" style="width: 234px;">
                    <table cellpadding="0" cellspacing="0">
                        <tbody>
                            <tr>
                                <td width="70">
                                    <label for="userName">${userNameLabel}</label>
                                </td>
                                <td width="165">
                                    <input type="text" id="userName" />
                                    <span style="left:240px;top:10px;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="userEmail">${emailLabel}</label>
                                </td>
                                <td>
                                    <input type="text" id="userEmail" />
                                    <span style="left:240px;top:58px;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="userPassword">${passwordLabel}</label>
                                </td>
                                <td>
                                    <input id="userPassword"  type="password" />
                                    <span style="left:240px;top:106px;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="confirmPassword">${userPasswordLabel2}</label>
                                </td>
                                <td>
                                    <input id="confirmPassword" type="password" />
                                    <span style="left:240px;top:154px;"></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="securityCode">${captchaLabel}</label>
                                </td>
                                <td>
                                    <input style="width:98px;" type="text" id="securityCode" />
                                    <span style="top:202px;left:240px;"></span>
                                    <img id="captcha" style="cursor:pointer" src="/captcha" onclick="this.src='/captcha?'+(new Date()).getTime()" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right">
                                    <span id="registerTip"></span>
                                    <button onclick="Register.register()">${registerLabel}</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="fn-left intro">
                    ${introLabel}
                </div>
            </div>
        </div>
        <#include "footer.ftl">
        <script type="text/javascript" src="/js/register.js"></script>
        <script type="text/javascript" src="/js/lib/md5.js"></script>
        <script>
            Register.init();
            Label.userNameErrorLabel = "${userNameErrorLabel}";
            Label.invalidEmailLabel = "${invalidEmailLabel}";
            Label.confirmPwdErrorLabel = "${confirmPwdErrorLabel}";
            Label.captchaErrorLabel = "${captchaErrorLabel}";
        </script>
    </body>
</html>
