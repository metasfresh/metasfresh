-- 2017-09-05T18:45:21.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540922,0,540740,TO_TIMESTAMP('2017-09-05 18:45:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','_Umsatzreport_BP_Menge','Y','N','N','N','N','Umsatzreport Geschäftspartner mit Menge',TO_TIMESTAMP('2017-09-05 18:45:21','YYYY-MM-DD HH24:MI:SS'),100,'Umsatzreport Geschäftspartner m. Menge')
;

-- 2017-09-05T18:45:21.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540922 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T18:45:21.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540922, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540922)
;

-- 2017-09-05T18:45:21.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T18:45:21.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:26.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540923,0,540741,TO_TIMESTAMP('2017-09-05 18:46:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Umsatzreport_BP_Woche_Menge','Y','N','N','N','N','Umsatzreport Geschäftspartner Woche mit Menge',TO_TIMESTAMP('2017-09-05 18:46:26','YYYY-MM-DD HH24:MI:SS'),100,'Umsatz Geschäftspartner Woche m. Menge')
;

-- 2017-09-05T18:46:26.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540923 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T18:46:26.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540923, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540923)
;

-- 2017-09-05T18:46:27.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:27.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:48.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:48.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T18:46:48.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T18:50:25.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Umsatz Geschäftspartner',Updated=TO_TIMESTAMP('2017-09-05 18:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540738
;

-- 2017-09-05T18:50:34.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Umsatz Geschäftspartner',Updated=TO_TIMESTAMP('2017-09-05 18:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540738
;

-- 2017-09-05T18:50:43.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Umsatz Geschäftspartner Woche mit Menge',Updated=TO_TIMESTAMP('2017-09-05 18:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540923
;

-- 2017-09-05T18:50:49.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Umsatz Geschäftspartner mit Menge',Updated=TO_TIMESTAMP('2017-09-05 18:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540922
;

-- 2017-09-05T18:52:19.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540924,0,540743,TO_TIMESTAMP('2017-09-05 18:52:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Statistik_Mengen_Gesamt_Woche','Y','N','N','N','N','Statistik nach Mengen Gesamt/ Woche',TO_TIMESTAMP('2017-09-05 18:52:19','YYYY-MM-DD HH24:MI:SS'),100,'Statistik nach Mengen Gesamt/ Woche')
;

-- 2017-09-05T18:52:19.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540924 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T18:52:19.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540924, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540924)
;

-- 2017-09-05T18:52:19.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T18:52:19.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T18:52:19.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T18:52:19.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:01.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540925,0,540682,TO_TIMESTAMP('2017-09-05 20:21:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Umsatz_BPartner_Woche','Y','N','N','N','N','Umsatz Geschäftspartner Woche',TO_TIMESTAMP('2017-09-05 20:21:01','YYYY-MM-DD HH24:MI:SS'),100,'Umsatz Geschäftspartner Woche')
;

-- 2017-09-05T20:21:01.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540925 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:21:01.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540925, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540925)
;

-- 2017-09-05T20:21:02.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:02.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:15.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:15.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:15.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:15.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:21:15.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:14.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540926,0,540558,TO_TIMESTAMP('2017-09-05 20:22:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','_Umsatzreport','Y','N','N','N','N','Umsatzreport',TO_TIMESTAMP('2017-09-05 20:22:14','YYYY-MM-DD HH24:MI:SS'),100,'Umsatzreport')
;

-- 2017-09-05T20:22:14.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540926 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:22:14.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540926, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540926)
;

-- 2017-09-05T20:22:15.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:15.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:15.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:15.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:15.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:15.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:17.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:17.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:17.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:17.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:17.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:17.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:22:59.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540927,0,540754,TO_TIMESTAMP('2017-09-05 20:22:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','_Umsatzreport_Woche','Y','N','N','N','N','Umsatzreport Woche',TO_TIMESTAMP('2017-09-05 20:22:59','YYYY-MM-DD HH24:MI:SS'),100,'Umsatzreport Woche')
;

-- 2017-09-05T20:22:59.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540927 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:22:59.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540927, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540927)
;

-- 2017-09-05T20:23:32.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:23:32.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:23:32.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:23:32.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:23:32.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:23:32.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:23:32.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:24:24.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540928,0,540563,TO_TIMESTAMP('2017-09-05 20:24:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','_Artikelstatistik','Y','N','N','N','N','Artikelstatistik',TO_TIMESTAMP('2017-09-05 20:24:24','YYYY-MM-DD HH24:MI:SS'),100,'Artikelstatistik')
;

-- 2017-09-05T20:24:24.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540928 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:24:24.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540928, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540928)
;

-- 2017-09-05T20:25:14.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:14.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540930,0,540655,TO_TIMESTAMP('2017-09-05 20:25:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','_ADR_Auswertung','Y','N','N','N','N','ADR Auswertung',TO_TIMESTAMP('2017-09-05 20:25:56','YYYY-MM-DD HH24:MI:SS'),100,'ADR Auswertung')
;

