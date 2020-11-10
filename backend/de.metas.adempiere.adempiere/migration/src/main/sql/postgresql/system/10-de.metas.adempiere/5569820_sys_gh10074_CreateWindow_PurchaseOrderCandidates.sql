-- 2020-10-07T08:42:04.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='isSOTrx = ''Y''',Updated=TO_TIMESTAMP('2020-10-07 11:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540279
;

-- 2020-10-07T08:42:44.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578409,0,TO_TIMESTAMP('2020-10-07 11:42:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Purchase Invoice Candidates','Purchase Invoice Candidates',TO_TIMESTAMP('2020-10-07 11:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:42:44.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578409 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-10-07T08:44:47.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsdisposition Einkauf', PrintName='Rechnungsdisposition Einkauf',Updated=TO_TIMESTAMP('2020-10-07 11:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578409 AND AD_Language='de_CH'
;

-- 2020-10-07T08:44:47.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578409,'de_CH') 
;

-- 2020-10-07T08:44:51.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsdisposition Einkauf', PrintName='Rechnungsdisposition Einkauf',Updated=TO_TIMESTAMP('2020-10-07 11:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578409 AND AD_Language='de_DE'
;

-- 2020-10-07T08:44:51.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578409,'de_DE') 
;

-- 2020-10-07T08:44:51.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578409,'de_DE') 
;

-- 2020-10-07T08:44:51.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnungsdisposition Einkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=578409
;

-- 2020-10-07T08:44:51.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnungsdisposition Einkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=578409 AND IsCentrallyMaintained='Y'
;

-- 2020-10-07T08:44:51.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsdisposition Einkauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578409) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578409)
;

-- 2020-10-07T08:44:51.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungsdisposition Einkauf', Name='Rechnungsdisposition Einkauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578409)
;

-- 2020-10-07T08:44:51.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsdisposition Einkauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578409
;

-- 2020-10-07T08:44:51.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsdisposition Einkauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 578409
;

-- 2020-10-07T08:44:51.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsdisposition Einkauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578409
;

-- 2020-10-07T08:44:53.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-07 11:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578409 AND AD_Language='en_US'
;

-- 2020-10-07T08:44:53.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578409,'en_US') 
;

-- 2020-10-07T08:45:43.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,578409,0,540983,TO_TIMESTAMP('2020-10-07 11:45:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Purchase Invoice Candidates','Y','N','N','Y','N','N','Rechnungsdisposition Einkauf','N',TO_TIMESTAMP('2020-10-07 11:45:42','YYYY-MM-DD HH24:MI:SS'),100,'T',0,0)
;

-- 2020-10-07T08:45:43.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=540983 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2020-10-07T08:45:43.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(578409) 
;

-- 2020-10-07T08:45:43.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540983
;

-- 2020-10-07T08:45:43.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540983)
;

-- 2020-10-07T08:46:16.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='de.metas.invoicecandidate', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsOneInstanceOnly='N', IsSOTrx='Y', WindowType='T', WinHeight=0, WinWidth=0,Updated=TO_TIMESTAMP('2020-10-07 11:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540983
;

-- 2020-10-07T08:46:16.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 540983
;

-- 2020-10-07T08:46:16.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 540983, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540092
;

-- 2020-10-07T08:46:16.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,572507,0,543052,540270,540983,'Y',TO_TIMESTAMP('2020-10-07 11:46:16','YYYY-MM-DD HH24:MI:SS'),100,'Enthält Positionen, die ggf. in Rechnung gestellt werden können.','de.metas.invoicecandidate','N','Ein Rechnungskandidat kann eine Auftragszeile referenzieren, muss aber nicht.','N','C_Invoice_Candidate','Y','N','N','Y','N','N','Y','N','N','N','N','Y','Y','N','N','N','Rechnungskandidaten','N',10,0,TO_TIMESTAMP('2020-10-07 11:46:16','YYYY-MM-DD HH24:MI:SS'),100,'isSOTrx = ''Y''')
;

-- 2020-10-07T08:46:16.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543052 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-10-07T08:46:16.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572507) 
;

-- 2020-10-07T08:46:16.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543052)
;

-- 2020-10-07T08:46:16.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 543052
;

-- 2020-10-07T08:46:16.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 543052, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540279
;

-- 2020-10-07T08:46:16.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544907,622299,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:16.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:16.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-10-07T08:46:17.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622299
;

-- 2020-10-07T08:46:17.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622299)
;

-- 2020-10-07T08:46:17.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622299
;

-- 2020-10-07T08:46:17.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622299, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546894
;

-- 2020-10-07T08:46:17.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544909,622300,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.invoicecandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:17.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:17.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2020-10-07T08:46:17.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622300
;

-- 2020-10-07T08:46:17.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622300)
;

-- 2020-10-07T08:46:17.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622300
;

-- 2020-10-07T08:46:17.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622300, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546895
;

-- 2020-10-07T08:46:17.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544910,622301,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.invoicecandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',30,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:17.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:17.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2020-10-07T08:46:17.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622301
;

-- 2020-10-07T08:46:17.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622301)
;

-- 2020-10-07T08:46:17.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622301
;

-- 2020-10-07T08:46:17.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622301, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546896
;

-- 2020-10-07T08:46:17.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544919,622302,1001733,0,543052,74,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags',7,'de.metas.invoicecandidate','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','Y','Y','N','N','N','Y','Y','Auftragsdatum',160,370,1,1,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:17.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:17.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001733) 
;

-- 2020-10-07T08:46:17.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622302
;

-- 2020-10-07T08:46:17.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622302)
;

-- 2020-10-07T08:46:17.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622302
;

-- 2020-10-07T08:46:17.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622302, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546897
;

-- 2020-10-07T08:46:18.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544912,622303,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',10,'@IsManual/''N''@=''N''','de.metas.invoicecandidate','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','Y','N','N','N','N','Y','Y','Auftragsposition',170,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:18.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:18.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2020-10-07T08:46:18.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622303
;

-- 2020-10-07T08:46:18.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622303)
;

-- 2020-10-07T08:46:18.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622303
;

-- 2020-10-07T08:46:18.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622303, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546898
;

-- 2020-10-07T08:46:18.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544903,622304,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.invoicecandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',180,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:18.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:18.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2020-10-07T08:46:18.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622304
;

-- 2020-10-07T08:46:18.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622304)
;

-- 2020-10-07T08:46:18.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622304
;

-- 2020-10-07T08:46:18.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622304, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546899
;

-- 2020-10-07T08:46:18.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544904,622305,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.invoicecandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',190,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:18.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:18.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2020-10-07T08:46:18.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622305
;

-- 2020-10-07T08:46:18.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622305)
;

-- 2020-10-07T08:46:18.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622305
;

-- 2020-10-07T08:46:18.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622305, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546900
;

-- 2020-10-07T08:46:18.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544924,622306,1001629,0,543052,'select case when ''@IsToRecompute@''= ''Y'' then(select ad_color_id from ad_color where name = ''Orange'') end as ad_color_id',42,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100,'Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.',1,'de.metas.invoicecandidate','Beim nächsten Lauf des Aktualisierungsprozesses wird der so markierten Rechnungskandidat aktualisiert und der Wert durch den Prozess danach auf "nein". gesetzt.',0,'Y','Y','Y','N','N','N','Y','N','zu Akt.',960,270,1,1,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:18.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:18.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001629) 
;

-- 2020-10-07T08:46:18.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622306
;

-- 2020-10-07T08:46:18.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622306)
;

-- 2020-10-07T08:46:18.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622306
;

-- 2020-10-07T08:46:18.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622306, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546901
;

-- 2020-10-07T08:46:18.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544906,622307,1002265,0,543052,79,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','Y','Rechnungskandidat',20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:18.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:18.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002265) 
;

-- 2020-10-07T08:46:18.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622307
;

-- 2020-10-07T08:46:18.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622307)
;

-- 2020-10-07T08:46:18.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622307
;

-- 2020-10-07T08:46:18.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622307, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546902
;

-- 2020-10-07T08:46:18.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544901,622308,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.invoicecandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',970,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:18.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:18.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-10-07T08:46:19.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622308
;

-- 2020-10-07T08:46:19.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622308)
;

-- 2020-10-07T08:46:19.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622308
;

-- 2020-10-07T08:46:19.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622308, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546903
;

-- 2020-10-07T08:46:19.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544913,622309,577439,0,543052,50,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Auftrag/Bestellung',150,40,1,1,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:19.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:19.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577439) 
;

-- 2020-10-07T08:46:19.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622309
;

-- 2020-10-07T08:46:19.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622309)
;

-- 2020-10-07T08:46:19.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622309
;

-- 2020-10-07T08:46:19.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622309, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546904
;

-- 2020-10-07T08:46:19.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544902,622310,0,543052,73,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.invoicecandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','Y','N','Sektion',10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:19.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:19.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-10-07T08:46:19.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622310
;

-- 2020-10-07T08:46:19.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622310)
;

-- 2020-10-07T08:46:19.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622310
;

-- 2020-10-07T08:46:19.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622310, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546905
;

-- 2020-10-07T08:46:19.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544923,622311,0,543052,181,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.invoicecandidate','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','Y','N','Produkt',260,100,1,1,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:19.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:19.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2020-10-07T08:46:19.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622311
;

-- 2020-10-07T08:46:19.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622311)
;

-- 2020-10-07T08:46:19.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622311
;

-- 2020-10-07T08:46:19.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622311, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546906
;

-- 2020-10-07T08:46:19.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544914,622312,0,543052,44,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Abzurechnen eff. (Lagereinheit)',760,340,1,1,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:19.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:19.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1251) 
;

-- 2020-10-07T08:46:19.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622312
;

-- 2020-10-07T08:46:19.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622312)
;

-- 2020-10-07T08:46:19.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622312
;

-- 2020-10-07T08:46:19.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622312, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546908
;

-- 2020-10-07T08:46:19.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544922,622313,1001303,0,543052,135,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Rechnungskontakt',700,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:19.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:19.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001303) 
;

-- 2020-10-07T08:46:19.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622313
;

-- 2020-10-07T08:46:19.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622313)
;

-- 2020-10-07T08:46:19.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622313
;

-- 2020-10-07T08:46:19.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622313, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546909
;

-- 2020-10-07T08:46:19.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544920,540070,622314,1001280,0,543052,291,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Rechnungspartner',680,90,1,1,TO_TIMESTAMP('2020-10-07 11:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:19.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:19.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001280) 
;

-- 2020-10-07T08:46:19.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622314
;

-- 2020-10-07T08:46:19.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622314)
;

-- 2020-10-07T08:46:20Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622314
;

-- 2020-10-07T08:46:20.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622314, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546910
;

-- 2020-10-07T08:46:20.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544921,622315,1002727,0,543052,141,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Rechnungsstandort',690,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002727) 
;

-- 2020-10-07T08:46:20.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622315
;

-- 2020-10-07T08:46:20.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622315)
;

-- 2020-10-07T08:46:20.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622315
;

-- 2020-10-07T08:46:20.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622315, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546911
;

-- 2020-10-07T08:46:20.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544918,540069,622316,0,543052,190,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.',1,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Rechnungsstellung',390,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(559) 
;

-- 2020-10-07T08:46:20.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622316
;

-- 2020-10-07T08:46:20.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622316)
;

-- 2020-10-07T08:46:20.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622316
;

-- 2020-10-07T08:46:20.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622316, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546912
;

-- 2020-10-07T08:46:20.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544925,622317,0,543052,121,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Informationen des Aktualisierungsprozesses',30,'@IsError/''N''@=''Y''','de.metas.invoicecandidate','Das Feld wird durch den Aktualisierungprozess zu Beginn geleert und dann ggf. mit Diagnose-Informationen gefüllt, um die Resultate der Aktualisierung nachvollziehbarer zu machen.',0,'Y','Y','N','N','N','N','Y','N','Status nach Akt.',1020,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541269) 
;

-- 2020-10-07T08:46:20.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622317
;

-- 2020-10-07T08:46:20.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622317)
;

-- 2020-10-07T08:46:20.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622317
;

-- 2020-10-07T08:46:20.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622317, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 546913
;

-- 2020-10-07T08:46:20.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544967,622318,1000972,0,543052,61,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis',10,'de.metas.invoicecandidate','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',0,'Y','Y','Y','N','N','N','Y','N','Preis eff.',620,170,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000972) 
;

-- 2020-10-07T08:46:20.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622318
;

-- 2020-10-07T08:46:20.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622318)
;

-- 2020-10-07T08:46:20.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622318
;

-- 2020-10-07T08:46:20.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622318, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547029
;

-- 2020-10-07T08:46:20.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544968,622319,0,543052,105,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis',10,'de.metas.invoicecandidate','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',0,'Y','Y','N','N','N','N','Y','Y','Einzelpreis abw.',630,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541280) 
;

-- 2020-10-07T08:46:20.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622319
;

-- 2020-10-07T08:46:20.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622319)
;

-- 2020-10-07T08:46:20.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622319
;

-- 2020-10-07T08:46:20.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622319, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547030
;

-- 2020-10-07T08:46:20.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544966,622320,0,543052,153,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Kosten',10,'de.metas.invoicecandidate','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)',0,'Y','Y','N','N','N','N','Y','Y','Kosten',250,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(968) 
;

-- 2020-10-07T08:46:20.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622320
;

-- 2020-10-07T08:46:20.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622320)
;

-- 2020-10-07T08:46:20.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622320
;

-- 2020-10-07T08:46:20.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622320, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547031
;

-- 2020-10-07T08:46:20.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544969,622321,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent',10,'de.metas.invoicecandidate','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.',0,'Y','Y','N','N','N','N','Y','N','Rabatt %',600,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:20.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:20.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(280) 
;

-- 2020-10-07T08:46:20.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622321
;

-- 2020-10-07T08:46:20.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622321)
;

-- 2020-10-07T08:46:20.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622321
;

-- 2020-10-07T08:46:20.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622321, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547032
;

-- 2020-10-07T08:46:21.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544970,622322,0,543052,107,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent',10,'de.metas.invoicecandidate','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.',0,'Y','Y','N','N','N','N','N','Y','Rabatt abw. %',610,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541281) 
;

-- 2020-10-07T08:46:21.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622322
;

-- 2020-10-07T08:46:21.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622322)
;

-- 2020-10-07T08:46:21.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622322
;

-- 2020-10-07T08:46:21.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622322, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547033
;

-- 2020-10-07T08:46:21.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545312,622323,576974,0,543052,56,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Beauftragt (Lagereinheit)',360,120,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576974) 
;

-- 2020-10-07T08:46:21.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622323
;

-- 2020-10-07T08:46:21.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622323)
;

-- 2020-10-07T08:46:21.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622323
;

-- 2020-10-07T08:46:21.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622323, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547551
;

-- 2020-10-07T08:46:21.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545314,622324,576973,0,543052,51,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Geliefert (Lagereinheit)',370,130,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576973) 
;

-- 2020-10-07T08:46:21.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622324
;

-- 2020-10-07T08:46:21.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622324)
;

-- 2020-10-07T08:46:21.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622324
;

-- 2020-10-07T08:46:21.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622324, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547552
;

-- 2020-10-07T08:46:21.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545315,622325,0,543052,'select case when @NetAmtToInvoice@= 0 and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Orange'') end as ad_color_id',73,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Abzurechnender Betrag',850,190,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1249) 
;

-- 2020-10-07T08:46:21.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622325
;

-- 2020-10-07T08:46:21.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622325)
;

-- 2020-10-07T08:46:21.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622325
;

-- 2020-10-07T08:46:21.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622325, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547553
;

-- 2020-10-07T08:46:21.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545313,622326,576972,0,543052,103,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Abgerechnet (Lagereinheit)',710,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576972) 
;

-- 2020-10-07T08:46:21.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622326
;

-- 2020-10-07T08:46:21.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622326)
;

-- 2020-10-07T08:46:21.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622326
;

-- 2020-10-07T08:46:21.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622326, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547554
;

-- 2020-10-07T08:46:21.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545316,622327,0,543052,61,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'de.metas.invoicecandidate','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','N','N','N','N','Y','N','Währung',310,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2020-10-07T08:46:21.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622327
;

-- 2020-10-07T08:46:21.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622327)
;

-- 2020-10-07T08:46:21.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622327
;

-- 2020-10-07T08:46:21.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622327, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547555
;

-- 2020-10-07T08:46:21.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545319,622328,1000784,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum auf der Rechnung',7,'de.metas.invoicecandidate','"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.',0,'Y','Y','N','N','N','N','Y','N','Rechnungsdatum',470,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000784) 
;

-- 2020-10-07T08:46:21.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622328
;

-- 2020-10-07T08:46:21.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622328)
;

-- 2020-10-07T08:46:21.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622328
;

-- 2020-10-07T08:46:21.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622328, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547561
;

