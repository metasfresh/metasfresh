-- 2019-08-21T18:56:45.283Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,543156,0,540677,TO_TIMESTAMP('2019-08-21 20:56:45','YYYY-MM-DD HH24:MI:SS'),100,'U','UI Element','Y','N','N','N','N','Y','UI Element','N',TO_TIMESTAMP('2019-08-21 20:56:45','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-08-21T18:56:45.284Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540677 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-08-21T18:56:45.287Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(543156) 
;

-- 2019-08-21T18:56:45.290Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540677
;

-- 2019-08-21T18:56:45.290Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540677)
;

-- 2019-08-21T18:57:44.167Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572754,0,541848,380,540677,'Y',TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Fehlermeldung','U','N','The Error Message Tab displays error messages that have been generated.  They can be deleted with an automated clean up process.','AD_Error','Y','N','Y','Y','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Fehlermeldung','N',10,0,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:44.169Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541848 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T18:57:44.171Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572754) 
;

-- 2019-08-21T18:57:44.175Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541848)
;

-- 2019-08-21T18:57:44.249Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4657,582814,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100,14,'U',0,'Y','N','N','N','N','N','N','N','Fehler',10,10,1,1,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:44.250Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:44.254Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1310) 
;

-- 2019-08-21T18:57:44.259Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582814
;

-- 2019-08-21T18:57:44.260Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582814)
;

-- 2019-08-21T18:57:44.331Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4658,582815,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',20,20,1,1,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:44.333Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:44.336Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T18:57:44.499Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582815
;

-- 2019-08-21T18:57:44.499Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582815)
;

-- 2019-08-21T18:57:44.607Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4659,582816,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',30,30,1,1,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:44.608Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:44.611Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T18:57:44.746Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582816
;

-- 2019-08-21T18:57:44.747Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582816)
;

-- 2019-08-21T18:57:44.924Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4665,582817,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100,'',60,'U','',0,'Y','Y','Y','N','N','N','N','N','Name',40,40,999,1,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:44.925Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:44.926Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-08-21T18:57:44.957Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582817
;

-- 2019-08-21T18:57:44.957Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582817)
;

-- 2019-08-21T18:57:45.028Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4660,582818,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2019-08-21 20:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:45.029Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:45.032Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T18:57:45.184Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582818
;

-- 2019-08-21T18:57:45.184Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582818)
;

-- 2019-08-21T18:57:45.284Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4667,582819,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:45','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag',14,'U','Definiert die Sprache für Anzeige und Aufbereitung',0,'Y','Y','Y','N','N','N','Y','N','Sprache',60,60,1,1,TO_TIMESTAMP('2019-08-21 20:57:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:45.285Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:45.288Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109) 
;

-- 2019-08-21T18:57:45.302Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582819
;

-- 2019-08-21T18:57:45.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582819)
;

-- 2019-08-21T18:57:45.376Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4666,582820,1001031,0,541848,0,TO_TIMESTAMP('2019-08-21 20:57:45','YYYY-MM-DD HH24:MI:SS'),100,'Validierungscode',60,'U','Der "Validierungscode" zeigt Datum, Zeit und Fehlerinformation.',0,'Y','Y','Y','N','N','N','N','N','Validierungscode',70,70,1,999,1,TO_TIMESTAMP('2019-08-21 20:57:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:57:45.377Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:57:45.379Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001031) 
;

-- 2019-08-21T18:57:45.381Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582820
;

-- 2019-08-21T18:57:45.382Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582820)
;

-- 2019-08-21T18:57:58.562Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582814
;

-- 2019-08-21T18:57:58.564Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582814
;

-- 2019-08-21T18:57:58.572Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582814
;

-- 2019-08-21T18:57:58.578Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582815
;

-- 2019-08-21T18:57:58.579Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582815
;

-- 2019-08-21T18:57:58.581Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582815
;

-- 2019-08-21T18:57:58.586Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582816
;

-- 2019-08-21T18:57:58.586Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582816
;

-- 2019-08-21T18:57:58.588Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582816
;

-- 2019-08-21T18:57:58.593Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582817
;

-- 2019-08-21T18:57:58.594Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582817
;

-- 2019-08-21T18:57:58.596Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582817
;

-- 2019-08-21T18:57:58.601Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582818
;

-- 2019-08-21T18:57:58.601Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582818
;

-- 2019-08-21T18:57:58.603Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582818
;

-- 2019-08-21T18:57:58.608Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582819
;

