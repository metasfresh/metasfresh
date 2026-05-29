-- Run mode: SWING_CLIENT
--
-- gh#28121: Overhaul all Umsatz (revenue) reports
-- Improve names, add proper DE/EN descriptions, fix inconsistencies.
--
-- Reports covered:
--   540050  Umsatz pro Kunde
--   540544  Umsatzliste
--   540558  Umsatzreport (by attributes)
--   540561  Umsatzreport Geschäftspartner
--   540682  Umsatzreport Geschäftspartner Woche
--   540740  Umsatzreport Geschäftspartner mit Menge
--   540741  Umsatzreport Geschäftspartner Woche mit Menge
--   540754  Umsatzreport Woche
--   540973  Umsatzbericht Kreditlimit
--   584651  ProductTopRevenue
--   584660  CustomerTopRevenue
--

-- ========================================================================
-- 1) AD_Process 540050: "Umsatz pro Kunde (Jasper)"
--    Keep "(Jasper)" convention, add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Zeigt Umsätze pro Kunde, Produktkategorie und Produkt mit Jahresvergleich (aktuelles Jahr vs. Vorjahr vs. Vorvorjahr). Basiert auf Rechnungspositionen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540050;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue by Customer (Jasper)',
    Description = 'Revenue per customer, product category and product with year-over-year comparison (current year vs. prior year vs. 2 years ago). Based on invoice lines.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540050;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatz pro Kunde (Jasper)',
    Description = 'Zeigt Umsätze pro Kunde, Produktkategorie und Produkt mit Jahresvergleich (aktuelles Jahr vs. Vorjahr vs. Vorvorjahr). Basiert auf Rechnungspositionen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540050;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatz pro Kunde (Jasper)',
    Description = 'Zeigt Umsätze pro Kunde, Produktkategorie und Produkt mit Jahresvergleich (aktuelles Jahr vs. Vorjahr vs. Vorvorjahr). Basiert auf Rechnungspositionen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540050;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();


-- ========================================================================
-- 2) AD_Process 540544: "Umsatzliste"
--    Already has DE description, add EN and improve
-- ========================================================================

UPDATE AD_Process
SET Description = 'Umsatzliste auf Basis von Rechnungskandidaten. Zeigt fakturierte, gelieferte und bestellte Beträge pro Kunde, Produkt und Monat. Filterbar nach Zeitraum, Kunde und Transaktionsart (Verkauf/Einkauf).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540544;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue List',
    Description = 'Revenue list based on invoice candidates. Shows invoiced, shipped, and ordered amounts by customer, product, and month. Filterable by date range, customer, and transaction type (sales/purchase).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540544;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzliste auf Basis von Rechnungskandidaten. Zeigt fakturierte, gelieferte und bestellte Beträge pro Kunde, Produkt und Monat. Filterbar nach Zeitraum, Kunde und Transaktionsart (Verkauf/Einkauf).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540544;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzliste auf Basis von Rechnungskandidaten. Zeigt fakturierte, gelieferte und bestellte Beträge pro Kunde, Produkt und Monat. Filterbar nach Zeitraum, Kunde und Transaktionsart (Verkauf/Einkauf).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540544;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();


-- ========================================================================
-- 3) AD_Process 540558: "Umsatzreport" → "Umsatzreport nach Merkmalen"
--    Name too generic - clarify that this is attribute-based
-- ========================================================================

UPDATE AD_Process
SET Name = 'Umsatzreport nach Merkmalen',
    Description = 'Umsatzreport gruppiert nach Produktmerkmalen mit Perioden- und Jahresvergleich. Zeigt kumulierte Differenz und prozentuale Veränderung. Basiert auf Buchungsdaten (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540558;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report by Attributes',
    Description = 'Revenue report grouped by product attributes with period-over-period and year-over-year comparison. Shows cumulative difference and percentage change. Based on accounting entries (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540558;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzreport nach Merkmalen',
    Description = 'Umsatzreport gruppiert nach Produktmerkmalen mit Perioden- und Jahresvergleich. Zeigt kumulierte Differenz und prozentuale Veränderung. Basiert auf Buchungsdaten (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540558;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzreport nach Merkmalen',
    Description = 'Umsatzreport gruppiert nach Produktmerkmalen mit Perioden- und Jahresvergleich. Zeigt kumulierte Differenz und prozentuale Veränderung. Basiert auf Buchungsdaten (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540558;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu entries (two menu items point to this process)
