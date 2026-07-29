-- Run mode: SWING_CLIENT

-- Value: M_HU_Trace_Report_Excel
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel
-- 2025-12-12T11:22:41.823Z
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

Berichtsspalten:
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
• 2_Trace_ID: Interne Rückverfolgungs-ID, die zusammengehörige Bewegungen verbindet.  ',Updated=TO_TIMESTAMP('2025-12-12 11:22:41.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585253
;

-- 2025-12-12T11:22:41.831Z
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

Berichtsspalten:
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

-- Name: Rückverfolgungsbericht
-- Action Type: P
-- Process: M_HU_Trace_Report_Excel(de.metas.handlingunits.trace.process.M_HU_Trace_Report_Excel)
-- 2025-12-12T11:22:41.847Z
UPDATE AD_Menu SET Description='Zweck:
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

Berichtsspalten:
• 2_Typ: Art der Bewegung (z. B. Wareneingang, Produktionsentnahme, Lieferung).
• 2_Produkt Nr.: Interne Produktnummer des Fertigprodukts oder der Komponente.
• 2_Produktname: Produktbezeichnung oder Beschreibung.
• 2_Menge: Menge des Produkts in der jeweiligen Bewegung.
• 2_Maßeinheit: Einheit, in der die Menge gemessen wird (Maßeinheit).
• 2_Leer: Lieferantenchargennummer, falls vorhanden (leer, wenn nicht zutreffend).
• 2_Belegdatum: Belegdatum oder Mindesthaltbarkeitsdatum, je nach Kontext.
• 2_Freigabestatus: Freigabe- ', IsActive='Y', Name='Rückverfolgungsbericht ',Updated=TO_TIMESTAMP('2025-12-12 11:22:41.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542081
;

-- 2025-12-12T11:22:41.849Z
UPDATE AD_Menu_Trl trl SET Description='Zweck:
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

Berichtsspalten:
• 2_Typ: Art der Bewegung (z. B. Wareneingang, Produktionsentnahme, Lieferung).
• 2_Produkt Nr.: Interne Produktnummer des Fertigprodukts oder der Komponente.
• 2_Produktname: Produktbezeichnung oder Beschreibung.
• 2_Menge: Menge des Produkts in der jeweiligen Bewegung.
• 2_Maßeinheit: Einheit, in der die Menge gemessen wird (Maßeinheit).
• 2_Leer: Lieferantenchargennummer, falls vorhanden (leer, wenn nicht zutreffend).
• 2_Belegdatum: Belegdatum oder Mindesthaltbarkeitsdatum, je nach Kontext.
• 2_Freigabestatus: Freigabe- ',Name='Rückverfolgungsbericht ' WHERE AD_Menu_ID=542081 AND AD_Language='de_DE'
;

