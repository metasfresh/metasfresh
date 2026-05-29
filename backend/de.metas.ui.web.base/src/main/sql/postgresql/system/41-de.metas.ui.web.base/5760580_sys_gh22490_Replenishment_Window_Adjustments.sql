-- Run mode: SWING_CLIENT

-- Element: Level_Max
-- 2025-07-10T10:04:09.499Z
UPDATE AD_Element_Trl SET Name='Maximalmenge', PrintName='Maximalmenge',Updated=TO_TIMESTAMP('2025-07-10 10:04:09.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='it_CH'
;

-- 2025-07-10T10:04:09.503Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:04:10.057Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'it_CH')
;

-- Element: Level_Max
-- 2025-07-10T10:04:12.719Z
UPDATE AD_Element_Trl SET Name='Maximalmenge', PrintName='Maximalmenge',Updated=TO_TIMESTAMP('2025-07-10 10:04:12.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='en_GB'
;

-- 2025-07-10T10:04:12.721Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:04:13.250Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'en_GB')
;

-- Element: Level_Max
-- 2025-07-10T10:04:15.540Z
UPDATE AD_Element_Trl SET Name='Maximalmenge', PrintName='Maximalmenge',Updated=TO_TIMESTAMP('2025-07-10 10:04:15.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='de_CH'
;

-- 2025-07-10T10:04:15.541Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:04:16.234Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'de_CH')
;

-- Element: Level_Max
-- 2025-07-10T10:04:19.837Z
UPDATE AD_Element_Trl SET Name='Maximalmenge', PrintName='Maximalmenge',Updated=TO_TIMESTAMP('2025-07-10 10:04:19.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=434 AND AD_Language='de_DE'
;

-- 2025-07-10T10:04:19.838Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:04:21.969Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(434,'de_DE')
;

-- 2025-07-10T10:04:21.970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(434,'de_DE')
;

-- 2025-07-10T10:06:10.009Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583797,0,TO_TIMESTAMP('2025-07-10 10:06:09.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Sollmenge','Sollmenge',TO_TIMESTAMP('2025-07-10 10:06:09.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-10T10:06:10.013Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583797 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-07-10T10:06:15.967Z
UPDATE AD_Element_Trl SET Name='Target quantity', PrintName='Target quantity',Updated=TO_TIMESTAMP('2025-07-10 10:06:15.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583797 AND AD_Language='en_US'
;

-- 2025-07-10T10:06:15.968Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:06:16.733Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583797,'en_US')
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Sollmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-07-10T10:06:32.373Z
UPDATE AD_Field SET AD_Name_ID=583797, Description=NULL, Help=NULL, Name='Sollmenge',Updated=TO_TIMESTAMP('2025-07-10 10:06:32.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740357
;

-- 2025-07-10T10:06:32.374Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Sollmenge' WHERE AD_Field_ID=740357 AND AD_Language='de_DE'
;

-- 2025-07-10T10:06:32.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583797)
;

-- 2025-07-10T10:06:32.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740357
;

-- 2025-07-10T10:06:32.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740357)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-07-10T10:06:52.467Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-07-10 10:06:52.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-07-10T10:07:03.172Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-07-10 10:07:03.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-07-10T10:07:03.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-07-10 10:07:03.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-07-10T10:07:03.185Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-07-10 10:07:03.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-07-10T10:11:29.452Z
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-07-10 10:11:29.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542932
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-07-10T10:11:43.099Z
UPDATE AD_Process_Para SET DefaultValue='',Updated=TO_TIMESTAMP('2025-07-10 10:11:43.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542932
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-07-10T10:15:50.625Z
UPDATE AD_Process_Para SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2025-07-10 10:15:50.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542932
;

-- Element: Level_Min
-- 2025-07-10T10:49:42.445Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-07-10 10:49:42.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=435 AND AD_Language='fr_CH'
;

-- 2025-07-10T10:49:42.447Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:49:43.307Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(435,'fr_CH')
;

-- Element: Level_Min
-- 2025-07-10T10:49:47.843Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-07-10 10:49:47.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=435 AND AD_Language='it_CH'
;

-- 2025-07-10T10:49:47.844Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:49:48.317Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(435,'it_CH')
;

-- Element: Level_Min
-- 2025-07-10T10:49:53.761Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-07-10 10:49:53.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=435 AND AD_Language='de_CH'
;

-- 2025-07-10T10:49:53.763Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:49:54.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(435,'de_CH')
;

-- Element: Level_Min
-- 2025-07-10T10:50:04.614Z
UPDATE AD_Element_Trl SET Name='Target quantity', PrintName='Target quantity',Updated=TO_TIMESTAMP('2025-07-10 10:50:04.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=435 AND AD_Language='en_US'
;

-- 2025-07-10T10:50:04.614Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:50:05.098Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(435,'en_US')
;

-- Element: Level_Min
-- 2025-07-10T10:50:09.438Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-07-10 10:50:09.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=435 AND AD_Language='de_DE'
;

-- 2025-07-10T10:50:09.438Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:50:11.197Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(435,'de_DE')
;

-- 2025-07-10T10:50:11.197Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(435,'de_DE')
;

-- Element: Level_Min
-- 2025-07-10T10:50:21.178Z
UPDATE AD_Element_Trl SET Name='Sollmenge', PrintName='Sollmenge',Updated=TO_TIMESTAMP('2025-07-10 10:50:21.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=435 AND AD_Language='en_GB'
;

-- 2025-07-10T10:50:21.178Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T10:50:21.771Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(435,'en_GB')
;
