-- View: EDI_Cctop_119_v

-- DROP VIEW IF EXISTS EDI_Cctop_119_v;

CREATE OR REPLACE VIEW EDI_Cctop_119_v AS
SELECT
	lookup.C_Invoice_ID AS EDI_Cctop_119_v_ID,
	lookup.C_Invoice_ID,
	lookup.C_Invoice_ID AS EDI_Cctop_INVOIC_v_ID,
	lookup.M_InOut_ID,
	pl.C_BPartner_Location_ID,
	pl.GLN,
	pl.Phone,
	pl.Fax,
	p.Name,
	p.Name2,
	p.Value,
	p.VATaxID,
	lookup.Vendor_ReferenceNo AS ReferenceNo,
	CASE lookup.Type_V
		WHEN 'ship'::TEXT THEN 'DP'::TEXT
		WHEN 'bill'::TEXT THEN 'IV'::TEXT
		WHEN 'cust'::TEXT THEN 'BY'::TEXT
		WHEN 'vend'::TEXT THEN 'SU'::TEXT
		WHEN 'snum'::TEXT THEN 'SN'::TEXT
		ELSE 'Error EANCOM_Type'::TEXT
	END AS eancom_locationtype,
	l.Address1,
	l.Address2,
	l.Postal,
	l.City,
	c.CountryCode,
	l.AD_Client_ID,
	l.AD_Org_ID,
	l.Created,
	l.CreatedBy,
	l.Updated,
	l.UpdatedBy,
	l.IsActive
