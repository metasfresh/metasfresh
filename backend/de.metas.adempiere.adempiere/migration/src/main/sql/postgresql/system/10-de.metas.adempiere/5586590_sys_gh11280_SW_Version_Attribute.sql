
-- 2021-06-14T09:18:10.975Z
-- URL zum Konzept
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2021-06-14 11:18:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540096,'SW_Version',TO_TIMESTAMP('2021-06-14 11:18:10','YYYY-MM-DD HH24:MI:SS'),100,'SW_Version')
;

-- 2021-06-14T09:18:13.419Z
-- URL zum Konzept
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2021-06-14 11:18:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540096
;

-- 2021-06-14T09:18:13.421Z
-- URL zum Konzept
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540096)
;

-- 2021-06-14T09:18:18.133Z
-- URL zum Konzept
UPDATE M_Attribute SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-14 11:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540096
;


-- 2021-06-14T12:17:36.921Z
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,0,TO_TIMESTAMP('2021-06-14 15:17:35','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','N','N','N','N',540096,540076,101,'NONE',130,TO_TIMESTAMP('2021-06-14 15:17:35','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-06-14T12:17:50.159Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET IsOnlyIfInProductAttributeSet='Y',Updated=TO_TIMESTAMP('2021-06-14 15:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540076
;




-- 2021-06-14T16:08:45.082Z
-- URL zum Konzept
UPDATE M_Attribute SET Name='Softwareversion',Updated=TO_TIMESTAMP('2021-06-14 19:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540096
;

