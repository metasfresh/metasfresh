-- Run mode: SWING_CLIENT

-- SysConfig Name: C_Flatrate_Conditions.MODULAR_CONTRACT_TRANSITION_TEMPLATE
-- SysConfig Value: 540037
-- 2023-10-23T10:04:34.692Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Modular Contract transition template', Name='C_Flatrate_Conditions.MODULAR_CONTRACT_TRANSITION_TEMPLATE',Updated=TO_TIMESTAMP('2023-10-23 13:04:34.691','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541643
;

-- Run mode: SWING_CLIENT

-- Element: null
-- 2023-10-23T15:45:32.740Z
UPDATE AD_Element_Trl SET Name='Vertrag', PrintName='Vertrag',Updated=TO_TIMESTAMP('2023-10-23 18:45:32.74','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_CH'
;

-- 2023-10-23T15:45:32.780Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_CH')
;

-- Element: null
-- 2023-10-23T15:45:40.277Z
UPDATE AD_Element_Trl SET Name='Vertrag', PrintName='Vertrag', WEBUI_NameBrowse='Vertrag',Updated=TO_TIMESTAMP('2023-10-23 18:45:40.276','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_DE'
;

-- 2023-10-23T15:45:40.279Z
UPDATE AD_Element SET Name='Vertrag', PrintName='Vertrag', WEBUI_NameBrowse='Vertrag' WHERE AD_Element_ID=574563
;

-- 2023-10-23T15:45:41.409Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(574563,'de_DE')
;

-- 2023-10-23T15:45:41.415Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_DE')
;

-- Element: null
-- 2023-10-23T15:45:48.164Z
UPDATE AD_Element_Trl SET Name='Vertrag', PrintName='Vertrag', WEBUI_NameBrowse='Vertrag',Updated=TO_TIMESTAMP('2023-10-23 18:45:48.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='it_IT'
;

-- 2023-10-23T15:45:48.167Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'it_IT')
;

-- Element: null
-- 2023-10-23T15:45:53.163Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertrag',Updated=TO_TIMESTAMP('2023-10-23 18:45:53.163','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_CH'
;

-- 2023-10-23T15:45:53.167Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_CH')
;

-- Element: null
-- 2023-10-23T15:46:08.385Z
UPDATE AD_Element_Trl SET Name='Contract', PrintName='Contract',Updated=TO_TIMESTAMP('2023-10-23 18:46:08.385','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='en_US'
;

-- 2023-10-23T15:46:08.387Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'en_US')
;

-- Run mode: SWING_CLIENT

-- Name: Vertrag
-- Action Type: W
-- Window: Vertrag(540359,de.metas.contracts)
-- 2023-10-24T09:06:34.643Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540951
;

-- 2023-10-24T09:06:34.652Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540951
;

-- 2023-10-24T09:06:34.654Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540951 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: Vertrag
-- Action Type: W
-- Window: Vertrag(540359,de.metas.contracts)
-- 2023-10-24T09:08:07.939Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,579915,542124,0,540359,TO_TIMESTAMP('2023-10-24 12:08:07.796','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','540359 (C_Flatrate_Term)','Y','N','N','N','N','Vertrag',TO_TIMESTAMP('2023-10-24 12:08:07.796','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-24T09:08:07.948Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542124 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-24T09:08:07.955Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542124, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542124)
;

-- 2023-10-24T09:08:08.003Z
/* DDL */  select update_menu_translation_from_ad_element(579915)
;

-- Reordering children of `Contract Management`
-- Node name: `Vertrag`
-- 2023-10-24T09:08:36.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542124 AND AD_Tree_ID=10
;

-- Node name: `Contract (C_Flatrate_Term)`
-- 2023-10-24T09:08:36.585Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-10-24T09:08:36.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-10-24T09:08:36.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-10-24T09:08:36.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-10-24T09:08:36.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (ModCntr_Type)`
-- 2023-10-24T09:08:36.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-10-24T09:08:36.590Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log (ModCntr_Log)`
-- 2023-10-24T09:08:36.590Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-10-24T09:08:36.591Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Subscription Discount (C_SubscrDiscount)`
-- 2023-10-24T09:08:36.592Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-24T09:08:36.592Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-24T09:08:36.593Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-10-24T09:08:36.594Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-10-24T09:08:36.595Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-10-24T09:08:36.595Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Settings (ModCntr_Settings)`
-- 2023-10-24T09:08:36.596Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542088 AND AD_Tree_ID=10
;

-- Node name: `Invoice Group (ModCntr_InvoicingGroup)`
-- 2023-10-24T09:08:36.596Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542106 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log Status (ModCntr_Log_Status)`
-- 2023-10-24T09:08:36.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542111 AND AD_Tree_ID=10
;

-- Node name: `Import Module Contract Log (I_ModCntr_Log)`
-- 2023-10-24T09:08:36.598Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542117 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Value: MODULAR_CONTRACT_TRANSITION_PREFIX
-- 2023-10-24T10:31:18.636Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545355,0,TO_TIMESTAMP('2023-10-24 13:31:18.416','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modularer Vertrag Übergangsbedingungen','I',TO_TIMESTAMP('2023-10-24 13:31:18.416','YYYY-MM-DD HH24:MI:SS.US'),100,'MODULAR_CONTRACT_TRANSITION_PREFIX')
;

-- 2023-10-24T10:31:18.650Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545355 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MODULAR_CONTRACT_TRANSITION_PREFIX
-- 2023-10-24T10:31:31.243Z
UPDATE AD_Message_Trl SET MsgText='Modular contract transition conditions',Updated=TO_TIMESTAMP('2023-10-24 13:31:31.243','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545355
;

-- Run mode: SWING_CLIENT

-- Value: de.metas.calendar.standard.impl.CalendarBL.YearHasNoPeriod
-- 2023-10-25T08:17:29.819Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545356,0,TO_TIMESTAMP('2023-10-25 11:17:29.608','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Für das betreffende Jahr des ausgewählten Kalenders sind keine Kalenderperioden verfügbar. Bitte Kalenderperioden hinzufügen.','E',TO_TIMESTAMP('2023-10-25 11:17:29.608','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.calendar.standard.impl.CalendarBL.YearHasNoPeriod')
;

-- 2023-10-25T08:17:29.839Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545356 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.calendar.standard.impl.CalendarBL.YearHasNoPeriod
-- 2023-10-25T08:17:37.318Z
UPDATE AD_Message_Trl SET MsgText='No calendar periods available for the relevant year of the selected calendar. Please add calendar periods.',Updated=TO_TIMESTAMP('2023-10-25 11:17:37.318','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545356
;

