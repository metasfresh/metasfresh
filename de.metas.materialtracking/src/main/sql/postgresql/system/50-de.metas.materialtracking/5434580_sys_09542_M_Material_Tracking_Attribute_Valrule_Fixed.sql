-- 02.12.2015 14:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS (
	SELECT 1
	FROM M_Material_Tracking mt
	INNER JOIN M_Attribute a on a.M_Attribute_ID=M_AttributeValue.M_Attribute_ID
	INNER JOIN C_Flatrate_Term term on mt.C_Flatrate_Term_ID = term.C_Flatrate_Term_ID
	WHERE a.Value=''M_Material_Tracking_ID''
	AND mt.M_Material_Tracking_ID::VARCHAR=M_AttributeValue.Value
	AND mt.C_BPartner_ID=@C_BPartner_ID@
	AND mt.M_Product_ID=@M_Product_ID@
	AND mt.Processed = ''N''
	AND term.DocStatus = ''CO''
)',Updated=TO_TIMESTAMP('2015-12-02 14:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540259
;

