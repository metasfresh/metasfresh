-- 2026-05-28T12:10:00
-- 4 user-friendly AD_Messages used by the workflow first-node guard interceptors and the
-- PPRoutingRepository load-time validation.

-- PPRouting_CannotDeactivateFirstNode -----------------------------------------------------------
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545731,0,TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der erste Arbeitsschritt eines Arbeitsablaufs kann nicht deaktiviert werden. Bitte setzen Sie zuerst einen anderen aktiven Arbeitsschritt als ersten Arbeitsschritt im Arbeitsablauf.','E',TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_CannotDeactivateFirstNode')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545731
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The first activity of a workflow cannot be deactivated. Please set another active activity as the workflow''s first activity first.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545731 AND AD_Language='en_US'
;

-- PPRouting_CannotDeleteFirstNode ---------------------------------------------------------------
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545732,0,TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der erste Arbeitsschritt eines Arbeitsablaufs kann nicht gelöscht werden. Bitte setzen Sie zuerst einen anderen Arbeitsschritt als ersten Arbeitsschritt im Arbeitsablauf.','E',TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_CannotDeleteFirstNode')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545732
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The first activity of a workflow cannot be deleted. Please set another activity as the workflow''s first activity first.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545732 AND AD_Language='en_US'
;

-- PPRouting_CannotRemoveResourceFromFirstNode ---------------------------------------------------
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545733,0,TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Ressource des ersten Arbeitsschritts eines Arbeitsablaufs darf nicht leer sein.','E',TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_CannotRemoveResourceFromFirstNode')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545733
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The resource of the first activity of a workflow must not be empty.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545733 AND AD_Language='en_US'
;

-- PPRouting_FirstNodeInvalid --------------------------------------------------------------------
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545734,0,TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der als erster Arbeitsschritt gewählte Arbeitsschritt ist nicht gültig (muss aktiv sein, zum gleichen Arbeitsablauf gehören und eine Ressource zugeordnet haben).','E',TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_FirstNodeInvalid')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545734
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The chosen first activity is invalid (must be active, belong to the same workflow, and have a resource assigned).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545734 AND AD_Language='en_US'
;
