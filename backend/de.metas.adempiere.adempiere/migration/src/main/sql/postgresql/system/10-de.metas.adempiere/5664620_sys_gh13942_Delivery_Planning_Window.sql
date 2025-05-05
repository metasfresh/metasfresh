-- Window: Delivery Planning, InternalName=null
-- 2022-11-16T15:09:13.353Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581677,0,541632,TO_TIMESTAMP('2022-11-16 17:09:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Delivery Planning','N',TO_TIMESTAMP('2022-11-16 17:09:12','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-11-16T15:09:13.429Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541632 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-16T15:09:13.488Z
/* DDL */  select update_window_translation_from_ad_element(581677) 
;

-- 2022-11-16T15:09:13.548Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541632
;

-- 2022-11-16T15:09:13.578Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541632)
;

-- Tab: Delivery Planning(541632,D) -> Delivery Planning
-- Table: M_Delivery_Planning
-- 2022-11-16T15:10:05.314Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581677,0,546674,542259,541632,'Y',TO_TIMESTAMP('2022-11-16 17:10:04','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Delivery_Planning','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Delivery Planning','N',10,0,TO_TIMESTAMP('2022-11-16 17:10:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:10:05.343Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546674 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-16T15:10:05.373Z
/* DDL */  select update_tab_translation_from_ad_element(581677) 
;

-- 2022-11-16T15:10:05.407Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546674)
;

-- Tab: Delivery Planning(541632,D) -> Delivery Planning(546674,D)
-- UI Section: main
-- 2022-11-16T15:10:33.164Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546674,545299,TO_TIMESTAMP('2022-11-16 17:10:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-16 17:10:32','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-16T15:10:33.192Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545299 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 10
-- 2022-11-16T15:10:33.568Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546452,545299,TO_TIMESTAMP('2022-11-16 17:10:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-16 17:10:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 20
-- 2022-11-16T15:10:33.909Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546453,545299,TO_TIMESTAMP('2022-11-16 17:10:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-16 17:10:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10
-- UI Element Group: default
-- 2022-11-16T15:10:34.312Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546452,550027,TO_TIMESTAMP('2022-11-16 17:10:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-16 17:10:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Delivery Planning
-- Column: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2022-11-16T15:11:16.843Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584986,708068,0,546674,TO_TIMESTAMP('2022-11-16 17:11:16','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Delivery Planning',TO_TIMESTAMP('2022-11-16 17:11:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:16.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:16.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581677) 
;

-- 2022-11-16T15:11:16.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708068
;

-- 2022-11-16T15:11:16.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708068)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Active
-- Column: M_Delivery_Planning.IsActive
-- 2022-11-16T15:11:17.491Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584990,708069,0,546674,TO_TIMESTAMP('2022-11-16 17:11:17','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','Active',TO_TIMESTAMP('2022-11-16 17:11:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:17.519Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:17.549Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-16T15:11:18.275Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708069
;

-- 2022-11-16T15:11:18.303Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708069)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Created
-- Column: M_Delivery_Planning.Created
-- 2022-11-16T15:11:18.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584991,708070,0,546674,TO_TIMESTAMP('2022-11-16 17:11:18','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',7,'D','The Created field indicates the date that this record was created.','Y','Y','N','N','N','N','N','Created',TO_TIMESTAMP('2022-11-16 17:11:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:18.897Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:18.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2022-11-16T15:11:19.068Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708070
;

-- 2022-11-16T15:11:19.097Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708070)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Client
-- Column: M_Delivery_Planning.AD_Client_ID
-- 2022-11-16T15:11:19.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584992,708071,0,546674,TO_TIMESTAMP('2022-11-16 17:11:19','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-11-16 17:11:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:19.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:19.681Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-16T15:11:20.063Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708071
;

-- 2022-11-16T15:11:20.092Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708071)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Organisation
-- Column: M_Delivery_Planning.AD_Org_ID
-- 2022-11-16T15:11:20.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584993,708072,0,546674,TO_TIMESTAMP('2022-11-16 17:11:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-11-16 17:11:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:20.666Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:20.694Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-16T15:11:20.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708072
;

-- 2022-11-16T15:11:20.986Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708072)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Created By
-- Column: M_Delivery_Planning.CreatedBy
-- 2022-11-16T15:11:21.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584994,708073,0,546674,TO_TIMESTAMP('2022-11-16 17:11:21','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','Y','N','N','N','N','N','Created By',TO_TIMESTAMP('2022-11-16 17:11:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:21.558Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:21.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2022-11-16T15:11:21.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708073
;

-- 2022-11-16T15:11:21.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708073)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Updated
-- Column: M_Delivery_Planning.Updated
-- 2022-11-16T15:11:22.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584995,708074,0,546674,TO_TIMESTAMP('2022-11-16 17:11:21','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated',29,'D','The Updated field indicates the date that this record was updated.','Y','Y','N','N','N','N','N','Updated',TO_TIMESTAMP('2022-11-16 17:11:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:22.218Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:22.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2022-11-16T15:11:22.346Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708074
;

-- 2022-11-16T15:11:22.374Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708074)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Updated By
-- Column: M_Delivery_Planning.UpdatedBy
-- 2022-11-16T15:11:22.899Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584996,708075,0,546674,TO_TIMESTAMP('2022-11-16 17:11:22','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records',10,'D','The Updated By field indicates the user who updated this record.','Y','Y','N','N','N','N','N','Updated By',TO_TIMESTAMP('2022-11-16 17:11:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:22.930Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:22.959Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2022-11-16T15:11:23.048Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708075
;

-- 2022-11-16T15:11:23.076Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708075)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2022-11-16T15:11:23.573Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585005,708076,0,546674,TO_TIMESTAMP('2022-11-16 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Type',TO_TIMESTAMP('2022-11-16 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:23.602Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:23.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581679) 
;

-- 2022-11-16T15:11:23.659Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708076
;

-- 2022-11-16T15:11:23.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708076)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2022-11-16T15:11:24.175Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585006,708077,0,546674,TO_TIMESTAMP('2022-11-16 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','B2B',TO_TIMESTAMP('2022-11-16 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:24.203Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:24.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581680) 
;

-- 2022-11-16T15:11:24.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708077
;

-- 2022-11-16T15:11:24.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708077)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Section Code
-- Column: M_Delivery_Planning.M_SectionCode_ID
-- 2022-11-16T15:11:24.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585007,708078,0,546674,TO_TIMESTAMP('2022-11-16 17:11:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-11-16 17:11:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:24.850Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:24.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-11-16T15:11:24.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708078
;

-- 2022-11-16T15:11:24.949Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708078)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Incoterms
-- Column: M_Delivery_Planning.C_Incoterms_ID
-- 2022-11-16T15:11:25.427Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585008,708079,0,546674,TO_TIMESTAMP('2022-11-16 17:11:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2022-11-16 17:11:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:25.455Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:25.524Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927) 
;

-- 2022-11-16T15:11:25.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708079
;

-- 2022-11-16T15:11:25.584Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708079)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Order Document No
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2022-11-16T15:11:26.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585009,708080,0,546674,TO_TIMESTAMP('2022-11-16 17:11:25','YYYY-MM-DD HH24:MI:SS'),100,'Document Number of the Order',250,'D','Y','Y','N','N','N','N','N','Order Document No',TO_TIMESTAMP('2022-11-16 17:11:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:26.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:26.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543353) 
;

-- 2022-11-16T15:11:26.155Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708080
;

-- 2022-11-16T15:11:26.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708080)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Order Reference
-- Column: M_Delivery_Planning.POReference
-- 2022-11-16T15:11:26.741Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585010,708081,0,546674,TO_TIMESTAMP('2022-11-16 17:11:26','YYYY-MM-DD HH24:MI:SS'),100,'Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner',250,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','Order Reference',TO_TIMESTAMP('2022-11-16 17:11:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:26.771Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:26.800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2022-11-16T15:11:26.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708081
;

-- 2022-11-16T15:11:26.876Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708081)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Partner Name
-- Column: M_Delivery_Planning.BPartnerName
-- 2022-11-16T15:11:27.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585011,708082,0,546674,TO_TIMESTAMP('2022-11-16 17:11:26','YYYY-MM-DD HH24:MI:SS'),100,5000,'D','Y','Y','N','N','N','N','N','Partner Name',TO_TIMESTAMP('2022-11-16 17:11:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:27.396Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:27.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350) 
;

-- 2022-11-16T15:11:27.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708082
;

-- 2022-11-16T15:11:27.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708082)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Business Partner
-- Column: M_Delivery_Planning.C_BPartner_ID
-- 2022-11-16T15:11:28.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585012,708083,0,546674,TO_TIMESTAMP('2022-11-16 17:11:27','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2022-11-16 17:11:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:28.052Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:28.083Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-11-16T15:11:28.134Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708083
;

-- 2022-11-16T15:11:28.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708083)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Location
-- Column: M_Delivery_Planning.C_BPartner_Location_ID
-- 2022-11-16T15:11:28.683Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585013,708084,0,546674,TO_TIMESTAMP('2022-11-16 17:11:28','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Location',TO_TIMESTAMP('2022-11-16 17:11:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:28.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:28.741Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2022-11-16T15:11:28.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708084
;

-- 2022-11-16T15:11:28.806Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708084)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Ship-to location
-- Column: M_Delivery_Planning.ShipToLocation_Name
-- 2022-11-16T15:11:29.282Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585014,708085,0,546674,TO_TIMESTAMP('2022-11-16 17:11:28','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Ship-to location',TO_TIMESTAMP('2022-11-16 17:11:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:29.311Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:29.341Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581681) 
;

-- 2022-11-16T15:11:29.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708085
;

-- 2022-11-16T15:11:29.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708085)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Product Name
-- Column: M_Delivery_Planning.ProductName
-- 2022-11-16T15:11:29.937Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585015,708086,0,546674,TO_TIMESTAMP('2022-11-16 17:11:29','YYYY-MM-DD HH24:MI:SS'),100,'Name of the Product',5000,'D','Y','Y','N','N','N','N','N','Product Name',TO_TIMESTAMP('2022-11-16 17:11:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:29.965Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:29.994Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659) 
;

-- 2022-11-16T15:11:30.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708086
;

-- 2022-11-16T15:11:30.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708086)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Product Value
-- Column: M_Delivery_Planning.ProductValue
-- 2022-11-16T15:11:30.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585016,708087,0,546674,TO_TIMESTAMP('2022-11-16 17:11:30','YYYY-MM-DD HH24:MI:SS'),100,'Product identifier; "val-<search key>", "ext-<external id>" or internal M_Product_ID',250,'D','','Y','Y','N','N','N','N','N','Product Value',TO_TIMESTAMP('2022-11-16 17:11:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:30.655Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:30.683Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1675) 
;

-- 2022-11-16T15:11:30.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708087
;

-- 2022-11-16T15:11:30.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708087)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Grade
-- Column: M_Delivery_Planning.Grade
-- 2022-11-16T15:11:31.218Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585017,708088,0,546674,TO_TIMESTAMP('2022-11-16 17:11:30','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Grade',TO_TIMESTAMP('2022-11-16 17:11:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:31.246Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:31.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581693) 
;

-- 2022-11-16T15:11:31.306Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708088
;

-- 2022-11-16T15:11:31.339Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708088)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-11-16T15:11:31.898Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585018,708090,0,546674,TO_TIMESTAMP('2022-11-16 17:11:31','YYYY-MM-DD HH24:MI:SS'),100,'Qty Ordered',10,'D','','Y','Y','N','N','N','N','N','Qty Ordered',TO_TIMESTAMP('2022-11-16 17:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:31.926Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:31.954Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531) 
;

-- 2022-11-16T15:11:31.989Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708090
;

-- 2022-11-16T15:11:32.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708090)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2022-11-16T15:11:32.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585019,708091,0,546674,TO_TIMESTAMP('2022-11-16 17:11:32','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Qty Total Open',TO_TIMESTAMP('2022-11-16 17:11:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:32.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:32.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581682) 
;

-- 2022-11-16T15:11:32.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708091
;

-- 2022-11-16T15:11:32.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708091)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Warehouse Name
-- Column: M_Delivery_Planning.WarehouseName
-- 2022-11-16T15:11:33.180Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585020,708092,0,546674,TO_TIMESTAMP('2022-11-16 17:11:32','YYYY-MM-DD HH24:MI:SS'),100,'',500,'D','Y','Y','N','N','N','N','N','Warehouse Name',TO_TIMESTAMP('2022-11-16 17:11:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:33.208Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:33.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2280) 
;

-- 2022-11-16T15:11:33.268Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708092
;

-- 2022-11-16T15:11:33.299Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708092)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Warehouse
-- Column: M_Delivery_Planning.M_Warehouse_ID
-- 2022-11-16T15:11:33.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585021,708093,0,546674,TO_TIMESTAMP('2022-11-16 17:11:33','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point',10,'D','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.','Y','Y','N','N','N','N','N','Warehouse',TO_TIMESTAMP('2022-11-16 17:11:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:33.853Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:33.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2022-11-16T15:11:33.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708093
;

-- 2022-11-16T15:11:33.951Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708093)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Order Status
-- Column: M_Delivery_Planning.OrderStatus
-- 2022-11-16T15:11:34.430Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585022,708094,0,546674,TO_TIMESTAMP('2022-11-16 17:11:34','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Order Status',TO_TIMESTAMP('2022-11-16 17:11:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:34.458Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:34.487Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581683) 
;

-- 2022-11-16T15:11:34.517Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708094
;

-- 2022-11-16T15:11:34.606Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708094)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2022-11-16T15:11:35.079Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585023,708095,0,546674,TO_TIMESTAMP('2022-11-16 17:11:34','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Planned Delivery Date',TO_TIMESTAMP('2022-11-16 17:11:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:35.107Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:35.135Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581686) 
;

-- 2022-11-16T15:11:35.164Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708095
;

-- 2022-11-16T15:11:35.191Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708095)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2022-11-16T15:11:35.659Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585024,708096,0,546674,TO_TIMESTAMP('2022-11-16 17:11:35','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Actual Delivery Date',TO_TIMESTAMP('2022-11-16 17:11:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:35.689Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:35.717Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581687) 
;

-- 2022-11-16T15:11:35.748Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708096
;

-- 2022-11-16T15:11:35.776Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708096)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2022-11-16T15:11:36.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585025,708097,0,546674,TO_TIMESTAMP('2022-11-16 17:11:35','YYYY-MM-DD HH24:MI:SS'),100,'Internal Release Number',250,'D','Y','Y','N','N','N','N','N','Release No',TO_TIMESTAMP('2022-11-16 17:11:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:36.273Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:36.301Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2122) 
;

-- 2022-11-16T15:11:36.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708097
;

-- 2022-11-16T15:11:36.361Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708097)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-11-16T15:11:36.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585026,708098,0,546674,TO_TIMESTAMP('2022-11-16 17:11:36','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Planned Loading Date',TO_TIMESTAMP('2022-11-16 17:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:36.914Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:36.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581688) 
;

-- 2022-11-16T15:11:36.974Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708098
;

-- 2022-11-16T15:11:37.001Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708098)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-11-16T15:11:37.475Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585027,708099,0,546674,TO_TIMESTAMP('2022-11-16 17:11:37','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Actual Loading Date',TO_TIMESTAMP('2022-11-16 17:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:37.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:37.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581689) 
;

-- 2022-11-16T15:11:37.597Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708099
;

-- 2022-11-16T15:11:37.633Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708099)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-11-16T15:11:38.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585028,708100,0,546674,TO_TIMESTAMP('2022-11-16 17:11:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Actual Load Qty',TO_TIMESTAMP('2022-11-16 17:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:38.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:38.175Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581690) 
;

-- 2022-11-16T15:11:38.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708100
;

-- 2022-11-16T15:11:38.233Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708100)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-11-16T15:11:38.770Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585029,708101,0,546674,TO_TIMESTAMP('2022-11-16 17:11:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Actual Delivered Qty',TO_TIMESTAMP('2022-11-16 17:11:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:38.804Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:38.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581691) 
;

-- 2022-11-16T15:11:38.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708101
;

-- 2022-11-16T15:11:38.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708101)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Batch
-- Column: M_Delivery_Planning.Batch
-- 2022-11-16T15:11:39.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585030,708102,0,546674,TO_TIMESTAMP('2022-11-16 17:11:38','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Batch',TO_TIMESTAMP('2022-11-16 17:11:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:39.406Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:39.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581692) 
;

-- 2022-11-16T15:11:39.470Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708102
;

-- 2022-11-16T15:11:39.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708102)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2022-11-16T15:11:40.044Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585031,708103,0,546674,TO_TIMESTAMP('2022-11-16 17:11:39','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Country Of Origin',TO_TIMESTAMP('2022-11-16 17:11:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:40.072Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:40.101Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581694) 
;

-- 2022-11-16T15:11:40.131Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708103
;

-- 2022-11-16T15:11:40.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708103)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Means of Transportation
-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-11-16T15:11:40.717Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585034,708104,0,546674,TO_TIMESTAMP('2022-11-16 17:11:40','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Means of Transportation',TO_TIMESTAMP('2022-11-16 17:11:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:40.748Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:40.776Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581695) 
;

-- 2022-11-16T15:11:40.810Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708104
;

-- 2022-11-16T15:11:40.839Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708104)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Forwarder
-- Column: M_Delivery_Planning.Forwarder
-- 2022-11-16T15:11:41.310Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585035,708105,0,546674,TO_TIMESTAMP('2022-11-16 17:11:40','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Forwarder',TO_TIMESTAMP('2022-11-16 17:11:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:41.342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:41.373Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581696) 
;

-- 2022-11-16T15:11:41.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708105
;

-- 2022-11-16T15:11:41.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708105)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2022-11-16T15:11:41.977Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585036,708106,0,546674,TO_TIMESTAMP('2022-11-16 17:11:41','YYYY-MM-DD HH24:MI:SS'),100,5000,'D','Y','Y','N','N','N','N','N','Transport Details',TO_TIMESTAMP('2022-11-16 17:11:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:42.005Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:42.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581697) 
;

-- 2022-11-16T15:11:42.063Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708106
;

-- 2022-11-16T15:11:42.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708106)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2022-11-16T15:11:42.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585037,708107,0,546674,TO_TIMESTAMP('2022-11-16 17:11:42','YYYY-MM-DD HH24:MI:SS'),100,15,'D','Y','Y','N','N','N','N','N','Way Bill No',TO_TIMESTAMP('2022-11-16 17:11:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:42.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:42.681Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581698) 
;

-- 2022-11-16T15:11:42.715Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708107
;

-- 2022-11-16T15:11:42.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708107)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2022-11-16T15:11:43.233Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585038,708108,0,546674,TO_TIMESTAMP('2022-11-16 17:11:42','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Transport Order',TO_TIMESTAMP('2022-11-16 17:11:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:43.261Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:43.290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581700) 
;

-- 2022-11-16T15:11:43.320Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708108
;

-- 2022-11-16T15:11:43.348Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708108)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Transportation Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2022-11-16T15:11:43.877Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585045,708109,0,546674,TO_TIMESTAMP('2022-11-16 17:11:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Transportation Order',TO_TIMESTAMP('2022-11-16 17:11:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:43.906Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:43.934Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2022-11-16T15:11:43.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708109
;

-- 2022-11-16T15:11:43.994Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708109)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Product
-- Column: M_Delivery_Planning.M_Product_ID
-- 2022-11-16T15:11:44.475Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585046,708110,0,546674,TO_TIMESTAMP('2022-11-16 17:11:44','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item',10,'D','Identifies an item which is either purchased or sold in this organization.','Y','Y','N','N','N','N','N','Product',TO_TIMESTAMP('2022-11-16 17:11:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:44.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:44.532Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-11-16T15:11:44.691Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708110
;

-- 2022-11-16T15:11:44.718Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708110)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Sales order
-- Column: M_Delivery_Planning.C_Order_ID
-- 2022-11-16T15:11:45.200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585047,708111,0,546674,TO_TIMESTAMP('2022-11-16 17:11:44','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','Sales order',TO_TIMESTAMP('2022-11-16 17:11:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:45.229Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:45.256Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2022-11-16T15:11:45.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708111
;

-- 2022-11-16T15:11:45.331Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708111)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Material Receipt Candidates
-- Column: M_Delivery_Planning.M_ReceiptSchedule_ID
-- 2022-11-16T15:11:45.880Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585048,708112,0,546674,TO_TIMESTAMP('2022-11-16 17:11:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Material Receipt Candidates',TO_TIMESTAMP('2022-11-16 17:11:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:45.908Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:45.936Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542202) 
;

-- 2022-11-16T15:11:45.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708112
;

-- 2022-11-16T15:11:45.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708112)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Shipment Candidate
-- Column: M_Delivery_Planning.M_ShipmentSchedule_ID
-- 2022-11-16T15:11:46.485Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585049,708113,0,546674,TO_TIMESTAMP('2022-11-16 17:11:46','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Shipment Candidate',TO_TIMESTAMP('2022-11-16 17:11:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:46.514Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:46.543Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221) 
;

-- 2022-11-16T15:11:46.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708113
;

-- 2022-11-16T15:11:46.601Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708113)
;

-- Tab: Delivery Planning(541632,D) -> Delivery Planning(546674,D)
-- UI Section: main
-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 20
-- 2022-11-16T15:18:05.545Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546453
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 10
-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10
-- UI Element Group: default
-- 2022-11-16T15:18:06.023Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550027
;

-- 2022-11-16T15:18:06.245Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546452
;

-- 2022-11-16T15:18:06.300Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545299
;

-- 2022-11-16T15:18:06.466Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545299
;

-- Tab: Delivery Planning(541632,D) -> Delivery Planning(546674,D)
-- UI Section: main
-- 2022-11-16T15:18:26.289Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546674,545300,TO_TIMESTAMP('2022-11-16 17:18:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-16 17:18:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-16T15:18:26.370Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545300 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 10
-- 2022-11-16T15:18:26.710Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546454,545300,TO_TIMESTAMP('2022-11-16 17:18:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-16 17:18:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 20
-- 2022-11-16T15:18:27.053Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546455,545300,TO_TIMESTAMP('2022-11-16 17:18:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-16 17:18:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10
-- UI Element Group: default
-- 2022-11-16T15:18:27.437Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546454,550028,TO_TIMESTAMP('2022-11-16 17:18:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-16 17:18:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Active
-- Column: M_Delivery_Planning.IsActive
-- 2022-11-16T15:18:27.981Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708069,0,546674,550028,613475,'F',TO_TIMESTAMP('2022-11-16 17:18:27','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','Y','Y','N','Active',10,10,0,TO_TIMESTAMP('2022-11-16 17:18:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Created
-- Column: M_Delivery_Planning.Created
-- 2022-11-16T15:18:28.446Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708070,0,546674,550028,613476,'F',TO_TIMESTAMP('2022-11-16 17:18:28','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created','The Created field indicates the date that this record was created.','Y','N','Y','Y','N','Created',20,20,0,TO_TIMESTAMP('2022-11-16 17:18:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Client
-- Column: M_Delivery_Planning.AD_Client_ID
-- 2022-11-16T15:18:28.893Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708071,0,546674,550028,613477,'F',TO_TIMESTAMP('2022-11-16 17:18:28','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','Y','N','Client',30,30,0,TO_TIMESTAMP('2022-11-16 17:18:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Organisation
-- Column: M_Delivery_Planning.AD_Org_ID
-- 2022-11-16T15:18:29.317Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708072,0,546674,550028,613478,'F',TO_TIMESTAMP('2022-11-16 17:18:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','Y','N','Organisation',40,40,0,TO_TIMESTAMP('2022-11-16 17:18:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Created By
-- Column: M_Delivery_Planning.CreatedBy
-- 2022-11-16T15:18:29.821Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708073,0,546674,550028,613479,'F',TO_TIMESTAMP('2022-11-16 17:18:29','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records','The Created By field indicates the user who created this record.','Y','N','Y','Y','N','Created By',50,50,0,TO_TIMESTAMP('2022-11-16 17:18:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Updated
-- Column: M_Delivery_Planning.Updated
-- 2022-11-16T15:18:30.245Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708074,0,546674,550028,613480,'F',TO_TIMESTAMP('2022-11-16 17:18:29','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated','The Updated field indicates the date that this record was updated.','Y','N','Y','Y','N','Updated',60,60,0,TO_TIMESTAMP('2022-11-16 17:18:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Updated By
-- Column: M_Delivery_Planning.UpdatedBy
-- 2022-11-16T15:18:30.705Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708075,0,546674,550028,613481,'F',TO_TIMESTAMP('2022-11-16 17:18:30','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records','The Updated By field indicates the user who updated this record.','Y','N','Y','Y','N','Updated By',70,70,0,TO_TIMESTAMP('2022-11-16 17:18:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2022-11-16T15:18:31.142Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708076,0,546674,550028,613482,'F',TO_TIMESTAMP('2022-11-16 17:18:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Type',80,80,0,TO_TIMESTAMP('2022-11-16 17:18:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2022-11-16T15:18:31.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708077,0,546674,550028,613483,'F',TO_TIMESTAMP('2022-11-16 17:18:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','B2B',90,90,0,TO_TIMESTAMP('2022-11-16 17:18:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Section Code
-- Column: M_Delivery_Planning.M_SectionCode_ID
-- 2022-11-16T15:18:32.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708078,0,546674,550028,613484,'F',TO_TIMESTAMP('2022-11-16 17:18:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Section Code',100,100,0,TO_TIMESTAMP('2022-11-16 17:18:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterms
-- Column: M_Delivery_Planning.C_Incoterms_ID
-- 2022-11-16T15:18:32.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708079,0,546674,550028,613485,'F',TO_TIMESTAMP('2022-11-16 17:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Incoterms',110,110,0,TO_TIMESTAMP('2022-11-16 17:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Document No
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2022-11-16T15:18:32.895Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708080,0,546674,550028,613486,'F',TO_TIMESTAMP('2022-11-16 17:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Document Number of the Order','Y','N','Y','Y','N','Order Document No',120,120,0,TO_TIMESTAMP('2022-11-16 17:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Reference
-- Column: M_Delivery_Planning.POReference
-- 2022-11-16T15:18:33.323Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708081,0,546674,550028,613487,'F',TO_TIMESTAMP('2022-11-16 17:18:32','YYYY-MM-DD HH24:MI:SS'),100,'Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','Y','N','Order Reference',130,130,0,TO_TIMESTAMP('2022-11-16 17:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Partner Name
-- Column: M_Delivery_Planning.BPartnerName
-- 2022-11-16T15:18:33.821Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708082,0,546674,550028,613488,'F',TO_TIMESTAMP('2022-11-16 17:18:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Partner Name',140,140,0,TO_TIMESTAMP('2022-11-16 17:18:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Business Partner
-- Column: M_Delivery_Planning.C_BPartner_ID
-- 2022-11-16T15:18:34.247Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708083,0,546674,550028,613489,'F',TO_TIMESTAMP('2022-11-16 17:18:33','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','N','Business Partner',150,150,0,TO_TIMESTAMP('2022-11-16 17:18:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:18:34.667Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,708084,0,541664,613489,TO_TIMESTAMP('2022-11-16 17:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-16 17:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Ship-to location
-- Column: M_Delivery_Planning.ShipToLocation_Name
-- 2022-11-16T15:18:35.089Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708085,0,546674,550028,613490,'F',TO_TIMESTAMP('2022-11-16 17:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ship-to location',160,160,0,TO_TIMESTAMP('2022-11-16 17:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Name
-- Column: M_Delivery_Planning.ProductName
-- 2022-11-16T15:18:35.589Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708086,0,546674,550028,613491,'F',TO_TIMESTAMP('2022-11-16 17:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Name of the Product','Y','N','Y','Y','N','Product Name',170,170,0,TO_TIMESTAMP('2022-11-16 17:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Value
-- Column: M_Delivery_Planning.ProductValue
-- 2022-11-16T15:18:36.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708087,0,546674,550028,613492,'F',TO_TIMESTAMP('2022-11-16 17:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Product identifier; "val-<search key>", "ext-<external id>" or internal M_Product_ID','','Y','N','Y','Y','N','Product Value',180,180,0,TO_TIMESTAMP('2022-11-16 17:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Grade
-- Column: M_Delivery_Planning.Grade
-- 2022-11-16T15:18:36.485Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708088,0,546674,550028,613493,'F',TO_TIMESTAMP('2022-11-16 17:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Grade',190,190,0,TO_TIMESTAMP('2022-11-16 17:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-11-16T15:18:36.916Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708090,0,546674,550028,613494,'F',TO_TIMESTAMP('2022-11-16 17:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Qty Ordered','','Y','N','Y','Y','N','Qty Ordered',200,200,0,TO_TIMESTAMP('2022-11-16 17:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2022-11-16T15:18:37.344Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708091,0,546674,550028,613495,'F',TO_TIMESTAMP('2022-11-16 17:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Qty Total Open',210,210,0,TO_TIMESTAMP('2022-11-16 17:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Warehouse Name
-- Column: M_Delivery_Planning.WarehouseName
-- 2022-11-16T15:18:37.833Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708092,0,546674,550028,613496,'F',TO_TIMESTAMP('2022-11-16 17:18:37','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Warehouse Name',220,220,0,TO_TIMESTAMP('2022-11-16 17:18:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Warehouse
-- Column: M_Delivery_Planning.M_Warehouse_ID
-- 2022-11-16T15:18:38.274Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708093,0,546674,550028,613497,'F',TO_TIMESTAMP('2022-11-16 17:18:37','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.','Y','N','Y','Y','N','Warehouse',230,230,0,TO_TIMESTAMP('2022-11-16 17:18:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Status
-- Column: M_Delivery_Planning.OrderStatus
-- 2022-11-16T15:18:38.761Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708094,0,546674,550028,613498,'F',TO_TIMESTAMP('2022-11-16 17:18:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Order Status',240,240,0,TO_TIMESTAMP('2022-11-16 17:18:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2022-11-16T15:18:39.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708095,0,546674,550028,613499,'F',TO_TIMESTAMP('2022-11-16 17:18:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Planned Delivery Date',250,250,0,TO_TIMESTAMP('2022-11-16 17:18:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2022-11-16T15:18:39.656Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708096,0,546674,550028,613500,'F',TO_TIMESTAMP('2022-11-16 17:18:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Actual Delivery Date',260,260,0,TO_TIMESTAMP('2022-11-16 17:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2022-11-16T15:18:40.081Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708097,0,546674,550028,613501,'F',TO_TIMESTAMP('2022-11-16 17:18:39','YYYY-MM-DD HH24:MI:SS'),100,'Internal Release Number','Y','N','Y','Y','N','Release No',270,270,0,TO_TIMESTAMP('2022-11-16 17:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-11-16T15:18:40.516Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708098,0,546674,550028,613502,'F',TO_TIMESTAMP('2022-11-16 17:18:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Planned Loading Date',280,280,0,TO_TIMESTAMP('2022-11-16 17:18:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-11-16T15:18:40.930Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708099,0,546674,550028,613503,'F',TO_TIMESTAMP('2022-11-16 17:18:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Actual Loading Date',290,290,0,TO_TIMESTAMP('2022-11-16 17:18:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-11-16T15:18:41.346Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708100,0,546674,550028,613504,'F',TO_TIMESTAMP('2022-11-16 17:18:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Actual Load Qty',300,300,0,TO_TIMESTAMP('2022-11-16 17:18:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-11-16T15:18:41.828Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708101,0,546674,550028,613505,'F',TO_TIMESTAMP('2022-11-16 17:18:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Actual Delivered Qty',310,310,0,TO_TIMESTAMP('2022-11-16 17:18:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2022-11-16T15:18:42.258Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708102,0,546674,550028,613506,'F',TO_TIMESTAMP('2022-11-16 17:18:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Batch',320,320,0,TO_TIMESTAMP('2022-11-16 17:18:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2022-11-16T15:18:42.695Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708103,0,546674,550028,613507,'F',TO_TIMESTAMP('2022-11-16 17:18:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Country Of Origin',330,330,0,TO_TIMESTAMP('2022-11-16 17:18:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-11-16T15:18:43.107Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708104,0,546674,550028,613508,'F',TO_TIMESTAMP('2022-11-16 17:18:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Means of Transportation',340,340,0,TO_TIMESTAMP('2022-11-16 17:18:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.Forwarder
-- 2022-11-16T15:18:43.555Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708105,0,546674,550028,613509,'F',TO_TIMESTAMP('2022-11-16 17:18:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Forwarder',350,350,0,TO_TIMESTAMP('2022-11-16 17:18:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2022-11-16T15:18:43.988Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708106,0,546674,550028,613510,'F',TO_TIMESTAMP('2022-11-16 17:18:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Transport Details',360,360,0,TO_TIMESTAMP('2022-11-16 17:18:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2022-11-16T15:18:44.406Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708107,0,546674,550028,613511,'F',TO_TIMESTAMP('2022-11-16 17:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Way Bill No',370,370,0,TO_TIMESTAMP('2022-11-16 17:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2022-11-16T15:18:44.877Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708108,0,546674,550028,613512,'F',TO_TIMESTAMP('2022-11-16 17:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Transport Order',380,380,0,TO_TIMESTAMP('2022-11-16 17:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transportation Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2022-11-16T15:18:45.303Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708109,0,546674,550028,613513,'F',TO_TIMESTAMP('2022-11-16 17:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Transportation Order',390,390,0,TO_TIMESTAMP('2022-11-16 17:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product
-- Column: M_Delivery_Planning.M_Product_ID
-- 2022-11-16T15:18:45.784Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708110,0,546674,550028,613514,'F',TO_TIMESTAMP('2022-11-16 17:18:45','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item','Identifies an item which is either purchased or sold in this organization.','Y','N','Y','Y','N','Product',400,400,0,TO_TIMESTAMP('2022-11-16 17:18:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Sales order
-- Column: M_Delivery_Planning.C_Order_ID
-- 2022-11-16T15:18:46.211Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708111,0,546674,550028,613515,'F',TO_TIMESTAMP('2022-11-16 17:18:45','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','Y','N','Sales order',410,410,0,TO_TIMESTAMP('2022-11-16 17:18:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Material Receipt Candidates
-- Column: M_Delivery_Planning.M_ReceiptSchedule_ID
-- 2022-11-16T15:18:46.699Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708112,0,546674,550028,613516,'F',TO_TIMESTAMP('2022-11-16 17:18:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Material Receipt Candidates',420,420,0,TO_TIMESTAMP('2022-11-16 17:18:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Shipment Candidate
-- Column: M_Delivery_Planning.M_ShipmentSchedule_ID
-- 2022-11-16T15:18:47.145Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708113,0,546674,550028,613517,'F',TO_TIMESTAMP('2022-11-16 17:18:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Shipment Candidate',430,430,0,TO_TIMESTAMP('2022-11-16 17:18:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20
-- UI Element Group: flags
-- 2022-11-16T15:19:50.512Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546455,550029,TO_TIMESTAMP('2022-11-16 17:19:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-16 17:19:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20
-- UI Element Group: dates
-- 2022-11-16T15:19:59.852Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546455,550030,TO_TIMESTAMP('2022-11-16 17:19:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2022-11-16 17:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20
-- UI Element Group: org
-- 2022-11-16T15:20:06.911Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546455,550031,TO_TIMESTAMP('2022-11-16 17:20:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2022-11-16 17:20:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> org.Organisation
-- Column: M_Delivery_Planning.AD_Org_ID
-- 2022-11-16T15:22:20.946Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550031, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613478
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> org.Client
-- Column: M_Delivery_Planning.AD_Client_ID
-- 2022-11-16T15:22:36.197Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550031, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613477
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Created
-- Column: M_Delivery_Planning.Created
-- 2022-11-16T15:22:52.196Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613476
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Created By
-- Column: M_Delivery_Planning.CreatedBy
-- 2022-11-16T15:22:56.472Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613479
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Updated
-- Column: M_Delivery_Planning.Updated
-- 2022-11-16T15:23:00.390Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613480
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Updated By
-- Column: M_Delivery_Planning.UpdatedBy
-- 2022-11-16T15:23:04.656Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613481
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Active
-- Column: M_Delivery_Planning.IsActive
-- 2022-11-16T15:23:39.430Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550029, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613475
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2022-11-16T15:24:00.761Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550029, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:24:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613483
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-11-16T15:24:39.305Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550030, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-11-16T15:24:59.607Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550030, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2022-11-16T15:25:20.309Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550030, SeqNo=30,Updated=TO_TIMESTAMP('2022-11-16 17:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613499
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2022-11-16T15:25:34.787Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550030, SeqNo=40,Updated=TO_TIMESTAMP('2022-11-16 17:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613500
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10
-- UI Element Group: qtys
-- 2022-11-16T15:26:04.080Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546454,550032,TO_TIMESTAMP('2022-11-16 17:26:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','qtys',20,TO_TIMESTAMP('2022-11-16 17:26:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 20
-- 2022-11-16T15:26:17.561Z
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546454
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 10
-- 2022-11-16T15:26:21.787Z
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546455
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 10
-- 2022-11-16T15:26:38.753Z
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546454
;

-- UI Section: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main
-- UI Column: 20
-- 2022-11-16T15:26:42.616Z
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:26:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546455
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2022-11-16T15:28:02.069Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550029, SeqNo=30,Updated=TO_TIMESTAMP('2022-11-16 17:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613482
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> qtys.Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-11-16T15:28:29.628Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550032, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:28:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613494
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> qtys.Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2022-11-16T15:28:38.652Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550032, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613495
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-11-16T15:29:08.889Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550032, SeqNo=30,Updated=TO_TIMESTAMP('2022-11-16 17:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-11-16T15:29:18.807Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550032, SeqNo=40,Updated=TO_TIMESTAMP('2022-11-16 17:29:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20
-- UI Element Group: qtys
-- 2022-11-16T15:29:34.173Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=546455, SeqNo=40,Updated=TO_TIMESTAMP('2022-11-16 17:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550032
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10
-- UI Element Group: links
-- 2022-11-16T15:30:05.706Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546454,550033,TO_TIMESTAMP('2022-11-16 17:30:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','links',20,TO_TIMESTAMP('2022-11-16 17:30:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Business Partner
-- Column: M_Delivery_Planning.C_BPartner_ID
-- 2022-11-16T15:30:31.333Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-16 17:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613489
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Warehouse
-- Column: M_Delivery_Planning.M_Warehouse_ID
-- 2022-11-16T15:30:42.241Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-16 17:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613497
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Transportation Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2022-11-16T15:30:58.843Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=30,Updated=TO_TIMESTAMP('2022-11-16 17:30:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613513
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Product
-- Column: M_Delivery_Planning.M_Product_ID
-- 2022-11-16T15:31:07.971Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=40,Updated=TO_TIMESTAMP('2022-11-16 17:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613514
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Material Receipt Candidates
-- Column: M_Delivery_Planning.M_ReceiptSchedule_ID
-- 2022-11-16T15:31:24.042Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=50,Updated=TO_TIMESTAMP('2022-11-16 17:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613516
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Shipment Candidate
-- Column: M_Delivery_Planning.M_ShipmentSchedule_ID
-- 2022-11-16T15:31:33.997Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=60,Updated=TO_TIMESTAMP('2022-11-16 17:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613517
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Sales order
-- Column: M_Delivery_Planning.C_Order_ID
-- 2022-11-16T15:31:45.036Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550033, SeqNo=70,Updated=TO_TIMESTAMP('2022-11-16 17:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613515
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Active
-- Column: M_Delivery_Planning.IsActive
-- 2022-11-16T15:34:25.050Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613475
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Business Partner
-- Column: M_Delivery_Planning.C_BPartner_ID
-- 2022-11-16T15:34:25.221Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613489
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> org.Client
-- Column: M_Delivery_Planning.AD_Client_ID
-- 2022-11-16T15:34:25.399Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613477
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Material Receipt Candidates
-- Column: M_Delivery_Planning.M_ReceiptSchedule_ID
-- 2022-11-16T15:34:25.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613516
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> org.Organisation
-- Column: M_Delivery_Planning.AD_Org_ID
-- 2022-11-16T15:34:25.751Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613478
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Product
-- Column: M_Delivery_Planning.M_Product_ID
-- 2022-11-16T15:34:25.918Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613514
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Sales order
-- Column: M_Delivery_Planning.C_Order_ID
-- 2022-11-16T15:34:26.089Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613515
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Shipment Candidate
-- Column: M_Delivery_Planning.M_ShipmentSchedule_ID
-- 2022-11-16T15:34:26.315Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613517
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Transportation Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2022-11-16T15:34:26.482Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613513
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Warehouse
-- Column: M_Delivery_Planning.M_Warehouse_ID
-- 2022-11-16T15:34:26.649Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-16 17:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613497
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2022-11-16T15:34:26.816Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-16 17:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613482
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2022-11-16T15:34:26.983Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-16 17:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613483
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Section Code
-- Column: M_Delivery_Planning.M_SectionCode_ID
-- 2022-11-16T15:34:27.206Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-16 17:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613484
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterms
-- Column: M_Delivery_Planning.C_Incoterms_ID
-- 2022-11-16T15:34:27.373Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-16 17:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613485
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Document No
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2022-11-16T15:34:27.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-16 17:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613486
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Reference
-- Column: M_Delivery_Planning.POReference
-- 2022-11-16T15:34:27.708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-16 17:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613487
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Partner Name
-- Column: M_Delivery_Planning.BPartnerName
-- 2022-11-16T15:34:27.876Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-16 17:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613488
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Ship-to location
-- Column: M_Delivery_Planning.ShipToLocation_Name
-- 2022-11-16T15:34:28.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-11-16 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613490
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Name
-- Column: M_Delivery_Planning.ProductName
-- 2022-11-16T15:34:28.267Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-11-16 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613491
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Value
-- Column: M_Delivery_Planning.ProductValue
-- 2022-11-16T15:34:28.435Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-11-16 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613492
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Grade
-- Column: M_Delivery_Planning.Grade
-- 2022-11-16T15:34:28.602Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-11-16 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613493
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-11-16T15:34:28.771Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-11-16 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613494
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2022-11-16T15:34:28.937Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-11-16 17:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613495
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Warehouse Name
-- Column: M_Delivery_Planning.WarehouseName
-- 2022-11-16T15:34:29.106Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-11-16 17:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613496
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Status
-- Column: M_Delivery_Planning.OrderStatus
-- 2022-11-16T15:34:29.335Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-11-16 17:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613498
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2022-11-16T15:34:29.501Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-11-16 17:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613499
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2022-11-16T15:34:29.673Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-11-16 17:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613500
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2022-11-16T15:34:29.842Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-11-16 17:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613501
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-11-16T15:34:30.011Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2022-11-16 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-11-16T15:34:30.222Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2022-11-16 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-11-16T15:34:30.389Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-11-16 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-11-16T15:34:30.560Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-11-16 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2022-11-16T15:34:30.727Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-11-16 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613506
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2022-11-16T15:34:30.896Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2022-11-16 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613507
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-11-16T15:34:31.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2022-11-16 17:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613508
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.Forwarder
-- 2022-11-16T15:34:31.302Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2022-11-16 17:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613509
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2022-11-16T15:34:31.479Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2022-11-16 17:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613510
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2022-11-16T15:34:31.646Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2022-11-16 17:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613511
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2022-11-16T15:34:31.820Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2022-11-16 17:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613512
;









-- Name: Delivery Planning
-- Action Type: W
-- Window: Delivery Planning(541632,D)
-- 2022-11-16T16:09:50.776Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581677,542021,0,541632,TO_TIMESTAMP('2022-11-16 18:09:50','YYYY-MM-DD HH24:MI:SS'),100,'D','M_Delivery_Planning','Y','N','N','N','N','Delivery Planning',TO_TIMESTAMP('2022-11-16 18:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T16:09:50.807Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542021 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-11-16T16:09:50.837Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542021, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542021)
;

-- 2022-11-16T16:09:50.874Z
/* DDL */  select update_menu_translation_from_ad_element(581677) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2022-11-16T16:10:00.914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2022-11-16T16:10:00.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2022-11-16T16:10:00.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2022-11-16T16:10:01.065Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2022-11-16T16:10:01.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2022-11-16T16:10:01.120Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2022-11-16T16:10:01.149Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2022-11-16T16:10:01.178Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2022-11-16T16:10:01.206Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2022-11-16T16:10:01.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2022-11-16T16:10:01.261Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2022-11-16T16:10:01.289Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2022-11-16T16:10:01.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2022-11-16T16:10:01.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2022-11-16T16:10:01.374Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2022-11-16T16:10:01.405Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2022-11-16T16:10:01.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2022-11-16T16:10:01.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2022-11-16T16:10:01.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2022-11-16T16:10:01.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2022-11-16T16:10:01.550Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2022-11-16T16:10:01.578Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2022-11-16T16:10:01.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-16T16:10:01.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-16T16:10:01.661Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-16T16:10:01.689Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2022-11-16T16:10:01.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2022-11-16T16:10:01.750Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2022-11-16T16:10:01.778Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2022-11-16T16:10:01.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2022-11-16T16:10:12.246Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2022-11-16T16:10:12.274Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2022-11-16T16:10:12.305Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2022-11-16T16:10:12.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2022-11-16T16:10:12.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2022-11-16T16:10:12.394Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2022-11-16T16:10:12.421Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2022-11-16T16:10:12.449Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2022-11-16T16:10:12.476Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2022-11-16T16:10:12.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2022-11-16T16:10:12.534Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2022-11-16T16:10:12.561Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2022-11-16T16:10:12.590Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2022-11-16T16:10:12.619Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2022-11-16T16:10:12.648Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2022-11-16T16:10:12.675Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2022-11-16T16:10:12.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2022-11-16T16:10:12.730Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2022-11-16T16:10:12.757Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2022-11-16T16:10:12.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2022-11-16T16:10:12.822Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2022-11-16T16:10:12.850Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2022-11-16T16:10:12.877Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2022-11-16T16:10:12.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-16T16:10:12.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-16T16:10:12.959Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-16T16:10:12.987Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2022-11-16T16:10:13.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2022-11-16T16:10:13.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2022-11-16T16:10:13.070Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;



-- Table: M_Delivery_Planning
-- 2022-11-22T18:27:39.566Z
UPDATE AD_Table SET AD_Window_ID=541632,Updated=TO_TIMESTAMP('2022-11-22 20:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542259
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Orderline
-- Column: M_Delivery_Planning.C_OrderLine_ID
-- 2022-11-22T18:28:17.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585123,708152,0,546674,TO_TIMESTAMP('2022-11-22 20:28:16','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Orderline',TO_TIMESTAMP('2022-11-22 20:28:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T18:28:17.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T18:28:17.496Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2022-11-22T18:28:17.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708152
;

-- 2022-11-22T18:28:17.594Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708152)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Orderline
-- Column: M_Delivery_Planning.C_OrderLine_ID
-- 2022-11-22T18:29:11.786Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708152,0,546674,550033,613545,'F',TO_TIMESTAMP('2022-11-22 20:29:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Orderline',80,0,0,TO_TIMESTAMP('2022-11-22 20:29:11','YYYY-MM-DD HH24:MI:SS'),100)
;





-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UOM
-- Column: M_Delivery_Planning.C_UOM_ID
-- 2022-11-23T15:18:50.472Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585130,708191,0,546674,TO_TIMESTAMP('2022-11-23 17:18:50','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure',10,'D','The UOM defines a unique non monetary Unit of Measure','Y','Y','N','N','N','N','N','UOM',TO_TIMESTAMP('2022-11-23 17:18:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T15:18:50.501Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708191 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T15:18:50.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2022-11-23T15:18:50.676Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708191
;

-- 2022-11-23T15:18:50.705Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708191)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.UOM
-- Column: M_Delivery_Planning.C_UOM_ID
-- 2022-11-23T15:19:25.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708191,0,546674,550032,613566,'F',TO_TIMESTAMP('2022-11-23 17:19:25','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure','The UOM defines a unique non monetary Unit of Measure','Y','N','N','Y','N','N','N',0,'UOM',50,0,0,TO_TIMESTAMP('2022-11-23 17:19:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20
-- UI Element Group: qtys
-- 2022-11-23T15:19:35.543Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2022-11-23 17:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550032
;

-- UI Column: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20
-- UI Element Group: org
-- 2022-11-23T15:19:39.288Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2022-11-23 17:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550031
;


-- Tab: Delivery Planning(541632,D) -> Delivery Planning
-- Table: M_Delivery_Planning
-- 2022-11-24T10:18:53.317Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2022-11-24 12:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546674
;



-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Land
-- Column: M_Delivery_Planning.C_Country_ID
-- 2022-11-24T13:10:27.902Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585137,708201,0,546674,TO_TIMESTAMP('2022-11-24 15:10:27','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','Y','N','N','N','N','N','Land',TO_TIMESTAMP('2022-11-24 15:10:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T13:10:27.903Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T13:10:27.905Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192) 
;

-- 2022-11-24T13:10:27.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708201
;

-- 2022-11-24T13:10:27.921Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708201)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Land
-- Column: M_Delivery_Planning.C_Country_ID
-- 2022-11-24T13:11:24.539Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708201,0,546674,550033,613577,'F',TO_TIMESTAMP('2022-11-24 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','N','N',0,'Land',90,0,0,TO_TIMESTAMP('2022-11-24 15:11:24','YYYY-MM-DD HH24:MI:SS'),100)
;





-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Destination Country
-- Column: M_Delivery_Planning.C_DestinationCountry_ID
-- 2022-11-24T13:42:40.661Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585138,708202,0,546674,TO_TIMESTAMP('2022-11-24 15:42:40','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Destination Country',TO_TIMESTAMP('2022-11-24 15:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T13:42:40.663Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T13:42:40.665Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581730) 
;

-- 2022-11-24T13:42:40.668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708202
;

-- 2022-11-24T13:42:40.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708202)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Destination Country
-- Column: M_Delivery_Planning.C_DestinationCountry_ID
-- 2022-11-24T13:43:10.031Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708202,0,546674,550028,613578,'F',TO_TIMESTAMP('2022-11-24 15:43:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Destination Country',390,0,0,TO_TIMESTAMP('2022-11-24 15:43:09','YYYY-MM-DD HH24:MI:SS'),100)
;







-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Creation date
-- Column: M_Delivery_Planning.CreationDate
-- 2022-11-24T13:50:10.109Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585139,708203,0,546674,TO_TIMESTAMP('2022-11-24 15:50:09','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Creation date',TO_TIMESTAMP('2022-11-24 15:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T13:50:10.111Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T13:50:10.113Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578970) 
;

-- 2022-11-24T13:50:10.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708203
;

-- 2022-11-24T13:50:10.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708203)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Creation date
-- Column: M_Delivery_Planning.CreationDate
-- 2022-11-24T13:51:05.352Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708203,0,546674,550033,613579,'F',TO_TIMESTAMP('2022-11-24 15:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'Creation date',100,0,0,TO_TIMESTAMP('2022-11-24 15:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Creation date
-- Column: M_Delivery_Planning.CreationDate
-- 2022-11-24T13:51:15.947Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613579
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Type
-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2022-11-24T13:51:15.954Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613482
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.B2B
-- Column: M_Delivery_Planning.IsB2B
-- 2022-11-24T13:51:15.961Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613483
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Section Code
-- Column: M_Delivery_Planning.M_SectionCode_ID
-- 2022-11-24T13:51:15.968Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613484
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterms
-- Column: M_Delivery_Planning.C_Incoterms_ID
-- 2022-11-24T13:51:15.975Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613485
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Document No
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2022-11-24T13:51:15.981Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613486
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Reference
-- Column: M_Delivery_Planning.POReference
-- 2022-11-24T13:51:15.988Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613487
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Partner Name
-- Column: M_Delivery_Planning.BPartnerName
-- 2022-11-24T13:51:15.994Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-11-24 15:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613488
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Ship-to location
-- Column: M_Delivery_Planning.ShipToLocation_Name
-- 2022-11-24T13:51:16Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613490
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Name
-- Column: M_Delivery_Planning.ProductName
-- 2022-11-24T13:51:16.007Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613491
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Value
-- Column: M_Delivery_Planning.ProductValue
-- 2022-11-24T13:51:16.013Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613492
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Grade
-- Column: M_Delivery_Planning.Grade
-- 2022-11-24T13:51:16.018Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613493
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-11-24T13:51:16.024Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613494
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2022-11-24T13:51:16.029Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613495
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Warehouse Name
-- Column: M_Delivery_Planning.WarehouseName
-- 2022-11-24T13:51:16.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613496
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Status
-- Column: M_Delivery_Planning.OrderStatus
-- 2022-11-24T13:51:16.040Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613498
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2022-11-24T13:51:16.046Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613499
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2022-11-24T13:51:16.052Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613500
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2022-11-24T13:51:16.057Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613501
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-11-24T13:51:16.062Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-11-24T13:51:16.067Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-11-24T13:51:16.072Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-11-24T13:51:16.078Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2022-11-24T13:51:16.083Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613506
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2022-11-24T13:51:16.088Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613507
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_Delivery_Planning.MeansOfTransportation
-- 2022-11-24T13:51:16.093Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613508
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.Forwarder
-- 2022-11-24T13:51:16.098Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613509
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2022-11-24T13:51:16.103Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613510
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2022-11-24T13:51:16.109Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613511
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2022-11-24T13:51:16.114Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2022-11-24 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613512
;



-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Destination Country
-- Column: M_Delivery_Planning.C_DestinationCountry_ID
-- 2022-11-24T14:16:05.253Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708202,0,546674,550033,613580,'F',TO_TIMESTAMP('2022-11-24 16:16:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Destination Country',110,0,0,TO_TIMESTAMP('2022-11-24 16:16:05','YYYY-MM-DD HH24:MI:SS'),100)
;





-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Origin Country
-- Column: M_Delivery_Planning.C_OriginCountry_ID
-- 2022-11-24T14:27:26.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585140,708204,0,546674,TO_TIMESTAMP('2022-11-24 16:27:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Origin Country',TO_TIMESTAMP('2022-11-24 16:27:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-24T14:27:26.998Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-24T14:27:27Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581734) 
;


-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Origin Country
-- Column: M_Delivery_Planning.C_OriginCountry_ID
-- 2022-11-24T14:28:14.260Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708204,0,546674,550033,613581,'F',TO_TIMESTAMP('2022-11-24 16:28:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Origin Country',120,0,0,TO_TIMESTAMP('2022-11-24 16:28:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Destination Country
-- Column: M_Delivery_Planning.C_DestinationCountry_ID
-- 2022-11-24T14:28:23.169Z
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2022-11-24 16:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613580
;



-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Destination Country
-- Column: M_Delivery_Planning.C_DestinationCountry_ID
-- 2022-11-24T15:21:10.819Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613578
;



-- Tab: Delivery Planning(541632,D) -> Delivery Planning
-- Table: M_Delivery_Planning
-- 2022-11-24T16:03:05.270Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2022-11-24 18:03:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546674
;




