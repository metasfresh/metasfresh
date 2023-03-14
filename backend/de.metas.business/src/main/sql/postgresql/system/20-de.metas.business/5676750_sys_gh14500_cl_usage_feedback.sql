-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Business Partner
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T08:59:37.041Z
UPDATE AD_Field SET AD_Name_ID=187, Description='', Help='', Name='Business Partner',Updated=TO_TIMESTAMP('2023-02-13 09:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712321
;

-- 2023-02-13T08:59:37.042Z
UPDATE AD_Field_Trl trl SET Description='',Help='',Name='Business Partner' WHERE AD_Field_ID=712321 AND AD_Language='en_US'
;

-- 2023-02-13T08:59:37.059Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-13T08:59:37.125Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712321
;

-- 2023-02-13T08:59:37.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712321)
;

-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-13T09:02:53.998Z
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-13 10:02:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542293
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Credit Limit Usage % (Delivery Based)
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T09:04:43.336Z
UPDATE AD_Field SET AD_Name_ID=582021, Description=NULL, Help=NULL, Name='Credit Limit Usage % (Delivery Based)',Updated=TO_TIMESTAMP('2023-02-13 10:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712327
;

-- 2023-02-13T09:04:43.337Z
UPDATE AD_Field_Trl trl SET Name='Credit Limit Usage % (Delivery Based)' WHERE AD_Field_ID=712327 AND AD_Language='en_US'
;

-- 2023-02-13T09:04:43.340Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582021) 
;

-- 2023-02-13T09:04:43.347Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712327
;

-- 2023-02-13T09:04:43.348Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712327)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Credit Limit Department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T09:05:10.803Z
UPDATE AD_Field SET AD_Name_ID=582018, Description='Totql Credit Limit By  Department', Help=NULL, Name='Credit Limit Department',Updated=TO_TIMESTAMP('2023-02-13 10:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712325
;

-- 2023-02-13T09:05:10.804Z
UPDATE AD_Field_Trl trl SET Description='Totql Credit Limit By  Department',Name='Credit Limit Department' WHERE AD_Field_ID=712325 AND AD_Language='en_US'
;

-- 2023-02-13T09:05:10.807Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582018) 
;

-- 2023-02-13T09:05:10.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712325
;

-- 2023-02-13T09:05:10.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712325)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Credit Limit Usage % (Order Based)
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T09:05:33.033Z
UPDATE AD_Field SET AD_Name_ID=582020, Description=NULL, Help=NULL, Name='Credit Limit Usage % (Order Based)',Updated=TO_TIMESTAMP('2023-02-13 10:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712326
;

-- 2023-02-13T09:05:33.033Z
UPDATE AD_Field_Trl trl SET Name='Credit Limit Usage % (Order Based)' WHERE AD_Field_ID=712326 AND AD_Language='en_US'
;

-- 2023-02-13T09:05:33.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582020) 
;

-- 2023-02-13T09:05:33.039Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712326
;

-- 2023-02-13T09:05:33.040Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712326)
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Credit Limit Section Code
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T09:07:08.007Z
UPDATE AD_Field SET AD_Name_ID=582019, Description='Credit limit section code (Credit Limit of this Section Partner)', Help=NULL, Name='Credit Limit Section Code',Updated=TO_TIMESTAMP('2023-02-13 10:07:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712324
;

-- 2023-02-13T09:07:08.008Z
UPDATE AD_Field_Trl trl SET Description='Credit limit section code (Credit Limit of this Section Partner)',Help=NULL,Name='Credit Limit Section Code' WHERE AD_Field_ID=712324 AND AD_Language='en_US'
;

-- 2023-02-13T09:07:08.010Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582019) 
;

-- 2023-02-13T09:07:08.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712324
;

-- 2023-02-13T09:07:08.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712324)
;

