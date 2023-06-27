-- Name: C_Invoice_Candidate_SOTrx_Project_and_Activity
-- 2022-10-20T07:17:24.342Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541674,TO_TIMESTAMP('2022-10-20 10:17:24.189','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Invoice_Candidate_SOTrx_Project_and_Activity',TO_TIMESTAMP('2022-10-20 10:17:24.189','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2022-10-20T07:17:24.350Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541674 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate_SOTrx_Project_and_Activity
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-10-20T07:18:16.962Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,544906,0,541674,540270,540092,TO_TIMESTAMP('2022-10-20 10:18:16.957','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2022-10-20 10:18:16.957','YYYY-MM-DD HH24:MI:SS.US'),100,'IsSoTrx=''Y'' AND C_Activity_ID IS NOT NULL AND C_Project_ID IS NOT NULL')
;

-- Name: C_Invoice_Candidate_matching_S_EffortControl
-- 2022-10-20T07:18:46.519Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541675,TO_TIMESTAMP('2022-10-20 10:18:46.355','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Invoice_Candidate_matching_S_EffortControl',TO_TIMESTAMP('2022-10-20 10:18:46.355','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2022-10-20T07:18:46.520Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541675 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate_matching_S_EffortControl
-- Table: S_EffortControl
-- Key: S_EffortControl.S_EffortControl_ID
-- 2022-10-20T07:19:20.358Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,584340,0,541675,542215,541615,TO_TIMESTAMP('2022-10-20 10:19:20.356','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2022-10-20 10:19:20.356','YYYY-MM-DD HH24:MI:SS.US'),100,'S_EffortControl_ID in (select eff.S_EffortControl_ID                               from S_EffortControl eff                                        inner join C_Invoice_Candidate cand                                                   on cand.C_Activity_ID = eff.C_Activity_ID                                                       and cand.C_Project_ID = eff.C_Project_ID                                                       and cand.AD_Org_ID = eff.AD_Org_ID                               where cand.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID / -1@                                 and S_EffortControl.S_EffortControl_ID = eff.S_EffortControl_ID)')
;

-- 2022-10-20T07:20:24.592Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541674,541675,540376,TO_TIMESTAMP('2022-10-20 10:20:24.428','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Invoice_Candidate(SOTrx) -> S_EffortControl',TO_TIMESTAMP('2022-10-20 10:20:24.428','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: S_EffortControl
-- 2022-10-20T07:21:37.662Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541676,TO_TIMESTAMP('2022-10-20 10:21:37.537','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','S_EffortControl',TO_TIMESTAMP('2022-10-20 10:21:37.537','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2022-10-20T07:21:37.664Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541676 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: S_EffortControl
-- Table: S_EffortControl
-- Key: S_EffortControl.S_EffortControl_ID
-- 2022-10-20T07:21:50.312Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584340,0,541676,542215,TO_TIMESTAMP('2022-10-20 10:21:50.307','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2022-10-20 10:21:50.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: S_EffortControl_matching_C_Invoice_Candidate_SOTrx
-- 2022-10-20T07:22:13.983Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541677,TO_TIMESTAMP('2022-10-20 10:22:13.846','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','S_EffortControl_matching_C_Invoice_Candidate_SOTrx',TO_TIMESTAMP('2022-10-20 10:22:13.846','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2022-10-20T07:22:13.990Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541677 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: S_EffortControl_matching_C_Invoice_Candidate_SOTrx
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-10-20T07:23:00.780Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,544906,0,541677,540270,540092,TO_TIMESTAMP('2022-10-20 10:23:00.777','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2022-10-20 10:23:00.777','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Invoice_Candidate_ID in (select cand.C_Invoice_Candidate_ID                                   from C_Invoice_Candidate cand                                            inner join S_EffortControl eff                                                       on eff.C_Activity_ID = cand.C_Activity_ID                                                           and eff.C_Project_ID = cand.C_Project_ID                                                           and eff.AD_Org_ID = cand.AD_Org_ID                                   where cand.IsSOTrx = ''Y''                                     and cand.C_Activity_ID is not null                                     and cand.C_Project_ID is not null                                     and eff.S_EffortControl_ID =@ S_EffortControl_ID / -1@)')
;

-- 2022-10-20T07:45:50.111Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541676,541677,540377,TO_TIMESTAMP('2022-10-20 10:45:49.795','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','S_EffortControl -> C_Invoice_Candidate',TO_TIMESTAMP('2022-10-20 10:45:49.795','YYYY-MM-DD HH24:MI:SS.US'),100)
;

