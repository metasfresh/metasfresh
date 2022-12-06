/*
2022-01-20 commented out because
psql:/root/apply_migr_scripts_20220119/dist/sql/10-de.metas.adempiere/5529171_sys_gh5444_add_window_for_organisation_master_data.sql:1: ERROR:  duplicate key value violates unique constraint "ad_element_pkey"
        DETAIL:  Key (ad_element_id)=(1000283) already exists.
*/
INSERT INTO public.ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew)
SELECT 1000283, 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, null, 'D', 'Standort', 'Standort', 'Identifiziert die (Liefer-) Adresse des Geschäftspartners', 'Identifiziert die Adresse des Geschäftspartners', null, null, null, null, null, null, null, null, null
where not exists (select 1 from ad_element where ad_element_id=1000283);

INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
SELECT 1000283, 'de_CH', 0, 0, 'Y', '2021-07-26 09:18:13.210779 +00:00', -1, '2021-07-26 09:18:13.210779 +00:00', -1, 'Standort', 'Standort', 'Identifiziert die (Liefer-) Adresse des Geschäftspartners', 'Identifiziert die Adresse des Geschäftspartners', null, null, null, null, 'N', null, null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000283 and ad_language='de_CH');

INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
SELECT 1000283, 'de_DE', 0, 0, 'Y', '2018-11-26 07:57:00.353716 +00:00', 100, '2018-11-26 07:57:00.353716 +00:00', 100, 'Standort', 'Standort', 'Identifiziert die (Liefer-) Adresse des Geschäftspartners', 'Identifiziert die Adresse des Geschäftspartners', null, null, null, null, 'N', null, null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000283 and ad_language='de_DE');

INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
SELECT 1000283, 'en_US', 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, 'Partner Location', 'Partner Location', 'Identifies the (ship to) address for this Business Partner', 'The Partner address indicates the location of a Business Partner', null, null, null, null, 'Y', null, null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000283 and ad_language='en_US');

INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
SELECT 1000283, 'nl_NL', 0, 0, 'Y', '2021-07-26 09:18:17.647262 +00:00', -1, '2021-07-26 09:18:17.647262 +00:00', -1, 'Standort', 'Standort', 'Identifiziert die (Liefer-) Adresse des Geschäftspartners', 'Identifiziert die Adresse des Geschäftspartners', null, null, null, null, 'N', null, null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000283 and ad_language='nl_NL');

-- ----
-- 
INSERT INTO ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew)
SELECT 1000284, 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, null, 'D', 'Zielbelegart', 'Zielbelegart', 'Zielbelegart für die Umwandlung von Dokumenten', 'Sie können Dokumente umwandeln (z.B. von Angebot zu Auftrag oder Rechnung). Die Umwandlung zeigt sich in der dann vorliegenden Belegart. Der Prozess wird durch Auswahl der entsprechenden Belegaktion angestossen.', null, null, null, null, null, null, null, null, null
where not exists (select 1 from ad_element where ad_element_id=1000284);
-- 
INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
SELECT 1000284, 'de_CH', 0, 0, 'Y', '2021-07-26 09:18:13.210779 +00:00', -1, '2021-07-26 09:18:13.210779 +00:00', -1, 'Zielbelegart', 'Zielbelegart', 'Zielbelegart für die Umwandlung von Dokumenten', 'Sie können Dokumente umwandeln (z.B. von Angebot zu Auftrag oder Rechnung). Die Umwandlung zeigt sich in der dann vorliegenden Belegart. Der Prozess wird durch Auswahl der entsprechenden Belegaktion angestossen.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000284 and ad_language='de_CH');
-- 
INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
SELECT 1000284, 'de_DE', 0, 0, 'Y', '2018-11-26 07:57:00.353716 +00:00', 100, '2018-11-26 07:57:00.353716 +00:00', 100, 'Zielbelegart', 'Zielbelegart', 'Zielbelegart für die Umwandlung von Dokumenten', 'Sie können Dokumente umwandeln (z.B. von Angebot zu Auftrag oder Rechnung). Die Umwandlung zeigt sich in der dann vorliegenden Belegart. Der Prozess wird durch Auswahl der entsprechenden Belegaktion angestossen.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000284 and ad_language='de_DE');
-- 
INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
SELECT 1000284, 'en_US', 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, 'Target Document Type', 'Target Document Type', 'Target document type for conversing documents', 'You can convert document types (e.g. from Offer to Order or Invoice).  The conversion is then reflected in the current type.  This processing is initiated by selecting the appropriate Document Action.', null, null, null, null, 'Y', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000284 and ad_language='en_US');
-- 
INSERT INTO public.ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
 SELECT 1000284, 'nl_NL', 0, 0, 'Y', '2021-07-26 09:18:17.647262 +00:00', -1, '2021-07-26 09:18:17.647262 +00:00', -1, 'Zielbelegart', 'Zielbelegart', 'Zielbelegart für die Umwandlung von Dokumenten', 'Sie können Dokumente umwandeln (z.B. von Angebot zu Auftrag oder Rechnung). Die Umwandlung zeigt sich in der dann vorliegenden Belegart. Der Prozess wird durch Auswahl der entsprechenden Belegaktion angestossen.', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000284 and ad_language='nl_NL');


-- clean up corrupt widgetsizes with empty string leading to exception when copying windows with those widets
update ad_ui_element set widgetsize=NULL where widgetsize = '';

-- 2019-08-21T18:51:55.229Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577022,0,TO_TIMESTAMP('2019-08-21 20:51:55','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Organisation Stammdaten','Organisation Stammdaten',TO_TIMESTAMP('2019-08-21 20:51:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T18:51:55.235Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577022 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-08-21T18:52:40.259Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Organisation''s Masterdata', PrintName='Organisation''s Masterdata',Updated=TO_TIMESTAMP('2019-08-21 20:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577022 AND AD_Language='en_US'
;

-- 2019-08-21T18:52:40.306Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577022,'en_US') 
;

-- 2019-08-21T18:53:59.659Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,577022,0,540676,TO_TIMESTAMP('2019-08-21 20:53:59','YYYY-MM-DD HH24:MI:SS'),100,'U','OrganisationStammdaten','Y','N','N','N','N','Y','Organisation Stammdaten','N',TO_TIMESTAMP('2019-08-21 20:53:59','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-08-21T18:53:59.662Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540676 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-08-21T18:53:59.666Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(577022) 
;

-- 2019-08-21T18:53:59.674Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540676
;

-- 2019-08-21T18:53:59.678Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540676)
;

-- 2019-08-21T19:50:37.997Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Element_ID=574339, AD_Image_ID=NULL, AD_Org_ID=0, Description=NULL, EntityType='U', Help=NULL, IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsOneInstanceOnly='N', IsSOTrx='Y', Processing='N', WindowType='M', WinHeight=0, WinWidth=0,Updated=TO_TIMESTAMP('2019-08-21 21:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540676
;

-- 2019-08-21T19:50:37.999Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(574339) 
;

-- 2019-08-21T19:50:38.002Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540676
;

-- 2019-08-21T19:50:38.002Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540676)
;

-- 2019-08-21T19:50:38.004Z
-- URL zum Konzept
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 540676
;

-- 2019-08-21T19:50:38.005Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 540676, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540366
;

-- 2019-08-21T19:50:38.163Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573762,0,541852,291,540676,'Y',TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','C_BPartner','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Geschäftspartner','N',10,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.164Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541852 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T19:50:38.165Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(573762) 
;

-- 2019-08-21T19:50:38.167Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541852)
;

-- 2019-08-21T19:50:38.169Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541852
;

-- 2019-08-21T19:50:38.169Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541852, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540871
;

-- 2019-08-21T19:50:38.249Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541936,583056,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Kundencockpit_includedTab3',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.249Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.251Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540456) 
;

-- 2019-08-21T19:50:38.253Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583056
;

-- 2019-08-21T19:50:38.254Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583056)
;

-- 2019-08-21T19:50:38.255Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583056
;

-- 2019-08-21T19:50:38.255Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583056, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559953
;

-- 2019-08-21T19:50:38.362Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541698,583057,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Eltern-Sponsor editierbar',20,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.362Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.364Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540408) 
;

-- 2019-08-21T19:50:38.365Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583057
;

-- 2019-08-21T19:50:38.366Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583057)
;

-- 2019-08-21T19:50:38.368Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583057
;

-- 2019-08-21T19:50:38.368Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583057, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559954
;

-- 2019-08-21T19:50:38.437Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2897,583058,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',30,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.438Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.440Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-08-21T19:50:38.486Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583058
;

-- 2019-08-21T19:50:38.486Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583058)
;

-- 2019-08-21T19:50:38.488Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583058
;

-- 2019-08-21T19:50:38.489Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583058, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559955
;

-- 2019-08-21T19:50:38.588Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2898,583059,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',40,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.590Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.592Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-08-21T19:50:38.624Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583059
;

-- 2019-08-21T19:50:38.625Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583059)
;

-- 2019-08-21T19:50:38.627Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583059
;

-- 2019-08-21T19:50:38.627Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583059, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559956
;

-- 2019-08-21T19:50:38.705Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540049,583060,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','Frachtkostenpauschale',50,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.706Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.708Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540003) 
;

-- 2019-08-21T19:50:38.711Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583060
;

-- 2019-08-21T19:50:38.711Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583060)
;

-- 2019-08-21T19:50:38.713Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583060
;

-- 2019-08-21T19:50:38.713Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583060, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559957
;

-- 2019-08-21T19:50:38.792Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,531088,583061,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,14,'U',0,'Y','N','N','N','N','N','N','N','Kreditoren-Nr',60,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.793Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.795Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531088) 
;

-- 2019-08-21T19:50:38.797Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583061
;

-- 2019-08-21T19:50:38.798Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583061)
;

-- 2019-08-21T19:50:38.800Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583061
;

-- 2019-08-21T19:50:38.801Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583061, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559958
;

-- 2019-08-21T19:50:38.865Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540364,583062,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Kundencockpit_includedTab1',70,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.866Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.869Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540066) 
;

-- 2019-08-21T19:50:38.871Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583062
;

-- 2019-08-21T19:50:38.872Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583062)
;

-- 2019-08-21T19:50:38.874Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583062
;

-- 2019-08-21T19:50:38.874Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583062, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559959
;

-- 2019-08-21T19:50:38.944Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541724,583063,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung',10,'U','"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','N','N','N','N','N','N','N','Lieferweg',80,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:38.946Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:38.949Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455) 
;

-- 2019-08-21T19:50:38.955Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583063
;

-- 2019-08-21T19:50:38.956Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583063)
;

-- 2019-08-21T19:50:38.958Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583063
;

-- 2019-08-21T19:50:38.959Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583063, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559960
;

-- 2019-08-21T19:50:39.037Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540377,583064,1000284,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Zielbelegart für die Umwandlung von Dokumenten',22,'U','Sie können Dokumente umwandeln (z.B. von Angebot zu Auftrag oder Rechnung). Die Umwandlung zeigt sich in der dann vorliegenden Belegart. Der Prozess wird durch Auswahl der entsprechenden Belegaktion angestossen.',0,'Y','N','N','N','N','N','N','N','Zielbelegart',90,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.038Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.040Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000284) 
;

-- 2019-08-21T19:50:39.041Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583064
;

-- 2019-08-21T19:50:39.042Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583064)
;

-- 2019-08-21T19:50:39.044Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583064
;

-- 2019-08-21T19:50:39.045Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583064, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559961
;

-- 2019-08-21T19:50:39.112Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4429,583065,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.',14,'U',0,'Y','N','N','N','N','N','N','N','Rechnungsstellung',100,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.113Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.116Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(559) 
;

-- 2019-08-21T19:50:39.122Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583065
;

-- 2019-08-21T19:50:39.123Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583065)
;

-- 2019-08-21T19:50:39.125Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583065
;

-- 2019-08-21T19:50:39.126Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583065, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559962
;

-- 2019-08-21T19:50:39.198Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2906,583066,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Dun & Bradstreet - Nummer',20,'U','Für EDI verwendet - Details unter www.dnb.com/dunsno/list.htm',0,'Y','N','N','N','N','N','N','N','D-U-N-S',110,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.199Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.202Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(260) 
;

-- 2019-08-21T19:50:39.207Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583066
;

-- 2019-08-21T19:50:39.208Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583066)
;

-- 2019-08-21T19:50:39.210Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583066
;

-- 2019-08-21T19:50:39.210Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583066, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559963
;

-- 2019-08-21T19:50:39.277Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2910,583067,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Standard Industry Code oder sein Nachfolger NAIC - http://www.osha.gov/oshstats/sicser.html',20,'U','NAICS/SIC identifiziert einen dieser Codes der für den Geschäftspartner zutrifft',0,'Y','N','N','N','N','N','N','N','NAICS/SIC',120,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.278Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.280Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(468) 
;

-- 2019-08-21T19:50:39.284Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583067
;

-- 2019-08-21T19:50:39.284Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583067)
;

-- 2019-08-21T19:50:39.286Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583067
;

-- 2019-08-21T19:50:39.286Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583067, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559964
;

-- 2019-08-21T19:50:39.359Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,505271,583068,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'U','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.',0,'Y','N','N','N','N','N','N','N','Preissystem',130,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.360Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.362Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135) 
;

-- 2019-08-21T19:50:39.365Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583068
;

-- 2019-08-21T19:50:39.366Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583068)
;

-- 2019-08-21T19:50:39.367Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583068
;

-- 2019-08-21T19:50:39.367Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583068, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559965
;

-- 2019-08-21T19:50:39.432Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2899,583069,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',140,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.432Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.434Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-08-21T19:50:39.456Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583069
;

-- 2019-08-21T19:50:39.456Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583069)
;

-- 2019-08-21T19:50:39.457Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583069
;

-- 2019-08-21T19:50:39.458Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583069, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559966
;

-- 2019-08-21T19:50:39.555Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,54463,583070,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','Tax Group',150,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.556Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.557Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53356) 
;

-- 2019-08-21T19:50:39.558Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583070
;

-- 2019-08-21T19:50:39.558Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583070)
;

-- 2019-08-21T19:50:39.560Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583070
;

-- 2019-08-21T19:50:39.560Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583070, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559968
;

-- 2019-08-21T19:50:39.628Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543967,583071,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','N','N','Shipping Notification Email',160,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.629Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.632Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541036) 
;

-- 2019-08-21T19:50:39.633Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583071
;

-- 2019-08-21T19:50:39.634Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583071)
;

-- 2019-08-21T19:50:39.636Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583071
;

-- 2019-08-21T19:50:39.636Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583071, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559969
;

-- 2019-08-21T19:50:39.712Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53246,583072,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,7,'U',0,'Y','N','N','N','N','N','N','N','Dunning Grace Date',170,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.713Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.716Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53223) 
;

-- 2019-08-21T19:50:39.718Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583072
;

-- 2019-08-21T19:50:39.718Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583072)
;

-- 2019-08-21T19:50:39.720Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583072
;

-- 2019-08-21T19:50:39.721Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583072, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559970
;

-- 2019-08-21T19:50:39.792Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3084,583073,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird',14,'U','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',0,'Y','N','N','N','N','N','N','N','Zahlungsweise',180,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.793Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.796Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143) 
;

-- 2019-08-21T19:50:39.803Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583073
;

-- 2019-08-21T19:50:39.804Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583073)
;

-- 2019-08-21T19:50:39.806Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583073
;

-- 2019-08-21T19:50:39.806Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583073, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559971
;

-- 2019-08-21T19:50:39.884Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3086,583074,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu druckenden Kopien',11,'U','"Kopien" gibt die Anzahl der Kopien an, die von jedem Dokument generiert werden sollen.',0,'Y','N','N','N','N','N','N','N','Kopien',190,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.885Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.887Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(866) 
;

-- 2019-08-21T19:50:39.891Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583074
;

-- 2019-08-21T19:50:39.891Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583074)
;

-- 2019-08-21T19:50:39.894Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583074
;

-- 2019-08-21T19:50:39.894Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583074, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559972
;

-- 2019-08-21T19:50:39.969Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2929,583075,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if  the business partner is a sales representative or company agent',1,'U','The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.',0,'Y','N','N','N','N','N','N','N','Vertriebsbeauftragter',200,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:39.970Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:39.973Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(409) 
;

-- 2019-08-21T19:50:39.977Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583075
;

-- 2019-08-21T19:50:39.977Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583075)
;

-- 2019-08-21T19:50:39.979Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583075
;

-- 2019-08-21T19:50:39.980Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583075, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559973
;

-- 2019-08-21T19:50:40.068Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3085,583076,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Rules for overdue invoices',14,'U','The Dunning indicates the rules and method of dunning for past due payments.',0,'Y','N','N','N','N','N','N','N','Mahnung',210,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.070Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.073Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(838) 
;

-- 2019-08-21T19:50:40.078Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583076
;

-- 2019-08-21T19:50:40.078Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583076)
;

-- 2019-08-21T19:50:40.081Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583076
;

-- 2019-08-21T19:50:40.081Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583076, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559974
;

-- 2019-08-21T19:50:40.152Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12406,583077,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Flat discount percentage ',26,'U',0,'Y','N','N','N','N','N','N','N','Fester Rabatt %',220,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.154Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.157Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1712) 
;

-- 2019-08-21T19:50:40.161Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583077
;

-- 2019-08-21T19:50:40.161Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583077)
;

-- 2019-08-21T19:50:40.164Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583077
;

-- 2019-08-21T19:50:40.164Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583077, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559975
;

-- 2019-08-21T19:50:40.233Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540365,583078,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Kundencockpit_includedTab2',230,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.234Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.236Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540067) 
;

-- 2019-08-21T19:50:40.238Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583078
;

-- 2019-08-21T19:50:40.238Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583078)
;

-- 2019-08-21T19:50:40.241Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583078
;

-- 2019-08-21T19:50:40.241Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583078, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559976
;

-- 2019-08-21T19:50:40.312Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2900,583079,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',240,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.313Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.316Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-08-21T19:50:40.348Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583079
;

-- 2019-08-21T19:50:40.348Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583079)
;

-- 2019-08-21T19:50:40.350Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583079
;

-- 2019-08-21T19:50:40.351Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583079, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559977
;

-- 2019-08-21T19:50:40.429Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,505276,583080,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','Einkaufspreissystem',250,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.430Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.431Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505274) 
;

-- 2019-08-21T19:50:40.433Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583080
;

-- 2019-08-21T19:50:40.433Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583080)
;

-- 2019-08-21T19:50:40.434Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583080
;

-- 2019-08-21T19:50:40.434Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583080, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559978
;

-- 2019-08-21T19:50:40.506Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,531087,583081,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,14,'U',0,'Y','N','N','N','N','N','N','N','Debitoren-Nr',260,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.507Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.510Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531087) 
;

-- 2019-08-21T19:50:40.512Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583081
;

-- 2019-08-21T19:50:40.512Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583081)
;

-- 2019-08-21T19:50:40.515Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583081
;

-- 2019-08-21T19:50:40.515Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583081, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559979
;

-- 2019-08-21T19:50:40.591Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541957,583082,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Auftrag anlegen',270,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.592Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.595Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2388) 
;

-- 2019-08-21T19:50:40.597Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583082
;

-- 2019-08-21T19:50:40.597Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583082)
;

-- 2019-08-21T19:50:40.599Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583082
;

-- 2019-08-21T19:50:40.600Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583082, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559980
;

-- 2019-08-21T19:50:40.681Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3083,583083,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Klassifizierung oder Wichtigkeit',11,'U','Das "Rating" wird verwendet um Wichtigkeit zu unterscheiden.',0,'Y','N','N','N','N','N','N','Y','Rating',280,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.682Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.685Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(962) 
;

-- 2019-08-21T19:50:40.689Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583083
;

-- 2019-08-21T19:50:40.690Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583083)
;

-- 2019-08-21T19:50:40.692Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583083
;

-- 2019-08-21T19:50:40.692Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583083, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559981
;

-- 2019-08-21T19:50:40.769Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,502674,583084,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Sammel-Lieferscheine erlaubt',290,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.770Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.773Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(502393) 
;

-- 2019-08-21T19:50:40.775Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583084
;

-- 2019-08-21T19:50:40.776Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583084)
;

-- 2019-08-21T19:50:40.778Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583084
;

-- 2019-08-21T19:50:40.778Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583084, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559983
;

-- 2019-08-21T19:50:40.848Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6580,583085,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Schema to calculate the purchase trade discount percentage',14,'U',0,'Y','N','N','N','N','N','N','N','Einkauf Rabatt Schema',300,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.849Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.852Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1717) 
;

-- 2019-08-21T19:50:40.856Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583085
;

-- 2019-08-21T19:50:40.857Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583085)
;

-- 2019-08-21T19:50:40.859Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583085
;

-- 2019-08-21T19:50:40.860Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583085, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559985
;

-- 2019-08-21T19:50:40.936Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2893,583086,1000998,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',14,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','N','N','N','N','N','N','Geschäftspartner',310,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:40.937Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:40.939Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000998) 
;

-- 2019-08-21T19:50:40.940Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583086
;

-- 2019-08-21T19:50:40.941Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583086)
;

-- 2019-08-21T19:50:40.943Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583086
;

-- 2019-08-21T19:50:40.943Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583086, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559986
;

-- 2019-08-21T19:50:41.020Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2909,583087,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Steuernummer',20,'U','"Steuer-ID" gibt die offizielle Steuernummer für diese Einheit an.',0,'Y','N','N','N','N','N','N','N','Steuer-ID',320,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.021Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.024Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(590) 
;

-- 2019-08-21T19:50:41.028Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583087
;

-- 2019-08-21T19:50:41.029Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583087)
;

-- 2019-08-21T19:50:41.031Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583087
;

-- 2019-08-21T19:50:41.032Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583087, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559987
;

-- 2019-08-21T19:50:41.100Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2927,583088,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if  this Business Partner is an employee',1,'U','The Employee checkbox indicates if this Business Partner is an Employee.  If it is selected, additional fields will display which further identify this employee.',0,'Y','N','N','N','N','N','N','N','Mitarbeiter',330,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.101Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.103Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(373) 
;

-- 2019-08-21T19:50:41.108Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583088
;

-- 2019-08-21T19:50:41.108Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583088)
;

-- 2019-08-21T19:50:41.110Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583088
;

-- 2019-08-21T19:50:41.110Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583088, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559988
;

-- 2019-08-21T19:50:41.177Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3080,583089,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','Y','One time transaction',340,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.178Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.181Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(922) 
;

-- 2019-08-21T19:50:41.185Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583089
;

-- 2019-08-21T19:50:41.185Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583089)
;

-- 2019-08-21T19:50:41.188Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583089
;

-- 2019-08-21T19:50:41.188Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583089, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559989
;

-- 2019-08-21T19:50:41.257Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2922,583090,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Die Kosten um den Interessenten als Kunden zu gewinnen',26,'@IsEmployee@=N','U','"Akquisitionskosten" bezeichnet die Kosten, die aufgewendet wurden um den Interessenten als Kunden zu gewinnen.',0,'Y','N','N','N','N','N','N','N','Akquisitionskosten',350,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.259Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.261Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(151) 
;

-- 2019-08-21T19:50:41.265Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583090
;

-- 2019-08-21T19:50:41.266Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583090)
;

-- 2019-08-21T19:50:41.268Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583090
;

-- 2019-08-21T19:50:41.269Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583090, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559990
;

-- 2019-08-21T19:50:41.343Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2919,583091,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Ersten Verkaufs',14,'@IsEmployee@=N','U','"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.',0,'Y','N','N','N','N','N','N','N','Erster Verkauf',360,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.344Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.347Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(305) 
;

-- 2019-08-21T19:50:41.351Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583091
;

-- 2019-08-21T19:50:41.351Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583091)
;

-- 2019-08-21T19:50:41.354Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583091
;

-- 2019-08-21T19:50:41.354Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583091, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559991
;

-- 2019-08-21T19:50:41.420Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,58381,583092,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Business partner is exempt from tax on purchases',1,'U','If a business partner is exempt from tax on purchases, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.',0,'Y','N','N','N','N','N','N','Y','PO Tax exempt',370,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.421Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.423Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53926) 
;

-- 2019-08-21T19:50:41.425Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583092
;

-- 2019-08-21T19:50:41.425Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583092)
;

-- 2019-08-21T19:50:41.428Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583092
;

-- 2019-08-21T19:50:41.428Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583092, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559992
;

-- 2019-08-21T19:50:41.492Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2918,583093,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Kennzeichnet einen Interessenten oder Kunden',1,'@IsEmployee@=N','U','Das Selektionsfeld zeigt an, ob es sich um einen aktiven Interessenten oder Kunden handelt.',0,'Y','N','N','N','N','N','N','N','Aktiver Interessent/Kunde',380,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.493Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.496Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(402) 
;

-- 2019-08-21T19:50:41.500Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583093
;

-- 2019-08-21T19:50:41.500Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583093)
;

-- 2019-08-21T19:50:41.503Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583093
;

-- 2019-08-21T19:50:41.503Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583093, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559993
;

-- 2019-08-21T19:50:41.576Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2911,583094,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'U','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','N','N','N','N','N','N','Y','Zusammenfassungseintrag',390,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.577Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.579Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416) 
;

-- 2019-08-21T19:50:41.587Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583094
;

-- 2019-08-21T19:50:41.587Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583094)
;

-- 2019-08-21T19:50:41.589Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583094
;

-- 2019-08-21T19:50:41.589Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583094, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559994
;

-- 2019-08-21T19:50:41.666Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2923,583095,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Erwarteter Gesamtertrag',26,'@IsEmployee@=N','U','"Möglicher Gesamtertrag" ist der voraussichtliche Ertrag in Buchführungswährung, der durch diesen Geschäftspartner generiert wird.',0,'Y','N','N','N','N','N','N','N','Möglicher Gesamtertrag',400,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.666Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.668Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(515) 
;

-- 2019-08-21T19:50:41.670Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583095
;

-- 2019-08-21T19:50:41.670Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583095)
;

-- 2019-08-21T19:50:41.672Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583095
;

-- 2019-08-21T19:50:41.672Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583095, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559995
;

-- 2019-08-21T19:50:41.733Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2905,583096,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Ihre Kunden- oder Lieferantennummer beim Geschäftspartner',11,'U','Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.',0,'Y','N','N','N','N','N','N','Y','Referenznummer',410,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.733Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.734Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540) 
;

-- 2019-08-21T19:50:41.737Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583096
;

-- 2019-08-21T19:50:41.737Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583096)
;

