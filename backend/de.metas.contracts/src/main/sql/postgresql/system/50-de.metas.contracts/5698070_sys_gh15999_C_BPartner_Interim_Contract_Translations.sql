-- Table: C_BPartner_InterimContract
-- 2023-08-04T07:16:49.154935800Z
UPDATE AD_Table SET Name='Interim payment contract',Updated=TO_TIMESTAMP('2023-08-04 10:16:49.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542357
;

-- 2023-08-04T07:16:49.192936600Z
UPDATE AD_Table_Trl trl SET Name='Interim payment contract' WHERE AD_Table_ID=542357 AND AD_Language='de_DE'
;

-- 2023-08-04T07:17:01.207039900Z
UPDATE AD_Table_Trl SET Name='Vorfinanzierungsvertrag',Updated=TO_TIMESTAMP('2023-08-04 10:17:01.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542357
;

-- 2023-08-04T07:17:06.433039100Z
UPDATE AD_Table_Trl SET Name='Vorfinanzierungsvertrag',Updated=TO_TIMESTAMP('2023-08-04 10:17:06.332','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542357
;

-- 2023-08-04T07:17:06.468539300Z
UPDATE AD_Table SET Name='Vorfinanzierungsvertrag' WHERE AD_Table_ID=542357
;

-- 2023-08-04T07:22:47.772589500Z
UPDATE AD_Table_Trl SET Name='Interim payment contract',Updated=TO_TIMESTAMP('2023-08-04 10:22:47.67','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542357
;

-- Element: C_BPartner_InterimContract_ID
-- 2023-08-04T07:23:23.240591Z
UPDATE AD_Element_Trl SET Name='Vorfinanzierungsvertrag', PrintName='Vorfinanzierungsvertrag',Updated=TO_TIMESTAMP('2023-08-04 10:23:23.24','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582617 AND AD_Language='de_CH'
;

-- 2023-08-04T07:23:23.349088900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582617,'de_CH') 
;

-- Element: C_BPartner_InterimContract_ID
-- 2023-08-04T07:23:29.058587600Z
UPDATE AD_Element_Trl SET Name='Vorfinanzierungsvertrag', PrintName='Vorfinanzierungsvertrag',Updated=TO_TIMESTAMP('2023-08-04 10:23:29.058','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582617 AND AD_Language='de_DE'
;

-- 2023-08-04T07:23:29.092590500Z
UPDATE AD_Element SET Name='Vorfinanzierungsvertrag', PrintName='Vorfinanzierungsvertrag' WHERE AD_Element_ID=582617
;

-- 2023-08-04T07:23:30.225591400Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582617,'de_DE') 
;

-- 2023-08-04T07:23:30.261086800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582617,'de_DE') 
;

-- Element: C_BPartner_InterimContract_ID
-- 2023-08-04T07:24:21.879794500Z
UPDATE AD_Element_Trl SET Description='These settings are used as a basis for generating interim payment contracts for the selected business partner.', Help='These settings are used as a basis for generating interim payment contracts for the selected business partner.', Name='Interim payment contract', PrintName='Interim payment contract',Updated=TO_TIMESTAMP('2023-08-04 10:24:21.879','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582617 AND AD_Language='en_US'
;

-- 2023-08-04T07:24:21.951793500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582617,'en_US') 
;

-- Element: C_BPartner_InterimContract_ID
-- 2023-08-04T07:24:25.553795Z
UPDATE AD_Element_Trl SET Description='Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', Help='Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.',Updated=TO_TIMESTAMP('2023-08-04 10:24:25.553','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582617 AND AD_Language='de_DE'
;

-- 2023-08-04T07:24:25.589294600Z
UPDATE AD_Element SET Description='Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', Help='Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.' WHERE AD_Element_ID=582617
;

-- 2023-08-04T07:24:26.584794200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582617,'de_DE') 
;

-- 2023-08-04T07:24:26.617793500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582617,'de_DE') 
;

-- Element: C_BPartner_InterimContract_ID
-- 2023-08-04T07:25:03.031295100Z
UPDATE AD_Element_Trl SET Description='Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', Help='Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.',Updated=TO_TIMESTAMP('2023-08-04 10:25:03.031','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582617 AND AD_Language='de_CH'
;

-- 2023-08-04T07:25:03.099793300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582617,'de_CH') 
;

-- Element: IsInterimContract
-- 2023-08-04T07:31:03.969466Z
UPDATE AD_Element_Trl SET Name='Interim payment contract', PrintName='Interim payment contract',Updated=TO_TIMESTAMP('2023-08-04 10:31:03.968','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582618 AND AD_Language='en_US'
;

-- 2023-08-04T07:31:04.036965600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582618,'en_US') 
;

-- Element: IsInterimContract
-- 2023-08-04T07:31:36.169465500Z
UPDATE AD_Element_Trl SET Name='Vorfinanzierungsvertrag', PrintName='Vorfinanzierungsvertrag',Updated=TO_TIMESTAMP('2023-08-04 10:31:36.168','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582618 AND AD_Language='de_DE'
;

-- 2023-08-04T07:31:36.204964Z
UPDATE AD_Element SET Name='Vorfinanzierungsvertrag', PrintName='Vorfinanzierungsvertrag' WHERE AD_Element_ID=582618
;

-- 2023-08-04T07:31:37.138461200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582618,'de_DE') 
;

-- 2023-08-04T07:31:37.171962100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582618,'de_DE') 
;

-- Element: IsInterimContract
-- 2023-08-04T07:32:01.497464600Z
UPDATE AD_Element_Trl SET Name='Vorfinanzierungsvertrag', PrintName='Vorfinanzierungsvertrag',Updated=TO_TIMESTAMP('2023-08-04 10:32:01.496','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582618 AND AD_Language='de_CH'
;

-- 2023-08-04T07:32:01.567964900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582618,'de_CH') 
;

-- Value: C_BPartner_InterimContract
-- Classname: de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert
-- 2023-08-04T07:33:37.481260900Z
UPDATE AD_Process SET Name='Vorfinanzierungsvertrag definieren',Updated=TO_TIMESTAMP('2023-08-04 10:33:37.38','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585301
;

-- 2023-08-04T07:33:37.516260Z
UPDATE AD_Process_Trl trl SET Name='Vorfinanzierungsvertrag definieren' WHERE AD_Process_ID=585301 AND AD_Language='de_DE'
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- 2023-08-04T07:33:45.920758400Z
UPDATE AD_Process_Trl SET Name='Vorfinanzierungsvertrag definieren',Updated=TO_TIMESTAMP('2023-08-04 10:33:45.92','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585301
;

-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- 2023-08-04T07:33:58.327258400Z
UPDATE AD_Process_Trl SET Name='Define interim payment contract',Updated=TO_TIMESTAMP('2023-08-04 10:33:58.326','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585301
;

-- Value: de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService.InterimContractExists
-- 2023-08-04T07:37:28.918143100Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545317,0,TO_TIMESTAMP('2023-08-04 10:37:28.489','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Dieser Geschäftspartner hat bereits einen Vorfinanzierungsvertrag für dieses Erntejahr abgeschlossen.','E',TO_TIMESTAMP('2023-08-04 10:37:28.489','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService.InterimContractExists')
;

-- 2023-08-04T07:37:28.958142700Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545317 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService.InterimContractExists
-- 2023-08-04T07:38:00.226650600Z
UPDATE AD_Message_Trl SET MsgText='This business partner already has an interim payment contract for this crop year.',Updated=TO_TIMESTAMP('2023-08-04 10:38:00.226','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545317
;

