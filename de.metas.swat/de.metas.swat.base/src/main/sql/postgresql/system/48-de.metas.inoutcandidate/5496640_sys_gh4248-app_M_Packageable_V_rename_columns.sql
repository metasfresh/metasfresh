-- 2018-06-26T17:56:00.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560511,542435,0,30,271,540823,'N','C_OrderLineSO_ID',TO_TIMESTAMP('2018-06-26 17:56:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftragsposition','de.metas.inoutcandidate',10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Auftragsposition',0,0,TO_TIMESTAMP('2018-06-26 17:56:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-06-26T17:56:00.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-26T17:56:29.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543479, AD_Reference_Value_ID=540100, ColumnName='C_OrderSO_ID', Description='Auftrag', Help=NULL, Name='Auftrag',Updated=TO_TIMESTAMP('2018-06-26 17:56:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556841
;

-- 2018-06-26T17:56:29.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftrag', Description='Auftrag', Help=NULL WHERE AD_Column_ID=556841
;

-- 2018-06-26T21:11:38.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help=NULL, AD_Reference_Value_ID=173, ColumnName='C_BPartner_Customer_ID', Description=NULL, Name='Kunde', AD_Element_ID=541356,Updated=TO_TIMESTAMP('2018-06-26 21:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556835
;

-- 2018-06-26T21:11:38.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunde', Description=NULL, Help=NULL WHERE AD_Column_ID=556835
;

-- 2018-06-26T21:11:58.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2018-06-26 21:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556835
;

-- 2018-06-27T15:39:51.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=290,Updated=TO_TIMESTAMP('2018-06-27 15:39:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556841
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
	
	-- QtyPicked
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
			and pc.IsActive = 'Y'
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
LEFT JOIN C_Order o ON (o.C_Order_ID=s.C_Order_ID)
LEFT JOIN C_DocType dt ON (dt.C_DocType_ID=o.C_DocType_ID)
LEFT JOIN M_Shipper sh ON (sh.M_Shipper_ID=ol.M_Shipper_ID)
WHERE
	s.Processed='N'
	AND s.IsActive = 'Y'
	AND s.QtyToDeliver > 0
	AND (stats.SOCreditStatus NOT IN ('S', 'H') OR stats.SOCreditStatus IS NULL)
;
