-- Gives two fields on the C_Doc_Outbound_Config window (AD_Window 540173) DEDICATED
-- AD_Elements via AD_Field.AD_Name_ID, so each can carry accurate, user-friendly
-- Description + Help without disturbing the shared elements they currently inherit.
--
-- Both fields currently have AD_Field.AD_Name_ID = NULL and inherit a generic/shared
-- element (ExternalSystem_Config_ID is widely shared; DocBaseType is shared across
-- many tables). We create one name-only AD_Element per field and point the field's
-- AD_Name_ID at it.
--
-- IDs allocated from idserver.metas.de on 2026-06-11:
--   AD_Element 584973  (dedicated label for AD_Field 780753  ExternalSystem_Config_ID
--                       on C_Doc_Outbound_Config; AD_Element.ColumnName='ExternalSystem_Config_ID_DocOutbound')
--   AD_Element 584974  (dedicated label for AD_Field 551888  DocBaseType
--                       on C_Doc_Outbound_Config; AD_Element.ColumnName='DocBaseType_DocOutboundConfig')
--   AD_MigrationScript prefix: 5807280
--
-- AFFECTED RECORDS
-- =====================================================================
--
-- 1) NEW AD_Element 584973 -> AD_Field 780753 (C_Doc_Outbound_Config.ExternalSystem_Config_ID)
--    Window 540173 "Ausgehende Belege Konfig"
--    Lang  | Name                            | Description / Help (new)
--    ------+---------------------------------+-------------------------------------
--    de_DE | Externes System (Export-Ziel)   | optionale Verknuepfung + Hinweis dass
--    de_CH | Externes System (Export-Ziel)   |   das ext. System selbst per eigenen
--          |                                 |   Regeln entscheidet
--    en_US | External System (export target) | optional link + note that ext. system
--          |                                 |   decides by its own rules
--
-- 2) NEW AD_Element 584974 -> AD_Field 551888 (C_Doc_Outbound_Config.DocBaseType)
--    Window 540173 "Ausgehende Belege Konfig"
--    Lang  | Name               | Description / Help (new)
--    ------+--------------------+-----------------------------------------------
--    de_DE | Beleg-Basistyp     | schraenkt die Konfig auf eine Basisart ein;
--    de_CH | Beleg-Basistyp     |   leer = Auffang-Konfig; Aufloesungsreihenfolge
--    en_US | Document Base Type | restricts config to a base type; empty = catch-all;
--          |                    |   resolution order
--
-- 3) NOT AFFECTED:
--    - The shared elements behind ExternalSystem_Config_ID (578728) and DocBaseType
--      remain unchanged; every other field/column using them is untouched.
--    - 5807200 (already applied) is NOT modified.

-- ============================================================
-- FIELD 1: ExternalSystem_Config_ID (AD_Field 780753)
-- ============================================================