-- 2017-09-05T20:25:56.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540930 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:25:56.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540930, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540930)
;

-- 2017-09-05T20:25:56.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:25:56.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:09.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:45.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540931,0,540562,TO_TIMESTAMP('2017-09-05 20:26:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Statistik_nach_Mengen','Y','N','N','N','N','Statistik nach Mengen',TO_TIMESTAMP('2017-09-05 20:26:45','YYYY-MM-DD HH24:MI:SS'),100,'Statistik nach Mengen')
;

-- 2017-09-05T20:26:45.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540931 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:26:45.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540931, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540931)
;

-- 2017-09-05T20:26:45.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000100, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:26:51.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:27:47.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540932,0,540742,TO_TIMESTAMP('2017-09-05 20:27:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Statistik_nach_Mengen_Woche','Y','N','N','N','N','Statistik nach Mengen Woche',TO_TIMESTAMP('2017-09-05 20:27:47','YYYY-MM-DD HH24:MI:SS'),100,'Statistik nach Mengen Woche')
;

-- 2017-09-05T20:27:47.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540932 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:27:47.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540932, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540932)
;

-- 2017-09-05T20:27:47.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000002, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2017-09-05T20:28:09.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:01.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540933,0,540723,TO_TIMESTAMP('2017-09-05 20:29:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Bericht_Reklamationen','Y','N','N','N','N','Bericht Reklamationen',TO_TIMESTAMP('2017-09-05 20:29:01','YYYY-MM-DD HH24:MI:SS'),100,'Bericht Reklamationen')
;

-- 2017-09-05T20:29:01.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540933 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:29:01.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540933, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540933)
;

-- 2017-09-05T20:29:01.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000066, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2017-09-05T20:29:05.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:28.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540934,0,540674,TO_TIMESTAMP('2017-09-05 20:30:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Anbauplanung_Auswertung','Y','N','N','N','N','Anbauplanung Auswertung',TO_TIMESTAMP('2017-09-05 20:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Anbauplanung Auswertung')
;

-- 2017-09-05T20:30:28.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540934 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:30:28.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540934, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540934)
;

-- 2017-09-05T20:30:28.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000100, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540880 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:40.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-09-05T20:30:43.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:08.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540935,0,540676,TO_TIMESTAMP('2017-09-05 20:31:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Anbauplanung_Auswertung_Excel','Y','N','N','N','N','Anbauplanung Auswertung Excel',TO_TIMESTAMP('2017-09-05 20:31:08','YYYY-MM-DD HH24:MI:SS'),100,'Anbauplanung Auswertung Excel')
;

-- 2017-09-05T20:31:08.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540935 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:31:08.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540935, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540935)
;

-- 2017-09-05T20:31:09.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:09.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:17.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:17.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:18.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:31:18.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540936,0,540656,TO_TIMESTAMP('2017-09-05 20:32:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','_Gebindeübersicht_EK','Y','N','N','N','N','Gebindeübersicht',TO_TIMESTAMP('2017-09-05 20:32:07','YYYY-MM-DD HH24:MI:SS'),100,'Gebindeübersicht')
;

-- 2017-09-05T20:32:08.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540936 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:32:08.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540936, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540936)
;

-- 2017-09-05T20:32:08.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:08.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540936 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:24.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000066, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540936 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:30.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:30.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540936 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:30.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:35.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:35.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2017-09-05T20:32:35.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540936 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:16.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540937,0,540672,TO_TIMESTAMP('2017-09-05 20:35:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Bilanz','Y','N','N','N','N','Bilanz',TO_TIMESTAMP('2017-09-05 20:35:15','YYYY-MM-DD HH24:MI:SS'),100,'Bilanz')
;

-- 2017-09-05T20:35:16.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540937 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:35:16.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540937, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540937)
;

-- 2017-09-05T20:35:16.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000100, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:22.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540938,0,540588,TO_TIMESTAMP('2017-09-05 20:35:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Saldobilanz','Y','N','N','N','N','Saldobilanz',TO_TIMESTAMP('2017-09-05 20:35:54','YYYY-MM-DD HH24:MI:SS'),100,'Saldobilanz')
;

-- 2017-09-05T20:35:54.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540938 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:35:54.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540938, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540938)
;

-- 2017-09-05T20:35:54.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:35:54.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:14.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:14.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:16.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:16.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:45.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540939,0,540582,TO_TIMESTAMP('2017-09-05 20:36:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Deckungsbeitragsrechnung','Y','N','N','N','N','Deckungsbeitragsrechnung',TO_TIMESTAMP('2017-09-05 20:36:45','YYYY-MM-DD HH24:MI:SS'),100,'Deckungsbeitragsrechnung')
;

-- 2017-09-05T20:36:45.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540939 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:36:45.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540939, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540939)
;

-- 2017-09-05T20:36:46.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53199 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53075 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53076 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53078 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53077 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53079 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53080 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53081 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53198 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53082 AND AD_Tree_ID=10
;

