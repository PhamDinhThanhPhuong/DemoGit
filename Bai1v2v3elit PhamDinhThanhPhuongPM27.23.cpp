#include <iostream>
#include <string>
#include <vector>
using namespace std;

// ===== Bài 1 =====
class Diem {
private:
    float x, y;
public:
    void nhap() {
        cout << "Nhap hoanh do x: ";
        cin >> x;
        cout << "Nhap tung do y: ";
        cin >> y;
    }
    void hienThi() {
        cout << "(" << x << ", " << y << ")" << endl;
    }
};

void bai1() {
    cout << "\n=== BAI 1 ===\n";
    Diem d;
    d.nhap();
    cout << "Toa do vua nhap la: ";
    d.hienThi();
}

// ===== Bài 2 & 3 =====
struct SinhVien {
    string hoTen;
    string ngaySinh;
    string gioiTinh;
    float toan, ly, hoa, dtb;

    void nhap() {
        cout << "Nhap ho ten: ";
        getline(cin, hoTen);
        cout << "Nhap ngay sinh (dd/mm/yyyy): ";
        getline(cin, ngaySinh);
        cout << "Nhap gioi tinh: ";
        getline(cin, gioiTinh);
        cout << "Nhap diem Toan: ";
        cin >> toan;
        cout << "Nhap diem Ly: ";
        cin >> ly;
        cout << "Nhap diem Hoa: ";
        cin >> hoa;
        cin.ignore(); // b? k? t? Enter c?n sót
        dtb = (toan + ly + hoa) / 3;
    }

    void hienThi() {
        cout << hoTen << "\t" << ngaySinh << "\t" << gioiTinh
             << "\t" << toan << "\t" << ly << "\t" << hoa
             << "\t" << dtb << endl;
    }
};

void bai2_bai3() {
    cout << "\n=== BAI 2 & BAI 3 ===\n";
    int n;
    cout << "Nhap so luong sinh vien: ";
    cin >> n;
    cin.ignore();

    vector<SinhVien> ds(n);

    for (int i = 0; i < n; i++) {
        cout << "\nNhap thong tin sinh vien thu " << i + 1 << ":\n";
        ds[i].nhap();
    }

    cout << "\n=== Danh sach sinh vien ===\n";
    cout << "Ho ten\tNgay sinh\tGioi tinh\tToan\tLy\tHoa\tDTB\n";
    for (int i = 0; i < n; i++) {
        ds[i].hienThi();
    }
}

// ===== Main =====
int main() {
    int chon;
    do {
        cout << "\n===== MENU =====\n";
        cout << "1. Bai 1: Nhap & hien thi toa do diem\n";
        cout << "2. Bai 2 & 3: Quan ly sinh vien\n";
        cout << "0. Thoat\n";
        cout << "Chon bai muon chay: ";
        cin >> chon;
        cin.ignore(); // b? Enter

        switch (chon) {
        case 1:
            bai1();
            break;
        case 2:
            bai2_bai3();
            break;
        case 0:
            cout << "Thoat chuong trinh.\n";
            break;
        default:
            cout << "Lua chon khong hop le.\n";
        }
    } while (chon != 0);

    return 0;
}
