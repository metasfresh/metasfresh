-- 2026-04-30 https://github.com/metasfresh/me03/issues/29369
-- AD_Field rows for ReferenceDate and IsPaid on tabs 548449 and 548450

INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Name_ID, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, SeqNo, SeqNoGrid, SortNo, EntityType)
  VALUES
  (777476, 548449, 592457, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0, NULL, 'Y', 'Y', 'Y', 'N', 30, 30, 0, 'D'),
  (777477, 548450, 592457, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0, NULL, 'Y', 'Y', 'Y', 'N', 30, 30, 0, 'D'),
  (777478, 548449, 592458, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0, NULL, 'Y', 'Y', 'Y', 'N', 35, 35, 0, 'D'),
  (777479, 548450, 592458, 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0, NULL, 'Y', 'Y', 'Y', 'N', 35, 35, 0, 'D');

INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, IsTranslated)
  SELECT f.AD_Field_ID, l.AD_Language, f.AD_Client_ID, f.AD_Org_ID, f.IsActive, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy,
         c.Name, c.Description, NULL, 'N'
    FROM AD_Field f
    JOIN AD_Column c ON f.AD_Column_ID = c.AD_Column_ID
    JOIN AD_Language l ON l.IsSystemLanguage = 'Y'
   WHERE f.AD_Field_ID IN (777476, 777477, 777478, 777479)
     AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl WHERE AD_Field_ID = f.AD_Field_ID AND AD_Language = l.AD_Language);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584813);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1402); -- existing IsPaid element
