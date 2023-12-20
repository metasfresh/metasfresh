-- Tab: Sales Order_OLD(143,D) -> Order Cost
-- Table: C_Order_Cost
-- 2023-03-08T13:00:29.429Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,585914,582030,0,546846,542296,143,'Y',TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Order_Cost','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Order Cost',2161,'N',60,1,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:29.431Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546846 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-08T13:00:29.462Z
/* DDL */  select update_tab_translation_from_ad_element(582030) 
;

-- 2023-03-08T13:00:29.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546846)
;

-- 2023-03-08T13:00:29.471Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546846
;

-- 2023-03-08T13:00:29.472Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546846, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546812
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-03-08T13:00:29.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585892,712795,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',0,'Y','N','N','N','N','N','N','Y','N','Client',10,1,1,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:29.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:29.597Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-03-08T13:00:29.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712795
;

-- 2023-03-08T13:00:29.691Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712795)
;

-- 2023-03-08T13:00:29.694Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712795
;

-- 2023-03-08T13:00:29.695Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712795, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712246
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-03-08T13:00:29.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585893,712796,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.',0,'Y','N','N','N','N','N','N','N','N','Organisation',20,1,1,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:29.832Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:29.834Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-03-08T13:00:29.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712796
;

-- 2023-03-08T13:00:29.901Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712796)
;

-- 2023-03-08T13:00:29.904Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712796
;

-- 2023-03-08T13:00:29.905Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712796, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712247
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Active
-- Column: C_Order_Cost.IsActive
-- 2023-03-08T13:00:30.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585896,712797,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.',0,'Y','N','N','N','N','N','N','N','N','Active',30,1,1,TO_TIMESTAMP('2023-03-08 15:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.033Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.034Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-03-08T13:00:30.096Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712797
;

-- 2023-03-08T13:00:30.097Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712797)
;

-- 2023-03-08T13:00:30.100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712797
;

-- 2023-03-08T13:00:30.101Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712797, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712248
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Order Cost
-- Column: C_Order_Cost.C_Order_Cost_ID
-- 2023-03-08T13:00:30.231Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585899,712798,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Order Cost',40,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.232Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582030) 
;

-- 2023-03-08T13:00:30.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712798
;

-- 2023-03-08T13:00:30.239Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712798)
;

-- 2023-03-08T13:00:30.241Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712798
;

-- 2023-03-08T13:00:30.242Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712798, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712249
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-03-08T13:00:30.369Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585900,712799,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Cost Type',50,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.370Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.372Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-03-08T13:00:30.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712799
;

-- 2023-03-08T13:00:30.377Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712799)
;

-- 2023-03-08T13:00:30.380Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712799
;

-- 2023-03-08T13:00:30.381Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712799, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712250
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-03-08T13:00:30.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585901,712800,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','N','Calculation Method',60,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.498Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582025) 
;

-- 2023-03-08T13:00:30.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712800
;

-- 2023-03-08T13:00:30.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712800)
;

-- 2023-03-08T13:00:30.510Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712800
;

-- 2023-03-08T13:00:30.511Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712800, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712251
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-03-08T13:00:30.615Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585902,712801,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','N','Distribution',70,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.618Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582024) 
;

-- 2023-03-08T13:00:30.621Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712801
;

-- 2023-03-08T13:00:30.623Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712801)
;

-- 2023-03-08T13:00:30.625Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712801
;

-- 2023-03-08T13:00:30.626Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712801, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712252
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-03-08T13:00:30.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585903,712802,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Cost Amount',80,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-03-08T13:00:30.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712802
;

-- 2023-03-08T13:00:30.752Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712802)
;

-- 2023-03-08T13:00:30.755Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712802
;

-- 2023-03-08T13:00:30.755Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712802, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712253
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-03-08T13:00:30.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585904,712803,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record',0,'Y','N','N','N','N','N','N','N','N','Currency',90,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:30.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:30.893Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-03-08T13:00:30.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712803
;

-- 2023-03-08T13:00:30.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712803)
;

-- 2023-03-08T13:00:30.910Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712803
;

-- 2023-03-08T13:00:30.912Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712803, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712254
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-03-08T13:00:31.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585914,712804,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.',0,'Y','N','N','N','N','N','N','N','N','Sales order',100,1,1,TO_TIMESTAMP('2023-03-08 15:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:31.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:31.037Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-03-08T13:00:31.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712804
;