-- 2020-10-07T08:46:21.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545683,622329,0,543052,'select case when ''@IsToClear@''= ''Y'' and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Orange'') end as ad_color_id',34,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Leistung wird nicht unmittelbar in Rechnung gestellt, sondern mit anderen Posten (z.B. Pauschale) verrechnet',1,'@IsManual/''N''@=''N''','de.metas.invoicecandidate','Diese Markierung wird vom System gesetzt, wenn ein Rechnungskandidat eine Auftragszeile referenziert, die einem laufenden Pauschalen-Vertrag unterliegt.',0,'Y','Y','Y','N','N','N','Y','Y','zur Verrechnung',140,320,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:21.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:21.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541431) 
;

-- 2020-10-07T08:46:21.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622329
;

-- 2020-10-07T08:46:21.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622329)
;

-- 2020-10-07T08:46:21.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622329
;

-- 2020-10-07T08:46:21.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622329, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548114
;

-- 2020-10-07T08:46:22.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545771,622330,1001157,0,543052,42,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob der Rechnungskandidat komplet abgwerechnet wurde',1,'de.metas.invoicecandidate','Der Beleg wird vom System als verarbeitet markiert, wenn die bestellte Menge komplett in Rechnung gestellt wurde.<br>
Abgerechnete Datensätze dürfen nicht mehr geändert werden.',0,'Y','Y','Y','N','N','N','Y','Y','Komplett Abgerechnet eff.',1010,250,1,1,TO_TIMESTAMP('2020-10-07 11:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001157) 
;

-- 2020-10-07T08:46:22.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622330
;

-- 2020-10-07T08:46:22.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622330)
;

-- 2020-10-07T08:46:22.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622330
;

-- 2020-10-07T08:46:22.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622330, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548115
;

-- 2020-10-07T08:46:22.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545835,622331,1002360,0,543052,58,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.',10,'de.metas.invoicecandidate','Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.',0,'Y','Y','Y','N','N','N','N','Y','Abzurechnen abw. (Lagereinheit)',740,140,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002360) 
;

-- 2020-10-07T08:46:22.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622331
;

-- 2020-10-07T08:46:22.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622331)
;

-- 2020-10-07T08:46:22.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622331
;

-- 2020-10-07T08:46:22.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622331, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548190
;

-- 2020-10-07T08:46:22.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545836,622332,0,543052,247,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Angabe über den Teil der abweichenden Menge, der bereits in Rechnung gestellt wurde',10,'de.metas.invoicecandidate','Sobald eine vom Benutzer angegebene "Zu berechn. Menge abw." komplett in Rechnung gestellt wurde, wird der Wert dieses Feldes sowie die abweichende Menge vom System gelöscht. Dadurch wird verhindert, die abweichenden Menge mehrfach in Rechnung gestellt wird.',0,'Y','Y','N','N','N','N','Y','Y','Zu berechn. Menge abw. erl.',750,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541459) 
;

-- 2020-10-07T08:46:22.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622332
;

-- 2020-10-07T08:46:22.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622332)
;

-- 2020-10-07T08:46:22.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622332
;

-- 2020-10-07T08:46:22.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622332, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548191
;

-- 2020-10-07T08:46:22.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545847,622333,0,543052,190,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt es, eine abweichende Rechnungsstellungsregel vorzugeben.',1,'de.metas.invoicecandidate','"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält. Über die abweichende Rechnungsstellung kann ein Rechnungskandidat z.B. vorab schon in Rechnung gestellt werden, obwohl dies aufgrund der Rechnungsstellungsregel des Auftragskopfes noch nicht vorgesehen war.',0,'Y','Y','N','N','N','N','N','Y','Rechnungsstellung abw.',400,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541461) 
;

-- 2020-10-07T08:46:22.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622333
;

-- 2020-10-07T08:46:22.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622333)
;

-- 2020-10-07T08:46:22.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622333
;

-- 2020-10-07T08:46:22.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622333, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548192
;

-- 2020-10-07T08:46:22.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545852,622334,0,543052,88,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'de.metas.invoicecandidate','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.',0,'Y','Y','Y','N','N','N','Y','N','Preissystem',290,330,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135) 
;

-- 2020-10-07T08:46:22.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622334
;

-- 2020-10-07T08:46:22.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622334)
;

-- 2020-10-07T08:46:22.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622334
;

-- 2020-10-07T08:46:22.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622334, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548198
;

-- 2020-10-07T08:46:22.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545854,622335,0,543052,214,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde',10,'de.metas.invoicecandidate','Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.',0,'Y','Y','N','N','N','N','Y','Y','Abgerechneter Betrag',860,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541464) 
;

-- 2020-10-07T08:46:22.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622335
;

-- 2020-10-07T08:46:22.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622335)
;

-- 2020-10-07T08:46:22.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622335
;

-- 2020-10-07T08:46:22.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622335, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548199
;

-- 2020-10-07T08:46:22.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545891,540067,622336,0,543052,88,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz',10,'@IsToClear/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Aggregator',910,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541470) 
;

-- 2020-10-07T08:46:22.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622336
;

-- 2020-10-07T08:46:22.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622336)
;

-- 2020-10-07T08:46:22.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622336
;

-- 2020-10-07T08:46:22.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622336, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548259
;

-- 2020-10-07T08:46:22.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545892,622337,0,543052,250,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.',10,'@IsToClear/''N''@=''N''','de.metas.invoicecandidate','Hinweis: Das Feld ist nur Relevant, wenn der Rechnungskandidat nicht als "zu Verrechnen" markiert ist.<p>
Das Aggregierungsmerkmal wird während der RK-Aktualisierung vom System gesetzt.
Unterschiedliche Rechnungskandidaten werden zu einer Rechnungszeile zusammengefasst, wenn folgende Bedingungen erfüllt sind:
<ul>
<li>Die Kandidaten gehören zu selben Rechnung (d.h. Rechnungsempfänger, Währung usw. sind gleich)</li>
<li>Die Kandidaten haben das gleiche Merkmal</li>
</ul>',0,'Y','Y','N','N','N','N','Y','N','Zeilen-Aggregationsmerkmal',930,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:22.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:22.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541474) 
;

-- 2020-10-07T08:46:22.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622337
;

-- 2020-10-07T08:46:22.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622337)
;

-- 2020-10-07T08:46:22.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622337
;

-- 2020-10-07T08:46:22.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622337, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548260
;

-- 2020-10-07T08:46:23.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545893,622338,0,543052,224,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100,'Optionale Möglichkeit, einzelne Rechnungskandidaten aus einer gemeinsamen Aggregations-Gruppe herauszulösen.',20,'@IsToClear/''N''@=''N''','de.metas.invoicecandidate','Hinweis: Das Feld ist nur Relevant, wenn der Rechnungskandidat nicht als "zu Verrechnen" markiert ist.<p>
Der Aggregierungs-Zusatz wird bei der Rechnungsgenerierung an das vom System ermittelte Aggregierungsmerkmal angehangen. Somit kann der Bnutzer dafür sorgen, dass ein Rechnungskandidat abweichend von der Systemvorgabe in einer eigenen Rechnungszeile verarbeitet wird, anstatt ihn mit anderen Rechnungskandidaten zusammenzufassen.',0,'Y','Y','N','N','N','N','N','Y','Aggregations-Zusatz',940,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541475) 
;

-- 2020-10-07T08:46:23.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622338
;

-- 2020-10-07T08:46:23.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622338)
;

-- 2020-10-07T08:46:23.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622338
;

-- 2020-10-07T08:46:23.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622338, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548261
;

-- 2020-10-07T08:46:23.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546178,622339,0,543052,84,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',20,'@IsManual/''N''@=''N''','de.metas.invoicecandidate','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','N','N','N','N','Y','Y','Datensatz-ID',120,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2020-10-07T08:46:23.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622339
;

-- 2020-10-07T08:46:23.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622339)
;

-- 2020-10-07T08:46:23.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622339
;

-- 2020-10-07T08:46:23.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622339, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548719
;

-- 2020-10-07T08:46:23.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546177,622340,0,543052,83,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'@IsManual/''N''@=''N''','de.metas.invoicecandidate','The Database Table provides the information of the table definition',0,'Y','Y','N','N','N','N','Y','N','DB-Tabelle',110,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622340 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2020-10-07T08:46:23.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622340
;

-- 2020-10-07T08:46:23.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622340)
;

-- 2020-10-07T08:46:23.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622340
;

-- 2020-10-07T08:46:23.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622340, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548720
;

-- 2020-10-07T08:46:23.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546227,622341,0,543052,'select case when ''@IsError@''= ''Y''  then(select ad_color_id from ad_color where name = ''Orange'') end as ad_color_id',53,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten',1,'','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Fehler',970,300,1,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395) 
;

-- 2020-10-07T08:46:23.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622341
;

-- 2020-10-07T08:46:23.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622341)
;

-- 2020-10-07T08:46:23.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622341
;

-- 2020-10-07T08:46:23.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622341, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548749
;

-- 2020-10-07T08:46:23.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546203,622342,0,543052,250,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Rechnungskandidaten-Controller',100,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541582) 
;

-- 2020-10-07T08:46:23.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622342
;

-- 2020-10-07T08:46:23.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622342)
;

-- 2020-10-07T08:46:23.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622342
;

-- 2020-10-07T08:46:23.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622342, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548750
;

-- 2020-10-07T08:46:23.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546229,622343,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,'System-Nachricht',100,'@IsError/''N''@=''Y''','de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','Statusmeldung',1030,0,0,999,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1348) 
;

-- 2020-10-07T08:46:23.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622343
;

-- 2020-10-07T08:46:23.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622343)
;

-- 2020-10-07T08:46:23.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622343
;

-- 2020-10-07T08:46:23.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622343, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548751
;

-- 2020-10-07T08:46:23.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546339,622344,0,543052,113,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Termin ab dem die Rechnung erstellt werden darf',7,'de.metas.invoicecandidate','"Abrechnung ab" beschreibt das Datum an dem der Rechnungskandidat abgerechnet werden kann. Dieses Datum entspricht i.d.R. dem Erstellungsdatum des Rechnungskandidaten, kann aber abweichen wenn beim Geschäftspartner ein Terminplan für Rechnungen hinterlegt wurde.',0,'Y','Y','N','N','N','N','Y','N','Abrechnung ab',440,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:23.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:23.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541603) 
;

-- 2020-10-07T08:46:23.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622344
;

-- 2020-10-07T08:46:23.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622344)
;

-- 2020-10-07T08:46:23.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622344
;

-- 2020-10-07T08:46:23.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622344, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548972
;

-- 2020-10-07T08:46:24.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546340,622345,0,543052,146,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Überschreibt den regulären Termin ab dem die Rechnung erstellt werden darf',7,'de.metas.invoicecandidate','"Abrechnung ab abw." Überschreibt das Feld "Abrechnung ab" und damit das Datum an dem der Rechnungskandidat abgerechnet werden kann. Es dient dazu, bestimmte Rechnungskandidaten vorzeitig abzurechnen. ',0,'Y','Y','N','N','N','N','N','Y','Abrechnung ab abw.',450,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541604) 
;

-- 2020-10-07T08:46:24.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622345
;

-- 2020-10-07T08:46:24.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622345)
;

-- 2020-10-07T08:46:24.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622345
;

-- 2020-10-07T08:46:24.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622345, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548973
;

-- 2020-10-07T08:46:24.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546489,622346,0,543052,136,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,100,'@IsError/''N''@=''Y''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Fehlermeldung',1030,0,0,999,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1021) 
;

-- 2020-10-07T08:46:24.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622346
;

-- 2020-10-07T08:46:24.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622346)
;

-- 2020-10-07T08:46:24.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622346
;

-- 2020-10-07T08:46:24.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622346, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549596
;

-- 2020-10-07T08:46:24.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546601,622347,1000525,0,543052,62,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,'Kursart',10,'de.metas.invoicecandidate','Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.',0,'Y','Y','N','N','N','N','Y','Y','Kursart',320,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622347 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000525) 
;

-- 2020-10-07T08:46:24.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622347
;

-- 2020-10-07T08:46:24.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622347)
;

-- 2020-10-07T08:46:24.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622347
;

-- 2020-10-07T08:46:24.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622347, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549712
;

-- 2020-10-07T08:46:24.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546602,622348,0,543052,'select case 	when ''@InvoiceScheduleAmtStatus@''=''OK'' 	then (select ad_color_id from ad_color where name = ''Gruen'') 	else (select ad_color_id from ad_color where name = ''Rot'') end',113,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,'Bei Geschätspartnern, deren Rechnungs-Terminplan einen Grenzbetrag hat, zeigt dieses Feld an, ob der Grenzbetrag unterschritten ist.',512,'@InvoiceScheduleAmtStatus/@!''OK''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Status Terminplan',430,0,999,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541683) 
;

-- 2020-10-07T08:46:24.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622348
;

-- 2020-10-07T08:46:24.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622348)
;

-- 2020-10-07T08:46:24.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622348
;

-- 2020-10-07T08:46:24.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622348, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549713
;

-- 2020-10-07T08:46:24.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546600,622349,0,543052,135,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung',10,'de.metas.invoicecandidate','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.',0,'Y','Y','N','N','N','N','Y','N','Terminplan Rechnung',420,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(560) 
;

-- 2020-10-07T08:46:24.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622349
;

-- 2020-10-07T08:46:24.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622349)
;

-- 2020-10-07T08:46:24.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622349
;

-- 2020-10-07T08:46:24.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622349, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549714
;

-- 2020-10-07T08:46:24.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546607,622350,0,543052,'select case when ''@DateToInvoice_Effective@'' > ''@#Date@'' then(select ad_color_id from ad_color where name = ''Orange'')  end as ad_color_id
',65,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,'Enthält den Wert aus "Abrechnung ab" oder der Überschreibe-Wert aus "Abrechnung ab abw.',7,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Abrechnung ab eff.',460,260,-1,1,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541688) 
;

-- 2020-10-07T08:46:24.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622350
;

-- 2020-10-07T08:46:24.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622350)
;

-- 2020-10-07T08:46:24.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622350
;

-- 2020-10-07T08:46:24.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622350, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549735
;

-- 2020-10-07T08:46:24.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546639,622351,0,543052,194,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Rechnungsstellung eff.',410,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:24.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:24.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541693) 
;

-- 2020-10-07T08:46:24.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622351
;

-- 2020-10-07T08:46:24.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622351)
;

-- 2020-10-07T08:46:24.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622351
;

-- 2020-10-07T08:46:24.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622351, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549754
;

-- 2020-10-07T08:46:25.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547832,622352,1001390,0,543052,112,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein manueller Vorgang',0,'de.metas.invoicecandidate','Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorang manuell durchgefĂĽhrt wird.',0,'Y','Y','N','N','N','N','Y','Y','Manuell',50,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001390) 
;

-- 2020-10-07T08:46:25.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622352
;

-- 2020-10-07T08:46:25.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622352)
;

-- 2020-10-07T08:46:25.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622352
;

-- 2020-10-07T08:46:25.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622352, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551089
;

