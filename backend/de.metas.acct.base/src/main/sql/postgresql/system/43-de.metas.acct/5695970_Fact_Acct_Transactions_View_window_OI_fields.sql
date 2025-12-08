-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:17:17.894Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587124,582550,0,22,541485,'OI_OpenAmountSource',TO_TIMESTAMP('2023-07-16 10:17:17','YYYY-MM-DD HH24:MI:SS'),100,'OI Open amount in source currency','D',14,'Y','Y','N','N','N','N','N','N','N','N','Y','OI Open Amount (source)',TO_TIMESTAMP('2023-07-16 10:17:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-16T07:17:17.897Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-16T07:17:19.012Z
/* DDL */  select update_Column_Translation_From_AD_Element(582550) 
;

-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:17:37.775Z
UPDATE AD_Column SET AD_Reference_ID=12, FieldLength=10,Updated=TO_TIMESTAMP('2023-07-16 10:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587124
;

-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-16T07:17:50.783Z
UPDATE AD_Column SET AD_Reference_ID=12, FieldLength=10,Updated=TO_TIMESTAMP('2023-07-16 10:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587090
;

-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-16T07:18:34.894Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-07-16 10:18:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587088
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount (source)
-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:19:26.125Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587124,716707,0,242,TO_TIMESTAMP('2023-07-16 10:19:25','YYYY-MM-DD HH24:MI:SS'),100,'OI Open amount in source currency',10,'D','Y','N','N','N','N','N','N','N','OI Open Amount (source)',TO_TIMESTAMP('2023-07-16 10:19:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-16T07:19:26.133Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716707 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-16T07:19:26.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582550) 
;

-- 2023-07-16T07:19:26.161Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716707
;

-- 2023-07-16T07:19:26.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716707)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> Open Item.OI Open Amount (source)
-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:21:27.064Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716707,0,242,550825,618288,'F',TO_TIMESTAMP('2023-07-16 10:21:26','YYYY-MM-DD HH24:MI:SS'),100,'OI Open amount in source currency','Y','N','Y','N','N','OI Open Amount (source)',50,0,0,TO_TIMESTAMP('2023-07-16 10:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> Open Item.OI Open Amount (source)
-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:21:50.589Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-07-16 10:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618288
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount (source)
-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:23:15.460Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/X@!X',Updated=TO_TIMESTAMP('2023-07-16 10:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716707
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount
-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-16T07:23:23.687Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/X@!X',Updated=TO_TIMESTAMP('2023-07-16 10:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716660
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-16T07:23:40.339Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/X@!X',Updated=TO_TIMESTAMP('2023-07-16 10:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716658
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Key
-- Column: Fact_Acct_Transactions_View.OpenItemKey
-- 2023-07-16T07:23:51.172Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/X@!X',Updated=TO_TIMESTAMP('2023-07-16 10:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716657
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Reconciled
-- Column: Fact_Acct_Transactions_View.IsOpenItemsReconciled
-- 2023-07-16T07:24:03.928Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/X@!X',Updated=TO_TIMESTAMP('2023-07-16 10:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716659
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Transaction Type
-- Column: Fact_Acct_Transactions_View.OI_TrxType
-- 2023-07-16T07:25:26.220Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@!''''',Updated=TO_TIMESTAMP('2023-07-16 10:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716658
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Reconciled
-- Column: Fact_Acct_Transactions_View.IsOpenItemsReconciled
-- 2023-07-16T07:26:04.270Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@!''''',Updated=TO_TIMESTAMP('2023-07-16 10:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716659
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount
-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-16T07:26:06.503Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@!''''',Updated=TO_TIMESTAMP('2023-07-16 10:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716660
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount (source)
-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:26:08.309Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@!''''',Updated=TO_TIMESTAMP('2023-07-16 10:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716707
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Open Item Key
-- Column: Fact_Acct_Transactions_View.OpenItemKey
-- 2023-07-16T07:26:10.381Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@!''''',Updated=TO_TIMESTAMP('2023-07-16 10:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716657
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount
-- Column: Fact_Acct_Transactions_View.OI_OpenAmount
-- 2023-07-16T07:27:17.818Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@=O',Updated=TO_TIMESTAMP('2023-07-16 10:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716660
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> OI Open Amount (source)
-- Column: Fact_Acct_Transactions_View.OI_OpenAmountSource
-- 2023-07-16T07:27:22.765Z
UPDATE AD_Field SET DisplayLogic='@OI_TrxType/@=O',Updated=TO_TIMESTAMP('2023-07-16 10:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716707
;

