-- English translation edited alongside the German (base-language) text below, per the
-- established base-language sync pattern for AD_Process 585253 (see 5775190).
-- Documents: the new link-basis column (proven vs. guessed pairing) and the corrected
-- meaning of 2_Menge / 2_Liefermenge (document totals, not per-row values -- do not sum).
-- Also restores 5775190's "only filled for Production Issue or Production Receipt" qualifier
-- on the German heading and adds the matching qualifier to the English heading, which never had it.

-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
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

Report 2_ Columns (only filled for Production Issue or Production Receipt):
• 2_Typ: The type of transaction (e.g., Goods Receipt, Production Issue, Shipment).
• 2_Produkt Nr.: The internal product number of the finished good or component.
• 2_Produktname: The product name or description.
• 2_Menge: The related shipment document''s total quantity for this product and lot.
• 2_Maßeinheit: Unit of measure (UOM) used for the quantity.
• 2_Leer: Supplier’s lot number, if available (blank if not applicable).
• 2_Belegdatum: Document date or best-before date, depending on context.
• 2_Freigabestatus: Clearance or quality status of the lot.
• 2_Kunde/Lieferant Nr.: Customer or vendor number involved in the transaction.
• 2_Kunde/Lieferant: Customer or vendor name.
• 2_Liefermenge: Same as 2_Menge – the related shipment document''s total quantity for this product and lot.
• 2_Belegnummer: Reference document number (e.g., shipment or receipt document).
• 2_Bestand: Current stock quantity of the lot.
• 2_Trace_ID: Internal traceability identifier linking related transactions

Additional columns:
• Menge: The related receipt document''s total quantity for this product and lot.
• Link basis: States whether the receipt-to-shipment pairing is proven or only estimated. TRACED: proven from the handling units'' packaging/transformation history. LOT_CANDIDATE: not proven, only guessed from a matching lot number. PRODUCT_CANDIDATE: like LOT_CANDIDATE but without a lot number, guessed from the product alone.

Important note: Menge, 2_Menge and 2_Liefermenge are document totals and repeat on every row assigned to that document. If, for example, one receipt has several rows assigned to it (one per shipment it is paired with), its full quantity appears on each of those rows – so these columns must NOT be summed, or the quantity gets multiplied. To get the actual received or shipped quantity, de-duplicate by document first.',Updated=TO_TIMESTAMP('2026-09-02 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585253
;

UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage() AND base.AD_Process_ID=585253
;

-- Value: M_HU_Trace_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel
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

Berichtsspalten (2_, nur ausgefüllt bei Produktionsentnahme oder Produktionswareneingang):
• 2_Typ: Art der Bewegung (z. B. Wareneingang, Produktionsentnahme, Lieferung).
• 2_Produkt Nr.: Interne Produktnummer des Fertigprodukts oder der Komponente.
• 2_Produktname: Produktbezeichnung oder Beschreibung.
• 2_Menge: Gesamtmenge des zugehörigen Warenausgangs-Belegs (Lieferung) für dieses Produkt und diese Charge.
• 2_Maßeinheit: Einheit, in der die Menge gemessen wird (Maßeinheit).
• 2_Leer: Lieferantenchargennummer, falls vorhanden (leer, wenn nicht zutreffend).
• 2_Belegdatum: Belegdatum oder Mindesthaltbarkeitsdatum, je nach Kontext.
• 2_Freigabestatus: Freigabe- oder Qualitätsstatus der Charge.
• 2_Kunde/Lieferant Nr.: Kunden- oder Lieferantennummer, die an der Bewegung beteiligt ist.
• 2_Kunde/Lieferant: Name des Kunden oder Lieferanten.
• 2_Liefermenge: Wie 2_Menge – Gesamtmenge des zugehörigen Warenausgangs-Belegs für dieses Produkt und diese Charge.
• 2_Belegnummer: Referenzbelegnummer (z. B. Liefer- oder Wareneingangsbeleg).
• 2_Bestand: Aktueller Bestand der Charge.
• 2_Trace_ID: Interne Rückverfolgungs-ID, die zusammengehörige Bewegungen verbindet.

