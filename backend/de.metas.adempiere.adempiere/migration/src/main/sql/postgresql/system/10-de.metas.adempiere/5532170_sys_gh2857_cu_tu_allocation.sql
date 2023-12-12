-- INSERT INTO public.ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (1000510, 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, null, 'de.metas.handlingunits', 'Gültig ab', 'Gültig ab', 'Gültig ab inklusiv (erster Tag)', '"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.', null, null, null, null, null, null, null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000510, 'de_CH', 0, 0, 'Y', '2021-07-26 09:18:13.210779 +00:00', -1, '2021-07-26 09:18:13.210779 +00:00', -1, 'Gültig ab', 'Gültig ab', 'Gültig ab inklusiv (erster Tag)', '"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000510, 'de_DE', 0, 0, 'Y', '2018-11-26 07:57:00.353716 +00:00', 100, '2018-11-26 07:57:00.353716 +00:00', 100, 'Gültig ab', 'Gültig ab', 'Gültig ab inklusiv (erster Tag)', '"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000510, 'en_US', 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, 'Valid from', 'Valid from', 'Valid from including this date (first day)', 'The Valid From date indicates the first day of a date range', null, null, null, null, 'Y', null, null, null, null, 'N', null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000510, 'nl_NL', 0, 0, 'Y', '2021-07-26 09:18:17.647262 +00:00', -1, '2021-07-26 09:18:17.647262 +00:00', -1, 'Gültig ab', 'Gültig ab', 'Gültig ab inklusiv (erster Tag)', '"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null);

-- 2019-09-26T12:24:32.310Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,573566,0,540717,TO_TIMESTAMP('2019-09-26 15:24:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','Packvorschrift Nachweis','N',TO_TIMESTAMP('2019-09-26 15:24:32','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-09-26T12:24:32.310Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540717 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-09-26T12:24:32.358Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(573566) 
;

-- 2019-09-26T12:24:32.377Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540717
;

-- 2019-09-26T12:24:32.413Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540717)
;

-- 2019-09-26T12:29:26.177Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,542132,0,542022,540508,540717,'Y',TO_TIMESTAMP('2019-09-26 15:29:26','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','M_HU_PI_Item_Product','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Packvorschrift','N',10,0,TO_TIMESTAMP('2019-09-26 15:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:29:26.197Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542022 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-26T12:29:26.222Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(542132) 
;

-- 2019-09-26T12:29:26.227Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542022)
;

-- 2019-09-26T12:30:43.877Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549820,588905,0,542022,0,TO_TIMESTAMP('2019-09-26 15:30:43','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Jedes Produkt erlauben',0,0,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:43.886Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:43.896Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542251) 
;

-- 2019-09-26T12:30:43.906Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588905
;

-- 2019-09-26T12:30:43.906Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588905)
;

-- 2019-09-26T12:30:43.996Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549182,588906,0,542022,0,TO_TIMESTAMP('2019-09-26 15:30:43','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',0,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:43.996Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:44.001Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-09-26T12:30:44.096Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588906
;

-- 2019-09-26T12:30:44.096Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588906)
;

-- 2019-09-26T12:30:44.206Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549185,588907,0,542022,0,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',0,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:44.206Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:44.217Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-09-26T12:30:44.296Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588907
;

-- 2019-09-26T12:30:44.296Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588907)
;

-- 2019-09-26T12:30:44.466Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549186,588908,0,542022,0,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:44.466Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:44.477Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-09-26T12:30:44.556Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588908
;

-- 2019-09-26T12:30:44.556Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588908)
;

-- 2019-09-26T12:30:44.667Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549183,588909,0,542022,0,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',0,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:44.667Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:44.676Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-09-26T12:30:44.781Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588909
;

-- 2019-09-26T12:30:44.781Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588909)
;

-- 2019-09-26T12:30:44.886Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549187,588910,542132,0,542022,0,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','Packvorschrift',0,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:44.886Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:44.891Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132) 
;

-- 2019-09-26T12:30:44.901Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588910
;

-- 2019-09-26T12:30:44.901Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588910)
;

-- 2019-09-26T12:30:44.987Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549180,588911,0,542022,65,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-09-26 15:30:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:44.997Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:45.006Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-26T12:30:45.481Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588911
;

-- 2019-09-26T12:30:45.481Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588911)
;

-- 2019-09-26T12:30:45.616Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549181,588912,0,542022,78,TO_TIMESTAMP('2019-09-26 15:30:45','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-09-26 15:30:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:45.616Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:45.627Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-26T12:30:46.088Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588912
;

-- 2019-09-26T12:30:46.089Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588912)
;

