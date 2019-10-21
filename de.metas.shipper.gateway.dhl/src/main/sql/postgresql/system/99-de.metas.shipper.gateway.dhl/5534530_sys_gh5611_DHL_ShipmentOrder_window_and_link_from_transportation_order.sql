-- 2019-10-17T09:51:02.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577225,0,TO_TIMESTAMP('2019-10-17 12:51:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','DHL Delivery Order','DHL Delivery Order',TO_TIMESTAMP('2019-10-17 12:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:51:02.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577225 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-17T09:53:15.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,577225,0,540743,TO_TIMESTAMP('2019-10-17 12:53:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','DHL Delivery Order','N',TO_TIMESTAMP('2019-10-17 12:53:15','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-10-17T09:53:15.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540743 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-10-17T09:53:15.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(577225) 
;

-- 2019-10-17T09:53:15.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540743
;

-- 2019-10-17T09:53:15.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540743)
;

-- 2019-10-17T09:54:15.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,577156,0,542067,541419,540743,'Y',TO_TIMESTAMP('2019-10-17 12:54:14','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','DHL_ShipmentOrder','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'DHL_ShipmetnOrder','N',10,0,TO_TIMESTAMP('2019-10-17 12:54:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:54:15.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542067 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-10-17T09:54:15.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577156) 
;

-- 2019-10-17T09:54:15.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542067)
;

