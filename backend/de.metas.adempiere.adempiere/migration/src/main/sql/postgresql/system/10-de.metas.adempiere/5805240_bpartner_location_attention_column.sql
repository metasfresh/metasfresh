-- nShift: dedicated attention field on BPartner Location
-- IDs allocated from idserver.metas.de on 2026-05-28:
--   AD_Element 584922 (Attention label for C_BPartner_Location)
--   AD_Column  592663 (C_BPartner_Location.Attention)

-- =============================================================================
-- 1. AD_Element for Attention field
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584922 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100, TO_TIMESTAMP('2026-05-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Attention',
        'z. Hd.',
        'z. Hd.',
        'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)',
        NULL,
        'D');

-- Skeleton Trl rows for Attention element
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584922
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation for Attention
UPDATE AD_Element_Trl
SET Name         = 'Attention',
    PrintName    = 'Attention',
    Description  = 'Attention, door code or additional mandatory information for the shipping label (max. 30 characters)',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-28 00:00:01', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584922 AND AD_Language = 'en_US';

-- German translation de_DE for Attention
UPDATE AD_Element_Trl
SET Name         = 'z. Hd.',
    PrintName    = 'z. Hd.',
    Description  = 'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)',
    Help         = NULL,
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-05-28 00:00:02', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584922 AND AD_Language = 'de_DE';

-- German translation de_CH for Attention
UPDATE AD_Element_Trl
SET Name         = 'z. Hd.',
    PrintName    = 'z. Hd.',
    Description  = 'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)',
    Help         = NULL,
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-05-28 00:00:03', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584922 AND AD_Language = 'de_CH';

-- =============================================================================
-- 2. AD_Column for C_BPartner_Location.Attention
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent,
                       IsSelectionColumn, FilterOperator,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592663 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-28 00:00:04', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100, TO_TIMESTAMP('2026-05-28 00:00:04', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        0, 293 /*C_BPartner_Location*/, 584922 /*From ID Server*/, 10 /*String*/, NULL,
        'Attention', 'z. Hd.', 'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)', NULL,
        30, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N',
        'N', NULL,
        'N', 'N', 'N', 'Y',
        'N', 'P');

-- Skeleton Trl rows for Attention column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592663
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate Attention element translations to the new AD_Column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584922);

-- =============================================================================
-- 3. DB column
-- =============================================================================
ALTER TABLE C_BPartner_Location ADD COLUMN IF NOT EXISTS Attention VARCHAR(30);
