-- Factoring OP-Liste: Expose FactoringContractNo + FactoringClientAccountId on C_BPartner window
-- Visible only when IsFactorer='Y'
-- Placement: adjacent to IsFactorer field (AD_Field_ID=761012, UI element in group 540671 at seqno 170)
-- Core window: AD_Window_ID=123 "Geschäftspartner", AD_Tab_ID=220

-- IDs allocated from idserver.metas.de on 2026-07-22:
--   AD_MigrationScript  5815550
--   AD_Field            781768 (FactoringContractNo)
--   AD_Field            781769 (FactoringClientAccountId)
--   AD_UI_Element       652700 (FactoringContractNo)
--   AD_UI_Element       652701 (FactoringClientAccountId)

-- =============================================================================
-- 1. AD_Field for FactoringContractNo on C_BPartner main tab (220)
-- =============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Column_ID, AD_Tab_ID,
                      Created, CreatedBy, Updated, UpdatedBy, IsActive,
                      EntityType, DisplayLength, DisplayLogic,
                      IsDisplayed, IsDisplayedGrid,
                      IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid)
VALUES (0, 0, 781768 /*From ID Server*/, 592972, 220,
        TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'D', 20, '@IsFactorer@=''Y''',
        'Y', 'N',
        'N', 'N', 'N', 'N', 'N',
        10, 0)
;

-- Skeleton Trl rows for FactoringContractNo field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, et.Description, et.Help, et.Name, et.IsTranslated,
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l
JOIN AD_Field t ON t.AD_Field_ID = 781768
JOIN AD_Column c ON c.AD_Column_ID = t.AD_Column_ID
JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID AND et.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- Propagate element translations for FactoringContractNo
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585119)
;

-- Wire up element-link for FactoringContractNo field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781768
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781768)
;

-- =============================================================================
-- 2. AD_Field for FactoringClientAccountId on C_BPartner main tab (220)
-- =============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Column_ID, AD_Tab_ID,
                      Created, CreatedBy, Updated, UpdatedBy, IsActive,
                      EntityType, DisplayLength, DisplayLogic,
                      IsDisplayed, IsDisplayedGrid,
                      IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid)
VALUES (0, 0, 781769 /*From ID Server*/, 592973, 220,
        TO_TIMESTAMP('2026-07-22 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-22 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'D', 20, '@IsFactorer@=''Y''',
        'Y', 'N',
        'N', 'N', 'N', 'N', 'N',
        20, 0)
;

-- Skeleton Trl rows for FactoringClientAccountId field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, et.Description, et.Help, et.Name, et.IsTranslated,
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l
JOIN AD_Field t ON t.AD_Field_ID = 781769
JOIN AD_Column c ON c.AD_Column_ID = t.AD_Column_ID
JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID AND et.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- Propagate element translations for FactoringClientAccountId
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585120)
;

-- Wire up element-link for FactoringClientAccountId field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781769
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781769)
;

-- =============================================================================
-- 3. AD_UI_Element for FactoringContractNo (group 540671, seqno 180)
-- =============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID,
                           AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
                           Created, CreatedBy, Updated, UpdatedBy, IsActive,
                           Name,
                           IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES (0, 0, 652700 /*From ID Server*/,
        781768,
        220,
        540671,
        'F',
        TO_TIMESTAMP('2026-07-22 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-22 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'Vertragsnummer',
        'N',
        'Y', 'N', 'N',
        180, 0, 0)
;

-- =============================================================================
-- 4. AD_UI_Element for FactoringClientAccountId (group 540671, seqno 190)
-- =============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID,
                           AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
                           Created, CreatedBy, Updated, UpdatedBy, IsActive,
                           Name,
                           IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES (0, 0, 652701 /*From ID Server*/,
        781769,
        220,
        540671,
        'F',
        TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'Kundenkontonummer',
        'N',
        'Y', 'N', 'N',
        190, 0, 0)
;

-- =============================================================================
-- 5. Propagate translations for the new AD_Elements
-- =============================================================================
SELECT add_missing_translations();
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585119);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585120);
