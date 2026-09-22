-- Run mode: SWING_CLIENT

-- Bestellkontrolle — backfill the document type on existing reports
-- Backfill C_DocType_ID based on DocumentType for existing C_Order_MFGWarehouse_Report records
-- Mapping:
--   DocumentType = 'WH' (Warehouse) → C_DocType_ID = 541177 (Bestellkontrolle Produktion)
--   DocumentType = 'PL' (Plant) → C_DocType_ID = 541178 (Bestellkontrolle Büro)
-- ~140 rows on the target instance; no batching needed.

SELECT backup_table('c_order_mfgwarehouse_report', '_gh32265_backfill_doctype');

-- Backfill Warehouse reports (DocumentType = 'WH')
UPDATE C_Order_MFGWarehouse_Report
SET C_DocType_ID = 541177 /*Bestellkontrolle Produktion*/
WHERE C_DocType_ID IS NULL
  AND DocumentType = 'WH'
;

-- Backfill Plant reports (DocumentType = 'PL')
UPDATE C_Order_MFGWarehouse_Report
SET C_DocType_ID = 541178 /*Bestellkontrolle Büro*/
WHERE C_DocType_ID IS NULL
  AND DocumentType = 'PL'
;
