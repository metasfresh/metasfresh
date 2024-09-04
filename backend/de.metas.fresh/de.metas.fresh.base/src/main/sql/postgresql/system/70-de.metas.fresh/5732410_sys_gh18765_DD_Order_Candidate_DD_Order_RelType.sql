-- Name: DD_Order_Candidate
-- 2024-09-04T08:49:41.679Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541885,TO_TIMESTAMP('2024-09-04 09:49:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','DD_Order_Candidate',TO_TIMESTAMP('2024-09-04 09:49:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-04T08:49:41.983Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541885 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: DD_Order_Candidate
-- Table: DD_Order_Candidate
-- Key: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-09-04T08:51:54.889Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,588723,0,541885,542424,TO_TIMESTAMP('2024-09-04 09:51:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-09-04 09:51:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: RelType DD_Order_Candidate->DD_Order
-- 2024-09-04T08:53:27.957Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541886,TO_TIMESTAMP('2024-09-04 09:53:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType DD_Order_Candidate->DD_Order',TO_TIMESTAMP('2024-09-04 09:53:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-09-04T08:53:28.060Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541886 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: RelType DD_Order_Candidate->DD_Order
-- Table: DD_Order
-- Key: DD_Order.DD_Order_ID
-- 2024-09-04T09:00:19.917Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,53883,0,541886,53037,541266,TO_TIMESTAMP('2024-09-04 10:00:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-09-04 10:00:19','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS           (SELECT 1            from DD_Order dd_o                     INNER JOIN DD_OrderLine dd_ol on dd_o.DD_Order_ID = dd_ol.DD_Order_ID                     INNER JOIN DD_Order_Candidate_DDOrder dd_ic_od on dd_ol.DD_OrderLine_ID = dd_ic_od.DD_OrderLine_ID                     INNER JOIN DD_Order_Candidate dd_ic on dd_ic_od.DD_Order_Candidate_ID = dd_ic.DD_Order_Candidate_ID            where dd_ic.DD_Order_Candidate_ID = @DD_Order_Candidate_ID@              AND DD_Order.DD_Order_ID = dd_o.DD_Order_ID)')
;

-- 2024-09-04T09:02:42.462Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541885,541886,540443,TO_TIMESTAMP('2024-09-04 10:02:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','DD_Order_Candidate -> DD_Order',TO_TIMESTAMP('2024-09-04 10:02:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: DD_Order_Candidate
-- Table: DD_Order_Candidate
-- Key: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-09-04T09:06:23.956Z
UPDATE AD_Ref_Table SET AD_Window_ID=541807,Updated=TO_TIMESTAMP('2024-09-04 10:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541885
;

-- Reference: DD_Order_Candidate
-- Table: DD_Order_Candidate
-- Key: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-09-04T09:10:10.890Z
UPDATE AD_Ref_Table SET AD_Display=588723,Updated=TO_TIMESTAMP('2024-09-04 10:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541885
;

-- Reference: RelType DD_Order_Candidate->DD_Order
-- Table: DD_Order
-- Key: DD_Order.DD_Order_ID
-- 2024-09-04T09:11:27.708Z
UPDATE AD_Ref_Table SET AD_Display=53864, OrderByClause='DocumentNo',Updated=TO_TIMESTAMP('2024-09-04 10:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541886
;

-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-09-04T09:13:37.421Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-09-04 10:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588723
;

