-- View: RV_HU_Quantities

-- DROP VIEW IF EXISTS RV_HU_Quantities;

CREATE OR REPLACE VIEW RV_HU_Quantities AS
--
-- NOTE: this view is used in MRP Product Info - Details: Stock tab
--
SELECT
	q.M_Product_ID
	, q.Name AS ProductName
	, ih.HUStorageASIKey AS ASIKey
	, ih.HUStorageASIKey AS ASIKeyName
	, q.C_UOM_ID
	, CEIL(SUM(COALESCE(QtyReserved, 0))) AS QtyReserved
	, CEIL(SUM(COALESCE(QtyOrdered, 0))) AS QtyOrdered
	, CEIL(SUM(COALESCE(QtyOnHand, 0))) AS QtyOnHand
	, CEIL(SUM(COALESCE(QtyOnHand, 0)) - SUM(COALESCE(QtyReserved, 0))) AS QtyAvailable

	, q.Value AS ProductValue
	, q.IsPurchased
	, q.IsSold
	, q.M_Product_Category_ID

	, ih.M_HU_PI_Item_Product

	, q.AD_Client_ID
	, q.AD_Org_ID
	, q.isActive
	, MAX(q.Created) AS Created
	, MAX(q.CreatedBy) AS CreatedBy
	, MAX(q.Updated) AS Updated
	, MAX(q.UpdatedBy) AS UpdatedBy
	--
	, CEIL(SUM(COALESCE(QtyReserved_MRP, 0))) AS QtyReserved_MRP -- fresh_08273
	, NULL::Date AS DateTrx -- fresh_GOLIVE
	, 0::NUMERIC AS fresh_QtyOnHand_OnDate -- fresh_GOLIVE
FROM M_Product q
LEFT OUTER JOIN RV_M_HU_Storage_MRPProductInfo ih ON ih.M_Product_ID = q.M_Product_ID
GROUP BY 
	q.M_Product_ID
	, q.Name
	, ih.HUStorageASIKey

	, q.Value
	, q.IsPurchased
	, q.IsSold
	, q.M_Product_Category_ID

	, ih.M_HU_PI_Item_Product

	, q.AD_Client_ID
	, q.AD_Org_ID
	, q.isActive
--
--
-- Union with what user had manually booked on their manual inventory countings
-- NOTE: this is needed just until we golive with other modules (MO issue, MRP etc)
-- fresh_GOLIVE:
UNION ALL
SELECT
	p.M_Product_ID
	, p.Name AS ProductName
	, NULL AS ASIKey
	, GenerateHUStorageASIKey(qohl.M_AttributeSetInstance_ID) AS ASIKeyName
	, p.C_UOM_ID
	, 0 AS QtyReserved
	, 0 AS QtyOrdered
	, 0 AS QtyOnHand
	, 0 AS QtyAvailable

	, p.Value AS ProductValue
	, p.IsPurchased
	, p.IsSold
	, p.M_Product_Category_ID

	, NULL::character varying(80) AS M_HU_PI_Item_Product

	, qohl.AD_Client_ID
	, qohl.AD_Org_ID
	, qohl.isActive
	, qohl.Created AS Created
	, qohl.CreatedBy AS CreatedBy
	, qohl.Updated AS Updated
	, qohl.UpdatedBy AS UpdatedBy
	--
	, 0 AS QtyReserved_MRP
	, qoh.DateDoc::Date AS DateTrx -- fresh_GOLIVE
	, CEIL(qohl.QtyCount) AS fresh_QtyOnHand_OnDate  -- fresh_GOLIVE
FROM fresh_QtyOnHand_Line qohl
INNER JOIN M_Product p on (p.M_Product_ID=qohl.M_Product_ID)
INNER JOIN fresh_QtyOnHand qoh ON (qoh.fresh_QtyOnHand_ID=qohl.fresh_QtyOnHand_ID)
--
;


