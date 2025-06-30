**TÀI LIỆU ĐẶC TẢ YÊU CẦU PHẦN MỀM**

**(Software Requirement Specification – SRS)**

&lt; CGV Cinemas&gt;

**Nhóm sinh viên: 3**

## Mục đích

Mô tả các yêu cầu và phạm vi kiểm thử đối với hệ thống CGV Cinemas. Tài liệu này sẽ xác định các chức năng và tính năng của hệ thống cần được kiểm thử để đảm bảo rằng hệ thống hoạt động đúng và đáp ứng các yêu cầu của người dùng.

## Nhận diện

- **Tên hệ thống**: CGV Cinemas
- **Loại hệ thống**: Ứng dụng di động
- **Đối tượng sử dụng**:
  - Người dùng cuối: Khách hàng sử dụng điện thoại hoặc máy tính bảng để đặt vé
  - Quản trị viên: Quản lý thông tin phim và lịch chiếu qua ứng dụng web
- **Nền tảng triển khai**: Android (8.0 trở lên) và iOS (13.0 trở lên)
- **Môi trường kiểm thử**: Kiểm thử trên thiết bị di động Android và iOS với kết nối mạng Wi-Fi và 4G/5G

## Kiến trúc hệ thống

1. **Frontend**:
    - **Flutter** và **Dart** được sử dụng để phát triển ứng dụng di động, hỗ trợ cả Android và iOS.
    - Flutter giúp tạo giao diện người dùng mượt mà và Dart xử lý logic của ứng dụng.
2. **Backend**:
    - **Firebase** đảm nhiệm các chức năng như xác thực người dùng, quản lý cơ sở dữ liệu và xử lý thanh toán.
3. **Cơ sở dữ liệu**:
    - **Firebase Firestore** lưu trữ các thông tin quan trọng như dữ liệu phim, lịch chiếu và thông tin người dùng.
4. **Môi trường triển khai**:
    - Ứng dụng yêu cầu kết nối mạng ổn định (Wi-Fi hoặc 4G/5G) để đảm bảo trải nghiệm người dùng mượt mà.

# Các yêu cầu chức năng

## Mô tả hệ thống

Hệ thống là một ứng dụng di động giúp người dùng đặt vé xem phim dễ dàng trên nền tảng Android và iOS. Ứng dụng cung cấp các chức năng như tìm kiếm phim, xem lịch chiếu, đặt vé và thanh toán.

## Các chức năng của hệ thống

- Đăng nhập và đăng ký người dùng
- Xem danh sách phim đang chiếu và sắp chiếu
- Xem thông tin phim và lịch chiếu
- Đặt vé xem phim
- Thanh toán vé
- Quản lý thông tin người dùng
- Kiểm tra vé đã mua

## Đặc tả use-case

- - 1. Đăng ký

| **Mã Use Case** | UC001 |
| --- | --- |
| **Tên Use Case** | Đăng ký tài khoản |
| **Tác nhân chính** | Khách |

| **Tác nhân phụ** | Hệ thống |
| --- | --- |
| **Mô tả** | Tác nhân chính (Khách) đăng ký vào hệ thống để sử dụng các<br><br>chức năng hệ thống |
| **Sự kiện kích**<br><br>**hoạt** | Click vào nút "Đăng ký" trên giao diện ứng dụng |
| **Tiền điều kiện** | Tác nhân chưa có tài khoản trên hệ thống. |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn chức năng đăng ký |
| 2   | Hệ thống | Hiển thị giao diện đăng ký |
| 3   | Khách | Nhập tên đăng nhập, email, và mật khẩu,… |
| 4   | Khách | Gửi yêu cầu đăng ký |
| 5   | Hệ thống | Kiểm tra thông tin nhập liệu |
| 6   | Hệ thống | Kiểm tra email hoặc tên đăng nhập đã tồn tại chưa |
| 7   | Hệ thống | Gửi otp đến người dùng |
| 8   | Khách | Nhập otp ứng dụng gửi đến |
| 9   | Hệ thống | Thông báo thành công và chuyển hướng đến trang đăng nhập |

Luồng sự kiện thay thế:

| 5a  | Hệ<br><br>thống | Thông báo lỗi nếu thông tin bắt buộc bị thiếu hoặc không hợp lệ |
| --- | --- | --- |
| 6a  | Hệ<br><br>thống | Thông báo yêu cầu sử dụng email hoặc tên đăng nhập khác nếu đã<br><br>tồn tại |

