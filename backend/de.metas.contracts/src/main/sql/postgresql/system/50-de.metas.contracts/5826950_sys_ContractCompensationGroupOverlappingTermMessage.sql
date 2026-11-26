-- AD_Message: ContractCompensationGroup_OverlappingTerm
-- Refuses completing a compensation-group contract that overlaps another active one of the same invoice partner for a common order document type.
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545877 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Dieser Vertrag überschneidet sich mit dem aktiven Vertrag {0} für denselben Rechnungsempfänger und einen gemeinsamen Belegtyp.','E',TO_TIMESTAMP('2026-09-29 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ContractCompensationGroup_OverlappingTerm')
;

UPDATE AD_Message SET ErrorCode='ContractCompGroup_OverlappingTerm', Updated=TO_TIMESTAMP('2026-09-29 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545877
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Message_ID=545877
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='This contract overlaps active contract {0} for the same invoice partner and a common document type.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545877
;

-- de_DE/de_CH already carry the base (German) text as seeded above; just mark them as translated.
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545877
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545877
;
