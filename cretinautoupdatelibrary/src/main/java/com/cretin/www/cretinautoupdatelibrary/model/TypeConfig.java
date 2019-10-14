package com.cretin.www.cretinautoupdatelibrary.model;

/**
 * @date: on 2019-10-13
 * @author: ctetin
 * @email: mxnzp_life@163.com
 * @desc: 类型配置类
 */
public class TypeConfig {
    /**
     * 更新信息的来源
     */
    public static final int DATA_SOURCE_TYPE_MODEL = 10;//调用方提供信息model
    public static final int DATA_SOURCE_TYPE_URL = 11;//通过配置链接供sdk自主请求
    public static final int DATA_SOURCE_TYPE_JSON = 12;//调用方提供信息json

    /**
     * 请求方式类型
     */
    public static final int METHOD_GET = 20; //GET请求
    public static final int METHOD_POST = 21; //POST请求

    /**
     * UI样式类型
     */
    public static final int UI_THEME_AUTO = 300;//sdk自主决定，随机从十几种样式中选择一种，并保证同一个设备选择的唯一的
    public static final int UI_THEME_CUSTOM = 399;//用户自定义UI
    public static final int UI_THEME_A = 301;//类型A，具体样式效果请关注demo
    public static final int UI_THEME_B = 302;//类型B，具体样式效果请关注demo
    public static final int UI_THEME_C = 303;//类型C，具体样式效果请关注demo
    public static final int UI_THEME_D = 304;//类型D，具体样式效果请关注demo
    public static final int UI_THEME_E = 305;//类型E，具体样式效果请关注demo
    public static final int UI_THEME_F = 306;//类型F，具体样式效果请关注demo
    public static final int UI_THEME_G = 307;//类型G，具体样式效果请关注demo
    public static final int UI_THEME_H = 308;//类型H，具体样式效果请关注demo
    public static final int UI_THEME_I = 309;//类型I，具体样式效果请关注demo
    public static final int UI_THEME_J = 310;//类型J，具体样式效果请关注demo
    public static final int UI_THEME_K = 311;//类型K，具体样式效果请关注demo
    public static final int UI_THEME_L = 312;//类型K，具体样式效果请关注demo
}
