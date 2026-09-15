-- 2026-03-08
-- gh#28565: Change Invoiced parameter from YesNo (checkbox) to List (dropdown)
--
-- Problem: YesNo (AD_Reference_ID=20) is always Y or N — there is no "empty" state.
-- So the user can never see "all orders" — unchecked = N = only non-invoiced.
-- Fix: Use a List reference with two entries (Y/N). When the user leaves the dropdown
-- empty, the value is NULL → the SQL function shows all orders.

-- ============================================================
-- 1) Create AD_Reference (List type) for Invoiced filter
-- ============================================================
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType,
                          IsActive, IsOrderByValue, Name, ValidationType, Updated, UpdatedBy)
VALUES (0, 0, 542074, '2026-03-08 13:00', 100, 'D',
        'Y', 'N', 'PromotionCode_Invoiced_List', 'L', '2026-03-08 13:00', 100);

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name,
                              IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542074, NULL, NULL, 'PromotionCode_Invoiced_List',
       'Y', 0, 0, '2026-03-08 13:00', 100, '2026-03-08 13:00', 100
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = 542074);

-- ============================================================
-- 2) AD_Ref_List entries: Y = Ja/Yes, N = Nein/No
-- ============================================================
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy,
                         EntityType, IsActive, Name, Value, Updated, UpdatedBy)
VALUES (0, 0, 542074, 544166, '2026-03-08 13:00', 100, 'D', 'Y', 'Ja', 'Y', '2026-03-08 13:00', 100);

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name,
                             IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 544166, NULL, 'Ja',
       'N', 0, 0, '2026-03-08 13:00', 100, '2026-03-08 13:00', 100
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = 544166);

UPDATE AD_Ref_List_Trl SET Name = 'Yes', IsTranslated = 'Y', Updated = '2026-03-08 13:00', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544166 AND AD_Language = 'en_US';

INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy,
                         EntityType, IsActive, Name, Value, Updated, UpdatedBy)
VALUES (0, 0, 542074, 544167, '2026-03-08 13:00', 100, 'D', 'Y', 'Nein', 'N', '2026-03-08 13:00', 100);

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name,
                             IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 544167, NULL, 'Nein',
       'N', 0, 0, '2026-03-08 13:00', 100, '2026-03-08 13:00', 100
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = 544167);

UPDATE AD_Ref_List_Trl SET Name = 'No', IsTranslated = 'Y', Updated = '2026-03-08 13:00', UpdatedBy = 100
WHERE AD_Ref_List_ID = 544167 AND AD_Language = 'en_US';

-- ============================================================
-- 3) Change AD_Process_Para from YesNo (20) to List (17) with reference
-- ============================================================
UPDATE AD_Process_Para
SET AD_Reference_ID    = 17,
    AD_Reference_Value_ID = 542074,
    Description        = 'Filtert nach Fakturierungsstatus: Ja = nur fakturierte, Nein = nur nicht-fakturierte. Nicht gesetzt = alle Aufträge.',
    Help               = 'Dropdown mit drei Zuständen: Ja zeigt nur Aufträge die bereits fakturiert sind, Nein zeigt nur nicht-fakturierte Aufträge, '
                             || 'und wenn nichts gewählt ist werden alle Aufträge unabhängig vom Fakturierungsstatus angezeigt.',
    Updated            = '2026-03-08 13:00',
    UpdatedBy          = 100
WHERE AD_Process_Para_ID = 543142;

UPDATE AD_Process_Para_Trl
SET Description  = 'Filter by invoicing status: Yes = invoiced only, No = not-invoiced only. Leave empty for all orders.',
    Help         = 'Dropdown with three states: Yes shows only orders that have been invoiced, No shows only orders not yet invoiced, '
                       || 'and when nothing is selected all orders are shown regardless of invoicing status.',
    IsTranslated = 'Y',
    Updated      = '2026-03-08 13:00',
    UpdatedBy    = 100
WHERE AD_Process_Para_ID = 543142 AND AD_Language = 'en_US';
