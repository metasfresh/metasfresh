-- Name: DD_Order Source
-- 2024-09-04T08:19:23.825Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541883,TO_TIMESTAMP('2024-09-04 09:19:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','DD_Order Source',TO_TIMESTAMP('2024-09-04 09:19:23','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-04T08:19:24.130Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541883 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: DD_Order Source
-- Table: DD_Order
-- Key: DD_Order.DD_Order_ID
-- 2024-09-04T08:20:34.251Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,53883,0,541883,53037,53012,TO_TIMESTAMP('2024-09-04 09:20:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-09-04 09:20:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: RelType DD_Order->DD_Order_Candidate
-- 2024-09-04T08:24:04.460Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541884,TO_TIMESTAMP('2024-09-04 09:24:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType DD_Order->DD_Order_Candidate',TO_TIMESTAMP('2024-09-04 09:24:03','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-04T08:24:04.609Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541884 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: RelType DD_Order->DD_Order_Candidate
-- Table: DD_Order_Candidate
-- Key: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-09-04T08:35:32.095Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,588723,0,541884,542424,541807,TO_TIMESTAMP('2024-09-04 09:35:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-09-04 09:35:31','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS           (SELECT 1            from DD_Order_Candidate dd_ic                     INNER JOIN DD_Order_Candidate_DDOrder dd_ic_od on dd_ic_od.DD_Order_Candidate_ID = dd_ic.DD_Order_Candidate_ID                     INNER JOIN DD_OrderLine dd_ol on dd_ol.DD_OrderLine_ID = dd_ic_od.DD_OrderLine_ID                     INNER JOIN DD_Order dd_o on dd_o.DD_Order_ID = dd_ol.DD_Order_ID             where dd_o.DD_Order_ID = @DD_Order_ID@              AND DD_Order_Candidate.DD_Order_Candidate_ID = dd_ic.DD_Order_Candidate_ID)')
;

-- 2024-09-04T08:37:39.917Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541883,541884,540442,TO_TIMESTAMP('2024-09-04 09:37:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','DD_Order->DD_Order_Candidate',TO_TIMESTAMP('2024-09-04 09:37:39','YYYY-MM-DD HH24:MI:SS'),100)
;

