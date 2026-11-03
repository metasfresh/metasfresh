-- IDs allocated from idserver.metas.de on 2026-07-07:
--   AD_Message_ID     545772 (GRAI PO-reference-mismatch picking error message)
--   AD_MigrationScript 5812440
--
-- Thrown when a scanned Migros returnable-asset GRAI (companyPrefix 7613204 / assetType 00307) does not match
-- the current sales order's PO-reference-derived serial prefix during GRAI-scan picking. A non-Migros GRAI is
-- never subject to this check.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545772 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Der gescannte GRAI "{0}" gehört zu einem anderen Auftrag (Bestellreferenz "{1}").','E',TO_TIMESTAMP('2026-07-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.GRAIPOReferenceMismatch');
UPDATE AD_Message SET ErrorCode='GRAIPOReferenceMismatch', Updated=TO_TIMESTAMP('2026-07-07 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545772;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545772
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The scanned GRAI "{0}" belongs to another order (PO reference "{1}").',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-07 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545772;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-07 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545772;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-07 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545772;
