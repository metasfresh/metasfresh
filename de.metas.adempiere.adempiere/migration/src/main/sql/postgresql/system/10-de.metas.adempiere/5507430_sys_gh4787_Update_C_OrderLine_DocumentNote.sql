
UPDATE C_OrderLine ol set M_Product_DocumentNote = x.DocumentNote
FROM
(
	Select trl.DocumentNote , ol. C_OrderLine_ID 
	from C_OrderLine ol
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	JOIN C_BPartner bp on o.C_Bpartner_ID = bp.C_BPartner_ID
	JOIN M_Product_Trl trl on ol.M_Product_ID = trl.M_Product_ID and trl.ad_language = bp.ad_language
	
)
x
 WHERE ol.c_orderLine_ID = x.C_OrderLine_ID and ol.M_Product_DocumentNote is null and x.DocumentNote is not null;