-- INSERT INTO public.ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew) VALUES (1000478, 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, null, 'EE01', 'Zeile', 'Zeile', 'Einzelne Zeile in dem Dokument', 'Indicates the unique line for a document.  It will also control the display order of the lines within a document.', null, null, null, null, null, null, null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000478, 'de_CH', 0, 0, 'Y', '2021-07-26 09:18:13.210779 +00:00', -1, '2021-07-26 09:18:13.210779 +00:00', -1, 'Zeile', 'Zeile', 'Einzelne Zeile in dem Dokument', 'Indicates the unique line for a document.  It will also control the display order of the lines within a document.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000478, 'de_DE', 0, 0, 'Y', '2018-11-26 07:57:00.353716 +00:00', 100, '2018-11-26 07:57:00.353716 +00:00', 100, 'Zeile', 'Zeile', 'Einzelne Zeile in dem Dokument', 'Indicates the unique line for a document.  It will also control the display order of the lines within a document.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000478, 'en_US', 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, 'Line', 'Line', 'Unique line for this document', 'Indicates the unique line for a document.  It will also control the display order of the lines within a document.', null, null, null, null, 'Y', null, null, null, null, 'N', null, null, null);
-- INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized) VALUES (1000478, 'nl_NL', 0, 0, 'Y', '2021-07-26 09:18:17.647262 +00:00', -1, '2021-07-26 09:18:17.647262 +00:00', -1, 'Zeile', 'Zeile', 'Einzelne Zeile in dem Dokument', 'Indicates the unique line for a document.  It will also control the display order of the lines within a document.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null);

-- 2019-09-27T10:28:35.184Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,572848,0,540720,TO_TIMESTAMP('2019-09-27 13:28:35','YYYY-MM-DD HH24:MI:SS'),100,'Components of the BOM & Formula','EE01','The information relative to every component that will be used in the BOM & Formula of the finished product.','New Components of the BOM & Formula','Y','N','N','Y','N','N','Stücklistenbestandteile','N',TO_TIMESTAMP('2019-09-27 13:28:35','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-09-27T10:28:35.187Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540720 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-09-27T10:28:35.231Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(572848) 
;

-- 2019-09-27T10:28:35.234Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540720
;

-- 2019-09-27T10:28:35.266Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540720)
;

-- 2019-09-27T10:29:14.267Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572847,0,542032,53018,540720,'Y',TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Define the Parent Product to this BOM & Formula','EE01','N','Define the Parent Product to this BOM & Formula','PP_Product_BOM','Y','N','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Stücklistenartikel','N',10,0,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:14.268Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542032 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-27T10:29:14.270Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572847) 
;

-- 2019-09-27T10:29:14.274Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542032)
;

-- 2019-09-27T10:29:14.387Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53326,589195,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Copy BOM Lines from an exising BOM',1,'EE01','Copy BOM Lines from an exising BOM. The BOM being copied to, must not have any existing BOM Lines.',0,'Y','N','N','N','N','N','N','N','Copy BOM Lines From',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:14.410Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:14.467Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2037) 
;

-- 2019-09-27T10:29:14.471Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589195
;

-- 2019-09-27T10:29:14.471Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589195)
;

-- 2019-09-27T10:29:14.571Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53335,589196,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01',0,'Y','N','N','N','N','N','N','N','Verarbeiten',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:14.575Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:14.584Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2019-09-27T10:29:14.635Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589196
;

-- 2019-09-27T10:29:14.636Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589196)
;

-- 2019-09-27T10:29:14.733Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53331,589197,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:14.735Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:14.741Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-27T10:29:14.951Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589197
;

-- 2019-09-27T10:29:14.951Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589197)
;

-- 2019-09-27T10:29:15.066Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53341,589198,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:15.068Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:15.073Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-27T10:29:15.245Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589198
;

-- 2019-09-27T10:29:15.245Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589198)
;

-- 2019-09-27T10:29:15.343Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53333,589199,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',22,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',30,30,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:15.343Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:15.359Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-09-27T10:29:15.412Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589199
;

-- 2019-09-27T10:29:15.412Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589199)
;

-- 2019-09-27T10:29:15.513Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53340,589200,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',22,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','N','Y','Merkmale',40,40,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:15.513Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:15.528Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2019-09-27T10:29:15.559Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589200
;

-- 2019-09-27T10:29:15.559Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589200)
;

-- 2019-09-27T10:29:15.675Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53321,589201,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',22,'EE01','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Suchschlüssel',50,50,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:15.675Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:15.675Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2019-09-27T10:29:15.728Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589201
;

-- 2019-09-27T10:29:15.728Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589201)
;

-- 2019-09-27T10:29:15.829Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53344,589202,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',22,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','Y','Maßeinheit',60,60,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:15.829Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:15.845Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2019-09-27T10:29:15.876Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589202
;

-- 2019-09-27T10:29:15.876Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589202)
;

-- 2019-09-27T10:29:15.976Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53322,589203,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100,'',120,'EE01','',0,'Y','Y','Y','N','N','N','N','N','Name',70,70,1,999,1,TO_TIMESTAMP('2019-09-27 13:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:15.992Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:15.998Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-09-27T10:29:16.061Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589203
;

-- 2019-09-27T10:29:16.061Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589203)
;

-- 2019-09-27T10:29:16.160Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53325,589204,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,510,'EE01',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',80,80,0,999,1,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:16.161Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:16.163Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-27T10:29:16.205Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589204
;

-- 2019-09-27T10:29:16.205Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589204)
;

-- 2019-09-27T10:29:16.294Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53329,589205,1000458,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint',2000,'EE01','The Help field contains a hint, comment or help about the use of this item.',0,'Y','Y','Y','N','N','N','N','N','Kommentar/Hilfe',90,90,0,999,1,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:16.298Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:16.305Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000458) 
;

-- 2019-09-27T10:29:16.310Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589205
;