-- 2019-10-17T09:54:50.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569203,589599,0,542067,0,TO_TIMESTAMP('2019-10-17 12:54:50','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','awb',10,10,0,1,1,TO_TIMESTAMP('2019-10-17 12:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:54:50.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:54:50.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577217) 
;

-- 2019-10-17T09:54:50.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589599
;

-- 2019-10-17T09:54:50.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589599)
;

-- 2019-10-17T09:57:10.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569090,589600,0,542067,0,TO_TIMESTAMP('2019-10-17 12:57:10','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',255,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',20,20,0,1,1,TO_TIMESTAMP('2019-10-17 12:57:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:57:10.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:57:10.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-10-17T09:57:10.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589600
;

-- 2019-10-17T09:57:10.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589600)
;

-- 2019-10-17T09:57:23.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569091,589601,0,542067,0,TO_TIMESTAMP('2019-10-17 12:57:23','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',255,'D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',30,30,0,1,1,TO_TIMESTAMP('2019-10-17 12:57:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:57:23.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:57:23.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2019-10-17T09:57:23.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589601
;

-- 2019-10-17T09:57:23.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589601)
;

-- 2019-10-17T09:57:52.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569094,589602,0,542067,0,TO_TIMESTAMP('2019-10-17 12:57:52','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','Kundenreferenz',40,40,0,1,1,TO_TIMESTAMP('2019-10-17 12:57:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:57:52.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:57:52.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540568) 
;

-- 2019-10-17T09:57:52.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589602
;

-- 2019-10-17T09:57:52.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589602)
;

-- 2019-10-17T09:58:13.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569093,589603,0,542067,0,TO_TIMESTAMP('2019-10-17 12:58:13','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_AccountNumber',50,50,0,1,1,TO_TIMESTAMP('2019-10-17 12:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:58:13.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:58:13.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577159) 
;

-- 2019-10-17T09:58:13.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589603
;

-- 2019-10-17T09:58:13.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589603)
;

-- 2019-10-17T09:58:39.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=569096, Description=NULL, Help=NULL, Name='DHL_HeightInCm',Updated=TO_TIMESTAMP('2019-10-17 12:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589603
;

-- 2019-10-17T09:58:39.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577161) 
;

-- 2019-10-17T09:58:39.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589603
;

-- 2019-10-17T09:58:39.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589603)
;

-- 2019-10-17T09:58:53.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569097,589604,0,542067,0,TO_TIMESTAMP('2019-10-17 12:58:53','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_LengthInCm',60,60,0,1,1,TO_TIMESTAMP('2019-10-17 12:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:58:53.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589604 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:58:53.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577162) 
;

-- 2019-10-17T09:58:53.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589604
;

-- 2019-10-17T09:58:53.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589604)
;

-- 2019-10-17T09:59:11.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569098,589605,0,542067,0,TO_TIMESTAMP('2019-10-17 12:59:11','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_WidthInCm',70,70,0,1,1,TO_TIMESTAMP('2019-10-17 12:59:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:59:11.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589605 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:59:11.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577163) 
;

-- 2019-10-17T09:59:11.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589605
;

-- 2019-10-17T09:59:11.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589605)
;

-- 2019-10-17T09:59:42.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569099,589606,0,542067,0,TO_TIMESTAMP('2019-10-17 12:59:42','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_WeightInKg',80,80,0,1,1,TO_TIMESTAMP('2019-10-17 12:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:59:42.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589606 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:59:42.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577184) 
;

-- 2019-10-17T09:59:42.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589606
;

-- 2019-10-17T09:59:42.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589606)
;

-- 2019-10-17T09:59:53.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569092,589607,0,542067,0,TO_TIMESTAMP('2019-10-17 12:59:52','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Product',90,90,0,1,1,TO_TIMESTAMP('2019-10-17 12:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T09:59:53.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589607 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T09:59:53.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577158) 
;

-- 2019-10-17T09:59:53.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589607
;

-- 2019-10-17T09:59:53.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589607)
;

-- 2019-10-17T10:01:14.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569107,589608,0,542067,0,TO_TIMESTAMP('2019-10-17 13:01:14','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_City',100,100,0,1,1,TO_TIMESTAMP('2019-10-17 13:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:01:14.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589608 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:01:14.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577172) 
;

-- 2019-10-17T10:01:14.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589608
;

-- 2019-10-17T10:01:14.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589608)
;

-- 2019-10-17T10:01:33.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569108,589609,0,542067,0,TO_TIMESTAMP('2019-10-17 13:01:33','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_Country_ID',110,110,0,1,1,TO_TIMESTAMP('2019-10-17 13:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:01:33.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589609 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:01:33.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577173) 
;

-- 2019-10-17T10:01:33.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589609
;

-- 2019-10-17T10:01:33.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589609)
;

-- 2019-10-17T10:01:45.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569198,589610,0,542067,0,TO_TIMESTAMP('2019-10-17 13:01:44','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_CountryISO3Code',120,120,0,1,1,TO_TIMESTAMP('2019-10-17 13:01:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:01:45.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589610 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:01:45.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577212) 
;

-- 2019-10-17T10:01:45.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589610
;

-- 2019-10-17T10:01:45.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589610)
;

-- 2019-10-17T10:02:00.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569109,589611,0,542067,0,TO_TIMESTAMP('2019-10-17 13:02:00','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_Email',130,130,0,1,1,TO_TIMESTAMP('2019-10-17 13:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:02:00.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:02:00.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577174) 
;

-- 2019-10-17T10:02:00.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589611
;

-- 2019-10-17T10:02:00.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589611)
;

-- 2019-10-17T10:02:10.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569101,589612,0,542067,0,TO_TIMESTAMP('2019-10-17 13:02:10','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_Name1',140,140,0,1,1,TO_TIMESTAMP('2019-10-17 13:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:02:10.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:02:10.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577166) 
;

-- 2019-10-17T10:02:10.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589612
;

-- 2019-10-17T10:02:10.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589612)
;

-- 2019-10-17T10:02:17.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569102,589613,0,542067,0,TO_TIMESTAMP('2019-10-17 13:02:16','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_Name2',150,150,0,1,1,TO_TIMESTAMP('2019-10-17 13:02:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:02:17.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:02:17.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577167) 
;

-- 2019-10-17T10:02:18.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589613
;

-- 2019-10-17T10:02:18.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589613)
;

-- 2019-10-17T10:02:32.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569110,589614,0,542067,0,TO_TIMESTAMP('2019-10-17 13:02:32','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_Phone',160,160,0,1,1,TO_TIMESTAMP('2019-10-17 13:02:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:02:32.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:02:32.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577175) 
;

-- 2019-10-17T10:02:32.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589614
;

-- 2019-10-17T10:02:32.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589614)
;

-- 2019-10-17T10:02:45.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569103,589615,0,542067,0,TO_TIMESTAMP('2019-10-17 13:02:44','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_StreetName1',170,170,0,1,1,TO_TIMESTAMP('2019-10-17 13:02:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:02:45.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:02:45.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577168) 
;

-- 2019-10-17T10:02:45.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589615
;

-- 2019-10-17T10:02:45.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589615)
;

-- 2019-10-17T10:02:53.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569104,589616,0,542067,0,TO_TIMESTAMP('2019-10-17 13:02:53','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_StreetName2',180,180,0,1,1,TO_TIMESTAMP('2019-10-17 13:02:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:02:53.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:02:53.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577169) 
;

-- 2019-10-17T10:02:53.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589616
;

-- 2019-10-17T10:02:53.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589616)
;

-- 2019-10-17T10:03:02.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569105,589617,0,542067,0,TO_TIMESTAMP('2019-10-17 13:03:01','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_StreetNumber',190,190,0,1,1,TO_TIMESTAMP('2019-10-17 13:03:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:03:02.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:03:02.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577170) 
;

-- 2019-10-17T10:03:02.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589617
;

-- 2019-10-17T10:03:02.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589617)
;

-- 2019-10-17T10:03:13.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569106,589618,0,542067,0,TO_TIMESTAMP('2019-10-17 13:03:13','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Receiver_ZipCode',200,200,0,1,1,TO_TIMESTAMP('2019-10-17 13:03:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:03:13.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:03:13.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577171) 
;

-- 2019-10-17T10:03:13.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589618
;

-- 2019-10-17T10:03:13.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589618)
;

-- 2019-10-17T10:03:38.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569100,589619,0,542067,0,TO_TIMESTAMP('2019-10-17 13:03:38','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_RecipientEmailAddress',210,210,0,1,1,TO_TIMESTAMP('2019-10-17 13:03:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:03:38.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:03:38.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577165) 
;

-- 2019-10-17T10:03:38.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589619
;

-- 2019-10-17T10:03:38.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589619)
;

-- 2019-10-17T10:03:53.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569095,589620,0,542067,0,TO_TIMESTAMP('2019-10-17 13:03:53','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_ShipmentDate',220,220,0,1,1,TO_TIMESTAMP('2019-10-17 13:03:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:03:53.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:03:53.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577160) 
;

-- 2019-10-17T10:03:53.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589620
;

-- 2019-10-17T10:03:53.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589620)
;

-- 2019-10-17T10:04:55.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569127,589621,0,542067,0,TO_TIMESTAMP('2019-10-17 13:04:55','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'N','Y','Y','N','N','N','N','N','DHL Shipment Order Request',230,230,0,1,1,TO_TIMESTAMP('2019-10-17 13:04:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:04:55.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:04:55.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577185) 
;

-- 2019-10-17T10:04:55.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589621
;

-- 2019-10-17T10:04:55.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589621)
;

-- 2019-10-17T10:05:10.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569086,589622,0,542067,0,TO_TIMESTAMP('2019-10-17 13:05:10','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'N','Y','Y','N','N','N','N','N','DHL_ShipmetnOrder',240,240,0,1,1,TO_TIMESTAMP('2019-10-17 13:05:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:05:10.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:05:10.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577156) 
;

-- 2019-10-17T10:05:10.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589622
;

-- 2019-10-17T10:05:10.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589622)
;

-- 2019-10-17T10:05:23.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569117,589623,0,542067,0,TO_TIMESTAMP('2019-10-17 13:05:23','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_City',250,250,0,1,1,TO_TIMESTAMP('2019-10-17 13:05:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:05:23.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:05:23.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577182) 
;

-- 2019-10-17T10:05:23.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589623
;

-- 2019-10-17T10:05:23.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589623)
;

-- 2019-10-17T10:05:36.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569118,589624,0,542067,0,TO_TIMESTAMP('2019-10-17 13:05:36','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_CountryISO2Code',260,260,0,1,1,TO_TIMESTAMP('2019-10-17 13:05:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:05:36.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:05:36.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577183) 
;

-- 2019-10-17T10:05:36.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589624
;

-- 2019-10-17T10:05:36.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589624)
;

-- 2019-10-17T10:06:03.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569199,589625,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_CountryISO3Code',270,270,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:03.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589625 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:03.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577213) 
;

-- 2019-10-17T10:06:03.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589625
;

-- 2019-10-17T10:06:03.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589625)
;

-- 2019-10-17T10:06:13.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569111,589626,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:13','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_Name1',280,280,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:13.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589626 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:13.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577176) 
;

-- 2019-10-17T10:06:13.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589626
;

-- 2019-10-17T10:06:13.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589626)
;

-- 2019-10-17T10:06:20.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569112,589627,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:20','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_Name2',290,290,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:20.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589627 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:20.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577177) 
;

-- 2019-10-17T10:06:20.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589627
;

-- 2019-10-17T10:06:20.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589627)
;

-- 2019-10-17T10:06:33.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569113,589628,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:33','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_StreetName1',300,300,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:33.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589628 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:33.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577178) 
;

-- 2019-10-17T10:06:33.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589628
;

-- 2019-10-17T10:06:33.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589628)
;

-- 2019-10-17T10:06:40.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569114,589629,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:39','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_StreetName2',310,310,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:40.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589629 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:40.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577179) 
;

-- 2019-10-17T10:06:40.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589629
;

-- 2019-10-17T10:06:40.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589629)
;

-- 2019-10-17T10:06:50.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569115,589630,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_StreetNumber',320,320,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:50.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589630 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:50.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577180) 
;

-- 2019-10-17T10:06:50.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589630
;

-- 2019-10-17T10:06:50.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589630)
;

-- 2019-10-17T10:06:57.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569116,589631,0,542067,0,TO_TIMESTAMP('2019-10-17 13:06:57','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','DHL_Shipper_ZipCode',330,330,0,1,1,TO_TIMESTAMP('2019-10-17 13:06:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:06:57.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589631 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:06:57.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577181) 
;

-- 2019-10-17T10:06:57.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589631
;

-- 2019-10-17T10:06:57.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589631)
;

-- 2019-10-17T10:11:32.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569219,589632,0,542067,0,TO_TIMESTAMP('2019-10-17 13:11:32','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung',255,'D','"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','Y','Y','N','N','N','N','N','Lieferweg',340,340,0,1,1,TO_TIMESTAMP('2019-10-17 13:11:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:11:32.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589632 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:11:32.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455) 
;

-- 2019-10-17T10:11:32.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589632
;

-- 2019-10-17T10:11:32.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589632)
;

-- 2019-10-17T10:11:49.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569089,589633,0,542067,0,TO_TIMESTAMP('2019-10-17 13:11:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','Package Id',350,350,0,1,1,TO_TIMESTAMP('2019-10-17 13:11:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:11:49.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:11:49.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577157) 
;

-- 2019-10-17T10:11:49.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589633
;

-- 2019-10-17T10:11:49.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589633)
;

-- 2019-10-17T10:12:57.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569202,589634,0,542067,0,TO_TIMESTAMP('2019-10-17 13:12:57','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'N','Y','Y','N','N','N','N','N','PdfLabelData',360,360,0,1,1,TO_TIMESTAMP('2019-10-17 13:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:12:57.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:12:57.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577216) 
;

-- 2019-10-17T10:12:57.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589634
;

-- 2019-10-17T10:12:57.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589634)
;

-- 2019-10-17T10:30:30.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569224,540089,0,30,541419,'M_ShipperTransportation_ID',TO_TIMESTAMP('2019-10-17 13:30:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.shipper.gateway.dhl',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Transport Auftrag',0,0,TO_TIMESTAMP('2019-10-17 13:30:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-17T10:30:30.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569224 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-17T10:30:30.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(540089) 
;

-- 2019-10-17T10:30:34.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_ShipmentOrder','ALTER TABLE public.DHL_ShipmentOrder ADD COLUMN M_ShipperTransportation_ID NUMERIC(10)')
;

-- 2019-10-17T10:30:34.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DHL_ShipmentOrder ADD CONSTRAINT MShipperTransportation_DHLShipmentOrder FOREIGN KEY (M_ShipperTransportation_ID) REFERENCES public.M_ShipperTransportation DEFERRABLE INITIALLY DEFERRED
;

-- 2019-10-17T10:33:15.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569093
;

-- 2019-10-17T10:33:15.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569093
;

-- 2019-10-17T10:34:58.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='DHL_ShipmentOrder', PrintName='DHL_ShipmentOrder',Updated=TO_TIMESTAMP('2019-10-17 13:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577156
;

-- 2019-10-17T10:34:58.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_ShipmentOrder_ID', Name='DHL_ShipmentOrder', Description=NULL, Help=NULL WHERE AD_Element_ID=577156
;

-- 2019-10-17T10:34:58.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_ShipmentOrder_ID', Name='DHL_ShipmentOrder', Description=NULL, Help=NULL, AD_Element_ID=577156 WHERE UPPER(ColumnName)='DHL_SHIPMENTORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T10:34:58.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_ShipmentOrder_ID', Name='DHL_ShipmentOrder', Description=NULL, Help=NULL WHERE AD_Element_ID=577156 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T10:34:58.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DHL_ShipmentOrder', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577156) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577156)
;

-- 2019-10-17T10:34:58.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL_ShipmentOrder', Name='DHL_ShipmentOrder' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577156)
;

-- 2019-10-17T10:34:58.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DHL_ShipmentOrder', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577156
;

-- 2019-10-17T10:34:58.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DHL_ShipmentOrder', Description=NULL, Help=NULL WHERE AD_Element_ID = 577156
;

-- 2019-10-17T10:34:58.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DHL_ShipmentOrder', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577156
;

-- 2019-10-17T10:49:17.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569224,589635,0,542067,0,TO_TIMESTAMP('2019-10-17 13:49:17','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','Transport Auftrag',370,370,0,1,1,TO_TIMESTAMP('2019-10-17 13:49:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:49:17.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589635 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T10:49:17.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2019-10-17T10:49:17.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589635
;

-- 2019-10-17T10:49:17.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589635)
;

-- 2019-10-17T10:50:19.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542067,541598,TO_TIMESTAMP('2019-10-17 13:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-17 13:50:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-10-17T10:50:19.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541598 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-10-17T10:51:13.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542035,541598,TO_TIMESTAMP('2019-10-17 13:51:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-17 13:51:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:53:17.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542036,541598,TO_TIMESTAMP('2019-10-17 13:53:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-10-17 13:53:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:53:49.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542035,543080,TO_TIMESTAMP('2019-10-17 13:53:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-10-17 13:53:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:56:39.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589635,0,542067,543080,563261,'F',TO_TIMESTAMP('2019-10-17 13:56:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Transportation Order',10,0,0,TO_TIMESTAMP('2019-10-17 13:56:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:57:01.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589632,0,542067,543080,563262,'F',TO_TIMESTAMP('2019-10-17 13:57:00','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','Y','N','N','N',0,'Shipper',20,0,0,TO_TIMESTAMP('2019-10-17 13:57:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:57:17.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542035,543081,TO_TIMESTAMP('2019-10-17 13:57:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','secondary',20,TO_TIMESTAMP('2019-10-17 13:57:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:57:47.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543081
;

-- 2019-10-17T10:58:07.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542067,541599,TO_TIMESTAMP('2019-10-17 13:58:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-10-17 13:58:07','YYYY-MM-DD HH24:MI:SS'),100,'rest')
;

-- 2019-10-17T10:58:07.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541599 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-10-17T10:58:14.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542037,541599,TO_TIMESTAMP('2019-10-17 13:58:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-17 13:58:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:58:32.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542037,543082,TO_TIMESTAMP('2019-10-17 13:58:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','everything',10,TO_TIMESTAMP('2019-10-17 13:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:59:12.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589599,0,542067,543082,563263,'F',TO_TIMESTAMP('2019-10-17 13:59:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'awb',10,0,0,TO_TIMESTAMP('2019-10-17 13:59:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:59:22.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589600,0,542067,543082,563264,'F',TO_TIMESTAMP('2019-10-17 13:59:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',20,0,0,TO_TIMESTAMP('2019-10-17 13:59:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:59:40.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589601,0,542067,543082,563265,'F',TO_TIMESTAMP('2019-10-17 13:59:40','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'c bpartner location',30,0,0,TO_TIMESTAMP('2019-10-17 13:59:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T10:59:53.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589602,0,542067,543082,563266,'F',TO_TIMESTAMP('2019-10-17 13:59:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'customer reference',40,0,0,TO_TIMESTAMP('2019-10-17 13:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:00:20.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589633,0,542067,543082,563267,'F',TO_TIMESTAMP('2019-10-17 14:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Package Id',50,0,0,TO_TIMESTAMP('2019-10-17 14:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:02:04.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,577225,541388,0,TO_TIMESTAMP('2019-10-17 14:02:04','YYYY-MM-DD HH24:MI:SS'),100,'D','dhl_delivery_order','Y','N','N','N','N','DHL Delivery Order',TO_TIMESTAMP('2019-10-17 14:02:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:02:04.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541388 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-10-17T11:02:04.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541388, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541388)
;

-- 2019-10-17T11:02:04.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(577225) 
;

-- 2019-10-17T11:02:05.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53203 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=586 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53251 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540994 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=138 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541160 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=139 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=249 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=141 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53455 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=216 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=589 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=140 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=300 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=142 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=295 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53012 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=143 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=201 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=176 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53086 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=239 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=517 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=499 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53221 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53222 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53089 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53267 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53568 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540037 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540492 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53266 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540735 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540575 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541333 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:05.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:28.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-10-17T11:02:54.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Action='W', AD_Window_ID=540743,Updated=TO_TIMESTAMP('2019-10-17 14:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541388
;

-- 2019-10-17T11:05:33.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569079,589636,0,542067,TO_TIMESTAMP('2019-10-17 14:05:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-10-17 14:05:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:05:33.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589636 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T11:05:33.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-10-17T11:05:33.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589636
;

-- 2019-10-17T11:05:33.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589636)
;

-- 2019-10-17T11:05:33.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569080,589637,0,542067,TO_TIMESTAMP('2019-10-17 14:05:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-10-17 14:05:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:05:33.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T11:05:33.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-10-17T11:05:34.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589637
;

-- 2019-10-17T11:05:34.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589637)
;

-- 2019-10-17T11:05:34.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569083,589638,0,542067,TO_TIMESTAMP('2019-10-17 14:05:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-10-17 14:05:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:05:34.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-17T11:05:34.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-10-17T11:05:34.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589638
;

-- 2019-10-17T11:05:34.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589638)
;

-- 2019-10-17T11:06:40.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-17 14:06:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589622
;

-- 2019-10-17T11:06:43.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-17 14:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589621
;

-- 2019-10-17T11:06:44.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-17 14:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589634
;

-- 2019-10-17T11:10:54.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542038,541599,TO_TIMESTAMP('2019-10-17 14:10:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-10-17 14:10:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:12:16.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542036,543083,TO_TIMESTAMP('2019-10-17 14:12:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','a',10,TO_TIMESTAMP('2019-10-17 14:12:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:12:28.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589599,0,542067,543083,563268,'F',TO_TIMESTAMP('2019-10-17 14:12:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'awb',10,0,0,TO_TIMESTAMP('2019-10-17 14:12:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:12:36.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589600,0,542067,543083,563269,'F',TO_TIMESTAMP('2019-10-17 14:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',20,0,0,TO_TIMESTAMP('2019-10-17 14:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:12:43.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589601,0,542067,543083,563270,'F',TO_TIMESTAMP('2019-10-17 14:12:43','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',30,0,0,TO_TIMESTAMP('2019-10-17 14:12:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:12:58.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589602,0,542067,543083,563271,'F',TO_TIMESTAMP('2019-10-17 14:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kundenreferenz',40,0,0,TO_TIMESTAMP('2019-10-17 14:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:13:06.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589633,0,542067,543083,563272,'F',TO_TIMESTAMP('2019-10-17 14:13:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Package Id',50,0,0,TO_TIMESTAMP('2019-10-17 14:13:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:13:36.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563263
;

-- 2019-10-17T11:13:42.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563264
;

-- 2019-10-17T11:13:52.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563265
;

-- 2019-10-17T11:13:55.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563266
;

-- 2019-10-17T11:13:57.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563267
;

-- 2019-10-17T11:14:51.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589623,0,542067,543082,563273,'F',TO_TIMESTAMP('2019-10-17 14:14:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_City',10,0,0,TO_TIMESTAMP('2019-10-17 14:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:00.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589624,0,542067,543082,563274,'F',TO_TIMESTAMP('2019-10-17 14:15:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_CountryISO2Code',20,0,0,TO_TIMESTAMP('2019-10-17 14:15:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:10.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589625,0,542067,543082,563275,'F',TO_TIMESTAMP('2019-10-17 14:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_CountryISO3Code',30,0,0,TO_TIMESTAMP('2019-10-17 14:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:18.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589626,0,542067,543082,563276,'F',TO_TIMESTAMP('2019-10-17 14:15:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_Name1',40,0,0,TO_TIMESTAMP('2019-10-17 14:15:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:24.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589627,0,542067,543082,563277,'F',TO_TIMESTAMP('2019-10-17 14:15:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_Name2',50,0,0,TO_TIMESTAMP('2019-10-17 14:15:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:33.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589628,0,542067,543082,563278,'F',TO_TIMESTAMP('2019-10-17 14:15:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_StreetName1',60,0,0,TO_TIMESTAMP('2019-10-17 14:15:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:41.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589629,0,542067,543082,563279,'F',TO_TIMESTAMP('2019-10-17 14:15:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_StreetName2',70,0,0,TO_TIMESTAMP('2019-10-17 14:15:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:48.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589630,0,542067,543082,563280,'F',TO_TIMESTAMP('2019-10-17 14:15:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_StreetNumber',80,0,0,TO_TIMESTAMP('2019-10-17 14:15:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:15:56.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589631,0,542067,543082,563281,'F',TO_TIMESTAMP('2019-10-17 14:15:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Shipper_ZipCode',90,0,0,TO_TIMESTAMP('2019-10-17 14:15:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:16:21.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542038,543084,TO_TIMESTAMP('2019-10-17 14:16:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','receiver',10,TO_TIMESTAMP('2019-10-17 14:16:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:16:30.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='shipper',Updated=TO_TIMESTAMP('2019-10-17 14:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543082
;

-- 2019-10-17T11:17:00.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589608,0,542067,543084,563282,'F',TO_TIMESTAMP('2019-10-17 14:17:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_City',10,0,0,TO_TIMESTAMP('2019-10-17 14:17:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:17:20.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589610,0,542067,543084,563283,'F',TO_TIMESTAMP('2019-10-17 14:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_CountryISO3Code',20,0,0,TO_TIMESTAMP('2019-10-17 14:17:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:17:27Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589611,0,542067,543084,563284,'F',TO_TIMESTAMP('2019-10-17 14:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_Email',30,0,0,TO_TIMESTAMP('2019-10-17 14:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:17:34.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589612,0,542067,543084,563285,'F',TO_TIMESTAMP('2019-10-17 14:17:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_Name1',40,0,0,TO_TIMESTAMP('2019-10-17 14:17:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:17:45.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589613,0,542067,543084,563286,'F',TO_TIMESTAMP('2019-10-17 14:17:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_Name2',50,0,0,TO_TIMESTAMP('2019-10-17 14:17:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:17:54.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589614,0,542067,543084,563287,'F',TO_TIMESTAMP('2019-10-17 14:17:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_Phone',60,0,0,TO_TIMESTAMP('2019-10-17 14:17:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:18:01.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589615,0,542067,543084,563288,'F',TO_TIMESTAMP('2019-10-17 14:18:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_StreetName1',70,0,0,TO_TIMESTAMP('2019-10-17 14:18:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:18:08.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589616,0,542067,543084,563289,'F',TO_TIMESTAMP('2019-10-17 14:18:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_StreetName2',80,0,0,TO_TIMESTAMP('2019-10-17 14:18:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:18:15.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589617,0,542067,543084,563290,'F',TO_TIMESTAMP('2019-10-17 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_StreetNumber',90,0,0,TO_TIMESTAMP('2019-10-17 14:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:18:23.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589618,0,542067,543084,563291,'F',TO_TIMESTAMP('2019-10-17 14:18:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_ZipCode',100,0,0,TO_TIMESTAMP('2019-10-17 14:18:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:21:19.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', PrintName='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173
;

-- 2019-10-17T11:21:19.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE AD_Element_ID=577173
;

-- 2019-10-17T11:21:19.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL, AD_Element_ID=577173 WHERE UPPER(ColumnName)='DHL_RECEIVER_COUNTRYISO2CODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T11:21:19.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE AD_Element_ID=577173 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T11:21:19.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577173) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577173)
;

-- 2019-10-17T11:21:19.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577173)
;

-- 2019-10-17T11:21:19.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T11:21:19.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T11:21:19.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DHL_Receiver_CountryISO2Code', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T11:21:25.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHL_Receiver_CountryISO2Code', PrintName='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_CH'
;

-- 2019-10-17T11:21:25.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_CH') 
;

-- 2019-10-17T11:21:27.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_DE'
;

-- 2019-10-17T11:21:27.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_DE') 
;

-- 2019-10-17T11:21:27.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577173,'de_DE') 
;

-- 2019-10-17T11:21:27.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_Country_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577173)
;

-- 2019-10-17T11:21:27.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='en_US'
;

-- 2019-10-17T11:21:27.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'en_US') 
;

-- 2019-10-17T11:21:29.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='nl_NL'
;

-- 2019-10-17T11:21:29.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'nl_NL') 
;

-- 2019-10-17T11:21:30.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='en_US'
;

-- 2019-10-17T11:21:30.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'en_US') 
;

-- 2019-10-17T11:21:32.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_DE'
;

-- 2019-10-17T11:21:32.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_DE') 
;

-- 2019-10-17T11:21:32.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577173,'de_DE') 
;

-- 2019-10-17T11:21:32.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE AD_Element_ID=577173
;

-- 2019-10-17T11:21:32.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL, AD_Element_ID=577173 WHERE UPPER(ColumnName)='DHL_RECEIVER_COUNTRYISO2CODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T11:21:32.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE AD_Element_ID=577173 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T11:21:32.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577173) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577173)
;

-- 2019-10-17T11:21:32.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL_Receiver_CountryISO2Code', Name='DHL_Receiver_CountryISO2Code' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577173)
;

-- 2019-10-17T11:21:32.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T11:21:32.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DHL_Receiver_CountryISO2Code', Description=NULL, Help=NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T11:21:32.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DHL_Receiver_CountryISO2Code', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T11:21:44.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='nl_NL'
;

-- 2019-10-17T11:21:44.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'nl_NL') 
;

-- 2019-10-17T11:24:15.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589609,0,542067,543084,563292,'F',TO_TIMESTAMP('2019-10-17 14:24:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Receiver_CountryISO2Code',110,0,0,TO_TIMESTAMP('2019-10-17 14:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:24:36.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=19,Updated=TO_TIMESTAMP('2019-10-17 14:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563292
;

-- 2019-10-17T11:32:04.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589620,0,542067,543083,563293,'F',TO_TIMESTAMP('2019-10-17 14:32:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_ShipmentDate',60,0,0,TO_TIMESTAMP('2019-10-17 14:32:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:32:32.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589607,0,542067,543083,563294,'F',TO_TIMESTAMP('2019-10-17 14:32:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_Product',70,0,0,TO_TIMESTAMP('2019-10-17 14:32:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:34:07.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-17 14:34:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563283
;

-- 2019-10-17T11:34:17.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-17 14:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563275
;

-- 2019-10-17T11:36:40.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542035,543085,TO_TIMESTAMP('2019-10-17 14:36:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','dimensions',20,TO_TIMESTAMP('2019-10-17 14:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:36:48.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589604,0,542067,543085,563295,'F',TO_TIMESTAMP('2019-10-17 14:36:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_LengthInCm',10,0,0,TO_TIMESTAMP('2019-10-17 14:36:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:36:56.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589605,0,542067,543085,563296,'F',TO_TIMESTAMP('2019-10-17 14:36:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_WidthInCm',20,0,0,TO_TIMESTAMP('2019-10-17 14:36:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:37:02.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589603,0,542067,543085,563297,'F',TO_TIMESTAMP('2019-10-17 14:37:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_HeightInCm',30,0,0,TO_TIMESTAMP('2019-10-17 14:37:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:37:09.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589606,0,542067,543085,563298,'F',TO_TIMESTAMP('2019-10-17 14:37:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL_WeightInKg',40,0,0,TO_TIMESTAMP('2019-10-17 14:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T11:38:17.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=244, Description='Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html', Help='Für Details - http://www.din.de/gremien/nas/nabd/iso3166ma/codlstp1.html oder - http://www.unece.org/trade/rec/rec03en.htm', Name='ISO Ländercode',Updated=TO_TIMESTAMP('2019-10-17 14:38:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589609
;

-- 2019-10-17T11:38:17.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(244) 
;

-- 2019-10-17T11:38:17.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589609
;

-- 2019-10-17T11:38:17.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589609)
;

-- 2019-10-17T11:39:35.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=NULL, Description=NULL, Help=NULL, Name='DHL_Receiver_CountryISO2Code',Updated=TO_TIMESTAMP('2019-10-17 14:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589609
;

-- 2019-10-17T11:39:35.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577173) 
;

-- 2019-10-17T11:39:35.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589609
;

-- 2019-10-17T11:39:35.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589609)
;

-- 2019-10-17T11:41:51.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 14:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577225 AND AD_Language='en_US'
;

-- 2019-10-17T11:41:51.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577225,'en_US') 
;

-- 2019-10-17T11:41:54.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHL Versandauftrag', PrintName='DHL Versandauftrag',Updated=TO_TIMESTAMP('2019-10-17 14:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577225 AND AD_Language='de_DE'
;

-- 2019-10-17T11:41:54.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577225,'de_DE') 
;

-- 2019-10-17T11:41:54.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577225,'de_DE') 
;

-- 2019-10-17T11:41:54.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='DHL Versandauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=577225
;

-- 2019-10-17T11:41:54.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='DHL Versandauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=577225 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T11:41:54.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DHL Versandauftrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577225) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577225)
;

-- 2019-10-17T11:41:54.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL Versandauftrag', Name='DHL Versandauftrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577225)
;

-- 2019-10-17T11:41:54.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DHL Versandauftrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577225
;

-- 2019-10-17T11:41:54.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DHL Versandauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 577225
;

-- 2019-10-17T11:41:54.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'DHL Versandauftrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577225
;

-- 2019-10-17T11:41:57.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHL Versandauftrag', PrintName='DHL Versandauftrag',Updated=TO_TIMESTAMP('2019-10-17 14:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577225 AND AD_Language='de_CH'
;

-- 2019-10-17T11:41:57.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577225,'de_CH') 
;

-- 2019-10-17T11:42:06.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 14:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577225 AND AD_Language='de_CH'
;

-- 2019-10-17T11:42:06.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577225,'de_CH') 
;

-- 2019-10-17T11:42:07.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 14:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577225 AND AD_Language='de_DE'
;

-- 2019-10-17T11:42:07.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577225,'de_DE') 
;

-- 2019-10-17T11:42:07.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577225,'de_DE') 
;

-- 2019-10-17T12:16:25.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Awb (Air Waybill)', PrintName='Awb (Air Waybill)',Updated=TO_TIMESTAMP('2019-10-17 15:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='en_US'
;

-- 2019-10-17T12:16:25.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'en_US') 
;

-- 2019-10-17T12:16:26.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Luftfrachtbrief',Updated=TO_TIMESTAMP('2019-10-17 15:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_DE'
;

-- 2019-10-17T12:16:26.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_DE') 
;

-- 2019-10-17T12:16:26.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577217,'de_DE') 
;

-- 2019-10-17T12:16:26.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Luftfrachtbrief', Name='awb' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577217)
;

-- 2019-10-17T12:16:28.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='awbLuftfrachtbrief', PrintName='Luftfrachtbrief',Updated=TO_TIMESTAMP('2019-10-17 15:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_CH'
;

-- 2019-10-17T12:16:28.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_CH') 
;

-- 2019-10-17T12:16:29.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Luftfrachtbrief',Updated=TO_TIMESTAMP('2019-10-17 15:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_DE'
;

-- 2019-10-17T12:16:29.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_DE') 
;

-- 2019-10-17T12:16:29.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577217,'de_DE') 
;

-- 2019-10-17T12:16:29.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='awb', Name='Luftfrachtbrief', Description=NULL, Help=NULL WHERE AD_Element_ID=577217
;

-- 2019-10-17T12:16:29.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='awb', Name='Luftfrachtbrief', Description=NULL, Help=NULL, AD_Element_ID=577217 WHERE UPPER(ColumnName)='AWB' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:16:29.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='awb', Name='Luftfrachtbrief', Description=NULL, Help=NULL WHERE AD_Element_ID=577217 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:16:29.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Luftfrachtbrief', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577217) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577217)
;

-- 2019-10-17T12:16:29.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Luftfrachtbrief', Name='Luftfrachtbrief' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577217)
;

-- 2019-10-17T12:16:29.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Luftfrachtbrief', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577217
;

-- 2019-10-17T12:16:29.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Luftfrachtbrief', Description=NULL, Help=NULL WHERE AD_Element_ID = 577217
;

-- 2019-10-17T12:16:29.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Luftfrachtbrief', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577217
;

-- 2019-10-17T12:16:30.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='en_US'
;

-- 2019-10-17T12:16:30.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'en_US') 
;

-- 2019-10-17T12:16:30.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_DE'
;

-- 2019-10-17T12:16:30.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_DE') 
;

-- 2019-10-17T12:16:30.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577217,'de_DE') 
;

-- 2019-10-17T12:16:35.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Luftfrachtbrief',Updated=TO_TIMESTAMP('2019-10-17 15:16:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577217 AND AD_Language='de_CH'
;

-- 2019-10-17T12:16:35.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577217,'de_CH') 
;

-- 2019-10-17T12:18:01.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Height in CM', PrintName='Height in CM',Updated=TO_TIMESTAMP('2019-10-17 15:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577161 AND AD_Language='en_US'
;

-- 2019-10-17T12:18:01.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577161,'en_US') 
;

-- 2019-10-17T12:18:03.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Höhe in cm', PrintName='Höhe in cm',Updated=TO_TIMESTAMP('2019-10-17 15:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577161 AND AD_Language='de_DE'
;

-- 2019-10-17T12:18:03.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577161,'de_DE') 
;

-- 2019-10-17T12:18:03.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577161,'de_DE') 
;

-- 2019-10-17T12:18:03.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_HeightInCm', Name='Höhe in cm', Description=NULL, Help=NULL WHERE AD_Element_ID=577161
;

-- 2019-10-17T12:18:03.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_HeightInCm', Name='Höhe in cm', Description=NULL, Help=NULL, AD_Element_ID=577161 WHERE UPPER(ColumnName)='DHL_HEIGHTINCM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:18:03.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_HeightInCm', Name='Höhe in cm', Description=NULL, Help=NULL WHERE AD_Element_ID=577161 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:18:03.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Höhe in cm', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577161) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577161)
;

-- 2019-10-17T12:18:03.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Höhe in cm', Name='Höhe in cm' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577161)
;

-- 2019-10-17T12:18:03.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Höhe in cm', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577161
;

-- 2019-10-17T12:18:03.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Höhe in cm', Description=NULL, Help=NULL WHERE AD_Element_ID = 577161
;

-- 2019-10-17T12:18:03.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Höhe in cm', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577161
;

-- 2019-10-17T12:18:06.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Höhe in cm', PrintName='Höhe in cm',Updated=TO_TIMESTAMP('2019-10-17 15:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577161 AND AD_Language='de_CH'
;

-- 2019-10-17T12:18:06.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577161,'de_CH') 
;

-- 2019-10-17T12:18:06.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577161 AND AD_Language='de_DE'
;

-- 2019-10-17T12:18:06.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577161,'de_DE') 
;

-- 2019-10-17T12:18:06.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577161,'de_DE') 
;

-- 2019-10-17T12:18:08.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577161 AND AD_Language='de_CH'
;

-- 2019-10-17T12:18:08.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577161,'de_CH') 
;

-- 2019-10-17T12:18:36.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Length in CM',Updated=TO_TIMESTAMP('2019-10-17 15:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='en_US'
;

-- 2019-10-17T12:18:36.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'en_US') 
;

-- 2019-10-17T12:18:38.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Länge in cm',Updated=TO_TIMESTAMP('2019-10-17 15:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='de_DE'
;

-- 2019-10-17T12:18:38.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'de_DE') 
;

-- 2019-10-17T12:18:38.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577162,'de_DE') 
;

-- 2019-10-17T12:18:38.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_LengthInCm', Name='Länge in cm', Description=NULL, Help=NULL WHERE AD_Element_ID=577162
;

-- 2019-10-17T12:18:38.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_LengthInCm', Name='Länge in cm', Description=NULL, Help=NULL, AD_Element_ID=577162 WHERE UPPER(ColumnName)='DHL_LENGTHINCM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:18:38.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_LengthInCm', Name='Länge in cm', Description=NULL, Help=NULL WHERE AD_Element_ID=577162 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:18:38.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Länge in cm', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577162) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577162)
;

-- 2019-10-17T12:18:38.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL_LengthInCm', Name='Länge in cm' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577162)
;

-- 2019-10-17T12:18:38.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Länge in cm', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577162
;

-- 2019-10-17T12:18:38.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Länge in cm', Description=NULL, Help=NULL WHERE AD_Element_ID = 577162
;

-- 2019-10-17T12:18:38.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Länge in cm', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577162
;

-- 2019-10-17T12:18:39.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Länge in cm', PrintName='Länge in cm',Updated=TO_TIMESTAMP('2019-10-17 15:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='de_CH'
;

-- 2019-10-17T12:18:39.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'de_CH') 
;

-- 2019-10-17T12:18:40.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Länge in cm',Updated=TO_TIMESTAMP('2019-10-17 15:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='de_DE'
;

-- 2019-10-17T12:18:40.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'de_DE') 
;

-- 2019-10-17T12:18:40.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577162,'de_DE') 
;

-- 2019-10-17T12:18:40.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Länge in cm', Name='Länge in cm' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577162)
;

-- 2019-10-17T12:18:44.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Length in CM',Updated=TO_TIMESTAMP('2019-10-17 15:18:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='en_US'
;

-- 2019-10-17T12:18:44.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'en_US') 
;

-- 2019-10-17T12:18:45.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='de_DE'
;

-- 2019-10-17T12:18:45.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'de_DE') 
;

-- 2019-10-17T12:18:45.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577162,'de_DE') 
;

-- 2019-10-17T12:18:46.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:18:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='de_CH'
;

-- 2019-10-17T12:18:46.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'de_CH') 
;

-- 2019-10-17T12:18:58.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Length in cm', PrintName='Length in cm',Updated=TO_TIMESTAMP('2019-10-17 15:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577162 AND AD_Language='en_US'
;

-- 2019-10-17T12:18:58.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577162,'en_US') 
;

-- 2019-10-17T12:19:11.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Height in cm', PrintName='Height in cm',Updated=TO_TIMESTAMP('2019-10-17 15:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577161 AND AD_Language='en_US'
;

-- 2019-10-17T12:19:11.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577161,'en_US') 
;

-- 2019-10-17T12:19:33.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Product', PrintName='Product',Updated=TO_TIMESTAMP('2019-10-17 15:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='en_US'
;

-- 2019-10-17T12:19:33.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'en_US') 
;

-- 2019-10-17T12:19:34.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Produkt',Updated=TO_TIMESTAMP('2019-10-17 15:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='de_DE'
;

-- 2019-10-17T12:19:34.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'de_DE') 
;

-- 2019-10-17T12:19:34.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577158,'de_DE') 
;

-- 2019-10-17T12:19:34.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt', Name='DHL_Product' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577158)
;

-- 2019-10-17T12:19:36.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='DHProduktL_Product', PrintName='Produkt',Updated=TO_TIMESTAMP('2019-10-17 15:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='de_CH'
;

-- 2019-10-17T12:19:36.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'de_CH') 
;

-- 2019-10-17T12:19:39.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt',Updated=TO_TIMESTAMP('2019-10-17 15:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='de_CH'
;

-- 2019-10-17T12:19:39.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'de_CH') 
;

-- 2019-10-17T12:19:41.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produkt',Updated=TO_TIMESTAMP('2019-10-17 15:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='de_DE'
;

-- 2019-10-17T12:19:41.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'de_DE') 
;

-- 2019-10-17T12:19:41.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577158,'de_DE') 
;

-- 2019-10-17T12:19:41.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Product', Name='Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577158
;

-- 2019-10-17T12:19:41.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Product', Name='Produkt', Description=NULL, Help=NULL, AD_Element_ID=577158 WHERE UPPER(ColumnName)='DHL_PRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:19:41.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Product', Name='Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577158 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:19:41.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577158) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577158)
;

-- 2019-10-17T12:19:41.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt', Name='Produkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577158)
;

-- 2019-10-17T12:19:41.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577158
;

-- 2019-10-17T12:19:41.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577158
;

-- 2019-10-17T12:19:41.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577158
;

-- 2019-10-17T12:19:41.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='de_CH'
;

-- 2019-10-17T12:19:41.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'de_CH') 
;

-- 2019-10-17T12:19:42.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577158 AND AD_Language='en_US'
;

-- 2019-10-17T12:19:42.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577158,'en_US') 
;

-- 2019-10-17T12:20:09.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Receiver City', PrintName='Receiver City',Updated=TO_TIMESTAMP('2019-10-17 15:20:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577172 AND AD_Language='en_US'
;

-- 2019-10-17T12:20:09.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577172,'en_US') 
;

-- 2019-10-17T12:20:10.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfängerort',Updated=TO_TIMESTAMP('2019-10-17 15:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577172 AND AD_Language='de_DE'
;

-- 2019-10-17T12:20:10.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577172,'de_DE') 
;

-- 2019-10-17T12:20:10.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577172,'de_DE') 
;

-- 2019-10-17T12:20:10.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_City', Name='Empfängerort', Description=NULL, Help=NULL WHERE AD_Element_ID=577172
;

-- 2019-10-17T12:20:10.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_City', Name='Empfängerort', Description=NULL, Help=NULL, AD_Element_ID=577172 WHERE UPPER(ColumnName)='DHL_RECEIVER_CITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:20:10.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_City', Name='Empfängerort', Description=NULL, Help=NULL WHERE AD_Element_ID=577172 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:20:10.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfängerort', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577172)
;

-- 2019-10-17T12:20:10.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DHL_Receiver_City', Name='Empfängerort' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577172)
;

-- 2019-10-17T12:20:10.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Empfängerort', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577172
;

-- 2019-10-17T12:20:10.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Empfängerort', Description=NULL, Help=NULL WHERE AD_Element_ID = 577172
;

-- 2019-10-17T12:20:10.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Empfängerort', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577172
;

-- 2019-10-17T12:20:11.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfängerort', PrintName='Empfängerort',Updated=TO_TIMESTAMP('2019-10-17 15:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577172 AND AD_Language='de_CH'
;

-- 2019-10-17T12:20:11.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577172,'de_CH') 
;

-- 2019-10-17T12:20:14.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Empfängerort',Updated=TO_TIMESTAMP('2019-10-17 15:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577172 AND AD_Language='de_DE'
;

-- 2019-10-17T12:20:14.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577172,'de_DE') 
;

-- 2019-10-17T12:20:14.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577172,'de_DE') 
;

-- 2019-10-17T12:20:14.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Empfängerort', Name='Empfängerort' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577172)
;

-- 2019-10-17T12:20:14.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577172 AND AD_Language='de_CH'
;

-- 2019-10-17T12:20:14.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577172,'de_CH') 
;

-- 2019-10-17T12:20:16.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577172 AND AD_Language='en_US'
;

-- 2019-10-17T12:20:16.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577172,'en_US') 
;

-- 2019-10-17T12:21:06.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Country Code ISO-2', PrintName='Country Code ISO-2',Updated=TO_TIMESTAMP('2019-10-17 15:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='en_US'
;

-- 2019-10-17T12:21:06.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'en_US') 
;