FROM
(
	SELECT
		union_lookup.*
	FROM
	(
		SELECT DISTINCT
			1::INTEGER AS SeqNo
			, 'cust'::TEXT AS Type_V
			, (CASE
				WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
					THEN o.C_BPartner_Location_ID
				ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
					i.C_BPartner_Location_ID
			END) AS C_BPartner_Location_ID
			, i.C_Invoice_ID
			, 0::INTEGER AS M_InOut_ID
			, NULL::TEXT AS Vendor_ReferenceNo
		FROM C_Invoice i
			LEFT JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
				LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
					LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
		--
		UNION
		--
		SELECT
			2::INTEGER AS SeqNo
			, 'vend'::TEXT AS Type_V
			, pl_vend.C_BPartner_Location_ID
			, i.C_Invoice_ID
			, 0::INTEGER AS M_InOut_ID
			, p_cust.ReferenceNo AS Vendor_ReferenceNo
		FROM C_Invoice i
			JOIN C_BPartner p_cust ON p_cust.C_BPartner_ID = i.C_BPartner_ID
			JOIN C_BPartner p_vend ON p_vend.AD_OrgBP_ID = i.AD_Org_ID
				JOIN C_BPartner_Location pl_vend ON pl_vend.C_BPartner_ID = p_vend.C_BPartner_ID AND pl_vend.isremitto = 'Y'
		--
		UNION
		--
		SELECT DISTINCT
			3::INTEGER AS SeqNo
			, 'ship'::TEXT AS Type_V
			, -- Could use COALESCE(NULLIF(.., ..), ..) here, but it gets messy, so I prefer CASE
			(CASE
				WHEN o.HandOver_Location_ID IS NOT NULL AND o.HandOver_Location_ID != 0::INTEGER
					THEN o.HandOver_Location_ID
				WHEN s.C_BPartner_Location_ID IS NOT NULL AND s.C_BPartner_Location_ID != 0::INTEGER
					THEN s.C_BPartner_Location_ID
				WHEN o.DropShip_Location_ID IS NOT NULL AND o.DropShip_Location_ID != 0::INTEGER
					THEN o.DropShip_Location_ID
				WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
					THEN o.C_BPartner_Location_ID
				ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
					i.C_BPartner_Location_ID
			END) AS C_BPartner_Location_ID
			, i.C_Invoice_ID
			, 0::INTEGER AS M_InOut_ID
			, NULL::TEXT AS Vendor_ReferenceNo
		FROM C_Invoice i
		INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
			LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID=il.C_OrderLine_ID
				LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
					LEFT JOIN M_InOutline sl ON (
						-- try to join directly from C_InvoiceLine
						sl.M_InOutLine_ID=il.M_InOutLine_ID AND il.M_InOutLine_ID IS NOT NULL AND il.M_InOutLine_ID != 0)
						-- fallback and try to join from C_OrderLine if (and only if) it's missing in C_InvoiceLine
						OR (sl.C_OrderLine_ID = il.C_OrderLine_ID AND (il.M_InOutLine_ID IS NULL OR il.M_InOutLine_ID = 0))
						LEFT JOIN M_InOut s ON s.M_InOut_ID = sl.M_InOut_ID
		--
		UNION
		--
		SELECT
			4::INTEGER AS SeqNo
			, 'bill'::TEXT AS Type_V
			, i.C_BPartner_Location_ID
			, i.C_Invoice_ID
			, 0::INTEGER AS M_InOut_ID
			, NULL::UNKNOWN AS Vendor_ReferenceNo
		FROM C_Invoice i
		--
		UNION
		--
		SELECT DISTINCT
			5::INTEGER AS SeqNo
			, 'snum'::TEXT AS Type_V
			, -- Could use COALESCE(NULLIF(.., ..), ..) here, but it gets messy, so I prefer CASE
			(CASE
				WHEN s.C_BPartner_Location_ID IS NOT NULL AND s.C_BPartner_Location_ID != 0::INTEGER
					THEN s.C_BPartner_Location_ID
				WHEN o.DropShip_Location_ID IS NOT NULL AND o.DropShip_Location_ID != 0::INTEGER
					THEN o.DropShip_Location_ID
				WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
					THEN o.C_BPartner_Location_ID
				ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
					i.C_BPartner_Location_ID
			END) AS C_BPartner_Location_ID
			, i.C_Invoice_ID
			, s.M_InOut_ID
			, NULL::TEXT AS Vendor_ReferenceNo
		FROM C_Invoice i
			INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
				LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID=il.C_OrderLine_ID
					LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
						LEFT JOIN M_InOutline sl ON (
							-- try to join directly from C_InvoiceLine
							sl.M_InOutLine_ID=il.M_InOutLine_ID AND il.M_InOutLine_ID IS NOT NULL AND il.M_InOutLine_ID != 0)
							-- fallback and try to join from C_OrderLine if (and only if) it's missing in C_InvoiceLine
							OR (sl.C_OrderLine_ID = il.C_OrderLine_ID AND (il.M_InOutLine_ID IS NULL OR il.M_InOutLine_ID = 0))
							LEFT JOIN M_InOut s ON s.M_InOut_ID = sl.M_InOut_ID
	) union_lookup
	ORDER BY union_lookup.SeqNo, union_lookup.Type_V, union_lookup.C_BPartner_Location_ID, C_Invoice_ID, M_InOut_ID
) lookup
LEFT JOIN C_BPartner_Location pl ON pl.C_BPartner_Location_ID = lookup.C_BPartner_Location_ID
LEFT JOIN C_BPartner p ON p.C_BPartner_ID = pl.C_BPartner_ID
LEFT JOIN C_Location l ON l.C_Location_ID = pl.C_Location_ID
LEFT JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID
WHERE true
	ADN p.VATaxID IS NOT NULL 
	AND (l.Address1 IS NOT NULL OR l.Address2 IS NOT NULL)
ORDER BY (
	lookup.C_Invoice_ID,
	CASE lookup.Type_V
		WHEN 'ship'::TEXT THEN 'DP'::TEXT
		WHEN 'bill'::TEXT THEN 'IV'::TEXT
		WHEN 'cust'::TEXT THEN 'BY'::TEXT
		WHEN 'vend'::TEXT THEN 'SU'::TEXT
		WHEN 'snum'::TEXT THEN 'SN'::TEXT
		ELSE 'Error EANCOM_Type'::TEXT
	END
);

