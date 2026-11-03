-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: AD_Field rows for BaseAmt, M_InOut_ID, C_Invoice_ID on both Zahlungsplan tabs.
-- Both tabs reference C_OrderPaySchedule:
--   AD_Tab_ID=548449 (window 181  "Bestellung_OLD")
--   AD_Tab_ID=548450 (window 541889 "Bestellung")
-- DueAmt_Actual (iter 2) sits at SeqNo=10/SeqNoGrid=10 on both tabs.
-- New columns placed immediately after: BaseAmt=15, M_InOut_ID=20, C_Invoice_ID=25.
-- M_InOut_ID display label overridden via AD_Name_ID=584793 ("Wareneingang") because the
-- canonical element 1025 is named "Lieferung/Wareneingang" across 71+ other fields.
-- All fields are system-managed: IsReadOnly='Y'.

-- =============================================================================
-- BaseAmt — tab 548449 (Bestellung_OLD)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777439 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
       t.AD_Tab_ID, 592430 /*From ID Server*/, NULL,
       'Berechtigter Betrag',
       'Betrag, auf den der Prozentsatz angewendet wird',
       'Y', 'Y', 'Y', 'N',
       15, 15, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548449
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777439);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777439
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- BaseAmt — tab 548450 (Bestellung)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777440 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
       t.AD_Tab_ID, 592430 /*From ID Server*/, NULL,
       'Berechtigter Betrag',
       'Betrag, auf den der Prozentsatz angewendet wird',
       'Y', 'Y', 'Y', 'N',
       15, 15, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548450
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777440);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777440
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- M_InOut_ID — tab 548449 (Bestellung_OLD)
-- AD_Name_ID=584793 overrides the label to "Wareneingang" (spec AC #27)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777441 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
       t.AD_Tab_ID, 592431 /*From ID Server*/, 584793 /*Wareneingang override*/,
       'Wareneingang',
       'Der Wareneingang, an den diese Lieferungs-Unterzeile gebunden ist',
       'Y', 'Y', 'Y', 'N',
       20, 20, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548449
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777441);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777441
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- M_InOut_ID — tab 548450 (Bestellung)
-- AD_Name_ID=584793 overrides the label to "Wareneingang" (spec AC #27)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777442 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
       t.AD_Tab_ID, 592431 /*From ID Server*/, 584793 /*Wareneingang override*/,
       'Wareneingang',
       'Der Wareneingang, an den diese Lieferungs-Unterzeile gebunden ist',
       'Y', 'Y', 'Y', 'N',
       20, 20, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548450
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777442);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777442
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- C_Invoice_ID — tab 548449 (Bestellung_OLD)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777443 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
       t.AD_Tab_ID, 592432 /*From ID Server*/, NULL,
       'Rechnung',
       'Invoice Identifier',
       'Y', 'Y', 'Y', 'N',
       25, 25, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548449
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777443);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777443
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- C_Invoice_ID — tab 548450 (Bestellung)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
SELECT 777444 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
       t.AD_Tab_ID, 592432 /*From ID Server*/, NULL,
       'Rechnung',
       'Invoice Identifier',
       'Y', 'Y', 'Y', 'N',
       25, 25, 0, 'D'
FROM AD_Tab t
WHERE t.AD_Tab_ID = 548450
  AND NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 777444);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777444
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Propagate element translations now that all AD_Field rows exist
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584792);  -- BaseAmt
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584793);  -- Wareneingang override
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1008);    -- C_Invoice_ID
