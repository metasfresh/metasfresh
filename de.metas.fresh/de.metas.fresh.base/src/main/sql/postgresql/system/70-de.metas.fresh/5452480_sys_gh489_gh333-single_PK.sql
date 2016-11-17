--
-- DDL for creating a single PK for AD_PInstance_Para
--
-- 26.10.2016 21:39
-- URL zum Konzept
ALTER TABLE AD_PInstance_Para ADD COLUMN AD_PInstance_Para_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_pinstance_para_seq')
;

-- 26.10.2016 21:40
-- URL zum Konzept
ALTER TABLE AD_PInstance_Para DROP CONSTRAINT IF EXISTS ad_pinstance_para_pkey
;

-- 26.10.2016 21:40
-- URL zum Konzept
ALTER TABLE AD_PInstance_Para ADD CONSTRAINT ad_pinstance_para_pkey PRIMARY KEY (AD_PInstance_Para_ID)
;

--
-- DDL for creating a single PK for AD_PInstance_Log
--
-- 27.10.2016 07:07
-- URL zum Konzept
ALTER TABLE AD_PInstance_Log ADD COLUMN AD_PInstance_Log_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_pinstance_log_seq')
;

-- 27.10.2016 07:09
-- URL zum Konzept
ALTER TABLE AD_PInstance_Log DROP CONSTRAINT IF EXISTS ad_pinstance_log_pkey
;

-- 27.10.2016 07:09
-- URL zum Konzept
ALTER TABLE AD_PInstance_Log ADD CONSTRAINT ad_pinstance_log_pkey PRIMARY KEY (AD_PInstance_Log_ID)
;

