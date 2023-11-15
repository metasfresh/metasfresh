-- Column: Fact_Acct_Transactions_View.POReference
-- 2023-07-12T13:02:55.133Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587086,952,0,14,541485,'POReference',TO_TIMESTAMP('2023-07-12 16:02:54','YYYY-MM-DD HH24:MI:SS'),100,'Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner','D',1024,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','N','N','N','Y','Order Reference',TO_TIMESTAMP('2023-07-12 16:02:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-12T13:02:55.136Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587086 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-12T13:02:55.691Z
/* DDL */  select update_Column_Translation_From_AD_Element(952) 
;

-- Column: Fact_Acct_Transactions_View.OpenItemKey
-- 2023-07-12T13:02:56.243Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587087,582532,0,10,541485,'OpenItemKey',TO_TIMESTAMP('2023-07-12 16:02:56','YYYY-MM-DD HH24:MI:SS'),100,'D',255,'Y','Y','N','N','N','N','N','N','N','N','Y','Open Item Key',TO_TIMESTAMP('2023-07-12 16:02:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-12T13:02:56.245Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587087 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-12T13:02:56.780Z
/* DDL */  select update_Column_Translation_From_AD_Element(582532) 
;

-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:02:57.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587088,582538,0,20,541485,'OI_TrxType',TO_TIMESTAMP('2023-07-12 16:02:57','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'Y','Y','N','N','N','N','N','N','N','N','Y','Open Item Transaction Type',TO_TIMESTAMP('2023-07-12 16:02:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-12T13:02:57.293Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587088 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-12T13:02:57.844Z
/* DDL */  select update_Column_Translation_From_AD_Element(582538) 
;

-- Column: Fact_Acct_Transactions_View.IsOpenItemsReconciled
-- 2023-07-12T13:02:58.385Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587089,582533,0,20,541485,'IsOpenItemsReconciled',TO_TIMESTAMP('2023-07-12 16:02:58','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'Y','Y','N','N','N','N','N','N','N','N','Y','Open Item Reconciled',TO_TIMESTAMP('2023-07-12 16:02:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-12T13:02:58.386Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587089 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-12T13:02:58.933Z
/* DDL */  select update_Column_Translation_From_AD_Element(582533) 
;

-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-12T13:02:59.498Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587090,582539,0,22,541485,'OI_OpenAmount',TO_TIMESTAMP('2023-07-12 16:02:59','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','Y','OI Open Amount',TO_TIMESTAMP('2023-07-12 16:02:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-12T13:02:59.499Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587090 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-12T13:03:00.050Z
/* DDL */  select update_Column_Translation_From_AD_Element(582539) 
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Order Reference
-- Column: Fact_Acct_Transactions_View.POReference
-- 2023-07-12T13:03:58.541Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587086,716656,0,242,TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100,'Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner',1024,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','N','N','N','N','N','Order Reference',TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-12T13:03:58.543Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716656 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-12T13:03:58.545Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2023-07-12T13:03:58.567Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716656
;

-- 2023-07-12T13:03:58.569Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716656)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Key
-- Column: Fact_Acct_Transactions_View.OpenItemKey
-- 2023-07-12T13:03:58.702Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587087,716657,0,242,TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Open Item Key',TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-12T13:03:58.704Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716657 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-12T13:03:58.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582532) 
;

-- 2023-07-12T13:03:58.710Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716657
;

-- 2023-07-12T13:03:58.711Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716657)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:03:58.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587088,716658,0,242,TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Open Item Transaction Type',TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-12T13:03:58.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716658 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-12T13:03:58.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582538) 
;

-- 2023-07-12T13:03:58.844Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716658
;

-- 2023-07-12T13:03:58.845Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716658)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Reconciled
-- Column: Fact_Acct_Transactions_View.IsOpenItemsReconciled
-- 2023-07-12T13:03:58.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587089,716659,0,242,TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Open Item Reconciled',TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-12T13:03:58.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716659 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-12T13:03:58.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582533) 
;

-- 2023-07-12T13:03:58.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716659
;

-- 2023-07-12T13:03:58.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716659)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount
-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-12T13:03:59.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587090,716660,0,242,TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','OI Open Amount',TO_TIMESTAMP('2023-07-12 16:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-12T13:03:59.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716660 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-12T13:03:59.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582539) 
;

-- 2023-07-12T13:03:59.092Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716660
;

-- 2023-07-12T13:03:59.093Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716660)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> document.Order Reference
-- Column: Fact_Acct_Transactions_View.POReference
-- 2023-07-12T13:06:05.332Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716656,0,242,540305,618243,'F',TO_TIMESTAMP('2023-07-12 16:06:05','YYYY-MM-DD HH24:MI:SS'),100,'Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','Order Reference',60,0,0,TO_TIMESTAMP('2023-07-12 16:06:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10
-- UI Element Group: Open Item
-- 2023-07-12T13:06:28.407Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540199,550825,TO_TIMESTAMP('2023-07-12 16:06:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','Open Item',30,TO_TIMESTAMP('2023-07-12 16:06:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> Open Item.Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:06:56.234Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716658,0,242,550825,618244,'F',TO_TIMESTAMP('2023-07-12 16:06:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Open Item Transaction Type',10,0,0,TO_TIMESTAMP('2023-07-12 16:06:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> Open Item.OI Open Amount
-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-12T13:07:07.427Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716660,0,242,550825,618245,'F',TO_TIMESTAMP('2023-07-12 16:07:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','OI Open Amount',20,0,0,TO_TIMESTAMP('2023-07-12 16:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> Open Item.Open Item Reconciled
-- Column: Fact_Acct_Transactions_View.IsOpenItemsReconciled
-- 2023-07-12T13:07:14.840Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716659,0,242,550825,618246,'F',TO_TIMESTAMP('2023-07-12 16:07:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Open Item Reconciled',30,0,0,TO_TIMESTAMP('2023-07-12 16:07:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> Open Item.Open Item Key
-- Column: Fact_Acct_Transactions_View.OpenItemKey
-- 2023-07-12T13:07:23.172Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716657,0,242,550825,618247,'F',TO_TIMESTAMP('2023-07-12 16:07:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Open Item Key',40,0,0,TO_TIMESTAMP('2023-07-12 16:07:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:08:30.934Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541800,Updated=TO_TIMESTAMP('2023-07-12 16:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587088
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:09:46.577Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/-@!''-''',Updated=TO_TIMESTAMP('2023-07-12 16:09:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716658
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:10:22.338Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/-@!-',Updated=TO_TIMESTAMP('2023-07-12 16:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716658
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-12T13:11:06.539Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2023-07-12 16:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716658
;

