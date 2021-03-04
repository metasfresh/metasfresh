-- 2021-02-16T10:30:48.860Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541259,TO_TIMESTAMP('2021-02-16 11:30:48','YYYY-MM-DD HH24:MI:SS'),100,'Problem Area Code','D','Y','N','ProblemCode',TO_TIMESTAMP('2021-02-16 11:30:48','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-02-16T10:30:48.861Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541259 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-16T10:32:05.789Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Description='Problembereichscode', IsTranslated='Y', Name='Problembereichscode',Updated=TO_TIMESTAMP('2021-02-16 11:32:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541259
;

-- 2021-02-16T10:32:16.849Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 11:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541259
;

-- 2021-02-16T10:32:26.621Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Description='Problembereichscode', IsTranslated='Y', Name='Problembereichscode',Updated=TO_TIMESTAMP('2021-02-16 11:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541259
;

-- 2021-02-16T10:34:02.353Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541259,542274,TO_TIMESTAMP('2021-02-16 11:34:02','YYYY-MM-DD HH24:MI:SS'),100,'Electrical surge damage','D','Y','Electrical surge damage',TO_TIMESTAMP('2021-02-16 11:34:02','YYYY-MM-DD HH24:MI:SS'),100,'ED','Electrical surge damage')
;

-- 2021-02-16T10:34:02.356Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542274 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T10:41:00.817Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541259,542275,TO_TIMESTAMP('2021-02-16 11:41:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','No detactable fault',TO_TIMESTAMP('2021-02-16 11:41:00','YYYY-MM-DD HH24:MI:SS'),100,'NF','No detactable fault')
;

-- 2021-02-16T10:41:00.820Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542275 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T10:42:33.744Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Kein Fehler feststellbar',Updated=TO_TIMESTAMP('2021-02-16 11:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542275
;

-- 2021-02-16T10:42:44.206Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Kein Fehler feststellbar',Updated=TO_TIMESTAMP('2021-02-16 11:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542275
;

-- 2021-02-16T10:42:52.255Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 11:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542275
;

-- 2021-02-16T10:43:04.167Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Kein Fehler feststellbar',Updated=TO_TIMESTAMP('2021-02-16 11:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542275
;

-- 2021-02-16T10:49:01.362Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541259,542276,TO_TIMESTAMP('2021-02-16 11:49:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Configuration Error',TO_TIMESTAMP('2021-02-16 11:49:01','YYYY-MM-DD HH24:MI:SS'),100,'CE','Configuration Error')
;

-- 2021-02-16T10:49:01.364Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542276 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T10:49:31.785Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541259,542277,TO_TIMESTAMP('2021-02-16 11:49:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Software Fault',TO_TIMESTAMP('2021-02-16 11:49:31','YYYY-MM-DD HH24:MI:SS'),100,'SF','Software Fault')
;

-- 2021-02-16T10:49:31.786Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542277 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T10:50:03.068Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541259,542278,TO_TIMESTAMP('2021-02-16 11:50:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hardware Fault',TO_TIMESTAMP('2021-02-16 11:50:02','YYYY-MM-DD HH24:MI:SS'),100,'HF','Hardware Fault')
;

-- 2021-02-16T10:50:03.071Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542278 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T10:56:54.549Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578762,0,'ProblemCode',TO_TIMESTAMP('2021-02-16 11:56:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ProblemCode','ProblemCode',TO_TIMESTAMP('2021-02-16 11:56:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-16T10:56:54.550Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578762 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-16T10:57:36.071Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Problembereichscode', PrintName='Problembereichscode',Updated=TO_TIMESTAMP('2021-02-16 11:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578762 AND AD_Language='de_CH'
;

-- 2021-02-16T10:57:36.086Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578762,'de_CH') 
;

-- 2021-02-16T10:57:43.116Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Problembereichscode', PrintName='Problembereichscode',Updated=TO_TIMESTAMP('2021-02-16 11:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578762 AND AD_Language='de_DE'
;

-- 2021-02-16T10:57:43.117Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578762,'de_DE') 
;

-- 2021-02-16T10:57:43.122Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578762,'de_DE') 
;

-- 2021-02-16T10:57:43.123Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ProblemCode', Name='Problembereichscode', Description=NULL, Help=NULL WHERE AD_Element_ID=578762
;

-- 2021-02-16T10:57:43.123Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ProblemCode', Name='Problembereichscode', Description=NULL, Help=NULL, AD_Element_ID=578762 WHERE UPPER(ColumnName)='PROBLEMCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-16T10:57:43.124Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ProblemCode', Name='Problembereichscode', Description=NULL, Help=NULL WHERE AD_Element_ID=578762 AND IsCentrallyMaintained='Y'
;

-- 2021-02-16T10:57:43.124Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Problembereichscode', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578762) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578762)
;

-- 2021-02-16T10:57:43.133Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Problembereichscode', Name='Problembereichscode' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578762)
;

-- 2021-02-16T10:57:43.134Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Problembereichscode', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578762
;

-- 2021-02-16T10:57:43.135Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Problembereichscode', Description=NULL, Help=NULL WHERE AD_Element_ID = 578762
;

-- 2021-02-16T10:57:43.135Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Problembereichscode', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578762
;

-- 2021-02-16T10:57:47.782Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 11:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578762 AND AD_Language='en_US'
;

-- 2021-02-16T10:57:47.783Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578762,'en_US') 
;

