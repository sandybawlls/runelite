package net.runelite.client.plugins.miscplugins.runedoku;

import static net.runelite.client.plugins.miscplugins.runedoku.RuneToSudoku.*;
public enum RunedokuSelection {
    PLAYER_SELECT_MIND_RUNE(124, 95, MIND_RUNE),
    PLAYER_SELECT_FIRE_RUNE(125, 94,FIRE_RUNE),
    PLAYER_SELECT_BODY_RUNE(126, 96,BODY_RUNE),
    PLAYER_SELECT_AIR_RUNE(123, 93,AIR_RUNE),
    PLAYER_SELECT_DEATH_RUNE(127, 97,DEATH_RUNE),
    PLAYER_SELECT_WATER_RUNE(122, 92,WATER_RUNE),
    PLAYER_SELECT_CHAOS_RUNE(128, 98,CHAOS_RUNE),
    PLAYER_SELECT_EARTH_RUNE(121, 91,EARTH_RUNE),
    PLAYER_SELECT_LAW_RUNE(129, 99,LAW_RUNE),
    ;
    private final int index;
    private final int selectedIndex;
    private final RuneToSudoku pieceForSudoku;
    public static final int noRuneIndex = 100;

    RunedokuSelection(int index, int selectedIndex, RuneToSudoku pieceForSudoku) {
        this.index = index;
        this.selectedIndex = selectedIndex;
        this.pieceForSudoku = pieceForSudoku;
    }

    public int getIndex() {
        return index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getPieceForSudoku() {
        return pieceForSudoku.getNumber();
    }
}
