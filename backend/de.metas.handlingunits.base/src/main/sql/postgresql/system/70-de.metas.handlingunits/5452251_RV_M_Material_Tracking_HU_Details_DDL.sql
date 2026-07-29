DROP VIEW IF EXISTS RV_M_Material_Tracking_HU_Details;

CREATE OR REPLACE VIEW RV_M_Material_Tracking_HU_Details AS
SELECT
	mt.Lot
	, mt.M_Material_Tracking_ID
	, hu.M_HU_ID
	, hu.M_HU_ID AS RV_M_Material_Tracking_HU_Details_ID
	, hu.HUStatus
	, hu.M_Locator_ID
	, hs.M_Product_ID
	, hs.Qty, hs.C_UOM_ID
	--
	-- IsQualityInspection (Waschproble alt) attribute
	, COALESCE(
		(select hua_qi.Value
			from M_HU_Attribute hua_qi
			inner join M_Attribute a_qi on (a_qi.M_Attribute_ID=hua_qi.M_Attribute_ID and a_qi.Value='IsQualityInspection')
			where hua_qi.M_HU_ID=hu.M_HU_ID
		)
		, 'N')::char(1) as IsQualityInspection
	--
	-- QualityInspectionCycle attribute
	, (
		select hua_qi.Value
			from M_HU_Attribute hua_qi
			inner join M_Attribute a_qi on (a_qi.M_Attribute_ID=hua_qi.M_Attribute_ID and a_qi.Value='QualityInspectionCycle')
			where hua_qi.M_HU_ID=hu.M_HU_ID
		) as QualityInspectionCycle
	--
	-- Standard columns
	, hu.Created
	, hu.CreatedBy
	, hu.Updated
	, hu.UpdatedBy
	, hu.AD_Client_ID
	, hu.AD_Org_ID
	, hu.IsActive
	, po_issue.PP_Order_ID AS pp_order_issue_id
	, po_issue.C_DocType_ID AS pp_order_issue_doctype_id
	, po_issue.DocStatus AS pp_order_issue_docstatus
	, po_receipt.PP_Order_ID AS pp_order_receipt_id
	, po_receipt.C_DocType_ID AS pp_order_receipt_doctype_id
	, po_receipt.DocStatus AS pp_order_receipt_docstatus
	, io_receipt.M_InOut_ID AS M_InOut_Receipt_ID
	, io_shipment.M_InOut_ID AS M_InOut_Shipment_ID

