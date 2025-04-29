-- Run mode: SWING_CLIENT

-- 2025-04-09T09:25:04.206Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583577,0,'IsShowLastPickedBestBeforeDateForLines',TO_TIMESTAMP('2025-04-09 09:25:03.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Show Best Before of Last Picked Item ','Show Best Before of Last Picked Item ',TO_TIMESTAMP('2025-04-09 09:25:03.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-09T09:25:04.222Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583577 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-04-09T09:25:19.615Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-09 09:25:19.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577
;

-- 2025-04-09T09:25:19.654Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'de_DE')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:25:35.780Z
UPDATE AD_Element_Trl SET Name='Anzeige des Mindesthaltbarkeitsdatums des zuletzt kommissionierten Artikels', PrintName='Anzeige des Mindesthaltbarkeitsdatums des zuletzt kommissionierten Artikels',Updated=TO_TIMESTAMP('2025-04-09 09:25:35.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='de_CH'
;

-- 2025-04-09T09:25:35.780Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-09T09:25:36.213Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'de_CH')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:26:20.408Z
UPDATE AD_Element_Trl SET Name='Anzeige des MHD des zuletzt kommissionierten Artikels', PrintName='Anzeige des MHD des zuletzt kommissionierten Artikels',Updated=TO_TIMESTAMP('2025-04-09 09:26:20.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='de_DE'
;

-- 2025-04-09T09:26:20.421Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-09T09:26:22.007Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583577,'de_DE')
;

-- 2025-04-09T09:26:22.008Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'de_DE')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:26:26.551Z
UPDATE AD_Element_Trl SET Name='Anzeige des MHD des zuletzt kommissionierten Artikels', PrintName='Anzeige des MHD des zuletzt kommissionierten Artikels',Updated=TO_TIMESTAMP('2025-04-09 09:26:26.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='de_CH'
;

-- 2025-04-09T09:26:26.552Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-09T09:26:27.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'de_CH')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:26:33.288Z
UPDATE AD_Element_Trl SET Name='Anzeige des MHD des zuletzt kommissionierten Artikels', PrintName='Anzeige des MHD des zuletzt kommissionierten Artikels',Updated=TO_TIMESTAMP('2025-04-09 09:26:33.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='fr_CH'
;

-- 2025-04-09T09:26:33.289Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-09T09:26:33.719Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'fr_CH')
;

