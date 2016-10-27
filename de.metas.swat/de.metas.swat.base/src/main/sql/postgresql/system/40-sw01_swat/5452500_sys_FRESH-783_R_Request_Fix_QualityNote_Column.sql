-- 26.10.2016 18:25
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=555286, AD_Key=555285, AD_Table_ID=540794,Updated=TO_TIMESTAMP('2016-10-26 18:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 26.10.2016 18:25
-- URL zum Konzept
UPDATE AD_Reference SET Name='M_QualityNote_List',Updated=TO_TIMESTAMP('2016-10-26 18:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 26.10.2016 18:25
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540692
;

-- 26.10.2016 18:26
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555284,Updated=TO_TIMESTAMP('2016-10-26 18:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 26.10.2016 18:27
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2016-10-26 18:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 26.10.2016 18:28
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540351,TO_TIMESTAMP('2016-10-26 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','R_Request_PerformanceType','S',TO_TIMESTAMP('2016-10-26 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 18:34
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='@M_QualityNote_ID/-1@ = -1 | AD_Ref_List.Value = (select PerformanceType from M_QualityNote where M_QualityNote_ID = @M_QualityNote_ID/-1@)',Updated=TO_TIMESTAMP('2016-10-26 18:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;

-- 26.10.2016 18:34
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540351,Updated=TO_TIMESTAMP('2016-10-26 18:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555046
;

-- 26.10.2016 18:34
-- URL zum Konzept
UPDATE AD_Val_Rule SET Name='R_Request_PerformanceType_ValRule',Updated=TO_TIMESTAMP('2016-10-26 18:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;

commit;
-- 26.10.2016 18:42
-- URL zum Konzept
INSERT INTO t_alter_column values('r_request','PerformanceType','VARCHAR(50)',null,'NULL')
;


commit;

-- 26.10.2016 18:42
-- URL zum Konzept
INSERT INTO t_alter_column values('r_request','QualityNote','VARCHAR(250)',null,'NULL')
;


commit;
-- 26.10.2016 18:44
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555285,Updated=TO_TIMESTAMP('2016-10-26 18:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 26.10.2016 18:45
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='@QualityNote/-1@ = -1 | AD_Ref_List.Value = (select PerformanceType from M_QualityNote where M_QualityNote_ID = @QualityNote/-1@)',Updated=TO_TIMESTAMP('2016-10-26 18:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;

-- 26.10.2016 18:46
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555284,Updated=TO_TIMESTAMP('2016-10-26 18:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 26.10.2016 18:46
-- URL zum Konzept
INSERT INTO t_alter_column values('r_request','QualityNote','VARCHAR(250)',null,'NULL')
;

-- 27.10.2016 11:21
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='@QualityNote/-1@ = -1 OR (AD_Ref_List.Value = (select PerformanceType from M_QualityNote where M_QualityNote_ID = @QualityNote/-1@))',Updated=TO_TIMESTAMP('2016-10-27 11:21:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;


-- 27.10.2016 12:35
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 12:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 27.10.2016 12:35
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 12:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555046
;

-- 27.10.2016 13:05
-- URL zum Konzept
UPDATE AD_Reference SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 13:05:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:06
-- URL zum Konzept
UPDATE AD_Ref_Table SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 13:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:06
-- URL zum Konzept
INSERT INTO t_alter_column values('r_request','QualityNote','VARCHAR(250)',null,'NULL')
;

-- 27.10.2016 13:06
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555285,Updated=TO_TIMESTAMP('2016-10-27 13:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:07
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='@QualityNote/-1@ = -1 OR (AD_Ref_List.Value = (select PerformanceType from M_QualityNote where value = @QualityNote/-1@))',Updated=TO_TIMESTAMP('2016-10-27 13:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;

-- 27.10.2016 13:15
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='@QualityNote''@ IS NULL OR (AD_Ref_List.Value = (select PerformanceType from M_QualityNote where value = @QualityNote@))',Updated=TO_TIMESTAMP('2016-10-27 13:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;

-- 27.10.2016 13:19
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555284,Updated=TO_TIMESTAMP('2016-10-27 13:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:19
-- URL zum Konzept
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2016-10-27 13:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:20
-- URL zum Konzept
UPDATE AD_Reference SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 13:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:20
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2016-10-27 13:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 27.10.2016 13:20
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=30,Updated=TO_TIMESTAMP('2016-10-27 13:20:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 27.10.2016 13:22
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=543213, AD_Reference_ID=19, ColumnName='M_QualityNote_ID', Description=NULL, EntityType='de.metas.swat', FieldLength=10, Help=NULL, Name='Qualität-Notiz',Updated=TO_TIMESTAMP('2016-10-27 13:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

-- 27.10.2016 13:32
-- URL zum Konzept
ALTER TABLE R_Request ADD M_QualityNote_ID NUMERIC(10) DEFAULT NULL 
;

-- 27.10.2016 13:44
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='@M_QualityNote_ID/-1@ = -1 OR (AD_Ref_List.Value = (select PerformanceType from M_QualityNote where M_QualityNote_ID =@M_QualityNote_ID/-1@))',Updated=TO_TIMESTAMP('2016-10-27 13:44:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540351
;

-- 27.10.2016 13:45
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-10-27 13:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555041
;

