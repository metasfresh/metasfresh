-- Stock per week (F19100): add Description + Help to the window's custom fields.
--
-- The columns WeekStartDate / QtyExpectedShipments / QtyExpectedReceipts and the ATP
-- label-override element were created with Name/PrintName only (no Description/Help), so
-- their WebUI column headers render without an explanatory tooltip. This script sets the
-- base-language (German) Description/Help on each element + an en_US translation override,
-- then re-runs the standard propagation functions so the values cascade to AD_Field_Trl.
--
-- Elements touched (all window-specific — created by this issue's earlier migrations):
--   584938  WeekStartDate         (col 592708, field 780691, ad_name_id NULL  -> via AD_Column)
--   584939  QtyExpectedShipments  (col 592709, field 780692, ad_name_id NULL  -> via AD_Column)
--   584940  QtyExpectedReceipts   (col 592710, field 780693, ad_name_id NULL  -> via AD_Column)
--   584945  StockPerWeek_ATP      (label override for field 780694, ad_name_id 584945 -> via AD_Name)
-- The shared standard element 584821 (QtyATP) is deliberately NOT modified — the ATP field's
-- help comes from the window-specific override element 584945 via AD_Field.AD_Name_ID.

-- ============================================================
-- 584938 — Wochenbeginn (KW) / Week start
-- 2026-06-04T08:00:00Z
UPDATE AD_Element
   SET Description = 'Montag der ISO-Kalenderwoche.',
       Help = 'Beginn (Montag) der ISO-Kalenderwoche, für die die Mengen in dieser Zeile aggregiert sind. Überfällige Vorgänge (datiert vor der aktuellen Woche) werden in die aktuelle Woche gerollt.',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584938
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Montag der ISO-Kalenderwoche.',
       Help = 'Beginn (Montag) der ISO-Kalenderwoche, für die die Mengen in dieser Zeile aggregiert sind. Überfällige Vorgänge (datiert vor der aktuellen Woche) werden in die aktuelle Woche gerollt.',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584938
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Monday of the ISO calendar week.',
       Help = 'Start (Monday) of the ISO calendar week for which the quantities in this row are aggregated. Overdue items (dated before the current week) are rolled into the current week.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584938 AND AD_Language = 'en_US'
;
-- 2026-06-04T08:00:00Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584938)
;
-- 2026-06-04T08:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584938)
;

-- ============================================================
-- 584939 — Erwartete Lieferungen / Expected shipments
-- 2026-06-04T08:00:00Z
UPDATE AD_Element
   SET Description = 'Erwartete Warenausgänge in dieser Woche.',
       Help = 'Summe der geplanten Abgänge (offene Lieferdispositionen / Bedarf) für dieses Produkt im aufgelösten Lager in dieser Kalenderwoche. Quelle: Material-Disposition (MD_Candidate).',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584939
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Erwartete Warenausgänge in dieser Woche.',
       Help = 'Summe der geplanten Abgänge (offene Lieferdispositionen / Bedarf) für dieses Produkt im aufgelösten Lager in dieser Kalenderwoche. Quelle: Material-Disposition (MD_Candidate).',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584939
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Expected outbound quantity in this week.',
       Help = 'Sum of planned outbound movements (open shipment demand) for this product in the resolved warehouse during this calendar week. Source: material disposition (MD_Candidate).',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584939 AND AD_Language = 'en_US'
;
-- 2026-06-04T08:00:00Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584939)
;
-- 2026-06-04T08:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584939)
;

-- ============================================================
-- 584940 — Erwartete Wareneingänge / Expected receipts
-- 2026-06-04T08:00:00Z
UPDATE AD_Element
   SET Description = 'Erwartete Wareneingänge in dieser Woche.',
       Help = 'Summe der geplanten Zugänge (offene Beschaffungs- / Wareneingangsdispositionen) für dieses Produkt im aufgelösten Lager in dieser Kalenderwoche. Quelle: Material-Disposition (MD_Candidate).',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584940
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Erwartete Wareneingänge in dieser Woche.',
       Help = 'Summe der geplanten Zugänge (offene Beschaffungs- / Wareneingangsdispositionen) für dieses Produkt im aufgelösten Lager in dieser Kalenderwoche. Quelle: Material-Disposition (MD_Candidate).',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584940
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Expected inbound quantity in this week.',
       Help = 'Sum of planned inbound movements (open purchase / receipt supply) for this product in the resolved warehouse during this calendar week. Source: material disposition (MD_Candidate).',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584940 AND AD_Language = 'en_US'
;
-- 2026-06-04T08:00:00Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584940)
;
-- 2026-06-04T08:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584940)
;

-- ============================================================
-- 584945 — Verfügbar (ATP) / Available (ATP)   [the key field]
-- 2026-06-04T08:00:00Z
UPDATE AD_Element
   SET Description = 'Verfügbare Menge (Available-to-Promise) am Ende der Woche.',
       Help = 'Available-to-Promise: die kumulierte projizierte verfügbare Menge am Ende dieser Kalenderwoche – der fortgeschriebene Bestand inkl. aller erwarteten Zu- und Abgänge (Wareneingänge, Lieferungen, Produktion, Umlagerung). In der aktuellen Woche entspricht dies dem heutigen Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfließen, ist die ATP-Veränderung nicht zwingend gleich (Erwartete Wareneingänge − Erwartete Lieferungen). Quelle: Material-Disposition (MD_Candidate).',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584945
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Verfügbare Menge (Available-to-Promise) am Ende der Woche.',
       Help = 'Available-to-Promise: die kumulierte projizierte verfügbare Menge am Ende dieser Kalenderwoche – der fortgeschriebene Bestand inkl. aller erwarteten Zu- und Abgänge (Wareneingänge, Lieferungen, Produktion, Umlagerung). In der aktuellen Woche entspricht dies dem heutigen Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfließen, ist die ATP-Veränderung nicht zwingend gleich (Erwartete Wareneingänge − Erwartete Lieferungen). Quelle: Material-Disposition (MD_Candidate).',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584945
;
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Available-to-Promise quantity at the end of the week.',
       Help = 'Available-to-Promise: the cumulative projected available quantity at the end of this calendar week — the running stock balance including all expected inbound and outbound movements (receipts, shipments, production, distribution). For the current week this equals today''s on-hand stock. Because movements such as production and distribution also contribute, the ATP change is not necessarily equal to (expected receipts − expected shipments). Source: material disposition (MD_Candidate).',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584945 AND AD_Language = 'en_US'
;
-- de_CH: Swiss-German variant of the de_DE Help (ß -> ss: "einfließen" -> "einfliessen").
-- 2026-06-04T08:00:00Z
UPDATE AD_Element_Trl
   SET Description = 'Verfügbare Menge (Available-to-Promise) am Ende der Woche.',
       Help = 'Available-to-Promise: die kumulierte projizierte verfügbare Menge am Ende dieser Kalenderwoche – der fortgeschriebene Bestand inkl. aller erwarteten Zu- und Abgänge (Wareneingänge, Lieferungen, Produktion, Umlagerung). In der aktuellen Woche entspricht dies dem heutigen Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfliessen, ist die ATP-Veränderung nicht zwingend gleich (Erwartete Wareneingänge − Erwartete Lieferungen). Quelle: Material-Disposition (MD_Candidate).',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-04 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584945 AND AD_Language = 'de_CH'
;
-- 2026-06-04T08:00:00Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584945)
;
-- 2026-06-04T08:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584945)
;
