-- 27.10.2016 12:41
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540316,TO_TIMESTAMP('2016-10-27 12:41:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','N','N','N','Y','Quality Note','N',TO_TIMESTAMP('2016-10-27 12:41:29','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 27.10.2016 12:41
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540316 AND NOT EXISTS (SELECT * FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 27.10.2016 12:42
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540767,540794,540316,TO_TIMESTAMP('2016-10-27 12:42:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Qualität-Notiz','N',10,0,TO_TIMESTAMP('2016-10-27 12:42:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:42
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540767 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555277,557364,0,540767,TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.inout','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557364 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555278,557365,0,540767,TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.inout','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557365 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555281,557366,0,540767,TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.inout','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557366 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555284,557367,0,540767,TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inout','Y','Y','N','N','N','N','N','N','N','Qualität-Notiz',TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557367 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555285,557368,0,540767,TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',255,'de.metas.inout','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557368 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555286,557369,0,540767,TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',255,'de.metas.inout','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','Y','Y','N','N','N','N','N','Name',TO_TIMESTAMP('2016-10-27 12:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557369 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 12:44
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=10, SeqNoGrid=10,Updated=TO_TIMESTAMP('2016-10-27 12:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557364
;

-- 27.10.2016 12:44
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=20, SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-10-27 12:44:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557365
;

-- 27.10.2016 12:44
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=30, SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-10-27 12:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557368
;

-- 27.10.2016 12:44
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=40, SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-10-27 12:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557369
;

-- 27.10.2016 12:44
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-10-27 12:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557366
;

-- 27.10.2016 12:47
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,540734,0,540316,TO_TIMESTAMP('2016-10-27 12:47:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','M_QualityNote','Y','N','N','N','N','Quality Note',TO_TIMESTAMP('2016-10-27 12:47:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:47
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540734 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 27.10.2016 12:47
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540734, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540734)
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=355 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=354 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=359 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=353 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=356 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=565 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=358 AND AD_Tree_ID=10
;

-- 27.10.2016 12:47
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540734 AND AD_Tree_ID=10
;

-- 27.10.2016 12:51
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555285,Updated=TO_TIMESTAMP('2016-10-27 12:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 12:51
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=555284,Updated=TO_TIMESTAMP('2016-10-27 12:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 12:52
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540316,Updated=TO_TIMESTAMP('2016-10-27 12:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540692
;

-- 27.10.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555287,557370,0,540767,0,TO_TIMESTAMP('2016-10-27 13:00:48','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.inout',0,'Y','Y','Y','Y','N','N','N','N','N','PerformanceType',60,60,0,1,1,TO_TIMESTAMP('2016-10-27 13:00:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557370 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 13:01
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-10-27 13:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557370
;

-- 27.10.2016 13:01
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-10-27 13:01:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557366
;

