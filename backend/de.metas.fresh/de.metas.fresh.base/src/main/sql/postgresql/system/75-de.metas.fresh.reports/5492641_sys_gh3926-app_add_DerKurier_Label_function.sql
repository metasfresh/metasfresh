DROP FUNCTION IF EXISTS report.DerKurier_Label(IN DerKurier_DeliveryOrderLine numeric);
CREATE OR REPLACE FUNCTION report.DerKurier_Label(IN DerKurier_DeliveryOrderLine numeric)
RETURNS TABLE
(	Barcode character varying,
	Weight numeric,
	ZipCode character varying,
	PackageAmount numeric,
	pageNo numeric,
	TrackingNumber numeric,
	DesiredStation character varying,
	DK_CustomerNumber character varying,
	BP_Name character varying,
	BP_address1 character varying,
	BP_postal character varying,
	BP_City character varying, 
	BP_CountryCode character(2),
	BP_Phone character varying,
	DesiredDeliveryDate timestamp without time zone, 
	DesiredDeliveryTime_From timestamp without time zone, 
	DesiredDeliveryTime_To timestamp without time zone,
	Consignee_Name character varying,
	Consignee_Name2 character varying, 
	Consignee_Name3 character varying, 
	Consignee_Phone character varying, 
	Consignee_Country character varying(2),
	Consignee_ZipCode character varying, 
	Consignee_City character varying, 
	Consignee_Street character varying
)
AS
$$
	SELECT
		DK_ParcelNumber AS Barcode,
		DK_ParcelWeight AS Weight,
		DK_Consignee_ZipCode AS ZipCode,
		DK_PackageAmount AS PackageAmount,
		report.get_page_no(DK_PackageAmount) as pageNo,
		DerKurier_DeliveryOrderLine_ID AS TrackingNumber, --
		DK_Consignee_DesiredStation AS DesiredStation, --
		DK_CustomerNumber, 
		--
		bp.Name, l.address1, l.postal, l.City, c.CountryCode, us.phone,
		--
		DK_DesiredDeliveryDate AS DesiredDeliveryDate, 
		DK_DesiredDeliveryTime_From AS DesiredDeliveryTime_From, 
		DK_DesiredDeliveryTime_To AS DesiredDeliveryTime_To,
		--
		DK_Consignee_Name AS Consignee_Name, 
		DK_Consignee_Name2 AS Consignee_Name2, 
		DK_Consignee_Name3 AS Consignee_Name3, 
		DK_Consignee_Phone AS Consignee_Phone, 
		DK_Consignee_Country AS Consignee_Country,
		DK_Consignee_ZipCode AS Consignee_ZipCode, 
		DK_Consignee_City AS Consignee_City, 
		DK_Consignee_Street AS Consignee_Street
		
	FROM
	DerKurier_DeliveryOrderLine ddol

	JOIN AD_Org o ON ddol.AD_Org_ID = o.AD_Org_ID
	JOIN AD_OrgInfo oi ON o.AD_Org_ID = oi.AD_Org_ID
	LEFT JOIN C_BPartner_Location bpl ON oi.OrgBP_Location_ID = bpl.C_BPartner_Location_ID
	LEFT JOIN C_BPartner bp ON bpl.C_BPartner_ID = bp.C_BPartner_ID
	LEFT JOIN C_Location l ON bpl.C_Location_ID = l.C_Location_ID
	LEFT JOIN C_Country c ON l.C_Country_ID = c.C_Country_ID

	LEFT OUTER JOIN AD_User us ON us.AD_User_ID = 
	(
		SELECT AD_User_ID FROM AD_User sub_us
		WHERE bp.c_bpartner_id = sub_us.c_bpartner_id
		ORDER BY IsDefaultContact DESC
		LIMIT 1
	)

	WHERE DerKurier_DeliveryOrderLine_ID = $1
$$
LANGUAGE sql STABLE;
