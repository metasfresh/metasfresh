-- Add WorkStation_ID to PP_Product_Planning

-- Column
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID,
                       AD_Val_Rule_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo,
                       FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete,
                       IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy,
                       Version)
VALUES (0, 592801, 583018, 0, 30, 541855,
        53020, 540669, 'XX', 'WorkStation_ID',
        TO_TIMESTAMP('2026-06-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99, 'N',
        'D', 0, 10, 'Y', 'N', 'Y',
        'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Arbeitsstation', 0, 0, TO_TIMESTAMP('2026-06-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        99, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.CreatedBy,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Column_ID = 592801
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(583018)
;

-- DDL
SELECT public.db_alter_table('PP_Product_Planning', 'ALTER TABLE public.PP_Product_Planning ADD COLUMN WorkStation_ID NUMERIC(10)')
;

ALTER TABLE PP_Product_Planning
    ADD CONSTRAINT WorkStation_PPProductPlanning FOREIGN KEY (WorkStation_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;

-- Field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592801, 780755, 0, 542102, TO_TIMESTAMP('2026-06-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Arbeitsstation', TO_TIMESTAMP('2026-06-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99)
;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.CreatedBy,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780755
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(583018)
;

DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 780755
;

SELECT AD_Element_Link_Create_Missing_Field(780755)
;

-- UI Element: SeqNo 55 (between Produktionsstätte=50 and Planer=70), group 543142
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780755, 0, 542102, 543142, 652050, 'F', TO_TIMESTAMP('2026-06-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99, 'Y', 'N', 'Y', 'N', 'N', 'Arbeitsstation', 55, 0, 0, TO_TIMESTAMP('2026-06-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99)
;
