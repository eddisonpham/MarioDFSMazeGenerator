package com.example.mario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    int row = 11;
    int col = 11;

    int marioX = 0;
    int marioY = 0;

    GridLayout grid;
    ImageView pics[][] = new ImageView[row][col];
    int board[][] = new int[row][col];

    int rootX=0;
    int rootY=0;
    Stack<String> stackX = new Stack();
    Stack<String> stackY = new Stack();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid=findViewById(R.id.grid);
        int m=0;
        for (int i = 0;i<row;i++){
            for (int j = 0;j<col;j++){
                pics[i][j] = new ImageView(this);
                pics[i][j].setId(m);
                pics[i][j].setImageResource(R.drawable.t7);
                board[i][j]=R.drawable.t7;
                grid.addView(pics[i][j]);
                m++;
            }
        }
        DFS();
    }
    public void DFS(){
        boolean moves[] = {false, false, false, false};
        moves=getValidMoves(moves);
        if (deadEnd(moves)) {
            stackX.pop();
            stackY.pop();
            if (stackX.peek().equals("0")&&stackY.peek().equals("0")) {
                board[marioX][marioY]=R.drawable.t1;
                rootX=marioX;
                rootY=marioY;
                spawnStuff();
                update();
                return;
            }
            rootX = Integer.parseInt(stackX.peek());
            rootY = Integer.parseInt(stackY.peek());
            DFS();
        }else {
            stackX.push(rootX+"");
            stackY.push(rootY+"");
            int random = (int)(Math.random()*4);
            while (!moves[random]) {
                random = (int)(Math.random()*4);
            }
            switch(random) {
                case 0:up();
                    break;
                case 1:down();
                    break;
                case 2: left();
                    break;
                default: right();
                    break;
            }
            DFS();
        }
    }
    public void up () {
        for (int i = 1;i<3;i++) {
            board[rootX][rootY]=R.drawable.t2;
            board[rootX-i][rootY]=R.drawable.t2;
        }
        rootX -=2;
    }
    public void down () {
        for (int i = 1;i<3;i++) {
            board[rootX][rootY]=R.drawable.t2;
            board[rootX+i][rootY]=R.drawable.t2;
        }
        rootX +=2;
    }
    public void left () {
        for (int i = 1;i<3;i++) {
            board[rootX][rootY]=R.drawable.t2;
            board[rootX][rootY-i]=R.drawable.t2;
        }
        rootY -=2;
    }
    public void right () {
        for (int i = 1;i<3;i++) {
            board[rootX][rootY]=R.drawable.t2;
            board[rootX][rootY+i]=R.drawable.t2;
        }
        rootY +=2;
    }

    public void spawnStuff() {
        int mushroomCount=7;
        int gateCount=2;
        int mysterboxCount=5;
        int flowerCount=6;
        while (mushroomCount>0) {
            int randX = (int)(Math.random()*row);
            int randY = (int)(Math.random()*col);
            if (board[randX][randY]==R.drawable.t2) {
                board[randX][randY]=R.drawable.t5;
                mushroomCount--;
            }
        }
        while (gateCount>0) {
            int randX = (int)(Math.random()*row);
            int randY = (int)(Math.random()*col);
            if (board[randX][randY]==R.drawable.t2) {
                board[randX][randY]=R.drawable.t4;
                gateCount--;
            }
        }
        while (mysterboxCount>0) {
            int randX = (int)(Math.random()*row);
            int randY = (int)(Math.random()*col);
            if (board[randX][randY]==R.drawable.t2) {
                board[randX][randY]=R.drawable.t8;
                mysterboxCount--;
            }
        }
        while (flowerCount>0) {
            int randX = (int)(Math.random()*row);
            int randY = (int)(Math.random()*col);
            if (board[randX][randY]==R.drawable.t2) {
                board[randX][randY]=R.drawable.t6;
                flowerCount--;
            }
        }
    }
    public boolean deadEnd(boolean moves[]) {
        for (boolean i:moves) {
            if (i) return false;
        }
        return true;
    }
    public boolean[] getValidMoves(boolean[] moves) {
        if (rootX-2>=0&&!visited(rootX-2,rootY)) {
            moves[0]=true;
        }
        if (rootX+2<row&&!visited(rootX+2,rootY)) {
            moves[1]=true;
        }
        if (rootY-2>=0&&!visited(rootX,rootY-2)) {
            moves[2]=true;
        }
        if (rootY+2<col&&!visited(rootX,rootY+2)) {
            moves[3]=true;
        }
        return moves;
    }
    public boolean visited(int i, int j) {
        return board[i][j]!=R.drawable.t7;
    }
    public void resetBoard(View view) {
        for (int i = 0 ;i<row;i++) {
            for (int j = 0;j<col;j++) {
                board[i][j]=R.drawable.t7;
            }
        }
        DFS();
    }
    public void marioMove(View view) {
        int previousX = marioX;
        int previousY = marioY;
        int id = view.getId();
        if (id==R.id.Left&&marioY-1>=0&&board[marioX][marioY-1]!=R.drawable.t7) {
            marioY--;
        }else if (id==R.id.Right&&marioY+1<col&&board[marioX][marioY+1]!=R.drawable.t7) {
            marioY++;
        }else if(id==R.id.Up&&marioX-1>=0&&board[marioX-1][marioY]!=R.drawable.t7) {
            marioX--;
        }else if(id==R.id.Down&&marioX+1<row&&board[marioX+1][marioY]!=R.drawable.t7) {
            marioX++;
        }
        board[previousX][previousY]=R.drawable.t2;
        board[marioX][marioY]=R.drawable.t1;
        update();
    }
    public void update() {
        for (int i = 0; i<row;i++) {
            for (int j = 0; j<col;j++) {
                pics[i][j].setImageResource(board[i][j]);
            }
        }
    }
}