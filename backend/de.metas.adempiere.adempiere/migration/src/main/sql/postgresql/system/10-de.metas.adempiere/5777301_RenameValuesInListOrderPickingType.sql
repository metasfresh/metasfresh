-- Reference: OrderPickingType List
-- Value: MP
-- ValueName: Multiple
-- 2025-11-18T07:13:18.677Z
UPDATE AD_Ref_List SET Name='Multi',Updated=TO_TIMESTAMP('2025-11-18 07:13:18.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544068
;

-- 2025-11-18T07:13:18.683Z
UPDATE AD_Ref_List_Trl trl SET Name='Multi' WHERE AD_Ref_List_ID=544068 AND AD_Language='de_DE'
;

-- Reference Item: OrderPickingType List -> MP_Multiple
-- 2025-11-18T07:13:30.130Z
UPDATE AD_Ref_List_Trl SET Name='Multi',Updated=TO_TIMESTAMP('2025-11-18 07:13:30.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544068
;

-- 2025-11-18T07:13:30.131Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: OrderPickingType List -> MP_Multiple
-- 2025-11-18T07:13:39.892Z
UPDATE AD_Ref_List_Trl SET Name='Multi',Updated=TO_TIMESTAMP('2025-11-18 07:13:39.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544068
;

-- 2025-11-18T07:13:39.893Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: OrderPickingType List
-- Value: SG
-- ValueName: Single
-- 2025-11-18T07:13:45.163Z
UPDATE AD_Ref_List SET Name='Single',Updated=TO_TIMESTAMP('2025-11-18 07:13:45.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544067
;

-- 2025-11-18T07:13:45.164Z
UPDATE AD_Ref_List_Trl trl SET Name='Single' WHERE AD_Ref_List_ID=544067 AND AD_Language='de_DE'
;

-- Reference Item: OrderPickingType List -> SG_Single
-- 2025-11-18T07:13:51.836Z
UPDATE AD_Ref_List_Trl SET Name='Single',Updated=TO_TIMESTAMP('2025-11-18 07:13:51.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544067
;

-- 2025-11-18T07:13:51.837Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

