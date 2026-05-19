-- gh#28631: Add IsDeliveryStop and DeliveryStopReason columns to C_BPartner
-- These allow Finance to manually set a delivery/order stop on a business partner.

-- ==========================================================================
-- 1. AD_Element for DeliveryStopReason (IsDeliveryStop element 543441 already exists)
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (584648 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        'DeliveryStopReason', 'D', 'Lieferstopp Grund', 'Lieferstopp Grund', 'Grund für die Liefer-/Auftragssperre');

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            Name, PrintName, Description, IsTranslated)
SELECT 584648, AD_Language, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
       'Lieferstopp Grund', 'Lieferstopp Grund', 'Grund für die Liefer-/Auftragssperre', 'N'
FROM AD_Language WHERE IsSystemLanguage = 'Y' AND AD_Language NOT IN (SELECT AD_Language FROM AD_Element_Trl WHERE AD_Element_ID = 584648);

UPDATE AD_Element_Trl
SET Name = 'Delivery Stop Reason', PrintName = 'Delivery Stop Reason', Description = 'Reason for the delivery/order stop', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 584648 AND AD_Language = 'en_US';

-- Fix base language for IsDeliveryStop element (543441) - currently English, should be German
UPDATE AD_Element
SET Name = 'Lieferstopp', PrintName = 'Lieferstopp', Description = 'Liefer-/Auftragssperre',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 543441;

UPDATE AD_Element_Trl
SET Name = 'Lieferstopp', PrintName = 'Lieferstopp', Description = 'Liefer-/Auftragssperre', IsTranslated = 'N',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 543441 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name = 'Delivery Stop', PrintName = 'Delivery Stop', Description = 'Delivery / order stop', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 543441 AND AD_Language = 'en_US';

SELECT update_ad_element_on_ad_element_trl_update(543441, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543441, NULL);

-- ==========================================================================
-- 2. AD_Column: C_BPartner.IsDeliveryStop
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description,
                       FieldLength, IsMandatory, IsUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsSelectionColumn,
                       IsAlwaysUpdateable, PersonalDataCategory)
VALUES (592208 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        0, 'de.metas.inoutcandidate', 291, 543441, 20,
        'IsDeliveryStop', 'Lieferstopp', 'Liefer-/Auftragssperre',
        1, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'N', 'N',
        'N', 'NP');

-- Physical column. Atomic via db_alter_table to avoid PG15 multi-statement trigger error.
/* DDL */ SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE public.C_BPartner ADD COLUMN IF NOT EXISTS IsDeliveryStop CHAR(1) DEFAULT ''N'' CHECK (IsDeliveryStop IN (''Y'',''N'')) NOT NULL')
;

-- ==========================================================================
-- 3. AD_Column: C_BPartner.DeliveryStopReason
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description,
                       FieldLength, IsMandatory, IsUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsSelectionColumn,
                       IsAlwaysUpdateable, PersonalDataCategory)
VALUES (592209 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        0, 'de.metas.inoutcandidate', 291, 584648, 10,
        'DeliveryStopReason', 'Lieferstopp Grund', 'Grund für die Liefer-/Auftragssperre',
        200, 'N', 'Y', NULL,
        'N', 'N', 'N', 'N', 'N',
        'N', 'NP');

-- Physical column (nullable; reason is mandatory only via AD_Field MandatoryLogic when IsDeliveryStop=Y)
/* DDL */ SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE public.C_BPartner ADD COLUMN IF NOT EXISTS DeliveryStopReason VARCHAR(200)')
;