-- 2019-08-21T18:57:58.608Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582819
;

-- 2019-08-21T18:57:58.610Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582819
;

-- 2019-08-21T18:57:58.614Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582820
;

-- 2019-08-21T18:57:58.615Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582820
;

-- 2019-08-21T18:57:58.617Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=582820
;

-- 2019-08-21T18:57:58.619Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=541848
;

-- 2019-08-21T18:57:58.621Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=541848
;

-- 2019-08-21T18:58:37.938Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,543156,0,541849,540783,540677,'Y',TO_TIMESTAMP('2019-08-21 20:58:37','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','AD_UI_Element','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'UI Element','N',10,0,TO_TIMESTAMP('2019-08-21 20:58:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:58:37.940Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541849 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T18:58:37.942Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(543156) 
;

-- 2019-08-21T18:58:37.945Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541849)
;

-- 2019-08-21T18:59:01.458Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555030,582821,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:01','YYYY-MM-DD HH24:MI:SS'),100,'Register auf einem Fenster',10,'U','"Register" definiert ein Register zur Anzeige auf einem Fenster.',0,'Y','N','N','N','N','N','N','N','Register',0,0,1,1,TO_TIMESTAMP('2019-08-21 20:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:01.459Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:01.463Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(125) 
;

-- 2019-08-21T18:59:01.472Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582821
;

-- 2019-08-21T18:59:01.473Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582821)
;

-- 2019-08-21T18:59:01.550Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554973,582822,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:01','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','Mandant',0,0,1,1,TO_TIMESTAMP('2019-08-21 20:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:01.552Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:01.556Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T18:59:01.825Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582822
;

-- 2019-08-21T18:59:01.826Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582822)
;

-- 2019-08-21T18:59:01.933Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554974,582823,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',0,0,1,1,TO_TIMESTAMP('2019-08-21 20:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:01.934Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:01.938Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T18:59:02.181Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582823
;

-- 2019-08-21T18:59:02.181Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582823)
;

-- 2019-08-21T18:59:02.253Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554997,582824,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','UI Element Group',10,10,1,1,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:02.254Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:02.257Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543154) 
;

-- 2019-08-21T18:59:02.261Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582824
;

-- 2019-08-21T18:59:02.262Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582824)
;

-- 2019-08-21T18:59:02.317Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554981,582825,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100,'',255,'U','',0,'Y','Y','Y','N','N','N','N','N','Name',20,20,1,1,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:02.319Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:02.322Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-08-21T18:59:02.383Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582825
;

-- 2019-08-21T18:59:02.383Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582825)
;

-- 2019-08-21T18:59:02.450Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554982,582826,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100,2000,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',30,30,1,1,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:02.452Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:02.455Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-08-21T18:59:02.526Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582826
;

-- 2019-08-21T18:59:02.527Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582826)
;

-- 2019-08-21T18:59:02.602Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554983,582827,1001739,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint',2000,'U','The Help field contains a hint, comment or help about the use of this item.',0,'Y','Y','Y','N','N','N','N','N','Kommentar/Hilfe',40,40,1,1,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:02.604Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:02.607Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001739) 
;

-- 2019-08-21T18:59:02.608Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582827
;

-- 2019-08-21T18:59:02.609Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582827)
;

-- 2019-08-21T18:59:02.681Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554977,582828,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:02.683Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:02.686Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T18:59:02.975Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582828
;

-- 2019-08-21T18:59:02.976Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582828)
;

-- 2019-08-21T18:59:03.089Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557048,582829,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Element type',60,60,1,1,TO_TIMESTAMP('2019-08-21 20:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.090Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.092Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543396) 
;

-- 2019-08-21T18:59:03.093Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582829
;

-- 2019-08-21T18:59:03.094Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582829)
;

-- 2019-08-21T18:59:03.157Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554984,582830,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Ein Feld einer Datenbanktabelle',10,'@AD_UI_ElementType@=F','U','"Feld" bezeichnet ein Feld einer Datenbanktabelle.',0,'Y','Y','Y','N','N','N','N','N','Feld',70,70,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.158Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.159Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(107) 
;

-- 2019-08-21T18:59:03.160Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582830
;

-- 2019-08-21T18:59:03.161Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582830)
;

-- 2019-08-21T18:59:03.221Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557050,582831,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,10,'@AD_UI_ElementType@=L','U',0,'Y','Y','Y','N','N','N','N','N','Labels content tab',80,80,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.222Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.224Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543398) 
;

