package uneti.edu.vn.unetituyensinhapp;


import java.io.Serializable;

/**
 * Created by Shushi on 4/25/2017.
 */

public class ClassInfoTuyenSinh implements Serializable {
    private String Header;
    private String InfoMain;
    private String Text;
    private String Time;
    private String url;

    public ClassInfoTuyenSinh() {
    }

    public void setInfoMain(String infoMain) {
        InfoMain = infoMain;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfoMain() {
        return InfoMain;
    }

    public String getText() {
        return Text;
    }

    public String getTime() {
        return Time;
    }

    public String getHeader() {
        return Header;
    }
}

