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
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, EntityType, WidgetSize)
VALUES (648396, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        540098, 774697, 'Abschreibungsart', 160, 'Y', 'Y', 'N', 'D', 'S');

-- 6. AD_UI_Element on tab 686 (UI group 540062, max seqno was 120 → use 130)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, EntityType, WidgetSize)
VALUES (648397, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        540062, 774700, 'Abschreibungsart', 130, 'Y', 'Y', 'N', 'D', 'S');

-- Note: Tabs 684 and 685 have UI element groups (540026, 540222) with max_seqno=0 (no existing UI elements).
-- Adding UI elements there since the field is relevant for users to see the write-off type.

-- 7. AD_UI_Element on tab 684 (UI group 540026, no existing elements → use 10)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, EntityType, WidgetSize)
VALUES (648398, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        540026, 774698, 'Abschreibungsart', 10, 'Y', 'Y', 'N', 'D', 'S');

-- 8. AD_UI_Element on tab 685 (UI group 540222, no existing elements → use 10)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, EntityType, WidgetSize)
VALUES (648399, 0, 0, 'Y', TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99, TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), 99,
        540222, 774699, 'Abschreibungsart', 10, 'Y', 'Y', 'N', 'D', 'S');
