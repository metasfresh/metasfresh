-- 2019-03-20T11:29:54.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576251,0,'IFA_Manufacturer',TO_TIMESTAMP('2019-03-20 11:29:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Anbieter-Nr.','Anbieter-Nr.',TO_TIMESTAMP('2019-03-20 11:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:29:54.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576251 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T11:29:58.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-20 11:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576251 AND AD_Language='de_CH'
;

-- 2019-03-20T11:29:58.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576251,'de_CH') 
;

-- 2019-03-20T11:30:01.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-20 11:30:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576251 AND AD_Language='de_DE'
;

-- 2019-03-20T11:30:01.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576251,'de_DE') 
;

-- 2019-03-20T11:30:01.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576251,'de_DE') 
;

-- 2019-03-20T11:30:10.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Manufacturer', PrintName='Manufacturer',Updated=TO_TIMESTAMP('2019-03-20 11:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576251 AND AD_Language='en_US'
;

-- 2019-03-20T11:30:10.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576251,'en_US') 
;

-- 2019-03-20T11:30:14.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-20 11:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576251 AND AD_Language='nl_NL'
;

-- 2019-03-20T11:30:14.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576251,'nl_NL') 
;

-- 2019-03-20T11:31:02.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564554,576251,0,10,291,'IFA_Manufacturer',TO_TIMESTAMP('2019-03-20 11:31:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.pharma',5,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Anbieter-Nr.',0,0,TO_TIMESTAMP('2019-03-20 11:31:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T11:31:02.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564554 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T11:31:17.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IFA_Manufacturer VARCHAR(5)')
;

-- 2019-03-20T11:32:32.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,564554,577939,0,541012,0,TO_TIMESTAMP('2019-03-20 11:32:32','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.vertical.pharma',0,'Y','Y','Y','N','N','N','N','N','Anbieter-Nr.',360,350,0,1,1,TO_TIMESTAMP('2019-03-20 11:32:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:32:32.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T11:34:08.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577939,0,541012,557981,541431,'F',TO_TIMESTAMP('2019-03-20 11:34:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Anbieter-Nr.',15,0,0,TO_TIMESTAMP('2019-03-20 11:34:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:34:19.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576251,627409,577939,0,540409,TO_TIMESTAMP('2019-03-20 11:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 11:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:34:28.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576251,Updated=TO_TIMESTAMP('2019-03-20 11:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577939
;

-- 2019-03-20T11:34:29.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=627409
;

-- 2019-03-20T11:34:29.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576251,627410,577939,0,540409,TO_TIMESTAMP('2019-03-20 11:34:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 11:34:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:34:29.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576251) 
;

-- 2019-03-20T11:41:12.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-20 11:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557981
;

-- 2019-03-20T11:42:18.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541197,TO_TIMESTAMP('2019-03-20 11:42:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','N','Import Pharma BPartners','NP','L','I_Pharma_BPartner',TO_TIMESTAMP('2019-03-20 11:42:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:42:18.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541197 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-03-20T11:42:18.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554854,TO_TIMESTAMP('2019-03-20 11:42:18','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table I_Pharma_BPartner',1,'Y','N','Y','Y','I_Pharma_BPartner','N',1000000,TO_TIMESTAMP('2019-03-20 11:42:18','YYYY-MM-DD HH24:MI:SS'),100)
;


CREATE TABLE I_Pharma_BPartner 
(
	B00SSATZ varchar(1),
	B00ADRNR varchar(5),
	B00GDAT timestamp,
	B00LKZ varchar(1),
	B00SNAME varchar(22),
	B00NAME1 varchar(80),
	B00NAME2 varchar(40),
	B00NAME3 varchar(40),
	B00LAND varchar(3),
	B00PLZZU1 varchar(10),
	B00ORTZU varchar(40),
	B00STR varchar(40),
	B00HNRV varchar(4),
	B00HNRVZ varchar(4),
	B00HNRB varchar(40),
	B00HNRBZ varchar(4),
	B00PLZPF1 varchar(10),
	B00ORTPF varchar(40),
	B00PF1 varchar(10),
	B00PLZGK1 varchar(10),
	B00TEL1 varchar(24),
	B00TEL2 varchar(24),
	B00FAX1 varchar(24),
	B00FAX2 varchar(24),
	B00EMAIL varchar(60),
	B00EMAIL2 varchar(60),
	B00HOMEPAG varchar(60),
	B00HERSTER varchar(2),
	B00GHERLAU varchar(2),
	B00REGNR9 varchar(15)
);

-- 2019-03-20T12:03:19.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576254,0,'b00ssatz',TO_TIMESTAMP('2019-03-20 12:03:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00ssatz','b00ssatz',TO_TIMESTAMP('2019-03-20 12:03:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:19.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576254 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:19.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564557,576254,0,10,541197,'b00ssatz',TO_TIMESTAMP('2019-03-20 12:03:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',1,'Y','Y','N','N','N','N','N','N','N','N','Y','b00ssatz',TO_TIMESTAMP('2019-03-20 12:03:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:19.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564557 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:20.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576255,0,'b00adrnr',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00adrnr','b00adrnr',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:20.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576255 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:20.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564558,576255,0,10,541197,'b00adrnr',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',5,'Y','Y','N','N','N','N','N','N','N','N','Y','b00adrnr',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:20.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564558 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:20.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576256,0,'b00gdat',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00gdat','b00gdat',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:20.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576256 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:20.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564559,576256,0,16,541197,'b00gdat',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',29,'Y','Y','N','N','N','N','N','N','N','N','Y','b00gdat',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:20.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564559 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:20.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576257,0,'b00lkz',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00lkz','b00lkz',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:20.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576257 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:20.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564560,576257,0,10,541197,'b00lkz',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',1,'Y','Y','N','N','N','N','N','N','N','N','Y','b00lkz',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:20.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564560 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:20.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576258,0,'b00sname',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00sname','b00sname',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:20.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576258 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:20.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564561,576258,0,10,541197,'b00sname',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',22,'Y','Y','N','N','N','N','N','N','N','N','Y','b00sname',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:20.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564561 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:20.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576259,0,'b00name1',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00name1','b00name1',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:20.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576259 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:20.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564562,576259,0,10,541197,'b00name1',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',80,'Y','Y','N','N','N','N','N','N','N','N','Y','b00name1',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:20.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:20.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576260,0,'b00name2',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00name2','b00name2',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:20.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576260 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:21.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564563,576260,0,10,541197,'b00name2',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',40,'Y','Y','N','N','N','N','N','N','N','N','Y','b00name2',TO_TIMESTAMP('2019-03-20 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:21.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:21.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576261,0,'b00name3',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00name3','b00name3',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:21.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576261 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:21.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564564,576261,0,10,541197,'b00name3',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',40,'Y','Y','N','N','N','N','N','N','N','N','Y','b00name3',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:21.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:21.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576262,0,'b00land',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00land','b00land',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:21.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576262 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:21.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564565,576262,0,10,541197,'b00land',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',3,'Y','Y','N','N','N','N','N','N','N','N','Y','b00land',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:21.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:21.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576263,0,'b00plzzu1',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00plzzu1','b00plzzu1',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:21.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576263 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:21.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564566,576263,0,10,541197,'b00plzzu1',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',10,'Y','Y','N','N','N','N','N','N','N','N','Y','b00plzzu1',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:21.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:21.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576264,0,'b00ortzu',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00ortzu','b00ortzu',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:21.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576264 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:21.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564567,576264,0,10,541197,'b00ortzu',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',40,'Y','Y','N','N','N','N','N','N','N','N','Y','b00ortzu',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:21.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:21.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576265,0,'b00str',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00str','b00str',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:21.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576265 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:21.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564568,576265,0,10,541197,'b00str',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',40,'Y','Y','N','N','N','N','N','N','N','N','Y','b00str',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:21.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564568 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:21.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576266,0,'b00hnrv',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00hnrv','b00hnrv',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:21.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576266 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:22.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564569,576266,0,10,541197,'b00hnrv',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',4,'Y','Y','N','N','N','N','N','N','N','N','Y','b00hnrv',TO_TIMESTAMP('2019-03-20 12:03:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:22.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:22.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576267,0,'b00hnrvz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00hnrvz','b00hnrvz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:22.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576267 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:22.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564570,576267,0,10,541197,'b00hnrvz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',4,'Y','Y','N','N','N','N','N','N','N','N','Y','b00hnrvz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:22.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:22.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576268,0,'b00hnrb',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00hnrb','b00hnrb',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:22.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576268 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:22.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564571,576268,0,10,541197,'b00hnrb',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',40,'Y','Y','N','N','N','N','N','N','N','N','Y','b00hnrb',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:22.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:22.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576269,0,'b00hnrbz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00hnrbz','b00hnrbz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:22.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576269 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:22.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564572,576269,0,10,541197,'b00hnrbz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',4,'Y','Y','N','N','N','N','N','N','N','N','Y','b00hnrbz',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:22.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:22.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576270,0,'b00plzpf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00plzpf1','b00plzpf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:22.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576270 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:22.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564573,576270,0,10,541197,'b00plzpf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',10,'Y','Y','N','N','N','N','N','N','N','N','Y','b00plzpf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:22.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564573 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:22.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576271,0,'b00ortpf',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00ortpf','b00ortpf',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:22.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576271 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:22.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564574,576271,0,10,541197,'b00ortpf',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',40,'Y','Y','N','N','N','N','N','N','N','N','Y','b00ortpf',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:22.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:22.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576272,0,'b00pf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00pf1','b00pf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:22.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576272 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564575,576272,0,10,541197,'b00pf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',10,'Y','Y','N','N','N','N','N','N','N','N','Y','b00pf1',TO_TIMESTAMP('2019-03-20 12:03:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564575 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:23.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576273,0,'b00plzgk1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00plzgk1','b00plzgk1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:23.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576273 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564576,576273,0,10,541197,'b00plzgk1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',10,'Y','Y','N','N','N','N','N','N','N','N','Y','b00plzgk1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564576 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:23.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576274,0,'b00tel1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00tel1','b00tel1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:23.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576274 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564577,576274,0,10,541197,'b00tel1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',24,'Y','Y','N','N','N','N','N','N','N','N','Y','b00tel1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564577 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:23.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576275,0,'b00tel2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00tel2','b00tel2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:23.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576275 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564578,576275,0,10,541197,'b00tel2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',24,'Y','Y','N','N','N','N','N','N','N','N','Y','b00tel2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564578 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:23.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576276,0,'b00fax1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00fax1','b00fax1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:23.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576276 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564579,576276,0,10,541197,'b00fax1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',24,'Y','Y','N','N','N','N','N','N','N','N','Y','b00fax1',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564579 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:23.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576277,0,'b00fax2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00fax2','b00fax2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:23.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576277 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564580,576277,0,10,541197,'b00fax2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',24,'Y','Y','N','N','N','N','N','N','N','N','Y','b00fax2',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564580 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:23.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576278,0,'b00email',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00email','b00email',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:23.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576278 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:23.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564581,576278,0,10,541197,'b00email',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',60,'Y','Y','N','N','N','N','N','N','N','N','Y','b00email',TO_TIMESTAMP('2019-03-20 12:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:23.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564581 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:24.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576279,0,'b00email2',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00email2','b00email2',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:24.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576279 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:24.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564582,576279,0,10,541197,'b00email2',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',60,'Y','Y','N','N','N','N','N','N','N','N','Y','b00email2',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:24.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564582 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:24.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576280,0,'b00homepag',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00homepag','b00homepag',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:24.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576280 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:24.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564583,576280,0,10,541197,'b00homepag',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',60,'Y','Y','N','N','N','N','N','N','N','N','Y','b00homepag',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:24.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564583 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:24.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576281,0,'b00herster',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00herster','b00herster',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:24.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576281 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:24.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564584,576281,0,10,541197,'b00herster',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',2,'Y','Y','N','N','N','N','N','N','N','N','Y','b00herster',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:24.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564584 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:24.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576282,0,'b00gherlau',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00gherlau','b00gherlau',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:24.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576282 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:24.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564585,576282,0,10,541197,'b00gherlau',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',2,'Y','Y','N','N','N','N','N','N','N','N','Y','b00gherlau',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:24.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564585 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:03:24.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576283,0,'b00regnr9',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','b00regnr9','b00regnr9',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:03:24.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576283 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:03:24.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,564586,576283,0,10,541197,'b00regnr9',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',15,'Y','Y','N','N','N','N','N','N','N','N','Y','b00regnr9',TO_TIMESTAMP('2019-03-20 12:03:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:03:24.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564586 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:44.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564587,102,0,19,541197,'AD_Client_ID',TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.vertical.pharma',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:44.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564587 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:44.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564588,113,0,19,541197,'AD_Org_ID',TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.vertical.pharma',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:44.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564588 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:44.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564589,245,0,16,541197,'Created',TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.vertical.pharma',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:44.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564589 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:44.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564590,246,0,18,110,541197,'CreatedBy',TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.vertical.pharma',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2019-03-20 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564590 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564591,912,0,10,541197,'I_ErrorMsg',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','de.metas.vertical.pharma',2000,'"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Import-Fehlermeldung',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564591 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564592,913,0,17,540745,541197,'I_IsImported',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Ist dieser Import verarbeitet worden?','de.metas.vertical.pharma',1,'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','Y','N','N','N','N','Y','N','N','N','N','Y','Importiert',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564592 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564593,348,0,20,541197,'IsActive',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.vertical.pharma',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564593 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564594,1047,0,20,541197,'Processed',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','de.metas.vertical.pharma',1,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','Y','N','N','N','N','Y','Verarbeitet',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564594 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564595,607,0,16,541197,'Updated',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.vertical.pharma',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564595 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564596,608,0,18,110,541197,'UpdatedBy',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.vertical.pharma',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564596 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:04:45.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576284,0,'I_Pharma_BPartner_ID',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Import Pharma BPartners','Import Pharma BPartners',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:04:45.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576284 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T12:04:45.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564597,576284,0,13,541197,'I_Pharma_BPartner_ID',TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','Import Pharma BPartners',0,TO_TIMESTAMP('2019-03-20 12:04:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:04:45.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564597 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:05:03.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564588
;

-- 2019-03-20T12:05:03.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564588
;

-- 2019-03-20T12:10:47.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540964,TO_TIMESTAMP('2019-03-20 12:10:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','N','Pharma_b00herster',TO_TIMESTAMP('2019-03-20 12:10:47','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-03-20T12:10:47.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540964 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-03-20T12:12:07.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541888,540964,TO_TIMESTAMP('2019-03-20 12:12:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','nein',TO_TIMESTAMP('2019-03-20 12:12:07','YYYY-MM-DD HH24:MI:SS'),100,'00','00')
;

-- 2019-03-20T12:12:07.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541888 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-03-20T12:12:21.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541889,540964,TO_TIMESTAMP('2019-03-20 12:12:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','ja',TO_TIMESTAMP('2019-03-20 12:12:21','YYYY-MM-DD HH24:MI:SS'),100,'01','01')
;

-- 2019-03-20T12:12:21.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541889 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-03-20T12:12:36.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541890,540964,TO_TIMESTAMP('2019-03-20 12:12:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','nicht erforderlich',TO_TIMESTAMP('2019-03-20 12:12:36','YYYY-MM-DD HH24:MI:SS'),100,'02','02')
;

-- 2019-03-20T12:12:36.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541890 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-03-20T12:12:52.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540964,Updated=TO_TIMESTAMP('2019-03-20 12:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564584
;

-- 2019-03-20T12:15:01.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Pharma_B00HERSTER_B00GHERLAU',Updated=TO_TIMESTAMP('2019-03-20 12:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540964
;

-- 2019-03-20T12:15:35.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540964,Updated=TO_TIMESTAMP('2019-03-20 12:15:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564585
;

-- 2019-03-20T12:15:40.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_bpartner','b00gherlau','VARCHAR(2)',null,null)
;

-- 2019-03-20T12:16:45.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2019-03-20T12:16:56.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2019-03-20T12:16:59.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN I_ErrorMsg VARCHAR(2000)')
;

-- 2019-03-20T12:17:03.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN I_IsImported CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- 2019-03-20T12:17:06.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN I_Pharma_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2019-03-20T12:17:06.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Pharma_BPartner ADD CONSTRAINT I_Pharma_BPartner_Key PRIMARY KEY (I_Pharma_BPartner_ID)
;

-- 2019-03-20T12:17:09.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2019-03-20T12:17:13.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- 2019-03-20T12:17:19.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2019-03-20T12:17:23.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- 2019-03-20T12:17:28.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2019-03-20T12:21:43.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564598,113,0,19,541197,'AD_Org_ID',TO_TIMESTAMP('2019-03-20 12:21:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.vertical.pharma',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Sektion',0,0,TO_TIMESTAMP('2019-03-20 12:21:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:21:43.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564598 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:21:43.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN AD_Org_ID NUMERIC(10)')
;

-- 2019-03-20T12:22:43.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564599,187,0,19,541197,'C_BPartner_ID',TO_TIMESTAMP('2019-03-20 12:22:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','de.metas.vertical.pharma',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartner',0,0,TO_TIMESTAMP('2019-03-20 12:22:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:22:43.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564599 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:22:46.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2019-03-20T12:22:46.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Pharma_BPartner ADD CONSTRAINT CBPartner_IPharmaBPartner FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2019-03-20T12:37:32.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576284,0,540610,TO_TIMESTAMP('2019-03-20 12:37:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','N','N','N','N','Y','Import Pharma BPartners','N',TO_TIMESTAMP('2019-03-20 12:37:31','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-03-20T12:37:32.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540610 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-03-20T12:37:32.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(576284) 
;

-- 2019-03-20T12:38:01.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564600,543913,0,19,541197,'C_DataImport_ID',TO_TIMESTAMP('2019-03-20 12:38:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.pharma',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Data import',0,0,TO_TIMESTAMP('2019-03-20 12:38:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:38:01.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564600 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:38:04.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN C_DataImport_ID NUMERIC(10)')
;

-- 2019-03-20T12:38:04.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Pharma_BPartner ADD CONSTRAINT CDataImport_IPharmaBPartner FOREIGN KEY (C_DataImport_ID) REFERENCES public.C_DataImport DEFERRABLE INITIALLY DEFERRED
;

-- 2019-03-20T12:38:47.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576284,0,541679,541197,540610,'Y',TO_TIMESTAMP('2019-03-20 12:38:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','N','N','I_Pharma_BPartner','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Import Pharma BPartners','N',10,0,TO_TIMESTAMP('2019-03-20 12:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:47.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541679 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-03-20T12:38:47.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576284) 
;

-- 2019-03-20T12:38:52.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564557,577942,0,541679,TO_TIMESTAMP('2019-03-20 12:38:51','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00ssatz',TO_TIMESTAMP('2019-03-20 12:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564558,577943,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,5,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00adrnr',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564559,577944,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,29,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00gdat',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564560,577945,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00lkz',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564561,577946,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,22,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00sname',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564562,577947,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,80,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00name1',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577947 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564563,577948,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00name2',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564564,577949,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00name3',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564565,577950,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,3,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00land',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564566,577951,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00plzzu1',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564567,577952,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00ortzu',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564568,577953,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00str',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564569,577954,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,4,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00hnrv',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564570,577955,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,4,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00hnrvz',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:52.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564571,577956,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00hnrb',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:52.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564572,577957,0,541679,TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100,4,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00hnrbz',TO_TIMESTAMP('2019-03-20 12:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564573,577958,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00plzpf1',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564574,577959,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00ortpf',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564575,577960,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00pf1',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564576,577961,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00plzgk1',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564577,577962,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,24,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00tel1',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564578,577963,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,24,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00tel2',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564579,577964,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,24,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00fax1',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564580,577965,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,24,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00fax2',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564581,577966,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,60,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00email',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564582,577967,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,60,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00email2',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564583,577968,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,60,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00homepag',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564584,577969,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00herster',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:53.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564585,577970,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00gherlau',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:53.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564586,577971,0,541679,TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100,15,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','b00regnr9',TO_TIMESTAMP('2019-03-20 12:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564587,577972,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.vertical.pharma','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564591,577973,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'de.metas.vertical.pharma','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564592,577974,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'de.metas.vertical.pharma','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','N','N','N','Importiert',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564593,577975,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.vertical.pharma','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564594,577976,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'de.metas.vertical.pharma','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564597,577977,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','Import Pharma BPartners',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564598,577978,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.pharma','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564599,577979,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.vertical.pharma','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:38:54.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564600,577980,0,541679,TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','N','Data import',TO_TIMESTAMP('2019-03-20 12:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:38:54.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:42:52.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577977
;

-- 2019-03-20T12:42:52.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577980
;

-- 2019-03-20T12:42:52.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577973
;

-- 2019-03-20T12:42:52.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577974
;

-- 2019-03-20T12:42:52.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577976
;

-- 2019-03-20T12:42:52.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577975
;

-- 2019-03-20T12:42:52.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577979
;

-- 2019-03-20T12:42:52.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577942
;

-- 2019-03-20T12:42:52.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577943
;

-- 2019-03-20T12:42:52.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577944
;

-- 2019-03-20T12:42:52.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577945
;

-- 2019-03-20T12:42:52.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577946
;

-- 2019-03-20T12:42:52.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577947
;

-- 2019-03-20T12:42:52.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577948
;

-- 2019-03-20T12:42:52.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577949
;

-- 2019-03-20T12:42:52.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577950
;

-- 2019-03-20T12:42:52.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577951
;

-- 2019-03-20T12:42:52.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577952
;

-- 2019-03-20T12:42:52.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577953
;

-- 2019-03-20T12:42:52.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577954
;

-- 2019-03-20T12:42:52.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577955
;

-- 2019-03-20T12:42:52.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577956
;

-- 2019-03-20T12:42:52.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577957
;

-- 2019-03-20T12:42:52.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577958
;

-- 2019-03-20T12:42:52.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577959
;

-- 2019-03-20T12:42:52.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577960
;

-- 2019-03-20T12:42:52.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577961
;

-- 2019-03-20T12:42:52.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577962
;

-- 2019-03-20T12:42:52.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577963
;

-- 2019-03-20T12:42:52.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577964
;

-- 2019-03-20T12:42:52.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577965
;

-- 2019-03-20T12:42:52.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577966
;

-- 2019-03-20T12:42:52.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577967
;

-- 2019-03-20T12:42:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577968
;

-- 2019-03-20T12:42:52.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577969
;

-- 2019-03-20T12:42:52.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577970
;

-- 2019-03-20T12:42:52.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2019-03-20 12:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577971
;

-- 2019-03-20T12:43:03.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577980
;

-- 2019-03-20T12:43:09.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577976
;

-- 2019-03-20T12:43:22.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577943
;

-- 2019-03-20T12:43:28.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577945
;

-- 2019-03-20T12:43:32.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577947
;

-- 2019-03-20T12:43:35.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577949
;

-- 2019-03-20T12:43:37.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577951
;

-- 2019-03-20T12:43:40.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577953
;

-- 2019-03-20T12:43:46.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577955
;

-- 2019-03-20T12:43:49.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577957
;

-- 2019-03-20T12:43:53.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577959
;

-- 2019-03-20T12:43:56.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577961
;

-- 2019-03-20T12:43:58.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577963
;

-- 2019-03-20T12:44:01.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577965
;

-- 2019-03-20T12:44:05.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577967
;

-- 2019-03-20T12:44:09.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577969
;

-- 2019-03-20T12:44:12.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577971
;

-- 2019-03-20T12:44:48.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,576284,541224,0,540610,TO_TIMESTAMP('2019-03-20 12:44:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Import Pharma BPartners','Y','N','N','N','N','Import Pharma BPartners',TO_TIMESTAMP('2019-03-20 12:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:44:48.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541224 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-20T12:44:48.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541224, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541224)
;

-- 2019-03-20T12:44:48.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576284) 
;

-- 2019-03-20T12:44:55.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:55.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-20T12:44:57.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:08.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:13.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541055 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541084 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541022 AND AD_Tree_ID=10
;

-- 2019-03-20T12:45:30.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541076 AND AD_Tree_ID=10
;

-- 2019-03-20T12:53:40.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564601,189,0,19,541197,'C_BPartner_Location_ID',TO_TIMESTAMP('2019-03-20 12:53:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.vertical.pharma',10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Standort',0,0,TO_TIMESTAMP('2019-03-20 12:53:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T12:53:40.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564601 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T12:53:42.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2019-03-20T12:53:42.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Pharma_BPartner ADD CONSTRAINT CBPartnerLocation_IPharmaBPartner FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2019-03-20T12:53:49.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564601,577981,0,541679,TO_TIMESTAMP('2019-03-20 12:53:49','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.vertical.pharma','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2019-03-20 12:53:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T12:53:49.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T12:54:11.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577981
;

-- 2019-03-20T12:54:11.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577942
;

-- 2019-03-20T12:54:11.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577943
;

-- 2019-03-20T12:54:11.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577944
;

-- 2019-03-20T12:54:11.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577945
;

-- 2019-03-20T12:54:11.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577946
;

-- 2019-03-20T12:54:11.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577947
;

-- 2019-03-20T12:54:11.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577948
;

-- 2019-03-20T12:54:11.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577949
;

-- 2019-03-20T12:54:11.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577950
;

-- 2019-03-20T12:54:11.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577951
;

-- 2019-03-20T12:54:11.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577952
;

-- 2019-03-20T12:54:11.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577953
;

-- 2019-03-20T12:54:11.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577954
;

-- 2019-03-20T12:54:11.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577955
;

-- 2019-03-20T12:54:11.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577956
;

-- 2019-03-20T12:54:11.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577957
;

-- 2019-03-20T12:54:11.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577958
;

-- 2019-03-20T12:54:11.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577959
;

-- 2019-03-20T12:54:11.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577960
;

-- 2019-03-20T12:54:11.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577961
;

-- 2019-03-20T12:54:11.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577962
;

-- 2019-03-20T12:54:11.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577963
;

-- 2019-03-20T12:54:11.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577964
;

-- 2019-03-20T12:54:11.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577965
;

-- 2019-03-20T12:54:11.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577966
;

-- 2019-03-20T12:54:11.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577967
;

-- 2019-03-20T12:54:11.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577968
;

-- 2019-03-20T12:54:11.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577969
;

-- 2019-03-20T12:54:11.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577970
;

-- 2019-03-20T12:54:11.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2019-03-20 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577971
;

-- 2019-03-20T12:54:20.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 12:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577981
;




-- 2019-03-20T16:31:13.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564611,192,0,19,541197,'C_Country_ID',TO_TIMESTAMP('2019-03-20 16:31:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Land','de.metas.vertical.pharma',10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Land',0,0,TO_TIMESTAMP('2019-03-20 16:31:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T16:31:13.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564611 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T16:31:14.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Pharma_BPartner','ALTER TABLE public.I_Pharma_BPartner ADD COLUMN C_Country_ID NUMERIC(10)')
;

-- 2019-03-20T16:31:14.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Pharma_BPartner ADD CONSTRAINT CCountry_IPharmaBPartner FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- 2019-03-20T16:32:18.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540610,Updated=TO_TIMESTAMP('2019-03-20 16:32:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541197
;

-- 2019-03-20T16:32:26.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564611,578033,0,541679,TO_TIMESTAMP('2019-03-20 16:32:26','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'de.metas.vertical.pharma','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2019-03-20 16:32:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:32:26.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:32:40.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578033
;

-- 2019-03-20T16:32:40.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577951
;

-- 2019-03-20T16:32:40.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577952
;

-- 2019-03-20T16:32:40.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577953
;

-- 2019-03-20T16:32:40.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577954
;

-- 2019-03-20T16:32:40.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577955
;

-- 2019-03-20T16:32:40.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577956
;

-- 2019-03-20T16:32:40.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577957
;

-- 2019-03-20T16:32:40.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577958
;

-- 2019-03-20T16:32:40.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577959
;

-- 2019-03-20T16:32:40.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577960
;

-- 2019-03-20T16:32:40.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577961
;

-- 2019-03-20T16:32:40.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577962
;

-- 2019-03-20T16:32:40.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577963
;

-- 2019-03-20T16:32:40.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577964
;

-- 2019-03-20T16:32:40.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577965
;

-- 2019-03-20T16:32:40.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577966
;

-- 2019-03-20T16:32:40.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577967
;

-- 2019-03-20T16:32:40.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577968
;

-- 2019-03-20T16:32:40.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577969
;

-- 2019-03-20T16:32:40.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577970
;

-- 2019-03-20T16:32:40.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2019-03-20 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577971
;

-- 2019-03-20T16:33:23.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578033
;

-- 2019-03-20T16:33:28.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577951
;

-- 2019-03-20T16:33:30.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577952
;

-- 2019-03-20T16:33:32.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577953
;

-- 2019-03-20T16:33:33.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577954
;

-- 2019-03-20T16:33:36.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577955
;

-- 2019-03-20T16:33:37.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577956
;

-- 2019-03-20T16:33:39.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577957
;

-- 2019-03-20T16:33:41.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577958
;

-- 2019-03-20T16:33:42.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577959
;

-- 2019-03-20T16:33:45.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577960
;

-- 2019-03-20T16:33:46.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577961
;

-- 2019-03-20T16:33:48.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577962
;

-- 2019-03-20T16:33:50.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577963
;

-- 2019-03-20T16:33:53.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577964
;

-- 2019-03-20T16:33:56.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577965
;

-- 2019-03-20T16:33:58.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577966
;

-- 2019-03-20T16:34:00.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577967
;

-- 2019-03-20T16:34:03.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577968
;

-- 2019-03-20T16:34:05.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577969
;

-- 2019-03-20T16:34:09.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2019-03-20 16:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577970
;

-- 2019-03-20T16:34:11.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-03-20 16:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577971
;






-- 2019-03-20T16:56:44.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576287,0,'Fax2',TO_TIMESTAMP('2019-03-20 16:56:44','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','de.metas.vertical.pharma','The Fax identifies a facsimile number for this Business Partner or  Location','Y','Fax2','Fax2',TO_TIMESTAMP('2019-03-20 16:56:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:56:44.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576287 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T16:57:01.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,564612,576287,0,10,293,'Fax2',TO_TIMESTAMP('2019-03-20 16:57:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Faxnummer','de.metas.vertical.pharma',40,'The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N','Fax2',0,TO_TIMESTAMP('2019-03-20 16:57:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T16:57:01.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564612 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-20T16:57:14.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-20 16:57:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576287
;

-- 2019-03-20T16:57:19.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-20 16:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564612
;

-- 2019-03-20T16:57:26.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN Fax2 VARCHAR(40)')
;

-- 2019-03-20T17:15:12.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,562305,0,541016,558009,541435,'F',TO_TIMESTAMP('2019-03-20 17:15:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Telefon',131,0,0,TO_TIMESTAMP('2019-03-20 17:15:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:15:31.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,562306,0,541016,558010,541435,'F',TO_TIMESTAMP('2019-03-20 17:15:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mobil',132,0,0,TO_TIMESTAMP('2019-03-20 17:15:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:15:41.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,562307,0,541016,558011,541435,'F',TO_TIMESTAMP('2019-03-20 17:15:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fax',133,0,0,TO_TIMESTAMP('2019-03-20 17:15:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:16:28.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,564612,578034,0,541016,0,TO_TIMESTAMP('2019-03-20 17:16:28','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer',0,'','U','The Fax identifies a facsimile number for this Business Partner or  Location',0,'Y','Y','Y','N','N','N','N','N','Alternative Fax',170,150,0,1,1,TO_TIMESTAMP('2019-03-20 17:16:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:16:28.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T17:16:43.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.vertical.pharma',Updated=TO_TIMESTAMP('2019-03-20 17:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578034
;

-- 2019-03-20T17:16:50.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576287,628232,578034,0,540409,TO_TIMESTAMP('2019-03-20 17:16:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 17:16:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:16:51.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576287, Name='Fax2',Updated=TO_TIMESTAMP('2019-03-20 17:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578034
;

-- 2019-03-20T17:16:51.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=628232
;

-- 2019-03-20T17:16:52.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576287,628233,578034,0,540409,TO_TIMESTAMP('2019-03-20 17:16:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 17:16:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:16:52.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576287) 
;

-- 2019-03-20T17:17:01.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Alternative Fax', PrintName='Alternative Fax',Updated=TO_TIMESTAMP('2019-03-20 17:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576287
;

-- 2019-03-20T17:17:01.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Fax2', Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE AD_Element_ID=576287
;

-- 2019-03-20T17:17:01.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Fax2', Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location', AD_Element_ID=576287 WHERE UPPER(ColumnName)='FAX2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T17:17:01.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Fax2', Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE AD_Element_ID=576287 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T17:17:01.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576287) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576287)
;

-- 2019-03-20T17:17:01.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Alternative Fax', Name='Alternative Fax' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576287)
;

-- 2019-03-20T17:17:01.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location', CommitWarning = NULL WHERE AD_Element_ID = 576287
;

-- 2019-03-20T17:17:01.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE AD_Element_ID = 576287
;

-- 2019-03-20T17:17:01.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Alternative Fax', Description = 'Faxnummer', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576287
;

-- 2019-03-20T17:17:07.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alternative Fax', PrintName='Alternative Fax',Updated=TO_TIMESTAMP('2019-03-20 17:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576287 AND AD_Language='de_CH'
;

-- 2019-03-20T17:17:07.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576287,'de_CH') 
;

-- 2019-03-20T17:17:12.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alternative Fax', PrintName='Alternative Fax',Updated=TO_TIMESTAMP('2019-03-20 17:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576287 AND AD_Language='de_DE'
;

-- 2019-03-20T17:17:12.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576287,'de_DE') 
;

-- 2019-03-20T17:17:12.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576287,'de_DE') 
;

-- 2019-03-20T17:17:12.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Fax2', Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE AD_Element_ID=576287
;

-- 2019-03-20T17:17:12.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Fax2', Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location', AD_Element_ID=576287 WHERE UPPER(ColumnName)='FAX2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T17:17:12.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Fax2', Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE AD_Element_ID=576287 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T17:17:12.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576287) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576287)
;

-- 2019-03-20T17:17:12.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Alternative Fax', Name='Alternative Fax' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576287)
;

-- 2019-03-20T17:17:12.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location', CommitWarning = NULL WHERE AD_Element_ID = 576287
;

-- 2019-03-20T17:17:12.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Alternative Fax', Description='Faxnummer', Help='The Fax identifies a facsimile number for this Business Partner or  Location' WHERE AD_Element_ID = 576287
;

-- 2019-03-20T17:17:12.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Alternative Fax', Description = 'Faxnummer', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576287
;

-- 2019-03-20T17:17:30.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578034,0,541016,558012,541435,'F',TO_TIMESTAMP('2019-03-20 17:17:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Alternative Fax',134,0,0,TO_TIMESTAMP('2019-03-20 17:17:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T17:17:43.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-20 17:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558012
;

-- 2019-03-20T17:17:45.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-20 17:17:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558011
;

-- 2019-03-20T17:17:47.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-20 17:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558010
;

-- 2019-03-20T17:17:49.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-20 17:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558009
;







-- 2019-03-27T18:22:05.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564643,881,0,10,293,'EMail',TO_TIMESTAMP('2019-03-27 18:22:05','YYYY-MM-DD HH24:MI:SS'),100,'N','EMail-Adresse','D',200,'The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','eMail',0,0,TO_TIMESTAMP('2019-03-27 18:22:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-27T18:22:05.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564643 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-27T18:22:12.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN EMail VARCHAR(200)')
;

-- 2019-03-27T18:23:26.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576305,0,'EMail2',TO_TIMESTAMP('2019-03-27 18:23:26','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','D','','Y','Alternative eMail','Alternative eMail',TO_TIMESTAMP('2019-03-27 18:23:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T18:23:26.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576305 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-27T18:23:37.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564644,576305,0,10,293,'EMail2',TO_TIMESTAMP('2019-03-27 18:23:37','YYYY-MM-DD HH24:MI:SS'),100,'N','EMail-Adresse','D',200,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Alternative eMail',0,0,TO_TIMESTAMP('2019-03-27 18:23:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-27T18:23:37.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-27T18:24:08.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549130,578102,0,541016,TO_TIMESTAMP('2019-03-27 18:24:07','YYYY-MM-DD HH24:MI:SS'),100,'When data is imported from a an external datasource, this element can be used to identify the data record',50,'D','Y','N','N','N','N','N','N','N','Migration_Key',TO_TIMESTAMP('2019-03-27 18:24:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T18:24:08.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-27T18:24:08.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560989,578103,0,541016,TO_TIMESTAMP('2019-03-27 18:24:08','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2019-03-27 18:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T18:24:08.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-27T18:24:08.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564643,578104,0,541016,TO_TIMESTAMP('2019-03-27 18:24:08','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2019-03-27 18:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T18:24:08.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-27T18:24:08.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564644,578105,0,541016,TO_TIMESTAMP('2019-03-27 18:24:08','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','','Y','N','N','N','N','N','N','N','Alternative eMail',TO_TIMESTAMP('2019-03-27 18:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T18:24:08.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-27T18:24:23.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562326
;

-- 2019-03-27T18:24:23.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562320
;

-- 2019-03-27T18:24:23.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562321
;

-- 2019-03-27T18:24:23.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562322
;

-- 2019-03-27T18:24:23.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562323
;

-- 2019-03-27T18:24:23.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562324
;

-- 2019-03-27T18:24:23.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562325
;

-- 2019-03-27T18:24:23.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562327
;

-- 2019-03-27T18:24:23.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562328
;

-- 2019-03-27T18:24:23.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562329
;

-- 2019-03-27T18:24:23.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562330
;

-- 2019-03-27T18:24:23.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562331
;

-- 2019-03-27T18:24:23.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578034
;

-- 2019-03-27T18:24:23.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578104
;

-- 2019-03-27T18:24:23.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2019-03-27 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578105
;

-- 2019-03-27T18:25:28.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578104,0,541016,558087,541435,'F',TO_TIMESTAMP('2019-03-27 18:25:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'eMail',135,0,0,TO_TIMESTAMP('2019-03-27 18:25:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T18:25:46.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578105,0,541016,558088,541435,'F',TO_TIMESTAMP('2019-03-27 18:25:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Alternative eMail',136,0,0,TO_TIMESTAMP('2019-03-27 18:25:46','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2019-03-27T18:59:21.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN EMail2 VARCHAR(200)')
;

