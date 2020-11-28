
DROP VIEW IF EXISTS C_BPartner_Recent_ID;
CREATE OR REPLACE VIEW C_BPartner_Recent_V
AS
SELECT DISTINCT ON (C_BPartner_ID) 
    C_BPartner_Recent_V_ID, C_BPartner_ID, Updated
FROM (    
    SELECT C_BPartner_ID AS C_BPartner_Recent_V_ID, C_BPartner_ID, Updated
    FROM C_BPartner

    UNION

    SELECT C_BPartner_ID, C_BPartner_ID, Updated
    FROM C_BPartner_Location
    
    UNION

    SELECT C_BPartner_ID, C_BPartner_ID, l.Updated
    FROM C_BPartner_Location bpl
        JOIN C_Location l ON l.C_Location_ID = bpl.C_Location_ID

    UNION

    SELECT C_BPartner_ID, C_BPartner_ID, Updated
    FROM AD_User
    WHERE C_BPartner_ID IS NOT NULL
) d
ORDER BY C_BPartner_ID, Updated DESC
;

COMMENT ON VIEW C_BPartner_Recent_V 
IS 'View to select C_BPartner_IDs of C_BPartners, C_BPartner_Locations and AD_Users that were recently changed; See https://github.com/metasfresh/metasfresh/issues/5273';

CREATE INDEX IF NOT EXISTS C_BPartner_Updated ON c_bpartner (Updated);
CREATE INDEX IF NOT EXISTS C_BPartner_Location_Updated ON C_BPartner_Location (Updated);
CREATE INDEX IF NOT EXISTS C_Location_Updated ON C_Location (Updated);
CREATE INDEX IF NOT EXISTS AD_User_Updated ON AD_User (Updated);

-- 2019-06-18T23:19:01.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='D',Updated=TO_TIMESTAMP('2019-06-18 23:19:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541370
;

-- 2019-06-19T00:20:49.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-06-19 00:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568199
;

-- 2019-06-19T00:32:02.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TableName='C_BPartner_Recent_V', Name='C_BPartner_Recent_V',Updated=TO_TIMESTAMP('2019-06-19 00:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541370
;

-- 2019-06-19T00:32:02.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='C_BPartner_Recent_V',Updated=TO_TIMESTAMP('2019-06-19 00:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555032
;

-- 2019-06-19T00:32:06.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='N',Updated=TO_TIMESTAMP('2019-06-19 00:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568199
;

-- 2019-06-19T00:32:31.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-06-19 00:32:30','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-06-19 00:32:30','YYYY-MM-DD HH24:MI:SS'),100,576856,0,'C_BPartner_Recent_V_ID','D','C_BPartner_Recent_V_ID','C_BPartner_Recent_V_ID')
;

-- 2019-06-19T00:32:31.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576856 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-06-19T00:32:47.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-06-19 00:32:47','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-06-19 00:32:47','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541370,'N',568275,'N','N','N','N','N','N','N','N',0,0,576856,'D','N','N','C_BPartner_Recent_V_ID','N','C_BPartner_Recent_V_ID')
;

-- 2019-06-19T00:32:47.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568275 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-06-19T00:32:47.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576856) 
;

-- 2019-06-19T00:36:16.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-06-19 00:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568200
;

-- 2019-06-19T00:36:24.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-06-19 00:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568199
;

