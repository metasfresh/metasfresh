-- Process "Erzeuge Vertrag" (C_Flatrate_Term_Create_For_BPartners), parameter EndDate (AD_Process_Para 543331):
-- a dedicated element, so the parameter's description and help can say when the entered end date is applied.
-- It is applied only when the conditions' transition has duration 0; for a duration greater than 0 the end date
-- computed from the transition stands. The generic EndDate element (294) stays untouched; its description/help do not
-- fit this dialog (and are untranslated English in de_DE).
--
-- IDs allocated from idserver.metas.de on 2026-09-30:
--   AD_Element 585498

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (585498 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-30 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-30 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Enddatum', 'Enddatum',
        'Letzter Gültigkeitstag des Vertrags (einschließlich).',
        'Wird nur übernommen, wenn der Vertrags-Übergang der Vertragsbedingungen eine Laufzeit von 0 hat; der Vertrag behält dann das eingegebene Enddatum. Bei einer Laufzeit größer 0 wird das Enddatum aus dem Vertrags-Übergang berechnet und diese Eingabe ignoriert.')
ON CONFLICT (AD_Element_ID) DO NOTHING
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585498
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name         = 'Contract End',
    PrintName    = 'Contract End',
    Description  = 'Last effective day of the contract (inclusive).',
    Help         = 'Only applied if the transition of the contract conditions has a duration of 0; the contract then keeps the entered end date. For a duration greater than 0 the end date is computed from the transition and this entry is ignored.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 10:10:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585498 AND AD_Language = 'en_US'
;

-- de_CH mirrors de_DE (copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 10:10:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585498 AND AD_Language = 'de_CH'
;

UPDATE AD_Process_Para
SET AD_Element_ID         = 585498,
    IsCentrallyMaintained = 'Y',
    Updated               = TO_TIMESTAMP('2026-09-30 10:10:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy             = 100
WHERE AD_Process_Para_ID = 543331
;

-- take the new element's texts into AD_Process_Para / AD_Process_Para_Trl (only parameters of element 585498)
SELECT update_Process_Para_Translation_From_AD_Element(585498)
;
