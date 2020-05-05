-- 2017-09-15T17:48:12.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540366,TO_TIMESTAMP('2017-09-15 17:48:12','YYYY-MM-DD HH24:MI:SS'),100,'U','_Geschäftspartner_Dist-Orgs','Y','N','N','N','Y','Geschäftspartner Dist-Orgs','N',TO_TIMESTAMP('2017-09-15 17:48:12','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2017-09-15T17:48:12.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540366 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2017-09-15T17:49:11.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540871,291,540366,TO_TIMESTAMP('2017-09-15 17:49:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Geschäftspartner','N',10,0,TO_TIMESTAMP('2017-09-15 17:49:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:49:11.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540871 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-15T17:51:53.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540961,0,540366,TO_TIMESTAMP('2017-09-15 17:51:53','YYYY-MM-DD HH24:MI:SS'),100,'D','_Geschäftspartner_Dist-Orgs','Y','N','N','N','N','Geschäftspartner Dist-Orgs',TO_TIMESTAMP('2017-09-15 17:51:53','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner Dist-Orgs')
;

-- 2017-09-15T17:51:53.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540961 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-15T17:51:53.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540961, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540961)
;

-- 2017-09-15T17:51:53.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540901, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540902 AND AD_Tree_ID=10
;

-- 2017-09-15T17:51:53.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540901, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540961 AND AD_Tree_ID=10
;

-- 2017-09-15T17:52:20.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 17:52:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Businesspartner Dist-Orgs',WEBUI_NameBrowse='Businesspartner Dist-Orgs' WHERE AD_Menu_ID=540961 AND AD_Language='en_US'
;

-- 2017-09-15T17:52:40.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-15 17:52:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Businesspartner Dist-Orgs' WHERE AD_Window_ID=540366 AND AD_Language='en_US'
;

