-- nShift: add Attention (z. Hd.) advanced field to BPartner Location tab
-- IDs allocated from idserver.metas.de on 2026-05-28:
--   AD_MigrationScript 5805250
--   AD_Field           780640 (C_BPartner_Location.Attention window field)
--   AD_UI_Element      651941 (UI element for Attention field)

-- AD_Field: Attention field in BPartner Location tab (222)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592663, 780640 /*From ID Server*/, 0, 222,
        TO_TIMESTAMP('2026-05-28 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Attention, door code or additional mandatory information for the shipping label (max. 30 characters)',
        30, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'z. Hd.',
        TO_TIMESTAMP('2026-05-28 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- AD_Field_Trl: skeleton for all active languages
INSERT INTO AD_Field_Trl (AD_Client_ID, AD_Field_ID, AD_Language, Created, CreatedBy,
                          Description, Help, IsActive, IsTranslated, Name, Updated, UpdatedBy, AD_Org_ID)
SELECT 0, 780640 /*From ID Server*/, l.AD_Language,
       TO_TIMESTAMP('2026-05-28 10:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100, NULL, NULL, 'Y', 'N', 'z. Hd.',
       TO_TIMESTAMP('2026-05-28 10:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100, 0
FROM AD_Language l WHERE l.IsActive='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Field_ID=780640 AND tt.AD_Language=l.AD_Language);

-- AD_UI_Element: advanced field (hidden until user expands advanced section)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780640, 0, 222, 1000034, 651941 /*From ID Server*/,
        TO_TIMESTAMP('2026-05-28 10:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'z. Hd.', 190, 130, 0,
        TO_TIMESTAMP('2026-05-28 10:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);