-- 2019-08-21T18:59:03.224Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582831
;

-- 2019-08-21T18:59:03.224Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582831)
;

-- 2019-08-21T18:59:03.289Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557051,582832,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,10,'@AD_UI_ElementType@=L','U',0,'Y','Y','Y','N','N','N','N','Y','Labels selector field',90,90,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.290Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.291Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543399) 
;

-- 2019-08-21T18:59:03.292Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582832
;

-- 2019-08-21T18:59:03.292Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582832)
;

-- 2019-08-21T18:59:03.366Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558609,582833,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,1,'@AD_UI_ElementType@=L','U',0,'Y','Y','Y','N','N','N','N','N','Allow filtering',100,100,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.366Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.368Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543774) 
;

-- 2019-08-21T18:59:03.369Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582833
;

-- 2019-08-21T18:59:03.369Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582833)
;

-- 2019-08-21T18:59:03.443Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555023,582834,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','Y','Y','N','N','N','N','N','UI Style',110,110,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.444Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.447Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543155) 
;

-- 2019-08-21T18:59:03.448Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582834
;

-- 2019-08-21T18:59:03.449Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582834)
;

-- 2019-08-21T18:59:03.534Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556819,582835,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Widget size',120,120,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.535Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.537Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543348) 
;

-- 2019-08-21T18:59:03.538Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582835
;

-- 2019-08-21T18:59:03.539Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582835)
;

-- 2019-08-21T18:59:03.616Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559554,582836,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','Y','Y','N','N','N','N','N','Media Types',130,130,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.617Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.620Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543928) 
;

-- 2019-08-21T18:59:03.622Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582836
;

-- 2019-08-21T18:59:03.622Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582836)
;

-- 2019-08-21T18:59:03.691Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554986,582837,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Advanced field',140,140,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.692Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.695Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543157) 
;

-- 2019-08-21T18:59:03.696Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582837
;

-- 2019-08-21T18:59:03.697Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582837)
;

-- 2019-08-21T18:59:03.763Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555025,582838,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Determines, if this field is displayed',1,'U','If the field is displayed, the field Display Logic will determine at runtime, if it is actually displayed',0,'Y','Y','Y','N','N','N','N','N','Displayed',150,150,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.765Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.768Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(368) 
;

-- 2019-08-21T18:59:03.771Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582838
;

-- 2019-08-21T18:59:03.771Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582838)
;

-- 2019-08-21T18:59:03.845Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555000,582839,1001784,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'U','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','N','Y','Reihenfolge',160,160,1,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.846Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.849Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001784) 
;

-- 2019-08-21T18:59:03.851Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582839
;

-- 2019-08-21T18:59:03.852Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582839)
;

-- 2019-08-21T18:59:03.962Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555026,582840,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Determines, if this field is displayed in grid mode',1,'U','If the field is displayed in grid, the field Display Logic will determine at runtime, if it is actually displayed',0,'Y','Y','Y','N','N','N','Y','N','Displayed in Grid',170,170,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:03.963Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:03.966Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(56998) 
;

-- 2019-08-21T18:59:03.968Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582840
;

-- 2019-08-21T18:59:03.968Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582840)
;

-- 2019-08-21T18:59:04.055Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555027,582841,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'U','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','Y','Y','Reihenfolge (grid)',180,180,1,1,TO_TIMESTAMP('2019-08-21 20:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:04.056Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:04.059Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(56999) 
;

-- 2019-08-21T18:59:04.061Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582841
;

-- 2019-08-21T18:59:04.061Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582841)
;

-- 2019-08-21T18:59:04.136Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555028,582842,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100,'Determines, if this field is displayed in Side list',1,'U',0,'Y','Y','Y','N','N','N','Y','N','Displayed in Side List',190,190,1,1,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:04.138Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:04.140Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543164) 
;

-- 2019-08-21T18:59:04.141Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582842
;

-- 2019-08-21T18:59:04.142Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582842)
;

-- 2019-08-21T18:59:04.217Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555029,582843,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','Y','Y','Reihenfolge (Side List)',200,200,1,1,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:04.219Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:04.222Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543165) 
;

-- 2019-08-21T18:59:04.223Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582843
;

-- 2019-08-21T18:59:04.224Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582843)
;

-- 2019-08-21T18:59:04.305Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560853,582844,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100,'',1,'U','If selected, the importer will check for multiline text',0,'Y','Y','N','N','N','N','N','N','Multi Line',210,1,1,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:04.306Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:04.309Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543430) 
;

