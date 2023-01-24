-- Window: Foreign Exchange Contract, InternalName=forexContact
-- 2023-01-19T20:32:14.798Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581935,0,541664,TO_TIMESTAMP('2023-01-19 22:32:14','YYYY-MM-DD HH24:MI:SS'),100,'D','forexContact','Y','N','N','N','N','N','N','Y','Foreign Exchange Contract','N',TO_TIMESTAMP('2023-01-19 22:32:14','YYYY-MM-DD HH24:MI:SS'),100,'T',0,0,100)
;

-- 2023-01-19T20:32:14.800Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541664 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-01-19T20:32:14.830Z
/* DDL */  select update_window_translation_from_ad_element(581935) 
;

-- 2023-01-19T20:32:14.842Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541664
;

-- 2023-01-19T20:32:14.844Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541664)
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract
-- Table: C_ForeignExchangeContract
-- 2023-01-19T20:32:33.969Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581935,0,546745,542281,541664,'Y',TO_TIMESTAMP('2023-01-19 22:32:33','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_ForeignExchangeContract','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Foreign Exchange Contract','N',10,0,TO_TIMESTAMP('2023-01-19 22:32:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:33.971Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546745 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-19T20:32:33.973Z
/* DDL */  select update_tab_translation_from_ad_element(581935) 
;

-- 2023-01-19T20:32:33.977Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546745)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Client
-- Column: C_ForeignExchangeContract.AD_Client_ID
-- 2023-01-19T20:32:36.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585530,710299,0,546745,TO_TIMESTAMP('2023-01-19 22:32:36','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-01-19 22:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:36.277Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:36.280Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-19T20:32:36.492Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710299
;

-- 2023-01-19T20:32:36.494Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710299)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Organisation
-- Column: C_ForeignExchangeContract.AD_Org_ID
-- 2023-01-19T20:32:36.610Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585531,710300,0,546745,TO_TIMESTAMP('2023-01-19 22:32:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-01-19 22:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:36.611Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:36.613Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-19T20:32:36.771Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710300
;

-- 2023-01-19T20:32:36.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710300)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Active
-- Column: C_ForeignExchangeContract.IsActive
-- 2023-01-19T20:32:36.876Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585534,710301,0,546745,TO_TIMESTAMP('2023-01-19 22:32:36','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-01-19 22:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:36.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:36.879Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-19T20:32:37.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710301
;

-- 2023-01-19T20:32:37.076Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710301)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Foreign Exchange Contract
-- Column: C_ForeignExchangeContract.C_ForeignExchangeContract_ID
-- 2023-01-19T20:32:37.197Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585537,710302,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.199Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581935) 
;

-- 2023-01-19T20:32:37.204Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710302
;

-- 2023-01-19T20:32:37.204Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710302)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Document No
-- Column: C_ForeignExchangeContract.DocumentNo
-- 2023-01-19T20:32:37.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585538,710303,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',255,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Document No',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.310Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-01-19T20:32:37.316Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710303
;

-- 2023-01-19T20:32:37.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710303)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Currency
-- Column: C_ForeignExchangeContract.C_Currency_ID
-- 2023-01-19T20:32:37.431Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585539,710304,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.435Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-01-19T20:32:37.454Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710304
;

-- 2023-01-19T20:32:37.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710304)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> To Currency
-- Column: C_ForeignExchangeContract.To_Currency_ID
-- 2023-01-19T20:32:37.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585540,710305,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','To Currency',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.575Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581936) 
;

-- 2023-01-19T20:32:37.579Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710305
;

-- 2023-01-19T20:32:37.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710305)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Currency Rate
-- Column: C_ForeignExchangeContract.CurrencyRate
-- 2023-01-19T20:32:37.703Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585541,710306,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Currency Rate',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.704Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(253) 
;

-- 2023-01-19T20:32:37.710Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710306
;

-- 2023-01-19T20:32:37.711Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710306)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> FEC Amount
-- Column: C_ForeignExchangeContract.FEC_Amount
-- 2023-01-19T20:32:37.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585542,710307,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Amount',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581937) 
;

