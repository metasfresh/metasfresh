-- 2017-04-25T17:01:42.624
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543319,0,'VendorReturn_Base_InOutLine_ID',TO_TIMESTAMP('2017-04-25 17:01:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','VendorReturn_Base_InOutLine_ID','VendorReturn_Base_InOutLine_ID',TO_TIMESTAMP('2017-04-25 17:01:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-25T17:01:42.628
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543319 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-04-25T17:02:20.716
-- URL zum Konzept
UPDATE AD_Element SET Description='Original receipt line containing the products that are returned.',Updated=TO_TIMESTAMP('2017-04-25 17:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543319
;

-- 2017-04-25T17:02:20.719
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543319
;

-- 2017-04-25T17:02:20.722
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='VendorReturn_Base_InOutLine_ID', Name='VendorReturn_Base_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Element_ID=543319
;

-- 2017-04-25T17:02:20.751
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='VendorReturn_Base_InOutLine_ID', Name='VendorReturn_Base_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL, AD_Element_ID=543319 WHERE UPPER(ColumnName)='VENDORRETURN_BASE_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-04-25T17:02:20.755
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='VendorReturn_Base_InOutLine_ID', Name='VendorReturn_Base_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Element_ID=543319 AND IsCentrallyMaintained='Y'
;

-- 2017-04-25T17:02:20.756
-- URL zum Konzept
UPDATE AD_Field SET Name='VendorReturn_Base_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543319) AND IsCentrallyMaintained='Y'
;



-- 2017-04-25T17:14:46.604
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556514,543319,0,30,295,320,234,'N','VendorReturn_Base_InOutLine_ID',TO_TIMESTAMP('2017-04-25 17:14:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','Original receipt line containing the products that are returned.','de.metas.swat',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','VendorReturn_Base_InOutLine_ID',0,TO_TIMESTAMP('2017-04-25 17:14:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-25T17:14:46.606
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556514 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;




-- 2017-04-25T17:22:50.100
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556514,558145,0,53277,0,TO_TIMESTAMP('2017-04-25 17:22:49','YYYY-MM-DD HH24:MI:SS'),100,'Original receipt line containing the products that are returned.',0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','N','VendorReturn_Base_InOutLine_ID',155,155,0,1,1,TO_TIMESTAMP('2017-04-25 17:22:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-25T17:22:50.110
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558145 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-25T17:24:56.212
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='VendorReturn_Origin_InOutLine_ID', Name='VendorReturn_Origin_InOutLine_ID', PrintName='VendorReturn_Origin_InOutLine_ID',Updated=TO_TIMESTAMP('2017-04-25 17:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543319
;

-- 2017-04-25T17:24:56.214
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543319
;

-- 2017-04-25T17:24:56.215
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='VendorReturn_Origin_InOutLine_ID', Name='VendorReturn_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Element_ID=543319
;

-- 2017-04-25T17:24:56.241
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='VendorReturn_Origin_InOutLine_ID', Name='VendorReturn_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL, AD_Element_ID=543319 WHERE UPPER(ColumnName)='VENDORRETURN_ORIGIN_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-04-25T17:24:56.242
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='VendorReturn_Origin_InOutLine_ID', Name='VendorReturn_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Element_ID=543319 AND IsCentrallyMaintained='Y'
;

-- 2017-04-25T17:24:56.243
-- URL zum Konzept
UPDATE AD_Field SET Name='VendorReturn_Origin_InOutLine_ID', Description='Original receipt line containing the products that are returned.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543319) AND IsCentrallyMaintained='Y'
;

-- 2017-04-25T17:24:56.254
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='VendorReturn_Origin_InOutLine_ID', Name='VendorReturn_Origin_InOutLine_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543319)
;




-- 2017-04-25T17:27:29.880
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inoutline','ALTER TABLE public.M_InOutLine ADD COLUMN VendorReturn_Origin_InOutLine_ID NUMERIC(10)')
;




-- 2017-04-27T10:52:58.462
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2017-04-27 10:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556514
;

-- 2017-04-27T11:03:49.127
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540716,TO_TIMESTAMP('2017-04-27 11:03:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','M_InOutLine_Origin',TO_TIMESTAMP('2017-04-27 11:03:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-04-27T11:03:49.134
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540716 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-04-27T11:04:27.758
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3529,3529,0,540716,320,TO_TIMESTAMP('2017-04-27 11:04:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N',TO_TIMESTAMP('2017-04-27 11:04:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-27T11:05:15.548
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540716,Updated=TO_TIMESTAMP('2017-04-27 11:05:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556514
;

-- 2017-04-27T11:06:53.135
-- URL zum Konzept
UPDATE AD_Ref_Table SET IsValueDisplayed='Y',Updated=TO_TIMESTAMP('2017-04-27 11:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540716
;