-- 2019-10-17T12:21:10.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfänger Ländercode (ISO-2)', PrintName='Empfänger Ländercode (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_DE'
;

-- 2019-10-17T12:21:10.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_DE') 
;

-- 2019-10-17T12:21:10.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577173,'de_DE') 
;

-- 2019-10-17T12:21:10.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_CountryISO2Code', Name='Empfänger Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE AD_Element_ID=577173
;

-- 2019-10-17T12:21:10.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO2Code', Name='Empfänger Ländercode (ISO-2)', Description=NULL, Help=NULL, AD_Element_ID=577173 WHERE UPPER(ColumnName)='DHL_RECEIVER_COUNTRYISO2CODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:21:10.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO2Code', Name='Empfänger Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE AD_Element_ID=577173 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:21:10.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfänger Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577173) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577173)
;

-- 2019-10-17T12:21:10.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Empfänger Ländercode (ISO-2)', Name='Empfänger Ländercode (ISO-2)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577173)
;

-- 2019-10-17T12:21:10.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Empfänger Ländercode (ISO-2)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T12:21:10.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Empfänger Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T12:21:10.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Empfänger Ländercode (ISO-2)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577173
;

-- 2019-10-17T12:21:12.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfänger Ländercode (ISO-2)', PrintName='Empfänger Ländercode (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_CH'
;

-- 2019-10-17T12:21:12.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_CH') 
;

-- 2019-10-17T12:21:24.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Country Code (ISO-2)', PrintName='Country Code (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='en_US'
;

-- 2019-10-17T12:21:24.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'en_US') 
;

-- 2019-10-17T12:21:24.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_DE'
;

-- 2019-10-17T12:21:24.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_DE') 
;

-- 2019-10-17T12:21:24.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577173,'de_DE') 
;

