-- Column: C_AcctSchema_Default.P_CostClearing_Acct
-- 2023-02-16T10:51:53.228Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-16 12:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586066
;

-- 2023-02-16T10:51:54.101Z
INSERT INTO t_alter_column values('c_acctschema_default','P_CostClearing_Acct','NUMERIC(10)',null,null)
;

-- 2023-02-16T10:51:54.106Z
INSERT INTO t_alter_column values('c_acctschema_default','P_CostClearing_Acct',null,'NOT NULL',null)
;

