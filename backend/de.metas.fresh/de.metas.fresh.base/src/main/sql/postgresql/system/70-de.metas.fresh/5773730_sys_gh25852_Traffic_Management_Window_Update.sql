-- Run mode: SWING_CLIENT

-- 2025-10-20T14:26:02.198Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584131,0,TO_TIMESTAMP('2025-10-20 14:26:01.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Verkehrsmanagement','Verkehrsmanagement',TO_TIMESTAMP('2025-10-20 14:26:01.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-20T14:26:02.270Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584131 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-20T14:26:45.552Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Traffic Management', PrintName='Traffic Management',Updated=TO_TIMESTAMP('2025-10-20 14:26:45.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584131 AND AD_Language='en_US'
;

-- 2025-10-20T14:26:45.625Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:26:53.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584131,'en_US')
;

-- Window: Verkehrsmanagement, InternalName=pickingJobSchedule
-- 2025-10-20T14:28:01.953Z
UPDATE AD_Window SET AD_Element_ID=584131, Description=NULL, Help=NULL, Name='Verkehrsmanagement',Updated=TO_TIMESTAMP('2025-10-20 14:28:01.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541929
;

-- 2025-10-20T14:28:02.030Z
UPDATE AD_Window_Trl trl SET Name='Verkehrsmanagement' WHERE AD_Window_ID=541929 AND AD_Language='de_DE'
;

-- Name: Verkehrsmanagement
-- Action Type: W
-- Window: Verkehrsmanagement(541929,de.metas.handlingunits)
-- 2025-10-20T14:28:02.318Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Verkehrsmanagement',Updated=TO_TIMESTAMP('2025-10-20 14:28:02.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542241
;

-- 2025-10-20T14:28:02.390Z
UPDATE AD_Menu_Trl trl SET Name='Verkehrsmanagement' WHERE AD_Menu_ID=542241 AND AD_Language='de_DE'
;

-- 2025-10-20T14:28:02.693Z
/* DDL */  select update_window_translation_from_ad_element(584131)
;

-- 2025-10-20T14:28:02.768Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541929
;

-- 2025-10-20T14:28:02.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541929)
;

-- Name: Verkehrsmanagement
-- Action Type: W
-- Window: Verkehrsmanagement(541929,de.metas.handlingunits)
-- 2025-10-20T14:28:48.532Z
UPDATE AD_Menu SET AD_Element_ID=584131, Description=NULL, Name='Verkehrsmanagement', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2025-10-20 14:28:48.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542241
;

-- 2025-10-20T14:28:48.606Z
/* DDL */  select update_menu_translation_from_ad_element(584131)
;

-- Element: null
-- 2025-10-20T14:30:27.149Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Traffic Management', PrintName='Traffic Management',Updated=TO_TIMESTAMP('2025-10-20 14:30:27.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584131 AND AD_Language='de_DE'
;

-- 2025-10-20T14:30:27.219Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:30:36.250Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584131,'de_DE')
;

-- 2025-10-20T14:30:36.320Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584131,'de_DE')
;

-- Element: null
-- 2025-10-20T14:30:48.720Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Traffic Management', PrintName='Traffic Management',Updated=TO_TIMESTAMP('2025-10-20 14:30:48.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584131 AND AD_Language='de_CH'
;

-- 2025-10-20T14:30:48.795Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:30:54.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584131,'de_CH')
;

-- Element: null
-- 2025-10-20T14:31:03.172Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Traffic Management', PrintName='Traffic Management',Updated=TO_TIMESTAMP('2025-10-20 14:31:03.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584131 AND AD_Language='fr_CH'
;

-- 2025-10-20T14:31:03.249Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:31:10.185Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584131,'fr_CH')
;

