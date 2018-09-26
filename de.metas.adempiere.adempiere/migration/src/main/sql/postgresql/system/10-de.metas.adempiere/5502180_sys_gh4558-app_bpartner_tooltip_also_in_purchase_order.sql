-- 2018-09-25T15:11:35.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Org_ID,AD_Table_ID,Name,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation) VALUES (0,'4',0,100,'Y',TO_TIMESTAMP('2018-09-25 15:11:35','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-09-25 15:11:35','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N',0,541143,'AD_Attachment_MultiRef','U','AD_Attachment_MultiRef','NP','N')
;

-- 2018-09-25T15:11:35.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=541143 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-09-25T15:11:35.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2018-09-25 15:11:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2018-09-25 15:11:35','YYYY-MM-DD HH24:MI:SS'),100,554726,'Table AD_Attachment_MultiRef',0,'AD_Attachment_MultiRef',0)
;

-- 2018-09-25T15:11:42.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='AD_Attachment',Updated=TO_TIMESTAMP('2018-09-25 15:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=254
;

-- 2018-09-25T15:12:20.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=128, AccessLevel='6', EntityType='D',Updated=TO_TIMESTAMP('2018-09-25 15:12:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541143
;

-- 2018-09-25T15:12:41.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','Y','N',TO_TIMESTAMP('2018-09-25 15:12:40','YYYY-MM-DD HH24:MI:SS'),100,541143,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',563208,'Y','Mandant für diese Installation.',0,'Mandant',102,'D')
;

-- 2018-09-25T15:12:41.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563208 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y','N','Y','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',563209,'Y','Organisatorische Einheit des Mandanten',0,'Sektion',113,'D')
;

-- 2018-09-25T15:12:41.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563209 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Created',563210,'Y','Datum, an dem dieser Eintrag erstellt wurde',0,'Erstellt',245,'D')
;

-- 2018-09-25T15:12:41.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563210 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,'CreatedBy',563211,'Y','Nutzer, der diesen Eintrag erstellt hat',0,'Erstellt durch',246,'D')
;

-- 2018-09-25T15:12:41.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563211 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','IsActive',563212,'Y','Der Eintrag ist im System aktiv',0,'Aktiv',348,'D')
;

-- 2018-09-25T15:12:41.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563212 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Updated',563213,'Y','Datum, an dem dieser Eintrag aktualisiert wurde',0,'Aktualisiert',607,'D')
;

-- 2018-09-25T15:12:41.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,'UpdatedBy',563214,'Y','Nutzer, der diesen Eintrag aktualisiert hat',0,'Aktualisiert durch',608,'D')
;

-- 2018-09-25T15:12:41.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563214 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:12:41.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'AD_Attachment_MultiRef',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,544430,0,'AD_Attachment_MultiRef','AD_Attachment_MultiRef_ID','D')
;

-- 2018-09-25T15:12:41.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544430 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-25T15:12:41.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N',TO_TIMESTAMP('2018-09-25 15:12:41','YYYY-MM-DD HH24:MI:SS'),100,541143,'AD_Attachment_MultiRef_ID',563215,'Y',0,'AD_Attachment_MultiRef',544430,'D')
;

-- 2018-09-25T15:12:41.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563215 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:13:20.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:13:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-25 15:13:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541143,'N','Eine "Anlage" kann jeder Dokumenten-/Datei-Typ sein und jedem Eintrag im System zugeordnet werden.','AD_Attachment_ID',563216,'N','Y','N','N','N','N','N','N','Anlage zum Eintrag',0,0,'Anlage',101,'D','N','N')
;

-- 2018-09-25T15:13:20.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563216 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:14:20.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (28,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:14:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-25 15:14:20','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541143,'N','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Record_ID',563217,'N','N','N','N','N','N','N','N','Direct internal record ID',0,0,'Datensatz-ID',538,'D','N','N')
;

