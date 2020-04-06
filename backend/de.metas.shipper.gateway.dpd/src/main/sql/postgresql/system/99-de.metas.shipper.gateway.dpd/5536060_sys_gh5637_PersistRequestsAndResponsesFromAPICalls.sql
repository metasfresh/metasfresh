-- 2019-11-11T11:52:13.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Table_ID,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation,AD_Org_ID,EntityType,Name) VALUES (0,'7',0,100,'Y',TO_TIMESTAMP('2019-11-11 13:52:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2019-11-11 13:52:13','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N',541439,'DPD_StoreOrder_Log','NP','N',0,'de.metas.shipper.gateway.dpd','Dpd StoreOrder Log')
;

-- 2019-11-11T11:52:13.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541439 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-11-11T11:52:13.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,Description,AD_Org_ID) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2019-11-11 13:52:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2019-11-11 13:52:13','YYYY-MM-DD HH24:MI:SS'),100,555081,0,'DPD_StoreOrder_Log','Table DPD_StoreOrder_Log',0)
;

-- 2019-11-11T11:52:37.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','Y','N',TO_TIMESTAMP('2019-11-11 13:52:37','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',569560,'Y',102,'AD_Client_ID','N','Mandant',0,'Mandant für diese Installation.','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:37.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569560 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:37.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2019-11-11T11:52:37.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N','Y','N',TO_TIMESTAMP('2019-11-11 13:52:37','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',569561,'Y',113,'AD_Org_ID','N','Sektion',0,'Organisatorische Einheit des Mandanten','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:37.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569561 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:37.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2019-11-11T11:52:38.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',569562,'Y',245,'Created','N','Erstellt',0,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:38.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:38.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2019-11-11T11:52:38.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,569563,'Y',246,'CreatedBy','N','Erstellt durch',0,'Nutzer, der diesen Eintrag erstellt hat','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:38.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:38.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2019-11-11T11:52:38.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',569564,'Y',348,'IsActive','N','Aktiv',0,'Der Eintrag ist im System aktiv','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:38.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:38.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2019-11-11T11:52:38.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2019-11-11 13:52:38','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',569565,'Y',607,'Updated','N','Aktualisiert',0,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:38.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:38.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-11-11T11:52:39.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2019-11-11 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,541439,'N','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,569566,'Y',608,'UpdatedBy','N','Aktualisiert durch',0,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:39.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:39.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2019-11-11T11:52:39.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-11 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2019-11-11 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,577340,'DPD_StoreOrder_Log_ID','Dpd StoreOrder Log','Dpd StoreOrder Log','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:39.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577340 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-11T11:52:39.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,AD_Column_ID,IsMandatory,AD_Element_ID,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N',TO_TIMESTAMP('2019-11-11 13:52:39','YYYY-MM-DD HH24:MI:SS'),100,541439,'N',569567,'Y',577340,'DPD_StoreOrder_Log_ID','N','Dpd StoreOrder Log',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:52:39.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:52:39.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577340) 
;

-- 2019-11-11T11:53:15.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (10,2048,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:53:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N',569568,'N','N','N','N','N','N','N','N',0,577229,'N','N','ConfigSummary','N','Config Summary',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:53:15.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569568 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:53:15.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577229) 
;

-- 2019-11-11T11:53:52.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:53:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:53:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N',569569,'N','N','N','N','N','N','N','N',0,577258,'N','N','DPD_StoreOrder_ID','N','DPD StoreOrder',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:53:52.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:53:52.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577258) 
;

-- 2019-11-11T11:54:10.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (11,'0',14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:54:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:54:10','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N',569570,'N','Y','N','N','N','N','N','N',0,543760,'N','N','DurationMillis','N','Duration (ms)',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:54:10.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:54:10.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543760) 
;

-- 2019-11-11T11:54:27.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:54:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N',569571,'N','Y','N','N','N','N','N','N',0,2395,'N','N','IsError','N','Fehler',0,'Ein Fehler ist bei der Durchführung aufgetreten','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:54:27.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:54:27.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2395) 
;

-- 2019-11-11T11:54:45.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (36,4000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:54:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:54:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N',569572,'N','N','N','N','N','N','N','N',0,543758,'N','N','RequestMessage','N','Request Message',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:54:45.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:54:45.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543758) 
;

-- 2019-11-11T11:55:08.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (36,4000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:55:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:55:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N',569573,'N','N','N','N','N','N','N','N',0,543759,'N','N','ResponseMessage','N','Response Message',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:55:08.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569573 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:55:08.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543759) 
;

-- 2019-11-11T11:55:43.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-11 13:55:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-11 13:55:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541439,'N','System Issues are created to speed up the resolution of any system related issues (potential bugs).  If enabled, they are automatically reported to Adempiere.  No data or confidential information is transferred.',569574,'N','N','N','N','N','N','N','N',0,2887,'N','N','AD_Issue_ID','N','System-Problem',0,'Automatically created or manually entered System Issue','de.metas.shipper.gateway.dpd')
;

-- 2019-11-11T11:55:43.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-11T11:55:43.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2887) 
;

-- 2019-11-11T11:56:09.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DPD_StoreOrder_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, ConfigSummary VARCHAR(2048), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DPD_StoreOrder_ID NUMERIC(10), DPD_StoreOrder_Log_ID NUMERIC(10) NOT NULL, DurationMillis NUMERIC(10) DEFAULT 0 NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL, RequestMessage TEXT, ResponseMessage TEXT, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADIssue_DPDStoreOrderLog FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED, CONSTRAINT DPDStoreOrder_DPDStoreOrderLog FOREIGN KEY (DPD_StoreOrder_ID) REFERENCES public.DPD_StoreOrder DEFERRABLE INITIALLY DEFERRED, CONSTRAINT DPD_StoreOrder_Log_Key PRIMARY KEY (DPD_StoreOrder_Log_ID))
;

