-- gh#28631: Add IsDeliveryStop and DeliveryStopReason fields to Business Partner window (AD_Window_ID=123)
-- Fields are placed as ADVANCED fields (not shown by default in the main view).
-- ReadOnlyLogic: editable only when role group is 'Accounting'.

-- ==========================================================================
-- 1. AD_Field: IsDeliveryStop on BPartner main tab (AD_Tab_ID=220)
-- ==========================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      EntityType, ReadOnlyLogic)
VALUES (774882, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        220, 592208, NULL,
        'Lieferstopp', 'Liefer-/Auftragssperre',
        'Y', 'N', 'N', 'N',
        'de.metas.inoutcandidate', '@#AD_Role_Group/''''@!Accounting');

-- ==========================================================================
-- 2. AD_Field: DeliveryStopReason on BPartner main tab (AD_Tab_ID=220)
-- ==========================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      EntityType, ReadOnlyLogic)
VALUES (774883, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        220, 592209, NULL,
        'Lieferstopp Grund', 'Grund für die Liefer-/Auftragssperre',
        'Y', 'N', 'N', 'N',
        'de.metas.inoutcandidate', '@#AD_Role_Group/''''@!Accounting');

-- ==========================================================================
-- 3. AD_UI_Element: IsDeliveryStop as advanced field
-- Section 540287 "advanced edit", Column 540391, Group 540671 "advanced edit"
-- ==========================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField)
VALUES (648530, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        220, 540671, 774882, 'Lieferstopp', 100, 'Y');

-- ==========================================================================
-- 4. AD_UI_Element: DeliveryStopReason as advanced field
-- ==========================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField)
VALUES (648531, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        220, 540671, 774883, 'Lieferstopp Grund', 110, 'Y');