-- 2019-09-27T10:29:16.312Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589205)
;

-- 2019-09-27T10:29:16.417Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53330,589206,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',100,100,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:16.420Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:16.428Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-27T10:29:16.716Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589206
;

-- 2019-09-27T10:29:16.716Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589206)
;

-- 2019-09-27T10:29:16.822Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53332,589207,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,'Bill of Materials (Engineering) Change Notice (Version)',10,'EE01',0,'Y','Y','Y','N','N','N','N','Y','Änderungsmeldung',110,110,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:16.825Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:16.834Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2783) 
;

-- 2019-09-27T10:29:16.849Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589207
;

-- 2019-09-27T10:29:16.851Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589207)
;

-- 2019-09-27T10:29:16.954Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53323,589208,1001737,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',22,'EE01','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Nr.',120,120,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:16.958Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:16.965Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001737) 
;

-- 2019-09-27T10:29:16.971Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589208
;

-- 2019-09-27T10:29:16.972Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589208)
;

-- 2019-09-27T10:29:17.073Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53324,589209,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','Y','Y','N','N','N','N','Y','Version',130,130,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.076Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.083Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53244) 
;

-- 2019-09-27T10:29:17.091Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589209
;

-- 2019-09-27T10:29:17.093Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589209)
;

-- 2019-09-27T10:29:17.198Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53338,589210,1002796,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)',7,'EE01','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','N','N','N','N','N','Gültig ab',140,140,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.198Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.203Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002796) 
;

-- 2019-09-27T10:29:17.203Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589210
;

-- 2019-09-27T10:29:17.203Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589210)
;

-- 2019-09-27T10:29:17.335Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53339,589211,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)',7,'EE01','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','N','N','N','N','Y','Gültig bis',150,150,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.335Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.335Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2019-09-27T10:29:17.350Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589211
;

-- 2019-09-27T10:29:17.350Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589211)
;

-- 2019-09-27T10:29:17.451Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53342,589212,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'Art der Zugehörigkeit zur Stückliste',1,'EE01','Art der Zugehörigkeit eines Produktes zur Stückliste. Ein Standard-Produkt (default) ist immer in der Stückliste enthalten. Ein optionales Produkt kann in "Stückliste einfügen" ausgewählt werden. Wenn ein Teil zu einer der Alternativgruppen gehört, können Sie eine der Alternativen mit der Funktion "Stückliste einfügen" auswählen  (z.B.: alternativ 64/256/512 MB Speicher).',0,'Y','Y','Y','N','N','N','N','N','Stücklisten-Zugehörigkeit',160,160,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.451Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.466Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2030) 
;

-- 2019-09-27T10:29:17.466Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589212
;

-- 2019-09-27T10:29:17.466Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589212)
;

-- 2019-09-27T10:29:17.551Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53343,589213,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'The use of the Bill of Material',1,'EE01','By default the Master BOM is used, if the alternatives are not defined',0,'Y','Y','Y','N','N','N','N','Y','Verwendung',170,170,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.567Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.567Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2784) 
;

-- 2019-09-27T10:29:17.567Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589213
;

-- 2019-09-27T10:29:17.567Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589213)
;

-- 2019-09-27T10:29:17.645Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Included_Tab_ID,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53334,589214,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'BOM & Formula',22,'EE01',53029,0,'Y','Y','Y','N','N','N','N','N','BOM & Formula',180,180,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.655Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.655Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53245) 
;

-- 2019-09-27T10:29:17.655Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589214
;

-- 2019-09-27T10:29:17.655Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589214)
;

-- 2019-09-27T10:29:17.749Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,567626,589215,0,542032,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','N','N','Nummernfolge für Seriennummer',1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.755Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:17.755Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576554) 
;

-- 2019-09-27T10:29:17.755Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589215
;

-- 2019-09-27T10:29:17.755Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589215)
;

-- 2019-09-27T10:29:17.872Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572680,0,542033,53191,540720,'Y',TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'EE01','N','N','PP_Product_BOM_Trl','Y','N','Y','N','N','N','N','Y','N','N','N','Y','Y','Y','N','Y','Parent Product Translation','N',15,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:17.887Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542033 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-27T10:29:17.887Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572680) 
;

-- 2019-09-27T10:29:17.903Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542033)
;

-- 2019-09-27T10:29:18.004Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57214,589216,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-09-27 13:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:18.004Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:18.004Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-27T10:29:18.113Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589216
;

-- 2019-09-27T10:29:18.113Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589216)
;

-- 2019-09-27T10:29:18.209Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57216,589217,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:18.212Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:18.219Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-27T10:29:18.352Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589217
;

-- 2019-09-27T10:29:18.352Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589217)
;

-- 2019-09-27T10:29:18.451Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57224,589218,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100,'BOM & Formula',22,'EE01',0,'Y','Y','Y','N','N','N','Y','N','BOM & Formula',30,30,1,1,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:18.454Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:18.462Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53245) 
;

-- 2019-09-27T10:29:18.485Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589218
;

-- 2019-09-27T10:29:18.486Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589218)
;

-- 2019-09-27T10:29:18.582Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57215,589219,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag',6,'EE01','Definiert die Sprache für Anzeige und Aufbereitung',0,'Y','Y','Y','N','N','N','Y','N','Sprache',40,40,1,1,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:18.585Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589219 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:18.595Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109) 
;

-- 2019-09-27T10:29:18.636Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589219
;

-- 2019-09-27T10:29:18.637Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589219)
;

-- 2019-09-27T10:29:18.732Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57223,589220,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100,'',60,'EE01','',0,'Y','Y','Y','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:18.735Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:18.742Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-09-27T10:29:18.816Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589220
;

-- 2019-09-27T10:29:18.817Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589220)
;

-- 2019-09-27T10:29:18.919Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57219,589221,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100,255,'EE01',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:18.922Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:18.929Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-27T10:29:18.997Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589221
;

