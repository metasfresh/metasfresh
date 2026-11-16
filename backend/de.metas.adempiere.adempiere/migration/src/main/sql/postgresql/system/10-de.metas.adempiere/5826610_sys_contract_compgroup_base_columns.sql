-- Contract compensation groups: base-table columns.
-- Adds C_CompensationGroup_SchemaLine.M_Product_Category_ID (product-category schema-line base,
-- Table Direct, optional, reuses existing element 453 M_Product_Category_ID),
-- C_CompensationGroup_Schema.IsAdditive (each discount line computed on its own base instead of
-- the running total, YesNo, mandatory, default 'N', new element), and
-- C_Order_CompensationGroup.C_Flatrate_Term_ID (compensation group <-> contract term link, Search,
-- optional, read-only, reuses existing element 541447 C_Flatrate_Term_ID).

-- ============================================================================
-- 1) New AD_Element for IsAdditive
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585491 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'IsAdditive', 'D', 'Additiv', 'Additiv',
        'Jede Rabattzeile wird auf Basis ihrer eigenen Bemessungsgrundlage berechnet und nicht auf der laufenden Summe inklusive vorheriger Rabattzeilen.');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585491, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585491
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
SET Name         = 'Additive',
    PrintName    = 'Additive',
    Description  = 'Each discount line is computed on its own base, not on the running total incl. previous discount lines',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-28 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585491
  AND AD_Language = 'en_US';

-- de_CH mirrors de_DE (already copied by the skeleton insert above)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-28 09:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585491
  AND AD_Language = 'de_CH';

-- ============================================================================
-- 2) C_CompensationGroup_SchemaLine.M_Product_Category_ID (AD_Table_ID=540941)
--    Table Direct, optional, reuses existing element 453 (M_Product_Category_ID)
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593647 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.order', 'M_Product_Category_ID', 540941, 453, 19,
        10, 'Produkt Kategorie', 'Kategorie eines Produktes',
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593647
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(453);

/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine', 'ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN M_Product_Category_ID NUMERIC(10)');

ALTER TABLE C_CompensationGroup_SchemaLine
    ADD CONSTRAINT mproductcategory_ccompensationgroupschemaline
    FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED;

-- ============================================================================
-- 3) C_CompensationGroup_Schema.IsAdditive (AD_Table_ID=540940)
--    YesNo, mandatory, default 'N', new element 585491
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593648 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'D', 'IsAdditive', 540940, 585491, 20,
        1, 'Additiv', 'Jede Rabattzeile wird auf Basis ihrer eigenen Bemessungsgrundlage berechnet und nicht auf der laufenden Summe inklusive vorheriger Rabattzeilen.',
        'Y', 'Y', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593648
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585491);

/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema', 'ALTER TABLE public.C_CompensationGroup_Schema ADD COLUMN IsAdditive CHAR(1) NOT NULL DEFAULT ''N''');

ALTER TABLE C_CompensationGroup_Schema
    ADD CONSTRAINT c_compensationgroup_schema_isadditive_check
    CHECK (IsAdditive IN ('Y', 'N'));

-- ============================================================================
-- 4) C_Order_CompensationGroup.C_Flatrate_Term_ID (AD_Table_ID=540856)
--    Search, optional, read-only, reuses existing element 541447
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593649 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.order', 'C_Flatrate_Term_ID', 540856, 541447, 30,
        10, 'Pauschale - Vertragsperiode', NULL,
        'N', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593649
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(541447);

/* DDL */ SELECT public.db_alter_table('C_Order_CompensationGroup', 'ALTER TABLE public.C_Order_CompensationGroup ADD COLUMN C_Flatrate_Term_ID NUMERIC(10)');

ALTER TABLE C_Order_CompensationGroup
    ADD CONSTRAINT cflatrateterm_cordercompensationgroup
    FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED;

-- ============================================================================
-- 5) AD_Field: IsAdditive on the Schema header tab (541041), flags group (541469),
--    last in the group (after IsActive and IsInheritPackingInstruction)
-- ============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly,
                      SeqNo, SeqNoGrid)
VALUES (785581 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541041, 593648, 'Additiv',
        'Jede Rabattzeile wird auf Basis ihrer eigenen Bemessungsgrundlage berechnet und nicht auf der laufenden Summe inklusive vorheriger Rabattzeilen.',
        'de.metas.order',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N',
        NULL, 0);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 785581, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 785581
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585491);

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785581;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(785581);

-- AD_UI_Element for IsAdditive: flags group (541469), last in the group (after IsActive
-- seqno=10 and IsInheritPackingInstruction seqno=20)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_Tab_ID, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name)
VALUES (654909 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:04:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:04:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541469, 785581, 541041, 30, 'Y', 'N', 'N', 'Additiv');

-- ============================================================================
-- 6) AD_Field: M_Product_Category_ID in the SchemaLine grid tab (541042), right after M_Product_ID
-- ============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly,
                      SeqNo, SeqNoGrid)
VALUES (785582 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541042, 593647, 'Produkt Kategorie', 'Kategorie eines Produktes',
        'de.metas.order',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N',
        15, 15);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 785582, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 785582
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(453);

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785582;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(785582);

-- AD_UI_Element for M_Product_Category_ID: same group as M_Product_ID (541468), right after it
-- (M_Product_ID: SeqNo=20/SeqNoGrid=20; next used slot is 30) -- 25 is free on both layers.
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_Tab_ID, SeqNo, SeqNoGrid, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name)
VALUES (654910 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 09:05:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 09:05:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541468, 785582, 541042, 25, 25, 'Y', 'Y', 'N', 'Produkt Kategorie');
