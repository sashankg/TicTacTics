/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactics;

import java.util.Scanner;

/**
 *
 * @author Sashank
 */
public class TextView {

    private TTModel model;
    private String[][] locations;

    public TextView(TTModel m) {
        model = m;
        locations = new String[][]{{"Top Left", "Top Middle", "Top Right"}, {"Middle Left", "Center", "Middle Right"}, {"Bottom Left", "Bottom Middle", "Bottom Right"}};
    }

    public void run() {
        Scanner keyboard = new Scanner(System.in);
        while (!model.didWin(model.getOPlayer(), model.getMegaBoard()) || !model.didWin(model.getXPlayer(), model.getMegaBoard())) {
            if (model.getMegaBoard().getInitialPhase()) {
                for (int i = 0; i < 9; i++) {
                    if (i % 3 == 0 && i != 0) {
                        System.out.println("---------------------");
                    }
                    for (int j = 0; j < 9; j++) {
                        if (j % 3 == 0 && j != 0) {
                            System.out.print("| ");
                        }
                        boolean marked = false;
                        for (int[] a : model.getTurn().getInitialMarks()) {
                            if (a[0] == i && a[1] == j) {
                                marked = true;
                                break;
                            }
                        }
                        if (marked) {
                            System.out.print(model.getTurn().getChar() + " ");
                        } else {
                            char c = model.getSpot(i, j).getOwner().getChar();
                            if (!model.getTurn().canMakeMark(i, j)) {
                                System.out.print("/ ");
                            } else {
                                System.out.print(c + " ");
                            }
                        }
                    }
                    System.out.println();
                }
                System.out.println("Placing initial moves for " + model.getTurn().getChar());
                System.out.println(9 - model.getTurn().getInitialMarksSize() + " initial moves left");
                System.out.print("X: ");
                int y = keyboard.nextInt();
                System.out.print("Y: ");
                int x = keyboard.nextInt();
                if (!model.makeInitialMark(model.getTurn(), x, y)) {
                    System.out.println("Invalid Move");
                }

                if (model.canConfirm(model.getTurn())) {
                    System.out.println("Confirm? (Y/N)");
                    char c = keyboard.next().toLowerCase().charAt(0);
                    if (c == 'y') {
                        model.confirm(model.getTurn());
                    }
                }

            } else {
                for (int i = 0; i < 9; i++) {
                    if (i % 3 == 0 && i != 0) {
                        System.out.println("---------------------");
                    }
                    for (int j = 0; j < 9; j++) {
                        if (j % 3 == 0 && j != 0) {
                            System.out.print("| ");
                        }
                        char c = model.getSpot(i, j).getOwner().getChar();
                        if ((model.getSpot(i, j).isDisabled() 
                                || !model.canMakeMark(model.getTurn(), i, j))&&model.getSpot(i, j).getOwner().getChar() == '_') {
                            System.out.print("/ ");
                        } else {
                            System.out.print(c + " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println(model.getTurn().getChar() + "'s Turn");
                if (model.getMoveLocation().x > -1) {
                    System.out.println("Move in " + locations[model.getMoveLocation().x][model.getMoveLocation().y]);
                } else {
                    System.out.println("Can Move Anywhere");
                }
                System.out.print("X: ");
                int y = keyboard.nextInt();
                System.out.print("Y: ");
                int x = keyboard.nextInt();
                if (model.canMakeMark(model.getTurn(), x, y)) {
                    model.makeMark(model.getTurn(), x, y);
                } else {
                    System.out.println("Invalid Move");
                }
            }
        }
    }
}