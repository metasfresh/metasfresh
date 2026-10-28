-- Compensation groups: packing-material category translations
-- Adds translations for AD_Element 585519 (M_Product_Category_PackingMaterial_ID)
-- into de_DE, de_CH, en_US, and fr_CH.

-- ============================================================================
-- AD_Element 585519 (Packing material category)
-- ============================================================================

-- de_DE translation
UPDATE AD_Element_Trl
SET Name = 'Packmittel-Kategorie',
    PrintName = 'Packmittel-Kategorie',
    Description = 'Leer = alle Zeilen. Wenn gesetzt, zählen nur Auftragszeilen, deren Packvorschrift ein Packmittel mit einem Produkt in dieser Kategorie (inkl. Unterkategorien) hat.',
    Help = 'Es zählt die Packvorschrift der Auftragszeile, auch für jede Teilrechnung. Bei einem Auftrag im Entwurf wirkt eine geänderte Packvorschrift erst bei der nächsten Änderung des Zeilenbetrags.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-06 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585519 AND AD_Language = 'de_DE'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585519, 'de_DE')
;

-- de_CH translation (identical to de_DE)
UPDATE AD_Element_Trl
SET Name = 'Packmittel-Kategorie',
    PrintName = 'Packmittel-Kategorie',
    Description = 'Leer = alle Zeilen. Wenn gesetzt, zählen nur Auftragszeilen, deren Packvorschrift ein Packmittel mit einem Produkt in dieser Kategorie (inkl. Unterkategorien) hat.',
    Help = 'Es zählt die Packvorschrift der Auftragszeile, auch für jede Teilrechnung. Bei einem Auftrag im Entwurf wirkt eine geänderte Packvorschrift erst bei der nächsten Änderung des Zeilenbetrags.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-06 09:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585519 AND AD_Language = 'de_CH'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585519, 'de_CH')
;

-- en_US translation
UPDATE AD_Element_Trl
SET Name = 'Packing material category',
    PrintName = 'Packing material category',
    Description = 'Empty = all lines. If set, only order lines whose packing instruction has a packing material with a product in this category (incl. sub-categories) count.',
    Help = 'The order line''s packing instruction decides, also for every partial invoice. On a draft order, a changed packing instruction takes effect with the next change of the line amount.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-06 09:30:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585519 AND AD_Language = 'en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585519, 'en_US')
;

-- fr_CH translation
UPDATE AD_Element_Trl
SET Name = 'Catégorie de matériel d''emballage',
    PrintName = 'Catégorie de matériel d''emballage',
    Description = 'Vide = toutes les lignes. Si renseigné, seules comptent les lignes de commande dont l''instruction d''emballage comporte un matériel d''emballage avec un produit de cette catégorie (sous-catégories incluses).',
    Help = 'L''instruction d''emballage de la ligne de commande fait foi, également pour chaque facture partielle. Sur une commande en brouillon, une instruction d''emballage modifiée prend effet lors de la prochaine modification du montant de la ligne.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-06 09:30:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585519 AND AD_Language = 'fr_CH'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585519, 'fr_CH')
;
