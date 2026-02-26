-- Run mode: SWING_CLIENT

-- Element: IsQtyPerWarehouse
-- 2025-10-01T08:31:50.825Z
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, wird die verfügbare Menge in der Auftragszeile pro Lager berechnet. Wenn deaktiviert, wird die verfügbare Menge in der Auftragszeile über alle Lager hinweg summiert.',Updated=TO_TIMESTAMP('2025-10-01 08:31:50.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584046 AND AD_Language='de_CH'
;

-- 2025-10-01T08:31:50.831Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T08:31:51.410Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584046,'de_CH')
;

-- Element: IsQtyPerWarehouse
-- 2025-10-01T08:31:53.894Z
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, wird die verfügbare Menge in der Auftragszeile pro Lager berechnet. Wenn deaktiviert, wird die verfügbare Menge in der Auftragszeile über alle Lager hinweg summiert.',Updated=TO_TIMESTAMP('2025-10-01 08:31:53.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584046 AND AD_Language='de_DE'
;

-- 2025-10-01T08:31:53.895Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T08:31:55.625Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584046,'de_DE')
;

-- 2025-10-01T08:31:55.625Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584046,'de_DE')
;

-- Element: IsQtyPerWarehouse
-- 2025-10-01T08:32:04.355Z
UPDATE AD_Element_Trl SET Description='If enabled, the available quantity shown in the Sales Order Line is calculated separately per warehouse. If disabled, the available quantity in the Sales Order Line is calculated across all warehouses combined.',Updated=TO_TIMESTAMP('2025-10-01 08:32:04.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584046 AND AD_Language='en_US'
;

-- 2025-10-01T08:32:04.355Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T08:32:04.980Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584046,'en_US')
;

-- Element: IsQtyPerWarehouse
-- 2025-10-01T08:32:11.851Z
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, wird die verfügbare Menge in der Auftragszeile pro Lager berechnet. Wenn deaktiviert, wird die verfügbare Menge in der Auftragszeile über alle Lager hinweg summiert.',Updated=TO_TIMESTAMP('2025-10-01 08:32:11.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584046 AND AD_Language='fr_CH'
;

-- 2025-10-01T08:32:11.851Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T08:32:12.354Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584046,'fr_CH')
;

-- 2025-10-01T08:33:46.467Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584050,0,'WarehouseCode',TO_TIMESTAMP('2025-10-01 08:33:46.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel des Lagers','D','Schlüssel zur Identifikation des Lagers.','Y','Lager-Schlüssel','Lager-Schlüssel',TO_TIMESTAMP('2025-10-01 08:33:46.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-01T08:33:46.471Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584050 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: WarehouseCode
-- 2025-10-01T08:34:08.334Z
UPDATE AD_Element_Trl SET Description='Key of the Warehouse', Help='Key to identify the Warehouse', Name='Warehouse Key', PrintName='Warehouse Key',Updated=TO_TIMESTAMP('2025-10-01 08:34:08.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584050 AND AD_Language='en_US'
;

-- 2025-10-01T08:34:08.335Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-01T08:34:09.114Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584050,'en_US')
;

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: WarehouseCode
-- 2025-10-01T08:34:26.633Z
UPDATE AD_Process_Para SET AD_Element_ID=584050, ColumnName='WarehouseCode',Updated=TO_TIMESTAMP('2025-10-01 08:34:26.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543002
;

UPDATE AD_Process SET JSONPath='/available_for_sales_json_v?ExternalSystem=ilike.@ExternalSystem/%@&WarehouseCode=ilike.@WarehouseCode/%@&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-09-29 12:35:38.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585498
;
