-- BP-Group window: disambiguate the two payment-term field labels + rename "Verband".
-- Operates on the core window 192 (all customers). The element-583888 rename below propagates
-- via update_TRL_Tables_On_AD_Element_TRL_Update to every AD_Field referencing that element,
-- so any customer override window that reuses it is updated by the same call.
--
-- AFFECTED RECORDS
-- =====================================================================
-- 1) NEW AD_Element 585048 (name-only override for the C_PaymentTerm_ID field):
--      de_DE 'Zahlungsbedingung (Kunde)' | de_CH 'Zahlungskondition (Kunde)' | en_US 'Payment Term (Customer)'
-- 2) NEW AD_Element 585049 (name-only override for the PO_PaymentTerm_ID field):
--      de_DE 'Zahlungskondition (Lieferant)' | de_CH 'Zahlungskondition (Lieferant)' | en_US 'PO Payment Term (Vendor)'
-- 3) AD_Field 753506 (C_PaymentTerm_ID, window 192 tab 322) -> AD_Name_ID=585048
--    AD_Field 753505 (PO_PaymentTerm_ID, window 192 tab 322) -> AD_Name_ID=585049
-- 4) AD_Element 583888 (the IsDeviatingBillBPartner column label) renamed:
--      'Verband'/'Association' -> de_DE/de_CH 'Abweichender Rechnungsempfaenger' | en_US 'Alternate Invoice Recipient'
--
-- NOT AFFECTED: shared elements 204 (C_PaymentTerm_ID) / 1576 (PO_PaymentTerm_ID) stay untouched --
-- they are context-dependent on ~50 / ~16 other windows where a (Kunde)/(Lieferant) suffix would be
-- wrong; only the two BP-Group fields get the suffixed label via AD_Field.AD_Name_ID.

-- ============================================================================
-- 1) AD_Element 585048 — C_PaymentTerm_ID label "(Kunde)"
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585048 /*From ID Server*/, 0, NULL,
        TO_TIMESTAMP('2026-06-24 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        NULL, 'D', 'Y', 'Zahlungsbedingung (Kunde)', 'Zahlungsbedingung (Kunde)',
        TO_TIMESTAMP('2026-06-24 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585048
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Zahlungsbedingung (Kunde)', PrintName='Zahlungsbedingung (Kunde)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585048 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Zahlungskondition (Kunde)', PrintName='Zahlungskondition (Kunde)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585048 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='Payment Term (Customer)', PrintName='Payment Term (Customer)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585048 AND AD_Language='en_US';

-- ============================================================================
-- 2) AD_Element 585049 — PO_PaymentTerm_ID label "(Lieferant)"
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585049 /*From ID Server*/, 0, NULL,
        TO_TIMESTAMP('2026-06-24 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        NULL, 'D', 'Y', 'Zahlungskondition (Lieferant)', 'Zahlungskondition (Lieferant)',
        TO_TIMESTAMP('2026-06-24 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585049
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Zahlungskondition (Lieferant)', PrintName='Zahlungskondition (Lieferant)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585049 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Zahlungskondition (Lieferant)', PrintName='Zahlungskondition (Lieferant)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585049 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='PO Payment Term (Vendor)', PrintName='PO Payment Term (Vendor)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585049 AND AD_Language='en_US';

-- ============================================================================
-- 3) Point the two displayed core fields (window 192 tab 322) at the override elements
--    (earlier timestamp than the element_trl rows so the propagation guard fires)
-- ============================================================================
UPDATE AD_Field SET AD_Name_ID=585048, Updated=TO_TIMESTAMP('2026-06-24 09:59:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=753506;
UPDATE AD_Field SET AD_Name_ID=585049, Updated=TO_TIMESTAMP('2026-06-24 09:59:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=753505;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585048);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585049);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=753506;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(753506);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753505;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(753505);

-- ============================================================================
-- 4) Rename element 583888 (column IsDeviatingBillBPartner): Verband -> Abweichender Rechnungsempfaenger
--    Single-use element (only AD_Column 590706). Propagation updates AD_Column + every AD_Field that
--    references it (core window 192 field 752676, plus any customer override window reusing it).
-- ============================================================================
UPDATE AD_Element_Trl SET Name='Abweichender Rechnungsempfänger', PrintName='Abweichender Rechnungsempfänger', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=583888 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Abweichender Rechnungsempfänger', PrintName='Abweichender Rechnungsempfänger', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=583888 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='Alternate Invoice Recipient', PrintName='Alternate Invoice Recipient', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-24 10:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=583888 AND AD_Language='en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583888);