--
-- DDL to give M_MovementLineMA a single PK column (FRESH-618 gh #333)
--
-- 26.10.2016 14:51
-- URL zum Konzept
ALTER TABLE M_MovementLineMA ADD COLUMN M_MovementLineMA_ID numeric(10,0) NOT NULL DEFAULT nextval('m_movementlinema_seq')
;
-- 26.10.2016 14:51
-- URL zum Konzept
ALTER TABLE M_MovementLineMA DROP CONSTRAINT IF EXISTS m_movementlinema_pkey
;
-- 26.10.2016 14:51
-- URL zum Konzept
ALTER TABLE M_MovementLineMA ADD CONSTRAINT m_movementlinema_pkey PRIMARY KEY (M_MovementLineMA_ID)
;

--
-- DDL for creating a single PK for M_HU_Snapshot
--
-- 28.10.2016 06:34
-- URL zum Konzept
ALTER TABLE M_HU_Snapshot ADD COLUMN M_HU_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_snapshot_seq')
;

-- 28.10.2016 06:34
-- URL zum Konzept
ALTER TABLE M_HU_Snapshot DROP CONSTRAINT IF EXISTS m_hu_snapshot_pkey
;

-- 28.10.2016 06:34
-- URL zum Konzept
ALTER TABLE M_HU_Snapshot ADD CONSTRAINT m_hu_snapshot_pkey PRIMARY KEY (M_HU_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_HU_Storage_Snapshot
--
-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Storage_Snapshot ADD COLUMN M_HU_Storage_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_storage_snapshot_seq')
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Storage_Snapshot DROP CONSTRAINT IF EXISTS m_hu_storage_snapshot_pkey
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Storage_Snapshot ADD CONSTRAINT m_hu_storage_snapshot_pkey PRIMARY KEY (M_HU_Storage_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_HU_Attribute_Snapshot
--
-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Attribute_Snapshot ADD COLUMN M_HU_Attribute_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_attribute_snapshot_seq')
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Attribute_Snapshot DROP CONSTRAINT IF EXISTS m_hu_attribute_snapshot_pkey
;

-- 28.10.2016 06:35
-- URL zum Konzept
ALTER TABLE M_HU_Attribute_Snapshot ADD CONSTRAINT m_hu_attribute_snapshot_pkey PRIMARY KEY (M_HU_Attribute_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_AttributeInstance
--
-- 31.10.2016 14:21
-- URL zum Konzept
ALTER TABLE M_AttributeInstance ADD COLUMN M_AttributeInstance_ID numeric(10,0) NOT NULL DEFAULT nextval('m_attributeinstance_seq')
;

-- 31.10.2016 14:22
-- URL zum Konzept
ALTER TABLE M_AttributeInstance DROP CONSTRAINT IF EXISTS m_attributeinstance_pkey
;

-- 31.10.2016 14:22
-- URL zum Konzept
ALTER TABLE M_AttributeInstance ADD CONSTRAINT m_attributeinstance_pkey PRIMARY KEY (M_AttributeInstance_ID)
;

--
-- DDL for creating a single PK for M_InventoryLineMA
--
-- 03.11.2016 06:31
-- URL zum Konzept
ALTER TABLE M_InventoryLineMA ADD COLUMN M_InventoryLineMA_ID numeric(10,0) NOT NULL DEFAULT nextval('m_inventorylinema_seq')
;

-- 03.11.2016 06:31
-- URL zum Konzept
ALTER TABLE M_InventoryLineMA DROP CONSTRAINT IF EXISTS m_inventorylinema_pkey
;

-- 03.11.2016 06:31
-- URL zum Konzept
ALTER TABLE M_InventoryLineMA ADD CONSTRAINT m_inventorylinema_pkey PRIMARY KEY (M_InventoryLineMA_ID)
;

--
-- DDL for creating a single PK for M_ProductionLineMA
--
-- 03.11.2016 06:32
-- URL zum Konzept
ALTER TABLE M_ProductionLineMA ADD COLUMN M_ProductionLineMA_ID numeric(10,0) NOT NULL DEFAULT nextval('m_productionlinema_seq')
;

-- 03.11.2016 06:32
-- URL zum Konzept
ALTER TABLE M_ProductionLineMA DROP CONSTRAINT IF EXISTS m_productionlinema_pkey
;

-- 03.11.2016 06:32
-- URL zum Konzept
ALTER TABLE M_ProductionLineMA ADD CONSTRAINT m_productionlinema_pkey PRIMARY KEY (M_ProductionLineMA_ID)
;

--
-- DDL for creating a single PK for M_Cost_ID
--
-- 03.11.2016 06:36
-- URL zum Konzept
ALTER TABLE M_Cost ADD COLUMN M_Cost_ID numeric(10,0) NOT NULL DEFAULT nextval('m_cost_seq')
;

-- 03.11.2016 06:36
-- URL zum Konzept
ALTER TABLE M_Cost DROP CONSTRAINT IF EXISTS m_cost_pkey
;

-- 03.11.2016 06:36
-- URL zum Konzept
ALTER TABLE M_Cost ADD CONSTRAINT m_cost_pkey PRIMARY KEY (M_Cost_ID)
;

--
-- DDL for creating a single PK for R_Group_Prospect
--
-- 03.11.2016 06:39
-- URL zum Konzept
ALTER TABLE R_Group_Prospect ADD COLUMN R_Group_Prospect_ID numeric(10,0) NOT NULL DEFAULT nextval('r_group_prospect_seq')
;

-- 03.11.2016 06:39
-- URL zum Konzept
ALTER TABLE R_Group_Prospect DROP CONSTRAINT IF EXISTS r_group_prospect_pkey
;

-- 03.11.2016 06:39
-- URL zum Konzept
ALTER TABLE R_Group_Prospect ADD CONSTRAINT r_group_prospect_pkey PRIMARY KEY (R_Group_Prospect_ID)
;

--
-- DDL for creating a single PK for M_HU_Item_Snapshot
--
-- 03.11.2016 07:11
-- URL zum Konzept
ALTER TABLE M_HU_Item_Snapshot ADD COLUMN M_HU_Item_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_item_snapshot_seq')
;

-- 03.11.2016 07:11
-- URL zum Konzept
ALTER TABLE M_HU_Item_Snapshot DROP CONSTRAINT IF EXISTS m_hu_item_snapshot_pkey
;

-- 03.11.2016 07:11
-- URL zum Konzept
ALTER TABLE M_HU_Item_Snapshot ADD CONSTRAINT m_hu_item_snapshot_pkey PRIMARY KEY (M_HU_Item_Snapshot_ID)
;

--
-- DDL for creating a single PK for M_HU_Item_Storage_Snapshot
--
-- 03.11.2016 07:11
-- URL zum Konzept
ALTER TABLE M_HU_Item_Storage_Snapshot ADD COLUMN M_HU_Item_Storage_Snapshot_ID numeric(10,0) NOT NULL DEFAULT nextval('m_hu_item_storage_snapshot_seq')
;

-- 03.11.2016 07:11
-- URL zum Konzept
ALTER TABLE M_HU_Item_Storage_Snapshot DROP CONSTRAINT IF EXISTS m_hu_item_storage_snapshot_pkey
;

-- 03.11.2016 07:11
-- URL zum Konzept
ALTER TABLE M_HU_Item_Storage_Snapshot ADD CONSTRAINT m_hu_item_storage_snapshot_pkey PRIMARY KEY (M_HU_Item_Storage_Snapshot_ID)
;

--
-- DDL for creating a single PK for C_BP_Withholding
--
-- 03.11.2016 07:23
-- URL zum Konzept
ALTER TABLE C_BP_Withholding ADD COLUMN C_BP_Withholding_ID numeric(10,0) NOT NULL DEFAULT nextval('c_bp_withholding_seq')
;

-- 03.11.2016 07:23
-- URL zum Konzept
ALTER TABLE C_BP_Withholding DROP CONSTRAINT IF EXISTS c_bp_withholding_pkey
;

-- 03.11.2016 07:23
-- URL zum Konzept
ALTER TABLE C_BP_Withholding ADD CONSTRAINT c_bp_withholding_pkey PRIMARY KEY (C_BP_Withholding_ID)
;


--
-- DDL for creating a single PK for C_ProjectIssueMA
--
-- 03.11.2016 07:23
-- URL zum Konzept
ALTER TABLE C_ProjectIssueMA ADD COLUMN C_ProjectIssueMA_ID numeric(10,0) NOT NULL DEFAULT nextval('c_projectissuema_seq')
;

-- 03.11.2016 07:23
-- URL zum Konzept
ALTER TABLE C_ProjectIssueMA DROP CONSTRAINT IF EXISTS c_projectissuema_pkey
;

-- 03.11.2016 07:23
-- URL zum Konzept
ALTER TABLE C_ProjectIssueMA ADD CONSTRAINT c_projectissuema_pkey PRIMARY KEY (C_ProjectIssueMA_ID)
;


--
-- DDL for creating a single PK for M_TransactionAllocation
--
-- 03.11.2016 07:24
-- URL zum Konzept
ALTER TABLE M_TransactionAllocation ADD COLUMN M_TransactionAllocation_ID numeric(10,0) NOT NULL DEFAULT nextval('m_transactionallocation_seq')
;

-- 03.11.2016 07:24
-- URL zum Konzept
ALTER TABLE M_TransactionAllocation DROP CONSTRAINT IF EXISTS m_transactionallocation_pkey
;

-- 03.11.2016 07:24
-- URL zum Konzept
ALTER TABLE M_TransactionAllocation ADD CONSTRAINT m_transactionallocation_pkey PRIMARY KEY (M_TransactionAllocation_ID)
;


--
-- DDL for creating a single PK for P_AdvCommissionDetails
--
-- 03.11.2016 07:24
-- URL zum Konzept
ALTER TABLE P_AdvCommissionDetails ADD COLUMN P_AdvCommissionDetails_ID numeric(10,0) NOT NULL DEFAULT nextval('p_advcommissiondetails_seq')
;

-- 03.11.2016 07:24
-- URL zum Konzept
ALTER TABLE P_AdvCommissionDetails DROP CONSTRAINT IF EXISTS p_advcommissiondetails_pkey
;

-- 03.11.2016 07:24
-- URL zum Konzept
ALTER TABLE P_AdvCommissionDetails ADD CONSTRAINT p_advcommissiondetails_pkey PRIMARY KEY (P_AdvCommissionDetails_ID)
;


--
-- DDL for creating a single PK for PP_MRP_Alloc
--

-- 03.11.2016 07:26
-- URL zum Konzept
ALTER TABLE PP_MRP_Alloc ADD COLUMN PP_MRP_Alloc_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_mrp_alloc_seq')
;

-- 03.11.2016 07:26
-- URL zum Konzept
ALTER TABLE PP_MRP_Alloc DROP CONSTRAINT IF EXISTS pp_mrp_alloc_pkey
;

-- 03.11.2016 07:26
-- URL zum Konzept
ALTER TABLE PP_MRP_Alloc ADD CONSTRAINT pp_mrp_alloc_pkey PRIMARY KEY (PP_MRP_Alloc_ID)
;

--
-- DDL for creating a single PK for PP_MRP_Alternative
--
ALTER TABLE pp_mrp_alternative DROP CONSTRAINT IF EXISTS pp_mrp_alternative_key;
-- 03.11.2016 07:27
-- URL zum Konzept
ALTER TABLE PP_MRP_Alternative ADD COLUMN PP_MRP_Alternative_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_mrp_alternative_seq')
;

-- 03.11.2016 07:27
-- URL zum Konzept
ALTER TABLE PP_MRP_Alternative DROP CONSTRAINT IF EXISTS pp_mrp_alternative_pkey
;

-- 03.11.2016 07:27
-- URL zum Konzept
ALTER TABLE PP_MRP_Alternative ADD CONSTRAINT pp_mrp_alternative_pkey PRIMARY KEY (PP_MRP_Alternative_ID)
;


--
-- DDL for creating a single PK for R_CategoryUpdates
--
-- 03.11.2016 07:28
-- URL zum Konzept
ALTER TABLE R_CategoryUpdates ADD COLUMN R_CategoryUpdates_ID numeric(10,0) NOT NULL DEFAULT nextval('r_categoryupdates_seq')
;

-- 03.11.2016 07:28
-- URL zum Konzept
ALTER TABLE R_CategoryUpdates DROP CONSTRAINT IF EXISTS r_categoryupdates_pkey
;

-- 03.11.2016 07:28
-- URL zum Konzept
ALTER TABLE R_CategoryUpdates ADD CONSTRAINT r_categoryupdates_pkey PRIMARY KEY (R_CategoryUpdates_ID)
;

--
-- DDL for creating a single PK for R_ContactInterest
--
ALTER TABLE r_contactinterest DROP CONSTRAINT IF EXISTS r_contactinterest_key;

-- 03.11.2016 07:31
-- URL zum Konzept
ALTER TABLE R_ContactInterest ADD COLUMN R_ContactInterest_ID numeric(10,0) NOT NULL DEFAULT nextval('r_contactinterest_seq')
;

-- 03.11.2016 07:31
-- URL zum Konzept
ALTER TABLE R_ContactInterest DROP CONSTRAINT IF EXISTS r_contactinterest_pkey
;

-- 03.11.2016 07:31
-- URL zum Konzept
ALTER TABLE R_ContactInterest ADD CONSTRAINT r_contactinterest_pkey PRIMARY KEY (R_ContactInterest_ID)
;

--
-- DDL for creating a single PK for R_GroupUpdates
--
-- 03.11.2016 07:32
-- URL zum Konzept
ALTER TABLE R_GroupUpdates ADD COLUMN R_GroupUpdates_ID numeric(10,0) NOT NULL DEFAULT nextval('r_groupupdates_seq')
;

-- 03.11.2016 07:32
-- URL zum Konzept
ALTER TABLE R_GroupUpdates DROP CONSTRAINT IF EXISTS r_groupupdates_pkey
;

-- 03.11.2016 07:32
-- URL zum Konzept
ALTER TABLE R_GroupUpdates ADD CONSTRAINT r_groupupdates_pkey PRIMARY KEY (R_GroupUpdates_ID)
;

--
-- DDL for creating a single PK for R_RequestTypeUpdates
--
-- 03.11.2016 07:32
-- URL zum Konzept
ALTER TABLE R_RequestTypeUpdates ADD COLUMN R_RequestTypeUpdates_ID numeric(10,0) NOT NULL DEFAULT nextval('r_requesttypeupdates_seq')
;

-- 03.11.2016 07:32
-- URL zum Konzept
ALTER TABLE R_RequestTypeUpdates DROP CONSTRAINT IF EXISTS r_requesttypeupdates_pkey
;

-- 03.11.2016 07:32
-- URL zum Konzept
ALTER TABLE R_RequestTypeUpdates ADD CONSTRAINT r_requesttypeupdates_pkey PRIMARY KEY (R_RequestTypeUpdates_ID)
;


--
-- DDL for creating a single PK for R_RequestUpdates
--
-- 03.11.2016 07:33
-- URL zum Konzept
ALTER TABLE R_RequestUpdates ADD COLUMN R_RequestUpdates_ID numeric(10,0) NOT NULL DEFAULT nextval('r_requestupdates_seq')
;

-- 03.11.2016 07:33
-- URL zum Konzept
ALTER TABLE R_RequestUpdates DROP CONSTRAINT IF EXISTS r_requestupdates_pkey
;

-- 03.11.2016 07:33
-- URL zum Konzept
ALTER TABLE R_RequestUpdates ADD CONSTRAINT r_requestupdates_pkey PRIMARY KEY (R_RequestUpdates_ID)
;

COMMIT;



--
-- DML for creating a single PK for ad_pinstance_para
--

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543214,0,'AD_PInstance_Para_ID',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_PInstance_Para','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543214 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555290,543214,0,13,283,'N','AD_PInstance_Para_ID',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:39:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 26.10.2016 21:39
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555290 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557358,0,540633,TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.async','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557358 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557359,0,664,TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557359 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557360,0,53022,TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557360 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555290,557361,0,540672,TO_TIMESTAMP('2016-10-26 21:40:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.async','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Para',TO_TIMESTAMP('2016-10-26 21:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 21:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557361 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 07:06
-- URL zum Konzept
UPDATE AD_Column SET IsKey='N',Updated=TO_TIMESTAMP('2016-10-27 07:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8779
;

-- 27.10.2016 07:10
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2016-10-27 07:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8779
;


--
-- DML for creating a single PK for ad_pinstance_Log
--


-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543216,0,'AD_PInstance_Log_ID',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_PInstance_Log','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543216 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555296,543216,0,13,578,'N','AD_PInstance_Log_ID',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:07:05','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 27.10.2016 07:07
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555296 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555296,557362,0,540634,TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.async','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557362 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555296,557363,0,665,TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','AD_PInstance_Log',TO_TIMESTAMP('2016-10-27 07:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557363 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


--
-- DML to give M_MovementLineMA a single PK column (FRESH-618 gh #333)
--
-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543212,0,'M_MovementLineMA_ID',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_MovementLineMA','M_MovementLineMA',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543212 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555276,543212,0,13,764,'N','M_MovementLineMA_ID',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_MovementLineMA',TO_TIMESTAMP('2016-10-26 14:51:08','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555276 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555276,557357,0,750,TO_TIMESTAMP('2016-10-26 14:51:19','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','M_MovementLineMA',TO_TIMESTAMP('2016-10-26 14:51:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 14:51
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557357 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



--
-- DML to give M_HU_Snapshot a single PK column (FRESH-618 gh #333)
--
-- 28.10.2016 06:34
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555346,542807,0,13,540669,'N','M_HU_Snapshot_ID',TO_TIMESTAMP('2016-10-28 06:34:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units (snapshot)',TO_TIMESTAMP('2016-10-28 06:34:40','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 28.10.2016 06:34
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555346 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML to give M_HU_Storage_Snapshot_ID a single PK column (FRESH-618 gh #333)
--
-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555347,542811,0,13,540672,'N','M_HU_Storage_Snapshot_ID',TO_TIMESTAMP('2016-10-28 06:35:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units Storage Snapshot',TO_TIMESTAMP('2016-10-28 06:35:17','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555347 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

--
-- DML to give M_HU_Attribute_Snapshot_ID a single PK column (FRESH-618 gh #333)
--
-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555348,542810,0,13,540671,'N','M_HU_Attribute_Snapshot_ID',TO_TIMESTAMP('2016-10-28 06:35:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units Attribute',TO_TIMESTAMP('2016-10-28 06:35:34','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 28.10.2016 06:35
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555348 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML for creating a single PK for M_AttributeInstance
--
-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543223,0,'M_AttributeInstance_ID',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_AttributeInstance','M_AttributeInstance',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543223 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555375,543223,0,13,561,'N','M_AttributeInstance_ID',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_AttributeInstance',TO_TIMESTAMP('2016-10-31 14:21:01','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 31.10.2016 14:21
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555375 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML for creating a single PK for M_InventoryLineMA
--
-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543224,0,'M_InventoryLineMA_ID',TO_TIMESTAMP('2016-11-03 06:31:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_InventoryLineMA','M_InventoryLineMA',TO_TIMESTAMP('2016-11-03 06:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543224 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555404,543224,0,13,763,'N','M_InventoryLineMA_ID',TO_TIMESTAMP('2016-11-03 06:31:23','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_InventoryLineMA',TO_TIMESTAMP('2016-11-03 06:31:23','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555404 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555404,557389,0,540560,TO_TIMESTAMP('2016-11-03 06:31:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.swat','Y','Y','N','N','N','N','N','N','N','M_InventoryLineMA',TO_TIMESTAMP('2016-11-03 06:31:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557389 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555404,557390,0,53134,TO_TIMESTAMP('2016-11-03 06:31:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','M_InventoryLineMA',TO_TIMESTAMP('2016-11-03 06:31:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557390 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555404,557391,0,749,TO_TIMESTAMP('2016-11-03 06:31:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','M_InventoryLineMA',TO_TIMESTAMP('2016-11-03 06:31:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557391 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


--
-- DML for creating a single PK for M_ProductionLineMA
--
-- 03.11.2016 06:32
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543225,0,'M_ProductionLineMA_ID',TO_TIMESTAMP('2016-11-03 06:32:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Production Line MA','Production Line MA',TO_TIMESTAMP('2016-11-03 06:32:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:32
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543225 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 06:32
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555405,543225,0,13,765,'N','M_ProductionLineMA_ID',TO_TIMESTAMP('2016-11-03 06:32:24','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Production Line MA',TO_TIMESTAMP('2016-11-03 06:32:24','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 06:32
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555405 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML for creating a single PK for M_Cost
--
-- 03.11.2016 06:36
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543226,0,'M_Cost_ID',TO_TIMESTAMP('2016-11-03 06:36:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Cost','Product Cost',TO_TIMESTAMP('2016-11-03 06:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:36
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543226 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 06:36
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555406,543226,0,13,771,'N','M_Cost_ID',TO_TIMESTAMP('2016-11-03 06:36:23','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Product Cost',TO_TIMESTAMP('2016-11-03 06:36:23','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 06:36
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555406 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.11.2016 06:36
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555406,557392,0,701,TO_TIMESTAMP('2016-11-03 06:36:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Product Cost',TO_TIMESTAMP('2016-11-03 06:36:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:36
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557392 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for creating a single PK for R_Group_Prospect
--
-- 03.11.2016 06:39
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543227,0,'R_Group_Prospect_ID',TO_TIMESTAMP('2016-11-03 06:39:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.callcenter','Y','Request Group Selected Prospects','Request Group Selected Prospects',TO_TIMESTAMP('2016-11-03 06:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:39
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543227 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 06:39
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555407,543227,0,13,540016,'N','R_Group_Prospect_ID',TO_TIMESTAMP('2016-11-03 06:39:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.callcenter',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Request Group Selected Prospects',TO_TIMESTAMP('2016-11-03 06:39:37','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 06:39
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555407 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.11.2016 06:39
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555407,557393,0,540032,TO_TIMESTAMP('2016-11-03 06:39:37','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.callcenter','Y','Y','N','N','N','N','N','N','N','Request Group Selected Prospects',TO_TIMESTAMP('2016-11-03 06:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 06:39
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557393 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for creating a single PK for M_HU_Item_Snapshot
--
-- 03.11.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555409,542809,0,13,540670,'N','M_HU_Item_Snapshot_ID',TO_TIMESTAMP('2016-11-03 07:11:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units (snapshot)',TO_TIMESTAMP('2016-11-03 07:11:32','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555409 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555410,542812,0,13,540673,'N','M_HU_Item_Storage_Snapshot_ID',TO_TIMESTAMP('2016-11-03 07:11:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Handling Units Item Storage Snapshot',TO_TIMESTAMP('2016-11-03 07:11:59','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555410 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
--
-- DML for creating a single PK for C_BP_Withholding
--
-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543229,0,'C_BP_Withholding_ID',TO_TIMESTAMP('2016-11-03 07:23:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BP_Withholding','C_BP_Withholding',TO_TIMESTAMP('2016-11-03 07:23:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543229 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555414,543229,0,13,299,'N','C_BP_Withholding_ID',TO_TIMESTAMP('2016-11-03 07:23:09','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','C_BP_Withholding',TO_TIMESTAMP('2016-11-03 07:23:09','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555414 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555414,557395,0,229,TO_TIMESTAMP('2016-11-03 07:23:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','C_BP_Withholding',TO_TIMESTAMP('2016-11-03 07:23:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557395 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



--
-- DML for creating a single PK for C_ProjectIssueMA
--
-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543230,0,'C_ProjectIssueMA_ID',TO_TIMESTAMP('2016-11-03 07:23:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_ProjectIssueMA','C_ProjectIssueMA',TO_TIMESTAMP('2016-11-03 07:23:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543230 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555415,543230,0,13,761,'N','C_ProjectIssueMA_ID',TO_TIMESTAMP('2016-11-03 07:23:41','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','C_ProjectIssueMA',TO_TIMESTAMP('2016-11-03 07:23:41','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:23
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555415 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML for creating a single PK for M_TransactionAllocation
--
-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543231,0,'M_TransactionAllocation_ID',TO_TIMESTAMP('2016-11-03 07:24:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_TransactionAllocation','M_TransactionAllocation',TO_TIMESTAMP('2016-11-03 07:24:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543231 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555416,543231,0,13,636,'N','M_TransactionAllocation_ID',TO_TIMESTAMP('2016-11-03 07:24:18','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','M_TransactionAllocation',TO_TIMESTAMP('2016-11-03 07:24:18','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555416 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


--
-- DML for creating a single PK for P_AdvCommissionDetails
--
-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543232,0,'P_AdvCommissionDetails_ID',TO_TIMESTAMP('2016-11-03 07:24:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.commission','Y','AdvCommision Details View','AdvCommision Details View',TO_TIMESTAMP('2016-11-03 07:24:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543232 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555417,543232,0,13,540183,'N','P_AdvCommissionDetails_ID',TO_TIMESTAMP('2016-11-03 07:24:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.commission',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AdvCommision Details View',TO_TIMESTAMP('2016-11-03 07:24:40','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555417 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555417,557396,0,540194,TO_TIMESTAMP('2016-11-03 07:24:41','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.commission','Y','Y','N','N','N','N','N','N','N','AdvCommision Details View',TO_TIMESTAMP('2016-11-03 07:24:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:24
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557396 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for creating a single PK for  PP_MRP_Alloc
--
-- 03.11.2016 07:26
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555419,542648,0,13,540632,'N','PP_MRP_Alloc_ID',TO_TIMESTAMP('2016-11-03 07:26:16','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','MRP Supply-Demand Allocation',TO_TIMESTAMP('2016-11-03 07:26:16','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:26
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555419 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 07:26
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555419,557397,0,540648,TO_TIMESTAMP('2016-11-03 07:26:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','MRP Supply-Demand Allocation',TO_TIMESTAMP('2016-11-03 07:26:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:26
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557397 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 07:26
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555419,557398,0,540647,TO_TIMESTAMP('2016-11-03 07:26:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','MRP Supply-Demand Allocation',TO_TIMESTAMP('2016-11-03 07:26:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:26
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557398 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



--
-- DML for creating a single PK for PP_MRP_Alternative
--
-- 03.11.2016 07:27
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555421,542603,0,13,540627,'N','PP_MRP_Alternative_ID',TO_TIMESTAMP('2016-11-03 07:27:57','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','MRP Alternative',TO_TIMESTAMP('2016-11-03 07:27:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:27
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555421 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.11.2016 07:27
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555421,557399,0,540643,TO_TIMESTAMP('2016-11-03 07:27:57','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','MRP Alternative',TO_TIMESTAMP('2016-11-03 07:27:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:27
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557399 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



--
-- DML for creating a single PK for R_CategoryUpdates
--
-- 03.11.2016 07:28
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543233,0,'R_CategoryUpdates_ID',TO_TIMESTAMP('2016-11-03 07:28:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_CategoryUpdates','R_CategoryUpdates',TO_TIMESTAMP('2016-11-03 07:28:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:28
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543233 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:28
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555422,543233,0,13,785,'N','R_CategoryUpdates_ID',TO_TIMESTAMP('2016-11-03 07:28:46','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','R_CategoryUpdates',TO_TIMESTAMP('2016-11-03 07:28:46','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:28
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555422 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 07:28
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555422,557400,0,715,TO_TIMESTAMP('2016-11-03 07:28:46','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','R_CategoryUpdates',TO_TIMESTAMP('2016-11-03 07:28:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:28
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557400 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



--
-- DML for creating a single PK for R_ContactInterest
--
-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543235,0,'R_ContactInterest_ID',TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_ContactInterest','R_ContactInterest',TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543235 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555424,543235,0,13,528,'N','R_ContactInterest_ID',TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','R_ContactInterest',TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555424 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555424,557401,0,536,TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','R_ContactInterest',TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557401 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555424,557402,0,540062,TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.callcenter','Y','Y','N','N','N','N','N','N','N','R_ContactInterest',TO_TIMESTAMP('2016-11-03 07:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557402 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555424,557403,0,53101,TO_TIMESTAMP('2016-11-03 07:31:29','YYYY-MM-DD HH24:MI:SS'),100,10,'EE02','Y','Y','N','N','N','N','N','N','N','R_ContactInterest',TO_TIMESTAMP('2016-11-03 07:31:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557403 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555424,557404,0,439,TO_TIMESTAMP('2016-11-03 07:31:29','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','R_ContactInterest',TO_TIMESTAMP('2016-11-03 07:31:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557404 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for creating a single PK for R_GroupUpdates
--
-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543236,0,'R_GroupUpdates_ID',TO_TIMESTAMP('2016-11-03 07:32:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_GroupUpdates','R_GroupUpdates',TO_TIMESTAMP('2016-11-03 07:32:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543236 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555425,543236,0,13,786,'N','R_GroupUpdates_ID',TO_TIMESTAMP('2016-11-03 07:32:19','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','R_GroupUpdates',TO_TIMESTAMP('2016-11-03 07:32:19','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555425 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555425,557405,0,716,TO_TIMESTAMP('2016-11-03 07:32:20','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','R_GroupUpdates',TO_TIMESTAMP('2016-11-03 07:32:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557405 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for creating a single PK for R_RequestTypeUpdates
--
-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543237,0,'R_RequestTypeUpdates_ID',TO_TIMESTAMP('2016-11-03 07:32:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Request Type Updates','Request Type Updates',TO_TIMESTAMP('2016-11-03 07:32:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543237 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555426,543237,0,13,784,'N','R_RequestTypeUpdates_ID',TO_TIMESTAMP('2016-11-03 07:32:39','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Request Type Updates',TO_TIMESTAMP('2016-11-03 07:32:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555426 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555426,557406,0,718,TO_TIMESTAMP('2016-11-03 07:32:40','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Request Type Updates',TO_TIMESTAMP('2016-11-03 07:32:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:32
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557406 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for creating a single PK for R_RequestUpdates
--
-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543238,0,'R_RequestUpdates_ID',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Request Update Recipiants','Request Update Recipiants',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543238 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555427,543238,0,13,783,'N','R_RequestUpdates_ID',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Request Update Recipiants',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555427 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555427,557407,0,719,TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Request Update Recipiants',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557407 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555427,557408,0,540034,TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.callcenter','Y','Y','N','N','N','N','N','N','N','Request Update Recipiants',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557408 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555427,557409,0,714,TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Request Update Recipiants',TO_TIMESTAMP('2016-11-03 07:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.11.2016 07:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557409 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
