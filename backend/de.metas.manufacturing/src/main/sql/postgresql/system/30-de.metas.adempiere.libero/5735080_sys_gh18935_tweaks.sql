-- Name: PP_Order_Candidate_Source
-- 2024-09-26T15:35:46.102Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541894,TO_TIMESTAMP('2024-09-26 17:35:45','YYYY-MM-DD HH24:MI:SS'),100,'This i the parent candidate from whe we want to zoom to the child-candidates','EE04','Y','N','PP_Order_Candidate_Source',TO_TIMESTAMP('2024-09-26 17:35:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-26T15:35:46.108Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541894 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order_Candidate_Source
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2024-09-26T15:37:03.741Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,577875,0,541894,541913,541316,TO_TIMESTAMP('2024-09-26 17:37:03','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N',TO_TIMESTAMP('2024-09-26 17:37:03','YYYY-MM-DD HH24:MI:SS'),100,'1=1')
;

-- Name: PP_Order_Candidate_Source
-- 2024-09-26T15:37:09.369Z
UPDATE AD_Reference SET Description='This is the parent candidate from whe we want to zoom to the child-candidates',Updated=TO_TIMESTAMP('2024-09-26 17:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541894
;

-- Name: PP_Order_Candidate_Source
-- 2024-09-26T15:37:45.842Z
UPDATE AD_Reference SET EntityType='EE01',Updated=TO_TIMESTAMP('2024-09-26 17:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541894
;

-- Name: PP_Order_Candidate Target for PP_Order_Candidate_Parent_ID
-- 2024-09-26T15:38:46.493Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541895,TO_TIMESTAMP('2024-09-26 17:38:45','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','PP_Order_Candidate Target for PP_Order_Candidate_Parent_ID',TO_TIMESTAMP('2024-09-26 17:38:45','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-26T15:38:46.495Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541895 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order_Candidate Target for PP_Order_Candidate_Parent_ID
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2024-09-26T15:40:28.426Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,577875,577875,0,541895,541913,541316,TO_TIMESTAMP('2024-09-26 17:40:28','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N',TO_TIMESTAMP('2024-09-26 17:40:28','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from PP_Order_Candidate child  join PP_Order_Candidate parent on child.PP_Order_Candidate_Parent_ID = parent.PP_Order_Candidate_ID  where parent.PP_Order_Candidate_ID = @PP_Order_Candidate_ID/0@ and child.PP_Order_Candidate_ID = PP_Order_Candidate.PP_Order_Candidate_ID)')
;

-- 2024-09-26T15:41:12.680Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541894,541895,540444,TO_TIMESTAMP('2024-09-26 17:41:12','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','PP_Order_Candidate_Parent -> PP_Order_Candidate',TO_TIMESTAMP('2024-09-26 17:41:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: AD_RelationType Role
-- Value: PP_OrderCandidate_Children
-- ValueName: Vorher zu produzierende Kandidaten
-- 2024-09-27T06:43:55.806Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,53331,543727,TO_TIMESTAMP('2024-09-27 08:43:55','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Vorher zu produzierende Kandidaten',TO_TIMESTAMP('2024-09-27 08:43:55','YYYY-MM-DD HH24:MI:SS'),100,'PP_OrderCandidate_Children','Vorher zu produzierende Kandidaten')
;

-- 2024-09-27T06:43:55.814Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543727 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: AD_RelationType Role -> PP_OrderCandidate_Children_Vorher zu produzierende Kandidaten
-- 2024-09-27T06:43:59.599Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-27 08:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543727
;

-- Reference Item: AD_RelationType Role -> PP_OrderCandidate_Children_Vorher zu produzierende Kandidaten
-- 2024-09-27T06:44:04.978Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-27 08:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543727
;

-- Reference Item: AD_RelationType Role -> PP_OrderCandidate_Children_Vorher zu produzierende Kandidaten
-- 2024-09-27T06:44:51.057Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Candidates to be produced beforehand',Updated=TO_TIMESTAMP('2024-09-27 08:44:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543727
;

-- 2024-09-27T06:45:11.795Z
UPDATE AD_RelationType SET Role_Target='PP_OrderCandidate_Children',Updated=TO_TIMESTAMP('2024-09-27 08:45:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540444
;


