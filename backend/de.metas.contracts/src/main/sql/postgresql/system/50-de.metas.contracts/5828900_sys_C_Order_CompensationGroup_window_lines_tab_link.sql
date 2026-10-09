-- Order compensation group window (542196): bind the order-lines sub-tab (549518) to its group by
-- C_OrderLine.C_Order_CompensationGroup_ID.
--
-- 5828830 set only AD_Tab.Parent_Column_ID to the child FK. The WebUI takes Parent_Column_ID as the PARENT tab's
-- link column (falling back to the parent key) and takes the CHILD link column from AD_Tab.AD_Column_ID, or else
-- from the child's IsParent columns - for C_OrderLine that is C_Order_ID. So the tab listed the lines of the order
-- whose C_Order_ID equals the group id. Now: AD_Column_ID = the child FK (it needs a field on the tab, added here
-- hidden), Parent_Column_ID empty (= the group's key).
--
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_MigrationScript 5828900 (this script)
--   AD_Field 785681 (C_OrderLine.C_Order_CompensationGroup_ID on tab 549518, hidden)

INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 557744, 785681 /*From ID Server*/, 0, 549518, TO_TIMESTAMP('2026-10-09 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Auftrag Kompensationsgruppe', 0, 0, TO_TIMESTAMP('2026-10-09 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785681
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543469 /*C_Order_CompensationGroup_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785681
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785681)
;

UPDATE AD_Tab
SET AD_Column_ID     = 557744 /* C_OrderLine.C_Order_CompensationGroup_ID */,
    Parent_Column_ID = NULL,
    Updated          = TO_TIMESTAMP('2026-10-09 15:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy        = 100
WHERE AD_Tab_ID = 549518
;
