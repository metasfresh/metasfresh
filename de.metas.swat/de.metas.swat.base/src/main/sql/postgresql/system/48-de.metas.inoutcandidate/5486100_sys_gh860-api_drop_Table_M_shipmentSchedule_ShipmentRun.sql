
DROP VIEW IF EXISTS M_Packageable_V;
CREATE OR REPLACE VIEW M_Packageable_V AS
SELECT
	--
	-- BPartner
	p.C_BPartner_ID,
	p.Value AS BPartnerValue,
	(coalesce(p.Name,'') || coalesce(p.Name2,'')) AS BPartnerName,
	--
	-- BPartner location
	l.C_BPartner_Location_ID,
	l.Name AS BPartnerLocationName,
	s.BPartnerAddress_Override,
	--
	-- Order Info
	o.C_Order_ID,
	o.DocumentNo as OrderDocumentNo,
	o.FreightCostRule,
	dt.DocSubType,
	s.DateOrdered,
	--
	-- Warehouse
	w.M_Warehouse_ID,
	w.Name AS WarehouseName,
	
	--
	-- Shipment schedule
	s.M_ShipmentSchedule_ID,
	s.IsDisplayed,
	prod.C_UOM_ID as C_UOM_ID, -- shipment schedule's UOM (see de.metas.inoutcandidate.api.impl.ShipmentScheduleBL.getC_UOM)
	COALESCE(s.QtyToDeliver_Override, s.QtyToDeliver) AS QtyToDeliver,
	COALESCE (s.DeliveryViaRule_Override, s.DeliveryViaRule) AS DeliveryViaRule,
	COALESCE(s.DeliveryDate_Override, s.DeliveryDate) AS DeliveryDate,
	COALESCE(s.PreparationDate_Override, s.PreparationDate) AS PreparationDate,
	COALESCE(s.PriorityRule_Override, s.PriorityRule) as PriorityRule,
	-- Shipper
	sh.Name AS ShipperName,
	sh.M_Shipper_ID,
	-- Product
	s.M_Product_ID,
	prod.Name AS ProductName
	
	--
	-- QtyPickedPlanned
	, (
		select
			COALESCE(SUM(uomConvert(
				prod.M_Product_ID, -- product
				pc.C_UOM_ID, -- from UOM
				prod.C_UOM_ID, -- to UOM: shipment schedule's UOM
				pc.QtyPicked
			)), 0)
		from M_Picking_Candidate pc
		where pc.M_ShipmentSchedule_ID=s.M_ShipmentSchedule_ID
			 -- IP means in progress, i.e. not yet covered my M_ShipmentSchedule_QtyPicked
			 -- note that when the pc is processed (->status PR or CL), then the QtyToDeliver is decreased accordingly
			and pc.Status='IP'
	) as QtyPickedPlanned
	
	--
	-- Standard columns
	, s.AD_Client_ID, s.AD_Org_ID, s.Created, s.CreatedBy, s.Updated, s.UpdatedBy, s.IsActive
FROM M_ShipmentSchedule s
LEFT JOIN M_Warehouse w ON (w.M_Warehouse_ID = COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID))
LEFT JOIN C_BPartner p ON (p.C_BPartner_ID=COALESCE(s.C_BPartner_Override_ID, s.C_BPartner_ID))
LEFT JOIN C_BPartner_Stats stats ON (p.C_BPartner_ID = stats.C_BPartner_ID)
LEFT JOIN C_BPartner_Location l ON (l.C_BPartner_Location_ID=COALESCE(s.C_BP_Location_Override_ID, s.C_BPartner_Location_ID))
LEFT JOIN M_Product prod ON (prod.M_Product_ID=s.M_Product_ID)
LEFT JOIN C_OrderLine ol ON (ol.C_OrderLine_ID=s.C_OrderLine_ID)
LEFT JOIN C_Order o ON (o.C_Order_ID=ol.C_Order_ID)
LEFT JOIN C_DocType dt ON (dt.C_DocType_ID=o.C_DocType_ID)
LEFT JOIN M_Shipper sh ON (sh.M_Shipper_ID=ol.M_Shipper_ID)
WHERE
	true
	AND s.QtyToDeliver > 0
	AND (stats.SOCreditStatus NOT IN ('S', 'H') OR stats.SOCreditStatus IS NULL)
;


SELECT public.db_alter_table('M_ShipmentSchedule_ShipmentRun','DROP TABLE M_ShipmentSchedule_ShipmentRun');
