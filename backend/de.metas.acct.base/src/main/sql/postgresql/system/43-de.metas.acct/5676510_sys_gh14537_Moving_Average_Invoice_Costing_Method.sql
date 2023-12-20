-- Reference: C_AcctSchema Costing Method
-- Value: MAI
-- ValueName: MovingAverageInvoice
-- 2023-02-09T18:18:46.763Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,122,543405,TO_TIMESTAMP('2023-02-09 20:18:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Moving Average Invoice',TO_TIMESTAMP('2023-02-09 20:18:46','YYYY-MM-DD HH24:MI:SS'),100,'M','MovingAverageInvoice')
;

-- 2023-02-09T18:18:46.765Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543405 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_AcctSchema Costing Method
-- Value: M
-- ValueName: MovingAverageInvoice
-- 2023-02-14T09:38:59.901Z
UPDATE AD_Ref_List SET Value='M',Updated=TO_TIMESTAMP('2023-02-14 11:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543405
;

