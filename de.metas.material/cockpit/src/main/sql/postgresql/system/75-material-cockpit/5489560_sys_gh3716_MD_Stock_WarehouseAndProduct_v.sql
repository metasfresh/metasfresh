-- 2018-03-28T12:38:28.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,AD_Client_ID,CreatedBy,IsActive,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,EntityType,CopyColumnsFromTable,TableName,IsAutocomplete,IsDLM,Description,AD_Org_ID,Name,UpdatedBy,AD_Table_ID,LoadSeq,Created,ACTriggerLength,Updated) VALUES ('3',0,100,'Y','N','N','Y','Y','N','N','L','de.metas.material.cockpit','N','MD_Stock_WarehouseAndProduct_v','N','N','',0,'MD_Stock_WarehouseAndProduct_v',100,540958,0,TO_TIMESTAMP('2018-03-28 12:38:28','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-03-28 12:38:28','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:38:28.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540958 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-03-28T12:39:47.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,453,'N','N','N','Y','N',540958,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','M_Product_Category_ID',559618,'N','Kategorie eines Produktes',0,100,'Produkt Kategorie','de.metas.material.cockpit',10,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559618 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:47.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,454,'N','N','N','Y','N',540958,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','M_Product_ID',559619,'N','Produkt, Leistung, Artikel',0,100,'Produkt','de.metas.material.cockpit',10,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559619 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:47.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,1675,'N','N','N','Y','N',540958,'ProductValue',559620,'N','Schlüssel des Produktes',0,100,'Produktschlüssel','de.metas.material.cockpit',40,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559620 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:47.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,459,'N','N','N','Y','N',540958,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','M_Warehouse_ID',559621,'N','Lager oder Ort für Dienstleistung',0,100,'Lager','de.metas.material.cockpit',10,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559621 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:47.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (29,'N','N','N','N',0,'Y',100,530,'N','N','N','Y','N',540958,'Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.','QtyOnHand',559622,'N','Bestand',0,100,'Bestand','de.metas.material.cockpit',131089,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559622 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:47.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,102,'N','N','N','Y','N',540958,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',559623,'N','Mandant für diese Installation.',0,100,'Mandant','de.metas.material.cockpit',10,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559623 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:47.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,113,'N','N','N','Y','N',540958,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',559624,'N','Organisatorische Einheit des Mandanten',0,100,'Sektion','de.metas.material.cockpit',10,0,TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-28 12:39:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-28T12:39:47.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559624 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-03-28T12:39:59.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-03-28 12:39:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559623
;

-- 2018-03-28T12:40:10.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-03-28 12:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559624
;

-- 2018-03-28T12:40:16.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-03-28 12:40:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559618
;

-- 2018-03-28T12:40:21.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-03-28 12:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559619
;

-- 2018-03-28T12:40:25.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-03-28 12:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559621
;

-- 2018-03-28T12:40:34.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2018-03-28 12:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559622
;

