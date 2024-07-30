
--
-- migrate existing records
--
CREATE TEMPORARY TABLE EDI_Desadv_Pack_Line AS
SELECT (ROW_NUMBER() OVER (
    PARTITION BY EDI_Desadv_ID
    ORDER BY EDI_Desaadv_Pack_ID)) * 10 AS Line,
       EDI_Desadv_Pack_ID,
       EDI_Desadv_ID
FROM EDI_Desadv_Pack
;

UPDATE EDI_Desadv_Pack p
SET UpdatedBy=99, Updated=TO_TIMESTAMP('2024-07-29 10:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    Line=d.Line
FROM EDI_Desadv_Pack_Line d
WHERE p.EDI_Desadv_Pack_ID = d.EDI_Desadv_Pack_ID
  AND p.Line IS NULL
;
