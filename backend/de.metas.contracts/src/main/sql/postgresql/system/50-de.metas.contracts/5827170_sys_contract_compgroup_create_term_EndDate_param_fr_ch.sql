-- Process "Erzeuge Vertrag", parameter EndDate: fr_CH translation of its dedicated element 585498 (5827160),
-- so fr_CH users keep a French label ("Date de fin", as on the generic EndDate element before) instead of a German copy.

UPDATE AD_Element_Trl
SET Name         = 'Date de fin',
    PrintName    = 'Date de fin',
    Description  = 'Dernier jour de validité du contrat (inclus).',
    Help         = 'N''est repris que si la transition des conditions contractuelles a une durée de 0 ; le contrat conserve alors la date de fin saisie. Pour une durée supérieure à 0, la date de fin est calculée à partir de la transition et cette saisie est ignorée.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 10:20:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585498 AND AD_Language = 'fr_CH'
;

SELECT update_Process_Para_Translation_From_AD_Element(585498, 'fr_CH')
;