Hậu điều kiện: Tác nhân chính có thể đăng nhập vào hệ thống sau khi hoàn tất đăng ký và xác thực email hoặc qua Google/Facebook.

- - 1. Đăng nhập tài khoản

| **Mã Use Case** | UC002 |
| --- | --- |
| **Tên Use Case** | Đăng nhập tài khoản |
| **Tác nhân chính** | Khách |
| **Tác nhân phụ** | Hệ thống |

| **Mô tả** | Tác nhân đăng nhập vào hệ thống để sử dụng các chức năng của<br><br>ứng dụng |
| --- | --- |
| **Sự kiện kích**<br><br>**hoạt** | Click vào nút "Đăng nhập" trên giao diện ứng dụng |
| **Tiền điều kiện** | Tác nhân chính đã có tài khoản trên hệ thống |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện<br><br>bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn chức năng đăng nhập |
| 2   | Hệ thống | Hiển thị giao diện đăng nhập |
| 3   | Khách | Nhập tên đăng nhập, mật khẩu |
| 4   | Hệ thống | Kiểm tra thông tin đăng nhập |
| 5   | Hệ thống | Thông báo thành công và chuyển hướng đến giao diện chính<br><br>của ứng dụng. |

Luồng sự kiện thay thế:

| 4a  | Hệ thống | Thông báo lỗi nếu tên đăng nhập hoặc mật khẩu không chính xác |
| --- | --- | --- |

Hậu điều kiện: Tác nhân chính được truy cập vào giao diện chính của ứng dụng.

- - 1. Xem danh sách phim

| **Mã Use Case** | UC003 |
| --- | --- |
| **Tên Use Case** | Xem danh sách phim |
| **Tác nhân**<br><br>**chính** | Khách |
| **Tác nhân phụ** | Hệ thống |
| **Mô tả** | Tác nhân chính (Khách) xem danh sách các bộ phim đang chiếu<br><br>hoặc sắp chiếu |
| **Sự kiện kích**<br><br>**hoạt** | Người dùng đăng nhập vào hệ thống |

| **Tiền điều kiện** | Người dùng đã đăng nhập hệ thống |
| --- | --- |

Luồng sự kiện chính (Thành công):

| STT | Thực<br><br>hiện bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn mục “Movie” |
| 2   | Hệ<br><br>thống | Gửi yêu cầu tới Server để lấy danh sách các bộ phim đang chiếu<br><br>hoặc sắp chiếu |
| 3   | Hệ thống | Nhận danh sách phim từ Server và hiển thị nó trên giao diện người dùng. Danh sách có thể bao gồm các bộ phim theo thể loại,<br><br>thời gian chiếu, và các thông tin cơ bản khác. |
| 4   | Khách | Duyệt qua danh sách phim và chọn một bộ phim để xem thêm<br><br>thông tin chi tiết |

Luồng sự kiện thay thế:

| 2a  | Hệ thống | Nếu có lỗi khi lấy dữ liệu danh sách phim từ Server (hoặc không có<br><br>dữ liệu phim), hệ thống sẽ thông báo lỗi và hiển thị một thông báo rằng không thể tải danh sách phim. |
| --- | --- | --- |

Hậu điều kiện:

- - - - Thành công: Khách có thể xem danh sách phim đang chiếu hoặc sắp chiếu và chọn phim để xem chi tiết.
            - Lỗi: Nếu Server không cung cấp dữ liệu, khách sẽ nhận thông báo lỗi và có thể thử lại hoặc quay lại trang trước đó.

        1. Xem thông tin phim

| **Mã Use Case** | UC004 |
| --- | --- |
| **Tên Use Case** | Xem thông tin phim |
| **Tác nhân**<br><br>**chính** | Khách |
| **Tác nhân phụ** | Hệ thống |

| **Mô tả** | Tác nhân chính (Khách) xem thông tin chi tiết về bộ phim như thể<br><br>loại, đạo diễn, diễn viên, thời lượng, nội dung tóm tắt, v.v. |
| --- | --- |
| **Sự kiện kích**<br><br>**hoạt** | Click vào một bộ phim từ danh sách phim đang chiếu hoặc sắp<br><br>chiếu. |
| **Tiền điều kiện** | Người dùng đã đăng nhập hệ thống<br><br>Server hoạt động và cung cấp dữ liệu chính xác |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện<br><br>bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn phim từ danh sách phim đang chiếu hoặc sắp chiếu. |
| 2   | Hệ thống | Gửi yêu cầu tới Server để lấy thông tin chi tiết của bộ phim |
| 3   | Hệ thống | Hiển thị giao diện chi tiết phim với thông tin đầy đủ. |
| 4   | Khách | Xem thông tin về phim (thể loại, đạo diễn, diễn viên, nội dung,<br><br>thời lượng, v.v.). |

