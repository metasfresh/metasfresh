-- 2026-06-05T00:00:00Z
-- Restricts the location lookup to active, non-ephemeral ShipTo/HandoverLocation locations
-- belonging to the selected BPartner (AD_Val_Rule_ID=167 "C_BPartner_Loc Ship To").
-- AD_Column_ID=592679, AD_Table_ID=542610 (C_BPartner_EDI_Setting)

UPDATE AD_Column
SET AD_Val_Rule_ID=167,
    Updated=TO_TIMESTAMP('2026-06-05 00:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=592679
;
