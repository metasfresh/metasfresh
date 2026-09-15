-- Add LotNumberCode field to the Resource window (AD_Window_ID=236, AD_Tab_ID=414)
-- AD_Column_ID=592876, AD_Element_ID=585043
-- AD_Field_ID=781245, AD_UI_Element_ID=652364  (all from ID server)

-- =============================================================================
-- Step 1: Add Description + Help to AD_Element_Trl (de_DE, de_CH, en_US)
--         so users see a meaningful tooltip on the field.
-- =============================================================================
UPDATE AD_Element_Trl
SET    Description  = 'Code dieser Ressource, der in generierte Lot-Nummern eingebettet wird.',
       Help         = 'Code dieser Ressource, der in generierte Lot-Nummern eingebettet wird.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-06-23 08:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID = 585043
  AND  AD_Language   = 'de_DE';

UPDATE AD_Element_Trl
SET    Description  = 'Code dieser Ressource, der in generierte Lot-Nummern eingebettet wird.',
       Help         = 'Code dieser Ressource, der in generierte Lot-Nummern eingebettet wird.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-06-23 08:00:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID = 585043
  AND  AD_Language   = 'de_CH';

UPDATE AD_Element_Trl
SET    Description  = 'Code of this resource, embedded into generated lot numbers.',
       Help         = 'Code of this resource, embedded into generated lot numbers.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-06-23 08:00:03', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID = 585043
  AND  AD_Language   = 'en_US';

-- Also set Description/Help on the base AD_Element (base language = de_DE)
UPDATE AD_Element
SET    Description  = 'Code dieser Ressource, der in generierte Lot-Nummern eingebettet wird.',
       Help         = 'Code dieser Ressource, der in generierte Lot-Nummern eingebettet wird.',
       Updated      = TO_TIMESTAMP('2026-06-23 08:00:04', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID = 585043;

-- Propagate element TRL changes to all dependent translation tables
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585043);

-- =============================================================================
-- Step 2: Create AD_Field for LotNumberCode on tab 414 (main S_Resource tab)
-- Placement: primary group (SeqNo=80, after Arbeitsplatz at 70)
--            visible in grid at SeqNoGrid=25 (between Name=20 and Beschreibung=70)
-- Not mandatory, editable.
-- =============================================================================
INSERT INTO AD_Field
  (AD_Field_ID, AD_Client_ID, AD_Org_ID,
   IsActive, Created, CreatedBy, Updated, UpdatedBy,
   Name, AD_Tab_ID, AD_Column_ID,
   IsDisplayed, DisplayLength, SeqNo,
   IsReadOnly, IsMandatory, IsFieldOnly, IsHeading,
   IsDisplayedGrid, SeqNoGrid,
   EntityType)
VALUES
  (781245 /*From ID Server*/, 0, 0,
   'Y',
   TO_TIMESTAMP('2026-06-23 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-06-23 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   'Lot-Nummer Code', 414, 592876,
   'Y', 10, 80,
   'N', 'N', 'N', 'N',
   'Y', 25,
   'D');

-- =============================================================================
-- Step 3: Seed AD_Field_Trl skeleton rows for all active system languages
-- =============================================================================
INSERT INTO AD_Field_Trl
  (AD_Language, AD_Field_ID, Name, IsTranslated,
   AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-23 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-23 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'Y'
FROM   AD_Language l, AD_Field t
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND  t.AD_Field_ID = 781245
  AND  NOT EXISTS (
         SELECT 1 FROM AD_Field_Trl tt
         WHERE  tt.AD_Language = l.AD_Language
           AND  tt.AD_Field_ID = t.AD_Field_ID
       );

-- =============================================================================
-- Step 4: Propagate element translations → field translations
--         (uses AD_Element_ID=585043, not the field ID)
-- =============================================================================
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585043);

-- =============================================================================
-- Step 5: Rebuild AD_Element_Link for the new field
-- =============================================================================
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781245;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781245);

-- =============================================================================
-- Step 6: Create AD_UI_Element — places the field in the primary group
--         of the left column of tab 414.
--         Group 543924 = 'default' (UIStyle='primary'), left column of section 542062.
--         SeqNo=80 (form view, after Arbeitsplatz=70)
--         SeqNoGrid=25 (grid view, between Name=20 and Beschreibung=70)
-- =============================================================================
INSERT INTO AD_UI_Element
  (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID,
   IsActive, Created, CreatedBy, Updated, UpdatedBy,
   AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
   AD_UI_ElementType,
   Name,
   SeqNo, SeqNoGrid, SeqNo_SideList,
   IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
   IsAdvancedField, WidgetSize)
VALUES
  (652364 /*From ID Server*/, 0, 0,
   'Y',
   TO_TIMESTAMP('2026-06-23 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-06-23 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   414, 781245, 543924,
   'F',
   'LotNumberCode',
   80, 25, 0,
   'Y', 'Y', 'N',
   'N', 'S');   -- narrow widget: 10-char code field