-- 1a. Name-only AD_Element 584973 (German base text)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    EntityType, Name, PrintName, Description, Help)
VALUES (0, 584973 /*From ID Server*/, 0, 'ExternalSystem_Config_ID_DocOutbound', 'Y',
    TO_TIMESTAMP('2026-06-11 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-11 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'D', 'Externes System (Export-Ziel)', 'Externes System (Export-Ziel)',
    'Optionale Verknüpfung mit der Konfiguration eines externen Systems. Wenn gesetzt, werden abgeschlossene Belege dieser Ausgangs-Konfiguration an das externe System zum Export übergeben — die tatsächliche Übermittlung hängt jedoch von den eigenen Zuordnungs-/Filterregeln des externen Systems ab (z.B. Belegart). Die Verknüpfung allein exportiert nicht zwangsläufig jeden Beleg.',
    'Das externe System (ExternalSystem_Config) und seine Export-Konfiguration entscheiden anhand eigener Kriterien (Belegart, Filterbedingung), welche der abgeschlossenen Belege tatsächlich exportiert werden.');

-- 1b. Skeleton AD_Element_Trl for all system + base languages (copies base German text)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name,
    PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
    WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name,
    t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
    t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584973
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- 1c. German translations de_DE / de_CH (IsTranslated='Y')
UPDATE AD_Element_Trl
SET Name='Externes System (Export-Ziel)', PrintName='Externes System (Export-Ziel)',
    Description='Optionale Verknüpfung mit der Konfiguration eines externen Systems. Wenn gesetzt, werden abgeschlossene Belege dieser Ausgangs-Konfiguration an das externe System zum Export übergeben — die tatsächliche Übermittlung hängt jedoch von den eigenen Zuordnungs-/Filterregeln des externen Systems ab (z.B. Belegart). Die Verknüpfung allein exportiert nicht zwangsläufig jeden Beleg.',
    Help='Das externe System (ExternalSystem_Config) und seine Export-Konfiguration entscheiden anhand eigener Kriterien (Belegart, Filterbedingung), welche der abgeschlossenen Belege tatsächlich exportiert werden.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 09:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584973 AND AD_Language IN ('de_DE','de_CH');

-- 1d. English translation en_US (IsTranslated='Y')
UPDATE AD_Element_Trl
SET Name='External System (export target)', PrintName='External System (export target)',
    Description='Optional link to an external-system configuration. When set, completed documents of this outbound config are handed to that external system for export — but whether a given document is actually transmitted depends on the external system''s own matching/filter rules (e.g. document base type). Linking alone does not export every document.',
    Help='The external system (ExternalSystem_Config) and its export configuration decide, by their own criteria (document base type, filter condition), which completed documents are actually exported.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 09:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584973 AND AD_Language='en_US';

-- 1e. Point AD_Field 780753 at the dedicated element (EARLIER timestamp than the
--     element_trl updates so the propagation guard f.updated <> e_trl.updated passes).
--     Help (base lang) is NOT propagated from the element, so set Description/Help directly too.
UPDATE AD_Field
SET AD_Name_ID=584973,
    Description='Optionale Verknüpfung mit der Konfiguration eines externen Systems. Wenn gesetzt, werden abgeschlossene Belege dieser Ausgangs-Konfiguration an das externe System zum Export übergeben — die tatsächliche Übermittlung hängt jedoch von den eigenen Zuordnungs-/Filterregeln des externen Systems ab (z.B. Belegart). Die Verknüpfung allein exportiert nicht zwangsläufig jeden Beleg.',
    Help='Das externe System (ExternalSystem_Config) und seine Export-Konfiguration entscheiden anhand eigener Kriterien (Belegart, Filterbedingung), welche der abgeschlossenen Belege tatsächlich exportiert werden.',
    Updated=TO_TIMESTAMP('2026-06-11 08:59:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780753;

-- 1f. Propagate the dedicated element to the field's Name/Description/Help (+ _Trl)
SELECT update_FieldTranslation_From_AD_Name_Element(584973);

-- 1g. Recreate element link for the field
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780753;
SELECT AD_Element_Link_Create_Missing_Field(780753);

-- ============================================================
-- FIELD 2: DocBaseType (AD_Field 551888)
-- ============================================================

-- 2a. Name-only AD_Element 584974 (German base text)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    EntityType, Name, PrintName, Description, Help)
VALUES (0, 584974 /*From ID Server*/, 0, 'DocBaseType_DocOutboundConfig', 'Y',
    TO_TIMESTAMP('2026-06-11 09:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-11 09:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'D', 'Beleg-Basistyp', 'Beleg-Basistyp',
    'Schränkt diese Ausgangs-Konfiguration auf eine bestimmte Beleg-Basisart ein. Leer = gilt für alle Beleg-Basisarten dieser Tabelle (Auffang-Konfiguration).',
    'Bei der Auflösung wird die spezifischste passende Konfiguration gewählt, in dieser Reihenfolge: (1) Tabelle + Beleg-Basisart + Organisation, (2) Tabelle + (leer) + Organisation, (3) Tabelle + Beleg-Basisart + beliebige Organisation, (4) Tabelle + (leer) + beliebige Organisation.');

-- 2b. Skeleton AD_Element_Trl for all system + base languages (copies base German text)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name,
    PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
    WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name,
    t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
    t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584974
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- 2c. German translations de_DE / de_CH (IsTranslated='Y')
UPDATE AD_Element_Trl
SET Name='Beleg-Basistyp', PrintName='Beleg-Basistyp',
    Description='Schränkt diese Ausgangs-Konfiguration auf eine bestimmte Beleg-Basisart ein. Leer = gilt für alle Beleg-Basisarten dieser Tabelle (Auffang-Konfiguration).',
    Help='Bei der Auflösung wird die spezifischste passende Konfiguration gewählt, in dieser Reihenfolge: (1) Tabelle + Beleg-Basisart + Organisation, (2) Tabelle + (leer) + Organisation, (3) Tabelle + Beleg-Basisart + beliebige Organisation, (4) Tabelle + (leer) + beliebige Organisation.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 09:10:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584974 AND AD_Language IN ('de_DE','de_CH');

-- 2d. English translation en_US (IsTranslated='Y')
UPDATE AD_Element_Trl
SET Name='Document Base Type', PrintName='Document Base Type',
    Description='Restricts this outbound config to a specific document base type. Empty = applies to all document base types of this table (catch-all config).',
    Help='Resolution picks the most specific matching config, in this order: (1) table + document base type + organization, (2) table + (empty) + organization, (3) table + document base type + any organization, (4) table + (empty) + any organization.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-11 09:10:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584974 AND AD_Language='en_US';

-- 2e. Point AD_Field 551888 at the dedicated element (EARLIER timestamp than element_trl)
UPDATE AD_Field
SET AD_Name_ID=584974,
    Description='Schränkt diese Ausgangs-Konfiguration auf eine bestimmte Beleg-Basisart ein. Leer = gilt für alle Beleg-Basisarten dieser Tabelle (Auffang-Konfiguration).',
    Help='Bei der Auflösung wird die spezifischste passende Konfiguration gewählt, in dieser Reihenfolge: (1) Tabelle + Beleg-Basisart + Organisation, (2) Tabelle + (leer) + Organisation, (3) Tabelle + Beleg-Basisart + beliebige Organisation, (4) Tabelle + (leer) + beliebige Organisation.',
    Updated=TO_TIMESTAMP('2026-06-11 09:09:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=551888;

-- 2f. Propagate the dedicated element to the field's Name/Description/Help (+ _Trl)
SELECT update_FieldTranslation_From_AD_Name_Element(584974);

-- 2g. Recreate element link for the field
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551888;
SELECT AD_Element_Link_Create_Missing_Field(551888);