Luồng sự kiện thay thế:

| 2a  | Hệ thống | Nếu thông tin phim không khả dụng hoặc có lỗi trong việc lấy dữ liệu từ Server, hệ thống sẽ thông báo lỗi và hiển thị một thông báo rằng<br><br>thông tin phim không thể được tải. |
| --- | --- | --- |

Hậu điều kiện: Khách có thể xem được thông tin chi tiết về bộ phim (trong trường hợp thành công) hoặc nhận thông báo lỗi nếu dữ liệu không khả dụng.

- - 1. Xem trailer phim

| **Mã Use Case** | UC005 |
| --- | --- |
| **Tên Use Case** | Xem trailer phim |
| **Tác nhân chính** | Khách |
| **Tác nhân phụ** | Hệ thống |
| **Mô tả** | Tác nhân chính (Khách) xem trailer phim trước khi quyết định<br><br>đặt vé |

| **Sự kiện kích**<br><br>**hoạt** | Click vào nút "Xem trailer" trên giao diện chi tiết phim |
| --- | --- |
| **Tiền điều kiện** | Phim phải có trailer được hệ thống lưu trữ hoặc liên kết |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn phim muốn xem trailer |
| 2   | Hệ thống | Hiển thị giao diện chi tiết phim với nút "Xem trailer" |
| 3   | Khách | Click vào nút "Xem trailer" |
| 4   | Hệ thống | Phát trailer phim trên giao diện ứng dụng |

Luồng sự kiện thay thế:

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 4a  | Hệ thống | Thông báo lỗi nếu trailer không khả dụng hoặc liên kết bị lỗi |

Hậu điều kiện: Khách xem được trailer phim

- - 1. Đặt vé xem phim

| **Mã Use Case** | UC006 |
| --- | --- |
| **Tên Use Case** | Đặt vé xem phim |
| **Tác nhân**<br><br>**chính** | Khách |
| **Tác nhân phụ** | Hệ thống |
| **Mô tả** | Tác nhân chính (Khách) chọn suất chiếu, ghế ngồi và thực hiện<br><br>đặt vé xem phim. |
| **Sự kiện kích**<br><br>**hoạt** | Click vào nút "Đặt vé" sau khi chọn suất chiếu. |
| **Tiền điều kiện** | Lịch chiếu phim có sẵn.<br><br>Khách đã đăng nhập vào hệ thống. |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn phim, rạp |
| 2   | Hệ thống | Hiển thị sơ đồ ghế ngồi của suất chiếu |
| 3   | Khách | Chọn ghế ngồi, ngày giờ và xác nhận đặt vé. |
| 4   | Hệ thống | Ghi nhận thông tin đặt vé. |
| 5   | Hệ thống | Chuyển hướng đến giao diện Thanh toán. |

Luồng sự kiện thay thế: Không có

Hậu điều kiện: Thông tin đặt vé được lưu trữ, khách có thể tiếp tục thanh toán để hoàn tất.

- - 1. Thanh toán vé phim

| **Mã Use**<br><br>**Case** | UC007 |
| --- | --- |
| **Tên Use**<br><br>**Case** | Thanh toán vé phim |
| **Tác nhân**<br><br>**chính** | Khách |
| **Tác nhân**<br><br>**phụ** | Hệ thống thanh toán |
| **Mô tả** | Tác nhân chính (Khách) thực hiện thanh toán vé xem phim qua hệ<br><br>thống bằng cách sử dụng thẻ tín dụng, ví điện tử hoặc các phương thức thanh toán khác. |
| **Sự kiện**<br><br>**kích hoạt** | Click vào nút "Thanh toán" sau khi đặt vé thành công. |
| **Tiền điều**<br><br>**kiện** | Khách đã chọn phim, suất chiếu và ghế ngồi.<br><br>Phương thức thanh toán được hỗ trợ. |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện<br><br>bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Click vào nút “Thanh toán” |
| 2   | Hệ thống | Hiển thị giao diện thanh toán gồm thông tin suất chiếu, chỗ<br><br>ngồi, mã giảm giá và các phương thức thanh toán được hỗ trợ |
| 3   | Khách | Chọn phương thức thanh toán (thẻ tín dụng, ví điện tử, v.v.) |
| 4   | Hệ thống<br><br>thanh toán | Xử lý thông tin và thực hiện giao dịch |
| 5   | Hệ thống | Thông báo giao dịch thành công và gửi vé điện tử qua email<br><br>và hiển thị trên ứng dụng |

