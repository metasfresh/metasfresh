-- Invoice Processing Service Company assignment: at most ONE active assignment per customer (C_BPartner_ID)
-- among the rows that have NO C_DocType_ID. Together with the pre-existing unique index
-- C_InvoiceProcessingServiceCompany_BPAssignment_BP_DocType_uq (C_DocType_ID, C_BPartner_ID; gh11478, migration 5597550),
-- this makes the payment-service fee single-valued per (customer, doc type), NULL doc type counted as one bucket.
--
-- Prior art:
--  * gh6537 (migration 5558390) created the unique index InvoiceProcessingServiceCompany_BPartnerAssignment_UQ
--    on (C_BPartner_ID) WHERE IsActive='Y' ("A BPartner shall be assigned to only one Invoice Processing Service Company").
--  * gh6904 (migration 5562430) DROPPED that index (and its AD_Index_Table row) when it added C_DocType_ID,
--    because one customer may legitimately have one assignment per doc type.
--  * gh11478 (migration 5597550) then added the plain unique index on (C_DocType_ID, C_BPartner_ID). That covers the
--    rows with a doc type, but a unique index treats NULLs as distinct, so any number of active rows WITHOUT a doc type
--    were still possible for the same customer.
-- This script closes exactly that gap: the guard is restored per (customer, doc type) - not per customer - so the
-- per-doc-type rows enabled by gh6904 are NOT blocked again. Company is intentionally not part of the key.
--
-- When this migration fails, it is because a customer already has more than one active assignment without doc type.
-- Detect with:
--   SELECT C_BPartner_ID, count(*) FROM InvoiceProcessingServiceCompany_BPartnerAssignment
--   WHERE IsActive='Y' AND C_DocType_ID IS NULL GROUP BY C_BPartner_ID HAVING count(*) > 1;
-- and deactivate the surplus rows manually.

-- 2026-10-05T09:00:00
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause)
VALUES (0,540876 /*From ID Server*/,0,541494,TO_TIMESTAMP('2026-10-05 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Unique index on C_BPartner_ID for active rows without C_DocType_ID','D','Ein Geschäftspartner darf ohne Belegart nur einem Rechnungsserviceunternehmen zugewiesen sein.','Y','Y','InvoiceProcessingServiceCompany_BPAssignment_NullDocType_UQ','N',TO_TIMESTAMP('2026-10-05 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND C_DocType_ID IS NULL')
;

-- 2026-10-05T09:00:01
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540876 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2026-10-05T09:00:02
UPDATE AD_Index_Table_Trl SET IsTranslated='Y', ErrorMsg='A business partner can be assigned to only one Invoice Processing Service Company when no document type is set.', Updated=TO_TIMESTAMP('2026-10-05 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Index_Table_ID=540876
;

-- 2026-10-05T09:00:03
UPDATE AD_Index_Table_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-05 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540876
;

-- 2026-10-05T09:00:04
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,570677,541551 /*From ID Server*/,540876,0,TO_TIMESTAMP('2026-10-05 09:00:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2026-10-05 09:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-10-05T09:00:05
CREATE UNIQUE INDEX InvoiceProcessingServiceCompany_BPAssignment_NullDocType_UQ ON InvoiceProcessingServiceCompany_BPartnerAssignment (C_BPartner_ID) WHERE IsActive='Y' AND C_DocType_ID IS NULL
;
