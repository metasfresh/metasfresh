-- 2019-03-15T20:43:19.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576241,0,540608,TO_TIMESTAMP('2019-03-15 20:43:19','YYYY-MM-DD HH24:MI:SS'),100,'CreditPass Einstellungen verwalten','de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','CreditPass Einstellungen','N',TO_TIMESTAMP('2019-03-15 20:43:19','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-03-15T20:43:19.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540608 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-03-15T20:43:20.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(576241) 
;

-- 2019-03-15T20:50:43.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576241,0,541674,541186,540608,'Y',TO_TIMESTAMP('2019-03-15 20:50:43','YYYY-MM-DD HH24:MI:SS'),100,'CreditPass Einstellungen verwalten','de.metas.vertical.creditscore.creditpass','N','N','CS_Creditpass_Config','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'CreditPass Einstellungen','N',10,0,TO_TIMESTAMP('2019-03-15 20:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:50:43.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541674 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-03-15T20:50:43.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576241) 
;

-- 2019-03-15T20:51:41.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564346,577788,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.vertical.creditscore.creditpass','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:41.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:41.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564347,577789,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.creditscore.creditpass','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:41.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:41.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564351,577790,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.vertical.creditscore.creditpass','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:41.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:41.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564352,577791,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','N','CS Creditpass Configuration',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:41.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:41.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564354,577792,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,'',50,'de.metas.vertical.creditscore.creditpass','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','N','N','N','N','N','Kennwort',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:41.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:41.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564358,577793,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.vertical.creditscore.creditpass','The authorization ID required to access the telego/creditPass API','Y','N','N','N','N','N','N','N','Berechtigungs ID',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:41.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:41.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564359,577794,0,541674,TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','N','REST API URL',TO_TIMESTAMP('2019-03-15 20:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:42.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:42.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564361,577795,0,541674,TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100,3,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','N','Grund der Anfrage',TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:42.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:42.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564490,577796,0,541674,TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100,3,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','N','Creditpass-Prüfung wiederholen ',TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:42.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:42.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564491,577797,0,541674,TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100,'Bestellungen wie ausgewählt abschließen',50,'de.metas.vertical.creditscore.creditpass','Bestellungen wie ausgewählt abschließen, wenn credipass manuelle Prüfung empfiehlt oder wenn creditpass fehl schlägt','Y','N','N','N','N','N','N','N','Standard Ergebnis',TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:42.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:51:42.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564492,577798,0,541674,TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100,'Benutzer für die Benachrichtigung zur manuellen Prüfung',10,'de.metas.vertical.creditscore.creditpass','','Y','N','N','N','N','N','N','N','Benutzer für manuelle Prüfung',TO_TIMESTAMP('2019-03-15 20:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:51:42.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T20:52:45.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541674,541227,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-15T20:52:45.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541227 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-15T20:52:45.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541564,541227,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:52:45.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541565,541227,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:52:45.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541564,542366,TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-15 20:52:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:54:16.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577788
;

-- 2019-03-15T20:54:16.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577789
;

-- 2019-03-15T20:54:17.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577790
;

-- 2019-03-15T20:54:18.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577791
;

-- 2019-03-15T20:54:18.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577792
;

-- 2019-03-15T20:54:19.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577793
;

-- 2019-03-15T20:54:20.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577794
;

-- 2019-03-15T20:54:20.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577795
;

-- 2019-03-15T20:54:21.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577796
;

-- 2019-03-15T20:54:21.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577797
;

-- 2019-03-15T20:54:40.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577798
;

-- 2019-03-15T20:54:49.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-03-15 20:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577795
;

-- 2019-03-15T20:56:35.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577788
;

-- 2019-03-15T20:56:35.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577789
;

-- 2019-03-15T20:56:36.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577790
;

-- 2019-03-15T20:56:37.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577791
;

-- 2019-03-15T20:56:37.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577792
;

-- 2019-03-15T20:56:38.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577793
;

-- 2019-03-15T20:56:38.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577794
;

-- 2019-03-15T20:56:39.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577795
;

-- 2019-03-15T20:56:39.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577796
;

-- 2019-03-15T20:56:40.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577797
;

-- 2019-03-15T20:56:41.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-15 20:56:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577798
;

-- 2019-03-15T20:57:27.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542366
;

-- 2019-03-15T20:57:32.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541564
;

-- 2019-03-15T20:57:35.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541565
;

-- 2019-03-15T20:57:40.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541227
;

-- 2019-03-15T20:57:40.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541227
;

-- 2019-03-15T20:57:59.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541674,541228,TO_TIMESTAMP('2019-03-15 20:57:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-15 20:57:58','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-15T20:57:59.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541228 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-15T20:57:59.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541566,541228,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541567,541228,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541566,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577788,0,541674,557958,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','Y','Mandant',10,0,10,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577789,0,541674,557959,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','Sektion',20,0,20,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577790,0,541674,557960,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','Y','Aktiv',30,0,30,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577791,0,541674,557961,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','CS Creditpass Configuration',40,0,40,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577792,0,541674,557962,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','Y','N','Y','Kennwort',50,0,50,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577793,0,541674,557963,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'The authorization ID required to access the telego/creditPass API','Y','N','Y','N','Y','Berechtigungs ID',60,0,60,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577794,0,541674,557964,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','REST API URL',70,0,70,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577795,0,541674,557965,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Grund der Anfrage',80,0,80,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577796,0,541674,557966,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Creditpass-Prüfung wiederholen ',90,0,90,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:57:59.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577797,0,541674,557967,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Bestellungen wie ausgewählt abschließen','Bestellungen wie ausgewählt abschließen, wenn credipass manuelle Prüfung empfiehlt oder wenn creditpass fehl schlägt','Y','N','Y','N','Y','Standard Ergebnis',100,0,100,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T20:58:00.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577798,0,541674,557968,542367,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Benutzer für die Benachrichtigung zur manuellen Prüfung','','Y','N','Y','N','Y','Benutzer für manuelle Prüfung',110,0,110,TO_TIMESTAMP('2019-03-15 20:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:00:53.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541566,542368,TO_TIMESTAMP('2019-03-15 21:00:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','auth',20,TO_TIMESTAMP('2019-03-15 21:00:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:01:20.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='auth',Updated=TO_TIMESTAMP('2019-03-15 21:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542367
;

-- 2019-03-15T21:04:05.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='logic',Updated=TO_TIMESTAMP('2019-03-15 21:04:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542368
;

-- 2019-03-15T21:04:13.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541566,542369,TO_TIMESTAMP('2019-03-15 21:04:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2019-03-15 21:04:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:05:09.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541566,542370,TO_TIMESTAMP('2019-03-15 21:05:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','info',40,TO_TIMESTAMP('2019-03-15 21:05:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:06:13.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542368, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-15 21:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557968
;

-- 2019-03-15T21:06:24.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542368, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-15 21:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557967
;

-- 2019-03-15T21:08:41.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542368, SeqNo=30,Updated=TO_TIMESTAMP('2019-03-15 21:08:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557966
;

-- 2019-03-15T21:08:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542370, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-15 21:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557965
;

-- 2019-03-15T21:10:33.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=557961
;

-- 2019-03-15T21:10:52.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2019-03-15 21:10:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577791
;

-- 2019-03-15T21:11:14.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542370, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-15 21:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557960
;

-- 2019-03-15T21:11:26.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542369, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-15 21:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557959
;

-- 2019-03-15T21:11:35.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542369, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-15 21:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557958
;

-- 2019-03-15T21:12:11.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541567, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-15 21:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542369
;

-- 2019-03-15T21:12:17.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541567, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-15 21:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542370
;

-- 2019-03-15T21:14:12.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576231,0,541675,541193,540608,'Y',TO_TIMESTAMP('2019-03-15 21:14:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.creditpass','N','N','CS_Creditpass_Config_PaymentRule','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'CS Creditpass Configuration payment rule','N',10,0,TO_TIMESTAMP('2019-03-15 21:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:14:12.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541675 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-03-15T21:14:12.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576231) 
;

-- 2019-03-15T21:15:33.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=20, TabLevel=1,Updated=TO_TIMESTAMP('2019-03-15 21:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541675
;

-- 2019-03-15T21:19:30.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.creditscore.creditpass',Updated=TO_TIMESTAMP('2019-03-15 21:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576231
;

-- 2019-03-15T21:20:58.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576242,0,TO_TIMESTAMP('2019-03-15 21:20:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.creditpass','Y','Zahlungsarten','Zahlungsarten',TO_TIMESTAMP('2019-03-15 21:20:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:20:58.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576242 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-15T21:21:28.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Payment rule', PrintName='Payment rule',Updated=TO_TIMESTAMP('2019-03-15 21:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576242 AND AD_Language='en_US'
;

-- 2019-03-15T21:21:28.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576242,'en_US') 
;

-- 2019-03-15T21:21:52.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576231,627396,0,541675,540608,TO_TIMESTAMP('2019-03-15 21:21:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-15 21:21:52','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-15T21:21:55.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=576242, Name='Zahlungsarten',Updated=TO_TIMESTAMP('2019-03-15 21:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541675
;

-- 2019-03-15T21:21:55.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=627396
;

-- 2019-03-15T21:21:55.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576242,627397,0,541675,540608,TO_TIMESTAMP('2019-03-15 21:21:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-15 21:21:55','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-15T21:21:55.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576242) 
;

-- 2019-03-15T21:30:28.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564493,577799,0,541675,TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.vertical.creditscore.creditpass','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:28.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:28.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564494,577800,0,541675,TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.creditscore.creditpass','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:28.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:28.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564498,577801,0,541675,TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','N','CS Creditpass Configuration payment rule',TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:28.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:29.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564500,577802,0,541675,TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.vertical.creditscore.creditpass','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-03-15 21:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:29.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:29.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564502,577803,0,541675,TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird',50,'de.metas.vertical.creditscore.creditpass','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','Y','N','N','N','N','N','Zahlungsweise',TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:29.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:29.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564503,577804,0,541675,TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.vertical.creditscore.creditpass','Y','Y','N','N','N','N','N','Kaufstyp',TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:29.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:29.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564504,577805,0,541675,TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.vertical.creditscore.creditpass','Y','Y','N','N','N','N','N','Preis der Überprüfung',TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:29.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:30:29.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564526,577806,0,541675,TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.creditpass','Y','Y','N','N','N','N','N','CS Creditpass Configuration',TO_TIMESTAMP('2019-03-15 21:30:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:30:29.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-15T21:32:47.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541675,541229,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-15T21:32:47.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541229 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-15T21:32:47.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541568,541229,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:47.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541568,542371,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577799,0,541675,557969,542371,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','Y','Mandant',0,0,10,TO_TIMESTAMP('2019-03-15 21:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577800,0,541675,557970,542371,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','Y','Sektion',0,0,20,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577802,0,541675,557971,542371,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','Y','Aktiv',0,0,30,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577803,0,541675,557972,542371,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','N','N','Y','Zahlungsweise',0,0,40,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577804,0,541675,557973,542371,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Kaufstyp',0,0,50,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577805,0,541675,557974,542371,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Preis der Überprüfung',0,0,60,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:32:48.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,577806,0,541675,557975,542371,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','CS Creditpass Configuration',0,0,70,TO_TIMESTAMP('2019-03-15 21:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-15T21:34:50.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557975
;

-- 2019-03-15T21:34:50.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557969
;

-- 2019-03-15T21:34:50.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557972
;

-- 2019-03-15T21:34:50.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557973
;

-- 2019-03-15T21:34:50.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557974
;

-- 2019-03-15T21:34:50.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557971
;

-- 2019-03-15T21:34:50.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-03-15 21:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557970
;

-- 2019-03-18T11:26:36.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576246,0,TO_TIMESTAMP('2019-03-18 11:26:35','YYYY-MM-DD HH24:MI:SS'),100,' Zeigt die Kredit-Score Ergebnisse an','de.metas.vertical.creditscore.base','Y','Kredit-Score Transaktionen','Kredit-Score Transaktionen',TO_TIMESTAMP('2019-03-18 11:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-18T11:26:36.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576246 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-18T11:27:32.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Shows the credit score results', IsTranslated='Y', Name='Credit score transactions', PrintName='Credit score transactions',Updated=TO_TIMESTAMP('2019-03-18 11:27:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576246 AND AD_Language='en_US'
;

-- 2019-03-18T11:27:32.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576246,'en_US') 
;

-- 2019-03-18T11:27:44.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=572502,Updated=TO_TIMESTAMP('2019-03-18 11:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=224
;

-- 2019-03-18T11:27:44.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=623634
;

-- 2019-03-18T11:27:44.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,572502,627398,0,224,123,TO_TIMESTAMP('2019-03-18 11:27:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-18 11:27:44','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-18T11:27:44.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572502) 
;

-- 2019-03-18T11:27:44.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=623626
;

-- 2019-03-18T11:27:44.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,572502,627399,0,212,123,TO_TIMESTAMP('2019-03-18 11:27:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-18 11:27:44','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-18T11:30:58.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,564523,576233,0,541677,541194,123,'Y',TO_TIMESTAMP('2019-03-18 11:30:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.base','N','N','CS_Transaction_Results','Y','Y','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'CS_Transaction_Results','N',260,2,TO_TIMESTAMP('2019-03-18 11:30:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-18T11:30:58.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541677 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-03-18T11:30:58.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576233) 
;

-- 2019-03-18T14:02:07.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576247,0,TO_TIMESTAMP('2019-03-18 14:02:06','YYYY-MM-DD HH24:MI:SS'),100,'Einstellungen die für CreditPass Aufrufe benutzt werden','de.metas.vertical.creditscore.creditpass','Y','CreditPass Einstellungen','CreditPass Einstellungen',TO_TIMESTAMP('2019-03-18 14:02:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-18T14:02:07.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576247 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-18T14:02:53.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Configuration used for CreditPass requests', IsTranslated='Y', Name='CreditPass configuration', PrintName='CreditPass configuration',Updated=TO_TIMESTAMP('2019-03-18 14:02:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576247 AND AD_Language='en_US'
;

-- 2019-03-18T14:02:53.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576247,'en_US') 
;

-- 2019-03-18T14:04:43.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,576241,541221,0,TO_TIMESTAMP('2019-03-18 14:04:43','YYYY-MM-DD HH24:MI:SS'),100,'CreditPass Einstellungen verwalten','de.metas.vertical.creditscore.creditpass','CreditPass Einstellungen','Y','N','N','N','N','CreditPass Einstellungen',TO_TIMESTAMP('2019-03-18 14:04:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-18T14:04:43.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541221 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-18T14:04:43.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541221, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541221)
;

-- 2019-03-18T14:04:43.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576241) 
;

-- 2019-03-18T14:04:43.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=452 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=454 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=466 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540716 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=468 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=467 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=463 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=549 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=471 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=205 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=204 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=360 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=493 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=206 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=516 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540495 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540688 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541039 AND AD_Tree_ID=10
;

-- 2019-03-18T14:04:43.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=203, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2019-03-18T14:05:59.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Window_ID=540608, InternalName='CreditPassConfiguration',Updated=TO_TIMESTAMP('2019-03-18 14:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541221
;

-- 2019-03-18T14:09:23.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='CreditPass Einstellungen',Updated=TO_TIMESTAMP('2019-03-18 14:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541221
;

-- 2019-03-18T14:12:43.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2019-03-18T14:12:43.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2019-03-18T14:29:22.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=576247, Description='Einstellungen die fr CreditPass Aufrufe benutzt werden', WEBUI_NameBrowse=NULL,Updated=TO_TIMESTAMP('2019-03-18 14:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541221
;

-- 2019-03-18T14:29:22.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576247) 
;

-- 2019-03-18T14:30:34.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET WEBUI_NameBrowse='CreditPass Einstellungen', WEBUI_NameNew='Neue CreditPass Einstellung', WEBUI_NameNewBreadcrumb='CreditPass Einstellungen',Updated=TO_TIMESTAMP('2019-03-18 14:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576247
;

-- 2019-03-18T14:30:34.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'CreditPass Einstellungen', Description = 'Einstellungen die fur CreditPass Aufrufe benutzt werden', WEBUI_NameBrowse = 'CreditPass Einstellungen', WEBUI_NameNew = 'Neue CreditPass Einstellung', WEBUI_NameNewBreadcrumb = 'CreditPass Einstellungen' WHERE AD_Element_ID = 576247
;

-- 2019-03-18T14:31:02.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.creditscore.creditpass',Updated=TO_TIMESTAMP('2019-03-18 14:31:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576241
;

-- 2019-03-18T14:42:11.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2019-03-18 14:42:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541674
;

-- 2019-03-18T14:57:57.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsCreateNew='Y',Updated=TO_TIMESTAMP('2019-03-18 14:57:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541221
;

-- 2019-03-18T15:21:18.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameNewBreadcrumb='RestAPIBaseURL',Updated=TO_TIMESTAMP('2019-03-18 15:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541221
;

-- 2019-03-18T15:25:53.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsGridModeOnly='Y', IsSearchActive='N',Updated=TO_TIMESTAMP('2019-03-18 15:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541675
;

-- 2019-03-18T15:33:24.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameNewBreadcrumb='CreditPass Einstellungen',Updated=TO_TIMESTAMP('2019-03-18 15:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541221
;

-- 2019-03-18T15:38:58.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,576241,541223,0,540608,TO_TIMESTAMP('2019-03-18 15:38:58','YYYY-MM-DD HH24:MI:SS'),100,'CreditPass Einstellungen verwalten','U','_CreditPassConfiguration','Y','Y','N','N','N','CreditPass Einstellungen',TO_TIMESTAMP('2019-03-18 15:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-18T15:38:58.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541223 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-18T15:38:58.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541223, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541223)
;

-- 2019-03-18T15:38:58.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576241) 
;

-- 2019-03-18T15:38:58.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2019-03-18T15:38:58.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541223 AND AD_Tree_ID=10
;

-- 2019-03-18T15:42:50.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541223
;

-- 2019-03-18T15:42:50.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=541223
;

-- 2019-03-18T15:42:50.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541223 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2019-03-18T17:18:53.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsAdvancedTab='N', IsSingleRow='Y',Updated=TO_TIMESTAMP('2019-03-18 17:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541677
;

-- 2019-03-18T17:19:37.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-03-18 17:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541677
;

-- 2019-03-18T19:55:16.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576250,0,TO_TIMESTAMP('2019-03-18 19:55:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.creditpass','Y','Geschäftspartnergruppen','Geschäftspartnergruppen',TO_TIMESTAMP('2019-03-18 19:55:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-18T19:55:16.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576250 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-18T19:55:41.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Business partner groups', PrintName='Business partner groups',Updated=TO_TIMESTAMP('2019-03-18 19:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576250 AND AD_Language='en_US'
;

-- 2019-03-18T19:55:41.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576250,'en_US') 
;

-- 2019-03-18T19:56:27.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The business partner groups for creditPass configuration',Updated=TO_TIMESTAMP('2019-03-18 19:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576250 AND AD_Language='en_US'
;

-- 2019-03-18T19:56:27.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576250,'en_US') 
;

-- 2019-03-18T19:57:45.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description=' Die Geschäftspartnergruppen für die creditPass-Konfiguration',Updated=TO_TIMESTAMP('2019-03-18 19:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576250 AND AD_Language='de_CH'
;

-- 2019-03-18T19:57:45.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576250,'de_CH') 
;

-- 2019-03-18T19:57:46.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description=' Die Geschäftspartnergruppen für die creditPass-Konfiguration',Updated=TO_TIMESTAMP('2019-03-18 19:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576250 AND AD_Language='de_DE'
;

-- 2019-03-18T19:57:46.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576250,'de_DE') 
;

-- 2019-03-18T19:57:46.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576250,'de_DE') 
;

------

-- 2019-03-20T14:36:17.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:17.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:17.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:18.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T14:36:43.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541232 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

/*
-- 2019-03-20T14:38:11.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575970,627411,563151,0,540424,TO_TIMESTAMP('2019-03-20 14:38:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575834,627412,569780,0,113,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,541510,627413,570309,0,53005,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,109,627414,570681,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1159,627415,570682,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627416,570683,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627417,570684,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627418,570685,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,469,627419,570686,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:11.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1171,627420,570687,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,420,627421,570688,0,540552,TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,542055,627422,570689,0,540552,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,541976,627423,570690,0,172,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575886,627424,570691,0,172,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575887,627425,570692,0,172,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575888,627426,570693,0,172,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575885,627427,570694,0,540334,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543736,627428,570695,0,540334,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,2019,627429,570707,0,140,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627430,570708,0,140,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:12.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575896,627431,570721,0,540334,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575898,627432,570722,0,540334,TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,983,627433,570723,0,172,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,544128,627434,570736,0,540433,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627435,570737,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1047,627436,570738,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543546,627437,570739,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575903,627438,570740,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575904,627439,570741,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575905,627440,570742,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575906,627441,570743,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575910,627442,570744,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:13.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575911,627443,570745,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575912,627444,570746,0,540386,TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575913,627445,570747,0,540386,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575915,627446,570748,0,540095,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575916,627447,570749,0,540490,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575917,627448,570750,0,540490,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575918,627449,570751,0,540490,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627450,570752,0,123,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627451,570753,0,123,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627452,570754,0,123,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575932,627453,570755,0,123,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,187,627454,570756,0,123,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575933,627455,570757,0,123,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:14.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,505135,627456,570867,0,172,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,505274,627457,570868,0,172,TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575947,627458,570869,0,172,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575948,627459,570870,0,172,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627460,571707,0,125,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627461,571708,0,125,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627462,571709,0,125,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575972,627463,571710,0,125,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,2700,627464,571711,0,125,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,181,627465,571712,0,125,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575995,627466,572919,0,140,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-20T14:38:15.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575996,627467,572920,0,140,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,125,627468,574165,0,100,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:15.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576032,627469,574166,0,100,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576033,627470,574167,0,100,TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576034,627471,574168,0,100,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,125,627472,574169,0,165,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576032,627473,574170,0,165,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576033,627474,574171,0,165,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576034,627475,574172,0,165,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627476,574173,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627477,574174,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627478,574175,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576035,627479,574176,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576036,627480,574177,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,469,627481,574178,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,275,627482,574179,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:16.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576041,627483,574180,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627484,574181,0,540571,TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627485,574182,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627486,574183,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576037,627487,574184,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576035,627488,574185,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,469,627489,574186,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,275,627490,574187,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576041,627491,574188,0,540571,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627492,574189,0,540572,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627493,574190,0,540572,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627494,574191,0,540572,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576038,627495,574192,0,540572,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:17.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,469,627496,574194,0,540572,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,275,627497,574195,0,540572,TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627498,574196,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,392,627499,574197,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,544018,627500,574198,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576039,627501,574199,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,54444,627502,574200,0,165,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,54444,627503,574201,0,100,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627504,574202,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627505,574203,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627506,574204,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576040,627507,574205,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576038,627508,574206,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:18.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,469,627509,574207,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627510,574208,0,540572,TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576070,627511,574517,0,146,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576070,627512,574518,0,540321,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,544051,627513,574519,0,540327,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576095,627514,574792,0,165,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627515,574793,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627516,574794,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627517,574795,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576099,627518,574796,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,187,627519,574797,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1383,627520,574798,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:19.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,454,627521,574799,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,617,627522,574800,0,540580,TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,618,627523,574801,0,540580,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,275,627524,574802,0,540580,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,957,627525,574803,0,540580,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,193,627526,574804,0,540580,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,192,627527,574805,0,540580,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,211,627528,574806,0,540580,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576109,627529,574809,0,102,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627530,574888,0,540582,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627531,574889,0,540582,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627532,574890,0,540582,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:20.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576076,627533,574891,0,540582,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,187,627534,574892,0,540582,TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,454,627535,574893,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,540316,627536,574894,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576077,627537,574895,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576110,627538,574896,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576111,627539,574897,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576114,627540,574898,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576115,627541,574899,0,540582,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627542,574960,0,540571,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627543,574961,0,540571,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,275,627544,574962,0,540572,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576141,627545,574963,0,151,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:21.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576142,627546,574964,0,151,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576143,627547,574965,0,151,TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576144,627548,574966,0,151,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576145,627549,574967,0,165,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627550,575071,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627551,575072,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576148,627552,575073,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,912,627553,575074,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,913,627554,575075,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627555,575076,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1047,627556,575077,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,524,627557,575078,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:22.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543753,627558,575079,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,544091,627559,575080,0,540590,TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,187,627560,575081,0,540590,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,280,627561,575082,0,540431,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576156,627562,575089,0,140,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627563,575090,0,540591,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627564,575091,0,540591,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627565,575092,0,540591,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576005,627566,575093,0,540591,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576157,627567,575094,0,540591,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576158,627568,575720,0,189,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627569,575721,0,540593,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:23.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627570,575722,0,540593,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627571,575723,0,540593,TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576133,627572,575724,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,469,627573,575725,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,275,627574,575726,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576134,627575,575727,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576135,627576,575728,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627577,575729,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576037,627578,575730,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627579,575731,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627580,575732,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627581,575733,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:24.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576159,627582,575734,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576133,627583,575735,0,540593,TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,566,627584,575736,0,540593,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576159,627585,575737,0,540572,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,544051,627586,575998,0,540368,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,540617,627587,576001,0,540368,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543913,627588,576004,0,540368,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,505,627589,576005,0,540368,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,301,627590,576006,0,540368,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,542820,627591,576009,0,172,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,55445,627592,576010,0,172,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:25.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,2585,627593,576011,0,172,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,542819,627594,576014,0,172,TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543753,627595,577694,0,540368,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,2019,627596,577712,0,53009,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575922,627597,577713,0,53009,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575923,627598,577714,0,53009,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575924,627599,577715,0,53009,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,627600,577799,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,113,627601,577800,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576231,627602,577801,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,348,627603,577802,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1143,627604,577803,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:26.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576185,627605,577804,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:27.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576232,627606,577805,0,540608,TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:27.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576178,627607,577806,0,540608,TO_TIMESTAMP('2019-03-20 14:38:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:28.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,572858,627618,0,541395,540552,TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:28.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575972,627619,0,541434,125,TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:28.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576037,627620,0,541520,540571,TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:28.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576038,627621,0,541521,540572,TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576040,627622,0,541522,540572,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576099,627623,0,541551,540580,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576076,627624,0,541555,540582,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576148,627625,0,541566,540590,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576157,627626,0,541567,540591,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576159,627627,0,541591,540593,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576241,627628,0,541674,540608,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576233,627629,0,541677,123,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:29.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574071,627630,0,100,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574072,627631,0,101,TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574300,627632,0,102,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573927,627633,0,103,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574075,627634,0,104,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574076,627635,0,105,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573921,627636,0,106,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574267,627637,0,107,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574079,627638,0,108,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574081,627639,0,109,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574082,627640,0,110,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573922,627641,0,111,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:30.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573956,627642,0,112,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574477,627643,0,113,TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574442,627644,0,114,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574083,627645,0,115,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574085,627646,0,116,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574128,627647,0,117,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574111,627648,0,118,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574086,627649,0,120,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574212,627650,0,121,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574087,627651,0,122,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:31.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574089,627652,0,123,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574090,627653,0,125,TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574426,627654,0,127,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574074,627655,0,128,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574091,627656,0,129,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573947,627657,0,130,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574443,627658,0,131,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574444,627659,0,132,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574101,627660,0,134,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574445,627661,0,135,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:32.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574092,627662,0,137,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574441,627663,0,138,TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574136,627664,0,139,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574080,627665,0,140,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574115,627666,0,141,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574093,627667,0,142,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573965,627668,0,143,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574342,627669,0,144,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573928,627670,0,146,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574238,627671,0,147,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574179,627672,0,150,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:33.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574446,627673,0,151,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573951,627674,0,152,TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574335,627675,0,153,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574094,627676,0,154,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574095,627677,0,158,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574245,627678,0,159,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574096,627679,0,160,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574097,627680,0,161,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574428,627681,0,162,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574098,627682,0,163,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574099,627683,0,165,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574145,627684,0,167,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574225,627685,0,168,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:34.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574077,627686,0,169,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574100,627687,0,170,TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573937,627688,0,172,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574102,627689,0,174,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574103,627690,0,175,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574235,627691,0,176,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574104,627692,0,178,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573929,627693,0,180,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574084,627694,0,181,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574105,627695,0,183,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574447,627696,0,184,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574394,627697,0,185,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573962,627698,0,186,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574448,627699,0,187,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:35.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574107,627700,0,188,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574490,627701,0,189,TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574108,627702,0,192,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574110,627703,0,193,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573932,627704,0,194,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574112,627705,0,195,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574414,627706,0,196,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574470,627707,0,197,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574208,627708,0,198,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574360,627709,0,200,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574137,627710,0,201,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574142,627711,0,203,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:36.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573945,627712,0,204,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574117,627713,0,205,TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574116,627714,0,206,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574231,627715,0,207,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573948,627716,0,208,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574243,627717,0,210,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574408,627718,0,212,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573941,627719,0,213,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573940,627720,0,215,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574118,627721,0,216,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574119,627722,0,217,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574120,627723,0,218,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574121,627724,0,220,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574415,627725,0,221,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:37.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574327,627726,0,223,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573933,627727,0,224,TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574450,627728,0,225,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574451,627729,0,226,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574452,627730,0,227,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574266,627731,0,228,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574045,627732,0,229,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574435,627733,0,231,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574138,627734,0,232,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574440,627735,0,233,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574184,627736,0,234,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574185,627737,0,235,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574125,627738,0,236,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:38.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574126,627739,0,237,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574060,627740,0,238,TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574061,627741,0,239,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574425,627742,0,240,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573925,627743,0,241,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574182,627744,0,242,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573926,627745,0,243,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574478,627746,0,244,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574436,627747,0,245,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574437,627748,0,246,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574199,627749,0,247,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574381,627750,0,248,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574194,627751,0,249,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573977,627752,0,250,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574268,627753,0,251,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:39.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574269,627754,0,252,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574438,627755,0,253,TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574183,627756,0,254,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574063,627757,0,255,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573978,627758,0,256,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574210,627759,0,257,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574211,627760,0,258,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574250,627761,0,259,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574064,627762,0,260,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574483,627763,0,261,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573975,627764,0,262,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574065,627765,0,263,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573979,627766,0,264,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574219,627767,0,265,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:40.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574213,627768,0,266,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574222,627769,0,267,TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574066,627770,0,268,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574278,627771,0,269,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574078,627772,0,270,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574067,627773,0,271,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574069,627774,0,272,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574209,627775,0,273,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574263,627776,0,274,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573980,627777,0,275,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574070,627778,0,276,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574467,627779,0,277,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:41.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574197,627780,0,278,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574200,627781,0,279,TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574224,627782,0,280,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573944,627783,0,281,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574003,627784,0,282,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574005,627785,0,283,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574383,627786,0,284,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574006,627787,0,285,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574220,627788,0,286,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573916,627789,0,287,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574431,627790,0,288,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:42.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574432,627791,0,289,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574007,627792,0,290,TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574008,627793,0,291,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574011,627794,0,292,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574260,627795,0,293,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574146,627796,0,294,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574012,627797,0,295,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574223,627798,0,296,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574281,627799,0,297,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574262,627800,0,298,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573953,627801,0,299,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574247,627802,0,300,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574246,627803,0,301,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:43.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574013,627804,0,302,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574014,627805,0,303,TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574015,627806,0,304,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573992,627807,0,305,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574282,627808,0,306,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574016,627809,0,307,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574017,627810,0,308,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574018,627811,0,309,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574004,627812,0,310,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574019,627813,0,312,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574296,627814,0,314,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574188,627815,0,315,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:44.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574259,627816,0,318,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574474,627817,0,319,TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574127,627818,0,320,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574400,627819,0,321,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574020,627820,0,322,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574021,627821,0,323,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574295,627822,0,324,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574258,627823,0,325,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573999,627824,0,326,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573942,627825,0,327,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574001,627826,0,328,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574384,627827,0,329,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574000,627828,0,330,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:45.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574132,627829,0,331,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573991,627830,0,332,TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573915,627831,0,333,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574195,627832,0,334,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574251,627833,0,335,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574252,627834,0,336,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573995,627835,0,337,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573996,627836,0,338,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573998,627837,0,339,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573989,627838,0,340,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574204,627839,0,341,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574244,627840,0,342,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573990,627841,0,343,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573913,627842,0,344,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:46.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574141,627843,0,345,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574140,627844,0,346,TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574139,627845,0,347,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574319,627846,0,348,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574143,627847,0,349,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574276,627848,0,350,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574214,627849,0,351,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574215,627850,0,352,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574429,627851,0,353,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574382,627852,0,354,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573997,627853,0,355,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573914,627854,0,356,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574430,627855,0,358,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:47.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573982,627856,0,359,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573986,627857,0,360,TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574190,627858,0,361,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573987,627859,0,362,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574361,627860,0,363,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574469,627861,0,364,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574255,627862,0,365,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574466,627863,0,366,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574206,627864,0,367,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574216,627865,0,368,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574186,627866,0,369,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574218,627867,0,370,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:48.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573946,627868,0,371,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573988,627869,0,372,TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574275,627870,0,373,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574354,627871,0,374,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574279,627872,0,375,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574265,627873,0,376,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574022,627874,0,377,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574239,627875,0,379,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574191,627876,0,380,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574023,627877,0,381,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574277,627878,0,383,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574270,627879,0,384,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574024,627880,0,385,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:49.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574283,627881,0,386,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574236,627882,0,387,TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574237,627883,0,388,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574433,627884,0,50006,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574192,627885,0,50007,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574272,627886,0,52000,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574274,627887,0,52001,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574273,627888,0,52002,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574271,627889,0,52003,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574025,627890,0,53003,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573938,627891,0,53004,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574172,627892,0,53005,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:50.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574286,627893,0,53006,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574285,627894,0,53007,TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574027,627895,0,53008,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574284,627896,0,53009,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574028,627897,0,53010,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574029,627898,0,53011,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574325,627899,0,53012,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574030,627900,0,53013,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573917,627901,0,53014,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574031,627902,0,53015,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-20T14:38:51.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573918,627903,0,53016,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-20T14:38:51.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574248,627904,0,53017,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:51.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574328,627905,0,53018,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573919,627906,0,53019,TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574434,627907,0,53020,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574032,627908,0,53021,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573920,627909,0,53022,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574033,627910,0,53023,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574034,627911,0,53024,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574035,627912,0,53025,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574036,627913,0,53026,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574037,627914,0,53027,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574289,627915,0,53028,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574038,627916,0,53029,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:52.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574041,627917,0,53032,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574042,627918,0,53033,TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574043,627919,0,53034,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574044,627920,0,53035,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574046,627921,0,53036,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574047,627922,0,53037,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574048,627923,0,53038,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574049,627924,0,53039,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574050,627925,0,53040,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574051,627926,0,53041,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574052,627927,0,53042,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574385,627928,0,53044,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574387,627929,0,53045,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:53.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574388,627930,0,53046,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574389,627931,0,53047,TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574053,627932,0,53048,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574386,627933,0,53049,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574353,627934,0,53050,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574054,627935,0,53051,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574390,627936,0,53052,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574391,627937,0,53053,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573974,627938,0,53054,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574055,627939,0,53055,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574056,627940,0,53056,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574392,627941,0,53057,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573955,627942,0,53058,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:54.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574057,627943,0,53059,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573960,627944,0,53060,TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573961,627945,0,53061,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573963,627946,0,53062,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573943,627947,0,53063,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574402,627948,0,53064,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574058,627949,0,53065,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574249,627950,0,53069,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574198,627951,0,53071,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573950,627952,0,53073,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574476,627953,0,53074,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574114,627954,0,53078,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574165,627955,0,53084,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574059,627956,0,53085,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:55.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574229,627957,0,53086,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574113,627958,0,53087,TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574130,627959,0,53097,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574131,627960,0,53098,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574129,627961,0,53099,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574320,627962,0,53100,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573981,627963,0,53102,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574449,627964,0,53110,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574457,627965,0,53138,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574456,627966,0,53139,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574329,627967,0,53189,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574151,627968,0,500221,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574427,627969,0,504410,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574254,627970,0,504620,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574256,627971,0,540000,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:56.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573939,627972,0,540011,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574405,627973,0,540012,TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574413,627974,0,540013,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574460,627975,0,540014,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574257,627976,0,540016,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574409,627977,0,540018,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574234,627978,0,540019,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573983,627979,0,540020,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574233,627980,0,540022,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574232,627981,0,540024,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574473,627982,0,540025,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574144,627983,0,540026,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574149,627984,0,540027,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574412,627985,0,540028,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:57.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574264,627986,0,540029,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573952,627987,0,540030,TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574472,627988,0,540032,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574410,627989,0,540035,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574202,627990,0,540036,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574240,627991,0,540037,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574465,627992,0,540038,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574464,627993,0,540039,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574193,627994,0,540040,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574227,627995,0,540042,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:58.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574226,627996,0,540043,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574203,627997,0,540044,TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573966,627998,0,540045,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574207,627999,0,540056,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574241,628000,0,540057,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574404,628001,0,540060,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574396,628002,0,540071,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574406,628003,0,540072,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574205,628004,0,540078,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574123,628005,0,540079,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574196,628006,0,540080,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574475,628007,0,540083,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573949,628008,0,540084,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574401,628009,0,540089,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:38:59.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573934,628010,0,540092,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574398,628011,0,540093,TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574221,628012,0,540094,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574148,628013,0,540095,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574106,628014,0,540098,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574062,628015,0,540099,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574122,628016,0,540100,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574411,628017,0,540107,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574124,628018,0,540108,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574171,628019,0,540109,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574175,628020,0,540110,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574150,628021,0,540112,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574133,628022,0,540113,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574455,628023,0,540116,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:00.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574153,628024,0,540117,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574261,628025,0,540120,TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574508,628026,0,540124,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574454,628027,0,540126,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574134,628028,0,540127,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574201,628029,0,540149,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573976,628030,0,540150,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574026,628031,0,540154,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574468,628032,0,540155,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574156,628033,0,540156,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574154,628034,0,540157,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574228,628035,0,540158,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:01.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574176,628036,0,540159,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574152,628037,0,540163,TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574135,628038,0,540165,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574160,628039,0,540166,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574453,628040,0,540167,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574159,628041,0,540168,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574380,628042,0,540169,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574147,628043,0,540170,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574158,628044,0,540171,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574073,628045,0,540172,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:02.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574157,628046,0,540173,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574399,628047,0,540174,TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574395,628048,0,540176,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574407,628049,0,540177,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574161,628050,0,540179,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574162,628051,0,540180,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574163,628052,0,540181,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574164,628053,0,540182,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574167,628054,0,540188,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574168,628055,0,540189,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:03.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573935,628056,0,540190,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574458,628057,0,540191,TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574169,628058,0,540192,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574461,628059,0,540193,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574170,628060,0,540196,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574459,628061,0,540201,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574174,628062,0,540205,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574348,628063,0,540206,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573964,628064,0,540210,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:04.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574462,628065,0,540212,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574463,628066,0,540213,TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574173,628067,0,540215,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574403,628068,0,540223,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574499,628069,0,540224,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573993,628070,0,540225,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573994,628071,0,540226,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574424,628072,0,540230,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574180,628073,0,540231,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574471,628074,0,540247,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574181,628075,0,540248,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:05.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574480,628076,0,540253,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574253,628077,0,540254,TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573970,628078,0,540256,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573973,628079,0,540257,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573967,628080,0,540259,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573972,628081,0,540260,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573968,628082,0,540261,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573969,628083,0,540262,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573971,628084,0,540263,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574178,628085,0,540270,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574177,628086,0,540271,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574287,628087,0,540274,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574288,628088,0,540275,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:06.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574187,628089,0,540276,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574290,628090,0,540279,TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574242,628091,0,540282,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574291,628092,0,540283,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574280,628093,0,540285,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574294,628094,0,540286,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574292,628095,0,540287,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574293,628096,0,540288,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574297,628097,0,540289,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574298,628098,0,540290,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574299,628099,0,540293,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574302,628100,0,540295,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574304,628101,0,540296,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:07.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574301,628102,0,540297,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574305,628103,0,540314,TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574306,628104,0,540315,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574350,628105,0,540316,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574303,628106,0,540317,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574307,628107,0,540320,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574308,628108,0,540321,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574314,628109,0,540322,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574309,628110,0,540323,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574310,628111,0,540325,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:08.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574311,628112,0,540326,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574418,628113,0,540328,TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574312,628114,0,540331,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574313,628115,0,540333,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574338,628116,0,540334,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574316,628117,0,540336,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574479,628118,0,540337,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574317,628119,0,540339,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574318,628120,0,540343,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574326,628121,0,540344,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574330,628122,0,540345,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574321,628123,0,540346,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574322,628124,0,540347,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574323,628125,0,540348,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:09.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574324,628126,0,540349,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574419,628127,0,540350,TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574420,628128,0,540353,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574332,628129,0,540354,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574333,628130,0,540355,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574334,628131,0,540356,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574346,628132,0,540358,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574343,628133,0,540359,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574344,628134,0,540360,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574345,628135,0,540361,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574331,628136,0,540363,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574336,628137,0,540364,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574337,628138,0,540365,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:10.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574339,628139,0,540366,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574341,628140,0,540368,TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574347,628141,0,540370,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574421,628142,0,540371,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574349,628143,0,540373,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574315,628144,0,540375,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574423,628145,0,540376,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574487,628146,0,540380,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574482,628147,0,540381,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574484,628148,0,540382,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574485,628149,0,540383,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574486,628150,0,540384,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574351,628151,0,540386,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:11.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574352,628152,0,540390,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574481,628153,0,540391,TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574357,628154,0,540392,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574358,628155,0,540393,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574359,628156,0,540394,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574422,628157,0,540395,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574369,628158,0,540396,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574489,628159,0,540397,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574362,628160,0,540398,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574371,628161,0,540400,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574372,628162,0,540401,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574373,628163,0,540402,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574374,628164,0,540403,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:12.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574492,628165,0,540404,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574375,628166,0,540405,TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574370,628167,0,540406,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574491,628168,0,540407,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574488,628169,0,540408,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574363,628170,0,540409,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574364,628171,0,540410,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574365,628172,0,540412,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:13.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574368,628173,0,540413,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574367,628174,0,540414,TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574366,628175,0,540415,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574356,628176,0,540417,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573959,628177,0,540419,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574355,628178,0,540420,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574439,628179,0,540421,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574497,628180,0,540422,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574493,628181,0,540423,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574494,628182,0,540424,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574495,628183,0,540425,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574496,628184,0,540426,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574230,628185,0,540427,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:14.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574498,628186,0,540429,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574393,628187,0,540430,TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574397,628188,0,540431,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574500,628189,0,540432,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574501,628190,0,540433,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574503,628191,0,540434,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574502,628192,0,540435,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574509,628193,0,540438,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574376,628194,0,540439,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574377,628195,0,540440,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574416,628196,0,540441,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574378,628197,0,540442,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574379,628198,0,540443,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574504,628199,0,540444,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:15.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574505,628200,0,540445,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574506,628201,0,540446,TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573912,628202,0,540447,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573923,628203,0,540448,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573924,628204,0,540449,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573930,628205,0,540451,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573931,628206,0,540452,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573936,628207,0,540453,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573954,628208,0,540454,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574009,628209,0,540455,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574039,628210,0,540456,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573957,628211,0,540457,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573984,628212,0,540458,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574010,628213,0,540459,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:16.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574040,628214,0,540460,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574068,628215,0,540461,TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574088,628216,0,540474,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574109,628217,0,540475,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574155,628218,0,540476,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574189,628219,0,540484,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,574217,628220,0,540485,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,573958,628221,0,540490,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575856,628222,0,540524,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,572858,628223,0,540552,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576046,628224,0,540571,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576048,628225,0,540572,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576076,628226,0,540582,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576148,628227,0,540590,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:17.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576157,628228,0,540591,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:18.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576162,628229,0,540593,TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:39:18.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576241,628230,0,540608,TO_TIMESTAMP('2019-03-20 14:39:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-20 14:39:18','YYYY-MM-DD HH24:MI:SS'),100)
;
*/

-- 2019-03-20T14:41:50.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541228
;

-- 2019-03-20T14:41:56.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541674,541233,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-20T14:41:56.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541233 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-20T14:41:56.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541573,541233,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:41:56.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541574,541233,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:41:56.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541573,542382,TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-20 14:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:43:02.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577984
;

-- 2019-03-20T14:43:02.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577985
;

-- 2019-03-20T14:43:03.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577986
;

-- 2019-03-20T14:43:04.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577987
;

-- 2019-03-20T14:43:04.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577988
;

-- 2019-03-20T14:43:05.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577989
;

-- 2019-03-20T14:43:05.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577990
;

-- 2019-03-20T14:43:06.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577991
;

-- 2019-03-20T14:43:07.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577992
;

-- 2019-03-20T14:43:08.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577993
;

-- 2019-03-20T14:43:19.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577984
;

-- 2019-03-20T14:43:19.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577985
;

-- 2019-03-20T14:43:20.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577986
;

-- 2019-03-20T14:43:20.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577987
;

-- 2019-03-20T14:43:21.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577988
;

-- 2019-03-20T14:43:21.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577989
;

-- 2019-03-20T14:43:22.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577990
;

-- 2019-03-20T14:43:22.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577991
;

-- 2019-03-20T14:43:23.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577992
;

-- 2019-03-20T14:43:24.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 14:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577993
;

-- 2019-03-20T14:43:32.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542382
;

-- 2019-03-20T14:43:36.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541574
;

-- 2019-03-20T14:43:36.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541573
;

-- 2019-03-20T14:43:40.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541233
;

-- 2019-03-20T14:43:40.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541233
;

-- 2019-03-20T14:43:45.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541674,541234,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-20T14:43:45.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541234 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-20T14:43:45.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541575,541234,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:43:45.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541576,541234,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:43:45.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541575,542383,TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-20 14:43:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:44:24.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541575,542384,TO_TIMESTAMP('2019-03-20 14:44:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','logic',20,TO_TIMESTAMP('2019-03-20 14:44:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:44:32.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541575,542385,TO_TIMESTAMP('2019-03-20 14:44:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2019-03-20 14:44:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:44:37.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541575,542386,TO_TIMESTAMP('2019-03-20 14:44:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','info',40,TO_TIMESTAMP('2019-03-20 14:44:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T14:44:42.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='auth',Updated=TO_TIMESTAMP('2019-03-20 14:44:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542383
;

-- 2019-03-20T14:45:17.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542386, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 14:45:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557990
;

-- 2019-03-20T14:45:35.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542386, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 14:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557994
;

-- 2019-03-20T14:45:48.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542384, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 14:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557995
;

-- 2019-03-20T14:46:01.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542384, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 14:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557996
;

-- 2019-03-20T14:46:24.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542385, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 14:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557989
;

-- 2019-03-20T14:46:34.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542385, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 14:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557988
;

-- 2019-03-20T14:46:59.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-03-20 14:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557993
;

-- 2019-03-20T14:47:17.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-03-20 14:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557997
;

-- 2019-03-20T14:47:21.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542384, SeqNo=30,Updated=TO_TIMESTAMP('2019-03-20 14:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557997
;

-- 2019-03-20T14:47:37.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-03-20 14:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557991
;

-- 2019-03-20T14:47:55.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-03-20 14:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542385
;

-- 2019-03-20T14:48:00.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-03-20 14:48:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542386
;

-- 2019-03-20T14:48:48.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541576, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 14:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542385
;

-- 2019-03-20T14:48:55.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541576, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 14:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542386
;

-- 2019-03-20T14:49:06.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-03-20 14:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577990
;

-- 2019-03-20T14:56:25.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=541678
;

-- 2019-03-20T14:56:25.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab WHERE AD_Tab_ID=541678
;

-- 2019-03-20T14:56:25.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542381
;

-- 2019-03-20T14:56:25.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541572
;

-- 2019-03-20T14:56:25.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541232
;

-- 2019-03-20T14:56:25.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541232
;

-- 2019-03-20T15:00:05.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-03-20 15:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564523
;

-- 2019-03-20T15:15:52.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576285,0,TO_TIMESTAMP('2019-03-20 15:15:52','YYYY-MM-DD HH24:MI:SS'),100,'Ergebnise fur CreditPass API Aufrufe','de.metas.vertical.creditscore.base','Y','CreditPass Transaktionsergebnise','CreditPass Transaktionsergebnise',TO_TIMESTAMP('2019-03-20 15:15:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:15:52.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576285 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T15:16:18.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='CreditPass transaction results', PrintName='CreditPass transaction results',Updated=TO_TIMESTAMP('2019-03-20 15:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576285 AND AD_Language='en_US'
;

-- 2019-03-20T15:16:18.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576285,'en_US') 
;

-- 2019-03-20T15:16:41.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Results for CreditPass API requests',Updated=TO_TIMESTAMP('2019-03-20 15:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576285 AND AD_Language='en_US'
;

-- 2019-03-20T15:16:41.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576285,'en_US') 
;

-- 2019-03-20T15:17:21.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576285,0,540611,TO_TIMESTAMP('2019-03-20 15:17:21','YYYY-MM-DD HH24:MI:SS'),100,'Ergebnise fur CreditPass API Aufrufe','de.metas.vertical.creditscore.base','Y','N','N','N','N','Y','CreditPass Transaktionsergebnise','N',TO_TIMESTAMP('2019-03-20 15:17:21','YYYY-MM-DD HH24:MI:SS'),100,'Q',0,0)
;

-- 2019-03-20T15:17:21.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540611 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-03-20T15:17:21.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(576285) 
;

-- 2019-03-20T15:20:57.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576285,0,541680,541194,540611,'Y',TO_TIMESTAMP('2019-03-20 15:20:57','YYYY-MM-DD HH24:MI:SS'),100,'Ergebnise fur CreditPass API Aufrufe','de.metas.vertical.creditscore.base','N','N','CS_Transaction_Results','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'CreditPass Transaktionsergebnise','N',10,0,TO_TIMESTAMP('2019-03-20 15:20:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:20:57.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541680 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-03-20T15:20:57.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576285) 
;

-- 2019-03-20T15:21:01.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-03-20 15:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541680
;

-- 2019-03-20T15:21:10.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-03-20 15:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541680
;

-- 2019-03-20T15:21:26.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564508,577994,0,541680,TO_TIMESTAMP('2019-03-20 15:21:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.vertical.creditscore.base','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-03-20 15:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:26.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:26.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564509,577995,0,541680,TO_TIMESTAMP('2019-03-20 15:21:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.creditscore.base','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-20 15:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:26.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:26.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:26.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564513,577997,0,541680,TO_TIMESTAMP('2019-03-20 15:21:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','CS_Transaction_Results',TO_TIMESTAMP('2019-03-20 15:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:26.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564515,577999,0,541680,TO_TIMESTAMP('2019-03-20 15:21:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.vertical.creditscore.base','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-03-20 15:21:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:27.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=577999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564523,578005,0,541680,TO_TIMESTAMP('2019-03-20 15:21:27','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.vertical.creditscore.base','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2019-03-20 15:21:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:27.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:27.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564524,578006,0,541680,TO_TIMESTAMP('2019-03-20 15:21:27','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'de.metas.vertical.creditscore.base','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2019-03-20 15:21:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:27.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:21:56.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541680,541235,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-20T15:21:56.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541235 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-20T15:21:56.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541577,541235,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:56.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541578,541235,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:21:56.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541577,542387,TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-20 15:21:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:22:23.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577998
;

-- 2019-03-20T15:22:23.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577999
;

-- 2019-03-20T15:22:24.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578000
;

-- 2019-03-20T15:22:24.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578001
;

-- 2019-03-20T15:22:25.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578002
;

-- 2019-03-20T15:22:25.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578003
;

-- 2019-03-20T15:22:26.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578004
;

-- 2019-03-20T15:22:26.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578005
;

-- 2019-03-20T15:22:27.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578006
;

-- 2019-03-20T15:22:27.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577994
;

-- 2019-03-20T15:22:28.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577995
;

-- 2019-03-20T15:22:28.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577996
;

-- 2019-03-20T15:24:54.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577997
;

-- 2019-03-20T15:25:07.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=577998
;

-- 2019-03-20T15:29:11.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=577998
;

-- 2019-03-20T15:29:11.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=577998
;

-- 2019-03-20T15:29:11.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578000
;

-- 2019-03-20T15:29:11.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578000
;

-- 2019-03-20T15:29:11.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578001
;

-- 2019-03-20T15:29:11.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578001
;

-- 2019-03-20T15:29:12.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578002
;

-- 2019-03-20T15:29:12.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578002
;

-- 2019-03-20T15:29:12.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578003
;

-- 2019-03-20T15:29:12.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578003
;

-- 2019-03-20T15:29:12.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578004
;

-- 2019-03-20T15:29:12.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578004
;

-- 2019-03-20T15:29:12.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578005
;

-- 2019-03-20T15:29:12.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578005
;

-- 2019-03-20T15:29:12.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578006
;

-- 2019-03-20T15:29:12.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578006
;

-- 2019-03-20T15:29:12.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=577994
;

-- 2019-03-20T15:29:12.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=577994
;

-- 2019-03-20T15:29:12.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=577995
;

-- 2019-03-20T15:29:12.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=577995
;

-- 2019-03-20T15:29:12.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=577996
;

-- 2019-03-20T15:29:12.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=577996
;

-- 2019-03-20T15:29:12.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=577997
;

-- 2019-03-20T15:29:12.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=577997
;

-- 2019-03-20T15:29:12.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=577999
;

-- 2019-03-20T15:29:12.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=577999
;

-- 2019-03-20T15:44:29.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564509,578008,0,541680,TO_TIMESTAMP('2019-03-20 15:44:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.creditscore.base','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-20 15:44:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:29.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:29.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564513,578009,0,541680,TO_TIMESTAMP('2019-03-20 15:44:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','CS_Transaction_Results',TO_TIMESTAMP('2019-03-20 15:44:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:29.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:29.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564523,578011,0,541680,TO_TIMESTAMP('2019-03-20 15:44:29','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.vertical.creditscore.base','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2019-03-20 15:44:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564524,578012,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'de.metas.vertical.creditscore.base','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564604,578013,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Anfrage Ende',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564605,578014,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Anfrage Start ',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564606,578015,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Antwort Code',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564607,578016,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,128,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Antwort Text',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564608,578017,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,512,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Antwort Details',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564609,578018,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,32,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Transaktionsreferenz ',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:30.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564610,578019,0,541680,TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100,32,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','Transaktionsreferenz ',TO_TIMESTAMP('2019-03-20 15:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:44:30.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T15:44:45.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578011
;

-- 2019-03-20T15:44:45.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578012
;

-- 2019-03-20T15:44:46.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578013
;

-- 2019-03-20T15:44:46.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578014
;

-- 2019-03-20T15:44:47.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578015
;

-- 2019-03-20T15:44:49.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578016
;

-- 2019-03-20T15:44:50.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578017
;

-- 2019-03-20T15:44:51.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578018
;

-- 2019-03-20T15:44:53.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578019
;

-- 2019-03-20T15:44:53.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578007
;

-- 2019-03-20T15:45:01.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578008
;

-- 2019-03-20T15:45:01.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578019
;

-- 2019-03-20T15:45:02.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578018
;

-- 2019-03-20T15:45:02.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578017
;

-- 2019-03-20T15:45:03.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578016
;

-- 2019-03-20T15:45:04.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578015
;

-- 2019-03-20T15:45:04.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578014
;

-- 2019-03-20T15:45:05.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578013
;

-- 2019-03-20T15:45:05.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578012
;

-- 2019-03-20T15:45:08.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-03-20 15:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578011
;

-- 2019-03-20T15:45:37.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541578
;

-- 2019-03-20T15:45:37.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542387
;

-- 2019-03-20T15:45:37.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541577
;

-- 2019-03-20T15:45:37.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541235
;

-- 2019-03-20T15:45:37.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541235
;

-- 2019-03-20T15:45:42.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541680,541236,TO_TIMESTAMP('2019-03-20 15:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 15:45:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-20T15:45:42.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541236 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-20T15:45:42.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541579,541236,TO_TIMESTAMP('2019-03-20 15:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-20 15:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541580,541236,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541579,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578008,0,541680,557999,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578011,0,541680,558000,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','Y','Geschäftspartner',30,0,10,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578012,0,541680,558001,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','Y','Auftrag',40,0,20,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578013,0,541680,558002,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Anfrage Ende',50,0,30,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578014,0,541680,558003,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Anfrage Start ',60,0,40,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578015,0,541680,558004,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Antwort Code',70,0,50,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578016,0,541680,558005,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Antwort Text',80,0,60,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578017,0,541680,558006,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Antwort Details',90,0,70,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578018,0,541680,558007,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Transaktionsreferenz ',100,0,80,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:45:43.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578019,0,541680,558008,542388,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Transaktionsreferenz ',110,0,90,TO_TIMESTAMP('2019-03-20 15:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:46:09.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-03-20 15:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558003
;

-- 2019-03-20T15:46:09.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-03-20 15:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558002
;

-- 2019-03-20T15:46:09.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-03-20 15:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558000
;

-- 2019-03-20T15:46:09.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-03-20 15:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558001
;

-- 2019-03-20T15:46:58.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541579,542389,TO_TIMESTAMP('2019-03-20 15:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-03-20 15:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:47:09.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541579,542390,TO_TIMESTAMP('2019-03-20 15:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','info',30,TO_TIMESTAMP('2019-03-20 15:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:47:17.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-03-20 15:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542389
;

-- 2019-03-20T15:47:22.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 15:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542390
;

-- 2019-03-20T15:47:40.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542389, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 15:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557999
;

-- 2019-03-20T15:47:45.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542389, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 15:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557998
;

-- 2019-03-20T15:48:46.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542390, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 15:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558008
;

-- 2019-03-20T15:48:51.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542390, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 15:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558007
;

-- 2019-03-20T15:49:03.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542390, SeqNo=30,Updated=TO_TIMESTAMP('2019-03-20 15:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558003
;

-- 2019-03-20T15:49:12.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542390, SeqNo=40,Updated=TO_TIMESTAMP('2019-03-20 15:49:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558002
;

-- 2019-03-20T15:49:36.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541580, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-20 15:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542389
;

-- 2019-03-20T15:49:40.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541580, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-20 15:49:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542390
;

-- 2019-03-20T15:56:25.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,576285,541226,0,540611,TO_TIMESTAMP('2019-03-20 15:56:25','YYYY-MM-DD HH24:MI:SS'),100,'Ergebnise fur CreditPass API Aufrufe','de.metas.vertical.creditscore.base','CreditPassTransactions','Y','N','N','N','N','CreditPass Transaktionsergebnise',TO_TIMESTAMP('2019-03-20 15:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T15:56:25.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541226 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-20T15:56:25.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541226, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541226)
;

-- 2019-03-20T15:56:25.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576285) 
;

-- 2019-03-20T15:56:25.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:25.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- 2019-03-20T15:56:37.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='CreditPass Transaktionsergebnise',Updated=TO_TIMESTAMP('2019-03-20 15:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541226
;

-- 2019-03-20T16:14:50.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564508,578020,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.vertical.creditscore.base','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:50.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564509,578021,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.creditscore.base','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:50.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564513,578022,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.base','Y','N','N','N','N','N','N','N','CS_Transaction_Results',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:50.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564515,578023,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.vertical.creditscore.base','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:50.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564523,578024,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.vertical.creditscore.base','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:50.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564524,578025,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'de.metas.vertical.creditscore.base','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:50.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564604,578026,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Anfrage Ende',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:50.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:51.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564605,578027,0,541677,TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Anfrage Start ',TO_TIMESTAMP('2019-03-20 16:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:51.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:51.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564606,578028,0,541677,TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Antwort Code',TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:51.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:51.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564607,578029,0,541677,TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,128,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Antwort Text',TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:51.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:51.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564608,578030,0,541677,TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,512,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Antwort Details',TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:51.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:51.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564609,578031,0,541677,TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,32,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Transaktionsreferenz ',TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:51.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T16:14:51.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564610,578032,0,541677,TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,32,'de.metas.vertical.creditscore.base','Y','Y','N','N','N','N','N','Transaktionsreferenz ',TO_TIMESTAMP('2019-03-20 16:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T16:14:51.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-20T18:25:08.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578023
;

-- 2019-03-20T18:25:08.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578023
;

-- 2019-03-20T18:25:08.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578025
;

-- 2019-03-20T18:25:08.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578025
;

-- 2019-03-20T18:25:09.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578026
;

-- 2019-03-20T18:25:09.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578026
;

-- 2019-03-20T18:25:09.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578027
;

-- 2019-03-20T18:25:09.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578027
;

-- 2019-03-20T18:25:09.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578028
;

-- 2019-03-20T18:25:09.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578028
;

-- 2019-03-20T18:25:09.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578029
;

-- 2019-03-20T18:25:09.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578029
;

-- 2019-03-20T18:25:09.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578030
;

-- 2019-03-20T18:25:09.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578030
;

-- 2019-03-20T18:25:09.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578031
;

-- 2019-03-20T18:25:09.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578031
;

-- 2019-03-20T18:25:09.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578032
;

-- 2019-03-20T18:25:09.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578032
;

-- 2019-03-20T18:25:09.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578020
;

-- 2019-03-20T18:25:09.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578020
;

-- 2019-03-20T18:25:09.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578021
;

-- 2019-03-20T18:25:09.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578021
;

-- 2019-03-20T18:25:09.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578024
;

-- 2019-03-20T18:25:09.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578024
;

-- 2019-03-20T18:25:09.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=578022
;

-- 2019-03-20T18:25:09.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=578022
;

-- 2019-03-20T18:26:00.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-03-20 18:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541677
;

-- 2019-03-20T18:26:54.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=541677
;

-- 2019-03-21T11:25:27.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576288,0,541681,541198,540608,'Y',TO_TIMESTAMP('2019-03-21 11:25:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.creditpass','N','N','CS_Creditpass_BP_Groups','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'CS Creditpass business partner groups','N',30,0,TO_TIMESTAMP('2019-03-21 11:25:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:25:27.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541681 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-03-21T11:25:27.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576288) 
;

-- 2019-03-21T11:29:26.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564613,578035,0,541681,TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.vertical.creditscore.creditpass','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:29:26.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-21T11:29:26.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564614,578036,0,541681,TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.vertical.creditscore.creditpass','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:29:26.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-21T11:29:26.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564617,578037,0,541681,TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.creditpass','Y','Y','N','N','N','N','N','CS Creditpass Configuration',TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:29:26.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-21T11:29:26.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564618,578038,0,541681,TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','N','CS Creditpass business partner groups',TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:29:26.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-21T11:29:26.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564619,578039,0,541681,TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.vertical.creditscore.creditpass','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:29:26.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-21T11:29:26.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564625,578040,0,541681,TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe',10,'de.metas.vertical.creditscore.creditpass','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','Y','N','N','N','N','N','Geschäftspartnergruppe',TO_TIMESTAMP('2019-03-21 11:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:29:26.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-21T11:31:28.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-03-21 11:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542385
;

-- 2019-03-21T11:31:35.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2019-03-21 11:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542386
;

-- 2019-03-21T11:35:04.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,0,541674,558013,542384,'F',TO_TIMESTAMP('2019-03-21 11:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppen, die nicht von creditPass überprüft werden sollen','Y','N','N','Y','N','N','N',0,'Geschäftspartnergruppen',40,0,0,TO_TIMESTAMP('2019-03-21 11:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T11:35:40.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementType='L', Labels_Selector_Field_ID=578040, Labels_Tab_ID=541681,Updated=TO_TIMESTAMP('2019-03-21 11:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558013
;

-- 2019-03-21T11:55:32.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2019-03-21 11:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:10:46.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=564617, Parent_Column_ID=564352,Updated=TO_TIMESTAMP('2019-03-21 12:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:11:51.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=NULL, Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2019-03-21 12:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:12:18.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-03-21 12:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:12:22.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2019-03-21 12:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:21:34.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAllowFiltering='Y',Updated=TO_TIMESTAMP('2019-03-21 12:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558013
;

-- 2019-03-21T12:27:53.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CS Creditpass business partner group', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576288) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576288)
;

-- 2019-03-21T12:27:53.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='CS Creditpass business partner groups', Name='CS Creditpass business partner group' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576288)
;

-- 2019-03-21T12:27:53.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='CS Creditpass business partner group', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576288
;

-- 2019-03-21T12:27:53.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='CS Creditpass business partner group', Description=NULL, Help=NULL WHERE AD_Element_ID = 576288
;

-- 2019-03-21T12:27:53.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'CS Creditpass business partner group', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576288
;

-- 2019-03-21T12:32:21.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2019-03-21 12:32:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2019-03-21T12:33:55.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 12:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2019-03-21T12:36:15.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-03-21 12:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2019-03-21T12:36:19.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2019-03-21 12:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2019-03-21T12:36:23.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N', IsDisplayed='N',Updated=TO_TIMESTAMP('2019-03-21 12:36:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550398
;

-- 2019-03-21T12:44:56.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541681,541237,TO_TIMESTAMP('2019-03-21 12:44:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-21 12:44:55','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-03-21T12:44:56.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541237 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-03-21T12:44:56.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541581,541237,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:44:56.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541581,542391,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:44:56.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578035,0,541681,558014,542391,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','Y','Mandant',0,0,10,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:44:56.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578036,0,541681,558015,542391,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','Y','Sektion',0,0,20,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:44:56.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578037,0,541681,558016,542391,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','CS Creditpass Configuration',0,0,30,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:44:56.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578039,0,541681,558017,542391,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','Y','Aktiv',0,0,40,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:44:56.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578040,0,541681,558018,542391,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','N','N','Y','Geschäftspartnergruppe',0,0,50,TO_TIMESTAMP('2019-03-21 12:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T12:45:54.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-21 12:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558018
;

-- 2019-03-21T12:46:25.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558016
;

-- 2019-03-21T12:46:27.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558017
;

-- 2019-03-21T12:46:27.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-03-21 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558016
;

-- 2019-03-21T12:46:28.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-03-21 12:46:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558017
;

-- 2019-03-21T12:46:29.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N', IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558018
;

-- 2019-03-21T12:46:30.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558017
;

-- 2019-03-21T12:46:30.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558016
;

-- 2019-03-21T12:46:31.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558015
;

-- 2019-03-21T12:46:44.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 12:46:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558014
;

-- 2019-03-21T12:49:31.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsGridModeOnly='Y', IsSearchActive='N',Updated=TO_TIMESTAMP('2019-03-21 12:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:59:07.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576288,628234,0,541681,540608,TO_TIMESTAMP('2019-03-21 12:59:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-21 12:59:07','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-21T12:59:14.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=576289, Name='Creditpass Geschäftspartnergruppen',Updated=TO_TIMESTAMP('2019-03-21 12:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541681
;

-- 2019-03-21T12:59:14.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=628234
;

-- 2019-03-21T12:59:14.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576289,628235,0,541681,540608,TO_TIMESTAMP('2019-03-21 12:59:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-21 12:59:14','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-21T12:59:14.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576289) 
;

-- 2019-03-21T13:02:18.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-03-21 13:02:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558013
;

-- 2019-03-21T14:14:19.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-03-21 14:14:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558013
;

-- 2019-03-21T14:15:35.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558018
;

-- 2019-03-21T14:15:35.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558017
;

-- 2019-03-21T14:15:35.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558016
;

-- 2019-03-21T14:15:35.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558015
;

-- 2019-03-21T14:15:35.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558014
;

-- 2019-03-21T14:15:35.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542391
;

-- 2019-03-21T14:15:35.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541581
;

-- 2019-03-21T14:15:35.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541237
;

-- 2019-03-21T14:15:35.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541237
;

-- 2019-03-22T18:40:18.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-03-22 18:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544785
;

-- 2019-03-22T18:40:40.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=1000026, SeqNo=50,Updated=TO_TIMESTAMP('2019-03-22 18:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544785
;

-- 2019-03-22T18:42:04.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540010,542394,TO_TIMESTAMP('2019-03-22 18:42:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','creditPass',30,TO_TIMESTAMP('2019-03-22 18:42:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T14:47:18.915
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542368, SeqNo=40,Updated=TO_TIMESTAMP('2019-03-26 14:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558013
;

-- 2019-03-26T14:47:33.220
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542383
;

-- 2019-03-26T14:47:33.242
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542384
;

-- 2019-03-26T14:47:33.256
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541575
;

-- 2019-03-26T14:47:33.281
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542385
;

-- 2019-03-26T14:47:33.300
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542386
;

-- 2019-03-26T14:47:33.323
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541576
;

-- 2019-03-26T14:47:33.327
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541234
;

-- 2019-03-26T14:47:33.337
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541234
;

-- 2019-03-20T19:46:06.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540611,Updated=TO_TIMESTAMP('2019-03-20 19:46:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541194
;

-- 2019-03-27T03:42:40.579
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-03-27 03:42:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564513
;

-- 2019-03-27T03:53:37.093
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557964
;

-- 2019-03-27T03:53:37.104
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557962
;

-- 2019-03-27T03:53:37.114
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557963
;

-- 2019-03-27T03:53:37.122
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557965
;

-- 2019-03-27T03:53:37.131
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557966
;

-- 2019-03-27T03:53:37.141
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557967
;

-- 2019-03-27T03:53:37.151
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557968
;

-- 2019-03-27T03:53:37.162
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557960
;

-- 2019-03-27T03:53:37.172
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557958
;

-- 2019-03-27T03:53:37.182
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-03-27 03:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557959
;

-- 2019-03-27T03:54:01.428
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-03-27 03:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557964
;

-- 2019-03-27T03:54:20.682
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2019-03-27 03:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557963
;

-- 2019-03-27T03:55:25.538
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-03-27 03:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542369
;

-- 2019-03-27T03:55:30.124
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2019-03-27 03:55:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542370
;

-- 2019-03-27T03:57:45.542
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-03-27 03:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557960
;

-- 2019-03-27T03:58:06.081
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-03-27 03:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557958
;

-- 2019-03-27T03:58:08.774
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-03-27 03:58:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557959
;

-- 2019-03-27T04:00:04.784
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-03-27 04:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557964
;

-- 2019-03-27T04:01:57.357
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558004
;

-- 2019-03-27T04:01:57.367
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558005
;

-- 2019-03-27T04:01:57.376
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558006
;

-- 2019-03-27T04:01:57.385
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558007
;

-- 2019-03-27T04:01:57.393
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558008
;

-- 2019-03-27T04:01:57.401
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558000
;

-- 2019-03-27T04:01:57.411
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-03-27 04:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558001
;

-- 2019-03-27T04:02:32.913
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-03-27 04:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542389
;

-- 2019-03-27T04:02:47.003
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2019-03-27 04:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542390
;

-- 2019-03-27T04:04:42.405
-- URL zum Konzept
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2019-03-27 04:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578014
;

-- 2019-03-27T04:06:03.950
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-03-27 04:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558002
;

-- 2019-03-27T04:06:06.656
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-03-27 04:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558003
;

-- 2019-03-27T04:07:29.349
-- URL zum Konzept
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2019-03-27 04:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578014
;

-- 2019-03-27T04:10:51.940
-- URL zum Konzept
-- INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,102,628242,578008,0,540611,TO_TIMESTAMP('2019-03-27 04:10:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-27 04:10:51','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2019-03-27T04:11:42.332
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,564508,578098,0,541680,0,TO_TIMESTAMP('2019-03-27 04:11:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','N','N','Mandant',10,10,0,1,1,TO_TIMESTAMP('2019-03-27 04:11:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T04:11:42.338
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-27T04:12:17.599
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,578098,0,541680,542389,558085,'F',TO_TIMESTAMP('2019-03-27 04:12:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2019-03-27 04:12:17','YYYY-MM-DD HH24:MI:SS'),100)
;

