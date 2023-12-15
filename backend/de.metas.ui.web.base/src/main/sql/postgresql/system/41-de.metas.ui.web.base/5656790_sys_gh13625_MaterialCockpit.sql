-- 2022-09-19T14:06:13.117Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581468,0,'QtyDemand_SalesOrder',TO_TIMESTAMP('2022-09-19 17:06:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Beauftragt - offen','Beauftragt - offen',TO_TIMESTAMP('2022-09-19 17:06:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-19T14:06:13.124Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581468 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-19T14:06:29.817Z
UPDATE AD_Element_Trl SET Name='Sold - pending', PrintName='Sold - pending',Updated=TO_TIMESTAMP('2022-09-19 17:06:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581468 AND AD_Language='en_US'
;

-- 2022-09-19T14:06:29.849Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581468,'en_US') 
;

-- 2022-09-19T14:08:02.026Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581469,0,'QtySupply_PurchaseOrder',TO_TIMESTAMP('2022-09-19 17:08:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.cockpit','Y','Bestellt - offen','Bestellt - offen',TO_TIMESTAMP('2022-09-19 17:08:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-19T14:08:02.028Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581469 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-19T14:08:12.395Z
UPDATE AD_Element_Trl SET Name='Purchased - pending', PrintName='Purchased - pending',Updated=TO_TIMESTAMP('2022-09-19 17:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581469 AND AD_Language='en_US'
;

-- 2022-09-19T14:08:12.397Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581469,'en_US') 
;

