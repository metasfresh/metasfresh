-- Window: Credit Limit (Departments), InternalName=null
-- 2023-02-01T21:07:07.542Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581986,0,541667,TO_TIMESTAMP('2023-02-01 23:07:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Credit Limit (Departments)','N',TO_TIMESTAMP('2023-02-01 23:07:06','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-01T21:07:07.543Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541667 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-01T21:07:07.547Z
/* DDL */  select update_window_translation_from_ad_element(581986) 
;

-- 2023-02-01T21:07:07.549Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541667
;

-- 2023-02-01T21:07:07.550Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541667)
;

-- Tab: Credit Limit (Departments)(541667,D) -> Editierbares Attribut
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T21:07:40.194Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579873,0,546755,542288,541667,'Y',TO_TIMESTAMP('2023-02-01 23:07:40','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_BPartner_CreditLimit_Departments_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Editierbares Attribut','N',10,0,TO_TIMESTAMP('2023-02-01 23:07:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:07:40.195Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546755 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-01T21:07:40.198Z
/* DDL */  select update_tab_translation_from_ad_element(579873) 
;

-- 2023-02-01T21:07:40.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546755)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Geschäftspartner
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T21:08:22.404Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585696,710782,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:22.405Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:22.407Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-01T21:08:22.419Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710782
;

-- 2023-02-01T21:08:22.420Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710782)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Sektionskennung
-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T21:08:22.518Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585697,710783,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:22.520Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:22.522Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-02-01T21:08:22.526Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710783
;

-- 2023-02-01T21:08:22.527Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710783)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Department
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T21:08:22.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585698,710784,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:22.615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:22.617Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-02-01T21:08:22.620Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710784
;

-- 2023-02-01T21:08:22.622Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710784)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Währung
-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T21:08:22.724Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585699,710785,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:22.725Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:22.727Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-01T21:08:22.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710785
;

-- 2023-02-01T21:08:22.745Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710785)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Kredit gewährt
-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T21:08:22.840Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585700,710786,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,'Gegenwärtiger Aussenstand',14,'D','The Credit Used indicates the total amount of open or unpaid invoices in primary accounting currency for the Business Partner. Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','N','N','N','N','N','N','N','Kredit gewährt',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:22.841Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:22.843Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(554) 
;

-- 2023-02-01T21:08:22.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710786
;

-- 2023-02-01T21:08:22.847Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710786)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Order Open Amount (Department)
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-01T21:08:22.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585701,710787,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Order Open Amount (Department)',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:22.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:22.984Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581982) 
;

-- 2023-02-01T21:08:22.991Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710787
;

-- 2023-02-01T21:08:22.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710787)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Delivery Credit Used
-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T21:08:23.074Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585702,710788,0,546755,TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',14,'D','Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','N','N','N','N','N','N','N','Delivery Credit Used',TO_TIMESTAMP('2023-02-01 23:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.077Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581934) 
;

-- 2023-02-01T21:08:23.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710788
;

-- 2023-02-01T21:08:23.081Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710788)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Total Order Value
-- Column: C_BPartner_CreditLimit_Departments_V.TotalOrderValue
-- 2023-02-01T21:08:23.180Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585703,710789,0,546755,TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Total Order Value',TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.182Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581984) 
;

-- 2023-02-01T21:08:23.185Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710789
;

-- 2023-02-01T21:08:23.186Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710789)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Offene Posten
-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T21:08:23.281Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585704,710790,0,546755,TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100,'',14,'D','','Y','N','N','N','N','N','N','N','Offene Posten',TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.283Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.284Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543858) 
;

-- 2023-02-01T21:08:23.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710790
;

-- 2023-02-01T21:08:23.288Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710790)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Credit limit
-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T21:08:23.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585705,710791,0,546755,TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed',14,'D','The Credit Limit field indicates the credit limit for this account.','Y','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(855) 
;

-- 2023-02-01T21:08:23.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710791
;

