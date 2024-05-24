-- Run mode: SWING_CLIENT

-- Value: de.metas.invoice.process.OverrideDueDate
-- 2024-02-16T17:42:20.818Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545373,0,TO_TIMESTAMP('2024-02-16 17:42:20.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Das Feld Fälligkeitsdatum kann nur überschrieben werden, wenn für die entsprechende Zahlungsbedingung das Kennzeichen Fälligkeitsdatum überschreiben erlauben auf Ja gesetzt ist.','E',TO_TIMESTAMP('2024-02-16 17:42:20.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoice.process.OverrideDueDate')
;

-- 2024-02-16T17:42:20.856Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545373 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.invoice.process.OverrideDueDate
-- 2024-02-16T17:42:33.004Z
UPDATE AD_Message_Trl SET MsgText='Due Date field can be overridden only when the corresponding payment term has flag Allow override due date set on Yes.',Updated=TO_TIMESTAMP('2024-02-16 17:42:33.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545373
;

-- Run mode: SWING_CLIENT

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Datum Fälligkeit
-- Column: C_Invoice.DueDate
-- 2024-02-16T19:23:19.116Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-02-16 19:23:19.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=725185
;