-- 2023-03-08T13:00:31.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712804)
;

-- 2023-03-08T13:00:31.048Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712804
;

-- 2023-03-08T13:00:31.049Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712804, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712255
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-03-08T13:00:31.167Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585931,712805,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,10,'@CostCalculationMethod/-@=F','D',0,'Y','N','N','N','N','N','N','N','N','Fixed Amount',110,1,1,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:31.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:31.170Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582037) 
;

-- 2023-03-08T13:00:31.172Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712805
;

-- 2023-03-08T13:00:31.173Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712805)
;

-- 2023-03-08T13:00:31.176Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712805
;

-- 2023-03-08T13:00:31.177Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712805, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712256
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-03-08T13:00:31.298Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585932,712806,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,10,'@CostCalculationMethod/-@=P','D',0,'Y','N','N','N','N','N','N','N','N','Percentage',120,1,1,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:31.299Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:31.301Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582038) 
;

-- 2023-03-08T13:00:31.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712806
;

-- 2023-03-08T13:00:31.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712806)
;

-- 2023-03-08T13:00:31.307Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712806
;

-- 2023-03-08T13:00:31.308Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712806, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712257
;

-- Field: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-03-08T13:00:31.421Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585956,712807,0,546846,0,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','',0,'Y','N','N','N','N','N','N','N','N','Business Partner',130,1,1,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:00:31.422Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T13:00:31.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-03-08T13:00:31.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712807
;

-- 2023-03-08T13:00:31.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712807)
;

-- 2023-03-08T13:00:31.435Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712807
;

-- 2023-03-08T13:00:31.436Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712277
;

-- Tab: Sales Order_OLD(143,D) -> Order Cost(546846,D)
-- UI Section: main
-- 2023-03-08T13:00:31.589Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546846,545465,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-03-08T13:00:31.590Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545465 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-03-08T13:00:31.593Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545465
;

-- 2023-03-08T13:00:31.595Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545465, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545439
;

-- UI Section: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main
-- UI Column: 10
-- 2023-03-08T13:00:31.782Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546677,545465,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10
-- UI Element Group: primary
-- 2023-03-08T13:00:32.005Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546677,550428,TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-03-08 15:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> primary.Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-03-08T13:00:32.209Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712799,0,546846,550428,615976,'F',TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Cost Type',20,20,0,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> primary.Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-03-08T13:00:32.363Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712807,0,546846,550428,615977,'F',TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','Y','N','N','Business Partner',30,10,0,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10
-- UI Element Group: cost calculation
-- 2023-03-08T13:00:32.492Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546677,550429,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','cost calculation',20,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> cost calculation.Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-03-08T13:00:32.625Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712800,0,546846,550429,615978,'F',TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Calculation Method',10,0,0,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> cost calculation.Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-03-08T13:00:32.757Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712805,0,546846,550429,615979,'F',TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Fixed Amount',20,0,0,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> cost calculation.Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-03-08T13:00:32.884Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712806,0,546846,550429,615980,'F',TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Percentage',30,0,0,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10
-- UI Element Group: cost distribution
-- 2023-03-08T13:00:33.008Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546677,550430,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','cost distribution',30,TO_TIMESTAMP('2023-03-08 15:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> cost distribution.Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-03-08T13:00:33.139Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712801,0,546846,550430,615981,'F',TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Distribution',10,0,0,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10
-- UI Element Group: amounts
-- 2023-03-08T13:00:33.258Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546677,550431,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts',40,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> amounts.Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-03-08T13:00:33.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712802,0,546846,550431,615982,'F',TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Cost Amount',10,40,0,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 10 -> amounts.Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-03-08T13:00:33.535Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712803,0,546846,550431,615983,'F',TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','Y','Y','N','N','Currency',20,30,0,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main
-- UI Column: 20
-- 2023-03-08T13:00:33.669Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546678,545465,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 20
-- UI Element Group: org and client
-- 2023-03-08T13:00:33.791Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546678,550432,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','org and client',10,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 20 -> org and client.Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-03-08T13:00:33.932Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712796,0,546846,550432,615984,'F',TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order Cost(546846,D) -> main -> 20 -> org and client.Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-03-08T13:00:34.069Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712795,0,546846,550432,615985,'F',TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N','Client',20,0,0,TO_TIMESTAMP('2023-03-08 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