-- 2023-02-01T21:08:23.382Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710791)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Credit Limit (Departments)
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T21:08:23.473Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585706,710792,0,546755,TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Credit Limit (Departments)',TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.476Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581986) 
;

-- 2023-02-01T21:08:23.479Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710792
;

-- 2023-02-01T21:08:23.480Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710792)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Aktiv
-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T21:08:23.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585711,710793,0,546755,TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',2147483647,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.566Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.567Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-01T21:08:23.790Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710793
;

-- 2023-02-01T21:08:23.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710793)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Mandant
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T21:08:23.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585712,710794,0,546755,TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-02-01 23:08:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:23.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:23.891Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-01T21:08:24.064Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710794
;

-- 2023-02-01T21:08:24.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710794)
;

-- Field: Credit Limit (Departments)(541667,D) -> Editierbares Attribut(546755,D) -> Sektion
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T21:08:24.168Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585713,710795,0,546755,TO_TIMESTAMP('2023-02-01 23:08:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-01 23:08:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:08:24.170Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:08:24.171Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-01T21:08:24.337Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710795
;

-- 2023-02-01T21:08:24.338Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710795)
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T21:08:47.995Z
UPDATE AD_Tab SET AD_Element_ID=581986, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Credit Limit (Departments)',Updated=TO_TIMESTAMP('2023-02-01 23:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546755
;

-- 2023-02-01T21:08:47.997Z
UPDATE AD_Tab_Trl trl SET Name='Credit Limit (Departments)' WHERE AD_Tab_ID=546755 AND AD_Language='de_DE'
;

-- 2023-02-01T21:08:47.998Z
/* DDL */  select update_tab_translation_from_ad_element(581986) 
;

-- 2023-02-01T21:08:48.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546755)
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D)
-- UI Section: main
-- 2023-02-01T21:09:15.161Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546755,545388,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-01T21:09:15.163Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545388 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main
-- UI Column: 10
-- 2023-02-01T21:09:15.285Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546572,545388,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main
-- UI Column: 20
-- 2023-02-01T21:09:15.376Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546573,545388,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10
-- UI Element Group: default
-- 2023-02-01T21:09:15.522Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546572,550264,TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-02-01 23:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:09:53.537Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710782
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Geschäftspartner
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T21:09:53.541Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710782
;

-- 2023-02-01T21:09:53.548Z
DELETE FROM AD_Field WHERE AD_Field_ID=710782
;

-- 2023-02-01T21:09:53.605Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710783
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Sektionskennung
-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T21:09:53.608Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710783
;

-- 2023-02-01T21:09:53.614Z
DELETE FROM AD_Field WHERE AD_Field_ID=710783
;

-- 2023-02-01T21:09:53.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710784
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Department
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T21:09:53.675Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710784
;

-- 2023-02-01T21:09:53.680Z
DELETE FROM AD_Field WHERE AD_Field_ID=710784
;

-- 2023-02-01T21:09:53.734Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710785
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Währung
-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T21:09:53.738Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710785
;

-- 2023-02-01T21:09:53.771Z
DELETE FROM AD_Field WHERE AD_Field_ID=710785
;

-- 2023-02-01T21:09:53.827Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710786
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Kredit gewährt
-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T21:09:53.831Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710786
;

-- 2023-02-01T21:09:53.838Z
DELETE FROM AD_Field WHERE AD_Field_ID=710786
;

-- 2023-02-01T21:09:53.891Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710787
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Order Open Amount (Department)
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-01T21:09:53.894Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710787
;

-- 2023-02-01T21:09:53.900Z
DELETE FROM AD_Field WHERE AD_Field_ID=710787
;

-- 2023-02-01T21:09:53.957Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710788
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Delivery Credit Used
-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T21:09:53.959Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710788
;

-- 2023-02-01T21:09:53.965Z
DELETE FROM AD_Field WHERE AD_Field_ID=710788
;

-- 2023-02-01T21:09:54.016Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710789
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Total Order Value
-- Column: C_BPartner_CreditLimit_Departments_V.TotalOrderValue
-- 2023-02-01T21:09:54.019Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710789
;

