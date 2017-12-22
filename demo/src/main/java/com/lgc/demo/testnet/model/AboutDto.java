package com.lgc.demo.testnet.model;


import com.lgc.garylianglib.model.BaseDto;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/11/9 9:59
 *     desc   :   等级
 *     version: 1.0
 * </pre>
 */
public class AboutDto extends BaseDto<AboutDto.AboutBean> {

    public static class AboutBean{


        /**
         * id : efa0bc4897634c6398cba5c99be4c819
         * content :
         我们的态度：
             创造空间；（空间包含市场空间、利润空间、发展空间、创新空间）
             财富共盈；（共盈包含商家共盈、员工共盈、茶友共盈、收藏共盈）
             品牌巅峰；（巅峰包含一流品质、远大理想、优质服务、雄厚资金）
             体现价值；（ 价值包含品牌价值、人生价值、友谊价值、增长价值）
         我们的理想：
             成为同行业产品质量、服务意识、经营理念、价格制度的标杆；成为爱茶之人的终身伴侣成功人士品质生活的要素；高贵身份显著的礼品
         我们的观念：
             一直只做纯料易武茶，物美价实，品质保证，童叟无欺！
         我们的追求：
             全国品牌链锁经营，“茶莫停”总会有一间就在你身边，总有一份关爱时刻陪伴着你。



         Copyright © 2017 茶莫停
         广州市百年保健食品有限公司
         * createTime : null
         */

        private String id;
        private String content;
        private Object createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "AboutBean{" +
                    "id='" + id + '\'' +
                    ", content='" + content + '\'' +
                    ", createTime=" + createTime +
                    '}';
        }
    }
}
