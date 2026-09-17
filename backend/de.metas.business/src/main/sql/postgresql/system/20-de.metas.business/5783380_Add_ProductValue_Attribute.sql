-- Run mode: WEBUI

-- 2026-01-12T12:16:10.194Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValuesOrderBy,AttributeValueType,Created,CreatedBy,Description,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,'V','S',TO_TIMESTAMP('2026-01-12 12:16:10.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Project.Value','Y','N','N','N','Y','N','N','N','N','Y','N',540186,'Project Value',TO_TIMESTAMP('2026-01-12 12:16:10.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProjectValue')
;

-- 2026-01-12T16:01:54.160Z
UPDATE M_Attribute SET IsAttrDocumentRelevant='Y',Updated=TO_TIMESTAMP('2026-01-12 16:01:54.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE M_Attribute_ID=540186
;

-- 2026-01-12T16:13:20.684Z
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,SplitterStrategy_JavaClass_ID,Updated,UpdatedBy,UseInASI) VALUES (0,0,TO_TIMESTAMP('2026-01-12 16:13:20.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540027,'Y','Y','Y','N','N','N',540186,540135,100,'TOPD',9150,540017,TO_TIMESTAMP('2026-01-12 16:13:20.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y')
;