-- 2017-09-05T20:36:46.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53074, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:06.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:06.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:06.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540940,0,540678,TO_TIMESTAMP('2017-09-05 20:37:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Deckungsbeitrag_Kurzversion','Y','N','N','N','N','Deckungsbeitrag Kurzversion',TO_TIMESTAMP('2017-09-05 20:37:34','YYYY-MM-DD HH24:MI:SS'),100,'Deckungsbeitrag Kurzversion')
;

-- 2017-09-05T20:37:34.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540940 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:37:34.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540940, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540940)
;

-- 2017-09-05T20:37:34.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:34.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:53.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:53.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:53.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:37:53.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:35.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540941,0,540643,TO_TIMESTAMP('2017-09-05 20:38:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Deckungsbeitrag_Kurzversion_mit Vorjahr','Y','N','N','N','N','Deckungsbeitrag Kurzversion mit Vorjahr',TO_TIMESTAMP('2017-09-05 20:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Deckungsbeitrag Kurzversion mit Vorjahr')
;

-- 2017-09-05T20:38:35.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540941 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:38:35.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540941, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540941)
;

-- 2017-09-05T20:38:36.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:36.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:59.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:59.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:59.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:59.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:38:59.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:42.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540942,0,540564,TO_TIMESTAMP('2017-09-05 20:39:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Konten_Information','Y','N','N','N','N','Konten Information',TO_TIMESTAMP('2017-09-05 20:39:42','YYYY-MM-DD HH24:MI:SS'),100,'Konten Information')
;

-- 2017-09-05T20:39:42.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540942 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:39:42.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540942, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540942)
;

-- 2017-09-05T20:39:42.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000067, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:48.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:48.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:48.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:48.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:48.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:39:48.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:40:59.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540943,0,540651,TO_TIMESTAMP('2017-09-05 20:40:59','YYYY-MM-DD HH24:MI:SS'),100,'Gebindesaldo Gesamt','de.metas.fresh','Gebindesaldo_Gesamt','Y','N','N','N','N','Gebindesaldo Gesamt',TO_TIMESTAMP('2017-09-05 20:40:59','YYYY-MM-DD HH24:MI:SS'),100,'Gebindesaldo Gesamt')
;

-- 2017-09-05T20:40:59.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540943 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:40:59.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540943, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540943)
;

-- 2017-09-05T20:40:59.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540890 AND AD_Tree_ID=10
;

-- 2017-09-05T20:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540943 AND AD_Tree_ID=10
;

-- 2017-09-05T20:41:17.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000065, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540943 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540944,0,540594,TO_TIMESTAMP('2017-09-05 20:42:00','YYYY-MM-DD HH24:MI:SS'),100,'Bankauszug','de.metas.fresh','Bankauszug','Y','N','N','N','N','Bankauszug',TO_TIMESTAMP('2017-09-05 20:42:00','YYYY-MM-DD HH24:MI:SS'),100,'Bankauszug')
;

-- 2017-09-05T20:42:00.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540944 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:42:00.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540944, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540944)
;

-- 2017-09-05T20:42:00.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:00.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:15.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:16.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:19.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:42:54.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540945,0,540595,TO_TIMESTAMP('2017-09-05 20:42:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Bankauszug_(FiBU)','Y','N','N','N','N','Bankauszug (FiBU)',TO_TIMESTAMP('2017-09-05 20:42:54','YYYY-MM-DD HH24:MI:SS'),100,'Bankauszug (FiBU)')
;

-- 2017-09-05T20:42:54.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540945 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:42:54.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540945, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540945)
;

-- 2017-09-05T20:43:48.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:43:48.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540946,0,540662,TO_TIMESTAMP('2017-09-05 20:45:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Fibukonto_Kontoblatt','Y','N','N','N','N','Fibukonto Kontoblatt',TO_TIMESTAMP('2017-09-05 20:45:15','YYYY-MM-DD HH24:MI:SS'),100,'Fibukonto Kontoblatt')
;

-- 2017-09-05T20:45:15.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540946 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:45:15.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540946, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540946)
;

-- 2017-09-05T20:45:15.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=441 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=149 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50010 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=171 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=437 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53325 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=240 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=361 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:15.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:45:21.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:42.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540947,0,540610,TO_TIMESTAMP('2017-09-05 20:46:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Statistik_nach_Mengen_Gesamt','Y','N','N','N','N','Statistik nach Mengen Gesamt',TO_TIMESTAMP('2017-09-05 20:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Statistik nach Mengen Gesamt')
;

-- 2017-09-05T20:46:42.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540947 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:46:42.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540947, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540947)
;

-- 2017-09-05T20:46:43.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2017-09-05T20:46:43.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:08.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540948,0,500174,TO_TIMESTAMP('2017-09-05 20:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Offene Posten (Jasper)','U','Offene_Posten','Y','N','N','N','N','Offene Posten',TO_TIMESTAMP('2017-09-05 20:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Offene Posten')
;

-- 2017-09-05T20:47:52.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540948 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-05T20:47:52.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540948, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540948)
;

-- 2017-09-05T20:47:52.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:52.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2017-09-05T20:47:57.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