-- 2025-10-20T14:48:20.586Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584132,0,TO_TIMESTAMP('2025-10-20 14:48:20.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Packtisch','Packtisch',TO_TIMESTAMP('2025-10-20 14:48:20.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-20T14:48:20.659Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584132 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-20T14:48:42.774Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing table', PrintName='Packing table',Updated=TO_TIMESTAMP('2025-10-20 14:48:42.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584132 AND AD_Language='en_US'
;

-- 2025-10-20T14:48:42.844Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:48:49.371Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584132,'en_US')
;

-- 2025-10-20T14:49:30.060Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584133,0,TO_TIMESTAMP('2025-10-20 14:49:29.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zugewiesen','Zugewiesen',TO_TIMESTAMP('2025-10-20 14:49:29.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-20T14:49:30.131Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584133 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-20T14:49:53.174Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Assigned', PrintName='Assigned',Updated=TO_TIMESTAMP('2025-10-20 14:49:53.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584133 AND AD_Language='en_US'
;

-- 2025-10-20T14:49:53.247Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:50:01.012Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584133,'en_US')
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Zugewiesen
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2025-10-20T14:51:02.750Z
UPDATE AD_Field SET AD_Name_ID=584133, Description=NULL, Help=NULL, Name='Zugewiesen',Updated=TO_TIMESTAMP('2025-10-20 14:51:02.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752557
;

-- 2025-10-20T14:51:02.821Z
UPDATE AD_Field_Trl trl SET Name='Zugewiesen' WHERE AD_Field_ID=752557 AND AD_Language='de_DE'
;

-- 2025-10-20T14:51:02.895Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584133)
;

-- 2025-10-20T14:51:02.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752557
;

-- 2025-10-20T14:51:03.038Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752557)
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Packtisch
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2025-10-20T14:51:51.765Z
UPDATE AD_Field SET AD_Name_ID=584132, Description=NULL, Help=NULL, Name='Packtisch',Updated=TO_TIMESTAMP('2025-10-20 14:51:51.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752558
;

-- 2025-10-20T14:51:51.837Z
UPDATE AD_Field_Trl trl SET Name='Packtisch' WHERE AD_Field_ID=752558 AND AD_Language='de_DE'
;

-- 2025-10-20T14:51:51.910Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584132)
;

-- 2025-10-20T14:51:51.983Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752558
;

-- 2025-10-20T14:51:52.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752558)
;

-- 2025-10-20T14:58:24.669Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584134,0,TO_TIMESTAMP('2025-10-20 14:58:24.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Versanddienstleister','Versanddienstleister',TO_TIMESTAMP('2025-10-20 14:58:24.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-20T14:58:24.743Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584134 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-20T14:59:02.830Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery service provider', PrintName='Delivery service provider',Updated=TO_TIMESTAMP('2025-10-20 14:59:02.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584134 AND AD_Language='en_US'
;

-- 2025-10-20T14:59:02.900Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-20T14:59:10.612Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584134,'en_US')
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Versanddienstleister
-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2025-10-20T14:59:44.551Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591391,755053,584134,0,548377,0,TO_TIMESTAMP('2025-10-20 14:59:43.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Versanddienstleister',0,0,10,0,1,1,TO_TIMESTAMP('2025-10-20 14:59:43.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-20T14:59:44.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-20T14:59:44.698Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584134)
;

-- 2025-10-20T14:59:44.772Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755053
;

-- 2025-10-20T14:59:44.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755053)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Versanddienstleister
-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2025-10-20T15:00:49.807Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755053,0,548377,553423,637919,'F',TO_TIMESTAMP('2025-10-20 15:00:49.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Versanddienstleister',70,0,0,TO_TIMESTAMP('2025-10-20 15:00:49.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary
-- UI Column: 20
-- 2025-10-20T15:01:52.264Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=548403
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Versanddienstleister
-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2025-10-20T15:02:13.598Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-20 15:02:13.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637919
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Bereitstellungsdatum
-- Column: M_Picking_Job_Schedule_view.PreparationDate
-- 2025-10-20T15:02:14.023Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-20 15:02:14.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636479
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Maßeinheit
-- Column: M_Picking_Job_Schedule_view.C_UOM_ID
-- 2025-10-20T15:02:14.449Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-20 15:02:14.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636477
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2025-10-20T15:02:14.871Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-20 15:02:14.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2025-10-20T15:02:15.296Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-10-20 15:02:15.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636482
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2025-10-20T15:02:15.718Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-10-20 15:02:15.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636481
;

-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2025-10-20T15:03:23.401Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-20 15:03:23.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591391
;

-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2025-10-20T15:07:04.537Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540272, AD_Val_Rule_ID=540751, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-10-20 15:07:04.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591391
;

-- Name: Carrier_Product
-- 2025-10-22T07:09:05.533Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542003,TO_TIMESTAMP('2025-10-22 07:09:05.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Carrier_Product',TO_TIMESTAMP('2025-10-22 07:09:05.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-10-22T07:09:05.540Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542003 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Carrier_Product
-- Table: Carrier_Product
-- Key: Carrier_Product.Carrier_Product_ID
-- 2025-10-22T07:09:44.812Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,591348,0,542003,542545,TO_TIMESTAMP('2025-10-22 07:09:44.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-10-22 07:09:44.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_Picking_Job_Schedule_view.Carrier_Product_ID
-- 2025-10-22T07:11:17.679Z
UPDATE AD_Column SET AD_Reference_Value_ID=542003,Updated=TO_TIMESTAMP('2025-10-22 07:11:17.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591391
;
