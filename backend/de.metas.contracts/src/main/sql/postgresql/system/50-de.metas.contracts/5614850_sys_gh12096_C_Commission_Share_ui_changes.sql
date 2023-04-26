-- 2021-11-22T11:28:53.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542971,540271,TO_TIMESTAMP('2021-11-22 13:28:52','YYYY-MM-DD HH24:MI:SS'),100,'Other busisness partners pay a revenue-based fee.','de.metas.contracts','Y','License fee',TO_TIMESTAMP('2021-11-22 13:28:52','YYYY-MM-DD HH24:MI:SS'),100,'LicenseFee','LicenseFee')
;

-- 2021-11-22T11:28:53.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542971 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-11-22T11:29:18.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Externe Geschäftspartner zahlen eine umsatzbasierte Gebühr.', Name='Lizenzgebühren',Updated=TO_TIMESTAMP('2021-11-22 13:29:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542971
;

-- 2021-11-22T11:29:25.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Externe Geschäftspartner zahlen eine umsatzbasierte Gebühr.', Name='Lizenzgebühren',Updated=TO_TIMESTAMP('2021-11-22 13:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542971
;

-- 2021-11-22T11:29:34.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Externe Geschäftspartner zahlen eine umsatzbasierte Gebühr.', Name='Lizenzgebühren',Updated=TO_TIMESTAMP('2021-11-22 13:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542971
;

-- 2021-11-22T11:31:37.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578582,580267,0,30,540311,'C_LicenseFeeSettings_ID',TO_TIMESTAMP('2021-11-22 13:31:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@Type_Conditions/''''@=''LicenseFee''',0,'C_LicenseFeeSettings',0,0,TO_TIMESTAMP('2021-11-22 13:31:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-22T11:31:37.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578582 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-22T11:31:37.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580267) 
;

-- 2021-11-22T11:32:08.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Lizenzgebühren', PrintName='Einstellungen für Lizenzgebühren',Updated=TO_TIMESTAMP('2021-11-22 13:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580267 AND AD_Language='de_CH'
;

-- 2021-11-22T11:32:08.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580267,'de_CH') 
;

-- 2021-11-22T11:32:14.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Lizenzgebühren', PrintName='Einstellungen für Lizenzgebühren',Updated=TO_TIMESTAMP('2021-11-22 13:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580267 AND AD_Language='de_DE'
;

-- 2021-11-22T11:32:14.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580267,'de_DE') 
;

-- 2021-11-22T11:32:14.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580267,'de_DE') 
;

-- 2021-11-22T11:32:14.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_LicenseFeeSettings_ID', Name='Einstellungen für Lizenzgebühren', Description=NULL, Help=NULL WHERE AD_Element_ID=580267
;

-- 2021-11-22T11:32:14.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_LicenseFeeSettings_ID', Name='Einstellungen für Lizenzgebühren', Description=NULL, Help=NULL, AD_Element_ID=580267 WHERE UPPER(ColumnName)='C_LICENSEFEESETTINGS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-22T11:32:14.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_LicenseFeeSettings_ID', Name='Einstellungen für Lizenzgebühren', Description=NULL, Help=NULL WHERE AD_Element_ID=580267 AND IsCentrallyMaintained='Y'
;

-- 2021-11-22T11:32:14.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einstellungen für Lizenzgebühren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580267) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580267)
;

-- 2021-11-22T11:32:14.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Einstellungen für Lizenzgebühren', Name='Einstellungen für Lizenzgebühren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580267)
;

-- 2021-11-22T11:32:14.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einstellungen für Lizenzgebühren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580267
;

-- 2021-11-22T11:32:14.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einstellungen für Lizenzgebühren', Description=NULL, Help=NULL WHERE AD_Element_ID = 580267
;

-- 2021-11-22T11:32:14.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einstellungen für Lizenzgebühren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580267
;

-- 2021-11-22T11:32:25.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='License fee settings', PrintName='License fee settings',Updated=TO_TIMESTAMP('2021-11-22 13:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580267 AND AD_Language='en_US'
;

-- 2021-11-22T11:32:25.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580267,'en_US') 
;

-- 2021-11-22T11:32:35.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Lizenzgebühren', PrintName='Einstellungen für Lizenzgebühren',Updated=TO_TIMESTAMP('2021-11-22 13:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580267 AND AD_Language='nl_NL'
;

-- 2021-11-22T11:32:35.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580267,'nl_NL') 
;

-- 2021-11-22T11:32:49.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN C_LicenseFeeSettings_ID NUMERIC(10)')
;

