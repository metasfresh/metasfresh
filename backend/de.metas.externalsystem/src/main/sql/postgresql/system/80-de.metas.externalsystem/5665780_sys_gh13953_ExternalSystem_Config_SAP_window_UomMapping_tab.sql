-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T12:49:13.947Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583308,579930,0,546681,541486,541631,'Y',TO_TIMESTAMP('2022-11-23 14:49:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','N','N','A','S_ExternalReference_UOM','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'UOM Zuordnung','N',40,0,TO_TIMESTAMP('2022-11-23 14:49:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:49:13.949Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546681 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-23T12:49:13.979Z
/* DDL */  select update_tab_translation_from_ad_element(579930) 
;

-- 2022-11-23T12:49:13.990Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546681)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T12:50:45.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570588,708163,0,546681,TO_TIMESTAMP('2022-11-23 14:50:45','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalreference','Y','N','N','N','N','N','N','N','External reference',TO_TIMESTAMP('2022-11-23 14:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:50:45.804Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:50:45.807Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577602) 
;

-- 2022-11-23T12:50:45.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708163
;

-- 2022-11-23T12:50:45.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708163)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference)
-- UI Section: main
-- 2022-11-23T12:51:01.692Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,UIStyle,Updated,UpdatedBy,Value) VALUES (0,0,546681,545304,TO_TIMESTAMP('2022-11-23 14:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'primary',TO_TIMESTAMP('2022-11-23 14:51:01','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-23T12:51:01.694Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545304 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main
-- UI Column: 10
-- 2022-11-23T12:51:04.398Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546461,545304,TO_TIMESTAMP('2022-11-23 14:51:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-23 14:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10
-- UI Element Group: externalRef
-- 2022-11-23T12:51:18.343Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546461,550045,TO_TIMESTAMP('2022-11-23 14:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','externalRef',10,TO_TIMESTAMP('2022-11-23 14:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-23T12:51:49.389Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570585,708164,0,546681,TO_TIMESTAMP('2022-11-23 14:51:49','YYYY-MM-DD HH24:MI:SS'),100,'Name of an external system (e.g. Github )',255,'de.metas.externalreference','Name of an external system (e.g. Github )','Y','N','N','N','N','N','N','N','External system',TO_TIMESTAMP('2022-11-23 14:51:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:51:49.390Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:51:49.392Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577608) 
;

-- 2022-11-23T12:51:49.396Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708164
;

-- 2022-11-23T12:51:49.397Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708164)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Mandant
-- Column: S_ExternalReference.AD_Client_ID
-- 2022-11-23T12:51:59.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570573,708165,0,546681,TO_TIMESTAMP('2022-11-23 14:51:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalreference','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-23 14:51:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:51:59.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:51:59.372Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-23T12:51:59.615Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708165
;

-- 2022-11-23T12:51:59.620Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708165)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Sektion
-- Column: S_ExternalReference.AD_Org_ID
-- 2022-11-23T12:52:12.528Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570574,708166,0,546681,TO_TIMESTAMP('2022-11-23 14:52:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalreference','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-23 14:52:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:52:12.530Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:52:12.531Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-23T12:52:12.755Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708166
;

-- 2022-11-23T12:52:12.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708166)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Aktiv
-- Column: S_ExternalReference.IsActive
-- 2022-11-23T12:52:23.233Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570578,708167,0,546681,TO_TIMESTAMP('2022-11-23 14:52:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalreference','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-23 14:52:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:52:23.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:52:23.237Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-23T12:52:23.533Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708167
;

-- 2022-11-23T12:52:23.533Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708167)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Read Only In Metasfresh
-- Column: S_ExternalReference.IsReadOnlyInMetasfresh
-- 2022-11-23T12:52:31.261Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584454,708168,0,546681,TO_TIMESTAMP('2022-11-23 14:52:31','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.externalreference','Y','N','N','N','N','N','N','N','Read Only In Metasfresh',TO_TIMESTAMP('2022-11-23 14:52:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:52:31.262Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:52:31.263Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581490) 
;

-- 2022-11-23T12:52:31.266Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708168
;

-- 2022-11-23T12:52:31.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708168)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T12:52:42.799Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570590,708169,0,546681,TO_TIMESTAMP('2022-11-23 14:52:42','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',10,'de.metas.externalreference','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2022-11-23 14:52:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:52:42.800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:52:42.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2022-11-23T12:52:42.816Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708169
;

-- 2022-11-23T12:52:42.817Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708169)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Art
-- Column: S_ExternalReference.Type
-- 2022-11-23T12:52:54.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570581,708170,0,546681,TO_TIMESTAMP('2022-11-23 14:52:53','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.externalreference','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2022-11-23 14:52:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T12:52:54.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T12:52:54.057Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2022-11-23T12:52:54.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708170
;

-- 2022-11-23T12:52:54.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708170)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10 -> externalRef.External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-23T12:53:06.690Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708164,0,546681,613548,550045,'F',TO_TIMESTAMP('2022-11-23 14:53:06','YYYY-MM-DD HH24:MI:SS'),100,'Name of an external system (e.g. Github )','Name of an external system (e.g. Github )','Y','N','N','Y','N','N','N',0,'External system',10,0,0,TO_TIMESTAMP('2022-11-23 14:53:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10 -> externalRef.External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T12:53:17.322Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708163,0,546681,613549,550045,'F',TO_TIMESTAMP('2022-11-23 14:53:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External reference',20,0,0,TO_TIMESTAMP('2022-11-23 14:53:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10 -> externalRef.Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T12:53:33.440Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708169,0,546681,613550,550045,'F',TO_TIMESTAMP('2022-11-23 14:53:33','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',30,0,0,TO_TIMESTAMP('2022-11-23 14:53:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main
-- UI Column: 20
-- 2022-11-23T12:54:06.333Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546462,545304,TO_TIMESTAMP('2022-11-23 14:54:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-23 14:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 20
-- UI Element Group: flags
-- 2022-11-23T12:54:15.040Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546462,550046,TO_TIMESTAMP('2022-11-23 14:54:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-23 14:54:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 20 -> flags.Aktiv
-- Column: S_ExternalReference.IsActive
-- 2022-11-23T12:54:28.238Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708167,0,546681,613551,550046,'F',TO_TIMESTAMP('2022-11-23 14:54:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-11-23 14:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 20
-- UI Element Group: org
-- 2022-11-23T12:54:34.747Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546462,550047,TO_TIMESTAMP('2022-11-23 14:54:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-11-23 14:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 20 -> org.Mandant
-- Column: S_ExternalReference.AD_Client_ID
-- 2022-11-23T12:54:47.246Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708165,0,546681,613552,550047,'F',TO_TIMESTAMP('2022-11-23 14:54:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-11-23 14:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 20 -> org.Sektion
-- Column: S_ExternalReference.AD_Org_ID
-- 2022-11-23T12:54:56.429Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708166,0,546681,613553,550047,'F',TO_TIMESTAMP('2022-11-23 14:54:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-11-23 14:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-23T12:55:09.693Z
UPDATE AD_Field SET DefaultValue='SAP', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-23 14:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708164
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T12:56:08.858Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-11-23 14:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546681
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10
-- UI Element Group: externalRef
-- 2022-11-23T12:56:56.316Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-11-23 14:56:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550045
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T12:57:46.593Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=114,Updated=TO_TIMESTAMP('2022-11-23 14:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708169
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T13:06:43.948Z
UPDATE AD_Tab SET WhereClause='S_ExternalReference.Type=''UOM''',Updated=TO_TIMESTAMP('2022-11-23 15:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546681
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T13:14:18.136Z
UPDATE AD_Tab SET DefaultWhereClause='S_ExternalReference.Type=''UOM''',Updated=TO_TIMESTAMP('2022-11-23 15:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546681
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T13:15:01.274Z
UPDATE AD_Tab SET DefaultWhereClause='', WhereClause='',Updated=TO_TIMESTAMP('2022-11-23 15:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546681
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Art
-- Column: S_ExternalReference.Type
-- 2022-11-23T13:19:11.938Z
UPDATE AD_Field SET DefaultValue='UOM',Updated=TO_TIMESTAMP('2022-11-23 15:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708170
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Art
-- Column: S_ExternalReference.Type
-- 2022-11-23T13:20:22.036Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-23 15:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708170
;

-- 2022-11-23T13:23:31.553Z
INSERT INTO t_alter_column values('s_externalreference','AD_Client_ID','NUMERIC(10)',null,null)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Referenced table ID
-- Column: S_ExternalReference.Referenced_AD_Table_ID
-- 2022-11-23T13:24:16.887Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570617,708171,0,546681,TO_TIMESTAMP('2022-11-23 15:24:16','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalreference','Y','N','N','N','N','N','N','N','Referenced table ID',TO_TIMESTAMP('2022-11-23 15:24:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:24:16.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:24:16.891Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577669) 
;

-- 2022-11-23T13:24:16.894Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708171
;

-- 2022-11-23T13:24:16.894Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708171)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> Referenced record ID
-- Column: S_ExternalReference.Referenced_Record_ID
-- 2022-11-23T13:24:33.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570620,708172,0,546681,TO_TIMESTAMP('2022-11-23 15:24:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalreference','Y','N','N','N','N','N','N','N','Referenced record ID',TO_TIMESTAMP('2022-11-23 15:24:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:24:33.084Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:24:33.085Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577670) 
;

-- 2022-11-23T13:24:33.087Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708172
;

-- 2022-11-23T13:24:33.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708172)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> External System Config
-- Column: S_ExternalReference.ExternalSystem_Config_ID
-- 2022-11-23T13:36:43.296Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583308,708173,0,546681,TO_TIMESTAMP('2022-11-23 15:36:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalreference','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2022-11-23 15:36:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:36:43.298Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:36:43.323Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-11-23T13:36:43.336Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708173
;

-- 2022-11-23T13:36:43.341Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708173)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T13:43:01.178Z
UPDATE AD_Tab SET Parent_Column_ID=584643,Updated=TO_TIMESTAMP('2022-11-23 15:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546681
;

