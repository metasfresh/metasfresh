-- Run mode: SWING_CLIENT

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- 2025-10-24T13:08:27.500Z
UPDATE AD_Process_Trl SET Description='Purpose:
Creates a complete traceability overview for the product and batch (lot number).
The report tracks handling units (HUs) throughout their entire lifecycle – from receipt of goods by the supplier through production and storage to shipment to the customer.
This makes it possible to understand where materials come from, how they were used, and where they were delivered.

Process Parameters:
• Product: The product for which the traceability analysis is to be performed.
• Lot Number (Batch): The specific lot or batch to be traced across all related transactions.

What it shows:
• Current Stock per Lot: Displays the current on-hand quantity of the selected lot in the warehouse.
• Goods Receipts from Suppliers: Lists incoming deliveries of the lot, showing supplier, document, and date information.
• Shipments to Customers: Shows outgoing deliveries of the lot, including customer details, document numbers, and shipped quantities.
• Production Movements: Details components issued to production and finished or semi-finished goods received from production orders.
• Inventory Adjustments and Clearances: Includes manual stock corrections, inventory counts, and clearance or quality status changes.
• Material Trace Links: Connects raw material lots with the finished goods they were used in (and vice versa), ensuring full upstream and downstream traceability.

Report 2_ Columns:
• 2_Typ: The type of transaction (e.g., Goods Receipt, Production Issue, Shipment).
• 2_Produkt Nr.: The internal product number of the finished good or component.
• 2_Produktname: The product name or description.
• 2_Menge: Quantity of the product in the transaction.
• 2_Maßeinheit: Unit of measure (UOM) used for the quantity.
• 2_Leer: Supplier’s lot number, if available (blank if not applicable).
• 2_Belegdatum: Document date or best-before date, depending on context.
• 2_Freigabestatus: Clearance or quality status of the lot.
• 2_Kunde/Lieferant Nr.: Customer or vendor number involved in the transaction.
• 2_Kunde/Lieferant: Customer or vendor name.
• 2_Liefermenge: Quantity shipped to the customer (or received from supplier).
• 2_Belegnummer: Reference document number (e.g., shipment or receipt document).
• 2_Bestand: Current stock quantity of the lot.
• 2_Trace_ID: Internal traceability identifier linking related transactions',Updated=TO_TIMESTAMP('2025-10-24 13:08:27.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585253
;

-- 2025-10-24T13:08:27.569Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_HU_Trace_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel
-- 2025-10-24T13:14:34.228Z
UPDATE AD_Process SET Description='Zweck:
Erstellt eine vollständige Rückverfolgbarkeitsübersicht für Produkt und Charge (Losnummer).
Der Bericht verfolgt die Handling Units (HUs) über ihren gesamten Lebenszyklus hinweg – von der Warenanlieferung durch den Lieferanten über die Produktion und Lagerung bis hin zum Versand an den Kunden.
So lässt sich nachvollziehen, woher Materialien stammen, wie sie verwendet wurden und wohin sie geliefert wurden.

Prozessparameter:
• Produkt: Das Produkt, für das die Rückverfolgung durchgeführt werden soll.
• Losnummer (Charge): Die spezifische Charge oder Losnummer, deren Materialfluss nachvollzogen werden soll.

Inhalt des Berichts:
• Aktueller Lagerbestand pro Charge: Zeigt die derzeit verfügbare Menge der ausgewählten Charge im Lager.
• Wareneingänge von Lieferanten: Listet alle eingegangenen Lieferungen dieser Charge auf, inklusive Lieferant, Beleg und Datum.
• Warenausgänge zu Kunden: Zeigt alle Auslieferungen der Charge an Kunden mit Kundendaten, Belegnummer und Menge.
• Produktionsbewegungen: Beinhaltet Materialentnahmen für die Produktion und Wareneingänge fertiger oder halbfertiger Produkte.
• Bestandskorrekturen und Freigaben: Enthält manuelle Bestandsanpassungen, Inventurzählungen und Freigabe- oder Qualitätsstatusänderungen.
• Materialverknüpfungen: Verknüpft Rohstoffchargen mit den daraus hergestellten Endprodukten (und umgekehrt) für eine vollständige Rückverfolgbarkeit in beide Richtungen.