-- 2019-08-21T18:59:04.310Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582844
;

-- 2019-08-21T18:59:04.311Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582844)
;

-- 2019-08-21T18:59:04.392Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560854,582845,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100,10,'@IsMultiLine/N@=Y','U',0,'Y','Y','N','N','N','N','N','Y','Lines Count',220,1,1,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:04.393Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:04.396Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544226) 
;

-- 2019-08-21T18:59:04.397Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582845
;

-- 2019-08-21T18:59:04.398Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582845)
;

-- 2019-08-21T18:59:04.463Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554980,582846,0,541849,0,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','UI Element',0,1,1,TO_TIMESTAMP('2019-08-21 20:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:59:04.464Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T18:59:04.467Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543156) 
;

-- 2019-08-21T18:59:04.468Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582846
;

-- 2019-08-21T18:59:04.469Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582846)
;

-- 2019-08-21T18:59:22.641Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-08-21 20:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541849
;

-- 2019-08-21T19:03:02.985Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,543156,541333,0,540677,TO_TIMESTAMP('2019-08-21 21:03:02','YYYY-MM-DD HH24:MI:SS'),100,'U','UI Element','Y','N','N','N','N','UI Element',TO_TIMESTAMP('2019-08-21 21:03:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:03:02.987Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541333 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-08-21T19:03:02.989Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541333, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541333)
;

-- 2019-08-21T19:03:02.997Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(543156) 
;

-- 2019-08-21T19:03:03.564Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53203 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.566Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=586 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.567Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53251 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.568Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540994 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.569Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=138 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.569Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541160 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.570Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=139 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.571Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=249 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.571Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=141 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.572Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53455 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.573Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=216 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.573Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=589 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.574Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=140 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.575Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=300 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.576Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=142 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.576Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=295 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.577Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53012 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.578Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=143 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.579Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=201 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.579Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=176 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.580Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53086 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.581Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=239 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.582Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=517 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.583Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=499 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.584Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53221 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.585Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53222 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.585Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53089 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.586Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53267 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.587Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53568 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.588Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540037 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.588Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540492 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.589Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53266 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.590Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540735 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.590Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540575 AND AD_Tree_ID=10
;

-- 2019-08-21T19:03:03.591Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541333 AND AD_Tree_ID=10
;

-- 2019-08-21T19:04:14.180Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541849,541404,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:04:14.181Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541404 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:04:14.257Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541802,541404,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.329Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541803,541404,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.396Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541802,542725,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.477Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582824,0,541849,542725,560506,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','UI Element Group',10,10,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.551Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582825,0,541849,542725,560507,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Name',20,20,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.628Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582826,0,541849,542725,560508,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',30,30,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.703Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582827,0,541849,542725,560509,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','Y','N','Kommentar/Hilfe',40,40,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.775Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582828,0,541849,542725,560510,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.848Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582829,0,541849,542725,560511,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Element type',60,60,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.912Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582830,0,541849,542725,560512,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Ein Feld einer Datenbanktabelle','"Feld" bezeichnet ein Feld einer Datenbanktabelle.','Y','N','Y','Y','N','Feld',70,70,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:14.972Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582831,0,541849,542725,560513,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Labels content tab',80,80,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.027Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582832,0,541849,542725,560514,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Labels selector field',90,90,0,TO_TIMESTAMP('2019-08-21 21:04:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.106Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582833,0,541849,542725,560515,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Allow filtering',100,100,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.174Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582834,0,541849,542725,560516,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','UI Style',110,110,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.239Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582835,0,541849,542725,560517,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Widget size',120,120,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.313Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582836,0,541849,542725,560518,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Media Types',130,130,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.371Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582837,0,541849,542725,560519,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Advanced field',140,140,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.428Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582838,0,541849,542725,560520,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Determines, if this field is displayed','If the field is displayed, the field Display Logic will determine at runtime, if it is actually displayed','Y','N','Y','Y','N','Displayed',150,150,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.492Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582839,0,541849,542725,560521,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','Y','N','Reihenfolge',160,160,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.561Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582840,0,541849,542725,560522,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Determines, if this field is displayed in grid mode','If the field is displayed in grid, the field Display Logic will determine at runtime, if it is actually displayed','Y','N','Y','Y','N','Displayed in Grid',170,170,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.631Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582841,0,541849,542725,560523,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','Y','N','Reihenfolge (grid)',180,180,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.690Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582842,0,541849,542725,560524,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Determines, if this field is displayed in Side list','Y','N','Y','Y','N','Displayed in Side List',190,190,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.760Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582843,0,541849,542725,560525,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Reihenfolge (Side List)',200,200,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.831Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582844,0,541849,542725,560526,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'','If selected, the importer will check for multiline text','Y','N','Y','N','N','Multi Line',210,0,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:04:15.907Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582845,0,541849,542725,560527,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lines Count',220,0,0,TO_TIMESTAMP('2019-08-21 21:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:08:00.066Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560508
;

-- 2019-08-21T19:08:00.068Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560509
;

-- 2019-08-21T19:08:00.069Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560512
;

-- 2019-08-21T19:08:00.070Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560506
;

-- 2019-08-21T19:08:00.072Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560510
;

-- 2019-08-21T19:08:00.073Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560511
;

-- 2019-08-21T19:08:00.074Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560513
;

-- 2019-08-21T19:08:00.075Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560514
;

-- 2019-08-21T19:08:00.076Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560515
;

-- 2019-08-21T19:08:00.078Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560516
;

-- 2019-08-21T19:08:00.079Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560517
;

-- 2019-08-21T19:08:00.080Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560518
;

-- 2019-08-21T19:08:00.081Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560519
;

-- 2019-08-21T19:08:00.082Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560520
;

-- 2019-08-21T19:08:00.083Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560521
;

-- 2019-08-21T19:08:00.084Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560522
;

-- 2019-08-21T19:08:00.085Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560523
;

-- 2019-08-21T19:08:00.086Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560524
;

-- 2019-08-21T19:08:00.087Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2019-08-21 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560525
;

-- 2019-08-21T19:08:36.042Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554984
;

-- 2019-08-21T19:08:39.484Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2019-08-21 21:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554974
;

-- 2019-08-21T19:08:42.687Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555030
;

-- 2019-08-21T19:08:46.072Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-08-21 21:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554997
;

-- 2019-08-21T19:08:49.334Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557048
;

-- 2019-08-21T19:08:54.420Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554975
;

-- 2019-08-21T19:08:58.830Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554977
;

-- 2019-08-21T19:09:02.021Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554986
;

-- 2019-08-21T19:09:03.575Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558609
;

-- 2019-08-21T19:09:04.752Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555025
;

-- 2019-08-21T19:09:10.387Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555026
;

-- 2019-08-21T19:09:19.836Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560853
;

-- 2019-08-21T19:09:24.660Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555023
;

-- 2019-08-21T19:09:26.444Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554978
;

-- 2019-08-21T19:22:01.068Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568590,143,0,19,540783,'AD_Window_ID','(            select w.ad_window_id            from ad_field f                     JOIN ad_tab t on f.ad_tab_id = t.ad_tab_id                     JOIN ad_window w on t.ad_window_id = w.ad_window_id            where f.ad_field_id = ad_ui_element.ad_field_id )',TO_TIMESTAMP('2019-08-21 21:22:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Eingabe- oder Anzeige-Fenster','D',10,'Das Feld "Fenster" identifiziert ein bestimmtes Fenster im system.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Fenster',0,0,TO_TIMESTAMP('2019-08-21 21:22:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-08-21T19:22:01.070Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568590 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-08-21T19:22:01.073Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(143) 
;

-- 2019-08-21T19:22:22.115Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568590,582847,0,541849,0,TO_TIMESTAMP('2019-08-21 21:22:21','YYYY-MM-DD HH24:MI:SS'),100,'Eingabe- oder Anzeige-Fenster',0,'U','Das Feld "Fenster" identifiziert ein bestimmtes Fenster im system.',0,'Y','Y','Y','N','N','N','N','N','Fenster',230,210,0,1,1,TO_TIMESTAMP('2019-08-21 21:22:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:22:22.117Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:22:22.122Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(143) 
;

-- 2019-08-21T19:22:22.153Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582847
;

-- 2019-08-21T19:22:22.154Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582847)
;

-- 2019-08-21T19:23:03.271Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-08-21 21:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568590
;

-- 2019-08-21T19:23:53.733Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541803,542726,TO_TIMESTAMP('2019-08-21 21:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Info',10,TO_TIMESTAMP('2019-08-21 21:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:24:07.857Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582847,0,541849,542726,560528,'F',TO_TIMESTAMP('2019-08-21 21:24:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fenster',10,0,0,TO_TIMESTAMP('2019-08-21 21:24:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:28:31.498Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-08-21 21:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568590
;

