
-- 2025-09-28T18:48:55.106Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584044,0,TO_TIMESTAMP('2025-09-28 18:48:54.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','SmallPackagingMaterial','Kleinverpackung Material',TO_TIMESTAMP('2025-09-28 18:48:54.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T18:48:55.114Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584044 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-28T18:49:07.219Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 18:49:07.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584044 AND AD_Language='de_CH'
;

-- 2025-09-28T18:49:07.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584044,'de_CH')
;

-- Element: null
-- 2025-09-28T18:49:53.216Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 18:49:53.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584044 AND AD_Language='de_DE'
;

-- 2025-09-28T18:49:53.221Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584044,'de_DE')
;

-- 2025-09-28T18:49:53.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584044,'de_DE')
;

-- Element: null
-- 2025-09-28T18:50:04.057Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Small Packaging Material',Updated=TO_TIMESTAMP('2025-09-28 18:50:04.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584044 AND AD_Language='en_US'
;

-- 2025-09-28T18:50:04.059Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-28T18:50:04.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584044,'en_US')
;

-- 2025-09-28T18:50:33.887Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584045,0,TO_TIMESTAMP('2025-09-28 18:50:33.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','OuterPackagingMaterial','Überverpackung Material',TO_TIMESTAMP('2025-09-28 18:50:33.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-28T18:50:33.891Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584045 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-28T18:50:43.125Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Outer Packaging Material',Updated=TO_TIMESTAMP('2025-09-28 18:50:43.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584045 AND AD_Language='en_US'
;

-- 2025-09-28T18:50:43.127Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-28T18:50:43.430Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584045,'en_US')
;

-- Element: null
-- 2025-09-28T18:50:50.912Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 18:50:50.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584045 AND AD_Language='de_DE'
;

-- 2025-09-28T18:50:50.916Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584045,'de_DE')
;

-- 2025-09-28T18:50:50.918Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584045,'de_DE')
;

-- Element: null
-- 2025-09-28T18:50:54.308Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-28 18:50:54.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584045 AND AD_Language='de_CH'
;

-- 2025-09-28T18:50:54.312Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584045,'de_CH')
;

-- 2025-09-29T07:06:41.612Z
UPDATE AD_Element SET ColumnName='SmallPackagingMaterial',Updated=TO_TIMESTAMP('2025-09-29 07:06:41.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584044
;

-- 2025-09-29T07:06:41.618Z
UPDATE AD_Column SET ColumnName='SmallPackagingMaterial' WHERE AD_Element_ID=584044
;

-- 2025-09-29T07:06:41.620Z
UPDATE AD_Process_Para SET ColumnName='SmallPackagingMaterial' WHERE AD_Element_ID=584044
;

-- 2025-09-29T07:06:41.632Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584044,'de_DE')
;

-- 2025-09-29T07:06:55.399Z
UPDATE AD_Element SET ColumnName='OuterPackagingMaterial',Updated=TO_TIMESTAMP('2025-09-29 07:06:55.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584045
;

-- 2025-09-29T07:06:55.401Z
UPDATE AD_Column SET ColumnName='OuterPackagingMaterial' WHERE AD_Element_ID=584045
;

-- 2025-09-29T07:06:55.402Z
UPDATE AD_Process_Para SET ColumnName='OuterPackagingMaterial' WHERE AD_Element_ID=584045
;

-- 2025-09-29T07:06:55.407Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584045,'de_DE')
;

-- Element: OuterPackagingMaterial
-- 2025-09-29T07:08:32.040Z
UPDATE AD_Element_Trl SET Name='Überverpackung Material',Updated=TO_TIMESTAMP('2025-09-29 07:08:32.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584045 AND AD_Language='de_CH'
;

-- 2025-09-29T07:08:32.043Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T07:08:32.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584045,'de_CH')
;

-- Element: OuterPackagingMaterial
-- 2025-09-29T07:08:38.390Z
UPDATE AD_Element_Trl SET Name='Überverpackung Material',Updated=TO_TIMESTAMP('2025-09-29 07:08:38.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584045 AND AD_Language='de_DE'
;

-- 2025-09-29T07:08:38.394Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T07:08:40.057Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584045,'de_DE')
;

-- 2025-09-29T07:08:40.060Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584045,'de_DE')
;

-- Element: SmallPackagingMaterial
-- 2025-09-29T07:08:56.769Z
UPDATE AD_Element_Trl SET Name='Kleinverpackung Material',Updated=TO_TIMESTAMP('2025-09-29 07:08:56.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584044 AND AD_Language='de_DE'
;

-- 2025-09-29T07:08:56.771Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T07:08:57.320Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584044,'de_DE')
;

-- 2025-09-29T07:08:57.321Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584044,'de_DE')
;

-- Element: SmallPackagingMaterial
-- 2025-09-29T07:09:03.581Z
UPDATE AD_Element_Trl SET Name='Kleinverpackung Material',Updated=TO_TIMESTAMP('2025-09-29 07:09:03.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584044 AND AD_Language='de_CH'
;

-- 2025-09-29T07:09:03.583Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T07:09:03.902Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584044,'de_CH')
;