-- 2019-09-26T12:30:46.196Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550222,588913,0,542022,190,TO_TIMESTAMP('2019-09-26 15:30:46','YYYY-MM-DD HH24:MI:SS'),100,'',40,'D','',0,'Y','Y','Y','N','N','N','Y','N','Name',30,140,999,1,TO_TIMESTAMP('2019-09-26 15:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:46.207Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:46.216Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-09-26T12:30:46.336Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588913
;

-- 2019-09-26T12:30:46.336Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588913)
;

-- 2019-09-26T12:30:46.432Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549284,588914,0,542022,184,TO_TIMESTAMP('2019-09-26 15:30:46','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',15,'@IsAllowAnyProduct@=''N''','D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',40,30,1,1,TO_TIMESTAMP('2019-09-26 15:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:46.436Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:46.447Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-09-26T12:30:46.671Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588914
;

-- 2019-09-26T12:30:46.671Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588914)
;

-- 2019-09-26T12:30:46.776Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549184,588915,0,542022,78,TO_TIMESTAMP('2019-09-26 15:30:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','Y','Aktiv',50,40,1,1,TO_TIMESTAMP('2019-09-26 15:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:46.776Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:46.786Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-26T12:30:47.256Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588915
;

-- 2019-09-26T12:30:47.256Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588915)
;

-- 2019-09-26T12:30:47.366Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549283,588916,0,542022,138,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100,15,'D',0,'Y','Y','Y','N','N','N','N','N','Packvorschrift Position',60,50,1,1,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:47.366Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:47.376Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542133) 
;

-- 2019-09-26T12:30:47.386Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588916
;

-- 2019-09-26T12:30:47.386Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588916)
;

-- 2019-09-26T12:30:47.467Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549387,588917,0,542022,121,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100,512,'D',0,'Y','Y','Y','N','N','N','Y','N','Beschreibung',70,60,0,999,1,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:47.476Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:47.476Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-26T12:30:47.566Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588917
;

-- 2019-09-26T12:30:47.566Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588917)
;

-- 2019-09-26T12:30:47.666Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,69414,588918,0,542022,140,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Unbestimmte Kapazität',80,70,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:47.666Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:47.676Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(56560) 
;

-- 2019-09-26T12:30:47.676Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588918
;

-- 2019-09-26T12:30:47.676Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588918)
;

-- 2019-09-26T12:30:47.777Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549285,588919,0,542022,54,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100,'Menge',15,'@IsInfiniteCapacity@=N','D','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.',0,'Y','Y','Y','N','N','N','N','Y','Menge',90,80,1,1,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:47.777Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:47.777Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2019-09-26T12:30:47.827Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588919
;

-- 2019-09-26T12:30:47.827Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588919)
;

-- 2019-09-26T12:30:47.927Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549286,588920,0,542022,116,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',15,'D','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','Y','Y','Maßeinheit',100,90,1,1,TO_TIMESTAMP('2019-09-26 15:30:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:47.937Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:47.941Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2019-09-26T12:30:48.084Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588920
;

-- 2019-09-26T12:30:48.085Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588920)
;

-- 2019-09-26T12:30:48.176Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549767,588921,1001811,0,542022,249,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',0,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',110,100,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:48.176Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:48.176Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001811) 
;

-- 2019-09-26T12:30:48.186Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588921
;

-- 2019-09-26T12:30:48.186Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588921)
;

-- 2019-09-26T12:30:48.277Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549789,588922,1000510,0,542022,71,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)',7,'D','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','N','N','N','N','N','Gültig ab',120,110,-1,1,1,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:48.277Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:48.277Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000510) 
;

-- 2019-09-26T12:30:48.286Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588922
;

-- 2019-09-26T12:30:48.286Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588922)
;

-- 2019-09-26T12:30:48.386Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549790,588923,0,542022,95,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)',7,'D','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','N','N','N','N','Y','Gültig bis',130,120,1,1,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:48.386Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:48.386Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2019-09-26T12:30:48.401Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588923
;

-- 2019-09-26T12:30:48.401Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588923)
;

-- 2019-09-26T12:30:48.487Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549977,588924,0,542022,87,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation der Trade Unit durch European Article Number',14,'D',0,'Y','Y','Y','N','N','N','N','N','TU-EAN',140,130,0,1,1,TO_TIMESTAMP('2019-09-26 15:30:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:30:48.487Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-26T12:30:48.497Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(603) 
;

-- 2019-09-26T12:30:48.531Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588924
;

-- 2019-09-26T12:30:48.531Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(588924)
;

