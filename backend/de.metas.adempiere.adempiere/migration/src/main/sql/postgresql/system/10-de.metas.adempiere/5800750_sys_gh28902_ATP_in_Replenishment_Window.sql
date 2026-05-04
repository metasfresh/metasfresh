-- Source DDL: backend/de.metas.business/src/main/sql/postgresql/ddl/views/M_Material_Needs_Planner_V.sql
-- gh28902 Show ATP in Replenishment Window — https://github.com/metasfresh/me03/issues/28902
-- Run mode: SWING_CLIENT
--
-- Adds a QtyATP column to view M_Material_Needs_Planner_V, sourced from
-- de_metas_material.retrieve_atp_at_date(now()), and exposes it on the core
-- "Wiederauffüllung > Materialbedarf" tab (AD_Window_ID=541869, AD_Tab_ID=547920),
-- placed in the grid directly to the right of "Lagerbestand".

-- =====================================================================
-- 1. AD_Element — "Verfügbare Menge" / "Available Qty" (ColumnName=QtyATP)
-- =====================================================================

INSERT INTO AD_Element
    (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName,
     Created, CreatedBy, EntityType, IsActive,
     Name, PrintName, Updated, UpdatedBy)
VALUES
    (0, 584821 /*From ID Server*/, 0, 'QtyATP',
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0, 'D', 'Y',
     'Verfügbare Menge', 'Verfügbare Menge',
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0)
;

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name,
     PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
     WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584821
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl
   SET Name='Available Qty', PrintName='Available Qty',
       Updated=TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Element_ID=584821 AND AD_Language='en_US'
;

UPDATE AD_Element base
   SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID=base.AD_Element_ID
  AND trl.AD_Language='en_US'
  AND trl.AD_Language=getBaseLanguage()
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584821, 'en_US')
;

-- =====================================================================
-- 2. AD_Column — QtyATP on M_Material_Needs_Planner_V (AD_Table_ID=542466)
-- =====================================================================
-- Mirrors the existing QuantityOnHand AD_Column (589733), with PersonalDataCategory='NP'.
-- AD_Reference_ID=11 (Integer) matches Lagerbestand for visual consistency in the grid.

INSERT INTO AD_Column
    (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID,
     AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy,
     DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
     IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
     IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
     IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
     IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
     IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
     IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons,
     IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated,
     IsUpdateable, IsUseDocSequence, MaxFacetsToFetch,
     Name, PersonalDataCategory, SelectionColumnSeqNo, SeqNo,
     Updated, UpdatedBy, Version)
VALUES
    (0, 592461 /*From ID Server*/, 584821, 0, 11,
     542466, 'XX', 'QtyATP', TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0,
     'N', 'D', 0, 10,
     'Y', 'N', 'Y', 'N',
     'N', 'N', 'N', 'N',
     'N', 'N', 'N', 'N',
     'N', 'N', 'N',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N', 'N',
     'N', 'N', 'N', 'N',
     'N', 'N', 0,
     'Verfügbare Menge', 'NP', 0, 0,
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0, 0)
;

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592461
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- =====================================================================
-- 3. View recreation — adds the new QtyATP column
-- =====================================================================

DROP VIEW IF EXISTS m_material_needs_planner_v$new;

