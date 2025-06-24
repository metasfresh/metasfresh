-- Run mode: WEBUI

-- 2025-06-23T15:39:12.314Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2025-06-23 15:39:12.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','Y','N','N','N','N','N','N',540165,'Entgeltminderung-Code',TO_TIMESTAMP('2025-06-23 15:39:12.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Entgeltminderung-Code')
;

-- 2025-06-23T15:40:43.228Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2025-06-23 15:40:43.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','Y','N','N','N','N','N','N',540166,'Entgeltminderung-Text',TO_TIMESTAMP('2025-06-23 15:40:43.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Entgeltminderung-Text')
;

-- Run mode: WEBUI

-- 2025-06-23T15:44:02.821Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-06-23 15:44:02.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540165,540019,540092,20,TO_TIMESTAMP('2025-06-23 15:44:02.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-23T15:44:02.823Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540019 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2025-06-23T15:44:02.824Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540019 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- 2025-06-23T15:44:11.307Z
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,M_AttributeUse_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-06-23 15:44:11.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',540166,540019,540093,30,TO_TIMESTAMP('2025-06-23 15:44:11.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-23T15:44:11.308Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=540019 AND IsInstanceAttribute='N' AND (EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- 2025-06-23T15:44:11.309Z
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=540019 AND IsInstanceAttribute='Y' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;