-- 2019-10-17T12:21:27.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='de_CH'
;

-- 2019-10-17T12:21:27.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'de_CH') 
;

-- 2019-10-17T12:21:55.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Country Code (ISO-3)', PrintName='Country Code (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577212 AND AD_Language='en_US'
;

-- 2019-10-17T12:21:55.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577212,'en_US') 
;

-- 2019-10-17T12:21:56.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Empfänger Ländercode (ISO-3)', PrintName='Empfänger Ländercode (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577212 AND AD_Language='de_DE'
;

-- 2019-10-17T12:21:56.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577212,'de_DE') 
;

-- 2019-10-17T12:21:56.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577212,'de_DE') 
;

-- 2019-10-17T12:21:56.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_CountryISO3Code', Name='Empfänger Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE AD_Element_ID=577212
;

-- 2019-10-17T12:21:56.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO3Code', Name='Empfänger Ländercode (ISO-3)', Description=NULL, Help=NULL, AD_Element_ID=577212 WHERE UPPER(ColumnName)='DHL_RECEIVER_COUNTRYISO3CODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:21:56.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_CountryISO3Code', Name='Empfänger Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE AD_Element_ID=577212 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:21:56.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Empfänger Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577212) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577212)
;

-- 2019-10-17T12:21:57.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Empfänger Ländercode (ISO-3)', Name='Empfänger Ländercode (ISO-3)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577212)
;

