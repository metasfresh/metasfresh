-- 2019-10-23T17:40:06.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product SET IsStocked='N', Name='Provisionspunkt',Updated=TO_TIMESTAMP('2019-10-23 19:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Product_ID=540420
;

-- 2019-10-23T17:40:06.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product_Trl SET CustomerLabelName=NULL, Description=NULL, DocumentNote=NULL, Ingredients=NULL, Name='Provisionspunkt', IsTranslated='Y' WHERE M_Product_ID=540420
;

-- 2019-10-23T17:40:06.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE A_Asset SET Name=SUBSTR((SELECT bp.Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=A_Asset.C_BPartner_ID) || ' - ' || p.Name,1,60),Description=p.Description FROM M_Product p WHERE p.M_Product_ID=A_Asset.M_Product_ID AND A_Asset.IsActive='Y' AND A_Asset.M_Product_ID=540420
;

-- 2019-10-23T17:40:21.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product_Trl SET Name='Commission point',Updated=TO_TIMESTAMP('2019-10-23 19:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Product_ID=540420
;

UPDATE M_Product SET Value='COMMISSION_POINT' WHERE M_Product_ID=540420;
