-- 2019-04-17T14:21:27.315
-- URL zum Konzept
INSERT INTO AD_EntityType (Processing,AD_Client_ID,IsActive,Created,CreatedBy,ModelPackage,Updated,UpdatedBy,AD_EntityType_ID,IsDisplayed,AD_Org_ID,EntityType,Name)
select 'N',0,'Y',TO_TIMESTAMP('2019-04-17 14:21:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.securpharm.model',TO_TIMESTAMP('2019-04-17 14:21:26','YYYY-MM-DD HH24:MI:SS'),100,540248,'Y',0,'de.metas.vertical.pharma.securpharm','de.metas.vertical.pharma.securpharm'
where not exists (select 1 from AD_EntityType where ad_entitytype_id=540248)
;

-- 2019-04-18T14:46:19.197
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name)
select 0,'Y',TO_TIMESTAMP('2019-04-18 14:46:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 14:46:19','YYYY-MM-DD HH24:MI:SS'),100,576652,0,'LotNumber','de.metas.vertical.pharma.securpharm','Chargennummer','Chargennummer'
where not exists (select 1 from AD_Element where ad_element_id=576652)
;

-- 2019-04-18T14:46:19.207
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576652 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T14:46:41.766
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Lot number', IsTranslated='Y', Name='Lot number',Updated=TO_TIMESTAMP('2019-04-18 14:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576652
;

-- 2019-04-18T14:46:41.770
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576652,'en_US')
;