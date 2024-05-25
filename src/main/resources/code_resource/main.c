#include <stdio.h>

int main() {
    int a;
    for (int i = 0; i < 2; i++) {
        scanf("%d", &a);
        printf("number%d: %d\n", (i+1), a);
    }
    return 0;
}
