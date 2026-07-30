-- Intrastat preview window — new AD_Element for the view-backed AD_Table PK.
--
-- All other AD_Elements referenced by the preview window's columns already exist and
-- are reused (semantic reuse per the metasfresh-designing-windows skill § 4):
--   584668  Intrastat                        (window/tab caption)
--   584088  CNCode                           (commodity number)
--   584089  GoodsDescription
--   584090  CountryDestinationConsignment
--   584091  CountryOfOrigin
--   584085  IntrastaNatureOfTransaction      (established metasfresh spelling — no "t" between Intrasta/Nature)
--   584092  NetMass
--   584093  SupplementaryUnits
--   584094  InvoiceValue
--   584095  StatisticalValue
--   584096  RecipientVATNo
-- Standard framework columns reuse the well-known central elements:
--   102 AD_Client_ID · 113 AD_Org_ID · 348 IsActive · 245 Created · 246 CreatedBy ·
--   607 Updated · 608 UpdatedBy · 1106 IsSOTrx · 206 C_Period_ID · 223 C_Year_ID.
--
-- This migration creates only the PK element (Intrastat_Preview_V_ID) that the new
-- AD_Table needs. The follow-up migration 5816980 creates the AD_Table + AD_Columns.

-- =====================================================================
-- 1. Intrastat_Preview_V_ID (PK element for the new AD_Table)
-- =====================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName)
VALUES (585149 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat_Preview_V_ID', 'D', 'Intrastat-Vorschau', 'Intrastat-Vorschau');

-- Seed AD_Element_Trl skeleton for every active system language (per skill review-rule:
-- SELECT-from-AD_Language, never explicit per-language VALUES).
INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsTranslated,
    Name, PrintName)
SELECT e.AD_Element_ID, l.AD_Language, e.AD_Client_ID, e.AD_Org_ID, 'Y',
    e.Created, e.CreatedBy, e.Updated, e.UpdatedBy, 'N',
    e.Name, e.PrintName
FROM AD_Element e, AD_Language l
WHERE e.AD_Element_ID = 585149
  AND l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t
                  WHERE t.AD_Element_ID = e.AD_Element_ID AND t.AD_Language = l.AD_Language);

-- en_US caption (differs from the German base).
UPDATE AD_Element_Trl
SET Name = 'Intrastat Preview', PrintName = 'Intrastat Preview', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 585149 AND AD_Language = 'en_US';

-- de_DE / de_CH: mark the base German as translated (Swiss inherits de_DE — no ß in the text).
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 585149 AND AD_Language IN ('de_DE', 'de_CH');
