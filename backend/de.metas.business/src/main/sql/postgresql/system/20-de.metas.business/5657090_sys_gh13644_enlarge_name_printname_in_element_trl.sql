-- Column: AD_Element_Trl.Name
-- 2022-09-22T08:33:48.367Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2646
;

-- 2022-09-22T08:33:57.134Z
INSERT INTO t_alter_column values('ad_element_trl','Name','VARCHAR(120)',null,null)
;

-- Column: AD_Element_Trl.PrintName
-- 2022-09-22T08:34:08.626Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:34:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4300
;

-- 2022-09-22T08:34:10.574Z
INSERT INTO t_alter_column values('ad_element_trl','PrintName','VARCHAR(120)',null,null)
;

-- Column: AD_Element_Trl.PO_Name
-- 2022-09-22T08:34:40.234Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6450
;

-- Column: AD_Element_Trl.PO_PrintName
-- 2022-09-22T08:34:50.932Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6451
;

-- 2022-09-22T08:34:53.009Z
INSERT INTO t_alter_column values('ad_element_trl','PO_PrintName','VARCHAR(120)',null,null)
;


-- 2022-09-22T09:26:25.146Z
UPDATE AD_Language SET IsSystemLanguage='Y',Updated=TO_TIMESTAMP('2022-09-22 10:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language_ID=156
;

