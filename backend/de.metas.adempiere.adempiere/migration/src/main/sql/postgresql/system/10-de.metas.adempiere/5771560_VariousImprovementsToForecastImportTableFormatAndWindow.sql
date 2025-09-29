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



-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------






-- Column: M_ForecastLine.C_BPartner_ID
-- 2025-09-29T06:44:24.494Z
UPDATE AD_Column SET DefaultValue='@C_BPartner_ID/-1@',Updated=TO_TIMESTAMP('2025-09-29 06:44:24.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550452
;

-- 2025-09-29T09:14:19.921Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=542120
;

-- 2025-09-29T09:14:23.496Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=542122
;

-- 2025-09-29T09:14:28.764Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=542127
;

-- 2025-09-29T09:14:32.004Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=542128
;

-- 2025-09-29T09:14:35.126Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=542129
;

-- 2025-09-29T09:14:48.381Z
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2025-09-29 09:14:48.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542121
;

-- 2025-09-29T09:14:52.163Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2025-09-29 09:14:52.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542123
;

-- 2025-09-29T09:14:55.773Z
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2025-09-29 09:14:55.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542124
;

-- 2025-09-29T09:14:59.106Z
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2025-09-29 09:14:59.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542125
;

-- 2025-09-29T09:15:04.194Z
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2025-09-29 09:15:04.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542130
;

-- 2025-09-29T09:15:22.124Z
UPDATE AD_ImpFormat_Row SET SeqNo=2,Updated=TO_TIMESTAMP('2025-09-29 09:15:22.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542121
;

-- 2025-09-29T09:15:29.386Z
UPDATE AD_ImpFormat_Row SET SeqNo=20,Updated=TO_TIMESTAMP('2025-09-29 09:15:29.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542121
;

-- 2025-09-29T09:15:31.749Z
UPDATE AD_ImpFormat_Row SET SeqNo=30,Updated=TO_TIMESTAMP('2025-09-29 09:15:31.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542123
;

-- 2025-09-29T09:15:34.141Z
UPDATE AD_ImpFormat_Row SET SeqNo=40,Updated=TO_TIMESTAMP('2025-09-29 09:15:34.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542124
;

-- 2025-09-29T09:15:39.660Z
UPDATE AD_ImpFormat_Row SET SeqNo=50,Updated=TO_TIMESTAMP('2025-09-29 09:15:39.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542125
;

-- 2025-09-29T09:15:42.374Z
UPDATE AD_ImpFormat_Row SET SeqNo=60,Updated=TO_TIMESTAMP('2025-09-29 09:15:42.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542130
;

-- Column: I_Forecast.BPValue
-- 2025-09-29T09:34:09.713Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-09-29 09:34:09.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590863
;

-- 2025-09-29T09:34:11.552Z
INSERT INTO t_alter_column values('i_forecast','BPValue','VARCHAR(40)',null,null)
;

-- 2025-09-29T09:34:11.572Z
INSERT INTO t_alter_column values('i_forecast','BPValue',null,'NULL',null)
;

-- Column: I_Forecast.CampaignValue
-- 2025-09-29T11:02:54.724Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2025-09-29 11:02:54.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590881
;

-- 2025-09-29T11:02:58.045Z
INSERT INTO t_alter_column values('i_forecast','CampaignValue','VARCHAR(40)',null,null)
;


ALTER TABLE i_forecast
    ALTER COLUMN campaignvalue DROP DEFAULT;

-- Table: I_Forecast
-- 2025-09-29T11:27:01.650Z
UPDATE AD_Table SET AD_Window_ID=541940,Updated=TO_TIMESTAMP('2025-09-29 11:27:01.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542523
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 20 -> main.Prognose
-- Column: I_Forecast.M_Forecast_ID
-- 2025-09-29T11:29:17.262Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637022
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Name
-- Column: I_Forecast.Name
-- 2025-09-29T11:29:17.272Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636990
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Zugesagter Termin
-- Column: I_Forecast.DatePromised
-- 2025-09-29T11:29:17.284Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636997
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Produktschlüssel
-- Column: I_Forecast.ProductValue
-- 2025-09-29T11:29:17.294Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636993
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Produkt
-- Column: I_Forecast.M_Product_ID
-- 2025-09-29T11:29:17.302Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636994
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Menge
-- Column: I_Forecast.Qty
-- 2025-09-29T11:29:17.311Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636998
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Mengeneinheit
-- Column: I_Forecast.UOM
-- 2025-09-29T11:29:17.320Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637007
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Maßeinheit
-- Column: I_Forecast.C_UOM_ID
-- 2025-09-29T11:29:17.328Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637008
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 20 -> main.Verarbeitet
-- Column: I_Forecast.Processed
-- 2025-09-29T11:29:17.335Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637014
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 20 -> main.Import-Fehlermeldung
-- Column: I_Forecast.I_ErrorMsg
-- 2025-09-29T11:29:17.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637016
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Auszeichnungspreis
-- Column: I_Forecast.PriceList
-- 2025-09-29T11:29:17.352Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636995
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Preisliste
-- Column: I_Forecast.M_PriceList_ID
-- 2025-09-29T11:29:17.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636996
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Nr.
-- Column: I_Forecast.BPValue
-- 2025-09-29T11:29:17.366Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636991
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Geschäftspartner
-- Column: I_Forecast.C_BPartner_ID
-- 2025-09-29T11:29:17.374Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636992
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.ActivityValue
-- Column: I_Forecast.ActivityValue
-- 2025-09-29T11:29:17.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637001
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Kostenstelle
-- Column: I_Forecast.C_Activity_ID
-- 2025-09-29T11:29:17.390Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637002
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Campaign value
-- Column: I_Forecast.CampaignValue
-- 2025-09-29T11:29:17.397Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637003
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Werbemassnahme
-- Column: I_Forecast.C_Campaign_ID
-- 2025-09-29T11:29:17.404Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637004
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Projekt-Schlüssel
-- Column: I_Forecast.ProjectValue
-- 2025-09-29T11:29:17.412Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637005
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Projekt
-- Column: I_Forecast.C_Project_ID
-- 2025-09-29T11:29:17.419Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637006
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 10 -> Main.Externe ID
-- Column: I_Forecast.ExternalId
-- 2025-09-29T11:29:17.430Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637009
;

-- UI Element: Prognose importieren(541940,D) -> Prognose importieren(548411,D) -> Main -> 20 -> main.Sektion
-- Column: I_Forecast.AD_Org_ID
-- 2025-09-29T11:29:17.437Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-09-29 11:29:17.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637020
;

-- Column: I_Forecast.Processed
-- 2025-09-29T13:19:57.946Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-29 13:19:57.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590858
;

