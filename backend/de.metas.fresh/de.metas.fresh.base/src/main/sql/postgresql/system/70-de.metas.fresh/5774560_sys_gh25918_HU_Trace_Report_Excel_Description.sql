-- Run mode: SWING_CLIENT

-- Value: M_HU_Trace_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel
-- 2025-10-24T11:13:20.842Z

-- 2025-10-24T11:18:11.305Z
UPDATE AD_Menu_Trl trl SET Description='Zweck:
Erstellt eine vollständige Rückverfolgbarkeitsübersicht für ein ausgewähltes Produkt und eine bestimmte Charge (Losnummer).
Der Bericht verfolgt die gewählten Handling Units (HUs) über ihren gesamten Lebenszyklus hinweg – von der Warenanlieferung durch den Lieferanten über die Produktion und Lagerung bis hin zum Versand an den Kunden.
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
• Materialverknüpfungen: Verknüpft Rohstoffchargen mit den daraus hergestellten Endprodukten (und umgekehrt) für eine vollständige Rückverfolgbarkeit in beide Richtungen.' WHERE AD_Menu_ID=542081 AND AD_Language='de_DE'
;

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- 2025-10-24T11:19:38.047Z
UPDATE AD_Process_Trl SET Description='Purpose:
Generates a complete traceability overview for a selected product and lot number (batch).
The report tracks the chosen handling units (HUs) throughout their entire lifecycle – from goods receipt, through production and storage, to shipment to the customer.
It helps identify where materials originated, how they were used in production, and where they were delivered.

Process Parameters:
• Product: The product for which the traceability analysis is to be performed.
• Lot Number (Batch): The specific lot or batch to be traced across all related transactions.

What it shows:
• Current Stock per Lot: Displays the current on-hand quantity of the selected lot in the warehouse.
• Goods Receipts from Suppliers: Lists incoming deliveries of the lot, showing supplier, document, and date information.
• Shipments to Customers: Shows outgoing deliveries of the lot, including customer details, document numbers, and shipped quantities.
• Production Movements: Details components issued to production and finished or semi-finished goods received from production orders.
• Inventory Adjustments and Clearances: Includes manual stock corrections, inventory counts, and clearance or quality status changes.
• Material Trace Links: Connects raw material lots with the finished goods they were used in (and vice versa), ensuring full upstream and downstream traceability.', IsTranslated='Y', Name='Traceability Report',Updated=TO_TIMESTAMP('2025-10-24 11:19:38.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585253
;

-- 2025-10-24T11:19:38.115Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

