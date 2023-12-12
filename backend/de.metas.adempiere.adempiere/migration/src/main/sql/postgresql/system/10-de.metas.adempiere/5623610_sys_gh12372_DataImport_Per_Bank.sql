-- 2022-01-27T18:58:25.810Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579175,543913,0,30,296,'C_DataImport_ID',TO_TIMESTAMP('2022-01-27 20:58:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Daten Import',0,0,TO_TIMESTAMP('2022-01-27 20:58:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-27T18:58:25.848Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579175 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-27T18:58:25.943Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543913) 
;

-- 2022-01-27T18:58:33.860Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Bank','ALTER TABLE public.C_Bank ADD COLUMN C_DataImport_ID NUMERIC(10)')
;

-- 2022-01-27T18:58:33.905Z
-- URL zum Konzept
ALTER TABLE C_Bank ADD CONSTRAINT CDataImport_CBank FOREIGN KEY (C_DataImport_ID) REFERENCES public.C_DataImport DEFERRABLE INITIALLY DEFERRED
;

-- 2022-01-27T19:04:58.574Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580530,0,'DataImport_ConfigType',TO_TIMESTAMP('2022-01-27 21:04:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ConfigType','ConfigType',TO_TIMESTAMP('2022-01-27 21:04:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T19:04:58.606Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580530 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-27T19:15:03.522Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541535,TO_TIMESTAMP('2022-01-27 21:15:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DataImport_ConfigType',TO_TIMESTAMP('2022-01-27 21:15:03','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-01-27T19:15:03.576Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541535 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-01-27T19:15:16.736Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541535,543118,TO_TIMESTAMP('2022-01-27 21:15:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Standard',TO_TIMESTAMP('2022-01-27 21:15:16','YYYY-MM-DD HH24:MI:SS'),100,'Standard','Standard')
;

-- 2022-01-27T19:15:16.764Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543118 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-01-27T19:26:04.064Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541535,543119,TO_TIMESTAMP('2022-01-27 21:26:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bank Statement Import',TO_TIMESTAMP('2022-01-27 21:26:03','YYYY-MM-DD HH24:MI:SS'),100,'Bank Statement Import','Bank Statement Import')
;

-- 2022-01-27T19:26:04.097Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543119 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-01-27T19:26:10.331Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Import - Bankauszug',Updated=TO_TIMESTAMP('2022-01-27 21:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543119
;

-- 2022-01-27T19:26:14.049Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Import - Bankauszug',Updated=TO_TIMESTAMP('2022-01-27 21:26:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543119
;

-- 2022-01-27T20:39:42.711Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579176,580530,0,17,541535,540950,'DataImport_ConfigType',TO_TIMESTAMP('2022-01-27 22:39:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ConfigType',0,0,TO_TIMESTAMP('2022-01-27 22:39:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-27T20:39:42.744Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579176 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-27T20:39:42.802Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580530) 
;

-- 2022-01-27T20:39:50.035Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_DataImport','ALTER TABLE public.C_DataImport ADD COLUMN DataImport_ConfigType VARCHAR(250)')
;






-- 2022-01-30T15:14:34.118Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579176,677980,0,541050,TO_TIMESTAMP('2022-01-30 17:14:33','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','ConfigType',TO_TIMESTAMP('2022-01-30 17:14:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-30T15:14:34.149Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-30T15:14:34.203Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580530) 
;

-- 2022-01-30T15:14:34.243Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677980
;

-- 2022-01-30T15:14:34.277Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677980)
;

-- 2022-01-30T15:17:09.260Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2022-01-30 17:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551182
;

-- 2022-01-30T15:17:38.634Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677980,0,541050,541501,600105,'F',TO_TIMESTAMP('2022-01-30 17:17:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ConfigType',40,0,0,TO_TIMESTAMP('2022-01-30 17:17:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-30T15:19:15.945Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Daten Import Typ', PrintName='Daten Import Typ',Updated=TO_TIMESTAMP('2022-01-30 17:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580530 AND AD_Language='de_CH'
;

-- 2022-01-30T15:19:15.980Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580530,'de_CH') 
;

-- 2022-01-30T15:19:41.576Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Daten Import Typ', PrintName='Daten Import Typ',Updated=TO_TIMESTAMP('2022-01-30 17:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580530 AND AD_Language='de_DE'
;

-- 2022-01-30T15:19:41.607Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580530,'de_DE') 
;

-- 2022-01-30T15:19:41.693Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580530,'de_DE') 
;

-- 2022-01-30T15:19:41.727Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DataImport_ConfigType', Name='Daten Import Typ', Description=NULL, Help=NULL WHERE AD_Element_ID=580530
;

-- 2022-01-30T15:19:41.760Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DataImport_ConfigType', Name='Daten Import Typ', Description=NULL, Help=NULL, AD_Element_ID=580530 WHERE UPPER(ColumnName)='DATAIMPORT_CONFIGTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-30T15:19:41.793Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DataImport_ConfigType', Name='Daten Import Typ', Description=NULL, Help=NULL WHERE AD_Element_ID=580530 AND IsCentrallyMaintained='Y'
;

-- 2022-01-30T15:19:41.825Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Daten Import Typ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580530) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580530)
;

-- 2022-01-30T15:19:41.866Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Daten Import Typ', Name='Daten Import Typ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580530)
;

-- 2022-01-30T15:19:41.897Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Daten Import Typ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580530
;

-- 2022-01-30T15:19:41.926Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Daten Import Typ', Description=NULL, Help=NULL WHERE AD_Element_ID = 580530
;

-- 2022-01-30T15:19:41.957Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Daten Import Typ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580530
;

-- 2022-01-30T15:19:54.572Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Data Import Type', PrintName='Data Import Type',Updated=TO_TIMESTAMP('2022-01-30 17:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580530 AND AD_Language='en_US'
;

-- 2022-01-30T15:19:54.602Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580530,'en_US') 
;

-- 2022-01-30T15:20:22.036Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-01-30 17:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600105
;

-- 2022-01-30T15:20:34.993Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Data Import Type',Updated=TO_TIMESTAMP('2022-01-30 17:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600105
;

-- 2022-01-30T15:22:56.325Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Standard',Updated=TO_TIMESTAMP('2022-01-30 17:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579176
;

-- 2022-01-30T15:23:01.662Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_dataimport','DataImport_ConfigType','VARCHAR(250)',null,'Standard')
;

-- 2022-01-30T15:26:34.696Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-01-30 17:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600105
;

-- 2022-01-30T15:26:34.819Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-01-30 17:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551182
;

-- 2022-01-30T15:26:34.941Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-01-30 17:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551181
;

-- 2022-01-30T15:26:35.069Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-01-30 17:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551184
;

-- 2022-01-30T15:28:05.039Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-01-30 17:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579176
;

-- 2022-01-30T15:28:10.760Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_dataimport','DataImport_ConfigType','VARCHAR(250)',null,'Standard')
;

-- -- 2022-01-30T15:28:10.799Z
-- -- URL zum Konzept
-- UPDATE C_DataImport SET DataImport_ConfigType='Standard' WHERE DataImport_ConfigType IS NULL
-- ;



-- 2022-01-30T15:30:11.539Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='BSI',Updated=TO_TIMESTAMP('2022-01-30 17:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543119
;

-- 2022-01-30T15:30:19.174Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='S',Updated=TO_TIMESTAMP('2022-01-30 17:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543118
;

-- 2022-01-30T15:30:24.504Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='S',Updated=TO_TIMESTAMP('2022-01-30 17:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579176
;

-- 2022-01-30T15:30:32.092Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_dataimport','DataImport_ConfigType','VARCHAR(250)',null,'S')
;

-- 2022-01-30T15:30:32.130Z
-- URL zum Konzept
UPDATE C_DataImport SET DataImport_ConfigType='S' WHERE DataImport_ConfigType IS NULL
;


-- 2022-01-30T15:28:10.829Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_dataimport','DataImport_ConfigType',null,'NOT NULL',null)
;



UPDATE c_dataimport
SET DataImport_ConfigType = 'BSI'
WHERE ad_impFormat_ID IN (SELECT ad_impFormat_ID FROM AD_ImpFormat WHERE ad_table_id = get_table_id('I_BankStatement'))
;






-- 2022-01-30T15:41:22.525Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540570,'AD_ImpFormat.AD_Table_ID = get_table_id(''I_BankStatement'')',TO_TIMESTAMP('2022-01-30 17:41:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_ImpFormat_ImportBankStatement','S',TO_TIMESTAMP('2022-01-30 17:41:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-30T15:45:55.768Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(AD_ImpFormat.AD_Table_ID = get_table_id(''I_BankStatement'')) OR @DataImport_ConfigType/S@ != ''BSI''',Updated=TO_TIMESTAMP('2022-01-30 17:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540570
;

-- 2022-01-30T15:46:01.960Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(AD_ImpFormat.AD_Table_ID = get_table_id(''I_BankStatement'')) OR @DataImport_ConfigType@ != ''BSI''',Updated=TO_TIMESTAMP('2022-01-30 17:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540570
;

-- 2022-01-30T15:46:12.411Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(AD_ImpFormat.AD_Table_ID = get_table_id(''I_BankStatement'')) OR (@DataImport_ConfigType@ != ''BSI'')',Updated=TO_TIMESTAMP('2022-01-30 17:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540570
;

-- 2022-01-30T15:46:32.514Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540570,Updated=TO_TIMESTAMP('2022-01-30 17:46:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559482
;

-- 2022-01-30T15:46:38.326Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_dataimport','AD_ImpFormat_ID','NUMERIC(10)',null,null)
;

-- 2022-01-30T15:50:23.330Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(AD_ImpFormat.AD_Table_ID = get_table_id(''I_BankStatement'')) OR (@DataImport_ConfigType@ not ilike ''BSI'')',Updated=TO_TIMESTAMP('2022-01-30 17:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540570
;

-- 2022-01-30T15:51:25.498Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(AD_ImpFormat.AD_Table_ID = get_table_id(''I_BankStatement'')) OR (''@DataImport_ConfigType@'' not ilike ''BSI'')',Updated=TO_TIMESTAMP('2022-01-30 17:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540570
;











-- 2022-01-30T15:54:04.214Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540571,'C_DataImport.DataImport_ConfigType ilike ''BSI''',TO_TIMESTAMP('2022-01-30 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_DataImport_BankStatement','C',TO_TIMESTAMP('2022-01-30 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-30T15:54:19.328Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540571,Updated=TO_TIMESTAMP('2022-01-30 17:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579175
;








-- 2022-01-30T15:56:02.462Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579175,677981,0,227,TO_TIMESTAMP('2022-01-30 17:56:02','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2022-01-30 17:56:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-30T15:56:02.492Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-30T15:56:02.523Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2022-01-30T15:56:02.557Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677981
;

-- 2022-01-30T15:56:02.588Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677981)
;

-- 2022-01-30T16:00:12.156Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@SQL= SELECT C_DataImport_ID from C_DataImport WHERE InternalName ilike ''Bank_Statement_Import''',Updated=TO_TIMESTAMP('2022-01-30 18:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579175
;

-- 2022-01-30T16:00:26.842Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bank','C_DataImport_ID','NUMERIC(10)',null,null)
;

-- 2022-01-30T16:05:25.757Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579175,677982,0,540805,TO_TIMESTAMP('2022-01-30 18:05:25','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2022-01-30 18:05:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-30T16:05:25.789Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-30T16:05:25.822Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2022-01-30T16:05:25.859Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677982
;

-- 2022-01-30T16:05:25.889Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677982)
;

-- 2022-01-30T16:05:47.217Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677982,0,540805,540316,600106,'F',TO_TIMESTAMP('2022-01-30 18:05:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Daten Import',40,0,0,TO_TIMESTAMP('2022-01-30 18:05:46','YYYY-MM-DD HH24:MI:SS'),100)
;






-- 2022-01-30T16:08:06.636Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bank','C_DataImport_ID','NUMERIC(10)',null,null)
;

-- 2022-01-30T16:10:00.783Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Type='S',Updated=TO_TIMESTAMP('2022-01-30 18:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540571
;












UPDATE c_bank SET c_dataimport_id = 540009 WHERE c_dataimport_id IS NULL;













-- 2022-01-31T18:48:16.444Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579178,577746,0,12,600,'BankFeeAmt',TO_TIMESTAMP('2022-01-31 20:48:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Bankgebühren',0,0,TO_TIMESTAMP('2022-01-31 20:48:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-31T18:48:16.482Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579178 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-31T18:48:16.570Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577746) 
;

-- 2022-01-31T18:49:50.306Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-01-31 20:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579178
;

-- 2022-01-31T18:49:55.038Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('I_BankStatement','ALTER TABLE public.I_BankStatement ADD COLUMN BankFeeAmt NUMERIC DEFAULT 0 NOT NULL')
;












-- 2022-01-31T21:11:34.900Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568329,678362,0,507,TO_TIMESTAMP('2022-01-31 23:11:34','YYYY-MM-DD HH24:MI:SS'),100,'Cashbook/Bank account To',10,'D','Y','N','N','N','N','N','N','N','Cashbook/Bank account To',TO_TIMESTAMP('2022-01-31 23:11:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:34.946Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:34.978Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542687) 
;

-- 2022-01-31T21:11:35.025Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678362
;

-- 2022-01-31T21:11:35.054Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678362)
;

-- 2022-01-31T21:11:35.493Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568844,678363,0,507,TO_TIMESTAMP('2022-01-31 23:11:35','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2022-01-31 23:11:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:35.523Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:35.553Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2022-01-31T21:11:35.585Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678363
;

-- 2022-01-31T21:11:35.614Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678363)
;

-- 2022-01-31T21:11:36.087Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568845,678364,0,507,TO_TIMESTAMP('2022-01-31 23:11:35','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2022-01-31 23:11:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:36.118Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:36.150Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2022-01-31T21:11:36.181Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678364
;

-- 2022-01-31T21:11:36.210Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678364)
;

-- 2022-01-31T21:11:36.702Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568846,678365,0,507,TO_TIMESTAMP('2022-01-31 23:11:36','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2022-01-31 23:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:36.733Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:36.765Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114) 
;

-- 2022-01-31T21:11:36.806Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678365
;

-- 2022-01-31T21:11:36.838Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678365)
;

-- 2022-01-31T21:11:37.328Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568847,678366,0,507,TO_TIMESTAMP('2022-01-31 23:11:36','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2022-01-31 23:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:37.365Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:37.398Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115) 
;

-- 2022-01-31T21:11:37.436Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678366
;

-- 2022-01-31T21:11:37.466Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678366)
;

-- 2022-01-31T21:11:37.916Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568848,678367,0,507,TO_TIMESTAMP('2022-01-31 23:11:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2022-01-31 23:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:37.946Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:37.978Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116) 
;

-- 2022-01-31T21:11:38.013Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678367
;

-- 2022-01-31T21:11:38.045Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678367)
;

-- 2022-01-31T21:11:38.537Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572182,678368,0,507,TO_TIMESTAMP('2022-01-31 23:11:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','DebitOrCreditAmt',TO_TIMESTAMP('2022-01-31 23:11:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:38.571Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:38.605Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578556) 
;

-- 2022-01-31T21:11:38.641Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678368
;

-- 2022-01-31T21:11:38.672Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678368)
;

-- 2022-01-31T21:11:39.190Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572183,678369,0,507,TO_TIMESTAMP('2022-01-31 23:11:38','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','DebitOrCreditIndicator',TO_TIMESTAMP('2022-01-31 23:11:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:39.219Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:39.248Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578557) 
;

-- 2022-01-31T21:11:39.278Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678369
;

-- 2022-01-31T21:11:39.307Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678369)
;

-- 2022-01-31T21:11:39.813Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572184,678370,0,507,TO_TIMESTAMP('2022-01-31 23:11:39','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','AmtFormat',TO_TIMESTAMP('2022-01-31 23:11:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:39.843Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:39.875Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578558) 
;

-- 2022-01-31T21:11:39.903Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678370
;

-- 2022-01-31T21:11:39.933Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678370)
;

-- 2022-01-31T21:11:40.392Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572185,678371,0,507,TO_TIMESTAMP('2022-01-31 23:11:39','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','LineDescriptionExtra_1',TO_TIMESTAMP('2022-01-31 23:11:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:40.426Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:40.457Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578559) 
;

-- 2022-01-31T21:11:40.487Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678371
;

-- 2022-01-31T21:11:40.516Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678371)
;

-- 2022-01-31T21:11:40.968Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572186,678372,0,507,TO_TIMESTAMP('2022-01-31 23:11:40','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','LineDescriptionExtra_2',TO_TIMESTAMP('2022-01-31 23:11:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:41.001Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678372 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:41.034Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578560) 
;

-- 2022-01-31T21:11:41.064Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678372
;

-- 2022-01-31T21:11:41.095Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678372)
;

-- 2022-01-31T21:11:41.546Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572187,678373,0,507,TO_TIMESTAMP('2022-01-31 23:11:41','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','LineDescriptionExtra_3',TO_TIMESTAMP('2022-01-31 23:11:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:41.577Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:41.609Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578561) 
;

-- 2022-01-31T21:11:41.639Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678373
;

-- 2022-01-31T21:11:41.668Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678373)
;

-- 2022-01-31T21:11:42.160Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572188,678374,0,507,TO_TIMESTAMP('2022-01-31 23:11:41','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','LineDescriptionExtra_4',TO_TIMESTAMP('2022-01-31 23:11:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:42.190Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:42.222Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578562) 
;

-- 2022-01-31T21:11:42.254Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678374
;

-- 2022-01-31T21:11:42.283Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678374)
;

-- 2022-01-31T21:11:42.722Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579178,678375,0,507,TO_TIMESTAMP('2022-01-31 23:11:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Bankgebühren',TO_TIMESTAMP('2022-01-31 23:11:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-31T21:11:42.753Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678375 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-31T21:11:42.785Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577746) 
;

-- 2022-01-31T21:11:42.819Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678375
;

-- 2022-01-31T21:11:42.849Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(678375)
;

-- 2022-01-31T21:12:33.051Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,678375,0,507,542669,600357,'F',TO_TIMESTAMP('2022-01-31 23:12:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bankgebühren',600,0,0,TO_TIMESTAMP('2022-01-31 23:12:32','YYYY-MM-DD HH24:MI:SS'),100)
;








-- 2022-01-31T21:25:32.998Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2022-01-31 23:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=677981
;

-- 2022-01-31T21:27:25.729Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677981,0,227,540313,600358,'F',TO_TIMESTAMP('2022-01-31 23:27:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Daten Import',85,0,0,TO_TIMESTAMP('2022-01-31 23:27:25','YYYY-MM-DD HH24:MI:SS'),100)
;

