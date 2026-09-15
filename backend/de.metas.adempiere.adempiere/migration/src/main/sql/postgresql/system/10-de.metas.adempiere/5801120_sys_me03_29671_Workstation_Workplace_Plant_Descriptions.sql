-- me03#29671: add (or rewrite) descriptions on Workstation / Workplace / Plant /
-- Resource elements + the 5 ManufacturingResourceType list values, so configurers
-- get a useful tooltip in the WebUI without having to consult mf15-documentation
-- issue #113.
--
-- Locked terminology source-of-truth: ai-work/29671/terminology.md
-- Companion mf15-doc: https://github.com/metasfresh/mf15-documentation/issues/113
--
-- Pattern: for each AD_Element, set Description on the base + de_DE/de_CH/en_US
-- Trls (IsTranslated='Y' so the Trls don't fall back to base), then call the
-- standard helper to propagate to AD_Column / AD_Field. AD_Ref_List values are
-- updated directly (no helper function for them).

--
-- 1. IsManufacturingResource (AD_Element 53232) — flag on S_Resource
--

UPDATE AD_Element
SET Description='Markiert die Ressource als für die Produktion relevant. Nur entsprechend markierte S_Resource-Datensätze erscheinen in den Produktionsmodul-Auswahllisten und können vom Mobile-UI als Arbeitsstation gescannt werden.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53232;

UPDATE AD_Element_Trl
SET Description='Markiert die Ressource als für die Produktion relevant. Nur entsprechend markierte S_Resource-Datensätze erscheinen in den Produktionsmodul-Auswahllisten und können vom Mobile-UI als Arbeitsstation gescannt werden.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53232 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Description='Markiert die Ressource als für die Produktion relevant. Nur entsprechend markierte S_Resource-Datensätze erscheinen in den Produktionsmodul-Auswahllisten und können vom Mobile-UI als Arbeitsstation gescannt werden.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53232 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Description='Marks the resource as relevant for manufacturing. Only S_Resource records flagged accordingly appear in the manufacturing module''s selection lists and can be scanned as a Workstation in MobileUI.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53232 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(53232);
SELECT update_FieldTranslation_From_AD_Name_Element(53232);

--
-- 2. ManufacturingResourceType (AD_Element 53233) — type discriminator
--

UPDATE AD_Element
SET Description='Art der Produktions-Ressource: Arbeitsstation (physisches scanbares Gerät), Arbeitsbereich (Aggregat innerhalb einer Produktionsstätte), Produktionsstätte (Werk), Produktionslinie oder Externes System.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53233;

UPDATE AD_Element_Trl
SET Description='Art der Produktions-Ressource: Arbeitsstation (physisches scanbares Gerät), Arbeitsbereich (Aggregat innerhalb einer Produktionsstätte), Produktionsstätte (Werk), Produktionslinie oder Externes System.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53233 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Description='Art der Produktions-Ressource: Arbeitsstation (physisches scanbares Gerät), Arbeitsbereich (Aggregat innerhalb einer Produktionsstätte), Produktionsstätte (Werk), Produktionslinie oder Externes System.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53233 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Description='Type of manufacturing resource: Workstation (physical scannable device), Work Center (aggregate within a Plant), Plant (manufacturing site), Production Line, or External System.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=53233 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(53233);
SELECT update_FieldTranslation_From_AD_Name_Element(53233);

--
-- 3. WorkStation_ID (AD_Element 583018) — FK to S_Resource type WS used on PP_Order
--

UPDATE AD_Element
SET Description='Die Arbeitsstation, an der dieser Produktionsauftrag bearbeitet werden soll. In der MobileUI-Produktion erscheinen nur Aufträge, deren Arbeitsstation der vom Bediener gescannten entspricht.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583018;

UPDATE AD_Element_Trl
SET Description='Die Arbeitsstation, an der dieser Produktionsauftrag bearbeitet werden soll. In der MobileUI-Produktion erscheinen nur Aufträge, deren Arbeitsstation der vom Bediener gescannten entspricht.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583018 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Description='Die Arbeitsstation, an der dieser Produktionsauftrag bearbeitet werden soll. In der MobileUI-Produktion erscheinen nur Aufträge, deren Arbeitsstation der vom Bediener gescannten entspricht.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583018 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Description='The Workstation at which this manufacturing order is to be processed. In MobileUI Manufacturing, only orders whose Workstation matches the one scanned by the operator are shown.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583018 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(583018);
SELECT update_FieldTranslation_From_AD_Name_Element(583018);

--
-- 4. PP_Workstation_UserAssign_ID (AD_Element 583021) — user↔workstation assignment
--

UPDATE AD_Element
SET Description='Aktive Zuordnung eines Benutzers zu einer Arbeitsstation. Wird beim Scannen einer Arbeitsstation in der MobileUI-Produktion gesetzt und steuert anschließend, welche Aufträge dem Benutzer angezeigt werden.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583021;

UPDATE AD_Element_Trl
SET Description='Aktive Zuordnung eines Benutzers zu einer Arbeitsstation. Wird beim Scannen einer Arbeitsstation in der MobileUI-Produktion gesetzt und steuert anschließend, welche Aufträge dem Benutzer angezeigt werden.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583021 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Description='Aktive Zuordnung eines Benutzers zu einer Arbeitsstation. Wird beim Scannen einer Arbeitsstation in der MobileUI-Produktion gesetzt und steuert anschließend, welche Aufträge dem Benutzer angezeigt werden.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583021 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Description='Active assignment of a user to a Workstation. Created when the user scans a Workstation in MobileUI Manufacturing and afterwards drives which orders are shown to the user.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=583021 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(583021);
SELECT update_FieldTranslation_From_AD_Name_Element(583021);

--
-- 5. C_Workplace_ID (AD_Element 582772) — REWRITE the misleading existing description
--

UPDATE AD_Element
SET Description='Logischer Bereich in einem Lager, dem eine oder mehrere Arbeitsstationen zugeordnet sein können. Ein Bediener meldet sich pro Schicht an genau einem Arbeitsplatz an. Über das zugehörige Lager (M_Warehouse_ID) wird der Lagerort der dort verrichteten Arbeit bestimmt.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=582772;

UPDATE AD_Element_Trl
SET Description='Logischer Bereich in einem Lager, dem eine oder mehrere Arbeitsstationen zugeordnet sein können. Ein Bediener meldet sich pro Schicht an genau einem Arbeitsplatz an. Über das zugehörige Lager (M_Warehouse_ID) wird der Lagerort der dort verrichteten Arbeit bestimmt.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=582772 AND AD_Language='de_DE';

UPDATE AD_Element_Trl
SET Description='Logischer Bereich in einem Lager, dem eine oder mehrere Arbeitsstationen zugeordnet sein können. Ein Bediener meldet sich pro Schicht an genau einem Arbeitsplatz an. Über das zugehörige Lager (M_Warehouse_ID) wird der Lagerort der dort verrichteten Arbeit bestimmt.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=582772 AND AD_Language='de_CH';

UPDATE AD_Element_Trl
SET Description='Logical area within a warehouse, to which one or more Workstations can be assigned. An operator logs into exactly one Workplace per shift. The associated warehouse (M_Warehouse_ID) drives the storage location of the work performed there.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Element_ID=582772 AND AD_Language='en_US';

SELECT update_Column_Translation_From_AD_Element(582772);
SELECT update_FieldTranslation_From_AD_Name_Element(582772);

--
-- 6. AD_Ref_List values for ManufacturingResourceType (AD_Reference 53223)
--    — descriptions per value, in EN base + de_DE/de_CH Trls.
--    No helper function for AD_Ref_List; direct UPDATE only.
--

-- ES (AD_Ref_List 543704) -- External System
UPDATE AD_Ref_List
SET Description='External System: External system integration (e.g. Shopware, Alberta, external controller). Configured via ExternalSystem_Config_ID.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=543704;
UPDATE AD_Ref_List_Trl
SET Description='Externes System: Integration eines externen Systems (z. B. Shopware, Alberta, externe Steuerung). Wird über ExternalSystem_Config_ID konfiguriert.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=543704 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl
SET Description='Externes System: Integration eines externen Systems (z. B. Shopware, Alberta, externe Steuerung). Wird über ExternalSystem_Config_ID konfiguriert.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=543704 AND AD_Language='de_CH';

-- PL (AD_Ref_List 53244) -- Production Line
UPDATE AD_Ref_List
SET Description='Production Line: Production line within a Work Center.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53244;
UPDATE AD_Ref_List_Trl
SET Description='Produktionslinie: Produktionslinie innerhalb eines Arbeitsbereichs.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53244 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl
SET Description='Produktionslinie: Produktionslinie innerhalb eines Arbeitsbereichs.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53244 AND AD_Language='de_CH';

-- PT (AD_Ref_List 53245) -- Plant
UPDATE AD_Ref_List
SET Description='Plant: Manufacturing site. PP_Order.S_Resource_ID points at a Plant.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53245;
UPDATE AD_Ref_List_Trl
SET Description='Produktionsstätte: Standort der Fertigung. PP_Order.S_Resource_ID verweist auf eine Produktionsstätte.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53245 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl
SET Description='Produktionsstätte: Standort der Fertigung. PP_Order.S_Resource_ID verweist auf eine Produktionsstätte.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53245 AND AD_Language='de_CH';

-- WC (AD_Ref_List 53246) -- Work Center
UPDATE AD_Ref_List
SET Description='Work Center: Aggregate or department within a Plant. Groups multiple Workstations under one logical unit.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53246;
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsbereich: Aggregat oder Abteilung innerhalb einer Produktionsstätte. Fasst mehrere Arbeitsstationen zu einer logischen Einheit zusammen.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53246 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsbereich: Aggregat oder Abteilung innerhalb einer Produktionsstätte. Fasst mehrere Arbeitsstationen zu einer logischen Einheit zusammen.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53246 AND AD_Language='de_CH';

-- WS (AD_Ref_List 53247) -- Workstation
UPDATE AD_Ref_List
SET Description='Workstation: Physical scannable device within a Workplace (printer, scale, machine). Operators identify themselves by scanning the Workstation''s QR code.',
    Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247;
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsstation: Physisches scanbares Gerät innerhalb eines Arbeitsplatzes (Drucker, Waage, Maschine). Bediener identifizieren sich durch Scannen des Arbeitsstation-QR-Codes.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsstation: Physisches scanbares Gerät innerhalb eines Arbeitsplatzes (Drucker, Waage, Maschine). Bediener identifizieren sich durch Scannen des Arbeitsstation-QR-Codes.',
    IsTranslated='Y', Updated='2026-05-06 17:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='de_CH';
