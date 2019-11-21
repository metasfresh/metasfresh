-- 2019-11-20T11:23:46.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,IsActive,Created,CreatedBy,WindowType,Processing,IsSOTrx,WinHeight,WinWidth,IsBetaFunctionality,IsDefault,Updated,UpdatedBy,IsOneInstanceOnly,AD_Window_ID,InternalName,Name,IsEnableRemoteCacheInvalidation,AD_Org_ID,EntityType,AD_Element_ID) VALUES (0,'Y',TO_TIMESTAMP('2019-11-20 13:23:46','YYYY-MM-DD HH24:MI:SS'),100,'M','N','Y',0,0,'N','N',TO_TIMESTAMP('2019-11-20 13:23:46','YYYY-MM-DD HH24:MI:SS'),100,'N',540756,'PriceListSchemaLine','Preislisten-Schema Position','Y',0,'D',577361)
;

-- 2019-11-20T11:23:46.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540756 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-11-20T11:23:46.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(577361) 
;

-- 2019-11-20T11:23:46.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540756
;

-- 2019-11-20T11:23:46.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540756)
;

-- 2019-11-20T11:25:04.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,Name,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Org_ID,EntityType,AD_Element_ID) VALUES (TO_TIMESTAMP('2019-11-20 13:25:04','YYYY-MM-DD HH24:MI:SS'),'N',540756,10,'N',0,TO_TIMESTAMP('2019-11-20 13:25:04','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N','N',0,'Y','N','N','Y','Y','Y','N',477,542121,'N','Y',0,'Preislisten-Schema Position','PriceListSchemaLine','Y','N',0,'D',577361)
;

-- 2019-11-20T11:25:04.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542121 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-11-20T11:25:04.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577361) 
;

-- 2019-11-20T11:25:04.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542121)
;

-- 2019-11-20T11:25:31.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:31','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:31','YYYY-MM-DD HH24:MI:SS'),100,'For the Pricelist Discount Type, you enter how the list, standard and limit price is calculated.',591813,'N',6612,'Discount Pricelist',0,'Line of the pricelist trade discount schema','D')
;

-- 2019-11-20T11:25:31.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:31.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1716) 
;

-- 2019-11-20T11:25:31.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591813
;

-- 2019-11-20T11:25:31.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591813)
;

-- 2019-11-20T11:25:31.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-20 13:25:31','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',591814,'N',6613,'Mandant',0,'Mandant für diese Installation.','D')
;

-- 2019-11-20T11:25:31.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:31.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-11-20T11:25:31.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591814
;

-- 2019-11-20T11:25:31.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591814)
;

-- 2019-11-20T11:25:31.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:31','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:31','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',591815,'N',6614,'Sektion',0,'Organisatorische Einheit des Mandanten','D')
;

-- 2019-11-20T11:25:31.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:31.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-11-20T11:25:32.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591815
;

-- 2019-11-20T11:25:32.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591815)
;

-- 2019-11-20T11:25:32.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',591816,'N',6615,'Aktiv',0,'Der Eintrag ist im System aktiv','D')
;

-- 2019-11-20T11:25:32.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:32.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-11-20T11:25:32.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591816
;

-- 2019-11-20T11:25:32.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591816)
;

-- 2019-11-20T11:25:32.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.',591817,'N',6620,'Rabatt Schema',0,'Schema um den prozentualen Rabatt zu berechnen','D')
;

-- 2019-11-20T11:25:32.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:32.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1714) 
;

-- 2019-11-20T11:25:32.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591817
;

-- 2019-11-20T11:25:32.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591817)
;

-- 2019-11-20T11:25:32.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'"Reihenfolge" bestimmt die Reihenfolge der Einträge',591818,'N',6621,'Reihenfolge',0,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D')
;

-- 2019-11-20T11:25:32.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:32.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2019-11-20T11:25:32.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591818
;

-- 2019-11-20T11:25:32.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591818)
;

-- 2019-11-20T11:25:32.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',591819,'N',6622,'Produkt Kategorie',0,'Kategorie eines Produktes','D')
;

-- 2019-11-20T11:25:32.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:32.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2019-11-20T11:25:32.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591819
;

-- 2019-11-20T11:25:32.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591819)
;

-- 2019-11-20T11:25:32.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',591820,'N',6623,'Geschäftspartner',0,'Bezeichnet einen Geschäftspartner','D')
;

-- 2019-11-20T11:25:32.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:32.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-11-20T11:25:32.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591820
;