-- 2020-10-07T08:46:25.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548015,622353,0,543052,89,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,100,'@IsManual/''N''@=''Y''|@Description/@!''''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','N','Beschreibung',130,0,0,999,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2020-10-07T08:46:25.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622353
;

-- 2020-10-07T08:46:25.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622353)
;

-- 2020-10-07T08:46:25.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622353
;

-- 2020-10-07T08:46:25.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622353, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551170
;

-- 2020-10-07T08:46:25.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548490,101,622354,0,543052,72,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.',10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','N','Betreuer',950,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541510) 
;

-- 2020-10-07T08:46:25.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622354
;

-- 2020-10-07T08:46:25.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622354)
;

-- 2020-10-07T08:46:25.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622354
;

-- 2020-10-07T08:46:25.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622354, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551579
;

-- 2020-10-07T08:46:25.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548618,622355,1000088,0,543052,462,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsToClear/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Kopf-Aggregationsmerkmal',920,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000088) 
;

-- 2020-10-07T08:46:25.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622355
;

-- 2020-10-07T08:46:25.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622355)
;

-- 2020-10-07T08:46:25.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622355
;

-- 2020-10-07T08:46:25.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622355, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551671
;

-- 2020-10-07T08:46:25.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548623,622356,0,543052,87,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Abzurechnender Teilbetrag, der zur Zeit nicht in Rechnung gestellt werden kann und bei einer Faktur dieses Datensatzes in einen neuen Rechnungsdispo-Datensatz übernommen werden wird.',0,'Y','Y','N','N','N','N','Y','Y','Restbetrag',865,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542023) 
;

-- 2020-10-07T08:46:25.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622356
;

-- 2020-10-07T08:46:25.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622356)
;

-- 2020-10-07T08:46:25.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622356
;

-- 2020-10-07T08:46:25.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622356, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551679
;

-- 2020-10-07T08:46:25.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549012,540066,622357,1001828,0,543052,130,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,30,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','N','Kopfbeschreibung',1040,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001828) 
;

-- 2020-10-07T08:46:25.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622357
;

-- 2020-10-07T08:46:25.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622357)
;

-- 2020-10-07T08:46:25.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622357
;

-- 2020-10-07T08:46:25.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622357, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551903
;

-- 2020-10-07T08:46:25.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549013,622358,1000673,0,543052,89,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,30,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','N','Schlusstext',1050,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:25.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:25.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000673) 
;

-- 2020-10-07T08:46:25.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622358
;

-- 2020-10-07T08:46:25.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622358)
;

-- 2020-10-07T08:46:25.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622358
;

-- 2020-10-07T08:46:25.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622358, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551904
;

-- 2020-10-07T08:46:26.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549690,540074,622359,1001525,0,543052,50,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100,'Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit',0,'de.metas.invoicecandidate','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet',0,'Y','Y','Y','N','N','N','Y','N','Preis',570,160,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001525) 
;

-- 2020-10-07T08:46:26.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622359
;

-- 2020-10-07T08:46:26.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622359)
;

-- 2020-10-07T08:46:26.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622359
;

-- 2020-10-07T08:46:26.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622359, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552878
;

-- 2020-10-07T08:46:26.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549748,622360,0,543052,58,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet',0,'Y','Y','Y','N','N','N','N','Y','Preis Abw.',580,180,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542229) 
;

-- 2020-10-07T08:46:26.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622360
;

-- 2020-10-07T08:46:26.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622360)
;

-- 2020-10-07T08:46:26.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622360
;

-- 2020-10-07T08:46:26.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622360, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552879
;

-- 2020-10-07T08:46:26.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549847,622361,0,543052,44,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufstransaktion',1,'de.metas.invoicecandidate','The Sales Transaction checkbox indicates if this item is a Sales Transaction.',0,'Y','Y','Y','N','N','N','Y','N','Verkaufstransaktion',40,240,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622361 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2020-10-07T08:46:26.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622361
;

-- 2020-10-07T08:46:26.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622361)
;

-- 2020-10-07T08:46:26.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622361
;

-- 2020-10-07T08:46:26.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622361, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553181
;

-- 2020-10-07T08:46:26.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549869,622362,0,543052,'select case when @QualityDiscountPercent_ReceiptSchedule@>0 then(select ad_color_id from ad_color where name = ''Rot'')  end as ad_color_id
  ',0,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','Y','Qualitätsabzug % (WED)',1060,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542263) 
;

-- 2020-10-07T08:46:26.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622362
;

-- 2020-10-07T08:46:26.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622362)
;

-- 2020-10-07T08:46:26.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622362
;

-- 2020-10-07T08:46:26.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622362, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553211
;

-- 2020-10-07T08:46:26.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549870,622363,0,543052,'select case when @QualityDiscountPercent_Effective@> 0 and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Orange'') end as ad_color_id',37,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,3,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Qualitätsabzug % eff.',510,310,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542264) 
;

-- 2020-10-07T08:46:26.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622363
;

-- 2020-10-07T08:46:26.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622363)
;

-- 2020-10-07T08:46:26.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622363
;

-- 2020-10-07T08:46:26.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622363, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553212
;

-- 2020-10-07T08:46:26.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550198,622364,0,543052,123,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,30,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Qualitäts-Notiz (WED)',550,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542323) 
;

-- 2020-10-07T08:46:26.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622364
;

-- 2020-10-07T08:46:26.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622364)
;

-- 2020-10-07T08:46:26.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622364
;

-- 2020-10-07T08:46:26.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622364, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553926
;

-- 2020-10-07T08:46:26.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550513,622365,1002644,0,543052,94,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Qualitätsabzug %',490,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:26.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:26.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002644) 
;

-- 2020-10-07T08:46:26.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622365
;

-- 2020-10-07T08:46:26.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622365)
;

-- 2020-10-07T08:46:26.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622365
;

-- 2020-10-07T08:46:26.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622365, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554060
;

-- 2020-10-07T08:46:27.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550514,622366,0,543052,124,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','Y','Qualitätsabzug % abw.',500,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542413) 
;

-- 2020-10-07T08:46:27.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622366
;

-- 2020-10-07T08:46:27.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622366)
;

-- 2020-10-07T08:46:27.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622366
;

-- 2020-10-07T08:46:27.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622366, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554061
;

-- 2020-10-07T08:46:27.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550516,622367,0,543052,151,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,'Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.',0,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Minderwertige Menge',520,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542414) 
;

-- 2020-10-07T08:46:27.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622367
;

-- 2020-10-07T08:46:27.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622367)
;

-- 2020-10-07T08:46:27.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622367
;

-- 2020-10-07T08:46:27.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622367, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554062
;

-- 2020-10-07T08:46:27.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550515,622368,0,543052,'select case when ''@IsInDispute@''= ''Y'' and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Orange'') end as ad_color_id',39,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.invoicecandidate','',0,'Y','Y','Y','N','N','N','N','Y','Strittig',530,280,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2543) 
;

-- 2020-10-07T08:46:27.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622368
;

-- 2020-10-07T08:46:27.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622368)
;

-- 2020-10-07T08:46:27.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622368
;

-- 2020-10-07T08:46:27.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622368, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554063
;

-- 2020-10-07T08:46:27.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550575,622369,0,543052,145,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','QtyOrderedOverUnder',380,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542410) 
;

-- 2020-10-07T08:46:27.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622369
;

-- 2020-10-07T08:46:27.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622369)
;

-- 2020-10-07T08:46:27.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622369
;

-- 2020-10-07T08:46:27.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622369, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554131
;

-- 2020-10-07T08:46:27.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550576,622370,0,543052,110,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,30,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','N','ReasonDiscount',560,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542432) 
;

-- 2020-10-07T08:46:27.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622370
;

-- 2020-10-07T08:46:27.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622370)
;

-- 2020-10-07T08:46:27.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622370
;

-- 2020-10-07T08:46:27.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622370, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554132
;

-- 2020-10-07T08:46:27.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550629,622371,576970,0,543052,38,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Abrechnungseinheit',356,230,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576970) 
;

-- 2020-10-07T08:46:27.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622371
;

-- 2020-10-07T08:46:27.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622371)
;

-- 2020-10-07T08:46:27.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622371
;

-- 2020-10-07T08:46:27.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622371, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554200
;

-- 2020-10-07T08:46:27.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550848,622372,1002455,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Preiseinheit',780,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622372 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002455) 
;

-- 2020-10-07T08:46:27.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622372
;

-- 2020-10-07T08:46:27.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622372)
;

-- 2020-10-07T08:46:27.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622372
;

-- 2020-10-07T08:46:27.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622372, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554361
;

-- 2020-10-07T08:46:27.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551072,622373,0,543052,110,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',10,'de.metas.invoicecandidate','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','N','N','N','N','Y','N','Kostenstelle',240,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:27.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:27.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2020-10-07T08:46:27.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622373
;

-- 2020-10-07T08:46:27.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622373)
;

-- 2020-10-07T08:46:27.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622373
;

-- 2020-10-07T08:46:27.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622373, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554661
;

-- 2020-10-07T08:46:28.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551073,622374,0,543052,172,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100,'Steuerart',10,'de.metas.invoicecandidate','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.',0,'Y','Y','N','N','N','N','Y','N','Steuer',330,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(213) 
;

-- 2020-10-07T08:46:28.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622374
;

-- 2020-10-07T08:46:28.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622374)
;

-- 2020-10-07T08:46:28.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622374
;

-- 2020-10-07T08:46:28.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622374, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554662
;

-- 2020-10-07T08:46:28.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551281,622375,0,543052,76,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document / line is printed',1,'de.metas.invoicecandidate','The Printed checkbox indicates if this document or line will included when printing.',0,'Y','Y','N','N','N','N','Y','N','andrucken',1060,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622375 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(399) 
;

-- 2020-10-07T08:46:28.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622375
;

-- 2020-10-07T08:46:28.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622375)
;

-- 2020-10-07T08:46:28.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622375
;

-- 2020-10-07T08:46:28.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622375, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554837
;

-- 2020-10-07T08:46:28.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551284,622376,0,543052,95,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Total des Auftrags',800,220,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542571) 
;

-- 2020-10-07T08:46:28.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622376
;

-- 2020-10-07T08:46:28.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622376)
;

-- 2020-10-07T08:46:28.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622376
;

-- 2020-10-07T08:46:28.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622376, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554865
;

-- 2020-10-07T08:46:28.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551285,622377,0,543052,73,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument',10,'de.metas.invoicecandidate','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','Y','N','N','N','N','Y','Y','Zeile Nr.',80,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622377 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2020-10-07T08:46:28.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622377
;

-- 2020-10-07T08:46:28.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622377)
;

-- 2020-10-07T08:46:28.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622377
;

-- 2020-10-07T08:46:28.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622377, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554866
;

-- 2020-10-07T08:46:28.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551286,622378,0,543052,179,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for invoices generated from this sales document',10,'de.metas.invoicecandidate','The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.',0,'Y','Y','N','N','N','N','Y','N','Rechnungs-Belegart',90,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1072) 
;

-- 2020-10-07T08:46:28.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622378
;

-- 2020-10-07T08:46:28.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622378)
;

-- 2020-10-07T08:46:28.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622378
;

-- 2020-10-07T08:46:28.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622378, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554867
;

-- 2020-10-07T08:46:28.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551290,622379,0,543052,71,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Material-Vorgang-ID',70,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542532) 
;

-- 2020-10-07T08:46:28.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622379
;

-- 2020-10-07T08:46:28.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622379)
;

-- 2020-10-07T08:46:28.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622379
;

-- 2020-10-07T08:46:28.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622379, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554869
;

-- 2020-10-07T08:46:28.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551293,622380,0,543052,52,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Freigabe zur Fakturierung',830,210,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542572) 
;

-- 2020-10-07T08:46:28.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622380
;

-- 2020-10-07T08:46:28.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622380)
;

-- 2020-10-07T08:46:28.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622380
;

-- 2020-10-07T08:46:28.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622380, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554872
;

-- 2020-10-07T08:46:28.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551338,622381,0,543052,148,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,'Abweichender Steuersatz',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Steuer abw.',340,390,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:28.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622381 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:28.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542590) 
;

-- 2020-10-07T08:46:28.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622381
;

-- 2020-10-07T08:46:28.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622381)
;

-- 2020-10-07T08:46:28.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622381
;

-- 2020-10-07T08:46:28.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622381, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555019
;

-- 2020-10-07T08:46:29.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551339,622382,0,543052,182,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Steuer eff.',350,400,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622382 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542591) 
;

-- 2020-10-07T08:46:29.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622382
;

-- 2020-10-07T08:46:29.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622382)
;

-- 2020-10-07T08:46:29.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622382
;

-- 2020-10-07T08:46:29.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622382, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555020
;

-- 2020-10-07T08:46:29.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551469,622383,0,543052,91,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',7,'de.metas.invoicecandidate','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','Y','N','Referenz',220,380,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622383 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2020-10-07T08:46:29.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622383
;

-- 2020-10-07T08:46:29.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622383)
;

-- 2020-10-07T08:46:29.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622383
;

-- 2020-10-07T08:46:29.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622383, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555142
;

-- 2020-10-07T08:46:29.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551715,622384,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date',0,'de.metas.invoicecandidate','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.',0,'Y','Y','N','N','N','N','Y','Y','Buchungsdatum',480,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622384 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2020-10-07T08:46:29.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622384
;

-- 2020-10-07T08:46:29.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622384)
;

-- 2020-10-07T08:46:29.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622384
;

-- 2020-10-07T08:46:29.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622384, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555397
;

-- 2020-10-07T08:46:29.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551763,622385,0,543052,72,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Auftrag Total ohne Rabatt',810,200,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622385 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542695) 
;

-- 2020-10-07T08:46:29.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622385
;

-- 2020-10-07T08:46:29.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622385)
;

-- 2020-10-07T08:46:29.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622385
;

-- 2020-10-07T08:46:29.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622385, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555407
;

-- 2020-10-07T08:46:29.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551762,622386,1001411,0,543052,91,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,'Erste Lieferung oder Wareneingang.',0,'@IsManual/''N''@=''N''','de.metas.invoicecandidate','The Material Shipment / Receipt ',0,'Y','Y','Y','N','N','N','Y','N','Lieferung/Wareneingang',180,50,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622386 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001411) 
;

-- 2020-10-07T08:46:29.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622386
;

-- 2020-10-07T08:46:29.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622386)
;

-- 2020-10-07T08:46:29.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622386
;

-- 2020-10-07T08:46:29.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622386, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555409
;

-- 2020-10-07T08:46:29.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551761,622387,0,543052,64,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Lieferdatum',210,30,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622387 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376) 
;

-- 2020-10-07T08:46:29.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622387
;

-- 2020-10-07T08:46:29.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622387)
;

-- 2020-10-07T08:46:29.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622387
;

-- 2020-10-07T08:46:29.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622387, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555410
;

-- 2020-10-07T08:46:29.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551765,622388,0,543052,82,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Preis Eff. Netto',640,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622388 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542696) 
;

-- 2020-10-07T08:46:29.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622388
;

-- 2020-10-07T08:46:29.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622388)
;

-- 2020-10-07T08:46:29.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622388
;

-- 2020-10-07T08:46:29.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622388, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555411
;

-- 2020-10-07T08:46:29.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551790,622389,1003024,0,543052,85,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Menge TU',790,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:29.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622389 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:29.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003024) 
;

-- 2020-10-07T08:46:29.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622389
;

-- 2020-10-07T08:46:29.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622389)
;

-- 2020-10-07T08:46:29.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622389
;

-- 2020-10-07T08:46:29.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622389, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555434
;

-- 2020-10-07T08:46:30.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551766,622390,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,'Tax is included in the price ',0,'de.metas.invoicecandidate','The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.',0,'Y','N','N','N','N','N','Y','N','Preis inklusive Steuern',1070,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622390 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1065) 
;

-- 2020-10-07T08:46:30.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622390
;

-- 2020-10-07T08:46:30.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622390)
;

-- 2020-10-07T08:46:30.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622390
;

-- 2020-10-07T08:46:30.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622390, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555435
;

-- 2020-10-07T08:46:30.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551791,622391,0,543052,130,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Tax is included in the price ',0,'de.metas.invoicecandidate','The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.',0,'Y','Y','N','N','N','N','N','Y','Preis inkl. Steuern abw.',590,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622391 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542702) 
;

-- 2020-10-07T08:46:30.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622391
;

-- 2020-10-07T08:46:30.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622391)
;

-- 2020-10-07T08:46:30.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622391
;

-- 2020-10-07T08:46:30.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622391, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555436
;

-- 2020-10-07T08:46:30.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551792,622392,0,543052,85,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren',0,'de.metas.invoicecandidate','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.',0,'Y','Y','N','N','N','N','Y','Y','Zeilennetto',820,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622392 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(441) 
;

-- 2020-10-07T08:46:30.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622392
;

-- 2020-10-07T08:46:30.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622392)
;

-- 2020-10-07T08:46:30.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622392
;

-- 2020-10-07T08:46:30.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622392, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555477
;

-- 2020-10-07T08:46:30.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551794,622393,1002807,0,543052,38,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Lieferung/ Wareneingang freigeben',840,350,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622393 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002807) 
;

-- 2020-10-07T08:46:30.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622393
;

-- 2020-10-07T08:46:30.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622393)
;

-- 2020-10-07T08:46:30.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622393
;

-- 2020-10-07T08:46:30.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622393, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555480
;

-- 2020-10-07T08:46:30.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551796,622394,0,543052,115,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','N','Ist Material-Vorgang',60,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622394 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542704) 
;

-- 2020-10-07T08:46:30.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622394
;

-- 2020-10-07T08:46:30.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622394)
;

-- 2020-10-07T08:46:30.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622394
;

-- 2020-10-07T08:46:30.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622394, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555481
;

-- 2020-10-07T08:46:30.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551813,622395,0,543052,110,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Zu berechn. Menge vor Qualitätsabzug',730,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622395 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542707) 
;

-- 2020-10-07T08:46:30.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622395
;

-- 2020-10-07T08:46:30.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622395)
;

-- 2020-10-07T08:46:30.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622395
;

-- 2020-10-07T08:46:30.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622395, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555542
;

-- 2020-10-07T08:46:30.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551819,622396,0,543052,178,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Liefermenge, die nicht fakturiert wird soll. Der Wert weicht von "Minderwertige Menge" ab, wenn ein abweichender "Qualitätsabzug %" Wert gesetzt wurde.',0,'@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','Y','Minderwertige Menge eff.',540,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622396 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542709) 
;

-- 2020-10-07T08:46:30.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622396
;

-- 2020-10-07T08:46:30.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622396)
;

-- 2020-10-07T08:46:30.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622396
;

-- 2020-10-07T08:46:30.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622396, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555544
;

