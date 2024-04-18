-- Run mode: SWING_CLIENT

-- Value: ComputingMethodTypeRequiresRawProduct
-- 2024-04-18T07:44:20.668Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545388,0,TO_TIMESTAMP('2024-04-18 10:44:20.466','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','This Computing Method Type requires the raw product.','E',TO_TIMESTAMP('2024-04-18 10:44:20.466','YYYY-MM-DD HH24:MI:SS.US'),100,'ComputingMethodTypeRequiresRawProduct')
;

-- 2024-04-18T07:44:20.681Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545388 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ComputingMethodTypeRequiresProcessedProduct
-- 2024-04-18T07:44:52.762Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545389,0,TO_TIMESTAMP('2024-04-18 10:44:52.642','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','This Computing Method Type requires the processed product.','E',TO_TIMESTAMP('2024-04-18 10:44:52.642','YYYY-MM-DD HH24:MI:SS.US'),100,'ComputingMethodTypeRequiresProcessedProduct')
;

-- 2024-04-18T07:44:52.763Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545389 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ComputingMethodTypeRequiresCoProduct
-- 2024-04-18T07:45:12.715Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545390,0,TO_TIMESTAMP('2024-04-18 10:45:12.579','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','This Computing Method Type requires the co-product.','E',TO_TIMESTAMP('2024-04-18 10:45:12.579','YYYY-MM-DD HH24:MI:SS.US'),100,'ComputingMethodTypeRequiresCoProduct')
;

-- 2024-04-18T07:45:12.716Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545390 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

