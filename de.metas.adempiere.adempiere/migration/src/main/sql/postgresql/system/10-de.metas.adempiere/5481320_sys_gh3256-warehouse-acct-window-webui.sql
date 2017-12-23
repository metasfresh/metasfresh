-- 2017-12-23T10:22:42.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540392,TO_TIMESTAMP('2017-12-23 10:22:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','Lager Konten','N',TO_TIMESTAMP('2017-12-23 10:22:42','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2017-12-23T10:22:42.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540392 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2017-12-23T10:22:51.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsSOTrx='N',Updated=TO_TIMESTAMP('2017-12-23 10:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540392
;

-- 2017-12-23T10:23:21.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540977,191,540392,TO_TIMESTAMP('2017-12-23 10:23:21','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Lager Konten','N',10,0,TO_TIMESTAMP('2017-12-23 10:23:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:21.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540977 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-12-23T10:23:28.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,1156,561255,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting',22,'D','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','Y','N','N','N','N','N','Buchführungs-Schema',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,1157,561256,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point',22,'D','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.','Y','Y','N','N','N','N','N','Lager',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2443,561257,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',22,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2444,561258,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2445,561259,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3386,561260,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Warehouse Inventory Asset Account - Currently not used',22,'D','The Warehouse Inventory Asset Account identifies the account used for recording the value of your inventory. This is the counter account for inventory revaluation differences. The Product Asset account maintains the product asset value.','Y','Y','N','N','N','N','N','(Not Used)',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3387,561261,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Differenzen im Lagerbestand (erfasst durch Inventur)',22,'D','The Warehouse Differences Account indicates the account used recording differences identified during inventory counts.','Y','Y','N','N','N','N','N','Lager Bestand Korrektur',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,5133,561262,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Lager Wert Korrektur Währungsdifferenz',22,'D','The Inventory Revaluation Account identifies the account used to records changes in inventory value due to currency revaluation.','Y','Y','N','N','N','N','N','Lager Wert Korrektur Währungsdifferenz',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,6124,561263,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)',22,'D','In actual costing systems, this account is used to post Inventory value adjustments. You could set it to the standard Inventory Asset account.','Y','Y','N','N','N','N','N','Lager Wert Korrektur',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:23:28.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557269,561264,0,540977,TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','M_Warehouse_Acct',TO_TIMESTAMP('2017-12-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:23:28.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-23T10:24:03.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:24:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse Accounts' WHERE AD_Window_ID=540392 AND AD_Language='en_US'
;

-- 2017-12-23T10:24:10.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:24:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse Accounts' WHERE AD_Tab_ID=540977 AND AD_Language='en_US'
;

-- 2017-12-23T10:24:18.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='_Lagerkonten',Updated=TO_TIMESTAMP('2017-12-23 10:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540392
;

-- 2017-12-23T10:25:02.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541002,0,540392,TO_TIMESTAMP('2017-12-23 10:25:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking.ch.lagerkonf','_Lagerkonten','Y','N','N','N','N','Lager Konten',TO_TIMESTAMP('2017-12-23 10:25:02','YYYY-MM-DD HH24:MI:SS'),100,'Lager Konten')
;

-- 2017-12-23T10:25:02.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541002 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-12-23T10:25:02.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541002, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541002)
;

-- 2017-12-23T10:25:02.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:02.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:02.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:02.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:02.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:02.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:02.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541000 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541001 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540956 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540881 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540882 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:07.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- 2017-12-23T10:25:29.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:25:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse Accounts',WEBUI_NameBrowse='Warehouse Accounts' WHERE AD_Menu_ID=541002 AND AD_Language='en_US'
;

-- 2017-12-23T10:30:41.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540977,540575,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-12-23T10:30:41.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540575 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-12-23T10:30:41.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540771,540575,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:41.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540772,540575,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540771,541325,TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-12-23 10:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561255,0,540977,541325,549870,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','Y','Y','N','Buchführungs-Schema',10,10,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561256,0,540977,541325,549871,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.','Y','N','Y','Y','N','Lager',20,20,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561257,0,540977,541325,549872,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','Y','N','Mandant',30,30,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561258,0,540977,541325,549873,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',40,40,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561259,0,540977,541325,549874,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561260,0,540977,541325,549875,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Warehouse Inventory Asset Account - Currently not used','The Warehouse Inventory Asset Account identifies the account used for recording the value of your inventory. This is the counter account for inventory revaluation differences. The Product Asset account maintains the product asset value.','Y','N','Y','Y','N','(Not Used)',60,60,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561261,0,540977,541325,549876,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Differenzen im Lagerbestand (erfasst durch Inventur)','The Warehouse Differences Account indicates the account used recording differences identified during inventory counts.','Y','N','Y','Y','N','Lager Bestand Korrektur',70,70,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561262,0,540977,541325,549877,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Lager Wert Korrektur Währungsdifferenz','The Inventory Revaluation Account identifies the account used to records changes in inventory value due to currency revaluation.','Y','N','Y','Y','N','Lager Wert Korrektur Währungsdifferenz',80,80,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:30:42.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561263,0,540977,541325,549878,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)','In actual costing systems, this account is used to post Inventory value adjustments. You could set it to the standard Inventory Asset account.','Y','N','Y','Y','N','Lager Wert Korrektur',90,90,0,TO_TIMESTAMP('2017-12-23 10:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:32:03.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540772,541326,TO_TIMESTAMP('2017-12-23 10:32:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-12-23 10:32:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:32:07.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540772,541327,TO_TIMESTAMP('2017-12-23 10:32:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-12-23 10:32:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-23T10:32:23.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541327, SeqNo=10,Updated=TO_TIMESTAMP('2017-12-23 10:32:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549873
;

-- 2017-12-23T10:32:33.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541327, SeqNo=20,Updated=TO_TIMESTAMP('2017-12-23 10:32:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549872
;

-- 2017-12-23T10:32:40.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541326, SeqNo=10,Updated=TO_TIMESTAMP('2017-12-23 10:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549874
;

-- 2017-12-23T10:33:33.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=549875
;

-- 2017-12-23T10:35:22.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549872
;

-- 2017-12-23T10:35:22.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549871
;

-- 2017-12-23T10:35:22.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549870
;

-- 2017-12-23T10:35:22.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549876
;

-- 2017-12-23T10:35:22.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549877
;

-- 2017-12-23T10:35:22.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549878
;

-- 2017-12-23T10:35:22.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549874
;

-- 2017-12-23T10:35:22.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-12-23 10:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549873
;

-- 2017-12-23T10:35:33.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-12-23 10:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549871
;

-- 2017-12-23T10:35:33.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-12-23 10:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549870
;

-- 2017-12-23T10:35:33.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-12-23 10:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549874
;

-- 2017-12-23T10:35:33.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-12-23 10:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549873
;

-- 2017-12-23T10:36:55.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:36:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Accounting Schema' WHERE AD_Field_ID=561255 AND AD_Language='en_US'
;

-- 2017-12-23T10:37:08.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:37:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse' WHERE AD_Field_ID=561256 AND AD_Language='en_US'
;

-- 2017-12-23T10:37:20.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:37:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client' WHERE AD_Field_ID=561257 AND AD_Language='en_US'
;

-- 2017-12-23T10:37:32.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:37:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=561258 AND AD_Language='en_US'
;

-- 2017-12-23T10:37:41.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:37:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active' WHERE AD_Field_ID=561259 AND AD_Language='en_US'
;

-- 2017-12-23T10:38:06.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:38:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Differences Account',Description='' WHERE AD_Field_ID=561261 AND AD_Language='en_US'
;

-- 2017-12-23T10:38:28.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:38:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Revaluation Account',Description='' WHERE AD_Field_ID=561262 AND AD_Language='en_US'
;

-- 2017-12-23T10:38:59.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:38:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Adjust Value Account',Description='' WHERE AD_Field_ID=561263 AND AD_Language='en_US'
;

-- 2017-12-23T10:39:24.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-23 10:39:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse Accounts' WHERE AD_Field_ID=561264 AND AD_Language='en_US'
;

-- 2017-12-23T10:41:26.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540391,Updated=TO_TIMESTAMP('2017-12-23 10:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=191
;

-- 2017-12-23T10:44:34.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET IsActive='N',Updated=TO_TIMESTAMP('2017-12-23 10:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540495
;

-- 2017-12-23T10:45:52.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548741
;

-- 2017-12-23T10:45:52.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548738
;

-- 2017-12-23T10:45:52.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548736
;

-- 2017-12-23T10:45:52.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548739
;

-- 2017-12-23T10:45:52.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548740
;

-- 2017-12-23T10:45:53.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548742
;

-- 2017-12-23T10:45:53.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548743
;

-- 2017-12-23T10:45:53.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548744
;

-- 2017-12-23T10:45:53.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548737
;

-- 2017-12-23T10:45:56.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=541167
;

-- 2017-12-23T10:46:09.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET IsActive='Y',Updated=TO_TIMESTAMP('2017-12-23 10:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540495
;

-- 2017-12-23T10:46:12.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540665
;

-- 2017-12-23T10:46:16.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540495
;

-- 2017-12-23T10:46:16.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540495
;

-- 2017-12-23T10:47:29.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=2,Updated=TO_TIMESTAMP('2017-12-23 10:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=209
;

-- 2017-12-23T10:50:00.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2017-12-23 10:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=209
;

-- 2017-12-23T10:50:39.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540392,Updated=TO_TIMESTAMP('2017-12-23 10:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=191
;

