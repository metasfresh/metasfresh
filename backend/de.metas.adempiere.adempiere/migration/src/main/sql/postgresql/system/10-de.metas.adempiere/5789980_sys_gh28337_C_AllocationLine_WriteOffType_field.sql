-- gh#28337: Add WriteOffType field to C_AllocationLine tabs and make it visible in UI
-- Tabs: 349 (Zuordnungs-Position), 684 (Zuordnung/Invoice), 685 (Zuordnung/PurchaseInvoice), 686 (Zuordnungen/Payment)

-- 1. AD_Field on tab 349 (Zuordnungs-Position, window 205 "Zuordnung aufheben")
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, SeqNo, EntityType)
VALUES (774697, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        349, 592061, 'Abschreibungsart', 'Y', 'Y', 'N', 170, 'D');

-- 2. AD_Field on tab 684 (Zuordnung, window 167 "Rechnung")
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, SeqNo, EntityType)
VALUES (774698, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        684, 592061, 'Abschreibungsart', 'Y', 'Y', 'N', 130, 'D');

-- 3. AD_Field on tab 685 (Zuordnung, window 183 "Eingangsrechnung")
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, SeqNo, EntityType)
VALUES (774699, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        685, 592061, 'Abschreibungsart', 'Y', 'Y', 'N', 130, 'D');

-- 4. AD_Field on tab 686 (Zuordnungen, window 195 "Zahlung")
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, IsDisplayed, IsReadOnly, IsSameLine, SeqNo, EntityType)
VALUES (774700, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        686, 592061, 'Abschreibungsart', 'Y', 'Y', 'N', 120, 'D');

-- 5. AD_UI_Element on tab 349 (UI group 540098, max seqno was 150 → use 160)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774697, 0, 349, 540098, 648396, 'F',
        TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, 'Y', 'N', 'Y', 'Y', 'N',
        'Abschreibungsart', 160, 160, 0, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99);

-- 6. AD_UI_Element on tab 686 (UI group 540062, max seqno was 120 → use 130)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774700, 0, 686, 540062, 648397, 'F',
        TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, 'Y', 'N', 'Y', 'Y', 'N',
        'Abschreibungsart', 130, 130, 0, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99);

-- Note: Tabs 684 and 685 have UI element groups (540026, 540222) with max_seqno=0 (no existing UI elements).
-- Adding UI elements there since the field is relevant for users to see the write-off type.

-- 7. AD_UI_Element on tab 684 (UI group 540026, no existing elements → use 10)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774698, 0, 684, 540026, 648398, 'F',
        TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, 'Y', 'N', 'Y', 'Y', 'N',
        'Abschreibungsart', 10, 10, 0, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99);

-- 8. AD_UI_Element on tab 685 (UI group 540222, no existing elements → use 10)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774699, 0, 685, 540222, 648399, 'F',
        TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, 'Y', 'N', 'Y', 'Y', 'N',
        'Abschreibungsart', 10, 10, 0, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99);

-- 9. AD_Field_Trl — skeleton rows for each new field (one per system language)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=774697
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=774698
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=774699
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=774700
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 10. Propagate translations from AD_Element_Trl into the new AD_Field_Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584561);