-- 2020-10-07T08:46:30.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551835,622397,0,543052,'select case  when ''@IsError@''= ''Y'' then(select ad_color_id from ad_color where name = ''Rot'')  when @NetAmtToInvoice@= 0 then(select ad_color_id from ad_color where name = ''Rot'')  when ''@IsToRecompute@''= ''Y'' then(select ad_color_id from ad_color where name = ''Rot'')  when ''@IsToClear@''= ''Y'' then(select ad_color_id from ad_color where name = ''Rot'') when @PriceActual@= 0 then(select ad_color_id from ad_color where name = ''Rot'') when ''@IsInDispute@''= ''Y'' and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Rot'') when @QualityDiscountPercent@> 0 and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Rot'') end as ad_color_id',0,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','Y','Abrechnungsgruppe',1080,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:30.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622397 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:30.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542710) 
;

-- 2020-10-07T08:46:30.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622397
;

-- 2020-10-07T08:46:30.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622397)
;

-- 2020-10-07T08:46:30.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622397
;

-- 2020-10-07T08:46:30.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622397, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555546
;

-- 2020-10-07T08:46:31.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551836,622398,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','Y','Abrechnungsgruppe abw.',1090,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622398 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542712) 
;

-- 2020-10-07T08:46:31.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622398
;

-- 2020-10-07T08:46:31.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622398)
;

-- 2020-10-07T08:46:31.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622398
;

-- 2020-10-07T08:46:31.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622398, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555547
;

-- 2020-10-07T08:46:31.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551837,622399,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,2000,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Kopf-Aggregationsmerkmal (vorgegeben)',1100,0,999,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622399 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542713) 
;

-- 2020-10-07T08:46:31.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622399
;

-- 2020-10-07T08:46:31.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622399)
;

-- 2020-10-07T08:46:31.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622399
;

-- 2020-10-07T08:46:31.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622399, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555548
;

-- 2020-10-07T08:46:31.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColorLogic,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551838,622400,0,543052,'select case  when ''@IsError@''= ''Y'' then(select ad_color_id from ad_color where name = ''Rot'')  when ''@IsToRecompute@''= ''Y'' then(select ad_color_id from ad_color where name = ''Rot'')  when ''@IsToClear@''= ''Y'' then(select ad_color_id from ad_color where name = ''Rot'') when ''@IsInDispute@''= ''Y'' and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Rot'') when @QualityDiscountPercent_Effective@> 0 and ''@Processed@''=''N'' then(select ad_color_id from ad_color where name = ''Rot'') when ''@DateToInvoice_Effective@'' > ''@#Date@'' then(select ad_color_id from ad_color where name = ''Rot'') end as ad_color_id',43,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Abrechnungsgruppe eff.',30,10,1,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622400 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542714) 
;

-- 2020-10-07T08:46:31.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622400
;

-- 2020-10-07T08:46:31.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622400)
;

-- 2020-10-07T08:46:31.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622400
;

-- 2020-10-07T08:46:31.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622400, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555549
;

-- 2020-10-07T08:46:31.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551896,622401,0,543052,78,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,'BParter location of first shipment/receipt',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','First ship location',200,60,1,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622401 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542728) 
;

-- 2020-10-07T08:46:31.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622401
;

-- 2020-10-07T08:46:31.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622401)
;

-- 2020-10-07T08:46:31.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622401
;

-- 2020-10-07T08:46:31.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622401, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555636
;

-- 2020-10-07T08:46:31.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551935,622402,0,543052,30,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Verarbeitet abw.',1000,410,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622402 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542755) 
;

-- 2020-10-07T08:46:31.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622402
;

-- 2020-10-07T08:46:31.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622402)
;

-- 2020-10-07T08:46:31.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622402
;

-- 2020-10-07T08:46:31.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622402, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555693
;

-- 2020-10-07T08:46:31.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551936,622403,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','Y','N','Verarbeitet (System)',990,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622403 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542756) 
;

-- 2020-10-07T08:46:31.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622403
;

-- 2020-10-07T08:46:31.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622403)
;

-- 2020-10-07T08:46:31.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622403
;

-- 2020-10-07T08:46:31.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622403, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555694
;

-- 2020-10-07T08:46:31.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551953,622404,0,543052,104,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,'The partner from C_Order',0,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','Y','Auftragspartner',230,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:31.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622404 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:31.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542759) 
;

-- 2020-10-07T08:46:31.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622404
;

-- 2020-10-07T08:46:31.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622404)
;

-- 2020-10-07T08:46:31.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622404
;

-- 2020-10-07T08:46:31.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622404, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555715
;

-- 2020-10-07T08:46:32.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552288,622405,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt',0,'de.metas.invoicecandidate','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.',0,'Y','Y','N','N','N','N','N','Y','Produktart',280,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622405 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1899) 
;

-- 2020-10-07T08:46:32.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622405
;

-- 2020-10-07T08:46:32.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622405)
;

-- 2020-10-07T08:46:32.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622405
;

-- 2020-10-07T08:46:32.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622405, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555849
;

-- 2020-10-07T08:46:32.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552287,540070,622406,0,543052,74,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Name Rechnungspartner',650,290,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622406 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542805) 
;

-- 2020-10-07T08:46:32.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622406
;

-- 2020-10-07T08:46:32.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622406)
;

-- 2020-10-07T08:46:32.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622406
;

-- 2020-10-07T08:46:32.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622406, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555850
;

-- 2020-10-07T08:46:32.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552289,622407,0,543052,74,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes',0,'de.metas.invoicecandidate','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','Y','Y','N','N','N','N','Y','Produkt Kategorie',270,110,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622407 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2020-10-07T08:46:32.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622407
;

-- 2020-10-07T08:46:32.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622407)
;

-- 2020-10-07T08:46:32.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622407
;

-- 2020-10-07T08:46:32.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622407, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555851
;

-- 2020-10-07T08:46:32.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552023,622408,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','Y','Process Now',980,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622408 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2020-10-07T08:46:32.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622408
;

-- 2020-10-07T08:46:32.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622408)
;

-- 2020-10-07T08:46:32.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622408
;

-- 2020-10-07T08:46:32.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622408, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555869
;

-- 2020-10-07T08:46:32.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552564,622409,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Erhält EDI-INVOIC',660,70,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622409 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542000) 
;

-- 2020-10-07T08:46:32.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622409
;

-- 2020-10-07T08:46:32.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622409)
;

-- 2020-10-07T08:46:32.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622409
;

-- 2020-10-07T08:46:32.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622409, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556203
;

-- 2020-10-07T08:46:32.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552599,622410,1002301,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Beleg soll per EDI übermittelt werden',670,80,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622410 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002301) 
;

-- 2020-10-07T08:46:32.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622410
;

-- 2020-10-07T08:46:32.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622410)
;

-- 2020-10-07T08:46:32.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622410
;

-- 2020-10-07T08:46:32.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622410, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556235
;

-- 2020-10-07T08:46:32.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552844,622411,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',0,'@M_PriceList_Version_ID/0@!0','de.metas.invoicecandidate','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','N','N','N','Y','Y','Version Preisliste',300,360,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622411 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450) 
;

-- 2020-10-07T08:46:32.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622411
;

-- 2020-10-07T08:46:32.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622411)
;

-- 2020-10-07T08:46:32.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622411
;

-- 2020-10-07T08:46:32.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622411, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556415
;

-- 2020-10-07T08:46:32.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554387,622412,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag',0,'de.metas.invoicecandidate','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','Y','Y','N','N','N','N','Y','Zugesagter Termin',190,20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:32.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622412 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:32.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269) 
;

-- 2020-10-07T08:46:33.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622412
;

-- 2020-10-07T08:46:33.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622412)
;

-- 2020-10-07T08:46:33.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622412
;

-- 2020-10-07T08:46:33.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622412, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556919
;

-- 2020-10-07T08:46:33.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557788,622413,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Group Compensation Line',1110,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622413 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543458) 
;

-- 2020-10-07T08:46:33.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622413
;

-- 2020-10-07T08:46:33.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622413)
;

-- 2020-10-07T08:46:33.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622413
;

-- 2020-10-07T08:46:33.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622413, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560416
;

-- 2020-10-07T08:46:33.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557790,622414,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Compensation Amount Type',1120,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622414 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543462) 
;

-- 2020-10-07T08:46:33.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622414
;

-- 2020-10-07T08:46:33.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622414)
;

-- 2020-10-07T08:46:33.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622414
;

-- 2020-10-07T08:46:33.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622414, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560417
;

-- 2020-10-07T08:46:33.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557792,622415,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,'Base amount for calculating percentage group compensation',10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Compensation base amount',1130,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622415 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543464) 
;

-- 2020-10-07T08:46:33.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622415
;

-- 2020-10-07T08:46:33.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622415)
;

-- 2020-10-07T08:46:33.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622415
;

-- 2020-10-07T08:46:33.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622415, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560418
;

-- 2020-10-07T08:46:33.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557791,622416,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Compensation percentage',1140,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622416 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543460) 
;

-- 2020-10-07T08:46:33.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622416
;

-- 2020-10-07T08:46:33.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622416)
;

-- 2020-10-07T08:46:33.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622416
;

-- 2020-10-07T08:46:33.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622416, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560419
;

-- 2020-10-07T08:46:33.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557789,622417,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Compensation Type',1150,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622417 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543461) 
;

-- 2020-10-07T08:46:33.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622417
;

-- 2020-10-07T08:46:33.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622417)
;

-- 2020-10-07T08:46:33.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622417
;

-- 2020-10-07T08:46:33.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622417, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560420
;

-- 2020-10-07T08:46:33.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557794,622418,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Order Compensation Group',1160,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622418 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543469) 
;

-- 2020-10-07T08:46:33.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622418
;

-- 2020-10-07T08:46:33.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622418)
;

-- 2020-10-07T08:46:33.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622418
;

-- 2020-10-07T08:46:33.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622418, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560421
;

-- 2020-10-07T08:46:33.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558578,622419,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',0,'de.metas.invoicecandidate','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','N','N','N','N','Y','N','Zahlungsbedingung',870,0,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:33.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622419 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:33.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2020-10-07T08:46:33.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622419
;

-- 2020-10-07T08:46:33.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622419)
;

-- 2020-10-07T08:46:33.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622419
;

-- 2020-10-07T08:46:33.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622419, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 561480
;

-- 2020-10-07T08:46:34.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551877,622420,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Header aggregation builder',1170,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622420 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542723) 
;

-- 2020-10-07T08:46:34.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622420
;

-- 2020-10-07T08:46:34.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622420)
;

-- 2020-10-07T08:46:34.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622420
;

-- 2020-10-07T08:46:34.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622420, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563182
;

-- 2020-10-07T08:46:34.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552549,622421,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Line aggregation builder',1180,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622421 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542850) 
;

-- 2020-10-07T08:46:34.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622421
;

-- 2020-10-07T08:46:34.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622421)
;

-- 2020-10-07T08:46:34.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622421
;

-- 2020-10-07T08:46:34.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622421, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563183
;

-- 2020-10-07T08:46:34.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552552,622422,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,1,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Verpackungsmaterial',1190,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622422 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542246) 
;

-- 2020-10-07T08:46:34.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622422
;

-- 2020-10-07T08:46:34.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622422)
;

-- 2020-10-07T08:46:34.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622422
;

-- 2020-10-07T08:46:34.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622422, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563184
;

-- 2020-10-07T08:46:34.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552866,622423,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Rechnungspositionsart',1200,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622423 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542925) 
;

-- 2020-10-07T08:46:34.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622423
;

-- 2020-10-07T08:46:34.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622423)
;

-- 2020-10-07T08:46:34.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622423
;

-- 2020-10-07T08:46:34.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622423, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563185
;

-- 2020-10-07T08:46:34.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557793,622424,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.',1,'de.metas.invoicecandidate','The Priority indicates the importance of this request.',0,'Y','N','N','N','N','N','N','N','Priority',1210,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622424 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1514) 
;

-- 2020-10-07T08:46:34.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622424
;

-- 2020-10-07T08:46:34.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622424)
;

-- 2020-10-07T08:46:34.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622424
;

-- 2020-10-07T08:46:34.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622424, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563186
;

-- 2020-10-07T08:46:34.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559650,622425,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',10,'de.metas.invoicecandidate','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','N','N','N','N','N','Y','Zahlungsbedingung abw.',880,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622425 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543766) 
;

-- 2020-10-07T08:46:34.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622425
;

-- 2020-10-07T08:46:34.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622425)
;

-- 2020-10-07T08:46:34.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622425
;

-- 2020-10-07T08:46:34.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622425, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563188
;

-- 2020-10-07T08:46:34.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559651,622426,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','N','N','N','N','N','Y','Zahlungsbedingung eff.',890,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622426 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543949) 
;

-- 2020-10-07T08:46:34.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622426
;

-- 2020-10-07T08:46:34.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622426)
;

-- 2020-10-07T08:46:34.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622426
;

-- 2020-10-07T08:46:34.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622426, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563189
;

-- 2020-10-07T08:46:34.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560992,622427,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',0,'de.metas.invoicecandidate','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','N','N','N','N','N','N','N','Notiz',1070,420,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:34.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622427 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:34.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2020-10-07T08:46:34.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622427
;

-- 2020-10-07T08:46:34.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622427)
;

-- 2020-10-07T08:46:34.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622427
;

-- 2020-10-07T08:46:34.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622427, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568961
;

-- 2020-10-07T08:46:35.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,561008,622428,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Rechungsadresse abw.',1080,430,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622428 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541463) 
;

-- 2020-10-07T08:46:35.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622428
;

-- 2020-10-07T08:46:35.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622428)
;

-- 2020-10-07T08:46:35.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622428
;

-- 2020-10-07T08:46:35.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622428, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568973
;

-- 2020-10-07T08:46:35.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,561009,622429,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Rechnungskontakt abw.',1090,440,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622429 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544258) 
;

-- 2020-10-07T08:46:35.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622429
;

-- 2020-10-07T08:46:35.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622429)
;

-- 2020-10-07T08:46:35.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622429
;

-- 2020-10-07T08:46:35.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622429, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568974
;

-- 2020-10-07T08:46:35.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,567900,622430,576695,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,'Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Beauftragt',354,450,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622430 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576695) 
;

-- 2020-10-07T08:46:35.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622430
;

-- 2020-10-07T08:46:35.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622430)
;

-- 2020-10-07T08:46:35.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622430
;

-- 2020-10-07T08:46:35.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622430, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 580154
;

-- 2020-10-07T08:46:35.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568427,622431,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,1,'@IsManual/''N''@=''N''','de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','IsFreightCost',1220,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622431 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576917) 
;

-- 2020-10-07T08:46:35.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622431
;

-- 2020-10-07T08:46:35.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622431)
;

-- 2020-10-07T08:46:35.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622431
;

-- 2020-10-07T08:46:35.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622431, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582480
;

-- 2020-10-07T08:46:35.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568475,622432,576945,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,'Effektiv zugrunde zu legende gelieferte Menge; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Geliefert',1230,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622432 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576945) 
;

-- 2020-10-07T08:46:35.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622432
;

-- 2020-10-07T08:46:35.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622432)
;

-- 2020-10-07T08:46:35.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622432
;

-- 2020-10-07T08:46:35.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622432, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582482
;

-- 2020-10-07T08:46:35.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568478,622433,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',11,'','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Abr. Menge basiert auf',1240,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622433 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948) 
;

-- 2020-10-07T08:46:35.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622433
;

-- 2020-10-07T08:46:35.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622433)
;

-- 2020-10-07T08:46:35.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622433
;

-- 2020-10-07T08:46:35.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622433, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582483
;

-- 2020-10-07T08:46:35.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568519,622434,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Lagereinheit',1100,460,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622434 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576969) 
;

-- 2020-10-07T08:46:35.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622434
;

-- 2020-10-07T08:46:35.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622434)
;

-- 2020-10-07T08:46:35.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622434
;

-- 2020-10-07T08:46:35.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622434, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582496
;

-- 2020-10-07T08:46:35.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568520,622435,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Abgerechnet',1110,470,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:35.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622435 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:35.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576971) 
;

-- 2020-10-07T08:46:35.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622435
;

-- 2020-10-07T08:46:35.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622435)
;

-- 2020-10-07T08:46:35.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622435
;

-- 2020-10-07T08:46:35.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622435, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582497
;

-- 2020-10-07T08:46:36.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568521,622436,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Abzurechnen',1120,480,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622436 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576975) 
;

-- 2020-10-07T08:46:36.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622436
;

-- 2020-10-07T08:46:36.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622436)
;

-- 2020-10-07T08:46:36.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622436
;

-- 2020-10-07T08:46:36.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622436, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582498
;

-- 2020-10-07T08:46:36.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568528,622437,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.',10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Zu berechn. Menge In Preiseinheit',1250,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622437 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542529) 
;

-- 2020-10-07T08:46:36.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622437
;

-- 2020-10-07T08:46:36.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622437)
;

-- 2020-10-07T08:46:36.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622437
;

-- 2020-10-07T08:46:36.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622437, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582510
;

-- 2020-10-07T08:46:36.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568541,622438,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Abzurechnen eff.',1260,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622438 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576983) 
;

