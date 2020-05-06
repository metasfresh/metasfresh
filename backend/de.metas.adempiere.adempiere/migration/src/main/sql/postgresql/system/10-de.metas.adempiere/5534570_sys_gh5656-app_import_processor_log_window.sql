-- 2019-10-17T19:48:38.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,53384,0,540744,TO_TIMESTAMP('2019-10-17 21:48:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.replication','Y','N','N','N','N','Y','Import Processor Log','N',TO_TIMESTAMP('2019-10-17 21:48:38','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-10-17T19:48:38.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540744 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-10-17T19:48:38.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(53384) 
;

-- 2019-10-17T19:48:38.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540744
;

-- 2019-10-17T19:48:38.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540744)
;

-- 2019-10-17T19:49:15.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,53384,0,542068,53079,540744,'Y',TO_TIMESTAMP('2019-10-17 21:49:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.replication','N','N','IMP_ProcessorLog','Y','N','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'Import Processor Log','N',10,0,TO_TIMESTAMP('2019-10-17 21:49:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:15.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542068 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-10-17T19:49:15.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(53384) 
;

-- 2019-10-17T19:49:15.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542068)
;

-- 2019-10-17T19:49:20.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='IMP_ProcessorLog',Updated=TO_TIMESTAMP('2019-10-17 21:49:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540744
;

-- 2019-10-17T19:49:23.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2019-10-17 21:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540744
;

-- 2019-10-17T19:49:49.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2019-10-17 21:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542068
;

-- 2019-10-17T19:49:55.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54605,589639,0,542068,TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100,22,'de.metas.replication','Y','N','N','N','N','N','N','N','Import Processor Log',TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:55.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:55.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53384) 
;

-- 2019-10-17T19:49:55.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589639
;

-- 2019-10-17T19:49:55.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589639)
;

-- 2019-10-17T19:49:55.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54606,589640,0,542068,TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.replication','Y','Y','N','N','N','N','N','Import Processor',TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:55.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589640 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:55.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53381) 
;

-- 2019-10-17T19:49:55.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589640
;

-- 2019-10-17T19:49:55.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589640)
;

-- 2019-10-17T19:49:55.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54607,589641,0,542068,TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'de.metas.replication','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:55.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589641 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:55.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-10-17T19:49:55.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589641
;

-- 2019-10-17T19:49:55.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589641)
;

-- 2019-10-17T19:49:56.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54608,589642,0,542068,TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'de.metas.replication','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-10-17 21:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:56.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:56.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-10-17T19:49:56.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589642
;

-- 2019-10-17T19:49:56.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589642)
;

-- 2019-10-17T19:49:56.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54609,589643,0,542068,TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.replication','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:56.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:56.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-10-17T19:49:56.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589643
;

-- 2019-10-17T19:49:56.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589643)
;

-- 2019-10-17T19:49:56.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54614,589644,0,542068,TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.replication','Y','Y','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:56.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:56.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-10-17T19:49:56.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589644
;

-- 2019-10-17T19:49:56.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589644)
;

-- 2019-10-17T19:49:56.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54615,589645,0,542068,TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint',2000,'de.metas.replication','The Help field contains a hint, comment or help about the use of this item.','Y','Y','N','N','N','N','N','Kommentar/Hilfe',TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:56.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:56.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(326) 
;

-- 2019-10-17T19:49:56.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589645
;

-- 2019-10-17T19:49:56.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589645)
;

-- 2019-10-17T19:49:56.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54616,589646,0,542068,TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Binärer Wert',0,'de.metas.replication','Das Feld "Binärwert" speichert binäre Werte.','Y','Y','N','N','N','N','N','Binärwert',TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:56.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:56.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(174) 
;

-- 2019-10-17T19:49:56.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589646
;

-- 2019-10-17T19:49:56.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589646)
;

-- 2019-10-17T19:49:56.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54617,589647,0,542068,TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten',1,'de.metas.replication','Y','Y','N','N','N','N','N','Fehler',TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:56.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:56.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395) 
;

-- 2019-10-17T19:49:56.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589647
;

-- 2019-10-17T19:49:56.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589647)
;

-- 2019-10-17T19:49:57.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54618,589648,0,542068,TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request',2000,'de.metas.replication','The Summary allows free form text entry of a recap of this request.','Y','Y','N','N','N','N','N','Summary',TO_TIMESTAMP('2019-10-17 21:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:57.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:57.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1521) 
;

-- 2019-10-17T19:49:57.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589648
;

-- 2019-10-17T19:49:57.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589648)
;

