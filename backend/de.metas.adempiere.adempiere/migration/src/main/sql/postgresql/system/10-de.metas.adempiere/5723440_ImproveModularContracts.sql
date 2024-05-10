-- Run mode: SWING_CLIENT

-- Name: C_DocType (invoice)
-- 2024-05-09T14:48:02.525Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541867,TO_TIMESTAMP('2024-05-09 17:48:02.324','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','C_DocType (invoice)',TO_TIMESTAMP('2024-05-09 17:48:02.324','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-05-09T14:48:02.534Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541867 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_DocType (invoice)
-- Table: C_DocType
-- Key: C_DocType.C_DocType_ID
-- 2024-05-09T14:48:25.093Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1501,0,541867,217,TO_TIMESTAMP('2024-05-09 17:48:25.067','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N',TO_TIMESTAMP('2024-05-09 17:48:25.067','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: C_DocType (invoice)
-- Table: C_DocType
-- Key: C_DocType.C_DocType_ID
-- 2024-05-09T14:48:37.743Z
UPDATE AD_Ref_Table SET AD_Display=1509, OrderByClause='C_DocType.IsDefault DESC /*Default=Y records first*/',Updated=TO_TIMESTAMP('2024-05-09 17:48:37.743','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541867
;

-- Reference: C_DocType (invoice)
-- Table: C_DocType
-- Key: C_DocType.C_DocType_ID
-- 2024-05-09T14:49:02.597Z
UPDATE AD_Ref_Table SET WhereClause='( C_DocType.DocBaseType IN (''API'',''APC'') OR (C_DocType.DocBaseType IN (''ARC'',''ARI'') AND C_DocType.DocSubType IS NULL) /*only the default types*/ OR (C_DocType.DocBaseType=''ARC'' AND C_DocType.DocSubType = ''CS'') /*only the RMA-credit-memo*/ ) AND C_DocType.IsSOTrx=''@IsSOTrx@'' AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2024-05-09 17:49:02.597','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541867
;

-- Name: C_DocType (invoice)
-- 2024-05-09T14:49:07.879Z
UPDATE AD_Reference SET Description='Document Type AR/AP Invoice and Credit Memos, PLUS return material credit memo. Excludes other special-purpose invoice and credit memo types that are available via the "C_Invoice_Create CreditMemo" proc',Updated=TO_TIMESTAMP('2024-05-09 17:49:07.876','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541867
;

-- 2024-05-09T14:49:07.884Z
UPDATE AD_Reference_Trl trl SET Description='Document Type AR/AP Invoice and Credit Memos, PLUS return material credit memo. Excludes other special-purpose invoice and credit memo types that are available via the "C_Invoice_Create CreditMemo" proc' WHERE AD_Reference_ID=541867 AND AD_Language='de_DE'
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T14:49:20.149Z
UPDATE AD_Column SET AD_Reference_Value_ID=541867,Updated=TO_TIMESTAMP('2024-05-09 17:49:20.149','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Field: Kreditoren Rechnung(541531,D) -> Eingangsrechnung(546366,D) -> Zielbelegart
-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T14:50:53.200Z
UPDATE AD_Field SET Filter_Val_Rule_ID=540294,Updated=TO_TIMESTAMP('2024-05-09 17:50:53.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=700215
;

-- Field: Kreditoren Rechnung(541531,D) -> Eingangsrechnung(546366,D) -> Zielbelegart
-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T14:52:55.192Z
UPDATE AD_Field SET AD_Reference_ID=18, AD_Val_Rule_ID=540294,Updated=TO_TIMESTAMP('2024-05-09 17:52:55.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=700215
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T15:16:11.240Z
UPDATE AD_Column SET Filter_Val_Rule_ID=540294,Updated=TO_TIMESTAMP('2024-05-09 18:16:11.24','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T16:32:44.221Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-05-09 19:32:44.221','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Field: Kreditoren Rechnung(541531,D) -> Eingangsrechnung(546366,D) -> Zielbelegart
-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T16:33:59.734Z
UPDATE AD_Field SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-05-09 19:33:59.734','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=700215
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T16:41:40.187Z
UPDATE AD_Column SET Filter_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2024-05-09 19:41:40.186','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Field: Kreditoren Rechnung(541531,D) -> Eingangsrechnung(546366,D) -> Zielbelegart
-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-09T16:42:01.028Z
UPDATE AD_Field SET Filter_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2024-05-09 19:42:01.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=700215
;




------------------------------
------------------------------
------------------------------
------------------------------
------------------------------
------------------------------
------------------------------
------------------------------


-- Name: Harvesting Year
-- 2024-05-10T06:47:58.925Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541868,TO_TIMESTAMP('2024-05-10 09:47:58.767','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Harvesting Year',TO_TIMESTAMP('2024-05-10 09:47:58.767','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-05-10T06:47:58.930Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541868 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Harvesting Year
-- Table: C_Year
-- Key: C_Year.C_Year_ID
-- 2024-05-10T06:48:20.440Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1038,1031,0,541868,177,TO_TIMESTAMP('2024-05-10 09:48:20.435','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','','N',TO_TIMESTAMP('2024-05-10 09:48:20.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Reference: Harvesting Year
-- Table: C_Year
-- Key: C_Year.C_Year_ID
-- 2024-05-10T06:48:45.186Z
UPDATE AD_Ref_Table SET AD_Window_ID=117, OrderByClause='C_Year.fiscalyear desc',Updated=TO_TIMESTAMP('2024-05-10 09:48:45.185','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541868
;

-- Column: C_Invoice.Harvesting_Year_ID
-- 2024-05-10T06:49:32.993Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 09:49:32.993','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587251
;

-- Column: C_Order.Harvesting_Year_ID
-- 2024-05-10T06:59:51.725Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 09:59:51.725','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587166
;

-- Column: C_ValidCombination.Harvesting_Year_ID
-- 2024-05-10T07:00:46.171Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:00:46.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587306
;

-- Column: Fact_Acct.Harvesting_Year_ID
-- 2024-05-10T07:01:00.591Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:01:00.591','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587313
;

-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2024-05-10T07:01:14.582Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868, AD_Val_Rule_ID=540647,Updated=TO_TIMESTAMP('2024-05-10 10:01:14.582','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587304
;

-- Column: M_InOutLine.Harvesting_Year_ID
-- 2024-05-10T07:01:22.523Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:01:22.523','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587311
;

-- Column: C_InvoiceLine.Harvesting_Year_ID
-- 2024-05-10T07:01:28.874Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:01:28.874','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587309
;

-- Column: C_Invoice_Candidate.Harvesting_Year_ID
-- 2024-05-10T07:01:36.119Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:01:36.119','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587249
;

-- Column: C_Flatrate_Term.Harvesting_Year_ID
-- 2024-05-10T07:01:41.645Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:01:41.645','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588108
;

-- Column: Fact_Acct_Transactions_View.Harvesting_Year_ID
-- 2024-05-10T07:01:48.083Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:01:48.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587327
;

-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2024-05-10T07:02:00.504Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868, AD_Val_Rule_ID=540647,Updated=TO_TIMESTAMP('2024-05-10 10:02:00.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586853
;

-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2024-05-10T07:02:36.691Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2024-05-10 10:02:36.691','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586853
;

-- Column: C_BPartner_InterimContract.Harvesting_Year_ID
-- 2024-05-10T07:02:55.357Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:02:55.356','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587240
;

-- Column: C_Auction.Harvesting_Year_ID
-- 2024-05-10T07:03:02.783Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:03:02.783','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587269
;

-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2024-05-10T07:03:09.253Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:03:09.253','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587393
;

-- Column: I_ModCntr_Log.Harvesting_Year_ID
-- 2024-05-10T07:03:16.883Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:03:16.883','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587534
;

-- Column: ModCntr_Settings.C_Year_ID
-- 2024-05-10T07:10:33.006Z
UPDATE AD_Column SET AD_Reference_Value_ID=541868,Updated=TO_TIMESTAMP('2024-05-10 10:10:33.005','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586792
;

-- Column: ModCntr_Module.M_Product_ID
-- 2024-05-10T07:17:13.871Z
UPDATE AD_Column SET SeqNo=3,Updated=TO_TIMESTAMP('2024-05-10 10:17:13.871','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586806
;

-- Column: ModCntr_Module.SeqNo
-- 2024-05-10T07:17:47.597Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2024-05-10 10:17:47.597','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586803
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Sequenz
-- Column: ModCntr_Module.SeqNo
-- 2024-05-10T07:21:17.654Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-05-10 10:21:17.654','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716350
;

-- Name: Filter Invoices
-- 2024-05-10T08:29:00.660Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540675,'( C_DocType.DocBaseType IN (''API'',''APC'') OR (C_DocType.DocBaseType IN (''ARC'',''ARI'') AND C_DocType.DocSubType IS NULL) /*only the default types*/ OR (C_DocType.DocBaseType=''ARC'' AND C_DocType.DocSubType = ''CS'') /*only the RMA-credit-memo*/ ) ',TO_TIMESTAMP('2024-05-10 11:29:00.486','YYYY-MM-DD HH24:MI:SS.US'),100,'','U','Y','Filter Invoices','S',TO_TIMESTAMP('2024-05-10 11:29:00.486','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-10T08:29:15.764Z
UPDATE AD_Column SET Filter_Val_Rule_ID=540675,Updated=TO_TIMESTAMP('2024-05-10 11:29:15.763','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-10T08:46:01.079Z
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2024-05-10 11:46:01.079','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3781
;

-- Field: Kreditoren Rechnung(541531,D) -> Eingangsrechnung(546366,D) -> Zielbelegart
-- Column: C_Invoice.C_DocTypeTarget_ID
-- 2024-05-10T08:53:05.940Z
UPDATE AD_Field SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2024-05-10 11:53:05.94','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=700215
;

-- Element: MasterStartDate
-- 2024-05-10T10:16:37.823Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertragsbeginn', PrintName='Vertragsbeginn',Updated=TO_TIMESTAMP('2024-05-10 13:16:37.823','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543413 AND AD_Language='de_CH'
;

-- 2024-05-10T10:16:37.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543413,'de_CH')
;

-- Element: MasterStartDate
-- 2024-05-10T10:16:43.530Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertragsbeginn', PrintName='Vertragsbeginn',Updated=TO_TIMESTAMP('2024-05-10 13:16:43.53','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543413 AND AD_Language='de_DE'
;

-- 2024-05-10T10:16:43.534Z
UPDATE AD_Element SET Name='Vertragsbeginn', PrintName='Vertragsbeginn' WHERE AD_Element_ID=543413
;

-- 2024-05-10T10:16:43.846Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543413,'de_DE')
;

-- 2024-05-10T10:16:43.848Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543413,'de_DE')
;

-- Element: MasterEndDate
-- 2024-05-10T10:17:11.791Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertragsende', PrintName='Vertragsende',Updated=TO_TIMESTAMP('2024-05-10 13:17:11.791','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543414 AND AD_Language='de_CH'
;

-- 2024-05-10T10:17:11.794Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543414,'de_CH')
;

-- Element: MasterEndDate
-- 2024-05-10T10:17:17.256Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertragsende', PrintName='Vertragsende',Updated=TO_TIMESTAMP('2024-05-10 13:17:17.256','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543414 AND AD_Language='de_DE'
;

-- 2024-05-10T10:17:17.257Z
UPDATE AD_Element SET Name='Vertragsende', PrintName='Vertragsende' WHERE AD_Element_ID=543414
;

-- 2024-05-10T10:17:17.482Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543414,'de_DE')
;

-- 2024-05-10T10:17:17.484Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543414,'de_DE')
;

-- Process: C_Flatrate_Term_ChangePrice(de.metas.contracts.flatrate.process.C_Flatrate_Term_ChangePriceQty)
-- 2024-05-10T10:19:12.411Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preis ändern',Updated=TO_TIMESTAMP('2024-05-10 13:19:12.41','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540890
;

-- Process: C_Flatrate_Term_ChangePrice(de.metas.contracts.flatrate.process.C_Flatrate_Term_ChangePriceQty)
-- 2024-05-10T10:19:15.516Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preis ändern',Updated=TO_TIMESTAMP('2024-05-10 13:19:15.516','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540890
;

-- 2024-05-10T10:19:15.517Z
UPDATE AD_Process SET Name='Preis ändern' WHERE AD_Process_ID=540890
;

-- Process: C_Flatrate_Term_ChangeQty(de.metas.contracts.flatrate.process.C_Flatrate_Term_ChangePriceQty)
-- 2024-05-10T10:19:48.343Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Menge ändern',Updated=TO_TIMESTAMP('2024-05-10 13:19:48.343','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540891
;

-- Process: C_Flatrate_Term_ChangeQty(de.metas.contracts.flatrate.process.C_Flatrate_Term_ChangePriceQty)
-- 2024-05-10T10:19:50.662Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Menge ändern',Updated=TO_TIMESTAMP('2024-05-10 13:19:50.662','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540891
;

-- 2024-05-10T10:19:50.664Z
UPDATE AD_Process SET Name='Menge ändern' WHERE AD_Process_ID=540891
;

