#include <iostream>
using namespace std;

int main() {
    int a;
    for (int i = 0; i < 2; i++) {
        cin >> a;
        cout << "number" << (i+1) << ": " << a << endl;
    }
    return 0;
}
