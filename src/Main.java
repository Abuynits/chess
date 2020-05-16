import processing.core.PApplet;
import processing.core.PImage;

import javax.swing.*;
import java.util.ArrayList;

public class Main extends PApplet {
    private int alternate = 2;
    private int turn = 1;
    private int startxLoc;
    private int startyLoc;
    private int targetxLoc;
    private int targetyLoc;
    private boolean redCheck = false;
    private boolean blueCheck = false;
    private int valS = 0;
    private boolean checkBlueCheck = false;
    private boolean checkRedCheck = false;
    private boolean makeMove = false;
    private possibleMove move;
    private boolean showPosMove = true;
    private PImage bPawn, bBishop, bQueen, bKing, bRook, bKnigth, rPawn, rBishop, rQueen, rKing, rRook, rKnigth;
    private ArrayList<possibleMove> possible = new ArrayList<>();
    private int grid[][];
    private boolean castleBlueOk = true;
    private boolean castleRedOk = true;
    private boolean blueRookMoved = false;
    private boolean redRookMoved = false;
    boolean buffer = false;

    public void settings() {
        size(800, 800);


        grid = new int[8][8];
    }

    public void setup() {
        background(255, 255, 255);
        fill(0, 0, 0);


        initImages();
        assignLoc();

    }

    public void draw() {
        background(255, 255, 255);
        squares();
        board();
        showPieces();

        valS = grid[startxLoc][startyLoc];
        if (valS != 0) {//check if first click is not empty.
            if (turn == 1) {
                 displayPosMove();

                if (valS <= 10) {//check if click on own piece 1st
                    determinePieceBlue(true, startxLoc, startyLoc);
                      displayPosMove();
                }
            }
            possible.clear();
            if (checkBlueCheck) {
                check4CheckBlue();
                checkBlueCheck = false;
            }


            if (turn == 2) {
                //  displayPosMove();

//                if (check4CheckRed()) {
//                    System.out.println("red check");
//                    //redCheck = true;
//                }
//                if (redCheck) {
//                    // System.out.println("redCheck is in");
//
//                    // redCheckStop = false;
//
//                    if (valS >= 10) {//check if click on own piece 1st
//                        determinePieceRed(true, startxLoc, startyLoc);
//                        //  System.out.println("val is >=10");
//                    }
//
//                }
//
                if (valS >= 10) {//check if click on own piece 1st
                    determinePieceRed(true, startxLoc, startyLoc);
                      displayPosMove();
                }
//                }

            }
            possible.clear();
            if (checkRedCheck) {
                check4CheckRed();

                checkRedCheck = false;
            }

        }

        possible.clear();
    }

    private void displayPosMove() {
        for (possibleMove pos : possible) {
            fill(0, 255, 0);
            ellipse(pos.xloc * 100 + 50, pos.yloc * 100 + 50, 25, 25);

        }
    }//shows possible moves thorugh circles

