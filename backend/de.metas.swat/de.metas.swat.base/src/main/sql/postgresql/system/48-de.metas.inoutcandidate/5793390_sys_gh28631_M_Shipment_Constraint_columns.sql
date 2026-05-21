-- gh#28631: Add IsManual and DeliveryStopReason columns to M_Shipment_Constraint
-- IsManual distinguishes user-created (via BPartner checkbox) from system-generated (dunning) entries.
-- DeliveryStopReason stores the block reason (synced from BPartner for manual entries).

-- ==========================================================================
-- 1. AD_Column: M_Shipment_Constraint.IsManual (reuses existing AD_Element 1474)
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description,
                       FieldLength, IsMandatory, IsUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsSelectionColumn,
                       IsAlwaysUpdateable, PersonalDataCategory)
VALUES (592210 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        0, 'de.metas.inoutcandidate', 540845, 1474, 20,
        'IsManual', 'Manuell',
        'Kennzeichnet Sperren, die durch das Setzen von ''Liefer-/Auftragssperre'' am Geschäftspartner entstanden sind (im Unterschied zu vom System per Mahnung erzeugten Einträgen). Wird automatisch gepflegt.',
        1, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'N', 'N',
        'N', 'NP');

-- Physical column. Atomic via db_alter_table to avoid PG15 multi-statement trigger error.
/* DDL */ SELECT public.db_alter_table('M_Shipment_Constraint', 'ALTER TABLE public.M_Shipment_Constraint ADD COLUMN IF NOT EXISTS IsManual CHAR(1) DEFAULT ''N'' CHECK (IsManual IN (''Y'',''N'')) NOT NULL')
;

-- ==========================================================================
-- 2. AD_Column: M_Shipment_Constraint.DeliveryStopReason (reuses AD_Element 584648)
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description,
                       FieldLength, IsMandatory, IsUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsSelectionColumn,
                       IsAlwaysUpdateable, PersonalDataCategory)
VALUES (592211 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        0, 'de.metas.inoutcandidate', 540845, 584648, 10,
        'DeliveryStopReason', 'Lieferstopp Grund',
        'Begründung der Liefer-/Auftragssperre. Bei manuellen Sperren wird der am Geschäftspartner eingegebene Text gespiegelt; bei vom System (z.B. Mahnung) erzeugten Sperren wird der Hinweis programmatisch befüllt.',
        200, 'N', 'Y', NULL,
        'N', 'N', 'N', 'N', 'N',
        'N', 'NP');

-- Physical column (nullable)
/* DDL */ SELECT public.db_alter_table('M_Shipment_Constraint', 'ALTER TABLE public.M_Shipment_Constraint ADD COLUMN IF NOT EXISTS DeliveryStopReason VARCHAR(200)')
;

-- ==========================================================================
-- 3. Performance index on Bill_BPartner_ID
-- ==========================================================================
CREATE INDEX IF NOT EXISTS M_Shipment_Constraint_Bill_BPartner_ID
    ON M_Shipment_Constraint (Bill_BPartner_ID)
    WHERE IsActive = 'Y';
