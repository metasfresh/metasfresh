-- 4 user-friendly AD_Messages used by the workflow first-node guard interceptors and the
-- PPRoutingRepository load-time validation.

-- PPRouting_CannotDeactivateFirstNode -----------------------------------------------------------
-- 2026-05-28T12:10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545731 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','PPROUTING_CANNOT_DEACTIVATE_FIRST_NODE','Y','Der erste Arbeitsschritt eines Arbeitsablaufs kann nicht deaktiviert werden. Bitte setzen Sie zuerst einen anderen aktiven Arbeitsschritt als ersten Arbeitsschritt im Arbeitsablauf.','E',TO_TIMESTAMP('2026-05-28 12:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_CannotDeactivateFirstNode')
;
-- 2026-05-28T12:10:01
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545731
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
-- 2026-05-28T12:10:02
UPDATE AD_Message_Trl SET MsgText='The first activity of a workflow cannot be deactivated. Please set another active activity as the workflow''s first activity first.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545731 AND AD_Language='en_US'
;

-- PPRouting_CannotDeleteFirstNode ---------------------------------------------------------------
-- 2026-05-28T12:10:03
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545732 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-28 12:10:03','YYYY-MM-DD HH24:MI:SS'),100,'D','PPROUTING_CANNOT_DELETE_FIRST_NODE','Y','Der erste Arbeitsschritt eines Arbeitsablaufs kann nicht gelöscht werden. Bitte setzen Sie zuerst einen anderen Arbeitsschritt als ersten Arbeitsschritt im Arbeitsablauf.','E',TO_TIMESTAMP('2026-05-28 12:10:03','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_CannotDeleteFirstNode')
;
-- 2026-05-28T12:10:04
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545732
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
-- 2026-05-28T12:10:05
UPDATE AD_Message_Trl SET MsgText='The first activity of a workflow cannot be deleted. Please set another activity as the workflow''s first activity first.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545732 AND AD_Language='en_US'
;

-- PPRouting_FirstNodeRequiresResource ---------------------------------------------------
-- 2026-05-28T12:10:06
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545733 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-28 12:10:06','YYYY-MM-DD HH24:MI:SS'),100,'D','PPROUTING_FIRST_NODE_REQUIRES_RESOURCE','Y','Die Ressource des ersten Arbeitsschritts eines Arbeitsablaufs darf nicht leer sein.','E',TO_TIMESTAMP('2026-05-28 12:10:06','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_FirstNodeRequiresResource')
;
-- 2026-05-28T12:10:07
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545733
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
-- 2026-05-28T12:10:08
UPDATE AD_Message_Trl SET MsgText='The resource of the first activity of a workflow must not be empty.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545733 AND AD_Language='en_US'
;

-- PPRouting_FirstNodeInvalid --------------------------------------------------------------------
-- 2026-05-28T12:10:09
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545734 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-28 12:10:09','YYYY-MM-DD HH24:MI:SS'),100,'D','PPROUTING_FIRST_NODE_INVALID','Y','Der als erster Arbeitsschritt gewählte Arbeitsschritt ist nicht gültig (muss aktiv sein, zum gleichen Arbeitsablauf gehören und eine Ressource zugeordnet haben).','E',TO_TIMESTAMP('2026-05-28 12:10:09','YYYY-MM-DD HH24:MI:SS'),100,'PPRouting_FirstNodeInvalid')
;
-- 2026-05-28T12:10:10
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=545734
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
-- 2026-05-28T12:10:11
UPDATE AD_Message_Trl SET MsgText='The chosen first activity is invalid (must be active, belong to the same workflow, and have a resource assigned).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-05-28 12:10:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545734 AND AD_Language='en_US'
;
