package com.noubo.oldmancare.model;

import java.util.Date;

/**
 * Created by Wongnoubo on 2019/3/19.
 */

public class Data {
    private Date update_at;
    private String id;
    private Date create_time;
    private CurrentValue current_value;
    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
    public Date getUpdate_at() {
        return update_at;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    public Date getCreate_time() {
        return create_time;
    }

    public void setCurrent_value(CurrentValue current_value) {
        this.current_value = current_value;
    }
    public CurrentValue getCurrent_value() {
        return current_value;
    }
}
