package Utility;

import org.json.JSONException;

import java.util.Collection;
import java.util.List;

/**
 * Created by Lenovo on 12/11/2015.
 */
public interface IDoAsyncAction <T> {
    public String DoBackgroundAction(String buffer);

    public void DoResult(String doBackgroundString);

}