Berichtsspalten (2_):
• 2_Typ: Art der Bewegung (z. B. Wareneingang, Produktionsentnahme, Lieferung).
• 2_Produkt Nr.: Interne Produktnummer des Fertigprodukts oder der Komponente.
• 2_Produktname: Produktbezeichnung oder Beschreibung.
• 2_Menge: Menge des Produkts in der jeweiligen Bewegung.
• 2_Maßeinheit: Einheit, in der die Menge gemessen wird (Maßeinheit).
• 2_Leer: Lieferantenchargennummer, falls vorhanden (leer, wenn nicht zutreffend).
• 2_Belegdatum: Belegdatum oder Mindesthaltbarkeitsdatum, je nach Kontext.
• 2_Freigabestatus: Freigabe- oder Qualitätsstatus der Charge.
• 2_Kunde/Lieferant Nr.: Kunden- oder Lieferantennummer, die an der Bewegung beteiligt ist.
• 2_Kunde/Lieferant: Name des Kunden oder Lieferanten.
• 2_Liefermenge: Gelieferte oder empfangene Menge.
• 2_Belegnummer: Referenzbelegnummer (z. B. Liefer- oder Wareneingangsbeleg).
• 2_Bestand: Aktueller Bestand der Charge.
• 2_Trace_ID: Interne Rückverfolgungs-ID, die zusammengehörige Bewegungen verbindet.  ',Updated=TO_TIMESTAMP('2025-10-24 13:14:34.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585253
;

-- 2025-10-24T13:14:34.297Z
UPDATE AD_Process_Trl trl SET Description='Zweck:
Erstellt eine vollständige Rückverfolgbarkeitsübersicht für Produkt und Charge (Losnummer).
Der Bericht verfolgt die Handling Units (HUs) über ihren gesamten Lebenszyklus hinweg – von der Warenanlieferung durch den Lieferanten über die Produktion und Lagerung bis hin zum Versand an den Kunden.
So lässt sich nachvollziehen, woher Materialien stammen, wie sie verwendet wurden und wohin sie geliefert wurden.

Prozessparameter:
• Produkt: Das Produkt, für das die Rückverfolgung durchgeführt werden soll.
• Losnummer (Charge): Die spezifische Charge oder Losnummer, deren Materialfluss nachvollzogen werden soll.

Inhalt des Berichts:
• Aktueller Lagerbestand pro Charge: Zeigt die derzeit verfügbare Menge der ausgewählten Charge im Lager.
• Wareneingänge von Lieferanten: Listet alle eingegangenen Lieferungen dieser Charge auf, inklusive Lieferant, Beleg und Datum.
• Warenausgänge zu Kunden: Zeigt alle Auslieferungen der Charge an Kunden mit Kundendaten, Belegnummer und Menge.
• Produktionsbewegungen: Beinhaltet Materialentnahmen für die Produktion und Wareneingänge fertiger oder halbfertiger Produkte.
• Bestandskorrekturen und Freigaben: Enthält manuelle Bestandsanpassungen, Inventurzählungen und Freigabe- oder Qualitätsstatusänderungen.
• Materialverknüpfungen: Verknüpft Rohstoffchargen mit den daraus hergestellten Endprodukten (und umgekehrt) für eine vollständige Rückverfolgbarkeit in beide Richtungen.

Berichtsspalten (2_):
• 2_Typ: Art der Bewegung (z. B. Wareneingang, Produktionsentnahme, Lieferung).
• 2_Produkt Nr.: Interne Produktnummer des Fertigprodukts oder der Komponente.
• 2_Produktname: Produktbezeichnung oder Beschreibung.
• 2_Menge: Menge des Produkts in der jeweiligen Bewegung.
• 2_Maßeinheit: Einheit, in der die Menge gemessen wird (Maßeinheit).
• 2_Leer: Lieferantenchargennummer, falls vorhanden (leer, wenn nicht zutreffend).
• 2_Belegdatum: Belegdatum oder Mindesthaltbarkeitsdatum, je nach Kontext.
• 2_Freigabestatus: Freigabe- oder Qualitätsstatus der Charge.
• 2_Kunde/Lieferant Nr.: Kunden- oder Lieferantennummer, die an der Bewegung beteiligt ist.
• 2_Kunde/Lieferant: Name des Kunden oder Lieferanten.
• 2_Liefermenge: Gelieferte oder empfangene Menge.
• 2_Belegnummer: Referenzbelegnummer (z. B. Liefer- oder Wareneingangsbeleg).
• 2_Bestand: Aktueller Bestand der Charge.
• 2_Trace_ID: Interne Rückverfolgungs-ID, die zusammengehörige Bewegungen verbindet.  ' WHERE AD_Process_ID=585253 AND AD_Language='de_DE'
;

