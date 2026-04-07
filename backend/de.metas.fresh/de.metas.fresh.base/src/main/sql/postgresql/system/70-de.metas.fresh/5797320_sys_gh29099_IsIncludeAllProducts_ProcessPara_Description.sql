-- gh#29099: Set Description on IsIncludeAllProducts process parameters.
-- AD_Element propagation only covers Name, not Description/Help on AD_Process_Para.
-- Set it directly so users see a meaningful tooltip in the process dialog.

-- Detail report (585503) - para 543158
UPDATE AD_Process_Para
SET Description = 'Wenn aktiv, werden auch Produkte ohne Verpackungslizenz-Stammdaten (Produktgruppe, Material) einbezogen — so können fehlende Stammdaten identifiziert werden.',
    Updated = '2026-04-07 16:15',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543158;

UPDATE AD_Process_Para_Trl
SET Description = 'Wenn aktiv, werden auch Produkte ohne Verpackungslizenz-Stammdaten (Produktgruppe, Material) einbezogen — so können fehlende Stammdaten identifiziert werden.',
    Updated = '2026-04-07 16:15',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543158
  AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl
SET Description = 'When enabled, products without packaging licensing master data (product group, material) are included — useful for identifying missing master data.',
    IsTranslated = 'Y',
    Updated = '2026-04-07 16:15',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543158
  AND AD_Language = 'en_US';

-- Product report (585578) - para 543160
UPDATE AD_Process_Para
SET Description = 'Wenn aktiv, werden auch Produkte ohne Verpackungslizenz-Stammdaten (Produktgruppe, Material) einbezogen — so können fehlende Stammdaten identifiziert werden.',
    Updated = '2026-04-07 16:15',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543160;

UPDATE AD_Process_Para_Trl
SET Description = 'Wenn aktiv, werden auch Produkte ohne Verpackungslizenz-Stammdaten (Produktgruppe, Material) einbezogen — so können fehlende Stammdaten identifiziert werden.',
    Updated = '2026-04-07 16:15',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543160
  AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl
SET Description = 'When enabled, products without packaging licensing master data (product group, material) are included — useful for identifying missing master data.',
    IsTranslated = 'Y',
    Updated = '2026-04-07 16:15',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543160
  AND AD_Language = 'en_US';
