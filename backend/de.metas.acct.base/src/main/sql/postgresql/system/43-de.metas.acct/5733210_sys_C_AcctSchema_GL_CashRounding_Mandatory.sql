-- Run mode: SWING_CLIENT

-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-12T13:35:07.911Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-12 16:35:07.911','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588947
;

-- 2024-09-12T13:35:09.593Z
INSERT INTO t_alter_column values('c_acctschema_gl','CashRounding_Acct','NUMERIC(10)',null,null)
;

-- 2024-09-12T13:35:09.596Z
INSERT INTO t_alter_column values('c_acctschema_gl','CashRounding_Acct',null,'NOT NULL',null)
;

