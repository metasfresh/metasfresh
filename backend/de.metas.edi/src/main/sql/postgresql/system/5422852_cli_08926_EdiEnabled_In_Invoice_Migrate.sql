UPDATE C_Invoice i
SET IsEdiEnabled = x.IsEdiEnabled
FROM
(
	SELECT i. C_Invoice_ID, 
	(
	CASE WHEN i.C_Order_ID IS NOT NULL 
	THEN (SELECT o.ISEdiEnabled FROM C_Order o WHERE o. C_Order_ID = i.C_Order_ID)
	WHEN i.M_InOut_ID IS NOT NULL 
	THEN (SELECT ISEdiEnabled FROM M_InOut io WHERE io. M_InOut_ID = i.M_InOut_ID)
	ELSE
		'N'
	END
	) as IsEdiEnabled 
	
	FROM C_Invoice i
)
x
WHERE i.C_Invoice_ID = x.C_Invoice_ID;

-- TODO: Migration from invoice candidates ?