-- 2019-09-26T12:34:23.486Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542022,541574,TO_TIMESTAMP('2019-09-26 15:34:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-26 15:34:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-09-26T12:34:23.486Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541574 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-26T12:34:35.846Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542003,541574,TO_TIMESTAMP('2019-09-26 15:34:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-26 15:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:34:38.850Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542004,541574,TO_TIMESTAMP('2019-09-26 15:34:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-26 15:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:35:09.024Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542003,543029,TO_TIMESTAMP('2019-09-26 15:35:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','Product',10,'primary',TO_TIMESTAMP('2019-09-26 15:35:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:36:52.175Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542003,543030,TO_TIMESTAMP('2019-09-26 15:36:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','Sprache',20,TO_TIMESTAMP('2019-09-26 15:36:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:39:38.771Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588913,0,542022,562968,543029,'F',TO_TIMESTAMP('2019-09-26 15:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2019-09-26 15:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:40:31.075Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588914,0,542022,562969,543029,'F',TO_TIMESTAMP('2019-09-26 15:40:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Product',20,0,0,TO_TIMESTAMP('2019-09-26 15:40:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:40:54.359Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588910,0,542022,562970,543029,'F',TO_TIMESTAMP('2019-09-26 15:40:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Packing Item',30,0,0,TO_TIMESTAMP('2019-09-26 15:40:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:41:35.698Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588916,0,542022,562971,543029,'F',TO_TIMESTAMP('2019-09-26 15:41:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Packing Instruction Position',40,0,0,TO_TIMESTAMP('2019-09-26 15:41:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:42:20.974Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588921,0,542022,562972,543030,'F',TO_TIMESTAMP('2019-09-26 15:42:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Business partner',10,0,0,TO_TIMESTAMP('2019-09-26 15:42:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:43:17.037Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588919,0,542022,562973,543030,'F',TO_TIMESTAMP('2019-09-26 15:43:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Quantity',20,0,0,TO_TIMESTAMP('2019-09-26 15:43:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:43:34.962Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588920,0,542022,562974,543030,'F',TO_TIMESTAMP('2019-09-26 15:43:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UOM',30,0,0,TO_TIMESTAMP('2019-09-26 15:43:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:43:56.266Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588917,0,542022,562975,543030,'F',TO_TIMESTAMP('2019-09-26 15:43:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',40,0,0,TO_TIMESTAMP('2019-09-26 15:43:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:46:37.644Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542004,543031,TO_TIMESTAMP('2019-09-26 15:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-09-26 15:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:47:44.249Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588915,0,542022,562976,543031,'F',TO_TIMESTAMP('2019-09-26 15:47:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2019-09-26 15:47:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:50:23.823Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588918,0,542022,562977,543031,'F',TO_TIMESTAMP('2019-09-26 15:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Infinite Capacity',20,0,0,TO_TIMESTAMP('2019-09-26 15:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:53:44.697Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588905,0,542022,562978,543031,'F',TO_TIMESTAMP('2019-09-26 15:53:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Allow Any Product',30,0,0,TO_TIMESTAMP('2019-09-26 15:53:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:55:21.646Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542004,543032,TO_TIMESTAMP('2019-09-26 15:55:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2019-09-26 15:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:55:29.490Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542004,543033,TO_TIMESTAMP('2019-09-26 15:55:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2019-09-26 15:55:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:56:11.029Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588906,0,542022,562979,543032,'F',TO_TIMESTAMP('2019-09-26 15:56:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Created',10,0,0,TO_TIMESTAMP('2019-09-26 15:56:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:56:20.872Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588907,0,542022,562980,543032,'F',TO_TIMESTAMP('2019-09-26 15:56:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Updated',20,0,0,TO_TIMESTAMP('2019-09-26 15:56:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:56:51.842Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588922,0,542022,562981,543032,'F',TO_TIMESTAMP('2019-09-26 15:56:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Valid from',30,0,0,TO_TIMESTAMP('2019-09-26 15:56:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:57:07.136Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588923,0,542022,562982,543032,'F',TO_TIMESTAMP('2019-09-26 15:57:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Valid to',40,0,0,TO_TIMESTAMP('2019-09-26 15:57:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:57:20.266Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588908,0,542022,562983,543032,'F',TO_TIMESTAMP('2019-09-26 15:57:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Updated by',50,0,0,TO_TIMESTAMP('2019-09-26 15:57:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:57:44.333Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588909,0,542022,562984,543032,'F',TO_TIMESTAMP('2019-09-26 15:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Created by',60,0,0,TO_TIMESTAMP('2019-09-26 15:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:58:54.316Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588912,0,542022,562985,543033,'F',TO_TIMESTAMP('2019-09-26 15:58:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2019-09-26 15:58:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T12:59:15.842Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588911,0,542022,562986,543033,'F',TO_TIMESTAMP('2019-09-26 15:59:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2019-09-26 15:59:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T13:05:59.929Z
-- URL zum Konzept
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y', IsSOTrx='Y',Updated=TO_TIMESTAMP('2019-09-26 16:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540717
;

-- 2019-09-26T13:06:45.659Z
-- URL zum Konzept
UPDATE AD_Window SET InternalName='Packing instruction proof',Updated=TO_TIMESTAMP('2019-09-26 16:06:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540717
;

-- 2019-09-26T13:12:14.273Z
-- URL zum Konzept
UPDATE AD_Window SET InternalName='540717(Packing instruction proof)',Updated=TO_TIMESTAMP('2019-09-26 16:12:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540717
;

-- 2019-09-26T13:16:30.267Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=573713, Description='Packvorschrift Nachweis', Help='Packvorschrift Nachweis', InternalName='540717(Packvorschrift Nachweis)',Updated=TO_TIMESTAMP('2019-09-26 16:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540717
;

-- 2019-09-26T13:16:30.302Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(573713) 
;

-- 2019-09-26T13:16:30.302Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540717
;

-- 2019-09-26T13:16:30.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540717)
;

-- 2019-09-26T13:19:00.957Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,573713,541375,0,540717,TO_TIMESTAMP('2019-09-26 16:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Packvorschrift Nachweis','D','540717(Packvorschrift Nachweis)','Y','N','Y','N','N','Packvorschrift Nachweis',TO_TIMESTAMP('2019-09-26 16:19:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T13:19:00.967Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541375 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-26T13:19:00.977Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541375, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541375)
;

-- 2019-09-26T13:19:01.025Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(573713) 
;

-- 2019-09-26T13:19:01.629Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=355 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.630Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=354 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.635Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=359 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=353 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=356 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=565 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.637Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=358 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.637Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540734 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:01.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.527Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:27.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.630Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.631Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.631Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.632Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.632Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.633Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.633Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.634Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.634Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.635Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.635Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.637Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.639Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.639Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.640Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.640Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.641Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.641Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.642Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:31.642Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.388Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.388Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.390Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.397Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.397Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.400Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.401Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-09-26T13:19:33.401Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-09-26T13:25:14.010Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-26 16:25:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562975
;

-- 2019-09-26T13:25:23.961Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-26 16:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562973
;

-- 2019-09-26T13:28:04.486Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588924,0,542022,563079,543030,'F',TO_TIMESTAMP('2019-09-26 16:28:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UPC',50,0,0,TO_TIMESTAMP('2019-09-26 16:28:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-26T13:41:34.827Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-26 16:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562972
;

-- 2019-09-26T13:41:59.192Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-09-26 16:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562973
;

-- 2019-09-26T13:42:07.568Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-26 16:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562974
;

-- 2019-09-26T13:42:26.730Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-26 16:42:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562975
;

-- 2019-09-26T13:43:05.701Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=562980
;

-- 2019-09-26T13:43:08.922Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=562979
;

-- 2019-09-26T13:43:13.651Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=562983
;

-- 2019-09-26T13:43:16.097Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=562984
;

-- 2019-09-26T13:44:04.274Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=562970
;

-- 2019-09-26T14:05:18.901Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-09-26 17:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542022
;

-- 2019-09-26T14:18:52.678Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540343,Updated=TO_TIMESTAMP('2019-09-26 17:18:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540509
;

-- 2019-09-26T14:50:06.107Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540188,Updated=TO_TIMESTAMP('2019-09-26 17:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540509
;

-- 2019-09-26T14:50:42.941Z
-- URL zum Konzept
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2019-09-26 17:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540506
;

-- 2019-09-26T14:56:05.658Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540188,Updated=TO_TIMESTAMP('2019-09-26 17:56:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540501
;

-- 2019-09-26T14:56:46.006Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=540501,Updated=TO_TIMESTAMP('2019-09-26 17:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=588916
;

-- 2019-09-26T15:02:51.962Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=540343,Updated=TO_TIMESTAMP('2019-09-26 18:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540501
;

-- 2019-09-26T15:13:42.198Z
-- URL zum Konzept
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2019-09-26 18:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542022
;

-- 2019-09-26T15:17:33.377Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2019-09-26 18:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549284
;

-- 2019-09-26T15:17:33.391Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-09-26 18:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550222
;

-- 2019-09-26T15:17:33.416Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-09-26 18:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549283
;

-- 2019-09-26T15:17:33.433Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-09-26 18:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549767
;

-- 2019-09-26T15:17:55.502Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2019-09-26 18:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550222
;

-- 2019-09-26T15:17:55.512Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-09-26 18:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549283
;

-- 2019-09-26T15:17:55.521Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-09-26 18:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549767
;

-- 2019-09-26T15:17:55.530Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-09-26 18:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549181
;

