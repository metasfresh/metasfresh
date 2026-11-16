/*From ID Server*/

-- Make T_Expense_Acct nullable on C_Tax_Acct and C_AcctSchema_Default.
-- Mirrors T_Revenue_Acct ("deliberately-set-only" override semantic):
-- a NULL value means "no override, fall through to product's P_Expense_Acct";
-- a non-NULL value is a deliberate customer choice.

-- Column: C_Tax_Acct.T_Expense_Acct
-- 2026-05-18T15:00:00.000Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2026-05-18 15:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5088
;

-- 2026-05-18T15:00:00.001Z
INSERT INTO t_alter_column values('c_tax_acct','T_Expense_Acct','NUMERIC(10)',null,null)
;

-- 2026-05-18T15:00:00.002Z
INSERT INTO t_alter_column values('c_tax_acct','T_Expense_Acct',null,'NULL',null)
;

-- Column: C_AcctSchema_Default.T_Expense_Acct
-- 2026-05-18T15:00:00.003Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2026-05-18 15:00:00.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=4855
;

-- 2026-05-18T15:00:00.004Z
INSERT INTO t_alter_column values('c_acctschema_default','T_Expense_Acct','NUMERIC(10)',null,null)
;

-- 2026-05-18T15:00:00.005Z
INSERT INTO t_alter_column values('c_acctschema_default','T_Expense_Acct',null,'NULL',null)
;
