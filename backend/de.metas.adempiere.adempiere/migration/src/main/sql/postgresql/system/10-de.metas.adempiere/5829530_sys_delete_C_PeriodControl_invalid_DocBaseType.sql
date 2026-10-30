-- 5760750_sys_gh26910_Create_C_Period_for_2026_to_2030.sql created one period control per distinct C_DocType.DocBaseType,
-- including values that are not in the DocBaseType reference list: '' of the template doc type C_DocType_ID=0 and outdated ones (e.g. 'MMP').
-- With manual period control (C_AcctSchema.AutoPeriodControl='N') a single such period control makes completing any document
-- of that period fail with "No DocBaseType found for code: ...".
-- Same cleanup as 5741410_sys_gh19584_Delete_Outdated_Period_Controls.sql, which ran before the 2026-2030 periods existed.

SELECT backup_table('C_PeriodControl', '_invalid_DocBaseType')
;

DELETE
FROM C_PeriodControl
WHERE DocBaseType NOT IN (SELECT Value FROM AD_Ref_List WHERE AD_Reference_ID = 183)
;
