-- 2026-03-07
-- me03#26915: Add IsInheritPackingInstruction flag to C_CompensationGroup_Schema
-- When Y, Quick Input copies the main article's packing instruction to all template-created sub-article order lines

-- AD_Element
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (584629, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'IsInheritPackingInstruction', 'D', 'Inherit Packing Instruction', 'Inherit Packing Instruction',
        'If set, the packing instruction from the main article is applied to all sub-articles created from the compensation group schema template.');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584629, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 584629
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- German translation
UPDATE AD_Element_Trl
SET Name         = 'Packvorschrift vererben',
    PrintName    = 'Packvorschrift vererben',
    Description  = 'Wenn gesetzt, wird die Packvorschrift des Hauptartikels auf alle aus dem Kompensationsgruppen-Schema erstellten Unterartikel uebertragen.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584629
  AND AD_Language = 'de_DE';

-- English translation
UPDATE AD_Element_Trl
SET Name         = 'Inherit Packing Instruction',
    PrintName    = 'Inherit Packing Instruction',
    Description  = 'If set, the packing instruction from the main article is applied to all sub-articles created from the compensation group schema template.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584629
  AND AD_Language = 'en_US';

-- propagate element translations
SELECT update_ad_element_on_ad_element_trl_update(584629, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584629, 'de_DE');
SELECT update_ad_element_on_ad_element_trl_update(584629, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584629, 'en_US');

-- AD_Column on C_CompensationGroup_Schema (AD_Table_ID=540940)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (592198, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        0, 'D', 'IsInheritPackingInstruction', 540940, 584629, 20,
        1, 'Inherit Packing Instruction', 'If set, the packing instruction from the main article is applied to all sub-articles created from the compensation group schema template.',
        'Y', 'Y', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP');

-- Physical column (add new column, then enforce constraints)
ALTER TABLE C_CompensationGroup_Schema ADD COLUMN IF NOT EXISTS IsInheritPackingInstruction CHAR(1) DEFAULT 'N';
UPDATE C_CompensationGroup_Schema SET IsInheritPackingInstruction = 'N' WHERE IsInheritPackingInstruction IS NULL;
ALTER TABLE C_CompensationGroup_Schema ALTER COLUMN IsInheritPackingInstruction SET NOT NULL;

-- AD_Field on tab 541041 (Schema tab)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly)
VALUES (774855, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        541041, 592198, 'Inherit Packing Instruction',
        'If set, the packing instruction from the main article is applied to all sub-articles created from the compensation group schema template.',
        'de.metas.order',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 774855, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 774855
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- AD_UI_Section for tab 541041 (create if not exists)
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, SeqNo, Name)
SELECT 547599, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
       541041, 10, 'main'
WHERE NOT EXISTS (SELECT 1 FROM AD_UI_Section WHERE AD_Tab_ID = 541041 AND IsActive = 'Y');

-- AD_UI_Column
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_UI_Section_ID, SeqNo)
SELECT 549275, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
       (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 541041 AND IsActive = 'Y' ORDER BY SeqNo LIMIT 1),
       10
WHERE NOT EXISTS (SELECT 1 FROM AD_UI_Column uc JOIN AD_UI_Section us ON uc.AD_UI_Section_ID = us.AD_UI_Section_ID WHERE us.AD_Tab_ID = 541041 AND uc.IsActive = 'Y');

-- AD_UI_ElementGroup (flags group)
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                AD_UI_Column_ID, SeqNo, Name)
SELECT 554984, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
       (SELECT uc.AD_UI_Column_ID
        FROM AD_UI_Column uc
                 JOIN AD_UI_Section us ON uc.AD_UI_Section_ID = us.AD_UI_Section_ID
        WHERE us.AD_Tab_ID = 541041 AND uc.IsActive = 'Y'
        ORDER BY us.SeqNo, uc.SeqNo LIMIT 1),
       20, 'flags'
WHERE NOT EXISTS (SELECT 1 FROM AD_UI_ElementGroup eg
                               JOIN AD_UI_Column uc ON eg.AD_UI_Column_ID = uc.AD_UI_Column_ID
                               JOIN AD_UI_Section us ON uc.AD_UI_Section_ID = us.AD_UI_Section_ID
                  WHERE us.AD_Tab_ID = 541041 AND lower(eg.Name) = 'flags');

-- AD_UI_Element for IsInheritPackingInstruction
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_Tab_ID, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name)
VALUES (648505, 0, 0, 'Y', TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-07 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        (SELECT eg.AD_UI_ElementGroup_ID
         FROM AD_UI_ElementGroup eg
                  JOIN AD_UI_Column uc ON eg.AD_UI_Column_ID = uc.AD_UI_Column_ID
                  JOIN AD_UI_Section us ON uc.AD_UI_Section_ID = us.AD_UI_Section_ID
         WHERE us.AD_Tab_ID = 541041 AND lower(eg.Name) = 'flags'
         ORDER BY eg.SeqNo LIMIT 1),
        774855, 541041, 20, 'Y', 'N', 'N', 'Inherit Packing Instruction');