-- 2023-02-01T21:09:54.025Z
DELETE FROM AD_Field WHERE AD_Field_ID=710789
;

-- 2023-02-01T21:09:54.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710790
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Offene Posten
-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T21:09:54.080Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710790
;

-- 2023-02-01T21:09:54.085Z
DELETE FROM AD_Field WHERE AD_Field_ID=710790
;

-- 2023-02-01T21:09:54.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710791
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Credit limit
-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T21:09:54.139Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710791
;

-- 2023-02-01T21:09:54.144Z
DELETE FROM AD_Field WHERE AD_Field_ID=710791
;

-- 2023-02-01T21:09:54.192Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710792
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Credit Limit (Departments)
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T21:09:54.196Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710792
;

-- 2023-02-01T21:09:54.200Z
DELETE FROM AD_Field WHERE AD_Field_ID=710792
;

-- 2023-02-01T21:09:54.246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710793
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Aktiv
-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T21:09:54.248Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710793
;

-- 2023-02-01T21:09:54.252Z
DELETE FROM AD_Field WHERE AD_Field_ID=710793
;

-- 2023-02-01T21:09:54.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710794
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Mandant
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T21:09:54.299Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710794
;

-- 2023-02-01T21:09:54.304Z
DELETE FROM AD_Field WHERE AD_Field_ID=710794
;

-- 2023-02-01T21:09:54.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710795
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Sektion
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T21:09:54.359Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710795
;

-- 2023-02-01T21:09:54.364Z
DELETE FROM AD_Field WHERE AD_Field_ID=710795
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Geschäftspartner
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T21:10:07.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585696,710796,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:07.414Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:07.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-01T21:10:07.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710796
;

-- 2023-02-01T21:10:07.425Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710796)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Sektionskennung
-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T21:10:07.531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585697,710797,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:07.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:07.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-02-01T21:10:07.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710797
;

-- 2023-02-01T21:10:07.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710797)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Department
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T21:10:07.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585698,710798,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:07.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:07.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-02-01T21:10:07.632Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710798
;

-- 2023-02-01T21:10:07.633Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710798)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Währung
-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T21:10:07.739Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585699,710799,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:07.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:07.741Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-01T21:10:07.756Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710799
;

-- 2023-02-01T21:10:07.757Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710799)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Kredit gewährt
-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T21:10:07.847Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585700,710800,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,'Gegenwärtiger Aussenstand',14,'D','The Credit Used indicates the total amount of open or unpaid invoices in primary accounting currency for the Business Partner. Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','N','N','N','N','N','N','N','Kredit gewährt',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:07.848Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:07.849Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(554) 
;

-- 2023-02-01T21:10:07.852Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710800
;

-- 2023-02-01T21:10:07.853Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710800)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Order Open Amount (Department)
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-01T21:10:07.943Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585701,710801,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Order Open Amount (Department)',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:07.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:07.953Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581982) 
;

-- 2023-02-01T21:10:07.956Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710801
;

-- 2023-02-01T21:10:07.957Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710801)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Delivery Credit Used
-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T21:10:08.057Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585702,710802,0,546755,TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',14,'D','Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','N','N','N','N','N','N','N','Delivery Credit Used',TO_TIMESTAMP('2023-02-01 23:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.058Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.059Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581934) 
;

-- 2023-02-01T21:10:08.062Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710802
;

-- 2023-02-01T21:10:08.063Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710802)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Total Order Value
-- Column: C_BPartner_CreditLimit_Departments_V.TotalOrderValue
-- 2023-02-01T21:10:08.149Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585703,710803,0,546755,TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Total Order Value',TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.150Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581984) 
;

-- 2023-02-01T21:10:08.154Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710803
;

-- 2023-02-01T21:10:08.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710803)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Offene Posten
-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T21:10:08.261Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585704,710804,0,546755,TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,'',14,'D','','Y','N','N','N','N','N','N','N','Offene Posten',TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.262Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.263Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543858) 
;

