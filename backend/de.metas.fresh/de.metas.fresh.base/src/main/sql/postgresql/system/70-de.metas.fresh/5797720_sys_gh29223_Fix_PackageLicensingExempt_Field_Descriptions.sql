-- Fix missing de_DE field descriptions for PackageLicensingExempt fields.
-- AD_Field.Description was not set in the original migration, so de_DE tooltips were empty.

-- Set AD_Field.Description from AD_Element (base language = de_DE)
UPDATE AD_Field f
SET Description = e.Description,
    Help = e.Help,
    Updated = '2026-04-09 16:00',
    UpdatedBy = 0
FROM AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
WHERE f.AD_Column_ID = c.AD_Column_ID
  AND f.AD_Field_ID IN (777056, 777057, 777058, 777059, 777060, 777061)
  AND (f.Description IS NULL OR f.Description = '');

-- Propagate de_DE/de_CH descriptions to AD_Field_Trl
UPDATE AD_Field_Trl
SET Description = sub.elem_desc,
    Help = sub.elem_help,
    Updated = '2026-04-09 16:00',
    UpdatedBy = 0
FROM (
    SELECT f.AD_Field_ID, et.AD_Language, et.Description as elem_desc, et.Help as elem_help
    FROM AD_Field f
    JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
    JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
    WHERE f.AD_Field_ID IN (777056, 777057, 777058, 777059, 777060, 777061)
      AND et.AD_Language IN ('de_DE', 'de_CH')
) sub
WHERE AD_Field_Trl.AD_Field_ID = sub.AD_Field_ID
  AND AD_Field_Trl.AD_Language = sub.AD_Language
  AND (AD_Field_Trl.Description IS NULL OR AD_Field_Trl.Description = '');