--	, cc_issue.PP_Cost_Collector_ID
--	, hu_as_iol.M_HU_Assignment_ID
--	, iol.M_InOutLine_ID
--	,hu_as_po.M_HU_Assignment_ID
--	,hu_as_pobl.M_HU_Assignment_ID
--	,pobl.*
FROM 
	M_Material_Tracking mt 
        INNER JOIN M_Attribute a ON (a.Value='M_Material_Tracking_ID')
		JOIN M_HU_Attribute hu_at ON hu_at.M_Attribute_ID=a.M_Attribute_ID AND hu_at.Value=mt.M_Material_Tracking_ID::text
			JOIN M_HU hu ON hu_at.M_HU_ID=hu.M_HU_ID
				JOIN M_HU_Storage hs ON hu.M_HU_ID = hs.M_HU_ID
				
				/*
				 * Receive-PP_Orders
				 */
				LEFT JOIN M_HU_Assignment hu_as_po ON hu_as_po.M_HU_ID=hu.M_HU_ID AND hu_as_po.AD_Table_ID=get_table_id('PP_Order') /* received main product*/
						AND hu_as_po.M_LU_HU_ID IS NULL AND hu_as_po.M_TU_HU_ID IS NULL AND hu_as_po.VHU_ID IS NULL /* only the toplevel assignment */
				LEFT JOIN M_HU_Assignment hu_as_pobl ON hu_as_pobl.M_HU_ID=hu.M_HU_ID AND hu_as_pobl.AD_Table_ID=get_table_id('PP_Order_BOMLine') /* received by- and co- products, but not issued products, because we don'T create a hu_as for them!*/
						AND hu_as_pobl.M_LU_HU_ID IS NULL AND hu_as_pobl.M_TU_HU_ID IS NULL AND hu_as_pobl.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN PP_Order_BOMLine pobl ON pobl.PP_Order_BOMLine_ID=hu_as_pobl.Record_ID
						LEFT JOIN PP_Order po_receipt ON (
							po_receipt.PP_Order_ID=pobl.PP_Order_ID AND pobl.ComponentType IN ('CP', 'BY') /* received co-product or by-product */
							OR
							po_receipt.PP_Order_ID=hu_as_po.Record_ID)
						/* we don't have HU-Assignments for issues! */							
						/* LEFT JOIN PP_Order po_issue ON po_issue.PP_Order_ID=pobl.PP_Order_ID AND pobl.ComponentType IN ('CO','VA','PK') */ /* component, variant or package material */
				/*
				 * Issue-PP_Orders
				 */
				LEFT JOIN M_HU_Assignment hu_as_cc ON hu_as_cc.M_HU_ID=hu.M_HU_ID AND hu_as_cc.AD_Table_ID=get_table_id('PP_Cost_Collector')
						AND hu_as_cc.M_LU_HU_ID IS NULL AND hu_as_cc.M_TU_HU_ID IS NULL AND hu_as_cc.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN PP_Cost_Collector cc_issue ON cc_issue.PP_Cost_Collector_ID=hu_as_cc.Record_ID AND cc_issue.DocStatus IN ('CO','CL') AND cc_issue.CostCollectorType='110' /*110=ComponentIssue*/
						LEFT JOIN PP_Order po_issue ON po_issue.PP_Order_ID=cc_issue.PP_Order_ID
				/*
				 * Material Receipts and shipments
				 */
				LEFT JOIN M_HU_Assignment hu_as_iol ON hu_as_iol.M_HU_ID=hu.M_HU_ID AND hu_as_iol.AD_Table_ID=get_table_id('M_InOutLine') 
						AND hu_as_iol.M_LU_HU_ID IS NULL AND hu_as_iol.M_TU_HU_ID IS NULL AND hu_as_iol.VHU_ID IS NULL /* only the toplevel assignment */
					LEFT JOIN M_InOutLine iol ON iol.M_InOutLine_ID=hu_as_iol.Record_ID
						LEFT JOIN M_InOut io_receipt ON io_receipt.M_InOut_ID=iol.M_InOut_ID AND io_receipt.MovementType IN ('V+', 'C+') /* 'V+'=VendorReceipts, 'C+'=CustomerReturns*/
						LEFT JOIN M_InOut io_shipment ON io_shipment.M_InOut_ID=iol.M_InOut_ID AND io_shipment.MovementType IN ('V-', 'C-') /* 'V-'=VendorReturns, 'C-'=CustomerShipment*/
WHERE 
	a.Value='M_Material_Tracking_ID'
	AND hu.M_HU_Item_Parent_ID IS NULL -- only show top-level-HUs
	AND NOT (hu.HUStatus='D' AND M_HU_PI_Version_ID=101) -- hide destroyed virtual-PI-HUs, because they don't have a parent set, but still their also destroyed parent is there
	AND NOT (hu.HUStatus='P') -- hide planned HUs because they only exist for technical reasons and are not of interest for the users
--	AND (hu.HUStatus!='D' OR EXISTS (select 1 from M_HU_Assignment hu_any where hu_any.M_HU_ID=hu.M_HU_ID)) -- also hide destroyed HUs that don't have any HU_Assignement at all. They were planned HUs that got discarded
--	AND mt.M_Material_Tracking_ID = 1000443
--	AND pobl.PP_Order_BOMLine_ID=1120157
--	AND po_issue.PP_Order_ID=1046130
--	AND hu.M_HU_ID=10100810 --has 2 rows..2 iol-ID
;


