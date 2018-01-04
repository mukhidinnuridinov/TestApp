package app.nmn.com.testapplication.bean;

import java.io.Serializable;

/**
 * Created by User-PC on 1/2/2018.
 */

public class EmploymentBean implements Serializable {

    private String name;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}