-- 2019-10-17T19:49:57.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54619,589649,0,542068,TO_TIMESTAMP('2019-10-17 21:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Mitteilung',2000,'de.metas.replication','Y','Y','N','N','N','N','N','Mitteilung',TO_TIMESTAMP('2019-10-17 21:49:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:57.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:57.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2438) 
;

-- 2019-10-17T19:49:57.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589649
;

-- 2019-10-17T19:49:57.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589649)
;

-- 2019-10-17T19:49:57.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54620,589650,0,542068,TO_TIMESTAMP('2019-10-17 21:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Bezug für diesen Eintrag',60,'de.metas.replication','"Referenz" zeigt die Nummer des Ausgangsbelegs an.','Y','Y','N','N','N','N','N','Referenz',TO_TIMESTAMP('2019-10-17 21:49:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:49:57.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:49:57.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(539) 
;

-- 2019-10-17T19:49:57.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589650
;

-- 2019-10-17T19:49:57.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589650)
;

-- 2019-10-17T19:50:08.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542068,541600,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-10-17T19:50:08.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541600 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-10-17T19:50:08.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542039,541600,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:08.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542040,541600,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:08.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542039,543086,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:08.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589640,0,542068,543086,563299,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Import Processor',10,10,0,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:08.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589641,0,542068,543086,563300,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',20,20,0,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:08.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589642,0,542068,543086,563301,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',30,30,0,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:08.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589643,0,542068,543086,563302,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',40,40,0,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589644,0,542068,543086,563303,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2019-10-17 21:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589645,0,542068,543086,563304,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','Y','N','Kommentar/Hilfe',60,60,0,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589646,0,542068,543086,563305,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Binärer Wert','Das Feld "Binärwert" speichert binäre Werte.','Y','N','Y','Y','N','Binärwert',70,70,0,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589647,0,542068,543086,563306,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','Y','Y','N','Fehler',80,80,0,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589648,0,542068,543086,563307,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request','The Summary allows free form text entry of a recap of this request.','Y','N','Y','Y','N','Summary',90,90,0,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589649,0,542068,543086,563308,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Mitteilung','Y','N','Y','Y','N','Mitteilung',100,100,0,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:09.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589650,0,542068,543086,563309,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Bezug für diesen Eintrag','"Referenz" zeigt die Nummer des Ausgangsbelegs an.','Y','N','Y','Y','N','Referenz',110,110,0,TO_TIMESTAMP('2019-10-17 21:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:50:41.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542040,543087,TO_TIMESTAMP('2019-10-17 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2019-10-17 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:51:02.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543087, SeqNo=10,Updated=TO_TIMESTAMP('2019-10-17 21:51:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563301
;

-- 2019-10-17T19:51:11.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543087, SeqNo=20,Updated=TO_TIMESTAMP('2019-10-17 21:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563300
;

-- 2019-10-17T19:51:41.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-10-17 21:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563302
;

-- 2019-10-17T19:51:55.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-10-17 21:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563306
;

-- 2019-10-17T19:52:00.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2019-10-17 21:52:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563307
;

-- 2019-10-17T19:52:42.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563302
;

-- 2019-10-17T19:52:42.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563303
;

-- 2019-10-17T19:52:42.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563305
;

-- 2019-10-17T19:52:42.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563304
;

-- 2019-10-17T19:52:42.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563300
;

-- 2019-10-17T19:52:42.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563309
;

-- 2019-10-17T19:52:42.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563301
;

-- 2019-10-17T19:52:42.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563306
;

-- 2019-10-17T19:52:42.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563307
;

-- 2019-10-17T19:52:42.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-10-17 21:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563308
;

-- 2019-10-17T19:53:22.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2019-10-17 21:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54608
;

-- 2019-10-17T19:53:22.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2019-10-17 21:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54618
;

-- 2019-10-17T19:53:22.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2019-10-17 21:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54606
;

-- 2019-10-17T19:53:22.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-10-17 21:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54610
;

-- 2019-10-17T19:53:38.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsRangeFilter='Y',Updated=TO_TIMESTAMP('2019-10-17 21:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54610
;

-- 2019-10-17T19:54:01.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2019-10-17 21:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54606
;

-- 2019-10-17T19:54:14.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2019-10-17 21:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54610
;

-- 2019-10-17T19:54:26.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2019-10-17 21:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54617
;

-- 2019-10-17T19:54:39.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-10-17 21:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53079
;

-- 2019-10-17T19:55:12.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540744, EntityType='de.metas.replication',Updated=TO_TIMESTAMP('2019-10-17 21:55:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53079
;

-- 2019-10-17T19:55:17.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y', IsHighVolume='Y',Updated=TO_TIMESTAMP('2019-10-17 21:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53079
;

-- 2019-10-17T19:55:28.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='IMP_ProcessorLog',Updated=TO_TIMESTAMP('2019-10-17 21:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53079
;

-- 2019-10-17T19:56:09.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Import Prozessor Log', PrintName='Import Prozessor Log',Updated=TO_TIMESTAMP('2019-10-17 21:56:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53384 AND AD_Language='de_CH'
;

-- 2019-10-17T19:56:09.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53384,'de_CH') 
;

-- 2019-10-17T19:56:22.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Import Prozessor Log', PrintName='Import Prozessor Log',Updated=TO_TIMESTAMP('2019-10-17 21:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53384 AND AD_Language='de_DE'
;

-- 2019-10-17T19:56:22.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53384,'de_DE') 
;

-- 2019-10-17T19:56:22.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(53384,'de_DE') 
;

-- 2019-10-17T19:56:22.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IMP_ProcessorLog_ID', Name='Import Prozessor Log', Description=NULL, Help=NULL WHERE AD_Element_ID=53384
;

-- 2019-10-17T19:56:22.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IMP_ProcessorLog_ID', Name='Import Prozessor Log', Description=NULL, Help=NULL, AD_Element_ID=53384 WHERE UPPER(ColumnName)='IMP_PROCESSORLOG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T19:56:22.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IMP_ProcessorLog_ID', Name='Import Prozessor Log', Description=NULL, Help=NULL WHERE AD_Element_ID=53384 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T19:56:22.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Import Prozessor Log', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53384) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53384)
;

-- 2019-10-17T19:56:23.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Import Prozessor Log', Name='Import Prozessor Log' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53384)
;

-- 2019-10-17T19:56:23.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Import Prozessor Log', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53384
;

-- 2019-10-17T19:56:23.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Import Prozessor Log', Description=NULL, Help=NULL WHERE AD_Element_ID = 53384
;

-- 2019-10-17T19:56:23.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Import Prozessor Log', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53384
;

-- 2019-10-17T19:57:09.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54610,589651,0,542068,TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'de.metas.replication','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:57:09.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589651 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:57:09.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-10-17T19:57:09.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589651
;

-- 2019-10-17T19:57:09.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589651)
;

-- 2019-10-17T19:57:09.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54611,589652,0,542068,TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.replication','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:57:09.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589652 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:57:09.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-10-17T19:57:09.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589652
;

-- 2019-10-17T19:57:09.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589652)
;

-- 2019-10-17T19:57:09.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54612,589653,0,542068,TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'de.metas.replication','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:57:09.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589653 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:57:09.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-10-17T19:57:09.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589653
;

-- 2019-10-17T19:57:09.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589653)
;

-- 2019-10-17T19:57:09.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,54613,589654,0,542068,TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.replication','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2019-10-17 21:57:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:57:09.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T19:57:09.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-10-17T19:57:09.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589654
;

-- 2019-10-17T19:57:09.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589654)
;

