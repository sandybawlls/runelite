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

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.Color.RED;

/**
 * @author gazivodag
 */
@Slf4j
class RunedokuOverlay extends Overlay {

	private final RunedokuPlugin plugin;
	private final Client client;
	private final RunedokuUtil util;
	private final RunedokuConfig config;

	@Inject
	private RunedokuOverlay(final RunedokuPlugin plugin, final Client client, final RunedokuUtil util, RunedokuConfig config) {
		super(plugin);
		this.plugin = plugin;
		this.client = client;
		this.util = util;
		this.config = config;

		setPosition(OverlayPosition.DETACHED);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
		setPriority(OverlayPriority.MED);
	}

	private Sudoku solvedSudoku = null;
	@Override
	public Dimension render(Graphics2D graphics) {
		final Widget sudokuScreen = client.getWidget(288,131);

		if (sudokuScreen != null) {
			if (!sudokuScreen.isHidden()) {
				if (solvedSudoku == null) {
					Sudoku sudoku = new Sudoku(util.createTable(client));
					boolean solved = sudoku.solve();
					if (solved) solvedSudoku = sudoku;
					renderNextRunes(graphics,sudoku,solved);
				} else {
					Sudoku sudoku = new Sudoku(util.createTable(client));
					boolean solved = sudoku.solve();
					renderNextRunes(graphics,solvedSudoku,solved);
				}
//				renderReferenceRunes(graphics, solved);
//				renderSolvedPuzzle(graphics, sudoku, solved);
//				renderNextRunes(graphics,sudoku,solved);
			} else {
				solvedSudoku = null;
			}
		}
		return null;
	}

	/**
	 * highlights the runes on the left handside so you know which runes to place on the board
	 * @param graphics
	 * @param solved
	 */
	private void renderReferenceRunes(Graphics2D graphics, boolean solved) {
		//reference runes on the left handside
		for (int i = 121 ; i < 130 ; i++) {
			Widget widget = client.getWidget(288, i);
			if (solved) {
				if (!util.makeSimple(util.createTable(client)).contains(0)) {
					OverlayUtil.renderPolygon(graphics, util.RectangleToPolygon(widget.getBounds()), Color.GREEN);
				} else {
					OverlayUtil.renderPolygon(graphics, util.RectangleToPolygon(widget.getBounds()), util.referenceColors(i));
				}
			} else {
				OverlayUtil.renderPolygon(graphics, util.RectangleToPolygon(widget.getBounds()), RED);
			}
		}
	}

	/**
	 * goes through each 9x9 cell and tells you which piece to place in there
	 * @param graphics
	 * @param sudoku
	 * @param solved
	 */
	private void renderSolvedPuzzle(Graphics2D graphics, Sudoku sudoku, boolean solved) {
		ArrayList<Integer> simpleArr = util.makeSimple(sudoku.getBoard());
		//highlight each cell to tell you which piece to place
		int iteration = 0;
		for (int i = 10 ; i < 91 ; i++) {
			Widget squareToHighlight = client.getWidget(288, i);
			if (solved) {
				if (!util.makeSimple(util.createTable(client)).contains(0)) {
					OverlayUtil.renderPolygon(graphics, util.RectangleToPolygon(squareToHighlight.getBounds()), Color.GREEN);
				} else {
					OverlayUtil.renderPolygon(graphics, util.RectangleToPolygon(squareToHighlight.getBounds()), util.sudokuPieceToColor(simpleArr.get(iteration)));
				}
				iteration++;
			} else {
				OverlayUtil.renderPolygon(graphics, util.RectangleToPolygon(squareToHighlight.getBounds()), RED);
			}
		}
	}

	private void renderNextRunes(Graphics2D graphics, Sudoku sudoku, boolean solutionFound) {
		ArrayList<Integer> solvedBoard = util.makeSimple(sudoku.getBoard());
		ArrayList<Integer> unsolvedBoard = util.makeSimple(util.createTable(client));
		if (solutionFound) {
			for (RunedokuSelection s : RunedokuSelection.values()) {
				int pieceCount = (int)unsolvedBoard.stream().filter(n->n==s.getPieceForSudoku()).count();
				if (pieceCount > 9) {
					solutionFound = false;
					break;
				} else if (pieceCount == 9) continue;
				Widget selectedRune = client.getWidget(288, s.getSelectedIndex());
				if (selectedRune.isHidden()) {
					Widget selectRune = client.getWidget(288, s.getIndex());
					fillPolygon(graphics, util.RectangleToPolygon(selectRune.getBounds()), config.mindRuneColor());
					return;
				} else {
					for (int i = 10 ; i < 91 ; i++) {
						Widget squareToHighlight = client.getWidget(288, i);
						int index = i-10;
						if (solvedBoard.get(index) != s.getPieceForSudoku()) continue;
						if (unsolvedBoard.get(index) != 0) continue;
						fillPolygon(graphics, util.RectangleToPolygon(squareToHighlight.getBounds()), config.mindRuneColor());
					}
					return;
				}
			}
			if (solutionFound) {
				Widget fillCasket = client.getWidget(288, 9);
				fillPolygon(graphics, util.RectangleToPolygon(fillCasket.getBounds()), config.mindRuneColor());
			}
		}
		if (!solutionFound){
			Widget deselectedRune = client.getWidget(288, RunedokuSelection.noRuneIndex);
			if (deselectedRune.isHidden()) {
				fillPolygon(graphics, util.RectangleToPolygon(deselectedRune.getBounds()), config.mindRuneColor());
			} else {
				for (int i = 10 ; i < 91 ; i++) {
					int index = i-10;
					if (unsolvedBoard.get(index) == 0 || solvedBoard.get(index).intValue() == unsolvedBoard.get(index).intValue()) continue;
					Widget squareToHighlight = client.getWidget(288, i);
					fillPolygon(graphics, util.RectangleToPolygon(squareToHighlight.getBounds()), config.mindRuneColor());
				}
			}
		}
	}

	public static void fillPolygon(Graphics2D graphics, Polygon poly, Color color)
	{
		graphics.setColor(color);
		graphics.fillPolygon(poly);
	}
}
