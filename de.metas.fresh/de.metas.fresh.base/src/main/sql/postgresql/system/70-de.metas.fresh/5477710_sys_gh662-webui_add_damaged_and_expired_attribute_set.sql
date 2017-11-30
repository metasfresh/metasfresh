-- 2017-11-20T17:08:31.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeSet (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsGuaranteeDate,IsGuaranteeDateMandatory,IsInstanceAttribute,IsLot,IsLotMandatory,IsSerNo,IsSerNoMandatory,MandatoryType,M_AttributeSet_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-11-20 17:08:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540004,'Bruch_und_Verfall',TO_TIMESTAMP('2017-11-20 17:08:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T17:08:31.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540004 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2017-11-20T17:08:39.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-11-20 17:08:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',540031,540004,540002,10,TO_TIMESTAMP('2017-11-20 17:08:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T17:08:39.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540004 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2017-11-20T17:08:39.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540004 AND IsInstanceAttribute='Y'	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2017-11-20T17:08:46.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-11-20 17:08:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',540033,540004,540003,20,TO_TIMESTAMP('2017-11-20 17:08:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T17:08:46.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540004 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2017-11-20T17:08:46.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540004 AND IsInstanceAttribute='Y'	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

UPDATE M_AttributeSet SET AD_Client_ID=0, AD_Org_ID=0 WHERE M_AttributeSet_ID=540004;
UPDATE M_AttributeUse SET AD_Client_ID=0, AD_Org_ID=0 WHERE M_AttributeUse_ID=540002;
UPDATE M_AttributeUse SET AD_Client_ID=0, AD_Org_ID=0 WHERE M_AttributeUse_ID=540003;

-- FIX UOM of HU_expired
UPDATE M_Attribute SET C_UOM_ID=null WHERE M_Attribute_ID=540033;