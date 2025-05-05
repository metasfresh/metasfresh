-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T13:15:38.066Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545160,0,TO_TIMESTAMP('2022-09-30 16:15:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The record {0} cannot be deleted since it is externally referenced as read only in metasfresh.','I',TO_TIMESTAMP('2022-09-30 16:15:37','YYYY-MM-DD HH24:MI:SS'),100,'CannotDeleteExternalReferenceReadOnlyInMetasfresh')
;

-- 2022-09-30T13:15:38.075Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545160 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T13:16:33.046Z
UPDATE AD_Message_Trl SET MsgText='Das Datensatz {0} kann nicht gelöscht werden, da es in metasfresh von außen als schreibgeschützt referenziert wird.',Updated=TO_TIMESTAMP('2022-09-30 16:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T13:16:59.432Z
UPDATE AD_Message_Trl SET MsgText='Das Datensatz {0} kann nicht gelöscht werden, da es in metasfresh von außen als schreibgeschützt referenziert wird.',Updated=TO_TIMESTAMP('2022-09-30 16:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T13:17:14.365Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-30 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T13:17:26.744Z
UPDATE AD_Message SET MsgText='Das Datensatz {0} kann nicht gelöscht werden, da es in metasfresh von außen als schreibgeschützt referenziert wird.',Updated=TO_TIMESTAMP('2022-09-30 16:17:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T14:21:00.196Z
UPDATE AD_Message_Trl SET MsgText='The record {0} cannot be deleted since it is externally referenced and marked as read only.',Updated=TO_TIMESTAMP('2022-09-30 17:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T14:21:08.406Z
UPDATE AD_Message_Trl SET MsgText='Das Datensatz {0} kann nicht gelöscht werden, da es extern referenziert und als schreibgeschützt gekennzeichnet ist.',Updated=TO_TIMESTAMP('2022-09-30 17:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T14:21:14.049Z
UPDATE AD_Message_Trl SET MsgText='Das Datensatz {0} kann nicht gelöscht werden, da es extern referenziert und als schreibgeschützt gekennzeichnet ist.',Updated=TO_TIMESTAMP('2022-09-30 17:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T14:21:30.969Z
UPDATE AD_Message_Trl SET MsgText='The record {0} cannot be deleted since it is externally referenced and marked as read only.',Updated=TO_TIMESTAMP('2022-09-30 17:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545160
;

-- Value: CannotDeleteExternalReferenceReadOnlyInMetasfresh
-- 2022-09-30T14:21:40.334Z
UPDATE AD_Message SET MsgText='Das Datensatz {0} kann nicht gelöscht werden, da es extern referenziert und als schreibgeschützt gekennzeichnet ist.',Updated=TO_TIMESTAMP('2022-09-30 17:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545160
;

