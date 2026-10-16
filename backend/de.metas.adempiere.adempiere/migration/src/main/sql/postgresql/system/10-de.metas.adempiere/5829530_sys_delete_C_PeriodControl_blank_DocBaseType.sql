-- 5760750_sys_gh26910_Create_C_Period_for_2026_to_2030.sql created one period control per DISTINCT C_DocType.DocBaseType "IS NOT NULL",
-- which includes the template doc type C_DocType_ID=0 ("** Neu **") with DocBaseType ''.
-- With manual period control (C_AcctSchema.AutoPeriodControl='N') such a row makes every document of that period fail on completion
-- with "No DocBaseType found for code: " (PeriodDAO.retrievePeriodControlsByDocBaseType -> DocBaseType.ofCode('')).

SELECT backup_table('C_PeriodControl', '_blank_DocBaseType')
;

DELETE
FROM C_PeriodControl
WHERE TRIM(COALESCE(DocBaseType, '')) = ''
;
