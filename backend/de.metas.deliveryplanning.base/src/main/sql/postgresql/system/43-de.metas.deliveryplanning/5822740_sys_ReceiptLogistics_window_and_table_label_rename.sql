-- Rename the window to the name the business owner actually agreed on. An earlier change had
-- invented a different name purely to dodge a menu-caption collision with two existing
-- "Wareneingangsdisposition*" windows (540196, 541954) -- matching the agreed name outranks that
-- menu tidiness, so this reverses the invented name. Also make the underlying view's Name/Description
-- state plainly what it is.
--
-- Part 1: AD_Window 542190 + its AD_Menu entry 542359 share ONE dedicated AD_Element (585424) -- the
-- impact query (AD_Column / AD_Field.AD_Name_ID / AD_Window / AD_Tab / AD_Menu / AD_Process_Para /
-- AD_UI_Element.AD_Name_ID / WEBUI_KPI_Field, all filtered on AD_Element_ID=585424) returns exactly
-- those two rows and nothing else, so this element is safe to mutate directly -- no fork needed.
-- Base language is German (verified: AD_Language.IsBaseLanguage='Y' for de_DE), so the base column
-- carries German and en_US is the translation override, per the corpus's base-language convention.
-- de_CH mirrors de_DE (no Swiss term swap needed -- no szett in the new name). fr_CH is left
-- untouched: it already only mirrors the old de_DE text as an unmaintained fallback
-- (AD_Element_Trl.IsTranslated='N'), same as before this change -- out of scope here.
--
-- Part 2: RV_ReceiptLogistics (AD_Table 542644) is a report VIEW -- TableName stays exactly as-is
-- (the "RV_" house prefix for a report view is accurate and a physical rename would touch dozens of
-- files for no user-visible gain). AD_Table has no AD_Element_ID -- like AD_Process, it is self-owned,
-- so its Name/Description are set directly on AD_Table + every AD_Table_Trl row (never inferred from
-- the window's element). Description states what one row IS.
--
-- No customer override exists for AD_Window 542190 (checked: zero AD_Window rows with
-- Overrides_Window_ID=542190) -- this is the only script this change needs.

-- Part 1: AD_Element 585424 -- drives AD_Window 542190's and AD_Menu 542359's caption.
UPDATE AD_Element_Trl SET Name='Wareneingangsdisposition inkl. Lieferplanung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 14:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585424 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Wareneingangsdisposition inkl. Lieferplanung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 14:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585424 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Receipt Disposition including Delivery Planning', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 14:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585424 AND AD_Language='en_US'
;

-- Propagate the element into AD_Element (base, de_DE), AD_Window / AD_Window_Trl, AD_Menu / AD_Menu_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585424)
;

-- Part 2: AD_Table 542644 (RV_ReceiptLogistics) -- self-owned Name/Description, TableName untouched.
UPDATE AD_Table SET
    Name='Wareneingangsdisposition inkl. Lieferplanung',
    Description='Eine aktive, eingehende Lieferplanung mit Wareneingangsdisposition, oder eine Wareneingangsdisposition ohne aktive Planung; Streckengeschäft ausgeschlossen, da diese Waren die Wareneingangslogistik nie erreichen.',
    Updated=TO_TIMESTAMP('2026-09-04 14:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644
;
UPDATE AD_Table_Trl SET
    Name='Wareneingangsdisposition inkl. Lieferplanung',
    Description='Eine aktive, eingehende Lieferplanung mit Wareneingangsdisposition, oder eine Wareneingangsdisposition ohne aktive Planung; Streckengeschäft ausgeschlossen, da diese Waren die Wareneingangslogistik nie erreichen.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 14:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='de_DE'
;
UPDATE AD_Table_Trl SET
    Name='Wareneingangsdisposition inkl. Lieferplanung',
    Description='Eine aktive, eingehende Lieferplanung mit Wareneingangsdisposition, oder eine Wareneingangsdisposition ohne aktive Planung; Streckengeschäft ausgeschlossen, da diese Waren die Wareneingangslogistik nie erreichen.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 14:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='de_CH'
;
UPDATE AD_Table_Trl SET
    Name='Receipt Disposition including Delivery Planning',
    Description='Either an active Incoming delivery planning carrying a receipt schedule, or a receipt schedule no active planning refers to; dropship rows are excluded because those goods never reach receipt logistics.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 14:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='en_US'
;