UPDATE AD_Menu SET Name = 'Umsatzreport nach Merkmalen', Updated = now(), UpdatedBy = 100 WHERE AD_Menu_ID IN (540614, 540926);
UPDATE AD_Menu_Trl SET Name = 'Umsatzreport nach Merkmalen' WHERE AD_Menu_ID IN (540614, 540926) AND AD_Language IN ('de_DE', 'de_CH');
UPDATE AD_Menu_Trl SET Name = 'Revenue Report by Attributes' WHERE AD_Menu_ID IN (540614, 540926) AND AD_Language = 'en_US';


-- ========================================================================
-- 4) AD_Process 540561: "Umsatzreport Geschäftspartner"
--    Name is OK, add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Umsatzanalyse pro Geschäftspartner mit frei wählbarem Periodenvergleich (Basis- vs. Vergleichsperiode). Filterbar nach Kostenstelle, Produkt, Produktkategorie und Merkmalen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540561;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report by Partner',
    Description = 'Revenue analysis per business partner with configurable period comparison (base vs. comparison period). Filterable by cost center, product, product category, and attributes.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540561;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzanalyse pro Geschäftspartner mit frei wählbarem Periodenvergleich (Basis- vs. Vergleichsperiode). Filterbar nach Kostenstelle, Produkt, Produktkategorie und Merkmalen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540561;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzanalyse pro Geschäftspartner mit frei wählbarem Periodenvergleich (Basis- vs. Vergleichsperiode). Filterbar nach Kostenstelle, Produkt, Produktkategorie und Merkmalen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540561;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu descriptions
UPDATE AD_Menu SET Description = 'Umsatzanalyse pro Geschäftspartner mit frei wählbarem Periodenvergleich', Updated = now(), UpdatedBy = 100 WHERE AD_Menu_ID IN (540616, 540738);
UPDATE AD_Menu_Trl SET Name = 'Revenue Report by Partner' WHERE AD_Menu_ID IN (540616, 540738) AND AD_Language = 'en_US';


-- ========================================================================
-- 5) AD_Process 540682: "Umsatzreport Geschäftspartner Woche"
--    Fix de_CH bug ("Geschäftspartner Week" → "Woche"), add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Umsatzanalyse pro Geschäftspartner nach ISO-Kalenderwochen mit Wochenvergleich. Filterbar nach Produkt, Produktkategorie und Merkmalen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540682;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report by Partner (Weekly)',
    Description = 'Revenue analysis per business partner by ISO calendar week with week-over-week comparison. Filterable by product, product category, and attributes.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540682;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH: fix "Week" → "Woche"
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzreport Geschäftspartner Woche',
    Description = 'Umsatzanalyse pro Geschäftspartner nach ISO-Kalenderwochen mit Wochenvergleich. Filterbar nach Produkt, Produktkategorie und Merkmalen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540682;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzanalyse pro Geschäftspartner nach ISO-Kalenderwochen mit Wochenvergleich. Filterbar nach Produkt, Produktkategorie und Merkmalen.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540682;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu
UPDATE AD_Menu_Trl SET Name = 'Revenue Report by Partner (Weekly)' WHERE AD_Menu_ID IN (540712, 540925) AND AD_Language = 'en_US';


-- ========================================================================
-- 6) AD_Process 540740: "Umsatzreport Geschäftspartner mit Menge"
--    Add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Wie "Umsatzreport Geschäftspartner", zusätzlich mit Mengenangaben pro Produkt. Periodenvergleich mit Differenz und Prozentänderung.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540740;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report by Partner (with Qty)',
    Description = 'Like "Revenue Report by Partner" but additionally shows physical quantities per product. Period comparison with difference and percentage change.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540740;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Wie "Umsatzreport Geschäftspartner", zusätzlich mit Mengenangaben pro Produkt. Periodenvergleich mit Differenz und Prozentänderung.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540740;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Wie "Umsatzreport Geschäftspartner", zusätzlich mit Mengenangaben pro Produkt. Periodenvergleich mit Differenz und Prozentänderung.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540740;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu
