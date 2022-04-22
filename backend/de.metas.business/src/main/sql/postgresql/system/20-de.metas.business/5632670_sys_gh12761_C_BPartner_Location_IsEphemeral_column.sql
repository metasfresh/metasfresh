-- 2022-03-31T06:01:51.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580745,0,'IsEphemeral',TO_TIMESTAMP('2022-03-31 09:01:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ephemeral','Ephemeral',TO_TIMESTAMP('2022-03-31 09:01:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-31T06:01:51.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580745 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-31T06:02:14.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582649,580745,0,20,293,'IsEphemeral',TO_TIMESTAMP('2022-03-31 09:02:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Ephemeral',0,0,TO_TIMESTAMP('2022-03-31 09:02:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-31T06:02:14.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-31T06:02:14.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580745)
;

-- 2022-03-31T06:02:17.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN IsEphemeral CHAR(1) DEFAULT ''N'' CHECK (IsEphemeral IN (''Y'',''N'')) NOT NULL')
;

-- 2022-04-01T13:38:12.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2022-04-01 16:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582649
;

-- 2022-04-01T13:38:14.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_location','IsEphemeral','CHAR(1)',null,'N')
;

-- 2022-04-01T13:38:15.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BPartner_Location SET IsEphemeral='N' WHERE IsEphemeral IS NULL
;

-- 2022-04-01T13:39:07.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_location','IsEphemeral','CHAR(1)',null,'N')
;

-- 2022-04-01T13:39:08.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BPartner_Location SET IsEphemeral='N' WHERE IsEphemeral IS NULL
;

-- 2022-04-01T07:45:39.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-04-01 10:45:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2960
;

-- 2022-04-01T07:45:42.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_location','Name','VARCHAR(255)',null,'.')
;

-- 2022-04-01T07:45:43.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BPartner_Location SET Name='.' WHERE Name IS NULL
;