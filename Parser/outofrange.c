#include <stdio.h>

int main() {
    int data[5] = {65,66,67,68,69};
    
    for( int i=0;i<5; i++)
      printf( "%d ", data[i] );

    printf( "\n");
    for( int i=0;i<5; i++) printf( "%c ", data[i] );

    printf( "%d", data[10]);
    // data[-10] = 0; // ternyata nggak crash
}