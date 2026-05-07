-- Add AmountType field to the Mehrwertsteuercodes tab (AD_Window_ID=125, AD_Tab_ID=540720)

-- AD_Field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsCentrallyMaintained, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592499 /*From ID Server*/, 778076 /*From ID Server*/, 0, 540720,
    TO_TIMESTAMP('2026-05-07 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Betragsart', TO_TIMESTAMP('2026-05-07 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- AD_Field_Trl propagation
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 778076
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- English translation
UPDATE AD_Field_Trl
SET IsTranslated = 'Y', Name = 'Amount Type',
    Updated = TO_TIMESTAMP('2026-05-07 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Field_ID = 778076
;

-- AD_UI_Element (place after existing entries at SeqNo=90/SeqNoGrid=80)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 778076 /*From ID Server*/, 0, 540720,
    541335, 650507 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-05-07 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Betragsart', 100, 90, 0,
    TO_TIMESTAMP('2026-05-07 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
