package wechat

//import chanjarster.weixin.api.WxConfigStorage
//import chanjarster.weixin.api.WxInMemoryConfigStorage
//import chanjarster.weixin.api.WxService
//import chanjarster.weixin.api.WxServiceImpl
//import chanjarster.weixin.bean.WxCustomMessage
//import chanjarster.weixin.bean.WxGroup
//import chanjarster.weixin.bean.WxMassGroupMessage
//import chanjarster.weixin.bean.WxMassNews
//import chanjarster.weixin.bean.WxMassOpenIdsMessage
//import chanjarster.weixin.bean.WxMassVideo
//import chanjarster.weixin.bean.WxMenu
//import chanjarster.weixin.bean.result.WxMassSendResult
//import chanjarster.weixin.bean.result.WxMassUploadResult
//import chanjarster.weixin.bean.result.WxMediaUploadResult
//import chanjarster.weixin.bean.result.WxQrCodeTicket
//import chanjarster.weixin.bean.result.WxUser
//import chanjarster.weixin.bean.result.WxUserList
//import chanjarster.weixin.exception.WxErrorException
import grails.transaction.Transactional
import me.chanjar.weixin.common.bean.WxMenu
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult
import me.chanjar.weixin.common.exception.WxErrorException
import me.chanjar.weixin.mp.api.WxMpConfigStorage
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage
import me.chanjar.weixin.mp.api.WxMpService
import me.chanjar.weixin.mp.api.WxMpServiceImpl
import me.chanjar.weixin.mp.bean.WxMpCustomMessage
import me.chanjar.weixin.mp.bean.WxMpGroup
import me.chanjar.weixin.mp.bean.WxMpMassGroupMessage
import me.chanjar.weixin.mp.bean.WxMpMassNews
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage
import me.chanjar.weixin.mp.bean.WxMpMassVideo
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket
import me.chanjar.weixin.mp.bean.result.WxMpUser
import me.chanjar.weixin.mp.bean.result.WxMpUserList


@Transactional
class WeChatService implements WxMpService {

    def grailsApplication

     final WxMpServiceImpl  wxService
     final   WxMpInMemoryConfigStorage wxConfig
    def serverIp





    public WeChatService(  ){

        grailsApplication=new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
        println "def&&&&&&&&&&&&&&& grailsApplication:"+ grailsApplication
        def appid=grailsApplication.config.wechat.appid //BuildConfig.groovy
        def appSecret=grailsApplication.config.wechat.appSecret //BuildConfig.groovy
        def token=grailsApplication.config.wechat.token //BuildConfig.groovy
          serverIp=grailsApplication.config.wechat.server.ip //Config.groovy

        this.wxService = new WxMpServiceImpl();
        this.wxConfig = new WxMpInMemoryConfigStorage();

        wxConfig.setAppId(appid); // 设置微信公众号的appid
        wxConfig.setSecret(appSecret); // 设置微信公众号的app secret
        wxConfig.setToken(token); // 设置微信公众号的token
        wxService.setWxMpConfigStorage(wxConfig);
        println ""
        println ""
        println "WeChatService 配置成功"

    }





    @Override
    boolean checkSignature(String timestamp, String nonce, String signature) {
        wxService.checkSignature(timestamp, nonce, signature)
    }

    @Override
    void accessTokenRefresh() throws WxErrorException {
        wxService.accessTokenRefresh()
    }

    @Override
    WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException {
        return wxService.mediaUpload( mediaType,  fileType,  inputStream)
    }

    @Override
    WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
        return wxService.mediaUpload( mediaType,  file)
    }

    @Override
    File mediaDownload(String media_id) throws WxErrorException {
        return wxService.mediaDownload( media_id)
    }

    @Override
    void customMessageSend(WxMpCustomMessage message) throws WxErrorException {
        wxService.customMessageSend( message)
    }

    @Override
    WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException {
        return wxService.massNewsUpload( news)
    }

    @Override
    WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException {
        return wxService.massVideoUpload( video)
    }

    @Override
    WxMpMassSendResult massGroupMessageSend(WxMpMassGroupMessage message) throws WxErrorException {
        return wxService.massGroupMessageSend( message)
    }

    @Override
    WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException {
        return wxService. massOpenIdsMessageSend( message)
    }

    @Override
    void menuCreate(WxMenu menu) throws WxErrorException {
        wxService.menuCreate( menu)
    }

    @Override
    void menuDelete() throws WxErrorException {
        wxService.menuDelete()
    }

    @Override
    WxMenu menuGet() throws WxErrorException {
        return wxService.menuGet()
    }

    @Override
    WxMpGroup groupCreate(String name) throws WxErrorException {
        return wxService.groupCreate( name)
    }

    @Override
    List<WxMpGroup> groupGet() throws WxErrorException {
        return wxService.groupGet()
    }

    @Override
    long userGetGroup(String openid) throws WxErrorException {
        return wxService.userGetGroup( openid)
    }

    @Override
    void groupUpdate(WxMpGroup group) throws WxErrorException {
        wxService.groupUpdate( group)
    }

    @Override
    void userUpdateGroup(String openid, long to_groupid) throws WxErrorException {
        wxService.userUpdateGroup( openid,  to_groupid)
    }

    @Override
    void userUpdateRemark(String openid, String remark) throws WxErrorException {
        wxService.userUpdateRemark( openid,  remark)
    }

    @Override
    WxMpUser userInfo(String openid, String lang) throws WxErrorException {
        return wxService.userInfo( openid,  lang)
    }

    @Override
    WxMpUserList userList(String next_openid) throws WxErrorException {
        return wxService.userList( next_openid)
    }

    @Override
    WxMpQrCodeTicket qrCodeCreateTmpTicket(int scene_id, Integer expire_seconds) throws WxErrorException {
        return wxService. qrCodeCreateTmpTicket( scene_id,  expire_seconds)
    }

    @Override
    WxMpQrCodeTicket qrCodeCreateLastTicket(int scene_id) throws WxErrorException {
        return wxService.qrCodeCreateLastTicket( scene_id)
    }

    @Override
    File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException {
        return wxService.qrCodePicture( ticket)
    }

    @Override
    String shortUrl(String long_url) throws WxErrorException {
        return wxService.shortUrl( long_url)
    }

    @Override
    void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider) {
        wxService.setWxMpConfigStorage( wxConfigProvider)
    }

}
