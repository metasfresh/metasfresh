--
-- make m_shipmentschedule.M_Product_ID mandatory
--
-- 2018-08-03T06:55:45.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-08-03 06:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500231
;

-- 2018-08-03T06:55:49.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule','M_Product_ID','NUMERIC(10)',null,null)
;

--
-- add ASI to the view;
-- also change the joins for mandatory columns from LEFT JOIN to (inner) JOIN
--
-- 2018-08-03T07:06:54.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (35,10,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-08-03 07:06:54','YYYY-MM-DD HH24:MI:SS'),'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-08-03 07:06:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540823,'N','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','M_AttributeSetInstance_ID',560728,'N','N','N','N','N','N','N','N','Merkmals Ausprägungen zum Produkt',0,0,'Merkmale',2019,'de.metas.inoutcandidate','N','N')
;

-- 2018-08-03T07:06:54.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560728 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

DROP VIEW IF EXISTS M_Packageable_V;

CREATE OR REPLACE VIEW M_Packageable_V AS
SELECT
	--
	-- BPartner
	p.C_BPartner_ID AS C_BPartner_Customer_ID,
	p.Value AS BPartnerValue,
	(coalesce(p.Name,'') || coalesce(p.Name2,'')) AS BPartnerName,
	--
	-- BPartner location
	l.C_BPartner_Location_ID,
	l.Name AS BPartnerLocationName,
	s.BPartnerAddress_Override,
	--
	-- Order Info
	s.C_Order_ID AS C_OrderSO_ID,
	o.DocumentNo as OrderDocumentNo,
	o.FreightCostRule,
	dt.DocSubType,
	s.DateOrdered,
	s.C_OrderLine_ID as C_OrderLineSO_ID,
	--
	-- Warehouse
	w.M_Warehouse_ID,
	w.Name AS WarehouseName,
	--
	-- Shipment schedule
	s.M_ShipmentSchedule_ID,
	s.IsDisplayed,
	prod.C_UOM_ID as C_UOM_ID, -- shipment schedule's UOM (see de.metas.inoutcandidate.api.impl.ShipmentScheduleBL.getC_UOM)

	s.QtyOrdered as QtyOrdered,
	--
	-- Rules&Quantities
	(
		select COALESCE(s.QtyDelivered, 0) + (
			COALESCE(SUM(
				sqp.QtyPicked
			), 0))
		from M_ShipmentSchedule_QtyPicked sqp
		where sqp.M_ShipmentSchedule_ID=s.M_ShipmentSchedule_ID and sqp.Processed = 'N' and sqp.IsActive = 'Y'
			
	) as QtyPicked ,
	COALESCE(s.QtyToDeliver_Override, s.QtyToDeliver) AS QtyToDeliver,
	COALESCE (s.DeliveryViaRule_Override, s.DeliveryViaRule) AS DeliveryViaRule,
	COALESCE(s.DeliveryDate_Override, s.DeliveryDate) AS DeliveryDate,
	COALESCE(s.PreparationDate_Override, s.PreparationDate) AS PreparationDate,
	COALESCE(s.PriorityRule_Override, s.PriorityRule) as PriorityRule,
	--
	-- Shipper
	sh.Name AS ShipperName,
	sh.M_Shipper_ID,
	--
	-- Product & ASI
	s.M_Product_ID,
	prod.Name AS ProductName,
	s.M_AttributeSetInstance_ID,
	
	--
	-- QtyPickedPlanned
	(
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
			and pc.IsActive = 'Y'
	) as QtyPickedPlanned
	
	--
	-- Standard columns
	, s.AD_Client_ID, s.AD_Org_ID, s.Created, s.CreatedBy, s.Updated, s.UpdatedBy, s.IsActive
FROM M_ShipmentSchedule s
	JOIN M_Warehouse w ON (w.M_Warehouse_ID = COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID)) -- s.M_Warehouse_ID is mandatory
	JOIN C_BPartner p ON (p.C_BPartner_ID=COALESCE(s.C_BPartner_Override_ID, s.C_BPartner_ID))
		LEFT JOIN C_BPartner_Stats stats ON (p.C_BPartner_ID = stats.C_BPartner_ID)
	JOIN C_BPartner_Location l ON (l.C_BPartner_Location_ID=COALESCE(s.C_BP_Location_Override_ID, s.C_BPartner_Location_ID))
	JOIN M_Product prod ON (prod.M_Product_ID=s.M_Product_ID)
	LEFT JOIN C_OrderLine ol ON (ol.C_OrderLine_ID=s.C_OrderLine_ID)
		LEFT JOIN M_Shipper sh ON (sh.M_Shipper_ID=ol.M_Shipper_ID)
	LEFT JOIN C_Order o ON (o.C_Order_ID=s.C_Order_ID)
		LEFT JOIN C_DocType dt ON (dt.C_DocType_ID=o.C_DocType_ID)
WHERE
	s.Processed='N'
	AND s.IsActive = 'Y'
	AND s.QtyToDeliver > 0
	AND (stats.SOCreditStatus NOT IN ('S', 'H') OR stats.SOCreditStatus IS NULL)
;
