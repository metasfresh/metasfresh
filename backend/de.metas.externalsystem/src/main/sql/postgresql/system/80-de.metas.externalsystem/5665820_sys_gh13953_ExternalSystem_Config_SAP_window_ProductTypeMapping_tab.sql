-- 2022-11-23T14:05:33.577Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581724,0,TO_TIMESTAMP('2022-11-23 16:05:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ProductType Mappings','ProductType Mappings',TO_TIMESTAMP('2022-11-23 16:05:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:05:33.584Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581724 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping
-- Table: ProductType_External_Mapping
-- 2022-11-23T14:06:04.638Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,585120,581717,0,546683,542265,541631,'Y',TO_TIMESTAMP('2022-11-23 16:06:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ProductType_External_Mapping','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ProductType_External_Mapping',584643,'N',60,1,TO_TIMESTAMP('2022-11-23 16:06:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:06:04.642Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546683 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-23T14:06:04.644Z
/* DDL */  select update_tab_translation_from_ad_element(581717) 
;

-- 2022-11-23T14:06:04.647Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546683)
;

-- Column: ProductType_External_Mapping.AD_Client_ID
-- 2022-11-23T14:10:09.220Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585112
;

-- Column: ProductType_External_Mapping.AD_Org_ID
-- 2022-11-23T14:10:10.942Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585113
;

-- Column: ProductType_External_Mapping.Created
-- 2022-11-23T14:10:12.434Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585114
;

-- Column: ProductType_External_Mapping.CreatedBy
-- 2022-11-23T14:10:14.249Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585115
;

-- Column: ProductType_External_Mapping.ExternalSystem_Config_ID
-- 2022-11-23T14:10:15.813Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585120
;

-- Column: ProductType_External_Mapping.ExternalValue
-- 2022-11-23T14:10:17.174Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585122
;

-- Column: ProductType_External_Mapping.IsActive
-- 2022-11-23T14:10:18.593Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585116
;

-- Column: ProductType_External_Mapping.ProductType_External_Mapping_ID
-- 2022-11-23T14:10:19.820Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-23 16:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585119
;

-- Column: ProductType_External_Mapping.Updated
-- 2022-11-23T14:10:21.041Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585117
;

-- Column: ProductType_External_Mapping.UpdatedBy
-- 2022-11-23T14:10:22.443Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585118
;

-- Column: ProductType_External_Mapping.Value
-- 2022-11-23T14:10:28.289Z
UPDATE AD_Column SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585121
;

-- Table: ProductType_External_Mapping
-- 2022-11-23T14:10:33.050Z
UPDATE AD_Table SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-23 16:10:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542265
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> Mandant
-- Column: ProductType_External_Mapping.AD_Client_ID
-- 2022-11-23T14:10:57.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585112,708185,0,546683,TO_TIMESTAMP('2022-11-23 16:10:57','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-23 16:10:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:10:57.501Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T14:10:57.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-23T14:10:58.518Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708185
;

-- 2022-11-23T14:10:58.526Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708185)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> Sektion
-- Column: ProductType_External_Mapping.AD_Org_ID
-- 2022-11-23T14:11:12.614Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585113,708186,0,546683,TO_TIMESTAMP('2022-11-23 16:11:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-23 16:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:11:12.615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708186 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T14:11:12.617Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-23T14:11:12.833Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708186
;

-- 2022-11-23T14:11:12.836Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708186)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> External System Config
-- Column: ProductType_External_Mapping.ExternalSystem_Config_ID
-- 2022-11-23T14:11:23.816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585120,708187,0,546683,TO_TIMESTAMP('2022-11-23 16:11:23','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2022-11-23 16:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:11:23.817Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708187 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T14:11:23.819Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-11-23T14:11:23.823Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708187
;

-- 2022-11-23T14:11:23.823Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708187)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> External value
-- Column: ProductType_External_Mapping.ExternalValue
-- 2022-11-23T14:11:33.168Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585122,708188,0,546683,TO_TIMESTAMP('2022-11-23 16:11:33','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External value',TO_TIMESTAMP('2022-11-23 16:11:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:11:33.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708188 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T14:11:33.169Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581718) 
;

-- 2022-11-23T14:11:33.171Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708188
;

-- 2022-11-23T14:11:33.172Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708188)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> Aktiv
-- Column: ProductType_External_Mapping.IsActive
-- 2022-11-23T14:11:41.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585116,708189,0,546683,TO_TIMESTAMP('2022-11-23 16:11:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-23 16:11:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:11:41.321Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T14:11:41.322Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-23T14:11:41.667Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708189
;

-- 2022-11-23T14:11:41.667Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708189)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> Suchschlüssel
-- Column: ProductType_External_Mapping.Value
-- 2022-11-23T14:11:52.284Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585121,708190,0,546683,TO_TIMESTAMP('2022-11-23 16:11:52','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',255,'de.metas.externalsystem','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2022-11-23 16:11:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T14:11:52.285Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708190 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T14:11:52.288Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2022-11-23T14:11:52.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708190
;

-- 2022-11-23T14:11:52.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708190)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> Suchschlüssel
-- Column: ProductType_External_Mapping.Value
-- 2022-11-23T14:15:24.594Z
UPDATE AD_Field SET AD_Reference_ID=17, AD_Reference_Value_ID=270,Updated=TO_TIMESTAMP('2022-11-23 16:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708190
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem)
-- UI Section: main
-- 2022-11-23T14:15:40.141Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546683,545306,TO_TIMESTAMP('2022-11-23 16:15:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-23 16:15:39','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-23T14:15:40.142Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545306 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2022-11-23T14:15:43.303Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546465,545306,TO_TIMESTAMP('2022-11-23 16:15:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-23 16:15:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 10
-- UI Element Group: external
-- 2022-11-23T14:15:57.769Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546465,550051,TO_TIMESTAMP('2022-11-23 16:15:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','external',10,'primary',TO_TIMESTAMP('2022-11-23 16:15:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2022-11-23T14:16:19.770Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546466,545306,TO_TIMESTAMP('2022-11-23 16:16:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-23 16:16:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 10 -> external.External value
-- Column: ProductType_External_Mapping.ExternalValue
-- 2022-11-23T14:18:11.924Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708188,0,546683,613560,550051,'F',TO_TIMESTAMP('2022-11-23 16:18:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External value',10,0,0,TO_TIMESTAMP('2022-11-23 16:18:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 10 -> external.Suchschlüssel
-- Column: ProductType_External_Mapping.Value
-- 2022-11-23T14:18:22.324Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708190,0,546683,613561,550051,'F',TO_TIMESTAMP('2022-11-23 16:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Suchschlüssel',20,0,0,TO_TIMESTAMP('2022-11-23 16:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flags
-- 2022-11-23T14:18:32.750Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546466,550052,TO_TIMESTAMP('2022-11-23 16:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-23 16:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 20 -> flags.Aktiv
-- Column: ProductType_External_Mapping.IsActive
-- 2022-11-23T14:18:41.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708189,0,546683,613562,550052,'F',TO_TIMESTAMP('2022-11-23 16:18:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-11-23 16:18:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2022-11-23T14:18:47.222Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546466,550053,TO_TIMESTAMP('2022-11-23 16:18:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-11-23 16:18:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ProductType_External_Mapping.AD_Client_ID
-- 2022-11-23T14:18:56.367Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708185,0,546683,613563,550053,'F',TO_TIMESTAMP('2022-11-23 16:18:56','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-11-23 16:18:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType_External_Mapping(546683,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ProductType_External_Mapping.AD_Org_ID
-- 2022-11-23T14:19:02.633Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708186,0,546683,613564,550053,'F',TO_TIMESTAMP('2022-11-23 16:19:02','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-11-23 16:19:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductType Mappings
-- Table: ProductType_External_Mapping
-- 2022-11-23T14:19:34.655Z
UPDATE AD_Tab SET AD_Element_ID=581724, CommitWarning=NULL, Description=NULL, Help=NULL, Name='ProductType Mappings',Updated=TO_TIMESTAMP('2022-11-23 16:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546683
;

-- 2022-11-23T14:19:34.676Z
/* DDL */  select update_tab_translation_from_ad_element(581724) 
;

-- 2022-11-23T14:19:34.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546683)
;

-- Column: ProductType_External_Mapping.ProductType_External_Mapping_ID
-- 2022-11-23T14:37:33.897Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-23 16:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585119
;

-- Table: ProductType_External_Mapping
-- 2022-11-23T14:44:01.835Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2022-11-23 16:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542265
;

-- Column: ProductType_External_Mapping.ExternalSystem_Config_ID
-- 2022-11-23T14:45:03.904Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-11-23 16:45:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585120
;

-- 2022-11-23T14:45:05.978Z
INSERT INTO t_alter_column values('producttype_external_mapping','ExternalSystem_Config_ID','NUMERIC(10)',null,null)
;

-- 2022-11-23T14:45:05.984Z
INSERT INTO t_alter_column values('producttype_external_mapping','ExternalSystem_Config_ID',null,'NOT NULL',null)
;

-- Name: ExternalSystem_Config_SAP
-- 2022-11-23T14:59:29.272Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541692,TO_TIMESTAMP('2022-11-23 16:59:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','ExternalSystem_Config_SAP',TO_TIMESTAMP('2022-11-23 16:59:29','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-11-23T14:59:29.275Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541692 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ExternalSystem_Config_SAP
-- Table: ExternalSystem_Config_SAP
-- Key: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:00:38.003Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584652,0,541692,542238,541631,TO_TIMESTAMP('2022-11-23 17:00:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N',TO_TIMESTAMP('2022-11-23 17:00:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:01:01.763Z
UPDATE AD_Field SET AD_Reference_ID=28, AD_Reference_Value_ID=541317,Updated=TO_TIMESTAMP('2022-11-23 17:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:01:33.220Z
UPDATE AD_Field SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2022-11-23 17:01:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:02:11.061Z
UPDATE AD_Field SET AD_Reference_Value_ID=541371,Updated=TO_TIMESTAMP('2022-11-23 17:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- Name: ExternalSystem Config SAP
-- 2022-11-23T15:02:35.549Z
UPDATE AD_Reference SET Name='ExternalSystem Config SAP',Updated=TO_TIMESTAMP('2022-11-23 17:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541692
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:03:30.760Z
UPDATE AD_Field SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2022-11-23 17:03:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:06:18.104Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,UIStyle,Updated,UpdatedBy) VALUES (0,707467,0,546647,613565,549954,'F',TO_TIMESTAMP('2022-11-23 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalSystem_Config_SAP',20,0,0,'',TO_TIMESTAMP('2022-11-23 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:08:52.690Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581725,0,TO_TIMESTAMP('2022-11-23 17:08:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SAP Settings','SAP Settings',TO_TIMESTAMP('2022-11-23 17:08:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:08:52.694Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581725 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SAP Settings
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:09:01.263Z
UPDATE AD_Field SET AD_Name_ID=581725, Description=NULL, Help=NULL, Name='SAP Settings',Updated=TO_TIMESTAMP('2022-11-23 17:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- 2022-11-23T15:09:01.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581725) 
;

-- 2022-11-23T15:09:01.268Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707467
;

-- 2022-11-23T15:09:01.272Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707467)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:09:10.506Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-23 17:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613565
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-23T15:09:10.511Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-23 17:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613112
;

-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-23T15:10:41.334Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2022-11-23 17:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584644
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SAP Settings
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:11:36.459Z
UPDATE AD_Field SET AD_Reference_ID=NULL,Updated=TO_TIMESTAMP('2022-11-23 17:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SAP Settings
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-23T15:13:08.632Z
UPDATE AD_Field SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2022-11-23 17:13:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707467
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Externes System
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-11-23T15:22:44.757Z
UPDATE AD_Field SET AD_Name_ID=578733, Description=NULL, Help=NULL, Name='Externes System',Updated=TO_TIMESTAMP('2022-11-23 17:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708036
;

-- 2022-11-23T15:22:44.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578733) 
;

-- 2022-11-23T15:22:44.764Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708036
;

-- 2022-11-23T15:22:44.770Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708036)
;

UPDATE AD_Field SET AD_Reference_ID =541692 WHERE AD_Field_ID = 707467;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> default.Externes System
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-11-23T15:26:54.136Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708036,0,546671,613567,550016,'F',TO_TIMESTAMP('2022-11-23 17:26:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externes System',20,0,0,TO_TIMESTAMP('2022-11-23 17:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> default.Externes System
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-11-23T15:27:15.890Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2022-11-23 17:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613567
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-23T15:27:19.441Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-23 17:27:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613446
;


-- Element: null
-- 2022-11-23T15:29:56.450Z
UPDATE AD_Element_Trl SET Name='Product Category Mapping', PrintName='Product Category Mapping Mappings',Updated=TO_TIMESTAMP('2022-11-23 17:29:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_CH'
;

-- 2022-11-23T15:29:56.472Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_CH') 
;

-- Element: null
-- 2022-11-23T15:30:03.625Z
UPDATE AD_Element_Trl SET Name='Product Category Mapping', PrintName='Product Category Mapping',Updated=TO_TIMESTAMP('2022-11-23 17:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_DE'
;

-- 2022-11-23T15:30:03.628Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581723,'de_DE') 
;

-- 2022-11-23T15:30:03.630Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_DE') 
;

-- Element: null
-- 2022-11-23T15:30:08.019Z
UPDATE AD_Element_Trl SET Name='Product Category Mapping', PrintName='Product Category Mapping',Updated=TO_TIMESTAMP('2022-11-23 17:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='en_US'
;

-- 2022-11-23T15:30:08.020Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'en_US') 
;

-- Element: null
-- 2022-11-23T15:30:11.137Z
UPDATE AD_Element_Trl SET PrintName='Product Category Mapping',Updated=TO_TIMESTAMP('2022-11-23 17:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_CH'
;

-- 2022-11-23T15:30:11.139Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_CH') 
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Product Category Mapping(546682,de.metas.externalreference) -> External value
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T15:30:37.838Z
UPDATE AD_Field SET AD_Name_ID=581718, Description=NULL, Help=NULL, Name='External value',Updated=TO_TIMESTAMP('2022-11-23 17:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708184
;

-- 2022-11-23T15:30:37.840Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581718) 
;

-- 2022-11-23T15:30:37.851Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708184
;

-- 2022-11-23T15:30:37.855Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708184)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Product Category Mapping(546682,de.metas.externalreference) -> Produkt Kategorie
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T15:31:08.151Z
UPDATE AD_Field SET AD_Name_ID=453, Description='Kategorie eines Produktes', Help='Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.', Name='Produkt Kategorie',Updated=TO_TIMESTAMP('2022-11-23 17:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708180
;

-- 2022-11-23T15:31:08.152Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2022-11-23T15:31:08.164Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708180
;

-- 2022-11-23T15:31:08.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708180)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Product Category Mapping(546682,de.metas.externalreference) -> main -> 10 -> externalRef.External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T15:31:30.694Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-23 17:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613555
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Product Category Mapping(546682,de.metas.externalreference) -> main -> 10 -> externalRef.Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T15:31:30.698Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-23 17:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613556
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Product Category Mapping(546682,de.metas.externalreference) -> main -> 20 -> flags.Aktiv
-- Column: S_ExternalReference.IsActive
-- 2022-11-23T15:31:30.703Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-23 17:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613557
;

-- 2022-11-23T15:32:33.012Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581726,0,TO_TIMESTAMP('2022-11-23 17:32:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','UOM','UOM',TO_TIMESTAMP('2022-11-23 17:32:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:32:33.014Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581726 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> UOM
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T15:32:50.994Z
UPDATE AD_Field SET AD_Name_ID=581726, Description=NULL, Help=NULL, Name='UOM',Updated=TO_TIMESTAMP('2022-11-23 17:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708169
;

-- 2022-11-23T15:32:50.995Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581726) 
;

-- 2022-11-23T15:32:50.999Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708169
;

-- 2022-11-23T15:32:51.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708169)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> External value
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T15:33:02.157Z
UPDATE AD_Field SET AD_Name_ID=581718, Description=NULL, Help=NULL, Name='External value',Updated=TO_TIMESTAMP('2022-11-23 17:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708163
;

-- 2022-11-23T15:33:02.158Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581718) 
;

-- 2022-11-23T15:33:02.163Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708163
;

-- 2022-11-23T15:33:02.163Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708163)
;

-- Element: null
-- 2022-11-23T15:33:22.566Z
UPDATE AD_Element_Trl SET Name='Product Type Mapping', PrintName='Product Type Mapping',Updated=TO_TIMESTAMP('2022-11-23 17:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581724 AND AD_Language='en_US'
;

-- 2022-11-23T15:33:22.568Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581724,'en_US') 
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductType Mappings(546683,de.metas.externalsystem) -> Produktart
-- Column: ProductType_External_Mapping.Value
-- 2022-11-23T15:33:50.893Z
UPDATE AD_Field SET AD_Name_ID=1899, Description='Art von Produkt', Help='Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.', Name='Produktart',Updated=TO_TIMESTAMP('2022-11-23 17:33:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708190
;

-- 2022-11-23T15:33:50.894Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1899) 
;

-- 2022-11-23T15:33:50.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708190
;

-- 2022-11-23T15:33:50.900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708190)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType Mappings(546683,de.metas.externalsystem) -> main -> 10 -> external.External value
-- Column: ProductType_External_Mapping.ExternalValue
-- 2022-11-23T15:34:00.541Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-23 17:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613560
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType Mappings(546683,de.metas.externalsystem) -> main -> 10 -> external.Suchschlüssel
-- Column: ProductType_External_Mapping.Value
-- 2022-11-23T15:34:00.545Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-23 17:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613561
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductType Mappings(546683,de.metas.externalsystem) -> main -> 20 -> flags.Aktiv
-- Column: ProductType_External_Mapping.IsActive
-- 2022-11-23T15:34:00.549Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-23 17:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613562
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10 -> externalRef.External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T15:34:10.682Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-23 17:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613549
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10 -> externalRef.Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T15:34:10.687Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-23 17:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613550
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 20 -> flags.Aktiv
-- Column: S_ExternalReference.IsActive
-- 2022-11-23T15:34:10.691Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-23 17:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613551
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> main -> 10 -> externalRef.External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-24T07:35:12.021Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613548
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Product Category Mapping(546682,de.metas.externalreference) -> main -> 10 -> externalRef.External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-24T07:35:25.483Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613554
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Externes System
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-11-24T07:36:04.372Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-24 09:36:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708036
;


-- Element: null
-- 2022-11-24T07:43:09.096Z
UPDATE AD_Element_Trl SET Name='Produktkategorie-Zuordnung', PrintName='Produktkategorie-Zuordnung',Updated=TO_TIMESTAMP('2022-11-24 09:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_CH'
;

-- 2022-11-24T07:43:09.141Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_CH')
;

-- Element: null
-- 2022-11-24T07:43:14.701Z
UPDATE AD_Element_Trl SET Name='Produktkategorie-Zuordnung', PrintName='Produktkategorie-Zuordnung',Updated=TO_TIMESTAMP('2022-11-24 09:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_DE'
;

-- 2022-11-24T07:43:14.703Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581723,'de_DE')
;

-- 2022-11-24T07:43:14.741Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_DE')
;

-- Element: null
-- 2022-11-24T07:44:00.213Z
UPDATE AD_Element_Trl SET Name='Produktkategorie Zuordnung', PrintName='Produktkategorie Zuordnung',Updated=TO_TIMESTAMP('2022-11-24 09:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_CH'
;

-- 2022-11-24T07:44:00.215Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_CH')
;

-- Element: null
-- 2022-11-24T07:44:06.109Z
UPDATE AD_Element_Trl SET Name='Produktkategorie Zuordnung', PrintName='Produktkategorie Zuordnung',Updated=TO_TIMESTAMP('2022-11-24 09:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581723 AND AD_Language='de_DE'
;

-- 2022-11-24T07:44:06.111Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581723,'de_DE')
;

-- 2022-11-24T07:44:06.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581723,'de_DE')
;

-- Element: null
-- 2022-11-24T07:44:51.233Z
UPDATE AD_Element_Trl SET Name='Produkttyp Zuordnung', PrintName='Produkttyp Zuordnung',Updated=TO_TIMESTAMP('2022-11-24 09:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581724 AND AD_Language='de_CH'
;

-- 2022-11-24T07:44:51.235Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581724,'de_CH')
;

-- Element: null
-- 2022-11-24T07:44:56.110Z
UPDATE AD_Element_Trl SET Name='Produkttyp Zuordnung', PrintName='Produkttyp Zuordnung',Updated=TO_TIMESTAMP('2022-11-24 09:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581724 AND AD_Language='de_DE'
;

-- 2022-11-24T07:44:56.111Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581724,'de_DE')
;

-- 2022-11-24T07:44:56.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581724,'de_DE')
;

-- Element: ExternalValue
-- 2022-11-24T07:45:37.518Z
UPDATE AD_Element_Trl SET Name='Außenwert', PrintName='Außenwert',Updated=TO_TIMESTAMP('2022-11-24 09:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581718 AND AD_Language='de_CH'
;

-- 2022-11-24T07:45:37.520Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581718,'de_CH')
;

-- Element: ExternalValue
-- 2022-11-24T07:45:42.631Z
UPDATE AD_Element_Trl SET Name='Außenwert', PrintName='Außenwert',Updated=TO_TIMESTAMP('2022-11-24 09:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581718 AND AD_Language='de_DE'
;

-- 2022-11-24T07:45:42.633Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581718,'de_DE')
;

-- 2022-11-24T07:45:42.665Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581718,'de_DE')
;

-- Element: null
-- 2022-11-24T07:46:47.336Z
UPDATE AD_Element_Trl SET Name='SAP Einstellungen', PrintName='SAP Einstellungen',Updated=TO_TIMESTAMP('2022-11-24 09:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581725 AND AD_Language='de_CH'
;

-- 2022-11-24T07:46:47.337Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581725,'de_CH')
;

-- Element: null
-- 2022-11-24T07:46:50.921Z
UPDATE AD_Element_Trl SET Name='SAP Einstellungen', PrintName='SAP Einstellungen',Updated=TO_TIMESTAMP('2022-11-24 09:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581725 AND AD_Language='de_DE'
;

-- 2022-11-24T07:46:50.923Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581725,'de_DE')
;

-- 2022-11-24T07:46:50.945Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581725,'de_DE')
;

-- 2022-11-24T14:05:58.238Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581731,0,TO_TIMESTAMP('2022-11-24 16:05:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SAP Material Group','SAP Material Group',TO_TIMESTAMP('2022-11-24 16:05:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:05:58.249Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581731 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-24T14:06:12.466Z
UPDATE AD_Element_Trl SET Name='SAP-Materialgruppe', PrintName='SAP-Materialgruppe',Updated=TO_TIMESTAMP('2022-11-24 16:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581731 AND AD_Language='de_CH'
;

-- 2022-11-24T14:06:12.486Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581731,'de_CH')
;

-- Element: null
-- 2022-11-24T14:06:16.107Z
UPDATE AD_Element_Trl SET Name='SAP-Materialgruppe', PrintName='SAP-Materialgruppe',Updated=TO_TIMESTAMP('2022-11-24 16:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581731 AND AD_Language='de_DE'
;

-- 2022-11-24T14:06:16.108Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581731,'de_DE')
;

-- 2022-11-24T14:06:16.109Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581731,'de_DE')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Produkttyp Zuordnung(546683,de.metas.externalsystem) -> SAP-Materialgruppe
-- Column: ProductType_External_Mapping.ExternalValue
-- 2022-11-24T14:06:38.423Z
UPDATE AD_Field SET AD_Name_ID=581731, Description=NULL, Help=NULL, Name='SAP-Materialgruppe',Updated=TO_TIMESTAMP('2022-11-24 16:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708188
;

-- 2022-11-24T14:06:38.425Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581731)
;

-- 2022-11-24T14:06:38.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708188
;

-- 2022-11-24T14:06:38.444Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708188)
;

-- 2022-11-24T14:07:28.651Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581732,0,TO_TIMESTAMP('2022-11-24 16:07:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SAP Material Type','SAP Material Type',TO_TIMESTAMP('2022-11-24 16:07:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:07:28.652Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581732 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-24T14:08:22.296Z
UPDATE AD_Element_Trl SET Name='SAP Materialtyp', PrintName='SAP Materialtyp',Updated=TO_TIMESTAMP('2022-11-24 16:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581732 AND AD_Language='de_CH'
;

-- 2022-11-24T14:08:22.298Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581732,'de_CH')
;

-- Element: null
-- 2022-11-24T14:08:25.548Z
UPDATE AD_Element_Trl SET Name='SAP Materialtyp', PrintName='SAP Materialtyp',Updated=TO_TIMESTAMP('2022-11-24 16:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581732 AND AD_Language='de_DE'
;

-- 2022-11-24T14:08:25.550Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581732,'de_DE')
;

-- 2022-11-24T14:08:25.551Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581732,'de_DE')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Produktkategorie Zuordnung(546682,de.metas.externalreference) -> SAP Materialtyp
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-24T14:08:43.706Z
UPDATE AD_Field SET AD_Name_ID=581732, Description=NULL, Help=NULL, Name='SAP Materialtyp',Updated=TO_TIMESTAMP('2022-11-24 16:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708184
;

-- 2022-11-24T14:08:43.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581732)
;

-- 2022-11-24T14:08:43.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708184
;

-- 2022-11-24T14:08:43.709Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708184)
;

-- 2022-11-24T14:09:36.185Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581733,0,TO_TIMESTAMP('2022-11-24 16:09:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SAP Base Unit of Measure','SAP Base Unit of Measure',TO_TIMESTAMP('2022-11-24 16:09:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:09:36.189Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581733 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-24T14:14:03.113Z
UPDATE AD_Element_Trl SET Name='SAP Basismengeneinheit', PrintName='SAP Basismengeneinheit',Updated=TO_TIMESTAMP('2022-11-24 16:14:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581733 AND AD_Language='de_CH'
;

-- 2022-11-24T14:14:03.115Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581733,'de_CH')
;

-- Element: null
-- 2022-11-24T14:14:07.066Z
UPDATE AD_Element_Trl SET Name='SAP Basismengeneinheit', PrintName='SAP Basismengeneinheit',Updated=TO_TIMESTAMP('2022-11-24 16:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581733 AND AD_Language='de_DE'
;

-- 2022-11-24T14:14:07.068Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581733,'de_DE')
;

-- 2022-11-24T14:14:07.069Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581733,'de_DE')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung(546681,de.metas.externalreference) -> SAP Basismengeneinheit
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-24T14:14:46.278Z
UPDATE AD_Field SET AD_Name_ID=581733, Description=NULL, Help=NULL, Name='SAP Basismengeneinheit',Updated=TO_TIMESTAMP('2022-11-24 16:14:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708163
;

-- 2022-11-24T14:14:46.279Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581733)
;

-- 2022-11-24T14:14:46.282Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708163
;

-- 2022-11-24T14:14:46.287Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708163)
;



