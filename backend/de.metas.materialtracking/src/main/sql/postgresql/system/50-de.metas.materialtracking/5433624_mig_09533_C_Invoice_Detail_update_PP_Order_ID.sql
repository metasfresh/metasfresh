
UPDATE C_Invoice_Detail id_outer
SET PP_Order_ID=data.PP_Order_ID
FROM (
	SELECT ppo.PP_Order_ID, id.*
	FROM PP_Order ppo
		JOIN (
		select 
			id.C_Invoice_Detail_ID, 
			-- extract the documentNo from the description field; i wasn't allowed to use regexp_matches in a join or where clause (dunno why)
			(regexp_matches(id.Description, '(PP_Order\[IsQualityInspection=[a-z]*, PP_Order.DocumentNo=)([0-9]*)(\])'))[2] AS PP_OrderDocumentNo
		from C_Invoice_Detail id
		where true 
			AND id.PP_Order_ID IS NULL
			AND id.Description LIKE 'PP_Order[IsQualityInspection%'
		) id ON id.PP_OrderDocumentNo=ppo.DocumentNo
	WHERE true
) data
WHERE data.C_Invoice_Detail_ID=id_outer.C_Invoice_Detail_ID
;
