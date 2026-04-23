-- Run mode: WEBUI

-- 2026-03-05T18:02:41.209Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValuesOrderBy,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'V','S',TO_TIMESTAMP('2026-03-05 18:02:41.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','Y','N','N','N','N','N','N',540192,'GRAI',TO_TIMESTAMP('2026-03-05 18:02:41.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GRAI')
;

-- 2026-03-05T18:03:34.354Z
-- Guard: M_HU_PI_Version_ID=2001855 may not exist in environments where packing instructions were deleted
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM M_HU_PI_Version WHERE M_HU_PI_Version_ID = 2001855) THEN
        INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2026-03-05 18:03:34.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540027,'Y','Y','N','N','N','N',540192,540139,2001855,'NONE',200,TO_TIMESTAMP('2026-03-05 18:03:34.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y');

        -- 2026-03-05T18:03:45.748Z
        UPDATE M_HU_PI_Attribute SET UseInASI='N',Updated=TO_TIMESTAMP('2026-03-05 18:03:45.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540139;
    END IF;
END $$;
