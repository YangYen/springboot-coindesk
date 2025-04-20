-- 建立幣別與中文名稱對應表
DROP TABLE IF EXISTS currency;

CREATE TABLE currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    chinese_name VARCHAR(50) NOT NULL
);

-- 插入測試資料
INSERT INTO currency (code, chinese_name) VALUES
('USD', '美元'),
('GBP', '英鎊'),
('EUR', '歐元'),
('JPY', '日圓'); 