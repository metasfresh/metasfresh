-- gh#29099: Add explanatory Help text to IsIncludeAllProducts parameter
-- The parameter has different effects in the detail vs. summary reports.

-- Base element (de_DE)
UPDATE AD_Element
SET Help = 'Wenn aktiv, werden auch Produkte einbezogen, für die keine Verpackungslizenz-Stammdaten (Produktgruppe, Material) für das gewählte Land gepflegt sind. '
        || 'Im Bewegungsdetail-Bericht erscheinen diese Produkte mit leeren Verpackungsspalten — so können fehlende Stammdaten identifiziert werden. '
        || 'Im Zusammenfassungs-Bericht haben diese Produkte keinen Einfluss auf die Summen, da sie kein Material zugeordnet haben.',
    Updated = '2026-04-07 15:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 584691;

-- de_DE / de_CH
UPDATE AD_Element_Trl
SET Help = 'Wenn aktiv, werden auch Produkte einbezogen, für die keine Verpackungslizenz-Stammdaten (Produktgruppe, Material) für das gewählte Land gepflegt sind. '
        || 'Im Bewegungsdetail-Bericht erscheinen diese Produkte mit leeren Verpackungsspalten — so können fehlende Stammdaten identifiziert werden. '
        || 'Im Zusammenfassungs-Bericht haben diese Produkte keinen Einfluss auf die Summen, da sie kein Material zugeordnet haben.',
    Updated = '2026-04-07 15:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 584691
  AND AD_Language IN ('de_DE', 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Help = 'When enabled, products without packaging licensing master data (product group, material) for the selected country are included. '
        || 'In the Movement Details report, these products appear with blank packaging columns — useful for identifying missing master data. '
        || 'In the Summary report, these products have no effect on the totals because they have no material assigned.',
    IsTranslated = 'Y',
    Updated = '2026-04-07 15:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 584691
  AND AD_Language = 'en_US';

-- Propagate
SELECT update_ad_element_on_ad_element_trl_update(584691, 'de_DE');
SELECT update_ad_element_on_ad_element_trl_update(584691, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'en_US');