-- 2019-11-20T11:25:32.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591820)
;

-- 2019-11-20T11:25:33.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',591821,'N',6624,'Produkt',0,'Produkt, Leistung, Artikel','D')
;

-- 2019-11-20T11:25:33.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-11-20T11:25:33.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591821
;

-- 2019-11-20T11:25:33.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591821)
;

-- 2019-11-20T11:25:33.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The Conversion Date identifies the date used for currency conversion. The conversion rate chosen must include this date in it''s date range',591822,'N',6626,'Konvertierungsdatum',0,'Datum für den gewählten Umrechnungskurs','D')
;

-- 2019-11-20T11:25:33.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1214) 
;

-- 2019-11-20T11:25:33.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591822
;

-- 2019-11-20T11:25:33.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591822)
;

-- 2019-11-20T11:25:33.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The List Price Base indicates the price to use as the basis for the calculation of a new price list.',591823,'N',6627,'Basis Listenpreis',0,'Price used as the basis for price list calculations','D')
;

-- 2019-11-20T11:25:33.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1222) 
;

-- 2019-11-20T11:25:33.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591823
;

-- 2019-11-20T11:25:33.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591823)
;

-- 2019-11-20T11:25:33.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The List Price Surcharge Amount indicates the amount to be added to the price prior to multiplication.',591824,'N',6628,'Aufschlag auf Listenpreis',0,'List Price Surcharge Amount','D')
;

-- 2019-11-20T11:25:33.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1221) 
;

-- 2019-11-20T11:25:33.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591824
;

-- 2019-11-20T11:25:33.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591824)
;

-- 2019-11-20T11:25:33.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The List Price Discount Percentage indicates the percentage discount which will be subtracted from the base price.  A negative amount indicates the percentage which will be added to the base price.',591825,'N',6629,'Abschlag % auf Listenpreis',0,'Discount from list price as a percentage','D')
;

-- 2019-11-20T11:25:33.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1237) 
;

-- 2019-11-20T11:25:33.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591825
;

-- 2019-11-20T11:25:33.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591825)
;

-- 2019-11-20T11:25:33.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The List Price Rounding indicates how the final list price will be rounded.',591826,'N',6630,'Rundung Listenpreis',0,'Rounding rule for final list price','D')
;

-- 2019-11-20T11:25:33.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1226) 
;

-- 2019-11-20T11:25:33.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591826
;

-- 2019-11-20T11:25:33.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591826)
;

-- 2019-11-20T11:25:33.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The List Price Min Margin indicates the minimum margin for a product.  The margin is calculated by subtracting the original list price from the newly calculated price.  If this field contains 0.00 then it is ignored.',591827,'N',6631,'List price min Margin',0,'Minimum margin for a product','D')
;

-- 2019-11-20T11:25:33.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1224) 
;

-- 2019-11-20T11:25:33.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591827
;

-- 2019-11-20T11:25:33.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591827)
;

-- 2019-11-20T11:25:33.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The List Price Max Margin indicates the maximum margin for a product.  The margin is calculated by subtracting the original list price from the newly calculated price.  If this field contains 0.00 then it is ignored.',591828,'N',6632,'List price max Margin',0,'Maximum margin for a product','D')
;

-- 2019-11-20T11:25:33.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:33.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1223) 
;

-- 2019-11-20T11:25:33.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591828
;

-- 2019-11-20T11:25:33.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591828)
;

-- 2019-11-20T11:25:34.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:33','YYYY-MM-DD HH24:MI:SS'),100,'The Standard Price Base indicates the price to use as the basis for the calculation of a new price standard.
',591829,'N',6633,'Basis Standardpreis',0,'Base price for calculating new standard price','D')
;

-- 2019-11-20T11:25:34.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1230) 
;

-- 2019-11-20T11:25:34.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591829
;

-- 2019-11-20T11:25:34.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591829)
;

-- 2019-11-20T11:25:34.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'',591830,'N',6634,'Aufschlag auf Standardpreis',0,'','D')
;

-- 2019-11-20T11:25:34.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1229) 
;

-- 2019-11-20T11:25:34.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591830
;

-- 2019-11-20T11:25:34.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591830)
;

-- 2019-11-20T11:25:34.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'The Standard Price Discount Percentage indicates the percentage discount which will be subtracted from the base price.  A negative amount indicates the percentage which will be added to the base price.',591831,'N',6635,'Abschlag % auf Standardpreis',0,'Discount percentage to subtract from base price','D')
;

-- 2019-11-20T11:25:34.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1238) 
;

