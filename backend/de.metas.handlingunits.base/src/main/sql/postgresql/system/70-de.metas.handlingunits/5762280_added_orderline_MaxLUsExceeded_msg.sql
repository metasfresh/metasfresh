-- Run mode: SWING_CLIENT

-- Value: de.metas.quickinput.orderline.MaxLUsExceeded
-- 2025-08-07T14:10:01.271Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545571,0,TO_TIMESTAMP('2025-08-07 14:10:00.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Maximale Anzahl an LUs überschritten','E',TO_TIMESTAMP('2025-08-07 14:10:00.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.quickinput.orderline.MaxLUsExceeded')
;

-- 2025-08-07T14:10:01.275Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545571 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.quickinput.orderline.MaxLUsExceeded
-- 2025-08-07T14:10:35.176Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Maximum number of LUs exceeded',Updated=TO_TIMESTAMP('2025-08-07 14:10:35.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545571
;

-- 2025-08-07T14:10:35.178Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Field: Bestellung(181,D) -> Bestellposition(293,D) -> LU Anzahl
-- Column: C_OrderLine.QtyLU
-- 2025-08-07T15:34:41.584Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2025-08-07 15:34:41.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=750396
;

