-- drop table if exists backup.c_payment_bkp20210324;
CREATE TABLE backup.c_payment_bkp20210324 AS
SELECT *
FROM c_payment
;

UPDATE c_payment
SET isallocated='Y'
WHERE isallocated = 'N'
  AND paymentavailable(c_payment_id) = 0
;