-- 2019-09-27T10:29:18.997Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589221)
;

-- 2019-09-27T10:29:19.095Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57220,589222,1001227,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint',2000,'EE01','The Help field contains a hint, comment or help about the use of this item.',0,'Y','Y','Y','N','N','N','N','N','Kommentar/Hilfe',70,70,999,1,TO_TIMESTAMP('2019-09-27 13:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:19.098Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:19.104Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001227) 
;

-- 2019-09-27T10:29:19.104Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589222
;

-- 2019-09-27T10:29:19.104Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589222)
;

-- 2019-09-27T10:29:19.224Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57221,589223,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',80,80,1,1,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:19.224Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:19.224Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-27T10:29:19.462Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589223
;

-- 2019-09-27T10:29:19.462Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589223)
;

-- 2019-09-27T10:29:19.578Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57222,589224,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt',1,'EE01','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist',0,'Y','Y','Y','N','N','N','N','N','Übersetzt',90,90,1,1,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:19.593Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:19.593Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(420) 
;

-- 2019-09-27T10:29:19.609Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589224
;

-- 2019-09-27T10:29:19.609Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589224)
;

-- 2019-09-27T10:29:19.709Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555724,589225,0,542033,0,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','N','N','BOM & Formula **',1,1,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:19.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589225 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:19.709Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543264) 
;

-- 2019-09-27T10:29:19.709Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589225
;

-- 2019-09-27T10:29:19.725Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589225)
;

-- 2019-09-27T10:29:19.825Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572848,0,542034,53019,540720,'Y',TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Components of the BOM & Formula','EE01','N','The information relative to every component that will be used in the BOM & Formula of the finished product.','PP_Product_BOMLine','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Stücklistenbestandteile','N',20,1,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:19.825Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542034 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-27T10:29:19.825Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572848) 
;

-- 2019-09-27T10:29:19.841Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542034)
;

-- 2019-09-27T10:29:19.941Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53373,589226,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','Mandant',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:19.941Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589226 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:19.957Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-27T10:29:20.106Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589226
;

-- 2019-09-27T10:29:20.106Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589226)
;

-- 2019-09-27T10:29:20.204Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53365,589227,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'BOM Line',22,'EE01','The BOM Line is a unique identifier for a BOM line in an BOM.',0,'Y','N','N','N','N','N','N','N','BOM Line',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:20.205Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589227 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:20.207Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53254) 
;

-- 2019-09-27T10:29:20.210Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589227
;

-- 2019-09-27T10:29:20.210Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589227)
;

-- 2019-09-27T10:29:20.306Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53346,589228,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','Y','Sektion',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:20.307Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589228 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:20.309Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-27T10:29:20.430Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589228
;

-- 2019-09-27T10:29:20.431Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589228)
;

-- 2019-09-27T10:29:20.538Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Included_Tab_ID,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53366,589229,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'BOM & Formula',22,'EE01',53216,0,'Y','N','N','N','N','N','N','N','BOM & Formula',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:20.541Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589229 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:20.548Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53245) 
;

-- 2019-09-27T10:29:20.557Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589229
;

-- 2019-09-27T10:29:20.558Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589229)
;

