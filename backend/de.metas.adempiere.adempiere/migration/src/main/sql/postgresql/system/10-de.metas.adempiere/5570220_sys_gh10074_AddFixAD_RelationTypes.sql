-- 2020-10-12T04:58:05.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2020-10-12 08:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540119
;

-- 2020-10-12T05:03:57.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='RelType C_Invoice->C_Invoice_Candidate_Sales',Updated=TO_TIMESTAMP('2020-10-12 09:03:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 2020-10-12T05:04:11.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541211,TO_TIMESTAMP('2020-10-12 09:04:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','RelType C_Invoice->C_Invoice_Candidate_Purchase',TO_TIMESTAMP('2020-10-12 09:04:10','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-10-12T05:04:11.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541211 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-10-12T05:05:10.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,0,541211,540270,TO_TIMESTAMP('2020-10-12 09:05:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2020-10-12 09:05:10','YYYY-MM-DD HH24:MI:SS'),100,'exists   (   select 1 from C_Invoice_Candidate ic   inner join C_Invoice_Line_Alloc ila on ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID   inner join C_InvoiceLine il on ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID   inner join C_Invoice i  on il.C_Invoice_ID = i.C_Invoice_ID    where i.C_Invoice_ID = @C_Invoice_ID@    and C_Invoice_Candidate.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID  )')
;

-- 2020-10-12T05:05:57.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540092,Updated=TO_TIMESTAMP('2020-10-12 09:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540559
;

-- 2020-10-12T05:06:11.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540983,Updated=TO_TIMESTAMP('2020-10-12 09:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541211
;

-- 2020-10-12T05:07:08.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540680,541211,540266,TO_TIMESTAMP('2020-10-12 09:07:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','C_Invoice (PO) -> PurchaseInvoiceCandidate',TO_TIMESTAMP('2020-10-12 09:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-12T05:08:05.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540678,Updated=TO_TIMESTAMP('2020-10-12 09:08:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540119
;

-- 2020-10-12T05:09:26.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Invoice (SO) -> SalesInvoiceCandidate',Updated=TO_TIMESTAMP('2020-10-12 09:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540119
;

