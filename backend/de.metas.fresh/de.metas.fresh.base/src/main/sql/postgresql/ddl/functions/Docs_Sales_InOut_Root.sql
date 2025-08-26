
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root (IN p_record_id numeric, IN p_ad_language Character Varying (6));


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root (IN p_record_id numeric, IN p_ad_language Character Varying (6))
RETURNS TABLE
    (
    AD_User_ID numeric(10,0),
    AD_Org_ID numeric(10,0),
    M_InOut_ID numeric(10,0),
    DocStatus Character (2),
    C_BPartner_ID numeric(10,0),
    C_BPartner_Location_ID numeric (10,0),
    PrintName Character Varying(60),
    AD_Language Text,
    IsHidePackingMaterialInShipmentPrint Character (1),
    email Character Varying(50),
    displayhu text,
    issourcesupplycert character(1)
    ) AS
$$SELECT
	io.ad_user_id,
	io.ad_org_id,
	io.m_inout_id,
	io.docstatus,
	io.c_bpartner_id,
	io.c_bpartner_location_id,
	CASE WHEN io.DocStatus = 'DR'
		THEN dt.printname
		ELSE COALESCE(dtt.printname,dt.printname)
	END AS printname,
	CASE WHEN io.DocStatus IN ('DR', 'IP') THEN 'de_CH' ELSE p_ad_language END AS AD_Language,
	isHidePackingMaterialInShipmentPrint,
	mb.email,
	CASE
		WHEN
		EXISTS(
			SELECT iol1.M_HU_PI_Item_Product_ID
			FROM M_InOutLine iol1
			WHERE io.M_InOut_ID = iol1.M_InOut_ID
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	bp.issourcesupplycert
FROM
	M_InOut io
	JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language
	--get the email from AD_MailConfig for org, docbasetype and docsubtype, with fallback to org, docbasetype
	LEFT OUTER JOIN (
		SELECT mb.email, io.M_InOut_ID
		FROM M_InOut io
		INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
		LEFT OUTER JOIN AD_MailConfig mc1 ON io.AD_Org_ID = mc1.AD_Org_ID AND mc1.DocBaseType = dt.DocBaseType AND mc1.DocSubType = dt.DocSubType
		LEFT OUTER JOIN AD_MailConfig mc2 ON io.AD_Org_ID = mc2.AD_Org_ID AND mc2.DocBaseType = dt.DocBaseType AND mc2.DocSubType is null
		LEFT OUTER JOIN AD_MailConfig mc3 ON io.AD_Org_ID = mc3.AD_Org_ID AND mc3.DocBaseType is null AND mc3.DocSubType is null
		LEFT OUTER JOIN AD_Mailbox mb ON COALESCE(COALESCE(mc1.AD_Mailbox_ID, mc2.AD_Mailbox_ID),mc3.AD_Mailbox_ID) = mb.AD_Mailbox_ID

		WHERE io.M_InOut_ID = p_record_id
		LIMIT 1
	)mb ON mb.M_InOut_ID = io.M_InOut_ID

WHERE
	io.M_InOut_ID = p_record_id

$$
LANGUAGE sql STABLE
;

