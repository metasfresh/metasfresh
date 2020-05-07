-- View: EDI_M_InOutLine_HU_IPA_SSCC18_v

-- DROP VIEW IF EXISTS EDI_M_InOutLine_HU_IPA_SSCC18_v;

CREATE OR REPLACE VIEW EDI_M_InOutLine_HU_IPA_SSCC18_v AS
SELECT DISTINCT ON (ioh.Record_ID, ioh.M_HU_ID)
	ioh.Record_ID AS M_InOutLine_ID,
	ioh.M_HU_ID,
	hip.AttributeName,
	hip.Value
FROM M_InOutLine iol
LEFT JOIN M_HU_Assignment ioh ON (ioh.AD_Table_ID=320 /* M_InOutLine_ID */ and ioh.Record_ID=iol.M_InOutLine_ID)
LEFT JOIN M_HU_Instance_Properties_v hip ON hip.M_HU_ID=ioh.M_HU_ID
WHERE hip.AttributeName='SSCC18'
;

