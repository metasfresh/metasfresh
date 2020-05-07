UPDATE C_Order o
SET AD_InputDataSource_ID = x.AD_InputDataSource_ID
FROM
(
	SELECT olc.AD_InputDataSource_ID, o.C_Order_ID
	FROM C_OlCand olc
	INNER JOIN C_Order_Line_Alloc ola ON olc.C_OlCand_ID = ola.C_OlCand_ID
	INNER JOIN C_OrderLine ol on ola.C_OrderLine_ID = ol.C_OrderLine_ID
	INNER JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID

	WHERE o.IsSOTrx = 'Y'
	GROUP BY o.C_Order_ID, olc.AD_InputDataSource_ID
)
x
WHERE 
	o.C_Order_ID = x.C_Order_ID  
;