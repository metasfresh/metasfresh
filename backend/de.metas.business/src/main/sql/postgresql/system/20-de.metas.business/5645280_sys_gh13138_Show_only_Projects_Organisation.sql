CREATE TABLE backup.BKP_ad_column_gh13138_27062022
AS
SELECT * FROM ad_column
;

-- test the backup
SELECT * FROM backup.BKP_ad_column_gh13138_27062022
;

CREATE TABLE backup.BKP_ad_val_rule_gh13138_27062022
AS
SELECT * FROM ad_val_rule
;

-- test the backup
SELECT * FROM backup.BKP_ad_val_rule_gh13138_27062022
;