-- Run mode: SWING_CLIENT

-- 2025-10-07T14:55:38.573Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValuesOrderBy,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'V','S',TO_TIMESTAMP('2025-10-07 14:55:38.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','Y','N','N','N','N','N','N',540174,'Externer Barcode',TO_TIMESTAMP('2025-10-07 14:55:38.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ExternalBarcode',0,0)
;

-- 2025-10-07T14:56:37.403Z
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (0,0,TO_TIMESTAMP('2025-10-07 14:56:37.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540026,'Y','Y','Y','N','N','Y',540174,540130,100,'NONE',9140,TO_TIMESTAMP('2025-10-07 14:56:37.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

-- 2025-10-07T14:56:49.997Z
UPDATE M_HU_PI_Attribute SET UseInASI='N',Updated=TO_TIMESTAMP('2025-10-07 14:56:49.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540130
;

