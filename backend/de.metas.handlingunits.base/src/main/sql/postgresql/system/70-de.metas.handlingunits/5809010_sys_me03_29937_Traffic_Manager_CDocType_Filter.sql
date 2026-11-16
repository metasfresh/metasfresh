-- Source DDL: backend/de.metas.handlingunits.base/src/main/sql/postgresql/ddl/views/M_Picking_Job_Schedule_view.sql
-- Run mode: SWING_CLIENT

-- IDs from idserver.metas.de on 2026-06-19:
--   AD_Column_ID  592858  (C_DocType_ID on M_Picking_Job_Schedule_view / AD_Table_ID=542514)
--   AD_Field_ID   781230  (field in Traffic Management tab 548377)
--   AD_UI_Element_ID 652340

-- Step 1: Extend view DDL to expose C_DocType_ID
-- 2026-06-19T09:00:00.000Z
DROP VIEW IF EXISTS M_Picking_Job_Schedule_view$new
;

CREATE OR REPLACE VIEW M_Picking_Job_Schedule_view$new AS
WITH base_schedule AS (SELECT s.m_shipmentschedule_id,

                              s.c_bpartner_customer_id,
                              s.c_bpartner_location_id,
                              s.bpartnerlocationname,
                              s.bpartneraddress_override,
                              s.c_orderso_id,
                              s.poreference,
                              s.handover_partner_id,
                              s.handover_location_id,
                              s.setup_place_no,
                              s.dateordered,
                              s.c_orderlineso_id,
                              s.linenetamt,
                              s.c_currency_id,
                              s.m_warehouse_id,
                              s.preparationdate,
                              s.m_product_id,
                              s.c_uom_id,
                              s.m_attributesetinstance_id,
                              s.qtyordered,
                              s.qtytodeliver,
                              s.qtydelivered,
                              s.qtypickedanddelivered,
                              s.qtypickednotdelivered,
                              s.qtypickedplanned,
                              s.qtypickedordelivered,
                              s.qtyonhand,
                              s.qtyscheduledforpicking,
                              s.qtytodeliver - COALESCE(s.qtyscheduledforpicking, 0) AS QtyToScheduleForPicking,
                              s.qtyscheduledforpickingofprocessed,

                              s.deliveryviarule,
                              s.deliverydate,
                              s.priorityrule,
                              s.iscatchweight,
                              s.catch_uom_id,
                              s.m_shipper_id,
                              s.packto_hu_pi_item_product_id,
                              s.datepromised,
                              s.isfixeddatepromised,
                              s.isfixedpreparationdate,

                              s.ad_client_id,
                              s.ad_org_id,
                              s.created,
                              s.createdby,
                              s.updated,
                              s.updatedby,
                              s.isactive,

                              s.carrier_advising_status,
                              s.carrier_product_id,
                              s.carrier_goods_type_id,
                              (SELECT c_doctype_id FROM c_order WHERE c_order_id = s.c_orderso_id) AS c_doctype_id
                       FROM m_packageable_v s
                       WHERE s.carrier_product_id > 0
                          OR get_sysconfig_value('de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet') = 'N')

-- Real picking job rows
SELECT b.*,
       j.m_picking_job_schedule_id, -- Composed Key together with m_shipmentschedule_id
       j.qtytopick,
       (SELECT COALESCE(SUM(sqp.qtypicked), 0)
        FROM m_shipmentschedule_qtypicked sqp
        WHERE sqp.m_picking_job_schedule_id = j.m_picking_job_schedule_id
          AND sqp.processed = 'Y') AS qtypicked,
       j.c_workplace_id,
       j.processed,
       'N'                         AS isreschedule,
       'Y'                         AS isassigned
FROM base_schedule b
         JOIN M_Picking_Job_Schedule j ON j.m_shipmentschedule_id = b.m_shipmentschedule_id

UNION ALL

-- Synthetic "to be scheduled" row
SELECT b.*,
       0    AS m_picking_job_schedule_id, -- Composed Key together with m_shipmentschedule_id
       NULL AS qtytopick,
       NULL AS qtypicked,
       NULL AS c_workplace_id,
       'N'  AS processed,
       CASE
           WHEN (b.qtydelivered <> b.qtyscheduledforpickingofprocessed) THEN 'Y'
                                                                        ELSE 'N'
       END  AS isreschedule,
       'N'  AS isassigned
FROM base_schedule b
WHERE b.qtytoscheduleforpicking > 0
;

SELECT db_alter_view(
    'M_Picking_Job_Schedule_view',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('M_Picking_Job_Schedule_view$new'))
)
;

DROP VIEW IF EXISTS M_Picking_Job_Schedule_view$new
;

-- Step 2: Val rule — limit C_DocType_ID filter to sales order doc types
-- AD_Val_Rule_ID 540791 (From ID Server)
-- 2026-06-19T09:00:00.500Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540791 /*From ID Server*/,'C_DocType.DocBaseType=''SOO'' AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',TO_TIMESTAMP('2026-06-19 09:00:00.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','C_DocType SO (Traffic Manager)','S',TO_TIMESTAMP('2026-06-19 09:00:00.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Step 3: Register C_DocType_ID column in AD
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-06-19T09:00:01.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592858 /*From ID Server*/,196,0,19,542514,540791 /*From ID Server*/,'XX','C_DocType_ID',TO_TIMESTAMP('2026-06-19 09:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Belegart oder Verarbeitungsvorgaben','de.metas.handlingunits',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Belegart','NP',0,0,TO_TIMESTAMP('2026-06-19 09:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-19T09:00:01.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592858 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-19T09:00:01.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- Step 4: Make C_DocType_ID a filter column
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-06-19T09:00:02.000Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-06-19 09:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592858 /*From ID Server*/
;

-- Step 5: Add field to Traffic Management window → Kommissionierplan tab (548377)
-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Belegart
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-06-19T09:00:03.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592858 /*From ID Server*/,781230 /*From ID Server*/,0,548377,0,TO_TIMESTAMP('2026-06-19 09:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.handlingunits',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Belegart',0,0,55,0,1,1,TO_TIMESTAMP('2026-06-19 09:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-19T09:00:03.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-19T09:00:03.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2026-06-19T09:00:03.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781230
;

-- 2026-06-19T09:00:03.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781230)
;

-- Step 6: Place field in UI — "sales order" element group (553423) in tab 548377
-- UI Element: Traffic Management(541929) -> Kommissionierplan(548377) -> primary -> 10 -> sales order.Belegart
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-06-19T09:00:04.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781230 /*From ID Server*/,0,548377,553423,652340 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-19 09:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Belegart',80,55,0,TO_TIMESTAMP('2026-06-19 09:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
