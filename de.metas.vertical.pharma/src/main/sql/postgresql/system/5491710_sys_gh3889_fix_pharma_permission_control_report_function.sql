DROP FUNCTION IF EXISTS "de.metas.vertical.pharma".pharma_permission_control_report
(
	IN C_BPartner_ID numeric,
	IN isCustomer character(1),
	IN IsPharmaManufacturerPermission character(1),
	IN IsPharmaWholesalePermission character(1),
	IN IsPharmaAgentPermission character(1),
	IN IsPharmaciePermission character(1),
	IN IsVeterinaryPharmacyPermission character(1)
	
);
CREATE OR REPLACE FUNCTION "de.metas.vertical.pharma".pharma_permission_control_report
(
	IN C_BPartner_ID numeric,
	IN isCustomer character(1),
	IN IsPharmaManufacturerPermission character(1),
	IN IsPharmaWholesalePermission character(1),
	IN IsPharmaAgentPermission character(1),
	IN IsPharmaciePermission character(1),
	IN IsVeterinaryPharmacyPermission character(1)
	
) RETURNS TABLE
(
	Name character varying, 
	Name2 character varying, 
	Value character varying, 
	IsPharmaManufacturerPermission character(1), 
	IsPharmaWholesalePermission character(1), 
	IsPharmaAgentPermission character(1), 
	IsPharmaciePermission character(1), 
	IsVeterinaryPharmacyPermission character(1), 
	PermissionPharma character(1), 
	PermissionChangeDate timestamp without time zone,
	isCustomer character(1), isVendor character(1), unionType text,
	param_bp_name character varying, 
	param_customer character(1), param_manufacturer character(1),param_wholesale character(1),
	param_agent character(1), param_pharmacie character(1), param_veterinary character(1)
)
AS 
$$
SELECT 
	Name, 
	Name2, 
	Value, 
	IsPharmaManufacturerPermission, 
	IsPharmaWholesalePermission, 
	IsPharmaAgentPermission, 
	IsPharmaciePermission, 
	IsVeterinaryPharmacyPermission, 
	PermissionPharma, 
	PermissionChangeDate,
	isCustomer, isVendor, unionType,
	--parameters
	(SELECT name FROM C_BPartner WHERE C_BPartner_ID = $1) AS param_bp_name,
	$2 AS param_customer,
	$3 AS param_manufacturer, $4 AS param_wholesale, $5 AS param_agent, $6 AS param_pharmacie, $7 AS param_veterinary
	
	

FROM 
	(SELECT
		bp.C_BPartner_ID,
		bp.Name,
		bp.Name2,
		bp.Value,
		bp.IsPharmaManufacturerPermission, --Manufacturing Permission
		bp.IsPharmaWholesalePermission, --Wholesale Permission
		bp.IsPharmaAgentPermission, --Agent
		bp.IsPharmaciePermission, --Pharmacy Permission
		bp.IsVeterinaryPharmacyPermission, --Veterinary Permission
		--
		bp.ShipmentPermissionPharma as PermissionPharma,
		bp.ShipmentPermissionChangeDate as PermissionChangeDate,
		--
		bp.isCustomer,
		bp.isVendor,
		'Customer' as unionType

		FROM C_BPartner bp
		WHERE isCustomer = 'Y'


	UNION ALL
		SELECT
		
		bp.C_BPartner_ID,
		bp.Name,
		bp.Name2,
		bp.Value,
		bp.IsPharmaVendorManufacturerPermission, --Manufacturing Permission
		bp.IsPharmaVendorWholesalePermission, --Wholesale Permission
		bp.IsPharmaVendorAgentPermission, --Agent
		null as IsPharmaciePermission, --Pharmacy Permission
		null as IsVeterinaryPharmacyPermission, --Veterinary Permission
		--
		bp.ReceiptPermissionPharma as PermissionPharma,
		bp.ReceiptPermissionChangeDate as PermissionChangeDate,
		--
		bp.isCustomer,
		bp.isVendor,
		'Vendor'  as unionType

		FROM C_BPartner bp

		WHERE isVendor = 'Y'
	)u

WHERE 
	(CASE WHEN $1 IS NULL THEN TRUE ELSE C_BPartner_ID = $1 END) 	
	AND (CASE WHEN $2 IS NULL THEN TRUE  
		WHEN $2 = 'Y' THEN unionType = 'Customer' 
		WHEN $2 = 'N' THEN unionType = 'Vendor' 
		END)
	AND (CASE WHEN $3 IS NULL THEN TRUE ELSE IsPharmaManufacturerPermission = $3 END)
	AND (CASE WHEN $4 IS NULL THEN TRUE ELSE IsPharmaWholesalePermission = $4 END)
	AND (CASE WHEN $5 IS NULL THEN TRUE ELSE IsPharmaAgentPermission = $5 END)
	AND (CASE WHEN $6 IS NULL THEN TRUE ELSE IsPharmaciePermission = $6 END)
	AND (CASE WHEN $7 IS NULL THEN TRUE ELSE IsVeterinaryPharmacyPermission = $7 END)
	
ORDER BY PermissionChangeDate DESC, name ASC
$$
LANGUAGE sql STABLE;