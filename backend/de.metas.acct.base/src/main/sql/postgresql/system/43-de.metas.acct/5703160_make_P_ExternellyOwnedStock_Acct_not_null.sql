-- Run mode: SWING_CLIENT

-- Column: M_Product_Acct.P_ExternallyOwnedStock_Acct
-- 2023-09-18T10:46:44.744Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-18 13:46:44.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587477
;

-- 2023-09-18T10:46:46.443Z
INSERT INTO t_alter_column values('m_product_acct','P_ExternallyOwnedStock_Acct','NUMERIC(10)',null,null)
;

-- 2023-09-18T10:46:46.449Z
INSERT INTO t_alter_column values('m_product_acct','P_ExternallyOwnedStock_Acct',null,'NOT NULL',null)
;

-- Column: M_Product_Category_Acct.P_ExternallyOwnedStock_Acct
-- 2023-09-18T10:47:23.798Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-18 13:47:23.798','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587476
;

-- 2023-09-18T10:47:24.905Z
INSERT INTO t_alter_column values('m_product_category_acct','P_ExternallyOwnedStock_Acct','NUMERIC(10)',null,null)
;

-- 2023-09-18T10:47:24.909Z
INSERT INTO t_alter_column values('m_product_category_acct','P_ExternallyOwnedStock_Acct',null,'NOT NULL',null)
;

-- Column: C_AcctSchema_Default.P_ExternallyOwnedStock_Acct
-- 2023-09-18T10:47:54.077Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-18 13:47:54.077','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587474
;

-- 2023-09-18T10:47:55.131Z
INSERT INTO t_alter_column values('c_acctschema_default','P_ExternallyOwnedStock_Acct','NUMERIC(10)',null,null)
;

-- 2023-09-18T10:47:55.135Z
INSERT INTO t_alter_column values('c_acctschema_default','P_ExternallyOwnedStock_Acct',null,'NOT NULL',null)
;