-- 2019-09-27T10:29:20.653Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53345,589230,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Indicated the Feature for Product Configure',30,'(@ComponentType@=''VA''&@BOMType@=''C''|@ComponentType@=''OP'')&@BOMType@=''C''','EE01','Indicated the Feature for Product Configure',0,'Y','N','N','N','N','N','N','N','Feature',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:20.656Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:20.663Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53246) 
;

-- 2019-09-27T10:29:20.667Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589230
;

-- 2019-09-27T10:29:20.669Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589230)
;

-- 2019-09-27T10:29:20.780Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53348,589231,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'The Grouping Components to the Backflush',20,'@IssueMethod@=1','EE01','When the components are deliver is possible to indicated The Backflush Group this way you only can deliver the components that are for this Backflush Group.',0,'Y','N','N','N','N','N','N','Y','Retrograde Gruppe',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:20.783Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:20.790Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53248) 
;

-- 2019-09-27T10:29:20.794Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589231
;

-- 2019-09-27T10:29:20.796Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589231)
;

-- 2019-09-27T10:29:20.910Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53360,589232,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Optional Lead Time offest before starting production',10,'EE01','Optional Lead Time offest before starting production',0,'Y','N','N','N','N','N','N','N','Lead Time Offset',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:20.913Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:20.917Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2789) 
;

-- 2019-09-27T10:29:20.920Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589232
;

-- 2019-09-27T10:29:20.920Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589232)
;

-- 2019-09-27T10:29:21.026Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53347,589233,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Indicated the Quantity Assay to use into Quality Order',22,'EE01','Indicated the Quantity Assay to use into Quality Order',0,'Y','N','N','N','N','N','N','Y','Mengen Probe',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.030Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.038Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53247) 
;

-- 2019-09-27T10:29:21.043Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589233
;

-- 2019-09-27T10:29:21.045Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589233)
;

-- 2019-09-27T10:29:21.136Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53372,589234,1001270,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Valid from including this date (first day)',7,'EE01','The Valid From date indicates the first day of a date range

The Valid From and Valid To dates indicate the valid time period to use the BOM in a Manufacturing Order.',0,'Y','N','N','N','N','N','N','N','Valid from',0,0,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.136Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.136Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001270) 
;

-- 2019-09-27T10:29:21.136Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589234
;

-- 2019-09-27T10:29:21.151Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589234)
;

-- 2019-09-27T10:29:21.230Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53361,589235,1000478,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument',22,'EE01','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','Y','Y','N','N','N','N','N','Zeile',10,10,1,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.245Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.245Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000478) 
;

-- 2019-09-27T10:29:21.245Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589235
;

-- 2019-09-27T10:29:21.260Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589235)
;

-- 2019-09-27T10:29:21.382Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53364,589236,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',22,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',20,20,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.382Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.398Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-09-27T10:29:21.444Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589236
;

-- 2019-09-27T10:29:21.444Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589236)
;

-- 2019-09-27T10:29:21.554Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53350,589237,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Component Type for a Bill of Material or Formula',0,'EE01','The Component Type can be:

1.- By Product: Define a By Product as Component into BOM
2.- Component: Define a normal Component into BOM 
3.- Option: Define an Option for Product Configure BOM
4.- Phantom: Define a Phantom as Component into BOM
5.- Packing: Define a Packing as Component into BOM
6.- Planning : Define Planning as Component into BOM
7.- Tools: Define Tools as Component into BOM
8.- Variant: Define Variant  for Product Configure BOM
',0,'Y','Y','Y','N','N','N','N','Y','Komponententyp',30,30,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.554Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.569Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53249) 
;

-- 2019-09-27T10:29:21.569Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589237
;

-- 2019-09-27T10:29:21.569Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589237)
;

-- 2019-09-27T10:29:21.663Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550355,589238,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,0,'EE01',0,'Y','Y','Y','N','N','N','N','Y','Varianten Gruppe',40,40,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.663Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.663Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542377) 
;

-- 2019-09-27T10:29:21.679Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589238
;

-- 2019-09-27T10:29:21.679Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589238)
;

-- 2019-09-27T10:29:21.773Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53349,589239,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',22,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','N','Maßeinheit',50,50,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.773Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589239 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.788Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2019-09-27T10:29:21.819Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589239
;

-- 2019-09-27T10:29:21.819Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589239)
;

-- 2019-09-27T10:29:21.913Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53362,589240,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',22,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','N','Y','Merkmale',60,60,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:21.929Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:21.929Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2019-09-27T10:29:21.976Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589240
;

-- 2019-09-27T10:29:21.976Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589240)
;

-- 2019-09-27T10:29:22.070Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53353,589241,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100,510,'EE01',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',70,70,0,999,1,TO_TIMESTAMP('2019-09-27 13:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:22.070Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:22.085Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-27T10:29:22.159Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589241
;

-- 2019-09-27T10:29:22.159Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589241)
;

-- 2019-09-27T10:29:22.337Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53355,589242,1001491,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint',2000,'EE01','The Help field contains a hint, comment or help about the use of this item.',0,'Y','Y','Y','N','N','N','N','N','Kommentar/Hilfe',80,80,0,999,1,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:22.339Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:22.342Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001491) 
;

-- 2019-09-27T10:29:22.344Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589242
;

-- 2019-09-27T10:29:22.345Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589242)
;

-- 2019-09-27T10:29:22.469Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53356,589243,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',90,90,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:22.471Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:22.476Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-27T10:29:22.696Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589243
;

-- 2019-09-27T10:29:22.696Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589243)
;

-- 2019-09-27T10:29:22.808Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53363,589244,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Bill of Materials (Engineering) Change Notice (Version)',10,'EE01',0,'Y','Y','Y','N','N','N','N','Y','Änderungsmeldung',100,100,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:22.811Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:22.818Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2783) 
;

-- 2019-09-27T10:29:22.828Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589244
;

-- 2019-09-27T10:29:22.829Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589244)
;

-- 2019-09-27T10:29:22.933Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53374,589245,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)',7,'EE01','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','N','N','N','N','Y','Gültig bis',110,110,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:22.936Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:22.943Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2019-09-27T10:29:22.957Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589245
;

-- 2019-09-27T10:29:22.957Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589245)
;

-- 2019-09-27T10:29:23.070Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53358,589246,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Indicate that this component is based in % Quantity',1,'EE01','Indicate that this component is based in % Quantity',0,'Y','Y','Y','N','N','N','N','N','Ist %',120,120,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.073Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.077Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53252) 
;

-- 2019-09-27T10:29:23.079Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589246
;

-- 2019-09-27T10:29:23.079Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589246)
;

-- 2019-09-27T10:29:23.200Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53357,589247,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,'Indicate that a Manufacturing Order can not begin without have this component',1,'EE01','Indicate that a Manufacturing Order can not begin without have this component',0,'Y','Y','Y','N','N','N','N','Y','Kritische Komponente',130,130,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.204Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.212Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53251) 
;

-- 2019-09-27T10:29:23.216Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589247
;

-- 2019-09-27T10:29:23.217Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589247)
;

-- 2019-09-27T10:29:23.345Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53368,589248,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,'Indicate the Quantity % use in this Formula',22,'@IsQtyPercentage@ = Y','EE01','Exist two way the add a compenent to a BOM or Formula:

1.- Adding a Component based in quantity to use in this BOM
2.- Adding a Component based in % to use the Order Quantity of Manufacturing Order in this Formula.
',0,'Y','Y','Y','N','N','N','N','N','% Menge',140,140,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.348Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.354Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53256) 
;

-- 2019-09-27T10:29:23.358Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589248
;

-- 2019-09-27T10:29:23.359Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589248)
;

-- 2019-09-27T10:29:23.502Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560680,589249,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','Y','Y','N','N','N','N','N','Qty Attribute',145,145,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.505Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.513Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544181) 
;

-- 2019-09-27T10:29:23.518Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589249
;

-- 2019-09-27T10:29:23.519Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589249)
;

-- 2019-09-27T10:29:23.636Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53367,589250,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,'Indicate the Quantity  use in this BOM',22,'@IsQtyPercentage@ = N','EE01','Exist two way the add a compenent to a BOM or Formula:

1.- Adding a Component based in quantity to use in this BOM
2.- Adding a Component based in % to use the Order Quantity of Manufacturing Order in this Formula.
',0,'Y','Y','Y','N','N','N','N','N','Menge',150,150,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.638Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.642Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53255) 
;

-- 2019-09-27T10:29:23.644Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589250
;

-- 2019-09-27T10:29:23.645Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589250)
;

-- 2019-09-27T10:29:23.780Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551344,589251,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,22,'EE01',0,'Y','Y','Y','N','N','N','Y','N','Erwartetes Ergebnis',160,160,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.783Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.792Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542596) 
;

-- 2019-09-27T10:29:23.798Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589251
;

-- 2019-09-27T10:29:23.800Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589251)
;

-- 2019-09-27T10:29:23.913Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551343,589252,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,'Indicate the % Scrap  for calculate the Scrap Quantity',22,'EE01','Scrap is useful to determinate a rigth Standard Cost and management a good supply.',0,'Y','Y','Y','N','N','N','Y','N','% Ausschuss (alt)',160,160,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:23.916Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:23.924Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542595) 
;

-- 2019-09-27T10:29:23.926Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589252
;

-- 2019-09-27T10:29:23.927Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589252)
;

-- 2019-09-27T10:29:24.062Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53369,589253,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100,'Indicate the % Scrap  for calculate the Scrap Quantity',22,'EE01','Scrap is useful to determinate a rigth Standard Cost and management a good supply.',0,'Y','Y','Y','N','N','N','N','N','% Ausschuss',160,160,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.065Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:24.069Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53257) 
;

-- 2019-09-27T10:29:24.071Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589253
;

-- 2019-09-27T10:29:24.072Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589253)
;

-- 2019-09-27T10:29:24.183Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53354,589254,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100,'Indicated the % of participation this component into a of the BOM Planning',22,'@BOMUse@=''P''','EE01','The BOM of Planning Type are useful to Planning the Product family.

