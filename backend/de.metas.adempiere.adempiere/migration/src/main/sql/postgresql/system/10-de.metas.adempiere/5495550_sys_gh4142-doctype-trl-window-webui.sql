-- 2018-06-09T08:34:31.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540445,TO_TIMESTAMP('2018-06-09 08:34:31','YYYY-MM-DD HH24:MI:SS'),100,'D','_Belegart_Übersetzung','Y','N','N','N','N','Y','Belegart Übersetzung','N',TO_TIMESTAMP('2018-06-09 08:34:31','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2018-06-09T08:34:31.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540445 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2018-06-09T08:35:01.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,541147,300,540445,TO_TIMESTAMP('2018-06-09 08:35:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','N','Y','N','N','Y','Y','N','N','N',0,'Belegart Übersetzung','N',10,0,TO_TIMESTAMP('2018-06-09 08:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:01.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=541147 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-06-09T08:35:04.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2018-06-09 08:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541147
;

-- 2018-06-09T08:35:13.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3127,564557,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564557 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3128,564558,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564558 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3125,564559,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben',14,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','Y','Y','N','N','N','Y','N','Belegart',30,30,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3126,564560,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag',14,'D','Definiert die Sprache für Anzeige und Aufbereitung',0,'Y','Y','Y','N','N','N','Y','N','Sprache',40,40,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564560 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3129,564561,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564561 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3136,564562,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt',1,'D','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist',0,'Y','Y','Y','N','N','N','N','N','Übersetzt',60,60,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4322,564563,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'D','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','N','N','N','N','N','Name',70,70,999,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3134,564564,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll',11,'D','The Label to be printed indicates the name that will be printed on a document or correspondence. The max length is 2000 characters.',0,'Y','Y','Y','N','N','N','N','N','Drucktext',80,80,1,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:35:13.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3135,564565,0,541147,0,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Information für den Kunden',60,'D','"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.',0,'Y','Y','Y','N','N','N','N','N','Notiz / Zeilentext',90,90,999,1,TO_TIMESTAMP('2018-06-09 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:35:13.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-09T08:38:29.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541106,0,540445,TO_TIMESTAMP('2018-06-09 08:38:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.workflow','_Belegart_Übersetzung','Y','N','N','N','N','Belegart Übersetzung',TO_TIMESTAMP('2018-06-09 08:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Belegart Übersetzung')
;

-- 2018-06-09T08:38:29.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541106 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-06-09T08:38:29.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541106, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541106)
;

-- 2018-06-09T08:38:30.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=114 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:30.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=108 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:30.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=115 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:30.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53225 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:30.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53226 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:30.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541106 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540989 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540960 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540988 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541081 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541106 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:34.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541105 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540989 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540960 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540988 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541081 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541105 AND AD_Tree_ID=10
;

-- 2018-06-09T08:38:37.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541106 AND AD_Tree_ID=10
;

-- 2018-06-09T08:39:47.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:39:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Document Type Translation',WEBUI_NameBrowse='Document Type Translation' WHERE AD_Menu_ID=541106 AND AD_Language='en_US'
;

-- 2018-06-09T08:47:05.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541147,540764,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-06-09T08:47:05.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540764 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-06-09T08:47:05.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541002,540764,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541003,540764,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541002,541642,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564557,0,541147,541642,552172,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564558,0,541147,541642,552173,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564559,0,541147,541642,552174,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','Y','N','Belegart',30,30,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564560,0,541147,541642,552175,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','Y','N','Sprache',40,40,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564561,0,541147,541642,552176,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564562,0,541147,541642,552177,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','Y','Y','N','Übersetzt',60,60,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564563,0,541147,541642,552178,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',70,70,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564564,0,541147,541642,552179,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll','The Label to be printed indicates the name that will be printed on a document or correspondence. The max length is 2000 characters.','Y','N','Y','Y','N','Drucktext',80,80,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:47:05.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564565,0,541147,541642,552180,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Information für den Kunden','"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.','Y','N','Y','Y','N','Notiz / Zeilentext',90,90,0,TO_TIMESTAMP('2018-06-09 08:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:49:06.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541003,541643,TO_TIMESTAMP('2018-06-09 08:49:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-06-09 08:49:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:49:12.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541003,541644,TO_TIMESTAMP('2018-06-09 08:49:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-06-09 08:49:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-09T08:49:25.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541643, SeqNo=10,Updated=TO_TIMESTAMP('2018-06-09 08:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552176
;

-- 2018-06-09T08:49:34.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541643, SeqNo=20,Updated=TO_TIMESTAMP('2018-06-09 08:49:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552177
;

-- 2018-06-09T08:49:43.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541644, SeqNo=10,Updated=TO_TIMESTAMP('2018-06-09 08:49:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552173
;

-- 2018-06-09T08:49:50.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541644, SeqNo=20,Updated=TO_TIMESTAMP('2018-06-09 08:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552172
;

-- 2018-06-09T08:53:18.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552172
;

-- 2018-06-09T08:53:18.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552174
;

-- 2018-06-09T08:53:18.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552175
;

-- 2018-06-09T08:53:18.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552178
;

-- 2018-06-09T08:53:18.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552179
;

-- 2018-06-09T08:53:18.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552180
;

-- 2018-06-09T08:53:18.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552176
;

-- 2018-06-09T08:53:18.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552177
;

-- 2018-06-09T08:53:18.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-06-09 08:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552173
;

-- 2018-06-09T08:53:28.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2018-06-09 08:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552174
;

-- 2018-06-09T08:53:28.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2018-06-09 08:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552175
;

-- 2018-06-09T08:53:28.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2018-06-09 08:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552176
;

-- 2018-06-09T08:53:28.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2018-06-09 08:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552173
;

-- 2018-06-09T08:54:46.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:54:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Client',Description='',Help='' WHERE AD_Field_ID=564557 AND AD_Language='en_US'
;

-- 2018-06-09T08:54:58.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:54:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Organisation',Description='',Help='' WHERE AD_Field_ID=564558 AND AD_Language='en_US'
;

-- 2018-06-09T08:55:10.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:55:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Document Type',Description='',Help='' WHERE AD_Field_ID=564559 AND AD_Language='en_US'
;

-- 2018-06-09T08:55:19.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:55:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Language',Description='',Help='' WHERE AD_Field_ID=564560 AND AD_Language='en_US'
;

-- 2018-06-09T08:55:28.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:55:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Active',Description='',Help='' WHERE AD_Field_ID=564561 AND AD_Language='en_US'
;

-- 2018-06-09T08:55:38.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:55:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Translated',Description='',Help='' WHERE AD_Field_ID=564562 AND AD_Language='en_US'
;

-- 2018-06-09T08:55:42.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:55:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=564563 AND AD_Language='en_US'
;

-- 2018-06-09T08:55:55.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:55:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Print Name',Description='' WHERE AD_Field_ID=564564 AND AD_Language='en_US'
;

-- 2018-06-09T08:56:18.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-09 08:56:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Document Note',Description='',Help='' WHERE AD_Field_ID=564565 AND AD_Language='en_US'
;

-- 2018-06-09T08:56:50.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2018-06-09 08:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552174
;

-- 2018-06-09T08:56:54.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-06-09 08:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552175
;

-- 2018-06-09T08:57:00.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2018-06-09 08:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552178
;

-- 2018-06-09T08:57:06.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2018-06-09 08:57:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552180
;

-- 2018-06-09T08:57:13.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-06-09 08:57:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552178
;

-- 2018-06-09T08:57:18.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-06-09 08:57:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552174
;

-- 2018-06-09T08:57:29.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-06-09 08:57:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552172
;

-- 2018-06-09T08:57:32.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-06-09 08:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552173
;

-- 2018-06-09T08:58:51.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540445,Updated=TO_TIMESTAMP('2018-06-09 08:58:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=300
;

