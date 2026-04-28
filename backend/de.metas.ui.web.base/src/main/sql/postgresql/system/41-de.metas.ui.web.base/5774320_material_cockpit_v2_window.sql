-- Run mode: SWING_CLIENT

-- Column: QtyDemand_QtySupply_V.Name
-- 2025-10-23T13:50:31.827Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591424,469,0,10,542218,'Name',TO_TIMESTAMP('2025-10-23 13:50:31.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D',600,'','Y','Y','N','N','Y','N','N','N','N','N','N','Name',1,TO_TIMESTAMP('2025-10-23 13:50:31.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T13:50:31.831Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591424 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T13:50:31.855Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- Column: QtyDemand_QtySupply_V.Value
-- 2025-10-23T13:50:32.595Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591425,620,0,10,542218,'Value',TO_TIMESTAMP('2025-10-23 13:50:32.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',255,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2025-10-23 13:50:32.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T13:50:32.598Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591425 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T13:50:32.712Z
/* DDL */  select update_Column_Translation_From_AD_Element(620)
;

-- 2025-10-23T13:50:33.186Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584140,0,'qtytoproduce',TO_TIMESTAMP('2025-10-23 13:50:33.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','qtytoproduce','qtytoproduce',TO_TIMESTAMP('2025-10-23 13:50:33.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T13:50:33.190Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584140 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QtyDemand_QtySupply_V.qtytoproduce
-- 2025-10-23T13:50:33.617Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591426,584140,0,29,542218,'qtytoproduce',TO_TIMESTAMP('2025-10-23 13:50:33.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','qtytoproduce',TO_TIMESTAMP('2025-10-23 13:50:33.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T13:50:33.619Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591426 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T13:50:33.732Z
/* DDL */  select update_Column_Translation_From_AD_Element(584140)
;

-- 2025-10-23T13:50:34.282Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584141,0,'qtyforecasted',TO_TIMESTAMP('2025-10-23 13:50:34.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','qtyforecasted','qtyforecasted',TO_TIMESTAMP('2025-10-23 13:50:34.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T13:50:34.284Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584141 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QtyDemand_QtySupply_V.qtyforecasted
-- 2025-10-23T13:50:34.697Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591427,584141,0,29,542218,'qtyforecasted',TO_TIMESTAMP('2025-10-23 13:50:34.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','qtyforecasted',TO_TIMESTAMP('2025-10-23 13:50:34.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T13:50:34.699Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591427 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T13:50:34.819Z
/* DDL */  select update_Column_Translation_From_AD_Element(584141)
;

-- 2025-10-23T13:50:35.229Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584142,0,'qtystock',TO_TIMESTAMP('2025-10-23 13:50:35.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','qtystock','qtystock',TO_TIMESTAMP('2025-10-23 13:50:35.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T13:50:35.231Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584142 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QtyDemand_QtySupply_V.qtystock
-- 2025-10-23T13:50:35.625Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591428,584142,0,29,542218,'qtystock',TO_TIMESTAMP('2025-10-23 13:50:35.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','N','qtystock',TO_TIMESTAMP('2025-10-23 13:50:35.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T13:50:35.627Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591428 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T13:50:35.734Z
/* DDL */  select update_Column_Translation_From_AD_Element(584142)
;

-- 2025-10-23T13:50:36.142Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584143,0,'QtyDemand_QtySupply_V_ID',TO_TIMESTAMP('2025-10-23 13:50:36.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','MD_Cockpit QtyDemand and QtySupply','MD_Cockpit QtyDemand and QtySupply',TO_TIMESTAMP('2025-10-23 13:50:36.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T13:50:36.145Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584143 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QtyDemand_QtySupply_V.QtyDemand_QtySupply_V_ID
-- 2025-10-23T13:50:36.548Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591429,584143,0,13,542218,'QtyDemand_QtySupply_V_ID',TO_TIMESTAMP('2025-10-23 13:50:36.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',19,'Y','Y','N','N','N','Y','N','N','N','N','N','MD_Cockpit QtyDemand and QtySupply',TO_TIMESTAMP('2025-10-23 13:50:36.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T13:50:36.550Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591429 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T13:50:36.660Z
/* DDL */  select update_Column_Translation_From_AD_Element(584143)
;

-- 2025-10-23T13:53:09.796Z
UPDATE AD_Element SET ColumnName='QtyForecasted',Updated=TO_TIMESTAMP('2025-10-23 13:53:09.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584141
;

-- 2025-10-23T13:53:09.798Z
UPDATE AD_Column SET ColumnName='QtyForecasted' WHERE AD_Element_ID=584141
;

-- 2025-10-23T13:53:09.799Z
UPDATE AD_Process_Para SET ColumnName='QtyForecasted' WHERE AD_Element_ID=584141
;

-- 2025-10-23T13:53:09.803Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584141,'de_DE')
;

-- Element: QtyForecasted
-- 2025-10-23T13:53:49.196Z
UPDATE AD_Element_Trl SET Name='Prognose - offen', PrintName='Prognose - offen',Updated=TO_TIMESTAMP('2025-10-23 13:53:49.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584141 AND AD_Language='de_CH'
;

-- 2025-10-23T13:53:49.198Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:53:49.468Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584141,'de_CH')
;

-- Element: QtyForecasted
-- 2025-10-23T13:53:51.864Z
UPDATE AD_Element_Trl SET Name='Prognose - offen', PrintName='Prognose - offen',Updated=TO_TIMESTAMP('2025-10-23 13:53:51.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584141 AND AD_Language='fr_CH'
;

-- 2025-10-23T13:53:51.865Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:53:52.250Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584141,'fr_CH')
;

-- Element: QtyForecasted
-- 2025-10-23T13:53:54.303Z
UPDATE AD_Element_Trl SET Name='Prognose - offen', PrintName='Prognose - offen',Updated=TO_TIMESTAMP('2025-10-23 13:53:54.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584141 AND AD_Language='de_DE'
;

-- 2025-10-23T13:53:54.304Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:53:55.026Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584141,'de_DE')
;

-- 2025-10-23T13:53:55.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584141,'de_DE')
;

-- Element: QtyForecasted
-- 2025-10-23T13:54:07.905Z
UPDATE AD_Element_Trl SET Name='Forecast - open', PrintName='Forecast - open',Updated=TO_TIMESTAMP('2025-10-23 13:54:07.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584141 AND AD_Language='en_US'
;

-- 2025-10-23T13:54:07.906Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:54:08.178Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584141,'en_US')
;

-- 2025-10-23T13:54:40.495Z
UPDATE AD_Element SET ColumnName='QtyStock',Updated=TO_TIMESTAMP('2025-10-23 13:54:40.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584142
;

-- 2025-10-23T13:54:40.496Z
UPDATE AD_Column SET ColumnName='QtyStock' WHERE AD_Element_ID=584142
;

-- 2025-10-23T13:54:40.498Z
UPDATE AD_Process_Para SET ColumnName='QtyStock' WHERE AD_Element_ID=584142
;

-- 2025-10-23T13:54:40.501Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584142,'de_DE')
;

-- Element: QtyStock
-- 2025-10-23T13:54:48.058Z
UPDATE AD_Element_Trl SET Name='Bestand', PrintName='Bestand',Updated=TO_TIMESTAMP('2025-10-23 13:54:48.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584142 AND AD_Language='de_CH'
;

-- 2025-10-23T13:54:48.059Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:54:48.351Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584142,'de_CH')
;

-- Element: QtyStock
-- 2025-10-23T13:54:54.972Z
UPDATE AD_Element_Trl SET Name='Bestand', PrintName='Bestand',Updated=TO_TIMESTAMP('2025-10-23 13:54:54.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584142 AND AD_Language='fr_CH'
;

-- 2025-10-23T13:54:54.973Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:54:55.250Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584142,'fr_CH')
;

-- Element: QtyStock
-- 2025-10-23T13:54:57.411Z
UPDATE AD_Element_Trl SET Name='Bestand', PrintName='Bestand',Updated=TO_TIMESTAMP('2025-10-23 13:54:57.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584142 AND AD_Language='de_DE'
;

-- 2025-10-23T13:54:57.412Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:54:57.869Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584142,'de_DE')
;

-- 2025-10-23T13:54:57.870Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584142,'de_DE')
;

-- Element: QtyStock
-- 2025-10-23T13:55:06.519Z
UPDATE AD_Element_Trl SET Name='Stock', PrintName='Stock',Updated=TO_TIMESTAMP('2025-10-23 13:55:06.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584142 AND AD_Language='en_US'
;

-- 2025-10-23T13:55:06.520Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T13:55:06.804Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584142,'en_US')
;

-- Column: QtyDemand_QtySupply_V.Value
-- 2025-10-23T14:02:38.287Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591425
;

-- 2025-10-23T14:02:38.296Z
DELETE FROM AD_Column WHERE AD_Column_ID=591425
;

-- Column: QtyDemand_QtySupply_V.Name
-- 2025-10-23T14:02:41.571Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591424
;

-- 2025-10-23T14:02:41.576Z
DELETE FROM AD_Column WHERE AD_Column_ID=591424
;

-- Column: QtyDemand_QtySupply_V.ProductName
-- 2025-10-23T14:02:53.536Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591430,2659,0,14,542218,'ProductName',TO_TIMESTAMP('2025-10-23 14:02:53.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes','D',600,'Y','Y','N','N','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2025-10-23 14:02:53.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T14:02:53.538Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591430 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T14:02:53.670Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659)
;

-- Column: QtyDemand_QtySupply_V.ProductValue
-- 2025-10-23T14:02:54.078Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591431,1675,0,10,542218,'ProductValue',TO_TIMESTAMP('2025-10-23 14:02:53.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','Produktschlüssel',TO_TIMESTAMP('2025-10-23 14:02:53.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-23T14:02:54.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591431 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-23T14:02:54.202Z
/* DDL */  select update_Column_Translation_From_AD_Element(1675)
;

-- 2025-10-23T14:03:12.962Z
UPDATE AD_Element SET ColumnName='QtyToProduce',Updated=TO_TIMESTAMP('2025-10-23 14:03:12.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584140
;

-- 2025-10-23T14:03:12.963Z
UPDATE AD_Column SET ColumnName='QtyToProduce' WHERE AD_Element_ID=584140
;

-- 2025-10-23T14:03:12.964Z
UPDATE AD_Process_Para SET ColumnName='QtyToProduce' WHERE AD_Element_ID=584140
;

-- 2025-10-23T14:03:12.967Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584140,'de_DE')
;

-- Element: QtyToProduce
-- 2025-10-23T14:03:28.887Z
UPDATE AD_Element_Trl SET Name='Produktion - offen', PrintName='Produktion - offen',Updated=TO_TIMESTAMP('2025-10-23 14:03:28.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584140 AND AD_Language='de_CH'
;

-- 2025-10-23T14:03:28.888Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T14:03:29.288Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584140,'de_CH')
;

-- Element: QtyToProduce
-- 2025-10-23T14:03:34.069Z
UPDATE AD_Element_Trl SET Name='Produktion - offen', PrintName='Produktion - offen',Updated=TO_TIMESTAMP('2025-10-23 14:03:34.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584140 AND AD_Language='fr_CH'
;

-- 2025-10-23T14:03:34.070Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T14:03:34.347Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584140,'fr_CH')
;

-- Element: QtyToProduce
-- 2025-10-23T14:03:36.507Z
UPDATE AD_Element_Trl SET Name='Produktion - offen', PrintName='Produktion - offen',Updated=TO_TIMESTAMP('2025-10-23 14:03:36.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584140 AND AD_Language='de_DE'
;

-- 2025-10-23T14:03:36.508Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-23T14:03:36.931Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584140,'de_DE')
;

-- 2025-10-23T14:03:36.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584140,'de_DE')
;

-- 2025-10-23T14:05:43.501Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584144,0,TO_TIMESTAMP('2025-10-23 14:05:43.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Material Cockpit v2','Material Cockpit v2',TO_TIMESTAMP('2025-10-23 14:05:43.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:05:43.503Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584144 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Material Cockpit v2, InternalName=null
-- 2025-10-23T14:05:58.860Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584144,0,541963,TO_TIMESTAMP('2025-10-23 14:05:58.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Material Cockpit v2','N',TO_TIMESTAMP('2025-10-23 14:05:58.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-10-23T14:05:58.862Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541963 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-10-23T14:05:58.865Z
/* DDL */  select update_window_translation_from_ad_element(584144)
;

-- 2025-10-23T14:05:58.876Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541963
;

-- 2025-10-23T14:05:58.880Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541963)
;

-- Tab: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply
-- Table: QtyDemand_QtySupply_V
-- 2025-10-23T14:09:19.279Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584143,0,548476,542218,541963,'Y',TO_TIMESTAMP('2025-10-23 14:09:19.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','QtyDemand_QtySupply_V','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'MD_Cockpit QtyDemand and QtySupply','N',10,0,TO_TIMESTAMP('2025-10-23 14:09:19.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:19.281Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548476 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-23T14:09:19.283Z
/* DDL */  select update_tab_translation_from_ad_element(584143)
;

-- 2025-10-23T14:09:19.286Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548476)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Mandant
-- Column: QtyDemand_QtySupply_V.AD_Client_ID
-- 2025-10-23T14:09:25.986Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584379,755066,0,548476,TO_TIMESTAMP('2025-10-23 14:09:25.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-23 14:09:25.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:25.988Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:25.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-23T14:09:26.497Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755066
;

-- 2025-10-23T14:09:26.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755066)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Sektion
-- Column: QtyDemand_QtySupply_V.AD_Org_ID
-- 2025-10-23T14:09:26.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584380,755067,0,548476,TO_TIMESTAMP('2025-10-23 14:09:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-23 14:09:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:26.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:26.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-23T14:09:26.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755067
;

-- 2025-10-23T14:09:26.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755067)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Reservierte Menge
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-10-23T14:09:26.898Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584381,755068,0,548476,TO_TIMESTAMP('2025-10-23 14:09:26.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Reservierte Menge',10,'D','Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.','Y','N','N','N','N','N','N','N','Reservierte Menge',TO_TIMESTAMP('2025-10-23 14:09:26.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:26.900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:26.901Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(532)
;

-- 2025-10-23T14:09:26.914Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755068
;

-- 2025-10-23T14:09:26.915Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755068)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Menge zu bewegen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-10-23T14:09:27.039Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584382,755069,0,548476,TO_TIMESTAMP('2025-10-23 14:09:26.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Menge zu bewegen',TO_TIMESTAMP('2025-10-23 14:09:26.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.041Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.042Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542204)
;

-- 2025-10-23T14:09:27.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755069
;

-- 2025-10-23T14:09:27.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755069)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Produkt
-- Column: QtyDemand_QtySupply_V.M_Product_ID
-- 2025-10-23T14:09:27.159Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584383,755070,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2025-10-23 14:09:27.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-10-23T14:09:27.244Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755070
;

-- 2025-10-23T14:09:27.245Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755070)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Maßeinheit
-- Column: QtyDemand_QtySupply_V.C_UOM_ID
-- 2025-10-23T14:09:27.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584384,755071,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2025-10-23 14:09:27.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.370Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-10-23T14:09:27.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755071
;

-- 2025-10-23T14:09:27.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755071)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2025-10-23T14:09:27.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584385,755072,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1024,'D','Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','N','N','N','N','N','N','N','AttributesKey (technical)',TO_TIMESTAMP('2025-10-23 14:09:27.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.539Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543666)
;

-- 2025-10-23T14:09:27.543Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755072
;

-- 2025-10-23T14:09:27.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755072)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Lager
-- Column: QtyDemand_QtySupply_V.M_Warehouse_ID
-- 2025-10-23T14:09:27.664Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584386,755073,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2025-10-23 14:09:27.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.667Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-10-23T14:09:27.708Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755073
;

-- 2025-10-23T14:09:27.709Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755073)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Produktion - offen
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-10-23T14:09:27.819Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591426,755074,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D','Y','N','N','N','N','N','N','N','Produktion - offen',TO_TIMESTAMP('2025-10-23 14:09:27.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.821Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584140)
;

-- 2025-10-23T14:09:27.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755074
;

-- 2025-10-23T14:09:27.825Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755074)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Prognose - offen
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-10-23T14:09:27.947Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591427,755075,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D','Y','N','N','N','N','N','N','N','Prognose - offen',TO_TIMESTAMP('2025-10-23 14:09:27.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:27.949Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:27.950Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584141)
;

-- 2025-10-23T14:09:27.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755075
;

-- 2025-10-23T14:09:27.953Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755075)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Bestand
-- Column: QtyDemand_QtySupply_V.QtyStock
-- 2025-10-23T14:09:28.075Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591428,755076,0,548476,TO_TIMESTAMP('2025-10-23 14:09:27.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D','Y','N','N','N','N','N','N','N','Bestand',TO_TIMESTAMP('2025-10-23 14:09:27.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:28.077Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:28.078Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584142)
;

-- 2025-10-23T14:09:28.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755076
;

-- 2025-10-23T14:09:28.081Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755076)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> MD_Cockpit QtyDemand and QtySupply
-- Column: QtyDemand_QtySupply_V.QtyDemand_QtySupply_V_ID
-- 2025-10-23T14:09:28.196Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591429,755077,0,548476,TO_TIMESTAMP('2025-10-23 14:09:28.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,19,'D','Y','N','N','N','N','N','N','N','MD_Cockpit QtyDemand and QtySupply',TO_TIMESTAMP('2025-10-23 14:09:28.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:28.198Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:28.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584143)
;

-- 2025-10-23T14:09:28.201Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755077
;

-- 2025-10-23T14:09:28.202Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755077)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Produktname
-- Column: QtyDemand_QtySupply_V.ProductName
-- 2025-10-23T14:09:28.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591430,755078,0,548476,TO_TIMESTAMP('2025-10-23 14:09:28.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes',600,'D','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2025-10-23 14:09:28.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:28.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:28.330Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2025-10-23T14:09:28.336Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755078
;

-- 2025-10-23T14:09:28.336Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755078)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Produktschlüssel
-- Column: QtyDemand_QtySupply_V.ProductValue
-- 2025-10-23T14:09:28.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591431,755079,0,548476,TO_TIMESTAMP('2025-10-23 14:09:28.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',255,'D','Y','N','N','N','N','N','N','N','Produktschlüssel',TO_TIMESTAMP('2025-10-23 14:09:28.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:09:28.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T14:09:28.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1675)
;

-- 2025-10-23T14:09:28.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755079
;

-- 2025-10-23T14:09:28.468Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755079)
;

-- Column: QtyDemand_QtySupply_V.AD_Org_ID
-- 2025-10-23T14:10:09.010Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-23 14:10:09.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=584380
;

-- Column: QtyDemand_QtySupply_V.M_Warehouse_ID
-- 2025-10-23T14:10:30.007Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-23 14:10:30.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=584386
;

-- Column: QtyDemand_QtySupply_V.ProductName
-- 2025-10-23T14:10:35.813Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-23 14:10:35.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591430
;

-- Column: QtyDemand_QtySupply_V.ProductValue
-- 2025-10-23T14:10:42.942Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-23 14:10:42.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591431
;

-- Tab: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D)
-- UI Section: Main
-- 2025-10-23T14:13:39.054Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548476,547003,TO_TIMESTAMP('2025-10-23 14:13:38.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-23 14:13:38.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Main')
;

-- 2025-10-23T14:13:39.057Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547003 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main
-- UI Column: 10
-- 2025-10-23T14:13:41.929Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548538,547003,TO_TIMESTAMP('2025-10-23 14:13:41.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-23 14:13:41.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main
-- UI Column: 20
-- 2025-10-23T14:14:04.363Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548539,547003,TO_TIMESTAMP('2025-10-23 14:14:04.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-23 14:14:04.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10
-- UI Element Group: main
-- 2025-10-23T14:14:29.077Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548538,553650,TO_TIMESTAMP('2025-10-23 14:14:28.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-23 14:14:28.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produktname
-- Column: QtyDemand_QtySupply_V.ProductName
-- 2025-10-23T14:14:43.171Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755078,0,548476,553650,637932,'F',TO_TIMESTAMP('2025-10-23 14:14:42.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',10,0,0,TO_TIMESTAMP('2025-10-23 14:14:42.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produktschlüssel
-- Column: QtyDemand_QtySupply_V.ProductValue
-- 2025-10-23T14:14:48.969Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755079,0,548476,553650,637933,'F',TO_TIMESTAMP('2025-10-23 14:14:48.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','Y','N','N','Y','N','N','N',0,'Produktschlüssel',20,0,0,TO_TIMESTAMP('2025-10-23 14:14:48.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Maßeinheit
-- Column: QtyDemand_QtySupply_V.C_UOM_ID
-- 2025-10-23T14:15:25.101Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755071,0,548476,553650,637934,'F',TO_TIMESTAMP('2025-10-23 14:15:24.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',30,0,0,TO_TIMESTAMP('2025-10-23 14:15:24.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: QtyDemand_QtySupply_V.C_UOM_ID
-- 2025-10-23T14:15:46.694Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-23 14:15:46.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=584384
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2025-10-23T14:16:23.165Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755072,0,548476,553650,637935,'F',TO_TIMESTAMP('2025-10-23 14:16:22.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','N','N','Y','N','N','N',0,'AttributesKey (technical)',40,0,0,TO_TIMESTAMP('2025-10-23 14:16:22.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Lager
-- Column: QtyDemand_QtySupply_V.M_Warehouse_ID
-- 2025-10-23T14:16:31.011Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755073,0,548476,553650,637936,'F',TO_TIMESTAMP('2025-10-23 14:16:30.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',50,0,0,TO_TIMESTAMP('2025-10-23 14:16:30.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20
-- UI Element Group: qties
-- 2025-10-23T14:16:43.385Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548539,553651,TO_TIMESTAMP('2025-10-23 14:16:43.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','qties',10,TO_TIMESTAMP('2025-10-23 14:16:43.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20
-- UI Element Group: org
-- 2025-10-23T14:16:48.269Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548539,553652,TO_TIMESTAMP('2025-10-23 14:16:48.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-10-23 14:16:48.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> org.Sektion
-- Column: QtyDemand_QtySupply_V.AD_Org_ID
-- 2025-10-23T14:16:55.483Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755067,0,548476,553652,637937,'F',TO_TIMESTAMP('2025-10-23 14:16:55.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-10-23 14:16:55.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Reservierte Menge
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-10-23T14:17:33.491Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755068,0,548476,553651,637938,'F',TO_TIMESTAMP('2025-10-23 14:17:33.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Reservierte Menge','Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.','Y','N','N','Y','N','N','N',0,'Reservierte Menge',10,0,0,TO_TIMESTAMP('2025-10-23 14:17:33.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Beauftragt - offen
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-10-23T14:18:34.769Z
UPDATE AD_Field SET AD_Name_ID=581468, Description=NULL, Help=NULL, Name='Beauftragt - offen',Updated=TO_TIMESTAMP('2025-10-23 14:18:34.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755068
;

-- 2025-10-23T14:18:34.771Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Beauftragt - offen' WHERE AD_Field_ID=755068 AND AD_Language='de_DE'
;

-- 2025-10-23T14:18:34.773Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581468)
;

-- 2025-10-23T14:18:34.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755068
;

-- 2025-10-23T14:18:34.780Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755068)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Bestellt - offen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-10-23T14:18:58.488Z
UPDATE AD_Field SET AD_Name_ID=581469, Description=NULL, Help=NULL, Name='Bestellt - offen',Updated=TO_TIMESTAMP('2025-10-23 14:18:58.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755069
;

-- 2025-10-23T14:18:58.489Z
UPDATE AD_Field_Trl trl SET Name='Bestellt - offen' WHERE AD_Field_ID=755069 AND AD_Language='de_DE'
;

-- 2025-10-23T14:18:58.491Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581469)
;

-- 2025-10-23T14:18:58.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755069
;

-- 2025-10-23T14:18:58.495Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755069)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - offen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-10-23T14:19:47.715Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755069,0,548476,553651,637939,'F',TO_TIMESTAMP('2025-10-23 14:19:47.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestellt - offen',20,0,0,TO_TIMESTAMP('2025-10-23 14:19:47.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Produktion - offen
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-10-23T14:19:55.282Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755074,0,548476,553651,637940,'F',TO_TIMESTAMP('2025-10-23 14:19:55.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Produktion - offen',30,0,0,TO_TIMESTAMP('2025-10-23 14:19:55.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Prognose - offen
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-10-23T14:20:07.873Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755075,0,548476,553651,637941,'F',TO_TIMESTAMP('2025-10-23 14:20:07.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Prognose - offen',40,0,0,TO_TIMESTAMP('2025-10-23 14:20:07.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestand
-- Column: QtyDemand_QtySupply_V.QtyStock
-- 2025-10-23T14:20:15.711Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755076,0,548476,553651,637942,'F',TO_TIMESTAMP('2025-10-23 14:20:15.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestand',50,0,0,TO_TIMESTAMP('2025-10-23 14:20:15.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produktschlüssel
-- Column: QtyDemand_QtySupply_V.ProductValue
-- 2025-10-23T14:21:13.990Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-23 14:21:13.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637933
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Produktname
-- Column: QtyDemand_QtySupply_V.ProductName
-- 2025-10-23T14:21:13.996Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-23 14:21:13.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637932
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Maßeinheit
-- Column: QtyDemand_QtySupply_V.C_UOM_ID
-- 2025-10-23T14:21:14.001Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-23 14:21:14.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637934
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.Lager
-- Column: QtyDemand_QtySupply_V.M_Warehouse_ID
-- 2025-10-23T14:21:14.006Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-23 14:21:14.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637936
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2025-10-23T14:21:14.012Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-23 14:21:14.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637935
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Reservierte Menge
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-10-23T14:22:30.336Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-23 14:22:30.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637938
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - offen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-10-23T14:22:30.342Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-23 14:22:30.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637939
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Produktion - offen
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-10-23T14:22:30.348Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-23 14:22:30.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637940
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Prognose - offen
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-10-23T14:22:30.353Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-23 14:22:30.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637941
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestand
-- Column: QtyDemand_QtySupply_V.QtyStock
-- 2025-10-23T14:22:30.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-23 14:22:30.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637942
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> org.Sektion
-- Column: QtyDemand_QtySupply_V.AD_Org_ID
-- 2025-10-23T14:22:30.363Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-10-23 14:22:30.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637937
;

-- Name: Material Cockpit v2
-- Action Type: W
-- Window: Material Cockpit v2(541963,D)
-- 2025-10-23T14:25:26.115Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584144,542264,0,541963,TO_TIMESTAMP('2025-10-23 14:25:25.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Material cockpit v2','Y','N','N','N','N','Material Cockpit v2',TO_TIMESTAMP('2025-10-23 14:25:25.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T14:25:26.117Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542264 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-23T14:25:26.118Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542264, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542264)
;

-- 2025-10-23T14:25:26.127Z
/* DDL */  select update_menu_translation_from_ad_element(584144)
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace (C_Workplace_User_Assign)`
-- 2025-10-23T14:25:26.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace (C_Workplace)`
-- 2025-10-23T14:25:26.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2025-10-23T14:25:26.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2025-10-23T14:25:26.705Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-10-23T14:25:26.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2025-10-23T14:25:26.707Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2025-10-23T14:25:26.708Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2025-10-23T14:25:26.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2025-10-23T14:25:26.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Forecast Lines (M_ForecastLine)`
-- 2025-10-23T14:25:26.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2025-10-23T14:25:26.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-23T14:25:26.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-23T14:25:26.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-23T14:25:26.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2025-10-23T14:25:26.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2025-10-23T14:25:26.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Handling Units (M_InventoryLine_HU)`
-- 2025-10-23T14:25:26.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542254 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2025-10-23T14:25:26.717Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule Quantity Details (MD_Candidate_QtyDetails)`
-- 2025-10-23T14:25:26.718Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542226 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit v2`
-- 2025-10-23T14:25:26.718Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542264 AND AD_Tree_ID=10
;

-- Window: Material Cockpit v2, InternalName=541963 ()
-- 2025-10-23T14:25:44.457Z
UPDATE AD_Window SET InternalName='541963 ()',Updated=TO_TIMESTAMP('2025-10-23 14:25:44.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541963
;

-- Window: Material Cockpit v2, InternalName=541963 (QtyDemand_QtySupply_V)
-- 2025-10-23T14:25:54.691Z
UPDATE AD_Window SET InternalName='541963 (QtyDemand_QtySupply_V)',Updated=TO_TIMESTAMP('2025-10-23 14:25:54.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541963
;

