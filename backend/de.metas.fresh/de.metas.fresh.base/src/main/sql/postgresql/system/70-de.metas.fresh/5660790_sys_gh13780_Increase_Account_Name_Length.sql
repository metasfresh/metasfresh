-- Column: I_ElementValue.Name
-- 2022-10-17T17:27:46.605Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-10-17 18:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7913
;

-- 2022-10-17T17:28:40.053Z
INSERT INTO t_alter_column values('i_elementvalue','Name','VARCHAR(255)',null,null)
;

-- Column: C_ElementValue.Name
-- 2022-10-17T17:29:58.799Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-10-17 18:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1135
;

-- 2022-10-17T17:30:24.143Z
INSERT INTO t_alter_column values('c_elementvalue','Name','VARCHAR(255)',null,null)
;

-- Column: C_ElementValue_Trl.Name
-- 2022-10-17T19:37:59.621Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-10-17 20:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3163
;

-- 2022-10-17T19:38:01.487Z
INSERT INTO t_alter_column values('c_elementvalue_trl','Name','VARCHAR(255)',null,null)
;

-- Column: AD_Tree.Name
-- 2022-10-17T17:49:29.745Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-10-17 18:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2864
;

-- 2022-10-17T17:50:04.155Z
INSERT INTO t_alter_column values('ad_tree','Name','VARCHAR(255)',null,null)
;

-- Column: I_ElementValue.ElementName
-- 2022-10-17T19:08:52.443Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-10-17 20:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7931
;