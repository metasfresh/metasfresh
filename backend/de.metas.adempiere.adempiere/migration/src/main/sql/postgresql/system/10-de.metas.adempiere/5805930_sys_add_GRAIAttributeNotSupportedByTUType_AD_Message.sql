-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_Message_ID 545746 (GRAI scan guard: TU type does not declare the GRAI attribute slot)
--   AD_MigrationScript 5805930

-- GRAIAttributeNotSupportedByTUType: thrown at GRAI scan time when the resolved TU type's current PI version
-- does not declare the GRAI HU-attribute slot, so the scanned GRAI cannot be stored on the materialised TU.
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545746 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-03 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Die Verpackungsvorschrift der Transporteinheit ({0}) stellt das benötigte GRAI-Attribut nicht bereit. Der gescannte GRAI kann daher nicht gespeichert werden.','E',TO_TIMESTAMP('2026-06-03 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.GRAIAttributeNotSupportedByTUType');
UPDATE AD_Message SET ErrorCode='GRAIAttributeNotSupportedByTUType', Updated=TO_TIMESTAMP('2026-06-03 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545746;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545746
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The transport unit packing instructions ({0}) do not provide the required GRAI attribute, so the scanned GRAI cannot be stored.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-03 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545746;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-03 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545746;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-03 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545746;
