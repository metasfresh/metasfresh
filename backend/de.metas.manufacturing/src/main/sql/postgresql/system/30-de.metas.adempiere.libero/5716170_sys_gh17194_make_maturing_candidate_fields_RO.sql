-- Field: Reifedisposition -> Produktionsdisposition -> Reifung läuft
-- Column: PP_Order_Candidate.IsMaturing
-- Field: Reifedisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Reifung läuft
-- Column: PP_Order_Candidate.IsMaturing
-- 2024-01-31T07:34:03.236Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-01-31 09:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723255
;

-- Field: Reifedisposition -> Produktionsdisposition -> HU
-- Column: PP_Order_Candidate.Issue_HU_ID
-- Field: Reifedisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> HU
-- Column: PP_Order_Candidate.Issue_HU_ID
-- 2024-01-31T07:34:11.056Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-01-31 09:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723258
;

-- Field: Reifedisposition -> Produktionsdisposition -> Zuordnung Reifeprodukte
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- Field: Reifedisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Zuordnung Reifeprodukte
-- Column: PP_Order_Candidate.M_Maturing_Configuration_Line_ID
-- 2024-01-31T07:34:21.038Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-01-31 09:34:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723257
;

-- Field: Reifedisposition -> Produktionsdisposition -> Reifung Konfiguration
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- Field: Reifedisposition(541756,EE01) -> Produktionsdisposition(547345,EE01) -> Reifung Konfiguration
-- Column: PP_Order_Candidate.M_Maturing_Configuration_ID
-- 2024-01-31T07:34:27.320Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-01-31 09:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723256
;

-- 2024-01-31T07:36:04.441Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582932,0,TO_TIMESTAMP('2024-01-31 09:36:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reifedispositionzeile','Reifedispositionzeile',TO_TIMESTAMP('2024-01-31 09:36:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-31T07:36:04.444Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582932 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-01-31T07:36:33.152Z
UPDATE AD_Element_Trl SET Name='Maturing candidate line', PrintName='Maturing candidate line',Updated=TO_TIMESTAMP('2024-01-31 09:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582932 AND AD_Language='en_US'
;

-- 2024-01-31T07:36:33.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582932,'en_US') 
;

-- Tab: Reifedisposition -> Reifedispositionzeile
-- Table: PP_OrderLine_Candidate
-- Tab: Reifedisposition(541756,EE01) -> Reifedispositionzeile
-- Table: PP_OrderLine_Candidate
-- 2024-01-31T07:37:58.808Z
UPDATE AD_Tab SET AD_Element_ID=582932, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Reifedispositionzeile',Updated=TO_TIMESTAMP('2024-01-31 09:37:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547346
;

-- 2024-01-31T07:37:58.809Z
/* DDL */  select update_tab_translation_from_ad_element(582932) 
;

-- 2024-01-31T07:37:58.822Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547346)
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- Table: PP_Order_Candidate
-- Window: Produktionsdisposition(541316,EE01)
-- EntityType: EE01
-- 2024-01-31T07:45:10.949Z
UPDATE AD_Table_Process SET AD_Window_ID=541316,Updated=TO_TIMESTAMP('2024-01-31 09:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541015
;

-- Reference: RelType PP_Order -> PP_Order_Candidate
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2024-01-31T07:51:47.242Z
UPDATE AD_Ref_Table SET WhereClause='exists(     select 1 from pp_order_candidate cand     inner join pp_ordercandidate_pp_order alloc on cand.pp_order_candidate_id = alloc.pp_order_candidate_id     inner join pp_order o on alloc.pp_order_id = o.pp_order_id     where alloc.pp_order_id = @PP_Order_ID@     and pp_order_candidate.pp_order_candidate_id = cand.pp_order_candidate_id and cand.isMaturing=''N''      )',Updated=TO_TIMESTAMP('2024-01-31 09:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541493
;

-- 2024-01-31T07:52:11.510Z
UPDATE AD_RelationType SET Name='PP_Order -> PP_Order_Candidate (Manufacturing)',Updated=TO_TIMESTAMP('2024-01-31 09:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540328
;

-- Name: RelType PP_Order -> PP_Order_Candidate (Maturing)
-- 2024-01-31T07:52:41.875Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541851,TO_TIMESTAMP('2024-01-31 09:52:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType PP_Order -> PP_Order_Candidate (Maturing)',TO_TIMESTAMP('2024-01-31 09:52:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-01-31T07:52:41.877Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541851 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: RelType PP_Order -> PP_Order_Candidate (Maturing)
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2024-01-31T07:53:42.434Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,577875,0,541851,541913,541756,TO_TIMESTAMP('2024-01-31 09:53:42','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N',TO_TIMESTAMP('2024-01-31 09:53:42','YYYY-MM-DD HH24:MI:SS'),100,'exists(     select 1 from pp_order_candidate cand     inner join pp_ordercandidate_pp_order alloc on cand.pp_order_candidate_id = alloc.pp_order_candidate_id     inner join pp_order o on alloc.pp_order_id = o.pp_order_id     where alloc.pp_order_id = @PP_Order_ID@     and pp_order_candidate.pp_order_candidate_id = cand.pp_order_candidate_id and cand.isMaturing=''Y''      )')
;

-- Name: RelType PP_Order -> PP_Order_Candidate (Manufacturing)
-- 2024-01-31T07:54:01.135Z
UPDATE AD_Reference SET Name='RelType PP_Order -> PP_Order_Candidate (Manufacturing)',Updated=TO_TIMESTAMP('2024-01-31 09:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541493
;

-- Name: RelType PP_Order -> PP_Order_Candidate (Maturing)
-- 2024-01-31T07:54:08.569Z
UPDATE AD_Reference SET EntityType='EE01',Updated=TO_TIMESTAMP('2024-01-31 09:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541851
;

-- 2024-01-31T07:54:30.357Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540503,541851,540436,TO_TIMESTAMP('2024-01-31 09:54:30','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','PP_Order -> PP_Order_Candidate (Maturing)',TO_TIMESTAMP('2024-01-31 09:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

