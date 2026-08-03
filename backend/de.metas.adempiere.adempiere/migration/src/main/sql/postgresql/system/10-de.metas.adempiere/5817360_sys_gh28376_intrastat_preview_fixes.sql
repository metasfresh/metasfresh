-- Intrastat preview window (AD_Window 542179) — post-design-review fixes.
-- Findings from window-designer agent review (2026-08-03), Task 10.
--
-- Fix 1 (CRITICAL): UIStyle='label' is not a valid LayoutType enum value.
--   LayoutType.fromNullable() calls valueOf() which throws IllegalArgumentException
--   for any value other than 'primary' or 'secondary', crashing window load.
--   Only one AD_UI_ElementGroup in the whole DB used 'label' — this one.
--   Correct value for a grid-only single-group tab: NULL.
--
-- Fix 2 (HIGH): IsFilterField must be 'Y' for the three filter columns
--   (IsSOTrx, C_Year_ID, C_Period_ID). These carry IsSelectionColumn='Y' on
--   AD_Column, but AD_Field.IsFilterField was left NULL. Per design intent
--   (PLAN.md / REQUIREMENTS.md): all three are user-visible filter fields.
--
-- Fix 3 (HIGH): de_DE translations for 8 new custom AD_Elements were left as
--   English base text (e.g. "Goods description", "Net mass"). The AD_Element.Name
--   base language is de_DE in metasfresh; AD_Element_Trl.de_DE also carried the
--   English text with IsTranslated='N'. This causes the WebUI grid to render
--   English column headers for de_DE users.
--   Source of truth for intended German labels: AD_UI_Element.Name column, which
--   the author set correctly (e.g. "Warenbezeichnung", "Eigenmasse").
--   Fix: UPDATE AD_Element_Trl.de_DE + AD_Field_Trl.de_DE to the correct German
--   terms and set IsTranslated='Y'.
--   de_CH inherits de_DE (no ß in any of these terms) — also set IsTranslated='Y'.
--   RecipientVATNo: also fix missing umlaut (Empfaenger → Empfänger) in the
--   German label while updating.
--
-- Fix 4 (MEDIUM): AD_Field.IsDisplayedGrid='Y' for AD_Client_ID (field 781863).
--   Rule: Client must NOT appear in grid views. No AD_UI_Element exists for it
--   so it does not currently render, but the AD_Field flag is incorrect.
--   Set IsDisplayedGrid='N' to match the intent.

