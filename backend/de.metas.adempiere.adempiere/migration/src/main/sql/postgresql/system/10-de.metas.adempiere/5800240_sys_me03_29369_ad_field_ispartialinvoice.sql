-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: AD_Field rows for IsPartialInvoice.
-- C_DocType: tab 167 (Belegart window), no ReadOnlyLogic — admin sets the default freely.
-- C_Invoice: tabs 263 (Rechnung), 290 (Eingangsrechnung_OLD).
--   ReadOnlyLogic = '@DocStatus@!DR & @DocStatus@!IP'  (AC #14: editable only while DR or IP).
--
-- NOTE 2026-04-28: original draft also placed the field on tabs 549067 (Debitoren Rechnung,
-- window 542100) and 548567 (Eingangsrechnung, window 541976). Those tabs/windows exist only
-- on dev DBs — no committed migration script in metasfresh creates them, so the CI seed lacks
-- them and the FK insert fails. Both placements removed here to unblock CI; tracked as a
-- follow-up in ai-work/29369/pending-questions.md (Q-modern-invoice-tabs-missing-from-tree).

-- =============================================================================
-- 1. C_DocType — tab 167 (Belegart)
--    SeqNo 350 / SeqNoGrid 350 — after the last existing field (330/340)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID,
                      Name, Description, Help,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, EntityType)
VALUES (777445 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        167, 592433 /*C_DocType.IsPartialInvoice*/,
        'Teilrechnung',
        'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
        'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
        || 'wie der Anzahlungsbetrag zugeordnet wird.',
        'Y', 'Y', 'N', 'N',
        350, 350, 'D');

-- Skeleton AD_Field_Trl row
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777445
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 2. C_Invoice — tab 263 (Rechnung)
--    SeqNo 480 / SeqNoGrid 530 — after max (470/520)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID,
                      Name, Description, Help,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, ReadOnlyLogic, EntityType)
VALUES (777448 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        263, 592434 /*C_Invoice.IsPartialInvoice*/,
        'Teilrechnung',
        'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
        'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
        || 'wie der Anzahlungsbetrag zugeordnet wird.',
        'Y', 'Y', 'N', 'N',
        480, 530, '@DocStatus@!DR & @DocStatus@!IP', 'D');

-- Skeleton AD_Field_Trl row
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777448
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 3. C_Invoice — tab 290 (Eingangsrechnung_OLD)
--    SeqNo 400 / SeqNoGrid 340 — after max (390/330)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID,
                      Name, Description, Help,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, ReadOnlyLogic, EntityType)
VALUES (777449 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        290, 592434 /*C_Invoice.IsPartialInvoice*/,
        'Teilrechnung',
        'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
        'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
        || 'wie der Anzahlungsbetrag zugeordnet wird.',
        'Y', 'Y', 'N', 'N',
        400, 340, '@DocStatus@!DR & @DocStatus@!IP', 'D');

-- Skeleton AD_Field_Trl row
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777449
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 4. Re-propagate AD_Element translations to fill the new AD_Field_Trl rows
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584794);