-- 2019-10-17T12:21:57.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Empfänger Ländercode (ISO-3)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577212
;

-- 2019-10-17T12:21:57.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Empfänger Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577212
;

-- 2019-10-17T12:21:57.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Empfänger Ländercode (ISO-3)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577212
;

-- 2019-10-17T12:21:59.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Empfänger Ländercode (ISO-3)', PrintName='Empfänger Ländercode (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:21:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577212 AND AD_Language='de_CH'
;

-- 2019-10-17T12:21:59.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577212,'de_CH') 
;

-- 2019-10-17T12:21:59.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:21:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577212 AND AD_Language='de_DE'
;

-- 2019-10-17T12:21:59.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577212,'de_DE') 
;

-- 2019-10-17T12:21:59.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577212,'de_DE') 
;

-- 2019-10-17T12:22:00.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577212 AND AD_Language='en_US'
;

-- 2019-10-17T12:22:00.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577212,'en_US') 
;

-- 2019-10-17T12:22:29.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Receiver Email',Updated=TO_TIMESTAMP('2019-10-17 15:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577174 AND AD_Language='en_US'
;

-- 2019-10-17T12:22:29.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577174,'en_US') 
;

-- 2019-10-17T12:22:32.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='E-Mail Empfänger', PrintName='E-Mail Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:22:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577174 AND AD_Language='de_CH'
;

-- 2019-10-17T12:22:32.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577174,'de_CH') 
;

-- 2019-10-17T12:22:35.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='E-Mail Empfänger', PrintName='E-Mail Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577174 AND AD_Language='de_DE'
;

-- 2019-10-17T12:22:35.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577174,'de_DE') 
;

-- 2019-10-17T12:22:35.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577174,'de_DE') 
;

-- 2019-10-17T12:22:35.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_Email', Name='E-Mail Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577174
;

-- 2019-10-17T12:22:35.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Email', Name='E-Mail Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577174 WHERE UPPER(ColumnName)='DHL_RECEIVER_EMAIL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:22:35.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Email', Name='E-Mail Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577174 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:22:35.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='E-Mail Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577174) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577174)
;

-- 2019-10-17T12:22:35.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='E-Mail Empfänger', Name='E-Mail Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577174)
;

-- 2019-10-17T12:22:35.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='E-Mail Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577174
;

-- 2019-10-17T12:22:35.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='E-Mail Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577174
;

-- 2019-10-17T12:22:35.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'E-Mail Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577174
;

-- 2019-10-17T12:22:35.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577174 AND AD_Language='de_CH'
;

-- 2019-10-17T12:22:35.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577174,'de_CH') 
;

-- 2019-10-17T12:22:40.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Receiver Email',Updated=TO_TIMESTAMP('2019-10-17 15:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577174 AND AD_Language='en_US'
;

-- 2019-10-17T12:22:40.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577174,'en_US') 
;

-- 2019-10-17T12:23:03.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name 1 Empfänger', PrintName='Name 1 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577166 AND AD_Language='de_CH'
;

-- 2019-10-17T12:23:03.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577166,'de_CH') 
;

-- 2019-10-17T12:23:07.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name 1 Empfänger', PrintName='Name 1 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:23:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577166 AND AD_Language='de_DE'
;

-- 2019-10-17T12:23:07.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577166,'de_DE') 
;

-- 2019-10-17T12:23:07.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577166,'de_DE') 
;

-- 2019-10-17T12:23:07.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_Name1', Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577166
;

-- 2019-10-17T12:23:07.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Name1', Name='Name 1 Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577166 WHERE UPPER(ColumnName)='DHL_RECEIVER_NAME1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:23:07.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Name1', Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577166 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:23:07.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577166) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577166)
;

-- 2019-10-17T12:23:07.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name 1 Empfänger', Name='Name 1 Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577166)
;

-- 2019-10-17T12:23:07.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name 1 Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577166
;

-- 2019-10-17T12:23:07.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577166
;

-- 2019-10-17T12:23:07.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name 1 Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577166
;

-- 2019-10-17T12:23:21.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receiver Name 1', PrintName='Receiver Name 1',Updated=TO_TIMESTAMP('2019-10-17 15:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577166 AND AD_Language='en_US'
;

-- 2019-10-17T12:23:21.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577166,'en_US') 
;

-- 2019-10-17T12:23:33.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name 2 Empfänger', PrintName='Name 2 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577167 AND AD_Language='de_CH'
;

-- 2019-10-17T12:23:33.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577167,'de_CH') 
;

-- 2019-10-17T12:23:37.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Name 2 Empfänger', PrintName='Name 2 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577167 AND AD_Language='de_DE'
;

-- 2019-10-17T12:23:37.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577167,'de_DE') 
;

-- 2019-10-17T12:23:37.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577167,'de_DE') 
;

-- 2019-10-17T12:23:37.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_Name2', Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577167
;

-- 2019-10-17T12:23:37.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Name2', Name='Name 2 Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577167 WHERE UPPER(ColumnName)='DHL_RECEIVER_NAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:23:37.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Name2', Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577167 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:23:37.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577167) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577167)
;

-- 2019-10-17T12:23:37.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Name 2 Empfänger', Name='Name 2 Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577167)
;

-- 2019-10-17T12:23:37.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name 2 Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577167
;

-- 2019-10-17T12:23:37.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577167
;

-- 2019-10-17T12:23:37.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name 2 Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577167
;

-- 2019-10-17T12:23:46.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receiver Name 2', PrintName='Receiver Name 2',Updated=TO_TIMESTAMP('2019-10-17 15:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577167 AND AD_Language='en_US'
;

-- 2019-10-17T12:23:46.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577167,'en_US') 
;

-- 2019-10-17T12:24:04.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Telefon Empfänger', PrintName='Telefon Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577175 AND AD_Language='de_CH'
;

-- 2019-10-17T12:24:04.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577175,'de_CH') 
;

-- 2019-10-17T12:24:07.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Telefon Empfänger', PrintName='Telefon Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577175 AND AD_Language='de_DE'
;

-- 2019-10-17T12:24:07.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577175,'de_DE') 
;

-- 2019-10-17T12:24:07.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577175,'de_DE') 
;

-- 2019-10-17T12:24:07.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_Phone', Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577175
;

-- 2019-10-17T12:24:07.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Phone', Name='Telefon Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577175 WHERE UPPER(ColumnName)='DHL_RECEIVER_PHONE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:24:07.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_Phone', Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577175 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:24:07.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577175) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577175)
;

-- 2019-10-17T12:24:07.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Telefon Empfänger', Name='Telefon Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577175)
;

-- 2019-10-17T12:24:07.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Telefon Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577175
;

-- 2019-10-17T12:24:07.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Telefon Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577175
;

-- 2019-10-17T12:24:07.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Telefon Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577175
;

-- 2019-10-17T12:24:16.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receiver Phone', PrintName='Receiver Phone',Updated=TO_TIMESTAMP('2019-10-17 15:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577175 AND AD_Language='en_US'
;

-- 2019-10-17T12:24:16.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577175,'en_US') 
;