-- 2023-02-01T21:10:08.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710804
;

-- 2023-02-01T21:10:08.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710804)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Credit limit
-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T21:10:08.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585705,710805,0,546755,TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed',14,'D','The Credit Limit field indicates the credit limit for this account.','Y','N','N','N','N','N','N','N','Credit limit',TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.366Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.367Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(855) 
;

-- 2023-02-01T21:10:08.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710805
;

-- 2023-02-01T21:10:08.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710805)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Credit Limit (Departments)
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T21:10:08.472Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585706,710806,0,546755,TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Credit Limit (Departments)',TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.475Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581986) 
;

-- 2023-02-01T21:10:08.478Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710806
;

-- 2023-02-01T21:10:08.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710806)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Aktiv
-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T21:10:08.573Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585711,710807,0,546755,TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',2147483647,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-01T21:10:08.769Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710807
;

-- 2023-02-01T21:10:08.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710807)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Mandant
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T21:10:08.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585712,710808,0,546755,TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-02-01 23:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:08.885Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:08.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-01T21:10:09.126Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710808
;

-- 2023-02-01T21:10:09.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710808)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Sektion
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T21:10:09.213Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585713,710809,0,546755,TO_TIMESTAMP('2023-02-01 23:10:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-01 23:10:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:10:09.214Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:10:09.216Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-01T21:10:09.404Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710809
;

-- 2023-02-01T21:10:09.405Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710809)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Geschäftspartner
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T21:10:16.047Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710796
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Sektionskennung
-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T21:10:17.172Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710797
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Department
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T21:10:17.615Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710798
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Währung
-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T21:10:18.235Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710799
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Kredit gewährt
-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T21:10:18.774Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710800
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Order Open Amount (Department)
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-01T21:10:19.398Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710801
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Delivery Credit Used
-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T21:10:19.864Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710802
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Total Order Value
-- Column: C_BPartner_CreditLimit_Departments_V.TotalOrderValue
-- 2023-02-01T21:10:20.318Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710803
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Offene Posten
-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T21:10:20.969Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710804
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Credit limit
-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T21:10:21.614Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710805
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Credit Limit (Departments)
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T21:10:22.242Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710806
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Aktiv
-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T21:10:22.928Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710807
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Mandant
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T21:10:24.190Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710808
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Sektion
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T21:10:25.967Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710809
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D)
-- UI Section: main
-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main
-- UI Column: 20
-- 2023-02-01T21:10:35.405Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546573
;

-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main
-- UI Column: 10
-- UI Column: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10
-- UI Element Group: default
-- 2023-02-01T21:10:35.420Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550264
;

-- 2023-02-01T21:10:35.426Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546572
;

-- 2023-02-01T21:10:35.428Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545388
;

