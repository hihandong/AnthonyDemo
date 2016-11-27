package han.anthony.anthonydemo.beans;

/**
 * Created by senior on 2016/11/27.
 */

/**
 * 用来解析网络传来的Json
 * sematic_description是想要的结果
 */
public class LocationJsonBean {

    public DescriptionBean result;


   public class DescriptionBean{
        public String sematic_description;
    }
}
