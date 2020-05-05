
UPDATE PP_Order ppo_outer
SET IsInvoiceCandidate='Y',
	Updated=now(),
	UpdatedBy=99
FROM (
	SELECT ppo.PP_Order_ID
	FROM PP_Order ppo
		JOIN M_Material_Tracking mt ON mt.M_Material_Tracking_ID=ppo.M_Material_Tracking_ID
		JOIN C_Invoice_Detail id ON id.PP_Order_ID=ppo.PP_Order_ID
	WHERE true
		AND ppo.IsInvoiceCandidate='N'
) data
WHERE data.PP_Order_ID=ppo_outer.PP_Order_ID
;