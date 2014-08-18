// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.
//screen = @16384 - 24576 - loop 8192 times, writing 0000 0000 0000 0000
//or 1111 1111 1111 1111 to each register

// Put your code here.
//set @totalLoops to 8192
@8192
D=A
@totalLoops
M=D

//set screenStart to 16384
@16384
D=A
@screenStart
M=D

//set regfill to 32767
@0
D=A-1
@regFill
M=D

@0
D=A
@regEmpty
M=D

(START)
  //listen to keyboard output
  @24576
  D=M
  //if a key is pressed D!=0; we will jump to
  //draw screen otherwise jump to clear screen
  @DRAWSCREEN
  D;JNE
  @CLEARSCREEN
  0;JMP

  (DRAWSCREEN)
  @i
  M=0
    (DRAWLOOP)
      //loop 8192 times from 16384 to 24576 setting M value to all binary 1
      @i
       D=M
      @totalLoops
       D=D-M
      @ENDDRAWSCREEN
       D;JGE
      //calculate current memory address
      @screenStart
      D=M
      @i
      D=D+M //D holds memory address
      @cur
      M=D
      @regFill
      D=M
      @cur
      A=M
      M=D
      //increment i and loop
      @i
      D=M+1
      M=D
      @DRAWLOOP
      0;JMP
  (ENDDRAWSCREEN)
    @END
    0;JMP
  //end DRAWSCREEN

  (CLEARSCREEN)
    //put clearscreen code here
  @i
  M=0
    (CLEARLOOP)
      //loop 8192 times from 16384 to 24576 setting M value to all binary 1
      @i
       D=M
      @totalLoops
       D=D-M
      @ENDCLEARSCREEN
       D;JGE
      //calculate current memory address
      @screenStart
      D=M
      @i
      D=D+M //D holds memory address
      @cur
      M=D
      @regEmpty
      D=M
      @cur
      A=M
      M=D
      //increment i and loop
      @i
      D=M+1
      M=D
      @CLEARLOOP
      0;JMP
  (ENDCLEARSCREEN)
    @END
    0;JMP
  //end CLEARSCREEN
(END)
  @START
  0;JMP
//END START