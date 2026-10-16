-- Client Quick Setup (gh4955) cannot load an organization whose linked C_BPartner
-- (AD_OrgBP_ID) is inactive or missing. {0} = organization name.
--
-- AD_Message 545894 (ClientSetup_NoActiveOrgBPartner)

INSERT INTO AD_Message (AD_Message_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,MsgText,MsgType,EntityType)
SELECT 545894 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-08 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       TO_TIMESTAMP('2026-10-08 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       'ClientSetup_NoActiveOrgBPartner',
       'Client Quick Setup kann für Organisation {0} nicht ausgeführt werden: Es gibt keinen aktiven verknüpften Geschäftspartner. Der Organisationspartner wurde möglicherweise deaktiviert. Aktivieren Sie ihn wieder und starten Sie den Assistenten erneut.',
       'E','D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Message WHERE AD_Message_ID=545894)
  AND NOT EXISTS (SELECT 1 FROM AD_Message WHERE Value='ClientSetup_NoActiveOrgBPartner')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
  FROM AD_Language l, AD_Message t
 WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545894
   AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl
   SET MsgText = 'Client Quick Setup cannot run for organization {0}: there is no active linked business partner. The organization''s partner may have been deactivated. Reactivate that partner and start the wizard again.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-10-08 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Message_ID = 545894 AND AD_Language = 'en_US'
;
