DROP FUNCTION IF EXISTS report.tu_label(IN M_HU_ID numeric);

CREATE FUNCTION report.tu_label(IN M_HU_ID numeric) RETURNS TABLE
	(
	org_address text, 
	name text,
	anbau Character Varying (60), 
	herkunft Character Varying (60),
	gewicht numeric,
	abgepackt timestamp with time zone,
	lotnummer Character Varying,
	certificate Character Varying,
	produzent Character Varying,
	adr Character Varying (60),
	is_suisse_garantie boolean,
	is_suisse boolean
	)
AS 
$$

SELECT

(
	SELECT  COALESCE(org_bp.name, '') || ', ' || COALESCE(org_l.Postal, '')|| ' '  || COALESCE(org_l.city, '')
			FROM AD_Org org

			-- INFO I NEED
			INNER JOIN AD_OrgInfo org_info ON org.AD_Org_ID = org_info.AD_Org_ID AND org_info.isActive = 'Y'
			INNER JOIN C_BPartner_Location org_bpl ON org_info.orgBP_Location_ID = org_bpl.C_BPartner_Location_ID AND org_bpl.isActive = 'Y'
			INNER JOIN C_Location org_l ON org_bpl.C_Location_ID = org_l.C_Location_ID AND org_l.isActive = 'Y'
			INNER JOIN c_bpartner org_bp ON org_bpl.C_Bpartner_ID = org_bp.C_BPartner_ID AND org_bp.isActive = 'Y'

			-- LINKING
			LEFT OUTER JOIN M_HU_Assignment huas ON tu.M_HU_ID = huas.M_HU_ID AND huas.AD_Table_ID IN (Get_Table_ID('M_ReceiptSchedule'), Get_Table_ID('PP_Order'), Get_Table_ID('PP_Order_BOMLine'),Get_Table_ID('M_InOutLine')) AND huas.isActive = 'Y'
			LEFT OUTER JOIN M_ReceiptSchedule rs ON huas.AD_Table_ID = Get_Table_ID('M_ReceiptSchedule') and rs.M_ReceiptSchedule_ID = huas.Record_ID and rs.AD_Org_ID = org.AD_Org_ID AND rs.isActive = 'Y'
			LEFT OUTER JOIN PP_Order ppo ON huas.AD_Table_ID = Get_Table_ID('PP_Order') and ppo.PP_Order_ID = huas.Record_ID AND ppo.AD_Org_ID = org.AD_Org_ID AND ppo.isActive = 'Y'
			LEFT OUTER JOIN PP_Order_BOMLine ppl on huas.AD_Table_ID = Get_Table_ID('PP_Order_BOMLine') and ppl.PP_Order_BOMLine_ID = huas.Record_ID AND ppl.AD_Org_ID = org.AD_Org_ID AND ppl.isActive = 'Y'
			LEFT OUTER JOIN M_InOutLine iol on huas.AD_Table_ID = Get_Table_ID('M_InOutLine') and iol.M_InOutLine_ID = huas.Record_ID AND iol.AD_Org_ID = org.AD_Org_ID AND iol.isActive = 'Y'


			WHERE CASE WHEN huas.M_HU_ID IS NOT NULL THEN huas.M_HU_ID =tu.M_HU_ID ELSE tu.AD_Org_ID = org.AD_Org_ID END
				AND org.isActive = 'Y'
			limit 1
	) as org_address,
	p.name || ' / ' || pt.name as name,
	tua_anb.name as anbau,
	tua_her.name as herkunft,
	(CASE WHEN val.qty IS NOT NULL THEN round(tua_gew.valuenumber / val.qty,3) ELSE tua_gew.valuenumber END) as gewicht,
	tu.created as abgepackt,
	val.huvalue as lotnummer,
	/*tua_cert.name*/ ''::character varying as certificate,
	/*tua_prod.name*/ null::character varying as produzent,
	tua_adr.name as adr,
	tua_sg.name ILIKE '%suisse%garantie%' as is_suisse_garantie,
	tua_her.name ILIKE '%schweiz%' as is_suisse
FROM
	M_HU tu
	JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID AND tus.isActive = 'Y'
	JOIN M_Product p ON tus.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = 'fr_CH' AND pt.isActive = 'Y'
	LEFT JOIN (
		SELECT 	sub_tua.M_HU_ID, sub_av.name
		FROM	M_HU_Attribute sub_tua
			INNER JOIN M_AttributeValue sub_av ON sub_tua.value = sub_av.value AND sub_tua.M_Attribute_ID = sub_av.M_Attribute_ID AND sub_av.isActive = 'Y'
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		WHERE 	sub_a.value = '1000004' -- Produktionsart
				AND sub_tua.isActive = 'Y'
	) tua_anb ON tu.M_HU_ID = tua_anb.M_HU_ID
	LEFT JOIN (
		SELECT 	sub_tua.M_HU_ID, sub_av.name
		FROM	M_HU_Attribute sub_tua
			INNER JOIN M_AttributeValue sub_av ON sub_tua.value = sub_av.value AND sub_tua.M_Attribute_ID = sub_av.M_Attribute_ID AND sub_av.isActive = 'Y'
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		WHERE 	sub_a.value = '1000001' -- Herkunft
				AND sub_tua.isActive = 'Y'
	) tua_her ON tu.M_HU_ID = tua_her.M_HU_ID
	LEFT JOIN (
		SELECT 	sub_tua.M_HU_ID, sub_tua.valuenumber
		FROM	M_HU_Attribute sub_tua
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		WHERE 	sub_a.value = 'WeightNet' -- Gewicht Netto
				AND sub_tua.isActive = 'Y'
	) tua_gew ON tu.M_HU_ID = tua_gew.M_HU_ID
	LEFT JOIN (
		SELECT sub_tua.M_HU_ID, sub_av.name
		FROM	M_HU_Attribute sub_tua
			INNER JOIN M_AttributeValue sub_av ON sub_tua.value = sub_av.value AND sub_tua.M_Attribute_ID = sub_av.M_Attribute_ID AND sub_av.isActive = 'Y'
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		WHERE sub_a.value = '1000015' -- ADR
			AND sub_av.value != '01' -- 'keine / leer'
			AND sub_tua.isActive = 'Y'
	) tua_adr ON tu.M_HU_ID = tua_adr.M_HU_ID
	LEFT JOIN (
		SELECT 	sub_tua.M_HU_ID, sub_av.name
		FROM	M_HU_Attribute sub_tua
			INNER JOIN M_AttributeValue sub_av ON sub_tua.value = sub_av.value AND sub_tua.M_Attribute_ID = sub_av.M_Attribute_ID AND sub_av.isActive = 'Y'
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		WHERE 	sub_a.value = '1000002' -- Label
			AND sub_av.name ILIKE '%suisse%garantie%'
			AND sub_tua.isActive = 'Y'
	) tua_sg ON tu.M_HU_ID = tua_sg.M_HU_ID
	LEFT JOIN "de.metas.handlingunits".get_TU_Values_From_Aggregation(tu.M_HU_ID) val on true
WHERE
	tu.M_HU_ID = $1
;

$$
  LANGUAGE sql STABLE;