-- =========================================================================
-- Fix 1: UIStyle='label' → NULL on AD_UI_ElementGroup 555533
-- =========================================================================
UPDATE AD_UI_ElementGroup
SET    UIStyle  = NULL,
       Updated  = TO_TIMESTAMP('2026-08-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_UI_ElementGroup_ID = 555533;

-- =========================================================================
-- Fix 2: IsFilterField='Y' for the three filter AD_Fields
-- =========================================================================
UPDATE AD_Field
SET    IsFilterField = 'Y',
       Updated  = TO_TIMESTAMP('2026-08-03 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID IN (781870 /*IsSOTrx*/, 781871 /*C_Year_ID*/, 781872 /*C_Period_ID*/);

-- =========================================================================
-- Fix 3: de_DE / de_CH translations for 8 custom AD_Elements
--
-- Mapping (AD_Element_ID → correct de_DE name):
--   584088  CNCode                        → 'CN8-Code'
--   584089  GoodsDescription              → 'Warenbezeichnung'
--   584090  CountryDestinationConsignment → 'Bestimmungs-/Versendungsland'
--   584091  CountryOfOrigin               → 'Ursprungsland'
--   584092  NetMass                       → 'Eigenmasse'
--   584093  SupplementaryUnits            → 'Besondere Maßeinheit'
--   584094  InvoiceValue                  → 'Rechnungsbetrag'
--   584095  StatisticalValue              → 'Statistischer Wert'
--   584096  RecipientVATNo                → 'USt-IdNr. Empfänger'
-- Note: 584085 IntrastaNatureOfTransaction already has correct de_DE
--   'Art der Transaktion' (IsTranslated='N' is correct here — the base
--   AD_Element.Name IS already the German form, so 'N' just means no
--   separate override was needed; leaving as-is).
-- =========================================================================

-- 584088 CNCode
UPDATE AD_Element_Trl
SET    Name = 'CN8-Code', PrintName = 'CN8-Code', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584088 AND AD_Language IN ('de_DE', 'de_CH');

-- 584089 GoodsDescription
UPDATE AD_Element_Trl
SET    Name = 'Warenbezeichnung', PrintName = 'Warenbezeichnung', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584089 AND AD_Language IN ('de_DE', 'de_CH');

-- 584090 CountryDestinationConsignment
UPDATE AD_Element_Trl
SET    Name = 'Bestimmungs-/Versendungsland', PrintName = 'Bestimmungs-/Versendungsland', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584090 AND AD_Language IN ('de_DE', 'de_CH');

-- 584091 CountryOfOrigin
UPDATE AD_Element_Trl
SET    Name = 'Ursprungsland', PrintName = 'Ursprungsland', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584091 AND AD_Language IN ('de_DE', 'de_CH');

-- 584092 NetMass
UPDATE AD_Element_Trl
SET    Name = 'Eigenmasse', PrintName = 'Eigenmasse', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584092 AND AD_Language IN ('de_DE', 'de_CH');

-- 584093 SupplementaryUnits
-- 'Besondere Maßeinheit' uses ß — correct de_DE; de_CH version: 'Besondere Masseinheit' (ss)
UPDATE AD_Element_Trl
SET    Name = 'Besondere Maßeinheit', PrintName = 'Besondere Maßeinheit', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584093 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    Name = 'Besondere Masseinheit', PrintName = 'Besondere Masseinheit', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584093 AND AD_Language = 'de_CH';

-- 584094 InvoiceValue
UPDATE AD_Element_Trl
SET    Name = 'Rechnungsbetrag', PrintName = 'Rechnungsbetrag', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584094 AND AD_Language IN ('de_DE', 'de_CH');

-- 584095 StatisticalValue
UPDATE AD_Element_Trl
SET    Name = 'Statistischer Wert', PrintName = 'Statistischer Wert', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584095 AND AD_Language IN ('de_DE', 'de_CH');

-- 584096 RecipientVATNo — also fixes missing umlaut (Empfaenger → Empfänger)
UPDATE AD_Element_Trl
SET    Name = 'USt-IdNr. Empfänger', PrintName = 'USt-IdNr. Empfänger', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-08-03 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584096 AND AD_Language IN ('de_DE', 'de_CH');

-- Propagate updated AD_Element_Trl names into AD_Field_Trl for de_DE and de_CH.
-- Standard PostgreSQL UPDATE-FROM pattern: join source tables in FROM, correlate
-- the target table only in WHERE.
UPDATE AD_Field_Trl
SET    Name         = et.Name,
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-08-03 10:00:12', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
FROM   AD_Field f
JOIN   AD_Column c         ON c.AD_Column_ID   = f.AD_Column_ID
JOIN   AD_Element_Trl et   ON et.AD_Element_ID  = c.AD_Element_ID
WHERE  AD_Field_Trl.AD_Field_ID = f.AD_Field_ID
  AND  AD_Field_Trl.AD_Language  = et.AD_Language
  AND  f.AD_Tab_ID               = 549359
  AND  c.AD_Element_ID IN (584088, 584089, 584090, 584091, 584092, 584093, 584094, 584095, 584096)
  AND  AD_Field_Trl.AD_Language IN ('de_DE', 'de_CH');

-- =========================================================================
-- Fix 4: AD_Client_ID field — IsDisplayedGrid='N'
-- =========================================================================
UPDATE AD_Field
SET    IsDisplayedGrid = 'N',
       Updated  = TO_TIMESTAMP('2026-08-03 10:00:13', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 781863; /* AD_Client_ID on tab 549359 */
