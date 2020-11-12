-- 2020-11-12T15:10:13.300Z
-- URL zum Konzept
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (1000000,1000000,'L',TO_TIMESTAMP('2020-11-12 17:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Y','N',540083,'Testgerät Auftrag',TO_TIMESTAMP('2020-11-12 17:10:11','YYYY-MM-DD HH24:MI:SS'),100,'HU_TestAppliance',0,0)
;

-- 2020-11-12T15:29:17.127Z
-- URL zum Konzept
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2020-11-12 17:29:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540083,540041,'Test Appliance',TO_TIMESTAMP('2020-11-12 17:29:16','YYYY-MM-DD HH24:MI:SS'),100,'TestAppliance')
;

-- 2020-11-12T15:32:00.559Z
-- URL zum Konzept
INSERT INTO M_AttributeSet (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsGuaranteeDate,IsGuaranteeDateMandatory,IsInstanceAttribute,IsLot,IsLotMandatory,IsSerNo,IsSerNoMandatory,MandatoryType,M_AttributeSet_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2020-11-12 17:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540009,'TestAppliance',TO_TIMESTAMP('2020-11-12 17:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-12T15:32:00.616Z
-- URL zum Konzept
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540009 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2020-11-12T15:32:32.149Z
-- URL zum Konzept
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2020-11-12 17:32:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',540083,540009,540009,10,TO_TIMESTAMP('2020-11-12 17:32:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-12T15:32:32.211Z
-- URL zum Konzept
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540009 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2020-11-12T15:32:32.281Z
-- URL zum Konzept
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540009 AND IsInstanceAttribute='Y'	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2020-11-12T15:40:35.152Z
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2020-11-12 17:40:34','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','N','N','N','N',540083,540051,100,'NONE',9090,TO_TIMESTAMP('2020-11-12 17:40:34','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2020-11-12T15:41:45.327Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET PropagationType='TOPD',Updated=TO_TIMESTAMP('2020-11-12 17:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540051
;

-- 2020-11-12T15:41:58.251Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2020-11-12 17:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540051
;

