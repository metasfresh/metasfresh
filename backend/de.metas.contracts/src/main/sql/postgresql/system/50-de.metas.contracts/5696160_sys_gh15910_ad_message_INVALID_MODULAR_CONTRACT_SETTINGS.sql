-- Value: de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS
-- 2023-07-19T08:06:44.739863300Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545292,0,TO_TIMESTAMP('2023-07-19 11:06:44.519','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Cannot complete modular contract terms. Please add the missing modules in the corresponding modular contract settings.','E',TO_TIMESTAMP('2023-07-19 11:06:44.519','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS')
;

-- 2023-07-19T08:06:44.750238800Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545292 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS
-- 2023-07-19T08:08:12.303272Z
UPDATE AD_Message_Trl SET MsgText='Modulare Vertragsbedingungen können nicht fertiggestellt werden. Bitte fehlende Bausteine in den entsprechenden Einstellungen für modulare Verträge ergänzen.',Updated=TO_TIMESTAMP('2023-07-19 11:08:12.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545292
;

-- Value: de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS
-- 2023-07-19T08:08:17.110487600Z
UPDATE AD_Message_Trl SET MsgText='Modulare Vertragsbedingungen können nicht fertiggestellt werden. Bitte fehlende Bausteine in den entsprechenden Einstellungen für modulare Verträge ergänzen.',Updated=TO_TIMESTAMP('2023-07-19 11:08:17.11','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545292
;

-- 2023-07-19T08:08:17.111496700Z
UPDATE AD_Message SET MsgText='Modulare Vertragsbedingungen können nicht fertiggestellt werden. Bitte fehlende Bausteine in den entsprechenden Einstellungen für modulare Verträge ergänzen.' WHERE AD_Message_ID=545292
;

-- Value: de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS
-- 2023-07-19T08:08:19.710276200Z
UPDATE AD_Message_Trl SET MsgText='Modulare Vertragsbedingungen können nicht fertiggestellt werden. Bitte fehlende Bausteine in den entsprechenden Einstellungen für modulare Verträge ergänzen.',Updated=TO_TIMESTAMP('2023-07-19 11:08:19.71','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545292
;

-- Value: de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS
-- 2023-07-19T08:08:22.355260100Z
UPDATE AD_Message_Trl SET MsgText='Modulare Vertragsbedingungen können nicht fertiggestellt werden. Bitte fehlende Bausteine in den entsprechenden Einstellungen für modulare Verträge ergänzen.',Updated=TO_TIMESTAMP('2023-07-19 11:08:22.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545292
;

