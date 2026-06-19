-- 2018-03-28T10:22:40.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,AD_Client_ID,CreatedBy,IsActive,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,EntityType,CopyColumnsFromTable,TableName,IsAutocomplete,IsDLM,AD_Org_ID,Name,UpdatedBy,AD_Table_ID,LoadSeq,Created,ACTriggerLength,Updated) VALUES ('3',0,100,'Y','N','Y','N','N','N','Y','L','de.metas.vertical.pharma.msv3.server','N','MSV3_Server_Product_Category','N','N',0,'MSV3 Product_Category',100,540957,0,TO_TIMESTAMP('2018-03-28 10:22:40','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-03-28 10:22:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:40.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540957 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-03-28T10:22:40.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (IsAudited,StartNewYear,IsActive,IsTableID,CreatedBy,IsAutoSequence,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID,UpdatedBy,CurrentNext,Created,StartNo,IncrementNo,CurrentNextSys,Updated) VALUES ('N','N','Y','Y',100,'Y',554521,'Table MSV3_Server_Product_Category',0,'MSV3_Server_Product_Category',0,100,1000000,TO_TIMESTAMP('2018-03-28 10:22:40','YYYY-MM-DD HH24:MI:SS'),1000000,1,50000,TO_TIMESTAMP('2018-03-28 10:22:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:46.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,102,'N','N','Y','N','Y','N',540957,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',559609,'Y','Mandant für diese Installation.',0,100,'Mandant','de.metas.vertical.pharma.msv3.server',10,0,0,TO_TIMESTAMP('2018-03-28 10:22:46','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:46','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:46.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559609 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:46.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,113,'N','Y','Y','N','Y','N',540957,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',559610,'Y','Organisatorische Einheit des Mandanten',0,100,'Sektion','de.metas.vertical.pharma.msv3.server',10,0,0,TO_TIMESTAMP('2018-03-28 10:22:46','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:46','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:46.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559610 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:47.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (16,'N','N','N','N',0,'Y',100,245,'N','N','Y','N','N','N',540957,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Created',559611,'Y','Datum, an dem dieser Eintrag erstellt wurde',0,100,'Erstellt','de.metas.vertical.pharma.msv3.server',29,0,0,TO_TIMESTAMP('2018-03-28 10:22:46','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:46','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559611 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:47.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (18,'N','N','N','N',0,'Y',100,246,'N','N','Y','N','N','N',540957,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,'CreatedBy',559612,'Y','Nutzer, der diesen Eintrag erstellt hat',0,100,'Erstellt durch','de.metas.vertical.pharma.msv3.server',10,0,0,TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559612 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:47.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (20,'N','N','N','N',0,'Y',100,348,'Y','N','Y','N','Y','N',540957,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','IsActive',559613,'Y','Der Eintrag ist im System aktiv',0,100,'Aktiv','de.metas.vertical.pharma.msv3.server',1,0,0,TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559613 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:47.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (16,'N','N','N','N',0,'Y',100,607,'N','N','Y','N','N','N',540957,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Updated',559614,'Y','Datum, an dem dieser Eintrag aktualisiert wurde',0,100,'Aktualisiert','de.metas.vertical.pharma.msv3.server',29,0,0,TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559614 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:47.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (18,'N','N','N','N',0,'Y',100,608,'N','N','Y','N','N','N',540957,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,'UpdatedBy',559615,'Y','Nutzer, der diesen Eintrag aktualisiert hat',0,100,'Aktualisiert durch','de.metas.vertical.pharma.msv3.server',10,0,0,TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559615 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:22:47.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'MSV3 Product_Category','de.metas.vertical.pharma.msv3.server','MSV3_Server_Product_Category_ID',543945,0,'MSV3 Product_Category',100,TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543945 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-03-28T10:22:47.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (13,'Y','N','N','N',0,'Y',100,543945,'N','N','Y','N','N',540957,'MSV3_Server_Product_Category_ID',559616,'Y',0,100,'MSV3 Product_Category','de.metas.vertical.pharma.msv3.server',10,0,0,TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:22:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:22:47.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559616 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:23:08.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (19,'N','N','N','N',0,'Y',100,453,'Y','N','N','N','N','N','N','N','Y','N','N','N',540957,'N','N','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','M_Product_Category_ID',559617,'N','Y','N','N','N','N','Kategorie eines Produktes',0,100,'Produkt Kategorie','de.metas.vertical.pharma.msv3.server','N',10,0,0,TO_TIMESTAMP('2018-03-28 10:23:08','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-03-28 10:23:08','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-03-28T10:23:08.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559617 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T10:23:39.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,IsActive,CreatedBy,WindowType,Processing,IsSOTrx,EntityType,IsBetaFunctionality,IsEnableRemoteCacheInvalidation,IsDefault,IsOneInstanceOnly,AD_Window_ID,AD_Org_ID,Name,UpdatedBy,Updated,Created,WinHeight,WinWidth) VALUES (0,'Y',100,'M','N','Y','de.metas.vertical.pharma.msv3.server','N','N','N','N',540425,0,'MSV3 Server Products',100,TO_TIMESTAMP('2018-03-28 10:23:39','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:23:39','YYYY-MM-DD HH24:MI:SS'),0,0)
;

-- 2018-03-28T10:23:39.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540425 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2018-03-28T10:23:50.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540425,Updated=TO_TIMESTAMP('2018-03-28 10:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540957
;

-- 2018-03-28T10:23:58.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.MSV3_Server_Product_Category (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_Category_ID NUMERIC(10) NOT NULL, MSV3_Server_Product_Category_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MProductCategory_MSV3ServerProductCategory FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MSV3_Server_Product_Category_Key PRIMARY KEY (MSV3_Server_Product_Category_ID))
;

-- 2018-03-28T10:24:29.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (HasTree,AD_Window_ID,IsSingleRow,AD_Client_ID,IsActive,CreatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,EntityType,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,AD_Org_ID,Name,UpdatedBy,Created,SeqNo,TabLevel,MaxQueryRecords,Updated) VALUES ('N',540425,'N',0,'Y',100,'N','N','N','N','N','N','de.metas.vertical.pharma.msv3.server','Y','N','N','Y','Y','Y','N',540957,541059,'N','Y',0,'Product categories',100,TO_TIMESTAMP('2018-03-28 10:24:29','YYYY-MM-DD HH24:MI:SS'),10,0,0,TO_TIMESTAMP('2018-03-28 10:24:29','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:24:29.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=541059 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-03-28T10:24:44.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541059,'Y','N','N','N','N',0,'Y',100,'N','de.metas.vertical.pharma.msv3.server','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',563153,559609,'Mandant für diese Installation.',0,'Mandant',100,10,TO_TIMESTAMP('2018-03-28 10:24:44','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:24:44','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:24:44.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-28T10:24:45.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541059,'Y','N','N','N','N',0,'Y',100,'N','de.metas.vertical.pharma.msv3.server','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',563154,559610,'Organisatorische Einheit des Mandanten',0,'Sektion',100,10,TO_TIMESTAMP('2018-03-28 10:24:44','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:24:44','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:24:45.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-28T10:24:45.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541059,'Y','N','N','N','N',0,'Y',100,'N','de.metas.vertical.pharma.msv3.server','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',563155,559613,'Der Eintrag ist im System aktiv',0,'Aktiv',100,1,TO_TIMESTAMP('2018-03-28 10:24:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:24:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:24:45.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-28T10:24:45.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541059,'N','N','N','N','N',0,'Y',100,'N','de.metas.vertical.pharma.msv3.server',563156,'N',559616,0,'MSV3 Product_Category',100,10,TO_TIMESTAMP('2018-03-28 10:24:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:24:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:24:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-28T10:24:45.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541059,'Y','N','N','N','N',0,'Y',100,'N','de.metas.vertical.pharma.msv3.server','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',563157,559617,'Kategorie eines Produktes',0,'Produkt Kategorie',100,10,TO_TIMESTAMP('2018-03-28 10:24:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:24:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:24:45.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-03-28T10:24:56.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-03-28 10:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563153
;

-- 2018-03-28T10:24:56.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-03-28 10:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563154
;

-- 2018-03-28T10:24:56.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2018-03-28 10:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563157
;

-- 2018-03-28T10:24:56.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-03-28 10:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563155
;

-- 2018-03-28T10:25:01.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-03-28 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563153
;

-- 2018-03-28T10:25:01.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-03-28 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563154
;

-- 2018-03-28T10:25:01.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-03-28 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563155
;

-- 2018-03-28T10:25:01.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-03-28 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563156
;

-- 2018-03-28T10:25:01.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-03-28 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563157
;

-- 2018-03-28T10:25:38.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (AD_Window_ID,Action,AD_Client_ID,IsActive,CreatedBy,IsSummary,IsSOTrx,IsReadOnly,EntityType,AD_Menu_ID,IsCreateNew,InternalName,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (540425,'W',0,'Y',100,'N','N','N','de.metas.vertical.pharma.msv3.server',541067,'N','MSV3_Server_Product_Category',0,'MSV3 Server Products',100,TO_TIMESTAMP('2018-03-28 10:25:38','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 10:25:38','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T10:25:38.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541067 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-03-28T10:25:38.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541067, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541067)
;

-- 2018-03-28T10:25:39.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541065, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541063 AND AD_Tree_ID=10
;

-- 2018-03-28T10:25:39.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541065, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541066 AND AD_Tree_ID=10
;

-- 2018-03-28T10:25:39.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541065, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541067 AND AD_Tree_ID=10
;

