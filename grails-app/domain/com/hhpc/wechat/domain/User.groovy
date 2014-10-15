package com.hhpc.wechat.domain


import grails.persistence.*



/**
 * Created by Administrator on 2014/9/19.
 */
@Entity
class User {

   // transient springSecurityService
    static transients = ['username']

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    //  static hasMany = [authorities: Role]
  //  static belongsTo = [ UserGroup]
   // UserGroup userGroup
    String id;   //字符串主键
    String userRealName
    /** enabled  */

    String email
    boolean emailShow

    /** description  */
    String description = ''

    /** plain password to create a MD5 password  */
    //String pass = '[secret]'

    Date chgDate //修改日期

//    Set<Role> getAuthorities() {
//        UserRole.findAllByUser(this).collect { it.role } as Set
//    }

    static constraints = {
        id(blank: false, unique: true)
        userRealName(blank: false)
        password(blank: false)
        //userGroup(nullable: true)
        chgDate(nullable: true)
        enabled()
    }

    static mapping = {
        table 'MASA_USER'
        version false

        userRealName column: 'f_name'
        password column: 'f_password'
        enabled column: 'f_flag'     //'enabled'
        email column: 'f_email'
        chgDate column: 'f_chgDate'
        id column: 'f_userid', generator: 'assigned'
        // authorities column: 'f_userid', joinTable: 'MASA_USER_ROLE'
        //   groups column:'f_userid',joinTable:'MASA_USER_GROUP'


    }

    def onLoad = {
        username = id
    }


    String toString() {
        ":" + userRealName
    }
    /*****************************/
    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
      //  password = springSecurityService.encodePassword(password)
    }

}
