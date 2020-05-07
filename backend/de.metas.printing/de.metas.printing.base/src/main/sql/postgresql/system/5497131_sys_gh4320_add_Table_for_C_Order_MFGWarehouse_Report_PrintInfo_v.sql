-- 2018-07-04T13:46:33.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541001,'N',TO_TIMESTAMP('2018-07-04 13:46:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','N','Y','N','N','N','N','N','N','Y',0,'C_Order_MFGWarehouse_Report_PrintInfo_v','NP','L','C_Order_MFGWarehouse_Report_PrintInfo_v',TO_TIMESTAMP('2018-07-04 13:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T13:46:33.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=541001 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-07-04T13:50:06.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsView='N',Updated=TO_TIMESTAMP('2018-07-04 13:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541001
;

-- 2018-07-04T13:51:39.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsView='Y',Updated=TO_TIMESTAMP('2018-07-04 13:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541001
;

-- 2018-07-04T13:55:19.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560613,558,0,19,541001,'C_Order_ID',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','de.metas.fresh',10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:19.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560613 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:19.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560614,542890,0,19,541001,'C_Order_MFGWarehouse_Report_ID',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Order / MFG Warehouse report',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:19.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560614 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:19.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560615,542007,0,19,541001,'AD_User_Responsible_ID',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Verantwortlicher Benutzer',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:19.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560615 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:19.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560616,113,0,19,541001,'AD_Org_ID',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.fresh',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:19.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560616 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:19.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560617,102,0,19,541001,'AD_Client_ID',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.fresh',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:19.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560617 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:19.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544157,0,'ad_session_printpackage_id',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','ad_session_printpackage_id','ad_session_printpackage_id',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T13:55:19.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544157 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T13:55:19.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560618,544157,0,19,541001,'ad_session_printpackage_id',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','ad_session_printpackage_id',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:19.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560618 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560619,1777,0,19,541001,'S_Resource_ID',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Ressource',TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560619 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560620,459,0,19,541001,'M_Warehouse_ID',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','de.metas.fresh',10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','N','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560620 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560621,541906,0,19,541001,'C_Queue_Element_ID',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Element Queue',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560621 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560622,541904,0,19,541001,'C_Queue_WorkPackage_ID',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','WorkPackage Queue',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560622 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544158,0,'wp_isprocessed',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','wp_isprocessed','wp_isprocessed',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T13:55:20.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544158 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T13:55:20.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560623,544158,0,20,541001,'wp_isprocessed',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',1,'Y','Y','N','N','N','N','N','N','N','N','N','wp_isprocessed',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560623 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544159,0,'wp_iserror',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','wp_iserror','wp_iserror',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T13:55:20.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544159 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T13:55:20.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560624,544159,0,20,541001,'wp_iserror',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',1,'Y','Y','N','N','N','N','N','N','N','N','N','wp_iserror',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560624 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:20.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560625,2671,0,19,541001,'AD_Archive_ID',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'Archiv für Belege und Berichte','de.metas.fresh',10,'In Abhängigkeit des Grades der Archivierungsautomatik des Mandanten werden Dokumente und Berichte archiviert und stehen zur Ansicht zur Verfügung.','Y','Y','N','N','N','N','N','N','N','N','N','Archiv',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:20.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560625 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560626,541927,0,19,541001,'C_Printing_Queue_ID',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druck-Warteschlangendatensatz',TO_TIMESTAMP('2018-07-04 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560627,542012,0,19,541001,'C_Print_Job_Instructions_ID',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druck-Job Anweisung',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544160,0,'status_print_job_instructions',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','status_print_job_instructions','status_print_job_instructions',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T13:55:21.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544160 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T13:55:21.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560628,544160,0,20,541001,'status_print_job_instructions',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',1,'Y','Y','N','N','N','N','N','N','N','N','N','status_print_job_instructions',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560629,541955,0,19,541001,'C_Print_Package_ID',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druckpaket',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560630,541959,0,19,541001,'C_Print_PackageInfo_ID',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'Contains details for the print package, like printer, tray, pages from/to and print service name.','de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druckpaket-Info',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560630 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560631,541930,0,19,541001,'AD_PrinterHW_ID',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Hardware-Drucker',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560631 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560632,541932,0,19,541001,'AD_PrinterHW_MediaTray_ID',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Hardware-Schacht',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:21.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560633,541962,0,10,541001,'PrintServiceName',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',255,'Y','Y','N','N','N','N','N','N','N','N','N','Print service name',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:21.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560633 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:22.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560634,541933,0,11,541001,'TrayNumber',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',10,'Y','Y','N','N','N','N','N','N','N','N','N','Schachtnummer',TO_TIMESTAMP('2018-07-04 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:22.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560634 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:55:22.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544161,0,'printservicetray',TO_TIMESTAMP('2018-07-04 13:55:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','printservicetray','printservicetray',TO_TIMESTAMP('2018-07-04 13:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T13:55:22.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544161 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T13:55:22.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560635,544161,0,10,541001,'printservicetray',TO_TIMESTAMP('2018-07-04 13:55:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',255,'Y','Y','N','N','N','N','N','N','N','N','N','printservicetray',TO_TIMESTAMP('2018-07-04 13:55:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T13:55:22.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560635 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T13:59:35.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AD_Session_Printpackage_ID', Name='AD_Session_Printpackage_ID', PrintName='AD_Session_Printpackage_ID',Updated=TO_TIMESTAMP('2018-07-04 13:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544157
;

-- 2018-07-04T13:59:35.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Session_Printpackage_ID', Name='AD_Session_Printpackage_ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544157
;

-- 2018-07-04T13:59:35.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Session_Printpackage_ID', Name='AD_Session_Printpackage_ID', Description=NULL, Help=NULL, AD_Element_ID=544157 WHERE UPPER(ColumnName)='AD_SESSION_PRINTPACKAGE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T13:59:35.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Session_Printpackage_ID', Name='AD_Session_Printpackage_ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544157 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T13:59:35.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AD_Session_Printpackage_ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544157) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544157)
;

-- 2018-07-04T13:59:35.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='AD_Session_Printpackage_ID', Name='AD_Session_Printpackage_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544157)
;

-- 2018-07-04T14:02:01.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PrintServiceTray', Name='PrintServiceTray', PrintName='PrintServiceTray',Updated=TO_TIMESTAMP('2018-07-04 14:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544161
;

-- 2018-07-04T14:02:01.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PrintServiceTray', Name='PrintServiceTray', Description=NULL, Help=NULL WHERE AD_Element_ID=544161
;

-- 2018-07-04T14:02:01.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PrintServiceTray', Name='PrintServiceTray', Description=NULL, Help=NULL, AD_Element_ID=544161 WHERE UPPER(ColumnName)='PRINTSERVICETRAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:02:01.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PrintServiceTray', Name='PrintServiceTray', Description=NULL, Help=NULL WHERE AD_Element_ID=544161 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:02:01.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PrintServiceTray', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544161) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544161)
;

-- 2018-07-04T14:02:01.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PrintServiceTray', Name='PrintServiceTray' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544161)
;

-- 2018-07-04T14:03:59.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Status_Print_Job_Instructions', Name='Status_Print_Job_Instructions', PrintName='Status_Print_Job_Instructions',Updated=TO_TIMESTAMP('2018-07-04 14:03:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544160
;

-- 2018-07-04T14:03:59.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Status_Print_Job_Instructions', Name='Status_Print_Job_Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544160
;

-- 2018-07-04T14:03:59.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status_Print_Job_Instructions', Name='Status_Print_Job_Instructions', Description=NULL, Help=NULL, AD_Element_ID=544160 WHERE UPPER(ColumnName)='STATUS_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:03:59.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status_Print_Job_Instructions', Name='Status_Print_Job_Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544160 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:03:59.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status_Print_Job_Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544160) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544160)
;

