-- Run mode: SWING_CLIENT

-- 2025-10-01T14:18:04.327Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-10-01 14:18:03.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y',540011,'Inventur',TO_TIMESTAMP('2025-10-01 14:18:03.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'inventory')
;

-- 2025-10-01T14:18:04.346Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.Mobile_Application_ID=540011 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2025-10-01T14:36:21.892Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-01 14:36:21.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540011
;

-- 2025-10-01T14:36:22.095Z
/* DDL */  select update_Mobile_Application_TRLs(540011,'de_CH')
;

-- 2025-10-01T14:36:23.236Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-01 14:36:23.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540011
;

-- 2025-10-01T14:36:23.237Z
/* DDL */  select update_Mobile_Application_TRLs(540011,'de_DE')
;

-- 2025-10-01T14:36:43.198Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Inventory',Updated=TO_TIMESTAMP('2025-10-01 14:36:43.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540011
;

-- 2025-10-01T14:36:43.200Z
UPDATE Mobile_Application base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM Mobile_Application_Trl trl  WHERE trl.Mobile_Application_ID=base.Mobile_Application_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T14:36:43.205Z
/* DDL */  select update_Mobile_Application_TRLs(540011,'en_US')
;

