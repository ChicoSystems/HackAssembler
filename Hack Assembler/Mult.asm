// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[3], respectively.)

// Put your code here.
//multiplication is 
//for(int i=0; i<R1; i++){
//   sum=sum+R0
//}
//
//
@sum
M=0
@i
M=0
(LOOP)
//check if i-M[R1] is greater than 0 and jump to end
@i
D=M
@R0
D=D-M
@EQUALS
D;JEQ
//else add R1 to sum, increment i, and jump to start of loop
@sum
D=M
@R1

D=D+M
@sum
M=D
@i
D=M+1
M=D
@LOOP
0;JMP

(EQUALS)
@sum
D=M
@R2
M=D
(END)
@END
0;JMP