-- 2023-02-01T21:10:35.433Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545388
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D)
-- UI Section: main
-- 2023-02-01T21:10:42.213Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546755,545389,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-01T21:10:42.218Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545389 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main
-- UI Column: 10
-- 2023-02-01T21:10:42.307Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546574,545389,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main
-- UI Column: 20
-- 2023-02-01T21:10:42.402Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546575,545389,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10
-- UI Element Group: default
-- 2023-02-01T21:10:42.501Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546574,550265,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T21:10:42.612Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710796,0,546755,550265,614922,'F',TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','Geschäftspartner',10,0,0,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Sektionskennung
-- Column: C_BPartner_CreditLimit_Departments_V.M_SectionCode_ID
-- 2023-02-01T21:10:42.723Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710797,0,546755,550265,614923,'F',TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektionskennung',20,0,0,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Department
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T21:10:42.828Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710798,0,546755,550265,614924,'F',TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Department',30,0,0,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T21:10:42.937Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710799,0,546755,550265,614925,'F',TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',40,0,0,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Kredit gewährt
-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T21:10:43.029Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710800,0,546755,550265,614926,'F',TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100,'Gegenwärtiger Aussenstand','The Credit Used indicates the total amount of open or unpaid invoices in primary accounting currency for the Business Partner. Credit Management is based on the Total Open Amount, which includes Vendor activities.','Y','N','Y','N','N','Kredit gewährt',50,0,0,TO_TIMESTAMP('2023-02-01 23:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Order Open Amount (Department)
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-01T21:10:43.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710801,0,546755,550265,614927,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Open Amount (Department)',60,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Delivery Credit Used
-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T21:10:43.222Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710802,0,546755,550265,614928,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Specifies the amount of money that was already spent from the credit for completed delivery instructions. ','Y','N','Y','N','N','Delivery Credit Used',70,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Total Order Value
-- Column: C_BPartner_CreditLimit_Departments_V.TotalOrderValue
-- 2023-02-01T21:10:43.317Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710803,0,546755,550265,614929,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Total Order Value',80,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Offene Posten
-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T21:10:43.420Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710804,0,546755,550265,614930,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Offene Posten',90,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Credit limit
-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T21:10:43.523Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710805,0,546755,550265,614931,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Amount of Credit allowed','The Credit Limit field indicates the credit limit for this account.','Y','N','Y','N','N','Credit limit',100,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Credit Limit (Departments)
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v
-- 2023-02-01T21:10:43.639Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710806,0,546755,550265,614932,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Credit Limit (Departments)',110,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Aktiv
-- Column: C_BPartner_CreditLimit_Departments_V.IsActive
-- 2023-02-01T21:10:43.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710807,0,546755,550265,614933,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',120,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Mandant
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Client_ID
-- 2023-02-01T21:10:43.854Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710808,0,546755,550265,614934,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',130,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_CreditLimit_Departments_V.AD_Org_ID
-- 2023-02-01T21:10:43.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710809,0,546755,550265,614935,'F',TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',140,0,0,TO_TIMESTAMP('2023-02-01 23:10:43','YYYY-MM-DD HH24:MI:SS'),100)
;





-- 2023-02-01T21:41:52.239Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581987,0,TO_TIMESTAMP('2023-02-01 23:41:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Usage','Credit Usage',TO_TIMESTAMP('2023-02-01 23:41:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:41:52.240Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581987 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Usage
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T21:42:20.388Z
UPDATE AD_Tab SET AD_Element_ID=581987, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Credit Usage',Updated=TO_TIMESTAMP('2023-02-01 23:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546755
;

-- 2023-02-01T21:42:20.389Z
UPDATE AD_Tab_Trl trl SET Name='Credit Usage' WHERE AD_Tab_ID=546755 AND AD_Language='de_DE'
;

-- 2023-02-01T21:42:20.391Z
/* DDL */  select update_tab_translation_from_ad_element(581987) 
;

-- 2023-02-01T21:42:20.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546755)
;





-- Name: Credit Limit (Departments)
-- Action Type: W
-- Window: Credit Limit (Departments)(541667,D)
-- 2023-02-01T21:12:48.469Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581986,542045,0,541667,TO_TIMESTAMP('2023-02-01 23:12:48','YYYY-MM-DD HH24:MI:SS'),100,'D','C_BPartner_CreditLimit_Departments_V','Y','N','N','N','N','Credit Limit (Departments)',TO_TIMESTAMP('2023-02-01 23:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:12:48.471Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542045 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-01T21:12:48.472Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542045, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542045)
;

-- 2023-02-01T21:12:48.484Z
/* DDL */  select update_menu_translation_from_ad_element(581986) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2023-02-01T21:12:49.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2023-02-01T21:12:49.081Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2023-02-01T21:12:49.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2023-02-01T21:12:49.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2023-02-01T21:12:49.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2023-02-01T21:12:49.085Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2023-02-01T21:12:49.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2023-02-01T21:12:49.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2023-02-01T21:12:49.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2023-02-01T21:12:49.089Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2023-02-01T21:12:49.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2023-02-01T21:12:49.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing (M_HU_Trace)`
-- 2023-02-01T21:12:49.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning (M_Delivery_Planning)`
-- 2023-02-01T21:12:49.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction (M_ShipperTransportation)`
-- 2023-02-01T21:12:49.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2023-02-01T21:12:49.095Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2023-02-01T21:12:49.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2023-02-01T21:12:49.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2023-02-01T21:12:49.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2023-02-01T21:12:49.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2023-02-01T21:12:49.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2023-02-01T21:12:49.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2023-02-01T21:12:49.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2023-02-01T21:12:49.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2023-02-01T21:12:49.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-02-01T21:12:49.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-02-01T21:12:49.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-02-01T21:12:49.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2023-02-01T21:12:49.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2023-02-01T21:12:49.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2023-02-01T21:12:49.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation (M_MeansOfTransportation)`
-- 2023-02-01T21:12:49.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Department (M_Department)`
-- 2023-02-01T21:12:49.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542041 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit (Departments)`
-- 2023-02-01T21:12:49.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542045 AND AD_Tree_ID=10
;

-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T21:13:11.886Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-01 23:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542288
;

-- Name: Credit Limit (Departments)
-- Action Type: W
-- Window: Credit Limit (Departments)(541667,D)
-- 2023-02-01T21:13:30.885Z
UPDATE AD_Menu SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-01 23:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542045
;

-- Window: Credit Limit (Departments), InternalName=null
-- 2023-02-01T21:13:43.131Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-01 23:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541667
;

-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-01T21:15:38.843Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585714,581322,0,30,541640,542288,'Section_Group_Partner_ID',TO_TIMESTAMP('2023-02-01 23:15:38','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Section Group Partner',0,0,TO_TIMESTAMP('2023-02-01 23:15:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-01T21:15:38.846Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585714 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-01T21:15:38.852Z
/* DDL */  select update_Column_Translation_From_AD_Element(581322) 
;

-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-01T21:16:10.157Z
UPDATE AD_Table SET AD_Window_ID=541667,Updated=TO_TIMESTAMP('2023-02-01 23:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542288
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Section Group Partner
-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-01T21:16:24.790Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585714,710810,0,546755,TO_TIMESTAMP('2023-02-01 23:16:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-02-01 23:16:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:16:24.791Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:16:24.794Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581322) 
;

-- 2023-02-01T21:16:24.798Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710810
;

-- 2023-02-01T21:16:24.799Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710810)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> Section Group Partner
-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-01T21:16:32.906Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710810
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Section Group Partner
-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-01T21:17:03.470Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710810,0,546755,550265,614936,'F',TO_TIMESTAMP('2023-02-01 23:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Group Partner',5,0,0,TO_TIMESTAMP('2023-02-01 23:17:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Section Group Partner
-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-01T21:20:24.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614936
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-01T21:20:24.462Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614922
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Department
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-01T21:20:24.468Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614924
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Kredit gewährt
-- Column: C_BPartner_CreditLimit_Departments_V.SO_CreditUsed
-- 2023-02-01T21:20:24.475Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614926
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Order Open Amount (Department)
-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_Order_OpenAmt
-- 2023-02-01T21:20:24.482Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614927
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Delivery Credit Used
-- Column: C_BPartner_CreditLimit_Departments_V.Delivery_CreditUsed
-- 2023-02-01T21:20:24.491Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614928
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Total Order Value
-- Column: C_BPartner_CreditLimit_Departments_V.TotalOrderValue
-- 2023-02-01T21:20:24.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614929
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Offene Posten
-- Column: C_BPartner_CreditLimit_Departments_V.OpenItems
-- 2023-02-01T21:20:24.507Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614930
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Credit limit
-- Column: C_BPartner_CreditLimit_Departments_V.CreditLimit
-- 2023-02-01T21:20:24.514Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614931
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Departments)(546755,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_CreditLimit_Departments_V.C_Currency_ID
-- 2023-02-01T21:20:24.519Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-02-01 23:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614925
;
























