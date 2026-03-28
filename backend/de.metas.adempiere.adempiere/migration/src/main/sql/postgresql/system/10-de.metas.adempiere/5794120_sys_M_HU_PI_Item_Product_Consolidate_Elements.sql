-- 2026-03-12
-- AD_Elements for M_HU_PI_Item_Product consolidation process parameters

-- Element 1: IsNormalizeGTIN
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Element_ID, ColumnName, Name, PrintName, Description, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584660 /*From ID Server*/, 'IsNormalizeGTIN',
        'GTIN/EAN normalisieren', 'GTIN/EAN normalisieren',
        'Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN',
        'de.metas.handlingunits');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584660 /*From ID Server*/,
        'GTIN/EAN normalisieren', 'GTIN/EAN normalisieren',
        'Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN',
        'N');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584660 /*From ID Server*/,
        'GTIN/EAN normalisieren', 'GTIN/EAN normalisieren',
        'Kopiert EAN in GTIN wenn GTIN leer ist, löscht EAN wenn identisch mit GTIN',
        'N');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584660 /*From ID Server*/,
        'Normalize GTIN/EAN', 'Normalize GTIN/EAN',
        'Copies EAN to GTIN when GTIN is empty, clears EAN when identical to GTIN',
        'Y');

-- Element 2: IsConsolidate
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Element_ID, ColumnName, Name, PrintName, Description, EntityType)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584661 /*From ID Server*/, 'IsConsolidate',
        'Doppelte Einträge konsolidieren', 'Doppelte Einträge konsolidieren',
        'Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zusammen',
        'de.metas.handlingunits');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584661 /*From ID Server*/,
        'Doppelte Einträge konsolidieren', 'Doppelte Einträge konsolidieren',
        'Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zusammen',
        'N');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584661 /*From ID Server*/,
        'Doppelte Einträge konsolidieren', 'Doppelte Einträge konsolidieren',
        'Fasst doppelte Packvorschrift-Zuordnungen mit gleicher GTIN zusammen',
        'N');

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                            AD_Element_ID, Name, PrintName, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        584661 /*From ID Server*/,
        'Consolidate duplicates', 'Consolidate duplicates',
        'Merges duplicate packing instruction allocations with the same GTIN',
        'Y');
