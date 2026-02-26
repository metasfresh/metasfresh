-- Run mode: SWING_CLIENT

-- Value: de.metas.material.dispo.SupplyRequiredDecreasedIncomplete
-- 2025-07-01T07:04:39.544Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545565,0,TO_TIMESTAMP('2025-07-01 07:04:39.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','Y','Mengenänderungen können nicht automatisch in die Folgebelege übertragen werden, da diese bereits in Bearbeitung sind. Es sind manuelle Anpassungen erforderlich. Noch zu ändernde Restmenge: {}.','E',TO_TIMESTAMP('2025-07-01 07:04:39.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo.SupplyRequiredDecreaseFailed')
;

-- 2025-07-01T07:04:39.547Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545565 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.material.dispo.SupplyRequiredDecreasedIncomplete
-- 2025-07-01T07:04:57.190Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Quantity changes cannot be automatically transferred to follow-on documents as these are already being processed. Manual adjustments are required. Remaining quantity to be changed: {}.',Updated=TO_TIMESTAMP('2025-07-01 07:04:57.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545565
;

-- 2025-07-01T07:04:57.191Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

