package wechat

import chanjarster.weixin.api.WxConfigStorage
import chanjarster.weixin.api.WxInMemoryConfigStorage
import chanjarster.weixin.api.WxService
import chanjarster.weixin.api.WxServiceImpl
import chanjarster.weixin.bean.WxCustomMessage
import chanjarster.weixin.bean.WxGroup
import chanjarster.weixin.bean.WxMassGroupMessage
import chanjarster.weixin.bean.WxMassNews
import chanjarster.weixin.bean.WxMassOpenIdsMessage
import chanjarster.weixin.bean.WxMassVideo
import chanjarster.weixin.bean.WxMenu
import chanjarster.weixin.bean.result.WxMassSendResult
import chanjarster.weixin.bean.result.WxMassUploadResult
import chanjarster.weixin.bean.result.WxMediaUploadResult
import chanjarster.weixin.bean.result.WxQrCodeTicket
import chanjarster.weixin.bean.result.WxUser
import chanjarster.weixin.bean.result.WxUserList
import chanjarster.weixin.exception.WxErrorException
import grails.transaction.Transactional



@Transactional
class WeChatService implements WxService {

    def grailsApplication

     final WxServiceImpl wxService
     final   WxInMemoryConfigStorage wxConfig
    def serverIp





    public WeChatService(  ){

        grailsApplication=new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
        println "def&&&&&&&&&&&&&&& grailsApplication:"+ grailsApplication
        def appid=grailsApplication.config.wechat.appid //BuildConfig.groovy
        def appSecret=grailsApplication.config.wechat.appSecret //BuildConfig.groovy
        def token=grailsApplication.config.wechat.token //BuildConfig.groovy
          serverIp=grailsApplication.config.wechat.token //Config.groovy

        this.wxService = new WxServiceImpl();
        this.wxConfig = new WxInMemoryConfigStorage();

        wxConfig.setAppId(appid); // 设置微信公众号的appid
        wxConfig.setSecret(appSecret); // 设置微信公众号的app secret
        wxConfig.setToken(token); // 设置微信公众号的token
        wxService.setWxConfigStorage(wxConfig);
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
    void customMessageSend(WxCustomMessage message) throws WxErrorException {
        wxService.customMessageSend( message)
    }

    @Override
    WxMassUploadResult massNewsUpload(WxMassNews news) throws WxErrorException {
        return wxService.massNewsUpload( news)
    }

    @Override
    WxMassUploadResult massVideoUpload(WxMassVideo video) throws WxErrorException {
        return wxService.massVideoUpload( video)
    }

    @Override
    WxMassSendResult massGroupMessageSend(WxMassGroupMessage message) throws WxErrorException {
        return wxService.massGroupMessageSend( message)
    }

    @Override
    WxMassSendResult massOpenIdsMessageSend(WxMassOpenIdsMessage message) throws WxErrorException {
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
    WxGroup groupCreate(String name) throws WxErrorException {
        return wxService.groupCreate( name)
    }

    @Override
    List<WxGroup> groupGet() throws WxErrorException {
        return wxService.groupGet()
    }

    @Override
    long userGetGroup(String openid) throws WxErrorException {
        return wxService.userGetGroup( openid)
    }

    @Override
    void groupUpdate(WxGroup group) throws WxErrorException {
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
    WxUser userInfo(String openid, String lang) throws WxErrorException {
        return wxService.userInfo( openid,  lang)
    }

    @Override
    WxUserList userList(String next_openid) throws WxErrorException {
        return wxService.userList( next_openid)
    }

    @Override
    WxQrCodeTicket qrCodeCreateTmpTicket(int scene_id, Integer expire_seconds) throws WxErrorException {
        return wxService. qrCodeCreateTmpTicket( scene_id,  expire_seconds)
    }

    @Override
    WxQrCodeTicket qrCodeCreateLastTicket(int scene_id) throws WxErrorException {
        return wxService.qrCodeCreateLastTicket( scene_id)
    }

    @Override
    File qrCodePicture(WxQrCodeTicket ticket) throws WxErrorException {
        return wxService.qrCodePicture( ticket)
    }

    @Override
    String shortUrl(String long_url) throws WxErrorException {
        return wxService.shortUrl( long_url)
    }

    @Override
    void setWxConfigStorage(WxConfigStorage wxConfigProvider) {
        wxService.setWxConfigStorage( wxConfigProvider)
    }

}