-- 2019-08-21T19:50:41.738Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583096
;

-- 2019-08-21T19:50:41.738Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583096, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559996
;

-- 2019-08-21T19:50:41.805Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2904,583097,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Verkaufsvolumen in Tausend der Buchführungswährung',11,'@IsEmployee@=N','U','"Verkaufsvolumen" zeigt den Gesamtumfang der Verkäufe für einen Geschäftspartner an.',0,'Y','N','N','N','N','N','N','Y','Verkaufsvolumen in 1.000',420,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.806Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.807Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(563) 
;

-- 2019-08-21T19:50:41.809Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583097
;

-- 2019-08-21T19:50:41.809Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583097)
;

-- 2019-08-21T19:50:41.810Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583097
;

-- 2019-08-21T19:50:41.810Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583097, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559997
;

-- 2019-08-21T19:50:41.884Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2926,583098,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Anteil dieses Kunden in Prozent',11,'@IsEmployee@=N','U','"Anteil" zeigt den prozentualen Anteil dieses Geschäftspartners an den gelieferten Produkten an.',0,'Y','N','N','N','N','N','N','N','Anteil',430,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.885Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.887Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(569) 
;

-- 2019-08-21T19:50:41.891Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583098
;

-- 2019-08-21T19:50:41.891Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583098)
;

-- 2019-08-21T19:50:41.893Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583098
;

-- 2019-08-21T19:50:41.894Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583098, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559998
;

-- 2019-08-21T19:50:41.964Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541694,583099,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,255,'U',0,'Y','N','N','N','N','N','N','N','Nachname',440,0,999,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:41.965Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:41.968Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540399) 
;

-- 2019-08-21T19:50:41.970Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583099
;

-- 2019-08-21T19:50:41.971Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583099)
;

-- 2019-08-21T19:50:41.973Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583099
;

-- 2019-08-21T19:50:41.974Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583099, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 559999
;

-- 2019-08-21T19:50:42.039Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541693,583100,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Vorname',255,'U',0,'Y','N','N','N','N','N','N','N','Vorname',450,0,999,1,TO_TIMESTAMP('2019-08-21 21:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.041Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.044Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540398) 
;

-- 2019-08-21T19:50:42.046Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583100
;

-- 2019-08-21T19:50:42.047Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583100)
;

-- 2019-08-21T19:50:42.049Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583100
;

-- 2019-08-21T19:50:42.049Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583100, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560000
;

-- 2019-08-21T19:50:42.121Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4302,583101,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibung zur Verwendung auf Aufträgen',60,'U','"Beschreibung Auftrag" git die Standardbeschreibung an, die auf Afträgen für diesen Kunden verwendet werden soll.',0,'Y','N','N','N','N','N','N','N','Beschreibung Auftrag',460,0,999,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.123Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.126Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1244) 
;

-- 2019-08-21T19:50:42.129Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583101
;

-- 2019-08-21T19:50:42.129Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583101)
;

-- 2019-08-21T19:50:42.132Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583101
;

-- 2019-08-21T19:50:42.132Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583101, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560001
;

-- 2019-08-21T19:50:42.210Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10927,583102,0,541852,169,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'The Business Partner is another Organization for explicit Inter-Org transactions',23,'@IsEmployee@=N','U','The business partner is another organization in the system. So when performing transactions, the counter-document is created automatically. Example: You have BPartnerA linked to OrgA and BPartnerB linked to OrgB.  If you create a sales order for BPartnerB in OrgA a purchase order is created for BPartnerA in OrgB.  This allows to have explicit documents for Inter-Org transactions.',0,'Y','N','N','N','N','N','N','Y','Linked Organization',470,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.211Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.214Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2354) 
;

-- 2019-08-21T19:50:42.217Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583102
;

-- 2019-08-21T19:50:42.218Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583102)
;

-- 2019-08-21T19:50:42.220Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583102
;

-- 2019-08-21T19:50:42.220Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583102, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560002
;

-- 2019-08-21T19:50:42.296Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541687,583103,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,255,'U',0,'Y','N','N','N','N','N','N','N','SO_TargetDocTypeReason',480,0,999,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.297Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.300Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540401) 
;

-- 2019-08-21T19:50:42.301Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583103
;

-- 2019-08-21T19:50:42.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583103)
;

-- 2019-08-21T19:50:42.304Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583103
;

-- 2019-08-21T19:50:42.304Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583103, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560003
;

-- 2019-08-21T19:50:42.381Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4433,583104,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird',14,'U','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','N','N','N','N','N','N','N','Lieferung',490,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.382Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.384Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(274) 
;

-- 2019-08-21T19:50:42.390Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583104
;

-- 2019-08-21T19:50:42.390Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583104)
;

-- 2019-08-21T19:50:42.393Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583104
;

-- 2019-08-21T19:50:42.393Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583104, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560004
;

-- 2019-08-21T19:50:42.465Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3082,583105,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz steuerbefreit',1,'U','If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.',0,'Y','N','N','N','N','N','N','N','Steuerbefreit',500,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.466Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.469Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(930) 
;

-- 2019-08-21T19:50:42.472Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583105
;

-- 2019-08-21T19:50:42.472Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583105)
;

-- 2019-08-21T19:50:42.474Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583105
;

-- 2019-08-21T19:50:42.475Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583105, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560005
;

-- 2019-08-21T19:50:42.543Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4291,583106,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz',14,'U','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.',0,'Y','N','N','N','N','N','N','N','Anrede (ID)',510,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.544Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.545Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1159) 
;

-- 2019-08-21T19:50:42.549Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583106
;

-- 2019-08-21T19:50:42.549Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583106)
;

-- 2019-08-21T19:50:42.550Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583106
;

-- 2019-08-21T19:50:42.550Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583106, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560006
;

-- 2019-08-21T19:50:42.612Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4432,583107,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Methode zur Berechnung von Frachtkosten',14,'U','"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.',0,'Y','N','N','N','N','N','N','N','Frachtkostenberechnung',520,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.613Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.614Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1007) 
;

-- 2019-08-21T19:50:42.617Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583107
;

-- 2019-08-21T19:50:42.617Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583107)
;

-- 2019-08-21T19:50:42.618Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583107
;

-- 2019-08-21T19:50:42.619Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583107, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560007
;

-- 2019-08-21T19:50:42.688Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2924,583108,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'U','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','N','N','N','N','N','N','N','Zahlungsbedingung',530,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.689Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.691Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2019-08-21T19:50:42.696Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583108
;

-- 2019-08-21T19:50:42.696Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583108)
;

-- 2019-08-21T19:50:42.698Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583108
;

-- 2019-08-21T19:50:42.698Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583108, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560009
;

-- 2019-08-21T19:50:42.774Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2917,583109,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Plan für die Rechnungsstellung',14,'U','"Rechnungsintervall" definiert die Häufigkeit mit der Sammelrechnungen erstellt werden.',0,'Y','N','N','N','N','N','N','N','Terminplan Rechnung',540,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.774Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.777Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(560) 
;

-- 2019-08-21T19:50:42.780Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583109
;

-- 2019-08-21T19:50:42.781Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583109)
;

-- 2019-08-21T19:50:42.782Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583109
;

-- 2019-08-21T19:50:42.783Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583109, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560010
;

-- 2019-08-21T19:50:42.852Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5826,583110,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Zahlungskondition für die Bestellung',14,'U','Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.',0,'Y','N','N','N','N','N','N','N','Zahlungskondition',550,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.853Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.855Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1576) 
;

-- 2019-08-21T19:50:42.859Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583110
;

-- 2019-08-21T19:50:42.859Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583110)
;

-- 2019-08-21T19:50:42.861Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583110
;

-- 2019-08-21T19:50:42.862Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583110, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560011
;

-- 2019-08-21T19:50:42.983Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4215,583111,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'U','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','N','N','N','N','N','N','N','Referenz',560,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:42.985Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:42.987Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2019-08-21T19:50:42.996Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583111
;

-- 2019-08-21T19:50:42.997Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583111)
;

-- 2019-08-21T19:50:42.999Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583111
;

-- 2019-08-21T19:50:43Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583111, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560012
;

-- 2019-08-21T19:50:43.088Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4021,583112,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Möglichkeiten der Bezahlung einer Bestellung',14,'U','"Zahlungsweise" zeigt die Arten der Zahlungen für Einkäufe an.',0,'Y','N','N','N','N','N','N','N','Zahlungsweise',570,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.090Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.093Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(950) 
;

-- 2019-08-21T19:50:43.097Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583112
;

-- 2019-08-21T19:50:43.097Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583112)
;

-- 2019-08-21T19:50:43.101Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583112
;

-- 2019-08-21T19:50:43.101Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583112, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560013
;

-- 2019-08-21T19:50:43.177Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6579,583113,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen',14,'U','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.',0,'Y','N','N','N','N','N','N','N','Rabatt Schema',580,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.179Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.181Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1714) 
;

-- 2019-08-21T19:50:43.186Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583113
;

-- 2019-08-21T19:50:43.186Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583113)
;

-- 2019-08-21T19:50:43.188Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583113
;

-- 2019-08-21T19:50:43.189Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583113, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560014
;

-- 2019-08-21T19:50:43.269Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4301,583114,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Rabatt auf Rechnung und Bestellung drucken',1,'U','Zeigt an, ob der Rabtt auf dem Dokument gedruckt werden soll',0,'Y','N','N','N','N','N','N','N','Rabatte drucken',590,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.270Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.273Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1239) 
;

-- 2019-08-21T19:50:43.278Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583114
;

-- 2019-08-21T19:50:43.279Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583114)
;

-- 2019-08-21T19:50:43.281Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583114
;

-- 2019-08-21T19:50:43.282Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583114, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560015
;

-- 2019-08-21T19:50:43.357Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2916,583115,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschäftspartner ein Kunde ist',1,'U','The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.',0,'Y','N','N','N','N','N','N','N','Kunde',600,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.358Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.361Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(364) 
;

-- 2019-08-21T19:50:43.365Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583115
;

-- 2019-08-21T19:50:43.366Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583115)
;

-- 2019-08-21T19:50:43.368Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583115
;

-- 2019-08-21T19:50:43.368Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583115, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560016
;

-- 2019-08-21T19:50:43.445Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4431,583116,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'',14,'U','',0,'Y','N','N','N','N','N','N','N','Verkaufsmitarbeiter',610,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.449Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2019-08-21T19:50:43.463Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583116
;

-- 2019-08-21T19:50:43.463Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583116)
;

-- 2019-08-21T19:50:43.465Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583116
;

-- 2019-08-21T19:50:43.466Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583116, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560017
;

-- 2019-08-21T19:50:43.546Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8167,583117,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Enable sending Document EMail',1,'U','Send emails with document attached (e.g. Invoice, Delivery Note, etc.)',0,'Y','N','N','N','N','N','N','N','E-Mail senden',620,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.548Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.550Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1978) 
;

-- 2019-08-21T19:50:43.558Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583117
;

-- 2019-08-21T19:50:43.558Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583117)
;

-- 2019-08-21T19:50:43.561Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583117
;

-- 2019-08-21T19:50:43.561Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583117, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560018
;

-- 2019-08-21T19:50:43.636Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8768,583118,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Parent',14,'U','The parent (organization) of the Business Partner for reporting purposes.',0,'Y','N','N','N','N','N','N','N','Partner Parent',630,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.637Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.639Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2031) 
;

-- 2019-08-21T19:50:43.643Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583118
;

-- 2019-08-21T19:50:43.644Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583118)
;

-- 2019-08-21T19:50:43.646Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583118
;

-- 2019-08-21T19:50:43.646Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583118, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560019
;

-- 2019-08-21T19:50:43.717Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9332,583119,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Format für das Drucken von Rechnungen',14,'U','You need to define a Print Format to print the document.',0,'Y','N','N','N','N','N','N','N','Druckformat Rechnung',640,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.718Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.721Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1822) 
;

-- 2019-08-21T19:50:43.724Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583119
;

-- 2019-08-21T19:50:43.725Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583119)
;

-- 2019-08-21T19:50:43.726Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583119
;

-- 2019-08-21T19:50:43.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583119, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560020
;

-- 2019-08-21T19:50:43.810Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10122,583120,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Mindesthaltbarkeit in Prozent, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz',11,'U','Miminum Shelf Life of products with Guarantee Date instance. If > 0 you cannot select products with a shelf life ((Guarantee Date-Today) / Guarantee Days) less than the minum shelf life, unless you select "Show All"',0,'Y','N','N','N','N','N','N','N','Mindesthaltbarkeit %',650,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.812Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.814Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2240) 
;

-- 2019-08-21T19:50:43.819Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583120
;

-- 2019-08-21T19:50:43.819Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583120)
;

-- 2019-08-21T19:50:43.822Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583120
;

-- 2019-08-21T19:50:43.822Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583120, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560021
;

-- 2019-08-21T19:50:43.894Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2931,583121,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Preisliste die von diesem Geschäftspartenr verwendet wird',14,'U','Gibt die Preisliste an, die von einem Lieferanten für Produkte die von dieser Organisation eingekauft werden  verwendet wird.',0,'Y','N','N','N','N','N','N','N','Einkaufspreisliste',660,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.896Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.898Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(480) 
;

-- 2019-08-21T19:50:43.901Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583121
;

-- 2019-08-21T19:50:43.902Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583121)
;

-- 2019-08-21T19:50:43.904Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583121
;

-- 2019-08-21T19:50:43.904Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583121, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560022
;

-- 2019-08-21T19:50:43.988Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2930,583122,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste',14,'U','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.',0,'Y','N','N','N','N','N','N','N','Preisliste',670,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:43.989Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:43.990Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(449) 
;

-- 2019-08-21T19:50:43.995Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583122
;

-- 2019-08-21T19:50:43.995Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583122)
;

-- 2019-08-21T19:50:43.996Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583122
;

-- 2019-08-21T19:50:43.996Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583122, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560023
;

-- 2019-08-21T19:50:44.067Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2915,583123,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist',1,'U','The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.',0,'Y','N','N','N','N','N','N','N','Lieferant',680,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.068Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.069Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(426) 
;

-- 2019-08-21T19:50:44.071Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583123
;

-- 2019-08-21T19:50:44.071Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583123)
;

-- 2019-08-21T19:50:44.072Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583123
;

-- 2019-08-21T19:50:44.072Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583123, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560024
;

-- 2019-08-21T19:50:44.144Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2894,583124,0,541852,65,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.144Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.146Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T19:50:44.303Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583124
;

-- 2019-08-21T19:50:44.304Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583124)
;

-- 2019-08-21T19:50:44.305Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583124
;

-- 2019-08-21T19:50:44.305Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583124, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560025
;

-- 2019-08-21T19:50:44.388Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2895,583125,0,541852,88,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.389Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.391Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T19:50:44.530Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583125
;

-- 2019-08-21T19:50:44.531Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583125)
;

-- 2019-08-21T19:50:44.547Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583125
;

-- 2019-08-21T19:50:44.548Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583125, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560026
;

-- 2019-08-21T19:50:44.633Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2901,583126,0,541852,81,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',11,'U','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Suchschlüssel',30,30,1,1,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.634Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.636Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2019-08-21T19:50:44.660Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583126
;

-- 2019-08-21T19:50:44.661Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583126)
;

-- 2019-08-21T19:50:44.662Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583126
;

-- 2019-08-21T19:50:44.663Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583126, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560027
;

-- 2019-08-21T19:50:44.730Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2902,583127,0,541852,236,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,'',60,'@IsCompany/N@=N','U','',0,'Y','Y','Y','N','N','N','N','N','Name',40,40,1,999,1,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.731Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.732Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-08-21T19:50:44.762Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583127
;

-- 2019-08-21T19:50:44.762Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583127)
;

-- 2019-08-21T19:50:44.764Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583127
;

-- 2019-08-21T19:50:44.764Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583127, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560028
;

-- 2019-08-21T19:50:44.839Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4216,583128,0,541852,58,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung',60,'U',0,'Y','Y','Y','N','N','N','N','N','Name Zusatz',50,50,999,1,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.840Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.842Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1111) 
;

-- 2019-08-21T19:50:44.846Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583128
;

-- 2019-08-21T19:50:44.846Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583128)
;

-- 2019-08-21T19:50:44.848Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583128
;

-- 2019-08-21T19:50:44.848Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583128, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560029
;

-- 2019-08-21T19:50:44.938Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2903,583129,0,541852,89,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,60,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2019-08-21 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:44.939Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:44.942Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-08-21T19:50:45.013Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583129
;

-- 2019-08-21T19:50:45.013Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583129)
;

-- 2019-08-21T19:50:45.015Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583129
;

-- 2019-08-21T19:50:45.016Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583129, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560030
;

-- 2019-08-21T19:50:45.092Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2896,583130,0,541852,44,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.093Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.096Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T19:50:45.296Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583130
;

-- 2019-08-21T19:50:45.296Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583130)
;

-- 2019-08-21T19:50:45.297Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583130
;

-- 2019-08-21T19:50:45.297Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583130, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560031
;

-- 2019-08-21T19:50:45.409Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550158,583131,0,541852,96,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','Y','Anbauplanung',80,190,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.410Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.412Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542317) 
;

-- 2019-08-21T19:50:45.413Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583131
;

-- 2019-08-21T19:50:45.413Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583131)
;

-- 2019-08-21T19:50:45.415Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583131
;

-- 2019-08-21T19:50:45.415Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583131, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560032
;

-- 2019-08-21T19:50:45.488Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541686,583132,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Firma',90,90,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.489Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.492Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540400) 
;

-- 2019-08-21T19:50:45.493Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583132
;

-- 2019-08-21T19:50:45.494Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583132)
;

-- 2019-08-21T19:50:45.496Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583132
;

-- 2019-08-21T19:50:45.496Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583132, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560033
;

-- 2019-08-21T19:50:45.572Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542713,583133,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,60,'@IsCompany@=Y','U',0,'Y','Y','Y','N','N','N','N','N','Y','Firmenname',100,100,999,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.574Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.577Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540648) 
;

-- 2019-08-21T19:50:45.578Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583133
;

-- 2019-08-21T19:50:45.579Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583133)
;

-- 2019-08-21T19:50:45.582Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583133
;

-- 2019-08-21T19:50:45.582Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583133, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560034
;

-- 2019-08-21T19:50:45.652Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,502638,583134,0,541852,138,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Umsatzsteuer ID',110,80,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.653Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.656Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(502388) 
;

-- 2019-08-21T19:50:45.658Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583134
;

-- 2019-08-21T19:50:45.658Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583134)
;

-- 2019-08-21T19:50:45.661Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583134
;

-- 2019-08-21T19:50:45.661Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583134, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560035
;

-- 2019-08-21T19:50:45.725Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4940,583135,0,541852,255,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe',14,'U','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.',0,'Y','Y','Y','N','N','N','N','N','Gruppe',120,110,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.729Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1383) 
;

-- 2019-08-21T19:50:45.737Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583135
;

-- 2019-08-21T19:50:45.737Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583135)
;

-- 2019-08-21T19:50:45.740Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583135
;

-- 2019-08-21T19:50:45.740Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583135, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560036
;

-- 2019-08-21T19:50:45.805Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2914,583136,0,541852,93,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Language for this Business Partner if Multi-Language enabled',14,'U','The Language identifies the language to use for display and formatting documents. It requires, that on Client level, Multi-Lingual documents are selected and that you have created/loaded the language.',0,'Y','Y','Y','N','N','N','N','Y','Sprache',123,113,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.806Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.809Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(109) 
;

-- 2019-08-21T19:50:45.828Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583136
;

-- 2019-08-21T19:50:45.828Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583136)
;

-- 2019-08-21T19:50:45.830Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583136
;

-- 2019-08-21T19:50:45.831Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583136, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560037
;

-- 2019-08-21T19:50:45.922Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548482,583137,0,541852,107,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege',0,'U',0,'Y','Y','Y','N','N','N','N','N','Erhält EDI-Belege',130,120,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:45.923Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583137 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:45.926Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542000) 
;

-- 2019-08-21T19:50:45.928Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583137
;

-- 2019-08-21T19:50:45.928Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583137)
;

-- 2019-08-21T19:50:45.930Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583137
;

-- 2019-08-21T19:50:45.931Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583137, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560038
;

-- 2019-08-21T19:50:46.004Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548483,583138,0,541852,164,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100,'EDI-ID des Dateiempfängers',0,'@IsEdiRecipient@=Y','U',0,'Y','Y','Y','N','N','N','N','N','N','EDI-ID des Dateiempfängers',140,130,1,1,TO_TIMESTAMP('2019-08-21 21:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.005Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.008Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542001) 
;

-- 2019-08-21T19:50:46.009Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583138
;

-- 2019-08-21T19:50:46.010Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583138)
;

-- 2019-08-21T19:50:46.012Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583138
;

-- 2019-08-21T19:50:46.013Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583138, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560039
;

-- 2019-08-21T19:50:46.073Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553173,583139,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',0,'@IsEdiRecipient@=Y','U',0,'Y','Y','Y','N','N','N','N','Y','"CU pro TU" bei unbestimmter Verpackungskapazität',144,134,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.075Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.077Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542978) 
;

-- 2019-08-21T19:50:46.078Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583139
;

-- 2019-08-21T19:50:46.079Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583139)
;

-- 2019-08-21T19:50:46.081Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583139
;

-- 2019-08-21T19:50:46.082Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583139, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560040
;

-- 2019-08-21T19:50:46.147Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3081,583140,0,541852,206,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',11,'U','The URL defines an fully qualified web address like http://www.adempiere.org',0,'Y','Y','Y','N','N','N','N','N','URL',160,160,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.148Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.150Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(983) 
;

-- 2019-08-21T19:50:46.155Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583140
;

-- 2019-08-21T19:50:46.155Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583140)
;

-- 2019-08-21T19:50:46.157Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583140
;

-- 2019-08-21T19:50:46.157Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583140, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560041
;

-- 2019-08-21T19:50:46.223Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555108,583141,0,541852,206,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,11,'U',0,'Y','Y','Y','N','N','N','N','Y','Rechnung per eMail',161,161,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.224Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.225Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543189) 
;

-- 2019-08-21T19:50:46.226Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583141
;

-- 2019-08-21T19:50:46.227Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583141)
;

-- 2019-08-21T19:50:46.228Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583141
;

-- 2019-08-21T19:50:46.229Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583141, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560042
;

-- 2019-08-21T19:50:46.299Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553170,583142,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Autom. Referenz',163,173,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.300Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.301Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542973) 
;

-- 2019-08-21T19:50:46.302Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583142
;

-- 2019-08-21T19:50:46.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583142)
;

-- 2019-08-21T19:50:46.304Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583142
;

-- 2019-08-21T19:50:46.304Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583142, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560043
;

