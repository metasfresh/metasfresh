-- Fixes the unique index of 5827880 on databases that already ran its first variant, which was keyed on C_BPartner_ID alone.
-- That key refused a customer who is assigned without doc type in two versions of a service company (records with the same
-- ServiceCompany_BPartner_ID and consecutive ValidFrom dates), which is a valid setup: the fee lookup reads the assignment of
-- the version valid at the date. The index is now keyed per company version: (InvoiceProcessingServiceCompany_ID, C_BPartner_ID).
--
-- Re-run safe; works on every state 5827880 can have left:
--  * first variant applied (index on C_BPartner_ID): the index is re-created with the new key;
--  * 5827880 applied with its CREATE INDEX skipped (deploy unblocked by hand): the index is created;
--  * corrected 5827880 applied: the same index is re-created, AD rows unchanged.
--
-- When this migration fails, a customer has more than one active assignment without doc type in ONE company version.
-- Detect with:
--   SELECT InvoiceProcessingServiceCompany_ID, C_BPartner_ID, count(*) FROM InvoiceProcessingServiceCompany_BPartnerAssignment
--   WHERE IsActive='Y' AND C_DocType_ID IS NULL GROUP BY InvoiceProcessingServiceCompany_ID, C_BPartner_ID HAVING count(*) > 1;
-- and deactivate the surplus rows manually.

-- 2026-10-19T21:00:01
UPDATE AD_Index_Table SET Description='Unique index on InvoiceProcessingServiceCompany_ID, C_BPartner_ID for active rows without C_DocType_ID', ErrorMsg='Ein Geschäftspartner darf je Rechnungsserviceunternehmen ohne Belegart nur einmal zugewiesen sein.', Updated=TO_TIMESTAMP('2026-10-19 21:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540876
;

-- 2026-10-19T21:00:02
UPDATE AD_Index_Table_Trl SET ErrorMsg='Ein Geschäftspartner darf je Rechnungsserviceunternehmen ohne Belegart nur einmal zugewiesen sein.', Updated=TO_TIMESTAMP('2026-10-19 21:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540876 AND AD_Language<>'en_US'
;

-- 2026-10-19T21:00:03
UPDATE AD_Index_Table_Trl SET ErrorMsg='A business partner can be assigned only once per Invoice Processing Service Company when no document type is set.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-19 21:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Table_ID=540876 AND AD_Language='en_US'
;

-- 2026-10-19T21:00:04
UPDATE AD_Index_Column SET SeqNo=20, Updated=TO_TIMESTAMP('2026-10-19 21:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Column_ID=541551
;

-- 2026-10-19T21:00:05
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy)
SELECT 0,570676,541554 /*From ID Server*/,540876,0,TO_TIMESTAMP('2026-10-19 21:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2026-10-19 21:00:05','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_Index_Column WHERE AD_Index_Column_ID=541554)
;

-- 2026-10-19T21:00:06
DROP INDEX IF EXISTS InvoiceProcessingServiceCompany_BPAssignment_NullDocType_UQ
;

-- 2026-10-19T21:00:07
CREATE UNIQUE INDEX InvoiceProcessingServiceCompany_BPAssignment_NullDocType_UQ ON InvoiceProcessingServiceCompany_BPartnerAssignment (InvoiceProcessingServiceCompany_ID, C_BPartner_ID) WHERE IsActive='Y' AND C_DocType_ID IS NULL
;
