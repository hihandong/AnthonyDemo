package han.anthony.anthonydemo.beans;

/**
 * Created by senior on 2016/11/2.
 */
public class DemoBean {
    private int type;
    private String name;
    private String desc;
    private String className;

    public String getClassName() {
        return className;
    }

    public DemoBean setClassName(String className) {
        this.className = className;
        return this;
    }

    public DemoBean(String name, String desc,String className){
        this.name=name;
        this.desc=desc;
        this.className=className;
    }
    public String getDesc() {
        return desc;
    }

    public DemoBean setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getName() {
        return name;
    }

    public DemoBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getType() {
        return type;
    }

    public DemoBean setType(int type) {
        this.type = type;
        return this;
    }


}
