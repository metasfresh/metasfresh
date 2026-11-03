-- gh#28967: Add AD_Element for TotalSalesQty so Excel headers are translated

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy,
                        IsActive, EntityType, ColumnName, Name, PrintName)
VALUES (584692 /*From ID Server*/, 0, 0, '2026-03-22 01:30', 100, '2026-03-22 01:30', 100,
        'Y', 'D', 'TotalSalesQty', 'Verkaufsmenge Gesamt', 'Verkaufsmenge Gesamt');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584692, 'Verkaufsmenge Gesamt', 'Verkaufsmenge Gesamt', NULL,
       'N', 0, 0, '2026-03-22 01:30', 100, '2026-03-22 01:30', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584692);

-- de_DE (base language)
UPDATE AD_Element_Trl
SET IsTranslated = 'N',
    Name         = 'Verkaufsmenge Gesamt',
    PrintName    = 'Verkaufsmenge Gesamt',
    Updated      = '2026-03-22 01:30',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584692 AND AD_Language = 'de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584692, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584692, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Verkaufsmenge Gesamt',
    PrintName    = 'Verkaufsmenge Gesamt',
    Updated      = '2026-03-22 01:30',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584692 AND AD_Language = 'de_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584692, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Total Sales Qty',
    PrintName    = 'Total Sales Qty',
    Updated      = '2026-03-22 01:30',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584692 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584692, 'en_US');