UPDATE AD_Menu_Trl SET Name = 'Revenue Report by Partner (with Qty)' WHERE AD_Menu_ID IN (540739, 540922) AND AD_Language = 'en_US';


-- ========================================================================
-- 7) AD_Process 540741: "Umsatzreport Geschäftspartner Woche mit Menge"
--    Add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Umsatzanalyse pro Geschäftspartner nach Kalenderwochen mit Mengenangaben und Wochenvergleich.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540741;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report by Partner Weekly (with Qty)',
    Description = 'Revenue analysis per business partner by calendar week with quantities and week-over-week comparison.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540741;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzanalyse pro Geschäftspartner nach Kalenderwochen mit Mengenangaben und Wochenvergleich.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540741;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Umsatzanalyse pro Geschäftspartner nach Kalenderwochen mit Mengenangaben und Wochenvergleich.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540741;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu
UPDATE AD_Menu_Trl SET Name = 'Revenue Report by Partner Weekly (with Qty)' WHERE AD_Menu_ID IN (540740, 540923) AND AD_Language = 'en_US';


-- ========================================================================
-- 8) AD_Process 540754: "Umsatzreport Woche"
--    Add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Wöchentlicher Umsatzreport nach Merkmalen mit Jahresvergleich. Zeigt wöchentliche Umsatztotale und kumulative Jahresdaten. Basiert auf Buchungsdaten (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540754;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report Weekly (by Attributes)',
    Description = 'Weekly revenue report by product attributes with year-over-year comparison. Shows weekly totals and cumulative year-to-date figures. Based on accounting entries (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540754;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Wöchentlicher Umsatzreport nach Merkmalen mit Jahresvergleich. Zeigt wöchentliche Umsatztotale und kumulative Jahresdaten. Basiert auf Buchungsdaten (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540754;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Wöchentlicher Umsatzreport nach Merkmalen mit Jahresvergleich. Zeigt wöchentliche Umsatztotale und kumulative Jahresdaten. Basiert auf Buchungsdaten (Fact_Acct).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540754;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu
UPDATE AD_Menu_Trl SET Name = 'Revenue Report Weekly (by Attributes)' WHERE AD_Menu_ID IN (540750, 540927) AND AD_Language = 'en_US';


-- ========================================================================
-- 9) AD_Process 540973: "Umsatzbericht Kreditlimit"
--    Add descriptions
-- ========================================================================

UPDATE AD_Process
SET Description = 'Zeigt Kundenstammdaten, Kreditlimits (Versicherung/Management) und Umsätze der letzten 3 Monate. Filterbar nach Stichtag und Geschäftspartner.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 540973;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Revenue Report with Credit Limit',
    Description = 'Shows customer master data, credit limits (insurance/management) and revenue for the last 3 months. Filterable by reporting date and business partner.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 540973;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Zeigt Kundenstammdaten, Kreditlimits (Versicherung/Management) und Umsätze der letzten 3 Monate. Filterbar nach Stichtag und Geschäftspartner.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 540973;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Description = 'Zeigt Kundenstammdaten, Kreditlimits (Versicherung/Management) und Umsätze der letzten 3 Monate. Filterbar nach Stichtag und Geschäftspartner.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 540973;

UPDATE AD_Process base SET Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- Menu
UPDATE AD_Menu SET Description = 'Zeigt Kundenstammdaten, Kreditlimits und Umsätze der letzten 3 Monate', Updated = now(), UpdatedBy = 100 WHERE AD_Menu_ID IN (541099, 541100);
UPDATE AD_Menu_Trl SET Name = 'Revenue Report with Credit Limit' WHERE AD_Menu_ID IN (541099, 541100) AND AD_Language = 'en_US';


-- ========================================================================
-- 10) AD_Process 584651: "ProductTopRevenue"
--     Fix technical name, add DE name, fix garbled fr_CH, add descriptions
-- ========================================================================

