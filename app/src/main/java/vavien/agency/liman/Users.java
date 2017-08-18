package vavien.agency.liman;

/**
 * Created by ${Burhan} on 20.07.2017.
 * burhantoprakman@gmail.com
 */

public class Users {

    private String nameSurname;
    private String numb;
    private String pass;
    private String plakaNo;
    private String watchStatu;
    private String readStatu;

    public Users(String nameSurname,String numb,String pass,String plakaNo,String watchStatu,String readStatu){
        this.nameSurname=nameSurname;
        this.numb=numb;
        this.pass=pass;
        this.plakaNo=plakaNo;
        this.watchStatu=watchStatu;
        this.readStatu=readStatu;

    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getPlakaNo() {
        return plakaNo;
    }

    public void setPlakaNo(String plakaNo) {
        this.plakaNo = plakaNo;
    }

    public String getWatchStatu() {
        return watchStatu;
    }

    public void setWatchStatu(String watchStatu) {
        this.watchStatu = watchStatu;
    }

    public String getReadStatu() {
        return readStatu;
    }

    public void setReadStatu(String readStatu) {
        this.readStatu = readStatu;
    }
}
