-- Process "Erzeuge Vertrag" (C_Flatrate_Term_Create_For_BPartners), parameter EndDate (AD_Process_Para 543331, element 585498):
-- the en_US label reads "End Date", parallel to the "Start Date" parameter next to it.

UPDATE AD_Element_Trl
SET Name      = 'End Date',
    PrintName = 'End Date',
    Updated   = TO_TIMESTAMP('2026-09-30 14:20:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 585498 AND AD_Language = 'en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585498, 'en_US')
;