-- 2023-01-19T20:32:37.833Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710307
;

-- 2023-01-19T20:32:37.834Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710307)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> FEC Allocated Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Alloc
-- 2023-01-19T20:32:37.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585544,710308,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Allocated Amount',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:37.947Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:37.949Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581939) 
;

-- 2023-01-19T20:32:37.953Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710308
;

-- 2023-01-19T20:32:37.954Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710308)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> FEC Open Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Open
-- 2023-01-19T20:32:38.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585545,710309,0,546745,TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Open Amount',TO_TIMESTAMP('2023-01-19 22:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:38.064Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:38.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581940) 
;

-- 2023-01-19T20:32:38.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710309
;

-- 2023-01-19T20:32:38.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710309)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Validity Date
-- Column: C_ForeignExchangeContract.FEC_ValidityDate
-- 2023-01-19T20:32:38.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585546,710310,0,546745,TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Validity Date',TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:38.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:38.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581941) 
;

-- 2023-01-19T20:32:38.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710310
;

-- 2023-01-19T20:32:38.180Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710310)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Maturity Date
-- Column: C_ForeignExchangeContract.FEC_MaturityDate
-- 2023-01-19T20:32:38.273Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585547,710311,0,546745,TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Maturity Date',TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:38.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:38.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581942) 
;

-- 2023-01-19T20:32:38.280Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710311
;

-- 2023-01-19T20:32:38.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710311)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Description
-- Column: C_ForeignExchangeContract.Description
-- 2023-01-19T20:32:38.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585548,710312,0,546745,TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100,'',2000,'D','','Y','N','N','N','N','N','N','N','Description',TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:38.399Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:38.402Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-01-19T20:32:38.459Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710312
;

-- 2023-01-19T20:32:38.460Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710312)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Processed
-- Column: C_ForeignExchangeContract.Processed
-- 2023-01-19T20:32:38.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585549,710313,0,546745,TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','','Y','N','N','N','N','N','N','N','Processed',TO_TIMESTAMP('2023-01-19 22:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:32:38.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:32:38.580Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-01-19T20:32:38.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710313
;

