-- 2019-03-30T20:28:00.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_Field WHERE DataEntry_Line_ID=@DataEntry_Line_ID/0@',Updated=TO_TIMESTAMP('2019-03-30 20:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563945
;

-- 2019-04-01T17:57:42.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('4',0,0,0,541340,'N',TO_TIMESTAMP('2019-04-01 17:57:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','N','Y','N','N','N','N','N','N','N','N',0,'MD_Available_To_Sales_QueryResult','NP','L','MD_Available_To_Sales_QueryResult',TO_TIMESTAMP('2019-04-01 17:57:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-01T17:57:42.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541340 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-04-01T17:57:42.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554993,TO_TIMESTAMP('2019-04-01 17:57:42','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MD_Available_To_Sales_QueryResult',1,'Y','N','Y','Y','MD_Available_To_Sales_QueryResult','N',1000000,TO_TIMESTAMP('2019-04-01 17:57:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-01T17:58:33.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='There is no real table or view in the DB; This AD_Table is used in metasfresh to access the rows returned by the DB function de_metas_material.',Updated=TO_TIMESTAMP('2019-04-01 17:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541340
;

-- 2019-04-01T17:58:45.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='There is no real table or view in the DB; This AD_Table is used in metasfresh to access the rows returned by the DB function de_metas_material.retrieve_atp_at_date(timestamp with time zone).',Updated=TO_TIMESTAMP('2019-04-01 17:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540859
;

-- 2019-04-01T20:28:42.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567357,454,0,30,541340,'M_Product_ID',TO_TIMESTAMP('2019-04-01 20:28:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','de.metas.material.dispo',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produkt',0,0,TO_TIMESTAMP('2019-04-01 20:28:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-01T20:28:42.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567357 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-01T20:30:45.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567358,543463,0,10,541340,'StorageAttributesKey',TO_TIMESTAMP('2019-04-01 20:30:45','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',2000,'Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','StorageAttributesKey (technical)',0,0,TO_TIMESTAMP('2019-04-01 20:30:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-01T20:30:45.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567358 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-01T20:32:39.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576514,0,'QtyToBeShipped',TO_TIMESTAMP('2019-04-01 20:32:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','QtyToBeShipped','QtyToBeShipped',TO_TIMESTAMP('2019-04-01 20:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-01T20:32:39.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576514 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-01T20:32:50.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567359,576514,0,29,541340,'QtyToBeShipped',TO_TIMESTAMP('2019-04-01 20:32:50','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','QtyToBeShipped',0,0,TO_TIMESTAMP('2019-04-01 20:32:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-01T20:32:50.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567359 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-01T20:33:53.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576515,0,'QtyToBeReceived',TO_TIMESTAMP('2019-04-01 20:33:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','QtyToBeReceived','QtyToBeReceived',TO_TIMESTAMP('2019-04-01 20:33:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-01T20:33:53.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576515 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-01T20:34:01.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567360,576515,0,29,541340,'QtyToBeReceived',TO_TIMESTAMP('2019-04-01 20:34:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','QtyToBeReceived',0,0,TO_TIMESTAMP('2019-04-01 20:34:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-01T20:34:01.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567360 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-01T20:34:20.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567361,543686,0,29,541340,'QtyOnHandStock',TO_TIMESTAMP('2019-04-01 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Aktueller oder geplanter Lagerbestand','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lagerbestand',0,0,TO_TIMESTAMP('2019-04-01 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-01T20:34:20.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567361 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-01T20:35:24.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567362,215,0,30,541340,'C_UOM_ID',TO_TIMESTAMP('2019-04-01 20:35:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','de.metas.material.dispo',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Maßeinheit',0,0,TO_TIMESTAMP('2019-04-01 20:35:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-01T20:35:24.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-01T21:11:19.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541340
;

-- 2019-04-01T21:11:42.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567362
;

-- 2019-04-01T21:11:52.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567357
;

-- 2019-04-01T21:11:53.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567358
;

-- 2019-04-01T21:11:54.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567359
;

-- 2019-04-01T21:11:55.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567360
;

-- 2019-04-01T21:11:57.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-01 21:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567361
;

-- 2019-04-01T21:12:59.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Available_For_Sales_QueryResult', TableName='MD_Available_For_Sales_QueryResult',Updated=TO_TIMESTAMP('2019-04-01 21:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541340
;

-- 2019-04-01T21:12:59.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='MD_Available_For_Sales_QueryResult',Updated=TO_TIMESTAMP('2019-04-01 21:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554993
;

-- 2019-04-02T07:00:09.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576516,0,'ShipmentPreparationDate',TO_TIMESTAMP('2019-04-02 07:00:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','ShipmentPreparationDate','ShipmentPreparationDate',TO_TIMESTAMP('2019-04-02 07:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T07:00:09.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576516 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-02T07:00:24.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567363,576516,0,16,541340,'ShipmentPreparationDate',TO_TIMESTAMP('2019-04-02 07:00:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.cockpit',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','ShipmentPreparationDate',0,0,TO_TIMESTAMP('2019-04-02 07:00:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T07:00:24.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567363 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T07:00:45.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576517,0,'SalesOrderLastUpdated',TO_TIMESTAMP('2019-04-02 07:00:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','SalesOrderLastUpdated','SalesOrderLastUpdated',TO_TIMESTAMP('2019-04-02 07:00:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T07:00:45.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576517 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-02T07:00:55.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567364,576517,0,16,541340,'SalesOrderLastUpdated',TO_TIMESTAMP('2019-04-02 07:00:55','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.cockpit',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','SalesOrderLastUpdated',0,0,TO_TIMESTAMP('2019-04-02 07:00:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T07:00:55.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T12:26:38.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 12:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543954 AND AD_Language='de_CH'
;

-- 2019-04-02T12:26:38.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543954,'de_CH') 
;

-- 2019-04-02T12:26:47.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 12:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543954 AND AD_Language='de_DE'
;

-- 2019-04-02T12:26:47.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543954,'de_DE') 
;

-- 2019-04-02T12:26:47.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543954,'de_DE') 
;

-- 2019-04-02T12:26:47.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NoPriceConditionsColor_ID', Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', Help=NULL WHERE AD_Element_ID=543954
;

-- 2019-04-02T12:26:47.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NoPriceConditionsColor_ID', Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', Help=NULL, AD_Element_ID=543954 WHERE UPPER(ColumnName)='NOPRICECONDITIONSCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T12:26:47.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NoPriceConditionsColor_ID', Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', Help=NULL WHERE AD_Element_ID=543954 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T12:26:47.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543954) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543954)
;

-- 2019-04-02T12:26:47.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543954
;

-- 2019-04-02T12:26:47.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', Help=NULL WHERE AD_Element_ID = 543954
;

-- 2019-04-02T12:26:47.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'No Price Conditions Indicator', Description = 'Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543954
;

-- 2019-04-02T12:26:52.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen ellt nur für diese Position erstellt wurden.',Updated=TO_TIMESTAMP('2019-04-02 12:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543954 AND AD_Language='nl_NL'
;

-- 2019-04-02T12:26:52.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543954,'nl_NL') 
;

-- 2019-04-02T12:37:03.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Red means that mandatory pricing conditions are missing; yellow means that temporary pricing conditions were created just for the respective position.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 12:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543954 AND AD_Language='en_US'
;

-- 2019-04-02T12:37:03.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543954,'en_US') 
;

-- 2019-04-02T12:37:22.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.',Updated=TO_TIMESTAMP('2019-04-02 12:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543954 AND AD_Language='de_CH'
;

-- 2019-04-02T12:37:22.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543954,'de_CH') 
;

-- 2019-04-02T12:37:29.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.',Updated=TO_TIMESTAMP('2019-04-02 12:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543954 AND AD_Language='de_DE'
;

-- 2019-04-02T12:37:29.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543954,'de_DE') 
;

-- 2019-04-02T12:37:29.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543954,'de_DE') 
;

-- 2019-04-02T12:37:29.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NoPriceConditionsColor_ID', Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', Help=NULL WHERE AD_Element_ID=543954
;

-- 2019-04-02T12:37:29.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NoPriceConditionsColor_ID', Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', Help=NULL, AD_Element_ID=543954 WHERE UPPER(ColumnName)='NOPRICECONDITIONSCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T12:37:29.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NoPriceConditionsColor_ID', Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', Help=NULL WHERE AD_Element_ID=543954 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T12:37:29.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543954) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543954)
;

-- 2019-04-02T12:37:29.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543954
;

-- 2019-04-02T12:37:29.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='No Price Conditions Indicator', Description='Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', Help=NULL WHERE AD_Element_ID = 543954
;

-- 2019-04-02T12:37:29.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'No Price Conditions Indicator', Description = 'Rot bedeutet, dass erforderliche Preis-Konditionen fehlen; gelb bedeutet, dass temporäre Preis-Konditionen nur für die jeweilige Position erstellt wurden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543954
;

-- 2019-04-02T13:53:28.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576523,0,'InsufficientQtyAvailaibleForSalesColor_ID',TO_TIMESTAMP('2019-04-02 13:53:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','InsufficientQtyAvailaibleForSalesColor_ID','InsufficientQtyAvailaibleForSalesColor_ID',TO_TIMESTAMP('2019-04-02 13:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T13:53:28.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576523 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-02T14:07:54.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 14:07:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_CH'
;

-- 2019-04-02T14:07:54.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_CH') 
;

-- 2019-04-02T14:07:57.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 14:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_DE'
;

-- 2019-04-02T14:07:57.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_DE') 
;

-- 2019-04-02T14:07:57.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576523,'de_DE') 
;

-- 2019-04-02T14:07:57.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InsufficientQtyAvailaibleForSalesColor_ID', Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523
;

-- 2019-04-02T14:07:57.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailaibleForSalesColor_ID', Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, AD_Element_ID=576523 WHERE UPPER(ColumnName)='INSUFFICIENTQTYAVAILAIBLEFORSALESCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T14:07:57.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailaibleForSalesColor_ID', Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T14:07:57.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576523) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576523)
;

-- 2019-04-02T14:07:57.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-02T14:07:57.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-02T14:07:57.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'InsufficientQtyAvailaibleForSalesColor_ID', Description = 'Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-02T14:09:12.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Red means that the current stock minus foreseeable shipments is not sufficient to fulfill the current order line.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='en_US'
;

-- 2019-04-02T14:09:12.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'en_US') 
;

-- 2019-04-02T14:17:08.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576524,0,'QtyAvailaibleForSales',TO_TIMESTAMP('2019-04-02 14:17:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Verfügbar','Verfügbar',TO_TIMESTAMP('2019-04-02 14:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T14:17:08.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576524 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-02T14:17:42.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 14:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='de_CH'
;

-- 2019-04-02T14:17:42.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'de_CH') 
;

-- 2019-04-02T14:17:49.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-02 14:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='de_DE'
;

-- 2019-04-02T14:17:49.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'de_DE') 
;

-- 2019-04-02T14:17:49.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576524,'de_DE') 
;

-- 2019-04-02T14:17:49.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyAvailaibleForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', Help=NULL WHERE AD_Element_ID=576524
;

-- 2019-04-02T14:17:49.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailaibleForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', Help=NULL, AD_Element_ID=576524 WHERE UPPER(ColumnName)='QTYAVAILAIBLEFORSALES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T14:17:49.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailaibleForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', Help=NULL WHERE AD_Element_ID=576524 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T14:17:49.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576524) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576524)
;

-- 2019-04-02T14:17:49.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-02T14:17:49.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen', Help=NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-02T14:17:49.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verfügbar', Description = 'Aktueller Lagerbestand abzüglich absehbarer Lieferungen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-02T14:18:26.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Current stock minus foreseeable shipments', IsTranslated='Y', Name='Available', PrintName='Available',Updated=TO_TIMESTAMP('2019-04-02 14:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='en_US'
;

-- 2019-04-02T14:18:26.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'en_US') 
;

-- 2019-04-02T14:23:25.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567583,576523,0,27,260,'InsufficientQtyAvailaibleForSalesColor_ID',TO_TIMESTAMP('2019-04-02 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','InsufficientQtyAvailaibleForSalesColor_ID',0,0,TO_TIMESTAMP('2019-04-02 14:23:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T14:23:25.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567583 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T14:24:58.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.',Updated=TO_TIMESTAMP('2019-04-02 14:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='de_CH'
;

-- 2019-04-02T14:24:58.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'de_CH') 
;

-- 2019-04-02T14:25:03.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.',Updated=TO_TIMESTAMP('2019-04-02 14:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='de_DE'
;

-- 2019-04-02T14:25:03.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'de_DE') 
;

-- 2019-04-02T14:25:03.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576524,'de_DE') 
;

-- 2019-04-02T14:25:03.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyAvailaibleForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID=576524
;

-- 2019-04-02T14:25:03.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailaibleForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL, AD_Element_ID=576524 WHERE UPPER(ColumnName)='QTYAVAILAIBLEFORSALES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T14:25:03.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailaibleForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID=576524 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T14:25:03.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576524) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576524)
;

-- 2019-04-02T14:25:03.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-02T14:25:03.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-02T14:25:03.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verfügbar', Description = 'Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-02T14:25:27.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Current stock minus foreseeable shipments in the respective product''s stocking UOM',Updated=TO_TIMESTAMP('2019-04-02 14:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='en_US'
;

-- 2019-04-02T14:25:27.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'en_US') 
;

-- 2019-04-02T14:25:38.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Current stock minus foreseeable shipments in the respective product''s stocking UOM.',Updated=TO_TIMESTAMP('2019-04-02 14:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='en_US'
;

-- 2019-04-02T14:25:38.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'en_US') 
;

-- 2019-04-02T14:28:35.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567584,576524,0,29,260,'QtyAvailaibleForSales',TO_TIMESTAMP('2019-04-02 14:28:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Verfügbar',0,0,TO_TIMESTAMP('2019-04-02 14:28:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T14:28:35.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567584 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- -- 2019-04-02T14:28:35.946
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- /* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN QtyAvailaibleForSales NUMERIC')
-- ;

-- -- 2019-04-02T14:29:04.705
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO t_alter_column values('c_orderline','QtyAvailaibleForSales','NUMERIC',null,null)
-- ;

-- -- 2019-04-02T14:29:14.555
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- /* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN InsufficientQtyAvailaibleForSalesColor_ID NUMERIC(10)')
-- ;

-- -- 2019-04-02T14:34:20.336
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO t_alter_column values('c_orderline','InsufficientQtyAvailaibleForSalesColor_ID','NUMERIC(10)',null,null)
-- ;

-- 2019-04-02T14:45:34.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='InsufficientQtyAvailableForSalesColor_ID',Updated=TO_TIMESTAMP('2019-04-02 14:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523
;

-- 2019-04-02T14:45:34.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523
;

-- 2019-04-02T14:45:34.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, AD_Element_ID=576523 WHERE UPPER(ColumnName)='INSUFFICIENTQTYAVAILABLEFORSALESCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T14:45:34.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailaibleForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T14:45:43.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='InsufficientQtyAvailableForSalesColor_ID', PrintName='InsufficientQtyAvailableForSalesColor_ID',Updated=TO_TIMESTAMP('2019-04-02 14:45:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_CH'
;

-- 2019-04-02T14:45:43.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_CH') 
;

-- 2019-04-02T14:45:48.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='InsufficientQtyAvailableForSalesColor_ID', PrintName='InsufficientQtyAvailableForSalesColor_ID',Updated=TO_TIMESTAMP('2019-04-02 14:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_DE'
;

-- 2019-04-02T14:45:48.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_DE') 
;

-- 2019-04-02T14:45:48.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576523,'de_DE') 
;

-- 2019-04-02T14:45:48.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523
;

-- 2019-04-02T14:45:48.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, AD_Element_ID=576523 WHERE UPPER(ColumnName)='INSUFFICIENTQTYAVAILABLEFORSALESCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T14:45:48.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T14:45:48.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576523) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576523)
;

-- 2019-04-02T14:45:48.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576523)
;

-- 2019-04-02T14:45:48.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-02T14:45:48.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-02T14:45:48.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'InsufficientQtyAvailableForSalesColor_ID', Description = 'Rot bedeutet, dass der derzeitige Lagerbestand abzüglich bereits absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-02T14:45:54.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='InsufficientQtyAvailableForSalesColor_ID', PrintName='InsufficientQtyAvailableForSalesColor_ID',Updated=TO_TIMESTAMP('2019-04-02 14:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='en_US'
;

-- 2019-04-02T14:45:54.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'en_US') 
;

-- 2019-04-02T14:46:05.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN InsufficientQtyAvailableForSalesColor_ID NUMERIC(10)')
;

-- 2019-04-02T14:46:24.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyAvailableForSales',Updated=TO_TIMESTAMP('2019-04-02 14:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524
;

-- 2019-04-02T14:46:24.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyAvailableForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID=576524
;

-- 2019-04-02T14:46:24.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailableForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL, AD_Element_ID=576524 WHERE UPPER(ColumnName)='QTYAVAILABLEFORSALES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-02T14:46:24.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailableForSales', Name='Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID=576524 AND IsCentrallyMaintained='Y'
;

-- 2019-04-02T14:46:43.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN QtyAvailableForSales NUMERIC')
;
-- 2019-04-03T07:57:35.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576515
;

-- 2019-04-03T07:57:35.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576515
;

-- QtyToBeReceived - not needed after all
DELETE FROM AD_Column WHERE AD_Column_ID=567360;

-- 2019-04-03T08:02:49.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541343,'N',TO_TIMESTAMP('2019-04-03 08:02:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','N','Y','N','N','Y','N','N','N','N','N',0,'MD_AvailableForSales_Config','NP','L','MD_AvailableForSales_Config',TO_TIMESTAMP('2019-04-03 08:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:02:49.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541343 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-04-03T08:02:49.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554996,TO_TIMESTAMP('2019-04-03 08:02:49','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MD_AvailableForSales_Config',1,'Y','N','Y','Y','MD_AvailableForSales_Config','N',1000000,TO_TIMESTAMP('2019-04-03 08:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:03:00.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,541343,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',567607,'Y',0,102,'de.metas.material.dispo','AD_Client_ID','Mandant für diese Installation.','Mandant')
;

-- 2019-04-03T08:03:00.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567607 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:00.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y','N','Y','N',TO_TIMESTAMP('2019-04-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,541343,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',567608,'Y',0,113,'de.metas.material.dispo','AD_Org_ID','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-03T08:03:00.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567608 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:01.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,541343,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',567609,'Y',0,245,'de.metas.material.dispo','Created','Datum, an dem dieser Eintrag erstellt wurde','Erstellt')
;

-- 2019-04-03T08:03:01.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567609 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:01.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,541343,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,567610,'Y',0,246,'de.metas.material.dispo','CreatedBy','Nutzer, der diesen Eintrag erstellt hat','Erstellt durch')
;

-- 2019-04-03T08:03:01.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567610 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:01.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,541343,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',567611,'Y',0,348,'de.metas.material.dispo','IsActive','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-03T08:03:01.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567611 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:01.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,541343,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',567612,'Y',0,607,'de.metas.material.dispo','Updated','Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert')
;

-- 2019-04-03T08:03:01.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567612 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:01.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,541343,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,567613,'Y',0,608,'de.metas.material.dispo','UpdatedBy','Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch')
;

-- 2019-04-03T08:03:01.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567613 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:03:01.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576544,0,'MD_AvailableForSales_Config_ID',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','MD_AvailableForSales_Config','MD_AvailableForSales_Config',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:03:01.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576544 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T08:03:01.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N',TO_TIMESTAMP('2019-04-03 08:03:01','YYYY-MM-DD HH24:MI:SS'),100,541343,567614,'Y',0,576544,'de.metas.material.dispo','MD_AvailableForSales_Config_ID','MD_AvailableForSales_Config')
;

-- 2019-04-03T08:03:01.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567614 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:05:53.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.',Updated=TO_TIMESTAMP('2019-04-03 08:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_CH'
;

-- 2019-04-03T08:05:53.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_CH') 
;

-- 2019-04-03T08:05:58.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.',Updated=TO_TIMESTAMP('2019-04-03 08:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_DE'
;

-- 2019-04-03T08:05:58.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_DE') 
;

-- 2019-04-03T08:05:58.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576523,'de_DE') 
;

-- 2019-04-03T08:05:58.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523
;

-- 2019-04-03T08:05:58.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, AD_Element_ID=576523 WHERE UPPER(ColumnName)='INSUFFICIENTQTYAVAILABLEFORSALESCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:05:58.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:05:58.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576523) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576523)
;

-- 2019-04-03T08:05:58.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-03T08:05:58.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-03T08:05:58.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'InsufficientQtyAvailableForSalesColor_ID', Description = 'Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilge Auftragsposition zu bedienen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-03T08:06:51.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Color to use when flagging sale order lines where the current stock minus foreseeable shipments is not sufficient to fulfill the ordered quantity.',Updated=TO_TIMESTAMP('2019-04-03 08:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='en_US'
;

-- 2019-04-03T08:06:51.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'en_US') 
;

-- 2019-04-03T08:07:04.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.',Updated=TO_TIMESTAMP('2019-04-03 08:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576523 AND AD_Language='de_DE'
;

-- 2019-04-03T08:07:04.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576523,'de_DE') 
;

-- 2019-04-03T08:07:04.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576523,'de_DE') 
;

-- 2019-04-03T08:07:04.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523
;

-- 2019-04-03T08:07:04.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', Help=NULL, AD_Element_ID=576523 WHERE UPPER(ColumnName)='INSUFFICIENTQTYAVAILABLEFORSALESCOLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:07:04.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InsufficientQtyAvailableForSalesColor_ID', Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID=576523 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:07:04.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576523) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576523)
;

-- 2019-04-03T08:07:04.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-03T08:07:04.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='InsufficientQtyAvailableForSalesColor_ID', Description='Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', Help=NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-03T08:07:04.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'InsufficientQtyAvailableForSalesColor_ID', Description = 'Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576523
;

-- 2019-04-03T08:08:48.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540974,TO_TIMESTAMP('2019-04-03 08:08:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Color',TO_TIMESTAMP('2019-04-03 08:08:48','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-04-03T08:08:48.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540974 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-04-03T08:10:42.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,6230,6222,0,540974,457,225,TO_TIMESTAMP('2019-04-03 08:10:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-04-03 08:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:10:52.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='AD_Color',Updated=TO_TIMESTAMP('2019-04-03 08:10:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540974
;

-- 2019-04-03T08:11:14.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:11:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 08:11:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541343,'N',540974,567615,'N','N','N','N','N','N','N','N',0,0,576523,'de.metas.material.dispo','N','N','InsufficientQtyAvailableForSalesColor_ID','Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.','N','InsufficientQtyAvailableForSalesColor_ID')
;

-- 2019-04-03T08:11:14.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567615 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:11:35.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-04-03 08:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567615
;

-- 2019-04-03T08:13:50.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576545,0,'ShipmentDateLookAheadHours',TO_TIMESTAMP('2019-04-03 08:13:50','YYYY-MM-DD HH24:MI:SS'),100,'The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.','de.metas.material.cockpit','Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.','Y','ShipmentDateLookAheadHours','ShipmentDateLookAheadHours',TO_TIMESTAMP('2019-04-03 08:13:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:13:50.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576545 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T08:24:47.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Stunden ab Bereitstellungsdatum des aktuellen Auftrags, bis zu denen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit Bereitstellungsdatum dass nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.
', IsTranslated='Y', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', PrintName='Vorausschauinterval zu gepl. Lieferungen (Std)',Updated=TO_TIMESTAMP('2019-04-03 08:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_CH'
;

-- 2019-04-03T08:24:47.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_CH') 
;

-- 2019-04-03T08:24:51.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', PrintName='Vorausschauinterval zu gepl. Lieferungen (Std)',Updated=TO_TIMESTAMP('2019-04-03 08:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-03T08:24:51.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-03T08:24:51.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-03T08:24:51.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE AD_Element_ID=576545
;

-- 2019-04-03T08:24:51.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.', AD_Element_ID=576545 WHERE UPPER(ColumnName)='SHIPMENTDATELOOKAHEADHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:24:51.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE AD_Element_ID=576545 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:24:51.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576545)
;

-- 2019-04-03T08:24:51.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vorausschauinterval zu gepl. Lieferungen (Std)', Name='Vorausschauinterval zu gepl. Lieferungen (Std)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576545)
;

-- 2019-04-03T08:24:51.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.', CommitWarning = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:24:51.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:24:51.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vorausschauinterval zu gepl. Lieferungen (Std)', Description = 'The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:17.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.
',Updated=TO_TIMESTAMP('2019-04-03 08:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_CH'
;

-- 2019-04-03T08:26:17.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_CH') 
;

-- 2019-04-03T08:26:22.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-03 08:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-03T08:26:22.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-03T08:26:22.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-03T08:26:22.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE AD_Element_ID=576545
;

-- 2019-04-03T08:26:22.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.', AD_Element_ID=576545 WHERE UPPER(ColumnName)='SHIPMENTDATELOOKAHEADHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:26:22.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE AD_Element_ID=576545 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:26:22.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576545)
;

-- 2019-04-03T08:26:22.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.', CommitWarning = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:22.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Foreseeable shipments are shipment schedules with a preparation date prior to the current date plus this sys-config''s value. The are subtracted from the quantity available for sales.
The value to pick here should depend on the estimated time required to replenish missing stocks. E.g. if this usually takes three days, the value should be set to 72.' WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:22.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vorausschauinterval zu gepl. Lieferungen (Std)', Description = 'Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:33.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-03 08:26:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-03T08:26:33.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-03T08:26:33.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-03T08:26:33.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545
;

-- 2019-04-03T08:26:33.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', AD_Element_ID=576545 WHERE UPPER(ColumnName)='SHIPMENTDATELOOKAHEADHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:26:33.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipmentDateLookAheadHours', Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID=576545 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:26:33.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576545)
;

-- 2019-04-03T08:26:33.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.', CommitWarning = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:33.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vorausschauinterval zu gepl. Lieferungen (Std)', Description='Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', Help='Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.' WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:33.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vorausschauinterval zu gepl. Lieferungen (Std)', Description = 'Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576545
;

-- 2019-04-03T08:26:37.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='de_DE'
;

-- 2019-04-03T08:26:37.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'de_DE') 
;

-- 2019-04-03T08:26:37.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576545,'de_DE') 
;

-- 2019-04-03T08:27:12.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lookahead interval for planned shipments (hr)', PrintName='Lookahead interval for planned shipments (hr)',Updated=TO_TIMESTAMP('2019-04-03 08:27:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576545 AND AD_Language='en_US'
;

-- 2019-04-03T08:27:12.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576545,'en_US') 
;

-- 2019-04-03T08:30:03.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,ValueMin,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name) VALUES (11,'',7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:30:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','0','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 08:30:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541343,'N','Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.',567620,'N','Y','N','N','N','N','N','N',0,0,576545,'de.metas.material.dispo','N','N','ShipmentDateLookAheadHours','Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.','N','Vorausschauinterval zu gepl. Lieferungen (Std)')
;

-- 2019-04-03T08:30:03.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567620 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:32:33.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576547,0,'SalesOrderLookBehindHours',TO_TIMESTAMP('2019-04-03 08:32:33','YYYY-MM-DD HH24:MI:SS'),100,'The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.','de.metas.material.cockpit','Y','SalesOrderLookBehindHours','SalesOrderLookBehindHours',TO_TIMESTAMP('2019-04-03 08:32:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:32:33.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576547 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T08:37:43.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrag, innerhalb dessen noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Bearbeitungsdauer gesetzt werden.', IsTranslated='Y', Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', PrintName='Rückschauinterval zu nicht fertig gest. Auftragspositionen',Updated=TO_TIMESTAMP('2019-04-03 08:37:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_CH'
;

-- 2019-04-03T08:37:43.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_CH') 
;

-- 2019-04-03T08:37:50.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', PrintName='Rückschauinterval zu nicht fertig gest. Auftragspositionen',Updated=TO_TIMESTAMP('2019-04-03 08:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_DE'
;

-- 2019-04-03T08:37:50.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_DE') 
;

-- 2019-04-03T08:37:50.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576547,'de_DE') 
;

-- 2019-04-03T08:37:50.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description='The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', Help=NULL WHERE AD_Element_ID=576547
;

-- 2019-04-03T08:37:50.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description='The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', Help=NULL, AD_Element_ID=576547 WHERE UPPER(ColumnName)='SALESORDERLOOKBEHINDHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:37:50.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description='The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', Help=NULL WHERE AD_Element_ID=576547 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:37:50.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description='The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576547) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576547)
;

-- 2019-04-03T08:37:50.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576547)
;

-- 2019-04-03T08:37:50.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description='The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-03T08:37:50.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description='The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', Help=NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-03T08:37:50.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rückschauinterval zu nicht fertig gest. Auftragspositionen', Description = 'The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-03T08:38:26.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.',Updated=TO_TIMESTAMP('2019-04-03 08:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_CH'
;

-- 2019-04-03T08:38:26.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_CH') 
;

-- 2019-04-03T08:39:08.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', PrintName='Rückschauinterval Auftragspositionen in Bearb. (Std)',Updated=TO_TIMESTAMP('2019-04-03 08:39:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_DE'
;

-- 2019-04-03T08:39:08.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_DE') 
;

-- 2019-04-03T08:39:08.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576547,'de_DE') 
;

-- 2019-04-03T08:39:08.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE AD_Element_ID=576547
;

-- 2019-04-03T08:39:08.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL, AD_Element_ID=576547 WHERE UPPER(ColumnName)='SALESORDERLOOKBEHINDHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T08:39:08.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesOrderLookBehindHours', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE AD_Element_ID=576547 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T08:39:08.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576547) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576547)
;

-- 2019-04-03T08:39:08.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rückschauinterval Auftragspositionen in Bearb. (Std)', Name='Rückschauinterval Auftragspositionen in Bearb. (Std)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576547)
;

-- 2019-04-03T08:39:08.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-03T08:39:08.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', Description='Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', Help=NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-03T08:39:08.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rückschauinterval Auftragspositionen in Bearb. (Std)', Description = 'Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576547
;

-- 2019-04-03T08:39:17.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rückschauinterval Auftragspositionen in Bearb. (Std)', PrintName='Rückschauinterval Auftragspositionen in Bearb. (Std)',Updated=TO_TIMESTAMP('2019-04-03 08:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_CH'
;

-- 2019-04-03T08:39:17.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_CH') 
;

-- 2019-04-03T08:39:21.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:39:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='de_DE'
;

-- 2019-04-03T08:39:21.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'de_DE') 
;

-- 2019-04-03T08:39:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576547,'de_DE') 
;

-- 2019-04-03T08:40:14.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lookbehind interval for uncompleted sales order lines (hr)', PrintName='Lookbehind interval for uncompleted sales order lines (hr)',Updated=TO_TIMESTAMP('2019-04-03 08:40:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576547 AND AD_Language='en_US'
;

-- 2019-04-03T08:40:14.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576547,'en_US') 
;

-- 2019-04-03T08:40:57.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,ValueMin,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name) VALUES (11,'',14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:40:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','0','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 08:40:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541343,'N',567621,'N','Y','N','N','N','N','N','N',0,0,576547,'de.metas.material.dispo','N','N','SalesOrderLookBehindHours','Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.','N','Rückschauinterval Auftragspositionen in Bearb. (Std)')
;

-- 2019-04-03T08:40:57.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567621 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:41:26.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (14,5000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:41:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 08:41:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541343,'N',567622,'N','N','N','N','N','N','N','N',0,0,275,'de.metas.material.dispo','N','N','Description','N','Beschreibung')
;

-- 2019-04-03T08:41:26.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567622 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:41:31.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541343
;

-- 2019-04-03T08:41:43.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567613
;

-- 2019-04-03T08:41:44.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567612
;

-- 2019-04-03T08:41:45.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567620
;

-- 2019-04-03T08:41:46.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567621
;

-- 2019-04-03T08:41:46.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567614
;

-- 2019-04-03T08:41:47.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567611
;

-- 2019-04-03T08:41:48.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567615
;

-- 2019-04-03T08:41:49.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567622
;

-- 2019-04-03T08:41:50.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567610
;

-- 2019-04-03T08:41:51.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567609
;

-- 2019-04-03T08:41:51.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567608
;

-- 2019-04-03T08:41:54.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 08:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567607
;

-- 2019-04-03T08:42:25.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.MD_AvailableForSales_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, InsufficientQtyAvailableForSalesColor_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MD_AvailableForSales_Config_ID NUMERIC(10) NOT NULL, SalesOrderLookBehindHours NUMERIC(10) NOT NULL, ShipmentDateLookAheadHours NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT InsufficientQtyAvailableForSalesColor_MDAvailableForSalesConfig FOREIGN KEY (InsufficientQtyAvailableForSalesColor_ID) REFERENCES public.AD_Color DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MD_AvailableForSales_Config_Key PRIMARY KEY (MD_AvailableForSales_Config_ID))
;

-- 2019-04-03T08:45:29.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576549,0,TO_TIMESTAMP('2019-04-03 08:45:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Einstellungen zur verfügbaren Menge für den Verkauf','Einstellungen zur verfügbaren Menge für den Verkauf',TO_TIMESTAMP('2019-04-03 08:45:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:45:29.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576549 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T08:45:33.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:45:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576549 AND AD_Language='de_CH'
;

-- 2019-04-03T08:45:33.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576549,'de_CH') 
;

-- 2019-04-03T08:45:35.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576549 AND AD_Language='de_DE'
;

-- 2019-04-03T08:45:35.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576549,'de_DE') 
;

-- 2019-04-03T08:45:35.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576549,'de_DE') 
;

-- 2019-04-03T08:46:07.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Available quantity for sales config', PrintName='Available quantity for sales config',Updated=TO_TIMESTAMP('2019-04-03 08:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576549 AND AD_Language='en_US'
;

-- 2019-04-03T08:46:07.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576549,'en_US') 
;

-- 2019-04-03T08:46:56.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576549,0,540614,TO_TIMESTAMP('2019-04-03 08:46:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','N','N','N','N','Y','Einstellungen zur verfügbaren Menge für den Verkauf','N',TO_TIMESTAMP('2019-04-03 08:46:56','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-04-03T08:46:56.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540614 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-04-03T08:46:56.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(576549) 
;

-- 2019-04-03T08:47:00.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-04-03 08:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540614
;

-- 2019-04-03T08:47:57.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:47:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574645 AND AD_Language='de_CH'
;

-- 2019-04-03T08:47:57.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574645,'de_CH') 
;

-- 2019-04-03T08:48:10.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Config', PrintName='Config',Updated=TO_TIMESTAMP('2019-04-03 08:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574645 AND AD_Language='en_US'
;

-- 2019-04-03T08:48:10.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574645,'en_US') 
;

-- 2019-04-03T08:48:14.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574645 AND AD_Language='de_DE'
;

-- 2019-04-03T08:48:14.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574645,'de_DE') 
;

-- 2019-04-03T08:48:14.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574645,'de_DE') 
;

-- 2019-04-03T08:48:36.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,574645,0,541694,541343,540614,'Y',TO_TIMESTAMP('2019-04-03 08:48:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','N','N','MD_AvailableForSales_Config','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Einstellungen','N',10,0,TO_TIMESTAMP('2019-04-03 08:48:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:48:36.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541694 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-04-03T08:48:36.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(574645) 
;

-- 2019-04-03T08:48:52.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541694,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',578355,567607,0,'de.metas.material.cockpit','Mandant für diese Installation.','Mandant')
;

-- 2019-04-03T08:48:52.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541694,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',578356,567608,0,'de.metas.material.cockpit','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-03T08:48:52.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541694,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',578357,567611,0,'de.metas.material.cockpit','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-03T08:48:52.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541694,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,578358,'N',567614,0,'de.metas.material.cockpit','MD_AvailableForSales_Config')
;

-- 2019-04-03T08:48:52.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541694,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,578359,567615,0,'de.metas.material.cockpit','Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.','InsufficientQtyAvailableForSalesColor_ID')
;

-- 2019-04-03T08:48:52.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541694,'Y',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'Geplante Lieferungen sind offene Lieferdispo-Positionen mit einem Bereitstellungsdatum das nicht zu weit in der Zukunft liegt und daher noch relevant ist.
Beispiel: wenn es üblicherweise drei Tage dauert, bis fehlende Lagerbestände ausgeglichen sind, sollte der Wert auf 72 gesetzt werden.',578360,567620,0,'de.metas.material.cockpit','Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.','Vorausschauinterval zu gepl. Lieferungen (Std)')
;

-- 2019-04-03T08:48:52.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541694,'Y',14,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,578361,567621,0,'de.metas.material.cockpit','Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.','Rückschauinterval Auftragspositionen in Bearb. (Std)')
;

-- 2019-04-03T08:48:52.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578361 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:48:52.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541694,'Y',5000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:48:52','YYYY-MM-DD HH24:MI:SS'),100,578362,567622,0,'de.metas.material.cockpit','Beschreibung')
;

-- 2019-04-03T08:48:52.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:50:45.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576552,0,'IsFeatureActivated',TO_TIMESTAMP('2019-04-03 08:50:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Feature aktivtiert','Feature aktivtiert',TO_TIMESTAMP('2019-04-03 08:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:50:45.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576552 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T08:50:48.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:50:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='de_CH'
;

-- 2019-04-03T08:50:48.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'de_CH') 
;

-- 2019-04-03T08:50:52.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 08:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='de_DE'
;

-- 2019-04-03T08:50:52.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'de_DE') 
;

-- 2019-04-03T08:50:52.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576552,'de_DE') 
;

-- 2019-04-03T08:51:04.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Feature activated', PrintName='Feature activated',Updated=TO_TIMESTAMP('2019-04-03 08:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576552 AND AD_Language='en_US'
;

-- 2019-04-03T08:51:04.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576552,'en_US') 
;

-- 2019-04-03T08:51:19.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (20,'Y',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 08:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 08:51:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541343,'N',567624,'N','Y','N','N','N','N','N','N',0,0,576552,'de.metas.material.cockpit','N','N','IsFeatureActivated','N','Feature aktivtiert')
;

-- 2019-04-03T08:51:19.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567624 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T08:51:20.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_AvailableForSales_Config','ALTER TABLE public.MD_AvailableForSales_Config ADD COLUMN IsFeatureActivated CHAR(1) DEFAULT ''Y'' CHECK (IsFeatureActivated IN (''Y'',''N'')) NOT NULL')
;

-- 2019-04-03T08:51:35.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541694,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 08:51:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-03 08:51:34','YYYY-MM-DD HH24:MI:SS'),100,578418,567624,0,'de.metas.material.cockpit','Feature aktivtiert')
;

-- 2019-04-03T08:51:35.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578418 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T08:51:51.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540614,Updated=TO_TIMESTAMP('2019-04-03 08:51:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541343
;

-- 2019-04-03T08:51:55.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2019-04-03 08:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541343
;

-- 2019-04-03T08:52:00.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-04-03 08:52:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541343
;

-- 2019-04-03T08:52:47.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540489,0,541343,TO_TIMESTAMP('2019-04-03 08:52:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Y','MD_AvailableForSales_Config_UC','N',TO_TIMESTAMP('2019-04-03 08:52:47','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-04-03T08:52:47.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540489 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-04-03T08:53:02.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,567607,540938,540489,0,TO_TIMESTAMP('2019-04-03 08:53:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y',10,TO_TIMESTAMP('2019-04-03 08:53:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:53:26.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,567608,540939,540489,0,TO_TIMESTAMP('2019-04-03 08:53:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y',20,TO_TIMESTAMP('2019-04-03 08:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:53:38.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_AvailableForSales_Config_UC ON MD_AvailableForSales_Config (AD_Client_ID,AD_Org_ID) WHERE IsActive='Y'
;

-- 2019-04-03T08:54:59.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2019-04-03 08:54:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000050
;

-- 2019-04-03T08:56:43.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,576549,541232,0,540614,TO_TIMESTAMP('2019-04-03 08:56:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','MD_AvailableForSales_Config','Y','N','N','N','N','Einstellungen zur verfügbaren Menge für den Verkauf',TO_TIMESTAMP('2019-04-03 08:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:56:43.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541232 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-04-03T08:56:43.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541232, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541232)
;

-- 2019-04-03T08:56:43.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576549) 
;

-- 2019-04-03T08:56:43.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:43.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541232 AND AD_Tree_ID=10
;

-- 2019-04-03T08:56:52.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000050, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541232 AND AD_Tree_ID=10
;

-- 2019-04-03T08:57:15.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541278
;

-- 2019-04-03T08:57:56.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541277
;

-- 2019-04-03T08:58:02.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541276
;

-- 2019-04-03T09:22:39.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (187,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 09:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,0,TO_TIMESTAMP('2019-04-03 09:22:39','YYYY-MM-DD HH24:MI:SS'),100,578420,'Y',320,310,1,1,567584,0,'de.metas.material.cockpit','Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.','Verfügbar')
;

-- 2019-04-03T09:22:39.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578420 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T09:22:45.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2019-04-03 09:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578420
;

-- 2019-04-03T09:23:50.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='kurzfr. Verfügbar', PrintName='kurzfr. Verfügbar',Updated=TO_TIMESTAMP('2019-04-03 09:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='de_CH'
;

-- 2019-04-03T09:23:50.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'de_CH') 
;

-- 2019-04-03T09:23:59.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='kurzfr. Verfügbar', PrintName='kurzfr. Verfügbar',Updated=TO_TIMESTAMP('2019-04-03 09:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='de_DE'
;

-- 2019-04-03T09:23:59.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'de_DE') 
;

-- 2019-04-03T09:23:59.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576524,'de_DE') 
;

-- 2019-04-03T09:23:59.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyAvailableForSales', Name='kurzfr. Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID=576524
;

-- 2019-04-03T09:23:59.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailableForSales', Name='kurzfr. Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL, AD_Element_ID=576524 WHERE UPPER(ColumnName)='QTYAVAILABLEFORSALES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T09:23:59.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAvailableForSales', Name='kurzfr. Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID=576524 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T09:23:59.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='kurzfr. Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576524) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576524)
;

-- 2019-04-03T09:23:59.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='kurzfr. Verfügbar', Name='kurzfr. Verfügbar' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576524)
;

-- 2019-04-03T09:23:59.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='kurzfr. Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-03T09:23:59.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='kurzfr. Verfügbar', Description='Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', Help=NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-03T09:23:59.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'kurzfr. Verfügbar', Description = 'Aktueller Lagerbestand abzüglich absehbarer Lieferungen in der Lager-Maßeinheit des jeweiligen Produktes.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576524
;

-- 2019-04-03T09:24:49.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Available at short notice', PrintName='Available at short notice',Updated=TO_TIMESTAMP('2019-04-03 09:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576524 AND AD_Language='en_US'
;

-- 2019-04-03T09:24:49.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576524,'en_US') 
;

-- 2019-04-03T09:25:04.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=622223
;

-- 2019-04-03T09:25:04.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) 
-- VALUES (0,543954,628426,563198,0,143,TO_TIMESTAMP('2019-04-03 09:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-04-03 09:25:04','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-04-03T09:25:04.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=628426
;

-- 2019-04-03T09:25:04.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543954,628427,563198,0,143,TO_TIMESTAMP('2019-04-03 09:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-04-03 09:25:04','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-04-03T09:25:51.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (187,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-03 09:25:51','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-04-03 09:25:51','YYYY-MM-DD HH24:MI:SS'),100,578421,'Y',330,320,1,1,567583,0,'U','Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.','InsufficientQtyAvailableForSalesColor_ID')
;

-- 2019-04-03T09:25:51.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578421 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T09:26:13.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.material.cockpit',Updated=TO_TIMESTAMP('2019-04-03 09:26:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578421
;

-- 2019-04-03T09:26:51.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_Order_Sales',Updated=TO_TIMESTAMP('2019-04-03 09:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=143
;

-- 2019-04-03T09:30:19.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2019-04-03 09:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578421
;

-- 2019-04-03T09:32:12.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_OrderLine_QtyEntered',Updated=TO_TIMESTAMP('2019-04-03 09:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000037
;

-- 2019-04-03T10:00:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,578421,0,187,1000005,558190,'F',TO_TIMESTAMP('2019-04-03 10:00:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'InsufficientQtyAvailableForSalesColor_ID',63,0,0,TO_TIMESTAMP('2019-04-03 10:00:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T10:01:25.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,578420,0,187,1000005,558191,'F',TO_TIMESTAMP('2019-04-03 10:01:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_OrderLine_QtyAvailableForSales',62,0,0,TO_TIMESTAMP('2019-04-03 10:01:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T10:02:04.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558191
;

-- 2019-04-03T10:02:04.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558190
;

-- 2019-04-03T10:02:04.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000040
;

-- 2019-04-03T10:02:04.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000041
;

-- 2019-04-03T10:02:04.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000042
;

-- 2019-04-03T10:02:04.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000043
;

-- 2019-04-03T10:02:04.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000044
;

-- 2019-04-03T10:02:04.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000045
;

-- 2019-04-03T10:02:04.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552431
;

-- 2019-04-03T10:02:04.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552430
;

-- 2019-04-03T10:02:04.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000046
;

-- 2019-04-03T10:02:04.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000047
;

-- 2019-04-03T10:02:04.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549097
;

-- 2019-04-03T10:02:04.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000039
;

-- 2019-04-03T10:02:04.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549127
;

-- 2019-04-03T10:02:04.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549126
;

-- 2019-04-03T10:02:04.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549129
;

-- 2019-04-03T10:02:04.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549130
;

-- 2019-04-03T10:02:04.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549128
;

-- 2019-04-03T10:02:04.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550143
;

-- 2019-04-03T10:02:04.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552486
;

-- 2019-04-03T10:02:04.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551414
;

-- 2019-04-03T10:02:04.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2019-04-03 10:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547355
;


CREATE INDEX IF NOT EXISTS c_orderline_updated_product ON public.c_orderline (Updated DESC, M_Product_ID);
CREATE INDEX IF NOT EXISTS m_shipmentSchedule_PreparationDate_product ON public.m_shipmentSchedule (PreparationDate DESC, M_Product_ID);
CREATE INDEX IF NOT EXISTS md_stock_product ON public.md_stock (M_Product_ID);

DROP VIEW IF EXISTS de_metas_material.MD_ShipmentQty_V;
CREATE VIEW de_metas_material.MD_ShipmentQty_V AS
SELECT 
  sq.QtyToBeShipped, 
  p.C_UOM_ID,
  p.M_Product_ID,
  sq.AttributesKey,
  sq.PreparationDate,
  sq.SalesOrderLastUpdated
FROM
  M_Product p
  JOIN LATERAL (
    select 
        COALESCE(s.QtyToDeliver, ol.QtyOrdered) AS QtyToBeShipped,
        /* we expect relatively few rows to create the AttributesKey for, 
           because this view is invoked only for a certain SalesOrderLastUpdated /PreparationDate range */
        GenerateHUStorageASIKey( COALESCE(s.M_AttributeSetInstance_ID, ol.M_AttributeSetInstance_ID), '-1002'/*NONE*/) AS AttributesKey,
        COALESCE(s_PreparationDate, o_PreparationDate) AS PreparationDate,
        ol.Updated AS SalesOrderLastUpdated
    from 
    ( 
        select ol.C_OrderLine_ID, 
            ol.M_AttributeSetInstance_ID, 
            ol.QtyOrdered, 
            o.PreparationDate AS o_PreparationDate, 
            ol.Updated
        from C_OrderLine ol
        join C_Order o ON o.C_Order_ID=ol.C_Order_ID
        where o.IsSOTrx='Y' 
            AND ol.Processed='N'
            AND ol.IsActive='Y' AND o.IsActive='Y'
            AND ol.M_Product_ID = p.M_Product_ID
    ) ol
    FULL OUTER JOIN (
        select s.C_OrderLine_ID, 
            s.M_AttributeSetInstance_ID, 
            s.PreparationDate AS s_PreparationDate, 
            s.QtyToDeliver
        from M_ShipmentSchedule s
        where s.Processed='N'
            AND s.IsActive='Y'
            AND s.M_Product_ID = p.M_Product_ID
    ) s ON s.C_OrderLine_ID=ol.C_OrderLine_ID
  ) sq ON true
WHERE true;
COMMENT ON VIEW de_metas_material.MD_ShipmentQty_V IS
'View to be used in the DB function de_metas_material.retrieve_available_for_sales.
See https://github.com/metasfresh/metasfresh/issues/5108';
-----------------------------------------

DROP FUNCTION IF EXISTS de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer);
CREATE FUNCTION de_metas_material.retrieve_available_for_sales(
  IN p_QueryNo integer,
  IN p_M_Product_ID numeric, 
  IN p_StorageAttributesKey character varying, 
  IN p_PreparationDate timestamptz,
  IN p_shipmentDateLookAheadHours integer,
  IN p_salesOrderLookBehindHours integer)
RETURNS TABLE
( QueryNo integer,
  M_Product_ID numeric,
  StorageAttributesKey character varying,
  QtyToBeShipped numeric,
  QtyOnHandStock numeric,
  C_UOM_ID numeric,
  SalesOrderLastUpdated timestamptz, 
  ShipmentPreparationDate timestamptz
)
AS
$BODY$
  SELECT p_QueryNo, p_M_Product_ID, p_StorageAttributesKey, QtyToBeShipped, QtyOnHandStock, stock.C_UOM_ID, SalesOrderLastUpdated, ShipmentPreparationDate
  FROM
  (
    SELECT SUM(QtyToBeShipped) AS QtyToBeShipped, C_UOM_ID, SalesOrderLastUpdated, PreparationDate AS ShipmentPreparationDate
    FROM de_metas_material.MD_ShipmentQty_V sq
    WHERE sq.M_Product_ID = p_M_Product_ID
      AND sq.AttributesKey ILIKE '%' || p_StorageAttributesKey || '%'
      AND sq.PreparationDate <= p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL /*max. future PreparationDatses we still care for*/
      AND COALESCE(sq.SalesOrderLastUpdated, p_PreparationDate) >= p_PreparationDate - (p_salesOrderLookBehindHours || ' hours')::INTERVAL /*min updated value that is not yet too old for us to care */
    GROUP BY C_UOM_ID, SalesOrderLastUpdated, PreparationDate
  ) sales
  FULL OUTER JOIN (
    SELECT SUM(QtyOnHand) AS QtyOnHandStock, p.C_UOM_ID
    FROM MD_Stock s
      JOIN M_Product p ON p.M_Product_ID=s.M_Product_ID
    WHERE s.M_Product_ID = p_M_Product_ID
      AND s.AttributesKey ILIKE '%' || p_StorageAttributesKey || '%'
      AND s.IsActive='Y'
    GROUP BY C_UOM_ID
  ) stock ON TRUE
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer) IS
'Returns the current stock and the shipments to be expected in the nearest future.
Parameters:
* p_QueryNo: the given value is returend in the QueryNo result column; used by the function invoker in the context of select .. union.
* p_M_Product_ID
* p_StorageAttributesKey: the function will return rows whose attributesKey is a substring of this parameter. -1002 means "no storage attributes".
* p_PreparationDate: the date in question returned rows have a ShipmentPreparationDate and SalesOrderLastUpdated within a certain range around this parameter''s value.
* p_shipmentDateLookAheadHours: returned rows have a ShipmentPreparationDate not after p_PreparationDate plus the given number of hours.
* p_salesOrderLookBehindHours: returned rows have a SalesOrderLastUpdated date not before p_PreparationDate minus the given number of hours.

Also see https://github.com/metasfresh/metasfresh/issues/5108'
;

-- 2019-04-03T11:54:31.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576553,0,'QueryNo',TO_TIMESTAMP('2019-04-03 11:54:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','QueryNo','QueryNo',TO_TIMESTAMP('2019-04-03 11:54:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T11:54:31.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576553 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T11:54:46.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567625,576553,0,11,541340,'QueryNo',TO_TIMESTAMP('2019-04-03 11:54:46','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.material.cockpit',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','QueryNo',0,0,TO_TIMESTAMP('2019-04-03 11:54:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-03T11:54:46.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567625 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T11:55:19.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='There is no real table or view in the DB; This AD_Table is used in metasfresh to access the rows returned by the DB function de_metas_material.retrieve_available_for_sales',Updated=TO_TIMESTAMP('2019-04-03 11:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541340
;

-- 2019-04-03T11:56:30.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The DB function de_metas_material.retrieve_available_for_sales will return the QueryNo that was given as a function parameter.
This allows us to select unions and assigne the returned rows to their respective single logical queries',Updated=TO_TIMESTAMP('2019-04-03 11:56:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567625
;

-- 2019-04-03T20:30:22.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-04-03 20:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558190
;

