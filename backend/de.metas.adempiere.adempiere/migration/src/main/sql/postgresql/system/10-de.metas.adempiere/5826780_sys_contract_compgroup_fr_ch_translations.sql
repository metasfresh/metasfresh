-- Contract compensation groups: PR-review follow-up (fix round 1) -- fr_CH translations.
--
-- AD_Element 585496 (the C2 field-specific "applies to product category" label) shipped with an
-- untranslated German copy for fr_CH, while element 453 it replaces on this field carries a real
-- French translation ("Catégorie de produit"). Give it a proper French translation so fr_CH users
-- don't regress from a real translation to a raw German string.
--
-- Also checked the other elements introduced on this branch (585491 IsAdditive, 585492/585493 from
-- 5826630): 585491's direct table-sibling (584629 IsInheritPackingInstruction, same
-- C_CompensationGroup_Schema table) has NO real fr_CH either -- consistent with existing practice
-- in this immediate area, not a regression, so left alone. 585492/585493 DO have a direct sibling
-- with a real fr_CH translation -- AD_Element 543889 "Schéma du groupe de compensation"
-- (Kompensationsgruppe Schema), referenced right next to them in the same 5826630 script, same
-- contract-settings feature area -- so both get a real French translation here too, for
-- consistency within that one script's own translation set (en_US already treated them together).
--
-- IDs allocated from idserver.metas.de on 2026-09-28: none (this script only updates existing
-- AD_Element_Trl rows created by 5826610/5826630/5826750).
-- ============================================================================
-- 1) AD_Element 585496 (Applies to product category) -- required fix
-- ============================================================================
UPDATE AD_Element_Trl
SET Name = 'S''applique à la catégorie de produit',
    PrintName = 'S''applique à la catégorie de produit',
    Description = 'Seules les lignes de commande dont le produit appartient à cette catégorie ou à l''une de ses sous-catégories comptent pour cette ligne de remise. Vide = toutes les lignes.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585496 AND AD_Language = 'fr_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585496, 'fr_CH')
;
-- ============================================================================
-- 2) AD_Element 585492 (Compensation group contract settings) -- consistency fix
-- ============================================================================
UPDATE AD_Element_Trl
SET Name = 'Paramètres du contrat de groupe de compensation',
    PrintName = 'Paramètres du contrat de groupe de compensation',
    Description = 'Référence aux paramètres d''un contrat de groupe de compensation (schéma et types de justificatif).',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585492 AND AD_Language = 'fr_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585492, 'fr_CH')
;
-- ============================================================================
-- 3) AD_Element 585493 (Compensation group contract settings document type) -- consistency fix
-- ============================================================================
UPDATE AD_Element_Trl
SET Name = 'Type de justificatif des paramètres du contrat de groupe de compensation',
    PrintName = 'Type de justificatif des paramètres du contrat de groupe de compensation',
    Description = 'Type de justificatif de vente ou d''achat auquel s''applique le contrat de groupe de compensation.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585493 AND AD_Language = 'fr_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585493, 'fr_CH')
;
