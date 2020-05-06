-- 2017-04-11T16:25:01.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540333,TO_TIMESTAMP('2017-04-11 16:25:01','YYYY-MM-DD HH24:MI:SS'),100,'U','_Tourversion','Y','N','N','N','Y','Tourversion','N',TO_TIMESTAMP('2017-04-11 16:25:01','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2017-04-11T16:25:01.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540333 AND NOT EXISTS (SELECT * FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2017-04-11T16:25:12.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540799,540294,540333,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','Y','N','N','Y','Y','N','N','Y','Y','N','N','N','Tour','N',10,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540799 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-04-11T16:25:12.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545391,558065,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558065 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545397,558066,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558066 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545396,558067,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558067 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545390,558068,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558068 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545386,558069,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558069 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545387,558070,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558070 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545395,558071,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'U','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',30,30,999,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558071 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545393,558072,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',40,40,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558072 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545388,558073,0,540799,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,22,'U',0,'Y','Y','N','N','N','N','N','N','N','Tour',1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558073 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,545407,0,540800,540295,540333,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','Y','N','N','N','Y','Y','N','N','N','Tour Version',545388,'N',20,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540800 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-04-11T16:25:12.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545400,558074,0,540800,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558074 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545405,558075,0,540800,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558075 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545407,558076,0,540800,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','N','N','N','N','N','N','N','Tour',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558076 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545401,558077,0,540800,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558077 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545406,558078,0,540800,0,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558078 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545398,558079,0,540800,65,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558079 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545399,558080,0,540800,78,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558080 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545404,558081,0,540800,223,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'U','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',30,30,999,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558081 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545408,558082,0,540800,71,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)',7,'U','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','Y','N','N','N','N','Y','Gültig ab',40,40,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558082 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545416,558083,0,540800,124,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','Y','N','N','N','N','N','Cancel Delivery Day',50,50,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558083 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:12.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545415,558084,0,540800,135,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','Y','N','N','N','N','Y','Move Delivery Day to next/previous Day',60,60,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:12.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558084 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545417,558085,0,540800,84,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Weekly',70,70,1,1,TO_TIMESTAMP('2017-04-11 16:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558085 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545418,558086,0,540800,118,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Every x weeks',2,'@IsWeekly@=''N'' & @IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Every Week',80,80,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558086 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545409,558087,0,540800,59,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Montags verfügbar',1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Montag',90,90,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558087 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549396,558088,0,540800,101,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for monday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',100,100,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558088 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545410,558089,0,540800,63,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Dienstags verfügbar',1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Dienstag',110,110,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558089 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549397,558090,0,540800,103,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for tuesday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',120,120,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558090 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545411,558091,0,540800,68,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Mittwochs verfügbar',1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Mittwoch',130,130,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558091 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549398,558092,0,540800,101,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for wednesday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',140,140,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558092 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545434,558093,0,540800,78,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Donnerstags verfügbar',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Donnerstag',150,150,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558093 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549400,558094,0,540800,104,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for thursday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',160,160,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558094 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545412,558095,0,540800,53,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Freitags verfügbar',1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Freitag',170,170,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558095 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549401,558096,0,540800,106,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Friday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',180,180,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558096 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545413,558097,0,540800,62,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Samstags verfügbar',1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Samstag',190,190,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558097 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549402,558098,0,540800,112,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Saturday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',200,200,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558098 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545414,558099,0,540800,61,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Sonntags verfügbar',1,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Sonntag',210,210,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558099 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549399,558100,0,540800,112,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Sunday',0,'@IsMonthly@=''N'' & @EveryMonth@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Preparation Time',220,220,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558100 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545419,558101,0,540800,71,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,1,'@IsWeekly@=''N'' & @EveryWeek@=0','U',0,'Y','Y','Y','Y','N','N','N','N','N','Monthly',230,230,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558101 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545420,558102,0,540800,131,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Every x Months',2,'@IsMonthly@=''N'' & @IsWeekly@=''N'' & @EveryWeek@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Every Month',240,240,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558102 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545421,558103,0,540800,101,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Day of the month 1 to 28/29/30/31',14,'@IsWeekly@=''N'' & @EveryWeek@=0','U',0,'Y','Y','Y','Y','N','N','N','N','Y','Day of the Month',250,250,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558103 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545402,558104,0,540800,44,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',260,260,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558104 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545403,558105,0,540800,0,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,22,'U',0,'Y','Y','N','N','N','N','N','N','N','Tour Version',0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558105 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,545431,0,540801,540296,540333,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','Y','N','N','N','Y','Y','N','N','N','Tour Version Line',545403,'N',30,2,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540801 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-04-11T16:25:13.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545422,558106,0,540801,65,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','N','N','N','Mandant',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558106 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545423,558107,0,540801,78,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','Y','Sektion',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558107 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545425,558108,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558108 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545429,558109,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558109 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545430,558110,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558110 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545424,558111,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558111 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545431,558112,0,540801,219,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','N','N','N','N','N','N','N','Tour Version',0,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558112 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551039,558113,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'U','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','Y','N','N','N','N','N','Reihenfolge',10,10,1,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558113 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:13.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545432,558114,0,540801,318,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','Y','N','N','N','N','N','Geschäftspartner',20,20,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:13.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558114 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:14.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545433,558115,0,540801,343,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'U','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','Y','N','N','N','N','Y','Standort',30,30,1,1,TO_TIMESTAMP('2017-04-11 16:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:14.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558115 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:14.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550250,558116,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Puffer/Mindestabstand (in Stunden) zwischen Bereitstellungsdatum und zugesagtem Termin.',0,'Y','Y','Y','Y','N','N','N','N','N','Puffer (Std)',40,40,1,1,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:14.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558116 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:14.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545426,558117,0,540801,44,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:14.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558117 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:14.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550696,558118,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','Y','N','N','N','N','N','Abholung',60,60,0,1,1,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:14.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558118 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:14.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545427,558119,0,540801,0,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100,22,'U',0,'Y','Y','N','N','N','N','N','N','N','Tour Version Line',0,1,1,TO_TIMESTAMP('2017-04-11 16:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:25:14.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558119 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-11T16:25:32.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=10, TabLevel=0,Updated=TO_TIMESTAMP('2017-04-11 16:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540800
;

-- 2017-04-11T16:25:37.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=20, TabLevel=1,Updated=TO_TIMESTAMP('2017-04-11 16:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540801
;

-- 2017-04-11T16:25:51.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=NULL,Updated=TO_TIMESTAMP('2017-04-11 16:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540800
;

-- 2017-04-11T16:25:54.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540799
;

-- 2017-04-11T16:25:54.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab WHERE AD_Tab_ID=540799
;
-- 2017-04-11T16:33:49.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540800,540130,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540175,540130,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540176,540130,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540175,540256,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558079,0,540800,540256,542961,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558080,0,540800,540256,542962,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558081,0,540800,540256,542963,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:49.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558082,0,540800,540256,542964,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','Y','N','Gültig ab',40,40,0,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558083,0,540800,540256,542965,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Cancel Delivery Day',50,50,0,TO_TIMESTAMP('2017-04-11 16:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558084,0,540800,540256,542966,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Move Delivery Day to next/previous Day',60,60,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558085,0,540800,540256,542967,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Weekly',70,70,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558086,0,540800,540256,542968,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Every x weeks','Y','N','Y','Y','N','Every Week',80,80,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558087,0,540800,540256,542969,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Montags verfügbar','Y','N','Y','Y','N','Montag',90,90,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558088,0,540800,540256,542970,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for monday','Y','N','Y','Y','N','Preparation Time',100,100,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558089,0,540800,540256,542971,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Dienstags verfügbar','Y','N','Y','Y','N','Dienstag',110,110,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558090,0,540800,540256,542972,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for tuesday','Y','N','Y','Y','N','Preparation Time',120,120,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558091,0,540800,540256,542973,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Mittwochs verfügbar','Y','N','Y','Y','N','Mittwoch',130,130,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558092,0,540800,540256,542974,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for wednesday','Y','N','Y','Y','N','Preparation Time',140,140,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558093,0,540800,540256,542975,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Donnerstags verfügbar','Y','N','Y','Y','N','Donnerstag',150,150,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558094,0,540800,540256,542976,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for thursday','Y','N','Y','Y','N','Preparation Time',160,160,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558095,0,540800,540256,542977,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Freitags verfügbar','Y','N','Y','Y','N','Freitag',170,170,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558096,0,540800,540256,542978,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Friday','Y','N','Y','Y','N','Preparation Time',180,180,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558097,0,540800,540256,542979,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Samstags verfügbar','Y','N','Y','Y','N','Samstag',190,190,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558098,0,540800,540256,542980,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Saturday','Y','N','Y','Y','N','Preparation Time',200,200,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558099,0,540800,540256,542981,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Sonntags verfügbar','Y','N','Y','Y','N','Sonntag',210,210,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558100,0,540800,540256,542982,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Preparation time for Sunday','Y','N','Y','Y','N','Preparation Time',220,220,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558101,0,540800,540256,542983,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Monthly',230,230,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558102,0,540800,540256,542984,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Every x Months','Y','N','Y','Y','N','Every Month',240,240,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558103,0,540800,540256,542985,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Day of the month 1 to 28/29/30/31','Y','N','Y','Y','N','Day of the Month',250,250,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558104,0,540800,540256,542986,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',260,260,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540801,540131,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540177,540131,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540177,540257,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558113,0,540801,540257,542987,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,10,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558114,0,540801,540257,542988,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,20,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558115,0,540109,542988,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:50.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558116,0,540801,540257,542989,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Puffer/Mindestabstand (in Stunden) zwischen Bereitstellungsdatum und zugesagtem Termin.','Y','N','N','Y','N','Puffer (Std)',0,30,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:51.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558117,0,540801,540257,542990,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-04-11 16:33:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:33:51.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558118,0,540801,540257,542991,TO_TIMESTAMP('2017-04-11 16:33:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Abholung',0,50,0,TO_TIMESTAMP('2017-04-11 16:33:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:38:11.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540175,540258,TO_TIMESTAMP('2017-04-11 16:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-04-11 16:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:38:26.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=999, UIStyle='',Updated=TO_TIMESTAMP('2017-04-11 16:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540256
;

-- 2017-04-11T16:38:47.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558081,0,540800,540258,542992,TO_TIMESTAMP('2017-04-11 16:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-04-11 16:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:38:59.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558082,0,540800,540258,542993,TO_TIMESTAMP('2017-04-11 16:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gültig ab',20,0,0,TO_TIMESTAMP('2017-04-11 16:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:43:03.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540176,540259,TO_TIMESTAMP('2017-04-11 16:43:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2017-04-11 16:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:43:13.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558080,0,540800,540259,542994,TO_TIMESTAMP('2017-04-11 16:43:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-04-11 16:43:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:43:22.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558079,0,540800,540259,542995,TO_TIMESTAMP('2017-04-11 16:43:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-04-11 16:43:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:44:14.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558087,0,540800,540258,542996,TO_TIMESTAMP('2017-04-11 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Montag',30,0,0,TO_TIMESTAMP('2017-04-11 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:44:47.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558088,0,540800,540258,542997,TO_TIMESTAMP('2017-04-11 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Montag',40,0,0,TO_TIMESTAMP('2017-04-11 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:44:57.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558089,0,540800,540258,542998,TO_TIMESTAMP('2017-04-11 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Dienstag',50,0,0,TO_TIMESTAMP('2017-04-11 16:44:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:45:10.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558090,0,540800,540258,542999,TO_TIMESTAMP('2017-04-11 16:45:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Dienstag',60,0,0,TO_TIMESTAMP('2017-04-11 16:45:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:45:22.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558091,0,540800,540258,543000,TO_TIMESTAMP('2017-04-11 16:45:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mittwoch',70,0,0,TO_TIMESTAMP('2017-04-11 16:45:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:45:33.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558092,0,540800,540258,543001,TO_TIMESTAMP('2017-04-11 16:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Mittwoch',80,0,0,TO_TIMESTAMP('2017-04-11 16:45:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:45:52.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540176,540260,TO_TIMESTAMP('2017-04-11 16:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','date',20,TO_TIMESTAMP('2017-04-11 16:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:46:15.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540175,540261,TO_TIMESTAMP('2017-04-11 16:46:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','days',20,TO_TIMESTAMP('2017-04-11 16:46:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:46:27.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558087,0,540800,540261,543002,TO_TIMESTAMP('2017-04-11 16:46:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Montag',10,0,0,TO_TIMESTAMP('2017-04-11 16:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:46:37.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558088,0,540800,540261,543003,TO_TIMESTAMP('2017-04-11 16:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Montag',20,0,0,TO_TIMESTAMP('2017-04-11 16:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:46:46.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558089,0,540800,540261,543004,TO_TIMESTAMP('2017-04-11 16:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Dienstag',30,0,0,TO_TIMESTAMP('2017-04-11 16:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:46:58.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558090,0,540800,540261,543005,TO_TIMESTAMP('2017-04-11 16:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Dienstag',40,0,0,TO_TIMESTAMP('2017-04-11 16:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:47:08.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558091,0,540800,540261,543006,TO_TIMESTAMP('2017-04-11 16:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mittwoch',50,0,0,TO_TIMESTAMP('2017-04-11 16:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:47:19.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558092,0,540800,540261,543007,TO_TIMESTAMP('2017-04-11 16:47:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Mittwoch',60,0,0,TO_TIMESTAMP('2017-04-11 16:47:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:47:41.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542996
;

-- 2017-04-11T16:47:41.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542997
;

-- 2017-04-11T16:47:41.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542998
;

-- 2017-04-11T16:47:41.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542999
;

-- 2017-04-11T16:47:41.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543000
;

-- 2017-04-11T16:47:41.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543001
;

-- 2017-04-11T16:47:55.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='days',Updated=TO_TIMESTAMP('2017-04-11 16:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540260
;

-- 2017-04-11T16:48:10.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558093,0,540800,540260,543008,TO_TIMESTAMP('2017-04-11 16:48:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Donnerstag',10,0,0,TO_TIMESTAMP('2017-04-11 16:48:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:48:28.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558094,0,540800,540260,543009,TO_TIMESTAMP('2017-04-11 16:48:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Donnerstag',20,0,0,TO_TIMESTAMP('2017-04-11 16:48:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:48:56.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=558095,Updated=TO_TIMESTAMP('2017-04-11 16:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T16:49:06.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=558096, Name='Zeit Freitag',Updated=TO_TIMESTAMP('2017-04-11 16:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543009
;

-- 2017-04-11T16:49:15.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558097,0,540800,540260,543010,TO_TIMESTAMP('2017-04-11 16:49:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Samstag',30,0,0,TO_TIMESTAMP('2017-04-11 16:49:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:49:26.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558098,0,540800,540260,543011,TO_TIMESTAMP('2017-04-11 16:49:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Samstag',40,0,0,TO_TIMESTAMP('2017-04-11 16:49:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:49:37.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558099,0,540800,540260,543012,TO_TIMESTAMP('2017-04-11 16:49:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sonntag',50,0,0,TO_TIMESTAMP('2017-04-11 16:49:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:49:49.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558100,0,540800,540260,543013,TO_TIMESTAMP('2017-04-11 16:49:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Sonntag',60,0,0,TO_TIMESTAMP('2017-04-11 16:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:50:09.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558093,0,540800,540261,543014,TO_TIMESTAMP('2017-04-11 16:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Donnerstag',70,0,0,TO_TIMESTAMP('2017-04-11 16:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T16:50:22.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558094,0,540800,540261,543015,TO_TIMESTAMP('2017-04-11 16:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Donnerstag',80,0,0,TO_TIMESTAMP('2017-04-11 16:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T18:04:59.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542961
;

-- 2017-04-11T18:04:59.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542962
;

-- 2017-04-11T18:04:59.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542963
;

-- 2017-04-11T18:04:59.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542964
;

-- 2017-04-11T18:05:07.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542967
;

-- 2017-04-11T18:05:07.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542968
;

-- 2017-04-11T18:05:07.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542969
;

-- 2017-04-11T18:05:07.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542970
;

-- 2017-04-11T18:05:07.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542971
;

-- 2017-04-11T18:05:07.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542972
;

-- 2017-04-11T18:05:07.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542973
;

-- 2017-04-11T18:05:07.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542974
;

-- 2017-04-11T18:05:07.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542975
;

-- 2017-04-11T18:05:07.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542976
;

-- 2017-04-11T18:05:07.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542977
;

-- 2017-04-11T18:05:07.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542978
;

-- 2017-04-11T18:05:07.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542979
;

-- 2017-04-11T18:05:07.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542980
;

-- 2017-04-11T18:05:07.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542981
;

-- 2017-04-11T18:05:07.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542982
;

-- 2017-04-11T18:05:07.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542983
;

-- 2017-04-11T18:05:07.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542984
;

-- 2017-04-11T18:05:07.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542985
;

-- 2017-04-11T18:05:34.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540176,540262,TO_TIMESTAMP('2017-04-11 18:05:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','activ',5,TO_TIMESTAMP('2017-04-11 18:05:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T18:07:14.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558083,0,540800,540262,543016,TO_TIMESTAMP('2017-04-11 18:07:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Feiertage ausfallen',10,0,0,TO_TIMESTAMP('2017-04-11 18:07:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T18:07:35.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558084,0,540800,540262,543017,TO_TIMESTAMP('2017-04-11 18:07:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung verschieben',20,0,0,TO_TIMESTAMP('2017-04-11 18:07:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T18:07:42.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-04-11 18:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540259
;

-- 2017-04-11T18:08:38.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558104,0,540800,540262,543018,TO_TIMESTAMP('2017-04-11 18:08:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',5,0,0,TO_TIMESTAMP('2017-04-11 18:08:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T18:09:12.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542965
;

-- 2017-04-11T18:09:12.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542966
;

-- 2017-04-11T18:09:12.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542986
;

-- 2017-04-11T18:09:18.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540256
;

-- 2017-04-11T18:10:45.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542992
;

-- 2017-04-11T18:10:45.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542993
;

-- 2017-04-11T18:10:45.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543002
;

-- 2017-04-11T18:10:45.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543004
;

-- 2017-04-11T18:10:45.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543006
;

-- 2017-04-11T18:10:45.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543014
;

-- 2017-04-11T18:10:45.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543010
;

-- 2017-04-11T18:10:45.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-04-11 18:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543012
;

-- 2017-04-11T18:11:01.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Freitag',Updated=TO_TIMESTAMP('2017-04-11 18:11:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T18:11:09.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-04-11 18:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T18:11:09.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-04-11 18:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543010
;

-- 2017-04-11T18:11:09.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-04-11 18:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543012
;

-- 2017-04-11T18:13:50.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543004
;

-- 2017-04-11T18:13:50.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543014
;

-- 2017-04-11T18:13:50.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T18:13:50.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542993
;

-- 2017-04-11T18:13:50.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543006
;

-- 2017-04-11T18:13:50.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543002
;

-- 2017-04-11T18:13:50.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543010
;

-- 2017-04-11T18:13:50.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-11 18:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543012
;

-- 2017-04-11T18:19:00.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543002
;

-- 2017-04-11T18:19:00.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543004
;

-- 2017-04-11T18:19:00.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543006
;

-- 2017-04-11T18:19:00.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543014
;

-- 2017-04-11T18:19:00.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T18:19:00.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543010
;

-- 2017-04-11T18:19:00.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-04-11 18:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543012
;

-- 2017-04-11T19:06:11.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558076,0,540800,540258,543019,TO_TIMESTAMP('2017-04-11 19:06:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tour',15,0,0,TO_TIMESTAMP('2017-04-11 19:06:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T19:06:27.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543019
;

-- 2017-04-11T19:06:27.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542993
;

-- 2017-04-11T19:06:27.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543002
;

-- 2017-04-11T19:06:27.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543004
;

-- 2017-04-11T19:06:27.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543006
;

-- 2017-04-11T19:06:27.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543014
;

-- 2017-04-11T19:06:27.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T19:06:27.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543010
;

-- 2017-04-11T19:06:27.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-04-11 19:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543012
;

-- 2017-04-11T21:18:18.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-04-11 21:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540259
;

-- 2017-04-11T21:19:29.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540800,540132,TO_TIMESTAMP('2017-04-11 21:19:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','days',20,TO_TIMESTAMP('2017-04-11 21:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:19:35.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540178,540132,TO_TIMESTAMP('2017-04-11 21:19:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-11 21:19:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:19:37.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540179,540132,TO_TIMESTAMP('2017-04-11 21:19:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-04-11 21:19:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:19:54.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540178,540263,TO_TIMESTAMP('2017-04-11 21:19:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','mon-thu',10,TO_TIMESTAMP('2017-04-11 21:19:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:20:09.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558087,0,540800,540263,543020,TO_TIMESTAMP('2017-04-11 21:20:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Montag',10,0,0,TO_TIMESTAMP('2017-04-11 21:20:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:20:30.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558088,0,540800,540263,543021,TO_TIMESTAMP('2017-04-11 21:20:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Montag',20,0,0,TO_TIMESTAMP('2017-04-11 21:20:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:20:40.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558089,0,540800,540263,543022,TO_TIMESTAMP('2017-04-11 21:20:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Dienstag',30,0,0,TO_TIMESTAMP('2017-04-11 21:20:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:20:55.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558090,0,540800,540263,543023,TO_TIMESTAMP('2017-04-11 21:20:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Dienstag',40,0,0,TO_TIMESTAMP('2017-04-11 21:20:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:21:08.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558091,0,540800,540263,543024,TO_TIMESTAMP('2017-04-11 21:21:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mittwoch',50,0,0,TO_TIMESTAMP('2017-04-11 21:21:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:21:19.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558092,0,540800,540263,543025,TO_TIMESTAMP('2017-04-11 21:21:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Mittwoch',60,0,0,TO_TIMESTAMP('2017-04-11 21:21:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:21:28.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558093,0,540800,540263,543026,TO_TIMESTAMP('2017-04-11 21:21:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Donnerstag',70,0,0,TO_TIMESTAMP('2017-04-11 21:21:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:21:41.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558094,0,540800,540263,543027,TO_TIMESTAMP('2017-04-11 21:21:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Donnerstag',80,0,0,TO_TIMESTAMP('2017-04-11 21:21:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:22:12.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540179,540264,TO_TIMESTAMP('2017-04-11 21:22:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','fri-sun',10,TO_TIMESTAMP('2017-04-11 21:22:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:22:24.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558095,0,540800,540264,543028,TO_TIMESTAMP('2017-04-11 21:22:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Freitag',10,0,0,TO_TIMESTAMP('2017-04-11 21:22:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:22:35.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558096,0,540800,540264,543029,TO_TIMESTAMP('2017-04-11 21:22:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Freitag',20,0,0,TO_TIMESTAMP('2017-04-11 21:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:22:43.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558097,0,540800,540264,543030,TO_TIMESTAMP('2017-04-11 21:22:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Samstag',30,0,0,TO_TIMESTAMP('2017-04-11 21:22:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:22:54.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558098,0,540800,540264,543031,TO_TIMESTAMP('2017-04-11 21:22:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Samstag',40,0,0,TO_TIMESTAMP('2017-04-11 21:22:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:23:04.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558099,0,540800,540264,543032,TO_TIMESTAMP('2017-04-11 21:23:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sonntag',50,0,0,TO_TIMESTAMP('2017-04-11 21:23:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:23:16.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558100,0,540800,540264,543033,TO_TIMESTAMP('2017-04-11 21:23:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeit Sonntag',60,0,0,TO_TIMESTAMP('2017-04-11 21:23:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:23:41.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543002
;

-- 2017-04-11T21:23:41.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543003
;

-- 2017-04-11T21:23:41.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543004
;

-- 2017-04-11T21:23:41.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543005
;

-- 2017-04-11T21:23:41.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543006
;

-- 2017-04-11T21:23:41.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543007
;

-- 2017-04-11T21:23:41.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543014
;

-- 2017-04-11T21:23:41.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543015
;

-- 2017-04-11T21:23:45.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540261
;

-- 2017-04-11T21:23:57.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543008
;

-- 2017-04-11T21:23:57.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543009
;

-- 2017-04-11T21:23:57.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543010
;

-- 2017-04-11T21:23:57.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543011
;

-- 2017-04-11T21:23:57.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543012
;

-- 2017-04-11T21:23:57.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543013
;

-- 2017-04-11T21:24:00.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540260
;

-- 2017-04-11T21:24:24.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542994
;

-- 2017-04-11T21:24:26.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542995
;

-- 2017-04-11T21:24:31.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540259
;

-- 2017-04-11T21:25:02.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558080,0,540800,540264,543034,TO_TIMESTAMP('2017-04-11 21:25:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',70,0,0,TO_TIMESTAMP('2017-04-11 21:25:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:25:11.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558079,0,540800,540264,543035,TO_TIMESTAMP('2017-04-11 21:25:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',80,0,0,TO_TIMESTAMP('2017-04-11 21:25:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-11T21:29:59.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543020
;

-- 2017-04-11T21:29:59.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543022
;

-- 2017-04-11T21:29:59.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543024
;

-- 2017-04-11T21:29:59.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543026
;

-- 2017-04-11T21:29:59.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543028
;

-- 2017-04-11T21:29:59.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543030
;

-- 2017-04-11T21:29:59.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-04-11 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543032
;

-- 2017-04-11T21:30:30.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-04-11 21:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542992
;

-- 2017-04-11T21:30:30.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-04-11 21:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543019
;

-- 2017-04-11T21:30:30.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-04-11 21:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542993
;


