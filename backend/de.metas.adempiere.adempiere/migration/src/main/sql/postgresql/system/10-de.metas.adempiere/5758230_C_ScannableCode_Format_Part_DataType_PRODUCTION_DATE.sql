-- Run mode: SWING_CLIENT

-- Reference: C_ScannableCode_Format_Part_DataType
-- Value: PRODUCTION_DATE
-- ValueName: ProductionDate
-- 2025-06-20T11:30:12.726Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541944,543927,TO_TIMESTAMP('2025-06-20 11:30:12.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Herstelldatum',TO_TIMESTAMP('2025-06-20 11:30:12.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PRODUCTION_DATE','ProductionDate')
;

-- 2025-06-20T11:30:12.816Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543927 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_ScannableCode_Format_Part_DataType -> PRODUCTION_DATE_ProductionDate
-- 2025-06-20T11:30:23.326Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-20 11:30:23.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543927
;

-- Reference Item: C_ScannableCode_Format_Part_DataType -> PRODUCTION_DATE_ProductionDate
-- 2025-06-20T11:30:26.490Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-20 11:30:26.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543927
;

-- Reference Item: C_ScannableCode_Format_Part_DataType -> PRODUCTION_DATE_ProductionDate
-- 2025-06-20T11:30:32.256Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Production Date',Updated=TO_TIMESTAMP('2025-06-20 11:30:32.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543927
;

-- 2025-06-20T11:30:32.257Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