-- 2017-09-15T17:54:03.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541936,559953,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Kundencockpit_includedTab3',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541698,559954,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Eltern-Sponsor editierbar',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2897,559955,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2898,559956,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540049,559957,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','N','Frachtkostenpauschale',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,531088,559958,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,14,'D',0,'Y','Y','N','N','N','N','N','N','N','Kreditoren-Nr',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540364,559959,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Kundencockpit_includedTab1',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541724,559960,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung',10,'D','"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','Y','N','N','N','N','N','N','N','Lieferweg',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540377,559961,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Zielbelegart für die Umwandlung von Dokumenten',22,'D','Sie können Dokumente umwandeln (z.B. von Angebot zu Auftrag oder Rechnung). Die Umwandlung zeigt sich in der dann vorliegenden Belegart. Der Prozess wird durch Auswahl der entsprechenden Belegaktion angestossen.',0,'Y','Y','N','N','N','N','N','N','N','Zielbelegart',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4429,559962,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.',14,'D',0,'Y','Y','N','N','N','N','N','N','N','Rechnungsstellung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2906,559963,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Dun & Bradstreet - Nummer',20,'D','Für EDI verwendet - Details unter www.dnb.com/dunsno/list.htm',0,'Y','Y','N','N','N','N','N','N','N','D-U-N-S',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2910,559964,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Standard Industry Code oder sein Nachfolger NAIC - http://www.osha.gov/oshstats/sicser.html',20,'D','NAICS/SIC identifiziert einen dieser Codes der für den Geschäftspartner zutrifft',0,'Y','Y','N','N','N','N','N','N','N','NAICS/SIC',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,505271,559965,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'D','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.',0,'Y','Y','N','N','N','N','N','N','N','Preissystem',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2899,559966,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,500057,559967,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Betrag zur Bestimmung der Potofreigrenze im Lieferlauf.',14,'D',0,'Y','Y','N','N','N','N','N','N','N','PostageFreeAmt',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,54463,559968,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','N','Tax Group',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543967,559969,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','N','Shipping Notification Email',0,0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:03.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53246,559970,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,7,'D',0,'Y','Y','N','N','N','N','N','N','N','Dunning Grace Date',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:03.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3084,559971,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird',14,'D','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',0,'Y','Y','N','N','N','N','N','N','N','Zahlungsweise',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3086,559972,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu druckenden Kopien',11,'D','"Kopien" gibt die Anzahl der Kopien an, die von jedem Dokument generiert werden sollen.',0,'Y','Y','N','N','N','N','N','N','N','Kopien',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2929,559973,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if  the business partner is a sales representative or company agent',1,'D','The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.',0,'Y','Y','N','N','N','N','N','N','N','Vertriebsbeauftragter',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3085,559974,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Rules for overdue invoices',14,'D','The Dunning indicates the rules and method of dunning for past due payments.',0,'Y','Y','N','N','N','N','N','N','N','Mahnung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12406,559975,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Flat discount percentage ',26,'D',0,'Y','Y','N','N','N','N','N','N','N','Fester Rabatt %',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540365,559976,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Kundencockpit_includedTab2',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2900,559977,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,505276,559978,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','Y','N','N','N','N','N','N','N','Einkaufspreissystem',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,531087,559979,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,14,'D',0,'Y','Y','N','N','N','N','N','N','N','Debitoren-Nr',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541957,559980,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Auftrag anlegen',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3083,559981,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Klassifizierung oder Wichtigkeit',11,'D','Das "Rating" wird verwendet um Wichtigkeit zu unterscheiden.',0,'Y','Y','N','N','N','N','N','N','Y','Rating',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542631,559982,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,3,'D',0,'Y','Y','N','N','N','N','N','N','N','Potofreie Lieferung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,502674,559983,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Sammel-Lieferscheine erlaubt',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540423,559984,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Abo-Lieferungen beginnen erst, nachdem eine Bestätigung des Kunden eingegangen ist',1,'D',0,'Y','Y','N','N','N','N','N','N','N','Abo-Bestätigung notwendig',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6580,559985,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Schema to calculate the purchase trade discount percentage',14,'D',0,'Y','Y','N','N','N','N','N','N','N','Einkauf Rabatt Schema',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2893,559986,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',14,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','N','N','N','N','N','N','N','Geschäftspartner',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2909,559987,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Steuernummer',20,'D','"Steuer-ID" gibt die offizielle Steuernummer für diese Einheit an.',0,'Y','Y','N','N','N','N','N','N','N','Steuer-ID',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2927,559988,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if  this Business Partner is an employee',1,'D','The Employee checkbox indicates if this Business Partner is an Employee.  If it is selected, additional fields will display which further identify this employee.',0,'Y','Y','N','N','N','N','N','N','N','Mitarbeiter',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3080,559989,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','Y','One time transaction',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2922,559990,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Die Kosten um den Interessenten als Kunden zu gewinnen',26,'@IsEmployee@=N','D','"Akquisitionskosten" bezeichnet die Kosten, die aufgewendet wurden um den Interessenten als Kunden zu gewinnen.',0,'Y','Y','N','N','N','N','N','N','N','Akquisitionskosten',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2919,559991,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Ersten Verkaufs',14,'@IsEmployee@=N','D','"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.',0,'Y','Y','N','N','N','N','N','N','N','Erster Verkauf',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,58381,559992,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Business partner is exempt from tax on purchases',1,'D','If a business partner is exempt from tax on purchases, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.',0,'Y','Y','N','N','N','N','N','N','Y','PO Tax exempt',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2918,559993,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Kennzeichnet einen Interessenten oder Kunden',1,'@IsEmployee@=N','D','Das Selektionsfeld zeigt an, ob es sich um einen aktiven Interessenten oder Kunden handelt.',0,'Y','Y','N','N','N','N','N','N','N','Aktiver Interessent/Kunde',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2911,559994,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'D','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','Y','N','N','N','N','N','N','Y','Zusammenfassungseintrag',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2923,559995,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Erwarteter Gesamtertrag',26,'@IsEmployee@=N','D','"Möglicher Gesamtertrag" ist der voraussichtliche Ertrag in Buchführungswährung, der durch diesen Geschäftspartner generiert wird.',0,'Y','Y','N','N','N','N','N','N','N','Möglicher Gesamtertrag',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2905,559996,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Ihre Kunden- oder Lieferantennummer beim Geschäftspartner',11,'D','Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.',0,'Y','Y','N','N','N','N','N','N','Y','Referenznummer',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:04.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2904,559997,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Verkaufsvolumen in Tausend der Buchführungswährung',11,'@IsEmployee@=N','D','"Verkaufsvolumen" zeigt den Gesamtumfang der Verkäufe für einen Geschäftspartner an.',0,'Y','Y','N','N','N','N','N','N','Y','Verkaufsvolumen in 1.000',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:04.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2926,559998,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Anteil dieses Kunden in Prozent',11,'@IsEmployee@=N','D','"Anteil" zeigt den prozentualen Anteil dieses Geschäftspartners an den gelieferten Produkten an.',0,'Y','Y','N','N','N','N','N','N','N','Anteil',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541694,559999,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','N','N','N','N','N','N','N','Nachname',0,0,999,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541693,560000,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Vorname',255,'D',0,'Y','Y','N','N','N','N','N','N','N','Vorname',0,0,999,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4302,560001,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibung zur Verwendung auf Aufträgen',60,'D','"Beschreibung Auftrag" git die Standardbeschreibung an, die auf Afträgen für diesen Kunden verwendet werden soll.',0,'Y','Y','N','N','N','N','N','N','N','Beschreibung Auftrag',0,0,999,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10927,560002,0,540871,169,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'The Business Partner is another Organization for explicit Inter-Org transactions',23,'@IsEmployee@=N','D','The business partner is another organization in the system. So when performing transactions, the counter-document is created automatically. Example: You have BPartnerA linked to OrgA and BPartnerB linked to OrgB.  If you create a sales order for BPartnerB in OrgA a purchase order is created for BPartnerA in OrgB.  This allows to have explicit documents for Inter-Org transactions.',0,'Y','Y','N','N','N','N','N','N','Y','Linked Organization',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541687,560003,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','N','N','N','N','N','N','N','SO_TargetDocTypeReason',0,0,999,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4433,560004,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird',14,'D','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','Y','N','N','N','N','N','N','N','Lieferung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3082,560005,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz steuerbefreit',1,'D','If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.',0,'Y','Y','N','N','N','N','N','N','N','Steuerbefreit',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4291,560006,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz',14,'D','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.',0,'Y','Y','N','N','N','N','N','N','N','Anrede',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4432,560007,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Methode zur Berechnung von Frachtkosten',14,'D','"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.',0,'Y','Y','N','N','N','N','N','N','N','Frachtkostenberechnung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2920,560008,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Höchstsumme für Aussenstände',26,'D','The Credit Limit indicates the total amount allowed ''on account'' in primary accounting currency.  If the Credit Limit is 0, no ckeck is performed.  Credit Management is based on the Total Open Amount, which includes Vendor activities.',0,'Y','Y','N','N','N','N','N','N','N','Kreditlimit',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2924,560009,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'D','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','N','N','N','N','N','N','N','Zahlungsbedingung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2917,560010,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung',14,'D','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.',0,'Y','Y','N','N','N','N','N','N','N','Terminplan Rechnung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5826,560011,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Zahlungskondition für die Bestellung',14,'D','Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.',0,'Y','Y','N','N','N','N','N','N','N','Zahlungskondition',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4215,560012,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','N','N','N','N','N','N','N','Referenz',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4021,560013,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Möglichkeiten der Bezahlung einer Bestellung',14,'D','"Zahlungsweise" zeigt die Arten der Zahlungen für Einkäufe an.',0,'Y','Y','N','N','N','N','N','N','N','Zahlungsweise',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6579,560014,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen',14,'D','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.',0,'Y','Y','N','N','N','N','N','N','N','Rabatt Schema',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4301,560015,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Rabatt auf Rechnung und Bestellung drucken',1,'D','Zeigt an, ob der Rabtt auf dem Dokument gedruckt werden soll',0,'Y','Y','N','N','N','N','N','N','N','Rabatte drucken',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2916,560016,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschäftspartner ein Kunde ist',1,'D','The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.',0,'Y','Y','N','N','N','N','N','N','N','Kunde',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4431,560017,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'',14,'D','',0,'Y','Y','N','N','N','N','N','N','N','Aussendienst',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8167,560018,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Enable sending Document EMail',1,'D','Send emails with document attached (e.g. Invoice, Delivery Note, etc.)',0,'Y','Y','N','N','N','N','N','N','N','E-Mail senden',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8768,560019,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Parent',14,'D','The parent (organization) of the Business Partner for reporting purposes.',0,'Y','Y','N','N','N','N','N','N','N','Partner Parent',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9332,560020,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Format für das Drucken von Rechnungen',14,'D','You need to define a Print Format to print the document.',0,'Y','Y','N','N','N','N','N','N','N','Druckformat Rechnung',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10122,560021,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Mindesthaltbarkeit in Prozent, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz',11,'D','Miminum Shelf Life of products with Guarantee Date instance. If > 0 you cannot select products with a shelf life ((Guarantee Date-Today) / Guarantee Days) less than the minum shelf life, unless you select "Show All"',0,'Y','Y','N','N','N','N','N','N','N','Mindesthaltbarkeit %',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2931,560022,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Preisliste die von diesem Geschäftspartenr verwendet wird',14,'D','Gibt die Preisliste an, die von einem Lieferanten für Produkte die von dieser Organisation eingekauft werden  verwendet wird.',0,'Y','Y','N','N','N','N','N','N','N','Einkaufspreisliste',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2930,560023,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste',14,'D','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.',0,'Y','Y','N','N','N','N','N','N','N','Preisliste',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2915,560024,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist',1,'D','The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.',0,'Y','Y','N','N','N','N','N','N','N','Lieferant',0,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2894,560025,0,540871,65,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2895,560026,0,540871,88,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:05.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2901,560027,0,540871,81,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',11,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','Y','N','N','N','N','N','Suchschlüssel',30,30,1,1,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:05.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2902,560028,0,540871,236,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'@IsCompany/N@=N','D','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',40,40,999,1,TO_TIMESTAMP('2017-09-15 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4216,560029,0,540871,58,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung',60,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Name Zusatz',50,50,999,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2903,560030,0,540871,89,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,60,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2896,560031,0,540871,44,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550158,560032,0,540871,96,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','Anbauplanung',80,190,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541686,560033,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Firma',90,90,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542713,560034,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,60,'@IsCompany@=Y','D',0,'Y','Y','Y','Y','N','N','N','N','N','Y','Firmenname',100,100,999,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,502638,560035,0,540871,138,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Umsatzsteuer-ID',110,80,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4940,560036,0,540871,255,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe',14,'D','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.',0,'Y','Y','Y','Y','N','N','N','N','N','Geschäftspartnergruppe',120,110,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2914,560037,0,540871,93,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Language for this Business Partner if Multi-Language enabled',14,'D','The Language identifies the language to use for display and formatting documents. It requires, that on Client level, Multi-Lingual documents are selected and that you have created/loaded the language.',0,'Y','N','Y','Y','N','N','N','N','Y','Sprache',123,113,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548482,560038,0,540871,107,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Erhält EDI-Belege',130,120,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548483,560039,0,540871,164,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'EDI-ID des Dateiempfängers',0,'@IsEdiRecipient@=Y','D',0,'Y','Y','Y','Y','N','N','N','N','N','N','EDI-ID des Dateiempfängers',140,130,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553173,560040,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',0,'@IsEdiRecipient@=Y','D',0,'Y','Y','Y','Y','N','N','N','N','Y','"CU pro TU" bei unbestimmter Verpackungskapazität',144,134,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3081,560041,0,540871,206,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',11,'D','The URL defines an fully qualified web address like http://www.adempiere.org',0,'Y','Y','Y','Y','N','N','N','N','N','URL',160,160,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555108,560042,0,540871,206,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,11,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','Rechnung per eMail',161,161,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553170,560043,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Autom. Referenz',163,173,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553171,560044,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen',0,'@IsCreateDefaultPOReference@=''Y''','D','Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>',0,'Y','Y','Y','Y','N','N','N','N','Y','Referenz Vorgabe',165,175,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2907,560045,0,540871,102,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Mitarbeiter',11,'@IsEmployee@=N','D','Indicates the number of employees for this Business Partner.  This field displays only for Prospects.',0,'Y','Y','Y','Y','N','N','N','N','Y','Anzahl Beschäftigte',170,170,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550429,560046,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.',0,'Y','Y','Y','Y','N','N','N','N','N','Replikations-Standardwert',180,200,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,58113,50012,560047,0,540871,44,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,40,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Logo',190,180,0,999,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551767,540077,560048,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D',0,'Y','Y','Y','Y','N','N','N','N','N','MRP ausschliessen',200,210,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552450,540079,560049,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Memo Gebinde',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Memo_HU',210,250,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552451,540079,560050,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Memo Lieferung',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Memo_Delivery',220,260,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552452,540079,560051,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Memo Abrechnung',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Memo_Invoicing',230,270,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542502,540079,560052,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Memo',240,280,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4430,560053,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen',14,'D','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','Y','Y','N','N','N','N','N','N','Lieferart',250,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557022,560054,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal',14,'D','',0,'Y','Y','Y','Y','N','N','N','N','N','Sales Responsible',260,290,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557024,560055,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum Aussen',270,300,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:54:06.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557023,560056,0,540871,0,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum Innen',280,310,0,1,1,TO_TIMESTAMP('2017-09-15 17:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:54:06.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:04:37.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540872,417,540366,TO_TIMESTAMP('2017-09-15 18:04:37','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Vorgänge','N',20,1,TO_TIMESTAMP('2017-09-15 18:04:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:04:37.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540872 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-15T18:06:17.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,5844,0,540873,114,540366,TO_TIMESTAMP('2017-09-15 18:06:17','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Nutzer/ Kontakt','N',30,1,TO_TIMESTAMP('2017-09-15 18:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:06:17.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540873 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-15T18:06:49.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=5433,Updated=TO_TIMESTAMP('2017-09-15 18:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540872
;

-- 2017-09-15T18:08:30.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,2958,0,540874,293,540366,TO_TIMESTAMP('2017-09-15 18:08:30','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Adresse','N',40,1,TO_TIMESTAMP('2017-09-15 18:08:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:08:30.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540874 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-15T18:09:54.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,542717,0,540875,540148,540366,TO_TIMESTAMP('2017-09-15 18:09:54','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','N','Y','Y','N','Y','Y','N','N','N',0,'Verlauf','N',50,1,TO_TIMESTAMP('2017-09-15 18:09:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:09:54.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540875 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-15T18:09:59.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=2893,Updated=TO_TIMESTAMP('2017-09-15 18:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540875
;

-- 2017-09-15T18:10:24.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 18:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540874
;

-- 2017-09-15T18:10:29.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 18:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540873
;

-- 2017-09-15T18:10:32.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 18:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540872
;

-- 2017-09-15T18:10:35.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 18:10:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540875
;

-- 2017-09-15T18:12:24.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541704,560057,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Abo Standard Adresse',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:24.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:24.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2956,560058,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:24.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:24.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2957,560059,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:24.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2964,560060,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',20,'D','Beschreibt eine Telefon Nummer',0,'Y','Y','N','N','N','N','N','N','N','Telefon',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2965,560061,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer',20,'D','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.',0,'Y','Y','N','N','N','N','N','N','Y','Mobil',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2966,560062,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer',20,'D','The Fax identifies a facsimile number for this Business Partner or  Location',0,'Y','Y','N','N','N','N','N','N','N','Fax',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2967,560063,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'ISDN- oder Modem-Anschluss',20,'D','"ISDN" gibt einen ISDN- oder Modem-Anschluss an.',0,'Y','Y','N','N','N','N','N','N','Y','ISDN',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2968,560064,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Vertriebsgebiet',14,'D','"Vertriebsgebiet" gibt einen bestimmten Verkaufsbereich an.',0,'Y','Y','N','N','N','N','N','N','N','Vertriebsgebiet',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3434,560065,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','N','N','N','N','N','N','N','Standort',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541691,560066,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Provisionsabrechnungen werden hierhin geschickt',1,'D',0,'Y','Y','N','N','N','N','N','N','N','isCommissionTo',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541692,560067,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'An diese Adresse werden Abos geschickt',1,'D',0,'Y','Y','N','N','N','N','N','N','N','isSubscriptionTo',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541703,560068,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Provision Standard Adresse',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2954,560069,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2955,560070,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2951,560071,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2952,560072,0,540874,71,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Sektion',20,10,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2958,560073,0,540874,267,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',14,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','Y','N','N','N','Y','N','Geschäftspartner',30,20,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2960,560074,0,540874,327,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',11,'D','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',40,30,1,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548323,560075,0,540874,133,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','GLN',44,40,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2953,560076,0,540874,58,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2959,560077,0,540874,226,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift',26,'D','Das Feld "Adresse" definiert die Adressangaben eines Standortes.',0,'Y','Y','Y','Y','N','N','N','N','N','Anschrift',60,60,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,505104,560078,0,540874,301,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Anschrift',0,'D',0,'Y','Y','Y','Y','N','N','N','Y','Y','Adresse',70,70,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3091,560079,0,540874,85,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner',1,'D','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.',0,'Y','Y','Y','Y','N','N','N','N','N','Lieferstandard',110,80,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541705,560080,0,540874,92,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','Liefer Standard Adresse',120,90,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3092,560081,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner zahlt von dieser Adresse und wir senden Mahnungen an diese Adresse',1,'D','Wenn "Zahlungs-Adresse" selektiert ist, zahlt der Geschäftspartner von dieser Adresse und wir senden Mahnungen an diese Adresse.',0,'Y','Y','N','N','N','N','N','N','N','Zahlungs-Adresse',120,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3090,560082,0,540874,115,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner',1,'D','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.',0,'Y','Y','Y','Y','N','N','N','N','N','Vorbelegung Rechnung',130,100,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541706,560083,0,540874,122,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','Rechnung Standard Adresse',140,110,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549932,560084,0,540874,69,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Abladeort',146,120,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3093,560085,0,540874,135,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Erstattungs-Adresse für den Lieferanten',1,'D','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.',0,'Y','N','Y','Y','N','N','N','N','Y','EDI-Supplier-Addresse',150,130,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:25.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550430,560086,0,540874,0,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D','Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.',0,'Y','Y','Y','Y','N','N','N','N','N','Replikations-Standardwert',160,140,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:25.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13600,560087,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum der EMail-Überprüfung',20,'D',0,'Y','Y','N','N','N','N','N','Y','N','EMail überprüft',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6314,560088,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,23,'D',0,'Y','Y','N','N','N','N','N','N','N','Process Now',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7793,560089,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name/Konto (ID) im EMail-System',20,'D','The user name in the mail system is usually the string before the @ of your email address. Notwendig, wenn der EMail-SErver eine Anmeldung vor dem Versenden von EMails verlangt.',0,'Y','Y','N','N','N','N','N','N','N','EMail Nutzer-ID',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,625,560090,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,626,560091,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543414,560092,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Button that will call a process to unlock current selected account',15,'D',0,'Y','Y','N','N','N','N','N','N','N','Zugang entsperren',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,623,560093,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,624,560094,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542511,560095,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,100,'D',0,'Y','Y','N','N','N','N','N','N','N','Handynummer',0,0,999,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12401,560096,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Berechtigung über LDAP-Service',1,'D','Optional LDAP system user name for the user. If not defined, the normal Name of the user is used. This allows to use the internal (LDAP) user id (e.g. jjanke) and the normal display name (e.g. Jorg Janke).  The LDAP User Name can also be used without LDAP enables (see system window).  This would allow to sign in as jjanke and use the display name of Jorg Janke.',0,'Y','Y','N','N','N','N','N','N','N','Berechtigen über LDAP',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7794,560097,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Passwort Ihrer EMail Nutzer-ID',20,'D','Notwendig, wenn der EMail-Server eine Anmeldung vor dem Versenden von EMails verlangt.',0,'Y','Y','N','N','N','N','N','N','N','Passwort EMail-Nutzer',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14336,560098,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'The user/contact has full access to Business Partner information and resources',1,'D','If selected, the user has full access to the Business Partner (BP) information (Business Documents like Orders, Invoices - Requests) or resources (Assets, Downloads). If you deselet it, the user has no access rights unless, you explicitly grant it in tab "BP Access"',0,'Y','Y','N','N','N','N','N','N','Y','Vollzugriff Geschäftspartner',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,212,560099,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','N','N','N','N','N','N','N','Ansprechpartner',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13773,560100,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Art der Benachrichtigung',14,'D','EMail oder Nachricht bei aktualisierter Anfrage usw.',0,'Y','Y','N','N','N','N','N','N','Y','Benachrichtigungs-Art',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542513,560101,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','N','N','N','N','N','N','N','Begründung',0,0,999,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543407,560102,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Handelsregister',255,'D',0,'Y','Y','N','N','N','N','N','N','N','Handelsregister',0,0,999,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15975,560103,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','N','N','N','N','N','N','N','Suchschlüssel',0,0,999,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543412,560104,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Client IP address that was used when this account was locked ',20,'D',0,'Y','Y','N','N','N','N','N','N','N','Sperrung von IP',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543413,560105,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum Login Fehler',20,'D',0,'Y','Y','N','N','N','N','N','N','N','Datum Login Fehler',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543410,560106,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl Login Fehlversuche',2,'D',0,'Y','Y','N','N','N','N','N','N','N','Anzahl Login Fehlversuche',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,52066,560107,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,20,'D',0,'Y','Y','N','N','N','N','N','N','N','Benutzer PIN',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541639,560108,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Included Tab in this Tab (Master Dateail)',1,'D','You can include a Tab in a Tab. If displayed in single row record, the included tab is displayed as multi-row table.',0,'Y','Y','N','N','N','N','N','N','N','Included Tab',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56294,560109,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Defined if any User Contact will be used for Calculate Payroll',1,'D',0,'Y','Y','N','N','N','N','N','N','N','Is In Payroll',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543411,560110,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Kennzeichen das anzeigt ob der Zugang gesperrt wurde',1,'D',0,'Y','Y','N','N','N','N','N','N','N','Zugang gesperrt',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541897,560111,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','IsSubjectMatterContact',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542512,560112,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','N','N','N','N','N','N','N','Kontakt Einschränkung',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:34.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543402,560113,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,7,'D',0,'Y','Y','N','N','N','N','N','N','N','Löschdatum',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:34.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542515,560114,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100,40,'D',0,'Y','Y','N','N','N','N','N','N','N','Portalpasswort',0,0,999,1,TO_TIMESTAMP('2017-09-15 18:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14619,560115,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Auf welche Weise der Java-Client mit dem Server verbunden ist',1,'D','Depending on the connection profile, different protocols are used and tasks are performed on the server rather then the client. Usually the user can select different profiles, unless it is enforced by the User or Role definition. The User level profile overwrites the Role based profile.',0,'Y','Y','N','N','N','N','N','N','N','Verbindungsart',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8976,560116,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Durchführende oder auslösende Organisation',14,'D','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.',0,'Y','Y','N','N','N','N','N','N','N','Buchende Organisation',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5397,560117,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Vorgesetzter oder Übergeordneter für diesen Nutzer oder diese Organisation - verwendet für Eskalation und Freigabe',26,'D','"Vorgesetzter" zeigt an, wer für Weiterleitung und Eskalation von Vorfällen dieses Nutzers verwendet wird - oder für Freigaben.',0,'Y','Y','N','N','N','N','N','N','N','Vorgesetzter',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,422,560118,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,423,560119,0,540873,73,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Sektion',20,10,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5844,560120,0,540873,297,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','Y','N','N','N','Y','N','Geschäftspartner',30,20,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8743,560121,0,540873,55,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz',14,'D','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.',0,'Y','Y','Y','Y','N','N','N','N','N','Anrede',40,30,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541684,560122,0,540873,109,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Vorname',255,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Vorname',50,40,999,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541683,560123,0,540873,106,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','Nachname',60,50,999,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,213,560124,0,540873,146,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'D','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',70,60,1,999,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,214,560125,0,540873,128,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,60,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Beschreibung',80,70,999,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8752,560126,0,540873,122,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information',60,'D','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.',0,'Y','Y','Y','Y','N','N','N','N','N','Bemerkungen',90,80,999,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,622,560127,0,540873,44,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',100,90,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,500036,560128,0,540873,157,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Standard-Ansprechpartner',110,100,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549866,560129,0,540873,129,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','N','Weihnachtsgeschenk',120,110,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551164,560130,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Weihnachtsgeschenk',121,111,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5396,560131,0,540873,200,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.',0,'Y','Y','Y','Y','N','N','N','N','N','eMail',130,120,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,417,560132,0,540873,68,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Passwort beliebiger Länge (unterscheided Groß- und Kleinschreibung)',20,'D','The Password for this User.  Passwords are required to identify authorized users.  For Adempiere Users, you can change the password via the Process "Reset Password".',0,'Y','Y','Y','Y','Y','N','N','N','Y','Kennwort',140,130,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554318,560133,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Mengenmeldung App',145,135,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8751,560134,0,540873,40,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag',20,'D','"Titel" gibt die Bezeichnung für diesen Eintrag an.',0,'Y','Y','Y','Y','N','N','N','N','N','Titel',150,140,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8745,560135,0,540873,75,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag',14,'D','Geburtstag oder Jahrestag',0,'Y','Y','Y','Y','N','N','N','N','Y','Geburtstag',160,150,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8747,560136,0,540873,123,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',20,'D','Beschreibt eine Telefon Nummer',0,'Y','Y','Y','Y','N','N','N','N','N','Telefon',170,160,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8744,560137,0,540873,117,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer',20,'D','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.',0,'Y','Y','Y','Y','N','N','N','N','Y','Mobil',180,170,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560137 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8748,560138,0,540873,93,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer',20,'D','The Fax identifies a facsimile number for this Business Partner or  Location',0,'Y','Y','Y','Y','N','N','N','N','N','Fax',190,180,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14396,560139,0,540873,60,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Position in der Firma',1,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Position',200,190,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9884,560140,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Überprüfung der EMail-Adresse',20,'D','Dieses Feld enthält das Datum, an dem die EMail-Adresse verifiziert wurde.',0,'Y','Y','N','N','N','N','N','Y','Y','Überprüfung EMail',210,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8746,560141,0,540873,62,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','Y','N','N','N','N','Y','Standort',240,200,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550221,560142,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Einkaufskontakt',250,210,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556074,560143,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsPurchaseContact@ = ''Y''','D',0,'Y','Y','Y','Y','N','N','N','N','N','IsPurchaseContact_Default',255,215,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541898,560144,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','Y','Y','N','N','N','N','N','IsSalesContact',260,260,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556073,560145,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,1,'@IsSalesContact@ = ''Y'' ','D',0,'Y','Y','Y','Y','N','N','N','N','N','IsSalesContact_Default',265,265,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555109,560146,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Rechnung per eMail',270,220,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:35.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556333,560147,0,540873,0,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.',0,'@IsSystemUser@ = ''Y''','D','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.',0,'Y','Y','Y','Y','N','N','N','N','Y','Druck-Empfänger',280,275,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:35.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13488,114,560148,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',14,'D','The Role determines security and access a user who has this Role will have in the System.',0,'Y','Y','N','N','N','N','N','N','Y','Rolle',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13575,560149,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',20,'D','The Start Date indicates the first or starting date',0,'Y','Y','N','N','N','N','N','N','N','Anfangsdatum',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13496,114,560150,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die bereits in Rechnung gestellt wurde',26,'@IsInvoiced@=Y','D',0,'Y','Y','N','N','N','N','N','N','Y','Berechn. Menge',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13494,114,560151,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'End of the time span',20,'D',0,'Y','Y','N','N','N','N','N','N','Y','Enddatum',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13493,114,560152,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'',20,'D',0,'Y','Y','N','N','N','N','N','N','N','Startdatum',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13495,114,560153,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Quantity used for this event',26,'D',0,'Y','Y','N','N','N','N','N','N','Y','Quantity Used',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5418,101,560154,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','N','N','N','N','N','N','N','Aktiv',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12927,105,560155,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Date when last alert were sent',14,'D','The last alert date is updated when a reminder email is sent',0,'Y','Y','N','N','N','N','N','Y','Y','Last Alert',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555043,114,560156,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','Y','N','Request Type Interner Name',0,0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13497,114,560157,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Product/Resource/Service used in Request',26,'D','Invoicing uses the Product used.',0,'Y','Y','N','N','N','N','N','N','N','Product Used',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13489,560158,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?',1,'D','If selected, invoices are created',0,'Y','Y','N','N','N','N','N','N','Y','Invoiced',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13499,560159,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'The generated invoice for this request',14,'D','The optionally generated invoice for the request',0,'Y','Y','N','N','N','N','N','Y','Y','Request Invoice',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13576,560160,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Close Date',20,'D','The Start Date indicates the last or final date',0,'Y','Y','N','N','N','N','N','N','N','Close Date',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13487,560161,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Type of Confidentiality',14,'D',0,'Y','Y','N','N','N','N','N','N','N','Confidentiality',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:46.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5425,104,560162,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Amount associated with this request',26,'D','The Request Amount indicates any amount that is associated with this request.  For example, a warranty amount or refund amount.',0,'Y','Y','N','N','N','N','N','N','N','Request Amount',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:46.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13491,114,560163,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Confidentiality of the individual entry',14,'D',0,'Y','Y','N','N','N','N','N','N','Y','Entry Confidentiality',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5429,560164,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'This request has been escalated',1,'D','The Escalated checkbox indicates that this request has been escalated or raised in importance.',0,'Y','Y','N','N','N','N','N','Y','N','Escalated',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10580,560165,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Asset used internally or by customers',26,'D','An asset is either created by purchasing or by delivering a product.  An asset can be used internally or be a customer asset.',0,'Y','Y','N','N','N','N','N','N','Y','Asset',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13483,560166,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Request Category',14,'D','Category or Topic of the Request ',0,'Y','Y','N','N','N','N','N','N','N','Category',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8774,560167,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'This is a Self-Service entry or this entry can be changed via Self-Service',1,'D','Self-Service allows users to enter data or update their data.  The flag indicates, that this record was entered or created via Self-Service or that the user can change it via the Self-Service functionality.',0,'Y','Y','N','N','N','N','N','Y','Y','Self-Service',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13490,560168,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Related Request (Master Issue, ..)',14,'D','Request related to this request',0,'Y','Y','N','N','N','N','N','N','Y','Related Request',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13078,560169,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',14,'@AD_Table_ID@!0','D','The Database Table provides the information of the table definition',0,'Y','Y','N','N','N','N','N','Y','N','DB-Tabelle',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5441,114,560170,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Text templates for mailings',14,'D','The Mail Template indicates the mail template for return messages. Mail text can include variables.  The priority of parsing is User/Contact, Business Partner and then the underlying business object (like Request, Dunning, Workflow object).<br>
So, @Name@ would resolve into the User name (if user is defined defined), then Business Partner name (if business partner is defined) and then the Name of the business object if it has a Name.<br>
For Multi-Lingual systems, the template is translated based on the Business Partner''s language selection.',0,'Y','Y','N','N','N','N','N','N','Y','Mail Template',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5416,560171,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5417,560172,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7791,560173,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'','Type of request (e.g. Inquiry, Complaint, ..)',14,'D','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.',0,'Y','Y','Y','Y','N','N','N','N','N','Request Type',25,25,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5433,560174,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','Y','N','N','N','N','N','Geschäftspartner',30,30,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5423,560175,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',20,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','Y','N','N','N','N','Y','Nr.',40,40,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5434,560176,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','Y','N','N','N','N','Y','Ansprechpartner',50,50,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556050,560177,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Zulieferant',55,55,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5799,560178,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','Y','N','N','N','N','N','Project',60,60,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5436,560179,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',26,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','Y','N','N','N','N','N','Auftrag',70,70,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555039,560180,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde',0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','D',0,'Y','Y','Y','Y','N','N','N','N','Y','Lieferdatum',80,80,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5437,560181,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','The Invoice Document.',0,'Y','Y','Y','Y','N','N','N','N','Y','Rechnung',90,90,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5439,560182,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',26,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Produkt',100,100,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5438,560183,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).',0,'Y','Y','Y','Y','N','N','N','N','Y','Zahlung',110,110,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5435,560184,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@$Element_MC@=Y','D','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','Y','N','N','N','N','Y','Werbemassnahme',115,115,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560184 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13573,560185,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',26,'D','The Material Shipment / Receipt ',0,'Y','Y','Y','Y','N','N','N','N','N','Lieferung/Wareneingang',120,120,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13574,560186,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','A Return Material Authorization may be required to accept returns and to create Credit Memos',0,'Y','Y','Y','Y','N','N','N','N','Y','RMA',130,130,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560186 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555040,560187,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','D',0,'Y','Y','Y','Y','N','N','N','N','Y','Rücklieferung',140,140,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560187 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13079,560188,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',23,'@AD_Table_ID@!0','D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Datensatz-ID',150,150,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560188 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13498,114,560189,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','Y','N','N','N','N','N','Kostenstelle',160,160,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:47.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5445,114,560190,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','The Date Next Action indicates the next scheduled date for an action to occur for this request.',0,'Y','Y','Y','Y','N','N','N','N','N','Date next action',180,180,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:47.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560190 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5427,114,560191,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','The Due Type indicates if this request is Due, Overdue or Scheduled.',0,'Y','Y','Y','Y','N','N','N','N','Y','Due type',190,190,1,1,TO_TIMESTAMP('2017-09-15 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560191 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13482,114,560192,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Request Group',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','Group of requests (e.g. version numbers, responsibility, ...)',0,'Y','Y','Y','Y','N','N','N','N','Y','Group',220,220,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560192 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13485,114,560193,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution',14,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D','Resolution status (e.g. Fixed, Rejected, ..)',0,'Y','Y','Y','Y','N','N','N','N','N','Resolution',240,240,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5426,114,560194,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.',14,'D','The Priority indicates the importance of this request.',0,'Y','Y','Y','Y','N','N','N','N','N','Priority',260,260,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555046,114,560195,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','D',0,'Y','Y','Y','Y','N','N','N','N','Y','PerformanceType',265,265,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13486,114,560196,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','D',0,'Y','Y','Y','Y','N','N','N','N','Y','User Importance',270,270,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555041,114,560197,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','D',0,'Y','Y','Y','Y','N','N','N','N','N','Qualität-Notiz',275,275,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5428,114,560198,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request',60,'D','The Summary allows free form text entry of a recap of this request.',0,'Y','Y','Y','Y','N','N','N','N','N','Summary',280,280,999,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5444,114,560199,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken',14,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','D','The Next Action indicates the next action to be taken on this request.',0,'Y','Y','Y','Y','N','N','N','N','N','Next action',285,285,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5432,114,560200,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'',14,'D','',0,'Y','Y','Y','Y','N','N','N','Y','N','N','Aussendienst',290,290,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13492,114,560201,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ',14,'D','Text blocks to be copied into request response text',0,'Y','Y','Y','Y','N','N','N','N','N','Standard Response',300,300,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5443,114,560202,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken',60,'D','The Result indicates the result of any action taken on this request.',0,'Y','Y','Y','Y','N','N','N','N','N','Ergebnis',310,310,999,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13484,114,560203,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',14,'D','Status if the request (open, closed, investigating, ..)',0,'Y','Y','Y','Y','N','N','N','N','N','Status',320,320,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5430,105,560204,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on',20,'D','The Date Last Action indicates that last time that the request was acted on.',0,'Y','Y','Y','Y','N','N','N','Y','N','Date last action',340,340,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,Included_Tab_ID,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5415,105,560205,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Request from a Business Partner or Prospect',14,'D','The Request identifies a unique request from a Business Partner or Prospect.',0,740,'Y','Y','Y','Y','N','N','N','N','N','Aufgabe',350,350,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5431,105,560206,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact',60,'D','The Last Result identifies the result of the last contact made.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Last Result',355,355,999,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5419,105,560207,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',20,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','Y','N','N','N','Y','N','Erstellt',360,360,-1,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5420,105,560208,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',14,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Erstellt durch',370,370,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5421,105,560209,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',20,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','Y','N','N','N','Y','N','Aktualisiert',380,380,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5422,105,560210,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',14,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Aktualisiert durch',390,390,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5447,105,560211,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'1=2','D','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','Y','N','N','N','N','N','Y','Y','Verarbeitet',400,400,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557025,560212,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum',410,410,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557045,560213,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',0,'D','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','Y','N','N','N','N','N','Vorgangsdatum',420,420,-1,1,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:48.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540359,560214,0,540872,0,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100,40,'D',0,'Y','Y','N','N','N','N','N','N','N','Actual Phone',999,1,TO_TIMESTAMP('2017-09-15 18:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:48.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:58.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542720,560215,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','N','N','N','Mandant',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:58.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:58.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542717,560216,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','N','N','N','N','N','N','N','Geschäftspartner',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:58.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:58.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542727,560217,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',14,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','N','N','N','N','N','N','N','Datensatz-ID',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:58.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542726,560218,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',14,'D','The Database Table provides the information of the table definition',0,'Y','Y','N','N','N','N','N','N','N','DB-Tabelle',0,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542722,560219,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','Y','N','N','N','N','N','Erstellt',10,10,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560219 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542719,560220,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,30,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Typ',20,20,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542718,560221,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',30,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','Y','N','N','N','N','N','Nr.',30,30,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542724,560222,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktualisiert',40,40,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542725,560223,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktualisiert durch',50,50,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542723,560224,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','Y','N','N','N','N','N','Erstellt durch',60,60,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542728,560225,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Beschreibung',70,70,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560225 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542721,560226,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','N','N','Sektion',80,80,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560226 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557049,560227,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','x_bpartner_history_id',90,90,0,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560227 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:12:59.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557078,560228,0,540875,0,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',0,'D','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','Y','N','N','N','N','N','Vorgangsdatum',100,100,1,1,1,TO_TIMESTAMP('2017-09-15 18:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:12:59.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560228 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:14.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,546056,0,540876,540320,540366,TO_TIMESTAMP('2017-09-15 18:25:14','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','N','Y','Y','N','Y','Y','N','N','N',0,'Abos','N',20,1,TO_TIMESTAMP('2017-09-15 18:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:14.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540876 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-15T18:25:49.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545640,560229,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Dient der Zusammenfassung ähnlicher Maßeinheiten',2,'U','Y','Y','Y','N','N','N','N','N','Einheiten-Typ',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560229 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545795,560230,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545796,560231,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545797,560232,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545798,560233,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545799,560234,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545800,560235,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545801,560236,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545802,560237,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545803,560238,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Datenerfassung',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545806,560239,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Vertragsbedingungen',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560239 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545809,560240,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs',2,'U','You find the current status in the Document Status field. The options are listed in a popup','Y','Y','Y','N','N','N','N','N','Belegverarbeitung',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545810,560241,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'U','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','Y','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545811,560242,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545812,560243,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','Y','Y','N','N','N','N','N','Process Now',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545815,560244,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen',1,'U','Im Fall der Gegegenüberstellung von zu pauschal abgerechenten Beträgen und tatsächlich erbrachten Leistungen kann eine Verrechnung mit eventueller Nachzahlung oder Rückerstattung erfolgen.','Y','Y','Y','N','N','N','N','N','Gegenüberstellung mit erbr. Leist.',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545816,560245,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können',1,'U','Y','Y','Y','N','N','N','N','N','Abschlusskorrektur vorsehen',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545823,560246,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes',1,'U','Ob und welche Datensätze erstellt werden, hängt von den gewählten Vertragsbedingungen ab','Y','Y','Y','N','N','N','N','N','Abschlusskorrektur vorbereiten',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545858,560247,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Vertragsart',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545989,560248,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).',14,'U','Y','Y','Y','N','N','N','N','N','Planmenge pro Maßeinheit',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,545993,560249,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','Y','Y','N','N','N','N','N','Planspiel',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546003,560250,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'U','Eine eindeutige (nicht monetäre) Maßeinheit','Y','Y','Y','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:49.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546013,560251,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information für ein Dokument',2000,'U','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','Y','Y','N','N','N','N','N','Notiz',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:49.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546018,560252,0,540876,TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert',1,'U','Y','Y','Y','N','N','N','N','N','Vertrag autom. verlängern',TO_TIMESTAMP('2017-09-15 18:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546020,560253,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Nachfolgende Vertragsperiode',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546040,560254,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.',10,'U','Y','Y','Y','N','N','N','N','N','Vertragsverlängerung/-übergang',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546044,560255,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',7,'U','The Start Date indicates the first or starting date','Y','Y','Y','N','N','N','N','N','Anfangsdatum',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546045,560256,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)',7,'U','The End Date indicates the last date in this range.','Y','Y','Y','N','N','N','N','N','Enddatum',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546046,560257,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.',7,'U','Y','Y','Y','N','N','N','N','N','Kündigungs/Benachrichtigungsfrist',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546048,560258,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Verarbeitung zum Laufzeitende',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546050,560259,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','Y','Y','N','N','N','N','N','Vertrag jetzt verlängern',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546054,560260,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'U','Y','Y','Y','N','N','N','N','N','Rechnungsstandort',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546055,560261,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'U','Y','Y','Y','N','N','N','N','N','Rechnungskontakt',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546056,560262,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartners für die Rechnungsstellung',10,'U','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','Y','Y','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546058,560263,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.',10,'U','Y','Y','Y','N','N','N','N','N','Betreuer',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546113,560264,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen',4,'U','Wenn eine Gegenüberstellung mit tatsächliche erbrachten Leistungen vorgesehen ist, dann definiert dieses Feld, ob eine ggf. eine Abweichung in Rechnung gestellt werden kann.','Y','Y','Y','N','N','N','N','N','Verrechnungsart',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547282,560265,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Auftragszeile, mit der der Vertrag abgeschlossen wurde',10,'U','Y','Y','Y','N','N','N','N','N','Vertrags-Auftragszeile',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547283,560266,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547296,560267,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to',10,'U','If empty the business partner will be shipped to.','Y','Y','Y','N','N','N','N','N','Lieferempfänger',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547297,560268,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to',10,'U','Y','Y','Y','N','N','N','N','N','Lieferadresse',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547298,560269,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Contact for drop shipment',10,'U','Y','Y','Y','N','N','N','N','N','Lieferkontakt',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547302,560270,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,2,'U','Y','Y','Y','N','N','N','N','N','Vertrags-Status',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547303,560271,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','Y','Y','N','N','N','N','N','Portofrei',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547317,560272,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','Y','Y','N','N','N','N','N','Ändern oder Kündigen',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547318,560273,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde',10,'U','Y','Y','Y','N','N','N','N','N','Änderungs-Auftragszeile',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547319,560274,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde',10,'U','Y','Y','Y','N','N','N','N','N','Änderungs-Auftrag',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547320,560275,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag, mit der der Vertrag abgeschlossen wurde',10,'U','Y','Y','Y','N','N','N','N','N','Vertrags-Auftrag',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547322,560276,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen',1,'U','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','Y','Y','N','N','N','N','N','Lieferart',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547323,560277,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird',2,'U','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','Y','Y','N','N','N','N','N','Lieferung',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547758,560278,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob das System beim Fertigstellen einer neuen Vertragslaufzeit (z.B. bei automatischer Verlängerung) eine Auftragsbestätigung erzeugt.',1,'U','Y','Y','Y','N','N','N','N','N','AB bei neuer Vertragslaufzeit',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547761,560279,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'U','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','Y','Y','N','N','N','N','N','Preissystem',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547763,560280,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis',14,'U','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','Y','Y','N','N','N','N','N','Einzelpreis',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560280 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,548289,560281,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',25,'U','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','Y','N','N','N','N','N','Währung',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550678,560282,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','Y','Y','N','N','N','N','N','Rechnungskandidat schließen',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560282 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:50.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554317,560283,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Planmenge Folgejahr',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:50.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560283 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:51.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554320,560284,0,540876,TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','Y','Y','N','N','N','N','N','Lieferprodukt',TO_TIMESTAMP('2017-09-15 18:25:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:51.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560284 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:51.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554466,560285,0,540876,TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100,'Request for Quotation Response Line',10,'U','Request for Quotation Response Line from a potential Vendor','Y','Y','Y','N','N','N','N','N','RfQ Response Line',TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:51.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560285 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:51.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557139,560286,0,540876,TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100,7,'U','Y','Y','Y','N','N','N','N','N','Master Start Date',TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:51.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560286 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:51.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557140,560287,0,540876,TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100,7,'U','Y','Y','Y','N','N','N','N','N','Master End Date',TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:51.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560287 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:51.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557169,560288,0,540876,TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100,7,'U','Y','Y','Y','N','N','N','N','N','Vertrag Datum',TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:51.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560288 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:25:51.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557170,560289,0,540876,TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100,30,'U','Y','Y','Y','N','N','N','N','N','Vertrag Nr.',TO_TIMESTAMP('2017-09-15 18:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:25:51.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560289 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T18:28:25.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540871,540483,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-09-15T18:28:25.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540483 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:28:25.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540650,540483,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:25.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540651,540483,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:25.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540650,541145,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560025,0,540871,541145,548475,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-09-15 18:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560026,0,540871,541145,548476,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560027,0,540871,541145,548477,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560028,0,540871,541145,548478,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560029,0,540871,541145,548479,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','Y','Y','N','Name Zusatz',50,50,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560030,0,540871,541145,548480,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',60,60,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560031,0,540871,541145,548481,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',70,70,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560032,0,540871,541145,548482,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Anbauplanung',80,80,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560033,0,540871,541145,548483,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firma',90,90,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560034,0,540871,541145,548484,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firmenname',100,100,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560035,0,540871,541145,548485,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Umsatzsteuer-ID',110,110,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560036,0,540871,541145,548486,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','Y','N','Geschäftspartnergruppe',120,120,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560037,0,540871,541145,548487,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Language for this Business Partner if Multi-Language enabled','The Language identifies the language to use for display and formatting documents. It requires, that on Client level, Multi-Lingual documents are selected and that you have created/loaded the language.','Y','N','Y','Y','N','Sprache',130,130,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560038,0,540871,541145,548488,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege','Y','N','Y','Y','N','Erhält EDI-Belege',140,140,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560039,0,540871,541145,548489,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'EDI-ID des Dateiempfängers','Y','N','Y','Y','N','EDI-ID des Dateiempfängers',150,150,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560040,0,540871,541145,548490,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.','Y','N','Y','Y','N','"CU pro TU" bei unbestimmter Verpackungskapazität',160,160,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560041,0,540871,541145,548491,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org','The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','Y','Y','N','URL',170,170,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560042,0,540871,541145,548492,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Rechnung per eMail',180,180,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560043,0,540871,541145,548493,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.','Y','N','Y','Y','N','Autom. Referenz',190,190,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560044,0,540871,541145,548494,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen','Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>','Y','N','Y','Y','N','Referenz Vorgabe',200,200,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560045,0,540871,541145,548495,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Mitarbeiter','Indicates the number of employees for this Business Partner.  This field displays only for Prospects.','Y','N','Y','Y','N','Anzahl Beschäftigte',210,210,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560046,0,540871,541145,548496,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','Y','Y','N','Replikations-Standardwert',220,220,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560047,0,540871,541145,548497,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Logo',230,230,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560048,0,540871,541145,548498,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','MRP ausschliessen',240,240,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560049,0,540871,541145,548499,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Memo Gebinde','Y','N','Y','Y','N','Memo_HU',250,250,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560050,0,540871,541145,548500,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Memo Lieferung','Y','N','Y','Y','N','Memo_Delivery',260,260,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560051,0,540871,541145,548501,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Memo Abrechnung','Y','N','Y','Y','N','Memo_Invoicing',270,270,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560052,0,540871,541145,548502,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text','Y','N','Y','Y','N','Memo',280,280,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560053,0,540871,541145,548503,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','N','Y','N','N','Lieferart',290,0,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560054,0,540871,541145,548504,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal','','Y','N','Y','Y','N','Sales Responsible',300,290,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560055,0,540871,541145,548505,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Wiedervorlage Datum Aussen',310,300,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560056,0,540871,541145,548506,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Wiedervorlage Datum Innen',320,310,0,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:26.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540876,540484,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-09-15T18:28:26.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540484 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:28:27.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540652,540484,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540652,541146,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560229,0,540876,541146,548507,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Dient der Zusammenfassung ähnlicher Maßeinheiten','Y','N','N','Y','N','Einheiten-Typ',0,10,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560230,0,540876,541146,548508,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,20,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560231,0,540876,541146,548509,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,30,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560232,0,540876,541146,548510,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,40,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560233,0,540876,541146,548511,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,50,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560234,0,540876,541146,548512,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560235,0,540876,541146,548513,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,70,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560236,0,540876,541146,548514,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,80,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560238,0,540876,541146,548515,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Datenerfassung',0,90,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560239,0,540876,541146,548516,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertragsbedingungen',0,100,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560240,0,540876,541146,548517,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs','You find the current status in the Document Status field. The options are listed in a popup','Y','N','N','Y','N','Belegverarbeitung',0,110,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560241,0,540876,541146,548518,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','N','Belegstatus',0,120,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560242,0,540876,541146,548519,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','Verarbeitet',0,130,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560243,0,540876,541146,548520,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Process Now',0,140,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560244,0,540876,541146,548521,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen','Im Fall der Gegegenüberstellung von zu pauschal abgerechenten Beträgen und tatsächlich erbrachten Leistungen kann eine Verrechnung mit eventueller Nachzahlung oder Rückerstattung erfolgen.','Y','N','N','Y','N','Gegenüberstellung mit erbr. Leist.',0,150,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560245,0,540876,541146,548522,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können','Y','N','N','Y','N','Abschlusskorrektur vorsehen',0,160,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560246,0,540876,541146,548523,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes','Ob und welche Datensätze erstellt werden, hängt von den gewählten Vertragsbedingungen ab','Y','N','N','Y','N','Abschlusskorrektur vorbereiten',0,170,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560247,0,540876,541146,548524,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertragsart',0,180,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560248,0,540876,541146,548525,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).','Y','N','N','Y','N','Planmenge pro Maßeinheit',0,190,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560249,0,540876,541146,548526,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Planspiel',0,200,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560250,0,540876,541146,548527,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,210,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560251,0,540876,541146,548528,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information für ein Dokument','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','N','Notiz',0,220,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560252,0,540876,541146,548529,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert','Y','N','N','Y','N','Vertrag autom. verlängern',0,230,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560253,0,540876,541146,548530,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Nachfolgende Vertragsperiode',0,240,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560254,0,540876,541146,548531,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.','Y','N','N','Y','N','Vertragsverlängerung/-übergang',0,250,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560255,0,540876,541146,548532,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)','The Start Date indicates the first or starting date','Y','N','N','Y','N','Anfangsdatum',0,260,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560256,0,540876,541146,548533,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','The End Date indicates the last date in this range.','Y','N','N','Y','N','Enddatum',0,270,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560257,0,540876,541146,548534,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.','Y','N','N','Y','N','Kündigungs/Benachrichtigungsfrist',0,280,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560258,0,540876,541146,548535,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Verarbeitung zum Laufzeitende',0,290,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560259,0,540876,541146,548536,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertrag jetzt verlängern',0,300,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560260,0,540876,541146,548537,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung','Y','N','N','Y','N','Rechnungsstandort',0,310,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560261,0,540206,548537,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560262,0,540207,548537,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:27.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560263,0,540876,541146,548538,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.','Y','N','N','Y','N','Betreuer',0,320,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560264,0,540876,541146,548539,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen','Wenn eine Gegenüberstellung mit tatsächliche erbrachten Leistungen vorgesehen ist, dann definiert dieses Feld, ob eine ggf. eine Abweichung in Rechnung gestellt werden kann.','Y','N','N','Y','N','Verrechnungsart',0,330,0,TO_TIMESTAMP('2017-09-15 18:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560265,0,540876,541146,548540,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Auftragszeile, mit der der Vertrag abgeschlossen wurde','Y','N','N','Y','N','Vertrags-Auftragszeile',0,340,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560266,0,540876,541146,548541,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,350,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560267,0,540876,541146,548542,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to','If empty the business partner will be shipped to.','Y','N','N','Y','N','Lieferempfänger',0,360,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560268,0,540208,548542,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560269,0,540209,548542,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560270,0,540876,541146,548543,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertrags-Status',0,370,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560271,0,540876,541146,548544,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Portofrei',0,380,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560272,0,540876,541146,548545,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Ändern oder Kündigen',0,390,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560273,0,540876,541146,548546,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde','Y','N','N','Y','N','Änderungs-Auftragszeile',0,400,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560274,0,540876,541146,548547,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde','Y','N','N','Y','N','Änderungs-Auftrag',0,410,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560275,0,540876,541146,548548,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag, mit der der Vertrag abgeschlossen wurde','Y','N','N','Y','N','Vertrags-Auftrag',0,420,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560276,0,540876,541146,548549,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','N','N','Y','N','Lieferart',0,430,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560277,0,540876,541146,548550,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','N','Y','N','Lieferung',0,440,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560278,0,540876,541146,548551,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob das System beim Fertigstellen einer neuen Vertragslaufzeit (z.B. bei automatischer Verlängerung) eine Auftragsbestätigung erzeugt.','Y','N','N','Y','N','AB bei neuer Vertragslaufzeit',0,450,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560279,0,540876,541146,548552,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','Preissystem',0,460,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560280,0,540876,541146,548553,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','Y','N','Einzelpreis',0,470,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560281,0,540876,541146,548554,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','Währung',0,480,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560282,0,540876,541146,548555,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnungskandidat schließen',0,490,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560283,0,540876,541146,548556,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Planmenge Folgejahr',0,500,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560284,0,540876,541146,548557,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Lieferprodukt',0,510,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560285,0,540876,541146,548558,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Request for Quotation Response Line','Request for Quotation Response Line from a potential Vendor','Y','N','N','Y','N','RfQ Response Line',0,520,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560286,0,540876,541146,548559,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Master Start Date',0,530,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560287,0,540876,541146,548560,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Master End Date',0,540,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560288,0,540876,541146,548561,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertrag Datum',0,550,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560289,0,540876,541146,548562,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Vertrag Nr.',0,560,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540874,540485,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-09-15T18:28:28.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540485 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:28:28.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540653,540485,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:28.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540653,541147,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560072,0,540874,541147,548563,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,10,0,TO_TIMESTAMP('2017-09-15 18:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560073,0,540874,541147,548564,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,20,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560074,0,540874,541147,548565,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,30,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560075,0,540874,541147,548566,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','GLN',0,40,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560076,0,540874,541147,548567,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560077,0,540874,541147,548568,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','N','Y','N','Anschrift',0,60,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560078,0,540874,541147,548569,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Anschrift','Y','N','N','Y','N','Adresse',0,70,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560079,0,540874,541147,548570,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','N','Y','N','Lieferstandard',0,80,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560080,0,540874,541147,548571,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Liefer Standard Adresse',0,90,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560082,0,540874,541147,548572,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','Y','N','N','Y','N','Vorbelegung Rechnung',0,100,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560083,0,540874,541147,548573,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnung Standard Adresse',0,110,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560084,0,540874,541147,548574,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Abladeort',0,120,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560085,0,540874,541147,548575,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Erstattungs-Adresse für den Lieferanten','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.','Y','N','N','Y','N','EDI-Supplier-Addresse',0,130,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560086,0,540874,541147,548576,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','N','Y','N','Replikations-Standardwert',0,140,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540873,540486,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-09-15T18:28:29.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540486 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:28:29.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540654,540486,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:29.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540654,541148,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560119,0,540873,541148,548577,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,10,0,TO_TIMESTAMP('2017-09-15 18:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560120,0,540873,541148,548578,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,20,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560121,0,540873,541148,548579,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','Y','N','Anrede',0,30,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560122,0,540873,541148,548580,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','N','Y','N','Vorname',0,40,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560123,0,540873,541148,548581,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Nachname',0,50,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560124,0,540873,541148,548582,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,60,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560125,0,540873,541148,548583,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,70,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560126,0,540873,541148,548584,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','N','N','Y','N','Bemerkungen',0,80,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560127,0,540873,541148,548585,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,90,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560128,0,540873,541148,548586,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Standard-Ansprechpartner',0,100,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560130,0,540873,541148,548587,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Weihnachtsgeschenk',0,110,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560131,0,540873,541148,548588,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','eMail',0,120,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560132,0,540873,541148,548589,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Passwort beliebiger Länge (unterscheided Groß- und Kleinschreibung)','The Password for this User.  Passwords are required to identify authorized users.  For Adempiere Users, you can change the password via the Process "Reset Password".','Y','N','N','Y','N','Kennwort',0,130,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560133,0,540873,541148,548590,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann','Y','N','N','Y','N','Mengenmeldung App',0,140,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560134,0,540873,541148,548591,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag','"Titel" gibt die Bezeichnung für diesen Eintrag an.','Y','N','N','Y','N','Titel',0,150,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560135,0,540873,541148,548592,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','Geburtstag oder Jahrestag','Y','N','N','Y','N','Geburtstag',0,160,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:30.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560136,0,540873,541148,548593,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Beschreibt eine Telefon Nummer','Y','N','N','Y','N','Telefon',0,170,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560137,0,540873,541148,548594,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.','Y','N','N','Y','N','Mobil',0,180,0,TO_TIMESTAMP('2017-09-15 18:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560138,0,540873,541148,548595,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','Y','N','Fax',0,190,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560139,0,540873,541148,548596,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Position in der Firma','Y','N','N','Y','N','Position',0,200,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560141,0,540210,548578,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560142,0,540873,541148,548597,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Einkaufskontakt',0,210,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560143,0,540873,541148,548598,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsPurchaseContact_Default',0,220,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560146,0,540873,541148,548599,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rechnung per eMail',0,230,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560144,0,540873,541148,548600,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsSalesContact',0,240,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560145,0,540873,541148,548601,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsSalesContact_Default',0,250,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560147,0,540873,541148,548602,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.','Y','N','N','Y','N','Druck-Empfänger',0,260,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540872,540487,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-09-15T18:28:31.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540487 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:28:31.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540655,540487,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540655,541149,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560171,0,540872,541149,548603,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560172,0,540872,541149,548604,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560173,0,540872,541149,548605,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Type of request (e.g. Inquiry, Complaint, ..)','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.','Y','N','N','Y','N','Request Type',0,30,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560174,0,540872,541149,548606,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,40,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560175,0,540872,541149,548607,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Nr.',0,50,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560176,0,540211,548606,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560177,0,540872,541149,548608,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zulieferant',0,60,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560178,0,540872,541149,548609,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','Project',0,70,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560179,0,540872,541149,548610,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','Auftrag',0,80,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560180,0,540872,541149,548611,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde','Y','N','N','Y','N','Lieferdatum',0,90,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560181,0,540872,541149,548612,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','Rechnung',0,100,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560182,0,540872,541149,548613,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,110,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560183,0,540872,541149,548614,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).','Y','N','N','Y','N','Zahlung',0,120,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560184,0,540872,541149,548615,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','N','N','Y','N','Werbemassnahme',0,130,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560185,0,540872,541149,548616,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','Y','N','Lieferung/Wareneingang',0,140,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560186,0,540872,541149,548617,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization','A Return Material Authorization may be required to accept returns and to create Credit Memos','Y','N','N','Y','N','RMA',0,150,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:31.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560187,0,540872,541149,548618,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Rücklieferung',0,160,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560188,0,540872,541149,548619,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','Datensatz-ID',0,170,0,TO_TIMESTAMP('2017-09-15 18:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560189,0,540872,541149,548620,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','Kostenstelle',0,180,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560190,0,540872,541149,548621,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on','The Date Next Action indicates the next scheduled date for an action to occur for this request.','Y','N','N','Y','N','Date next action',0,190,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560191,0,540872,541149,548622,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request','The Due Type indicates if this request is Due, Overdue or Scheduled.','Y','N','N','Y','N','Due type',0,200,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560192,0,540872,541149,548623,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Request Group','Group of requests (e.g. version numbers, responsibility, ...)','Y','N','N','Y','N','Group',0,210,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560193,0,540872,541149,548624,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution','Resolution status (e.g. Fixed, Rejected, ..)','Y','N','N','Y','N','Resolution',0,220,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560194,0,540872,541149,548625,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.','The Priority indicates the importance of this request.','Y','N','N','Y','N','Priority',0,230,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560195,0,540872,541149,548626,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','PerformanceType',0,240,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560196,0,540872,541149,548627,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User','Y','N','N','Y','N','User Importance',0,250,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560197,0,540872,541149,548628,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Qualität-Notiz',0,260,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560198,0,540872,541149,548629,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request','The Summary allows free form text entry of a recap of this request.','Y','N','N','Y','N','Summary',0,270,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560199,0,540872,541149,548630,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken','The Next Action indicates the next action to be taken on this request.','Y','N','N','Y','N','Next action',0,280,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560200,0,540872,541149,548631,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','N','Aussendienst',0,290,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560201,0,540872,541149,548632,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ','Text blocks to be copied into request response text','Y','N','N','Y','N','Standard Response',0,300,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560202,0,540872,541149,548633,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken','The Result indicates the result of any action taken on this request.','Y','N','N','Y','N','Ergebnis',0,310,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560203,0,540872,541149,548634,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Request Status','Status if the request (open, closed, investigating, ..)','Y','N','N','Y','N','Status',0,320,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560204,0,540872,541149,548635,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on','The Date Last Action indicates that last time that the request was acted on.','Y','N','N','Y','N','Date last action',0,330,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560206,0,540872,541149,548636,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact','The Last Result identifies the result of the last contact made.','Y','N','N','Y','N','Last Result',0,340,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560207,0,540872,541149,548637,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,350,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560208,0,540872,541149,548638,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,360,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560209,0,540872,541149,548639,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,370,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560210,0,540872,541149,548640,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,380,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560212,0,540872,541149,548641,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Wiedervorlage Datum',0,390,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560213,0,540872,541149,548642,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','N','Vorgangsdatum',0,400,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540875,540488,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-09-15T18:28:32.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540488 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:28:32.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540656,540488,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540656,541150,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560219,0,540875,541150,548643,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,10,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560220,0,540875,541150,548644,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Typ',0,20,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560221,0,540875,541150,548645,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Nr.',0,30,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560222,0,540875,541150,548646,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,40,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:32.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560223,0,540875,541150,548647,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,50,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:33.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560224,0,540875,541150,548648,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,60,0,TO_TIMESTAMP('2017-09-15 18:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:33.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560225,0,540875,541150,548649,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,70,0,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:33.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560226,0,540875,541150,548650,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,80,0,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:33.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560227,0,540875,541150,548651,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','x_bpartner_history_id',0,90,0,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:28:33.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560228,0,540875,541150,548652,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','N','Vorgangsdatum',0,100,0,TO_TIMESTAMP('2017-09-15 18:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:31:12.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540650,541151,TO_TIMESTAMP('2017-09-15 18:31:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'rimary',TO_TIMESTAMP('2017-09-15 18:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:31:17.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-09-15 18:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541151
;

-- 2017-09-15T18:31:28.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90,Updated=TO_TIMESTAMP('2017-09-15 18:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541145
;

-- 2017-09-15T18:31:43.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560006,0,540871,541151,548653,TO_TIMESTAMP('2017-09-15 18:31:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anrede',10,0,0,TO_TIMESTAMP('2017-09-15 18:31:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:32:05.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560027,0,540871,541151,548654,TO_TIMESTAMP('2017-09-15 18:32:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',20,0,0,TO_TIMESTAMP('2017-09-15 18:32:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:32:14.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560033,0,540871,541151,548655,TO_TIMESTAMP('2017-09-15 18:32:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Firma',30,0,0,TO_TIMESTAMP('2017-09-15 18:32:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:32:23.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560028,0,540871,541151,548656,TO_TIMESTAMP('2017-09-15 18:32:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',40,0,0,TO_TIMESTAMP('2017-09-15 18:32:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:32:47.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548477
;

-- 2017-09-15T18:33:05.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548483
;

-- 2017-09-15T18:33:14.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548484
;

-- 2017-09-15T18:33:32.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560034,0,540871,541151,548657,TO_TIMESTAMP('2017-09-15 18:33:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Firmenname',50,0,0,TO_TIMESTAMP('2017-09-15 18:33:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:33:52.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548478
;

-- 2017-09-15T18:34:22.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540651,541152,TO_TIMESTAMP('2017-09-15 18:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','group',10,TO_TIMESTAMP('2017-09-15 18:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:34:57.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560036,0,540871,541152,548658,TO_TIMESTAMP('2017-09-15 18:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gruppe',10,0,0,TO_TIMESTAMP('2017-09-15 18:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:35:09.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560037,0,540871,541152,548659,TO_TIMESTAMP('2017-09-15 18:35:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sprache',20,0,0,TO_TIMESTAMP('2017-09-15 18:35:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:35:18.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540651,541153,TO_TIMESTAMP('2017-09-15 18:35:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2017-09-15 18:35:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:35:28.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560031,0,540871,541153,548660,TO_TIMESTAMP('2017-09-15 18:35:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-09-15 18:35:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:35:37.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540651,541154,TO_TIMESTAMP('2017-09-15 18:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','url',30,TO_TIMESTAMP('2017-09-15 18:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:35:50.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560041,0,540871,541154,548661,TO_TIMESTAMP('2017-09-15 18:35:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','URL',10,0,0,TO_TIMESTAMP('2017-09-15 18:35:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:35:56.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540651,541155,TO_TIMESTAMP('2017-09-15 18:35:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2017-09-15 18:35:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:36:07.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560026,0,540871,541155,548662,TO_TIMESTAMP('2017-09-15 18:36:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-09-15 18:36:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:36:21.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560025,0,540871,541155,548663,TO_TIMESTAMP('2017-09-15 18:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-09-15 18:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:36:42.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548475
;

-- 2017-09-15T18:36:42.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548476
;

-- 2017-09-15T18:36:42.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548481
;

-- 2017-09-15T18:36:42.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548486
;

-- 2017-09-15T18:36:55.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548487
;

-- 2017-09-15T18:36:55.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548491
;

-- 2017-09-15T18:37:09.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548479
;

-- 2017-09-15T18:37:10.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548480
;

-- 2017-09-15T18:37:10.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548482
;

-- 2017-09-15T18:37:11.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548485
;

-- 2017-09-15T18:37:12.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548488
;

-- 2017-09-15T18:37:12.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548489
;

-- 2017-09-15T18:37:13.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548490
;

-- 2017-09-15T18:37:13.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548492
;

-- 2017-09-15T18:37:14.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548493
;

-- 2017-09-15T18:37:14.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548494
;

-- 2017-09-15T18:37:15.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548495
;

-- 2017-09-15T18:37:16.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548496
;

-- 2017-09-15T18:37:16.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548497
;

-- 2017-09-15T18:37:17.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548498
;

-- 2017-09-15T18:37:18.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548499
;

-- 2017-09-15T18:37:19.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548500
;

-- 2017-09-15T18:37:19.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548501
;

-- 2017-09-15T18:37:20.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548502
;

-- 2017-09-15T18:37:21.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548503
;

-- 2017-09-15T18:37:21.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548504
;

-- 2017-09-15T18:37:22.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548505
;

-- 2017-09-15T18:37:24.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-15 18:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548506
;

-- 2017-09-15T18:37:58.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540871,540489,TO_TIMESTAMP('2017-09-15 18:37:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-09-15 18:37:58','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-09-15T18:37:58.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540489 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-09-15T18:38:02.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540657,540489,TO_TIMESTAMP('2017-09-15 18:38:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-09-15 18:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T18:38:17.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540657, SeqNo=10,Updated=TO_TIMESTAMP('2017-09-15 18:38:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541145
;

-- 2017-09-15T18:39:50.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gruppe',Updated=TO_TIMESTAMP('2017-09-15 18:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560036
;

-- 2017-09-15T18:45:45.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548490
;

-- 2017-09-15T18:45:45.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548482
;

-- 2017-09-15T18:45:45.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548495
;

-- 2017-09-15T18:45:45.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548493
;

-- 2017-09-15T18:45:45.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548480
;

-- 2017-09-15T18:45:45.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548489
;

-- 2017-09-15T18:45:45.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548488
;

-- 2017-09-15T18:45:45.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548497
;

-- 2017-09-15T18:45:45.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548498
;

-- 2017-09-15T18:45:45.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548502
;

-- 2017-09-15T18:45:45.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548500
;

-- 2017-09-15T18:45:45.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548499
;

-- 2017-09-15T18:45:45.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548501
;

-- 2017-09-15T18:45:45.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548479
;

-- 2017-09-15T18:45:45.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548492
;

-- 2017-09-15T18:45:45.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548494
;

-- 2017-09-15T18:45:45.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548496
;

-- 2017-09-15T18:45:45.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548504
;

-- 2017-09-15T18:45:45.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548485
;

-- 2017-09-15T18:45:45.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548505
;

-- 2017-09-15T18:45:45.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548506
;

-- 2017-09-15T18:45:45.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548653
;

-- 2017-09-15T18:45:45.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548656
;

-- 2017-09-15T18:45:45.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548657
;

-- 2017-09-15T18:45:45.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548654
;

-- 2017-09-15T18:45:45.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548660
;

-- 2017-09-15T18:45:45.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548655
;

-- 2017-09-15T18:45:45.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548658
;

-- 2017-09-15T18:45:45.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548503
;

-- 2017-09-15T18:45:45.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548661
;

-- 2017-09-15T18:45:45.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548659
;

-- 2017-09-15T18:45:45.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-09-15 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548662
;

-- 2017-09-15T18:46:22.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 18:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548656
;

-- 2017-09-15T18:46:22.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 18:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548657
;

-- 2017-09-15T18:46:22.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 18:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548653
;

-- 2017-09-15T18:46:38.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 18:46:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548654
;

-- 2017-09-15T18:46:38.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 18:46:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548653
;

-- 2017-09-15T18:46:51.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 18:46:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548653
;

-- 2017-09-15T18:46:55.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 18:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548654
;

-- 2017-09-15T18:46:58.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 18:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548655
;

-- 2017-09-15T18:47:03.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 18:47:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548656
;

-- 2017-09-15T18:47:07.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='',Updated=TO_TIMESTAMP('2017-09-15 18:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548655
;

-- 2017-09-15T18:47:11.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 18:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548657
;

-- 2017-09-15T18:47:30.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 18:47:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548662
;

-- 2017-09-15T18:47:33.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 18:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548663
;

-- 2017-09-15T18:48:03.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548657
;

-- 2017-09-15T18:48:03.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548654
;

-- 2017-09-15T18:48:03.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548653
;

-- 2017-09-15T18:48:03.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548660
;

-- 2017-09-15T18:48:03.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548655
;

-- 2017-09-15T18:48:03.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548658
;

-- 2017-09-15T18:48:03.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548503
;

-- 2017-09-15T18:48:03.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548661
;

-- 2017-09-15T18:48:03.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548659
;

-- 2017-09-15T18:48:03.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-09-15 18:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548662
;

-- 2017-09-15T18:48:36.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 18:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548659
;

-- 2017-09-15T18:48:42.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 18:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548658
;

-- 2017-09-15T18:49:51.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-09-15 18:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548656
;

-- 2017-09-15T18:49:51.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-09-15 18:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548654
;

-- 2017-09-15T18:49:51.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-09-15 18:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548653
;

-- 2017-09-15T18:49:51.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-09-15 18:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548660
;

-- 2017-09-15T18:55:17.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548551
;

-- 2017-09-15T18:55:17.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548523
;

-- 2017-09-15T18:55:17.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548522
;

-- 2017-09-15T18:55:17.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548512
;

-- 2017-09-15T18:55:17.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548513
;

-- 2017-09-15T18:55:17.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548514
;

-- 2017-09-15T18:55:17.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548518
;

-- 2017-09-15T18:55:17.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548517
;

-- 2017-09-15T18:55:17.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548538
;

-- 2017-09-15T18:55:17.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548515
;

-- 2017-09-15T18:55:17.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548507
;

-- 2017-09-15T18:55:17.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548533
;

-- 2017-09-15T18:55:17.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548510
;

-- 2017-09-15T18:55:17.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548511
;

-- 2017-09-15T18:55:17.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548521
;

-- 2017-09-15T18:55:17.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548534
;

-- 2017-09-15T18:55:17.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548549
;

-- 2017-09-15T18:55:17.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548542
;

-- 2017-09-15T18:55:17.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548557
;

-- 2017-09-15T18:55:17.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548550
;

-- 2017-09-15T18:55:17.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548508
;

-- 2017-09-15T18:55:17.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548560
;

-- 2017-09-15T18:55:17.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548559
;

-- 2017-09-15T18:55:17.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548527
;

-- 2017-09-15T18:55:17.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548530
;

-- 2017-09-15T18:55:17.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548528
;

-- 2017-09-15T18:55:17.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548556
;

-- 2017-09-15T18:55:17.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548526
;

-- 2017-09-15T18:55:17.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548544
;

-- 2017-09-15T18:55:17.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548552
;

-- 2017-09-15T18:55:17.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548520
;

-- 2017-09-15T18:55:17.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548555
;

-- 2017-09-15T18:55:17.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548537
;

-- 2017-09-15T18:55:17.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548558
;

-- 2017-09-15T18:55:17.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548509
;

-- 2017-09-15T18:55:17.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548519
;

-- 2017-09-15T18:55:17.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548535
;

-- 2017-09-15T18:55:17.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548539
;

-- 2017-09-15T18:55:17.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548561
;

-- 2017-09-15T18:55:17.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548562
;

-- 2017-09-15T18:55:17.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548529
;

-- 2017-09-15T18:55:17.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548536
;

-- 2017-09-15T18:55:17.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548548
;

-- 2017-09-15T18:55:17.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548540
;

-- 2017-09-15T18:55:17.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548524
;

-- 2017-09-15T18:55:17.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548531
;

-- 2017-09-15T18:55:17.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548554
;

-- 2017-09-15T18:55:17.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548545
;

-- 2017-09-15T18:55:17.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548547
;

-- 2017-09-15T18:55:17.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548546
;

-- 2017-09-15T18:55:17.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548525
;

-- 2017-09-15T18:55:17.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548541
;

-- 2017-09-15T18:55:17.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548553
;

-- 2017-09-15T18:55:17.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548516
;

-- 2017-09-15T18:55:17.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548532
;

-- 2017-09-15T18:55:17.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-09-15 18:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548543
;

-- 2017-09-15T18:55:43.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 18:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548543
;

-- 2017-09-15T18:55:46.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 18:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548532
;

-- 2017-09-15T18:55:48.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 18:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548516
;

-- 2017-09-15T18:55:50.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=300,Updated=TO_TIMESTAMP('2017-09-15 18:55:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548553
;

-- 2017-09-15T18:55:53.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 18:55:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548553
;

-- 2017-09-15T18:55:55.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-09-15 18:55:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548541
;

-- 2017-09-15T18:55:57.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-09-15 18:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548525
;

-- 2017-09-15T18:56:10.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548515
;

-- 2017-09-15T18:56:19.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548533
;

-- 2017-09-15T18:56:19.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548509
;

-- 2017-09-15T18:56:19.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548519
;

-- 2017-09-15T18:56:19.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548535
;

-- 2017-09-15T18:56:19.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548539
;

-- 2017-09-15T18:56:19.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548529
;

-- 2017-09-15T18:56:19.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548536
;

-- 2017-09-15T18:56:19.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548511
;

-- 2017-09-15T18:56:19.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548521
;

-- 2017-09-15T18:56:19.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548524
;

-- 2017-09-15T18:56:19.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548531
;

-- 2017-09-15T18:56:19.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548534
;

-- 2017-09-15T18:56:19.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548538
;

-- 2017-09-15T18:56:19.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548510
;

-- 2017-09-15T18:56:19.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548514
;

-- 2017-09-15T18:56:19.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548522
;

-- 2017-09-15T18:56:19.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548508
;

-- 2017-09-15T18:56:19.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548507
;

-- 2017-09-15T18:56:19.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548523
;

-- 2017-09-15T18:56:19.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548512
;

-- 2017-09-15T18:56:19.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548527
;

-- 2017-09-15T18:56:19.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548530
;

-- 2017-09-15T18:56:19.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548528
;

-- 2017-09-15T18:56:19.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548513
;

-- 2017-09-15T18:56:19.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548526
;

-- 2017-09-15T18:56:19.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548518
;

-- 2017-09-15T18:56:19.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548517
;

-- 2017-09-15T18:56:19.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548520
;

-- 2017-09-15T18:56:19.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548561
;

-- 2017-09-15T18:56:19.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548562
;

-- 2017-09-15T18:56:19.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548551
;

-- 2017-09-15T18:56:19.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548549
;

-- 2017-09-15T18:56:24.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548557
;

-- 2017-09-15T18:56:24.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548550
;

-- 2017-09-15T18:56:24.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548560
;

-- 2017-09-15T18:56:24.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548559
;

-- 2017-09-15T18:56:24.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548556
;

-- 2017-09-15T18:56:24.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548544
;

-- 2017-09-15T18:56:24.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548552
;

-- 2017-09-15T18:56:24.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548555
;

-- 2017-09-15T18:56:24.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548558
;

-- 2017-09-15T18:56:24.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548548
;

-- 2017-09-15T18:56:24.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548540
;

-- 2017-09-15T18:56:24.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548554
;

-- 2017-09-15T18:56:24.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548545
;

-- 2017-09-15T18:56:24.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548547
;

-- 2017-09-15T18:56:24.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548546
;

-- 2017-09-15T18:56:36.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540206
;

-- 2017-09-15T18:56:36.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540207
;

-- 2017-09-15T18:56:41.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548537
;

-- 2017-09-15T18:56:54.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540208
;

-- 2017-09-15T18:56:56.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540209
;

-- 2017-09-15T18:57:00.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548542
;

-- 2017-09-15T18:57:36.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge',Updated=TO_TIMESTAMP('2017-09-15 18:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560248
;

-- 2017-09-15T18:58:44.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Menge',Updated=TO_TIMESTAMP('2017-09-15 18:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548525
;

-- 2017-09-15T19:00:01.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge ',Updated=TO_TIMESTAMP('2017-09-15 19:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560248
;

-- 2017-09-15T19:00:05.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge',Updated=TO_TIMESTAMP('2017-09-15 19:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560248
;

-- 2017-09-15T19:00:31.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548525
;

-- 2017-09-15T19:00:36.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 19:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548516
;

-- 2017-09-15T19:00:47.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560231,0,540876,541146,548664,TO_TIMESTAMP('2017-09-15 19:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',70,0,0,TO_TIMESTAMP('2017-09-15 19:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T19:00:56.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560230,0,540876,541146,548665,TO_TIMESTAMP('2017-09-15 19:00:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',80,0,0,TO_TIMESTAMP('2017-09-15 19:00:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T19:01:00.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548543
;

-- 2017-09-15T19:01:00.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548553
;

-- 2017-09-15T19:01:00.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548541
;

-- 2017-09-15T19:01:01.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548532
;

-- 2017-09-15T19:01:01.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548516
;

-- 2017-09-15T19:01:03.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548525
;

-- 2017-09-15T19:01:10.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-09-15 19:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548664
;

-- 2017-09-15T19:01:21.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:01:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548664
;

-- 2017-09-15T19:01:25.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:01:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548665
;

-- 2017-09-15T19:01:29.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548532
;

-- 2017-09-15T19:01:34.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 19:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548541
;

-- 2017-09-15T19:01:40.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548543
;

-- 2017-09-15T19:08:25.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548574
;

-- 2017-09-15T19:08:25.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548569
;

-- 2017-09-15T19:08:25.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548575
;

-- 2017-09-15T19:08:25.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548566
;

-- 2017-09-15T19:08:25.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548564
;

-- 2017-09-15T19:08:25.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548571
;

-- 2017-09-15T19:08:25.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548570
;

-- 2017-09-15T19:08:25.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548573
;

-- 2017-09-15T19:08:25.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548576
;

-- 2017-09-15T19:08:25.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548572
;

-- 2017-09-15T19:08:25.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548565
;

-- 2017-09-15T19:08:25.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548568
;

-- 2017-09-15T19:08:25.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548567
;

-- 2017-09-15T19:08:25.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 19:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548563
;

-- 2017-09-15T19:08:45.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-09-15 19:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548565
;

-- 2017-09-15T19:08:45.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-09-15 19:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548568
;

-- 2017-09-15T19:08:45.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-09-15 19:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548567
;

-- 2017-09-15T19:08:45.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-09-15 19:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548563
;

-- 2017-09-15T19:08:58.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-09-15 19:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548565
;

-- 2017-09-15T19:09:02.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-09-15 19:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548568
;

-- 2017-09-15T19:09:04.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 19:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548567
;

-- 2017-09-15T19:09:40.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560071,0,540874,541147,548666,TO_TIMESTAMP('2017-09-15 19:09:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',40,0,0,TO_TIMESTAMP('2017-09-15 19:09:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T19:10:02.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-09-15 19:10:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548666
;

-- 2017-09-15T19:10:07.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 19:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548571
;

-- 2017-09-15T19:10:15.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 19:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548570
;

-- 2017-09-15T19:10:19.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 19:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548573
;

-- 2017-09-15T19:10:25.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-09-15 19:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548572
;

-- 2017-09-15T19:10:30.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-09-15 19:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548574
;

-- 2017-09-15T19:10:38.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-09-15 19:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548575
;

-- 2017-09-15T19:11:05.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-09-15 19:11:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548576
;

-- 2017-09-15T19:11:18.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2017-09-15 19:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548569
;

-- 2017-09-15T19:11:29.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=26,Updated=TO_TIMESTAMP('2017-09-15 19:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548566
;

-- 2017-09-15T19:11:32.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-09-15 19:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548563
;

-- 2017-09-15T19:11:35.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2017-09-15 19:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548666
;

-- 2017-09-15T19:11:43.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 19:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548569
;

-- 2017-09-15T19:11:45.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 19:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548566
;

-- 2017-09-15T19:11:47.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 19:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548567
;

-- 2017-09-15T19:11:49.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 19:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548571
;

-- 2017-09-15T19:11:52.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-09-15 19:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548570
;

-- 2017-09-15T19:11:54.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-09-15 19:11:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548573
;

-- 2017-09-15T19:11:57.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-09-15 19:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548572
;

-- 2017-09-15T19:12:00.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-09-15 19:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548574
;

-- 2017-09-15T19:12:02.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-09-15 19:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548575
;

-- 2017-09-15T19:12:05.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2017-09-15 19:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548576
;

-- 2017-09-15T19:12:09.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2017-09-15 19:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548563
;

-- 2017-09-15T19:12:13.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2017-09-15 19:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548666
;

-- 2017-09-15T19:12:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548666
;

-- 2017-09-15T19:12:42.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548563
;

-- 2017-09-15T19:12:46.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 19:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548565
;

-- 2017-09-15T19:12:55.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:12:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548568
;

-- 2017-09-15T19:13:23.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548565
;

-- 2017-09-15T19:13:23.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548568
;

-- 2017-09-15T19:13:24.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548569
;

-- 2017-09-15T19:13:24.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548566
;

-- 2017-09-15T19:13:25.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548567
;

-- 2017-09-15T19:13:25.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548571
;

-- 2017-09-15T19:13:25.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548570
;

-- 2017-09-15T19:13:26.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548573
;

-- 2017-09-15T19:13:26.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548572
;

-- 2017-09-15T19:13:27.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548574
;

-- 2017-09-15T19:13:27.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548575
;

-- 2017-09-15T19:13:28.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548576
;

-- 2017-09-15T19:13:28.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548563
;

-- 2017-09-15T19:13:30.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548666
;

-- 2017-09-15T19:19:20.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548584
;

-- 2017-09-15T19:19:20.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548583
;

-- 2017-09-15T19:19:20.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548602
;

-- 2017-09-15T19:19:20.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548578
;

-- 2017-09-15T19:19:20.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548598
;

-- 2017-09-15T19:19:20.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548601
;

-- 2017-09-15T19:19:20.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548589
;

-- 2017-09-15T19:19:20.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548590
;

-- 2017-09-15T19:19:20.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548594
;

-- 2017-09-15T19:19:20.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548582
;

-- 2017-09-15T19:19:20.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548599
;

-- 2017-09-15T19:19:20.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548591
;

-- 2017-09-15T19:19:20.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548587
;

-- 2017-09-15T19:19:20.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548579
;

-- 2017-09-15T19:19:20.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548581
;

-- 2017-09-15T19:19:20.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548580
;

-- 2017-09-15T19:19:20.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548585
;

-- 2017-09-15T19:19:20.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548588
;

-- 2017-09-15T19:19:20.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548593
;

-- 2017-09-15T19:19:20.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548595
;

-- 2017-09-15T19:19:20.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548592
;

-- 2017-09-15T19:19:20.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548596
;

-- 2017-09-15T19:19:20.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548600
;

-- 2017-09-15T19:19:20.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548597
;

-- 2017-09-15T19:19:20.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548586
;

-- 2017-09-15T19:19:20.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-09-15 19:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548577
;

-- 2017-09-15T19:19:50.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-09-15 19:19:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548579
;

-- 2017-09-15T19:19:52.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-09-15 19:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548581
;

-- 2017-09-15T19:19:54.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 19:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548580
;

-- 2017-09-15T19:19:56.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 19:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548585
;

-- 2017-09-15T19:19:57.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 19:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548588
;

-- 2017-09-15T19:19:59.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 19:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548593
;

-- 2017-09-15T19:20:00.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-09-15 19:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548595
;

-- 2017-09-15T19:20:02.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-09-15 19:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548592
;

-- 2017-09-15T19:20:03.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-09-15 19:20:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548596
;

-- 2017-09-15T19:20:06.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-09-15 19:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548600
;

-- 2017-09-15T19:20:08.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-09-15 19:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548597
;

-- 2017-09-15T19:20:10.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2017-09-15 19:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548586
;

-- 2017-09-15T19:20:24.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2017-09-15 19:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548577
;

-- 2017-09-15T19:20:42.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2017-09-15 19:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548577
;

-- 2017-09-15T19:20:56.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560118,0,540873,541148,548667,TO_TIMESTAMP('2017-09-15 19:20:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',130,0,0,TO_TIMESTAMP('2017-09-15 19:20:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T19:21:01.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-09-15 19:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548667
;

-- 2017-09-15T19:21:15.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2017-09-15 19:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548587
;

-- 2017-09-15T19:21:17.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2017-09-15 19:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548591
;

-- 2017-09-15T19:21:19.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2017-09-15 19:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548578
;

-- 2017-09-15T19:21:21.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=160,Updated=TO_TIMESTAMP('2017-09-15 19:21:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548589
;

-- 2017-09-15T19:21:23.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2017-09-15 19:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548590
;

-- 2017-09-15T19:21:25.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=180,Updated=TO_TIMESTAMP('2017-09-15 19:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548594
;

-- 2017-09-15T19:21:27.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2017-09-15 19:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548582
;

-- 2017-09-15T19:21:30.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=200,Updated=TO_TIMESTAMP('2017-09-15 19:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548602
;

-- 2017-09-15T19:21:38.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=210,Updated=TO_TIMESTAMP('2017-09-15 19:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548601
;

-- 2017-09-15T19:21:41.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=220,Updated=TO_TIMESTAMP('2017-09-15 19:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548598
;

-- 2017-09-15T19:21:44.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=230,Updated=TO_TIMESTAMP('2017-09-15 19:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548599
;

-- 2017-09-15T19:21:47.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2017-09-15 19:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548577
;

-- 2017-09-15T19:21:49.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2017-09-15 19:21:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548577
;

-- 2017-09-15T19:21:54.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2017-09-15 19:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548667
;

-- 2017-09-15T19:22:16.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548587
;

-- 2017-09-15T19:22:17.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548591
;

-- 2017-09-15T19:22:17.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548578
;

-- 2017-09-15T19:22:17.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548589
;

-- 2017-09-15T19:22:18.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548590
;

-- 2017-09-15T19:22:18.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548594
;

-- 2017-09-15T19:22:19.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548582
;

-- 2017-09-15T19:22:19.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548602
;

-- 2017-09-15T19:22:20.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548601
;

-- 2017-09-15T19:22:20.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548598
;

-- 2017-09-15T19:22:20.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548599
;

-- 2017-09-15T19:22:22.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548579
;

-- 2017-09-15T19:22:22.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548581
;

-- 2017-09-15T19:22:22.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548580
;

-- 2017-09-15T19:22:23.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548585
;

-- 2017-09-15T19:22:24.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548588
;

-- 2017-09-15T19:22:24.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548593
;

-- 2017-09-15T19:22:25.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548595
;

-- 2017-09-15T19:22:26.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548592
;

-- 2017-09-15T19:22:26.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548596
;

-- 2017-09-15T19:22:26.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548600
;

-- 2017-09-15T19:22:27.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548597
;

-- 2017-09-15T19:22:27.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548586
;

-- 2017-09-15T19:22:28.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548577
;

-- 2017-09-15T19:22:29.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548667
;

-- 2017-09-15T19:23:52.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Std. Einkaufskontakt',Updated=TO_TIMESTAMP('2017-09-15 19:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560143
;

-- 2017-09-15T19:23:59.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkaufskontakt',Updated=TO_TIMESTAMP('2017-09-15 19:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560144
;

-- 2017-09-15T19:24:10.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Std. Verkaufskontakt',Updated=TO_TIMESTAMP('2017-09-15 19:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560145
;

-- 2017-09-15T19:27:26.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548639
;

-- 2017-09-15T19:27:26.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548640
;

-- 2017-09-15T19:27:26.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548610
;

-- 2017-09-15T19:27:26.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548631
;

-- 2017-09-15T19:27:26.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548635
;

-- 2017-09-15T19:27:26.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548621
;

-- 2017-09-15T19:27:26.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548619
;

-- 2017-09-15T19:27:26.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548622
;

-- 2017-09-15T19:27:26.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548606
;

-- 2017-09-15T19:27:26.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548623
;

-- 2017-09-15T19:27:26.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548620
;

-- 2017-09-15T19:27:26.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548636
;

-- 2017-09-15T19:27:26.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548611
;

-- 2017-09-15T19:27:26.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548616
;

-- 2017-09-15T19:27:26.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548603
;

-- 2017-09-15T19:27:26.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548630
;

-- 2017-09-15T19:27:26.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548626
;

-- 2017-09-15T19:27:26.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548625
;

-- 2017-09-15T19:27:26.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548613
;

-- 2017-09-15T19:27:26.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548609
;

-- 2017-09-15T19:27:26.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548628
;

-- 2017-09-15T19:27:26.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548617
;

-- 2017-09-15T19:27:26.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548612
;

-- 2017-09-15T19:27:26.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548624
;

-- 2017-09-15T19:27:26.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548618
;

-- 2017-09-15T19:27:26.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548604
;

-- 2017-09-15T19:27:26.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548632
;

-- 2017-09-15T19:27:26.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548634
;

-- 2017-09-15T19:27:26.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548629
;

-- 2017-09-15T19:27:27.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548627
;

-- 2017-09-15T19:27:27.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548642
;

-- 2017-09-15T19:27:27.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548615
;

-- 2017-09-15T19:27:27.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548641
;

-- 2017-09-15T19:27:27.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548614
;

-- 2017-09-15T19:27:27.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548608
;

-- 2017-09-15T19:27:27.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548607
;

-- 2017-09-15T19:27:27.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548605
;

-- 2017-09-15T19:27:27.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548637
;

-- 2017-09-15T19:27:27.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548638
;

-- 2017-09-15T19:27:27.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-15 19:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548633
;

-- 2017-09-15T19:27:33.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-09-15 19:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548604
;

-- 2017-09-15T19:27:47.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 19:27:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548604
;

-- 2017-09-15T19:27:50.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 19:27:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548633
;

-- 2017-09-15T19:27:52.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 19:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548638
;

-- 2017-09-15T19:27:55.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 19:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548637
;

-- 2017-09-15T19:27:57.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-09-15 19:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548605
;

-- 2017-09-15T19:28:01.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-09-15 19:28:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548607
;

-- 2017-09-15T19:28:24.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560171,0,540872,541149,548668,TO_TIMESTAMP('2017-09-15 19:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',70,0,0,TO_TIMESTAMP('2017-09-15 19:28:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T19:28:35.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:28:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548607
;

-- 2017-09-15T19:28:36.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548605
;

-- 2017-09-15T19:28:36.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548637
;

-- 2017-09-15T19:28:37.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548638
;

-- 2017-09-15T19:28:37.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548633
;

-- 2017-09-15T19:29:12.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-09-15 19:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548607
;

-- 2017-09-15T19:29:12.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-09-15 19:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548605
;

-- 2017-09-15T19:29:12.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-09-15 19:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548637
;

-- 2017-09-15T19:29:12.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-09-15 19:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548638
;

-- 2017-09-15T19:30:18.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548604
;

-- 2017-09-15T19:30:22.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 19:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548633
;

-- 2017-09-15T19:30:32.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548638
;

-- 2017-09-15T19:30:40.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548637
;

-- 2017-09-15T19:30:43.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:30:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548605
;

-- 2017-09-15T19:30:47.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:30:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548607
;

-- 2017-09-15T19:31:19.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548604
;

-- 2017-09-15T19:31:35.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=70, WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548603
;

-- 2017-09-15T19:32:08.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548668
;

-- 2017-09-15T19:35:45.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548651
;

-- 2017-09-15T19:35:45.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548644
;

-- 2017-09-15T19:35:45.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548645
;

-- 2017-09-15T19:35:45.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548652
;

-- 2017-09-15T19:35:45.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548643
;

-- 2017-09-15T19:35:45.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548648
;

-- 2017-09-15T19:35:45.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548646
;

-- 2017-09-15T19:35:45.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548647
;

-- 2017-09-15T19:35:45.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548649
;

-- 2017-09-15T19:35:45.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-09-15 19:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548650
;

-- 2017-09-15T19:35:56.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-09-15 19:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548644
;

-- 2017-09-15T19:35:58.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-09-15 19:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548645
;

-- 2017-09-15T19:36:00.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-15 19:36:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548652
;

-- 2017-09-15T19:36:01.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-15 19:36:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548643
;

-- 2017-09-15T19:36:03.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-15 19:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548648
;

-- 2017-09-15T19:36:04.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-15 19:36:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548646
;

-- 2017-09-15T19:36:06.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-09-15 19:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548647
;

-- 2017-09-15T19:36:08.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-09-15 19:36:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548649
;

-- 2017-09-15T19:36:11.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-09-15 19:36:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548650
;

-- 2017-09-15T19:36:19.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560215,0,540875,541150,548669,TO_TIMESTAMP('2017-09-15 19:36:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',100,0,0,TO_TIMESTAMP('2017-09-15 19:36:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T19:36:25.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548644
;

-- 2017-09-15T19:36:25.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548645
;

-- 2017-09-15T19:36:26.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548652
;

-- 2017-09-15T19:36:27.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548643
;

-- 2017-09-15T19:36:27.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548648
;

-- 2017-09-15T19:36:28.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548646
;

-- 2017-09-15T19:36:28.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548647
;

-- 2017-09-15T19:36:28.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548649
;

-- 2017-09-15T19:36:30.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-09-15 19:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548650
;

-- 2017-09-15T19:37:12.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548650
;

-- 2017-09-15T19:37:16.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548669
;

-- 2017-09-15T19:37:20.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-09-15 19:37:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548644
;

-- 2017-09-15T19:37:24.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548645
;

-- 2017-09-15T19:37:29.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548652
;

-- 2017-09-15T19:37:34.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548643
;

-- 2017-09-15T19:37:38.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-09-15 19:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548646
;

-- 2017-09-15T19:37:42.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-09-15 19:37:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548649
;

