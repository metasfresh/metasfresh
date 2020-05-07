-- View: M_HU_Instance_Properties_v

-- DROP VIEW IF EXISTS M_HU_Instance_Properties_v;

CREATE OR REPLACE VIEW M_HU_Instance_Properties_v AS 
	SELECT 
			hu.ad_client_id, hu.ad_org_id, hu.isactive, hu.created, hu.createdby, hu.updated, hu.updatedby, -- standard columns
			pi.Name as piName, hua.m_attribute_id, hua.value, hua.valuenumber, a.name as AttributeName, hu.m_hu_id, doc.Name as documentName, doc.DocumentNo
	FROM M_HU hu
				LEFT JOIN m_hu_attribute hua ON hu.M_HU_ID=hua.M_HU_ID
				LEFT JOIN M_Attribute a ON a.M_Attribute_ID=hua.M_Attribute_ID
				INNER JOIN m_hu_pi_version pi ON pi.m_hu_pi_version_id=hu.m_hu_pi_version_id
				LEFT JOIN (
							   select distinct dt.Name, io.DocumentNo, iol_hu.M_HU_ID
							   from M_HU_Assignment iol_hu
											   inner join M_InOutLine iol ON iol.M_InOutLine_ID=iol_hu.Record_ID
											   inner join M_InOut io ON io.M_InOut_ID=iol.M_InOut_ID
											   inner join C_DocType dt ON dt.C_DocType_ID=io.C_DocType_ID
							   where iol_hu.AD_Table_ID=320 /* M_InOutLine_ID */
							   /* UNION
							   in future link other Documents as requried
							   */
				) doc ON doc.M_HU_ID=hua.M_HU_ID
;



GRANT ALL ON TABLE M_HU_Instance_Properties_v TO adempiere;
