-- 2019-12-04T16:05:18.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeSet (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsGuaranteeDate,IsGuaranteeDateMandatory,IsInstanceAttribute,IsLot,IsLotMandatory,IsSerNo,IsSerNoMandatory,MandatoryType,M_AttributeSet_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2019-12-04 17:05:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540005,'Mindesthaltbarkeitsdatum',TO_TIMESTAMP('2019-12-04 17:05:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-04T16:05:18.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540005 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2019-12-04T16:05:40.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2019-12-04 17:05:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',540020,540005,540007,10,TO_TIMESTAMP('2019-12-04 17:05:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-04T16:05:40.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540005 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2019-12-04T16:05:40.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540005 AND IsInstanceAttribute='Y'	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

