package me.levylin.easymemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 备忘录
 * Created by LinXin on 2017/1/16 15:12.
 */
@Entity
public class Memo {

    @Id
    private long id;
    private String content;
    private long time;

    @Generated(hash = 103637370)
    public Memo(long id, String content, long time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }

    @Generated(hash = 1901232184)
    public Memo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
