-- 2018-01-09T13:16:54.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,AD_Client_ID,CreatedBy,IsActive,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,TableName,IsAutocomplete,IsDLM,AD_Org_ID,Name,UpdatedBy,AD_Table_ID,EntityType,LoadSeq,Created,ACTriggerLength,Updated) VALUES ('3',0,100,'Y','N','Y','Y','N','N','N','L','N','GO_DeliveryOrder_Package','N','N',0,'GO Delivery Order Package',100,540894,'de.metas.shipper.gateway.go',0,TO_TIMESTAMP('2018-01-09 13:16:54','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-09 13:16:54','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:16:54.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540894 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-01-09T13:16:54.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (IsAudited,StartNewYear,IsActive,IsTableID,CreatedBy,IsAutoSequence,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID,UpdatedBy,CurrentNext,Created,StartNo,IncrementNo,CurrentNextSys,Updated) VALUES ('N','N','Y','Y',100,'Y',554470,'Table GO_DeliveryOrder_Package',0,'GO_DeliveryOrder_Package',0,100,1000000,TO_TIMESTAMP('2018-01-09 13:16:54','YYYY-MM-DD HH24:MI:SS'),1000000,1,50000,TO_TIMESTAMP('2018-01-09 13:16:54','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,102,'N','N','Y','N','Y','N',540894,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',558522,'Y','Mandant für diese Installation.',0,100,'Mandant','de.metas.shipper.gateway.go',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558522 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,113,'N','Y','Y','N','Y','N',540894,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',558523,'Y','Organisatorische Einheit des Mandanten',0,100,'Sektion','de.metas.shipper.gateway.go',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558523 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (16,'N','N','N','N',0,'Y',100,245,'N','N','Y','N','N','N',540894,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Created',558524,'Y','Datum, an dem dieser Eintrag erstellt wurde',0,100,'Erstellt','de.metas.shipper.gateway.go',29,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558524 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (18,'N','N','N','N',0,'Y',100,246,'N','N','Y','N','N','N',540894,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,'CreatedBy',558525,'Y','Nutzer, der diesen Eintrag erstellt hat',0,100,'Erstellt durch','de.metas.shipper.gateway.go',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558525 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (20,'N','N','N','N',0,'Y',100,348,'Y','N','Y','N','Y','N',540894,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','IsActive',558526,'Y','Der Eintrag ist im System aktiv',0,100,'Aktiv','de.metas.shipper.gateway.go',1,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558526 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (16,'N','N','N','N',0,'Y',100,607,'N','N','Y','N','N','N',540894,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Updated',558527,'Y','Datum, an dem dieser Eintrag aktualisiert wurde',0,100,'Aktualisiert','de.metas.shipper.gateway.go',29,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558527 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (18,'N','N','N','N',0,'Y',100,608,'N','N','Y','N','N','N',540894,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,'UpdatedBy',558528,'Y','Nutzer, der diesen Eintrag aktualisiert hat',0,100,'Aktualisiert durch','de.metas.shipper.gateway.go',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558528 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:01.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,ColumnName,AD_Element_ID,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'GO Delivery Order Package','GO_DeliveryOrder_Package_ID',543746,0,'GO Delivery Order Package','de.metas.shipper.gateway.go',100,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543746 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-09T13:17:01.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,SeqNo,Created,Updated) VALUES (13,'Y','N','N','N',0,'Y',100,543746,'N','N','Y','N','N',540894,'GO_DeliveryOrder_Package_ID',558529,'Y',0,100,'GO Delivery Order Package','de.metas.shipper.gateway.go',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-09 13:17:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:01.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558529 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:24.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','Y','N','N',0,'Y',100,543710,'N','N','N','N','N','N','N','Y','N','N','N',540894,'N','N','GO_DeliveryOrder_ID','N',558530,'N','Y','N','N','N','N',0,100,'GO Delivery Order','de.metas.shipper.gateway.go','N',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:23','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-09 13:17:23','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:24.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558530 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:17:41.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','Y','N','N',0,'Y',100,2410,'N','N','N','N','N','N','N','Y','N','N','N',540894,'N','N','A Shipment can have one or more Packages.  A Package may be individually tracked.','M_Package_ID','N',558531,'N','Y','N','N','N','N','Shipment Package',0,100,'Packstück','de.metas.shipper.gateway.go','N',10,0,0,TO_TIMESTAMP('2018-01-09 13:17:41','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-09 13:17:41','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-09T13:17:41.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558531 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-09T13:18:09.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.GO_DeliveryOrder_Package (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, GO_DeliveryOrder_ID NUMERIC(10) NOT NULL, GO_DeliveryOrder_Package_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Package_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT GODeliveryOrder_GODeliveryOrde FOREIGN KEY (GO_DeliveryOrder_ID) REFERENCES public.GO_DeliveryOrder DEFERRABLE INITIALLY DEFERRED, CONSTRAINT GO_DeliveryOrder_Package_Key PRIMARY KEY (GO_DeliveryOrder_Package_ID), CONSTRAINT MPackage_GODeliveryOrderPackag FOREIGN KEY (M_Package_ID) REFERENCES public.M_Package DEFERRABLE INITIALLY DEFERRED)
;

