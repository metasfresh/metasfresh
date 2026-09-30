-- Run mode: SWING_CLIENT

-- Bestellkontrolle — make C_Order_MFGWarehouse_Report.C_DocType_ID mandatory now that every row is
-- backfilled (5825550) and the sole writer, OrderCheckupBuilder.getDocTypeId(), throws unconditionally
-- when it cannot resolve a document type for a new row.

SELECT backup_table('c_order_mfgwarehouse_report', '_gh32265_mandatory');

-- Defensive re-run of the original backfill (5825550) -- a no-op on any instance where every row is
-- already covered; a safety net in case a row was created between that backfill and this rollout.
UPDATE C_Order_MFGWarehouse_Report
SET C_DocType_ID = 541177 /*Bestellkontrolle Produktion*/,
    Updated = TO_TIMESTAMP('2026-09-22 18:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE C_DocType_ID IS NULL
  AND DocumentType = 'WH'
;

UPDATE C_Order_MFGWarehouse_Report
SET C_DocType_ID = 541178 /*Bestellkontrolle Büro*/,
    Updated = TO_TIMESTAMP('2026-09-22 18:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE C_DocType_ID IS NULL
  AND DocumentType = 'PL'
;

-- The two UPDATEs above write to the FK column C_DocType_ID, which queues deferred FK-check trigger
-- events; flush them before the ALTER below or db_alter_table/t_alter_column aborts with "cannot ALTER
-- TABLE ... because it has pending trigger events".
SET CONSTRAINTS ALL IMMEDIATE;

-- AD metadata: mark the column mandatory
UPDATE AD_Column
SET IsMandatory = 'Y',
    Updated = TO_TIMESTAMP('2026-09-22 18:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 593637 /*C_Order_MFGWarehouse_Report.C_DocType_ID*/
;

-- Physical DDL: NOT NULL, no default (mandatory FK, matching the C_DocType_ID recipe in
-- metasfresh-convert-table-to-document)
INSERT INTO t_alter_column values('c_order_mfgwarehouse_report','C_DocType_ID','NUMERIC(10)','NOT NULL',null);
