-- Run mode: SWING_CLIENT

-- Name: C_BPartner_TourVersionLine OR M_TourVersion_ID Is NULL
-- 2025-10-29T09:09:52.134Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540754,'@M_TourVersion_ID/NULL@  Is NULL OR C_BPartner.C_BPartner_ID in ( select vl.C_BPartner_ID from M_TourVersionLine vl where vl.M_TourVersion_ID=@M_TourVersion_ID@ )',TO_TIMESTAMP('2025-10-29 09:09:51.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_BPartner_TourVersionLine OR M_TourVersion_ID Is NULL','S',TO_TIMESTAMP('2025-10-29 09:09:51.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_DeliveryDay.C_BPartner_ID
-- 2025-10-29T09:11:11.839Z
UPDATE AD_Column SET AD_Val_Rule_ID=540754,Updated=TO_TIMESTAMP('2025-10-29 09:11:11.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=545446
;

