package com.oneapp.oneappandroidapp.netty_model;

import java.io.Serializable;
import java.sql.Timestamp;


public class User implements Serializable {

        private static final long serialVersionUid = 8213761673743652497L;

        private Long uid; //用户id
        private String username; //用户名
        private String password;//用户密码
        private String phone;
        private String emil;
        private String photo;//头像
        private String sex;
        private String age;
        private String description;
        private Integer is_disabled;//表示是否禁用，0为正常，1为禁用
        private Integer is_deleted;//是否逻辑删除，0为正常，1为删除
        private String openid;//微信openid，如果要启用微信注册，需要用到这个
        private String code_verify;//验证码
        private Timestamp created;//创建时间，需要转换为TIMESTAMP存入sql
        private Timestamp updated;//更新时间，同样
        //左边是sql类型，右边是util类型
        // public Timestamp timestamp = new Timestamp(new Date().getTime());


        public Long getUid() {
                return uid;
        }

        public void setUid(Long uid) {
                this.uid = uid;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getEmil() {
                return emil;
        }

        public void setEmil(String emil) {
                this.emil = emil;
        }

        public String getPhoto() {
                return photo;
        }

        public void setPhoto(String photo) {
                this.photo = photo;
        }

        public String getSex() {
                return sex;
        }

        public void setSex(String sex) {
                this.sex = sex;
        }

        public String getAge() {
                return age;
        }

        public void setAge(String age) {
                this.age = age;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Integer getIs_disabled() {
                return is_disabled;
        }

        public void setIs_disabled(Integer is_disabled) {
                this.is_disabled = is_disabled;
        }

        public Integer getIs_deleted() {
                return is_deleted;
        }

        public void setIs_deleted(Integer is_deleted) {
                this.is_deleted = is_deleted;
        }

        public String getOpenid() {
                return openid;
        }

        public void setOpenid(String openid) {
                this.openid = openid;
        }

        public String getCode_verify() {
                return code_verify;
        }

        public void setCode_verify(String code_verify) {
                this.code_verify = code_verify;
        }

        public Timestamp getCreated() {
                return created;
        }

        public void setCreated(Timestamp created) {
                this.created = created;
        }

        public Timestamp getUpdated() {
                return updated;
        }

        public void setUpdated(Timestamp updated) {
                this.updated = updated;
        }

        public User() {
        }

        public User(Long uid, String username, String password, String phone, String emil, String photo, String sex, String age, String description, Integer is_disabled, Integer is_deleted, String openid, String code_verify, Timestamp created, Timestamp updated) {
                this.uid = uid;
                this.username = username;
                this.password = password;
                this.phone = phone;
                this.emil = emil;
                this.photo = photo;
                this.sex = sex;
                this.age = age;
                this.description = description;
                this.is_disabled = is_disabled;
                this.is_deleted = is_deleted;
                this.openid = openid;
                this.code_verify = code_verify;
                this.created = created;
                this.updated = updated;
        }

        public User(Long uid, String username, String password){
                this.setUid(uid);
                this.setUsername(username);
                this.setPassword(password);
        }

        @Override
        public String toString() {
                return "User{" +
                        "uid=" + uid +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        ", phone='" + phone + '\'' +
                        ", emil='" + emil + '\'' +
                        ", photo='" + photo + '\'' +
                        ", sex='" + sex + '\'' +
                        ", age='" + age + '\'' +
                        ", description='" + description + '\'' +
                        ", is_disabled=" + is_disabled +
                        ", is_deleted=" + is_deleted +
                        ", openid='" + openid + '\'' +
                        ", code_verify='" + code_verify + '\'' +
                        ", created=" + created +
                        ", updated=" + updated +
                        '}';
        }
}
