-- Contract compensation groups: de_DE translations of the texts introduced for this feature are final German texts,
-- so they are marked IsTranslated='Y' (like en_US and de_CH), and the propagation functions carry the flag into the
-- column, field, tab, window, menu and process parameter translations that use these elements.
--
-- Elements: 585491 (IsAdditive), 585492, 585493, 585494 (settings window), 585495 (doc types tab),
--           585496 (schema line "applies to product category"), 585498 ("Erzeuge Vertrag" EndDate parameter)

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585491 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585491, 'de_DE')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585492 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585492, 'de_DE')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585493 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585493, 'de_DE')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585494 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585494, 'de_DE')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585495 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585495, 'de_DE')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:06', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585496 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585496, 'de_DE')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:07', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585498 AND AD_Language = 'de_DE' AND IsTranslated = 'N'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585498, 'de_DE')
;

-- ref list value (not element-based)
UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 544372 AND AD_Language = 'de_DE'
;

-- tables C_CompensationGroup_ContractSettings and C_CompensationGroup_ContractSettings_DocType (not element-based)
UPDATE AD_Table_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 14:00:21', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Table_ID IN (542650, 542651) AND AD_Language = 'de_DE'
;
