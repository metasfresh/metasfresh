-- Run mode: SWING_CLIENT

-- Value: de.metas.handlingunits.impl.HUHasPackages
-- 2025-10-22T14:47:13.411Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545599,0,TO_TIMESTAMP('2025-10-22 14:47:13.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','You can’t destroy handling unit {0} because it’s still linked to these packages: {1}.','E',TO_TIMESTAMP('2025-10-22 14:47:13.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.impl.HUHasPackages')
;

-- 2025-10-22T14:47:13.415Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545599 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.impl.HUHasPackages
-- 2025-10-22T14:47:48.995Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Handling Unit {0} kann nicht zerstört werden, da sie noch mit folgenden Paketen verknüpft ist: {1}.',Updated=TO_TIMESTAMP('2025-10-22 14:47:48.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545599
;

-- 2025-10-22T14:47:48.997Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.impl.HUHasPackages
-- 2025-10-22T14:48:00.822Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Handling Unit {0} kann nicht zerstört werden, da sie noch mit folgenden Paketen verknüpft ist: {1}.',Updated=TO_TIMESTAMP('2025-10-22 14:48:00.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545599
;

-- 2025-10-22T14:48:00.823Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