CREATE OR REPLACE VIEW m_material_needs_planner_v$new AS
WITH qty_data AS (SELECT candidate.m_product_id,
                         candidate.m_warehouse_id,
                         COALESCE(SUM(candidate.qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '7 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE)), 0)
                             AS Total_Qty_One_Week_Ago,
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '14 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '7 days'), 0)
                             AS Total_Qty_Two_Weeks_Ago,
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '21 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '14 days'), 0)
                             AS Total_Qty_Three_Weeks_Ago,
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '28 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '21 days'), 0)
                             AS Total_Qty_Four_Weeks_Ago,
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '35 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '28 days'), 0)
                             AS Total_Qty_Five_Weeks_Ago,
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '42 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '35 days'), 0)
                             AS Total_Qty_Six_Weeks_Ago,
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '42 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE)), 0) / 6.0
                             AS Average_Qty_Last_Six_Weeks
                  FROM md_candidate candidate
                  WHERE candidate.md_candidate_type = 'DEMAND'
                    AND candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '42 days'
                    AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE)
                    AND candidate.qty IS NOT NULL
                    AND candidate.qty <> 0
                  GROUP BY candidate.m_product_id, candidate.m_warehouse_id),
     stock_data AS (SELECT m_product_id, m_warehouse_id, SUM(qtyonhand) AS QuantityOnHand FROM md_stock GROUP BY m_product_id, m_warehouse_id),
     atp_data AS (SELECT M_Product_ID, M_Warehouse_ID, SUM(Qty) AS QtyATP
                  FROM de_metas_material.retrieve_atp_at_date(now())
                  GROUP BY M_Product_ID, M_Warehouse_ID),
     replenish_data AS (SELECT m_product_id, m_warehouse_id, level_min, level_max
                        FROM m_replenish
                        WHERE isactive = 'Y'),
     product_warehouse_combinations AS (SELECT DISTINCT m_product_id, m_warehouse_id FROM qty_data
                                        UNION SELECT DISTINCT m_product_id, m_warehouse_id FROM stock_data
                                        UNION SELECT DISTINCT m_product_id, m_warehouse_id FROM atp_data
                                        UNION SELECT DISTINCT m_product_id, m_warehouse_id FROM replenish_data),
     product_hupi AS (SELECT DISTINCT ON (m_product_id) m_product_id, m_hu_pi_item_product_id
                      FROM m_hu_pi_item_product
                      WHERE isactive = 'Y'
                      ORDER BY m_product_id, validfrom DESC, m_hu_pi_item_product_id)
SELECT COALESCE(demand.Total_Qty_One_Week_Ago, 0)            AS Total_Qty_One_Week_Ago,
       COALESCE(demand.Total_Qty_Two_Weeks_Ago, 0)           AS Total_Qty_Two_Weeks_Ago,
       COALESCE(demand.Total_Qty_Three_Weeks_Ago, 0)         AS Total_Qty_Three_Weeks_Ago,
       COALESCE(demand.Total_Qty_Four_Weeks_Ago, 0)          AS Total_Qty_Four_Weeks_Ago,
       COALESCE(demand.Total_Qty_Five_Weeks_Ago, 0)          AS Total_Qty_Five_Weeks_Ago,
       COALESCE(demand.Total_Qty_Six_Weeks_Ago, 0)           AS Total_Qty_Six_Weeks_Ago,
       COALESCE(demand.Average_Qty_Last_Six_Weeks, 0)        AS Average_Qty_Last_Six_Weeks,
       COALESCE(stock.QuantityOnHand, 0)                     AS QuantityOnHand,
       COALESCE(atp.QtyATP, 0)                               AS QtyATP,
       COALESCE(replenish.level_min, 0)                      AS Level_Min,
       COALESCE(replenish.level_max, replenish.level_min, 0) AS Level_Max,
       product.m_product_category_id                         AS M_Product_Category_ID,
       product.m_product_id                                  AS M_Product_ID,
       wc.m_warehouse_id                                     AS M_Warehouse_ID,
       hupi.m_hu_pi_item_product_id                          AS M_HU_PI_Item_Product_ID,
       product.AD_Client_ID,
       product.AD_Org_ID,
       product.Created,
       product.CreatedBy,
       product.Updated,
       product.UpdatedBy,
       product.IsActive,
       product.isbom
FROM m_product product
         LEFT JOIN product_warehouse_combinations wc ON wc.m_product_id = product.m_product_id
         LEFT JOIN replenish_data replenish ON replenish.m_product_id = wc.m_product_id
                                            AND replenish.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN qty_data demand ON demand.m_product_id = wc.m_product_id
                                   AND demand.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN stock_data stock ON stock.m_product_id = wc.m_product_id
                                    AND stock.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN atp_data atp ON atp.m_product_id = wc.m_product_id
                                AND atp.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN product_hupi hupi ON hupi.m_product_id = product.m_product_id
WHERE product.isactive = 'Y'
  AND product.isstocked = 'Y'
;

SELECT db_alter_view(
    'm_material_needs_planner_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('m_material_needs_planner_v$new'))
);

DROP VIEW IF EXISTS m_material_needs_planner_v$new;

-- =====================================================================
-- 4. AD_Field — "Verfügbare Menge" on tab 547920 (Materialbedarf)
-- =====================================================================
-- Mirrors AD_Field 740338 (Lagerbestand) on the same tab.

INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, DisplayLength, EntityType,
     IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
     IsHeading, IsReadOnly, IsSameLine,
     Name, Updated, UpdatedBy)
VALUES
    (0, 592461, 778036 /*From ID Server*/, 0, 547920,
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0, 10, 'D',
     'Y', 'N', 'N', 'N', 'N',
     'N', 'N', 'N',
     'Verfügbare Menge',
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0)
;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 778036
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(584821)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 778036
;
SELECT AD_Element_Link_Create_Missing_Field(778036)
;

-- Set en_US labels on Column_Trl and Field_Trl. The propagation function does
-- not overwrite trl rows that match the base name, so we set them explicitly.
UPDATE AD_Column_Trl SET Name='Available Qty',
       Updated=TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Column_ID=592461 AND AD_Language='en_US'
;
UPDATE AD_Field_Trl SET Name='Available Qty',
       Updated=TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=0
WHERE AD_Field_ID=778036 AND AD_Language='en_US'
;

-- =====================================================================
-- 5. AD_UI_Element — place in grid between Lagerbestand (SeqNoGrid=110)
--                    and Level_Min (SeqNoGrid=120)
-- =====================================================================
-- Mirrors AD_UI_Element 630644 (Lagerbestand). Group 552634 = main grid section.

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Element_ID,
     AD_UI_ElementGroup_ID, AD_UI_ElementType,
     Created, CreatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList,
     IsDisplayedGrid, IsMultiLine,
     MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid,
     Updated, UpdatedBy)
VALUES
    (0, 778036, 0, 547920, 650476 /*From ID Server*/,
     552634, 'F',
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0,
     'Y', 'N', 'N', 'Y', 'N',
     'Y', 'N',
     0, 'Verfügbare Menge', 105, 0, 115,
     TO_TIMESTAMP('2026-05-04 12:00','YYYY-MM-DD HH24:MI'), 0)
;

-- Backfill any missing _Trl rows
SELECT add_missing_translations()
;
