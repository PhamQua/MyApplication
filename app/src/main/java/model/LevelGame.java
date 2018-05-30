package model;

/**
 * Created by Henry on 4/10/2018.
 */

public class LevelGame {
    private int mWidthOfGame;
    private int mHeightOfGame;
    private int mNumerOfHoles;
    private int mIntervalGame;
    private int mScoreNextLv;
    private int mAddScore;

    public LevelGame() {
    }

    public LevelGame(int mWidthOfGame, int mHeightOfGame, int mNumerOfHoles, int mIntervalGame, int mScoreNextLv, int mAddScore) {
        this.mWidthOfGame = mWidthOfGame;
        this.mHeightOfGame = mHeightOfGame;
        this.mNumerOfHoles = mNumerOfHoles;
        this.mIntervalGame = mIntervalGame;
        this.mScoreNextLv = mScoreNextLv;
        this.mAddScore = mAddScore;
    }

    public int getmWidthOfGame() {
        return mWidthOfGame;
    }

    public void setmWidthOfGame(int mWidthOfGame) {
        this.mWidthOfGame = mWidthOfGame;
    }

    public int getmHeightOfGame() {
        return mHeightOfGame;
    }

    public void setmHeightOfGame(int mHeightOfGame) {
        this.mHeightOfGame = mHeightOfGame;
    }

    public int getmNumerOfHoles() {
        return mNumerOfHoles;
    }

    public void setmNumerOfHoles(int mNumerOfHoles) {
        this.mNumerOfHoles = mNumerOfHoles;
    }

    public int getmIntervalGame() {
        return mIntervalGame;
    }

    public void setmIntervalGame(int mIntervalGame) {
        this.mIntervalGame = mIntervalGame;
    }

    public int getmScoreNextLv() {
        return mScoreNextLv;
    }

    public void setmScoreNextLv(int mScoreNextLv) {
        this.mScoreNextLv = mScoreNextLv;
    }

    public int getmAddScore() {
        return mAddScore;
    }

    public void setmAddScore(int mAddScore) {
        this.mAddScore = mAddScore;
    }
}