-- 2019-10-17T12:24:30.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 1 Empfänger', PrintName='Straße 1 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577168 AND AD_Language='de_CH'
;

-- 2019-10-17T12:24:30.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577168,'de_CH') 
;

-- 2019-10-17T12:24:34.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 1 Empfänger', PrintName='Straße 1 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577168 AND AD_Language='de_DE'
;

-- 2019-10-17T12:24:34.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577168,'de_DE') 
;

-- 2019-10-17T12:24:34.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577168,'de_DE') 
;

-- 2019-10-17T12:24:34.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_StreetName1', Name='Straße 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577168
;

-- 2019-10-17T12:24:34.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_StreetName1', Name='Straße 1 Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577168 WHERE UPPER(ColumnName)='DHL_RECEIVER_STREETNAME1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:24:34.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_StreetName1', Name='Straße 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577168 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:24:34.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Straße 1 Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577168) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577168)
;

-- 2019-10-17T12:24:34.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Straße 1 Empfänger', Name='Straße 1 Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577168)
;

-- 2019-10-17T12:24:34.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Straße 1 Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577168
;

-- 2019-10-17T12:24:34.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Straße 1 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577168
;

-- 2019-10-17T12:24:34.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Straße 1 Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577168
;

-- 2019-10-17T12:24:45.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Receiver Street Name 1', PrintName='Receiver Street Name 1',Updated=TO_TIMESTAMP('2019-10-17 15:24:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577168 AND AD_Language='en_US'
;

-- 2019-10-17T12:24:45.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577168,'en_US') 
;

-- 2019-10-17T12:24:46.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:24:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577168 AND AD_Language='en_US'
;

-- 2019-10-17T12:24:46.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577168,'en_US') 
;

-- 2019-10-17T12:25:02.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 2 Empfänger', PrintName='Straße 2 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577169 AND AD_Language='de_CH'
;

-- 2019-10-17T12:25:02.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577169,'de_CH') 
;

-- 2019-10-17T12:25:05.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 2 Empfänger', PrintName='Straße 2 Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577169 AND AD_Language='de_DE'
;

-- 2019-10-17T12:25:05.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577169,'de_DE') 
;

-- 2019-10-17T12:25:05.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577169,'de_DE') 
;

-- 2019-10-17T12:25:05.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_StreetName2', Name='Straße 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577169
;

-- 2019-10-17T12:25:05.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_StreetName2', Name='Straße 2 Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577169 WHERE UPPER(ColumnName)='DHL_RECEIVER_STREETNAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:25:05.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_StreetName2', Name='Straße 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577169 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:25:05.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Straße 2 Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577169) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577169)
;

-- 2019-10-17T12:25:05.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Straße 2 Empfänger', Name='Straße 2 Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577169)
;

-- 2019-10-17T12:25:05.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Straße 2 Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577169
;

-- 2019-10-17T12:25:05.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Straße 2 Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577169
;

-- 2019-10-17T12:25:05.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Straße 2 Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577169
;

-- 2019-10-17T12:25:20.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Receiver Street Name 2', PrintName='Receiver Street Name 2',Updated=TO_TIMESTAMP('2019-10-17 15:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577169 AND AD_Language='en_US'
;

-- 2019-10-17T12:25:20.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577169,'en_US') 
;

-- 2019-10-17T12:25:25.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577169 AND AD_Language='en_US'
;

-- 2019-10-17T12:25:25.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577169,'en_US') 
;

-- 2019-10-17T12:25:54.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hausnummer Empfänger', PrintName='Hausnummer Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577170 AND AD_Language='de_CH'
;

-- 2019-10-17T12:25:54.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577170,'de_CH') 
;

-- 2019-10-17T12:25:58.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hausnummer Empfänger', PrintName='Hausnummer Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577170 AND AD_Language='de_DE'
;

-- 2019-10-17T12:25:58.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577170,'de_DE') 
;

-- 2019-10-17T12:25:58.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577170,'de_DE') 
;

-- 2019-10-17T12:25:58.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_StreetNumber', Name='Hausnummer Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577170
;

-- 2019-10-17T12:25:58.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_StreetNumber', Name='Hausnummer Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577170 WHERE UPPER(ColumnName)='DHL_RECEIVER_STREETNUMBER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:25:58.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_StreetNumber', Name='Hausnummer Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577170 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:25:58.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Hausnummer Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577170) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577170)
;

-- 2019-10-17T12:25:58.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Hausnummer Empfänger', Name='Hausnummer Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577170)
;

-- 2019-10-17T12:25:58.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Hausnummer Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577170
;

-- 2019-10-17T12:25:58.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Hausnummer Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577170
;

-- 2019-10-17T12:25:58.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Hausnummer Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577170
;

-- 2019-10-17T12:26:08.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receiver Street Number', PrintName='Receiver Street Number',Updated=TO_TIMESTAMP('2019-10-17 15:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577170 AND AD_Language='en_US'
;

-- 2019-10-17T12:26:08.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577170,'en_US') 
;

-- 2019-10-17T12:26:34.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLZ Empfänger', PrintName='PLZ Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577171 AND AD_Language='de_CH'
;

-- 2019-10-17T12:26:34.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577171,'de_CH') 
;

-- 2019-10-17T12:26:38.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLZ Empfänger', PrintName='PLZ Empfänger',Updated=TO_TIMESTAMP('2019-10-17 15:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577171 AND AD_Language='de_DE'
;

-- 2019-10-17T12:26:38.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577171,'de_DE') 
;

-- 2019-10-17T12:26:38.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577171,'de_DE') 
;

-- 2019-10-17T12:26:38.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Receiver_ZipCode', Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577171
;

-- 2019-10-17T12:26:38.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_ZipCode', Name='PLZ Empfänger', Description=NULL, Help=NULL, AD_Element_ID=577171 WHERE UPPER(ColumnName)='DHL_RECEIVER_ZIPCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:26:38.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Receiver_ZipCode', Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID=577171 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:26:38.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577171) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577171)
;

-- 2019-10-17T12:26:38.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLZ Empfänger', Name='PLZ Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577171)
;

-- 2019-10-17T12:26:38.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLZ Empfänger', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577171
;

-- 2019-10-17T12:26:38.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLZ Empfänger', Description=NULL, Help=NULL WHERE AD_Element_ID = 577171
;

-- 2019-10-17T12:26:38.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLZ Empfänger', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577171
;

-- 2019-10-17T12:26:52.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receiver Zip Code', PrintName='Receiver Zip Code',Updated=TO_TIMESTAMP('2019-10-17 15:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577171 AND AD_Language='en_US'
;

-- 2019-10-17T12:26:52.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577171,'en_US') 
;

-- 2019-10-17T12:27:28.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum', PrintName='Lieferdatum',Updated=TO_TIMESTAMP('2019-10-17 15:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577160 AND AD_Language='de_CH'
;

-- 2019-10-17T12:27:28.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577160,'de_CH') 
;

-- 2019-10-17T12:27:31.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferdatum', PrintName='Lieferdatum',Updated=TO_TIMESTAMP('2019-10-17 15:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577160 AND AD_Language='de_DE'
;

-- 2019-10-17T12:27:31.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577160,'de_DE') 
;

-- 2019-10-17T12:27:31.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577160,'de_DE') 
;

-- 2019-10-17T12:27:31.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_ShipmentDate', Name='Lieferdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=577160
;

-- 2019-10-17T12:27:31.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_ShipmentDate', Name='Lieferdatum', Description=NULL, Help=NULL, AD_Element_ID=577160 WHERE UPPER(ColumnName)='DHL_SHIPMENTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:27:31.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_ShipmentDate', Name='Lieferdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=577160 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:27:31.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577160) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577160)
;

-- 2019-10-17T12:27:31.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferdatum', Name='Lieferdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577160)
;

-- 2019-10-17T12:27:31.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577160
;

-- 2019-10-17T12:27:31.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 577160
;

-- 2019-10-17T12:27:31.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577160
;

-- 2019-10-17T12:27:39.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipment Date', PrintName='Shipment Date',Updated=TO_TIMESTAMP('2019-10-17 15:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577160 AND AD_Language='en_US'
;

-- 2019-10-17T12:27:39.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577160,'en_US') 
;

-- 2019-10-17T12:28:14.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferort', PrintName='Lieferort',Updated=TO_TIMESTAMP('2019-10-17 15:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577182 AND AD_Language='de_CH'
;

-- 2019-10-17T12:28:14.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577182,'de_CH') 
;

-- 2019-10-17T12:28:17.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferort', PrintName='Lieferort',Updated=TO_TIMESTAMP('2019-10-17 15:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577182 AND AD_Language='de_DE'
;

-- 2019-10-17T12:28:17.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577182,'de_DE') 
;

-- 2019-10-17T12:28:17.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577182,'de_DE') 
;

-- 2019-10-17T12:28:17.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_City', Name='Lieferort', Description=NULL, Help=NULL WHERE AD_Element_ID=577182
;

-- 2019-10-17T12:28:17.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_City', Name='Lieferort', Description=NULL, Help=NULL, AD_Element_ID=577182 WHERE UPPER(ColumnName)='DHL_SHIPPER_CITY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:28:17.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_City', Name='Lieferort', Description=NULL, Help=NULL WHERE AD_Element_ID=577182 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:28:17.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferort', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577182) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577182)
;

-- 2019-10-17T12:28:17.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferort', Name='Lieferort' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577182)
;

-- 2019-10-17T12:28:17.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferort', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577182
;

-- 2019-10-17T12:28:17.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferort', Description=NULL, Help=NULL WHERE AD_Element_ID = 577182
;

-- 2019-10-17T12:28:17.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferort', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577182
;

-- 2019-10-17T12:28:25.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper City', PrintName='Shipper City',Updated=TO_TIMESTAMP('2019-10-17 15:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577182 AND AD_Language='en_US'
;

-- 2019-10-17T12:28:25.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577182,'en_US') 
;

-- 2019-10-17T12:28:42.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferant Ländercode (ISO-2)', PrintName='Lieferant Ländercode (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577183 AND AD_Language='de_CH'
;

-- 2019-10-17T12:28:42.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577183,'de_CH') 
;

-- 2019-10-17T12:28:45.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferant Ländercode (ISO-2)', PrintName='Lieferant Ländercode (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577183 AND AD_Language='de_DE'
;

-- 2019-10-17T12:28:45.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577183,'de_DE') 
;

-- 2019-10-17T12:28:45.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577183,'de_DE') 
;

-- 2019-10-17T12:28:45.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_CountryISO2Code', Name='Lieferant Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE AD_Element_ID=577183
;

-- 2019-10-17T12:28:45.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_CountryISO2Code', Name='Lieferant Ländercode (ISO-2)', Description=NULL, Help=NULL, AD_Element_ID=577183 WHERE UPPER(ColumnName)='DHL_SHIPPER_COUNTRYISO2CODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:28:45.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_CountryISO2Code', Name='Lieferant Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE AD_Element_ID=577183 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:28:45.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577183) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577183)
;

-- 2019-10-17T12:28:45.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferant Ländercode (ISO-2)', Name='Lieferant Ländercode (ISO-2)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577183)
;

-- 2019-10-17T12:28:45.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferant Ländercode (ISO-2)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577183
;

-- 2019-10-17T12:28:45.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferant Ländercode (ISO-2)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577183
;

-- 2019-10-17T12:28:45.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferant Ländercode (ISO-2)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577183
;

-- 2019-10-17T12:29:28.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Receiver Country Code (ISO-3)', PrintName='Receiver Country Code (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577212 AND AD_Language='en_US'
;

-- 2019-10-17T12:29:28.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577212,'en_US') 
;

