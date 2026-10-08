-- Boni: show C_Flatrate_RefundConfig.IsDeductedAtPayment in window 540113 (Vertragsbedingungen), tab 541106 (Rückvergütung),
-- form: between "Rückvergütung per" and "Terminplan Rechnung"; grid: after the bonus product.
--
-- IDs allocated from idserver.metas.de on 2026-10-06:
--   AD_Field 785611, AD_UI_Element 654938

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785611 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'Y', 'Y', 53, 37, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Column_ID = 593711 -- C_Flatrate_RefundConfig.IsDeductedAtPayment
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, f.Description, f.Help, 'N',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785611
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585516)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785611
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(785611)
;

-- existing element group 541612; form between "Rückvergütung per" (80) and "Terminplan Rechnung" (90), grid after the bonus product (14)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654938 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541106, 541612, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 87, 17, 0, 'M'
FROM AD_Field f
WHERE f.AD_Field_ID = 785611
;