-- 2019-08-21T19:50:46.379Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553171,583143,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen',0,'@IsCreateDefaultPOReference@=''Y''','U','Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>',0,'Y','Y','Y','N','N','N','N','Y','Referenz Vorgabe',165,175,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.379Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.381Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542974) 
;

-- 2019-08-21T19:50:46.381Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583143
;

-- 2019-08-21T19:50:46.382Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583143)
;

-- 2019-08-21T19:50:46.383Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583143
;

-- 2019-08-21T19:50:46.383Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583143, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560044
;

-- 2019-08-21T19:50:46.458Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2907,583144,0,541852,102,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Mitarbeiter',11,'@IsEmployee@=N','U','Indicates the number of employees for this Business Partner.  This field displays only for Prospects.',0,'Y','Y','Y','N','N','N','N','Y','Anzahl Beschäftigte',170,170,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.458Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.460Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(473) 
;

-- 2019-08-21T19:50:46.463Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583144
;

-- 2019-08-21T19:50:46.463Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583144)
;

-- 2019-08-21T19:50:46.465Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583144
;

-- 2019-08-21T19:50:46.465Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583144, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560045
;

-- 2019-08-21T19:50:46.529Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550429,583145,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,0,'U','Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.',0,'Y','Y','Y','N','N','N','N','N','Replikations Standardwert',180,200,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.530Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.533Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542394) 
;

-- 2019-08-21T19:50:46.535Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583145
;

-- 2019-08-21T19:50:46.535Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583145)
;

-- 2019-08-21T19:50:46.537Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583145
;

-- 2019-08-21T19:50:46.538Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583145, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560046
;

-- 2019-08-21T19:50:46.603Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,58113,50012,583146,0,541852,44,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','Y','Y','N','N','N','N','N','Logo',190,180,999,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.605Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.607Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53909) 
;

-- 2019-08-21T19:50:46.609Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583146
;

-- 2019-08-21T19:50:46.609Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583146)
;

-- 2019-08-21T19:50:46.611Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583146
;

-- 2019-08-21T19:50:46.611Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583146, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560047
;

-- 2019-08-21T19:50:46.685Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551767,540077,583147,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'',1,'U',0,'Y','Y','Y','N','N','N','N','N','MRP ausschliessen',200,210,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.686Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.689Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542697) 
;

-- 2019-08-21T19:50:46.692Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583147
;

-- 2019-08-21T19:50:46.692Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583147)
;

-- 2019-08-21T19:50:46.694Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583147
;

-- 2019-08-21T19:50:46.695Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583147, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560048
;

-- 2019-08-21T19:50:46.771Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552450,540079,583148,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Memo Gebinde',0,'U',0,'Y','Y','Y','N','N','N','N','N','Memo Handling Unit',210,250,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.772Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.774Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542818) 
;

-- 2019-08-21T19:50:46.776Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583148
;

-- 2019-08-21T19:50:46.777Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583148)
;

-- 2019-08-21T19:50:46.779Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583148
;

-- 2019-08-21T19:50:46.780Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583148, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560049
;

-- 2019-08-21T19:50:46.848Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552451,540079,583149,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Memo Lieferung',0,'U',0,'Y','Y','Y','N','N','N','N','N','Memo Lieferung',220,260,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.849Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.850Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542819) 
;

-- 2019-08-21T19:50:46.851Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583149
;

-- 2019-08-21T19:50:46.851Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583149)
;

-- 2019-08-21T19:50:46.854Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583149
;

-- 2019-08-21T19:50:46.854Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583149, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560050
;

-- 2019-08-21T19:50:46.926Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552452,540079,583150,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Memo Abrechnung',0,'U',0,'Y','Y','Y','N','N','N','N','N','Memo Rechnung',230,270,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.927Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.928Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542820) 
;

-- 2019-08-21T19:50:46.929Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583150
;

-- 2019-08-21T19:50:46.930Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583150)
;

-- 2019-08-21T19:50:46.931Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583150
;

-- 2019-08-21T19:50:46.932Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583150, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560051
;

-- 2019-08-21T19:50:46.995Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542502,540079,583151,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text',0,'U',0,'Y','Y','Y','N','N','N','N','N','Memo',240,280,1,1,TO_TIMESTAMP('2019-08-21 21:50:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:46.996Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:46.998Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2112) 
;

-- 2019-08-21T19:50:47.001Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583151
;

-- 2019-08-21T19:50:47.001Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583151)
;

-- 2019-08-21T19:50:47.003Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583151
;

-- 2019-08-21T19:50:47.004Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583151, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560052
;

-- 2019-08-21T19:50:47.093Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4430,583152,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen',14,'U','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','Y','N','N','N','N','N','N','Lieferart',250,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.095Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.097Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(555) 
;

-- 2019-08-21T19:50:47.105Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583152
;

-- 2019-08-21T19:50:47.105Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583152)
;

-- 2019-08-21T19:50:47.107Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583152
;

-- 2019-08-21T19:50:47.108Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583152, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560053
;

-- 2019-08-21T19:50:47.179Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557022,583153,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal',14,'U','',0,'Y','Y','Y','N','N','N','N','N','Verantwortlicher',260,290,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.181Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.183Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543385) 
;

-- 2019-08-21T19:50:47.184Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583153
;

-- 2019-08-21T19:50:47.185Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583153)
;

-- 2019-08-21T19:50:47.187Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583153
;

-- 2019-08-21T19:50:47.187Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583153, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560054
;

-- 2019-08-21T19:50:47.281Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557024,583154,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum Aussen',270,300,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.282Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.285Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543387) 
;

-- 2019-08-21T19:50:47.286Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583154
;

-- 2019-08-21T19:50:47.286Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583154)
;

-- 2019-08-21T19:50:47.288Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583154
;

-- 2019-08-21T19:50:47.289Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583154, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560055
;

-- 2019-08-21T19:50:47.376Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557023,583155,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum Innen',280,310,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.378Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.380Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543386) 
;

-- 2019-08-21T19:50:47.381Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583155
;

-- 2019-08-21T19:50:47.382Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583155)
;

-- 2019-08-21T19:50:47.384Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583155
;

-- 2019-08-21T19:50:47.385Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583155, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560056
;

-- 2019-08-21T19:50:47.456Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557180,583156,1001438,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort',60,'U','"Adresszeile 1" gibt die Anschrift für diesen Standort an.',0,'Y','Y','Y','N','N','N','N','N','Straße und Nr.',290,320,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.457Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.460Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001438) 
;

-- 2019-08-21T19:50:47.461Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583156
;

-- 2019-08-21T19:50:47.461Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583156)
;

-- 2019-08-21T19:50:47.463Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583156
;

-- 2019-08-21T19:50:47.464Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583156, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560290
;

-- 2019-08-21T19:50:47.541Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557179,583157,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl',60,'U','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.',0,'Y','Y','Y','N','N','N','N','N','PLZ',300,330,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.542Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.545Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(512) 
;

-- 2019-08-21T19:50:47.547Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583157
;

-- 2019-08-21T19:50:47.547Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583157)
;

-- 2019-08-21T19:50:47.549Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583157
;

-- 2019-08-21T19:50:47.550Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583157, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560291
;

-- 2019-08-21T19:50:47.621Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557178,583158,1000963,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes',60,'U','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.',0,'Y','Y','Y','N','N','N','N','N','Ort',310,340,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.622Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.624Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000963) 
;

-- 2019-08-21T19:50:47.625Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583158
;

-- 2019-08-21T19:50:47.626Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583158)
;

-- 2019-08-21T19:50:47.628Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583158
;

-- 2019-08-21T19:50:47.629Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583158, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560292
;

-- 2019-08-21T19:50:47.692Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557181,583159,0,541852,0,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',0,'U','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.',0,'Y','Y','Y','N','N','N','N','N','eMail',320,350,1,1,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.693Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:47.696Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2019-08-21T19:50:47.700Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583159
;

-- 2019-08-21T19:50:47.700Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583159)
;

-- 2019-08-21T19:50:47.702Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583159
;

-- 2019-08-21T19:50:47.702Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583159, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560293
;

-- 2019-08-21T19:50:47.762Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541852,541407,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:50:47.763Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541407 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:50:47.765Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541407
;

-- 2019-08-21T19:50:47.766Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541407, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540483
;

-- 2019-08-21T19:50:47.835Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541806,541407,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.905Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541806,542729,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:47.982Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583106,0,541852,542729,560533,'F',TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Anrede',10,30,30,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:48.059Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583126,0,541852,542729,560534,'F',TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Suchschlüssel',20,20,20,TO_TIMESTAMP('2019-08-21 21:50:47','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:48.135Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583132,0,541852,542729,560535,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Firma',30,50,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.213Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583127,0,541852,542729,560536,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Name',40,10,10,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:50:48.289Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583133,0,541852,542729,560537,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Firmenname',50,0,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:50:48.391Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583156,0,541852,542729,560538,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Strasse und Nr.',60,0,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.467Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583157,0,541852,542729,560539,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','PLZ',70,0,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.533Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583158,0,541852,542729,560540,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Ort',80,0,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.607Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583159,0,541852,542729,560541,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','eMail',90,0,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.676Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541807,541407,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.751Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541807,542730,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','group',10,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:48.827Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583135,0,541852,542730,560542,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Gruppe',10,60,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:50:48.899Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583136,0,541852,542730,560543,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sprache',20,90,0,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:48.962Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541807,542731,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.034Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583130,0,541852,542731,560544,'F',TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Aktiv',10,40,40,TO_TIMESTAMP('2019-08-21 21:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.094Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541807,542732,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','url',30,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.174Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583140,0,541852,542732,560545,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','URL',10,80,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.244Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541807,542733,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.318Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583125,0,541852,542733,560546,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',10,100,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:50:49.385Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583124,0,541852,542733,560547,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:50:49.499Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541852,541408,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2019-08-21T19:50:49.500Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541408 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:50:49.502Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541408
;

-- 2019-08-21T19:50:49.504Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541408, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540489
;

-- 2019-08-21T19:50:49.575Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541808,541408,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.633Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541808,542734,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.714Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583128,0,541852,542734,560548,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','Y','N','Y','N','N','N','Name Zusatz',50,0,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.790Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583129,0,541852,542734,560549,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Beschreibung',60,0,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.865Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583131,0,541852,542734,560550,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Anbauplanung',80,0,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:49.935Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583134,0,541852,542734,560551,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Umsatzsteuer-ID',110,0,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.013Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583137,0,541852,542734,560552,'F',TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Erhält EDI-Belege','Y','Y','N','Y','N','N','N','Erhält EDI-Belege',140,0,0,TO_TIMESTAMP('2019-08-21 21:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.081Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583138,0,541852,542734,560553,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'EDI-ID des Dateiempfängers','Y','Y','N','Y','N','N','N','EDI-ID des Dateiempfängers',150,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.153Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583139,0,541852,542734,560554,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.','Y','Y','N','Y','N','N','N','"CU pro TU" bei unbestimmter Verpackungskapazität',160,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.223Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583141,0,541852,542734,560555,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Rechnung per eMail',180,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.305Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583142,0,541852,542734,560556,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.','Y','Y','N','Y','N','N','N','Autom. Referenz',190,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.380Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583143,0,541852,542734,560557,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen','Beispiel:
<ul>
<li>Vorlage: 00600000000</li>
<li>Auftragsnumer: 12345</li>
<li>Erzeugte Referenz: 00600012345</li>
</ul>','Y','Y','N','Y','N','N','N','Referenz Vorgabe',200,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.452Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583144,0,541852,542734,560558,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Mitarbeiter','Indicates the number of employees for this Business Partner.  This field displays only for Prospects.','Y','Y','N','Y','N','N','N','Anzahl Beschäftigte',210,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.520Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583145,0,541852,542734,560559,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','Y','N','Y','N','N','N','Replikations-Standardwert',220,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.592Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583146,0,541852,542734,560560,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Logo',230,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.661Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583147,0,541852,542734,560561,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'','Y','Y','N','Y','N','N','N','MRP ausschliessen',240,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.730Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583148,0,541852,542734,560562,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Memo Gebinde','Y','Y','N','Y','N','N','N','Memo_HU',250,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.800Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583149,0,541852,542734,560563,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Memo Lieferung','Y','Y','N','Y','N','N','N','Memo_Delivery',260,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.879Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583150,0,541852,542734,560564,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Memo Abrechnung','Y','Y','N','Y','N','N','N','Memo_Invoicing',270,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:50.948Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583151,0,541852,542734,560565,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text','Y','Y','N','Y','N','N','N','Memo',280,0,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.023Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583152,0,541852,542734,560566,'F',TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','Y','N','Y','Y','N','N','Lieferart',290,70,0,TO_TIMESTAMP('2019-08-21 21:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.088Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583153,0,541852,542734,560567,'F',TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal','','Y','Y','N','Y','N','N','N','Sales Responsible',300,0,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.161Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583154,0,541852,542734,560568,'F',TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Wiedervorlage Datum Aussen',310,0,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.233Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583155,0,541852,542734,560569,'F',TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N','Wiedervorlage Datum Innen',320,0,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.308Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,547296,572448,0,541853,540320,540676,'Y',TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','C_Flatrate_Term','Y','N','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Abos','N',20,1,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.309Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541853 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T19:50:51.311Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572448) 
;

-- 2019-08-21T19:50:51.314Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541853)
;

-- 2019-08-21T19:50:51.318Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541853
;

-- 2019-08-21T19:50:51.318Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541853, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540876
;

-- 2019-08-21T19:50:51.393Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545640,583160,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Dient der Zusammenfassung ähnlicher Maßeinheiten',2,'U',0,'Y','Y','Y','N','N','N','N','N','Einheiten-Typ',10,1,1,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.394Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:51.396Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53927) 
;

-- 2019-08-21T19:50:51.399Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583160
;

-- 2019-08-21T19:50:51.399Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583160)
;

-- 2019-08-21T19:50:51.402Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583160
;

-- 2019-08-21T19:50:51.402Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583160, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560229
;

-- 2019-08-21T19:50:51.472Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545795,583161,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',20,1,1,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.473Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:51.476Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T19:50:51.660Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583161
;

-- 2019-08-21T19:50:51.660Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583161)
;

-- 2019-08-21T19:50:51.662Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583161
;

-- 2019-08-21T19:50:51.662Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583161, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560230
;

-- 2019-08-21T19:50:51.740Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545796,583162,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',30,1,1,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.741Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:51.743Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T19:50:51.886Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583162
;

-- 2019-08-21T19:50:51.886Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583162)
;

-- 2019-08-21T19:50:51.887Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583162
;

-- 2019-08-21T19:50:51.888Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583162, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560231
;

-- 2019-08-21T19:50:51.965Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545797,583163,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',40,1,1,TO_TIMESTAMP('2019-08-21 21:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:51.965Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:51.967Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-08-21T19:50:52Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583163
;

-- 2019-08-21T19:50:52.001Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583163)
;

-- 2019-08-21T19:50:52.002Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583163
;

-- 2019-08-21T19:50:52.003Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583163, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560232
;

-- 2019-08-21T19:50:52.064Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545798,583164,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',50,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:52.065Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:52.068Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-08-21T19:50:52.103Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583164
;

-- 2019-08-21T19:50:52.103Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583164)
;

-- 2019-08-21T19:50:52.105Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583164
;

-- 2019-08-21T19:50:52.105Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583164, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560233
;

-- 2019-08-21T19:50:52.176Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545799,583165,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',60,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:52.177Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:52.180Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T19:50:52.448Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583165
;

-- 2019-08-21T19:50:52.448Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583165)
;

-- 2019-08-21T19:50:52.449Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583165
;

-- 2019-08-21T19:50:52.449Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583165, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560234
;

-- 2019-08-21T19:50:52.642Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545800,583166,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert',70,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:52.643Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:52.646Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-08-21T19:50:52.690Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583166
;

-- 2019-08-21T19:50:52.690Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583166)
;

-- 2019-08-21T19:50:52.692Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583166
;

-- 2019-08-21T19:50:52.693Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583166, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560235
;

-- 2019-08-21T19:50:52.766Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545801,583167,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert durch',80,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:52.767Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:52.770Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-08-21T19:50:52.801Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583167
;

-- 2019-08-21T19:50:52.802Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583167)
;

-- 2019-08-21T19:50:52.804Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583167
;

-- 2019-08-21T19:50:52.804Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583167, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560236
;

-- 2019-08-21T19:50:52.876Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545802,583168,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',90,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:52.877Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:52.879Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447) 
;

-- 2019-08-21T19:50:52.882Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583168
;

-- 2019-08-21T19:50:52.883Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583168)
;

-- 2019-08-21T19:50:52.885Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583168
;

-- 2019-08-21T19:50:52.885Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583168, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560237
;

-- 2019-08-21T19:50:52.959Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545803,583169,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Datenerfassung',100,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:52.960Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:52.963Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541420) 
;

-- 2019-08-21T19:50:52.971Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583169
;

-- 2019-08-21T19:50:52.972Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583169)
;

-- 2019-08-21T19:50:52.974Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583169
;

-- 2019-08-21T19:50:52.974Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583169, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560238
;

-- 2019-08-21T19:50:53.045Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545806,583170,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Vertragsbedingungen',110,1,1,TO_TIMESTAMP('2019-08-21 21:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.046Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.048Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541423) 
;

-- 2019-08-21T19:50:53.051Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583170
;

-- 2019-08-21T19:50:53.051Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583170)
;

-- 2019-08-21T19:50:53.053Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583170
;

-- 2019-08-21T19:50:53.054Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583170, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560239
;

-- 2019-08-21T19:50:53.132Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545809,583171,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs',2,'U','You find the current status in the Document Status field. The options are listed in a popup',0,'Y','Y','Y','N','N','N','N','N','Belegverarbeitung',120,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.133Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.134Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(287) 
;

-- 2019-08-21T19:50:53.137Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583171
;

-- 2019-08-21T19:50:53.138Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583171)
;

-- 2019-08-21T19:50:53.139Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583171
;

-- 2019-08-21T19:50:53.139Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583171, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560240
;

-- 2019-08-21T19:50:53.211Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545810,583172,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'U','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','Y','Y','N','N','N','N','N','Belegstatus',130,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.211Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.213Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2019-08-21T19:50:53.217Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583172
;

-- 2019-08-21T19:50:53.217Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583172)
;

-- 2019-08-21T19:50:53.218Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583172
;

-- 2019-08-21T19:50:53.218Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583172, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560241
;

-- 2019-08-21T19:50:53.286Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545811,583173,1000063,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','Y','Y','N','N','N','N','N','Verarbeitet',140,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.287Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.288Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000063) 
;

-- 2019-08-21T19:50:53.289Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583173
;

-- 2019-08-21T19:50:53.289Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583173)
;

-- 2019-08-21T19:50:53.290Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583173
;

-- 2019-08-21T19:50:53.290Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583173, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560242
;

-- 2019-08-21T19:50:53.362Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545812,583174,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Process Now',150,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.363Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.364Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2019-08-21T19:50:53.381Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583174
;

-- 2019-08-21T19:50:53.381Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583174)
;

-- 2019-08-21T19:50:53.382Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583174
;

-- 2019-08-21T19:50:53.382Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583174, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560243
;

-- 2019-08-21T19:50:53.459Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545815,583175,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen',1,'U','Im Fall der Gegegenüberstellung von zu pauschal abgerechenten Beträgen und tatsächlich erbrachten Leistungen kann eine Verrechnung mit eventueller Nachzahlung oder Rückerstattung erfolgen.',0,'Y','Y','Y','N','N','N','N','N','Gegenüberstellung mit erbr. Leist.',160,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.460Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.462Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541449) 
;

-- 2019-08-21T19:50:53.464Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583175
;

-- 2019-08-21T19:50:53.465Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583175)
;

-- 2019-08-21T19:50:53.467Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583175
;

-- 2019-08-21T19:50:53.467Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583175, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560244
;

-- 2019-08-21T19:50:53.540Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545816,583176,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können',1,'U',0,'Y','Y','Y','N','N','N','N','N','Abschlusskorrektur vorsehen',170,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.541Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.543Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541450) 
;

-- 2019-08-21T19:50:53.544Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583176
;

-- 2019-08-21T19:50:53.544Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583176)
;

-- 2019-08-21T19:50:53.546Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583176
;

-- 2019-08-21T19:50:53.546Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583176, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560245
;

-- 2019-08-21T19:50:53.620Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545823,583177,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes',1,'U','Ob und welche Datensätze erstellt werden, hängt von den gewählten Vertragsbedingungen ab',0,'Y','Y','Y','N','N','N','N','N','Abschlusskorrektur vorbereiten',180,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.622Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.624Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541453) 
;

-- 2019-08-21T19:50:53.626Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583177
;

-- 2019-08-21T19:50:53.626Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583177)
;

-- 2019-08-21T19:50:53.628Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583177
;

-- 2019-08-21T19:50:53.629Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583177, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560246
;

-- 2019-08-21T19:50:53.701Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545858,583178,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Vertragsart',190,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.702Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.705Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541467) 
;

-- 2019-08-21T19:50:53.706Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583178
;

-- 2019-08-21T19:50:53.706Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583178)
;

-- 2019-08-21T19:50:53.709Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583178
;

-- 2019-08-21T19:50:53.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583178, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560247
;

-- 2019-08-21T19:50:53.787Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545989,583179,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).',14,'U',0,'Y','Y','Y','N','N','N','N','N','Menge',200,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.788Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.791Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541493) 
;

-- 2019-08-21T19:50:53.793Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583179
;

-- 2019-08-21T19:50:53.793Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583179)
;

-- 2019-08-21T19:50:53.795Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583179
;

-- 2019-08-21T19:50:53.796Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583179, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560248
;

-- 2019-08-21T19:50:53.857Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,545993,583180,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Planspiel',210,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.859Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.862Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541495) 
;

-- 2019-08-21T19:50:53.866Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583180
;

-- 2019-08-21T19:50:53.867Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583180)
;

-- 2019-08-21T19:50:53.869Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583180
;

-- 2019-08-21T19:50:53.870Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583180, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560249
;

-- 2019-08-21T19:50:53.943Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546003,583181,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'U','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','N','Maßeinheit',220,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:53.944Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:53.947Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2019-08-21T19:50:53.966Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583181
;

-- 2019-08-21T19:50:53.966Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583181)
;

-- 2019-08-21T19:50:53.968Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583181
;

-- 2019-08-21T19:50:53.969Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583181, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560250
;

-- 2019-08-21T19:50:54.035Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546013,583182,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',2000,'U','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',230,1,1,TO_TIMESTAMP('2019-08-21 21:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.036Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.038Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2019-08-21T19:50:54.043Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583182
;

-- 2019-08-21T19:50:54.044Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583182)
;

-- 2019-08-21T19:50:54.045Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583182
;

-- 2019-08-21T19:50:54.046Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583182, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560251
;

-- 2019-08-21T19:50:54.115Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546018,583183,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert',1,'U',0,'Y','Y','Y','N','N','N','N','N','Vertrag autom. verlängern',240,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.116Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.119Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540023) 
;

-- 2019-08-21T19:50:54.127Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583183
;

-- 2019-08-21T19:50:54.127Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583183)
;

-- 2019-08-21T19:50:54.129Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583183
;

-- 2019-08-21T19:50:54.130Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583183, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560252
;

-- 2019-08-21T19:50:54.202Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546020,583184,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Nachfolgende Vertragsperiode',250,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.203Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583184 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.206Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541513) 
;

-- 2019-08-21T19:50:54.208Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583184
;

-- 2019-08-21T19:50:54.208Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583184)
;

-- 2019-08-21T19:50:54.210Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583184
;

-- 2019-08-21T19:50:54.211Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583184, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560253
;

-- 2019-08-21T19:50:54.282Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546040,583185,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.',10,'U',0,'Y','Y','Y','N','N','N','N','N','Vertragsverlängerung/-übergang',260,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.283Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.286Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541514) 
;

-- 2019-08-21T19:50:54.288Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583185
;

-- 2019-08-21T19:50:54.288Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583185)
;

-- 2019-08-21T19:50:54.291Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583185
;

-- 2019-08-21T19:50:54.291Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583185, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560254
;

-- 2019-08-21T19:50:54.358Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546044,583186,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',7,'U','The Start Date indicates the first or starting date',0,'Y','Y','Y','N','N','N','N','N','Anfangsdatum',270,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.359Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583186 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.362Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574) 
;

-- 2019-08-21T19:50:54.368Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583186
;

-- 2019-08-21T19:50:54.369Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583186)
;

-- 2019-08-21T19:50:54.371Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583186
;

-- 2019-08-21T19:50:54.371Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583186, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560255
;

-- 2019-08-21T19:50:54.443Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546045,583187,1003113,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)',7,'U','The End Date indicates the last date in this range.',0,'Y','Y','Y','N','N','N','N','N','Enddatum',280,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.444Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583187 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.447Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003113) 
;

-- 2019-08-21T19:50:54.449Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583187
;

-- 2019-08-21T19:50:54.449Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583187)
;

-- 2019-08-21T19:50:54.452Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583187
;

-- 2019-08-21T19:50:54.452Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583187, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560256
;

-- 2019-08-21T19:50:54.523Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546046,583188,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.',7,'U',0,'Y','Y','Y','N','N','N','N','N','Kündigungs/Benachrichtigungsfrist',290,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.524Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583188 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.527Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541521) 
;

-- 2019-08-21T19:50:54.530Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583188
;

-- 2019-08-21T19:50:54.530Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583188)
;

-- 2019-08-21T19:50:54.533Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583188
;

-- 2019-08-21T19:50:54.533Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583188, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560257
;

-- 2019-08-21T19:50:54.606Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546048,583189,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Verarbeitung zum Laufzeitende',300,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.606Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.609Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541522) 
;

-- 2019-08-21T19:50:54.610Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583189
;

-- 2019-08-21T19:50:54.610Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583189)
;

-- 2019-08-21T19:50:54.612Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583189
;

-- 2019-08-21T19:50:54.612Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583189, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560258
;

-- 2019-08-21T19:50:54.681Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546050,583190,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Vertrag jetzt verlängern',310,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.681Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583190 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.683Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541523) 
;

-- 2019-08-21T19:50:54.684Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583190
;

-- 2019-08-21T19:50:54.684Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583190)
;

-- 2019-08-21T19:50:54.686Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583190
;

-- 2019-08-21T19:50:54.686Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583190, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560259
;

-- 2019-08-21T19:50:54.756Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546054,583191,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'U',0,'Y','Y','Y','N','N','N','N','N','Rechnungsstandort',320,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.757Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583191 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.758Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2040) 
;

-- 2019-08-21T19:50:54.760Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583191
;

-- 2019-08-21T19:50:54.760Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583191)
;

-- 2019-08-21T19:50:54.761Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583191
;

-- 2019-08-21T19:50:54.761Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583191, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560260
;

-- 2019-08-21T19:50:54.819Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546055,583192,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'U',0,'Y','Y','Y','N','N','N','N','N','Rechnungskontakt',330,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.819Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583192 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.821Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2041) 
;

-- 2019-08-21T19:50:54.822Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583192
;

-- 2019-08-21T19:50:54.822Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583192)
;

-- 2019-08-21T19:50:54.823Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583192
;

-- 2019-08-21T19:50:54.823Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583192, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560261
;

-- 2019-08-21T19:50:54.889Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546056,583193,1000107,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartners für die Rechnungsstellung',10,'U','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt',0,'Y','Y','Y','N','N','N','N','N','Rechnungspartner',340,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.890Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.891Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000107) 
;

-- 2019-08-21T19:50:54.892Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583193
;

-- 2019-08-21T19:50:54.892Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583193)
;

-- 2019-08-21T19:50:54.893Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583193
;

-- 2019-08-21T19:50:54.893Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583193, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560262
;

-- 2019-08-21T19:50:54.949Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546058,583194,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Person, die bei einem fachlichen Problem vom System informiert wird.',10,'U',0,'Y','Y','Y','N','N','N','N','N','Betreuer',350,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:54.949Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:54.950Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541510) 
;

-- 2019-08-21T19:50:54.951Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583194
;

-- 2019-08-21T19:50:54.952Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583194)
;

-- 2019-08-21T19:50:54.953Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583194
;

-- 2019-08-21T19:50:54.953Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583194, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560263
;

-- 2019-08-21T19:50:55.027Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546113,583195,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen',4,'U','Wenn eine Gegenüberstellung mit tatsächliche erbrachten Leistungen vorgesehen ist, dann definiert dieses Feld, ob eine ggf. eine Abweichung in Rechnung gestellt werden kann.',0,'Y','Y','Y','N','N','N','N','N','Verrechnungsart',360,1,1,TO_TIMESTAMP('2019-08-21 21:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.028Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.030Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541425) 
;

-- 2019-08-21T19:50:55.031Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583195
;

-- 2019-08-21T19:50:55.031Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583195)
;

-- 2019-08-21T19:50:55.033Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583195
;

-- 2019-08-21T19:50:55.033Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583195, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560264
;

-- 2019-08-21T19:50:55.105Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547282,583196,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Auftragszeile, mit der der Vertrag abgeschlossen wurde',10,'U',0,'Y','Y','Y','N','N','N','N','N','Vertrags-Auftragszeile',370,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.106Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.108Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541800) 
;

-- 2019-08-21T19:50:55.110Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583196
;

-- 2019-08-21T19:50:55.110Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583196)
;

-- 2019-08-21T19:50:55.112Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583196
;

-- 2019-08-21T19:50:55.113Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583196, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560265
;

-- 2019-08-21T19:50:55.182Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547283,583197,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',380,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.183Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.186Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-08-21T19:50:55.238Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583197
;

-- 2019-08-21T19:50:55.238Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583197)
;

-- 2019-08-21T19:50:55.241Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583197
;

-- 2019-08-21T19:50:55.241Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583197, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560266
;

-- 2019-08-21T19:50:55.303Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547296,583198,1000186,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to',10,'U','If empty the business partner will be shipped to.',0,'Y','Y','Y','N','N','N','N','N','Lieferempfänger',390,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.304Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.305Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000186) 
;

-- 2019-08-21T19:50:55.307Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583198
;

-- 2019-08-21T19:50:55.307Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583198)
;

-- 2019-08-21T19:50:55.309Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583198
;

-- 2019-08-21T19:50:55.309Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583198, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560267
;

-- 2019-08-21T19:50:55.371Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547297,583199,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to',10,'U',0,'Y','Y','Y','N','N','N','N','N','Lieferadresse',400,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.371Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.373Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53459) 
;

-- 2019-08-21T19:50:55.376Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583199
;

-- 2019-08-21T19:50:55.377Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583199)
;

-- 2019-08-21T19:50:55.379Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583199
;

-- 2019-08-21T19:50:55.379Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583199, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560268
;

-- 2019-08-21T19:50:55.443Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547298,583200,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Contact for drop shipment',10,'U',0,'Y','Y','Y','N','N','N','N','N','Lieferkontakt',410,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.444Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.445Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53460) 
;

-- 2019-08-21T19:50:55.446Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583200
;

-- 2019-08-21T19:50:55.446Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583200)
;

-- 2019-08-21T19:50:55.448Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583200
;

-- 2019-08-21T19:50:55.448Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583200, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560269
;

-- 2019-08-21T19:50:55.517Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547302,583201,1002923,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,2,'U',0,'Y','Y','Y','N','N','N','N','N','Vertrags-Status',420,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.518Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.519Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002923) 
;

-- 2019-08-21T19:50:55.524Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583201
;

-- 2019-08-21T19:50:55.524Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583201)
;

-- 2019-08-21T19:50:55.526Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583201
;

-- 2019-08-21T19:50:55.526Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583201, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560270
;

-- 2019-08-21T19:50:55.583Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547317,583202,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Ändern oder Kündigen',430,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.584Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.586Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541799) 
;

-- 2019-08-21T19:50:55.586Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583202
;

-- 2019-08-21T19:50:55.587Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583202)
;

-- 2019-08-21T19:50:55.588Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583202
;

-- 2019-08-21T19:50:55.589Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583202, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560272
;

-- 2019-08-21T19:50:55.667Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547318,583203,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde',10,'U',0,'Y','Y','Y','N','N','N','N','N','Änderungs-Auftragszeile',440,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.668Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.671Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541801) 
;

-- 2019-08-21T19:50:55.672Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583203
;

-- 2019-08-21T19:50:55.672Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583203)
;

-- 2019-08-21T19:50:55.675Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583203
;

-- 2019-08-21T19:50:55.675Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583203, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560273
;

-- 2019-08-21T19:50:55.746Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547319,583204,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde',10,'U',0,'Y','Y','Y','N','N','N','N','N','Änderungs-Auftrag',450,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.747Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.750Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541802) 
;

-- 2019-08-21T19:50:55.751Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583204
;

-- 2019-08-21T19:50:55.751Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583204)
;

-- 2019-08-21T19:50:55.754Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583204
;

-- 2019-08-21T19:50:55.754Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583204, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560274
;

-- 2019-08-21T19:50:55.817Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547320,583205,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag, mit der der Vertrag abgeschlossen wurde',10,'U',0,'Y','Y','Y','N','N','N','N','N','Vertrags-Auftrag',460,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.818Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.821Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541803) 
;

-- 2019-08-21T19:50:55.822Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583205
;

-- 2019-08-21T19:50:55.823Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583205)
;

-- 2019-08-21T19:50:55.825Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583205
;

-- 2019-08-21T19:50:55.826Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583205, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560275
;

-- 2019-08-21T19:50:55.896Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547322,583206,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen',1,'U','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','Y','Y','N','N','N','N','N','Lieferart',470,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:55.897Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:55.900Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(555) 
;

-- 2019-08-21T19:50:55.908Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583206
;

-- 2019-08-21T19:50:55.908Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583206)
;

-- 2019-08-21T19:50:55.910Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583206
;

-- 2019-08-21T19:50:55.911Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583206, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560276
;

-- 2019-08-21T19:50:56.007Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547323,583207,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird',2,'U','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','Y','Y','N','N','N','N','N','Lieferung',480,1,1,TO_TIMESTAMP('2019-08-21 21:50:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.008Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.010Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(274) 
;

-- 2019-08-21T19:50:56.018Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583207
;

-- 2019-08-21T19:50:56.019Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583207)
;

-- 2019-08-21T19:50:56.021Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583207
;

-- 2019-08-21T19:50:56.021Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583207, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560277
;

-- 2019-08-21T19:50:56.087Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547761,583208,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'U','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.',0,'Y','Y','Y','N','N','N','N','N','Preissystem',490,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.088Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.091Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135) 
;

-- 2019-08-21T19:50:56.097Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583208
;

-- 2019-08-21T19:50:56.098Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583208)
;

-- 2019-08-21T19:50:56.100Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583208
;

-- 2019-08-21T19:50:56.100Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583208, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560279
;

-- 2019-08-21T19:50:56.169Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547763,583209,1000115,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis',14,'U','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',0,'Y','Y','Y','N','N','N','N','N','Einzelpreis',500,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.170Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.173Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000115) 
;

-- 2019-08-21T19:50:56.174Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583209
;

-- 2019-08-21T19:50:56.175Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583209)
;

-- 2019-08-21T19:50:56.177Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583209
;

-- 2019-08-21T19:50:56.178Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583209, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560280
;

-- 2019-08-21T19:50:56.255Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548289,583210,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',25,'U','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','N','Währung',510,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.256Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.259Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2019-08-21T19:50:56.288Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583210
;

-- 2019-08-21T19:50:56.289Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583210)
;

-- 2019-08-21T19:50:56.292Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583210
;

-- 2019-08-21T19:50:56.293Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583210, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560281
;

-- 2019-08-21T19:50:56.368Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550678,583211,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Rechnungskandidat schließen',520,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.369Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.371Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542447) 
;

-- 2019-08-21T19:50:56.373Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583211
;

-- 2019-08-21T19:50:56.374Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583211)
;

-- 2019-08-21T19:50:56.376Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583211
;

-- 2019-08-21T19:50:56.376Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583211, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560282
;

-- 2019-08-21T19:50:56.442Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554317,583212,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Planmenge Folgejahr',530,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.443Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.446Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543058) 
;

-- 2019-08-21T19:50:56.448Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583212
;

-- 2019-08-21T19:50:56.448Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583212)
;

-- 2019-08-21T19:50:56.450Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583212
;

-- 2019-08-21T19:50:56.450Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583212, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560283
;

-- 2019-08-21T19:50:56.519Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554320,583213,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','Y','N','N','N','N','N','Lieferprodukt',540,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.520Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.523Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543037) 
;

-- 2019-08-21T19:50:56.525Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583213
;

-- 2019-08-21T19:50:56.526Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583213)
;

-- 2019-08-21T19:50:56.528Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583213
;

-- 2019-08-21T19:50:56.529Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583213, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560284
;

-- 2019-08-21T19:50:56.604Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554466,583214,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Request for Quotation Response Line',10,'U','Request for Quotation Response Line from a potential Vendor',0,'Y','Y','Y','N','N','N','N','N','RfQ Response Line',550,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.605Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.607Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2381) 
;

-- 2019-08-21T19:50:56.609Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583214
;

-- 2019-08-21T19:50:56.609Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583214)
;

-- 2019-08-21T19:50:56.612Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583214
;

-- 2019-08-21T19:50:56.612Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583214, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560285
;

-- 2019-08-21T19:50:56.685Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557139,583215,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,7,'U',0,'Y','Y','Y','N','N','N','N','N','Beginn Hauptvertrag',560,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.686Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.689Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543413) 
;

-- 2019-08-21T19:50:56.691Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583215
;

-- 2019-08-21T19:50:56.691Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583215)
;

-- 2019-08-21T19:50:56.694Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583215
;

-- 2019-08-21T19:50:56.694Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583215, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560286
;

-- 2019-08-21T19:50:56.774Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557140,583216,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,7,'U',0,'Y','Y','Y','N','N','N','N','N','Ende Hauptvertrag',570,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.775Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.777Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543414) 
;

-- 2019-08-21T19:50:56.778Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583216
;

-- 2019-08-21T19:50:56.779Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583216)
;

-- 2019-08-21T19:50:56.781Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583216
;

-- 2019-08-21T19:50:56.781Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583216, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560287
;

-- 2019-08-21T19:50:56.846Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557169,583217,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,7,'U',0,'Y','Y','Y','N','N','N','N','N','Vertrag Datum',580,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.848Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.850Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543419) 
;

-- 2019-08-21T19:50:56.851Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583217
;

-- 2019-08-21T19:50:56.852Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583217)
;

-- 2019-08-21T19:50:56.854Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583217
;

-- 2019-08-21T19:50:56.854Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583217, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560288
;

-- 2019-08-21T19:50:56.929Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557170,583218,1000249,0,541853,0,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',30,'U','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Nr.',590,1,1,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:56.930Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:56.932Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000249) 
;

-- 2019-08-21T19:50:56.938Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583218
;

-- 2019-08-21T19:50:56.939Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583218)
;

-- 2019-08-21T19:50:56.940Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583218
;

-- 2019-08-21T19:50:56.941Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583218, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560289
;

-- 2019-08-21T19:50:57.003Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541853,541409,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:50:56','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:50:57.004Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541409 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:50:57.005Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541409
;

-- 2019-08-21T19:50:57.006Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541409, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540484
;

-- 2019-08-21T19:50:57.066Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541809,541409,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:57.128Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541809,542735,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:57.197Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583179,0,541853,542735,560570,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).','Y','N','N','Y','Y','N','N','Menge',10,10,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:57.277Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583197,0,541853,542735,560571,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N','Produkt',20,20,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:50:57.349Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583209,0,541853,542735,560572,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','Y','Y','N','N','Einzelpreis',30,30,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:57.431Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583170,0,541853,542735,560573,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Vertragsbedingungen',40,40,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:50:57.498Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583186,0,541853,542735,560574,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)','The Start Date indicates the first or starting date','Y','N','N','Y','Y','N','N','Anfangsdatum',50,50,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:57.574Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583187,0,541853,542735,560575,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Enddatum',60,60,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:57.650Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583201,0,541853,542735,560576,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Vertrags-Status',70,70,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:50:57.736Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583215,0,541853,542735,560577,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beginn Hauptvertrag',80,80,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:57.801Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583216,0,541853,542735,560578,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Ende Hauptvertrag',90,90,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:57.869Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583193,0,541853,542735,560579,'F',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Rechnungspartner',100,0,0,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:57.952Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,583191,0,540404,560579,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.010Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,583192,0,540405,560579,TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,'widget',TO_TIMESTAMP('2019-08-21 21:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.095Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583198,0,541853,542735,560580,'F',TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Lieferempfänger',110,0,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.174Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,583199,0,540406,560580,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.232Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,583200,0,540407,560580,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,'widget',TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.306Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583162,0,541853,542735,560581,'F',TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',120,100,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:50:58.375Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583161,0,541853,542735,560582,'F',TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',130,0,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:50:58.438Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,2958,573764,0,541854,293,540676,'Y',TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','C_BPartner_Location','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Adresse','N',30,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.440Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541854 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T19:50:58.442Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(573764) 
;

-- 2019-08-21T19:50:58.444Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541854)
;

-- 2019-08-21T19:50:58.446Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541854
;

-- 2019-08-21T19:50:58.446Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541854, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540874
;

-- 2019-08-21T19:50:58.524Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541704,583219,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Abo Standard Adresse',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.525Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583219 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:58.528Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540411) 
;

-- 2019-08-21T19:50:58.529Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583219
;

-- 2019-08-21T19:50:58.529Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583219)
;

-- 2019-08-21T19:50:58.531Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583219
;

-- 2019-08-21T19:50:58.531Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583219, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560057
;

-- 2019-08-21T19:50:58.602Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2956,583220,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',20,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.603Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:58.605Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-08-21T19:50:58.646Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583220
;

-- 2019-08-21T19:50:58.647Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583220)
;

-- 2019-08-21T19:50:58.649Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583220
;

-- 2019-08-21T19:50:58.650Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583220, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560058
;

-- 2019-08-21T19:50:58.726Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2957,583221,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',30,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.726Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:58.729Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-08-21T19:50:58.763Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583221
;

-- 2019-08-21T19:50:58.764Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583221)
;

-- 2019-08-21T19:50:58.766Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583221
;

-- 2019-08-21T19:50:58.767Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583221, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560059
;

-- 2019-08-21T19:50:58.840Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2964,583222,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',20,'U','',0,'Y','N','N','N','N','N','N','N','Telefon',40,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.841Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:58.843Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505) 
;

-- 2019-08-21T19:50:58.847Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583222
;

-- 2019-08-21T19:50:58.847Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583222)
;

-- 2019-08-21T19:50:58.848Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583222
;

-- 2019-08-21T19:50:58.849Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583222, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560060
;

-- 2019-08-21T19:50:58.927Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2965,583223,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Telefonnummer',20,'U','',0,'Y','N','N','N','N','N','N','Y','Telefon (alternativ)',50,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:58.928Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:58.930Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(506) 
;

-- 2019-08-21T19:50:58.935Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583223
;

-- 2019-08-21T19:50:58.935Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583223)
;

-- 2019-08-21T19:50:58.937Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583223
;

-- 2019-08-21T19:50:58.938Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583223, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560061
;

-- 2019-08-21T19:50:59.007Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2966,583224,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer',20,'U','The Fax identifies a facsimile number for this Business Partner or  Location',0,'Y','N','N','N','N','N','N','N','Fax',60,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.008Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.010Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(301) 
;

-- 2019-08-21T19:50:59.016Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583224
;

-- 2019-08-21T19:50:59.018Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583224)
;

-- 2019-08-21T19:50:59.020Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583224
;

-- 2019-08-21T19:50:59.021Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583224, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560062
;

-- 2019-08-21T19:50:59.096Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2967,583225,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'ISDN- oder Modem-Anschluss',20,'U','"ISDN" gibt einen ISDN- oder Modem-Anschluss an.',0,'Y','N','N','N','N','N','N','Y','ISDN',70,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.098Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583225 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.100Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(327) 
;

-- 2019-08-21T19:50:59.102Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583225
;

-- 2019-08-21T19:50:59.102Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583225)
;

-- 2019-08-21T19:50:59.104Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583225
;

-- 2019-08-21T19:50:59.105Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583225, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560063
;

-- 2019-08-21T19:50:59.174Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2968,583226,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Vertriebsgebiet',14,'U','"Vertriebsgebiet" gibt einen bestimmten Verkaufsbereich an.',0,'Y','N','N','N','N','N','N','N','Vertriebsgebiet',80,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.175Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583226 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.178Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(210) 
;

-- 2019-08-21T19:50:59.183Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583226
;

-- 2019-08-21T19:50:59.184Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583226)
;

-- 2019-08-21T19:50:59.187Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583226
;

-- 2019-08-21T19:50:59.187Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583226, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560064
;

-- 2019-08-21T19:50:59.255Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3434,583227,189,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'U','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','N','N','N','N','N','N','Standort',90,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.258Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583227 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.260Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2019-08-21T19:50:59.261Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583227
;

-- 2019-08-21T19:50:59.262Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583227)
;

-- 2019-08-21T19:50:59.264Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583227
;

-- 2019-08-21T19:50:59.264Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583227, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560065
;

-- 2019-08-21T19:50:59.330Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541691,583228,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Provisionsabrechnungen werden hierhin geschickt',1,'U',0,'Y','N','N','N','N','N','N','N','isCommissionTo',100,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.332Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583228 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.334Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540404) 
;

-- 2019-08-21T19:50:59.336Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583228
;

-- 2019-08-21T19:50:59.337Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583228)
;

-- 2019-08-21T19:50:59.339Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583228
;

-- 2019-08-21T19:50:59.339Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583228, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560066
;

-- 2019-08-21T19:50:59.412Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541692,583229,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'An diese Adresse werden Abos geschickt',1,'U',0,'Y','N','N','N','N','N','N','N','isSubscriptionTo',110,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.414Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583229 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.416Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540405) 
;

-- 2019-08-21T19:50:59.418Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583229
;

-- 2019-08-21T19:50:59.419Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583229)
;

-- 2019-08-21T19:50:59.421Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583229
;

-- 2019-08-21T19:50:59.422Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583229, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560067
;

-- 2019-08-21T19:50:59.492Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541703,583230,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Provision Standard Adresse',120,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.493Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.496Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540410) 
;

-- 2019-08-21T19:50:59.497Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583230
;

-- 2019-08-21T19:50:59.498Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583230)
;

-- 2019-08-21T19:50:59.500Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583230
;

-- 2019-08-21T19:50:59.501Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583230, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560068
;

-- 2019-08-21T19:50:59.569Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2954,583231,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',130,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.571Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.574Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-08-21T19:50:59.625Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583231
;

-- 2019-08-21T19:50:59.625Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583231)
;

-- 2019-08-21T19:50:59.627Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583231
;

-- 2019-08-21T19:50:59.628Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583231, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560069
;

-- 2019-08-21T19:50:59.698Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2955,583232,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',140,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.700Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.703Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-08-21T19:50:59.747Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583232
;

-- 2019-08-21T19:50:59.748Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583232)
;

-- 2019-08-21T19:50:59.749Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583232
;

-- 2019-08-21T19:50:59.750Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583232, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560070
;

-- 2019-08-21T19:50:59.823Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2951,583233,0,541854,0,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:50:59.824Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:50:59.826Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T19:50:59.974Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583233
;

-- 2019-08-21T19:50:59.974Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583233)
;

-- 2019-08-21T19:50:59.975Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583233
;

-- 2019-08-21T19:50:59.976Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583233, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560071
;

-- 2019-08-21T19:51:00.058Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2952,583234,0,541854,71,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','Y','Y','Sektion',20,10,1,1,TO_TIMESTAMP('2019-08-21 21:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:00.059Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:00.061Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T19:51:00.184Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583234
;

-- 2019-08-21T19:51:00.185Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583234)
;

-- 2019-08-21T19:51:00.186Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583234
;

-- 2019-08-21T19:51:00.186Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583234, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560072
;

-- 2019-08-21T19:51:00.368Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2958,583235,1001395,0,541854,267,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',14,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','Y','N','Geschäftspartner',30,20,1,1,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:00.369Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:00.371Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001395) 
;

-- 2019-08-21T19:51:00.372Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583235
;

-- 2019-08-21T19:51:00.373Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583235)
;

-- 2019-08-21T19:51:00.375Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583235
;

-- 2019-08-21T19:51:00.375Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583235, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560073
;

-- 2019-08-21T19:51:00.449Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2960,583236,0,541854,327,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100,'',11,'U','',0,'Y','Y','Y','N','N','N','N','N','Name',40,30,1,1,1,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:00.450Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:00.453Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-08-21T19:51:00.515Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583236
;

-- 2019-08-21T19:51:00.516Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583236)
;

-- 2019-08-21T19:51:00.518Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583236
;

-- 2019-08-21T19:51:00.518Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583236, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560074
;

-- 2019-08-21T19:51:00.592Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,548323,583237,0,541854,133,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','Y','GLN',44,40,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:00.594Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:00.597Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541976) 
;

-- 2019-08-21T19:51:00.599Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583237
;

-- 2019-08-21T19:51:00.599Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583237)
;

-- 2019-08-21T19:51:00.601Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583237
;

-- 2019-08-21T19:51:00.602Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583237, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560075
;

-- 2019-08-21T19:51:00.675Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2953,583238,0,541854,58,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:00.676Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:00.678Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T19:51:00.958Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583238
;

-- 2019-08-21T19:51:00.958Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583238)
;

-- 2019-08-21T19:51:00.959Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583238
;

-- 2019-08-21T19:51:00.959Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583238, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560076
;

-- 2019-08-21T19:51:01.030Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2959,583239,202,0,541854,226,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift',26,'U','Das Feld "Adresse" definiert die Adressangaben eines Standortes.',0,'Y','Y','Y','N','N','N','N','N','Anschrift',60,60,1,1,TO_TIMESTAMP('2019-08-21 21:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.031Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583239 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.032Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(202) 
;

-- 2019-08-21T19:51:01.036Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583239
;

-- 2019-08-21T19:51:01.036Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583239)
;

-- 2019-08-21T19:51:01.038Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583239
;

-- 2019-08-21T19:51:01.038Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583239, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560077
;

-- 2019-08-21T19:51:01.135Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,505104,583240,0,541854,301,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Anschrift',0,'U',0,'Y','Y','Y','N','N','N','Y','Y','Adresse',70,70,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.136Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.139Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505104) 
;

-- 2019-08-21T19:51:01.141Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583240
;

-- 2019-08-21T19:51:01.141Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583240)
;

-- 2019-08-21T19:51:01.143Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583240
;

-- 2019-08-21T19:51:01.144Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583240, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560078
;

-- 2019-08-21T19:51:01.220Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3091,583241,0,541854,85,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner',1,'U','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.',0,'Y','Y','Y','N','N','N','N','N','Lieferstandard',110,80,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.221Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.223Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(929) 
;

-- 2019-08-21T19:51:01.232Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583241
;

-- 2019-08-21T19:51:01.233Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583241)
;

-- 2019-08-21T19:51:01.235Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583241
;

-- 2019-08-21T19:51:01.235Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583241, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560079
;

-- 2019-08-21T19:51:01.301Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541705,583242,0,541854,92,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','Y','Liefer Standard Adresse',120,90,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.302Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.305Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540412) 
;

-- 2019-08-21T19:51:01.307Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583242
;

-- 2019-08-21T19:51:01.307Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583242)
;

-- 2019-08-21T19:51:01.310Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583242
;

-- 2019-08-21T19:51:01.310Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583242, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560080
;

-- 2019-08-21T19:51:01.387Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3092,583243,0,541854,0,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner zahlt von dieser Adresse und wir senden Mahnungen an diese Adresse',1,'U','Wenn "Zahlungs-Adresse" selektiert ist, zahlt der Geschäftspartner von dieser Adresse und wir senden Mahnungen an diese Adresse.',0,'Y','N','N','N','N','N','N','N','Zahlungs-Adresse',120,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.388Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.390Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(925) 
;

-- 2019-08-21T19:51:01.394Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583243
;

-- 2019-08-21T19:51:01.395Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583243)
;

-- 2019-08-21T19:51:01.398Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583243
;

-- 2019-08-21T19:51:01.398Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583243, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560081
;

-- 2019-08-21T19:51:01.478Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3090,583244,0,541854,115,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner',1,'U','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.',0,'Y','Y','Y','N','N','N','N','N','Vorbelegung Rechnung',130,100,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.479Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.482Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(916) 
;

-- 2019-08-21T19:51:01.485Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583244
;

-- 2019-08-21T19:51:01.486Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583244)
;

-- 2019-08-21T19:51:01.488Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583244
;

-- 2019-08-21T19:51:01.489Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583244, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560082
;

-- 2019-08-21T19:51:01.565Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541706,583245,0,541854,122,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','Y','Rechnung Standard Adresse',140,110,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.566Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.569Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540413) 
;

-- 2019-08-21T19:51:01.570Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583245
;

-- 2019-08-21T19:51:01.571Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583245)
;

-- 2019-08-21T19:51:01.574Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583245
;

-- 2019-08-21T19:51:01.574Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583245, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560083
;

-- 2019-08-21T19:51:01.645Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549932,583246,0,541854,69,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Abladeort',146,120,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.646Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.650Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542278) 
;

-- 2019-08-21T19:51:01.652Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583246
;

-- 2019-08-21T19:51:01.652Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583246)
;

-- 2019-08-21T19:51:01.655Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583246
;

-- 2019-08-21T19:51:01.655Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583246, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560084
;

-- 2019-08-21T19:51:01.717Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3093,583247,0,541854,135,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Erstattungs-Adresse für den Lieferanten',1,'U','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.',0,'Y','Y','Y','N','N','N','N','Y','EDI-Supplier-Addresse',150,130,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.718Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.721Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(927) 
;

-- 2019-08-21T19:51:01.724Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583247
;

-- 2019-08-21T19:51:01.724Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583247)
;

-- 2019-08-21T19:51:01.726Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583247
;

-- 2019-08-21T19:51:01.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583247, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560085
;

-- 2019-08-21T19:51:01.796Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550430,583248,0,541854,0,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,0,'U','Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.',0,'Y','Y','Y','N','N','N','N','N','Replikations Standardwert',160,140,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:01.797Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:01.800Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542394) 
;

-- 2019-08-21T19:51:01.802Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583248
;

-- 2019-08-21T19:51:01.803Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583248)
;

-- 2019-08-21T19:51:01.805Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583248
;

-- 2019-08-21T19:51:01.805Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583248, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560086
;

-- 2019-08-21T19:51:01.873Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541854,541410,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:51:01.874Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541410 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:51:01.876Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541410
;

-- 2019-08-21T19:51:01.878Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541410, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540485
;

-- 2019-08-21T19:51:01.945Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541810,541410,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.007Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541810,542736,TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.080Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583235,0,541854,542736,560583,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','Geschäftspartner',10,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.155Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583236,0,541854,542736,560584,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','Y','Y','N','Name',10,10,10,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:51:02.227Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583239,0,541854,542736,560585,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','N','Y','Y','Y','N','Anschrift',20,20,20,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:02.294Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583240,0,541854,542736,560586,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Anschrift','Y','N','N','Y','N','N','N','Adresse',30,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.362Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583237,0,541854,542736,560587,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','GLN',40,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.466Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583238,0,541854,542736,560588,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','Y','N','Aktiv',50,30,30,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.530Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583242,0,541854,542736,560589,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Liefer Standard Adresse',60,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.603Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583241,0,541854,542736,560590,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','N','Y','N','N','N','Lieferstandard',70,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.684Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583245,0,541854,542736,560591,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Rechnung Standard Adresse',80,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.764Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583244,0,541854,542736,560592,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','Y','N','N','Y','N','N','N','Vorbelegung Rechnung',90,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.839Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583246,0,541854,542736,560593,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Abladeort',100,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.914Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583247,0,541854,542736,560594,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Erstattungs-Adresse für den Lieferanten','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.','Y','N','N','Y','N','N','N','EDI-Supplier-Addresse',110,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:02.981Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583248,0,541854,542736,560595,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','N','Y','N','N','N','Replikations-Standardwert',120,0,0,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.048Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583234,0,541854,542736,560596,'F',TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','Y','N','Sektion',130,40,40,TO_TIMESTAMP('2019-08-21 21:51:02','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:03.139Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583233,0,541854,542736,560597,'F',TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',140,0,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:03.207Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,5844,573896,0,541855,114,540676,'Y',TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','AD_User','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Nutzer/ Kontakt','N',40,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.208Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541855 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T19:51:03.210Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(573896) 
;

-- 2019-08-21T19:51:03.212Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541855)
;

-- 2019-08-21T19:51:03.220Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541855
;

-- 2019-08-21T19:51:03.220Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541855, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540873
;

-- 2019-08-21T19:51:03.294Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13600,583249,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Datum der EMail-Überprüfung',20,'U',0,'Y','N','N','N','N','N','Y','N','EMail überprüft',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.295Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.298Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2726) 
;

-- 2019-08-21T19:51:03.301Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583249
;

-- 2019-08-21T19:51:03.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583249)
;

-- 2019-08-21T19:51:03.305Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583249
;

-- 2019-08-21T19:51:03.306Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583249, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560087
;

-- 2019-08-21T19:51:03.389Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6314,583250,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,23,'U',0,'Y','N','N','N','N','N','N','N','Process Now',20,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.390Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.392Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2019-08-21T19:51:03.443Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583250
;

-- 2019-08-21T19:51:03.443Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583250)
;

-- 2019-08-21T19:51:03.445Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583250
;

-- 2019-08-21T19:51:03.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583250, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560088
;

-- 2019-08-21T19:51:03.518Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7793,583251,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer-Name/Konto (ID) im EMail-System',20,'U','The user name in the mail system is usually the string before the @ of your email address. Notwendig, wenn der EMail-SErver eine Anmeldung vor dem Versenden von EMails verlangt.',0,'Y','N','N','N','N','N','N','N','EMail Nutzer-ID',30,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.519Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.522Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1896) 
;

-- 2019-08-21T19:51:03.526Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583251
;

-- 2019-08-21T19:51:03.527Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583251)
;

-- 2019-08-21T19:51:03.529Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583251
;

-- 2019-08-21T19:51:03.529Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583251, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560089
;

-- 2019-08-21T19:51:03.606Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,625,583252,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','Aktualisiert',40,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.608Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.610Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-08-21T19:51:03.666Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583252
;

-- 2019-08-21T19:51:03.667Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583252)
;

-- 2019-08-21T19:51:03.668Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583252
;

-- 2019-08-21T19:51:03.669Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583252, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560090
;

-- 2019-08-21T19:51:03.744Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,626,583253,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','Aktualisiert durch',50,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.745Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.747Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-08-21T19:51:03.796Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583253
;

-- 2019-08-21T19:51:03.796Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583253)
;

-- 2019-08-21T19:51:03.798Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583253
;

-- 2019-08-21T19:51:03.798Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583253, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560091
;

-- 2019-08-21T19:51:03.861Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543414,583254,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Button that will call a process to unlock current selected account',15,'U',0,'Y','N','N','N','N','N','N','N','Zugang entsperren',60,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.862Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.864Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540824) 
;

-- 2019-08-21T19:51:03.865Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583254
;

-- 2019-08-21T19:51:03.866Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583254)
;

-- 2019-08-21T19:51:03.868Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583254
;

-- 2019-08-21T19:51:03.868Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583254, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560092
;

-- 2019-08-21T19:51:03.943Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,623,583255,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','Erstellt',70,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:03.944Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:03.947Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-08-21T19:51:04.021Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583255
;

-- 2019-08-21T19:51:04.021Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583255)
;

-- 2019-08-21T19:51:04.023Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583255
;

-- 2019-08-21T19:51:04.023Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583255, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560093
;

-- 2019-08-21T19:51:04.174Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,624,583256,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',22,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','Erstellt durch',80,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.175Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.177Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-08-21T19:51:04.233Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583256
;

-- 2019-08-21T19:51:04.233Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583256)
;

-- 2019-08-21T19:51:04.234Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583256
;

-- 2019-08-21T19:51:04.234Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583256, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560094
;

-- 2019-08-21T19:51:04.294Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542511,583257,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,100,'U',0,'Y','N','N','N','N','N','N','N','Handynummer',90,0,999,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.294Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.296Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540617) 
;

-- 2019-08-21T19:51:04.298Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583257
;

-- 2019-08-21T19:51:04.299Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583257)
;

-- 2019-08-21T19:51:04.300Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583257
;

-- 2019-08-21T19:51:04.301Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583257, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560095
;

-- 2019-08-21T19:51:04.374Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7794,583258,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Passwort Ihrer EMail Nutzer-ID',20,'U','Notwendig, wenn der EMail-Server eine Anmeldung vor dem Versenden von EMails verlangt.',0,'Y','N','N','N','N','N','N','N','Passwort EMail-Nutzer',100,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.374Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.376Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1897) 
;

-- 2019-08-21T19:51:04.379Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583258
;

-- 2019-08-21T19:51:04.379Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583258)
;

-- 2019-08-21T19:51:04.381Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583258
;

-- 2019-08-21T19:51:04.381Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583258, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560097
;

-- 2019-08-21T19:51:04.450Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14336,583259,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'The user/contact has full access to Business Partner information and resources',1,'U','If selected, the user has full access to the Business Partner (BP) information (Business Documents like Orders, Invoices - Requests) or resources (Assets, Downloads). If you deselet it, the user has no access rights unless, you explicitly grant it in tab "BP Access"',0,'Y','N','N','N','N','N','N','Y','Vollzugriff Geschäftspartner',110,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.450Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.452Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2835) 
;

-- 2019-08-21T19:51:04.454Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583259
;

-- 2019-08-21T19:51:04.454Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583259)
;

-- 2019-08-21T19:51:04.456Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583259
;

-- 2019-08-21T19:51:04.456Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583259, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560098
;

-- 2019-08-21T19:51:04.532Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,212,583260,1000643,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','N','N','N','N','N','N','N','Ansprechpartner',120,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.533Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.534Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000643) 
;

-- 2019-08-21T19:51:04.536Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583260
;

-- 2019-08-21T19:51:04.536Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583260)
;

-- 2019-08-21T19:51:04.537Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583260
;

-- 2019-08-21T19:51:04.537Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583260, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560099
;

-- 2019-08-21T19:51:04.629Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13773,583261,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Art der Benachrichtigung',14,'U','EMail oder Nachricht bei aktualisierter Anfrage usw.',0,'Y','N','N','N','N','N','N','Y','Benachrichtigungs-Art',130,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.630Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.632Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2755) 
;

-- 2019-08-21T19:51:04.636Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583261
;

-- 2019-08-21T19:51:04.636Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583261)
;

-- 2019-08-21T19:51:04.638Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583261
;

-- 2019-08-21T19:51:04.638Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583261, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560100
;

-- 2019-08-21T19:51:04.700Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542513,583262,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,255,'U',0,'Y','N','N','N','N','N','N','N','Begründung',140,0,999,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.701Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.704Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540620) 
;

-- 2019-08-21T19:51:04.706Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583262
;

-- 2019-08-21T19:51:04.706Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583262)
;

-- 2019-08-21T19:51:04.709Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583262
;

-- 2019-08-21T19:51:04.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583262, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560101
;

-- 2019-08-21T19:51:04.777Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543407,583263,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Handelsregister',255,'U',0,'Y','N','N','N','N','N','N','N','Handelsregister',150,0,999,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.777Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.780Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540813) 
;

-- 2019-08-21T19:51:04.781Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583263
;

-- 2019-08-21T19:51:04.782Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583263)
;

-- 2019-08-21T19:51:04.783Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583263
;

-- 2019-08-21T19:51:04.784Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583263, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560102
;

-- 2019-08-21T19:51:04.858Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15975,583264,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'U','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','N','N','N','N','N','N','Suchschlüssel',160,0,999,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.858Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.860Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2019-08-21T19:51:04.878Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583264
;

-- 2019-08-21T19:51:04.878Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583264)
;

-- 2019-08-21T19:51:04.880Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583264
;

-- 2019-08-21T19:51:04.880Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583264, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560103
;

-- 2019-08-21T19:51:04.951Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543412,583265,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Client IP address that was used when this account was locked ',20,'U',0,'Y','N','N','N','N','N','N','N','Sperrung von IP',170,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:04.951Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:04.952Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540819) 
;

-- 2019-08-21T19:51:04.953Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583265
;

-- 2019-08-21T19:51:04.953Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583265)
;

-- 2019-08-21T19:51:04.954Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583265
;

-- 2019-08-21T19:51:04.954Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583265, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560104
;

-- 2019-08-21T19:51:05.027Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543413,583266,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Datum Login Fehler',20,'U',0,'Y','N','N','N','N','N','N','N','Datum Login Fehler',180,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.027Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.029Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540820) 
;

-- 2019-08-21T19:51:05.030Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583266
;

-- 2019-08-21T19:51:05.030Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583266)
;

-- 2019-08-21T19:51:05.031Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583266
;

-- 2019-08-21T19:51:05.031Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583266, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560105
;

-- 2019-08-21T19:51:05.095Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543410,583267,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl Login Fehlversuche',2,'U',0,'Y','N','N','N','N','N','N','N','Anzahl Login Fehlversuche',190,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.096Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.097Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540816) 
;

-- 2019-08-21T19:51:05.098Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583267
;

-- 2019-08-21T19:51:05.098Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583267)
;

-- 2019-08-21T19:51:05.099Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583267
;

-- 2019-08-21T19:51:05.099Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583267, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560106
;

-- 2019-08-21T19:51:05.156Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,52066,583268,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,20,'U',0,'Y','N','N','N','N','N','N','N','Benutzer PIN',200,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.156Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.158Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52023) 
;

-- 2019-08-21T19:51:05.159Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583268
;

-- 2019-08-21T19:51:05.159Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583268)
;

-- 2019-08-21T19:51:05.160Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583268
;

-- 2019-08-21T19:51:05.161Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583268, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560107
;

-- 2019-08-21T19:51:05.221Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541639,583269,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Included Tab in this Tab (Master Dateail)',1,'U','You can include a Tab in a Tab. If displayed in single row record, the included tab is displayed as multi-row table.',0,'Y','N','N','N','N','N','N','N','Included Tab',210,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.222Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.223Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2026) 
;

-- 2019-08-21T19:51:05.224Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583269
;

-- 2019-08-21T19:51:05.225Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583269)
;

-- 2019-08-21T19:51:05.226Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583269
;

-- 2019-08-21T19:51:05.226Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583269, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560108
;

-- 2019-08-21T19:51:05.288Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56294,583270,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Defined if any User Contact will be used for Calculate Payroll',1,'U',0,'Y','N','N','N','N','N','N','N','Is In Payroll',220,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.289Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.290Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53676) 
;

-- 2019-08-21T19:51:05.292Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583270
;

-- 2019-08-21T19:51:05.292Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583270)
;

-- 2019-08-21T19:51:05.293Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583270
;

-- 2019-08-21T19:51:05.293Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583270, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560109
;

-- 2019-08-21T19:51:05.365Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543411,583271,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Kennzeichen das anzeigt ob der Zugang gesperrt wurde',1,'U',0,'Y','N','N','N','N','N','N','N','Zugang gesperrt',230,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.366Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.368Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540817) 
;

-- 2019-08-21T19:51:05.369Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583271
;

-- 2019-08-21T19:51:05.369Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583271)
;

-- 2019-08-21T19:51:05.371Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583271
;

-- 2019-08-21T19:51:05.371Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583271, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560110
;

-- 2019-08-21T19:51:05.430Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541897,583272,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Fachlicher Kontakt',240,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.431Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.434Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540455) 
;

-- 2019-08-21T19:51:05.436Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583272
;

-- 2019-08-21T19:51:05.437Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583272)
;

-- 2019-08-21T19:51:05.439Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583272
;

-- 2019-08-21T19:51:05.439Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583272, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560111
;

-- 2019-08-21T19:51:05.512Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542512,583273,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','N','N','N','N','N','N','N','Kontakt Einschränkung',250,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.513Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.516Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540619) 
;

-- 2019-08-21T19:51:05.518Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583273
;

-- 2019-08-21T19:51:05.518Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583273)
;

-- 2019-08-21T19:51:05.521Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583273
;

-- 2019-08-21T19:51:05.521Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583273, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560112
;

-- 2019-08-21T19:51:05.607Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543402,583274,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,7,'U',0,'Y','N','N','N','N','N','N','N','Löschdatum',260,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.608Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.610Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540810) 
;

-- 2019-08-21T19:51:05.612Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583274
;

-- 2019-08-21T19:51:05.613Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583274)
;

-- 2019-08-21T19:51:05.615Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583274
;

-- 2019-08-21T19:51:05.615Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583274, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560113
;

-- 2019-08-21T19:51:05.679Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542515,583275,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','N','N','N','N','N','N','N','Portalpasswort',270,0,999,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.680Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.683Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540622) 
;

-- 2019-08-21T19:51:05.685Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583275
;

-- 2019-08-21T19:51:05.685Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583275)
;

-- 2019-08-21T19:51:05.687Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583275
;

-- 2019-08-21T19:51:05.687Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583275, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560114
;

-- 2019-08-21T19:51:05.758Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14619,583276,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Auf welche Weise der Java-Client mit dem Server verbunden ist',1,'U','Depending on the connection profile, different protocols are used and tasks are performed on the server rather then the client. Usually the user can select different profiles, unless it is enforced by the User or Role definition. The User level profile overwrites the Role based profile.',0,'Y','N','N','N','N','N','N','N','Verbindungsart',280,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.759Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.762Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2880) 
;

-- 2019-08-21T19:51:05.766Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583276
;

-- 2019-08-21T19:51:05.767Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583276)
;

-- 2019-08-21T19:51:05.769Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583276
;

-- 2019-08-21T19:51:05.770Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583276, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560115
;

-- 2019-08-21T19:51:05.845Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8976,583277,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Durchführende oder auslösende Organisation',14,'U','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.',0,'Y','N','N','N','N','N','N','N','Buchende Organisation',290,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.846Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.849Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(112) 
;

-- 2019-08-21T19:51:05.864Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583277
;

-- 2019-08-21T19:51:05.864Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583277)
;

-- 2019-08-21T19:51:05.866Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583277
;

-- 2019-08-21T19:51:05.867Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583277, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560116
;

-- 2019-08-21T19:51:05.937Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5397,583278,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Vorgesetzter oder Übergeordneter für diesen Nutzer oder diese Organisation - verwendet für Eskalation und Freigabe',26,'U','"Vorgesetzter" zeigt an, wer für Weiterleitung und Eskalation von Vorfällen dieses Nutzers verwendet wird - oder für Freigaben.',0,'Y','N','N','N','N','N','N','N','Vorgesetzter',300,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:05.938Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:05.942Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1522) 
;

-- 2019-08-21T19:51:05.948Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583278
;

-- 2019-08-21T19:51:05.948Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583278)
;

-- 2019-08-21T19:51:05.951Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583278
;

-- 2019-08-21T19:51:05.951Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583278, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560117
;

-- 2019-08-21T19:51:06.028Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,422,583279,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.029Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.032Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T19:51:06.236Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583279
;

-- 2019-08-21T19:51:06.236Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583279)
;

-- 2019-08-21T19:51:06.237Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583279
;

-- 2019-08-21T19:51:06.238Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583279, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560118
;

-- 2019-08-21T19:51:06.315Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,423,583280,0,541855,73,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,10,1,1,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.316Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583280 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.317Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T19:51:06.529Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583280
;

-- 2019-08-21T19:51:06.529Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583280)
;

-- 2019-08-21T19:51:06.530Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583280
;

-- 2019-08-21T19:51:06.530Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583280, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560119
;

-- 2019-08-21T19:51:06.613Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5844,583281,1002045,0,541855,297,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','N','N','N','Y','N','Geschäftspartner',30,20,1,1,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.615Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.619Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002045) 
;

-- 2019-08-21T19:51:06.620Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583281
;

-- 2019-08-21T19:51:06.621Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583281)
;

-- 2019-08-21T19:51:06.622Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583281
;

-- 2019-08-21T19:51:06.622Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583281, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560120
;

-- 2019-08-21T19:51:06.686Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8743,583282,0,541855,55,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz',14,'U','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.',0,'Y','Y','Y','N','N','N','N','N','Anrede (ID)',40,30,1,1,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.686Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583282 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.691Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1159) 
;

-- 2019-08-21T19:51:06.698Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583282
;

-- 2019-08-21T19:51:06.699Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583282)
;

-- 2019-08-21T19:51:06.700Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583282
;

-- 2019-08-21T19:51:06.700Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583282, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560121
;

-- 2019-08-21T19:51:06.835Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541684,583283,0,541855,109,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100,'Vorname',255,'U',0,'Y','Y','Y','N','N','N','N','N','Vorname',50,40,999,1,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.836Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583283 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.838Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540398) 
;

-- 2019-08-21T19:51:06.840Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583283
;

-- 2019-08-21T19:51:06.841Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583283)
;

-- 2019-08-21T19:51:06.843Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583283
;

-- 2019-08-21T19:51:06.843Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583283, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560122
;

-- 2019-08-21T19:51:06.901Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541683,583284,0,541855,106,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100,255,'U',0,'Y','Y','Y','N','N','N','N','Y','Nachname',60,50,999,1,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.901Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583284 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.904Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540399) 
;

-- 2019-08-21T19:51:06.907Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583284
;

-- 2019-08-21T19:51:06.908Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583284)
;

-- 2019-08-21T19:51:06.910Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583284
;

-- 2019-08-21T19:51:06.910Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583284, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560123
;

-- 2019-08-21T19:51:06.966Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,213,583285,0,541855,146,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100,'',60,'U','',0,'Y','Y','Y','N','N','N','N','N','Name',70,60,1,999,1,TO_TIMESTAMP('2019-08-21 21:51:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:06.967Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583285 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:06.968Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-08-21T19:51:07.019Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583285
;

-- 2019-08-21T19:51:07.020Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583285)
;

-- 2019-08-21T19:51:07.021Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583285
;

-- 2019-08-21T19:51:07.021Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583285, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560124
;

-- 2019-08-21T19:51:07.091Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,214,583286,0,541855,128,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100,60,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',80,70,999,1,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:07.092Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583286 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:07.094Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-08-21T19:51:07.209Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583286
;

-- 2019-08-21T19:51:07.209Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583286)
;

-- 2019-08-21T19:51:07.211Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583286
;

-- 2019-08-21T19:51:07.211Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583286, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560125
;

-- 2019-08-21T19:51:07.277Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8752,583287,0,541855,122,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information',60,'U','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.',0,'Y','Y','Y','N','N','N','N','N','Bemerkungen',90,80,999,1,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:07.277Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583287 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:07.278Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(230) 
;

-- 2019-08-21T19:51:07.280Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583287
;

-- 2019-08-21T19:51:07.280Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583287)
;

-- 2019-08-21T19:51:07.282Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583287
;

-- 2019-08-21T19:51:07.282Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583287, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560126
;

-- 2019-08-21T19:51:07.348Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,622,583288,0,541855,44,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',100,90,1,1,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:07.348Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583288 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:07.350Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T19:51:07.780Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583288
;

-- 2019-08-21T19:51:07.780Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583288)
;

-- 2019-08-21T19:51:07.781Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583288
;

-- 2019-08-21T19:51:07.781Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583288, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560127
;

-- 2019-08-21T19:51:07.860Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,500036,583289,0,541855,157,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Standard-Ansprechpartner',110,100,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:07.861Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583289 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:07.864Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500035) 
;

-- 2019-08-21T19:51:07.871Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583289
;

-- 2019-08-21T19:51:07.871Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583289)
;

-- 2019-08-21T19:51:07.873Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583289
;

-- 2019-08-21T19:51:07.873Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583289, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560128
;

-- 2019-08-21T19:51:07.950Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549866,583290,0,541855,129,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','N','N','Weihnachtsgeschenk',120,110,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:07.951Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:07.953Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542245) 
;

-- 2019-08-21T19:51:07.957Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583290
;

-- 2019-08-21T19:51:07.957Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583290)
;

-- 2019-08-21T19:51:07.959Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583290
;

-- 2019-08-21T19:51:07.959Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583290, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560129
;

-- 2019-08-21T19:51:08.037Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551164,583291,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Weihnachtsgeschenk',121,111,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.038Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.041Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542543) 
;

-- 2019-08-21T19:51:08.043Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583291
;

-- 2019-08-21T19:51:08.043Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583291)
;

-- 2019-08-21T19:51:08.046Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583291
;

-- 2019-08-21T19:51:08.046Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583291, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560130
;

-- 2019-08-21T19:51:08.108Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5396,583292,0,541855,200,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'U','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.',0,'Y','Y','Y','N','N','N','N','N','eMail',130,120,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.109Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.113Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2019-08-21T19:51:08.129Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583292
;

-- 2019-08-21T19:51:08.129Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583292)
;

-- 2019-08-21T19:51:08.131Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583292
;

-- 2019-08-21T19:51:08.132Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583292, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560131
;

-- 2019-08-21T19:51:08.199Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,417,583293,0,541855,68,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'',20,'U','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".',0,'Y','Y','Y','Y','N','N','N','Y','Kennwort',140,130,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.200Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.202Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(498) 
;

-- 2019-08-21T19:51:08.213Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583293
;

-- 2019-08-21T19:51:08.213Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583293)
;

-- 2019-08-21T19:51:08.215Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583293
;

-- 2019-08-21T19:51:08.215Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583293, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560132
;

-- 2019-08-21T19:51:08.286Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554318,583294,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann',0,'U',0,'Y','Y','Y','N','N','N','N','N','Mengenmeldung App',145,135,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.288Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583294 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.291Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543059) 
;

-- 2019-08-21T19:51:08.293Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583294
;

-- 2019-08-21T19:51:08.293Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583294)
;

-- 2019-08-21T19:51:08.295Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583294
;

-- 2019-08-21T19:51:08.296Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583294, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560133
;

-- 2019-08-21T19:51:08.361Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8751,583295,0,541855,40,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag',20,'U','"Titel" gibt die Bezeichnung für diesen Eintrag an.',0,'Y','Y','Y','N','N','N','N','N','Titel',150,140,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.362Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.365Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(982) 
;

-- 2019-08-21T19:51:08.369Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583295
;

-- 2019-08-21T19:51:08.370Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583295)
;

-- 2019-08-21T19:51:08.372Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583295
;

-- 2019-08-21T19:51:08.372Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583295, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560134
;

-- 2019-08-21T19:51:08.446Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8745,583296,0,541855,75,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag',14,'U','Geburtstag oder Jahrestag',0,'Y','Y','Y','N','N','N','N','Y','Geburtstag',160,150,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.447Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.448Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1891) 
;

-- 2019-08-21T19:51:08.450Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583296
;

-- 2019-08-21T19:51:08.450Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583296)
;

-- 2019-08-21T19:51:08.452Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583296
;

-- 2019-08-21T19:51:08.452Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583296, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560135
;

-- 2019-08-21T19:51:08.515Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8747,583297,0,541855,123,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',20,'U','',0,'Y','Y','Y','N','N','N','N','N','Telefon',170,160,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.516Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.517Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505) 
;

-- 2019-08-21T19:51:08.520Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583297
;

-- 2019-08-21T19:51:08.520Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583297)
;

-- 2019-08-21T19:51:08.521Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583297
;

-- 2019-08-21T19:51:08.521Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583297, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560136
;

-- 2019-08-21T19:51:08.579Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8744,583298,0,541855,117,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Telefonnummer',20,'U','',0,'Y','Y','Y','N','N','N','N','Y','Telefon (alternativ)',180,170,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.580Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.582Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(506) 
;

-- 2019-08-21T19:51:08.586Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583298
;

-- 2019-08-21T19:51:08.586Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583298)
;

-- 2019-08-21T19:51:08.589Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583298
;

-- 2019-08-21T19:51:08.589Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583298, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560137
;

-- 2019-08-21T19:51:08.652Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8748,583299,0,541855,93,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer',20,'U','The Fax identifies a facsimile number for this Business Partner or  Location',0,'Y','Y','Y','N','N','N','N','N','Fax',190,180,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.653Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.656Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(301) 
;

-- 2019-08-21T19:51:08.661Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583299
;

-- 2019-08-21T19:51:08.662Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583299)
;

-- 2019-08-21T19:51:08.665Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583299
;

-- 2019-08-21T19:51:08.666Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583299, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560138
;

-- 2019-08-21T19:51:08.732Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14396,583300,0,541855,60,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Position in der Firma',1,'U',0,'Y','Y','Y','N','N','N','N','N','Position',200,190,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.733Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.735Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2761) 
;

-- 2019-08-21T19:51:08.740Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583300
;

-- 2019-08-21T19:51:08.740Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583300)
;

-- 2019-08-21T19:51:08.742Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583300
;

-- 2019-08-21T19:51:08.742Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583300, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560139
;

-- 2019-08-21T19:51:08.809Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9884,583301,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Überprüfung der EMail-Adresse',20,'U','Dieses Feld enthält das Datum, an dem die EMail-Adresse verifiziert wurde.',0,'Y','N','N','N','N','N','Y','Y','Überprüfung EMail',210,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.810Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.819Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2190) 
;

-- 2019-08-21T19:51:08.823Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583301
;

-- 2019-08-21T19:51:08.823Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583301)
;

-- 2019-08-21T19:51:08.826Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583301
;

-- 2019-08-21T19:51:08.826Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583301, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560140
;

-- 2019-08-21T19:51:08.895Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8746,583302,1000283,0,541855,62,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'U','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','Y','Standort',240,200,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.896Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:08.898Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000283) 
;

-- 2019-08-21T19:51:08.905Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583302
;

-- 2019-08-21T19:51:08.905Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583302)
;

-- 2019-08-21T19:51:08.908Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583302
;

-- 2019-08-21T19:51:08.908Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583302, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560141
;

-- 2019-08-21T19:51:08.997Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550221,583303,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Einkaufskontakt',250,210,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:08.998Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:09Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542329) 
;

-- 2019-08-21T19:51:09.001Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583303
;

-- 2019-08-21T19:51:09.001Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583303)
;

-- 2019-08-21T19:51:09.002Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583303
;

-- 2019-08-21T19:51:09.002Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583303, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560142
;

-- 2019-08-21T19:51:09.065Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556074,583304,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsPurchaseContact@ = ''Y''','U',0,'Y','Y','Y','N','N','N','N','N','Std. Einkaufskontakt',255,215,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.066Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:09.067Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543274) 
;