-- 2019-10-17T12:29:49.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Receiver Country Code (ISO-2)', PrintName='Receiver Country Code (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577173 AND AD_Language='en_US'
;

-- 2019-10-17T12:29:49.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577173,'en_US') 
;

-- 2019-10-17T12:30:05.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Shipper Country Code (ISO-2)', PrintName='Shipper Country Code (ISO-2)',Updated=TO_TIMESTAMP('2019-10-17 15:30:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577183 AND AD_Language='en_US'
;

-- 2019-10-17T12:30:05.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577183,'en_US') 
;

-- 2019-10-17T12:30:10.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577183 AND AD_Language='en_US'
;

-- 2019-10-17T12:30:10.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577183,'en_US') 
;

-- 2019-10-17T12:30:32.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper Country Code (ISO-3)', PrintName='Shipper Country Code (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577213 AND AD_Language='en_US'
;

-- 2019-10-17T12:30:32.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577213,'en_US') 
;

-- 2019-10-17T12:30:42.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferant Ländercode (ISO-3)', PrintName='Lieferant Ländercode (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577213 AND AD_Language='de_DE'
;

-- 2019-10-17T12:30:42.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577213,'de_DE') 
;

-- 2019-10-17T12:30:42.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577213,'de_DE') 
;

-- 2019-10-17T12:30:42.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_CountryISO3Code', Name='Lieferant Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE AD_Element_ID=577213
;

-- 2019-10-17T12:30:42.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_CountryISO3Code', Name='Lieferant Ländercode (ISO-3)', Description=NULL, Help=NULL, AD_Element_ID=577213 WHERE UPPER(ColumnName)='DHL_SHIPPER_COUNTRYISO3CODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:30:42.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_CountryISO3Code', Name='Lieferant Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE AD_Element_ID=577213 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:30:42.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577213) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577213)
;

-- 2019-10-17T12:30:42.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferant Ländercode (ISO-3)', Name='Lieferant Ländercode (ISO-3)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577213)
;

-- 2019-10-17T12:30:42.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferant Ländercode (ISO-3)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577213
;

-- 2019-10-17T12:30:42.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferant Ländercode (ISO-3)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577213
;

-- 2019-10-17T12:30:42.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferant Ländercode (ISO-3)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577213
;

-- 2019-10-17T12:30:47.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferant Ländercode (ISO-3)', PrintName='Lieferant Ländercode (ISO-3)',Updated=TO_TIMESTAMP('2019-10-17 15:30:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577213 AND AD_Language='de_CH'
;

-- 2019-10-17T12:30:47.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577213,'de_CH') 
;

-- 2019-10-17T12:31:01.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferant Name 1', PrintName='Lieferant Name 1',Updated=TO_TIMESTAMP('2019-10-17 15:31:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577176 AND AD_Language='de_CH'
;

-- 2019-10-17T12:31:01.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577176,'de_CH') 
;

-- 2019-10-17T12:31:04.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferant Name 1', PrintName='Lieferant Name 1',Updated=TO_TIMESTAMP('2019-10-17 15:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577176 AND AD_Language='de_DE'
;

-- 2019-10-17T12:31:04.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577176,'de_DE') 
;

-- 2019-10-17T12:31:04.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577176,'de_DE') 
;

-- 2019-10-17T12:31:04.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_Name1', Name='Lieferant Name 1', Description=NULL, Help=NULL WHERE AD_Element_ID=577176
;

-- 2019-10-17T12:31:04.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_Name1', Name='Lieferant Name 1', Description=NULL, Help=NULL, AD_Element_ID=577176 WHERE UPPER(ColumnName)='DHL_SHIPPER_NAME1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:31:04.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_Name1', Name='Lieferant Name 1', Description=NULL, Help=NULL WHERE AD_Element_ID=577176 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:31:04.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant Name 1', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577176) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577176)
;

-- 2019-10-17T12:31:04.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferant Name 1', Name='Lieferant Name 1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577176)
;

-- 2019-10-17T12:31:04.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferant Name 1', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577176
;

-- 2019-10-17T12:31:04.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferant Name 1', Description=NULL, Help=NULL WHERE AD_Element_ID = 577176
;

-- 2019-10-17T12:31:04.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferant Name 1', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577176
;

-- 2019-10-17T12:31:15.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper Name 1', PrintName='Shipper Name 1',Updated=TO_TIMESTAMP('2019-10-17 15:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577176 AND AD_Language='en_US'
;

-- 2019-10-17T12:31:15.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577176,'en_US') 
;

-- 2019-10-17T12:31:29.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper Name 2', PrintName='Shipper Name 2',Updated=TO_TIMESTAMP('2019-10-17 15:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577177 AND AD_Language='en_US'
;

-- 2019-10-17T12:31:29.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577177,'en_US') 
;

-- 2019-10-17T12:31:37.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name=' Lieferant Name 2', PrintName=' Lieferant Name 2',Updated=TO_TIMESTAMP('2019-10-17 15:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577177 AND AD_Language='de_CH'
;

-- 2019-10-17T12:31:37.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577177,'de_CH') 
;

-- 2019-10-17T12:31:41.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name=' Lieferant Name 2', PrintName=' Lieferant Name 2',Updated=TO_TIMESTAMP('2019-10-17 15:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577177 AND AD_Language='de_DE'
;

-- 2019-10-17T12:31:41.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577177,'de_DE') 
;

-- 2019-10-17T12:31:41.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577177,'de_DE') 
;

-- 2019-10-17T12:31:41.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_Name2', Name=' Lieferant Name 2', Description=NULL, Help=NULL WHERE AD_Element_ID=577177
;

-- 2019-10-17T12:31:41.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_Name2', Name=' Lieferant Name 2', Description=NULL, Help=NULL, AD_Element_ID=577177 WHERE UPPER(ColumnName)='DHL_SHIPPER_NAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:31:41.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_Name2', Name=' Lieferant Name 2', Description=NULL, Help=NULL WHERE AD_Element_ID=577177 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:31:41.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name=' Lieferant Name 2', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577177) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577177)
;

-- 2019-10-17T12:31:41.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName=' Lieferant Name 2', Name=' Lieferant Name 2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577177)
;

-- 2019-10-17T12:31:41.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name=' Lieferant Name 2', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577177
;

-- 2019-10-17T12:31:41.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name=' Lieferant Name 2', Description=NULL, Help=NULL WHERE AD_Element_ID = 577177
;

-- 2019-10-17T12:31:41.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = ' Lieferant Name 2', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577177
;

-- 2019-10-17T12:31:55.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 1 Lieferant', PrintName='Straße 1 Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:31:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577178 AND AD_Language='de_CH'
;

-- 2019-10-17T12:31:55.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577178,'de_CH') 
;

-- 2019-10-17T12:31:59.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 1 Lieferant', PrintName='Straße 1 Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577178 AND AD_Language='de_DE'
;

-- 2019-10-17T12:31:59.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577178,'de_DE') 
;

-- 2019-10-17T12:31:59.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577178,'de_DE') 
;

-- 2019-10-17T12:31:59.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_StreetName1', Name='Straße 1 Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577178
;

-- 2019-10-17T12:31:59.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_StreetName1', Name='Straße 1 Lieferant', Description=NULL, Help=NULL, AD_Element_ID=577178 WHERE UPPER(ColumnName)='DHL_SHIPPER_STREETNAME1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:31:59.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_StreetName1', Name='Straße 1 Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577178 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:31:59.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Straße 1 Lieferant', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577178) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577178)
;

-- 2019-10-17T12:31:59.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Straße 1 Lieferant', Name='Straße 1 Lieferant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577178)
;

-- 2019-10-17T12:31:59.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Straße 1 Lieferant', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577178
;

-- 2019-10-17T12:31:59.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Straße 1 Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID = 577178
;

-- 2019-10-17T12:31:59.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Straße 1 Lieferant', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577178
;

-- 2019-10-17T12:32:10.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper Street Name 1', PrintName='Shipper Street Name 1',Updated=TO_TIMESTAMP('2019-10-17 15:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577178 AND AD_Language='en_US'
;

-- 2019-10-17T12:32:10.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577178,'en_US') 
;

-- 2019-10-17T12:32:25.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper Street Name 2', PrintName='Shipper Street Name 2',Updated=TO_TIMESTAMP('2019-10-17 15:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577179 AND AD_Language='en_US'
;

-- 2019-10-17T12:32:25.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577179,'en_US') 
;

-- 2019-10-17T12:32:32.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 2 Lieferant', PrintName='Straße 2 Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577179 AND AD_Language='de_CH'
;

-- 2019-10-17T12:32:32.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577179,'de_CH') 
;

-- 2019-10-17T12:32:37.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Straße 2 Lieferant', PrintName='Straße 2 Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:32:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577179 AND AD_Language='de_DE'
;

-- 2019-10-17T12:32:37.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577179,'de_DE') 
;

-- 2019-10-17T12:32:37.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577179,'de_DE') 
;

-- 2019-10-17T12:32:37.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_StreetName2', Name='Straße 2 Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577179
;

-- 2019-10-17T12:32:37.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_StreetName2', Name='Straße 2 Lieferant', Description=NULL, Help=NULL, AD_Element_ID=577179 WHERE UPPER(ColumnName)='DHL_SHIPPER_STREETNAME2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:32:37.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_StreetName2', Name='Straße 2 Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577179 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:32:37.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Straße 2 Lieferant', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577179) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577179)
;

-- 2019-10-17T12:32:37.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Straße 2 Lieferant', Name='Straße 2 Lieferant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577179)
;

-- 2019-10-17T12:32:37.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Straße 2 Lieferant', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577179
;

-- 2019-10-17T12:32:37.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Straße 2 Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID = 577179
;

-- 2019-10-17T12:32:37.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Straße 2 Lieferant', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577179
;

-- 2019-10-17T12:32:52.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hausnummer Lieferant', PrintName='Hausnummer Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577180 AND AD_Language='de_CH'
;

-- 2019-10-17T12:32:52.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577180,'de_CH') 
;

-- 2019-10-17T12:32:55.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hausnummer Lieferant', PrintName='Hausnummer Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:32:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577180 AND AD_Language='de_DE'
;

-- 2019-10-17T12:32:55.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577180,'de_DE') 
;

-- 2019-10-17T12:32:55.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577180,'de_DE') 
;

-- 2019-10-17T12:32:55.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_StreetNumber', Name='Hausnummer Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577180
;

-- 2019-10-17T12:32:55.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_StreetNumber', Name='Hausnummer Lieferant', Description=NULL, Help=NULL, AD_Element_ID=577180 WHERE UPPER(ColumnName)='DHL_SHIPPER_STREETNUMBER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:32:55.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_StreetNumber', Name='Hausnummer Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577180 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:32:55.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Hausnummer Lieferant', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577180) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577180)
;

-- 2019-10-17T12:32:55.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Hausnummer Lieferant', Name='Hausnummer Lieferant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577180)
;

-- 2019-10-17T12:32:55.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Hausnummer Lieferant', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577180
;

-- 2019-10-17T12:32:55.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Hausnummer Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID = 577180
;

-- 2019-10-17T12:32:55.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Hausnummer Lieferant', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577180
;

-- 2019-10-17T12:33:05.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper Street Number', PrintName='Shipper Street Number',Updated=TO_TIMESTAMP('2019-10-17 15:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577180 AND AD_Language='en_US'
;

-- 2019-10-17T12:33:05.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577180,'en_US') 
;

-- 2019-10-17T12:33:21.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLZ Lieferant', PrintName='PLZ Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577181 AND AD_Language='de_CH'
;

-- 2019-10-17T12:33:21.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577181,'de_CH') 
;

-- 2019-10-17T12:33:24.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='PLZ Lieferant', PrintName='PLZ Lieferant',Updated=TO_TIMESTAMP('2019-10-17 15:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577181 AND AD_Language='de_DE'
;

-- 2019-10-17T12:33:24.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577181,'de_DE') 
;

