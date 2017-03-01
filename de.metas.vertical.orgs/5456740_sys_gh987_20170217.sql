-- 17.02.2017 11:39
-- URL zum Konzept
INSERT INTO AD_Tree (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Tree_ID,Created,CreatedBy,IsActive,IsAllNodes,IsDefault,Name,Processing,TreeType,Updated,UpdatedBy) VALUES (0,0,116,540005,TO_TIMESTAMP('2017-02-17 11:39:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Verband','N','MM',TO_TIMESTAMP('2017-02-17 11:39:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:39
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM WHERE AD_Tree_ID=540005 AND Node_ID<>0 AND Node_ID NOT IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Client_ID=0)
;

-- 17.02.2017 11:39
-- URL zum Konzept
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID,AD_Tree_ID,Created,CreatedBy,IsActive,Node_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,540005,TO_TIMESTAMP('2017-02-17 11:39:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,0,TO_TIMESTAMP('2017-02-17 11:39:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:43
-- URL zum Konzept
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,Name,Processing,Updated,UpdatedBy) VALUES (0,540212,0,TO_TIMESTAMP('2017-02-17 11:43:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.eoss.verband','Y','Y','de.metas.eoss.verband','N',TO_TIMESTAMP('2017-02-17 11:43:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:43
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,540769,0,TO_TIMESTAMP('2017-02-17 11:43:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.eoss.verband','verband.masterdata','Y','N','N','N','Y','Stammdaten',TO_TIMESTAMP('2017-02-17 11:43:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:43
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540769 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 17.02.2017 11:43
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540769, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540769)
;

-- 17.02.2017 11:45
-- URL zum Konzept
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID,AD_Tree_ID,Created,CreatedBy,IsActive,Node_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,540005,TO_TIMESTAMP('2017-02-17 11:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',540769,0,TO_TIMESTAMP('2017-02-17 11:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:45
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540769, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=129 AND AD_Tree_ID=540005
;

-- 17.02.2017 11:45
-- URL zum Konzept
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID,AD_Tree_ID,Created,CreatedBy,IsActive,Node_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,540005,TO_TIMESTAMP('2017-02-17 11:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',129,0,TO_TIMESTAMP('2017-02-17 11:45:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:51
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540769, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=129 AND AD_Tree_ID=540005
;

-- 17.02.2017 11:54
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameNew) VALUES ('W',0,540770,0,143,TO_TIMESTAMP('2017-02-17 11:54:58','YYYY-MM-DD HH24:MI:SS'),100,'Neuaufnahmen','de.metas.eoss.verband','1','Y','Y','N','Y','N','Neuaufnahmen',TO_TIMESTAMP('2017-02-17 11:54:58','YYYY-MM-DD HH24:MI:SS'),100,'Neuaufnahme')
;

-- 17.02.2017 11:54
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540770 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 17.02.2017 11:54
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540770, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540770)
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=319 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=369 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=317 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=318 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=327 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=328 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=349 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=329 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=284 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=344 AND AD_Tree_ID=10
;

-- 17.02.2017 11:54
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540770 AND AD_Tree_ID=10
;

-- 17.02.2017 11:55
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM WHERE AD_Tree_ID=540005 AND Node_ID=129
;

-- 17.02.2017 11:55
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540769, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540770 AND AD_Tree_ID=540005
;

-- 17.02.2017 11:55
-- URL zum Konzept
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID,AD_Tree_ID,Created,CreatedBy,IsActive,Node_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,540005,TO_TIMESTAMP('2017-02-17 11:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',540770,0,TO_TIMESTAMP('2017-02-17 11:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 11:56
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540769, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540770 AND AD_Tree_ID=540005
;

-- 17.02.2017 11:58
-- URL zum Konzept
UPDATE AD_Menu SET WEBUI_NameNew='Neues Mitglied',Updated=TO_TIMESTAMP('2017-02-17 11:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540770
;

-- 17.02.2017 11:58
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540770
;

-- 17.02.2017 13:36
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 13:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=236
;

-- 17.02.2017 13:37
-- URL zum Konzept
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID,AD_Tree_ID,Created,CreatedBy,IsActive,Node_ID,SeqNo,Updated,UpdatedBy) VALUES (0,0,540005,TO_TIMESTAMP('2017-02-17 13:37:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',110,0,TO_TIMESTAMP('2017-02-17 13:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.02.2017 13:38
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540769, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=110 AND AD_Tree_ID=540005
;

-- 17.02.2017 13:38
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540769, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540770 AND AD_Tree_ID=540005
;

-- 17.02.2017 13:44
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 13:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000010
;

-- 17.02.2017 13:44
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 13:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000009
;

-- 17.02.2017 13:44
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 13:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000026
;

-- 17.02.2017 13:44
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Aufnahmedatum',Updated=TO_TIMESTAMP('2017-02-17 13:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000008
;

-- 17.02.2017 13:45
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Auftragsdatum',Updated=TO_TIMESTAMP('2017-02-17 13:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000008
;

-- 17.02.2017 13:45
-- URL zum Konzept
UPDATE AD_Field SET Name='Aufnahmedatum',Updated=TO_TIMESTAMP('2017-02-17 13:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1093
;

-- 17.02.2017 13:45
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=1093
;

-- 17.02.2017 13:47
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 13:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540694
;

-- 17.02.2017 13:47
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 13:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000070
;

-- 17.02.2017 13:47
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 13:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000004
;

-- 17.02.2017 13:47
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET IsActive='Y',Updated=TO_TIMESTAMP('2017-02-17 13:47:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000004
;

-- 17.02.2017 13:48
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 13:48:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000026
;

-- 17.02.2017 13:49
-- URL zum Konzept
UPDATE AD_Field SET Name='Mitglied',Updated=TO_TIMESTAMP('2017-02-17 13:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1573
;

-- 17.02.2017 13:49
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=1573
;

-- 17.02.2017 13:49
-- URL zum Konzept
UPDATE AD_Tab SET Name='Mitgliedschaft',Updated=TO_TIMESTAMP('2017-02-17 13:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=187
;

-- 17.02.2017 13:49
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=187
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000221
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000036
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000038
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000042
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000035
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000041
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000047
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000033
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000044
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000039
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000037
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000040
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000043
;

-- 17.02.2017 13:52
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-02-17 13:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000045
;

-- 17.02.2017 16:12
-- URL zum Konzept
UPDATE AD_Menu SET Description='Mitglieder verwalten', Name='Mitglieder',Updated=TO_TIMESTAMP('2017-02-17 16:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=110
;

-- 17.02.2017 16:12
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=110
;

-- 17.02.2017 16:38
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000010
;

-- 17.02.2017 16:38
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540694
;

-- 17.02.2017 16:38
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000062
;

-- 17.02.2017 16:38
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- 17.02.2017 16:38
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-17 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000009
;

-- 17.02.2017 16:38
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-02-17 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540572
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=224
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=213
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=504622
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=214
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=226
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540653
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540739
;

-- 17.02.2017 18:27
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=212
;

-- 17.02.2017 18:28
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2017-02-17 18:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=223
;

-- 17.02.2017 18:29
-- URL zum Konzept
UPDATE AD_Tab SET SeqNo=11,Updated=TO_TIMESTAMP('2017-02-17 18:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=222
;

-- 17.02.2017 18:30
-- URL zum Konzept
UPDATE AD_Tab SET SeqNo=12,Updated=TO_TIMESTAMP('2017-02-17 18:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=496
;

-- 17.02.2017 18:31
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000082
;

-- 17.02.2017 18:31
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000083
;

-- 17.02.2017 18:31
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:31:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000084
;

-- 17.02.2017 18:31
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000085
;

-- 17.02.2017 18:31
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000081
;

-- 17.02.2017 18:32
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000073
;

-- 17.02.2017 18:46
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000087
;

-- 17.02.2017 18:46
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-02-17 18:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000080
;

