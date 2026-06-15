
-- Name: C_Invoice_PO_Target_For_C_Flatrate_Term
-- 2024-12-03T16:21:52.053Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541908,TO_TIMESTAMP('2024-12-03 17:21:51.918','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','C_Invoice_PO_Target_For_C_Flatrate_Term',TO_TIMESTAMP('2024-12-03 17:21:51.918','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-12-03T16:21:52.055Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541908 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_PO_Target_For_C_Flatrate_Term
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2024-12-03T16:25:58.129Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,541908,318,183,TO_TIMESTAMP('2024-12-03 17:25:58.123','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2024-12-03 17:25:58.123','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Invoice.C_Invoice_ID IN (SELECT DISTINCT i.C_Invoice_ID from C_Invoice i INNER JOIN C_InvoiceLine il on (i.C_Invoice_ID = il.C_Invoice_ID AND il.C_Flatrate_Term_ID = @C_Flatrate_Term_ID/-1@) where i.IsSOTrx = ''N'')')
;

-- Name: C_Flatrate_Term -> C_Invoice PO
-- Source Reference: C_Flatrate_Term_Source
-- Target Reference: C_Invoice_Target_For_C_Flatrate_Term
-- 2024-12-03T16:37:31.997Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541787,541908,540445,TO_TIMESTAMP('2024-12-03 17:37:31.871','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','C_Flatrate_Term -> C_Invoice',TO_TIMESTAMP('2024-12-03 17:37:31.871','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: C_Flatrate_Term -> C_Invoice PO
-- Source Reference: C_Flatrate_Term_Source
-- Target Reference: C_Invoice_Target_For_C_Flatrate_Term
-- 2024-12-04T06:56:11.494Z
UPDATE AD_RelationType SET Name='C_Flatrate_Term -> C_Invoice PO',Updated=TO_TIMESTAMP('2024-12-04 07:56:11.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540445
;

-- Name: C_Invoice_SO_Target_For_C_Flatrate_Term
-- 2024-12-04T07:06:54.436Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541909,TO_TIMESTAMP('2024-12-04 08:06:54.247','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','C_Invoice_SO_Target_For_C_Flatrate_Term',TO_TIMESTAMP('2024-12-04 08:06:54.247','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-12-04T07:06:54.438Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541909 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_SO_Target_For_C_Flatrate_Term
-- Table: C_Invoice
-- Key: C_Invoice.C_Invoice_ID
-- 2024-12-04T07:08:04.360Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,541909,318,167,TO_TIMESTAMP('2024-12-04 08:08:04.352','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2024-12-04 08:08:04.352','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Invoice.C_Invoice_ID IN (SELECT DISTINCT i.C_Invoice_ID from C_Invoice i INNER JOIN C_InvoiceLine il on (i.C_Invoice_ID = il.C_Invoice_ID AND il.C_Flatrate_Term_ID = @C_Flatrate_Term_ID/-1@) where i.IsSOTrx = ''Y'')')
;

-- Name: C_Flatrate_Term -> C_Invoice SO
-- Source Reference: C_Flatrate_Term_Source
-- Target Reference: C_Invoice_SO_Target_For_C_Flatrate_Term
-- 2024-12-04T07:09:28.955Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541787,541909,540446,TO_TIMESTAMP('2024-12-04 08:09:28.817','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','C_Flatrate_Term -> C_Invoice SO',TO_TIMESTAMP('2024-12-04 08:09:28.817','YYYY-MM-DD HH24:MI:SS.US'),100)
;

