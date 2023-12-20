-- 2023-06-01T10:55:44.381Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545273,0,TO_TIMESTAMP('2023-06-01 13:55:44.123','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','The HU was already assigned to a different picking slot: {0}!','E',TO_TIMESTAMP('2023-06-01 13:55:44.123','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.picking.impl.huAlreadyAssigned')
;

-- 2023-06-01T10:55:44.394Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545273 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-06-01T11:19:36.365Z
UPDATE AD_Message_Trl SET MsgText='Die HU ist bereits dem Pickingslot {0} zugeordnet',Updated=TO_TIMESTAMP('2023-06-01 14:19:36.364','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545273
;

-- 2023-06-01T11:19:39.115Z
UPDATE AD_Message_Trl SET MsgText='Die HU ist bereits dem Pickingslot {0} zugeordnet',Updated=TO_TIMESTAMP('2023-06-01 14:19:39.114','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545273
;

-- 2023-06-01T11:19:44.349Z
UPDATE AD_Message_Trl SET MsgText='The HU was already assigned to a different picking slot: {0}',Updated=TO_TIMESTAMP('2023-06-01 14:19:44.348','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545273
;

-- 2023-06-01T11:19:47.897Z
UPDATE AD_Message_Trl SET MsgText='Die HU ist bereits dem Pickingslot {0} zugeordnet',Updated=TO_TIMESTAMP('2023-06-01 14:19:47.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545273
;

-- 2023-06-01T11:19:52.223Z
UPDATE AD_Message SET MsgText='The HU was already assigned to a different picking slot: {0}',Updated=TO_TIMESTAMP('2023-06-01 14:19:52.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545273
;

-- 2023-06-01T11:21:52.579Z
UPDATE AD_Message SET MsgText='Die HU ist bereits dem Pickingslot {0} zugeordnet',Updated=TO_TIMESTAMP('2023-06-01 14:21:52.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545273
;

