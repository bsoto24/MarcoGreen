package doapps.marcogreen.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;

import doapps.marcogreen.R;

/**
 * Created by Bryam Soto on 15/08/2016.
 */
public class User {

    private String title;
    private int grade;
    private int cleanedDays;
    private Drawable icMedal;

    public User(int cleanedDays, Context context) {
        this.cleanedDays = cleanedDays;
        if (cleanedDays >= 0 && cleanedDays < 10) {
            title = "GUARDIAN DEL MEDIO AMBIENTE";
            icMedal = context.getResources().getDrawable(R.drawable.ic_medal_4);
            if (cleanedDays == 0) {
                grade = 4;
            } else if (cleanedDays >= 0 && cleanedDays < 3) {
                grade = 3;
            } else if (cleanedDays >= 3 && cleanedDays < 6) {
                grade = 2;
            } else if (cleanedDays >= 6 && cleanedDays < 10) {
                grade = 1;
            }
        }
        if (cleanedDays >= 10 && cleanedDays < 36) {
            title = "PROTECTOR DEL MEDIO AMBIENTE";
            icMedal = context.getResources().getDrawable(R.drawable.ic_medal_3);
            if (cleanedDays >= 10 && cleanedDays < 15) {
                grade = 4;
            } else if (cleanedDays >= 15 && cleanedDays < 21) {
                grade = 3;
            } else if (cleanedDays >= 21 && cleanedDays < 28) {
                grade = 2;
            } else if (cleanedDays >= 28 && cleanedDays < 36) {
                grade = 1;
            }
        }
        if (cleanedDays >= 36 && cleanedDays < 78) {
            title = "LÍDER DEL MEDIO AMBIENTE";
            icMedal = context.getResources().getDrawable(R.drawable.ic_medal_2);
            if (cleanedDays >= 36 && cleanedDays < 45) {
                grade = 4;
            } else if (cleanedDays >= 45 && cleanedDays < 55) {
                grade = 3;
            } else if (cleanedDays >= 55 && cleanedDays < 66) {
                grade = 2;
            } else if (cleanedDays >= 66 && cleanedDays < 78) {
                grade = 1;
            }
        }
        if (cleanedDays >= 78) {
            title = "HÉROE DEL MEDIO AMBIENTE";
            icMedal = context.getResources().getDrawable(R.drawable.ic_medal_1);
            if (cleanedDays >= 78 && cleanedDays < 91) {
                grade = 4;
            } else if (cleanedDays >= 91 && cleanedDays < 105) {
                grade = 3;
            } else if (cleanedDays >= 105 && cleanedDays < 120) {
                grade = 2;
            } else if (cleanedDays >= 120) {
                grade = 1;
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getCleanedDays() {
        return cleanedDays;
    }

    public void setCleanedDays(int cleanedDays) {
        this.cleanedDays = cleanedDays;
    }

    public Drawable getIcMedal() {
        return icMedal;
    }

    public void setIcMedal(Drawable icMedal) {
        this.icMedal = icMedal;
    }

    @Override
    public String toString() {
        return "User{" +
                "title='" + title + '\'' +
                ", grade=" + grade +
                ", cleanedDays=" + cleanedDays +
                ", icMedal=" + icMedal +
                '}';
    }
}
