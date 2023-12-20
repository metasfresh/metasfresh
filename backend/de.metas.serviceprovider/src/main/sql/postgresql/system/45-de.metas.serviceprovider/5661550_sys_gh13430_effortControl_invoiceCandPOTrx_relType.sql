-- Name: C_Invoice_Candidate_POTrx_Project_and_CostCenter
-- 2022-10-24T08:05:40.435Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541679,TO_TIMESTAMP('2022-10-24 11:05:40.257','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Invoice_Candidate_POTrx_Project_and_CostCenter',TO_TIMESTAMP('2022-10-24 11:05:40.257','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2022-10-24T08:05:40.440Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541679 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate_POTrx_Project_and_CostCenter
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-10-24T08:06:21.522Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,0,541679,540270,540983,TO_TIMESTAMP('2022-10-24 11:06:21.423','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2022-10-24 11:06:21.423','YYYY-MM-DD HH24:MI:SS.US'),100,'IsSoTrx=''N'' AND C_Activity_ID IS NOT NULL AND C_Project_ID IS NOT NULL')
;

-- Reference: C_Invoice_Candidate_POTrx_Project_and_CostCenter
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-10-24T08:06:47.720Z
UPDATE AD_Ref_Table SET AD_Display=544906,Updated=TO_TIMESTAMP('2022-10-24 11:06:47.72','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541679
;

-- 2022-10-24T08:07:31.408Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541679,541675,540378,TO_TIMESTAMP('2022-10-24 11:07:31.293','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Invoice_Candidate(POTrx) -> S_EffortControl',TO_TIMESTAMP('2022-10-24 11:07:31.293','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-24T08:08:03.480Z
UPDATE AD_RelationType SET Name='S_EffortControl -> C_Invoice_Candidate(SOTrx)',Updated=TO_TIMESTAMP('2022-10-24 11:08:03.48','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540377
;

-- Name: S_EffortControl_matching_C_Invoice_Candidate_POTrx
-- 2022-10-24T08:09:21.242Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541680,TO_TIMESTAMP('2022-10-24 11:09:21.12','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','S_EffortControl_matching_C_Invoice_Candidate_POTrx',TO_TIMESTAMP('2022-10-24 11:09:21.12','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2022-10-24T08:09:21.244Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541680 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: S_EffortControl_matching_C_Invoice_Candidate_POTrx
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-10-24T08:10:04.522Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,0,541680,540270,540983,TO_TIMESTAMP('2022-10-24 11:10:04.518','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2022-10-24 11:10:04.518','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Invoice_Candidate_ID in (select cand.C_Invoice_Candidate_ID                                   from C_Invoice_Candidate cand                                            inner join S_EffortControl eff on eff.C_Activity_ID = cand.C_Activity_ID and                                                                              eff.C_Project_ID = cand.C_Project_ID and                                                                              eff.AD_Org_ID = cand.AD_Org_ID                                   where cand.IsSOTrx = ''N''                                     and cand.C_Activity_ID is not null                                     and cand.C_Project_ID is not null                                     and eff.S_EffortControl_ID =@ S_EffortControl_ID / -1@)')
;

-- 2022-10-24T08:10:44.835Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541676,541680,540379,TO_TIMESTAMP('2022-10-24 11:10:44.687','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','S_EffortControl -> C_Invoice_Candidate(POTrx)',TO_TIMESTAMP('2022-10-24 11:10:44.687','YYYY-MM-DD HH24:MI:SS.US'),100)
;