-- 2019-08-21T19:51:09.069Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583304
;

-- 2019-08-21T19:51:09.069Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583304)
;

-- 2019-08-21T19:51:09.070Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583304
;

-- 2019-08-21T19:51:09.070Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583304, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560143
;

-- 2019-08-21T19:51:09.131Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541898,583305,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','Y','N','N','N','N','N','Verkaufskontakt',260,260,1,1,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.132Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:09.133Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540454) 
;

-- 2019-08-21T19:51:09.139Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583305
;

-- 2019-08-21T19:51:09.139Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583305)
;

-- 2019-08-21T19:51:09.141Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583305
;

-- 2019-08-21T19:51:09.141Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583305, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560144
;

-- 2019-08-21T19:51:09.211Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556073,583306,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,1,'@IsSalesContact@ = ''Y'' ','U',0,'Y','Y','Y','N','N','N','N','N','Std. Verkaufskontakt',265,265,1,1,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.212Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:09.214Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543275) 
;

-- 2019-08-21T19:51:09.216Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583306
;

-- 2019-08-21T19:51:09.217Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583306)
;

-- 2019-08-21T19:51:09.219Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583306
;

-- 2019-08-21T19:51:09.220Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583306, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560145
;

-- 2019-08-21T19:51:09.296Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555109,583307,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Rechnung per eMail',270,220,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.297Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:09.300Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543189) 
;

