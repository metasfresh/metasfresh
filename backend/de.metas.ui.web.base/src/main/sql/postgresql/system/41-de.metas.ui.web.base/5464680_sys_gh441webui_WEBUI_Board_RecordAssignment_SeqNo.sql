-- 2017-06-09T13:54:42.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,540859,0,540347,TO_TIMESTAMP('2017-06-09 13:54:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Board','Y','N','N','N','N','Board (WEBUI)',TO_TIMESTAMP('2017-06-09 13:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-09T13:54:42.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540859 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-09T13:54:42.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540859, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540859)
;

-- 2017-06-09T13:54:42.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=452 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=454 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=466 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540716 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=468 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=467 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=463 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=549 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=471 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=205 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=204 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=360 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=493 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=206 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=516 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=312 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540495 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540688 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:42.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540859 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540859 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540727 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=155 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=156 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=175 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=157 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=552 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540537 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540603 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540639 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540640 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540645 AND AD_Tree_ID=10
;

-- 2017-06-09T13:54:57.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540691 AND AD_Tree_ID=10
;

-- 2017-06-09T18:13:46.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,CacheInvalidateParent,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556920,566,0,11,540826,'N','N','SeqNo',TO_TIMESTAMP('2017-06-09 18:13:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.ui.web',10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Reihenfolge',0,TO_TIMESTAMP('2017-06-09 18:13:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-06-09T18:13:46.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556920 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-06-09T18:13:57.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('webui_board_recordassignment','ALTER TABLE public.WEBUI_Board_RecordAssignment ADD COLUMN SeqNo NUMERIC(10) NOT NULL')
;

