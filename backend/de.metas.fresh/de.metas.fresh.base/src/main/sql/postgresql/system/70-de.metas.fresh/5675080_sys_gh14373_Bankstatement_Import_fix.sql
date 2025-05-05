-- 2023-02-02T07:44:49.985Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-02-02 08:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579178
;

-- 2023-02-02T07:53:38.648Z
-- URL zum Konzept
INSERT INTO t_alter_column values('i_bankstatement','BankFeeAmt','NUMERIC',null,'0')
;

-- 2023-02-02T07:53:38.722Z
-- URL zum Konzept
INSERT INTO t_alter_column values('i_bankstatement','BankFeeAmt',null,'NULL',null)
;