-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T09:08:25.202Z
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=255,Updated=TO_TIMESTAMP('2023-02-13 10:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586033
;

-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T09:08:39.851Z
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=255,Updated=TO_TIMESTAMP('2023-02-13 10:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586034
;

-- UI Column: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 20
-- UI Element Group: flags
-- 2023-02-13T09:09:22.884Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546647,550371,TO_TIMESTAMP('2023-02-13 10:09:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-02-13 10:09:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 20 -> flags.Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T09:12:45.305Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712332,0,546816,550371,615691,'F',TO_TIMESTAMP('2023-02-13 10:12:45','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2023-02-13 10:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 20
-- UI Element Group: section
-- 2023-02-13T09:12:55.936Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546647,550372,TO_TIMESTAMP('2023-02-13 10:12:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','section',20,TO_TIMESTAMP('2023-02-13 10:12:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 20 -> section.Organisation
-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T09:13:06.146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712334,0,546816,550372,615692,'F',TO_TIMESTAMP('2023-02-13 10:13:06','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-02-13 10:13:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 20 -> section.Client
-- Column: CreditLimit_Usage_V.AD_Client_ID
-- 2023-02-13T09:13:13.100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712333,0,546816,550372,615693,'F',TO_TIMESTAMP('2023-02-13 10:13:13','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2023-02-13 10:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;


-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-13T10:44:02.284Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615570
;

-- 2023-02-13T10:44:02.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712149
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-13T10:44:02.291Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712149
;

-- 2023-02-13T10:44:02.293Z
DELETE FROM AD_Field WHERE AD_Field_ID=712149
;

-- 2023-02-13T10:44:02.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712147
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_ID
-- 2023-02-13T10:44:02.298Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712147
;

-- 2023-02-13T10:44:02.300Z
DELETE FROM AD_Field WHERE AD_Field_ID=712147
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Usage % (Delivery Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T10:44:02.307Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615575
;

-- 2023-02-13T10:44:02.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712155
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Credit Limit Usage % (Delivery Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T10:44:02.310Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712155
;

-- 2023-02-13T10:44:02.311Z
DELETE FROM AD_Field WHERE AD_Field_ID=712155
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-13T10:44:02.320Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615572
;

-- 2023-02-13T10:44:02.320Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712152
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Credit Limit Department
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_department
-- 2023-02-13T10:44:02.322Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712152
;

-- 2023-02-13T10:44:02.324Z
DELETE FROM AD_Field WHERE AD_Field_ID=712152
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T10:44:02.332Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615571
;

-- 2023-02-13T10:44:02.333Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712150
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T10:44:02.334Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712150
;

-- 2023-02-13T10:44:02.335Z
DELETE FROM AD_Field WHERE AD_Field_ID=712150
;

-- 2023-02-13T10:44:02.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712156
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Created
-- Column: C_BPartner_Creditlimit_Usage_V.Created
-- 2023-02-13T10:44:02.340Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712156
;

-- 2023-02-13T10:44:02.341Z
DELETE FROM AD_Field WHERE AD_Field_ID=712156
;

-- 2023-02-13T10:44:02.345Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712158
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Created By
-- Column: C_BPartner_Creditlimit_Usage_V.CreatedBy
-- 2023-02-13T10:44:02.346Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712158
;

-- 2023-02-13T10:44:02.347Z
DELETE FROM AD_Field WHERE AD_Field_ID=712158
;

-- 2023-02-13T10:44:02.351Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712157
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Updated
-- Column: C_BPartner_Creditlimit_Usage_V.Updated
-- 2023-02-13T10:44:02.352Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712157
;

-- 2023-02-13T10:44:02.353Z
DELETE FROM AD_Field WHERE AD_Field_ID=712157
;

-- 2023-02-13T10:44:02.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712159
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Updated By
-- Column: C_BPartner_Creditlimit_Usage_V.UpdatedBy
-- 2023-02-13T10:44:02.357Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712159
;

-- 2023-02-13T10:44:02.358Z
DELETE FROM AD_Field WHERE AD_Field_ID=712159
;

-- 2023-02-13T10:44:02.362Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712160
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Active
-- Column: C_BPartner_Creditlimit_Usage_V.IsActive
-- 2023-02-13T10:44:02.363Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712160
;

-- 2023-02-13T10:44:02.364Z
DELETE FROM AD_Field WHERE AD_Field_ID=712160
;

-- 2023-02-13T10:44:02.368Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712161
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Client
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Client_ID
-- 2023-02-13T10:44:02.369Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712161
;

-- 2023-02-13T10:44:02.370Z
DELETE FROM AD_Field WHERE AD_Field_ID=712161
;

-- 2023-02-13T10:44:02.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712162
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Organisation
-- Column: C_BPartner_Creditlimit_Usage_V.AD_Org_ID
-- 2023-02-13T10:44:02.374Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712162
;

-- 2023-02-13T10:44:02.375Z
DELETE FROM AD_Field WHERE AD_Field_ID=712162
;

-- 2023-02-13T10:44:02.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712153
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> creditlimit_by_sectioncode
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-13T10:44:02.380Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712153
;

-- 2023-02-13T10:44:02.381Z
DELETE FROM AD_Field WHERE AD_Field_ID=712153
;

-- 2023-02-13T10:44:02.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712146
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> CL Usage
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_Creditlimit_Usage_V_ID
-- 2023-02-13T10:44:02.388Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712146
;

-- 2023-02-13T10:44:02.389Z
DELETE FROM AD_Field WHERE AD_Field_ID=712146
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-13T10:44:02.397Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615569
;

-- 2023-02-13T10:44:02.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712148
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Business Partner
-- Column: C_BPartner_Creditlimit_Usage_V.partner_code
-- 2023-02-13T10:44:02.399Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712148
;

-- 2023-02-13T10:44:02.400Z
DELETE FROM AD_Field WHERE AD_Field_ID=712148
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-13T10:44:02.408Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615573
;

-- 2023-02-13T10:44:02.409Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712151
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Credit Limit Section Code
-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-13T10:44:02.410Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712151
;

-- 2023-02-13T10:44:02.411Z
DELETE FROM AD_Field WHERE AD_Field_ID=712151
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10 -> grid.Credit Limit Usage % (Order Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-13T10:44:02.419Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615574
;

-- 2023-02-13T10:44:02.420Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712154
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> Credit Limit Usage % (Order Based)
-- Column: C_BPartner_Creditlimit_Usage_V.cl_indicator_order
-- 2023-02-13T10:44:02.420Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712154
;

-- 2023-02-13T10:44:02.421Z
DELETE FROM AD_Field WHERE AD_Field_ID=712154
;

-- UI Column: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid -> 10
-- UI Element Group: grid
-- 2023-02-13T10:44:02.470Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550339
;

-- UI Section: Business Partner_OLD(123,D) -> CL Usage(546805,D) -> grid
-- UI Column: 10
-- 2023-02-13T10:44:02.473Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546627
;

-- Tab: Business Partner_OLD(123,D) -> CL Usage(546805,D)
-- UI Section: grid
-- 2023-02-13T10:44:02.473Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545432
;

-- 2023-02-13T10:44:02.475Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545432
;

-- Tab: Business Partner_OLD(123,D) -> CL Usage
-- Table: C_BPartner_Creditlimit_Usage_V
-- 2023-02-13T10:44:02.477Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=546805
;

-- 2023-02-13T10:44:02.478Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=546805
;

-- Tab: Business Partner_OLD(123,D) -> CreditLimit_Usage_V
-- Table: CreditLimit_Usage_V
-- 2023-02-13T10:44:27.313Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582057,0,546818,542304,123,'Y',TO_TIMESTAMP('2023-02-13 11:44:27','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','CreditLimit_Usage_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'CreditLimit_Usage_V','N',85,1,TO_TIMESTAMP('2023-02-13 11:44:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:27.314Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546818 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-13T10:44:27.333Z
/* DDL */  select update_tab_translation_from_ad_element(582057)
;

-- 2023-02-13T10:44:27.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546818)
;

-- Tab: Business Partner_OLD(123,D) -> CreditLimit_Usage_V
-- Table: CreditLimit_Usage_V
-- 2023-02-13T10:44:36.240Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-13 11:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546818
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T10:44:48.591Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586025,712353,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,19,'D','Y','N','N','N','N','N','N','N','CreditLimit_Usage_V',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.594Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582057)
;

-- 2023-02-13T10:44:48.601Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712353
;

-- 2023-02-13T10:44:48.601Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712353)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T10:44:48.659Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586026,712354,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944)
;

-- 2023-02-13T10:44:48.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712354
;

-- 2023-02-13T10:44:48.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712354)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T10:44:48.713Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586027,712355,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.714Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.715Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581322)
;

-- 2023-02-13T10:44:48.720Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712355
;

-- 2023-02-13T10:44:48.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712355)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T10:44:48.773Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586028,712356,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','Y','N','N','N','N','N','partner_code',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.774Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.775Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582015)
;

-- 2023-02-13T10:44:48.777Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712356
;

-- 2023-02-13T10:44:48.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712356)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T10:44:48.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586029,712357,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2023-02-13T10:44:48.850Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712357
;

-- 2023-02-13T10:44:48.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712357)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T10:44:48.905Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586030,712358,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.906Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.907Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2023-02-13T10:44:48.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712358
;

-- 2023-02-13T10:44:48.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712358)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T10:44:48.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586031,712359,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed',255,'D','The Credit Limit field indicates the credit limit for this account.','Y','Y','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:48.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:48.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(855)
;

-- 2023-02-13T10:44:48.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712359
;

-- 2023-02-13T10:44:48.987Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712359)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T10:44:49.039Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586032,712360,0,546818,TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','creditlimit_by_department',TO_TIMESTAMP('2023-02-13 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.040Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.041Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582004)
;

