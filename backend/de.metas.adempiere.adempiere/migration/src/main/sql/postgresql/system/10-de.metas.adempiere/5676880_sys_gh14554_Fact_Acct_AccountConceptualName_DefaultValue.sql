-- Column: Fact_Acct.AccountConceptualName
-- 2023-02-13T15:06:19.212Z
UPDATE AD_Column SET DefaultValue='UNSET',Updated=TO_TIMESTAMP('2023-02-13 17:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585960
;

-- 2023-02-13T15:06:23.017Z
INSERT INTO t_alter_column values('fact_acct','AccountConceptualName','VARCHAR(255)',null,'UNSET')
;

