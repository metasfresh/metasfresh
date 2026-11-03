-- IDs allocated from idserver.metas.de on 2026-07-20:
--   AD_Message_ID      545775 (BPartner composite path/body org mismatch error message)
--   AD_MigrationScript 5814600
--
-- Thrown when PUT /api/v2/bpartner/{orgCode} carries a body bpartnerComposite.orgCode
-- that resolves to a DIFFERENT organisation than the path {orgCode}.
-- {0} = path orgCode, {1} = body orgCode.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545775 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-20 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Organisation "{1}" im Anfrage-Body unterscheidet sich von der Organisation "{0}" im URL-Pfad. Bitte nur eine angeben oder übereinstimmende Werte verwenden.','E',TO_TIMESTAMP('2026-07-20 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'BPartnerCompositeOrgMismatch');
UPDATE AD_Message SET ErrorCode='BPartnerCompositeOrgMismatch', Updated=TO_TIMESTAMP('2026-07-20 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545775;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545775
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The organisation "{1}" in the request body differs from the organisation "{0}" in the URL path. Provide only one, or matching values.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-20 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545775;
UPDATE AD_Message_Trl SET MsgText='Die Organisation "{1}" im Anfrage-Body unterscheidet sich von der Organisation "{0}" im URL-Pfad. Bitte nur eine angeben oder übereinstimmende Werte verwenden.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-20 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545775;
UPDATE AD_Message_Trl SET MsgText='Die Organisation "{1}" im Anfrage-Body unterscheidet sich von der Organisation "{0}" im URL-Pfad. Bitte nur eine angeben oder übereinstimmende Werte verwenden.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-20 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545775;
