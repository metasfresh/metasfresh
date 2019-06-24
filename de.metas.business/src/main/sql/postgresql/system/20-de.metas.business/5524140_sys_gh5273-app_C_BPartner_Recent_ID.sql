

--------------------------------
CREATE OR REPLACE VIEW C_BPartner_Recent_ID 
AS
SELECT C_BPartner_ID, Updated
FROM C_BPartner
	UNION DISTINCT
SELECT C_BPartner_ID, Updated
FROM C_BPartner_Location
	UNION DISTINCT
SELECT C_BPartner_ID, Updated
FROM AD_User
WHERE C_BPartner_ID IS NOT NULL
;

CREATE INDEX IF NOT EXISTS C_BPartner_Updated ON c_bpartner (Updated);
CREATE INDEX IF NOT EXISTS C_BPartner_Location_Updated ON C_BPartner_Location (Updated);
CREATE INDEX IF NOT EXISTS AD_User_Updated ON AD_User (Updated);



-- 2019-06-07T22:34:39.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_EntityType (Processing,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_EntityType_ID,IsDisplayed,AD_Org_ID,EntityType,Name) VALUES ('N',0,'Y',TO_TIMESTAMP('2019-06-07 22:34:39','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-06-07 22:34:39','YYYY-MM-DD HH24:MI:SS'),100,540251,'Y',0,'D','D')
--;

-- 2019-06-07T22:35:00.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Org_ID,AD_Table_ID,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation,Name) VALUES (0,'4',0,100,'Y',TO_TIMESTAMP('2019-06-07 22:35:00','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2019-06-07 22:35:00','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N',0,541370,'D','C_BPartner_Recent_ID','NP','N','C_BPartner_Recent_ID')
;

-- 2019-06-07T22:35:00.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541370 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-06-07T22:35:00.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,AD_Org_ID,Description) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2019-06-07 22:35:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2019-06-07 22:35:00','YYYY-MM-DD HH24:MI:SS'),100,555032,0,'C_BPartner_Recent_ID',0,'Table C_BPartner_Recent_ID')
;

-- 2019-06-07T22:35:21.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-06-07 22:35:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-06-07 22:35:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541370,'N','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',568199,'N','N','N','N','N','N','N','N',0,0,187,'D','N','N','C_BPartner_ID','N','Geschäftspartner','Bezeichnet einen Geschäftspartner')
;

-- 2019-06-07T22:35:21.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568199 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-06-07T22:35:21.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2019-06-07T22:35:33.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (16,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-06-07 22:35:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-06-07 22:35:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541370,'N','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',568200,'N','N','N','N','N','N','N','N',0,0,607,'D','N','N','Updated','N','Aktualisiert','Datum, an dem dieser Eintrag aktualisiert wurde')
;

-- 2019-06-07T22:35:33.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-06-07T22:35:33.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-06-07T22:35:44.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N', IsView='Y',Updated=TO_TIMESTAMP('2019-06-07 22:35:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541370
;

-- 2019-06-07T22:36:12.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--UPDATE AD_EntityType SET ModelPackage='de.metas.rest_api.model',Updated=TO_TIMESTAMP('2019-06-07 22:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_EntityType_ID=540251
--;
