INSERT INTO ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help, po_name, po_printname, po_description, po_help, widgetsize, commitwarning, webui_namebrowse, webui_namenewbreadcrumb, webui_namenew)
VALUES (576062, 0, 0, 'Y', '2019-01-28 13:01:12.000000 +01:00', 100, '2019-01-28 13:01:28.000000 +01:00', 100, 'TechnicalDescription', 'D', 'Sachbezeichnung', 'Sachbezeichnung', 'Technische Bezeichnung des Produktes', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
ON CONFLICT DO NOTHING
;


-- 2019-04-02T12:18:05.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 576062
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
ON CONFLICT DO NOTHING
;


INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 585430, 576062, 0, 10, 208, 'TechnicalDescription', TO_TIMESTAMP('2022-12-22 16:48:49', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'Technische Bezeichnung des Produktes', 'D', 0, 2000, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Sachbezeichnung', 0, 0,
        TO_TIMESTAMP('2022-12-22 16:48:49', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
ON CONFLICT DO NOTHING
;

-- 2022-12-22T15:48:50.209Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 585430
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2022-12-22T15:48:50.231Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(576062)
;

-- 2022-12-22T15:49:03.602Z
/* DDL */


DO
$$
    BEGIN
        IF NOT EXISTS(SELECT column_name
                      FROM information_schema.columns
                      WHERE table_schema = 'public'
                        AND UPPER(table_name) = UPPER('m_product')
                        AND UPPER(column_name) = UPPER('TechnicalDescription')) THEN
            ALTER TABLE M_Product
                ADD COLUMN TechnicalDescription VARCHAR(2000)
            ;
        END IF;
    END
$$
;



-- Field: Produkt -> Produkt -> Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- Field: Produkt(140,D) -> Produkt(180,D) -> Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- 2022-12-22T16:58:47.473Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0,
        (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208),
        NEXTVAL('ad_field_seq'),
        0,
        180,
        0,
        TO_TIMESTAMP('2022-12-22 17:58:47', 'YYYY-MM-DD HH24:MI:SS'),
        100,
        'Technische Bezeichnung des Produktes',
        0,
        'D',
        0,
        'Y',
        'Y',
        'Y',
        'N',
        'N',
        'N',
        'N',
        'N',
        'Sachbezeichnung',
        0,
        600,
        0,
        1,
        1,
        TO_TIMESTAMP('2022-12-22 17:58:47', 'YYYY-MM-DD HH24:MI:SS'),
        100)
ON CONFLICT DO NOTHING
;

-- FROM AD_Field
-- WHERE NOT EXISTS(SELECT 1 FROM ad_field WHERE ad_tab_id = 180 AND ad_column_id = (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208))
-- ;

-- 2022-12-22T16:58:47.474Z
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
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = (SELECT ad_field_id FROM ad_field WHERE ad_tab_id = 180 AND ad_column_id = (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208))
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2022-12-22T16:58:47.489Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(576062)
;

-- 2022-12-22T16:58:47.497Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = (SELECT ad_field_id FROM ad_field WHERE ad_tab_id = 180 AND ad_column_id = (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208))
;

-- 2022-12-22T16:58:47.498Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field((SELECT ad_field_id FROM ad_field WHERE ad_tab_id = 180 AND ad_column_id = (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208)))
;


-- Field: Produkt -> Produkt -> Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- Field: Produkt(140,D) -> Produkt(180,D) -> Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- 2022-12-22T16:58:53.814Z
UPDATE AD_Field
SET IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2022-12-22 17:58:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = (SELECT ad_field_id FROM ad_field WHERE ad_tab_id = 180 AND ad_column_id = (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208))
;

-- UI Element: Produkt -> Produkt.Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- UI Element: Produkt(140,D) -> Produkt(180,D) -> advanced edit -> 10 -> warehouse.Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- 2022-12-22T16:59:26.386Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0,
        (SELECT ad_field_id FROM ad_field WHERE ad_tab_id = 180 AND ad_column_id = (SELECT ad_column_id FROM ad_column WHERE columnname = 'TechnicalDescription' AND ad_table_id = 208)),
        0,
        180,
        1000040,
        614577,
        'F',
        TO_TIMESTAMP('2022-12-22 17:59:26', 'YYYY-MM-DD HH24:MI:SS'),
        100,
        'Technische Bezeichnung des Produktes',
        'Y',
        'N',
        'N',
        'Y',
        'N',
        'N',
        'N',
        0,
        'Sachbezeichnung',
        155,
        0,
        0,
        TO_TIMESTAMP('2022-12-22 17:59:26', 'YYYY-MM-DD HH24:MI:SS'),
        100)
ON CONFLICT DO NOTHING
;


-- UI Element: Produkt -> Produkt.Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- UI Element: Produkt(140,D) -> Produkt(180,D) -> advanced edit -> 10 -> warehouse.Sachbezeichnung
-- Column: M_Product.TechnicalDescription
-- 2022-12-22T17:10:56.896Z
UPDATE AD_UI_Element
SET IsAdvancedField='Y', Updated=TO_TIMESTAMP('2022-12-22 18:10:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614577
;

-- Column: M_Product.TechnicalDescription
-- Column: M_Product.TechnicalDescription
-- 2022-12-22T17:13:52.727Z
UPDATE AD_Column
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2022-12-22 18:13:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 585430
;
