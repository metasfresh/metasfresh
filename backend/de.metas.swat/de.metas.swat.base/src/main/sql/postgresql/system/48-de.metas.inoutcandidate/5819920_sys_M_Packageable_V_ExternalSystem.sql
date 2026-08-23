-- Run mode: SWING_CLIENT

-- Exposes M_ShipmentSchedule.ExternalSystem_ID on M_Packageable_V, so the MobileUI picking launcher
-- can display and filter by the order's external system.
--
-- The value is already on the shipment schedule: OrderLineShipmentScheduleHandler copies
-- C_Order.ExternalSystem_ID onto the schedule at creation. Unlike PreparationDate / DeliveryDate /
-- PriorityRule further down, ExternalSystem_ID has no *_Override sibling on M_ShipmentSchedule, so
-- it is selected plain rather than through a COALESCE.
--
-- The view body below is 5819530_sys_gh31577_M_Packageable_v.sql (the current definition) with that
-- single column added in the "Shipment schedule" block -- nothing else differs. Basing it on an
-- older revision would silently drop the columns added since.
--
-- IDs allocated from idserver.metas.de on 2026-08-23:
--   AD_Column 593394 (M_Packageable_V.ExternalSystem_ID)

-- Column: M_Packageable_V.ExternalSystem_ID
-- Reuses AD_Element 583968 ("Externes System"), the element behind every ExternalSystem_ID column.
-- EntityType matches the table's own (de.metas.inoutcandidate), so GenerateModel picks it up without
-- IsForceIncludeInGeneratedModel. DDL_NoForeignKey='Y' because M_Packageable_V is a view.
-- 2026-08-23T10:00:04.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593394 /*From ID Server*/,583968,0,30,540823,'XX','ExternalSystem_ID',TO_TIMESTAMP('2026-08-23 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2026-08-23 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-08-23T10:00:04.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593394 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-23T10:00:04.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP VIEW IF EXISTS m_packageable_v$new
;

CREATE OR REPLACE VIEW m_packageable_v$new AS
SELECT p.*

     -- note: keep in sync with de.metas.picking.api.Packageable.getQtyPickedOrDelivered()
     , p.QtyDelivered + p.QtyPickedNotDelivered + p.QtyPickedPlanned AS QtyPickedOrDelivered
FROM (SELECT
          --
          -- BPartner
          p.C_BPartner_ID                                                         AS C_BPartner_Customer_ID,
          p.Value                                                                 AS BPartnerValue,
          (COALESCE(p.Name, '') || COALESCE(p.Name2, ''))                         AS BPartnerName,

          --
          -- BPartner location
          bpl.C_BPartner_Location_ID,
          bpl.Name                                                                AS BPartnerLocationName,
          (CASE
               WHEN s.BPartnerAddress_Override IS NOT NULL AND s.BPartnerAddress_Override != ''
                   THEN s.BPartnerAddress_Override
                   ELSE s.BPartnerAddress
           END)                                                                   AS BPartnerAddress_Override,

          --
          -- Order Info
          s.C_Order_ID                                                            AS C_OrderSO_ID,
          o.DocumentNo                                                            AS OrderDocumentNo,
          o.poreference,
          o.FreightCostRule,
          COALESCE(
                  (CASE WHEN o.IsUseHandOver_Location = 'Y' THEN o.HandOver_Location_ID ELSE o.C_BPartner_Location_ID END),
                  o.C_BPartner_Location_ID
          )                                                                       AS HandOver_Location_ID,
          COALESCE(
                  (CASE WHEN o.IsUseHandOver_Location = 'Y' THEN o.Handover_Partner_ID ELSE o.C_BPartner_ID END),
                  o.C_BPartner_ID
          )                                                                       AS HandOver_Partner_ID,
          cast_to_numeric_or_null(bpl.Setup_Place_No)                             AS Setup_Place_No,
          dt.DocSubType,
          s.DateOrdered,
          s.C_OrderLine_ID                                                        AS C_OrderLineSO_ID,
          ol.LineNetAmt,
          ol.C_Currency_ID,

          --
          -- Warehouse
          w.M_Warehouse_ID,
          w.Name                                                                  AS WarehouseName,
          w.M_Warehouse_Type_ID,

          --
          -- Shipment schedule
          s.M_ShipmentSchedule_ID,
          s.IsDisplayed,
          COALESCE(s.PreparationDate_Override, s.PreparationDate)                 AS PreparationDate,
          s.ExternalSystem_ID,
          s.ShipmentAllocation_BestBefore_Policy,
          -- 'Y' if there is at least one not-yet-processed picked qty already bound to a (draft) shipment line:
          (CASE
               WHEN EXISTS (SELECT 1
                            FROM M_ShipmentSchedule_QtyPicked sqp
                            WHERE sqp.M_ShipmentSchedule_ID = s.M_ShipmentSchedule_ID
                              AND sqp.IsActive = 'Y'
                              AND sqp.Processed = 'N'
                              AND sqp.M_InOutLine_ID IS NOT NULL)
                   THEN 'Y'
                   ELSE 'N'
           END)                                                                   AS IsPickQtyOnDraftShipment,

          --
          -- Product & ASI
          s.M_Product_ID,
          prod.Value                                                              AS ProductValue,
          prod.Name                                                               AS ProductName,
          prod.C_UOM_ID                                                           AS C_UOM_ID, -- shipment schedule's UOM (see de.metas.inoutcandidate.api.impl.ShipmentScheduleBL.getC_UOM); IMPORTANT: before changing it, check bellow, we might use this logic to convert some Qtys to shipment schedule's UOM
          s.M_AttributeSetInstance_ID,

          --
          -- Quantities (in shipment schedule's UOM, i.e. Product's UOM)
          s.QtyOrdered                                                            AS QtyOrdered,
          COALESCE(s.QtyToDeliver_Override, s.QtyToDeliver)                       AS QtyToDeliver,
          COALESCE(s.QtyDelivered, 0)                                             AS QtyDelivered,
          --
          (SELECT COALESCE(SUM(sqp.QtyPicked), 0)
           FROM M_ShipmentSchedule_QtyPicked sqp
           WHERE sqp.M_ShipmentSchedule_ID = s.M_ShipmentSchedule_ID
             AND sqp.IsActive = 'Y'
             AND sqp.Processed = 'Y')                                             AS QtyPickedAndDelivered,
          -- QtyPicked but not yet delivered:
          (SELECT COALESCE(SUM(sqp.QtyPicked), 0)
           FROM M_ShipmentSchedule_QtyPicked sqp
           WHERE sqp.M_ShipmentSchedule_ID = s.M_ShipmentSchedule_ID
             AND sqp.IsActive = 'Y'
             AND sqp.Processed = 'N')                                             AS QtyPickedNotDelivered,
          -- QtyPickedPlanned:
          (SELECT COALESCE(SUM(uomConvert(
                  prod.M_Product_ID, -- product
                  pc.C_UOM_ID, -- from UOM
                  prod.C_UOM_ID, -- to UOM: shipment schedule's UOM (see above)
                  pc.QtyPicked
                               )), 0)
           FROM M_Picking_Candidate pc
           WHERE pc.M_ShipmentSchedule_ID = s.M_ShipmentSchedule_ID
             -- IP means in progress, i.e. not yet covered my M_ShipmentSchedule_QtyPicked
             -- note that when the pc is processed (->status PR or CL), then the QtyToDeliver is decreased accordingly
             AND pc.Status = 'IP'
             AND pc.IsActive = 'Y')                                               AS QtyPickedPlanned,
          s.qtyonhand                                                             AS QtyOnHand,
          s.qtyscheduledforpicking                                                AS QtyScheduledForPicking,
          s.qtyscheduledforpickingofprocessed                                     AS QtyScheduledForPickingOfProcessed,

          --
          -- Rules&Quantities
          COALESCE(s.DeliveryViaRule_Override, s.DeliveryViaRule)                 AS DeliveryViaRule,
          COALESCE(s.DeliveryDate_Override, s.DeliveryDate)                       AS DeliveryDate,
          COALESCE(s.PriorityRule_Override, s.PriorityRule)                       AS PriorityRule,

          --
          -- Catch Weight
          s.iscatchweight,
          s.catch_uom_id,

          --
          -- Shipper
          sh.M_Shipper_ID,
          sh.Name                                                                 AS ShipperName,

          --
          -- Picking/Manufacturing
          s.PickFrom_Order_ID,

          --
          -- Packing
          COALESCE(s.M_HU_PI_Item_Product_Override_ID, s.M_HU_PI_Item_Product_ID) AS PackTo_HU_PI_Item_Product_ID,

          --
          -- Locking
          -- NOTE: assume there is only one M_ShipmentSchedule_Lock record per each M_ShipmentSchedule_ID
          (SELECT l.LockedBy_User_ID
           FROM M_ShipmentSchedule_Lock l
           WHERE l.M_ShipmentSchedule_ID = s.M_ShipmentSchedule_ID)               AS LockedBy_User_ID,
          -- Per-line promised date = the same override-inclusive value as the DeliveryDate column above
          -- (COALESCE(DeliveryDate_Override, DeliveryDate)). M_ShipmentSchedule has no DatePromised column of its own;
          -- "DatePromised" is the order/flag terminology for the same business date the schedule stores as DeliveryDate.
          -- Exposing it per-line here is what makes the ship-after gate (header flag IsFixedDatePromised, applies to
          -- ALL lines) hold each line until its OWN date. This is the single place the order(DatePromised)-vs-schedule
          -- (DeliveryDate) naming is bridged.
          COALESCE(s.DeliveryDate_Override, s.DeliveryDate)                        AS datepromised,
          o.IsFixedDatePromised,
          o.IsFixedPreparationDate,
          s.carrier_advising_status,
          s.carrier_product_id,
          s.carrier_goods_type_id,
          --
          -- Standard columns
          s.AD_Client_ID,
          s.AD_Org_ID,
          s.Created,
          s.CreatedBy,
          s.Updated,
          s.UpdatedBy,
          s.IsActive,
          l.c_country_id,
          Product_GrossWeight_KG(s.M_Product_ID) as GrossWeight
      FROM M_ShipmentSchedule s
               JOIN M_Warehouse w ON (w.M_Warehouse_ID = COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID)) -- s.M_Warehouse_ID is mandatory
               JOIN C_BPartner p ON (p.C_BPartner_ID = COALESCE(s.C_BPartner_Override_ID, s.C_BPartner_ID))
               LEFT JOIN C_BPartner_Stats stats ON (p.C_BPartner_ID = stats.C_BPartner_ID)
               JOIN C_BPartner_Location bpl ON (bpl.C_BPartner_Location_ID = COALESCE(s.C_BP_Location_Override_ID, s.C_BPartner_Location_ID))
               JOIN c_location l ON l.c_location_id = COALESCE(s.C_BP_Location_Override_Value_ID, s.C_BPartner_Location_Value_ID)
               JOIN M_Product prod ON (prod.M_Product_ID = s.M_Product_ID)
               LEFT JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = s.C_OrderLine_ID)
               LEFT JOIN M_Shipper sh ON (sh.M_Shipper_ID = COALESCE(s.M_Shipper_ID, ol.M_Shipper_ID))
               LEFT JOIN C_Order o ON (o.C_Order_ID = s.C_Order_ID)
               LEFT JOIN C_DocType dt ON (dt.C_DocType_ID = o.C_DocType_ID)
      WHERE TRUE
        AND s.Processed = 'N'
        AND s.IsActive = 'Y'
        AND (s.QtyToDeliver > 0 OR s.qtypicklist > 0)
        AND s.isclosed = 'N'
        AND (stats.SOCreditStatus NOT IN ('S', 'H') OR stats.SOCreditStatus IS NULL)) p
;


SELECT db_alter_view(
               'm_packageable_v',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_packageable_v$new'))
       )
;

DROP VIEW IF EXISTS m_packageable_v$new
;

