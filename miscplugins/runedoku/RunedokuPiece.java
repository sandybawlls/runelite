/*
 * Copyright (c) 2019, gazivodag <https://github.com/gazivodag>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.plugins.miscplugins.runedoku;

import static net.runelite.client.plugins.miscplugins.runedoku.RuneToSudoku.*;

public enum RunedokuPiece {

	NPC_PLACED_MIND_RUNE(6436, MIND_RUNE), //1
	NPC_PLACED_FIRE_RUNE(6428, FIRE_RUNE), //2
	NPC_PLACED_BODY_RUNE(6438, BODY_RUNE), //3
	NPC_PLACED_AIR_RUNE(6422, AIR_RUNE), //4
	NPC_PLACED_DEATH_RUNE(6432, DEATH_RUNE), //5
	NPC_PLACED_WATER_RUNE(6424, WATER_RUNE), //6
	NPC_PLACED_CHAOS_RUNE(6430, CHAOS_RUNE), //7
	NPC_PLACED_EARTH_RUNE(6426, EARTH_RUNE), //8
	NPC_PLACED_LAW_RUNE(6434, LAW_RUNE), //9

	PLAYER_PLACED_MIND_RUNE(558, MIND_RUNE), //1
	PLAYER_PLACED_FIRE_RUNE(554, FIRE_RUNE), //2
	PLAYER_PLACED_BODY_RUNE(559, BODY_RUNE), //3
	PLAYER_PLACED_AIR_RUNE(556, AIR_RUNE), //4
	PLAYER_PLACED_DEATH_RUNE(560, DEATH_RUNE), //5
	PLAYER_PLACED_WATER_RUNE(555, WATER_RUNE), //6
	PLAYER_PLACED_CHAOS_RUNE(562, CHAOS_RUNE), //7
	PLAYER_PLACED_EARTH_RUNE(557, EARTH_RUNE), //8
	PLAYER_PLACED_LAW_RUNE(563, LAW_RUNE), //9
	;

	private final int pieceID;
	private final RuneToSudoku pieceForSudoku;

	RunedokuPiece (int pieceID, RuneToSudoku pieceForSudoku) {
		this.pieceID = pieceID;
		this.pieceForSudoku = pieceForSudoku;
	}

	int getId () {
		return pieceID;
	}

	int getPieceForSudoku() {
		return pieceForSudoku.getNumber();
	}

	static RunedokuPiece getById(int pieceID) {
		for (RunedokuPiece e : RunedokuPiece.values()) {
			if (e.getId() == pieceID) {
				return e;
			}
		}
		return null;
	}

}
