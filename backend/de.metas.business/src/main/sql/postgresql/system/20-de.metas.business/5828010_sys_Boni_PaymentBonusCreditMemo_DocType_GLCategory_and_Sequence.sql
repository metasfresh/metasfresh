-- Payment bonus credit memo doc type: book it in the GL category and number it from the sequence of the plain AR credit memo ("Gutschrift") of the same client
-- (the plain AR credit memo: DocBaseType ARC without sub type; if there is none, the doc type keeps its values)
-- 2026-10-06
SELECT backup_table('C_DocType', '_pre_Boni_PaymentBonusCreditMemo_GLCategory');

UPDATE C_DocType dt
SET GL_Category_ID   = COALESCE((SELECT arc.GL_Category_ID
                                 FROM C_DocType arc
                                 WHERE arc.AD_Client_ID = dt.AD_Client_ID AND arc.DocBaseType = 'ARC' AND arc.DocSubType IS NULL AND arc.IsActive = 'Y'
                                 ORDER BY arc.IsDefault DESC, arc.C_DocType_ID
                                 LIMIT 1), dt.GL_Category_ID),
    DocNoSequence_ID = COALESCE((SELECT arc.DocNoSequence_ID
                                 FROM C_DocType arc
                                 WHERE arc.AD_Client_ID = dt.AD_Client_ID AND arc.DocBaseType = 'ARC' AND arc.DocSubType IS NULL AND arc.IsActive = 'Y'
                                 ORDER BY arc.IsDefault DESC, arc.C_DocType_ID
                                 LIMIT 1), dt.DocNoSequence_ID),
    Updated          = TO_TIMESTAMP('2026-10-06 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy        = 100
WHERE dt.C_DocType_ID = 541179
;
