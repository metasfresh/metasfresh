-- Run mode: SWING_CLIENT

-- 2025-11-20T13:09:53.647Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584245,0,'IsCompleteJobAutomatically',TO_TIMESTAMP('2025-11-20 13:09:53.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.','D','Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','Job automatisch abschließen','Job automatisch abschließen',TO_TIMESTAMP('2025-11-20 13:09:53.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T13:09:53.680Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584245 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCompleteJobAutomatically
-- 2025-11-20T13:09:58.726Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 13:09:58.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584245 AND AD_Language='de_DE'
;

-- 2025-11-20T13:09:58.900Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584245,'de_DE')
;

-- 2025-11-20T13:09:58.904Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584245,'de_DE')
;

-- Element: IsCompleteJobAutomatically
-- 2025-11-20T13:10:08.997Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-20 13:10:08.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584245 AND AD_Language='de_CH'
;

-- 2025-11-20T13:10:09.001Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584245,'de_CH')
;

-- Element: IsCompleteJobAutomatically
-- 2025-11-20T13:12:33.395Z
UPDATE AD_Element_Trl SET Description='Automatically completes the job when all lines are fulfilled.', Help='When enabled, the system will complete the job as soon as every line has been processed. The user no longer needs to press the Complete button manually.', IsTranslated='Y', Name='Complete job automatically', PrintName='Complete job automatically',Updated=TO_TIMESTAMP('2025-11-20 13:12:33.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584245 AND AD_Language='en_US'
;

-- 2025-11-20T13:12:33.397Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-20T13:12:33.716Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584245,'en_US')
;

-- Column: MobileUI_UserProfile_Picking.IsCompleteJobAutomatically
-- 2025-11-20T13:13:08.922Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591597,584245,0,20,542373,'XX','IsCompleteJobAutomatically',TO_TIMESTAMP('2025-11-20 13:13:08.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.','D',0,1,'Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Job automatisch abschließen',0,0,TO_TIMESTAMP('2025-11-20 13:13:08.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-20T13:13:08.934Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591597 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-20T13:13:08.938Z
/* DDL */  select update_Column_Translation_From_AD_Element(584245)
;

-- 2025-11-20T13:13:10.067Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsCompleteJobAutomatically CHAR(1) DEFAULT ''N'' CHECK (IsCompleteJobAutomatically IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Job automatisch abschließen
-- Column: MobileUI_UserProfile_Picking.IsCompleteJobAutomatically
-- 2025-11-20T13:13:25.425Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591597,756216,0,547258,TO_TIMESTAMP('2025-11-20 13:13:25.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.',1,'D','Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','N','N','N','N','N','N','N','Job automatisch abschließen',TO_TIMESTAMP('2025-11-20 13:13:25.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T13:13:25.438Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756216 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-20T13:13:25.443Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584245)
;

-- 2025-11-20T13:13:25.468Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756216
;

-- 2025-11-20T13:13:25.472Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756216)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10
-- UI Element Group: job complete settings
-- 2025-11-20T13:17:53.109Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547136,553777,TO_TIMESTAMP('2025-11-20 13:17:52.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','job complete settings',20,TO_TIMESTAMP('2025-11-20 13:17:52.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10
-- UI Element Group: job lines
-- 2025-11-20T13:18:02.950Z
UPDATE AD_UI_ElementGroup SET Name='job lines', SeqNo=30,Updated=TO_TIMESTAMP('2025-11-20 13:18:02.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551255
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> job complete settings.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- 2025-11-20T13:18:18.692Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553777, SeqNo=10,Updated=TO_TIMESTAMP('2025-11-20 13:18:18.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621123
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> job complete settings.Job automatisch abschließen
-- Column: MobileUI_UserProfile_Picking.IsCompleteJobAutomatically
-- 2025-11-20T13:18:41.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756216,0,547258,553777,638762,'F',TO_TIMESTAMP('2025-11-20 13:18:40.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Schließt den Job automatisch ab, sobald alle Positionen erfüllt sind.','Wenn aktiviert, wird der Job automatisch abgeschlossen, sobald alle Positionen verarbeitet wurden. Der Benutzer muss den Abschließen-Button nicht mehr manuell drücken.','Y','N','Y','N','N','Job automatisch abschließen',20,0,0,TO_TIMESTAMP('2025-11-20 13:18:40.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> job complete settings.Job automatisch abschließen
-- Column: MobileUI_UserProfile_Picking.IsCompleteJobAutomatically
-- 2025-11-20T13:18:54.913Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2025-11-20 13:18:54.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638762
;

