-- Column: AD_Window.AD_Element_ID
-- 2022-08-30T11:03:11.714Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-30 14:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563263
;

-- Column: AD_Window.AD_Element_ID
-- 2022-08-30T11:03:18.930Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-08-30 14:03:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563263
;

-- Column: AD_Window.AD_Element_ID
-- 2022-08-30T11:05:28.397Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-30 14:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563263
;

-- 2022-08-30T11:05:30.606Z
INSERT INTO t_alter_column values('ad_window','AD_Element_ID','NUMERIC(10)',null,null)
;

-- 2022-08-30T11:05:30.612Z
INSERT INTO t_alter_column values('ad_window','AD_Element_ID',null,'NOT NULL',null)
;

-- Column: AD_Menu.AD_Element_ID
-- 2022-08-30T11:06:07.660Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-30 14:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563264
;

-- 2022-08-30T11:06:08.324Z
INSERT INTO t_alter_column values('ad_menu','AD_Element_ID','NUMERIC(10)',null,null)
;

-- 2022-08-30T11:06:08.328Z
INSERT INTO t_alter_column values('ad_menu','AD_Element_ID',null,'NOT NULL',null)
;

-- Column: AD_Tab.AD_Element_ID
-- 2022-08-30T11:10:12.289Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-30 14:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563262
;

-- 2022-08-30T11:10:12.814Z
INSERT INTO t_alter_column values('ad_tab','AD_Element_ID','NUMERIC(10)',null,null)
;

-- 2022-08-30T11:10:12.818Z
INSERT INTO t_alter_column values('ad_tab','AD_Element_ID',null,'NOT NULL',null)
;



