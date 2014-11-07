import org.apache.commons.lang3.StringUtils

class BootStrap {
    def wxCpService
    def init = { servletContext ->
        if (StringUtils.isBlank(wxCpService.wxCpConfigStorage.getAccessToken())) {


            wxCpService.accessTokenRefresh();
            println "BootStrap init>>>>>>>>>>accessTokenRefresh"+wxCpService.wxCpConfigStorage.getAccessToken()
        }
    }
    def destroy = {
    }
}
