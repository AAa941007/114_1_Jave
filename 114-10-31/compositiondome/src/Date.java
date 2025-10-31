public class Date {
    private int month; // 月份（1-12）
    private int day; // 日（依月份而異，1-31）
    private int year; // 年份

    private static final int[] daysPerMonth =
            {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // 建構子：確認給定年份下月份與日期的正確性
    public Date(int month, int day, int year) {
        // 檢查月份是否在範圍內
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException(
                    "月份 (" + month + ") 必須在 1-12 之間");
        }

        // 檢查日期是否在該月份的範圍內
        if (day <= 0 ||
                (day > daysPerMonth[month] && !(month == 2 && day == 29))) {
            throw new IllegalArgumentException("日期 (" + day +
                    ") 超出指定月份與年份的範圍");
        }

        // 若為2月29日，檢查是否為閏年
        if (month == 2 && day == 29 && !(year % 400 == 0 ||
                (year % 4 == 0 && year % 100 != 0))) {
            throw new IllegalArgumentException("日期 (" + day +
                    ") 超出指定月份與年份的範圍");
        }

        this.month = month;
        this.day = day;
        this.year = year;

        //System.out.printf("建立 Date 物件: %s%n", this);
    }

    // 回傳格式為 月/日/年 的字串
    public String toString() {
        return String.format("%d/%d/%d", month, day, year);
    }
}