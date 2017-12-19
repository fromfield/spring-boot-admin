package com.github.util;


/** 系统常量 */
public final class Constants {

    /**管理员编码*/
    public static final String ADMINCODE = "su";
    /** 系统域名 */
    public static final String DOMAIN_NAME = "http://www.chaoxing.com/";
    public static final String SYSTEM_NAME = "后台管理系统";

	public static final String LOGIN_USER = "loginUser";

    /** 通用状态 */
    public static final Integer STATUS_TRUE = 1;
    public static final Integer STATUS_FALSE = 0;

    /** 系统BigDecimal除法精度 */
    public static final Integer BIGDECIMAL_SCALE = 4;
    public static final Integer BIGDECIMAL_MIN_SCALE = 2;


    public static final int COURSE_TYPE_TOP_PARENT_ID = 0;

    public  static final String UID = "UID";

    public  static final String FID = "fid";

    public static final String GRT_COURSE_DETAIL_HREF = "http://mooc1-api.chaoxing.com/gas/course?fields=id,name,imageurl,state,teacherfactor,schools,clazz.fields(id,bbsid)&ids={ids}";
    public static final String QIKAN_DETAIL_HREF = "http://qktest1.chaoxing.com/openapi/search/mag?magid={resId}";

    /**
     * 课程默认报名人数
     */
    public static final int COURSE_DEFAULT_ENROLL = 0;

    /**
     * 课程默认平均值
     */
    public static final float COURSE_DEFAULT_AVERAGE = 0f;

    /**
     * 课程默认发布状态:0未发布 1已发布 2驳回 3删除
     */
    public static final int COURSE_DEFAULT_STATUS = 1;

    /**
     * 课程默认 上架状态 0未上架 1已上架
     */
    public static final int COURSE_DEFAULT_ONLINE = 1;

    /**
     * 课程默认 上架状态 1已上架
     */
    public static final int COURSE_ONLINE = 1;

    /**
     * 课程默认学时
     */
    public static final int COURSE_DEFAULT_HOUR = 1;

    /**
     * 课程发布状态: 未发布
     */
    public static final int COURSE_STATUS_UNPUBLISHED = 0;

    /**
     * 课程发布状态: 已发布
     */
    public static final int COURSE_STATUS_PUBLISHED = 1;

    /**
     * 课程发布状态: 驳回
     */
    public static final int COURSE_STATUS_REJECT = 2;

    /**
     * 课程发布状态: 删除
     */
    public static final int COURSE_STATUS_DELETE = 3;

    /**
     * 图书、期刊、专题默认学时
     */
    public static final int SUBJECT_DEFAULT_HOUR = 1;
    /**
     * 图书、期刊、专题上架状态
     */
    public static final int SUBJECT_ONLINE = 1;
    /**
     * 图书、期刊、专题下架状态
     */
    public static final int SUBJECT_OFFLINE = 0;
    /**
     * 图书类型
     */
    public static final int BOOK_TYPE = 1;
    /**
     * 期刊类型
     */
    public static final int QIKAN_TYPE = 2;
    /**
     * 专题类型
     */
    public static final int ZHUANTI_TYPE = 3;

    /**
     * 课程分类启用状态
     */
    public static final int COURSE_TYPE_ENABLE_STATUS = 1;

    public static final String COURSE_PAGE_FID = "COURSE_PAGE_FID";
    public static final String BOOK_PAGE_FID = "BOOK_PAGE_FID";
    public static final String QIKAN_PAGE_FID = "QIKAN_PAGE_FID";
    public static final String ZHUANTI_PAGE_FID = "ZHUANTI_PAGE_FID";
    public static final String TEAM_PAGE_FID = "TEAM_PAGE_FID";

}
