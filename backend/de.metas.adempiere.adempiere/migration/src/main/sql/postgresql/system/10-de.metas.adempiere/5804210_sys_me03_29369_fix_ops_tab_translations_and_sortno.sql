-- me03 #29369 — fix translations + default sort on Order Payment Schedule tab (window 541889 / tab 548450)
--
-- Three small polish items surfaced during the iter-3 UAT on customer instance:
--
-- (1) AD_Element 584784 `DueAmt_Actual` (UI label "Tatsächlich fälliger Betrag") — en_GB trl row
--     still carries the German fallback (`Tatsächlich fälliger Betrag`, IsTranslated='N'). English-
--     locale users in en_GB profiles see the German string. The en_US row already has the correct
--     "Actual due amount" — copy it into en_GB.
--
-- (2) AD_Element 577683 `OffsetDays` (UI label "Offset days") — the AD_Element.Name BASE language
--     value is the English string "Offset days" (German base column carrying English text!), and
--     every AD_Element_Trl row (including en_US) is the same English string with IsTranslated='N'.
--     Fix:
--       - Update AD_Element.Name (German base) to "Versatztage"
--       - Set en_US + en_GB AD_Element_Trl rows to "Offset days" with IsTranslated='Y'
--     Other languages are intentionally left to fall back to the German base (consistent with the
--     rest of the AD trl coverage on this customer).
--
-- (3) AD_Field 754537 `SeqNo` on the OPS tab — currently AD_Field.SortNo IS NULL, so the grid
--     renders rows in undefined order (LC + 3 BL rows appear shuffled). Setting SortNo=1 makes
--     SeqNo the default sort column ascending, so the rows render as 10/20/30/40.
--
-- All Element-level updates are followed by update_TRL_Tables_On_AD_Element_TRL_Update(elem, NULL)
-- so AD_Field_Trl / AD_Column_Trl / AD_Tab_Trl / AD_Menu_Trl rows propagate consistently. This is
-- the idiomatic cascade per the metasfresh-application-dictionary skill — direct AD_Field_Trl
-- UPDATEs on Name would be silently overwritten on the next element sync.
--
-- UpdatedBy = 100 (metasfresh user) per the AD-dictionary CreatedBy convention.

-- ============================================================================
-- (1) DueAmt_Actual — fix en_GB to match en_US
-- ============================================================================
UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-22 14:30:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE trl.AD_Element_ID = 584784
   AND trl.AD_Language   = 'en_GB'
   AND en.AD_Element_ID  = 584784
   AND en.AD_Language    = 'en_US'
   AND en.IsTranslated   = 'Y'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584784, NULL);

-- ============================================================================
-- (2) OffsetDays — fix the German base + set proper English trl for en_US + en_GB
-- ============================================================================

-- (2a) Update AD_Element.Name (German base column) — currently carries the English string
UPDATE AD_Element
   SET Name      = 'Versatztage',
       PrintName = 'Versatztage',
       Updated   = TO_TIMESTAMP('2026-05-22 14:30:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID = 577683
   AND Name          = 'Offset days'
;

-- (2b) Set en_US trl to "Offset days" (the English text the en_US AD_Element_Trl row should hold)
UPDATE AD_Element_Trl
   SET Name         = 'Offset days',
       PrintName    = 'Offset days',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-22 14:30:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 577683
   AND AD_Language   = 'en_US'
;

-- (2c) Set en_GB trl to match en_US
UPDATE AD_Element_Trl
   SET Name         = 'Offset days',
       PrintName    = 'Offset days',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-22 14:30:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 577683
   AND AD_Language   = 'en_GB'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577683, NULL);

-- ============================================================================
-- (3) AD_Field.SortNo — set default sort on the SeqNo field of OPS tab
--    (window 541889 → tab 548450 "Zahlungsplan" → field 754537 SeqNo)
-- ============================================================================
UPDATE AD_Field
   SET SortNo    = 1,
       Updated   = TO_TIMESTAMP('2026-05-22 14:30:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Field_ID = 754537
;