For example is possible create a BOM Planning for an Automobile

10% Automobile Red
35% Automobile Blue
45% Automobile Black
19% Automobile Green
1%  Automobile Orange

When Material Plan is calculated MRP generate a Manufacturing Order meet to demand to each  of the Automobile',0,'Y','Y','Y','N','N','N','N','N','Geplant',170,170,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.183Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:24.198Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53250) 
;

-- 2019-09-27T10:29:24.198Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589254
;

-- 2019-09-27T10:29:24.198Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589254)
;

-- 2019-09-27T10:29:24.372Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53359,589255,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100,'There are two methods for issue the components to Manufacturing Order',1,'EE01','Method Issue: The component are delivered one for one and is necessary indicate the delivered quantity for each component.

Method BackFlush: The component are delivered based in BOM, The  delivered quantity for each component is based in BOM or Formula and Manufacturing Order Quantity.

Use the field Backflush Group for grouping the component in a Backflush Method.',0,'Y','Y','Y','N','N','N','N','N','Zuteil Methode',180,180,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.372Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:24.372Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53253) 
;

-- 2019-09-27T10:29:24.372Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589255
;

-- 2019-09-27T10:29:24.372Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589255)
;

-- 2019-09-27T10:29:24.513Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560495,589256,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100,0,'EE01',0,'Y','Y','Y','N','N','N','N','N','Inhaltsstoffe Unterstückliste anzeigen',190,190,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.513Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:24.513Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544136) 
;

-- 2019-09-27T10:29:24.528Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589256
;

-- 2019-09-27T10:29:24.528Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589256)
;

-- 2019-09-27T10:29:24.669Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560496,589257,0,542034,0,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100,0,'EE01',0,'Y','Y','Y','N','N','N','N','N','Menge Konsumentenlabel',200,200,0,1,1,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.669Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:24.669Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544137) 
;

-- 2019-09-27T10:29:24.669Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589257
;

-- 2019-09-27T10:29:24.684Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589257)
;

-- 2019-09-27T10:29:24.794Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573383,0,542035,53193,540720,'Y',TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100,'BOM & Formula Line Translation','EE01','N','N','PP_Product_BOMLine_Trl','Y','N','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','Y','Components Translation','N',30,2,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.809Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542035 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-27T10:29:24.809Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(573383) 
;

-- 2019-09-27T10:29:24.825Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542035)
;

-- 2019-09-27T10:29:24.934Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57230,589258,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-09-27 13:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:24.950Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:24.950Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-27T10:29:25.109Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589258
;

-- 2019-09-27T10:29:25.109Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589258)
;

-- 2019-09-27T10:29:25.207Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57232,589259,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:25.209Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:25.216Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-27T10:29:25.380Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589259
;

-- 2019-09-27T10:29:25.380Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589259)
;

-- 2019-09-27T10:29:25.606Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57240,589260,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100,'BOM Line',22,'EE01','The BOM Line is a unique identifier for a BOM line in an BOM.',0,'Y','Y','Y','N','N','N','Y','N','BOM Line',30,30,1,1,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:25.609Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:25.617Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53254) 
;

-- 2019-09-27T10:29:25.621Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589260
;

-- 2019-09-27T10:29:25.622Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589260)
;

-- 2019-09-27T10:29:25.765Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57231,589261,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag',6,'EE01','Definiert die Sprache für Anzeige und Aufbereitung',0,'Y','Y','Y','N','N','N','Y','N','Sprache',40,40,1,1,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:25.768Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:25.775Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109) 
;

-- 2019-09-27T10:29:25.813Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589261
;

-- 2019-09-27T10:29:25.814Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589261)
;

-- 2019-09-27T10:29:25.906Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57235,589262,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100,255,'EE01',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',50,50,999,1,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:25.909Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:25.917Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-27T10:29:25.990Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589262
;

-- 2019-09-27T10:29:25.991Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589262)
;

-- 2019-09-27T10:29:26.124Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57236,589263,1001255,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint',2000,'EE01','The Help field contains a hint, comment or help about the use of this item.',0,'Y','Y','Y','N','N','N','N','N','Kommentar/Hilfe',60,60,999,1,TO_TIMESTAMP('2019-09-27 13:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:26.124Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:26.139Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001255) 
;

-- 2019-09-27T10:29:26.139Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589263
;

-- 2019-09-27T10:29:26.139Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589263)
;

