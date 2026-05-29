-- gh#28967: Rename Package Licensing reports for consistency
-- Pattern: "Verpackungslizenzierung — <what>"

-- ==========================================================================
-- 1) Detail report (585503): Verpackungslizenzierung — Bewegungsdetails
-- ==========================================================================

-- de_DE (base language)
UPDATE AD_Element_Trl
SET IsTranslated = 'N',
    Name         = 'Verpackungslizenzierung — Bewegungsdetails',
    PrintName    = 'Verpackungslizenzierung — Bewegungsdetails',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584042 AND AD_Language = 'de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584042, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584042, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Verpackungslizenzierung — Bewegungsdetails',
    PrintName    = 'Verpackungslizenzierung — Bewegungsdetails',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584042 AND AD_Language = 'de_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584042, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Package Licensing — Movement Details',
    PrintName    = 'Package Licensing — Movement Details',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584042 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584042, 'en_US');

-- ==========================================================================
-- 2) Summary report (585504): Verpackungslizenzierung — Mengenmeldung
-- ==========================================================================

-- de_DE (base language)
UPDATE AD_Element_Trl
SET IsTranslated = 'N',
    Name         = 'Verpackungslizenzierung — Mengenmeldung',
    PrintName    = 'Verpackungslizenzierung — Mengenmeldung',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584043 AND AD_Language = 'de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584043, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584043, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Verpackungslizenzierung — Mengenmeldung',
    PrintName    = 'Verpackungslizenzierung — Mengenmeldung',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584043 AND AD_Language = 'de_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584043, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Package Licensing — Quantity Declaration',
    PrintName    = 'Package Licensing — Quantity Declaration',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584043 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584043, 'en_US');

-- ==========================================================================
-- 3) Product report (585578): Verpackungslizenzierung — Produktübersicht
-- ==========================================================================

-- de_DE (base language)
UPDATE AD_Element_Trl
SET IsTranslated = 'N',
    Name         = 'Verpackungslizenzierung — Produktübersicht',
    PrintName    = 'Verpackungslizenzierung — Produktübersicht',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584562 AND AD_Language = 'de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584562, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Verpackungslizenzierung — Produktübersicht',
    PrintName    = 'Verpackungslizenzierung — Produktübersicht',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584562 AND AD_Language = 'de_CH';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Package Licensing — Product Overview',
    PrintName    = 'Package Licensing — Product Overview',
    Updated      = '2026-03-22 01:00',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584562 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584562, 'en_US');
