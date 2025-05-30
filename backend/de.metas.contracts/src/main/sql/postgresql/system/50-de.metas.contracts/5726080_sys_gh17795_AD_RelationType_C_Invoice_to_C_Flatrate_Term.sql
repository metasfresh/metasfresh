-- 2024-06-12T07:21:13.180Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541872,TO_TIMESTAMP('2024-06-12 09:21:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Invoice->C_FlatrateTerm (via C_InvoiceLine)',TO_TIMESTAMP('2024-06-12 09:21:12','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-06-12T07:21:13.189Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541872 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Table: C_Flatrate_Term
-- 2024-06-12T07:22:35.603Z
UPDATE AD_Table SET Name='C_Flatrate_Term',Updated=TO_TIMESTAMP('2024-06-12 09:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540320
;

-- 2024-06-12T07:27:42.921Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,545802,0,541872,540320,540359,TO_TIMESTAMP('2024-06-12 09:27:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','DocumentNo','N',TO_TIMESTAMP('2024-06-12 09:27:42','YYYY-MM-DD HH24:MI:SS'),100,'exists  (  select 1 from C_Flatrate_Term ft  inner join C_InvoiceLine il on il.C_Flatrate_Term_ID = ft.C_Flatrate_Term_ID  where il.C_Invoice_ID = @C_Invoice_ID/0@ )')
;

-- 2024-06-12T07:36:31.628Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsTableRecordIdTarget,Name,Role_Source,Updated,UpdatedBy) VALUES (0,0,336,541872,540439,TO_TIMESTAMP('2024-06-12 09:36:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','C_Invoice_C_Flatrate_Term','Y','N','C_Invoice -> C_Flatrate_Term','Invoice',TO_TIMESTAMP('2024-06-12 09:36:31','YYYY-MM-DD HH24:MI:SS'),100)
;

