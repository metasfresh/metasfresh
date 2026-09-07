-- Run mode: SWING_CLIENT

-- Reference Item: Reason for without charge -> W_Warranty
-- 2026-07-24T07:24:40.339Z
UPDATE AD_Ref_List_Trl SET Name='Gewährleistung',Updated=TO_TIMESTAMP('2026-07-24 07:24:40.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543932
;

-- 2026-07-24T07:24:40.413Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> W_Warranty
-- 2026-07-24T07:24:56.604Z
UPDATE AD_Ref_List_Trl SET Name='Gewährleistung',Updated=TO_TIMESTAMP('2026-07-24 07:24:56.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543932
;

-- 2026-07-24T07:24:56.674Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> W_Warranty
-- 2026-07-24T07:25:12.205Z
UPDATE AD_Ref_List_Trl SET Name='Warranty',Updated=TO_TIMESTAMP('2026-07-24 07:25:12.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543932
;

-- 2026-07-24T07:25:12.274Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> F_FullService
-- 2026-07-24T07:25:55.750Z
UPDATE AD_Ref_List_Trl SET Name='Servicevertrag',Updated=TO_TIMESTAMP('2026-07-24 07:25:55.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543934
;

-- 2026-07-24T07:25:55.826Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> F_FullService
-- 2026-07-24T07:26:11.607Z
UPDATE AD_Ref_List_Trl SET Name='Servicevertrag',Updated=TO_TIMESTAMP('2026-07-24 07:26:11.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543934
;

-- 2026-07-24T07:26:11.687Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

