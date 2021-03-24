-- 2021-01-20T07:07:39.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540561,0,318,TO_TIMESTAMP('2021-01-20 09:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Used to prevent multiple reversals of the same invoice','U','Y','Y','C_Invoice_Reversal_ID','N',TO_TIMESTAMP('2021-01-20 09:07:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-20T07:07:39.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540561 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-01-20T07:07:53.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,55305,541051,540561,0,TO_TIMESTAMP('2021-01-20 09:07:53','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',10,TO_TIMESTAMP('2021-01-20 09:07:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-20T08:11:58.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Invoice_Reversal_ID ON C_Invoice (Reversal_ID)
;

-- When this migration script is failing, it's because there are multiple reversals for the same invoice ID.
-- In this case, manually run the below script.
/*
BEGIN;

CREATE TEMP TABLE Original_C_Invoice_IDs_With_Duplicate_Reversals
AS
SELECT c_invoice_id FROM c_invoice WHERE c_invoice_id in (SELECT reversal_id as c_invoice_id FROM (SELECT sum(1) AS total, reversal_id FROM c_invoice WHERE reversal_id IS NOT NULL GROUP BY reversal_id) a WHERE a.total > 1);

CREATE TEMP TABLE Duplicate_C_Invoice_IDs
AS
SELECT c_invoice_id FROM c_invoice WHERE c_invoice_id NOT IN (SELECT reversal_id FROM c_invoice WHERE c_invoice_id in (select c_invoice_id from Original_C_Invoice_IDs_With_Duplicate_Reversals)) AND reversal_id IN (SELECT c_invoice_id FROM Original_C_Invoice_IDs_With_Duplicate_Reversals);

CREATE TEMP TABLE Duplicate_C_AllocationHrd_Ids
AS
SELECT c_allocationhdr_id FROM c_allocationline WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);


CREATE TABLE backup.BKP_Duplicate_C_Invoice_20210120
AS
SELECT * FROM c_invoice WHERE c_invoice_id in (SELECT reversal_id as c_invoice_id FROM (SELECT sum(1) AS total, reversal_id FROM c_invoice WHERE reversal_id IS NOT NULL GROUP BY reversal_id) a WHERE a.total > 1);


CREATE TABLE backup.BKP_C_AllocationLine_20210120
AS
SELECT * FROM C_AllocationLine WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);

CREATE TABLE backup.BKP_M_MatchInv_20210120
AS
SELECT * FROM M_MatchInv WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);

CREATE TABLE backup.BKP_C_InvoiceLine_20210120
AS
SELECT * FROM C_InvoiceLine WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);

CREATE TABLE backup.BKP_C_InvoiceLine_Alloc_20210120
AS
SELECT * FROM c_invoice_line_alloc WHERE c_invoiceline_id  IN (SELECT c_invoiceline_id FROM c_invoiceline WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs));

CREATE TABLE backup.BKP_C_AllocationHdr_20210120
AS
SELECT * FROM C_AllocationHdr WHERE c_allocationhdr_id  IN (SELECT c_allocationhdr_id FROM c_allocationline WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs));

--fact_acct data can be repopulated, no need to backup
DELETE FROM fact_acct WHERE  ad_table_id=get_table_id('C_Invoice') AND record_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);

DELETE FROM c_invoice_line_alloc where c_invoiceline_id IN (SELECT c_invoiceline_id FROM c_invoiceline WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs));
DELETE FROM m_matchinv WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);
DELETE FROM c_allocationline WHERE c_allocationhdr_id  IN (SELECT c_allocationhdr_id from Duplicate_C_AllocationHrd_Ids);
DELETE FROM C_AllocationHdr WHERE c_allocationhdr_id  IN (SELECT c_allocationhdr_id from Duplicate_C_AllocationHrd_Ids);
DELETE FROM c_invoiceline WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);
DELETE FROM c_invoice WHERE c_invoice_id in (select c_invoice_id from Duplicate_C_Invoice_IDs);
COMMIT;*/