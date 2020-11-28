DROP FUNCTION IF EXISTS "de.metas.vertical.pharma".pharma_permission_control_report
(
	IN C_BPartner_ID numeric,
	IN IsPharmaManufacturerPermission character(1),
	IN IsPharmaWholesalePermission character(1),
	IN IsPharmaAgentPermission character(1),
	IN IsPharmaciePermission character(1),
	IN IsVeterinaryPharmacyPermission character(1)
	
);
DROP FUNCTION IF EXISTS "de.metas.vertical.pharma".pharma_permission_control_report
(
	IN p_C_BPartner_ID numeric,
	IN p_isCustomer character(1),
	IN p_IsPharmaManufacturerPermission character(1),
	IN p_IsPharmaWholesalePermission character(1),
	IN p_IsPharmaAgentPermission character(1),
	IN p_IsPharmaciePermission character(1),
	IN p_IsVeterinaryPharmacyPermission character(1)
	
);
CREATE OR REPLACE FUNCTION "de.metas.vertical.pharma".pharma_permission_control_report
(
	IN p_C_BPartner_ID numeric,
	IN p_isCustomer character(1),
	IN p_IsPharmaManufacturerPermission character(1),
	IN p_IsPharmaWholesalePermission character(1),
	IN p_IsPharmaAgentPermission character(1),
	IN p_IsPharmaciePermission character(1),
	IN p_IsVeterinaryPharmacyPermission character(1)
	
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
	(SELECT name FROM C_BPartner WHERE C_BPartner_ID = p_C_BPartner_ID) AS param_bp_name,
	p_isCustomer AS param_customer,
	p_IsPharmaManufacturerPermission AS param_manufacturer, p_IsPharmaWholesalePermission AS param_wholesale, p_IsPharmaAgentPermission AS param_agent, p_IsPharmaciePermission AS param_pharmacie, p_IsVeterinaryPharmacyPermission AS param_veterinary
	
	

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
	(CASE WHEN p_C_BPartner_ID IS NULL THEN TRUE ELSE C_BPartner_ID = p_C_BPartner_ID END) 	
	AND (CASE WHEN p_isCustomer IS NULL THEN TRUE  
		WHEN p_isCustomer = 'Y' THEN unionType = 'Customer' 
		WHEN p_isCustomer = 'N' THEN unionType = 'Vendor' 
		END)
	AND (CASE WHEN p_IsPharmaManufacturerPermission IS NULL THEN TRUE ELSE IsPharmaManufacturerPermission = p_IsPharmaManufacturerPermission END)
	AND (CASE WHEN p_IsPharmaWholesalePermission IS NULL THEN TRUE ELSE IsPharmaWholesalePermission = p_IsPharmaWholesalePermission END)
	AND (CASE WHEN p_IsPharmaAgentPermission IS NULL THEN TRUE ELSE IsPharmaAgentPermission = p_IsPharmaAgentPermission END)
	AND (CASE WHEN p_IsPharmaciePermission IS NULL THEN TRUE ELSE IsPharmaciePermission = p_IsPharmaciePermission END)
	AND (CASE WHEN p_IsVeterinaryPharmacyPermission IS NULL THEN TRUE ELSE IsVeterinaryPharmacyPermission = p_IsVeterinaryPharmacyPermission END)
	
ORDER BY PermissionChangeDate DESC, name ASC
$$
LANGUAGE sql STABLE;