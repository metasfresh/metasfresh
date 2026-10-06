-- Contract compensation groups: packing-material category on the compensation schema line.
-- Adds C_CompensationGroup_SchemaLine.M_Product_Category_PackingMaterial_ID (Table reference to
-- M_Product_Category, optional). When set, only order lines whose packing instruction has a
-- packing material with a product in this category (or a sub-category) count towards the line's base.
-- New element, column, window field (tab 541042, directly after "Gilt für Produktkategorie") and grid column.

-- ============================================================================
-- 1) New AD_Element
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (585519 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_Product_Category_PackingMaterial_ID', 'D', 'Packmittel-Kategorie', 'Packmittel-Kategorie',
        'Leer = alle Zeilen. Wenn gesetzt, zählen nur Auftragszeilen, deren Packvorschrift ein Packmittel mit einem Produkt in dieser Kategorie (inkl. Unterkategorien) hat.',
        'Es zählt die Packvorschrift der Auftragszeile, auch für jede Teilrechnung. Bei einem Auftrag im Entwurf wirkt eine geänderte Packvorschrift erst bei der nächsten Änderung des Zeilenbetrags.');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585519
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- de_DE and de_CH carry the base text as is
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-06 09:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585519
  AND AD_Language IN ('de_DE', 'de_CH');

-- ============================================================================
-- 2) C_CompensationGroup_SchemaLine.M_Product_Category_PackingMaterial_ID (AD_Table_ID=540941)
--    Table reference (AD_Reference_Value_ID 540153 = M_Product_Category), optional
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593712 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'D', 'M_Product_Category_PackingMaterial_ID', 540941, 585519, 18, 540153,
        10, 'Packmittel-Kategorie',
        'Leer = alle Zeilen. Wenn gesetzt, zählen nur Auftragszeilen, deren Packvorschrift ein Packmittel mit einem Produkt in dieser Kategorie (inkl. Unterkategorien) hat.',
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593712
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585519);

/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine', 'ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN M_Product_Category_PackingMaterial_ID NUMERIC(10)');

ALTER TABLE C_CompensationGroup_SchemaLine
    ADD CONSTRAINT mproductcategorypackingmaterial_ccompensationgroupschemaline
    FOREIGN KEY (M_Product_Category_PackingMaterial_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED;

-- ============================================================================
-- 3) AD_Field on the schema line tab (541042), form and grid sequence 17:
--    directly after M_Product_Category_ID (15) and before M_Product_ID (20)
-- ============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly,
                      SeqNo, SeqNoGrid)
VALUES (785612 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541042, 593712, 'Packmittel-Kategorie',
        'Leer = alle Zeilen. Wenn gesetzt, zählen nur Auftragszeilen, deren Packvorschrift ein Packmittel mit einem Produkt in dieser Kategorie (inkl. Unterkategorien) hat.',
        'de.metas.order',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N',
        17, 17);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 785612
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585519);

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785612;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(785612);

-- AD_UI_Element in the same group as M_Product_Category_ID / M_Product_ID (541468);
-- 17 is free on both the form and the grid sequence
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_Tab_ID, SeqNo, SeqNoGrid, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name)
VALUES (654939 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 09:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 09:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541468, 785612, 541042, 17, 17, 'Y', 'Y', 'N', 'Packmittel-Kategorie');
