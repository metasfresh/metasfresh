-- Column: I_BPartner.Name
-- 2023-06-21T14:35:25.039098100Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2023-06-21 16:35:25.039','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=7901
;

-- 2023-06-21T14:35:27.183145200Z
INSERT INTO t_alter_column values('i_bpartner','Name','VARCHAR(120)',null,null)
;

-- Column: C_BPartner.Name
-- 2023-06-21T15:56:57.278804400Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2023-06-21 17:56:57.278','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=2902
;

-- 2023-06-21T15:56:59.882157Z
INSERT INTO t_alter_column values('c_bpartner','Name','VARCHAR(120)',null,null)
;

