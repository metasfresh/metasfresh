-- Column: C_Payment.DateTrx
-- 2022-11-21T17:48:19.500Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-11-21 19:48:19.5','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5399
;

-- 2022-11-21T17:48:36.616Z
INSERT INTO t_alter_column values('c_payment','DateTrx','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_BankStatementLine.ValutaDate
-- 2022-11-21T18:16:04.512Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-11-21 20:16:04.511','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5222
;

-- 2022-11-21T18:16:17.794Z
INSERT INTO t_alter_column values('c_bankstatementline','ValutaDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_BankStatement.StatementDate
-- 2022-11-21T18:17:27.369Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-11-21 20:17:27.369','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=4918
;

-- 2022-11-21T18:17:43.165Z
INSERT INTO t_alter_column values('c_bankstatement','StatementDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_BankStatementLine.DateAcct
-- 2022-11-22T10:46:37.049Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-11-22 12:46:37.049','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5216
;

-- 2022-11-22T10:46:50.379Z
INSERT INTO t_alter_column values('c_bankstatementline','DateAcct','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_BankStatementLine.StatementLineDate
-- 2022-11-22T10:47:31.996Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-11-22 12:47:31.996','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=10337
;

-- 2022-11-22T10:47:44.254Z
INSERT INTO t_alter_column values('c_bankstatementline','StatementLineDate','TIMESTAMP WITH TIME ZONE',null,null)
;