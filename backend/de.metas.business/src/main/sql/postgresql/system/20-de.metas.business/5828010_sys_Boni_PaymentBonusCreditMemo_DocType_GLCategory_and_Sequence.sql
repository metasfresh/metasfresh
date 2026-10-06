-- Payment bonus credit memo doc type: book it in the GL category and number it from the sequence of the plain AR credit memo ("Gutschrift")
-- 2026-10-06
SELECT backup_table('C_DocType', '_pre_Boni_PaymentBonusCreditMemo_GLCategory');

UPDATE C_DocType dt
SET GL_Category_ID    = COALESCE(arc.GL_Category_ID, dt.GL_Category_ID),
    DocNoSequence_ID  = COALESCE(arc.DocNoSequence_ID, dt.DocNoSequence_ID),
    Updated           = TO_TIMESTAMP('2026-10-06 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy         = 100
FROM C_DocType arc
WHERE dt.C_DocType_ID = 541179
  AND arc.C_DocType_ID = 1000004
;
