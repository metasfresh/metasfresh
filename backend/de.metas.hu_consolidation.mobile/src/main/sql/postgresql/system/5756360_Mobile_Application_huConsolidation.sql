-- Run mode: SWING_CLIENT

-- 2025-05-30T14:42:43.844Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2025-05-30 14:42:43.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y',540010,'Verdichtung',TO_TIMESTAMP('2025-05-30 14:42:43.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'huConsolidation')
;

-- 2025-05-30T14:42:44.008Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.Mobile_Application_ID=540010 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2025-05-30T14:43:10.548Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-30 14:43:10.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540010
;

-- 2025-05-30T14:43:11.912Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-30 14:43:11.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540010
;

-- 2025-05-30T14:43:18.535Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='HU Consolidation',Updated=TO_TIMESTAMP('2025-05-30 14:43:18.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540010
;

-- 2025-05-30T14:43:18.537Z
UPDATE Mobile_Application base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM Mobile_Application_Trl trl  WHERE trl.Mobile_Application_ID=base.Mobile_Application_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