UPDATE AD_Process
SET Name = 'Umsatzstärkste Produkte (Excel)',
    Description = 'Exportiert die umsatzstärksten Produkte als Excel-Datei. Filterbar nach Zeitraum und Anzahl Ergebnisse (Limit).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 584651;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Top-Revenue Products (Excel)',
    Description = 'Exports top-revenue products to Excel. Filterable by date range and result limit.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 584651;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzstärkste Produkte (Excel)',
    Description = 'Exportiert die umsatzstärksten Produkte als Excel-Datei. Filterbar nach Zeitraum und Anzahl Ergebnisse (Limit).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 584651;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzstärkste Produkte (Excel)',
    Description = 'Exportiert die umsatzstärksten Produkte als Excel-Datei. Filterbar nach Zeitraum und Anzahl Ergebnisse (Limit).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 584651;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- fr_CH: fix garbled "ProductTopReveue"
DELETE FROM AD_Process_Trl WHERE AD_Language = 'fr_CH' AND AD_Process_ID = 584651;

-- Menu: add descriptions
UPDATE AD_Menu SET Name = 'Umsatzstärkste Produkte (Excel)', Description = 'Exportiert die umsatzstärksten Produkte als Excel-Datei', Updated = now(), UpdatedBy = 100 WHERE AD_Menu_ID = 541432;
UPDATE AD_Menu_Trl SET Name = 'Umsatzstärkste Produkte (Excel)', Description = 'Exportiert die umsatzstärksten Produkte als Excel-Datei' WHERE AD_Menu_ID = 541432 AND AD_Language IN ('de_DE', 'de_CH');
UPDATE AD_Menu_Trl SET Name = 'Top-Revenue Products (Excel)', Description = 'Exports top-revenue products to Excel' WHERE AD_Menu_ID = 541432 AND AD_Language = 'en_US';


-- ========================================================================
-- 11) AD_Process 584660: "CustomerTopRevenue"
--     Fix technical name, add DE name, fix garbled fr_CH, add descriptions
-- ========================================================================

UPDATE AD_Process
SET Name = 'Umsatzstärkste Kunden (Excel)',
    Description = 'Exportiert die umsatzstärksten Kunden als Excel-Datei. Filterbar nach Zeitraum und Anzahl Ergebnisse (Limit).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 584660;

-- en_US
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Top-Revenue Customers (Excel)',
    Description = 'Exports top-revenue customers to Excel. Filterable by date range and result limit.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 584660;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'en_US' AND trl.AD_Language = getBaseLanguage();

-- de_CH
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzstärkste Kunden (Excel)',
    Description = 'Exportiert die umsatzstärksten Kunden als Excel-Datei. Filterbar nach Zeitraum und Anzahl Ergebnisse (Limit).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 584660;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_CH' AND trl.AD_Language = getBaseLanguage();

-- de_DE
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name = 'Umsatzstärkste Kunden (Excel)',
    Description = 'Exportiert die umsatzstärksten Kunden als Excel-Datei. Filterbar nach Zeitraum und Anzahl Ergebnisse (Limit).',
    Updated = now(), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 584660;

UPDATE AD_Process base SET Name = trl.Name, Description = trl.Description, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage();

-- fr_CH: fix garbled "CustomETOPROPREVEUNE"
DELETE FROM AD_Process_Trl WHERE AD_Language = 'fr_CH' AND AD_Process_ID = 584660;

-- Menu: add descriptions
UPDATE AD_Menu SET Name = 'Umsatzstärkste Kunden (Excel)', Description = 'Exportiert die umsatzstärksten Kunden als Excel-Datei', Updated = now(), UpdatedBy = 100 WHERE AD_Menu_ID = 541439;
UPDATE AD_Menu_Trl SET Name = 'Umsatzstärkste Kunden (Excel)', Description = 'Exportiert die umsatzstärksten Kunden als Excel-Datei' WHERE AD_Menu_ID = 541439 AND AD_Language IN ('de_DE', 'de_CH');
UPDATE AD_Menu_Trl SET Name = 'Top-Revenue Customers (Excel)', Description = 'Exports top-revenue customers to Excel' WHERE AD_Menu_ID = 541439 AND AD_Language = 'en_US';
