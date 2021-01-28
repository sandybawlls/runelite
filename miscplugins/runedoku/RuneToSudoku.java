package net.runelite.client.plugins.miscplugins.runedoku;

public enum RuneToSudoku {
    MIND_RUNE(1), //1
    FIRE_RUNE(2), //2
    BODY_RUNE(3), //3
    AIR_RUNE(4), //4
    DEATH_RUNE(5), //5
    WATER_RUNE(6), //6
    CHAOS_RUNE(7), //7
    EARTH_RUNE(8), //8
    LAW_RUNE(9), //9
    ;

    private final int number;

    RuneToSudoku(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    static RuneToSudoku getById(int piece) {
        for (RuneToSudoku e : RuneToSudoku.values()) {
            if (e.getNumber() == piece) {
                return e;
            }
        }
        return null;
    }
}