Luồng sự kiện thay thế:

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 5a  | Hệ thống thanh<br><br>toán | Thông báo lỗi nếu giao dịch bị từ chối |
| 5b  | Hệ thống thanh<br><br>toán | Yêu cầu khách kiểm tra lại thông tin thanh toán nếu<br><br>không hợp lệ |

Hậu điều kiện: Vé được đặt thành công, giao dịch hoàn tất.

- - 1. Quản lý vé đã đặt

| **Mã Use Case** | UC008 |
| --- | --- |
| **Tên Use Case** | Quản lý vé đã đặt |
| **Tác nhân**<br><br>**chính** | Khách |
| **Tác nhân phụ** | Hệ thống |
| **Mô tả** | Tác nhân chính (Khách) xem danh sách các vé đã đặt trước đó và<br><br>kiểm tra thông tin chi tiết. |
| **Sự kiện kích**<br><br>**hoạt** | Click vào mục "Ticket" trên giao diện ứng dụng. |
| **Tiền điều**<br><br>**kiện** | Khách đã chọn phim, suất chiếu và ghế ngồi.<br><br>Phương thức thanh toán được hỗ trợ. |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện<br><br>bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Chọn mục “Ticket” |
| 2   | Hệ thống | Hiển thị danh sách các vé đã đặt |
| 3   | Khách | Chọn vé muốn xem chi tiết |
| 4   | Hệ thống | Hiển thị thông tin chi tiết của vé (phim, rạp, suất chiếu, ghế<br><br>ngồi, mã QR, v.v.). |

Luồng sự kiện thay thế:

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 2a  | Hệ thống | Thông báo không có vé nếu khách chưa đặt vé nào |

Hậu điều kiện: Khách xem được thông tin chi tiết về các vé đã đặt

- - 1. Quản lý thông tin cá nhân

| **Mã Use Case** | UC009 |
| --- | --- |
| **Tên Use Case** | Quản lý thông tin cá nhân |
| **Tác nhân**<br><br>**chính** | Khách |
| **Tác nhân**<br><br>**phụ** | Hệ thống |
| **Mô tả** | Tác nhân chính (Khách) cập nhật thông tin cá nhân như tên, email,<br><br>mật khẩu hoặc hình đại diện. |
| **Sự kiện kích**<br><br>**hoạt** | Click vào mục "Profile" trên giao diện ứng dụng.. |
| **Tiền điều**<br><br>**kiện** | Khách đã đăng nhập vào hệ thống. |

Luồng sự kiện chính (Thành công):

| STT | Thực hiện bởi | Hành động |
| --- | --- | --- |
| 1   | Khách | Click vào mục “Profile” |
| 2   | Hệ thống | Hiển thị giao diện thông tin cá nhân hiện tại. |
| 3   | Khách | Chỉnh sửa thông tin cần thay đổi. |
| 4   | Khách | Xác nhận cập nhật thông tin. |
| 5   | Hệ thống | Lưu thay đổi vào cơ sở dữ liệu. |
| 7   | Hệ thống | Thông báo cập nhật thành công. |

Luồng sự kiện thay thế:

| STT | Thực hiện<br><br>bởi | Hành động |
| --- | --- | --- |
| 5a  | Hệ thống | Thông báo lỗi nếu dữ liệu nhập không hợp lệ (email sai định<br><br>dạng, mật khẩu yếu, v.v.). |

Hậu điều kiện: Thông tin cá nhân của khách được cập nhật thành công.

# Yêu cầu về hiệu năng

- Thời gian phản hồi nhanh: Hệ thống cần phản hồi trong vòng 3 giây cho các yêu cầu của người dùng, bao gồm các thao tác như tìm kiếm, đặt vé, và thanh toán.
- Khả năng xử lý đồng thời: Hệ thống phải có khả năng xử lý ít nhất 1000 yêu cầu đồng thời mà không làm giảm hiệu suất.
- Khả năng mở rộng: Hệ thống phải có khả năng mở rộng để đáp ứng số lượng người dùng tăng trưởng mà không ảnh hưởng đến hiệu suất.
- Độ ổn định cao: Hệ thống cần đảm bảo hoạt động ổn định trong suốt quá trình sử dụng, ngay cả trong các thời điểm cao điểm.
- Xử lý thanh toán an toàn và nhanh chóng: Hệ thống phải đảm bảo thanh toán được thực hiện nhanh chóng và an toàn
