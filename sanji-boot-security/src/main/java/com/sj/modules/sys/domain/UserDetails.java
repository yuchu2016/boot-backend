package com.sj.modules.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 添加用户详细信息 用户表尽量不要修改
 * Created by sunxyz on 2017/3/13.
 */
@Setter
@Getter
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "news"})
@Entity
@Table(name = "sys_user_details")
@PrimaryKeyJoinColumn(name = "user_id")
public class UserDetails extends User {

    private String avatar;//头像

    private String stuId; //学号

    private String college; //学院

    private String nickname;//昵称

    private String description;//描述

    @Enumerated
    private Sex sex;//性别

//    private Date birthday;//生日

    private String phone;//手机

    //    @Email
    private String email;//邮箱

    public static enum Sex {
        MAN, WO_MAN
    }
}