-- 2019-08-21T19:51:09.303Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583307
;

-- 2019-08-21T19:51:09.303Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583307)
;

-- 2019-08-21T19:51:09.305Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583307
;

-- 2019-08-21T19:51:09.306Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583307, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560146
;

-- 2019-08-21T19:51:09.377Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556333,583308,0,541855,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.',0,'@IsSystemUser@ = ''Y''','U','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.',0,'Y','Y','Y','N','N','N','N','Y','Druck-Empfänger',280,275,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.378Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:09.381Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542879) 
;

-- 2019-08-21T19:51:09.384Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583308
;

-- 2019-08-21T19:51:09.384Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583308)
;

-- 2019-08-21T19:51:09.387Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583308
;

-- 2019-08-21T19:51:09.388Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583308, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560147
;

-- 2019-08-21T19:51:09.447Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541855,541411,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:51:09.448Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541411 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:51:09.449Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541411
;

-- 2019-08-21T19:51:09.450Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541411, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540486
;

-- 2019-08-21T19:51:09.518Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541811,541411,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.586Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541811,542737,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.661Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583286,0,541855,542737,560598,'F',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.737Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583287,0,541855,542737,560599,'F',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Kommentar oder zusätzliche Information','"Bemerkungen" erlaubt das Eintragen weiterer, nicht vorgegebener Information.','Y','N','N','N','N','N','N','Bemerkungen',20,0,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.808Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583282,0,541855,542737,560600,'F',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','Y','Y','N','N','Anrede',10,10,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.890Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583284,0,541855,542737,560601,'F',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Nachname',20,20,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:09.967Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583283,0,541855,542737,560602,'F',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','N','Y','Y','N','N','Vorname',30,30,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.036Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583288,0,541855,542737,560603,'F',TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',40,40,0,TO_TIMESTAMP('2019-08-21 21:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.112Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583292,0,541855,542737,560604,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','Y','N','N','eMail',50,50,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.176Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583297,0,541855,542737,560605,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Beschreibt eine Telefon Nummer','Y','N','N','Y','Y','N','N','Telefon',60,60,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.240Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583299,0,541855,542737,560606,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','Y','Y','N','N','Fax',70,70,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.321Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583296,0,541855,542737,560607,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Geburtstag oder Jahrestag','Geburtstag oder Jahrestag','Y','N','N','Y','Y','N','N','Geburtstag',80,80,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.397Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583300,0,541855,542737,560608,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Position in der Firma','Y','N','N','Y','Y','N','N','Position',90,90,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.464Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583305,0,541855,542737,560609,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','IsSalesContact',100,100,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.529Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583303,0,541855,542737,560610,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Einkaufskontakt',110,110,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.604Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583289,0,541855,542737,560611,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Standard-Ansprechpartner',120,120,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.679Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583291,0,541855,542737,560612,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Weihnachtsgeschenk',130,0,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.744Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583295,0,541855,542737,560613,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung für diesen Eintrag','"Titel" gibt die Bezeichnung für diesen Eintrag an.','Y','N','N','Y','N','N','N','Titel',140,0,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.821Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583281,0,541855,542737,560614,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','Geschäftspartner',150,0,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.889Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,583302,0,540408,560614,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:10.963Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583293,0,541855,542737,560615,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Passwort beliebiger Länge (unterscheided Groß- und Kleinschreibung)','The Password for this User.  Passwords are required to identify authorized users.  For Adempiere Users, you can change the password via the Process "Reset Password".','Y','N','N','Y','N','N','N','Kennwort',160,0,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.036Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583294,0,541855,542737,560616,'F',TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann','Y','N','N','Y','N','N','N','Mengenmeldung App',170,0,0,TO_TIMESTAMP('2019-08-21 21:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.103Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583298,0,541855,542737,560617,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Mobile Telefonnummer','"Telfon (alternativ)" gibt eine weitere Telefonnummer an.','Y','N','N','Y','N','N','N','Mobil',180,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.208Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583285,0,541855,542737,560618,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','N','N','Name',190,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.270Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583308,0,541855,542737,560619,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.','Y','N','N','Y','N','N','N','Druck-Empfänger',200,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.348Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583306,0,541855,542737,560620,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','IsSalesContact_Default',210,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.420Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583304,0,541855,542737,560621,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','IsPurchaseContact_Default',220,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.481Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583307,0,541855,542737,560622,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Rechnung per eMail',230,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.559Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583280,0,541855,542737,560623,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',240,130,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.626Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583279,0,541855,542737,560624,'F',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',250,0,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.694Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,5433,573897,0,541856,417,540676,'Y',TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','R_Request','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Vorgänge','N',50,1,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.696Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541856 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T19:51:11.698Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(573897) 
;

-- 2019-08-21T19:51:11.702Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541856)
;

-- 2019-08-21T19:51:11.704Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541856
;

-- 2019-08-21T19:51:11.705Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541856, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540872
;

-- 2019-08-21T19:51:11.772Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13488,114,583309,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',14,'U','The Role determines security and access a user who has this Role will have in the System.',0,'Y','N','N','N','N','N','N','Y','Rolle',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.773Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:11.776Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(123) 
;

-- 2019-08-21T19:51:11.786Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583309
;

-- 2019-08-21T19:51:11.787Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583309)
;

-- 2019-08-21T19:51:11.789Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583309
;

-- 2019-08-21T19:51:11.789Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583309, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560148
;

-- 2019-08-21T19:51:11.859Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13575,583310,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',20,'U','The Start Date indicates the first or starting date',0,'Y','N','N','N','N','N','N','N','Anfangsdatum',20,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.860Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:11.862Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574) 
;

-- 2019-08-21T19:51:11.875Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583310
;

-- 2019-08-21T19:51:11.875Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583310)
;

-- 2019-08-21T19:51:11.877Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583310
;

-- 2019-08-21T19:51:11.878Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583310, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560149
;

-- 2019-08-21T19:51:11.956Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13496,114,583311,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die bereits in Rechnung gestellt wurde',26,'@IsInvoiced@=Y','U',0,'Y','N','N','N','N','N','N','Y','Berechn. Menge',30,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:11.957Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:11.960Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(529) 
;

-- 2019-08-21T19:51:11.974Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583311
;

-- 2019-08-21T19:51:11.974Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583311)
;

-- 2019-08-21T19:51:11.977Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583311
;

-- 2019-08-21T19:51:11.977Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583311, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560150
;

-- 2019-08-21T19:51:12.049Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13494,114,583312,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100,'End of the time span',20,'U',0,'Y','N','N','N','N','N','N','Y','Enddatum',40,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:12.050Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:12.053Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2714) 
;

-- 2019-08-21T19:51:12.057Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583312
;

-- 2019-08-21T19:51:12.057Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583312)
;

-- 2019-08-21T19:51:12.059Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583312
;

-- 2019-08-21T19:51:12.059Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583312, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560151
;

-- 2019-08-21T19:51:12.129Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13493,114,583313,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100,'',20,'U',0,'Y','N','N','N','N','N','N','N','Startdatum',50,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:12.131Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:12.133Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2713) 
;

-- 2019-08-21T19:51:12.136Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583313
;

-- 2019-08-21T19:51:12.137Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583313)
;

-- 2019-08-21T19:51:12.139Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583313
;

-- 2019-08-21T19:51:12.139Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583313, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560152
;

-- 2019-08-21T19:51:12.201Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13495,114,583314,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100,'Quantity used for this event',26,'U',0,'Y','N','N','N','N','N','N','Y','Quantity Used',60,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:12.202Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:12.205Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2715) 
;

-- 2019-08-21T19:51:12.214Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583314
;

-- 2019-08-21T19:51:12.214Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583314)
;

-- 2019-08-21T19:51:12.216Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583314
;

-- 2019-08-21T19:51:12.217Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583314, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560153
;

-- 2019-08-21T19:51:12.289Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5418,101,583315,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',70,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:12.290Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:12.293Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-08-21T19:51:12.671Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583315
;

-- 2019-08-21T19:51:12.671Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583315)
;

-- 2019-08-21T19:51:12.674Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583315
;

-- 2019-08-21T19:51:12.674Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583315, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560154
;

-- 2019-08-21T19:51:12.923Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12927,105,583316,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100,'Date when last alert were sent',14,'U','The last alert date is updated when a reminder email is sent',0,'Y','N','N','N','N','N','Y','Y','Last Alert',80,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:12.924Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:12.926Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2629) 
;

-- 2019-08-21T19:51:12.929Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583316
;

-- 2019-08-21T19:51:12.930Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583316)
;

-- 2019-08-21T19:51:12.931Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583316
;

-- 2019-08-21T19:51:12.932Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583316, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560155
;

-- 2019-08-21T19:51:13.002Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555043,114,583317,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','Y','N','Request Type Interner Name',90,0,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.002Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.004Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543175) 
;

-- 2019-08-21T19:51:13.006Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583317
;

-- 2019-08-21T19:51:13.006Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583317)
;

-- 2019-08-21T19:51:13.007Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583317
;

-- 2019-08-21T19:51:13.008Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583317, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560156
;

-- 2019-08-21T19:51:13.087Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13497,114,583318,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Product/Resource/Service used in Request',26,'U','Invoicing uses the Product used.',0,'Y','N','N','N','N','N','N','N','Product Used',100,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.087Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.089Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2716) 
;

-- 2019-08-21T19:51:13.092Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583318
;

-- 2019-08-21T19:51:13.092Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583318)
;

-- 2019-08-21T19:51:13.095Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583318
;

-- 2019-08-21T19:51:13.096Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583318, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560157
;

-- 2019-08-21T19:51:13.168Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13489,583319,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?',1,'U','If selected, invoices are created',0,'Y','N','N','N','N','N','N','Y','Invoiced',110,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.169Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.171Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387) 
;

-- 2019-08-21T19:51:13.188Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583319
;

-- 2019-08-21T19:51:13.188Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583319)
;

-- 2019-08-21T19:51:13.190Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583319
;

-- 2019-08-21T19:51:13.191Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583319, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560158
;

-- 2019-08-21T19:51:13.269Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13499,583320,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'The generated invoice for this request',14,'U','The optionally generated invoice for the request',0,'Y','N','N','N','N','N','Y','Y','Request Invoice',120,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.270Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.272Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2717) 
;

-- 2019-08-21T19:51:13.275Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583320
;

-- 2019-08-21T19:51:13.275Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583320)
;

-- 2019-08-21T19:51:13.277Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583320
;

-- 2019-08-21T19:51:13.277Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583320, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560159
;

-- 2019-08-21T19:51:13.375Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13576,583321,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Close Date',20,'U','The Start Date indicates the last or final date',0,'Y','N','N','N','N','N','N','N','Close Date',130,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.376Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.378Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2721) 
;

-- 2019-08-21T19:51:13.387Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583321
;

-- 2019-08-21T19:51:13.388Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583321)
;

-- 2019-08-21T19:51:13.390Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583321
;

-- 2019-08-21T19:51:13.390Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583321, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560160
;

-- 2019-08-21T19:51:13.454Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13487,583322,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Type of Confidentiality',14,'U',0,'Y','N','N','N','N','N','N','N','Confidentiality',140,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.455Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.457Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2709) 
;

-- 2019-08-21T19:51:13.460Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583322
;

-- 2019-08-21T19:51:13.460Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583322)
;

-- 2019-08-21T19:51:13.462Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583322
;

-- 2019-08-21T19:51:13.462Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583322, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560161
;

-- 2019-08-21T19:51:13.538Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5425,104,583323,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Amount associated with this request',26,'U','The Request Amount indicates any amount that is associated with this request.  For example, a warranty amount or refund amount.',0,'Y','N','N','N','N','N','N','N','Request Amount',150,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.539Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.541Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1520) 
;

-- 2019-08-21T19:51:13.543Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583323
;

-- 2019-08-21T19:51:13.543Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583323)
;

-- 2019-08-21T19:51:13.544Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583323
;

-- 2019-08-21T19:51:13.544Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583323, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560162
;

-- 2019-08-21T19:51:13.603Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13491,114,583324,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Confidentiality of the individual entry',14,'U',0,'Y','N','N','N','N','N','N','Y','Entry Confidentiality',160,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.604Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.605Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2711) 
;

-- 2019-08-21T19:51:13.607Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583324
;

-- 2019-08-21T19:51:13.607Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583324)
;

-- 2019-08-21T19:51:13.608Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583324
;

-- 2019-08-21T19:51:13.608Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583324, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560163
;

-- 2019-08-21T19:51:13.696Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5429,583325,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'This request has been escalated',1,'U','The Escalated checkbox indicates that this request has been escalated or raised in importance.',0,'Y','N','N','N','N','N','Y','N','Escalated',170,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.697Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.699Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1509) 
;

-- 2019-08-21T19:51:13.702Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583325
;

-- 2019-08-21T19:51:13.702Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583325)
;

-- 2019-08-21T19:51:13.703Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583325
;

-- 2019-08-21T19:51:13.703Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583325, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560164
;

-- 2019-08-21T19:51:13.764Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10580,583326,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Asset used internally or by customers',26,'U','An asset is either created by purchasing or by delivering a product.  An asset can be used internally or be a customer asset.',0,'Y','N','N','N','N','N','N','Y','Asset',180,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.766Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.769Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1884) 
;

-- 2019-08-21T19:51:13.787Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583326
;

-- 2019-08-21T19:51:13.787Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583326)
;

-- 2019-08-21T19:51:13.790Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583326
;

-- 2019-08-21T19:51:13.790Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583326, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560165
;

-- 2019-08-21T19:51:13.857Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13483,583327,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Request Category',14,'U','Category or Topic of the Request ',0,'Y','N','N','N','N','N','N','N','Category',190,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.858Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.861Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2705) 
;

-- 2019-08-21T19:51:13.866Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583327
;

-- 2019-08-21T19:51:13.866Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583327)
;

-- 2019-08-21T19:51:13.868Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583327
;

-- 2019-08-21T19:51:13.869Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583327, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560166
;

-- 2019-08-21T19:51:13.941Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8774,583328,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'This is a Self-Service entry or this entry can be changed via Self-Service',1,'U','Self-Service allows users to enter data or update their data.  The flag indicates, that this record was entered or created via Self-Service or that the user can change it via the Self-Service functionality.',0,'Y','N','N','N','N','N','Y','Y','Self-Service',200,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:13.942Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:13.945Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2063) 
;

-- 2019-08-21T19:51:13.973Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583328
;

-- 2019-08-21T19:51:13.974Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583328)
;

-- 2019-08-21T19:51:13.976Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583328
;

-- 2019-08-21T19:51:13.977Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583328, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560167
;

-- 2019-08-21T19:51:14.060Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13490,583329,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Related Request (Master Issue, ..)',14,'U','Request related to this request',0,'Y','N','N','N','N','N','N','Y','Related Request',210,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:14.061Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:14.063Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2710) 
;

-- 2019-08-21T19:51:14.066Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583329
;

-- 2019-08-21T19:51:14.066Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583329)
;

-- 2019-08-21T19:51:14.069Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583329
;

-- 2019-08-21T19:51:14.069Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583329, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560168
;

-- 2019-08-21T19:51:14.144Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13078,583330,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',14,'@AD_Table_ID@!0','U','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','Y','N','DB-Tabelle',220,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:14.145Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:14.148Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-08-21T19:51:14.182Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583330
;

-- 2019-08-21T19:51:14.182Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583330)
;

-- 2019-08-21T19:51:14.185Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583330
;

-- 2019-08-21T19:51:14.185Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583330, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560169
;

-- 2019-08-21T19:51:14.259Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5441,114,583331,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100,'Text templates for mailings',14,'U','The Mail Template indicates the mail template for return messages. Mail text can include variables.  The priority of parsing is User/Contact, Business Partner and then the underlying business object (like Request, Dunning, Workflow object).<br>
So, @Name@ would resolve into the User name (if user is defined defined), then Business Partner name (if business partner is defined) and then the Name of the business object if it has a Name.<br>
For Multi-Lingual systems, the template is translated based on the Business Partner''s language selection.',0,'Y','N','N','N','N','N','N','Y','Mail Template',230,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:14.260Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:14.263Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1515) 
;

-- 2019-08-21T19:51:14.276Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583331
;

-- 2019-08-21T19:51:14.276Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583331)
;

-- 2019-08-21T19:51:14.279Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583331
;

-- 2019-08-21T19:51:14.279Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583331, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560170
;

-- 2019-08-21T19:51:14.348Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5416,583332,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:14.349Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:14.352Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T19:51:14.616Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583332
;

-- 2019-08-21T19:51:14.617Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583332)
;

-- 2019-08-21T19:51:14.618Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583332
;

-- 2019-08-21T19:51:14.618Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583332, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560171
;

-- 2019-08-21T19:51:14.701Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5417,583333,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:14.702Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:14.705Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T19:51:14.979Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583333
;

-- 2019-08-21T19:51:14.980Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583333)
;

-- 2019-08-21T19:51:14.981Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583333
;

-- 2019-08-21T19:51:14.981Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583333, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560172
;

-- 2019-08-21T19:51:15.061Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7791,583334,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100,'','Type of request (e.g. Inquiry, Complaint, ..)',14,'U','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.',0,'Y','Y','Y','N','N','N','N','N','Vorgangs Art',25,25,1,1,TO_TIMESTAMP('2019-08-21 21:51:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.061Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.064Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1894) 
;

-- 2019-08-21T19:51:15.069Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583334
;

-- 2019-08-21T19:51:15.069Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583334)
;

-- 2019-08-21T19:51:15.071Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583334
;

-- 2019-08-21T19:51:15.072Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583334, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560173
;

-- 2019-08-21T19:51:15.142Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5433,583335,1002597,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',30,30,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.143Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.146Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002597) 
;

-- 2019-08-21T19:51:15.148Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583335
;

-- 2019-08-21T19:51:15.148Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583335)
;

-- 2019-08-21T19:51:15.150Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583335
;

-- 2019-08-21T19:51:15.151Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583335, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560174
;

-- 2019-08-21T19:51:15.217Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5423,583336,1002418,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',20,'U','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','Y','Nr.',40,40,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.218Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.221Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002418) 
;

-- 2019-08-21T19:51:15.230Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583336
;

-- 2019-08-21T19:51:15.230Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583336)
;

-- 2019-08-21T19:51:15.232Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583336
;

-- 2019-08-21T19:51:15.233Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583336, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560175
;

-- 2019-08-21T19:51:15.316Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5434,583337,1002855,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',50,50,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.317Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.319Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002855) 
;

-- 2019-08-21T19:51:15.321Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583337
;

-- 2019-08-21T19:51:15.321Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583337)
;

-- 2019-08-21T19:51:15.324Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583337
;

-- 2019-08-21T19:51:15.324Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583337, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560176
;

-- 2019-08-21T19:51:15.389Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556050,583338,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Zulieferant',55,55,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.390Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.392Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543273) 
;

-- 2019-08-21T19:51:15.394Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583338
;

-- 2019-08-21T19:51:15.394Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583338)
;

-- 2019-08-21T19:51:15.396Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583338
;

-- 2019-08-21T19:51:15.397Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583338, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560177
;

-- 2019-08-21T19:51:15.482Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5799,583339,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Project',60,60,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.483Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.485Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-08-21T19:51:15.508Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583339
;

-- 2019-08-21T19:51:15.508Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583339)
;

-- 2019-08-21T19:51:15.510Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583339
;

-- 2019-08-21T19:51:15.510Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583339, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560178
;

-- 2019-08-21T19:51:15.583Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5436,583340,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',26,'U','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','N','N','N','N','N','Auftrag',70,70,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.584Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583340 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.587Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2019-08-21T19:51:15.613Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583340
;

-- 2019-08-21T19:51:15.614Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583340)
;

-- 2019-08-21T19:51:15.616Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583340
;

-- 2019-08-21T19:51:15.616Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583340, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560179
;

-- 2019-08-21T19:51:15.694Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555039,583341,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde',0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','Lieferdatum',80,80,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.695Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.697Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(264) 
;

-- 2019-08-21T19:51:15.708Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583341
;

-- 2019-08-21T19:51:15.708Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583341)
;

-- 2019-08-21T19:51:15.710Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583341
;

-- 2019-08-21T19:51:15.711Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583341, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560180
;

-- 2019-08-21T19:51:15.781Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5437,583342,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Invoice Document.',0,'Y','Y','Y','N','N','N','N','Y','Rechnung',90,90,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.782Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.784Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2019-08-21T19:51:15.804Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583342
;

-- 2019-08-21T19:51:15.804Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583342)
;

-- 2019-08-21T19:51:15.806Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583342
;

-- 2019-08-21T19:51:15.806Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583342, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560181
;

-- 2019-08-21T19:51:15.873Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5439,583343,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',26,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',100,100,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:15.873Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:15.875Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-08-21T19:51:15.938Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583343
;

-- 2019-08-21T19:51:15.939Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583343)
;

-- 2019-08-21T19:51:15.944Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583343
;

-- 2019-08-21T19:51:15.944Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583343, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560182
;

-- 2019-08-21T19:51:16.016Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5438,583344,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).',0,'Y','Y','Y','N','N','N','N','Y','Zahlung',110,110,1,1,TO_TIMESTAMP('2019-08-21 21:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.017Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.018Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1384) 
;

-- 2019-08-21T19:51:16.031Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583344
;

-- 2019-08-21T19:51:16.031Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583344)
;

-- 2019-08-21T19:51:16.033Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583344
;

-- 2019-08-21T19:51:16.033Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583344, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560183
;

-- 2019-08-21T19:51:16.110Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5435,583345,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@$Element_MC@=Y','U','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',115,115,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.111Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.113Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2019-08-21T19:51:16.129Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583345
;

-- 2019-08-21T19:51:16.129Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583345)
;

-- 2019-08-21T19:51:16.132Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583345
;

-- 2019-08-21T19:51:16.132Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583345, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560184
;

-- 2019-08-21T19:51:16.199Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13573,583346,1001527,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',26,'U','The Material Shipment / Receipt ',0,'Y','Y','Y','N','N','N','N','N','Lieferung/Wareneingang',120,120,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.201Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.204Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001527) 
;

-- 2019-08-21T19:51:16.206Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583346
;

-- 2019-08-21T19:51:16.206Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583346)
;

-- 2019-08-21T19:51:16.208Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583346
;

-- 2019-08-21T19:51:16.208Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583346, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560185
;

-- 2019-08-21T19:51:16.288Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13574,583347,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','A Return Material Authorization may be required to accept returns and to create Credit Memos',0,'Y','Y','Y','N','N','N','N','Y','RMA',130,130,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.289Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583347 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.293Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2412) 
;

-- 2019-08-21T19:51:16.306Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583347
;

-- 2019-08-21T19:51:16.306Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583347)
;

-- 2019-08-21T19:51:16.308Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583347
;

-- 2019-08-21T19:51:16.308Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583347, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560186
;

-- 2019-08-21T19:51:16.383Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555040,583348,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','Rücklieferung',140,140,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.384Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.387Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543174) 
;

-- 2019-08-21T19:51:16.390Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583348
;

-- 2019-08-21T19:51:16.390Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583348)
;

-- 2019-08-21T19:51:16.392Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583348
;

-- 2019-08-21T19:51:16.393Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583348, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560187
;

-- 2019-08-21T19:51:16.467Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13079,583349,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',23,'@AD_Table_ID@!0','U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','Y','N','N','N','Y','Y','Datensatz-ID',150,150,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.468Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.470Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2019-08-21T19:51:16.493Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583349
;

-- 2019-08-21T19:51:16.494Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583349)
;

-- 2019-08-21T19:51:16.496Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583349
;

-- 2019-08-21T19:51:16.496Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583349, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560188
;

-- 2019-08-21T19:51:16.562Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13498,114,583350,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle',160,160,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.563Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.565Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2019-08-21T19:51:16.590Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583350
;

-- 2019-08-21T19:51:16.590Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583350)
;

-- 2019-08-21T19:51:16.593Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583350
;

-- 2019-08-21T19:51:16.593Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583350, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560189
;

-- 2019-08-21T19:51:16.669Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5445,114,583351,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Date Next Action indicates the next scheduled date for an action to occur for this request.',0,'Y','Y','Y','N','N','N','N','N','Date next action',180,180,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.670Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.673Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1503) 
;

-- 2019-08-21T19:51:16.683Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583351
;

-- 2019-08-21T19:51:16.683Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583351)
;

-- 2019-08-21T19:51:16.685Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583351
;

-- 2019-08-21T19:51:16.686Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583351, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560190
;

-- 2019-08-21T19:51:16.768Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5427,114,583352,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Due Type indicates if this request is Due, Overdue or Scheduled.',0,'Y','Y','Y','N','N','N','N','Y','Due type',190,190,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.769Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.772Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1504) 
;

-- 2019-08-21T19:51:16.775Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583352
;

-- 2019-08-21T19:51:16.775Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583352)
;

-- 2019-08-21T19:51:16.777Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583352
;

-- 2019-08-21T19:51:16.778Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583352, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560191
;

-- 2019-08-21T19:51:16.834Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13482,114,583353,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Request Group',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Group of requests (e.g. version numbers, responsibility, ...)',0,'Y','Y','Y','N','N','N','N','Y','Group',220,220,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.836Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.838Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2704) 
;

-- 2019-08-21T19:51:16.844Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583353
;

-- 2019-08-21T19:51:16.844Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583353)
;

-- 2019-08-21T19:51:16.846Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583353
;

-- 2019-08-21T19:51:16.846Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583353, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560192
;

-- 2019-08-21T19:51:16.910Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13485,114,583354,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution',14,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Resolution status (e.g. Fixed, Rejected, ..)',0,'Y','Y','Y','N','N','N','N','N','Resolution',240,240,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:16.912Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:16.914Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2707) 
;

-- 2019-08-21T19:51:16.919Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583354
;

-- 2019-08-21T19:51:16.919Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583354)
;

-- 2019-08-21T19:51:16.922Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583354
;

-- 2019-08-21T19:51:16.922Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583354, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560193
;

-- 2019-08-21T19:51:17.001Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5426,114,583355,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.',14,'U','The Priority indicates the importance of this request.',0,'Y','Y','Y','N','N','N','N','N','Priority',260,260,1,1,TO_TIMESTAMP('2019-08-21 21:51:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.003Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.005Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1514) 
;

-- 2019-08-21T19:51:17.014Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583355
;

-- 2019-08-21T19:51:17.014Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583355)
;

-- 2019-08-21T19:51:17.016Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583355
;

-- 2019-08-21T19:51:17.017Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583355, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560194
;

-- 2019-08-21T19:51:17.089Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555046,114,583356,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','PerformanceType',265,265,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.089Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.091Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543176) 
;

-- 2019-08-21T19:51:17.092Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583356
;

-- 2019-08-21T19:51:17.092Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583356)
;

-- 2019-08-21T19:51:17.093Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583356
;

-- 2019-08-21T19:51:17.094Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583356, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560195
;

-- 2019-08-21T19:51:17.163Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13486,114,583357,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','User Importance',270,270,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.163Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.165Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2708) 
;

-- 2019-08-21T19:51:17.174Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583357
;

-- 2019-08-21T19:51:17.174Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583357)
;

-- 2019-08-21T19:51:17.176Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583357
;

-- 2019-08-21T19:51:17.176Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583357, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560196
;

-- 2019-08-21T19:51:17.238Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555041,114,583358,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','N','Qualität-Notiz',275,275,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.239Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.242Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543213) 
;

-- 2019-08-21T19:51:17.243Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583358
;

-- 2019-08-21T19:51:17.244Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583358)
;

-- 2019-08-21T19:51:17.246Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583358
;

-- 2019-08-21T19:51:17.246Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583358, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560197
;

-- 2019-08-21T19:51:17.318Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5428,114,583359,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request',60,'U','The Summary allows free form text entry of a recap of this request.',0,'Y','Y','Y','N','N','N','N','N','Zusammenfassung',280,280,999,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.319Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.322Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1521) 
;

-- 2019-08-21T19:51:17.327Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583359
;

-- 2019-08-21T19:51:17.328Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583359)
;

-- 2019-08-21T19:51:17.330Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583359
;

-- 2019-08-21T19:51:17.330Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583359, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560198
;

-- 2019-08-21T19:51:17.400Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5444,114,583360,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken',14,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U','The Next Action indicates the next action to be taken on this request.',0,'Y','Y','Y','N','N','N','N','N','Next action',285,285,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.401Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.404Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1513) 
;

-- 2019-08-21T19:51:17.406Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583360
;

-- 2019-08-21T19:51:17.407Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583360)
;

-- 2019-08-21T19:51:17.409Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583360
;

-- 2019-08-21T19:51:17.409Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583360, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560199
;

-- 2019-08-21T19:51:17.469Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5432,114,583361,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'',14,'U','',0,'Y','Y','Y','N','N','N','Y','N','N','Verkaufsmitarbeiter',290,290,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.470Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583361 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.473Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2019-08-21T19:51:17.501Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583361
;

-- 2019-08-21T19:51:17.502Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583361)
;

-- 2019-08-21T19:51:17.503Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583361
;

-- 2019-08-21T19:51:17.504Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583361, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560200
;

-- 2019-08-21T19:51:17.568Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13492,114,583362,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ',14,'U','Text blocks to be copied into request response text',0,'Y','Y','Y','N','N','N','N','N','Standard Response',300,300,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.569Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.571Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2712) 
;

-- 2019-08-21T19:51:17.573Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583362
;

-- 2019-08-21T19:51:17.573Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583362)
;

-- 2019-08-21T19:51:17.575Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583362
;

-- 2019-08-21T19:51:17.575Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583362, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560201
;

-- 2019-08-21T19:51:17.661Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5443,114,583363,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken',60,'U','The Result indicates the result of any action taken on this request.',0,'Y','Y','Y','N','N','N','N','N','Ergebnis',310,310,999,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.662Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.665Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(546) 
;

-- 2019-08-21T19:51:17.668Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583363
;

-- 2019-08-21T19:51:17.668Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583363)
;

-- 2019-08-21T19:51:17.670Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583363
;

-- 2019-08-21T19:51:17.671Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583363, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560202
;

-- 2019-08-21T19:51:17.734Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13484,114,583364,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',14,'U','Status if the request (open, closed, investigating, ..)',0,'Y','Y','Y','N','N','N','N','N','Status',320,320,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.735Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.738Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2706) 
;

-- 2019-08-21T19:51:17.743Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583364
;

-- 2019-08-21T19:51:17.743Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583364)
;

-- 2019-08-21T19:51:17.746Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583364
;

-- 2019-08-21T19:51:17.746Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583364, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560203
;

-- 2019-08-21T19:51:17.814Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5430,105,583365,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on',20,'U','The Date Last Action indicates that last time that the request was acted on.',0,'Y','Y','Y','N','N','N','Y','N','Date last action',340,340,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.814Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.816Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1502) 
;

-- 2019-08-21T19:51:17.825Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583365
;

-- 2019-08-21T19:51:17.825Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583365)
;

-- 2019-08-21T19:51:17.827Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583365
;

-- 2019-08-21T19:51:17.827Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583365, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560204
;

-- 2019-08-21T19:51:17.901Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,Included_Tab_ID,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5415,105,583366,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Request from a Business Partner or Prospect',14,'U','The Request identifies a unique request from a Business Partner or Prospect.',0,740,'Y','Y','Y','N','N','N','N','N','Aufgabe',350,350,1,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.904Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.907Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1516) 
;

-- 2019-08-21T19:51:17.914Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583366
;

-- 2019-08-21T19:51:17.915Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583366)
;

-- 2019-08-21T19:51:17.917Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583366
;

-- 2019-08-21T19:51:17.917Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583366, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560205
;

-- 2019-08-21T19:51:17.991Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5431,105,583367,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact',60,'U','The Last Result identifies the result of the last contact made.',0,'Y','Y','Y','N','N','N','Y','Y','Last Result',355,355,999,1,TO_TIMESTAMP('2019-08-21 21:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:17.992Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:17.994Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(431) 
;

-- 2019-08-21T19:51:18.003Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583367
;

-- 2019-08-21T19:51:18.004Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583367)
;

-- 2019-08-21T19:51:18.006Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583367
;

-- 2019-08-21T19:51:18.006Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583367, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560206
;

-- 2019-08-21T19:51:18.077Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5419,105,583368,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',20,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','Y','N','Erstellt',360,360,-1,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.078Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.080Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-08-21T19:51:18.149Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583368
;

-- 2019-08-21T19:51:18.149Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583368)
;

-- 2019-08-21T19:51:18.151Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583368
;

-- 2019-08-21T19:51:18.151Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583368, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560207
;

-- 2019-08-21T19:51:18.226Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5420,105,583369,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',14,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','Y','Y','Erstellt durch',370,370,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.227Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.228Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-08-21T19:51:18.273Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583369
;

-- 2019-08-21T19:51:18.273Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583369)
;

-- 2019-08-21T19:51:18.274Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583369
;

-- 2019-08-21T19:51:18.275Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583369, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560208
;

-- 2019-08-21T19:51:18.348Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5421,105,583370,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',20,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','Y','N','Aktualisiert',380,380,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.349Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.351Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-08-21T19:51:18.413Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583370
;

-- 2019-08-21T19:51:18.414Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583370)
;

-- 2019-08-21T19:51:18.415Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583370
;

-- 2019-08-21T19:51:18.416Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583370, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560209
;

-- 2019-08-21T19:51:18.561Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5422,105,583371,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',14,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','Y','Y','Aktualisiert durch',390,390,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.562Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.564Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-08-21T19:51:18.639Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583371
;

-- 2019-08-21T19:51:18.640Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583371)
;

-- 2019-08-21T19:51:18.641Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583371
;

-- 2019-08-21T19:51:18.642Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583371, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560210
;

-- 2019-08-21T19:51:18.721Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5447,105,583372,1000821,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'1=2','U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','Y','Y','Verarbeitet',400,400,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.722Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583372 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.724Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000821) 
;

-- 2019-08-21T19:51:18.726Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583372
;

-- 2019-08-21T19:51:18.726Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583372)
;

-- 2019-08-21T19:51:18.728Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583372
;

-- 2019-08-21T19:51:18.729Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583372, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560211
;

-- 2019-08-21T19:51:18.799Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557025,583373,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum',410,410,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.800Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.802Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543388) 
;

-- 2019-08-21T19:51:18.810Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583373
;

-- 2019-08-21T19:51:18.811Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583373)
;

-- 2019-08-21T19:51:18.813Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583373
;

-- 2019-08-21T19:51:18.813Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583373, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560212
;

-- 2019-08-21T19:51:18.886Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557045,583374,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',0,'U','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','N','N','N','N','N','Vorgangsdatum',420,420,-1,1,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.887Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.890Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2019-08-21T19:51:18.900Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583374
;

-- 2019-08-21T19:51:18.900Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583374)
;

-- 2019-08-21T19:51:18.902Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583374
;

-- 2019-08-21T19:51:18.903Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583374, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560213
;

-- 2019-08-21T19:51:18.977Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540359,583375,0,541856,0,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','N','N','N','N','N','N','N','Actual Phone',430,999,1,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:18.979Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583375 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:18.981Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540063) 
;

-- 2019-08-21T19:51:18.993Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583375
;

-- 2019-08-21T19:51:18.993Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583375)
;

-- 2019-08-21T19:51:18.996Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583375
;

-- 2019-08-21T19:51:18.996Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583375, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560214
;

-- 2019-08-21T19:51:19.062Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541856,541412,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:18','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:51:19.063Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541412 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:51:19.065Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541412
;

-- 2019-08-21T19:51:19.073Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541412, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540487
;

-- 2019-08-21T19:51:19.143Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541812,541412,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.218Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541812,542738,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.287Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583361,0,541856,542738,560625,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','N','N','N','N','Aussendienst',10,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.346Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583370,0,541856,542738,560626,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','Aktualisiert',20,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.415Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583371,0,541856,542738,560627,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','Aktualisiert durch',30,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.498Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583340,0,541856,542738,560628,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','Auftrag',40,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.575Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583365,0,541856,542738,560629,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on','The Date Last Action indicates that last time that the request was acted on.','Y','N','N','N','N','N','N','Date last action',50,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.649Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583351,0,541856,542738,560630,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on','The Date Next Action indicates the next scheduled date for an action to occur for this request.','Y','N','N','N','N','N','N','Date next action',60,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.720Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583349,0,541856,542738,560631,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','Datensatz-ID',70,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.803Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583352,0,541856,542738,560632,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request','The Due Type indicates if this request is Due, Overdue or Scheduled.','Y','N','N','N','N','N','N','Due type',80,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.918Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583335,0,541856,542738,560633,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','Geschäftspartner',90,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:19.984Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,583337,0,540409,560633,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.052Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583353,0,541856,542738,560634,'F',TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Request Group','Group of requests (e.g. version numbers, responsibility, ...)','Y','N','N','N','N','N','N','Group',100,0,0,TO_TIMESTAMP('2019-08-21 21:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.125Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583350,0,541856,542738,560635,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','Kostenstelle',110,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.196Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583367,0,541856,542738,560636,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact','The Last Result identifies the result of the last contact made.','Y','N','N','N','N','N','N','Last Result',120,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.268Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583341,0,541856,542738,560637,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde','Y','N','N','N','N','N','N','Lieferdatum',130,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.341Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583346,0,541856,542738,560638,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','N','N','N','N','Lieferung/Wareneingang',140,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.403Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583360,0,541856,542738,560639,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken','The Next Action indicates the next action to be taken on this request.','Y','N','N','N','N','N','N','Next action',150,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.479Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583356,0,541856,542738,560640,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','PerformanceType',160,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.551Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583355,0,541856,542738,560641,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.','The Priority indicates the importance of this request.','Y','N','N','N','N','N','N','Priority',170,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.615Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583343,0,541856,542738,560642,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','Produkt',180,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.674Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583339,0,541856,542738,560643,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','Project',190,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.734Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583358,0,541856,542738,560644,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Qualität-Notiz',200,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.816Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583347,0,541856,542738,560645,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization','A Return Material Authorization may be required to accept returns and to create Credit Memos','Y','N','N','N','N','N','N','RMA',210,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.894Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583342,0,541856,542738,560646,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','N','N','N','N','Rechnung',220,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:20.969Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583354,0,541856,542738,560647,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution','Resolution status (e.g. Fixed, Rejected, ..)','Y','N','N','N','N','N','N','Resolution',230,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.043Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583348,0,541856,542738,560648,'F',TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Rücklieferung',240,0,0,TO_TIMESTAMP('2019-08-21 21:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.121Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583362,0,541856,542738,560649,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ','Text blocks to be copied into request response text','Y','N','N','N','N','N','N','Standard Response',250,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.225Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583364,0,541856,542738,560650,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Request Status','Status if the request (open, closed, investigating, ..)','Y','N','N','N','N','N','N','Status',260,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.302Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583357,0,541856,542738,560651,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User','Y','N','N','N','N','N','N','User Importance',270,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.387Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583374,0,541856,542738,560652,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','N','N','N','N','Vorgangsdatum',280,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.464Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583345,0,541856,542738,560653,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','N','N','N','N','N','N','Werbemassnahme',290,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.542Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583373,0,541856,542738,560654,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Wiedervorlage Datum',300,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.620Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583344,0,541856,542738,560655,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).','Y','N','N','N','N','N','N','Zahlung',310,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.709Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583338,0,541856,542738,560656,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Zulieferant',320,0,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:21.787Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583336,0,541856,542738,560657,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','Y','Y','N','Nr.',10,10,10,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:51:21.858Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583334,0,541856,542738,560658,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Type of request (e.g. Inquiry, Complaint, ..)','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.','Y','N','N','Y','Y','Y','N','Request Type',20,20,20,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:21.921Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583359,0,541856,542738,560659,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request','The Summary allows free form text entry of a recap of this request.','Y','N','N','Y','Y','N','N','Zusammenfassung',30,30,0,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:51:21.989Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583368,0,541856,542738,560660,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','Y','Y','N','Erstellt',40,40,30,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:22.072Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583369,0,541856,542738,560661,'F',TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','Y','Y','N','Erstellt durch',50,50,40,TO_TIMESTAMP('2019-08-21 21:51:21','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:22.140Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583363,0,541856,542738,560662,'F',TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken','The Result indicates the result of any action taken on this request.','Y','N','N','Y','Y','N','N','Ergebnis',60,60,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:51:22.202Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583333,0,541856,542738,560663,'F',TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',70,70,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:22.274Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583332,0,541856,542738,560664,'F',TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',80,0,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:22.330Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,542717,573898,0,541857,540148,540676,'Y',TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','x_bpartner_history','Y','N','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Verlauf',2893,'N',60,1,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:22.332Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541857 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-08-21T19:51:22.334Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(573898) 
;

-- 2019-08-21T19:51:22.349Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541857)
;

-- 2019-08-21T19:51:22.352Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541857
;

-- 2019-08-21T19:51:22.352Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541857, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540875
;

-- 2019-08-21T19:51:22.436Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542720,583376,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','Mandant',10,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:22.437Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:22.440Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-08-21T19:51:22.725Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583376
;

-- 2019-08-21T19:51:22.726Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583376)
;

-- 2019-08-21T19:51:22.727Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583376
;

-- 2019-08-21T19:51:22.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583376, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560215
;

-- 2019-08-21T19:51:22.793Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542717,583377,1002079,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','N','N','N','N','N','N','Geschäftspartner',20,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:22.793Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583377 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:22.794Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002079) 
;

-- 2019-08-21T19:51:22.799Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583377
;

-- 2019-08-21T19:51:22.799Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583377)
;

-- 2019-08-21T19:51:22.800Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583377
;

-- 2019-08-21T19:51:22.801Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583377, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560216
;

-- 2019-08-21T19:51:22.869Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542727,583378,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',14,'U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','N','N','N','N','N','N','Datensatz-ID',30,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:22.870Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:22.872Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2019-08-21T19:51:22.892Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583378
;

-- 2019-08-21T19:51:22.892Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583378)
;

-- 2019-08-21T19:51:22.894Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583378
;

-- 2019-08-21T19:51:22.894Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583378, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560217
;

-- 2019-08-21T19:51:22.958Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542726,583379,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',14,'U','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','N','N','DB-Tabelle',40,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:22.959Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:22.961Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-08-21T19:51:22.989Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583379
;

-- 2019-08-21T19:51:22.990Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583379)
;

-- 2019-08-21T19:51:22.991Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583379
;

-- 2019-08-21T19:51:22.992Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583379, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560218
;

-- 2019-08-21T19:51:23.067Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542722,583380,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',10,10,1,1,TO_TIMESTAMP('2019-08-21 21:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.068Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.071Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-08-21T19:51:23.150Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583380
;

-- 2019-08-21T19:51:23.151Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583380)
;

-- 2019-08-21T19:51:23.153Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583380
;

-- 2019-08-21T19:51:23.153Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583380, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560219
;

-- 2019-08-21T19:51:23.228Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542719,583381,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,30,'U',0,'Y','Y','Y','N','N','N','N','N','Typ',20,20,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.229Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583381 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.231Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540652) 
;

-- 2019-08-21T19:51:23.234Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583381
;

-- 2019-08-21T19:51:23.234Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583381)
;

-- 2019-08-21T19:51:23.236Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583381
;

-- 2019-08-21T19:51:23.237Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583381, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560220
;

-- 2019-08-21T19:51:23.309Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542718,583382,1001601,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',30,'U','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Nr.',30,30,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.311Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583382 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.313Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001601) 
;

-- 2019-08-21T19:51:23.315Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583382
;

-- 2019-08-21T19:51:23.315Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583382)
;

-- 2019-08-21T19:51:23.319Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583382
;

-- 2019-08-21T19:51:23.319Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583382, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560221
;

-- 2019-08-21T19:51:23.383Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542724,583383,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert',40,40,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.384Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583383 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.387Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-08-21T19:51:23.450Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583383
;

-- 2019-08-21T19:51:23.450Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583383)
;

-- 2019-08-21T19:51:23.452Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583383
;

-- 2019-08-21T19:51:23.453Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583383, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560222
;

-- 2019-08-21T19:51:23.524Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542725,583384,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert durch',50,50,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.525Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583384 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.527Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-08-21T19:51:23.593Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583384
;

-- 2019-08-21T19:51:23.594Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583384)
;

-- 2019-08-21T19:51:23.595Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583384
;

-- 2019-08-21T19:51:23.596Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583384, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560223
;

-- 2019-08-21T19:51:23.671Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542723,583385,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',60,60,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.672Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583385 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.673Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-08-21T19:51:23.708Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583385
;

-- 2019-08-21T19:51:23.708Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583385)
;

-- 2019-08-21T19:51:23.710Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583385
;

-- 2019-08-21T19:51:23.710Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583385, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560224
;

-- 2019-08-21T19:51:23.788Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542728,583386,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',70,70,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:23.790Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583386 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:23.793Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-08-21T19:51:23.955Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583386
;

-- 2019-08-21T19:51:23.956Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583386)
;

-- 2019-08-21T19:51:23.957Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583386
;

-- 2019-08-21T19:51:23.958Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583386, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560225
;

-- 2019-08-21T19:51:24.029Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,542721,583387,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',80,80,1,1,TO_TIMESTAMP('2019-08-21 21:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:24.036Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583387 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:24.039Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-08-21T19:51:24.278Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583387
;

-- 2019-08-21T19:51:24.278Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583387)
;

-- 2019-08-21T19:51:24.279Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583387
;

-- 2019-08-21T19:51:24.280Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583387, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560226
;

-- 2019-08-21T19:51:24.349Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557049,583388,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','x_bpartner_history_id',90,90,0,1,1,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:24.350Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583388 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:24.352Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543397) 
;

-- 2019-08-21T19:51:24.355Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583388
;

-- 2019-08-21T19:51:24.356Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583388)
;

-- 2019-08-21T19:51:24.357Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583388
;

-- 2019-08-21T19:51:24.358Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583388, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560227
;

-- 2019-08-21T19:51:24.432Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557078,583389,0,541857,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',0,'U','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','N','N','N','N','N','Vorgangsdatum',100,100,1,1,1,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:24.433Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583389 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-21T19:51:24.436Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2019-08-21T19:51:24.439Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583389
;

-- 2019-08-21T19:51:24.439Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583389)
;

-- 2019-08-21T19:51:24.441Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583389
;

-- 2019-08-21T19:51:24.441Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583389, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560228
;

-- 2019-08-21T19:51:24.518Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541857,541413,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-08-21T19:51:24.519Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541413 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-08-21T19:51:24.521Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541413
;

-- 2019-08-21T19:51:24.522Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541413, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540488
;

-- 2019-08-21T19:51:24.589Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541813,541413,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:24.659Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541813,542739,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:24.732Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583388,0,541857,542739,560665,'F',TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','x_bpartner_history_id',10,0,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:24.804Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583381,0,541857,542739,560666,'F',TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Typ',10,10,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:24.876Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583382,0,541857,542739,560667,'F',TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','Y','N','N','Nr.',20,20,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:51:24.946Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583389,0,541857,542739,560668,'F',TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','Y','N','N','Vorgangsdatum',30,30,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:51:25.010Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583380,0,541857,542739,560669,'F',TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','Y','N','N','Erstellt',40,40,0,TO_TIMESTAMP('2019-08-21 21:51:24','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:51:25.072Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583385,0,541857,542739,560670,'F',TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','Y','N','N','Erstellt durch',50,50,0,TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:25.141Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583383,0,541857,542739,560671,'F',TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','Y','N','N','Aktualisiert',60,60,0,TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2019-08-21T19:51:25.219Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583384,0,541857,542739,560672,'F',TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','Y','N','N','Aktualisiert durch',70,70,0,TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:51:25.283Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583386,0,541857,542739,560673,'F',TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',80,80,0,TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-08-21T19:51:25.347Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583387,0,541857,542739,560674,'F',TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',90,90,0,TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:51:25.419Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,583376,0,541857,542739,560675,'F',TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',100,0,0,TO_TIMESTAMP('2019-08-21 21:51:25','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2019-08-21T19:52:11.720Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560673
;

-- 2019-08-21T19:52:11.721Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583386
;

-- 2019-08-21T19:52:11.721Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583386
;

-- 2019-08-21T19:52:11.727Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583386
;

-- 2019-08-21T19:52:11.733Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560675
;

-- 2019-08-21T19:52:11.734Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583376
;

-- 2019-08-21T19:52:11.734Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583376
;

-- 2019-08-21T19:52:11.736Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583376
;

-- 2019-08-21T19:52:11.739Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583377
;

-- 2019-08-21T19:52:11.739Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583377
;

-- 2019-08-21T19:52:11.741Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583377
;

-- 2019-08-21T19:52:11.744Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583378
;

-- 2019-08-21T19:52:11.744Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583378
;

-- 2019-08-21T19:52:11.745Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583378
;

-- 2019-08-21T19:52:11.748Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583379
;

-- 2019-08-21T19:52:11.749Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583379
;

-- 2019-08-21T19:52:11.750Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583379
;

-- 2019-08-21T19:52:11.754Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560669
;

-- 2019-08-21T19:52:11.755Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583380
;

-- 2019-08-21T19:52:11.755Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583380
;

-- 2019-08-21T19:52:11.756Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583380
;

-- 2019-08-21T19:52:11.761Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560666
;

-- 2019-08-21T19:52:11.761Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583381
;

-- 2019-08-21T19:52:11.762Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583381
;

-- 2019-08-21T19:52:11.762Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583381
;

-- 2019-08-21T19:52:11.767Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560667
;

-- 2019-08-21T19:52:11.768Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583382
;

-- 2019-08-21T19:52:11.768Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583382
;

-- 2019-08-21T19:52:11.769Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583382
;

-- 2019-08-21T19:52:11.773Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560671
;

-- 2019-08-21T19:52:11.774Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583383
;

-- 2019-08-21T19:52:11.774Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583383
;

-- 2019-08-21T19:52:11.775Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583383
;

-- 2019-08-21T19:52:11.779Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560672
;

-- 2019-08-21T19:52:11.779Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583384
;

-- 2019-08-21T19:52:11.780Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583384
;

-- 2019-08-21T19:52:11.780Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583384
;

-- 2019-08-21T19:52:11.784Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560670
;

-- 2019-08-21T19:52:11.784Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583385
;

-- 2019-08-21T19:52:11.785Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583385
;

-- 2019-08-21T19:52:11.785Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583385
;

-- 2019-08-21T19:52:11.789Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560674
;

-- 2019-08-21T19:52:11.789Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583387
;

-- 2019-08-21T19:52:11.790Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583387
;

-- 2019-08-21T19:52:11.790Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583387
;

-- 2019-08-21T19:52:11.794Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560665
;

-- 2019-08-21T19:52:11.794Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583388
;

-- 2019-08-21T19:52:11.794Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583388
;

-- 2019-08-21T19:52:11.795Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583388
;

-- 2019-08-21T19:52:11.798Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560668
;

-- 2019-08-21T19:52:11.798Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583389
;

-- 2019-08-21T19:52:11.798Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583389
;

-- 2019-08-21T19:52:11.799Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583389
;

-- 2019-08-21T19:52:11.808Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542739
;

-- 2019-08-21T19:52:11.809Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541813
;

-- 2019-08-21T19:52:11.810Z
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541413
;

-- 2019-08-21T19:52:11.816Z
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541413
;

-- 2019-08-21T19:52:11.816Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=541857
;

-- 2019-08-21T19:52:11.817Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=541857
;

-- 2019-08-21T19:52:18.040Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583309
;

-- 2019-08-21T19:52:18.041Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583309
;

-- 2019-08-21T19:52:18.044Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583309
;

-- 2019-08-21T19:52:18.049Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583310
;

-- 2019-08-21T19:52:18.050Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583310
;

-- 2019-08-21T19:52:18.052Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583310
;

-- 2019-08-21T19:52:18.056Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583311
;

-- 2019-08-21T19:52:18.056Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583311
;

-- 2019-08-21T19:52:18.058Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583311
;

-- 2019-08-21T19:52:18.063Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583312
;

-- 2019-08-21T19:52:18.064Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583312
;

-- 2019-08-21T19:52:18.065Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583312
;

-- 2019-08-21T19:52:18.070Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583313
;

-- 2019-08-21T19:52:18.071Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583313
;

-- 2019-08-21T19:52:18.073Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583313
;

-- 2019-08-21T19:52:18.080Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583314
;

-- 2019-08-21T19:52:18.080Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583314
;

-- 2019-08-21T19:52:18.082Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583314
;

-- 2019-08-21T19:52:18.087Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583315
;

-- 2019-08-21T19:52:18.087Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583315
;

-- 2019-08-21T19:52:18.088Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583315
;

-- 2019-08-21T19:52:18.092Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583316
;

-- 2019-08-21T19:52:18.092Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583316
;

-- 2019-08-21T19:52:18.094Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583316
;

-- 2019-08-21T19:52:18.097Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583317
;

-- 2019-08-21T19:52:18.098Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583317
;

-- 2019-08-21T19:52:18.099Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583317
;

-- 2019-08-21T19:52:18.103Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583318
;

-- 2019-08-21T19:52:18.103Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583318
;

-- 2019-08-21T19:52:18.105Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583318
;

-- 2019-08-21T19:52:18.109Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583319
;

-- 2019-08-21T19:52:18.109Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583319
;

-- 2019-08-21T19:52:18.111Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583319
;

-- 2019-08-21T19:52:18.115Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583320
;

-- 2019-08-21T19:52:18.116Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583320
;

-- 2019-08-21T19:52:18.118Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583320
;

-- 2019-08-21T19:52:18.122Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583321
;

-- 2019-08-21T19:52:18.123Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583321
;

-- 2019-08-21T19:52:18.125Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583321
;

-- 2019-08-21T19:52:18.129Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583322
;

-- 2019-08-21T19:52:18.129Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583322
;

-- 2019-08-21T19:52:18.131Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583322
;

-- 2019-08-21T19:52:18.135Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583323
;

-- 2019-08-21T19:52:18.135Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583323
;

-- 2019-08-21T19:52:18.136Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583323
;

-- 2019-08-21T19:52:18.142Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583324
;

-- 2019-08-21T19:52:18.142Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583324
;

-- 2019-08-21T19:52:18.143Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583324
;

-- 2019-08-21T19:52:18.146Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583325
;

-- 2019-08-21T19:52:18.146Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583325
;

-- 2019-08-21T19:52:18.147Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583325
;

-- 2019-08-21T19:52:18.150Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583326
;

-- 2019-08-21T19:52:18.150Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583326
;

-- 2019-08-21T19:52:18.151Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583326
;

-- 2019-08-21T19:52:18.154Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583327
;

-- 2019-08-21T19:52:18.154Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583327
;

-- 2019-08-21T19:52:18.155Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583327
;

-- 2019-08-21T19:52:18.158Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583328
;

-- 2019-08-21T19:52:18.158Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583328
;

-- 2019-08-21T19:52:18.159Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583328
;

-- 2019-08-21T19:52:18.162Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583329
;

-- 2019-08-21T19:52:18.162Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583329
;

-- 2019-08-21T19:52:18.163Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583329
;

-- 2019-08-21T19:52:18.165Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583330
;

-- 2019-08-21T19:52:18.166Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583330
;

-- 2019-08-21T19:52:18.166Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583330
;

-- 2019-08-21T19:52:18.169Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583331
;

-- 2019-08-21T19:52:18.169Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583331
;

-- 2019-08-21T19:52:18.170Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583331
;

-- 2019-08-21T19:52:18.174Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560664
;

-- 2019-08-21T19:52:18.174Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583332
;

-- 2019-08-21T19:52:18.174Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583332
;

-- 2019-08-21T19:52:18.175Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583332
;

-- 2019-08-21T19:52:18.180Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560663
;

-- 2019-08-21T19:52:18.180Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583333
;

-- 2019-08-21T19:52:18.180Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583333
;

-- 2019-08-21T19:52:18.181Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583333
;

-- 2019-08-21T19:52:18.185Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560658
;

-- 2019-08-21T19:52:18.185Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583334
;

-- 2019-08-21T19:52:18.185Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583334
;

-- 2019-08-21T19:52:18.186Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583334
;

-- 2019-08-21T19:52:18.190Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540409
;

-- 2019-08-21T19:52:18.191Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560633
;

-- 2019-08-21T19:52:18.192Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583335
;

-- 2019-08-21T19:52:18.192Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583335
;

-- 2019-08-21T19:52:18.192Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583335
;

-- 2019-08-21T19:52:18.198Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560651
;

-- 2019-08-21T19:52:18.199Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583357
;

-- 2019-08-21T19:52:18.199Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583357
;

-- 2019-08-21T19:52:18.200Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583357
;

-- 2019-08-21T19:52:18.204Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560657
;

-- 2019-08-21T19:52:18.204Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583336
;

-- 2019-08-21T19:52:18.204Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583336
;

-- 2019-08-21T19:52:18.205Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583336
;

-- 2019-08-21T19:52:18.207Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583337
;

-- 2019-08-21T19:52:18.207Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583337
;

-- 2019-08-21T19:52:18.208Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583337
;

-- 2019-08-21T19:52:18.211Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560656
;

-- 2019-08-21T19:52:18.212Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583338
;

-- 2019-08-21T19:52:18.212Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583338
;

-- 2019-08-21T19:52:18.212Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583338
;

-- 2019-08-21T19:52:18.216Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560643
;

-- 2019-08-21T19:52:18.217Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583339
;

-- 2019-08-21T19:52:18.217Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583339
;

-- 2019-08-21T19:52:18.218Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583339
;

-- 2019-08-21T19:52:18.221Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560628
;

-- 2019-08-21T19:52:18.222Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583340
;

-- 2019-08-21T19:52:18.222Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583340
;

-- 2019-08-21T19:52:18.223Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583340
;

-- 2019-08-21T19:52:18.226Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560637
;

-- 2019-08-21T19:52:18.226Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583341
;

-- 2019-08-21T19:52:18.227Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583341
;

-- 2019-08-21T19:52:18.227Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583341
;

-- 2019-08-21T19:52:18.231Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560646
;

-- 2019-08-21T19:52:18.231Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583342
;

-- 2019-08-21T19:52:18.231Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583342
;

-- 2019-08-21T19:52:18.232Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583342
;

-- 2019-08-21T19:52:18.235Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560642
;

-- 2019-08-21T19:52:18.236Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583343
;

-- 2019-08-21T19:52:18.236Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583343
;

-- 2019-08-21T19:52:18.237Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583343
;

-- 2019-08-21T19:52:18.240Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560655
;

-- 2019-08-21T19:52:18.240Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583344
;

-- 2019-08-21T19:52:18.241Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583344
;

-- 2019-08-21T19:52:18.241Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583344
;

-- 2019-08-21T19:52:18.245Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560653
;

-- 2019-08-21T19:52:18.245Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583345
;

-- 2019-08-21T19:52:18.245Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583345
;

-- 2019-08-21T19:52:18.246Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583345
;

-- 2019-08-21T19:52:18.250Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560638
;

-- 2019-08-21T19:52:18.250Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583346
;

-- 2019-08-21T19:52:18.251Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583346
;

-- 2019-08-21T19:52:18.251Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583346
;

-- 2019-08-21T19:52:18.254Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560645
;

-- 2019-08-21T19:52:18.255Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583347
;

-- 2019-08-21T19:52:18.255Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583347
;

-- 2019-08-21T19:52:18.256Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583347
;

-- 2019-08-21T19:52:18.259Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560648
;

-- 2019-08-21T19:52:18.260Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583348
;

-- 2019-08-21T19:52:18.260Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583348
;

-- 2019-08-21T19:52:18.261Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583348
;

-- 2019-08-21T19:52:18.265Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560631
;

-- 2019-08-21T19:52:18.265Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583349
;

-- 2019-08-21T19:52:18.265Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583349
;

-- 2019-08-21T19:52:18.266Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583349
;

-- 2019-08-21T19:52:18.269Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560635
;

-- 2019-08-21T19:52:18.269Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583350
;

-- 2019-08-21T19:52:18.270Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583350
;

-- 2019-08-21T19:52:18.270Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583350
;

-- 2019-08-21T19:52:18.273Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560630
;

-- 2019-08-21T19:52:18.274Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583351
;

-- 2019-08-21T19:52:18.274Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583351
;

-- 2019-08-21T19:52:18.274Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583351
;

-- 2019-08-21T19:52:18.277Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560632
;

-- 2019-08-21T19:52:18.278Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583352
;

-- 2019-08-21T19:52:18.278Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583352
;

-- 2019-08-21T19:52:18.278Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583352
;

-- 2019-08-21T19:52:18.281Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560634
;

-- 2019-08-21T19:52:18.281Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583353
;

-- 2019-08-21T19:52:18.282Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583353
;

-- 2019-08-21T19:52:18.282Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583353
;

-- 2019-08-21T19:52:18.285Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560647
;

-- 2019-08-21T19:52:18.285Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583354
;

-- 2019-08-21T19:52:18.285Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583354
;

-- 2019-08-21T19:52:18.286Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583354
;

-- 2019-08-21T19:52:18.289Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560641
;

-- 2019-08-21T19:52:18.289Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583355
;

-- 2019-08-21T19:52:18.289Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583355
;

-- 2019-08-21T19:52:18.290Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583355
;

-- 2019-08-21T19:52:18.293Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560640
;

-- 2019-08-21T19:52:18.293Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583356
;

-- 2019-08-21T19:52:18.293Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583356
;

-- 2019-08-21T19:52:18.294Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583356
;

-- 2019-08-21T19:52:18.297Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560644
;

-- 2019-08-21T19:52:18.297Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583358
;

-- 2019-08-21T19:52:18.297Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583358
;

-- 2019-08-21T19:52:18.298Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583358
;

-- 2019-08-21T19:52:18.301Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560659
;

-- 2019-08-21T19:52:18.302Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583359
;

-- 2019-08-21T19:52:18.302Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583359
;

-- 2019-08-21T19:52:18.303Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583359
;

-- 2019-08-21T19:52:18.306Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560639
;

-- 2019-08-21T19:52:18.306Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583360
;

-- 2019-08-21T19:52:18.307Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583360
;

-- 2019-08-21T19:52:18.307Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583360
;

-- 2019-08-21T19:52:18.312Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560625
;

-- 2019-08-21T19:52:18.313Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583361
;

-- 2019-08-21T19:52:18.313Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583361
;

-- 2019-08-21T19:52:18.313Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583361
;

-- 2019-08-21T19:52:18.318Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560649
;

-- 2019-08-21T19:52:18.318Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583362
;

-- 2019-08-21T19:52:18.319Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583362
;

-- 2019-08-21T19:52:18.319Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583362
;

-- 2019-08-21T19:52:18.322Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560662
;

-- 2019-08-21T19:52:18.323Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583363
;

-- 2019-08-21T19:52:18.323Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583363
;

-- 2019-08-21T19:52:18.324Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583363
;

-- 2019-08-21T19:52:18.327Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560650
;

-- 2019-08-21T19:52:18.328Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583364
;

-- 2019-08-21T19:52:18.328Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583364
;

-- 2019-08-21T19:52:18.329Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583364
;

-- 2019-08-21T19:52:18.332Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560629
;

-- 2019-08-21T19:52:18.332Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583365
;

-- 2019-08-21T19:52:18.332Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583365
;

-- 2019-08-21T19:52:18.333Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583365
;

-- 2019-08-21T19:52:18.335Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583366
;

-- 2019-08-21T19:52:18.335Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583366
;

-- 2019-08-21T19:52:18.335Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583366
;

-- 2019-08-21T19:52:18.338Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560636
;

-- 2019-08-21T19:52:18.338Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583367
;

-- 2019-08-21T19:52:18.338Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583367
;

-- 2019-08-21T19:52:18.339Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583367
;

-- 2019-08-21T19:52:18.342Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560660
;

-- 2019-08-21T19:52:18.342Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583368
;

-- 2019-08-21T19:52:18.342Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583368
;

-- 2019-08-21T19:52:18.342Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583368
;

-- 2019-08-21T19:52:18.345Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560661
;

-- 2019-08-21T19:52:18.345Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583369
;

-- 2019-08-21T19:52:18.346Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583369
;

-- 2019-08-21T19:52:18.346Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583369
;

-- 2019-08-21T19:52:18.349Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560626
;

-- 2019-08-21T19:52:18.349Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583370
;

-- 2019-08-21T19:52:18.349Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583370
;

-- 2019-08-21T19:52:18.350Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583370
;

-- 2019-08-21T19:52:18.353Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560627
;

-- 2019-08-21T19:52:18.353Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583371
;

-- 2019-08-21T19:52:18.354Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583371
;

-- 2019-08-21T19:52:18.354Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583371
;

-- 2019-08-21T19:52:18.356Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583372
;

-- 2019-08-21T19:52:18.356Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583372
;

-- 2019-08-21T19:52:18.357Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583372
;

-- 2019-08-21T19:52:18.360Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560654
;

-- 2019-08-21T19:52:18.360Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583373
;

-- 2019-08-21T19:52:18.360Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583373
;

-- 2019-08-21T19:52:18.361Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583373
;

-- 2019-08-21T19:52:18.364Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560652
;

-- 2019-08-21T19:52:18.364Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583374
;

-- 2019-08-21T19:52:18.365Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583374
;

-- 2019-08-21T19:52:18.365Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583374
;

-- 2019-08-21T19:52:18.367Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583375
;

-- 2019-08-21T19:52:18.367Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583375
;

-- 2019-08-21T19:52:18.368Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583375
;

-- 2019-08-21T19:52:18.372Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542738
;

-- 2019-08-21T19:52:18.373Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541812
;

-- 2019-08-21T19:52:18.373Z
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541412
;

-- 2019-08-21T19:52:18.374Z
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541412
;

-- 2019-08-21T19:52:18.375Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=541856
;

-- 2019-08-21T19:52:18.375Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=541856
;

-- 2019-08-21T19:52:25.729Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583160
;

-- 2019-08-21T19:52:25.730Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583160
;

-- 2019-08-21T19:52:25.731Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583160
;

-- 2019-08-21T19:52:25.738Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560582
;

-- 2019-08-21T19:52:25.739Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583161
;

-- 2019-08-21T19:52:25.740Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583161
;

-- 2019-08-21T19:52:25.741Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583161
;

-- 2019-08-21T19:52:25.748Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560581
;

-- 2019-08-21T19:52:25.748Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583162
;

-- 2019-08-21T19:52:25.749Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583162
;

-- 2019-08-21T19:52:25.750Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583162
;

-- 2019-08-21T19:52:25.754Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583163
;

-- 2019-08-21T19:52:25.754Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583163
;

-- 2019-08-21T19:52:25.756Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583163
;

-- 2019-08-21T19:52:25.760Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583164
;

-- 2019-08-21T19:52:25.760Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583164
;

-- 2019-08-21T19:52:25.761Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583164
;

-- 2019-08-21T19:52:25.765Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583165
;

-- 2019-08-21T19:52:25.766Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583165
;

-- 2019-08-21T19:52:25.767Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583165
;

-- 2019-08-21T19:52:25.770Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583166
;

-- 2019-08-21T19:52:25.771Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583166
;

-- 2019-08-21T19:52:25.772Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583166
;

-- 2019-08-21T19:52:25.775Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583167
;

-- 2019-08-21T19:52:25.775Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583167
;

-- 2019-08-21T19:52:25.777Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583167
;

-- 2019-08-21T19:52:25.780Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583168
;

-- 2019-08-21T19:52:25.781Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583168
;

-- 2019-08-21T19:52:25.782Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583168
;

-- 2019-08-21T19:52:25.786Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583169
;

-- 2019-08-21T19:52:25.786Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583169
;

-- 2019-08-21T19:52:25.787Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583169
;

-- 2019-08-21T19:52:25.792Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560573
;

-- 2019-08-21T19:52:25.793Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583170
;

-- 2019-08-21T19:52:25.793Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583170
;

-- 2019-08-21T19:52:25.794Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583170
;

-- 2019-08-21T19:52:25.798Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583171
;

-- 2019-08-21T19:52:25.798Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583171
;

-- 2019-08-21T19:52:25.799Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583171
;

-- 2019-08-21T19:52:25.802Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583172
;

-- 2019-08-21T19:52:25.803Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583172
;

-- 2019-08-21T19:52:25.804Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583172
;

-- 2019-08-21T19:52:25.807Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583173
;

-- 2019-08-21T19:52:25.808Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583173
;

-- 2019-08-21T19:52:25.809Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583173
;

-- 2019-08-21T19:52:25.812Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583174
;

-- 2019-08-21T19:52:25.812Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583174
;

-- 2019-08-21T19:52:25.813Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583174
;

-- 2019-08-21T19:52:25.817Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583175
;

-- 2019-08-21T19:52:25.817Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583175
;

-- 2019-08-21T19:52:25.818Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583175
;

-- 2019-08-21T19:52:25.822Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583176
;

-- 2019-08-21T19:52:25.822Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583176
;

-- 2019-08-21T19:52:25.823Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583176
;

-- 2019-08-21T19:52:25.827Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583177
;

-- 2019-08-21T19:52:25.827Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583177
;

-- 2019-08-21T19:52:25.828Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583177
;

-- 2019-08-21T19:52:25.831Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583178
;

-- 2019-08-21T19:52:25.832Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583178
;

-- 2019-08-21T19:52:25.833Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583178
;

-- 2019-08-21T19:52:25.837Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560570
;

-- 2019-08-21T19:52:25.837Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583179
;

-- 2019-08-21T19:52:25.838Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583179
;

-- 2019-08-21T19:52:25.838Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583179
;

-- 2019-08-21T19:52:25.841Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583180
;

-- 2019-08-21T19:52:25.842Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583180
;

-- 2019-08-21T19:52:25.843Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583180
;

-- 2019-08-21T19:52:25.846Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583181
;

-- 2019-08-21T19:52:25.846Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583181
;

-- 2019-08-21T19:52:25.847Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583181
;

-- 2019-08-21T19:52:25.850Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583182
;

-- 2019-08-21T19:52:25.851Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583182
;

-- 2019-08-21T19:52:25.852Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583182
;

-- 2019-08-21T19:52:25.854Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583183
;

-- 2019-08-21T19:52:25.855Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583183
;

-- 2019-08-21T19:52:25.855Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583183
;

-- 2019-08-21T19:52:25.858Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583184
;

-- 2019-08-21T19:52:25.858Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583184
;

-- 2019-08-21T19:52:25.859Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583184
;

-- 2019-08-21T19:52:25.862Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583185
;

-- 2019-08-21T19:52:25.862Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583185
;

-- 2019-08-21T19:52:25.863Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583185
;

-- 2019-08-21T19:52:25.867Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560574
;

-- 2019-08-21T19:52:25.868Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583186
;

-- 2019-08-21T19:52:25.868Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583186
;

-- 2019-08-21T19:52:25.869Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583186
;

-- 2019-08-21T19:52:25.872Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560575
;

-- 2019-08-21T19:52:25.873Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583187
;

-- 2019-08-21T19:52:25.873Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583187
;

-- 2019-08-21T19:52:25.874Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583187
;

-- 2019-08-21T19:52:25.877Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583188
;

-- 2019-08-21T19:52:25.877Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583188
;

-- 2019-08-21T19:52:25.878Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583188
;

-- 2019-08-21T19:52:25.880Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583189
;

-- 2019-08-21T19:52:25.880Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583189
;

-- 2019-08-21T19:52:25.881Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583189
;

-- 2019-08-21T19:52:25.884Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583190
;

-- 2019-08-21T19:52:25.884Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583190
;

-- 2019-08-21T19:52:25.885Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583190
;

-- 2019-08-21T19:52:25.887Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583191
;

-- 2019-08-21T19:52:25.888Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583191
;

-- 2019-08-21T19:52:25.888Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583191
;

-- 2019-08-21T19:52:25.891Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583192
;

-- 2019-08-21T19:52:25.891Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583192
;

-- 2019-08-21T19:52:25.892Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583192
;

-- 2019-08-21T19:52:25.896Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540404
;

-- 2019-08-21T19:52:25.898Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540405
;

-- 2019-08-21T19:52:25.899Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560579
;

-- 2019-08-21T19:52:25.899Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583193
;

-- 2019-08-21T19:52:25.899Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583193
;

-- 2019-08-21T19:52:25.900Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583193
;

-- 2019-08-21T19:52:25.903Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583194
;

-- 2019-08-21T19:52:25.903Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583194
;

-- 2019-08-21T19:52:25.904Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583194
;

-- 2019-08-21T19:52:25.906Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583195
;

-- 2019-08-21T19:52:25.906Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583195
;

-- 2019-08-21T19:52:25.907Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583195
;

-- 2019-08-21T19:52:25.910Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583196
;

-- 2019-08-21T19:52:25.910Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583196
;

-- 2019-08-21T19:52:25.910Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583196
;

-- 2019-08-21T19:52:25.914Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560571
;

-- 2019-08-21T19:52:25.915Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583197
;

-- 2019-08-21T19:52:25.915Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583197
;

-- 2019-08-21T19:52:25.916Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583197
;

-- 2019-08-21T19:52:25.919Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540406
;

-- 2019-08-21T19:52:25.921Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540407
;

-- 2019-08-21T19:52:25.922Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560580
;

-- 2019-08-21T19:52:25.922Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583198
;

-- 2019-08-21T19:52:25.923Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583198
;

-- 2019-08-21T19:52:25.923Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583198
;

-- 2019-08-21T19:52:25.926Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583199
;

-- 2019-08-21T19:52:25.926Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583199
;

-- 2019-08-21T19:52:25.927Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583199
;

-- 2019-08-21T19:52:25.929Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583200
;

-- 2019-08-21T19:52:25.929Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583200
;

-- 2019-08-21T19:52:25.930Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583200
;

-- 2019-08-21T19:52:25.934Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560576
;

-- 2019-08-21T19:52:25.934Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583201
;

-- 2019-08-21T19:52:25.934Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583201
;

-- 2019-08-21T19:52:25.935Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583201
;

-- 2019-08-21T19:52:25.937Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583202
;

-- 2019-08-21T19:52:25.937Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583202
;

-- 2019-08-21T19:52:25.938Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583202
;

-- 2019-08-21T19:52:25.940Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583203
;

-- 2019-08-21T19:52:25.940Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583203
;

-- 2019-08-21T19:52:25.941Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583203
;

-- 2019-08-21T19:52:25.944Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583204
;

-- 2019-08-21T19:52:25.944Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583204
;

-- 2019-08-21T19:52:25.944Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583204
;

-- 2019-08-21T19:52:25.947Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583205
;

-- 2019-08-21T19:52:25.947Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583205
;

-- 2019-08-21T19:52:25.948Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583205
;

-- 2019-08-21T19:52:25.950Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583206
;

-- 2019-08-21T19:52:25.950Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583206
;

-- 2019-08-21T19:52:25.951Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583206
;

-- 2019-08-21T19:52:25.953Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583207
;

-- 2019-08-21T19:52:25.954Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583207
;

-- 2019-08-21T19:52:25.954Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583207
;

-- 2019-08-21T19:52:25.957Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583208
;

-- 2019-08-21T19:52:25.957Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583208
;

-- 2019-08-21T19:52:25.957Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583208
;

-- 2019-08-21T19:52:25.961Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560572
;

-- 2019-08-21T19:52:25.961Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583209
;

-- 2019-08-21T19:52:25.962Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583209
;

-- 2019-08-21T19:52:25.962Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583209
;

-- 2019-08-21T19:52:25.965Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583210
;

-- 2019-08-21T19:52:25.965Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583210
;

-- 2019-08-21T19:52:25.966Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583210
;

-- 2019-08-21T19:52:25.968Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583211
;

-- 2019-08-21T19:52:25.968Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583211
;

-- 2019-08-21T19:52:25.969Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583211
;

-- 2019-08-21T19:52:25.971Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583212
;

-- 2019-08-21T19:52:25.971Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583212
;

-- 2019-08-21T19:52:25.972Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583212
;

-- 2019-08-21T19:52:25.975Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583213
;

-- 2019-08-21T19:52:25.975Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583213
;

-- 2019-08-21T19:52:25.976Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583213
;

-- 2019-08-21T19:52:25.978Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583214
;

-- 2019-08-21T19:52:25.979Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583214
;

-- 2019-08-21T19:52:25.980Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583214
;

-- 2019-08-21T19:52:25.983Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560577
;

-- 2019-08-21T19:52:25.984Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583215
;

-- 2019-08-21T19:52:25.984Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583215
;

-- 2019-08-21T19:52:25.984Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583215
;

-- 2019-08-21T19:52:25.988Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560578
;

-- 2019-08-21T19:52:25.988Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583216
;

-- 2019-08-21T19:52:25.988Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583216
;

-- 2019-08-21T19:52:25.989Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583216
;

-- 2019-08-21T19:52:25.992Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583217
;

-- 2019-08-21T19:52:25.992Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583217
;

-- 2019-08-21T19:52:25.993Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583217
;

-- 2019-08-21T19:52:25.995Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583218
;

-- 2019-08-21T19:52:25.995Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=583218
;

-- 2019-08-21T19:52:25.996Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=583218
;

-- 2019-08-21T19:52:26Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542735
;

-- 2019-08-21T19:52:26Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541809
;

-- 2019-08-21T19:52:26.001Z
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541409
;

-- 2019-08-21T19:52:26.002Z
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541409
;

-- 2019-08-21T19:52:26.002Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=541853
;

-- 2019-08-21T19:52:26.002Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=541853
;

-- 2019-08-21T19:54:43.907Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574339,541334,0,540676,TO_TIMESTAMP('2019-08-21 21:54:43','YYYY-MM-DD HH24:MI:SS'),100,'U','OrganisationStammdaten','Y','N','N','N','N','Organisation Stammdaten',TO_TIMESTAMP('2019-08-21 21:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-21T19:54:43.910Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541334 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-08-21T19:54:43.913Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541334, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541334)
;

-- 2019-08-21T19:54:43.916Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(574339) 
;

-- 2019-08-21T19:54:43.953Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=441 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.956Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=149 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.956Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50010 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.956Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=171 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.956Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=437 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.957Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53325 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.957Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=240 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.957Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=361 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:43.958Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541334 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:56.736Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540833, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541334 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:56.737Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540833, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540837 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:56.738Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540833, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540838 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:56.738Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540833, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540834 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:56.739Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540833, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540835 AND AD_Tree_ID=10
;

-- 2019-08-21T19:54:56.739Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540833, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540836 AND AD_Tree_ID=10
;

-- 2019-08-21T19:59:51.589Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-08-21 21:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541852
;

-- 2019-08-21T20:03:57.358Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=577022,Updated=TO_TIMESTAMP('2019-08-21 22:03:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540676
;

-- 2019-08-21T20:03:57.359Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(577022) 
;

-- 2019-08-21T20:03:57.361Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540676
;

-- 2019-08-21T20:03:57.362Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540676)
;

-- 2019-08-21T20:05:32.877Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='ad_orgbp_id is not null',Updated=TO_TIMESTAMP('2019-08-21 22:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541852
;

-- 2019-08-21T20:06:19.176Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='ad_orgbp_id is null',Updated=TO_TIMESTAMP('2019-08-21 22:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=220
;

-- 2019-08-21T20:06:31.187Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='ad_orgbp_id is null',Updated=TO_TIMESTAMP('2019-08-21 22:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540843
;

-- 2019-08-21T20:06:40.603Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='ad_orgbp_id is null',Updated=TO_TIMESTAMP('2019-08-21 22:06:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540871
;

-- 2019-08-21T20:06:50.784Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='ad_orgbp_id is null',Updated=TO_TIMESTAMP('2019-08-21 22:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541012
;

