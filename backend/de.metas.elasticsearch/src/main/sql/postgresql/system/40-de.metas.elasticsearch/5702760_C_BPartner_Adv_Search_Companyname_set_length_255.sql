-- Run mode: SWING_CLIENT

-- Column: C_BPartner_Adv_Search.Companyname

;

-- 2023-09-14T14:54:39.993223300Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-09-14 17:54:39.993','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587345
;

-- 2023-09-14T14:54:41.656543700Z
INSERT INTO t_alter_column values('c_bpartner_adv_search','Companyname','VARCHAR(255)',null,null)
;

