/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import APCSAnimation.AnimatedObject;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sashank
 */
public class AIControl implements AnimatedObject {

    TTModel model;
    TTView view;
    int delay;
    Point choice;
    boolean thinking;
    private final int DEPTH = 5;

    public AIControl(TTModel m, TTView v) {
        model = m;
        view = v;
        delay = 0;
        thinking = false;
    }

    @Override
    public void update(int dt) {
        if (model.getMegaBoard().getInitialPhase()) {
            for (int i = 0; i < 9; i++) {
                ArrayList<Point> moves = getPossibleInitialMoves();
                Point p = moves.get((int) (moves.size() * Math.random()));
                model.makeInitialMark(view.getPlayer(), p.x, p.y);
            }
            model.confirm(view.getPlayer());
        } else {
            if (model.getTurn() == view.getPlayer()) {
                if(choice == null && !thinking)
                {
                    System.out.println("inside");
                    thinking = true;
                    minimax(new PrimitiveModel(model), 0, new ArrayList<PrimitiveModel>());
                    model.makeMark(view.getPlayer(), choice.x, choice.y);
                    thinking = false;
                    choice = null;
                }
            }
        }
    }

    public int minimax(PrimitiveModel m, int depth, ArrayList<PrimitiveModel> visited) {
        System.out.println("depth: " + depth);
        if (m.getWinner() != '_' || depth > 4) {
            return getScore(m);
        }
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<Point> moves = new ArrayList<>();
        for (Point p : getPossibleMoves(m, m.getTurn())) {
            PrimitiveModel possible = new PrimitiveModel(m);
            boolean didVisit = false;
            for(PrimitiveModel a : visited)
            {
                if(a.equals(possible))
                {
                    didVisit = true;
                }
            }
            if(!didVisit)
            {
                visited.add(possible);
                possible.makeMark(m.getTurn(), p.x, p.y);
                scores.add(minimax(possible, depth + 1, visited));
                moves.add(p);
                System.out.println("hello");
            }
        }

        if (m.getTurn() == view.getPlayer().getChar()) {
            int i = maxIndex(scores);
            choice = moves.get(i);
            return scores.get(i);
        } else {
            int i = minIndex(scores);
            choice = moves.get(i);
            return scores.get(i);
        }
    }

    private int maxIndex(ArrayList<Integer> a) {
        int maxIndex = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > a.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private int minIndex(ArrayList<Integer> a) {
        int minIndex = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) < a.get(minIndex)) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public int getScore(PrimitiveModel m) {
        if (m.getWinner() == view.getPlayer().getChar()) {
            return 10;
        } else if (m.getWinner() != 'W' && m.getWinner() != '_') {
            return -10;
        }
        return 0;
    }

    @Override
    public void paint(Graphics g) {
    }

    private ArrayList<Point> getPossibleInitialMoves() {
        ArrayList<Point> o = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (view.getPlayer().canMakeMark(i, j)) {
                    o.add(new Point(i, j));
                }
            }
        }
        return o;
    }

    private ArrayList<Point> getPossibleMoves(PrimitiveModel m, char c) {
        ArrayList<Point> o = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (m.canMakeMark(c, i, j)) {
                    o.add(new Point(i, j));
                }
            }
        }
        return o;
    }

    private int negamax(PrimitiveModel m, int depth, int color) {
        //delay++;
        if (depth == 0 || m.getWinner() != '_') {
            if (m.getWinner() == 'W' || m.getWinner() == '_') {
                return 0;
            }
            return color * 10;
        }
        int bestValue = -99999;
        for (Point p : getPossibleMoves(m, m.getTurn())) {
            PrimitiveModel temp = new PrimitiveModel(m);
            temp.makeMark(m.getTurn(), p.x, p.y);
            int i = -negamax(temp, depth - 1, -color);
            if (i > bestValue) {
                bestValue = i;
                if (depth == DEPTH) {
                    choice = p;
                }
            }
        }
        return bestValue;
    }
}