-- Column: MobileUI_UserProfile_Picking.IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:27:03.511Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589905,583577,0,20,542373,'XX','IsShowLastPickedBestBeforeDateForLines',TO_TIMESTAMP('2025-04-09 09:27:03.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Anzeige des MHD des zuletzt kommissionierten Artikels',0,0,TO_TIMESTAMP('2025-04-09 09:27:03.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-09T09:27:03.527Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589905 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-09T09:27:03.535Z
/* DDL */  select update_Column_Translation_From_AD_Element(583577)
;

-- 2025-04-09T09:27:06.195Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsShowLastPickedBestBeforeDateForLines CHAR(1) DEFAULT ''N'' CHECK (IsShowLastPickedBestBeforeDateForLines IN (''Y'',''N'')) NOT NULL')
;

-- Column: MobileUI_UserProfile_Picking_Job.IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:27:39.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589906,583577,0,20,542464,'XX','IsShowLastPickedBestBeforeDateForLines',TO_TIMESTAMP('2025-04-09 09:27:38.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Anzeige des MHD des zuletzt kommissionierten Artikels',0,0,TO_TIMESTAMP('2025-04-09 09:27:38.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-09T09:27:39.085Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589906 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-09T09:27:39.282Z
/* DDL */  select update_Column_Translation_From_AD_Element(583577)
;

-- 2025-04-09T09:27:42.333Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_Job','ALTER TABLE public.MobileUI_UserProfile_Picking_Job ADD COLUMN IsShowLastPickedBestBeforeDateForLines CHAR(1) DEFAULT ''N'' CHECK (IsShowLastPickedBestBeforeDateForLines IN (''Y'',''N'')) NOT NULL')
;

-- 2025-04-09T09:28:04.942Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_job','IsShowLastPickedBestBeforeDateForLines','CHAR(1)',null,'N')
;

-- 2025-04-09T09:28:04.964Z
UPDATE MobileUI_UserProfile_Picking_Job SET IsShowLastPickedBestBeforeDateForLines='N' WHERE IsShowLastPickedBestBeforeDateForLines IS NULL
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Anzeige des MHD des zuletzt kommissionierten Artikels
-- Column: MobileUI_UserProfile_Picking.IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:28:42.914Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589905,741964,0,547258,TO_TIMESTAMP('2025-04-09 09:28:42.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Anzeige des MHD des zuletzt kommissionierten Artikels',TO_TIMESTAMP('2025-04-09 09:28:42.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-09T09:28:42.928Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-09T09:28:42.942Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583577)
;

-- 2025-04-09T09:28:42.971Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741964
;

-- 2025-04-09T09:28:43.021Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741964)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Anzeige des MHD des zuletzt kommissionierten Artikels
-- Column: MobileUI_UserProfile_Picking.IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:29:01.728Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741964,0,547258,631341,551252,'F',TO_TIMESTAMP('2025-04-09 09:29:01.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Anzeige des MHD des zuletzt kommissionierten Artikels',120,0,0,TO_TIMESTAMP('2025-04-09 09:29:01.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> Anzeige des MHD des zuletzt kommissionierten Artikels
-- Column: MobileUI_UserProfile_Picking_Job.IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:29:11.759Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589906,741965,0,547823,TO_TIMESTAMP('2025-04-09 09:29:11.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Anzeige des MHD des zuletzt kommissionierten Artikels',TO_TIMESTAMP('2025-04-09 09:29:11.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-09T09:29:11.762Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-09T09:29:11.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583577)
;

-- 2025-04-09T09:29:11.793Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741965
;

-- 2025-04-09T09:29:11.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741965)
;

-- UI Element: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> main -> 20 -> flags.Anzeige des MHD des zuletzt kommissionierten Artikels
-- Column: MobileUI_UserProfile_Picking_Job.IsShowLastPickedBestBeforeDateForLines
-- 2025-04-09T09:29:27.558Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741965,0,547823,631342,552482,'F',TO_TIMESTAMP('2025-04-09 09:29:27.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Anzeige des MHD des zuletzt kommissionierten Artikels',110,0,0,TO_TIMESTAMP('2025-04-09 09:29:27.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Value: de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE
-- 2025-04-04T01:57:26.496Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545521,0,TO_TIMESTAMP('2025-04-04 01:57:25.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Last Best Before Date','I',TO_TIMESTAMP('2025-04-04 01:57:25.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE')
;

-- 2025-04-04T01:57:26.505Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545521 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE
-- 2025-04-04T01:59:04.461Z
UPDATE AD_Message_Trl SET MsgText='Letzte  Mindesthaltbarkeit',Updated=TO_TIMESTAMP('2025-04-04 01:59:04.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545521
;

-- 2025-04-04T01:59:04.462Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE
-- 2025-04-04T01:59:07.782Z
UPDATE AD_Message_Trl SET MsgText='Letzte  Mindesthaltbarkeit',Updated=TO_TIMESTAMP('2025-04-04 01:59:07.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545521
;

-- 2025-04-04T01:59:07.783Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE
-- 2025-04-04T01:59:12.230Z
UPDATE AD_Message_Trl SET MsgText='Letzte  Mindesthaltbarkeit',Updated=TO_TIMESTAMP('2025-04-04 01:59:12.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545521
;

-- 2025-04-04T01:59:12.231Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;



-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-11T10:04:37.340Z
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, wird das Mindesthaltbarkeitsdatum (MHD) des zuletzt kommissioniert Artikels in der jeweiligen Zeile angezeigt.', Help='Wenn aktiviert, wird das Mindesthaltbarkeitsdatum (MHD) des zuletzt kommissioniert Artikels in der jeweiligen Zeile angezeigt.',Updated=TO_TIMESTAMP('2025-04-11 10:04:37.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='de_CH'
;

-- 2025-04-11T10:04:37.343Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-11T10:04:37.846Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'de_CH')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-11T10:04:42.846Z
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, wird das Mindesthaltbarkeitsdatum (MHD) des zuletzt kommissioniert Artikels in der jeweiligen Zeile angezeigt.', Help='Wenn aktiviert, wird das Mindesthaltbarkeitsdatum (MHD) des zuletzt kommissioniert Artikels in der jeweiligen Zeile angezeigt.',Updated=TO_TIMESTAMP('2025-04-11 10:04:42.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='de_DE'
;

-- 2025-04-11T10:04:42.848Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-11T10:04:44.618Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583577,'de_DE')
;

-- 2025-04-11T10:04:44.622Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'de_DE')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-11T10:04:54.287Z
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, wird das Mindesthaltbarkeitsdatum (MHD) des zuletzt kommissioniert Artikels in der jeweiligen Zeile angezeigt.', Help='Wenn aktiviert, wird das Mindesthaltbarkeitsdatum (MHD) des zuletzt kommissioniert Artikels in der jeweiligen Zeile angezeigt.',Updated=TO_TIMESTAMP('2025-04-11 10:04:54.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='fr_CH'
;

-- 2025-04-11T10:04:54.288Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-11T10:04:54.797Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'fr_CH')
;

-- Element: IsShowLastPickedBestBeforeDateForLines
-- 2025-04-11T10:05:19.781Z
UPDATE AD_Element_Trl SET Description='When enabled, the ''Best Before'' date of the last picked item will be shown on each line.', Help='When enabled, the ''Best Before'' date of the last picked item will be shown on each line.',Updated=TO_TIMESTAMP('2025-04-11 10:05:19.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583577 AND AD_Language='en_US'
;

-- 2025-04-11T10:05:19.785Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-11T10:05:20.283Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583577,'en_US')
;