-- 2023-02-13T10:44:49.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712360
;

-- 2023-02-13T10:44:49.045Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712360)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T10:44:49.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586033,712361,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','cl_indicator_order',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712361 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.101Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582006)
;

-- 2023-02-13T10:44:49.104Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712361
;

-- 2023-02-13T10:44:49.105Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712361)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T10:44:49.171Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586034,712362,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','cl_indicator_delivery',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.171Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.172Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582007)
;

-- 2023-02-13T10:44:49.176Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712362
;

-- 2023-02-13T10:44:49.176Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712362)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Created
-- Column: CreditLimit_Usage_V.Created
-- 2023-02-13T10:44:49.233Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586035,712363,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',35,'D','The Created field indicates the date that this record was created.','Y','Y','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.234Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.235Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2023-02-13T10:44:49.395Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712363
;

-- 2023-02-13T10:44:49.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712363)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Created By
-- Column: CreditLimit_Usage_V.CreatedBy
-- 2023-02-13T10:44:49.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586036,712364,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','Y','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.465Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.466Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2023-02-13T10:44:49.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712364
;

-- 2023-02-13T10:44:49.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712364)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Updated
-- Column: CreditLimit_Usage_V.Updated
-- 2023-02-13T10:44:49.566Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586037,712365,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated',35,'D','The Updated field indicates the date that this record was updated.','Y','Y','N','N','N','N','N','Updated',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.567Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.568Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2023-02-13T10:44:49.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712365
;

