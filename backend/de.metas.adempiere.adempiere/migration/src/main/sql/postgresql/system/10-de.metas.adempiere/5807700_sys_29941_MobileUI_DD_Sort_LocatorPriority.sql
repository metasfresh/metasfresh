-- Add LocatorPriority entry to AD_Reference MobileUI_UserProfile_DD_Sort_Field
-- (used by AD_Column MobileUI_UserProfile_DD_Sort.FieldName, AD_Reference_Value_ID 542026).
-- Backs the new DistributionJobSortingField.LocatorPriority enum value.
-- See https://github.com/metasfresh/me03/issues/29941

-- IDs from idserver.metas.de:
--   AD_Ref_List 544265

-- 1. INSERT AD_Ref_List
INSERT INTO AD_Ref_List
	(AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES
	(544265 /*From ID Server*/, 0, 0, 'Y',
	 TO_TIMESTAMP('2026-06-15 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	 TO_TIMESTAMP('2026-06-15 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	 542026, 'LocatorPriority', 'LocatorPriority', 'Lagerort An Priorität', 'D')
;

-- 2. Skeleton AD_Ref_List_Trl rows for all active system languages (inherit parent's Updated)
INSERT INTO AD_Ref_List_Trl
	(AD_Ref_List_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 Name, Description, IsTranslated)
SELECT
	t.AD_Ref_List_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
	t.Name, t.Description, 'N'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544265
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 3. English translation (timestamp strictly later than parent INSERT)
UPDATE AD_Ref_List_Trl
SET Name='Locator to Priority',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-15 22:00:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Ref_List_ID=544265 AND AD_Language='en_US'
;