-- 2018-09-25T15:14:20.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563217 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:14:34.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-25 15:14:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-25 15:14:34','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541143,'N','The Database Table provides the information of the table definition','AD_Table_ID',563218,'N','N','N','N','N','N','N','N','Database Table information',0,0,'DB-Tabelle',126,'D','N','N')
;

-- 2018-09-25T15:14:34.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563218 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-25T15:14:36.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-09-25 15:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563218
;

-- 2018-09-25T15:14:42.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-09-25 15:14:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563217
;

-- 2018-09-25T15:15:02.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.AD_Attachment_MultiRef (AD_Attachment_ID NUMERIC(10) NOT NULL, AD_Attachment_MultiRef_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Record_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADAttachment_ADAttachmentMultiRef FOREIGN KEY (AD_Attachment_ID) REFERENCES public.AD_Attachment DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_Attachment_MultiRef_Key PRIMARY KEY (AD_Attachment_MultiRef_ID), CONSTRAINT ADTable_ADAttachmentMultiRef FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED)
;

-- 2018-09-25T16:25:13.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-09-25 16:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563217
;

-- 2018-09-25T16:25:14.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachment_multiref','Record_ID','NUMERIC(10)',null,null)
;

-- 2018-09-25T16:25:14.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachment_multiref','Record_ID',null,'NULL',null)
;

-- 2018-09-25T16:25:22.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-09-25 16:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563218
;

-- 2018-09-25T16:25:25.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachment_multiref','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2018-09-25T16:25:25.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachment_multiref','AD_Table_ID',null,'NULL',null)
;

-- 2018-09-26T08:58:46.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-09-26 08:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557035
;

-- 2018-09-26T08:58:47.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','Record_ID','NUMERIC(10)',null,null)
;

-- 2018-09-26T08:58:47.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','Record_ID',null,'NULL',null)
;

-- 2018-09-26T08:58:57.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-09-26 08:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557030
;

-- 2018-09-26T08:58:58.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2018-09-26T08:58:58.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_attachmententry','AD_Table_ID',null,'NULL',null)
;

-- 2018-09-26T11:19:44.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (294,'N',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-09-26 11:19:44','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2018-09-26 11:19:44','YYYY-MM-DD HH24:MI:SS'),100,569148,'Y',550,180,1,1,560875,'Memo Text',0,'C_BPartner_Memo','U')
;

-- 2018-09-26T11:19:44.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-26T11:21:23.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (294,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-09-26 11:21:23','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2018-09-26 11:21:23','YYYY-MM-DD HH24:MI:SS'),100,569149,'Y',560,190,1,1,560876,0,'Rechnungspartner-Memo','D')
;

-- 2018-09-26T11:21:23.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-26T11:21:39.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-09-26 11:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569148
;

-- 2018-09-26T11:22:13.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (294,'N',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-09-26 11:22:13','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2018-09-26 11:22:13','YYYY-MM-DD HH24:MI:SS'),100,569150,'N',570,200,1,1,560877,0,'Lieferempfänger-Memo','D')
;

-- 2018-09-26T11:22:13.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-26T11:22:38.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-09-26 11:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569148
;

-- 2018-09-26T11:22:50.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-09-26 11:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569149
;

-- 2018-09-26T11:23:29.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type,TooltipIconName) VALUES (TO_TIMESTAMP('2018-09-26 11:23:28','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2018-09-26 11:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',540287,541279,569148,30,0,'tooltip','text')
;

-- 2018-09-26T11:24:09.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type,TooltipIconName) VALUES (TO_TIMESTAMP('2018-09-26 11:24:09','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2018-09-26 11:24:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',540288,541280,569149,30,0,'tooltip','text')
;

-- 2018-09-26T11:24:39.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type,TooltipIconName) VALUES (TO_TIMESTAMP('2018-09-26 11:24:39','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2018-09-26 11:24:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',540289,547190,569150,30,0,'tooltip','text')
;

