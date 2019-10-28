package org.bitcorej.chain.xmr.core.ed25519.fast;

public class fe_cmov {

//CONVERT #include "fe.h"

/*
Replace (f,g) with (g,g) if b == 1;
replace (f,g) with (f,g) if b == 0.

Preconditions: b in {0,1}.
*/

public static void fe_cmov(int[] f,int[] g,int b)
{
  int f0 = f[0];
  int f1 = f[1];
  int f2 = f[2];
  int f3 = f[3];
  int f4 = f[4];
  int f5 = f[5];
  int f6 = f[6];
  int f7 = f[7];
  int f8 = f[8];
  int f9 = f[9];
  int g0 = g[0];
  int g1 = g[1];
  int g2 = g[2];
  int g3 = g[3];
  int g4 = g[4];
  int g5 = g[5];
  int g6 = g[6];
  int g7 = g[7];
  int g8 = g[8];
  int g9 = g[9];
  int x0 = f0 ^ g0;
  int x1 = f1 ^ g1;
  int x2 = f2 ^ g2;
  int x3 = f3 ^ g3;
  int x4 = f4 ^ g4;
  int x5 = f5 ^ g5;
  int x6 = f6 ^ g6;
  int x7 = f7 ^ g7;
  int x8 = f8 ^ g8;
  int x9 = f9 ^ g9;
  b = -b;
  x0 &= b;
  x1 &= b;
  x2 &= b;
  x3 &= b;
  x4 &= b;
  x5 &= b;
  x6 &= b;
  x7 &= b;
  x8 &= b;
  x9 &= b;
  f[0] = f0 ^ x0;
  f[1] = f1 ^ x1;
  f[2] = f2 ^ x2;
  f[3] = f3 ^ x3;
  f[4] = f4 ^ x4;
  f[5] = f5 ^ x5;
  f[6] = f6 ^ x6;
  f[7] = f7 ^ x7;
  f[8] = f8 ^ x8;
  f[9] = f9 ^ x9;
}


}
