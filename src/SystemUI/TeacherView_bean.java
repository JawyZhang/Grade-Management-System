package SystemUI;

public class TeacherView_bean {
    private String sid;
    private String cid;
    private String score;
    private String tid;

    public TeacherView_bean(String sid, String cid, String score, String tid)
    {
        this.sid = sid;
        this.cid = cid;
        this.score = score;
        this.tid = tid;
    }
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