-- 2020-10-07T08:46:36.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622438
;

-- 2020-10-07T08:46:36.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622438)
;

-- 2020-10-07T08:46:36.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622438
;

-- 2020-10-07T08:46:36.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622438, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582550
;

-- 2020-10-07T08:46:36.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568740,622439,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','Vorbelegtes Rechnungsdatum',1270,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622439 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577060) 
;

-- 2020-10-07T08:46:36.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622439
;

-- 2020-10-07T08:46:36.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622439)
;

-- 2020-10-07T08:46:36.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622439
;

-- 2020-10-07T08:46:36.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622439, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 586112
;

-- 2020-10-07T08:46:36.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568741,622440,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,0,'@C_BPartner_SalesRep_ID/0@!0','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Zugeordneter Vertriebspartner',1130,490,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622440 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357) 
;

-- 2020-10-07T08:46:36.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622440
;

-- 2020-10-07T08:46:36.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622440)
;

-- 2020-10-07T08:46:36.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622440
;

-- 2020-10-07T08:46:36.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622440, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 586848
;

-- 2020-10-07T08:46:36.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569216,622441,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Externe Datensatz-Kopf-ID',1140,500,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622441 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575915) 
;

-- 2020-10-07T08:46:36.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622441
;

-- 2020-10-07T08:46:36.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622441)
;

-- 2020-10-07T08:46:36.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622441
;

-- 2020-10-07T08:46:36.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622441, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591336
;

-- 2020-10-07T08:46:36.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569217,622442,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Externe Datensatz-Zeilen-ID',1150,510,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622442 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575914) 
;

-- 2020-10-07T08:46:36.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622442
;

-- 2020-10-07T08:46:36.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622442)
;

-- 2020-10-07T08:46:36.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622442
;

-- 2020-10-07T08:46:36.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622442, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591337
;

-- 2020-10-07T08:46:36.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570844,622443,0,543052,0,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSimulation/''N''@=''Y''','de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Planspiel',1160,520,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:36.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622443 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:36.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541495) 
;

-- 2020-10-07T08:46:36.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622443
;

-- 2020-10-07T08:46:36.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622443)
;

-- 2020-10-07T08:46:36.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622443
;

-- 2020-10-07T08:46:36.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622443, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 614579
;

-- 2020-10-07T08:46:37.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,DisplayLogic,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,545705,572517,0,543053,540314,540983,'Y',TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100,'Enthält Referenzen und Informationen zur Verrechnung, sofern der jeweilige Rechnungskandidaten, als "zu verrechnen" Markiert ist.','@IsToClear@=''Y''','de.metas.invoicecandidate','N','N','C_Invoice_Clearing_Alloc','Y','N','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Zuordnung - Pauschale',544906,'N',20,1,TO_TIMESTAMP('2020-10-07 11:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:37.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543053 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-10-07T08:46:37.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572517) 
;

-- 2020-10-07T08:46:37.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543053)
;

-- 2020-10-07T08:46:37.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 543053
;

-- 2020-10-07T08:46:37.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 543053, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540338
;

-- 2020-10-07T08:46:37.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545699,622444,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:37.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622444 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:37.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-10-07T08:46:37.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622444
;

-- 2020-10-07T08:46:37.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622444)
;

-- 2020-10-07T08:46:37.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622444
;

-- 2020-10-07T08:46:37.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622444, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548068
;

-- 2020-10-07T08:46:37.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545700,622445,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.invoicecandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:37.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622445 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:37.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2020-10-07T08:46:37.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622445
;

-- 2020-10-07T08:46:37.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622445)
;

-- 2020-10-07T08:46:37.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622445
;

-- 2020-10-07T08:46:37.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622445, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548069
;

-- 2020-10-07T08:46:37.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545701,622446,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.invoicecandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',30,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:37.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622446 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:37.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2020-10-07T08:46:37.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622446
;

-- 2020-10-07T08:46:37.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622446)
;

-- 2020-10-07T08:46:37.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622446
;

-- 2020-10-07T08:46:37.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622446, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548070
;

-- 2020-10-07T08:46:37.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545697,622447,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.invoicecandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',40,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:37.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622447 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:37.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2020-10-07T08:46:37.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622447
;

-- 2020-10-07T08:46:37.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622447)
;

-- 2020-10-07T08:46:37.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622447
;

-- 2020-10-07T08:46:37.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622447, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548071
;

-- 2020-10-07T08:46:37.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545698,622448,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.invoicecandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',50,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:37.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622448 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:37.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2020-10-07T08:46:37.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622448
;

-- 2020-10-07T08:46:37.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622448)
;

-- 2020-10-07T08:46:37.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622448
;

-- 2020-10-07T08:46:37.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622448, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548072
;

-- 2020-10-07T08:46:38.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545704,622449,1002114,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Rechnungskandidat',40,40,1,1,TO_TIMESTAMP('2020-10-07 11:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:38.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622449 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:38.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002114) 
;

-- 2020-10-07T08:46:38.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622449
;

-- 2020-10-07T08:46:38.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622449)
;

-- 2020-10-07T08:46:38.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622449
;

-- 2020-10-07T08:46:38.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622449, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548073
;

-- 2020-10-07T08:46:38.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545695,622450,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.invoicecandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',60,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:38.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622450 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:38.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-10-07T08:46:38.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622450
;

-- 2020-10-07T08:46:38.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622450)
;

-- 2020-10-07T08:46:38.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622450
;

-- 2020-10-07T08:46:38.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622450, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548074
;

-- 2020-10-07T08:46:38.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545696,622451,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.invoicecandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',10,10,1,1,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:38.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:38.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-10-07T08:46:38.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622451
;

-- 2020-10-07T08:46:38.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622451)
;

-- 2020-10-07T08:46:38.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622451
;

-- 2020-10-07T08:46:38.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622451, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548075
;

-- 2020-10-07T08:46:38.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545702,622452,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Rechnungskanditad - Verrechnung',70,1,1,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:38.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622452 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:38.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541434) 
;

-- 2020-10-07T08:46:38.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622452
;

-- 2020-10-07T08:46:38.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622452)
;

-- 2020-10-07T08:46:38.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622452
;

-- 2020-10-07T08:46:38.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622452, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548077
;

-- 2020-10-07T08:46:38.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545705,622453,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Zu verrechnender Rechn-Kand.',10,10,1,1,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:38.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622453 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:38.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541435) 
;

-- 2020-10-07T08:46:38.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622453
;

-- 2020-10-07T08:46:38.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622453)
;

-- 2020-10-07T08:46:38.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622453
;

-- 2020-10-07T08:46:38.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622453, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548078
;

-- 2020-10-07T08:46:38.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545718,622454,1000642,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Abrechnungssatz',30,30,1,1,TO_TIMESTAMP('2020-10-07 11:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:38.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622454 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:38.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000642) 
;

-- 2020-10-07T08:46:38.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622454
;

-- 2020-10-07T08:46:38.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622454)
;

-- 2020-10-07T08:46:38.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622454
;

-- 2020-10-07T08:46:38.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622454, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548079
;

-- 2020-10-07T08:46:39.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546057,622455,0,543053,0,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Pauschale - Vertragsperiode',20,20,1,1,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:39.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622455 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:39.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2020-10-07T08:46:39.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622455
;

-- 2020-10-07T08:46:39.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622455)
;

-- 2020-10-07T08:46:39.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622455
;

-- 2020-10-07T08:46:39.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622455, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548512
;

