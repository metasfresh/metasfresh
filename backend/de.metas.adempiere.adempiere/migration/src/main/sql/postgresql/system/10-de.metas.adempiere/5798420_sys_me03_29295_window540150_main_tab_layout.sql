-- me03#29295: WebUI layout for window 540150 main tab "Handler" (AD_Tab_ID=540412)
--
-- Master data window layout per design guide:
-- Left column: mandatory/important fields (TableName, Classname)
-- Right column: Active switch (top), then Org + Client (advanced edit, bottom)

-- =============================================================================
-- UI Section
-- =============================================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (547673 /*From ID Server*/, 0, 0, 540412,
        '2026-04-17 10:00', 0, 'Y', 'main', 10, '2026-04-17 10:00', 0, 'main');

-- =============================================================================
-- UI Columns (left + right for master data)
-- =============================================================================
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, AD_UI_Section_ID,
                          Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (549367 /*From ID Server*/, 0, 0, 547673,
        '2026-04-17 10:00', 0, 'Y', 10, '2026-04-17 10:00', 0);

INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, AD_UI_Section_ID,
                          Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (549368 /*From ID Server*/, 0, 0, 547673,
        '2026-04-17 10:00', 0, 'Y', 20, '2026-04-17 10:00', 0);

-- =============================================================================
-- Left column: element group with TableName + Classname
-- =============================================================================
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, AD_UI_Column_ID,
                                Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (555126 /*From ID Server*/, 0, 0, 549367,
        '2026-04-17 10:00', 0, 'Y', 'default', 10, 'primary', '2026-04-17 10:00', 0);

-- TableName (DB Table Name) — top-left, the key identifier
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649820 /*From ID Server*/, 0, 0, 540412,
        555126, 550221, 'F',
        '2026-04-17 10:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'Name der DB-Tabelle', 10, 10, 0,
        '2026-04-17 10:00', 0);

-- Classname (Java Class)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649821 /*From ID Server*/, 0, 0, 540412,
        555126, 550217, 'F',
        '2026-04-17 10:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'Java-Klasse', 20, 20, 0,
        '2026-04-17 10:00', 0);

-- =============================================================================
-- Right column: Active switch (top), then Org + Client (advanced edit)
-- =============================================================================
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, AD_UI_Column_ID,
                                Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (555127 /*From ID Server*/, 0, 0, 549368,
        '2026-04-17 10:00', 0, 'Y', 'flags', 10, 'primary', '2026-04-17 10:00', 0);

-- Active — top of right column
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649822 /*From ID Server*/, 0, 0, 540412,
        555127, 550211, 'F',
        '2026-04-17 10:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'Aktiv', 10, 30, 0,
        '2026-04-17 10:00', 0);

-- Organisation — advanced edit, second to last
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649823 /*From ID Server*/, 0, 0, 540412,
        555127, 550222, 'F',
        '2026-04-17 10:00', 0, 'Y', 'Y',
        'N', 'N', 'N',
        'Sektion', 20, 0, 0,
        '2026-04-17 10:00', 0);

-- Client — advanced edit, last
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649824 /*From ID Server*/, 0, 0, 540412,
        555127, 550218, 'F',
        '2026-04-17 10:00', 0, 'Y', 'Y',
        'N', 'N', 'N',
        'Mandant', 30, 0, 0,
        '2026-04-17 10:00', 0);
