-- 2023-02-16T10:20:20.295Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582074,0,'P_CostClearing_Acct',TO_TIMESTAMP('2023-02-16 12:20:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Clearing Account','Cost Clearing Account',TO_TIMESTAMP('2023-02-16 12:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-16T10:20:20.297Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582074 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- Column: C_AcctSchema_Default.P_CostClearing_Acct
-- 2023-02-16T10:42:56.951Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586066,582074,0,25,315,'P_CostClearing_Acct',TO_TIMESTAMP('2023-02-16 12:42:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cost Clearing Account',0,0,TO_TIMESTAMP('2023-02-16 12:42:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-16T10:42:56.952Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586066 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-16T10:42:56.955Z
/* DDL */  select update_Column_Translation_From_AD_Element(582074) 
;

-- 2023-02-16T10:42:59.673Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_Default','ALTER TABLE public.C_AcctSchema_Default ADD COLUMN P_CostClearing_Acct NUMERIC(10)')
;

-- 2023-02-16T10:42:59.688Z
ALTER TABLE C_AcctSchema_Default ADD CONSTRAINT PCostClearingA_CAcctSchemaDefault FOREIGN KEY (P_CostClearing_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;

-- Field: Accounting Schema(125,D) -> Defaults(252,D) -> Cost Clearing Account
-- Column: C_AcctSchema_Default.P_CostClearing_Acct
-- 2023-02-16T10:43:19.673Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586066,712606,0,252,TO_TIMESTAMP('2023-02-16 12:43:19','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Clearing Account',TO_TIMESTAMP('2023-02-16 12:43:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-16T10:43:19.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712606 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-16T10:43:19.683Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582074) 
;

-- 2023-02-16T10:43:19.696Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712606
;

-- 2023-02-16T10:43:19.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712606)
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Cost Clearing Account
-- Column: C_AcctSchema_Default.P_CostClearing_Acct
-- 2023-02-16T10:43:41.227Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712606,0,252,541334,615841,'F',TO_TIMESTAMP('2023-02-16 12:43:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Clearing Account',750,0,0,TO_TIMESTAMP('2023-02-16 12:43:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Cost Clearing Account
-- Column: C_AcctSchema_Default.P_CostClearing_Acct
-- 2023-02-16T10:46:26.647Z
UPDATE AD_UI_Element SET SeqNo=205,Updated=TO_TIMESTAMP('2023-02-16 12:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615841
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Cost Clearing Account
-- Column: C_AcctSchema_Default.P_CostClearing_Acct
-- 2023-02-16T10:47:22.536Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615841
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Produkt Ertrag
-- Column: C_AcctSchema_Default.P_Revenue_Acct
-- 2023-02-16T10:47:22.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549986
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Produkt Vertriebsausgaben
-- Column: C_AcctSchema_Default.P_COGS_Acct
-- 2023-02-16T10:47:22.548Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549987
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Preisdifferenz Bestellung
-- Column: C_AcctSchema_Default.P_PurchasePriceVariance_Acct
-- 2023-02-16T10:47:22.556Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549988
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Preisdifferenz Einkauf Rechnung
-- Column: C_AcctSchema_Default.P_InvoicePriceVariance_Acct
-- 2023-02-16T10:47:22.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549989
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Erhaltene Rabatte
-- Column: C_AcctSchema_Default.P_TradeDiscountRec_Acct
-- 2023-02-16T10:47:22.568Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549990
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Gewährte Rabatte
-- Column: C_AcctSchema_Default.P_TradeDiscountGrant_Acct
-- 2023-02-16T10:47:22.586Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549991
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.(Not Used)
-- Column: C_AcctSchema_Default.W_Inventory_Acct
-- 2023-02-16T10:47:22.592Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549992
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Lager Wert Korrektur
-- Column: C_AcctSchema_Default.W_InvActualAdjust_Acct
-- 2023-02-16T10:47:22.598Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549993
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Lager Bestand Korrektur
-- Column: C_AcctSchema_Default.W_Differences_Acct
-- 2023-02-16T10:47:22.603Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549994
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Lager Wert Korrektur Währungsdifferenz
-- Column: C_AcctSchema_Default.W_Revaluation_Acct
-- 2023-02-16T10:47:22.608Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549995
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Project Asset
-- Column: C_AcctSchema_Default.PJ_Asset_Acct
-- 2023-02-16T10:47:22.613Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549996
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Unfertige Leistungen
-- Column: C_AcctSchema_Default.PJ_WIP_Acct
-- 2023-02-16T10:47:22.617Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549997
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Unfertige Leistungen
-- Column: C_AcctSchema_Default.P_WIP_Acct
-- 2023-02-16T10:47:22.623Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549998
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Floor Stock
-- Column: C_AcctSchema_Default.P_FloorStock_Acct
-- 2023-02-16T10:47:22.627Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549999
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Method Change Variance
-- Column: C_AcctSchema_Default.P_MethodChangeVariance_Acct
-- 2023-02-16T10:47:22.633Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=360,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550000
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Usage Variance
-- Column: C_AcctSchema_Default.P_UsageVariance_Acct
-- 2023-02-16T10:47:22.637Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=370,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550001
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Rate Variance
-- Column: C_AcctSchema_Default.P_RateVariance_Acct
-- 2023-02-16T10:47:22.642Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=380,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550002
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Mix Variance
-- Column: C_AcctSchema_Default.P_MixVariance_Acct
-- 2023-02-16T10:47:22.647Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=390,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550003
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Labor
-- Column: C_AcctSchema_Default.P_Labor_Acct
-- 2023-02-16T10:47:22.652Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=400,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550004
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Burden
-- Column: C_AcctSchema_Default.P_Burden_Acct
-- 2023-02-16T10:47:22.657Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=410,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550005
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Cost Of Production
-- Column: C_AcctSchema_Default.P_CostOfProduction_Acct
-- 2023-02-16T10:47:22.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=420,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550006
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Outside Processing
-- Column: C_AcctSchema_Default.P_OutsideProcessing_Acct
-- 2023-02-16T10:47:22.666Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=430,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550007
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Overhead
-- Column: C_AcctSchema_Default.P_Overhead_Acct
-- 2023-02-16T10:47:22.671Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=440,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550008
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Scrap
-- Column: C_AcctSchema_Default.P_Scrap_Acct
-- 2023-02-16T10:47:22.676Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=450,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550009
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Bank
-- Column: C_AcctSchema_Default.B_Asset_Acct
-- 2023-02-16T10:47:22.681Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=460,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550010
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Bank In Transit
-- Column: C_AcctSchema_Default.B_InTransit_Acct
-- 2023-02-16T10:47:22.686Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=470,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550011
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Bank Unidentified Receipts
-- Column: C_AcctSchema_Default.B_Unidentified_Acct
-- 2023-02-16T10:47:22.691Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=480,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550012
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Nicht zugeordnete Zahlungen
-- Column: C_AcctSchema_Default.B_UnallocatedCash_Acct
-- 2023-02-16T10:47:22.696Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=490,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550013
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Bezahlung selektiert
-- Column: C_AcctSchema_Default.B_PaymentSelect_Acct
-- 2023-02-16T10:47:22.701Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=500,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550014
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Nebenkosten des Geldverkehrs
-- Column: C_AcctSchema_Default.B_Expense_Acct
-- 2023-02-16T10:47:22.707Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=510,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550015
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Zins Aufwendungen
-- Column: C_AcctSchema_Default.B_InterestExp_Acct
-- 2023-02-16T10:47:22.712Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=520,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550016
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Zinserträge
-- Column: C_AcctSchema_Default.B_InterestRev_Acct
-- 2023-02-16T10:47:22.717Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=530,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550017
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Erträge aus Kursdifferenzen
-- Column: C_AcctSchema_Default.B_RevaluationGain_Acct
-- 2023-02-16T10:47:22.722Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=540,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550018
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Währungsverluste
-- Column: C_AcctSchema_Default.B_RevaluationLoss_Acct
-- 2023-02-16T10:47:22.727Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=550,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550019
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Bank Settlement Loss
-- Column: C_AcctSchema_Default.B_SettlementLoss_Acct
-- 2023-02-16T10:47:22.734Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=560,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550020
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Bank Settlement Gain
-- Column: C_AcctSchema_Default.B_SettlementGain_Acct
-- 2023-02-16T10:47:22.739Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=570,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550021
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Geschuldete MwSt.
-- Column: C_AcctSchema_Default.T_Due_Acct
-- 2023-02-16T10:47:22.746Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=580,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550022
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Verbindlichkeiten aus Steuern
-- Column: C_AcctSchema_Default.T_Liability_Acct
-- 2023-02-16T10:47:22.753Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=590,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550023
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Vorsteuer
-- Column: C_AcctSchema_Default.T_Credit_Acct
-- 2023-02-16T10:47:22.759Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=600,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550024
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Steuerüberzahlungen
-- Column: C_AcctSchema_Default.T_Receivables_Acct
-- 2023-02-16T10:47:22.766Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=610,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550025
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Sonstige Steuern
-- Column: C_AcctSchema_Default.T_Expense_Acct
-- 2023-02-16T10:47:22.774Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=620,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550026
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Betriebliche Aufwendungen
-- Column: C_AcctSchema_Default.Ch_Expense_Acct
-- 2023-02-16T10:47:22.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=630,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550027
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Sonstige Einnahmen
-- Column: C_AcctSchema_Default.Ch_Revenue_Acct
-- 2023-02-16T10:47:22.787Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=640,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550028
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Nicht realisierte Währungsgewinne
-- Column: C_AcctSchema_Default.UnrealizedGain_Acct
-- 2023-02-16T10:47:22.793Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=650,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550029
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Nicht realisierte Währungsverluste
-- Column: C_AcctSchema_Default.UnrealizedLoss_Acct
-- 2023-02-16T10:47:22.800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=660,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550030
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Realisierte Währungsgewinne
-- Column: C_AcctSchema_Default.RealizedGain_Acct
-- 2023-02-16T10:47:22.807Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=670,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550031
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Realisierte Währungsverluste
-- Column: C_AcctSchema_Default.RealizedLoss_Acct
-- 2023-02-16T10:47:22.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=680,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550032
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Kasse
-- Column: C_AcctSchema_Default.CB_Asset_Acct
-- 2023-02-16T10:47:22.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=690,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550033
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Kassendifferenz
-- Column: C_AcctSchema_Default.CB_Differences_Acct
-- 2023-02-16T10:47:22.825Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=700,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550034
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Geldtransit
-- Column: C_AcctSchema_Default.CB_CashTransfer_Acct
-- 2023-02-16T10:47:22.831Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=710,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550035
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Kasse Aufwand
-- Column: C_AcctSchema_Default.CB_Expense_Acct
-- 2023-02-16T10:47:22.838Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=720,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550036
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Kasse Ertrag
-- Column: C_AcctSchema_Default.CB_Receipt_Acct
-- 2023-02-16T10:47:22.846Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=730,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550037
;

-- UI Element: Accounting Schema(125,D) -> Defaults(252,D) -> main -> 10 -> default.Sektion
-- Column: C_AcctSchema_Default.AD_Org_ID
-- 2023-02-16T10:47:22.852Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=740,Updated=TO_TIMESTAMP('2023-02-16 12:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549964
;

