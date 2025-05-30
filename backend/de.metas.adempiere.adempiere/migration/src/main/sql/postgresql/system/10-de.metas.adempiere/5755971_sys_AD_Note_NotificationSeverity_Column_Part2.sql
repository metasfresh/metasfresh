
-- 2025-05-27T11:16:37.775Z
INSERT INTO t_alter_column values('ad_note','NotificationSeverity',null,'NOT NULL',null)
;






-- Reference: NotificationSeverity
-- Value: Warning
-- ValueName: Warning
-- 2025-05-28T18:16:36.872Z
UPDATE AD_Ref_List SET Name='WARNING',Updated=TO_TIMESTAMP('2025-05-28 18:16:36.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543919
;

-- 2025-05-28T18:16:36.876Z
UPDATE AD_Ref_List_Trl trl SET Name='WARNING' WHERE AD_Ref_List_ID=543919 AND AD_Language='de_DE'
;

-- Reference: NotificationSeverity
-- Value: Notice
-- ValueName: Notice
-- 2025-05-28T18:16:44.457Z
UPDATE AD_Ref_List SET Name='NOTICE',Updated=TO_TIMESTAMP('2025-05-28 18:16:44.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543918
;

-- 2025-05-28T18:16:44.458Z
UPDATE AD_Ref_List_Trl trl SET Name='NOTICE' WHERE AD_Ref_List_ID=543918 AND AD_Language='de_DE'
;

-- Reference: NotificationSeverity
-- Value: Error
-- ValueName: Error
-- 2025-05-28T18:16:52.686Z
UPDATE AD_Ref_List SET Name='ERROR',Updated=TO_TIMESTAMP('2025-05-28 18:16:52.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543920
;

-- 2025-05-28T18:16:52.686Z
UPDATE AD_Ref_List_Trl trl SET Name='ERROR' WHERE AD_Ref_List_ID=543920 AND AD_Language='de_DE'
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:34.658Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='FEHLER',Updated=TO_TIMESTAMP('2025-05-28 18:17:34.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:34.659Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:45.074Z
UPDATE AD_Ref_List_Trl SET Name='FEHLER',Updated=TO_TIMESTAMP('2025-05-28 18:17:45.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:45.075Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:49.701Z
UPDATE AD_Ref_List_Trl SET Name='ERROR',Updated=TO_TIMESTAMP('2025-05-28 18:17:49.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:49.703Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:59.777Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='FEHLER',Updated=TO_TIMESTAMP('2025-05-28 18:17:59.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:59.778Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:18:02.039Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-28 18:18:02.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543920
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:19:46.130Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='WARNHINWEIS',Updated=TO_TIMESTAMP('2025-05-28 18:19:46.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:19:46.132Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:19:50.835Z
UPDATE AD_Ref_List_Trl SET Name='WARNHINWEIS',Updated=TO_TIMESTAMP('2025-05-28 18:19:50.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:19:50.836Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:19:57.171Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='WARNING',Updated=TO_TIMESTAMP('2025-05-28 18:19:57.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:19:57.172Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:20:00.965Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='WARNHINWEIS',Updated=TO_TIMESTAMP('2025-05-28 18:20:00.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:20:00.966Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: NotificationSeverity
-- Value: Notice
-- ValueName: Notice
-- 2025-05-28T18:21:18.027Z
UPDATE AD_Ref_List SET Name='NACHRICHT',Updated=TO_TIMESTAMP('2025-05-28 18:21:18.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:18.029Z
UPDATE AD_Ref_List_Trl trl SET Name='NACHRICHT' WHERE AD_Ref_List_ID=543918 AND AD_Language='de_DE'
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:26.916Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-28 18:21:26.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543918
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:33.028Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='NOTICE',Updated=TO_TIMESTAMP('2025-05-28 18:21:33.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:33.029Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:37.737Z
UPDATE AD_Ref_List_Trl SET Name='NACHRICHT',Updated=TO_TIMESTAMP('2025-05-28 18:21:37.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:37.738Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:41.557Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='NACHRICHT',Updated=TO_TIMESTAMP('2025-05-28 18:21:41.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:41.559Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

