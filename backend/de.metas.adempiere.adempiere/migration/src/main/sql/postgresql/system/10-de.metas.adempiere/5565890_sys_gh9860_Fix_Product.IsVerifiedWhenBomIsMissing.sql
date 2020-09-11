

UPDATE m_product
SET IsVerified = 'N'
  , Updated=TO_TIMESTAMP('2020-08-26 13:03:36', 'YYYY-MM-DD HH24:MI:SS')
  , UpdatedBy=100
WHERE TRUE
  AND isbom = 'N'
  AND isverified = 'Y'
;
