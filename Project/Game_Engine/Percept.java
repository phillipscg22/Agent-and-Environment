package Project.Game_Engine;

public class Percept {

    private String northPercept;
    private String westPercept;
    private String eastPercept;
    private String southPercept;

    public Percept() {

        northPercept = null;
        westPercept = null;
        eastPercept = null;
        southPercept = null;
    }

    public void setNorthPercept(String northPercept) {

        this.northPercept = northPercept;
    }

    public String getNorthPercept() {

        return northPercept;
    }

    public void setWestPercept(String westPercept) {

        this.westPercept = westPercept;
    }

    public String getWestPercept() {

        return westPercept;
    }

    public void setEastPercept(String eastPercept) {

        this.eastPercept = eastPercept;
    }

    public String getEastPercept() {

        return eastPercept;
    }

    public void setSouthPercept(String southPercept) {

        this.southPercept = southPercept;
    }

    public String getSouthPercept() {

        return southPercept;
    }

    public String toString() {
        return "Percept{" +
                "northPercept='" + northPercept + '\'' +
                ", westPercept='" + westPercept + '\'' +
                ", eastPercept='" + eastPercept + '\'' +
                ", southPercept='" + southPercept + '\'' +
                '}';
    }
}