-- 2020-10-07T08:46:39.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,DisplayLogic,EntityType,HasTree,Help,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,545845,572652,0,543054,540321,540983,'Y',TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Enthält Referenzen und Informationen zur REchnungszeile, sofern der jeweilige Rechnungskandidate bereits (ggf. teilweise) in Rechnung gestellt wurde','@IsToClear@=''N''','de.metas.invoicecandidate','N','Zu Kandidatden, die als "zu verrechnen" markiert werden nur indirekt in Rechnung gestellt, und es werden zu ihnen keine Rechnungszeilen erzeugt.','N','C_Invoice_Line_Alloc','Y','N','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Zuordnung - Rechnungszeile','N',30,1,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:39.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543054 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-10-07T08:46:39.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572652) 
;

-- 2020-10-07T08:46:39.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543054)
;

-- 2020-10-07T08:46:39.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 543054
;

-- 2020-10-07T08:46:39.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 543054, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540342
;

-- 2020-10-07T08:46:39.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545841,622456,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:39.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622456 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:39.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-10-07T08:46:39.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622456
;

-- 2020-10-07T08:46:39.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622456)
;

-- 2020-10-07T08:46:39.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622456
;

-- 2020-10-07T08:46:39.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622456, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548180
;

-- 2020-10-07T08:46:39.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545842,622457,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.invoicecandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:39.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622457 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:39.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2020-10-07T08:46:39.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622457
;

-- 2020-10-07T08:46:39.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622457)
;

-- 2020-10-07T08:46:39.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622457
;

-- 2020-10-07T08:46:39.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622457, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548181
;

-- 2020-10-07T08:46:39.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545843,622458,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.invoicecandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',30,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:39.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622458 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:39.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2020-10-07T08:46:39.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622458
;

-- 2020-10-07T08:46:39.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622458)
;

-- 2020-10-07T08:46:39.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622458
;

-- 2020-10-07T08:46:39.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622458, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548182
;

-- 2020-10-07T08:46:40.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545839,622459,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.invoicecandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',40,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:40.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622459 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:40.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2020-10-07T08:46:40.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622459
;

-- 2020-10-07T08:46:40.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622459)
;

-- 2020-10-07T08:46:40.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622459
;

-- 2020-10-07T08:46:40.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622459, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548183
;

-- 2020-10-07T08:46:40.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545840,622460,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.invoicecandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',50,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:40.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622460 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:40.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2020-10-07T08:46:40.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622460
;

-- 2020-10-07T08:46:40.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622460)
;

-- 2020-10-07T08:46:40.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622460
;

-- 2020-10-07T08:46:40.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622460, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548184
;

-- 2020-10-07T08:46:40.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545837,622461,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.invoicecandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',60,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:40.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622461 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:40.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-10-07T08:46:40.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622461
;

-- 2020-10-07T08:46:40.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622461)
;

-- 2020-10-07T08:46:40.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622461
;

-- 2020-10-07T08:46:40.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622461, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548185
;

-- 2020-10-07T08:46:40.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545838,622462,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.invoicecandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',70,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:40.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:40.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-10-07T08:46:40.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622462
;

-- 2020-10-07T08:46:40.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622462)
;

-- 2020-10-07T08:46:40.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622462
;

-- 2020-10-07T08:46:40.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622462, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548186
;

-- 2020-10-07T08:46:40.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545845,622463,1001128,0,543054,111,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Rechnungskandidat',10,10,1,1,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:40.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:40.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001128) 
;

-- 2020-10-07T08:46:40.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622463
;

-- 2020-10-07T08:46:40.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622463)
;

-- 2020-10-07T08:46:40.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622463
;

-- 2020-10-07T08:46:40.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622463, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548187
;

-- 2020-10-07T08:46:40.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545844,622464,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Rechnungskandidat - Rechungszeile',80,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:40.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:40.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541460) 
;

-- 2020-10-07T08:46:40.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622464
;

-- 2020-10-07T08:46:40.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622464)
;

-- 2020-10-07T08:46:40.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622464
;

-- 2020-10-07T08:46:40.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622464, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548188
;

-- 2020-10-07T08:46:41.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545846,622465,1001896,0,543054,363,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile',10,'de.metas.invoicecandidate','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.',0,'Y','Y','Y','N','N','N','N','N','Rechnungsposition',20,20,1,1,TO_TIMESTAMP('2020-10-07 11:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001896) 
;

-- 2020-10-07T08:46:41.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622465
;

-- 2020-10-07T08:46:41.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622465)
;

-- 2020-10-07T08:46:41.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622465
;

-- 2020-10-07T08:46:41.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622465, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548189
;

-- 2020-10-07T08:46:41.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545850,622466,1002056,0,543054,124,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'de.metas.invoicecandidate','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','Y','Y','N','N','N','N','N','Belegstatus Rechnung',40,40,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002056) 
;

-- 2020-10-07T08:46:41.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622466
;

-- 2020-10-07T08:46:41.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622466)
;

-- 2020-10-07T08:46:41.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622466
;

-- 2020-10-07T08:46:41.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622466, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548193
;

-- 2020-10-07T08:46:41.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545849,622467,0,543054,95,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.',14,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Berechn. Menge',30,30,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(529) 
;

-- 2020-10-07T08:46:41.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622467
;

-- 2020-10-07T08:46:41.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622467)
;

-- 2020-10-07T08:46:41.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622467
;

-- 2020-10-07T08:46:41.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622467, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548194
;

-- 2020-10-07T08:46:41.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546220,622468,0,543054,72,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Aggregator',50,50,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541470) 
;

-- 2020-10-07T08:46:41.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622468
;

-- 2020-10-07T08:46:41.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622468)
;

-- 2020-10-07T08:46:41.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622468
;

-- 2020-10-07T08:46:41.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622468, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548745
;

-- 2020-10-07T08:46:41.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552529,622469,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',0,'de.metas.invoicecandidate','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',60,60,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622469 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2020-10-07T08:46:41.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622469
;

-- 2020-10-07T08:46:41.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622469)
;

-- 2020-10-07T08:46:41.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622469
;

-- 2020-10-07T08:46:41.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622469, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556176
;

-- 2020-10-07T08:46:41.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556051,622470,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.',0,'de.metas.invoicecandidate','Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.',0,'Y','Y','Y','N','N','N','N','N','Zu berechn. Menge abw.',70,70,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622470 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541458) 
;

-- 2020-10-07T08:46:41.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622470
;

-- 2020-10-07T08:46:41.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622470)
;

-- 2020-10-07T08:46:41.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622470
;

-- 2020-10-07T08:46:41.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622470, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557544
;

-- 2020-10-07T08:46:41.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556052,622471,0,543054,0,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet',0,'Y','Y','Y','N','N','N','N','Y','Preis Abw.',80,80,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622471 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:41.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542229) 
;

-- 2020-10-07T08:46:41.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622471
;

-- 2020-10-07T08:46:41.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622471)
;

-- 2020-10-07T08:46:41.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622471
;

-- 2020-10-07T08:46:41.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622471, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557545
;

-- 2020-10-07T08:46:41.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,550555,573414,0,543055,540579,540983,'Y',TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','N','N','C_InvoiceCandidate_InOutLine','Y','N','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N','Zuordnung Liefer-/Wareneingangszeile','N',45,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:41.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543055 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-10-07T08:46:41.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(573414) 
;

-- 2020-10-07T08:46:41.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543055)
;

-- 2020-10-07T08:46:41.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 543055
;

-- 2020-10-07T08:46:41.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 543055, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540665
;

-- 2020-10-07T08:46:42.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550547,622472,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'de.metas.invoicecandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:42.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622472 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:42.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-10-07T08:46:42.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622472
;

-- 2020-10-07T08:46:42.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622472)
;

-- 2020-10-07T08:46:42.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622472
;

-- 2020-10-07T08:46:42.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622472, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555482
;

-- 2020-10-07T08:46:42.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550548,622473,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'de.metas.invoicecandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','Y','Y','Sektion',20,10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:42.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:42.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-10-07T08:46:42.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622473
;

-- 2020-10-07T08:46:42.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622473)
;

-- 2020-10-07T08:46:42.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622473
;

-- 2020-10-07T08:46:42.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622473, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555483
;

-- 2020-10-07T08:46:42.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550555,622474,1002325,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Rechnungskandidat',30,20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:42.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:42.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002325) 
;

-- 2020-10-07T08:46:42.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622474
;

-- 2020-10-07T08:46:42.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622474)
;

-- 2020-10-07T08:46:42.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622474
;

-- 2020-10-07T08:46:42.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622474, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555484
;

-- 2020-10-07T08:46:42.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550556,622475,1002593,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Versand- oder Wareneingangsbeleg',0,'de.metas.invoicecandidate','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.',0,'Y','Y','Y','N','N','N','Y','N','Versand-/Wareneingangsposition',40,20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:42.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622475 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:42.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002593) 
;

-- 2020-10-07T08:46:42.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622475
;

-- 2020-10-07T08:46:42.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622475)
;

-- 2020-10-07T08:46:42.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622475
;

-- 2020-10-07T08:46:42.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622475, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555485
;

-- 2020-10-07T08:46:42.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551808,622476,576973,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','Y','Geliefert (Lagereinheit)',60,40,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:42.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622476 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:42.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576973) 
;

-- 2020-10-07T08:46:42.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622476
;

-- 2020-10-07T08:46:42.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622476)
;

-- 2020-10-07T08:46:42.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622476
;

-- 2020-10-07T08:46:42.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622476, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555488
;

-- 2020-10-07T08:46:42.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551809,622477,1000778,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','Y','N','Lieferung/ Wareneingang freigeben',70,50,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:42.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:42.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000778) 
;

-- 2020-10-07T08:46:42.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622477
;

-- 2020-10-07T08:46:42.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622477)
;

-- 2020-10-07T08:46:42.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622477
;

-- 2020-10-07T08:46:42.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622477, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555489
;

-- 2020-10-07T08:46:43.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550551,622478,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','Y','N','Aktiv',80,1,1,TO_TIMESTAMP('2020-10-07 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-10-07T08:46:43.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622478
;

-- 2020-10-07T08:46:43.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622478)
;

-- 2020-10-07T08:46:43.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622478
;

-- 2020-10-07T08:46:43.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622478, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582499
;

-- 2020-10-07T08:46:43.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550554,622479,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','C_InvoiceCandidate_InOutLine',90,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622479 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542423) 
;

-- 2020-10-07T08:46:43.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622479
;

-- 2020-10-07T08:46:43.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622479)
;

-- 2020-10-07T08:46:43.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622479
;

-- 2020-10-07T08:46:43.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622479, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582500
;

-- 2020-10-07T08:46:43.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550602,622480,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,'Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.',22,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','Berechn. Menge',100,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(529) 
;

-- 2020-10-07T08:46:43.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622480
;

-- 2020-10-07T08:46:43.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622480)
;

-- 2020-10-07T08:46:43.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622480
;

-- 2020-10-07T08:46:43.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622480, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582501
;

-- 2020-10-07T08:46:43.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551874,622481,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','IsInvalidQty',110,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542724) 
;

-- 2020-10-07T08:46:43.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622481
;

-- 2020-10-07T08:46:43.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622481)
;

-- 2020-10-07T08:46:43.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622481
;

-- 2020-10-07T08:46:43.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622481, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582502
;

-- 2020-10-07T08:46:43.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551875,622482,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','InvalidQtyReason',120,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542725) 
;

-- 2020-10-07T08:46:43.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622482
;

-- 2020-10-07T08:46:43.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622482)
;

-- 2020-10-07T08:46:43.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622482
;

-- 2020-10-07T08:46:43.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622482, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582503
;

-- 2020-10-07T08:46:43.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551876,622483,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','IsMigrated',130,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542726) 
;

-- 2020-10-07T08:46:43.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622483
;

-- 2020-10-07T08:46:43.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622483)
;

-- 2020-10-07T08:46:43.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622483
;

-- 2020-10-07T08:46:43.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622483, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582504
;

-- 2020-10-07T08:46:43.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568522,622484,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge',10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','Geliefert Catch',140,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:43.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:43.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576949) 
;

-- 2020-10-07T08:46:43.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622484
;

-- 2020-10-07T08:46:43.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622484)
;

-- 2020-10-07T08:46:43.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622484
;

-- 2020-10-07T08:46:43.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622484, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582505
;

-- 2020-10-07T08:46:44.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568523,622485,576977,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','Liefereinheit',150,1,1,TO_TIMESTAMP('2020-10-07 11:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:44.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622485 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:44.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576977) 
;

-- 2020-10-07T08:46:44.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622485
;

-- 2020-10-07T08:46:44.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622485)
;

-- 2020-10-07T08:46:44.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622485
;

-- 2020-10-07T08:46:44.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622485, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582506
;

-- 2020-10-07T08:46:44.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568524,622486,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Geliefert abw.',160,1,1,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:44.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:44.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576976) 
;

-- 2020-10-07T08:46:44.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622486
;

-- 2020-10-07T08:46:44.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622486)
;

-- 2020-10-07T08:46:44.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622486
;

-- 2020-10-07T08:46:44.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622486, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582507
;

-- 2020-10-07T08:46:44.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568527,622487,0,543055,0,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','Y','N','Geliefert Nominal',170,1,1,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:44.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622487 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:44.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576979) 
;

-- 2020-10-07T08:46:44.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622487
;

-- 2020-10-07T08:46:44.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622487)
;

-- 2020-10-07T08:46:44.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622487
;

-- 2020-10-07T08:46:44.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622487, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582508
;

-- 2020-10-07T08:46:44.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,551279,572546,0,543056,540614,540983,'Y',TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','N','N','C_Invoice_Detail','Y','N','Y','N','N','N','N','Y','Y','N','N','N','Y','N','N','N','Rechnungszeilendetails',544906,'N',50,1,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:44.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543056 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-10-07T08:46:44.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572546) 
;

-- 2020-10-07T08:46:44.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543056)
;

-- 2020-10-07T08:46:44.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 543056
;

-- 2020-10-07T08:46:44.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 543056, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540622
;

-- 2020-10-07T08:46:44.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551184,622488,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.invoicecandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:44.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622488 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:44.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-10-07T08:46:44.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622488
;

-- 2020-10-07T08:46:44.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622488)
;

-- 2020-10-07T08:46:44.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622488
;

-- 2020-10-07T08:46:44.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622488, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554838
;

-- 2020-10-07T08:46:44.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551185,622489,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.invoicecandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',20,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:44.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622489 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:44.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-10-07T08:46:45.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622489
;

-- 2020-10-07T08:46:45.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622489)
;

-- 2020-10-07T08:46:45.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622489
;

-- 2020-10-07T08:46:45.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622489, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554839
;

-- 2020-10-07T08:46:45.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551188,622490,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',30,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:45.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622490 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:45.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-10-07T08:46:45.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622490
;

-- 2020-10-07T08:46:45.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622490)
;

-- 2020-10-07T08:46:45.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622490
;

-- 2020-10-07T08:46:45.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622490, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554840
;

-- 2020-10-07T08:46:45.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551191,622491,1001949,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Invoice detailed informations',40,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:45.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622491 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:45.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001949) 
;

-- 2020-10-07T08:46:45.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622491
;

-- 2020-10-07T08:46:45.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622491)
;

-- 2020-10-07T08:46:45.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622491
;

-- 2020-10-07T08:46:45.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622491, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554841
;

-- 2020-10-07T08:46:45.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551192,622492,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',10,'de.metas.invoicecandidate','The Invoice Document.',0,'Y','Y','Y','N','N','N','N','N','Rechnung',150,150,1,1,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:45.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622492 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:45.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2020-10-07T08:46:45.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622492
;

-- 2020-10-07T08:46:45.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622492)
;

-- 2020-10-07T08:46:45.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622492
;

-- 2020-10-07T08:46:45.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622492, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554842
;

-- 2020-10-07T08:46:45.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551193,622493,1001663,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile',10,'de.metas.invoicecandidate','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.',0,'Y','Y','Y','N','N','N','N','Y','Rechnungsposition',160,160,1,1,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:45.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622493 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:45.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001663) 
;

-- 2020-10-07T08:46:45.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622493
;

-- 2020-10-07T08:46:45.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622493)
;

-- 2020-10-07T08:46:45.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622493
;

-- 2020-10-07T08:46:45.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622493, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554843
;

-- 2020-10-07T08:46:46Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551194,622494,1000340,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'de.metas.invoicecandidate','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','N','N','Reihenfolge',10,10,1,1,TO_TIMESTAMP('2020-10-07 11:46:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622494 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000340) 
;

-- 2020-10-07T08:46:46.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622494
;

-- 2020-10-07T08:46:46.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622494)
;

-- 2020-10-07T08:46:46.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622494
;

-- 2020-10-07T08:46:46.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622494, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554844
;

-- 2020-10-07T08:46:46.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551195,622495,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate','If set, this line will be printed before the line to which it links.',0,'Y','Y','Y','N','N','N','N','Y','davor andrucken',20,20,1,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542552) 
;

-- 2020-10-07T08:46:46.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622495
;

-- 2020-10-07T08:46:46.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622495)
;

-- 2020-10-07T08:46:46.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622495
;

-- 2020-10-07T08:46:46.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622495, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554845
;

-- 2020-10-07T08:46:46.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551196,622496,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.invoicecandidate','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',40,40,1,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2020-10-07T08:46:46.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622496
;

-- 2020-10-07T08:46:46.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622496)
;

-- 2020-10-07T08:46:46.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622496
;

-- 2020-10-07T08:46:46.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622496, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554846
;

-- 2020-10-07T08:46:46.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551197,622497,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622497 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2020-10-07T08:46:46.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622497
;

-- 2020-10-07T08:46:46.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622497)
;

-- 2020-10-07T08:46:46.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622497
;

-- 2020-10-07T08:46:46.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622497, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554847
;

-- 2020-10-07T08:46:46.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551198,622498,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',40,'de.metas.invoicecandidate','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',50,50,999,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622498 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2020-10-07T08:46:46.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622498
;

-- 2020-10-07T08:46:46.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622498)
;

-- 2020-10-07T08:46:46.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622498
;

-- 2020-10-07T08:46:46.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622498, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554848
;

-- 2020-10-07T08:46:46.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551199,622499,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.invoicecandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','N','N','N','N','N','N','Merkmale',170,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622499 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2020-10-07T08:46:46.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622499
;

-- 2020-10-07T08:46:46.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622499)
;

-- 2020-10-07T08:46:46.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622499
;

-- 2020-10-07T08:46:46.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622499, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554849
;

-- 2020-10-07T08:46:46.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551200,622500,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Menge',10,'de.metas.invoicecandidate','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.',0,'Y','Y','Y','N','N','N','N','N','Menge',130,130,1,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:46.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622500 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:46.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2020-10-07T08:46:46.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622500
;

-- 2020-10-07T08:46:46.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622500)
;

-- 2020-10-07T08:46:46.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622500
;

-- 2020-10-07T08:46:46.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622500, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554850
;

-- 2020-10-07T08:46:47.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551201,622501,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'de.metas.invoicecandidate','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','Y','Maßeinheit',140,140,1,1,TO_TIMESTAMP('2020-10-07 11:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2020-10-07T08:46:47.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622501
;

-- 2020-10-07T08:46:47.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622501)
;

-- 2020-10-07T08:46:47.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622501
;

-- 2020-10-07T08:46:47.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622501, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554851
;

-- 2020-10-07T08:46:47.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551202,622502,1001424,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge in Preiseinheit',10,'de.metas.invoicecandidate','Die "Bestellte Menge in Preiseinheit" bezeichnet die Menge einer Ware, die bestellt wurde, umgerechnet in die Mengeneinheit auf die sich der Preis bezieht.',0,'Y','Y','Y','N','N','N','N','N','Bestellte Menge in Preiseinheit',80,80,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622502 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001424) 
;

-- 2020-10-07T08:46:47.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622502
;

-- 2020-10-07T08:46:47.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622502)
;

-- 2020-10-07T08:46:47.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622502
;

-- 2020-10-07T08:46:47.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622502, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554852
;

-- 2020-10-07T08:46:47.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551203,622503,1001184,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Preiseinheit',120,120,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622503 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001184) 
;

-- 2020-10-07T08:46:47.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622503
;

-- 2020-10-07T08:46:47.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622503)
;

-- 2020-10-07T08:46:47.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622503
;

-- 2020-10-07T08:46:47.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622503, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554853
;

-- 2020-10-07T08:46:47.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551204,622504,1002440,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit',10,'de.metas.invoicecandidate','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet',0,'Y','Y','Y','N','N','N','N','Y','Preis',90,90,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622504 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002440) 
;

-- 2020-10-07T08:46:47.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622504
;

-- 2020-10-07T08:46:47.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622504)
;

-- 2020-10-07T08:46:47.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622504
;

-- 2020-10-07T08:46:47.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622504, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554854
;

-- 2020-10-07T08:46:47.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551205,622505,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis',10,'de.metas.invoicecandidate','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',0,'Y','Y','Y','N','N','N','N','Y','Einzelpreis',100,100,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622505 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(519) 
;

-- 2020-10-07T08:46:47.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622505
;

-- 2020-10-07T08:46:47.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622505)
;

-- 2020-10-07T08:46:47.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622505
;

-- 2020-10-07T08:46:47.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622505, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554855
;

-- 2020-10-07T08:46:47.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551206,622506,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent',10,'de.metas.invoicecandidate','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.',0,'Y','Y','Y','N','N','N','N','N','Rabatt %',110,110,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(280) 
;

-- 2020-10-07T08:46:47.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622506
;

-- 2020-10-07T08:46:47.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622506)
;

-- 2020-10-07T08:46:47.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622506
;

-- 2020-10-07T08:46:47.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622506, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554856
;

-- 2020-10-07T08:46:47.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551280,622507,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document / line is printed',1,'de.metas.invoicecandidate','The Printed checkbox indicates if this document or line will included when printing.',0,'Y','Y','Y','N','N','N','N','N','andrucken',15,15,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(399) 
;

-- 2020-10-07T08:46:47.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622507
;

-- 2020-10-07T08:46:47.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622507)
;

-- 2020-10-07T08:46:47.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622507
;

-- 2020-10-07T08:46:47.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622507, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554857
;

-- 2020-10-07T08:46:47.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551279,622508,1002728,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Rechnungskandidat',180,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:47.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:47.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002728) 
;

-- 2020-10-07T08:46:47.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622508
;

-- 2020-10-07T08:46:47.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622508)
;

-- 2020-10-07T08:46:47.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622508
;

-- 2020-10-07T08:46:47.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622508, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554861
;

-- 2020-10-07T08:46:48.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551282,622509,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','',0,'Y','Y','Y','N','N','N','N','N','Anteil',70,70,1,1,TO_TIMESTAMP('2020-10-07 11:46:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2004) 
;

-- 2020-10-07T08:46:48.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622509
;

-- 2020-10-07T08:46:48.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622509)
;

-- 2020-10-07T08:46:48.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622509
;

-- 2020-10-07T08:46:48.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622509, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554862
;

-- 2020-10-07T08:46:48.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551207,540059,622510,1001912,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Packvorschrift',170,170,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001912) 
;

-- 2020-10-07T08:46:48.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622510
;

-- 2020-10-07T08:46:48.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622510)
;

-- 2020-10-07T08:46:48.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622510
;

-- 2020-10-07T08:46:48.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622510, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554863
;

-- 2020-10-07T08:46:48.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551278,540059,622511,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','TU Anzahl',190,190,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622511 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542490) 
;

-- 2020-10-07T08:46:48.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622511
;

-- 2020-10-07T08:46:48.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622511)
;

-- 2020-10-07T08:46:48.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622511
;

-- 2020-10-07T08:46:48.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622511, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554864
;

-- 2020-10-07T08:46:48.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551288,540059,622512,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Packvorschrift (TU)',180,180,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622512 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542489) 
;

-- 2020-10-07T08:46:48.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622512
;

-- 2020-10-07T08:46:48.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622512)
;

-- 2020-10-07T08:46:48.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622512
;

-- 2020-10-07T08:46:48.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622512, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554868
;

-- 2020-10-07T08:46:48.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551291,622513,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Detail-Info statt Rechnungszeile andrucken',34,34,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622513 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542573) 
;

-- 2020-10-07T08:46:48.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622513
;

-- 2020-10-07T08:46:48.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622513)
;

-- 2020-10-07T08:46:48.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622513
;

-- 2020-10-07T08:46:48.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622513, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554870
;

-- 2020-10-07T08:46:48.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552846,622514,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','Y','Produktionsauftrag',45,45,0,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622514 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53276) 
;

-- 2020-10-07T08:46:48.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622514
;

-- 2020-10-07T08:46:48.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622514)
;

-- 2020-10-07T08:46:48.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622514
;

-- 2020-10-07T08:46:48.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622514, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556430
;

-- 2020-10-07T08:46:48.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569733,622515,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,256,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Label',200,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:48.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622515 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:48.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576005) 
;

-- 2020-10-07T08:46:48.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622515
;

-- 2020-10-07T08:46:48.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622515)
;

-- 2020-10-07T08:46:48.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622515
;

-- 2020-10-07T08:46:48.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622515, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 614930
;

-- 2020-10-07T08:46:49.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570889,622516,0,543056,0,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Datum',210,1,1,TO_TIMESTAMP('2020-10-07 11:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:49.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622516 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:49.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577762) 
;

-- 2020-10-07T08:46:49.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622516
;

-- 2020-10-07T08:46:49.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622516)
;

-- 2020-10-07T08:46:49.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622516
;

-- 2020-10-07T08:46:49.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622516, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 614931
;

-- 2020-10-07T08:46:49.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,560144,572548,0,543057,540981,540983,'Y',TO_TIMESTAMP('2020-10-07 11:46:49','YYYY-MM-DD HH24:MI:SS'),100,'Ggf. andere Rechnungskandidaten, die einem Rückvergütungsvertrag unterliegen. Die Rückvergütung erfolgt durch den aktuellen Rechnungskandiaten.','de.metas.invoicecandidate','N','N','C_Invoice_Candidate_Assignment','Y','N','Y','N','N','N','N','Y','Y','N','N','N','Y','N','N','N',0,'Rückzuvergütende RKn',544906,'N',60,1,TO_TIMESTAMP('2020-10-07 11:46:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:49.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543057 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-10-07T08:46:49.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572548) 
;

-- 2020-10-07T08:46:49.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543057)
;

-- 2020-10-07T08:46:49.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 543057
;

-- 2020-10-07T08:46:49.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 543057, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541110
;

-- 2020-10-07T08:46:49.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560136,622517,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.invoicecandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',20,1,1,TO_TIMESTAMP('2020-10-07 11:46:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:49.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622517 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:49.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-10-07T08:46:49.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622517
;

-- 2020-10-07T08:46:49.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622517)
;

-- 2020-10-07T08:46:49.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622517
;

-- 2020-10-07T08:46:49.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622517, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564239
;

-- 2020-10-07T08:46:50.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560137,622518,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.invoicecandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',40,1,1,TO_TIMESTAMP('2020-10-07 11:46:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:50.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622518 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:50.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-10-07T08:46:50.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622518
;

-- 2020-10-07T08:46:50.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622518)
;

-- 2020-10-07T08:46:50.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622518
;

-- 2020-10-07T08:46:50.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622518, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564240
;

-- 2020-10-07T08:46:50.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560140,622519,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',10,1,1,TO_TIMESTAMP('2020-10-07 11:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:50.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622519 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:50.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-10-07T08:46:50.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622519
;

-- 2020-10-07T08:46:50.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622519)
;

-- 2020-10-07T08:46:50.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622519
;

-- 2020-10-07T08:46:50.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622519, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564241
;

-- 2020-10-07T08:46:50.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560143,622520,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:50','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','C_Invoice_Candidate_Assignment',50,1,1,TO_TIMESTAMP('2020-10-07 11:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:50.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622520 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:50.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544074) 
;

-- 2020-10-07T08:46:50.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622520
;

-- 2020-10-07T08:46:50.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622520)
;

-- 2020-10-07T08:46:50.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622520
;

-- 2020-10-07T08:46:50.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622520, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564242
;

-- 2020-10-07T08:46:51.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560144,622521,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:50','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Vertrag-Rechnungskandidat',60,1,1,TO_TIMESTAMP('2020-10-07 11:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544086) 
;

-- 2020-10-07T08:46:51.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622521
;

-- 2020-10-07T08:46:51.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622521)
;

-- 2020-10-07T08:46:51.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622521
;

-- 2020-10-07T08:46:51.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622521, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564243
;

-- 2020-10-07T08:46:51.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560145,622522,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Rückzuvergütender RK',50,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622522 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544075) 
;

-- 2020-10-07T08:46:51.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622522
;

-- 2020-10-07T08:46:51.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622522)
;

-- 2020-10-07T08:46:51.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622522
;

-- 2020-10-07T08:46:51.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622522, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564244
;

-- 2020-10-07T08:46:51.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560146,622523,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Pauschale - Vertragsperiode',30,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622523 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2020-10-07T08:46:51.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622523
;

-- 2020-10-07T08:46:51.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622523)
;

-- 2020-10-07T08:46:51.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622523
;

-- 2020-10-07T08:46:51.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622523, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 564245
;

-- 2020-10-07T08:46:51.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560197,622524,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,'Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.',14,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Zugeordneter Geldbetrag',90,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622524 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544095) 
;

-- 2020-10-07T08:46:51.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622524
;

-- 2020-10-07T08:46:51.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622524)
;

-- 2020-10-07T08:46:51.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622524
;

-- 2020-10-07T08:46:51.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622524, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568302
;

-- 2020-10-07T08:46:51.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560759,622525,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,'Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes',14,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Zugeordnete Menge',100,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544204) 
;

-- 2020-10-07T08:46:51.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622525
;

-- 2020-10-07T08:46:51.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622525)
;

-- 2020-10-07T08:46:51.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622525
;

-- 2020-10-07T08:46:51.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622525, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568303
;

-- 2020-10-07T08:46:51.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560760,622526,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','C_Flatrate_RefundConfig',70,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544072) 
;

-- 2020-10-07T08:46:51.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622526
;

-- 2020-10-07T08:46:51.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622526)
;

-- 2020-10-07T08:46:51.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622526
;

-- 2020-10-07T08:46:51.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622526, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568304
;

-- 2020-10-07T08:46:51.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560767,622527,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Basisbetrag',80,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544207) 
;

-- 2020-10-07T08:46:51.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622527
;

-- 2020-10-07T08:46:51.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622527)
;

-- 2020-10-07T08:46:51.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622527
;

-- 2020-10-07T08:46:51.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622527, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568305
;

-- 2020-10-07T08:46:51.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560851,622528,0,543057,0,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Zugeordnete Menge wird in Summe einbez.',110,1,1,TO_TIMESTAMP('2020-10-07 11:46:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:51.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-07T08:46:51.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544225) 
;

-- 2020-10-07T08:46:51.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622528
;

-- 2020-10-07T08:46:51.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(622528)
;

-- 2020-10-07T08:46:51.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 622528
;

-- 2020-10-07T08:46:51.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 622528, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 568306
;

-- 2020-10-07T08:46:52.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543052,542275,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','',10,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-10-07T08:46:52.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542275 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:46:52.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542275
;

-- 2020-10-07T08:46:52.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542275, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540033
;

-- 2020-10-07T08:46:52.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542871,542275,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:52.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542871,544361,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:52.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622443,0,543052,544361,573377,'F',TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Planspiel',5,0,0,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:52.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622400,0,543052,544361,573378,'F',TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N','Abrechnungsgruppe',10,20,0,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:46:52.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622361,0,543052,544361,573379,'F',TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt, ob der Kandidat zu einer Einkaufs- oder Verkaufsrechnung verarbeitet wird','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','Y','N','N','IsSOTrx',15,10,0,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:53.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622314,0,543052,544361,573380,'F',TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Rechnungspartner',20,30,10,TO_TIMESTAMP('2020-10-07 11:46:52','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2020-10-07T08:46:53.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,622315,0,540768,573380,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:53.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,622313,0,540769,573380,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,'widget',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:53.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622440,0,543052,544361,573381,'F',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_SalesRep_ID',30,0,0,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:53.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622428,0,543052,544361,573382,'F',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Rechungsadresse abw.',40,0,0,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:53.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622429,0,543052,544361,573383,'F',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Rechnungskontakt abw.',50,0,0,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:53.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622309,0,543052,544361,573384,'F',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','C_Order_ID',60,40,0,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:46:53.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622311,0,543052,544361,573385,'F',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Produkt',70,50,20,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2020-10-07T08:46:54.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622407,0,543052,544361,573386,'F',TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N','Produkt-Kategorie',80,60,0,TO_TIMESTAMP('2020-10-07 11:46:53','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:46:54.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542871,544362,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','qtyInStockingUOM',15,'',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:54.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622434,0,543052,544362,573387,'F',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'StockingUOM_ID',100,0,0,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:54.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622323,0,543052,544362,573388,'F',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','QtyOrdered',110,90,0,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:54.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622324,0,543052,544362,573389,'F',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Gelieferte Menge',120,100,0,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:54.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622326,0,543052,544362,573390,'F',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Berechnete Menge',130,110,0,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:54.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622331,0,543052,544362,573391,'F',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoice_Override',140,0,0,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:54.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622312,0,543052,544362,573392,'F',TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','QtyToInvoice',150,120,0,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542871,544363,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','prices',20,TO_TIMESTAMP('2020-10-07 11:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622318,0,543052,544363,573393,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PriceActual',10,0,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622360,0,543052,544363,573394,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PriceEntered_Override',20,0,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622321,0,543052,544363,573395,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Discount',30,0,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622322,0,543052,544363,573396,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Discount_Override',40,0,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542871,544364,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','override',40,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622376,0,543052,544364,573397,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Total des Auftrags',10,150,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:55.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622345,0,543052,544364,573398,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Abrechnung ab abw.',20,0,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622333,0,543052,544364,573399,'F',TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Rechnungsstellung abw.',26,0,0,TO_TIMESTAMP('2020-10-07 11:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622351,0,543052,544364,573400,'F',TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Rechnungsstellung eff.',30,0,0,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542872,542275,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542872,544365,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates & flags',10,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622330,0,543052,544365,573401,'F',TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Processed',10,0,0,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622306,0,543052,544365,573402,'F',TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','IsToRecompute',20,70,0,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622341,0,543052,544365,573403,'F',TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','N','N','N','IsError',30,0,0,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:56.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622317,0,543052,544365,573404,'F',TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Informationen des Aktualisierungsprozesses','Das Feld wird durch den Aktualisierungprozess zu Beginn geleert und dann ggf. mit Diagnose-Informationen gefüllt, um die Resultate der Aktualisierung nachvollziehbarer zu machen.','Y','N','N','Y','N','N','N','Status nach Akt.',33,0,0,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:57.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622346,0,543052,544365,573405,'F',TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','ErrorMsg',36,0,0,TO_TIMESTAMP('2020-10-07 11:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:57.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622350,0,543052,544365,573406,'F',TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Abrechnung ab eff.',40,130,40,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:46:57.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622387,0,543052,544365,573407,'F',TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Lieferdatum',50,140,0,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:46:57.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542872,544366,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','quality',20,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:57.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622368,0,543052,544366,573408,'F',TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','im Disput',5,0,0,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:57.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622364,0,543052,544366,573409,'F',TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Qualitätsnotiz',10,0,0,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:57.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622366,0,543052,544366,573410,'F',TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Qualitätsabzug abw.',20,0,0,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:57.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622363,0,543052,544366,573411,'F',TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Qualitätsabzug',31,0,0,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542872,544367,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','qtyInUOM',30,TO_TIMESTAMP('2020-10-07 11:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622433,0,543052,544367,573412,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'InvoicableQtyBasedOn',2,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622371,0,543052,544367,573413,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_UOM_ID',4,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622430,0,543052,544367,573414,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyEntered',7,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622432,0,543052,544367,573415,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyDeliveredInUOM',40,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622438,0,543052,544367,573416,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoiceInUOM_Calc',50,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622435,0,543052,544367,573417,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyInvoicedInUOM',60,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:58.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542872,544368,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','totals',40,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:59.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622335,0,543052,544368,573418,'F',TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'NetAmtInvoiced',10,0,0,TO_TIMESTAMP('2020-10-07 11:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:59.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622325,0,543052,544368,573419,'F',TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'NetAmtToInvoice',20,0,0,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:59.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542872,544369,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',60,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:59.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622310,0,543052,544369,573420,'F',TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',10,160,0,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:46:59.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622308,0,543052,544369,573421,'F',TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:46:59.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543052,542276,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2020-10-07T08:46:59.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542276 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:46:59.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542276
;

-- 2020-10-07T08:46:59.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542276, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540462
;

-- 2020-10-07T08:46:59.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542873,542276,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:59.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542873,544370,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:46:59.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622427,0,543052,544370,573422,'F',TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','Y',2,'Notiz',10,0,0,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622307,0,543052,544370,573423,'F',TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Identifikationsnummer eines Rechnungskandidaten','Y','Y','N','Y','N','N','N','Rechnungskandidat',20,0,0,TO_TIMESTAMP('2020-10-07 11:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622441,0,543052,544370,573424,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'ExternalHeaderId',22,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622442,0,543052,544370,573425,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'ExternalLineId',24,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622352,0,543052,544370,573426,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein manueller Vorgang','Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorang manuell durchgefĂĽhrt wird.','Y','Y','N','Y','N','N','N','Manuell',50,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622394,0,543052,544370,573427,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Ist Material-Vorgang',60,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622379,0,543052,544370,573428,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Material-Vorgang-ID',70,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:00.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622377,0,543052,544370,573429,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','Y','N','Y','N','N','N','Zeile Nr.',80,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:00.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622378,0,543052,544370,573430,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for invoices generated from this sales document','The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.','Y','Y','N','Y','N','N','N','Rechnungs-Belegart',90,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622342,0,543052,544370,573431,'F',TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Rechnungskand.-Controller',100,0,0,TO_TIMESTAMP('2020-10-07 11:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622340,0,543052,544370,573432,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','Y','N','Y','N','N','N','DB-Tabelle',110,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622339,0,543052,544370,573433,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','Y','N','Y','N','N','N','Datensatz-ID',120,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622353,0,543052,544370,573434,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Beschreibung',130,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622329,0,543052,544370,573435,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Leistung wird nicht unmittelbar in Rechnung gestellt, sondern mit anderen Posten (z.B. Pauschale) verrechnet','Diese Markierung wird vom System gesetzt, wenn ein Rechnungskandidat eine Auftragszeile referenziert, die einem laufenden Pauschalen-Vertrag unterliegt.','Y','Y','N','Y','N','N','N','zur Verrechnung',140,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622302,0,543052,544370,573436,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','Y','N','Y','N','N','N','Auftragsdatum',160,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:01.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622303,0,543052,544370,573437,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','Y','N','Y','N','N','N','Auftragsposition',170,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:01.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622386,0,543052,544370,573438,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Erste Lieferung oder Wareneingang.','The Material Shipment / Receipt ','Y','Y','N','Y','N','N','N','Lieferung/Wareneingang',180,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:02.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622412,0,543052,544370,573439,'F',TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','Y','N','Y','N','N','N','Zugesagter Termin',190,0,0,TO_TIMESTAMP('2020-10-07 11:47:01','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:02.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622401,0,543052,544370,573440,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'BParter location of first shipment/receipt','Y','Y','N','Y','N','N','N','Lieferanschrift',200,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:02.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622383,0,543052,544370,573441,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','Y','N','N','N','Referenz',210,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:02.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622404,0,543052,544370,573442,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'The partner from C_Order','Y','Y','N','Y','N','N','N','Auftragspartner',230,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:02.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622373,0,543052,544370,573443,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehÃ¶rigen Kostenstelle','Y','Y','N','Y','N','N','N','Kostenstelle',240,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:02.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622320,0,543052,544370,573444,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Kosten','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)','Y','Y','N','Y','N','N','N','Kosten',250,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:02.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622405,0,543052,544370,573445,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.','Y','Y','N','Y','N','N','N','Produktart',280,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:02.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622334,0,543052,544370,573446,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','Y','N','Y','N','N','N','Preissystem',290,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:03.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622411,0,543052,544370,573447,'F',TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','Y','N','Y','N','N','N','Version Preisliste',300,0,0,TO_TIMESTAMP('2020-10-07 11:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:03.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622327,0,543052,544370,573448,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','Y','N','N','N','Währung',310,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:03.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622347,0,543052,544370,573449,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Kursart','Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.','Y','Y','N','Y','N','N','N','Kursart',320,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:03.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622381,0,543052,544370,573450,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Abweichender Steuersatz','Y','Y','N','Y','N','N','N','Steuer abw.',340,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:03.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622382,0,543052,544370,573451,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Steuer eff.',350,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:03.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622369,0,543052,544370,573452,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Differenz zwischen bestellter und gelieferter Menge','Y','Y','N','Y','N','N','N','Über-/Unterlieferung',380,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:03.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622316,0,543052,544370,573453,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','Y','N','Y','N','N','N','Rechnungsstellung',390,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:03.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622349,0,543052,544370,573454,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.','Y','Y','N','Y','N','N','N','Terminplan Rechnung',420,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:04.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622348,0,543052,544370,573455,'F',TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Bei Geschätspartnern, deren Rechnungs-Terminplan einen Grenzbetrag hat, zeigt dieses Feld an, ob der Grenzbetrag unterschritten ist.','Y','Y','N','Y','N','N','N','Status Terminplan',430,0,0,TO_TIMESTAMP('2020-10-07 11:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:04.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622344,0,543052,544370,573456,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Termin ab dem die Rechnung erstellt werden darf','"Abrechnung ab" beschreibt das Datum an dem der Rechnungskandidat abgerechnet werden kann. Dieses Datum entspricht i.d.R. dem Erstellungsdatum des Rechnungskandidaten, kann aber abweichen wenn beim Geschäftspartner ein Terminplan für Rechnungen hinterlegt wurde.','Y','Y','N','Y','N','N','N','Abrechnung ab',440,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:04.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622328,0,543052,544370,573457,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Datum auf der Rechnung','"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','Y','N','Y','N','N','N','Rechnungsdatum',470,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:04.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622384,0,543052,544370,573458,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','Y','N','Y','N','N','N','Buchungsdatum',480,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:04.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622439,0,543052,544370,573459,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Vorbelegtes Rechnungsdatum',485,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:04.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622365,0,543052,544370,573460,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Qualitätsabzug %',490,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:04.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622367,0,543052,544370,573461,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.','Y','Y','N','Y','N','N','N','Minderwertige Menge (WED)',520,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:05.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622391,0,543052,544370,573462,'F',TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Tax is included in the price ','The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.','Y','Y','N','Y','N','N','N','Preis inkl. Steuern abw.',590,0,0,TO_TIMESTAMP('2020-10-07 11:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:05.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622359,0,543052,544370,573463,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','Y','N','Y','N','N','N','Preis',620,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:05.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622388,0,543052,544370,573464,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Preis Eff. Netto',640,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:05.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622406,0,543052,544370,573465,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Name Rechnungspartner',650,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:05.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622409,0,543052,544370,573466,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege','Y','Y','N','Y','N','N','N','Erhält EDI-Belege',660,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:05.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622410,0,543052,544370,573467,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Beleg soll per EDI übermittelt werden',670,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:05.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622395,0,543052,544370,573468,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Zu ber. Menge vor Qualitätsabzug',710,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:05.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622332,0,543052,544370,573469,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Angabe über den Teil der abweichenden Menge, der bereits in Rechnung gestellt wurde','Sobald eine vom Benutzer angegebene "Zu berechn. Menge abw." komplett in Rechnung gestellt wurde, wird der Wert dieses Feldes sowie die abweichende Menge vom System gelöscht. Dadurch wird verhindert, die abweichenden Menge mehrfach in Rechnung gestellt wird.','Y','Y','N','Y','N','N','N','Zu ber. Menge abw. erl.',730,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:05.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622389,0,543052,544370,573470,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Menge TU',770,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:06.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622385,0,543052,544370,573471,'F',TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Auftrag Total ohne Rabatt',790,0,0,TO_TIMESTAMP('2020-10-07 11:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:06.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622392,0,543052,544370,573472,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','Y','N','Y','N','N','N','Netto der Auftragzeile',800,0,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:06.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622380,0,543052,544370,573473,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N','Freigabe zur Fakturierung',810,80,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:06.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622393,0,543052,544370,573474,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Lieferung/ Wareneingang freigeben',820,0,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:06.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622356,0,543052,544370,573475,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Abzurechnender Teilbetrag, der zur Zeit nicht in Rechnung gestellt werden kann und bei einer Faktur dieses Datensatzes in einen neuen Rechnungsdispo-Datensatz übernommen werden wird.','Y','Y','N','Y','N','N','N','Restbetrag',850,0,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:06.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622419,0,543052,544370,573476,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Zahlungsbedingung',855,0,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:06.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622336,0,543052,544370,573477,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz','Y','Y','N','Y','N','N','N','Aggregator',860,0,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622355,0,543052,544370,573478,'F',TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Kopf-Aggregationsmerkmal',870,0,0,TO_TIMESTAMP('2020-10-07 11:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622337,0,543052,544370,573479,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.','Hinweis: Das Feld ist nur Relevant, wenn der Rechnungskandidat nicht als "zu Verrechnen" markiert ist.<p>
Das Aggregierungsmerkmal wird während der RK-Aktualisierung vom System gesetzt.
Unterschiedliche Rechnungskandidaten werden zu einer Rechnungszeile zusammengefasst, wenn folgende Bedingungen erfüllt sind:
<ul>
<li>Die Kandidaten gehören zu selben Rechnung (d.h. Rechnungsempfänger, Währung usw. sind gleich)</li>
<li>Die Kandidaten haben das gleiche Merkmal</li>
</ul>','Y','Y','N','Y','N','N','N','Zeilen-Aggregationsmerkmal',880,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622338,0,543052,544370,573480,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Optionale Möglichkeit, einzelne Rechnungskandidaten aus einer gemeinsamen Aggregations-Gruppe herauszulösen.','Hinweis: Das Feld ist nur Relevant, wenn der Rechnungskandidat nicht als "zu Verrechnen" markiert ist.<p>
Der Aggregierungs-Zusatz wird bei der Rechnungsgenerierung an das vom System ermittelte Aggregierungsmerkmal angehangen. Somit kann der Bnutzer dafür sorgen, dass ein Rechnungskandidat abweichend von der Systemvorgabe in einer eigenen Rechnungszeile verarbeitet wird, anstatt ihn mit anderen Rechnungskandidaten zusammenzufassen.','Y','Y','N','Y','N','N','N','Aggregations-Zusatz',890,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622354,0,543052,544370,573481,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.','Y','Y','N','Y','N','N','N','Betreuer',900,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622408,0,543052,544370,573482,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','in Verarbeitung',930,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622403,0,543052,544370,573483,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Komplett Abgerechnet (System)',940,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622402,0,543052,544370,573484,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Komplett Abgerechnet abw.',950,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:07.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622357,0,543052,544370,573485,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Kopfbeschreibung',990,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622358,0,543052,544370,573486,'F',TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Schlusstext',1000,0,0,TO_TIMESTAMP('2020-10-07 11:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622375,0,543052,544370,573487,'F',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document / line is printed','The Printed checkbox indicates if this document or line will included when printing.','Y','Y','N','Y','N','N','N','andrucken',1010,0,0,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622370,0,543052,544370,573488,'F',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'ReasonDiscount',1020,0,0,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543053,542277,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','',10,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-10-07T08:47:08.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542277 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:47:08.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542277
;

-- 2020-10-07T08:47:08.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542277, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 1000024
;

-- 2020-10-07T08:47:08.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542874,542277,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542874,544371,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622449,0,543053,544371,573489,'F',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Identifikationsnummer eines Rechnungskandidaten','Y','N','N','N','N','N','N','Rechnungskandidat',10,0,0,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622453,0,543053,544371,573490,'F',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Zu verrechnender Rechn-Kand.',10,10,0,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:08.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622455,0,543053,544371,573491,'F',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Pauschale - Vertragsperiode',20,20,0,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622454,0,543053,544371,573492,'F',TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Abrechnungssatz',30,30,0,TO_TIMESTAMP('2020-10-07 11:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622451,0,543053,544371,573493,'F',TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',40,40,0,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622450,0,543053,544371,573494,'F',TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',50,0,0,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543054,542278,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','',10,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-10-07T08:47:09.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542278 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:47:09.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542278
;

-- 2020-10-07T08:47:09.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542278, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 1000025
;

-- 2020-10-07T08:47:09.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542875,542278,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542875,544372,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622463,0,543054,544372,573495,'F',TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Identifikationsnummer eines Rechnungskandidaten','Y','N','N','Y','N','N','N','Rechnungskandidat',10,0,0,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:09.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622465,0,543054,544372,573496,'F',TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','Y','Y','N','N','Rechnungsposition',20,10,0,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:10.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622467,0,543054,544372,573497,'F',TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die bereits in Rechnung gestellt wurde','Y','N','N','Y','Y','N','N','Berechn. Menge',30,20,0,TO_TIMESTAMP('2020-10-07 11:47:09','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:10.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622466,0,543054,544372,573498,'F',TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','Y','N','N','Belegstatus Rechnung',40,30,0,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:10.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622468,0,543054,544372,573499,'F',TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Definiert Richtlinien zur Aggregation von DatensÃ¤tzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz','Y','N','N','Y','Y','N','N','Aggregator',50,40,0,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:10.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622469,0,543054,544372,573500,'F',TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information fÃ¼r ein Dokument','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','Y','N','N','Notiz',60,50,0,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:10.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622462,0,543054,544372,573501,'F',TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',70,60,0,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:10.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622461,0,543054,544372,573502,'F',TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',80,0,0,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:10.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543055,542279,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','',10,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-10-07T08:47:10.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542279 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:47:10.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542279
;

-- 2020-10-07T08:47:10.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542279, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 1000026
;

-- 2020-10-07T08:47:10.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542876,542279,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:11.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542876,544373,TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-10-07 11:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:11.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622474,0,543055,544373,573503,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Identifikationsnummer eines Rechnungskandidaten','Y','N','N','N','N','N','N','Rechnungskandidat',10,0,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:11.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622475,0,543055,544373,573504,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','Y','Y','N','N','Versand-/Wareneingangsposition',10,10,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:11.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622476,0,543055,544373,573505,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','Y','Y','N','N','Gelieferte Menge',30,20,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:11.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622473,0,543055,544373,573506,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kÃ¶nnen Daten Ã¼ber Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',40,60,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:47:11.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622472,0,543055,544373,573507,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Mandant fÃ¼r diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie kÃ¶nnen keine Daten Ã¼ber Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',50,0,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:47:11.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622484,0,543055,544373,573508,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'QtyDeliveredInUOM_Catch',70,30,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:11.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622487,0,543055,544373,573509,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'QtyDeliveredInUOM_Nominal',80,40,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:11.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622486,0,543055,544373,573510,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'QtyDeliveredInUOM_Override',90,0,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:12.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622485,0,543055,544373,573511,'F',TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'C_UOM_ID',100,50,0,TO_TIMESTAMP('2020-10-07 11:47:11','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:12.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543056,542280,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','',10,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-10-07T08:47:12.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542280 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:47:12.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542280
;

-- 2020-10-07T08:47:12.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542280, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 1000027
;

-- 2020-10-07T08:47:12.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542877,542280,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:12.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542877,544374,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:12.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622494,0,543056,544374,573512,'F',TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der EintrÃ¤ge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der EintrÃ¤ge','Y','N','N','Y','Y','N','N','Reihenfolge',10,10,0,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:12.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622507,0,543056,544374,573513,'F',TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document / line is printed','The Printed checkbox indicates if this document or line will included when printing.','Y','N','N','Y','Y','N','N','andrucken',20,20,0,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:12.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622495,0,543056,544374,573514,'F',TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'If set, this line will be printed before the line to which it links.','Y','N','N','Y','Y','N','N','davor andrucken',30,30,0,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622513,0,543056,544374,573515,'F',TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Detail-Info statt Rechnungszeile andrucken',40,40,0,TO_TIMESTAMP('2020-10-07 11:47:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622496,0,543056,544374,573516,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N','Produkt',50,50,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2020-10-07T08:47:13.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,622510,0,540770,573516,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622514,0,543056,544374,573517,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Produktionsauftrag',60,60,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622498,0,543056,544374,573518,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information fÃ¼r ein Dokument','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','Y','N','N','Notiz',70,70,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622497,0,543056,544374,573519,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',80,80,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622516,0,543056,544374,573520,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Date',85,90,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622509,0,543056,544374,573521,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Percent of the entire amount','Percentage of an amount (up to 100)','Y','N','N','Y','Y','N','N','Percentage',90,100,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:13.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622502,0,543056,544374,573522,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge in Preiseinheit','Die "Bestellte Menge in Preiseinheit" bezeichnet die Menge einer Ware, die bestellt wurde, umgerechnet in die Mengeneinheit auf die sich der Preis bezieht.','Y','N','N','Y','Y','N','N','Bestellte Menge in Preiseinheit',100,110,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:14.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622504,0,543056,544374,573523,'F',TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Eingegebener Preis - der Preis basierend auf der gewÃ¤hlten Mengeneinheit','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet','Y','N','N','Y','Y','N','N','Preis',110,120,0,TO_TIMESTAMP('2020-10-07 11:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:14.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622505,0,543056,544374,573524,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis fÃ¼r das Produkt in AusgangswÃ¤hrung.','Y','N','N','Y','Y','N','N','Einzelpreis',120,130,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:14.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622506,0,543056,544374,573525,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','N','Y','Y','N','N','Rabatt %',130,140,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:14.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622503,0,543056,544374,573526,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Preiseinheit',140,150,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:14.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622500,0,543056,544374,573527,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels fÃ¼r dieses Dokument.','Y','N','N','Y','Y','N','N','Menge',150,160,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:14.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622501,0,543056,544374,573528,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'MaÃŸeinheit','Eine eindeutige (nicht monetÃ¤re) MaÃŸeinheit','Y','N','N','Y','Y','N','N','Maßeinheit',160,170,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:14.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622492,0,543056,544374,573529,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','Y','N','N','Rechnung',170,180,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:14.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622493,0,543056,544374,573530,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','Y','Y','N','N','Rechnungsposition',180,190,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:15.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622512,0,543056,544374,573531,'F',TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Packvorschrift (TU)',190,200,0,TO_TIMESTAMP('2020-10-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:15.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622511,0,543056,544374,573532,'F',TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Menge TU',200,210,0,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-10-07T08:47:15.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622489,0,543056,544374,573533,'F',TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',210,220,0,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:15.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622488,0,543056,544374,573534,'F',TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',220,0,0,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:15.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543057,542281,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-10-07T08:47:15.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542281 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-10-07T08:47:15.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 542281
;

-- 2020-10-07T08:47:15.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 542281, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540754
;

-- 2020-10-07T08:47:15.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542878,542281,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:15.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542878,544375,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:16.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622523,0,543057,544375,573535,'F',TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','C_Flatrate_Term_ID',10,10,0,TO_TIMESTAMP('2020-10-07 11:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:16.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622521,0,543057,544375,573536,'F',TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','N','N','N','C_Invoice_Candidate_Term_ID',20,0,0,TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:16.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622522,0,543057,544375,573537,'F',TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','C_Invoice_Candidate_Assigned_ID',25,30,0,TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:16.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622519,0,543057,544375,573538,'F',TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','IsActive',30,80,0,TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:16.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622518,0,543057,544375,573539,'F',TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','AD_Org_ID',40,90,0,TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:47:16.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,622517,0,543057,544375,573540,'F',TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','AD_Client_ID',50,0,0,TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2020-10-07T08:47:17.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622526,0,543057,544375,573541,'F',TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'C_Flatrate_RefundConfig_ID',60,20,0,TO_TIMESTAMP('2020-10-07 11:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:17.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622527,0,543057,544375,573542,'F',TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'BaseMoneyAmount',70,40,0,TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:17.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622524,0,543057,544375,573543,'F',TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'AssignedMoneyAmount',80,50,0,TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:17.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622525,0,543057,544375,573544,'F',TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'AssignedQuantity',90,60,0,TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:17.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622528,0,543057,544375,573545,'F',TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'IsAssignedQuantityIncludedInSum',100,70,0,TO_TIMESTAMP('2020-10-07 11:47:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:47:32.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='isSOTrx = ''N''',Updated=TO_TIMESTAMP('2020-10-07 11:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543052
;

-- 2020-10-07T08:49:46.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=575902, Description=NULL, Help=NULL, Name='A00ANBNR4',Updated=TO_TIMESTAMP('2020-10-07 11:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540983
;

-- 2020-10-07T08:49:46.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(575902) 
;

-- 2020-10-07T08:49:46.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540983
;

-- 2020-10-07T08:49:46.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540983)
;

-- 2020-10-07T08:49:52.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window_Trl WHERE AD_Language='it_CH' AND AD_Window_ID=540983
;

-- 2020-10-07T08:49:54.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window_Trl WHERE AD_Language='fr_CH' AND AD_Window_ID=540983
;

-- 2020-10-07T08:49:57.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window_Trl WHERE AD_Language='en_GB' AND AD_Window_ID=540983
;

-- 2020-10-07T08:50:09.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=578409, Description=NULL, Help=NULL, Name='Rechnungsdisposition Einkauf',Updated=TO_TIMESTAMP('2020-10-07 11:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540983
;

-- 2020-10-07T08:50:09.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(578409) 
;

-- 2020-10-07T08:50:09.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540983
;

-- 2020-10-07T08:50:09.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540983)
;

-- 2020-10-07T08:50:38.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET PO_Window_ID=540983,Updated=TO_TIMESTAMP('2020-10-07 11:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540270
;

-- 2020-10-07T08:51:13.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Sales Invoice Candidates', Help='Sales Invoice Candidates', Name='Sales Invoice Candidates', PrintName='Sales Invoice Candidates',Updated=TO_TIMESTAMP('2020-10-07 11:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573934 AND AD_Language='en_US'
;

-- 2020-10-07T08:51:13.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573934,'en_US') 
;

-- 2020-10-07T08:52:53.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=573934, Description='Fenster enthält so genannte "Rechnungskandidaten", die vor der Rechnungsstellung noch angepasst werden können', IsSOTrx='Y', Name='Rechnungsdisposition', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2020-10-07 11:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000104
;

-- 2020-10-07T08:52:53.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(573934) 
;

-- 2020-10-07T08:53:16.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,578409,541537,0,540983,TO_TIMESTAMP('2020-10-07 11:53:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Purchase Invoice Candidates','Y','N','N','N','N','Rechnungsdisposition Einkauf',TO_TIMESTAMP('2020-10-07 11:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-07T08:53:16.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541537 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-10-07T08:53:16.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541537, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541537)
;

-- 2020-10-07T08:53:16.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(578409) 
;

-- 2020-10-07T08:53:24.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540236 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540310 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=217 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=178 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=377 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=347 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540334 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:24.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=458, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- 2020-10-07T08:53:29.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;


-- 2020-10-07T11:39:50.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsSOTrx='N',Updated=TO_TIMESTAMP('2020-10-07 14:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540983
;













-- old window: 540092
-- new window: 540983
insert into ad_window_access
(ad_window_id, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, isreadwrite)
select 540983,
       ad_role_id,
       ad_client_id,
       ad_org_id,
       isactive,
       '2020-09-30 16:54:38.862775+03',
       createdby,
       '2020-09-30 16:54:38.862775+03',
       updatedby,
       isreadwrite
from ad_window_access
where ad_window_id = 540092
  and ad_role_id not in (SELECT wa.ad_role_id -- do not create records where they already exist
                         FROM ad_window_access wa
                         WHERE wa.ad_window_id = 540983
)
;


