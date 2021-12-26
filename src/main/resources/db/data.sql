INSERT INTO GOODS(
    UID,
    GOODS_NAME,
    GOODS_PRICE,
    REG_DATE
)
VALUES
(NEXT VALUE FOR goods_seq, '사과', 12000, NOW()),
(NEXT VALUE FOR goods_seq, '배', 15000, NOW()),
(NEXT VALUE FOR goods_seq, '바나나', 5000, NOW()),
(NEXT VALUE FOR goods_seq, '토마토', 4500, NOW());