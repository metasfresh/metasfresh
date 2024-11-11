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
          l.C_BPartner_Location_ID,
          l.Name                                                                  AS BPartnerLocationName,
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
          coalesce(
                  (case when o.IsUseHandOver_Location='Y' then o.HandOver_Location_ID else o.C_BPartner_Location_ID end),
                  o.C_BPartner_Location_ID
              )                                                                       AS HandOver_Location_ID,
          coalesce(
                  (case when o.IsUseHandOver_Location='Y' then o.Handover_Partner_ID else o.C_BPartner_ID end),
                  o.C_BPartner_ID
              )                                                                       AS HandOver_Partner_ID,
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
          s.ShipmentAllocation_BestBefore_Policy,

          --
          -- Product & ASI
          s.M_Product_ID,
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

          --
          -- Standard columns
          s.AD_Client_ID,
          s.AD_Org_ID,
          s.Created,
          s.CreatedBy,
          s.Updated,
          s.UpdatedBy,
          s.IsActive
      FROM M_ShipmentSchedule s
               JOIN M_Warehouse w ON (w.M_Warehouse_ID = COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID)) -- s.M_Warehouse_ID is mandatory
               JOIN C_BPartner p ON (p.C_BPartner_ID = COALESCE(s.C_BPartner_Override_ID, s.C_BPartner_ID))
               LEFT JOIN C_BPartner_Stats stats ON (p.C_BPartner_ID = stats.C_BPartner_ID)
               JOIN C_BPartner_Location l ON (l.C_BPartner_Location_ID = COALESCE(s.C_BP_Location_Override_ID, s.C_BPartner_Location_ID))
               JOIN M_Product prod ON (prod.M_Product_ID = s.M_Product_ID)
               LEFT JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = s.C_OrderLine_ID)
               LEFT JOIN M_Shipper sh ON (sh.M_Shipper_ID = COALESCE(s.M_Shipper_ID, ol.M_Shipper_ID))
               LEFT JOIN C_Order o ON (o.C_Order_ID = s.C_Order_ID)
               LEFT JOIN C_DocType dt ON (dt.C_DocType_ID = o.C_DocType_ID)
      WHERE TRUE
        AND s.Processed = 'N'
        AND s.IsActive = 'Y'
        AND s.QtyToDeliver > 0
        AND s.isclosed = 'N'
        AND (stats.SOCreditStatus NOT IN ('S', 'H') OR stats.SOCreditStatus IS NULL)) p
;


SELECT db_alter_view(
               'm_packageable_v',
               (SELECT view_definition
                FROM information_schema.views
                WHERE views.table_name = 'm_packageable_v$new')
           )
;

DROP VIEW IF EXISTS m_packageable_v$new
;

