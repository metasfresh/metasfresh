
CREATE OR REPLACE VIEW C_OrderLine_ID_With_Missing_ShipmentSchedule_v AS
SELECT ol.C_OrderLine_id
FROM C_OrderLine ol
	JOIN C_Order o ON ol.C_Order_id=o.C_Order_ID
		JOIN C_DocType dt on dt.C_DocType_ID=o.c_doctype_id
	JOIN M_Product p ON p.M_Product_ID=ol.M_Product_ID AND p.ProductType='I' /* gh #992; see comment */
where true
	AND ol.QtyOrdered <> ol.QtyDelivered 
	AND NOT EXISTS ( select * from M_ShipmentSchedule s where s.C_OrderLine_ID=ol.C_OrderLine_ID ) /* has no shipment schedule yet */
	and dt.DocBaseType='SOO' AND dt.DocSubType NOT IN ('ON','OB','WR') /* ignore proposals, quatations and POS orders */
	and o.IsSOTrx='Y' 
	AND o.docstatus='CO' 
	AND NOT EXISTS ( select 1 from M_IolCandHandler_Log log where log.M_IolCandHandler_ID=1000000 and log.AD_Table_ID=260 and log.Record_ID=ol.C_OrderLine_ID and log.IsActive='Y'   )
;	
COMMENT ON VIEW C_OrderLine_ID_With_Missing_ShipmentSchedule_v IS 
'Selects C_OrderLines that should have a shipment schedule and do not have one yet.
Issue gh #992: 
  * Only consider C_OrderLines whose product type is "I" (Item). Please keep this in sync with IProductBL.isItem() and IProduct.isService(). 
  * Note that the delivery rule for invoice candidates is already set to "Immediate" for such products.
Also see task 08896';