-- 2021-11-22T11:32:49.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Flatrate_Conditions ADD CONSTRAINT CLicenseFeeSettings_CFlatrateConditions FOREIGN KEY (C_LicenseFeeSettings_ID) REFERENCES public.C_LicenseFeeSettings DEFERRABLE INITIALLY DEFERRED
;

-- 2021-11-22T11:33:22.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-11-22 13:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=576040
;

-- 2021-11-22T11:35:38.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:35:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545773
;

-- 2021-11-22T11:35:50.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;

-- 2021-11-22T11:36:01.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','OnFlatrateTermExtend','VARCHAR(2)',null,'Ca')
;

-- 2021-11-22T11:36:01.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET OnFlatrateTermExtend='Ca' WHERE OnFlatrateTermExtend IS NULL
;

-- 2021-11-22T11:36:06.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','InvoiceRule','CHAR(1)',null,'I')
;

-- 2021-11-22T11:36:06.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Flatrate_Conditions SET InvoiceRule='I' WHERE InvoiceRule IS NULL
;

-- 2021-11-22T11:36:16.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','C_LicenseFeeSettings_ID','NUMERIC(10)',null,null)
;

-- 2021-11-22T11:36:50.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2021-11-22T11:37:06.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578582,669539,0,540331,TO_TIMESTAMP('2021-11-22 13:37:06','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Einstellungen für Lizenzgebühren',TO_TIMESTAMP('2021-11-22 13:37:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-22T11:37:06.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=669539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-11-22T11:37:06.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580267) 
;

-- 2021-11-22T11:37:06.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669539
;

-- 2021-11-22T11:37:06.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(669539)
;

-- 2021-11-22T11:37:38.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''''@=''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669539
;

-- 2021-11-22T11:38:26.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551476
;

-- 2021-11-22T11:38:30.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission''  & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563597
;

-- 2021-11-22T11:38:45.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548120
;

-- 2021-11-22T11:38:50.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547848
;

-- 2021-11-22T11:38:55.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551009
;

-- 2021-11-22T11:39:13.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee''',Updated=TO_TIMESTAMP('2021-11-22 13:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563597
;

-- 2021-11-22T11:41:24.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-11-22 13:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669539
;

-- 2021-11-22T11:41:56.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,669539,0,540331,595433,540760,'F',TO_TIMESTAMP('2021-11-22 13:41:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Einstellungen für Lizenzgebühren',80,0,0,TO_TIMESTAMP('2021-11-22 13:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-22T12:22:46.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-11-22 14:22:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578564
;

-- 2021-11-22T12:23:04.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2021-11-22 14:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578565
;

-- 2021-11-22T12:23:09.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=0,Updated=TO_TIMESTAMP('2021-11-22 14:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578565
;

-- 2021-11-22T12:23:11.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettings','Name','VARCHAR(255)',null,null)
;

-- 2021-11-22T12:31:42.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578585,580268,0,30,541406,'C_LicenseFeeSettingsLine_ID',TO_TIMESTAMP('2021-11-22 14:31:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'C_LicenseFeeSettingsLine',0,0,TO_TIMESTAMP('2021-11-22 14:31:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-22T12:31:42.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578585 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-22T12:31:42.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580268) 
;

-- 2021-11-22T12:31:51.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2021-11-22 14:31:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578585
;

-- 2021-11-22T12:32:00.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-11-22 14:32:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578585
;

-- 2021-11-22T12:32:14.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Share','ALTER TABLE public.C_Commission_Share ADD COLUMN C_LicenseFeeSettingsLine_ID NUMERIC(10)')
;


-- 2021-11-22T12:32:14.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Commission_Share ADD CONSTRAINT CLicenseFeeSettingsLine_CCommissionShare FOREIGN KEY (C_LicenseFeeSettingsLine_ID) REFERENCES public.C_LicenseFeeSettingsLine DEFERRABLE INITIALLY DEFERRED
;

-- 2021-11-22T12:33:30.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_share','C_LicenseFeeSettingsLine_ID','NUMERIC(10)',null,null)
;

-- 2021-11-22T12:34:52.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578585,669541,0,541943,TO_TIMESTAMP('2021-11-22 14:34:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.contracts.commission','Y','N','N','N','N','N','N','N','C_LicenseFeeSettingsLine',TO_TIMESTAMP('2021-11-22 14:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-22T12:34:52.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=669541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-11-22T12:34:52.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580268) 
;

-- 2021-11-22T12:34:52.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669541
;

-- 2021-11-22T12:34:52.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(669541)
;

-- 2021-11-22T12:35:39.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_LicenseFeeSettingsLine_ID/0@!0',Updated=TO_TIMESTAMP('2021-11-22 14:35:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669541
;

-- 2021-11-22T12:35:45.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2021-11-22 14:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669541
;