    private void showPieces() {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    image(bPawn, i * 100, j * 100);
                }
                if (grid[i][j] == 11) {
                    image(rPawn, i * 100, j * 100);
                }
                if (grid[i][j] == 12) {
                    image(rRook, i * 100, j * 100);
                }
                if (grid[i][j] == 2) {
                    image(bRook, i * 100, j * 100);
                }
                if (grid[i][j] == 3) {
                    image(bKnigth, i * 100, j * 100);
                }
                if (grid[i][j] == 13) {
                    image(rKnigth, i * 100, j * 100);
                }
                if (grid[i][j] == 4) {
                    image(bBishop, i * 100, j * 100);
                }
                if (grid[i][j] == 14) {
                    image(rBishop, i * 100, j * 100);
                }
                if (grid[i][j] == 16) {
                    image(rKing, i * 100, j * 100);
                }
                if (grid[i][j] == 6) {
                    image(bKing, i * 100, j * 100);
                }
                if (grid[i][j] == 5) {
                    image(bQueen, i * 100, j * 100);
                }
                if (grid[i][j] == 15) {
                    image(rQueen, i * 100, j * 100);
                }
            }
        }
    }//shows peices live on screen

    private void board() {
        for (int i = 0; i < 8; i++) {
            int deltaX = 100;
            line(deltaX + i * deltaX, 0, deltaX + i * deltaX, 800);
        }
        for (int i = 0; i < 8; i++) {
            int deltaY = 100;
            line(0, deltaY + i * deltaY, 800, deltaY + i * deltaY);
        }
    }//draws lines for gameboard

    private void squares() {
        boolean alt = true;
        for (int i = 0; i < grid.length + 1; i++) {
            for (int j = 0; j < grid[0].length + 1; j++) {
                if (alt) {
                    fill(255, 255, 255);
                    alt = false;
                } else {
                    fill(0, 0, 0);
                    alt = true;
                }
                rect(i * 100, j * 100, 100, 100);
            }
        }

    }//draws squares on board

    private void initImages() {
        bKnigth = loadImage("bKnigth.png");
        bPawn = loadImage("bPawn.png");
        bBishop = loadImage("bBishop.png");
        bQueen = loadImage("bQueen.png");
        bKing = loadImage("bKing.png");
        bRook = loadImage("bRook.png");
        rPawn = loadImage("rPawn.png");
        rKnigth = loadImage("rKnigth.png");
        rBishop = loadImage("rBishop.png");
        rQueen = loadImage("rQueen.png");
        rKing = loadImage("rKing.png");
        rRook = loadImage("rRook.png");
    }//sets up the images;

    private void assignLoc() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = 0;
            }
        }
        for (int i = 0; i < grid[1].length; i++) {
            grid[i][1] = 11;
            grid[i][6] = 1;
        }
        //rook setup
        grid[0][0] = 12;
        grid[0][7] = 2;
        grid[7][0] = 12;
        grid[7][7] = 2;
        // knigth setup
        grid[1][0] = 13;
        grid[6][0] = 13;
        grid[1][7] = 3;
        grid[6][7] = 3;
        //bishop
        grid[2][0] = 14;
        grid[5][0] = 14;
        grid[2][7] = 4;
        grid[5][7] = 4;
        //kings
        grid[4][0] = 16;
        grid[3][7] = 6;
        //queens
        grid[3][0] = 15;
        grid[4][7] = 5;
    }//gives initial value to gamepieces

    public void mouseClicked() {
        if (alternate == 1) {

            alternate = 2;
            showPosMove = false;
            buffer = true;
        } else {

            showPosMove = true;
            alternate = 1;
            buffer = true;

        }
        findCenter();
    }//detects clicks and runs find center meth

    private void switchTurn() {

        if (turn == 1) {
            checkRedCheck = true;//check if blue place a check on red
            buffer = false;//allows a pause
            turn = 2;

        } else if (turn == 2) {
            checkBlueCheck = true;//check if red placed a check on blue
            buffer = false;
            turn = 1;
        }
        // System.out.println("turn switched to "+ turn);
    }//switches the turn of the player

    private void findCenter() {
        int dist;
        int a, b;
        int closest = 100000;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                a = i * 100 + 50;
                b = j * 100 + 50;
                dist = (int) (Math.sqrt((a - mouseX) * (a - mouseX) + (b - mouseY) * (b - mouseY)));

                if (dist <= closest) {
                    closest = dist;
                    if (alternate == 1) {
                        startxLoc = i;
                        startyLoc = j;

                    } else {
                        targetxLoc = i;
                        targetyLoc = j;
                    }
                }

            }
        }
        if (alternate == 1) {
            if (turn == 1) {
                checkBlueCheck = true;

            } else {
                checkRedCheck = true;
            }
            System.out.println("debug: start xloc is " + startxLoc);
            System.out.println("debug: start yloc is " + startyLoc);

        } else {

            System.out.println("debug: target xloc is " + targetxLoc);
            System.out.println("debug: target yloc is " + targetyLoc);


        }
        if (alternate == 2) {
            makeMove = true;
        } else {
            makeMove = false;
        }
    }//assigns values to start loc and target loc ::: also finds the square which was clicked

    private void movePiece() {

        if (grid[targetxLoc][targetyLoc] != 16 && grid[targetxLoc][targetyLoc] != 6) {
            grid[targetxLoc][targetyLoc] = valS;
            grid[startxLoc][startyLoc] = 0;
        }
        switchTurn();
    }//blindly moves and takes the piece does NOT allow king to be taken

    private void showPosMove() {
        if (showPosMove) {
            for (possibleMove pos : possible) {
                System.out.println("pos move is " + pos.xloc + "," + pos.yloc);

            }
            showPosMove = false;
        }
    }//displays possible moves that are legal/ can be made

    private void makeAMove() {

        for (possibleMove possibleMove : possible) {
            if (targetxLoc == possibleMove.xloc && targetyLoc == possibleMove.yloc) {
                if (makeMove) {
                    movePiece();
                    makeMove = false;
                }
            }
        }
        targetyLoc = 10;
        targetxLoc = 10;
    }//goes through array and checks if target location is possible

    private void findPawnMoveBlue(int xLoc, int yLoc) {

        if (yLoc != 0) {
            if (xLoc > 0) {
                if (grid[xLoc - 1][yLoc - 1] >= 10) {
                    move = new possibleMove(xLoc - 1, yLoc - 1);
                    possible.add(move);

                }
            }
            if (xLoc < 7) {
                if (grid[xLoc + 1][yLoc - 1] >= 10) {
                    move = new possibleMove(xLoc + 1, yLoc - 1);
                    possible.add(move);

                }
            }

            if (grid[xLoc][yLoc - 1] == 0) {
                move = new possibleMove(xLoc, yLoc - 1);
                possible.add(move);
                if (yLoc == 6 && grid[xLoc][yLoc - 2] == 0) {
                    move = new possibleMove(xLoc, yLoc - 2);
                    possible.add(move);
                }
            }
        } else {
            String ask = JOptionPane.showInputDialog(null, "Please type: 1 for 'queen',2 for  'rook',3 for  'bishop'4 for  knight'");
            int response = Integer.parseInt(ask);
            if (response == 1) {
                grid[xLoc][yLoc] = 5;

            } else if (response == 2) {
                grid[xLoc][yLoc] = 2;

            } else if (response == 3) {
                grid[xLoc][yLoc] = 4;

            } else if (response == 4) {
                grid[xLoc][yLoc] = 3;

            }
        }

        showPosMove();
    }//finds possible moves for all blue pawns including when reach end

    private void findPawnMoveRed(int xLoc, int yLoc) {
        if (yLoc < 6) {
            buffer = false;
        }
        if (!buffer) {
            if (yLoc != 7) {
                if (xLoc > 0) {
                    if (grid[xLoc - 1][yLoc + 1] <= 10 && grid[xLoc - 1][yLoc + 1] != 0) {
                        move = new possibleMove(xLoc - 1, yLoc + 1);
                        possible.add(move);
                    }
                }
                if (xLoc < 7) {
                    if (grid[xLoc + 1][yLoc + 1] <= 10 && grid[xLoc + 1][yLoc + 1] != 0) {
                        move = new possibleMove(xLoc + 1, yLoc + 1);
                        possible.add(move);
                    }
                }
                if (grid[xLoc][yLoc + 1] == 0) {
                    move = new possibleMove(xLoc, yLoc + 1);
                    possible.add(move);
                    if (yLoc == 1 && grid[xLoc][yLoc + 2] == 0) {
                        move = new possibleMove(xLoc, yLoc + 2);
                        possible.add(move);
                    }
                    showPosMove();
                }//finds possible moves for all red pawns
            }
        }
        if (targetyLoc == 7) {
            buffer = true;
            System.out.println("reached end of board");
            String ask = JOptionPane.showInputDialog(null, "Please type: 1 for 'queen',2 for  'rook',3 for  'bishop'4 for  knight'");
            int response = Integer.parseInt(ask);
            grid[xLoc][yLoc] = 0;
            if (response == 1) {
                grid[targetxLoc][targetyLoc] = 15;

            } else if (response == 2) {
                grid[targetxLoc][targetyLoc] = 12;

            } else if (response == 3) {
                grid[targetxLoc][targetyLoc] = 14;

            } else if (response == 4) {
                grid[targetxLoc][targetyLoc] = 13;

            }
            System.out.println("replaced");
            switchTurn();
        }
    }//finds possible moves for all red pawns including when reach end

    private void findRookMoveBlue(int xLoc, int yLoc) {
        int delta = 1;
        boolean enenyNearby = false;
        boolean stop = false;
        if (xLoc > 0) {
            do {
                if (grid[xLoc - delta][yLoc] > 0 && (grid[xLoc - delta][yLoc] < 10)) {
                    stop = true;
                } else {
                    if (grid[xLoc - delta][yLoc] == 0) {
                        move = new possibleMove(xLoc - delta, yLoc);
                        possible.add(move);
                    }
                    if (grid[xLoc - delta][yLoc] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc - delta, yLoc);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc - delta >= 0 && !enenyNearby && !stop);//find moves to left
        }
        delta = 1;
        enenyNearby = false;
        stop = false;

        if (xLoc < 7) {

            do {
                if (grid[xLoc + delta][yLoc] > 0 && (grid[xLoc + delta][yLoc] < 10)) {
                    stop = true;
                } else {
                    if (grid[xLoc + delta][yLoc] == 0) {
                        move = new possibleMove(xLoc + delta, yLoc);
                        possible.add(move);
                    }
                    if (grid[xLoc + delta][yLoc] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc + delta, yLoc);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc + delta <= 7 && !enenyNearby && !stop);//find moves to left
        }

        delta = 1;
        enenyNearby = false;
        stop = false;

        if (yLoc < 7) {
            do {
                if (grid[xLoc][yLoc + delta] > 0 && (grid[xLoc][yLoc + delta] < 10)) {
                    stop = true;
                } else {
                    if (grid[xLoc][yLoc + delta] == 0) {
                        move = new possibleMove(xLoc, xLoc + delta);
                        possible.add(move);
                    }
                    if (grid[xLoc][yLoc + delta] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc, yLoc + delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (yLoc + delta <= 7 && !enenyNearby && !stop);//find moves down
        }

        delta = 1;
        enenyNearby = false;
        stop = false;

        if (yLoc > 0) {
            do {
                if (grid[xLoc][yLoc - delta] > 0 && (grid[xLoc][yLoc - delta] < 10)) {
                    stop = true;
                } else {
                    if (grid[xLoc][yLoc - delta] == 0) {
                        move = new possibleMove(xLoc, yLoc - delta);
                        possible.add(move);
                    }
                    if (grid[xLoc][yLoc - delta] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc, yLoc - delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (yLoc - delta >= 0 && !enenyNearby && !stop);//find moves to left
        }


        showPosMove();


    }// finds possible moves for all blue rooks

    private void findRookMoveRed(int xLoc, int yLoc) {
        int delta = 1;
        boolean enenyNearby = false;
        boolean stop = false;
        if (xLoc > 0) {
            do {
                if (grid[xLoc - delta][yLoc] > 10) {
                    stop = true;
                } else {
                    if (grid[xLoc - delta][yLoc] == 0) {
                        move = new possibleMove(xLoc - delta, yLoc);
                        possible.add(move);
                    }
                    if (grid[xLoc - delta][yLoc] > 0 && (grid[xLoc - delta][yLoc] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc - delta, yLoc);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc - delta >= 0 && !enenyNearby && !stop);//find moves to left
        }
        delta = 1;
        enenyNearby = false;
        stop = false;

        if (xLoc < 7) {

            do {
                if (grid[xLoc + delta][yLoc] > 10) {
                    stop = true;
                } else {
                    if (grid[xLoc + delta][yLoc] == 0) {
                        move = new possibleMove(xLoc + delta, yLoc);
                        possible.add(move);
                    }
                    if (grid[xLoc + delta][yLoc] > 0 && (grid[xLoc + delta][yLoc] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc + delta, yLoc);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc + delta <= 7 && !enenyNearby && !stop);//find moves to left
        }

        delta = 1;
        enenyNearby = false;
        stop = false;

        if (yLoc < 7) {
            do {
                if (grid[xLoc][yLoc + delta] > 10) {
                    stop = true;
                } else {
                    if (grid[xLoc][yLoc + delta] == 0) {
                        move = new possibleMove(xLoc, yLoc + delta);
                        possible.add(move);
                    }
                    if (grid[xLoc][yLoc + delta] > 0 && (grid[xLoc][yLoc + delta] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc, yLoc + delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (yLoc + delta <= 7 && !enenyNearby && !stop);//find moves down
        }

        delta = 1;
        enenyNearby = false;
        stop = false;

        if (yLoc > 0) {
            do {
                if (grid[xLoc][yLoc - delta] > 10) {
                    stop = true;
                } else {
                    if (grid[xLoc][yLoc - delta] == 0) {
                        move = new possibleMove(xLoc, yLoc - delta);
                        possible.add(move);
                    }
                    if (grid[xLoc][yLoc - delta] > 0 && (grid[xLoc][yLoc - delta] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc, yLoc - delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (yLoc - delta >= 0 && !enenyNearby && !stop);//find moves to left
        }


        showPosMove();


    }// finds possible moves for all red rooks

    private void findBishopMoveBlue(int xLoc, int yLoc) {
        int delta = 1;
        boolean enenyNearby = false;
        boolean stop = false;
        if (xLoc > 0 && yLoc > 0) {
            do {
                if (grid[xLoc - delta][yLoc - delta] > 0 && (grid[xLoc - delta][yLoc - delta] < 10)) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc - delta][yLoc - delta] == 0) {
                        move = new possibleMove(xLoc - delta, yLoc - delta);
                        possible.add(move);
                    }
                    if (grid[xLoc - delta][yLoc - delta] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc - delta, yLoc - delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc - delta >= 0 && yLoc - delta >= 0 && !enenyNearby && !stop);//find moves to top left
        }
        delta = 1;
        enenyNearby = false;
        stop = false;


        if (xLoc < 7 && yLoc > 0) {
            do {
                if (grid[xLoc + delta][yLoc - delta] > 0 && (grid[xLoc + delta][yLoc - delta] < 10)) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc + delta][yLoc - delta] == 0) {
                        move = new possibleMove(xLoc + delta, yLoc - delta);
                        possible.add(move);
                    }
                    if (grid[xLoc + delta][yLoc - delta] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc + delta, yLoc - delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc + delta <= 7 && yLoc - delta >= 0 && !enenyNearby && !stop);//find moves to top right
        }
        delta = 1;
        enenyNearby = false;
        stop = false;

        if (xLoc > 0 && yLoc < 7) {
            do {
                if (grid[xLoc - delta][yLoc + delta] > 0 && (grid[xLoc - delta][yLoc + delta] < 10)) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc - delta][yLoc + delta] == 0) {
                        move = new possibleMove(xLoc - delta, yLoc + delta);
                        possible.add(move);
                    }
                    if (grid[xLoc - delta][yLoc + delta] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc - delta, yLoc + delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc - delta >= 0 && yLoc + delta <= 7 && !enenyNearby && !stop);//find moves to bottom left
        }


        delta = 1;
        enenyNearby = false;
        stop = false;

        if (xLoc < 7 && yLoc < 7) {
            do {
                if (grid[xLoc + delta][yLoc + delta] > 0 && (grid[xLoc + delta][yLoc + delta] < 10)) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc + delta][yLoc + delta] == 0) {
                        move = new possibleMove(xLoc + delta, yLoc + delta);
                        possible.add(move);
                    }
                    if (grid[xLoc + delta][yLoc + delta] > 10) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc + delta, yLoc + delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc + delta <= 7 && yLoc + delta <= 7 && !enenyNearby && !stop);//find moves to bottom right
        }
        showPosMove();

    }//finds possible moves for all blue bishops.

    private void findBishopMoveRed(int xLoc, int yLoc) {
        int delta = 1;
        boolean enenyNearby = false;
        boolean stop = false;
        if (xLoc > 0 && yLoc > 0) {
            do {
                if (grid[xLoc - delta][yLoc - delta] > 10) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc - delta][yLoc - delta] == 0) {
                        move = new possibleMove(xLoc - delta, yLoc - delta);
                        possible.add(move);
                    }
                    if (grid[xLoc - delta][yLoc - delta] > 0 && (grid[xLoc - delta][yLoc - delta] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc - delta, yLoc - delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc - delta >= 0 && yLoc - delta >= 0 && !enenyNearby && !stop);//find moves to top left
        }
        delta = 1;
        enenyNearby = false;
        stop = false;


        if (xLoc < 7 && yLoc > 0) {
            do {
                if (grid[xLoc + delta][yLoc - delta] > 10) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc + delta][yLoc - delta] == 0) {
                        move = new possibleMove(xLoc + delta, yLoc - delta);
                        possible.add(move);
                    }
                    if (grid[xLoc + delta][yLoc - delta] > 0 && (grid[xLoc + delta][yLoc - delta] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc + delta, yLoc - delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc + delta <= 7 && yLoc - delta >= 0 && !enenyNearby && !stop);//find moves to top right
        }
        delta = 1;
        enenyNearby = false;
        stop = false;

        if (xLoc > 0 && yLoc < 7) {
            do {
                if (grid[xLoc - delta][yLoc + delta] > 10) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc - delta][yLoc + delta] == 0) {
                        move = new possibleMove(xLoc - delta, yLoc + delta);
                        possible.add(move);
                    }
                    if (grid[xLoc - delta][yLoc + delta] > 0 && (grid[xLoc - delta][yLoc + delta] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc - delta, yLoc + delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc - delta >= 0 && yLoc + delta <= 7 && !enenyNearby && !stop);//find moves to bottom left
        }


        delta = 1;
        enenyNearby = false;
        stop = false;

        if (xLoc < 7 && yLoc < 7) {
            do {
                if (grid[xLoc + delta][yLoc + delta] > 10) {//reach own player
                    stop = true;
                } else {
                    if (grid[xLoc + delta][yLoc + delta] == 0) {
                        move = new possibleMove(xLoc + delta, yLoc + delta);
                        possible.add(move);
                    }
                    if (grid[xLoc + delta][yLoc + delta] > 0 && (grid[xLoc + delta][yLoc + delta] < 10)) {
                        enenyNearby = true;
                        move = new possibleMove(xLoc + delta, yLoc + delta);
                        possible.add(move);
                    }
                    delta = delta + 1;
                }
            } while (xLoc + delta <= 7 && yLoc + delta <= 7 && !enenyNearby && !stop);//find moves to bottom right
        }
        showPosMove();

    }//finds possible moves for all blue bishops.

    private void findKnightMoveBlue(int xLoc, int yLoc) {
        if (yLoc + 2 <= 7) {
            if (xLoc - 1 >= 0) {
                if (grid[xLoc - 1][yLoc + 2] >= 10 || grid[xLoc - 1][yLoc + 2] == 0) {
                    move = new possibleMove(xLoc - 1, yLoc + 2);
                    possible.add(move);
                }
            }
            if (xLoc + 1 <= 7) {
                if (grid[xLoc + 1][yLoc + 2] >= 10 || grid[xLoc + 1][yLoc + 2] == 0) {
                    move = new possibleMove(xLoc + 1, yLoc + 2);
                    possible.add(move);
                }
            }
        }// down branch
        if (yLoc - 2 >= 0) {
            if (xLoc - 1 >= 0) {
                if (grid[xLoc - 1][yLoc - 2] >= 10 || grid[xLoc - 1][yLoc - 2] == 0) {
                    move = new possibleMove(xLoc - 1, yLoc - 2);
                    possible.add(move);
                }
            }
            if (xLoc + 1 <= 7) {
                if (grid[xLoc + 1][yLoc - 2] >= 10 || grid[xLoc + 1][yLoc - 2] == 0) {
                    move = new possibleMove(xLoc + 1, yLoc - 2);
                    possible.add(move);
                }
            }
        }// up branch
        if (xLoc - 2 >= 0) {
            if (yLoc - 1 >= 0) {
                if (grid[xLoc - 2][yLoc - 1] >= 10 || grid[xLoc - 2][yLoc - 1] == 0) {
                    move = new possibleMove(xLoc - 2, yLoc - 1);
                    possible.add(move);
                }
            }
            if (yLoc + 1 <= 7) {
                if (grid[xLoc - 2][yLoc + 1] >= 10 || grid[xLoc - 2][yLoc + 1] == 0) {
                    move = new possibleMove(xLoc - 2, yLoc + 1);
                    possible.add(move);
                }
            }
        }// left branch
        if (xLoc + 2 <= 7) {
            if (yLoc - 1 >= 0) {
                if (grid[xLoc + 2][yLoc - 1] >= 10 || grid[xLoc + 2][yLoc - 1] == 0) {
                    move = new possibleMove(xLoc + 2, yLoc - 1);
                    possible.add(move);
                }
            }
            if (yLoc + 1 <= 7) {
                if (grid[xLoc + 2][yLoc + 1] >= 10 || grid[xLoc + 2][yLoc + 1] == 0) {
                    move = new possibleMove(xLoc + 2, yLoc + 1);
                    possible.add(move);
                }
            }
        }//right branch
        showPosMove();
    }//finds possible moves for all blue knights.

    private void findKnightMoveRed(int xLoc, int yLoc) {
        if (yLoc + 2 <= 7) {
            if (xLoc - 1 >= 0) {
                if (grid[xLoc - 1][yLoc + 2] <= 10) {
                    move = new possibleMove(xLoc - 1, yLoc + 2);
                    possible.add(move);
                }
            }
            if (xLoc + 1 <= 7) {
                if (grid[xLoc + 1][yLoc + 2] <= 10) {
                    move = new possibleMove(xLoc + 1, yLoc + 2);
                    possible.add(move);
                }
            }
        }// down branch
        if (yLoc - 2 >= 0) {
            if (xLoc - 1 >= 0) {
                if (grid[xLoc - 1][yLoc - 2] <= 10) {
                    move = new possibleMove(xLoc - 1, yLoc - 2);
                    possible.add(move);
                }
            }
            if (xLoc + 1 <= 7) {
                if (grid[xLoc + 1][yLoc - 2] <= 10) {
                    move = new possibleMove(xLoc + 1, yLoc - 2);
                    possible.add(move);
                }
            }
        }// up branch
        if (xLoc - 2 >= 0) {
            if (yLoc - 1 >= 0) {
                if (grid[xLoc - 2][yLoc - 1] <= 10) {
                    move = new possibleMove(xLoc - 2, yLoc - 1);
                    possible.add(move);
                }
            }
            if (yLoc + 1 <= 7) {
                if (grid[xLoc - 2][yLoc + 1] <= 10) {
                    move = new possibleMove(xLoc - 2, yLoc + 1);
                    possible.add(move);
                }
            }
        }// left branch
        if (xLoc + 2 <= 7) {
            if (yLoc - 1 >= 0) {
                if (grid[xLoc + 2][yLoc - 1] <= 10) {
                    move = new possibleMove(xLoc + 2, yLoc - 1);
                    possible.add(move);
                }
            }
            if (yLoc + 1 <= 7) {
                if (grid[xLoc + 2][yLoc + 1] <= 10) {
                    move = new possibleMove(xLoc + 2, yLoc + 1);
                    possible.add(move);
                }
            }
        }//right branch
        showPosMove();
    }//finds possible moves for all red knights.

    private void determinePieceBlue(boolean Move, int xl, int yl) {
        if (valS == 1) {
            findPawnMoveBlue(xl, yl);
        }//pawn

        if (valS == 2) {
            findRookMoveBlue(xl, yl);
            blueRookMoved = true;
        }//Rook
        if (valS == 3) {
            findKnightMoveBlue(xl, yl);
        }
        if (valS == 4) {
            findBishopMoveBlue(xl, yl);
        }//bishop
        if (valS == 5) {
            findBishopMoveBlue(xl, yl);
            findRookMoveBlue(xl, yl);

        }

        if (valS == 6) {
            if (castleBlueOk) {
//                move = new possibleMove(1, 7);
//                possible.add(move);
//                move = new possibleMove(5,7);
//                possible.add(move);
            }
            //   System.out.println("value is 6");
            castleBlue();
            //   System.out.println("done running method castle");

            findKingMoveBlue(xl, yl);
            // System.out.println("done running method find king move");

        }


        if (Move) {
            makeAMove();
        }

    }//determines pieces for blue.

    private void determinePieceRed(boolean Move, int xl, int yl) {
        if (valS == 11) {
            buffer = true;
            findPawnMoveRed(xl, yl);

        }//pawn

        if (valS == 12) {
            findRookMoveRed(xl, yl);
            redRookMoved = true;
        }//Rook
        if (valS == 13) {
            findKnightMoveRed(xl, yl);

        }//knight
        if (valS == 14) {
            findBishopMoveRed(xl, yl);
            //bishop
        }
        if (valS == 15) {
            findBishopMoveRed(xl, yl);
            findRookMoveRed(xl, yl);
        }
        if (valS == 16) {
            if (castleRedOk) {
//                move = new possibleMove(1, 7);
//                possible.add(move);
//                move = new possibleMove(5,7);
//                possible.add(move);
            }
            //   System.out.println("value is 6");
            castleRed();
            //   System.out.println("done running method castle");

            findKingMoveRed(xl, yl);
            // System.out.println("done running method find king move");

        }
        if (Move) {
            makeAMove();
        }

    }//determines pieces for red

    private void findKingMoveBlue(int xLoc, int yLoc) {
        int newXLoc, newYLoc;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //System.out.println("debug info:: i is " + i + " j is " + j);
                if (xLoc == i && yLoc == j) {
                    //  System.out.println(" i,j are not the cetner");
                } else {
                    if (Math.abs(xLoc - i) <= 1) {
                        //System.out.println("delta row is less than 1");

                        if (Math.abs(yLoc - j) <= 1) {
                            // System.out.println("delta col is less than 1");
                            newXLoc = i;
                            newYLoc = j;
                            if (grid[newXLoc][newYLoc] == 0 || grid[newXLoc][newYLoc] >= 10) {
                                //   System.out.println("debug: takeover done at " + rowN + " , " + colN);
                                move = new possibleMove(i, j);
                                possible.add(move);
                            }
                        }
                    }
                }
            }
        }
        showPosMove();
    }//finds possible moves for the blue king

    private void findKingMoveRed(int xLoc, int yLoc) {
        int newXLoc, newYLoc;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //System.out.println("debug info:: i is " + i + " j is " + j);
                if (xLoc == i && yLoc == j) {
                    //  System.out.println(" i,j are not the cetner");
                } else {
                    if (Math.abs(xLoc - i) <= 1) {
                        //System.out.println("delta row is less than 1");

                        if (Math.abs(yLoc - j) <= 1) {
                            // System.out.println("delta col is less than 1");
                            newXLoc = i;
                            newYLoc = j;
                            if (grid[newXLoc][newYLoc] <= 10) {
                                //   System.out.println("debug: takeover done at " + rowN + " , " + colN);
                                move = new possibleMove(i, j);
                                possible.add(move);
                            }
                        }
                    }
                }
            }
        }
        showPosMove();
    }//finds possible moves for the red king

    private void check4CheckRed() {//checks if red king is under check- runs after turn 1;
        //problem withstart xloc and y loc
        //  System.out.println("reached check check");
        int val;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                val = grid[i][j];
                if (val <= 10 & val != 0) {

                    //  System.out.println("startxLoc is "+ i + "startyloc is "+ j);
                    //does consider each pts- 16 in total at start
                    System.out.println(" blue attach check i is " + i + " j is " + j);
                    determinePieceBlue(false, i, j);

                    for (possibleMove p : possible) {
                        if (p.xloc >= 0 && p.xloc <= 7 && p.yloc >= 0 && p.yloc <= 7) {
                            System.out.println("blue possible xloc is " + p.xloc + " y loc " + p.yloc);
                            System.out.println("location is " + grid[p.xloc][p.yloc]);
                            if (grid[p.xloc][p.yloc] == 16) {
                                System.out.println(" red check");
                                //   System.out.println("king is hit- xloc " + p.xloc + " y loc " + p.yloc);

                            }
                        }
                    }

                }
            }
        }

    }//finds if blue is under check

    private void check4CheckBlue() {
        //checks if blue king is under check - runs after turn 2
        //problem withstart xloc and y loc
        //  System.out.println("reached check check");
        int val;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                val = grid[i][j];
                if (val > 10) {

                    System.out.println("red attack i is " + i + "j is " + j);
                    determinePieceRed(false, i, j);

                    for (possibleMove p : possible) {
                        System.out.println("red possible xloc is " + p.xloc + " y loc " + p.yloc);
                        if (grid[p.xloc][p.yloc] == 6) {
                            System.out.println("blue check");
                        }
                    }


                }
            }
        }

    }//finds if red is under check

    private void castleBlue() {
        if (castleBlueOk && !blueRookMoved) {
            System.out.println("castle allowed");

            if (grid[1][7] == 0 && grid[2][7] == 0) {
                if (targetxLoc == 1 && targetyLoc == 7) {
                    System.out.println("empty spaces");
                    grid[0][7] = 0;
                    grid[1][7] = 6;
                    grid[2][7] = 2;
                    grid[3][7] = 0;
                    castleBlueOk = false;
                } else {
                    castleBlueOk = true;
                }
            }
            if (grid[4][7] == 0 && grid[5][7] == 0 && grid[6][7] == 0) {
                if (targetxLoc == 5 && targetyLoc == 7) {
                    System.out.println("empty spaces");
                    grid[7][7] = 0;
                    grid[5][7] = 6;
                    grid[4][7] = 2;
                    grid[3][7] = 0;
                    castleBlueOk = false;
                }
            } else {
                castleBlueOk = true;
            }
        }
    }//determines if blue can castle

    private void castleRed() {
        if (castleRedOk && !redRookMoved) {
            System.out.println("castle allowed");

            if (grid[1][0] == 0 && grid[2][0] == 0 && grid[3][0] == 0) {
                if (targetxLoc == 2 && targetyLoc == 0) {
                    System.out.println("empty spaces");
                    grid[0][0] = 0;
                    grid[4][0] = 0;
                    grid[2][0] = 16;
                    grid[3][0] = 12;
                    castleRedOk = false;
                } else {
                    castleRedOk = true;
                }
            }
            if (grid[5][0] == 0 && grid[6][0] == 0) {
                if (targetxLoc == 6 && targetyLoc == 0) {
                    System.out.println("empty spaces");
                    grid[4][0] = 0;
                    grid[7][0] = 0;
                    grid[6][0] = 16;
                    grid[5][0] = 12;
                    castleBlueOk = false;
                }
            } else {
                castleBlueOk = true;
            }
        }
    }//determines if red can castle

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
