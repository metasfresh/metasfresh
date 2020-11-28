drop function if exists "de.metas.handlingunits".addMissingHUAttribute(varchar);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".addMissingHUAttribute(p_AttributeName varchar)
RETURNS void
AS
$BODY$
/*
 * Function is adding M_HU_Attribute, specified by attribute code (M_Attribute.Value)
 * to all M_HUs which don't have it.
 */
DECLARE
	rowsInserted integer;
BEGIN
	INSERT INTO M_HU_Attribute
	(
		m_hu_attribute_id -- ID
		, m_attribute_id
		, m_hu_id
		--
		, value
		, valueInitial
		--
		, valueNumber
		, valueNumberInitial
		--
		, m_hu_pi_attribute_id
		, ad_client_id
		, ad_org_id
		, created
		, createdby
		, updated
		, updatedby
		, isactive
	)
	SELECT
		nextval('adempiere.m_hu_attribute_seq') as m_hu_attribute_id
		, pia.m_attribute_id
		, hu.m_hu_id
		--
		, null as value
		, null as valueInitial
		--
		, null as valueNumber
		, null as valueNumberInitial
		--
		, pia.m_hu_pi_attribute_id
		, hu.ad_client_id
		, hu.ad_org_id
		, now() as created
		, 0 as createdby
		, now() as updated
		, 0 as updatedby
		, 'Y' as isactive
	
	FROM M_HU hu
	inner join "de.metas.handlingunits".M_HU_PI_Attribute_Actual_v pia ON (pia.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID)
	where
		-- M_HU_Attribute Does not already exist
		not exists (select 1 from M_HU_Attribute hua where hua.M_HU_ID=hu.M_HU_ID and hua.M_HU_PI_Attribute_ID=pia.M_HU_PI_Attribute_ID)
		-- Only for given M_Attribute
		and pia.M_Attribute_ValueName=p_AttributeName
	-- order by hu.M_HU_ID
	;

	-- Log how many rows we inserted
	GET DIAGNOSTICS rowsInserted = ROW_COUNT;
	raise notice 'Created % M_HU_Attribute(s) for M_Attribute.Value=%', rowsInserted, p_AttributeName;
END;
$BODY$
	LANGUAGE plpgsql VOLATILE
	COST 100;