-- 2021-02-16T10:57:54.512Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Problembereichscode', PrintName='Problembereichscode',Updated=TO_TIMESTAMP('2021-02-16 11:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578762 AND AD_Language='nl_NL'
;

-- 2021-02-16T11:08:36.261Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Konfigurationsfehler',Updated=TO_TIMESTAMP('2021-02-16 13:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542276
;

-- 2021-02-16T11:08:44.325Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Konfigurationsfehler',Updated=TO_TIMESTAMP('2021-02-16 13:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542276
;

-- 2021-02-16T11:08:52.924Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 13:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542276
;

-- 2021-02-16T11:09:01.791Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Konfigurationsfehler',Updated=TO_TIMESTAMP('2021-02-16 13:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542276
;

-- 2021-02-16T11:09:34.852Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Überspannungsschaden', IsTranslated='Y', Name='Überspannungsschaden',Updated=TO_TIMESTAMP('2021-02-16 13:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542274
;

-- 2021-02-16T11:09:42.110Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 13:09:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542274
;

-- 2021-02-16T11:09:50.955Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Überspannungsschaden', IsTranslated='Y', Name='Überspannungsschaden',Updated=TO_TIMESTAMP('2021-02-16 13:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542274
;

-- 2021-02-16T11:10:00.466Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Überspannungsschaden', IsTranslated='Y', Name='Überspannungsschaden',Updated=TO_TIMESTAMP('2021-02-16 13:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542274
;

-- 2021-02-16T11:14:06.454Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Hardwarefehler', IsTranslated='Y', Name='Hardwarefehler',Updated=TO_TIMESTAMP('2021-02-16 13:14:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542278
;

-- 2021-02-16T11:14:13.370Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Hardwarefehler', IsTranslated='Y', Name='Hardwarefehler',Updated=TO_TIMESTAMP('2021-02-16 13:14:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542278
;

-- 2021-02-16T11:14:18.235Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 13:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542278
;

-- 2021-02-16T11:14:25.656Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Hardwarefehler', IsTranslated='Y', Name='Hardwarefehler',Updated=TO_TIMESTAMP('2021-02-16 13:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542278
;

-- 2021-02-16T11:15:19.129Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Softwarefehler', IsTranslated='Y', Name='Softwarefehler',Updated=TO_TIMESTAMP('2021-02-16 13:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542277
;

-- 2021-02-16T11:15:28.309Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Softwarefehler', IsTranslated='Y', Name='Softwarefehler',Updated=TO_TIMESTAMP('2021-02-16 13:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542277
;

-- 2021-02-16T11:15:37.214Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542277
;

-- 2021-02-16T11:15:44.266Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Softwarefehler', IsTranslated='Y', Name='Softwarefehler',Updated=TO_TIMESTAMP('2021-02-16 13:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542277
;


-- 2021-02-16T10:57:54.513Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578762,'nl_NL') 
;

-- 2021-02-16T10:59:09.266Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572814,578762,0,10,53027,'ProblemCode',TO_TIMESTAMP('2021-02-16 11:59:09','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Problembereichscode',0,0,TO_TIMESTAMP('2021-02-16 11:59:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-16T10:59:09.270Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572814 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-16T10:59:09.274Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578762) 
;

-- 2021-02-16T11:00:18.395Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541259,Updated=TO_TIMESTAMP('2021-02-16 12:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572814
;

-- 2021-02-16T11:00:22.263Z
-- URL zum Konzept
INSERT INTO t_alter_column values('pp_order','ProblemCode','VARCHAR(2)',null,null)
;