-- 2019-11-20T11:25:34.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591831
;

-- 2019-11-20T11:25:34.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591831)
;

-- 2019-11-20T11:25:34.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'The Standard Price Rounding indicates how the final Standard price will be rounded.',591832,'N',6636,'Rundung Standardpreis',0,'Rounding rule for calculated price','D')
;

-- 2019-11-20T11:25:34.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1234) 
;

-- 2019-11-20T11:25:34.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591832
;

-- 2019-11-20T11:25:34.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591832)
;

-- 2019-11-20T11:25:34.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'The Standard Price Min Margin indicates the minimum margin for a product.  The margin is calculated by subtracting the original Standard price from the newly calculated price.  If this field contains 0.00 then it is ignored.',591833,'N',6637,'Standard price min Margin',0,'Minimum margin allowed for a product','D')
;

-- 2019-11-20T11:25:34.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1232) 
;

-- 2019-11-20T11:25:34.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591833
;

-- 2019-11-20T11:25:34.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591833)
;

-- 2019-11-20T11:25:34.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'The Standard Price Max Margin indicates the maximum margin for a product.  The margin is calculated by subtracting the original Standard price from the newly calculated price.  If this field contains 0.00 then it is ignored.',591834,'N',6638,'Standard max Margin',0,'Maximum margin allowed for a product','D')
;

-- 2019-11-20T11:25:34.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1231) 
;

-- 2019-11-20T11:25:34.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591834
;

-- 2019-11-20T11:25:34.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591834)
;

-- 2019-11-20T11:25:34.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Identifies the price to be used as the base for calculating a new price list.',591835,'N',6639,'Basis Mindestpreis',0,'Base price for calculation of the new price','D')
;

-- 2019-11-20T11:25:34.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1216) 
;

-- 2019-11-20T11:25:34.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591835
;

-- 2019-11-20T11:25:34.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591835)
;

-- 2019-11-20T11:25:34.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Indicates the amount to be added to the Limit price prior to multiplication.',591836,'N',6640,'Aufschlag auf Mindestpreis',0,'Amount added to the converted/copied price before multiplying','D')
;

-- 2019-11-20T11:25:34.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:34.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1215) 
;

-- 2019-11-20T11:25:34.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591836
;

-- 2019-11-20T11:25:34.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591836)
;

-- 2019-11-20T11:25:35.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Indicates the discount in percent to be subtracted from base, if negative it will be added to base price',591837,'N',6641,'Abschlag % auf Mindestpreis',0,'Discount in percent to be subtracted from base, if negative it will be added to base price','D')
;

-- 2019-11-20T11:25:35.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1236) 
;

-- 2019-11-20T11:25:35.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591837
;

-- 2019-11-20T11:25:35.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591837)
;

-- 2019-11-20T11:25:35.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'A drop down list box which indicates the rounding (if any) will apply to the final prices in this price list.',591838,'N',6642,'Rundung Mindestpreis',0,'Rounding of the final result','D')
;

-- 2019-11-20T11:25:35.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1220) 
;

-- 2019-11-20T11:25:35.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591838
;

-- 2019-11-20T11:25:35.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591838)
;

-- 2019-11-20T11:25:35.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'Indicates the minimum margin for a product.  The margin is calculated by subtracting the original limit price from the newly calculated price.  If this field contains 0.00 then it is ignored.',591839,'N',6643,'Limit price min Margin',0,'Minimum difference to original limit price; ignored if zero','D')
;

-- 2019-11-20T11:25:35.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1218) 
;

-- 2019-11-20T11:25:35.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591839
;

-- 2019-11-20T11:25:35.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591839)
;

-- 2019-11-20T11:25:35.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'Indicates the maximum margin for a product.  The margin is calculated by subtracting the original limit price from the newly calculated price.  If this field contains 0.00 then it is ignored.',591840,'N',6644,'Limit price max Margin',0,'Maximum difference to original limit price; ignored if zero','D')
;

-- 2019-11-20T11:25:35.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1217) 
;

-- 2019-11-20T11:25:35.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591840
;

-- 2019-11-20T11:25:35.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591840)
;

-- 2019-11-20T11:25:35.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,591841,'N',6714,'Fixed Limit Price',0,'Fixed Limit Price (not calculated)','D')
;

-- 2019-11-20T11:25:35.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1744) 
;

-- 2019-11-20T11:25:35.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591841
;

-- 2019-11-20T11:25:35.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591841)
;

