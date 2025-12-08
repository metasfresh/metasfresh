-- Run mode: SWING_CLIENT

-- Reference: _Payment Rule
-- Value: E
-- ValueName: Rückerstattung
-- 2025-02-03T10:56:14.982Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,195,543787,TO_TIMESTAMP('2025-02-03 11:56:14.251','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rückerstattung',TO_TIMESTAMP('2025-02-03 11:56:14.251','YYYY-MM-DD HH24:MI:SS.US'),100,'E','Rückerstattung')
;

-- 2025-02-03T10:56:15.038Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543787 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: _Payment Rule -> E_Rückerstattung
-- 2025-02-03T10:56:33.336Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Reimbursement',Updated=TO_TIMESTAMP('2025-02-03 11:56:33.336','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543787
;

-- Reference Item: _Payment Rule -> E_Rückerstattung
-- 2025-02-03T10:56:48.388Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Remboursement',Updated=TO_TIMESTAMP('2025-02-03 11:56:48.388','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543787
;

-- Reference: _Payment Rule
-- Value: F
-- ValueName: Verrechnung
-- 2025-02-03T10:57:13.323Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,195,543788,TO_TIMESTAMP('2025-02-03 11:57:12.677','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Verrechnung',TO_TIMESTAMP('2025-02-03 11:57:12.677','YYYY-MM-DD HH24:MI:SS.US'),100,'F','Verrechnung')
;

-- 2025-02-03T10:57:13.374Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543788 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: _Payment Rule -> F_Verrechnung
-- 2025-02-03T10:57:26.271Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Settlement',Updated=TO_TIMESTAMP('2025-02-03 11:57:26.271','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543788
;

-- Reference Item: _Payment Rule -> F_Verrechnung
-- 2025-02-03T10:57:41.632Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Compensation',Updated=TO_TIMESTAMP('2025-02-03 11:57:41.632','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543788
;

-- Column: C_Invoice.PaymentRule
-- 2025-02-04T09:01:38.208Z
UPDATE AD_Column SET AD_Reference_ID=17, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-02-04 10:01:38.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=4020
;

