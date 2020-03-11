package FinalProject;

public class MazeObjects {
    Boolean wall,blank,cake;

    public MazeObjects(Boolean wall, Boolean blank, Boolean cake) {
        this.wall = wall;
        this.blank = blank;
        this.cake = cake;
    }

    public Boolean getWall() {
        return wall;
    }

    public Boolean getBlank() {
        return blank;
    }

    public Boolean getCake() {
        return cake;
    }

    public void setWall(Boolean wall) {
        this.wall = wall;
        if(wall) {
            this.blank = false;
            this.cake = false;
        }
    }

    public void setBlank(Boolean blank) {
        this.blank = blank;
    }

    public void setCake(Boolean cake) {
        this.cake = cake;
    }
}