-- 2019-10-17T19:57:27.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=10.000000000000,Updated=TO_TIMESTAMP('2019-10-17 21:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589651
;

-- 2019-10-17T19:57:56.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589651,0,542068,543086,563310,'F',TO_TIMESTAMP('2019-10-17 21:57:56','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','N','N',0,'Created',15,0,0,TO_TIMESTAMP('2019-10-17 21:57:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:58:52.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589639,0,542068,543086,563311,'F',TO_TIMESTAMP('2019-10-17 21:58:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IMP_ProcessorLog_ID',12,0,0,TO_TIMESTAMP('2019-10-17 21:58:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T19:59:10.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-10-17 21:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563311
;

-- 2019-10-17T19:59:10.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-10-17 21:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563310
;

-- 2019-10-17T19:59:10.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-10-17 21:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563306
;

-- 2019-10-17T19:59:10.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-10-17 21:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563307
;

-- 2019-10-17T19:59:10.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-10-17 21:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563308
;

-- 2019-10-17T19:59:27.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=20.000000000000,Updated=TO_TIMESTAMP('2019-10-17 21:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589651
;

-- 2019-10-17T19:59:50.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=10.000000000000,Updated=TO_TIMESTAMP('2019-10-17 21:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589639
;

-- 2019-10-17T19:59:58.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2019-10-17 21:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589651
;

-- 2019-10-17T20:01:24.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,53384,541389,0,540744,TO_TIMESTAMP('2019-10-17 22:01:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.replication','IMP_ProcessorLog','Y','N','N','N','N','Import Prozessor Log',TO_TIMESTAMP('2019-10-17 22:01:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T20:01:24.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541389 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-10-17T20:01:24.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541389, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541389)
;

-- 2019-10-17T20:01:24.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(53384) 
;

-- 2019-10-17T20:01:24.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:24.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-10-17T20:01:28.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

CREATE INDEX imp_processorlog_imp_processor_id
    ON public.imp_processorlog (imp_processor_id);

-- 2019-10-17T20:06:18.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-17 22:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53092
;

