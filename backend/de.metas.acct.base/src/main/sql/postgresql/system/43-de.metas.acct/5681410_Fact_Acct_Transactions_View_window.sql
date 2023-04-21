-- Column: Fact_Acct_Transactions_View.C_BPartner2_ID
-- 2023-03-10T12:06:46.815Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586303,582129,0,30,541252,541485,'C_BPartner2_ID',TO_TIMESTAMP('2023-03-10 14:06:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner (2)',0,0,TO_TIMESTAMP('2023-03-10 14:06:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-10T12:06:46.817Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586303 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-10T12:06:46.824Z
/* DDL */  select update_Column_Translation_From_AD_Element(582129) 
;

-- Column: Fact_Acct_Transactions_View.C_BPartner2_ID
-- 2023-03-10T12:07:00.613Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-03-10 14:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586303
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Business Partner (2)
-- Column: Fact_Acct_Transactions_View.C_BPartner2_ID
-- 2023-03-10T12:07:19.688Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586303,712827,0,242,TO_TIMESTAMP('2023-03-10 14:07:19','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Business Partner (2)',TO_TIMESTAMP('2023-03-10 14:07:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T12:07:19.691Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-10T12:07:19.693Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582129) 
;

-- 2023-03-10T12:07:19.696Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712827
;

-- 2023-03-10T12:07:19.697Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712827)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Asset
-- Column: Fact_Acct_Transactions_View.A_Asset_ID
-- 2023-03-10T12:08:00.126Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710012
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Counterpart Accounting Fact
-- Column: Fact_Acct_Transactions_View.Counterpart_Fact_Acct_ID
-- 2023-03-10T12:08:00.132Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710019
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Document Base Type
-- Column: Fact_Acct_Transactions_View.DocBaseType
-- 2023-03-10T12:08:00.138Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710017
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Document Type
-- Column: Fact_Acct_Transactions_View.C_DocType_ID
-- 2023-03-10T12:08:00.143Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710016
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Line ID
-- Column: Fact_Acct_Transactions_View.Line_ID
-- 2023-03-10T12:08:00.149Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=410,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710011
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Project Phase
-- Column: Fact_Acct_Transactions_View.C_ProjectPhase_ID
-- 2023-03-10T12:08:00.155Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=420,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710013
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Project Task
-- Column: Fact_Acct_Transactions_View.C_ProjectTask_ID
-- 2023-03-10T12:08:00.161Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=430,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710014
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Sales Order
-- Column: Fact_Acct_Transactions_View.C_OrderSO_ID
-- 2023-03-10T12:08:00.166Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=440,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710030
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> SubLine ID
-- Column: Fact_Acct_Transactions_View.SubLine_ID
-- 2023-03-10T12:08:00.171Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=450,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710015
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString1
-- Column: Fact_Acct_Transactions_View.UserElementString1
-- 2023-03-10T12:08:00.177Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=460,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710023
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString2
-- Column: Fact_Acct_Transactions_View.UserElementString2
-- 2023-03-10T12:08:00.182Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=470,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710024
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString3
-- Column: Fact_Acct_Transactions_View.UserElementString3
-- 2023-03-10T12:08:00.187Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=480,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710025
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString4
-- Column: Fact_Acct_Transactions_View.UserElementString4
-- 2023-03-10T12:08:00.193Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=490,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710026
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString5
-- Column: Fact_Acct_Transactions_View.UserElementString5
-- 2023-03-10T12:08:00.197Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710027
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString6
-- Column: Fact_Acct_Transactions_View.UserElementString6
-- 2023-03-10T12:08:00.203Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=510,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710028
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString7
-- Column: Fact_Acct_Transactions_View.UserElementString7
-- 2023-03-10T12:08:00.208Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=520,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710029
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> VAT Code
-- Column: Fact_Acct_Transactions_View.VATCode
-- 2023-03-10T12:08:00.213Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=530,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710018
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Business Partner (2)
-- Column: Fact_Acct_Transactions_View.C_BPartner2_ID
-- 2023-03-10T12:08:00.218Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=540,Updated=TO_TIMESTAMP('2023-03-10 14:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712827
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> references.Business Partner (2)
-- Column: Fact_Acct_Transactions_View.C_BPartner2_ID
-- 2023-03-10T12:08:52.496Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712827,0,242,540306,616002,'F',TO_TIMESTAMP('2023-03-10 14:08:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Business Partner (2)',60,0,0,TO_TIMESTAMP('2023-03-10 14:08:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: Fact_Acct_Transactions_View.C_BPartner2_ID
-- 2023-03-10T12:09:28.490Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-03-10 14:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586303
;

