-- 2026-06-24
-- AD_Messages for the dummy-GRAI PO-reference prerequisite validation.
-- Surfaced when a sales order's customer requires dummy GRAIs (GRAIRequired = YesWithDummyGRAIs) but the
-- order's PO reference cannot form a valid dummy-GRAI serial prefix (missing after trim, or longer than 10
-- characters). Used by the sales-order change/completion interceptors, the picking job-open check, and the
-- picking-completion backstop, so the same operator-facing text shows everywhere.

-- AD_Message: DummyGRAISerialPrefixTooLong
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'E', 'de.metas.handlingunits.grai.DummyGRAISerialPrefixTooLong',
        'Die Bestellreferenz "{0}" ist zu lang für die GRAI-Erzeugung (max. 10 Zeichen). Bitte die Bestellreferenz im Auftrag entsprechend kürzen.',
        'de.metas.handlingunits', 545765 /*From ID Server*/);
UPDATE AD_Message SET ErrorCode='GRAI_POREFERENCE_TOO_LONG', Updated=TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545765;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545765
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The PO reference "{0}" is too long for GRAI generation (max. 10 characters). Please shorten the PO reference on the order.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545765;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545765;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-24 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545765;

-- AD_Message: DummyGRAIPOReferenceMissing
INSERT INTO AD_Message (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        MsgType, Value, MsgText, EntityType, AD_Message_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
        'E', 'de.metas.handlingunits.grai.DummyGRAIPOReferenceMissing',
        'Auftrag {0}: Es ist keine Bestellreferenz hinterlegt. Für die GRAI-Erzeugung wird eine Bestellreferenz (max. 10 Zeichen) benötigt.',
        'de.metas.handlingunits', 545766 /*From ID Server*/);
UPDATE AD_Message SET ErrorCode='GRAI_POREFERENCE_MISSING', Updated=TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545766;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545766
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Order {0}: no PO reference is set. GRAI generation requires a PO reference (max. 10 characters).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545766;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545766;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-24 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545766;