-- 2018-07-04T14:03:59.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Status_Print_Job_Instructions', Name='Status_Print_Job_Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544160)
;

-- 2018-07-04T14:04:37.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Status Print Job Instructions', PrintName='Status Print Job Instructions',Updated=TO_TIMESTAMP('2018-07-04 14:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544160
;

-- 2018-07-04T14:04:37.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Status_Print_Job_Instructions', Name='Status Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544160
;

-- 2018-07-04T14:04:37.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status_Print_Job_Instructions', Name='Status Print Job Instructions', Description=NULL, Help=NULL, AD_Element_ID=544160 WHERE UPPER(ColumnName)='STATUS_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:04:37.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status_Print_Job_Instructions', Name='Status Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544160 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:04:37.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status Print Job Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544160) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544160)
;

-- 2018-07-04T14:04:37.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Status Print Job Instructions', Name='Status Print Job Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544160)
;

-- 2018-07-04T14:04:51.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Print Service Tray', PrintName='Print Service Tray',Updated=TO_TIMESTAMP('2018-07-04 14:04:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544161
;

-- 2018-07-04T14:04:51.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PrintServiceTray', Name='Print Service Tray', Description=NULL, Help=NULL WHERE AD_Element_ID=544161
;

-- 2018-07-04T14:04:51.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PrintServiceTray', Name='Print Service Tray', Description=NULL, Help=NULL, AD_Element_ID=544161 WHERE UPPER(ColumnName)='PRINTSERVICETRAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:04:51.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PrintServiceTray', Name='Print Service Tray', Description=NULL, Help=NULL WHERE AD_Element_ID=544161 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:04:51.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Print Service Tray', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544161) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544161)
;

