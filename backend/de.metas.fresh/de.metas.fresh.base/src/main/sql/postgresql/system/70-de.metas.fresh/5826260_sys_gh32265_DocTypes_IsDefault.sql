-- Run mode: SWING_CLIENT

-- Bestellkontrolle — mark the Produktion (BKP) and Büro (BKB) document types as the default of their base type,
-- so a lookup by document base type resolves them as the default, like the default document type of any other base type.

SELECT backup_table('C_DocType', '_gh32265_IsDefault');

UPDATE C_DocType SET IsDefault='Y', Updated=TO_TIMESTAMP('2026-09-24 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE C_DocType_ID=541177
;

UPDATE C_DocType SET IsDefault='Y', Updated=TO_TIMESTAMP('2026-09-24 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE C_DocType_ID=541178
;
