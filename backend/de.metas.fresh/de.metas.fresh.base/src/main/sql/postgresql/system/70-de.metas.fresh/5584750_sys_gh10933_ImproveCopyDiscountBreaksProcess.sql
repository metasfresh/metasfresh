-- 2021-04-05T12:04:51.906Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540538,'EXISTS (select 1 from m_discountschemabreak db where db.M_Product_ID = M_Product.M_Product_ID )',TO_TIMESTAMP('2021-04-05 14:04:46','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','M_Product_ForDiscountSchemaBreaks','S',TO_TIMESTAMP('2021-04-05 14:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-05T12:05:47.949Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540538,Updated=TO_TIMESTAMP('2021-04-05 14:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541945
;

-- 2021-04-05T12:24:57.105Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-04-05 14:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6600
;

-- 2021-04-05T12:24:57.111Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-04-05 14:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6610
;

-- 2021-04-05T12:24:57.116Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-04-05 14:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6598
;

-- 2021-04-05T12:24:57.120Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-04-05 14:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6611
;

-- 2021-04-05T12:25:40.513Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', IsUpdateable='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-04-05 14:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6598
;

-- 2021-04-05T12:25:40.518Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-04-05 14:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6611
;

-- 2021-04-05T12:25:40.522Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-04-05 14:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560512
;

-- 2021-04-05T12:25:40.525Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-04-05 14:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:26:37.108Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='Y',Updated=TO_TIMESTAMP('2021-04-05 14:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:27:16.013Z
-- URL zum Konzept
UPDATE AD_Column SET IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2021-04-05 14:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:27:53.940Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='''Y''', IsShowFilterInline='N',Updated=TO_TIMESTAMP('2021-04-05 14:27:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:28:23.209Z
-- URL zum Konzept
UPDATE AD_Column SET IsShowFilterIncrementButtons='Y',Updated=TO_TIMESTAMP('2021-04-05 14:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:28:41.843Z
-- URL zum Konzept
UPDATE AD_Column SET IsShowFilterIncrementButtons='N',Updated=TO_TIMESTAMP('2021-04-05 14:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:28:50.308Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2021-04-05 14:28:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:29:19.821Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-04-05 14:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:29:19.827Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-04-05 14:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6611
;

-- 2021-04-05T12:29:19.830Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-04-05 14:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560512
;

-- 2021-04-05T12:29:48.253Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='Y',Updated=TO_TIMESTAMP('2021-04-05 14:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:49:49.796Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='true',Updated=TO_TIMESTAMP('2021-04-05 14:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:51:06.795Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2021-04-05 14:51:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:51:12.268Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2021-04-05 14:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T12:51:28.468Z
-- URL zum Konzept
UPDATE AD_Column SET FilterDefaultValue='Y', FilterOperator='E',Updated=TO_TIMESTAMP('2021-04-05 14:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6601
;

-- 2021-04-05T13:10:59.604Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579001,0,'MakeTargetAsSource',TO_TIMESTAMP('2021-04-05 15:10:54','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Make Target As Source','Make Target As Source',TO_TIMESTAMP('2021-04-05 15:10:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-05T13:10:59.608Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579001 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-05T13:11:44.110Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel als Quelle festlegen', PrintName='Ziel als Quelle festlegen',Updated=TO_TIMESTAMP('2021-04-05 15:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579001 AND AD_Language='de_CH'
;

-- 2021-04-05T13:11:44.138Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579001,'de_CH') 
;

-- 2021-04-05T13:12:04.802Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel als Quelle festlegen', PrintName='Ziel als Quelle festlegen',Updated=TO_TIMESTAMP('2021-04-05 15:12:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579001 AND AD_Language='de_DE'
;

-- 2021-04-05T13:12:04.804Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579001,'de_DE') 
;

-- 2021-04-05T13:12:04.817Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579001,'de_DE') 
;

-- 2021-04-05T13:12:04.819Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='MakeTargetAsSource', Name='Ziel als Quelle festlegen', Description=NULL, Help=NULL WHERE AD_Element_ID=579001
;

-- 2021-04-05T13:12:04.820Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MakeTargetAsSource', Name='Ziel als Quelle festlegen', Description=NULL, Help=NULL, AD_Element_ID=579001 WHERE UPPER(ColumnName)='MAKETARGETASSOURCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-05T13:12:04.826Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='MakeTargetAsSource', Name='Ziel als Quelle festlegen', Description=NULL, Help=NULL WHERE AD_Element_ID=579001 AND IsCentrallyMaintained='Y'
;

-- 2021-04-05T13:12:04.826Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Ziel als Quelle festlegen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579001) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579001)
;

-- 2021-04-05T13:12:04.861Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Ziel als Quelle festlegen', Name='Ziel als Quelle festlegen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579001)
;

-- 2021-04-05T13:12:04.864Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Ziel als Quelle festlegen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579001
;

-- 2021-04-05T13:12:04.866Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Ziel als Quelle festlegen', Description=NULL, Help=NULL WHERE AD_Element_ID = 579001
;

-- 2021-04-05T13:12:04.868Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Ziel als Quelle festlegen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579001
;

-- 2021-04-05T13:12:09.567Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-05 15:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579001 AND AD_Language='en_US'
;

-- 2021-04-05T13:12:09.569Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579001,'en_US') 
;

-- 2021-04-05T13:12:41.414Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579001,0,584808,541958,17,540528,'MakeTargetAsSource',TO_TIMESTAMP('2021-04-05 15:12:36','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,'Y','N','Y','N','Y','N','Ziel als Quelle festlegen',30,TO_TIMESTAMP('2021-04-05 15:12:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-05T13:12:41.416Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541958 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

