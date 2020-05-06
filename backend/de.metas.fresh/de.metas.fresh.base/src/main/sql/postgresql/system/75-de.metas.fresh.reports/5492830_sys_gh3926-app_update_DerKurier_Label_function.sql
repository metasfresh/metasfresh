DROP FUNCTION IF EXISTS report.DerKurier_Label(IN DerKurier_DeliveryOrderLine numeric);
CREATE OR REPLACE FUNCTION report.DerKurier_Label(IN DerKurier_DeliveryOrderLine numeric)
RETURNS TABLE
(	Barcode character varying,
	ParcelNumber text,
	Weight numeric,
	ZipCode character varying,
	PackageAmount numeric,
	pageNo numeric,
	TrackingNumber numeric,
	DesiredStation character varying,
	DK_CustomerNumber character varying,
	
	Sender_Name character varying,
	Sender_Name2 character varying,
	Sender_Name3 character varying,
	Sender_Country character(2),
	Sender_City character varying,
	Sender_ZipCode character varying,
	Sender_Street character varying,
	Sender_HouseNumber character varying,

	DesiredDeliveryDate timestamp without time zone, 
	DesiredDeliveryTime_From text,	
	DesiredDeliveryTime_To text, 	
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
		ddol.DK_ParcelNumber AS Barcode,
		regexp_replace(ddol.DK_ParcelNumber, '[0-9]$', '') as DK_ParcelNumber,
		ddol.DK_ParcelWeight AS Weight,
		ddol.DK_Consignee_ZipCode AS ZipCode,
		ddol.DK_PackageAmount AS PackageAmount,
		report.get_page_no(ddol.DK_PackageAmount) as pageNo,
		ddol.DerKurier_DeliveryOrderLine_ID AS TrackingNumber, --
		ddol.DK_Consignee_DesiredStation AS DesiredStation, --
		ddol.DK_CustomerNumber, 
		--
		ddo.DK_Sender_Name,
		ddo.DK_Sender_Name2,
		ddo.DK_Sender_Name3,
		ddo.DK_Sender_Country,
		ddo.DK_Sender_City,
		ddo.DK_Sender_ZipCode,
		ddo.DK_Sender_Street,
		ddo.DK_Sender_HouseNumber,
		--
		ddol.DK_DesiredDeliveryDate AS DesiredDeliveryDate, 
		to_char(ddol.DK_DesiredDeliveryTime_From, 'HH24:MI') AS DK_DesiredDeliveryTime_From,
		to_char(ddol.DK_DesiredDeliveryTime_To, 'HH24:MI') AS DK_DesiredDeliveryTime_To,
		--
		ddol.DK_Consignee_Name AS Consignee_Name, 
		ddol.DK_Consignee_Name2 AS Consignee_Name2, 
		ddol.DK_Consignee_Name3 AS Consignee_Name3, 
		ddol.DK_Consignee_Phone AS Consignee_Phone, 
		ddol.DK_Consignee_Country AS Consignee_Country,
		ddol.DK_Consignee_ZipCode AS Consignee_ZipCode, 
		ddol.DK_Consignee_City AS Consignee_City, 
		ddol.DK_Consignee_Street AS Consignee_Street
		
	FROM
	DerKurier_DeliveryOrderLine ddol

	JOIN DerKurier_DeliveryOrder ddo ON ddol.DerKurier_DeliveryOrder_ID = ddo.DerKurier_DeliveryOrder_ID

	WHERE ddol.DerKurier_DeliveryOrderLine_ID = $1
$$
LANGUAGE sql STABLE;
