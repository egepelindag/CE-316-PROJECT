#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main()
{
    int vize1;
    int vize2;
    int Final;
    float okulortalama;
    double dersort;


    printf("vize1 : ");
    scanf("%d",&vize1);
    printf("%d\n",vize1);

    printf("vize2 : ");
    scanf("%d",&vize2);
    printf("%d\n",vize2);

    printf("Final : ");
    scanf("%d",&Final);
    printf("%d\n",Final);

    dersort = (vize1 + vize2 + Final) / 3.0;

    if(dersort >=60) {
        printf("Dersten gectiniz...\n");
    }
    else if(dersort >50 && dersort<60) {
            printf("Bute girebilirsiniz...\n");

    }
    else {
        printf("Dersten gecemediniz...\n\n\n\n");
    }







}