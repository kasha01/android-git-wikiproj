package Utility;

/**
 * Created by Lenovo on 1/14/2016.
 */
public class MasterParam {
    public String query;
    public int userId;
    public String content;
    public String tag;

    public MasterParam(String query){
        this.query = query;
    }
    public MasterParam(int userId,String content){
        this.userId = userId;
        this.content = content;
    }
    public MasterParam(int userId,String content,String tag){
        this.userId = userId;
        this.content = content;
        this.tag = tag;
    }
}