-- 2023-01-19T20:32:38.593Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710313)
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D)
-- UI Section: main
-- 2023-01-19T20:32:54.550Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546745,545378,TO_TIMESTAMP('2023-01-19 22:32:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 22:32:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-19T20:32:54.552Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545378 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main
-- UI Column: 10
-- 2023-01-19T20:32:58.897Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546560,545378,TO_TIMESTAMP('2023-01-19 22:32:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 22:32:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main
-- UI Column: 20
-- 2023-01-19T20:33:01.363Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546561,545378,TO_TIMESTAMP('2023-01-19 22:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-01-19 22:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10
-- UI Element Group: primary
-- 2023-01-19T20:33:44.187Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546560,550235,TO_TIMESTAMP('2023-01-19 22:33:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-01-19 22:33:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Document No
-- Column: C_ForeignExchangeContract.DocumentNo
-- 2023-01-19T20:34:09.498Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710303,0,546745,550235,614751,'F',TO_TIMESTAMP('2023-01-19 22:34:08','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Document No',10,0,0,TO_TIMESTAMP('2023-01-19 22:34:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Validity Date
-- Column: C_ForeignExchangeContract.FEC_ValidityDate
-- 2023-01-19T20:35:10.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710310,0,546745,550235,614752,'F',TO_TIMESTAMP('2023-01-19 22:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Validity Date',20,0,0,TO_TIMESTAMP('2023-01-19 22:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Maturity Date
-- Column: C_ForeignExchangeContract.FEC_MaturityDate
-- 2023-01-19T20:35:17.348Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710311,0,546745,550235,614753,'F',TO_TIMESTAMP('2023-01-19 22:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Maturity Date',30,0,0,TO_TIMESTAMP('2023-01-19 22:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10
-- UI Element Group: currency
-- 2023-01-19T20:35:27.444Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546560,550236,TO_TIMESTAMP('2023-01-19 22:35:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','currency',20,TO_TIMESTAMP('2023-01-19 22:35:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> currency.Currency
-- Column: C_ForeignExchangeContract.C_Currency_ID
-- 2023-01-19T20:35:39.538Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710304,0,546745,550236,614754,'F',TO_TIMESTAMP('2023-01-19 22:35:39','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',10,0,0,TO_TIMESTAMP('2023-01-19 22:35:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> currency.To Currency
-- Column: C_ForeignExchangeContract.To_Currency_ID
-- 2023-01-19T20:35:48.916Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710305,0,546745,550236,614755,'F',TO_TIMESTAMP('2023-01-19 22:35:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','To Currency',20,0,0,TO_TIMESTAMP('2023-01-19 22:35:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> currency.Currency Rate
-- Column: C_ForeignExchangeContract.CurrencyRate
-- 2023-01-19T20:35:56.163Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710306,0,546745,550236,614756,'F',TO_TIMESTAMP('2023-01-19 22:35:56','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Currency Rate',30,0,0,TO_TIMESTAMP('2023-01-19 22:35:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10
-- UI Element Group: amounts
-- 2023-01-19T20:36:18.522Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546560,550237,TO_TIMESTAMP('2023-01-19 22:36:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts',30,TO_TIMESTAMP('2023-01-19 22:36:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> amounts.FEC Amount
-- Column: C_ForeignExchangeContract.FEC_Amount
-- 2023-01-19T20:36:33.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710307,0,546745,550237,614757,'F',TO_TIMESTAMP('2023-01-19 22:36:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Amount',10,0,0,TO_TIMESTAMP('2023-01-19 22:36:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> amounts.FEC Allocated Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Alloc
-- 2023-01-19T20:36:40.877Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710308,0,546745,550237,614758,'F',TO_TIMESTAMP('2023-01-19 22:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Allocated Amount',20,0,0,TO_TIMESTAMP('2023-01-19 22:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> amounts.FEC Open Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Open
-- 2023-01-19T20:36:49.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710309,0,546745,550237,614759,'F',TO_TIMESTAMP('2023-01-19 22:36:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Open Amount',30,0,0,TO_TIMESTAMP('2023-01-19 22:36:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Description
-- Column: C_ForeignExchangeContract.Description
-- 2023-01-19T20:37:23.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710312,0,546745,550235,614760,'F',TO_TIMESTAMP('2023-01-19 22:37:23','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Description',40,0,0,TO_TIMESTAMP('2023-01-19 22:37:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20
-- UI Element Group: document status
-- 2023-01-19T20:37:43.049Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546561,550238,TO_TIMESTAMP('2023-01-19 22:37:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','document status',10,TO_TIMESTAMP('2023-01-19 22:37:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20 -> document status.Processed
-- Column: C_ForeignExchangeContract.Processed
-- 2023-01-19T20:38:02.477Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710313,0,546745,550238,614761,'F',TO_TIMESTAMP('2023-01-19 22:38:02','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Processed',10,0,0,TO_TIMESTAMP('2023-01-19 22:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20
-- UI Element Group: client&org
-- 2023-01-19T20:38:08.801Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546561,550239,TO_TIMESTAMP('2023-01-19 22:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','client&org',20,TO_TIMESTAMP('2023-01-19 22:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20 -> client&org.Organisation
-- Column: C_ForeignExchangeContract.AD_Org_ID
-- 2023-01-19T20:38:19.556Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710300,0,546745,550239,614762,'F',TO_TIMESTAMP('2023-01-19 22:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-01-19 22:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20 -> client&org.Client
-- Column: C_ForeignExchangeContract.AD_Client_ID
-- 2023-01-19T20:38:25.856Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710299,0,546745,550239,614763,'F',TO_TIMESTAMP('2023-01-19 22:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','N','N','Client',20,0,0,TO_TIMESTAMP('2023-01-19 22:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> FEC Allocated Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Alloc
-- 2023-01-19T20:38:45.541Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-19 22:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710308
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> FEC Open Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Open
-- 2023-01-19T20:38:47.129Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-19 22:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710309
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Processed
-- Column: C_ForeignExchangeContract.Processed
-- 2023-01-19T20:38:57.237Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-19 22:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710313
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Document No
-- Column: C_ForeignExchangeContract.DocumentNo
-- 2023-01-19T20:40:29.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614751
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> currency.Currency Rate
-- Column: C_ForeignExchangeContract.CurrencyRate
-- 2023-01-19T20:40:29.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614756
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> currency.Currency
-- Column: C_ForeignExchangeContract.C_Currency_ID
-- 2023-01-19T20:40:29.761Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614754
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Validity Date
-- Column: C_ForeignExchangeContract.FEC_ValidityDate
-- 2023-01-19T20:40:29.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614752
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Maturity Date
-- Column: C_ForeignExchangeContract.FEC_MaturityDate
-- 2023-01-19T20:40:29.774Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614753
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> amounts.FEC Amount
-- Column: C_ForeignExchangeContract.FEC_Amount
-- 2023-01-19T20:40:29.779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614757
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> amounts.FEC Allocated Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Alloc
-- 2023-01-19T20:40:29.786Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614758
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> amounts.FEC Open Amount
-- Column: C_ForeignExchangeContract.FEC_Amount_Open
-- 2023-01-19T20:40:29.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614759
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20 -> document status.Processed
-- Column: C_ForeignExchangeContract.Processed
-- 2023-01-19T20:40:29.797Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614761
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 20 -> client&org.Organisation
-- Column: C_ForeignExchangeContract.AD_Org_ID
-- 2023-01-19T20:40:29.802Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-01-19 22:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614762
;

-- Column: C_ForeignExchangeContract.C_Currency_ID
-- 2023-01-19T20:40:51.776Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-19 22:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585539
;

-- Column: C_ForeignExchangeContract.DocumentNo
-- 2023-01-19T20:41:03.087Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-19 22:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585538
;

-- Column: C_ForeignExchangeContract.Description
-- 2023-01-19T20:41:15.011Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-19 22:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585548
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation
-- Table: C_ForeignExchangeContract_Alloc
-- 2023-01-19T20:44:05.024Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581943,0,546746,542283,541664,'Y',TO_TIMESTAMP('2023-01-19 22:44:04','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_ForeignExchangeContract_Alloc','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Foreign Exchange Contract Allocation','N',20,0,TO_TIMESTAMP('2023-01-19 22:44:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:05.026Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546746 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-19T20:44:05.027Z
/* DDL */  select update_tab_translation_from_ad_element(581943) 
;

-- 2023-01-19T20:44:05.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546746)
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation
-- Table: C_ForeignExchangeContract_Alloc
-- 2023-01-19T20:44:09.605Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2023-01-19 22:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546746
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation
-- Table: C_ForeignExchangeContract_Alloc
-- 2023-01-19T20:44:17.511Z
UPDATE AD_Tab SET AD_Column_ID=585558, Parent_Column_ID=585537,Updated=TO_TIMESTAMP('2023-01-19 22:44:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546746
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation
-- Table: C_ForeignExchangeContract_Alloc
-- 2023-01-19T20:44:20.502Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-19 22:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546746
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Client
-- Column: C_ForeignExchangeContract_Alloc.AD_Client_ID
-- 2023-01-19T20:44:26.052Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585550,710314,0,546746,TO_TIMESTAMP('2023-01-19 22:44:25','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-01-19 22:44:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:26.054Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:26.056Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-19T20:44:26.265Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710314
;

-- 2023-01-19T20:44:26.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710314)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Organisation
-- Column: C_ForeignExchangeContract_Alloc.AD_Org_ID
-- 2023-01-19T20:44:26.385Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585551,710315,0,546746,TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:26.387Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:26.388Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-19T20:44:26.521Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710315
;

-- 2023-01-19T20:44:26.522Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710315)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Active
-- Column: C_ForeignExchangeContract_Alloc.IsActive
-- 2023-01-19T20:44:26.686Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585554,710316,0,546746,TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:26.687Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:26.689Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-19T20:44:26.847Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710316
;

-- 2023-01-19T20:44:26.848Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710316)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Foreign Exchange Contract Allocation
-- Column: C_ForeignExchangeContract_Alloc.C_ForeignExchangeContract_Alloc_ID
-- 2023-01-19T20:44:26.983Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585557,710317,0,546746,TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract Allocation',TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:26.985Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:26.986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581943) 
;

-- 2023-01-19T20:44:26.988Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710317
;

-- 2023-01-19T20:44:26.989Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710317)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Foreign Exchange Contract
-- Column: C_ForeignExchangeContract_Alloc.C_ForeignExchangeContract_ID
-- 2023-01-19T20:44:27.100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585558,710318,0,546746,TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-19 22:44:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:27.102Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:27.103Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581935) 
;

-- 2023-01-19T20:44:27.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710318
;

-- 2023-01-19T20:44:27.108Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710318)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Sales order
-- Column: C_ForeignExchangeContract_Alloc.C_Order_ID
-- 2023-01-19T20:44:27.224Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585559,710319,0,546746,TO_TIMESTAMP('2023-01-19 22:44:27','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Sales order',TO_TIMESTAMP('2023-01-19 22:44:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:27.225Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:27.227Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-01-19T20:44:27.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710319
;

-- 2023-01-19T20:44:27.241Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710319)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Amount
-- Column: C_ForeignExchangeContract_Alloc.Amount
-- 2023-01-19T20:44:27.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585560,710320,0,546746,TO_TIMESTAMP('2023-01-19 22:44:27','YYYY-MM-DD HH24:MI:SS'),100,'Amount in a defined currency',10,'D','The Amount indicates the amount for this document line.','Y','N','N','N','N','N','N','N','Amount',TO_TIMESTAMP('2023-01-19 22:44:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:27.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:27.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2023-01-19T20:44:27.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710320
;

-- 2023-01-19T20:44:27.365Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710320)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Currency
-- Column: C_ForeignExchangeContract_Alloc.C_Currency_ID
-- 2023-01-19T20:44:27.468Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585561,710321,0,546746,TO_TIMESTAMP('2023-01-19 22:44:27','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-01-19 22:44:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:44:27.469Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:44:27.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-01-19T20:44:27.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710321
;

-- 2023-01-19T20:44:27.487Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710321)
;

-- Tab: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D)
-- UI Section: main
-- 2023-01-19T20:44:42.963Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546746,545379,TO_TIMESTAMP('2023-01-19 22:44:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 22:44:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-19T20:44:42.965Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545379 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main
-- UI Column: 10
-- 2023-01-19T20:44:47.091Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546562,545379,TO_TIMESTAMP('2023-01-19 22:44:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 22:44:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10
-- UI Element Group: main
-- 2023-01-19T20:45:01.100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546562,550240,TO_TIMESTAMP('2023-01-19 22:45:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-01-19 22:45:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Sales order
-- Column: C_ForeignExchangeContract_Alloc.C_Order_ID
-- 2023-01-19T20:45:23.260Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710319,0,546746,550240,614764,'F',TO_TIMESTAMP('2023-01-19 22:45:23','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Sales order',10,0,0,TO_TIMESTAMP('2023-01-19 22:45:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Currency
-- Column: C_ForeignExchangeContract_Alloc.C_Currency_ID
-- 2023-01-19T20:45:38.198Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710321,0,546746,550240,614765,'F',TO_TIMESTAMP('2023-01-19 22:45:38','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',20,0,0,TO_TIMESTAMP('2023-01-19 22:45:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Amount
-- Column: C_ForeignExchangeContract_Alloc.Amount
-- 2023-01-19T20:45:44.051Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710320,0,546746,550240,614766,'F',TO_TIMESTAMP('2023-01-19 22:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Amount in a defined currency','The Amount indicates the amount for this document line.','Y','N','Y','N','N','Amount',30,0,0,TO_TIMESTAMP('2023-01-19 22:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Sales order
-- Column: C_ForeignExchangeContract_Alloc.C_Order_ID
-- 2023-01-19T20:45:56.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-19 22:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614764
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Currency
-- Column: C_ForeignExchangeContract_Alloc.C_Currency_ID
-- 2023-01-19T20:45:56.760Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-19 22:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614765
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> main -> 10 -> main.Amount
-- Column: C_ForeignExchangeContract_Alloc.Amount
-- 2023-01-19T20:45:56.766Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-19 22:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614766
;

-- Name: Foreign Exchange Contract
-- Action Type: W
-- Window: Foreign Exchange Contract(541664,D)
-- 2023-01-19T20:47:29.909Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581935,542042,0,541664,TO_TIMESTAMP('2023-01-19 22:47:29','YYYY-MM-DD HH24:MI:SS'),100,'D','forexContact','Y','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-19 22:47:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:47:29.911Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542042 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-19T20:47:29.913Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542042, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542042)
;

-- 2023-01-19T20:47:29.925Z
/* DDL */  select update_menu_translation_from_ad_element(581935) 
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2023-01-19T20:47:30.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2023-01-19T20:47:30.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2023-01-19T20:47:30.523Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2023-01-19T20:47:30.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2023-01-19T20:47:30.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2023-01-19T20:47:30.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2023-01-19T20:47:30.526Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2023-01-19T20:47:30.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2023-01-19T20:47:30.528Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2023-01-19T20:47:30.529Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2023-01-19T20:47:30.530Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2023-01-19T20:47:30.531Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2023-01-19T20:47:30.531Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2023-01-19T20:47:30.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2023-01-19T20:47:30.533Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2023-01-19T20:47:30.534Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2023-01-19T20:47:30.535Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2023-01-19T20:47:30.536Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2023-01-19T20:47:30.537Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2023-01-19T20:47:30.538Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2023-01-19T20:47:30.539Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2023-01-19T20:47:30.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-01-19T20:47:30.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-01-19T20:47:30.541Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2023-01-19T20:47:30.542Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2023-01-19T20:47:30.543Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2023-01-19T20:47:30.544Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2023-01-19T20:47:30.546Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2023-01-19T20:47:30.547Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2023-01-19T20:47:30.547Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2023-01-19T20:47:30.548Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2023-01-19T20:47:30.550Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2023-01-19T20:47:30.551Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2023-01-19T20:47:30.552Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2023-01-19T20:47:30.553Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-01-19T20:47:30.554Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2023-01-19T20:47:30.555Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-01-19T20:47:30.556Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-01-19T20:47:30.557Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2023-01-19T20:47:30.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2023-01-19T20:47:30.559Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Foreign Exchange Contract`
-- 2023-01-19T20:47:30.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542042 AND AD_Tree_ID=10
;

-- Value: C_Order_AllocateToForexContract
-- Classname: de.metas.forex.process.C_Order_AllocateToForexContract
-- 2023-01-19T21:28:21.486Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585186,'Y','de.metas.forex.process.C_Order_AllocateToForexContract','N',TO_TIMESTAMP('2023-01-19 23:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Allocate to Foreign Exchange Contract','D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Allocate to FEC','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-19 23:28:21','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_AllocateToForexContract')
;

-- 2023-01-19T21:28:21.488Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585186 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.process.C_Order_AllocateToForexContract)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-19T21:28:41.479Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581935,0,585186,542456,30,'C_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-19 23:28:41','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','Foreign Exchange Contract',10,TO_TIMESTAMP('2023-01-19 23:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T21:28:41.480Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542456 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.process.C_Order_AllocateToForexContract)
-- Table: C_Order
-- EntityType: D
-- 2023-01-19T21:29:02.825Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585186,259,541332,TO_TIMESTAMP('2023-01-19 23:29:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-19 23:29:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.process.C_Order_AllocateToForexContract)
-- Table: C_Order
-- EntityType: D
-- 2023-01-19T21:29:15.494Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2023-01-19 23:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541332
;