-- 2023-02-13T10:44:49.618Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712365)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Updated By
-- Column: CreditLimit_Usage_V.UpdatedBy
-- 2023-02-13T10:44:49.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586038,712366,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records',10,'D','The Updated By field indicates the user who updated this record.','Y','Y','N','N','N','N','N','Updated By',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.696Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2023-02-13T10:44:49.740Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712366
;

-- 2023-02-13T10:44:49.741Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712366)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T10:44:49.803Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586039,712367,0,546818,TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-13 11:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:49.804Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:49.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-02-13T10:44:50.159Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712367
;

-- 2023-02-13T10:44:50.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712367)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Client
-- Column: CreditLimit_Usage_V.AD_Client_ID
-- 2023-02-13T10:44:50.236Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586040,712368,0,546818,TO_TIMESTAMP('2023-02-13 11:44:50','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-13 11:44:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:50.237Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:50.239Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-02-13T10:44:50.444Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712368
;

-- 2023-02-13T10:44:50.445Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712368)
;

-- Field: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> Organisation
-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T10:44:50.516Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586041,712369,0,546818,TO_TIMESTAMP('2023-02-13 11:44:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-13 11:44:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:44:50.517Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T10:44:50.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-02-13T10:44:50.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712369
;

-- 2023-02-13T10:44:50.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712369)
;

-- Tab: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D)
-- UI Section: main
-- 2023-02-13T10:45:13.857Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546818,545445,TO_TIMESTAMP('2023-02-13 11:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-13 11:45:13','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-13T10:45:13.858Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545445 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> main
-- UI Column: 10
-- 2023-02-13T10:45:17.193Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546650,545445,TO_TIMESTAMP('2023-02-13 11:45:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-13 11:45:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> main
-- UI Column: 20
-- 2023-02-13T10:45:18.655Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546651,545445,TO_TIMESTAMP('2023-02-13 11:45:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-13 11:45:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Business Partner_OLD(123,D) -> CreditLimit_Usage_V(546818,D) -> main -> 10
-- UI Element Group: primary
-- 2023-02-13T10:45:27.818Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546650,550376,TO_TIMESTAMP('2023-02-13 11:45:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,TO_TIMESTAMP('2023-02-13 11:45:27','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Tab: Business Partner_OLD(123,D) -> CL Usage
-- Table: CreditLimit_Usage_V
-- 2023-02-13T11:08:40.001Z
UPDATE AD_Tab SET AD_Element_ID=582009, CommitWarning=NULL, Description=NULL, Help=NULL, Name='CL Usage',Updated=TO_TIMESTAMP('2023-02-13 12:08:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546818
;

-- 2023-02-13T11:08:40.002Z
UPDATE AD_Tab_Trl trl SET Name='CL Usage' WHERE AD_Tab_ID=546818 AND AD_Language='en_US'
;

-- 2023-02-13T11:08:40.021Z
/* DDL */  select update_tab_translation_from_ad_element(582009)
;

-- 2023-02-13T11:08:40.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546818)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-13T11:08:59.231Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712353,0,546818,550376,615708,'F',TO_TIMESTAMP('2023-02-13 12:08:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CreditLimit_Usage_V',10,0,0,TO_TIMESTAMP('2023-02-13 12:08:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T11:09:08.852Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712355,0,546818,550376,615709,'F',TO_TIMESTAMP('2023-02-13 12:09:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Group Partner',20,0,0,TO_TIMESTAMP('2023-02-13 12:09:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T11:09:14.929Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712354,0,546818,550376,615710,'F',TO_TIMESTAMP('2023-02-13 12:09:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',30,0,0,TO_TIMESTAMP('2023-02-13 12:09:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T11:09:24.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712358,0,546818,550376,615711,'F',TO_TIMESTAMP('2023-02-13 12:09:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',40,0,0,TO_TIMESTAMP('2023-02-13 12:09:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T11:09:32.475Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712357,0,546818,550376,615712,'F',TO_TIMESTAMP('2023-02-13 12:09:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Business Partner',50,0,0,TO_TIMESTAMP('2023-02-13 12:09:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T11:09:43.266Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712358,0,546818,550376,615713,'F',TO_TIMESTAMP('2023-02-13 12:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',60,0,0,TO_TIMESTAMP('2023-02-13 12:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T11:09:48.951Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712359,0,546818,550376,615714,'F',TO_TIMESTAMP('2023-02-13 12:09:48','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','The Credit Limit field indicates the credit limit for this account.','Y','N','N','Y','N','N','N',0,'Credit limit',70,0,0,TO_TIMESTAMP('2023-02-13 12:09:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T11:10:00.086Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712360,0,546818,550376,615715,'F',TO_TIMESTAMP('2023-02-13 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'creditlimit_by_department',80,0,0,TO_TIMESTAMP('2023-02-13 12:10:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T11:10:07.584Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712361,0,546818,550376,615716,'F',TO_TIMESTAMP('2023-02-13 12:10:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'cl_indicator_order',90,0,0,TO_TIMESTAMP('2023-02-13 12:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T11:10:13.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712362,0,546818,550376,615717,'F',TO_TIMESTAMP('2023-02-13 12:10:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'cl_indicator_delivery',100,0,0,TO_TIMESTAMP('2023-02-13 12:10:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T11:10:23.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712367,0,546818,550376,615718,'F',TO_TIMESTAMP('2023-02-13 12:10:23','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',110,0,0,TO_TIMESTAMP('2023-02-13 12:10:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T11:10:28.919Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615718
;

-- UI Column: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 20
-- UI Element Group: flags
-- 2023-02-13T11:10:41.965Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546651,550377,TO_TIMESTAMP('2023-02-13 12:10:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-02-13 12:10:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 20 -> flags.Active
-- Column: CreditLimit_Usage_V.IsActive
-- 2023-02-13T11:10:51.848Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712367,0,546818,550377,615719,'F',TO_TIMESTAMP('2023-02-13 12:10:51','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2023-02-13 12:10:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 20
-- UI Element Group: section
-- 2023-02-13T11:11:01.027Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546651,550378,TO_TIMESTAMP('2023-02-13 12:11:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','section',20,TO_TIMESTAMP('2023-02-13 12:11:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 20 -> section.Organisation
-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T11:11:10.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712369,0,546818,550378,615720,'F',TO_TIMESTAMP('2023-02-13 12:11:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-02-13 12:11:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 20 -> section.Client
-- Column: CreditLimit_Usage_V.AD_Client_ID
-- 2023-02-13T11:11:16.180Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712368,0,546818,550378,615721,'F',TO_TIMESTAMP('2023-02-13 12:11:16','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2023-02-13 12:11:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T11:12:00.907Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615713
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T11:12:10.642Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712356,0,546818,550376,615722,'F',TO_TIMESTAMP('2023-02-13 12:12:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'partner_code',110,0,0,TO_TIMESTAMP('2023-02-13 12:12:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.partner_code
-- Column: CreditLimit_Usage_V.partner_code
-- 2023-02-13T11:12:56.674Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615722
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T11:12:56.678Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615710
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-13T11:12:56.681Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615711
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Credit limit
-- Column: CreditLimit_Usage_V.CreditLimit
-- 2023-02-13T11:12:56.683Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615714
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.creditlimit_by_department
-- Column: CreditLimit_Usage_V.creditlimit_by_department
-- 2023-02-13T11:12:56.685Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615715
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.cl_indicator_order
-- Column: CreditLimit_Usage_V.cl_indicator_order
-- 2023-02-13T11:12:56.687Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615716
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.cl_indicator_delivery
-- Column: CreditLimit_Usage_V.cl_indicator_delivery
-- 2023-02-13T11:12:56.688Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615717
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 20 -> section.Organisation
-- Column: CreditLimit_Usage_V.AD_Org_ID
-- 2023-02-13T11:12:56.690Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-13 12:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615720
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> Department
-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-13T11:14:02.724Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2023-02-13 12:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712354
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> Section Group Partner
-- Column: CreditLimit_Usage_V.Section_Group_Partner_ID
-- 2023-02-13T11:14:10.854Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2023-02-13 12:14:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712355
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-13T11:14:25.033Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2023-02-13 12:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712357
;


-- Window: Credit limit Usage, InternalName=null
-- 2023-02-14T11:46:04.605Z
UPDATE AD_Window SET WindowType='Q',Updated=TO_TIMESTAMP('2023-02-14 12:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541679
;

-- Tab: Credit limit Usage(541679,D) -> CreditLimit_Usage_V
-- Table: CreditLimit_Usage_V
-- 2023-02-14T11:46:30.038Z
UPDATE AD_Tab SET IsGridModeOnly='N',Updated=TO_TIMESTAMP('2023-02-14 12:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546816
;

-- Tab: Credit limit Usage(541679,D) -> CreditLimit_Usage_V
-- Table: CreditLimit_Usage_V
-- 2023-02-14T11:46:35.194Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2023-02-14 12:46:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546816
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> CreditLimit_Usage_V
-- Column: CreditLimit_Usage_V.CreditLimit_Usage_V_ID
-- 2023-02-14T11:47:19.106Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-14 12:47:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712318
;

-- Reference: M_Department
-- Table: M_Department
-- Key: M_Department.M_Department_ID
-- 2023-02-14T11:50:01.015Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585571,585566,0,541715,542284,TO_TIMESTAMP('2023-02-14 12:50:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N',TO_TIMESTAMP('2023-02-14 12:50:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: CreditLimit_Usage_V.M_Department_ID
-- 2023-02-14T11:50:10.386Z
UPDATE AD_Column SET AD_Reference_Value_ID=541715,Updated=TO_TIMESTAMP('2023-02-14 12:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586026
;