-- 2019-11-20T11:25:35.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,591842,'N',6715,'Fixed List Price',0,'Fixes List Price (not calculated)','D')
;

-- 2019-11-20T11:25:35.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1745) 
;

-- 2019-11-20T11:25:35.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591842
;

-- 2019-11-20T11:25:35.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591842)
;

-- 2019-11-20T11:25:35.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',22,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.',591843,'N',10291,'Kursart',0,'Kursart','D')
;

-- 2019-11-20T11:25:35.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2278) 
;

-- 2019-11-20T11:25:35.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591843
;

-- 2019-11-20T11:25:35.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591843)
;

-- 2019-11-20T11:25:35.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,591844,'N',54237,'Group1',0,'D')
;

-- 2019-11-20T11:25:35.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52018) 
;

-- 2019-11-20T11:25:35.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591844
;

-- 2019-11-20T11:25:35.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591844)
;

-- 2019-11-20T11:25:35.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,591845,'N',54238,'Group2',0,'D')
;

-- 2019-11-20T11:25:35.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:35.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52019) 
;

-- 2019-11-20T11:25:35.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591845
;

-- 2019-11-20T11:25:35.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591845)
;

-- 2019-11-20T11:25:36.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',12,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:35','YYYY-MM-DD HH24:MI:SS'),100,'Die "Klassifizierung" kann für die optionale Gruppierung von Produkten verwendet werden.',591846,'N',54239,'Klassifizierung',0,'Klassifizierung für die Gruppierung','D')
;

-- 2019-11-20T11:25:36.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(852) 
;

-- 2019-11-20T11:25:36.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591846
;

-- 2019-11-20T11:25:36.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591846)
;

-- 2019-11-20T11:25:36.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',3,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591847,'N',544454,'Basis Prov.-Punkte',0,'D')
;

-- 2019-11-20T11:25:36.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541185) 
;

-- 2019-11-20T11:25:36.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591847
;

-- 2019-11-20T11:25:36.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591847)
;

-- 2019-11-20T11:25:36.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',14,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591848,'N',544455,'Abschlag % auf Provisionspunkte',0,'D')
;

-- 2019-11-20T11:25:36.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541186) 
;

-- 2019-11-20T11:25:36.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591848
;

-- 2019-11-20T11:25:36.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591848)
;

-- 2019-11-20T11:25:36.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591849,'N',544456,'Provisionspunkte MwSt. abziehen',0,'D')
;

-- 2019-11-20T11:25:36.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541187) 
;

-- 2019-11-20T11:25:36.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591849
;

-- 2019-11-20T11:25:36.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591849)
;

-- 2019-11-20T11:25:36.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',14,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591850,'N',544457,'Aufschlag auf Provisionspunkte',0,'D')
;

-- 2019-11-20T11:25:36.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541188) 
;

-- 2019-11-20T11:25:36.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591850
;

-- 2019-11-20T11:25:36.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591850)
;

-- 2019-11-20T11:25:36.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591851,'N',544458,'Rundung Provisionspunkte',0,'D')
;

-- 2019-11-20T11:25:36.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541189) 
;

-- 2019-11-20T11:25:36.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591851
;

-- 2019-11-20T11:25:36.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591851)
;

-- 2019-11-20T11:25:36.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542121,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
',591852,'N',551503,'Steuerkategorie',0,'Steuerkategorie','D')
;

-- 2019-11-20T11:25:36.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(211) 
;

-- 2019-11-20T11:25:36.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591852
;

-- 2019-11-20T11:25:36.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591852)
;

-- 2019-11-20T11:25:36.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591853,'N',551504,'Ziel-Steuerkategorie',0,'D')
;

-- 2019-11-20T11:25:36.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:36.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542628) 
;

-- 2019-11-20T11:25:36.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591853
;

-- 2019-11-20T11:25:36.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591853)
;

-- 2019-11-20T11:25:37.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542121,'N',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-20 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,591854,'N',54606,'Product Value',0,'D')
;

-- 2019-11-20T11:25:37.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-20T11:25:37.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577363) 
;

-- 2019-11-20T11:25:37.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591854
;

-- 2019-11-20T11:25:37.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591854)
;