-- 2018-07-04T14:04:51.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Print Service Tray', Name='Print Service Tray' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544161)
;

-- 2018-07-04T14:05:26.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Session Printpackage ID', PrintName='Session Printpackage ID',Updated=TO_TIMESTAMP('2018-07-04 14:05:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544157
;

-- 2018-07-04T14:05:26.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Session_Printpackage_ID', Name='Session Printpackage ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544157
;

-- 2018-07-04T14:05:26.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Session_Printpackage_ID', Name='Session Printpackage ID', Description=NULL, Help=NULL, AD_Element_ID=544157 WHERE UPPER(ColumnName)='AD_SESSION_PRINTPACKAGE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:05:26.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Session_Printpackage_ID', Name='Session Printpackage ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544157 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:05:26.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Session Printpackage ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544157) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544157)
;

-- 2018-07-04T14:05:26.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Session Printpackage ID', Name='Session Printpackage ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544157)
;

-- 2018-07-04T14:05:45.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Column_ID=560616, AD_Element_ID=113, AD_Reference_ID=19, ColumnName='AD_Org_ID', Created=TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'), Description='Organisatorische Einheit des Mandanten', EntityType='de.metas.printing', FieldLength=10, Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', Name='Sektion', Updated=TO_TIMESTAMP('2018-07-04 13:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560616
;

-- 2018-07-04T14:06:31.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='WP_IsError', Name='WP IsError', PrintName='WP IsError',Updated=TO_TIMESTAMP('2018-07-04 14:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544159
;

-- 2018-07-04T14:06:31.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='WP_IsError', Name='WP IsError', Description=NULL, Help=NULL WHERE AD_Element_ID=544159
;

-- 2018-07-04T14:06:31.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WP_IsError', Name='WP IsError', Description=NULL, Help=NULL, AD_Element_ID=544159 WHERE UPPER(ColumnName)='WP_ISERROR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:06:31.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WP_IsError', Name='WP IsError', Description=NULL, Help=NULL WHERE AD_Element_ID=544159 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:06:31.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='WP IsError', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544159) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544159)
;

-- 2018-07-04T14:06:31.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='WP IsError', Name='WP IsError' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544159)
;

-- 2018-07-04T14:09:15.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='WP_IsProcessed', Name='WP IsProcessed', PrintName='WP IsProcessed',Updated=TO_TIMESTAMP('2018-07-04 14:09:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544158
;

-- 2018-07-04T14:09:15.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='WP_IsProcessed', Name='WP IsProcessed', Description=NULL, Help=NULL WHERE AD_Element_ID=544158
;

-- 2018-07-04T14:09:15.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WP_IsProcessed', Name='WP IsProcessed', Description=NULL, Help=NULL, AD_Element_ID=544158 WHERE UPPER(ColumnName)='WP_ISPROCESSED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T14:09:15.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WP_IsProcessed', Name='WP IsProcessed', Description=NULL, Help=NULL WHERE AD_Element_ID=544158 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T14:09:15.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='WP IsProcessed', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544158) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544158)
;

-- 2018-07-04T14:09:15.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='WP IsProcessed', Name='WP IsProcessed' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544158)
;

-- 2018-07-04T14:12:56.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2018-07-04 14:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541001
;
-- 2018-07-04T14:41:19.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541001
;

-- 2018-07-04T14:55:17.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560625
;

-- 2018-07-04T14:55:19.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560617
;

-- 2018-07-04T14:55:22.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560623
;

-- 2018-07-04T14:55:23.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560624
;

-- 2018-07-04T14:55:24.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560634
;

-- 2018-07-04T14:55:25.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560628
;

-- 2018-07-04T14:55:26.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560619
;

-- 2018-07-04T14:55:26.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560635
;

-- 2018-07-04T14:55:27.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560633
;

-- 2018-07-04T14:55:28.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560620
;

-- 2018-07-04T14:55:28.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560622
;

-- 2018-07-04T14:55:29.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560621
;

-- 2018-07-04T14:55:29.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560626
;

-- 2018-07-04T14:55:31.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560630
;

-- 2018-07-04T14:55:31.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560629
;

-- 2018-07-04T14:55:32.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560627
;

-- 2018-07-04T14:55:33.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560614
;

-- 2018-07-04T14:55:33.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560613
;

-- 2018-07-04T14:55:34.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560615
;

-- 2018-07-04T14:55:35.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560618
;

-- 2018-07-04T14:55:35.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560632
;

-- 2018-07-04T14:55:36.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-04 14:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560631
;


