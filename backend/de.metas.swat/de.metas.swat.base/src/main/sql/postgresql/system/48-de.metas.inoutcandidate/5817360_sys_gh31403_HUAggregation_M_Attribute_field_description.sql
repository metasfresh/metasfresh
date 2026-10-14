-- gh31403: clarify the "Merkmal" (M_Attribute) field description in the HU-Aggregation tab
-- (Window 540150 "Lieferkandidaten - Handler" -> tab M_ShipmentSchedule_AttributeConfig, AD_Field 563057).
--
-- The field reuses the generic shared element 2015 ("Produkt-Merkmal / Product Attribute like Color, Size"),
-- which says nothing about what listing an attribute here actually does. Give the field a dedicated
-- description/help via AD_Field.AD_Name_ID -> a NEW AD_Element (585152), leaving the shared element 2015
-- untouched (it backs every M_Attribute field system-wide).
--
-- Behaviour documented (kept technical HERE, plain-language in the user-facing text below):
-- an attribute listed here splits the picked-qty allocation (M_ShipmentSchedule_QtyPicked) and the shipment
-- line (M_InOutLine) per distinct value; it makes NO statement about whether the attribute is carried on the
-- HU/stock (that is M_HU_PI_Attribute.UseInASI, an independent setting).

-- 1. New name-only AD_Element (ColumnName NULL; overrides only this field's name/description/help)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy,
                        Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585152 /*From ID Server*/, 0, NULL,
        TO_TIMESTAMP('2026-08-04 09:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Merkmale, die Lieferpositionen aufteilen. Kommissionierte Waren, die sich in einem hier aufgeführten Merkmal unterscheiden (z. B. Herkunftsland, Los), werden auf getrennte Lieferpositionen und Mengenbuchungen gebucht. Nicht hier aufgeführte Merkmale lösen keine Aufteilung aus.',
        'de.metas.inoutcandidate', 'Y',
        'Merkmal', 'Merkmal',
        TO_TIMESTAMP('2026-08-04 09:01:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100);

-- 2. AD_Element_Trl skeleton rows for all system + base languages
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name,
                            PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585152
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- 3. Per-language text (de_DE + de_CH = German, en_US = English). Later timestamp than the element INSERT
--    so the propagation guard (f.updated <> e_trl.updated) always fires.
UPDATE AD_Element_Trl SET Name='Merkmal',
    Description='Merkmale, die Lieferpositionen aufteilen. Kommissionierte Waren, die sich in einem hier aufgeführten Merkmal unterscheiden (z. B. Herkunftsland, Los), werden auf getrennte Lieferpositionen und Mengenbuchungen gebucht. Nicht hier aufgeführte Merkmale lösen keine Aufteilung aus.',
    Help='Merkmale, die Lieferpositionen aufteilen. Kommissionierte Waren, die sich in einem hier aufgeführten Merkmal unterscheiden (z. B. Herkunftsland, Los), werden auf getrennte Lieferpositionen und Mengenbuchungen gebucht. Nicht hier aufgeführte Merkmale lösen keine Aufteilung aus. Ob ein Merkmal am Bestand geführt wird, wird dagegen in der Packvorschrift eingestellt und ist von dieser Konfiguration unabhängig.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-04 09:01:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 585152 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Attribute',
    Description='Attributes that split shipment lines. Picked goods that differ in a listed attribute (e.g. Country of Origin, Lot) are booked to separate shipment lines and picked-quantity allocations. Attributes not listed here do not cause a shipment-line split.',
    Help='Attributes that split shipment lines. Picked goods that differ in a listed attribute (e.g. Country of Origin, Lot) are booked to separate shipment lines and picked-quantity allocations. Attributes not listed here do not cause a shipment-line split. Whether an attribute is carried on stock is configured separately in the packing instruction and is independent of this configuration.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-04 09:01:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 585152 AND AD_Language = 'en_US';

-- 4. Propagate element translations
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585152, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585152, 'en_US');

-- 5. Point the field at the new element (earlier timestamp than the element, so the propagation guard fires)
UPDATE AD_Field
SET AD_Name_ID = 585152,
    Description = 'Merkmale, die Lieferpositionen aufteilen. Kommissionierte Waren, die sich in einem hier aufgeführten Merkmal unterscheiden (z. B. Herkunftsland, Los), werden auf getrennte Lieferpositionen und Mengenbuchungen gebucht. Nicht hier aufgeführte Merkmale lösen keine Aufteilung aus.',
    Help        = 'Merkmale, die Lieferpositionen aufteilen. Kommissionierte Waren, die sich in einem hier aufgeführten Merkmal unterscheiden (z. B. Herkunftsland, Los), werden auf getrennte Lieferpositionen und Mengenbuchungen gebucht. Nicht hier aufgeführte Merkmale lösen keine Aufteilung aus. Ob ein Merkmal am Bestand geführt wird, wird dagegen in der Packvorschrift eingestellt und ist von dieser Konfiguration unabhängig.',
    Updated = TO_TIMESTAMP('2026-08-04 09:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Field_ID = 563057;

-- 6. Propagate the new name element to the field's translations
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585152);

-- 7. Recreate the element link for the field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 563057;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(563057);