-- 2019-11-20T11:26:26.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (Value,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Section_ID,Updated,UpdatedBy,AD_Tab_ID,SeqNo,AD_Org_ID) VALUES ('main',0,TO_TIMESTAMP('2019-11-20 13:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',541650,TO_TIMESTAMP('2019-11-20 13:26:26','YYYY-MM-DD HH24:MI:SS'),100,542121,10,0)
;

-- 2019-11-20T11:26:26.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541650 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-11-20T11:26:36.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-11-20 13:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',542101,TO_TIMESTAMP('2019-11-20 13:26:36','YYYY-MM-DD HH24:MI:SS'),541650,10,0)
;

-- 2019-11-20T11:26:39.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-11-20 13:26:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',542102,TO_TIMESTAMP('2019-11-20 13:26:38','YYYY-MM-DD HH24:MI:SS'),541650,20,0)
;

-- 2019-11-20T11:26:50.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,Name,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-20 13:26:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',543173,10,TO_TIMESTAMP('2019-11-20 13:26:50','YYYY-MM-DD HH24:MI:SS'),542102,100,'flags',0)
;

-- 2019-11-20T11:26:55.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,Name,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-20 13:26:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',543174,20,TO_TIMESTAMP('2019-11-20 13:26:55','YYYY-MM-DD HH24:MI:SS'),542102,100,'org',0)
;

-- 2019-11-20T11:28:04.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564000,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,100,10,0,'N',0,0,543174,'F','N',0,'N','Sektion',591815,542121,TO_TIMESTAMP('2019-11-20 13:28:04','YYYY-MM-DD HH24:MI:SS'),'Organisatorische Einheit des Mandanten','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:28:04','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:28:14.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564001,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,100,20,0,'N',0,0,543174,'F','N',0,'N','Mandant',591814,542121,TO_TIMESTAMP('2019-11-20 13:28:14','YYYY-MM-DD HH24:MI:SS'),'Mandant für diese Installation.','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:28:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:28:37.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564002,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,100,10,0,'N',0,0,543173,'F','N',0,'N','Aktiv',591816,542121,TO_TIMESTAMP('2019-11-20 13:28:37','YYYY-MM-DD HH24:MI:SS'),'Der Eintrag ist im System aktiv','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:28:37','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:30:53.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (AD_Window_ID,AD_Client_ID,IsActive,Created,CreatedBy,IsSummary,IsSOTrx,IsReadOnly,Updated,UpdatedBy,Name,AD_Menu_ID,IsCreateNew,InternalName,Action,AD_Org_ID,EntityType,AD_Element_ID) VALUES (540756,0,'Y',TO_TIMESTAMP('2019-11-20 13:30:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N',TO_TIMESTAMP('2019-11-20 13:30:53','YYYY-MM-DD HH24:MI:SS'),100,'Preislisten-Schema Position',541402,'N','PriceListSchemaLine','W',0,'D',577361)
;

-- 2019-11-20T11:30:53.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541402 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-11-20T11:30:53.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541402, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541402)
;

-- 2019-11-20T11:30:53.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(577361) 
;

-- 2019-11-20T11:31:20.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541399 AND AD_Tree_ID=10
;

-- 2019-11-20T11:31:20.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541402 AND AD_Tree_ID=10
;

-- 2019-11-20T11:31:20.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540778 AND AD_Tree_ID=10
;

-- 2019-11-20T11:31:20.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541078 AND AD_Tree_ID=10
;

-- 2019-11-20T11:31:52.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Price List Schema Line',Updated=TO_TIMESTAMP('2019-11-20 13:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541402
;

-- 2019-11-20T11:33:29.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,UIStyle,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,Name,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-20 13:33:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',543175,'primary',10,TO_TIMESTAMP('2019-11-20 13:33:29','YYYY-MM-DD HH24:MI:SS'),542101,100,'default',0)
;

-- 2019-11-20T11:34:27.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564003,'Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.',0,100,10,0,'N',0,0,543175,'F','N',0,'N','Price List Schema',591817,542121,TO_TIMESTAMP('2019-11-20 13:34:27','YYYY-MM-DD HH24:MI:SS'),'Schema um den prozentualen Rabatt zu berechnen','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:34:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:36:36.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564004,'"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,100,20,0,'N',0,0,543175,'F','N',0,'N','Reihenfolge',591818,542121,TO_TIMESTAMP('2019-11-20 13:36:35','YYYY-MM-DD HH24:MI:SS'),'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:36:35','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:38:21.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,Name,AD_Org_ID) VALUES (0,TO_TIMESTAMP('2019-11-20 13:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',543176,20,TO_TIMESTAMP('2019-11-20 13:38:21','YYYY-MM-DD HH24:MI:SS'),542101,100,'rest',0)
;

