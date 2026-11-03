-- Import lenient lookup: the BPartner GLN lookup label line may fall back to a GLN-only match when the label does not match.
-- The line is selected by format Value + column name, never by a hard-coded EXP_FormatLine_ID.
UPDATE EXP_FormatLine
SET IsImportLenient = 'Y',
    Updated         = TO_TIMESTAMP('2026-10-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE EXP_Format_ID = (SELECT EXP_Format_ID FROM EXP_Format WHERE Value = 'EDI_C_BPartner_Lookup_BPL_GLN_v')
  AND AD_Column_ID = (SELECT c.AD_Column_ID
                      FROM AD_Column c
                      WHERE c.ColumnName = 'Lookup_Label'
                        AND c.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'EDI_C_BPartner_Lookup_BPL_GLN_v'))
;