-- 2019-10-17T12:33:24.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577181,'de_DE') 
;

-- 2019-10-17T12:33:24.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_Shipper_ZipCode', Name='PLZ Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577181
;

-- 2019-10-17T12:33:24.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_ZipCode', Name='PLZ Lieferant', Description=NULL, Help=NULL, AD_Element_ID=577181 WHERE UPPER(ColumnName)='DHL_SHIPPER_ZIPCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:33:24.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_Shipper_ZipCode', Name='PLZ Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=577181 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:33:24.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLZ Lieferant', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577181) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577181)
;

-- 2019-10-17T12:33:24.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLZ Lieferant', Name='PLZ Lieferant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577181)
;

-- 2019-10-17T12:33:24.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLZ Lieferant', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577181
;

-- 2019-10-17T12:33:24.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLZ Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID = 577181
;

-- 2019-10-17T12:33:24.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLZ Lieferant', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577181
;

-- 2019-10-17T12:33:28.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577181 AND AD_Language='en_US'
;

-- 2019-10-17T12:33:28.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577181,'en_US') 
;

-- 2019-10-17T12:33:30.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:33:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577181 AND AD_Language='de_DE'
;

-- 2019-10-17T12:33:30.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577181,'de_DE') 
;

-- 2019-10-17T12:33:30.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577181,'de_DE') 
;

-- 2019-10-17T12:33:41.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Shipper Zip Code', PrintName='Shipper Zip Code',Updated=TO_TIMESTAMP('2019-10-17 15:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577181 AND AD_Language='en_US'
;

-- 2019-10-17T12:33:41.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577181,'en_US') 
;

-- 2019-10-17T12:33:56.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewicht in kg', PrintName='Gewicht in kg',Updated=TO_TIMESTAMP('2019-10-17 15:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577184 AND AD_Language='de_CH'
;

-- 2019-10-17T12:33:56.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577184,'de_CH') 
;

-- 2019-10-17T12:33:59.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewicht in kg', PrintName='Gewicht in kg',Updated=TO_TIMESTAMP('2019-10-17 15:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577184 AND AD_Language='de_DE'
;

-- 2019-10-17T12:33:59.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577184,'de_DE') 
;

-- 2019-10-17T12:33:59.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577184,'de_DE') 
;

-- 2019-10-17T12:33:59.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_WeightInKg', Name='Gewicht in kg', Description=NULL, Help=NULL WHERE AD_Element_ID=577184
;

-- 2019-10-17T12:33:59.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_WeightInKg', Name='Gewicht in kg', Description=NULL, Help=NULL, AD_Element_ID=577184 WHERE UPPER(ColumnName)='DHL_WEIGHTINKG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:33:59.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_WeightInKg', Name='Gewicht in kg', Description=NULL, Help=NULL WHERE AD_Element_ID=577184 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:33:59.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gewicht in kg', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577184) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577184)
;

-- 2019-10-17T12:33:59.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gewicht in kg', Name='Gewicht in kg' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577184)
;

-- 2019-10-17T12:33:59.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gewicht in kg', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577184
;

-- 2019-10-17T12:33:59.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gewicht in kg', Description=NULL, Help=NULL WHERE AD_Element_ID = 577184
;

-- 2019-10-17T12:33:59.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gewicht in kg', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577184
;

-- 2019-10-17T12:34:11.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Weight In kg', PrintName='Weight In kg',Updated=TO_TIMESTAMP('2019-10-17 15:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577184 AND AD_Language='en_US'
;

-- 2019-10-17T12:34:11.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577184,'en_US') 
;

-- 2019-10-17T12:34:29.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Breite in cm', PrintName='Breite in cm',Updated=TO_TIMESTAMP('2019-10-17 15:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577163 AND AD_Language='de_CH'
;

-- 2019-10-17T12:34:29.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577163,'de_CH') 
;

-- 2019-10-17T12:34:32.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Breite in cm', PrintName='Breite in cm',Updated=TO_TIMESTAMP('2019-10-17 15:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577163 AND AD_Language='de_DE'
;

-- 2019-10-17T12:34:32.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577163,'de_DE') 
;

-- 2019-10-17T12:34:32.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577163,'de_DE') 
;

-- 2019-10-17T12:34:32.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DHL_WidthInCm', Name='Breite in cm', Description=NULL, Help=NULL WHERE AD_Element_ID=577163
;

-- 2019-10-17T12:34:32.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_WidthInCm', Name='Breite in cm', Description=NULL, Help=NULL, AD_Element_ID=577163 WHERE UPPER(ColumnName)='DHL_WIDTHINCM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:34:32.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DHL_WidthInCm', Name='Breite in cm', Description=NULL, Help=NULL WHERE AD_Element_ID=577163 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:34:32.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Breite in cm', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577163) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577163)
;

-- 2019-10-17T12:34:32.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Breite in cm', Name='Breite in cm' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577163)
;

-- 2019-10-17T12:34:32.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Breite in cm', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577163
;

-- 2019-10-17T12:34:32.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Breite in cm', Description=NULL, Help=NULL WHERE AD_Element_ID = 577163
;

-- 2019-10-17T12:34:32.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Breite in cm', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577163
;

-- 2019-10-17T12:34:41.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Width In cm', PrintName='Width In cm',Updated=TO_TIMESTAMP('2019-10-17 15:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577163 AND AD_Language='en_US'
;

-- 2019-10-17T12:34:41.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577163,'en_US') 
;

-- 2019-10-17T12:34:42.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577163 AND AD_Language='en_US'
;

-- 2019-10-17T12:34:42.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577163,'en_US') 
;

-- 2019-10-17T12:35:10.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Paket-ID', PrintName='Paket-ID',Updated=TO_TIMESTAMP('2019-10-17 15:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577157 AND AD_Language='de_CH'
;

-- 2019-10-17T12:35:10.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577157,'de_CH') 
;

-- 2019-10-17T12:35:13.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Paket-ID', PrintName='Paket-ID',Updated=TO_TIMESTAMP('2019-10-17 15:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577157 AND AD_Language='de_DE'
;

-- 2019-10-17T12:35:13.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577157,'de_DE') 
;

-- 2019-10-17T12:35:13.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577157,'de_DE') 
;

-- 2019-10-17T12:35:13.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PackageId', Name='Paket-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=577157
;

-- 2019-10-17T12:35:13.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PackageId', Name='Paket-ID', Description=NULL, Help=NULL, AD_Element_ID=577157 WHERE UPPER(ColumnName)='PACKAGEID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-17T12:35:13.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PackageId', Name='Paket-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=577157 AND IsCentrallyMaintained='Y'
;

-- 2019-10-17T12:35:13.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Paket-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577157) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577157)
;

-- 2019-10-17T12:35:13.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Paket-ID', Name='Paket-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577157)
;

-- 2019-10-17T12:35:13.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Paket-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577157
;

-- 2019-10-17T12:35:13.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Paket-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 577157
;

-- 2019-10-17T12:35:13.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Paket-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577157
;

-- 2019-10-17T12:35:18.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-17 15:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577157 AND AD_Language='en_US'
;

-- 2019-10-17T12:35:18.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577157,'en_US') 
;

-- 2019-10-17T12:36:27.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-10-17 15:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563284
;

-- 2019-10-17T12:37:09.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-10-17 15:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563287
;

-- 2019-10-17T12:38:14.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=111,Updated=TO_TIMESTAMP('2019-10-17 15:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563292
;

-- 2019-10-17T12:38:30.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2019-10-17 15:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563274
;

-- 2019-10-17T12:38:50.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-10-17 15:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563285
;

-- 2019-10-17T12:38:52.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-10-17 15:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563286
;

-- 2019-10-17T12:38:53.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-10-17 15:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563288
;

-- 2019-10-17T12:38:54.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-10-17 15:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563289
;

-- 2019-10-17T12:38:55.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-10-17 15:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563290
;

-- 2019-10-17T12:38:56.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-10-17 15:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563291
;

-- 2019-10-17T12:38:57.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-10-17 15:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563284
;

-- 2019-10-17T12:38:59.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-10-17 15:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563292
;

-- 2019-10-17T12:39:23.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-10-17 15:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563276
;

-- 2019-10-17T12:39:23.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-10-17 15:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563277
;

-- 2019-10-17T12:39:24.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-10-17 15:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563278
;

-- 2019-10-17T12:39:24.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-10-17 15:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563279
;

-- 2019-10-17T12:39:25.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-10-17 15:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563280
;

-- 2019-10-17T12:39:26.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-10-17 15:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563281
;

-- 2019-10-17T12:39:28.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-10-17 15:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563274
;

-- 2019-10-17T12:40:10.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=1,Updated=TO_TIMESTAMP('2019-10-17 15:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563276
;

-- 2019-10-17T12:40:14.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=2,Updated=TO_TIMESTAMP('2019-10-17 15:40:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563277
;

-- 2019-10-17T12:40:26.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=1,Updated=TO_TIMESTAMP('2019-10-17 15:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563285
;

-- 2019-10-17T12:40:27.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=2,Updated=TO_TIMESTAMP('2019-10-17 15:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563286
;

-- 2019-10-17T12:41:19.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2019-10-17 15:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563287
;

-- 2019-10-17T12:42:07.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-10-17 15:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563292
;

-- 2019-10-17T12:42:08.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2019-10-17 15:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563284
;

-- 2019-10-17T12:42:12.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-10-17 15:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563287
;

-- 2019-10-17T13:00:27.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='ccccccccccccc', Help='dddddddddddddddddd', Name='aaaaaaaaaaaa', PrintName='bbbbbbbbbbbbbbb',Updated=TO_TIMESTAMP('2019-10-17 16:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577142 AND AD_Language='en_US'
;

-- 2019-10-17T13:00:27.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577142,'en_US') 
;

-- 2019-10-17T13:01:58.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Documentation and how to obtain Application ID, token and all the credentials: https://entwickler.dhl.de/group/ep/authentifizierung', Help='', Name='Dhl Configuration',Updated=TO_TIMESTAMP('2019-10-17 16:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577142 AND AD_Language='en_US'
;

-- 2019-10-17T13:01:58.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577142,'en_US') 
;
-- 2019-10-17T13:28:42.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541069,TO_TIMESTAMP('2019-10-17 16:28:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Transportation Order ID',TO_TIMESTAMP('2019-10-17 16:28:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-10-17T13:28:42.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541069 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-10-17T13:31:11.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,540426,0,541069,540030,TO_TIMESTAMP('2019-10-17 16:31:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-10-17 16:31:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T13:32:24.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541070,TO_TIMESTAMP('2019-10-17 16:32:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Dhl Shipment Order',TO_TIMESTAMP('2019-10-17 16:32:24','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-10-17T13:32:24.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541070 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-10-17T13:32:43.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,569224,0,541070,541419,TO_TIMESTAMP('2019-10-17 16:32:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-10-17 16:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T13:33:03.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Dhl Shipment Order -> Transportation Order',Updated=TO_TIMESTAMP('2019-10-17 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541070
;

-- 2019-10-17T13:33:39.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541069,541070,540230,TO_TIMESTAMP('2019-10-17 16:33:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Transportation Order -> DHL Delivery Order',TO_TIMESTAMP('2019-10-17 16:33:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-17T13:36:45.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540743,Updated=TO_TIMESTAMP('2019-10-17 16:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541070
;

-- 2019-10-17T13:40:11.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Finds a Dhl Shipment Order by its Transportation Order ID',Updated=TO_TIMESTAMP('2019-10-17 16:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541070
;

-- 2019-10-17T13:43:14.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipperTransportation_ID=@M_ShipperTransportation_ID/-1@',Updated=TO_TIMESTAMP('2019-10-17 16:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541070
;

