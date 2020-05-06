

-- 2019-01-09T15:48:51.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_BPartner_Org_Link',Updated=TO_TIMESTAMP('2019-01-09 15:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=260
;

-- 2019-01-09T17:02:09.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-01-09 17:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=170
;


--
-- Fix: insert missing AD_OrgInfos that might have piled up
--
INSERT INTO AD_OrgInfo (
    ad_org_id, -- numeric(10,0) NOT NULL,
    ad_client_id, -- numeric(10,0) NOT NULL,
    createdby, -- numeric(10,0) NOT NULL,
    updatedby -- numeric(10,0) NOT NULL,
)
SELECT 
    ad_org_id, -- numeric(10,0) NOT NULL,
    ad_client_id, -- numeric(10,0) NOT NULL,
    99 AS createdby, -- numeric(10,0) NOT NULL,
    99 AS updatedby -- numeric(10,0) NOT NULL,
FROM AD_Org o
WHERE NOT EXISTS (select 1 from AD_OrgInfo oi where oi.AD_Org_ID=o.AD_Org_ID);

COMMIT;

--
-- make AD_Org_ID a noremal parent link column and add a "normal" AD_OrgInfo_ID PK column
--
-- 2019-01-10T08:22:59.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@AD_Org_ID/-1@', IsKey='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-01-10 08:22:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1809
;

-- 2019-01-10T08:23:19.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575936,0,'AD_OrgInfo_ID',TO_TIMESTAMP('2019-01-10 08:23:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_OrgInfo_ID','AD_OrgInfo_ID',TO_TIMESTAMP('2019-01-10 08:23:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-10T08:23:19.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575936 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-10T08:23:30.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563754,575936,0,13,228,'AD_OrgInfo_ID',TO_TIMESTAMP('2019-01-10 08:23:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','AD_OrgInfo_ID',0,0,TO_TIMESTAMP('2019-01-10 08:23:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-01-10T08:23:30.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-01-10T08:24:08.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='N',Updated=TO_TIMESTAMP('2019-01-10 08:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563754
;

-- 2019-01-10T08:24:11.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_OrgInfo','ALTER TABLE public.AD_OrgInfo ADD COLUMN AD_OrgInfo_ID NUMERIC(10)')
;

COMMIT;

UPDATE AD_OrgInfo SET AD_OrgInfo_ID=AD_Org_ID WHERE AD_OrgInfo_ID IS NULL;

COMMIT;

ALTER TABLE public.ad_orginfo DROP CONSTRAINT ad_orginfo_pkey;
ALTER TABLE public.ad_orginfo ADD CONSTRAINT ad_orginfo_pkey PRIMARY KEY (AD_OrgInfo_ID);

COMMIT;
-- 2019-01-10T08:26:32.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='Y', IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-01-10 08:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563754
;

-- 2019-01-10T08:26:35.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','AD_OrgInfo_ID','NUMERIC(10)',null,null)
;


-- 2019-01-10T08:52:29.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540483,0,228,TO_TIMESTAMP('2019-01-10 08:52:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','AD_OrgInfo_UC_AD_Org_ID','N',TO_TIMESTAMP('2019-01-10 08:52:29','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-01-10T08:52:29.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540483 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-01-10T08:52:52.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,1809,540928,540483,0,TO_TIMESTAMP('2019-01-10 08:52:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-01-10 08:52:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-10T08:53:01.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_OrgInfo_UC_AD_Org_ID ON AD_OrgInfo (AD_Org_ID) WHERE IsActive='Y'
;