-- 2019-11-20T11:38:40.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564005,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,100,10,0,'N',0,0,543176,'F','N',0,'N','Produkt',591821,542121,TO_TIMESTAMP('2019-11-20 13:38:40','YYYY-MM-DD HH24:MI:SS'),'Produkt, Leistung, Artikel','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:38:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:38:46.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564006,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,100,20,0,'N',0,0,543176,'F','N',0,'N','Produkt Kategorie',591819,542121,TO_TIMESTAMP('2019-11-20 13:38:46','YYYY-MM-DD HH24:MI:SS'),'Kategorie eines Produktes','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:38:46','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:38:55.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564007,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,100,30,0,'N',0,0,543176,'F','N',0,'N','Geschäftspartner',591820,542121,TO_TIMESTAMP('2019-11-20 13:38:55','YYYY-MM-DD HH24:MI:SS'),'Bezeichnet einen Geschäftspartner','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:38:55','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:39:28.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564008,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
',0,100,40,0,'N',0,0,543176,'F','N',0,'N','Steuerkategorie',591852,542121,TO_TIMESTAMP('2019-11-20 13:39:28','YYYY-MM-DD HH24:MI:SS'),'Steuerkategorie','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:39:28','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:40:05.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564009,0,100,50,0,'N',0,0,543176,'F','N',0,'N','Aufschlag auf Standardpreis',591830,542121,TO_TIMESTAMP('2019-11-20 13:40:04','YYYY-MM-DD HH24:MI:SS'),'Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:40:04','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:40:24.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564010,'The Standard Price Rounding indicates how the final Standard price will be rounded.',0,100,60,0,'N',0,0,543176,'F','N',0,'N','Rundung Standardpreis',591832,542121,TO_TIMESTAMP('2019-11-20 13:40:24','YYYY-MM-DD HH24:MI:SS'),'Rounding rule for calculated price','Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:40:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-20T11:40:49.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564011,0,100,70,0,'N',0,0,543176,'F','N',0,'N','Ziel-Steuerkategorie',591853,542121,TO_TIMESTAMP('2019-11-20 13:40:48','YYYY-MM-DD HH24:MI:SS'),'Y','N','Y','N',TO_TIMESTAMP('2019-11-20 13:40:48','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-21T06:50:59.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
)

SELECT --
       -- fields which appear in the UI
       l.seqno,
       l.product_value m_product_id,
       l.m_product_category_id,
       l.c_bpartner_id,
       l.c_taxcategory_id,
       l.std_addamt,
       l.std_rounding,
       l.c_taxcategory_target_id

FROM exportedLines l

UNION ALL

SELECT --
       -- fields which appear in the UI
       (SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10 seqno,                  -- this select is needed since seqno must be unique
       p.value                                                                            m_product_id,
       NULL                                                                               m_product_category_id,
       NULL                                                                               c_bpartner_id,
       NULL                                                                               c_taxcategory_id,
       0                                                                                  std_addamt,
       ''C''                                                                                std_rounding,           -- this is the default value
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = 114)             c_taxcategory_target_id -- this is the default value

FROM M_Product p
WHERE 1 = 1
  AND p.m_product_id NOT IN (SELECT coalesce(m_product_id, 0) FROM exportedLines)
  AND p.IsActive = ''Y''
  AND p.IsSold = ''Y''
  AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
  AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)

ORDER BY seqno',Updated=TO_TIMESTAMP('2019-11-21 08:50:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T06:52:31.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
)

SELECT --
       -- fields which appear in the UI
       l.seqno,
       l.product_value m_product_id,
       l.m_product_category_id,
       l.c_bpartner_id,
       l.c_taxcategory_id,
       l.std_addamt,
       l.std_rounding,
       l.c_taxcategory_target_id

FROM exportedLines l

UNION ALL

SELECT --
       -- fields which appear in the UI
       (SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10 seqno,        -- this select is needed since seqno must be unique
       p.value                                                                            m_product_id,
       NULL                                                                               m_product_category_id,
       NULL                                                                               c_bpartner_id,
       NULL                                                                               c_taxcategory_id,
       0                                                                                  std_addamt,
       ''C''                                                                                std_rounding, -- this is the default value
       NULL                                                                               c_taxcategory_target_id

FROM M_Product p
WHERE 1 = 1
  AND p.m_product_id NOT IN (SELECT coalesce(m_product_id, 0) FROM exportedLines)
  AND p.IsActive = ''Y''
  AND p.IsSold = ''Y''
  AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
  AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)

ORDER BY seqno
',Updated=TO_TIMESTAMP('2019-11-21 08:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

