-- 2018-02-19T11:19:27.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,IsActive,CreatedBy,WindowType,Processing,IsSOTrx,EntityType,IsBetaFunctionality,IsEnableRemoteCacheInvalidation,IsDefault,IsOneInstanceOnly,AD_Window_ID,AD_Org_ID,Name,Updated,UpdatedBy,Created,WinHeight,WinWidth) VALUES (0,'Y',100,'M','N','Y','de.metas.order','N','N','N','N',540415,0,'Compensation Group Schema',TO_TIMESTAMP('2018-02-19 11:19:27','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-02-19 11:19:27','YYYY-MM-DD HH24:MI:SS'),0,0)
;

-- 2018-02-19T11:19:27.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540415 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2018-02-19T11:19:47.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (HasTree,AD_Window_ID,IsSingleRow,AD_Client_ID,IsActive,CreatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,EntityType,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,AD_Org_ID,Name,UpdatedBy,Created,SeqNo,TabLevel,MaxQueryRecords,Updated) VALUES ('N',540415,'N',0,'Y',100,'N','N','N','N','N','N','de.metas.order','Y','N','N','Y','Y','Y','N',540940,541041,'N','Y',0,'Schema',100,TO_TIMESTAMP('2018-02-19 11:19:47','YYYY-MM-DD HH24:MI:SS'),10,0,0,TO_TIMESTAMP('2018-02-19 11:19:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:19:47.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=541041 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-02-19T11:19:53.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541041,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',562789,559224,'Mandant für diese Installation.',0,'Mandant',100,10,TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:19:53.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:19:53.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541041,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',562790,559225,'Organisatorische Einheit des Mandanten',0,'Sektion',100,10,TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:19:53.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:19:53.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541041,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',562791,559228,'Der Eintrag ist im System aktiv',0,'Aktiv',100,1,TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:19:53.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:19:53.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541041,'N','N','N','N','N',0,'Y',100,'N','de.metas.order',562792,'N',559231,0,'Compensation Group Schema',100,10,TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:19:53.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:19:53.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541041,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',562793,559232,'Alphanumeric identifier of the entity',0,'Name',100,255,TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:19:53','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:19:53.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:20:07.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562789
;

-- 2018-02-19T11:20:07.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562790
;

-- 2018-02-19T11:20:07.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562791
;

-- 2018-02-19T11:20:07.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2018-02-19 11:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562793
;

-- 2018-02-19T11:20:15.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562789
;

-- 2018-02-19T11:20:15.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562790
;

-- 2018-02-19T11:20:15.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562791
;

-- 2018-02-19T11:20:15.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-02-19 11:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562793
;

-- 2018-02-19T11:21:33.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (HasTree,AD_Column_ID,AD_Window_ID,IsSingleRow,AD_Client_ID,IsActive,CreatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,EntityType,IsInsertRecord,IsAdvancedTab,Parent_Column_ID,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,AD_Org_ID,Name,UpdatedBy,Created,SeqNo,TabLevel,MaxQueryRecords,Updated) VALUES ('N',559241,540415,'N',0,'Y',100,'N','N','N','N','N','N','de.metas.order','Y','N',559231,'N','Y','Y','Y','N',540941,541042,'N','Y',0,'Lines',100,TO_TIMESTAMP('2018-02-19 11:21:33','YYYY-MM-DD HH24:MI:SS'),20,1,0,TO_TIMESTAMP('2018-02-19 11:21:33','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:33.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=541042 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-02-19T11:21:42.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',562794,559233,'Mandant für diese Installation.',0,'Mandant',100,10,TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:42.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:21:42.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',562795,559234,'Organisatorische Einheit des Mandanten',0,'Sektion',100,10,TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:42.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:21:42.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',562796,559237,'Der Eintrag ist im System aktiv',0,'Aktiv',100,1,TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:42.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:21:42.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'N','N','N','N','N',0,'Y',100,'N','de.metas.order',562797,'N',559240,0,'Compensation Group Schema Line',100,10,TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:42.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:21:42.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order',562798,559241,0,'Compensation Group Schema',100,10,TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:42.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:21:43.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',562799,559242,'Produkt, Leistung, Artikel',0,'Produkt',100,10,TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 11:21:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T11:21:43.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T11:21:54.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562794
;

-- 2018-02-19T11:21:54.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562795
;

-- 2018-02-19T11:21:54.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562798
;

-- 2018-02-19T11:21:54.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2018-02-19 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562796
;

-- 2018-02-19T11:21:54.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2018-02-19 11:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562799
;

-- 2018-02-19T11:22:05.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562794
;

-- 2018-02-19T11:22:05.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562795
;

-- 2018-02-19T11:22:05.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562798
;

-- 2018-02-19T11:22:05.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-19 11:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562796
;

-- 2018-02-19T11:22:05.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-02-19 11:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562799
;

-- 2018-02-19T11:22:17.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540415,Updated=TO_TIMESTAMP('2018-02-19 11:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540941
;

-- 2018-02-19T11:22:23.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540415,Updated=TO_TIMESTAMP('2018-02-19 11:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540940
;

