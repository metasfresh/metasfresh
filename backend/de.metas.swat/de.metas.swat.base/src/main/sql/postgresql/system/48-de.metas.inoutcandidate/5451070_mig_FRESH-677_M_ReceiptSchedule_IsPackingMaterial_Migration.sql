
UPDATE
	M_ReceiptSchedule rs
SET
	IsPackagingMaterial = 'Y'
FROM
	(
		SELECT 
			rec.M_ReceiptSchedule_ID
		FROM 
			M_ReceiptSchedule rec
			JOIN C_OrderLine ol on rec.ad_table_id = get_table_id('C_OrderLine') and rec.Record_ID = ol.C_Orderline_ID
		WHERE 
			rec.C_Order_ID > 0 AND
			ol.isPackagingMaterial = 'Y'
			
	)x
WHERE 
	rs.M_ReceiptSchedule_ID = x.M_ReceiptSchedule_ID
;