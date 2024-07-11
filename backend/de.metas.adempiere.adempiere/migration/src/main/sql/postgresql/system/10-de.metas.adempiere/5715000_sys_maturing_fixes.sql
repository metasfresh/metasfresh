-- Element: null
-- 2024-01-10T11:37:42.777Z
UPDATE AD_Element_Trl SET Name='Reifedisposition', PrintName='Reifedisposition',Updated=TO_TIMESTAMP('2024-01-10 13:37:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582866 AND AD_Language='de_DE'
;

-- 2024-01-10T11:37:42.811Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582866,'de_DE') 
;

-- 2024-01-10T11:37:42.813Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582866,'de_DE') 
;

-- Element: null
-- 2024-01-10T11:38:19.038Z
UPDATE AD_Element_Trl SET Name='Reifedisposition', PrintName='Reifedisposition',Updated=TO_TIMESTAMP('2024-01-10 13:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582866 AND AD_Language='de_CH'
;

-- 2024-01-10T11:38:19.039Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582866,'de_CH') 
;

-- Element: M_Maturing_Configuration_ID
-- 2024-01-10T13:39:15.425Z
UPDATE AD_Element_Trl SET Name='Reifung Konfiguration', PrintName='Reifung Konfiguration',Updated=TO_TIMESTAMP('2024-01-10 15:39:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582856 AND AD_Language='de_CH'
;

-- 2024-01-10T13:39:15.427Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582856,'de_CH') 
;

-- Element: M_Maturing_Configuration_ID
-- 2024-01-10T13:39:21.006Z
UPDATE AD_Element_Trl SET Name='Reifung Konfiguration', PrintName='Reifung Konfiguration',Updated=TO_TIMESTAMP('2024-01-10 15:39:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582856 AND AD_Language='de_DE'
;

-- 2024-01-10T13:39:21.008Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582856,'de_DE') 
;

-- 2024-01-10T13:39:21.010Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582856,'de_DE') 
;

-- Element: M_Maturing_Configuration_ID
-- 2024-01-10T13:39:34.918Z
UPDATE AD_Element_Trl SET Name='Reifung Konfiguration', PrintName='Reifung Konfiguration',Updated=TO_TIMESTAMP('2024-01-10 15:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582856 AND AD_Language='fr_CH'
;

-- 2024-01-10T13:39:34.920Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582856,'fr_CH') 
;

-- Name: M_Maturing_Configuration_Line target for C_Product
-- 2024-01-10T13:42:31.152Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541845,TO_TIMESTAMP('2024-01-10 15:42:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Maturing_Configuration_Line target for C_Product',TO_TIMESTAMP('2024-01-10 15:42:30','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-01-10T13:42:31.153Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541845 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Maturing_Configuration_Line target for C_Product
-- Table: M_Maturing_Configuration_Line
-- Key: M_Maturing_Configuration_Line.M_Maturing_Configuration_Line_ID
-- 2024-01-10T13:44:55.982Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,587747,0,541845,542384,541755,TO_TIMESTAMP('2024-01-10 15:44:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-01-10 15:44:55','YYYY-MM-DD HH24:MI:SS'),100,'From_Product_ID = @M_Product_ID/-1@ OR Matured_Product_ID = @M_Product_ID/-1@')
;

-- 2024-01-10T13:47:22.118Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540272,541845,540435,TO_TIMESTAMP('2024-01-10 15:47:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_Product => M_Maturing_Configuration_Line',TO_TIMESTAMP('2024-01-10 15:47:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Window: Reifedisposition, InternalName=null
-- Window: Reifedisposition, InternalName=null
-- 2024-01-10T15:17:14.140Z
UPDATE AD_Window SET Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2024-01-10 17:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541756
;

-- Name: Reifedisposition
-- Action Type: W
-- Window: Reifedisposition(541756,EE01)
-- 2024-01-10T15:18:32.843Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582866,542135,0,541756,TO_TIMESTAMP('2024-01-10 17:18:32','YYYY-MM-DD HH24:MI:SS'),100,'EE01','PP_Order_Disposition (isMatured=''Y'')','Y','N','N','N','N','Reifedisposition',TO_TIMESTAMP('2024-01-10 17:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-10T15:18:32.844Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542135 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-01-10T15:18:32.846Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542135, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542135)
;

-- 2024-01-10T15:18:32.855Z
/* DDL */  select update_menu_translation_from_ad_element(582866) 
;

-- Reordering children of `Manufacturing Workflow`
-- Node name: `Manufacturing Workflow Setup`
-- 2024-01-10T15:18:41.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=53019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53021 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2024-01-10T15:18:41.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=53019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53020 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Editor (org.eevolution.form.WFPanelManufacturing)`
-- 2024-01-10T15:18:41.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=53019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53128 AND AD_Tree_ID=10
;

-- Node name: `Reifedisposition`
-- 2024-01-10T15:18:41.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=53019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542135 AND AD_Tree_ID=10
;

-- Reordering children of `Manufacturing`
-- Node name: `Reifedisposition`
-- 2024-01-10T15:19:18.233Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542135 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2024-01-10T15:19:18.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2024-01-10T15:19:18.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2024-01-10T15:19:18.235Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-01-10T15:19:18.236Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-01-10T15:19:18.237Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-01-10T15:19:18.237Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