-- 2019-09-27T10:29:26.249Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57237,589264,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:26.264Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:26.264Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-27T10:29:26.467Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589264
;

-- 2019-09-27T10:29:26.467Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589264)
;

-- 2019-09-27T10:29:26.561Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,57238,589265,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt',1,'EE01','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist',0,'Y','Y','Y','N','N','N','N','N','Übersetzt',80,80,1,1,TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:26.561Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:26.577Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(420) 
;

-- 2019-09-27T10:29:26.608Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589265
;

-- 2019-09-27T10:29:26.624Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589265)
;

-- 2019-09-27T10:29:26.717Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555715,589266,0,542035,0,TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01',0,'Y','N','N','N','N','N','N','N','BOM Line **',1,1,TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:29:26.717Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-27T10:29:26.717Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543261) 
;

-- 2019-09-27T10:29:26.717Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589266
;

-- 2019-09-27T10:29:26.717Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(589266)
;

-- 2019-09-27T10:29:26.733Z
-- URL zum Konzept
UPDATE AD_Field SET Included_Tab_ID=542034,Updated=TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589214
;

-- 2019-09-27T10:29:26.749Z
-- URL zum Konzept
UPDATE AD_Field SET Included_Tab_ID=542035,Updated=TO_TIMESTAMP('2019-09-27 13:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589229
;

-- 2019-09-27T10:30:35.479Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589195
;

-- 2019-09-27T10:30:35.479Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589195
;

-- 2019-09-27T10:30:35.495Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589195
;

-- 2019-09-27T10:30:35.495Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589196
;

-- 2019-09-27T10:30:35.495Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589196
;

-- 2019-09-27T10:30:35.495Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589196
;

-- 2019-09-27T10:30:35.495Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589197
;

-- 2019-09-27T10:30:35.495Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589197
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589197
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589198
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589198
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589198
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589199
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589199
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589199
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589200
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589200
;

-- 2019-09-27T10:30:35.510Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589200
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589201
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589201
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589201
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589202
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589202
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589202
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589203
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589203
;

-- 2019-09-27T10:30:35.526Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589203
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589204
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589204
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589204
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589205
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589205
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589205
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589206
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589206
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589206
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589207
;

-- 2019-09-27T10:30:35.542Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589207
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589207
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589208
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589208
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589208
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589209
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589209
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589209
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589210
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589210
;

-- 2019-09-27T10:30:35.557Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589210
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589211
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589211
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589211
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589212
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589212
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589212
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589213
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589213
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589213
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589215
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589215
;

-- 2019-09-27T10:30:35.573Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589215
;

-- 2019-09-27T10:30:35.588Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589214
;

-- 2019-09-27T10:30:35.588Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589214
;

-- 2019-09-27T10:30:35.588Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589214
;

-- 2019-09-27T10:30:35.588Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=542032
;

-- 2019-09-27T10:30:35.588Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=542032
;

-- 2019-09-27T10:30:43.450Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589225
;

-- 2019-09-27T10:30:43.465Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589225
;

-- 2019-09-27T10:30:43.475Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589225
;

-- 2019-09-27T10:30:43.479Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589216
;

-- 2019-09-27T10:30:43.479Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589216
;

-- 2019-09-27T10:30:43.481Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589216
;

-- 2019-09-27T10:30:43.484Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589217
;

-- 2019-09-27T10:30:43.485Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589217
;

-- 2019-09-27T10:30:43.486Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589217
;

-- 2019-09-27T10:30:43.490Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589218
;

-- 2019-09-27T10:30:43.490Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589218
;

-- 2019-09-27T10:30:43.492Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589218
;

-- 2019-09-27T10:30:43.495Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589219
;

-- 2019-09-27T10:30:43.496Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589219
;

-- 2019-09-27T10:30:43.498Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589219
;

-- 2019-09-27T10:30:43.501Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589220
;

-- 2019-09-27T10:30:43.502Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589220
;

-- 2019-09-27T10:30:43.503Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589220
;

-- 2019-09-27T10:30:43.506Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589221
;

-- 2019-09-27T10:30:43.507Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589221
;

-- 2019-09-27T10:30:43.509Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589221
;

-- 2019-09-27T10:30:43.512Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589222
;

-- 2019-09-27T10:30:43.512Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589222
;

-- 2019-09-27T10:30:43.514Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589222
;

-- 2019-09-27T10:30:43.517Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589223
;

-- 2019-09-27T10:30:43.517Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589223
;

-- 2019-09-27T10:30:43.519Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589223
;

-- 2019-09-27T10:30:43.522Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589224
;

-- 2019-09-27T10:30:43.522Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589224
;

-- 2019-09-27T10:30:43.524Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=589224
;

-- 2019-09-27T10:30:43.525Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=542033
;

-- 2019-09-27T10:30:43.527Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=542033
;

-- 2019-09-27T10:31:25.202Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2019-09-27 13:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542034
;

-- 2019-09-27T10:34:31.639Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542035,541582,TO_TIMESTAMP('2019-09-27 13:34:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-27 13:34:31','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-09-27T10:34:31.640Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541582 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-27T10:34:39.667Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542013,541582,TO_TIMESTAMP('2019-09-27 13:34:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-27 13:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:34:41.692Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542014,541582,TO_TIMESTAMP('2019-09-27 13:34:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-27 13:34:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:35:07.943Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542013,543045,TO_TIMESTAMP('2019-09-27 13:35:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-09-27 13:35:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:35:24.911Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542013,543046,TO_TIMESTAMP('2019-09-27 13:35:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2019-09-27 13:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:36:34.587Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=542014
;

-- 2019-09-27T10:36:34.590Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543046
;

-- 2019-09-27T10:36:34.593Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543045
;

-- 2019-09-27T10:36:34.594Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=542013
;

-- 2019-09-27T10:36:34.595Z
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541582
;

-- 2019-09-27T10:36:34.597Z
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541582
;

-- 2019-09-27T10:36:55.974Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542034,541583,TO_TIMESTAMP('2019-09-27 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-27 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-09-27T10:36:55.976Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541583 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-27T10:37:03.291Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542015,541583,TO_TIMESTAMP('2019-09-27 13:37:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-27 13:37:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:37:05.145Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542016,541583,TO_TIMESTAMP('2019-09-27 13:37:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-27 13:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:37:21.262Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542015,543047,TO_TIMESTAMP('2019-09-27 13:37:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-09-27 13:37:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:37:29.571Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542015,543048,TO_TIMESTAMP('2019-09-27 13:37:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2019-09-27 13:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:37:57.330Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589236,0,542034,563084,543047,'F',TO_TIMESTAMP('2019-09-27 13:37:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Product',10,0,0,TO_TIMESTAMP('2019-09-27 13:37:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:38:45.210Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589240,0,542034,563085,543047,'F',TO_TIMESTAMP('2019-09-27 13:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Attributes',20,0,0,TO_TIMESTAMP('2019-09-27 13:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:39:45.957Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589239,0,542034,563086,543047,'F',TO_TIMESTAMP('2019-09-27 13:39:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UOM',30,0,0,TO_TIMESTAMP('2019-09-27 13:39:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:40:25.404Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589250,0,542034,563087,543047,'F',TO_TIMESTAMP('2019-09-27 13:40:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Qty',40,0,0,TO_TIMESTAMP('2019-09-27 13:40:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:41:51.233Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589233,0,542034,563088,543047,'F',TO_TIMESTAMP('2019-09-27 13:41:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Quantity Assay',50,0,0,TO_TIMESTAMP('2019-09-27 13:41:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:43:09.777Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589248,0,542034,563089,543047,'F',TO_TIMESTAMP('2019-09-27 13:43:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Qty Batch',60,0,0,TO_TIMESTAMP('2019-09-27 13:43:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:43:47.699Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589253,0,542034,563090,543047,'F',TO_TIMESTAMP('2019-09-27 13:43:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Scrap',70,0,0,TO_TIMESTAMP('2019-09-27 13:43:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:44:19.380Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589241,0,542034,563091,543048,'F',TO_TIMESTAMP('2019-09-27 13:44:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',10,0,0,TO_TIMESTAMP('2019-09-27 13:44:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:45:33.065Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589229,0,542034,563092,543048,'F',TO_TIMESTAMP('2019-09-27 13:45:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'BOM & Formula',20,0,0,TO_TIMESTAMP('2019-09-27 13:45:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:46:13.608Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589237,0,542034,563093,543048,'F',TO_TIMESTAMP('2019-09-27 13:46:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Component Type',30,0,0,TO_TIMESTAMP('2019-09-27 13:46:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:47:00.114Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589231,0,542034,563094,543048,'F',TO_TIMESTAMP('2019-09-27 13:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Backflush Group',40,0,0,TO_TIMESTAMP('2019-09-27 13:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:47:54.939Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589254,0,542034,563095,543048,'F',TO_TIMESTAMP('2019-09-27 13:47:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Forecast',50,0,0,TO_TIMESTAMP('2019-09-27 13:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:48:16.371Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589244,0,542034,563096,543048,'F',TO_TIMESTAMP('2019-09-27 13:48:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Change Notice',60,0,0,TO_TIMESTAMP('2019-09-27 13:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:48:41.144Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542016,543049,TO_TIMESTAMP('2019-09-27 13:48:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-09-27 13:48:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:48:48.231Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542016,543050,TO_TIMESTAMP('2019-09-27 13:48:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','valid',20,TO_TIMESTAMP('2019-09-27 13:48:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:48:56.599Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542016,543051,TO_TIMESTAMP('2019-09-27 13:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','attributes',30,TO_TIMESTAMP('2019-09-27 13:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:49:01.389Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542016,543052,TO_TIMESTAMP('2019-09-27 13:49:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2019-09-27 13:49:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:49:34.032Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589228,0,542034,563097,543052,'F',TO_TIMESTAMP('2019-09-27 13:49:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Org',10,0,0,TO_TIMESTAMP('2019-09-27 13:49:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:50:00.282Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589226,0,542034,563098,543052,'F',TO_TIMESTAMP('2019-09-27 13:50:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2019-09-27 13:50:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:50:37.961Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589243,0,542034,563099,543049,'F',TO_TIMESTAMP('2019-09-27 13:50:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2019-09-27 13:50:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:51:18.551Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589246,0,542034,563100,543049,'F',TO_TIMESTAMP('2019-09-27 13:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Is Qty Percentage',20,0,0,TO_TIMESTAMP('2019-09-27 13:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:52:11.730Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589234,0,542034,563101,543050,'F',TO_TIMESTAMP('2019-09-27 13:52:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Valid from',10,0,0,TO_TIMESTAMP('2019-09-27 13:52:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:52:41.090Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589245,0,542034,563102,543050,'F',TO_TIMESTAMP('2019-09-27 13:52:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Valid to',20,0,0,TO_TIMESTAMP('2019-09-27 13:52:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:53:50.180Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589240,0,542034,563103,543051,'F',TO_TIMESTAMP('2019-09-27 13:53:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Attributes',10,0,0,TO_TIMESTAMP('2019-09-27 13:53:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:54:15.417Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589233,0,542034,563104,543051,'F',TO_TIMESTAMP('2019-09-27 13:54:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Quantity Assay',20,0,0,TO_TIMESTAMP('2019-09-27 13:54:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:54:47.909Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589257,0,542034,563105,543051,'F',TO_TIMESTAMP('2019-09-27 13:54:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CU Label Qty',30,0,0,TO_TIMESTAMP('2019-09-27 13:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:55:24.641Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589250,0,542034,563106,543051,'F',TO_TIMESTAMP('2019-09-27 13:55:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyBOM',40,0,0,TO_TIMESTAMP('2019-09-27 13:55:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T10:55:56.708Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563085
;

-- 2019-09-27T10:56:01.209Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563087
;

-- 2019-09-27T10:56:04.069Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563088
;

-- 2019-09-27T11:18:39.423Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,572848,541376,0,540720,TO_TIMESTAMP('2019-09-27 14:18:39','YYYY-MM-DD HH24:MI:SS'),100,'Components of the BOM & Formula','EE01','New Components of the BOM & Formula','Y','N','N','N','N','Stücklistenbestandteile',TO_TIMESTAMP('2019-09-27 14:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:18:39.440Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541376 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-27T11:18:39.452Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541376, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541376)
;

-- 2019-09-27T11:18:39.471Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(572848) 
;

-- 2019-09-27T11:18:40.034Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53182 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:40.049Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=478 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:40.050Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53183 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:40.050Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53181 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:40.051Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.614Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.615Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.616Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.616Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.617Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.618Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.618Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.619Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.619Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.620Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.620Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.621Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.621Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.622Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.622Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.623Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.623Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.624Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.624Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.625Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.625Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:51.625Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.317Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.318Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.318Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.319Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.319Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.320Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.320Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.321Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.321Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.322Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.322Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.323Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.323Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.324Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.324Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.325Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.325Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:55.326Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.595Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.595Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.596Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.597Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.597Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.598Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.598Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.599Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.599Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.600Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.600Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.600Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.601Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.601Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.602Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.602Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.603Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2019-09-27T11:18:57.603Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.170Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.170Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.171Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.171Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.172Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.172Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.172Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.173Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.173Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.174Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.174Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.174Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.175Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.175Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.176Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.176Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.176Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2019-09-27T11:19:01.177Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2019-09-27T11:20:19.526Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589227,0,542034,563107,543047,'F',TO_TIMESTAMP('2019-09-27 14:20:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Line',80,0,0,TO_TIMESTAMP('2019-09-27 14:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:20:41.904Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-09-27 14:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563107
;

-- 2019-09-27T11:20:55.847Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-27 14:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563084
;

-- 2019-09-27T11:22:08.864Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589237,0,542034,563108,543047,'F',TO_TIMESTAMP('2019-09-27 14:22:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Component Type',80,0,0,TO_TIMESTAMP('2019-09-27 14:22:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:22:28.719Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-09-27 14:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563086
;

-- 2019-09-27T11:22:35.204Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-27 14:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563108
;

-- 2019-09-27T11:27:29.188Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563089
;

-- 2019-09-27T11:30:00.404Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589248,0,542034,563109,543047,'F',TO_TIMESTAMP('2019-09-27 14:30:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Qty',90,0,0,TO_TIMESTAMP('2019-09-27 14:30:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:30:46.990Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589240,0,542034,563110,543047,'F',TO_TIMESTAMP('2019-09-27 14:30:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Attributes',100,0,0,TO_TIMESTAMP('2019-09-27 14:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:31:22.836Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589249,0,542034,563111,543047,'F',TO_TIMESTAMP('2019-09-27 14:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Qty Attribute',110,0,0,TO_TIMESTAMP('2019-09-27 14:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:32:34.229Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563096
;

-- 2019-09-27T11:32:37.046Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563095
;

-- 2019-09-27T11:32:55.275Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563094
;

-- 2019-09-27T11:32:59.275Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563093
;

-- 2019-09-27T11:33:34.521Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589238,0,542034,563112,543048,'F',TO_TIMESTAMP('2019-09-27 14:33:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VariantGroup',30,0,0,TO_TIMESTAMP('2019-09-27 14:33:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:37:06.304Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543048, SeqNo=40,Updated=TO_TIMESTAMP('2019-09-27 14:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563105
;

-- 2019-09-27T11:37:29.128Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563104
;

-- 2019-09-27T11:38:28.483Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_Field_ID=589250,Updated=TO_TIMESTAMP('2019-09-27 14:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563109
;

-- 2019-09-27T11:41:53.257Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589248,0,542034,563113,543048,'F',TO_TIMESTAMP('2019-09-27 14:41:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'% Qty',50,0,0,TO_TIMESTAMP('2019-09-27 14:41:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:43:44.772Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563106
;

-- 2019-09-27T11:43:44.774Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563103
;

-- 2019-09-27T11:43:44.775Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543051
;

-- 2019-09-27T11:44:34.205Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589250,0,542034,563114,543048,'F',TO_TIMESTAMP('2019-09-27 14:44:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyBOM',60,0,0,TO_TIMESTAMP('2019-09-27 14:44:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:45:04.196Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-09-27 14:45:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563092
;

-- 2019-09-27T11:45:10.920Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-27 14:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563114
;

-- 2019-09-27T11:45:25.344Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-27 14:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563105
;

-- 2019-09-27T11:45:32.723Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-27 14:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563113
;

-- 2019-09-27T11:46:31.298Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-27 14:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563105
;

-- 2019-09-27T11:46:37.873Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-09-27 14:46:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563113
;

-- 2019-09-27T11:46:46.099Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-09-27 14:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563091
;

-- 2019-09-27T11:47:54.358Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589255,0,542034,563115,543048,'F',TO_TIMESTAMP('2019-09-27 14:47:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Issue Method',70,0,0,TO_TIMESTAMP('2019-09-27 14:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T11:51:24.900Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542016,543053,TO_TIMESTAMP('2019-09-27 14:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','space',30,TO_TIMESTAMP('2019-09-27 14:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T12:01:16.412Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543053
;

-- 2019-09-27T12:11:14Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589256,0,542034,563116,543049,'F',TO_TIMESTAMP('2019-09-27 15:11:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ShowSubBOMIngredients',30,0,0,TO_TIMESTAMP('2019-09-27 15:11:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-27T12:18:12.798Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589247,0,542034,563117,543049,'F',TO_TIMESTAMP('2019-09-27 15:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Is Critical Component',40,0,0,TO_TIMESTAMP('2019-09-27 15:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

