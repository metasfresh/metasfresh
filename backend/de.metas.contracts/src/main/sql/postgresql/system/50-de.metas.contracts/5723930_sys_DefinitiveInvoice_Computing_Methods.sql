-- Run mode: SWING_CLIENT

-- Reference: Computing Methods
-- Value: DefinitiveInvoiceRawProduct
-- ValueName: DefinitiveInvoiceRawProduct
-- 2024-05-20T12:48:51.608Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543682,TO_TIMESTAMP('2024-05-20 15:48:51.396','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Definitive Invoice for Raw Product',TO_TIMESTAMP('2024-05-20 15:48:51.396','YYYY-MM-DD HH24:MI:SS.US'),100,'DefinitiveInvoiceRawProduct','DefinitiveInvoiceRawProduct')
;

-- 2024-05-20T12:48:51.620Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543682 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoiceProcessedProduct
-- ValueName: DefinitiveInvoiceProcessedProduct
-- 2024-05-20T12:49:29.989Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543683,TO_TIMESTAMP('2024-05-20 15:49:29.839','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Definitive Invoice for Processed Product',TO_TIMESTAMP('2024-05-20 15:49:29.839','YYYY-MM-DD HH24:MI:SS.US'),100,'DefinitiveInvoiceProcessedProduct','DefinitiveInvoiceProcessedProduct')
;

-- 2024-05-20T12:49:29.990Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543683 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;



-- Reference: Computing Methods
-- Value: DefinitiveInvoice
-- ValueName: DefinitiveInvoice
-- 2024-05-20T13:12:27.578Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-05-20 16:12:27.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543665
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoice
-- ValueName: DefinitiveInvoice
-- 2024-05-20T13:12:41.564Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543665
;

-- 2024-05-20T13:12:41.569Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543665
;




-- Reference: Computing Methods
-- Value: DefinitiveInvoiceRawProduct
-- ValueName: DefinitiveInvoiceRawProduct
-- 2024-05-20T13:38:06.331Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-05-20 16:38:06.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543682
;

-- Reference: Computing Methods
-- Value: DefinitiveInvoiceProcessedProduct
-- ValueName: DefinitiveInvoiceProcessedProduct
-- 2024-05-20T13:38:09.642Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-05-20 16:38:09.642','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543683
;

