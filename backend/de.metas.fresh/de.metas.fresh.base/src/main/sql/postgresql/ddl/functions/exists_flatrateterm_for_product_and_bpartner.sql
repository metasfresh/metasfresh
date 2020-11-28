--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.exists_flatrateterm_for_product_and_bpartner(IN m_product_id numeric, IN c_bpartner_id numeric, IN startdate DATE, IN EndDate DATE );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.exists_flatrateterm_for_product_and_bpartner(IN m_product_id numeric, IN c_bpartner_id numeric, IN startdate DATE, IN EndDate DATE )
RETURNS BOOLEAN 
AS
$$
select exists(select 1 from C_FLatrate_Term ft
	JOIN C_FLatrate_Data fd on ft.C_Flatrate_Data_ID = fd.C_Flatrate_Data_ID AND fd.isActive = 'Y'
	JOIN C_FLatrate_Conditions fc on ft.C_FLatrate_Conditions_ID = fc.C_FLatrate_Conditions_ID and fc.Type_Conditions = 'Refundable' AND fc.isActive = 'Y'
	JOIN C_Flatrate_Matching fm on fm.C_FLatrate_Conditions_ID = fc.C_FLatrate_Conditions_ID AND fm.isActive = 'Y'
	JOIN M_Product p on p.m_product_id = $1 AND p.isActive = 'Y'
	WHERE fd.C_BPartner_ID = $2 AND
				(fm.M_Product_ID = p.M_Product_ID OR ft.M_Product_ID = p.M_Product_ID OR (fm.M_Product_Category_Matching_ID  = p. M_Product_Category_ID AND fm.M_Product_ID IS NULL))
				AND COALESCE( ft.EndDate >= $3, true )
				AND COALESCE( ft.StartDate <= $4, true )
				AND ft.isActive = 'Y'
				AND ft.docstatus IN ('CO','CL'))

$$
LANGUAGE sql STABLE;