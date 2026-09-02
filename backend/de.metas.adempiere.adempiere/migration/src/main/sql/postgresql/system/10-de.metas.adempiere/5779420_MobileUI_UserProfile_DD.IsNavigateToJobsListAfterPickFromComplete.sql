-- Run mode: SWING_CLIENT

-- 2025-12-03T11:38:43.430Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584328,0,'IsNavigateToJobsListAfterPickFromComplete',TO_TIMESTAMP('2025-12-03 11:38:42.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Navigiert zur Auftragsliste nach vollständiger Kommissionierung.','D','Wenn aktiviert (J), wird der Benutzer nach Kommissionierung des letzten Artikels zur Auftragsliste navigiert. Wenn deaktiviert (N), erfolgt die Navigation direkt zum nächsten verfügbaren Auftrag.','Y','Gehe zu Liste nach Pick-Ende','Gehe zu Liste nach Pick-Ende',TO_TIMESTAMP('2025-12-03 11:38:42.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-03T11:38:43.467Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584328 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:39:00.758Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-03 11:39:00.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584328 AND AD_Language='de_CH'
;

-- 2025-12-03T11:39:00.975Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584328,'de_CH')
;

-- Element: IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:39:03.487Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-03 11:39:03.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584328 AND AD_Language='de_DE'
;

-- 2025-12-03T11:39:03.491Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584328,'de_DE')
;

-- 2025-12-03T11:39:03.493Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584328,'de_DE')
;

-- Element: IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:40:28.004Z
UPDATE AD_Element_Trl SET Description='Goes to the Jobs List after picking an entire order.', Help='If enabled (Y), the user is navigated to the Jobs List screen immediately after picking the last item for an entire order.', IsTranslated='Y', Name='Navigate to List on Pick Complete', PrintName='Navigate to List on Pick Complete',Updated=TO_TIMESTAMP('2025-12-03 11:40:28.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584328 AND AD_Language='en_US'
;

-- 2025-12-03T11:40:28.006Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-03T11:40:28.390Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584328,'en_US')
;

-- Element: IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:40:37.020Z
UPDATE AD_Element_Trl SET Help='Wenn aktiviert (J), wird der Benutzer nach Kommissionierung des letzten Artikels zur Auftragsliste navigiert.',Updated=TO_TIMESTAMP('2025-12-03 11:40:37.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584328 AND AD_Language='de_DE'
;

-- 2025-12-03T11:40:37.022Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-03T11:40:38.275Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584328,'de_DE')
;

-- 2025-12-03T11:40:38.276Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584328,'de_DE')
;

-- Element: IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:40:46.783Z
UPDATE AD_Element_Trl SET Help='Wenn aktiviert (J), wird der Benutzer nach Kommissionierung des letzten Artikels zur Auftragsliste navigiert.',Updated=TO_TIMESTAMP('2025-12-03 11:40:46.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584328 AND AD_Language='de_CH'
;

-- 2025-12-03T11:40:46.785Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-03T11:40:47.099Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584328,'de_CH')
;

-- Element: IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:40:52.352Z
UPDATE AD_Element_Trl SET Help='Wenn aktiviert (J), wird der Benutzer nach Kommissionierung des letzten Artikels zur Auftragsliste navigiert.',Updated=TO_TIMESTAMP('2025-12-03 11:40:52.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584328 AND AD_Language='fr_CH'
;

-- 2025-12-03T11:40:52.354Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-03T11:40:52.664Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584328,'fr_CH')
;

-- Column: MobileUI_UserProfile_DD.IsNavigateToJobsListAfterPickFromComplete
-- 2025-12-03T11:41:02.908Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591661,584328,0,20,542462,'XX','IsNavigateToJobsListAfterPickFromComplete',TO_TIMESTAMP('2025-12-03 11:41:02.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Navigiert zur Auftragsliste nach vollständiger Kommissionierung.','D',0,1,'Wenn aktiviert (J), wird der Benutzer nach Kommissionierung des letzten Artikels zur Auftragsliste navigiert.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gehe zu Liste nach Pick-Ende',0,0,TO_TIMESTAMP('2025-12-03 11:41:02.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-03T11:41:02.912Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591661 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-03T11:41:02.917Z
/* DDL */  select update_Column_Translation_From_AD_Element(584328)
;

-- 2025-12-03T11:41:09.273Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_DD','ALTER TABLE public.MobileUI_UserProfile_DD ADD COLUMN IsNavigateToJobsListAfterPickFromComplete CHAR(1) DEFAULT ''N'' CHECK (IsNavigateToJobsListAfterPickFromComplete IN (''Y'',''N'')) NOT NULL')
;

-- UI Column: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20
-- UI Element Group: job
-- 2025-12-03T11:41:59.186Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-12-03 11:41:59.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553846
;

-- UI Column: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20
-- UI Element Group: pick from
-- 2025-12-03T11:42:07.743Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547725,553922,TO_TIMESTAMP('2025-12-03 11:42:07.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','pick from',20,TO_TIMESTAMP('2025-12-03 11:42:07.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Distribution Profile(541842,D) -> Mobile Distribution Profile(547735,D) -> main -> 20 -> pick from.Mobile Distribution Profile
-- Column: MobileUI_UserProfile_DD.MobileUI_UserProfile_DD_ID
-- 2025-12-03T11:42:21.037Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734674,0,547735,553922,639734,'F',TO_TIMESTAMP('2025-12-03 11:42:20.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mobile Distribution Profile',10,0,0,TO_TIMESTAMP('2025-12-03 11:42:20.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