Weitere Spalten:
• Menge: Gesamtmenge des zugehörigen Wareneingangs-Belegs für dieses Produkt und diese Charge.
• Zuordnung: Gibt an, ob die Verbindung zwischen Wareneingang und Warenausgang nachgewiesen oder nur geschätzt ist. TRACED: über die Verpackungs-/Umwandlungshistorie der Handling Units nachgewiesen. LOT_CANDIDATE: nicht nachgewiesen, sondern anhand einer übereinstimmenden Losnummer vermutet. PRODUCT_CANDIDATE: wie LOT_CANDIDATE, aber ohne Losnummer, nur anhand des Produkts vermutet.

Wichtiger Hinweis: Menge, 2_Menge und 2_Liefermenge sind Belegsummen und wiederholen sich auf jeder Zeile, die diesem Beleg zugeordnet ist. Ist z. B. ein Wareneingang mehreren Zeilen zugeordnet (je einer pro Warenausgang, dem er zugeordnet wurde), erscheint seine volle Menge auf jeder dieser Zeilen – die Spalten dürfen daher NICHT aufsummiert werden, sonst wird die Menge vervielfacht. Für die tatsächlich empfangene bzw. gelieferte Menge zuerst je Beleg deduplizieren.',Updated=TO_TIMESTAMP('2026-09-02 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585253
;

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

Berichtsspalten (2_, nur ausgefüllt bei Produktionsentnahme oder Produktionswareneingang):
• 2_Typ: Art der Bewegung (z. B. Wareneingang, Produktionsentnahme, Lieferung).
• 2_Produkt Nr.: Interne Produktnummer des Fertigprodukts oder der Komponente.
• 2_Produktname: Produktbezeichnung oder Beschreibung.
• 2_Menge: Gesamtmenge des zugehörigen Warenausgangs-Belegs (Lieferung) für dieses Produkt und diese Charge.
• 2_Maßeinheit: Einheit, in der die Menge gemessen wird (Maßeinheit).
• 2_Leer: Lieferantenchargennummer, falls vorhanden (leer, wenn nicht zutreffend).
• 2_Belegdatum: Belegdatum oder Mindesthaltbarkeitsdatum, je nach Kontext.
• 2_Freigabestatus: Freigabe- oder Qualitätsstatus der Charge.
• 2_Kunde/Lieferant Nr.: Kunden- oder Lieferantennummer, die an der Bewegung beteiligt ist.
• 2_Kunde/Lieferant: Name des Kunden oder Lieferanten.
• 2_Liefermenge: Wie 2_Menge – Gesamtmenge des zugehörigen Warenausgangs-Belegs für dieses Produkt und diese Charge.
• 2_Belegnummer: Referenzbelegnummer (z. B. Liefer- oder Wareneingangsbeleg).
• 2_Bestand: Aktueller Bestand der Charge.
• 2_Trace_ID: Interne Rückverfolgungs-ID, die zusammengehörige Bewegungen verbindet.

Weitere Spalten:
• Menge: Gesamtmenge des zugehörigen Wareneingangs-Belegs für dieses Produkt und diese Charge.
• Zuordnung: Gibt an, ob die Verbindung zwischen Wareneingang und Warenausgang nachgewiesen oder nur geschätzt ist. TRACED: über die Verpackungs-/Umwandlungshistorie der Handling Units nachgewiesen. LOT_CANDIDATE: nicht nachgewiesen, sondern anhand einer übereinstimmenden Losnummer vermutet. PRODUCT_CANDIDATE: wie LOT_CANDIDATE, aber ohne Losnummer, nur anhand des Produkts vermutet.

Wichtiger Hinweis: Menge, 2_Menge und 2_Liefermenge sind Belegsummen und wiederholen sich auf jeder Zeile, die diesem Beleg zugeordnet ist. Ist z. B. ein Wareneingang mehreren Zeilen zugeordnet (je einer pro Warenausgang, dem er zugeordnet wurde), erscheint seine volle Menge auf jeder dieser Zeilen – die Spalten dürfen daher NICHT aufsummiert werden, sonst wird die Menge vervielfacht. Für die tatsächlich empfangene bzw. gelieferte Menge zuerst je Beleg deduplizieren.',Updated=TO_TIMESTAMP('2026-09-02 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585253 AND AD_Language='de_DE'
;
