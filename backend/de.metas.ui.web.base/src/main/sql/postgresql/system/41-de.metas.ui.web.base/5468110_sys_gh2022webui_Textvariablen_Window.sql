-- 2017-07-19T14:34:22.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,540895,0,540026,TO_TIMESTAMP('2017-07-19 14:34:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.letters','540026 (Todo: Set Internal Name for UI testing)','Y','N','N','N','N','Textvariablen',TO_TIMESTAMP('2017-07-19 14:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T14:34:22.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540895 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-07-19T14:34:22.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540895, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540895)
;

-- 2017-07-19T14:34:23.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=265 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=104 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=105 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=384 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=111 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=106 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=117 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540675 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=418 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=102 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=103 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=270 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=121 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=476 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540715 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=409 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=151 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53087 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=124 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=123 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=547 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53189 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=174 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=254 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=120 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=135 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=550 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=551 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=306 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53091 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=417 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=307 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=393 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53248 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:23.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=265 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=104 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=105 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=384 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=111 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=106 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=117 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540675 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=418 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=102 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=103 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=270 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=121 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=476 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540715 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=409 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=151 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53087 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=124 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=123 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=547 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53189 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=174 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=254 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=120 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=135 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=550 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=551 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=306 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53091 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=417 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=307 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=393 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:39.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=164, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53248 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-07-19T14:34:45.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;



-- 2017-07-19T14:38:42.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Textvariablen',Updated=TO_TIMESTAMP('2017-07-19 14:38:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540895
;




-- 2017-07-19T15:10:24.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 15:10:24','YYYY-MM-DD HH24:MI:SS'),Name='Text Variable',WEBUI_NameBrowse='Text Variable' WHERE AD_Menu_ID=540895 AND AD_Language='en_US'
;

-- 2017-07-19T15:19:11.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540104,540370,TO_TIMESTAMP('2017-07-19 15:19:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-19 15:19:06','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-19T15:19:11.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540370 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-19T15:19:11.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540499,540370,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540500,540370,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540499,540859,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542306,0,540104,540859,546662,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,10,0,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542308,0,540104,540859,546663,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Variablenname',20,20,0,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542305,0,540104,540859,546664,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542309,0,540104,540859,546665,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Type of Validation (SQL, Java Script, Java Language)','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.','Y','N','Y','Y','N','Typ',40,40,0,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:11.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542307,0,540104,540859,546666,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Regel',50,50,0,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:12.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542310,0,540104,540859,546667,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'Validation Code','The Validation Code displays the date, time and message of the error.','Y','N','Y','Y','N','Validierungs Code',60,60,0,TO_TIMESTAMP('2017-07-19 15:19:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:12.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540134,540371,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-19T15:19:12.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540371 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-19T15:19:12.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540501,540371,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:12.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540501,540860,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:12.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542910,0,540134,540860,546668,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules','The Document Type determines document sequence and processing rules','Y','N','N','Y','N','Document Type',0,10,0,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:12.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542911,0,540134,540860,546669,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Evaluation Time',0,20,0,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:19:12.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542907,0,540134,540860,546670,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','Active',0,30,0,TO_TIMESTAMP('2017-07-19 15:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2017-07-19T15:24:32.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 15:24:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=540895 AND AD_Language='en_US'
;



-- 2017-07-19T15:30:54.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-19 15:30:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540499
;

-- 2017-07-19T15:30:59.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-19 15:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540500
;

-- 2017-07-19T15:31:36.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540500,540861,TO_TIMESTAMP('2017-07-19 15:31:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-19 15:31:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:33:21.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-19 15:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540499
;

-- 2017-07-19T15:33:24.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-19 15:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540500
;

-- 2017-07-19T15:33:33.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540861
;

-- 2017-07-19T15:33:52.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-19 15:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546664
;

-- 2017-07-19T15:33:59.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-19 15:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546665
;

-- 2017-07-19T15:34:23.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-19 15:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540499
;

-- 2017-07-19T15:34:26.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-19 15:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540500
;

-- 2017-07-19T15:34:34.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540500,540862,TO_TIMESTAMP('2017-07-19 15:34:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-19 15:34:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:34:49.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542305,0,540104,540862,546671,TO_TIMESTAMP('2017-07-19 15:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-07-19 15:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:35:17.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542308,0,540104,540862,546672,TO_TIMESTAMP('2017-07-19 15:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Variablenname',20,0,0,TO_TIMESTAMP('2017-07-19 15:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:36:48.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540499,540863,TO_TIMESTAMP('2017-07-19 15:36:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','type',20,TO_TIMESTAMP('2017-07-19 15:36:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:37:30.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542309,0,540104,540863,546673,TO_TIMESTAMP('2017-07-19 15:37:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Typ',10,0,0,TO_TIMESTAMP('2017-07-19 15:37:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:39:46.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540499,540864,TO_TIMESTAMP('2017-07-19 15:39:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-07-19 15:39:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:40:01.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542306,0,540104,540864,546674,TO_TIMESTAMP('2017-07-19 15:40:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-19 15:40:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:40:26.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540502,540370,TO_TIMESTAMP('2017-07-19 15:40:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,TO_TIMESTAMP('2017-07-19 15:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:41:50.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540502
;

-- 2017-07-19T15:42:41.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540500,540865,TO_TIMESTAMP('2017-07-19 15:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','sql',20,TO_TIMESTAMP('2017-07-19 15:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:43:07.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542310,0,540104,540865,546675,TO_TIMESTAMP('2017-07-19 15:43:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Validierungs Code',10,0,0,TO_TIMESTAMP('2017-07-19 15:43:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:43:31.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542307,0,540104,540863,546676,TO_TIMESTAMP('2017-07-19 15:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Regel',20,0,0,TO_TIMESTAMP('2017-07-19 15:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:44:47.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546662
;

-- 2017-07-19T15:44:47.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546663
;

-- 2017-07-19T15:44:47.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546665
;

-- 2017-07-19T15:44:47.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546666
;

-- 2017-07-19T15:44:47.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546667
;

-- 2017-07-19T15:44:47.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546664
;

-- 2017-07-19T15:44:51.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540859
;

-- 2017-07-19T15:44:56.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-19 15:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540863
;

-- 2017-07-19T15:46:03.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-19 15:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540864
;

-- 2017-07-19T15:46:15.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546676
;

-- 2017-07-19T15:46:34.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542307,0,540104,540865,546677,TO_TIMESTAMP('2017-07-19 15:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Regel',20,0,0,TO_TIMESTAMP('2017-07-19 15:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T15:49:54.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-19 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546674
;

-- 2017-07-19T15:49:54.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-19 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546672
;

-- 2017-07-19T15:49:54.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-19 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546671
;

-- 2017-07-19T15:49:54.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-19 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546673
;

-- 2017-07-19T15:49:54.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-19 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546677
;

-- 2017-07-19T15:49:54.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-19 15:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546675
;

-- 2017-07-19T15:50:15.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-19 15:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546672
;

-- 2017-07-19T15:50:15.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-19 15:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546671
;

-- 2017-07-19T15:50:24.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-19 15:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546673
;

-- 2017-07-19T16:01:09.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-19 16:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=540501
;

-- 2017-07-19T16:01:22.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540503,540371,TO_TIMESTAMP('2017-07-19 16:01:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-19 16:01:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:02:55.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540503,540866,TO_TIMESTAMP('2017-07-19 16:02:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-19 16:02:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:03:39.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542910,0,540134,540866,546678,TO_TIMESTAMP('2017-07-19 16:03:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Document Type',10,0,0,TO_TIMESTAMP('2017-07-19 16:03:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:03:54.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542911,0,540134,540866,546679,TO_TIMESTAMP('2017-07-19 16:03:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Evaluation Time',20,0,0,TO_TIMESTAMP('2017-07-19 16:03:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:04:16.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-19 16:04:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540860
;

-- 2017-07-19T16:04:27.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540501,540867,TO_TIMESTAMP('2017-07-19 16:04:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',10,TO_TIMESTAMP('2017-07-19 16:04:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:04:41.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542907,0,540134,540867,546680,TO_TIMESTAMP('2017-07-19 16:04:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Active',10,0,0,TO_TIMESTAMP('2017-07-19 16:04:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:04:58.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540501,540868,TO_TIMESTAMP('2017-07-19 16:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-07-19 16:04:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:05:29.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542912,0,540134,540868,546681,TO_TIMESTAMP('2017-07-19 16:05:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-19 16:05:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:05:44.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542909,0,540134,540868,546682,TO_TIMESTAMP('2017-07-19 16:05:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-19 16:05:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:06:56.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546668
;

-- 2017-07-19T16:06:56.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546669
;

-- 2017-07-19T16:06:56.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546670
;

-- 2017-07-19T16:07:15.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540860
;

-- 2017-07-19T16:16:19.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546681
;

-- 2017-07-19T16:16:21.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546682
;

-- 2017-07-19T16:16:28.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540868
;

-- 2017-07-19T16:23:58.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-19 16:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546678
;

-- 2017-07-19T16:23:58.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-19 16:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546679
;

-- 2017-07-19T16:23:58.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-19 16:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546680
;

-- 2017-07-19T16:36:27.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542912,0,540134,540867,546683,TO_TIMESTAMP('2017-07-19 16:36:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2017-07-19 16:36:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:37:00.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542909,0,540134,540867,546684,TO_TIMESTAMP('2017-07-19 16:37:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',30,0,0,TO_TIMESTAMP('2017-07-19 16:37:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:40:45.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542304,0,540104,540864,546685,TO_TIMESTAMP('2017-07-19 16:40:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-19 16:40:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T16:43:19.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542908,0,540134,540866,546686,TO_TIMESTAMP('2017-07-19 16:43:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','boiler plate variable',30,0,0,TO_TIMESTAMP('2017-07-19 16:43:19','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2017-07-19T17:22:32.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546686
;

-- 2017-07-19T17:22:45.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546683
;

-- 2017-07-19T17:22:47.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546684
;

-- 2017-07-19T17:22:56.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540501,540869,TO_TIMESTAMP('2017-07-19 17:22:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-07-19 17:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T17:23:10.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542912,0,540134,540869,546687,TO_TIMESTAMP('2017-07-19 17:23:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-19 17:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-19T17:23:18.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542909,0,540134,540869,546688,TO_TIMESTAMP('2017-07-19 17:23:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-19 17:23:18','YYYY-MM-DD HH24:MI:SS'),100)
;


