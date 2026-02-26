-- Run mode: SWING_CLIENT

-- 2026-01-12T14:41:14.261Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584390,0,'PackingInstruction',TO_TIMESTAMP('2026-01-12 14:41:13.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','D','Y','Kommissionierhinweis','Kommissionierhinweis',TO_TIMESTAMP('2026-01-12 14:41:13.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-12T14:41:14.289Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584390 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-01-12T14:41:23.483Z
UPDATE AD_Element SET ColumnName='PickingInstruction',Updated=TO_TIMESTAMP('2026-01-12 14:41:23.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584390
;

-- 2026-01-12T14:41:23.486Z
UPDATE AD_Column SET ColumnName='PickingInstruction' WHERE AD_Element_ID=584390
;

-- 2026-01-12T14:41:23.487Z
UPDATE AD_Process_Para SET ColumnName='PickingInstruction' WHERE AD_Element_ID=584390
;

-- 2026-01-12T14:41:23.659Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584390,'de_DE')
;

-- Element: PickingInstruction
-- 2026-01-12T14:41:28.829Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-12 14:41:28.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584390 AND AD_Language='de_CH'
;

-- 2026-01-12T14:41:28.833Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584390,'de_CH')
;

-- Element: PickingInstruction
-- 2026-01-12T14:41:33.586Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-12 14:41:33.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584390 AND AD_Language='de_DE'
;

-- 2026-01-12T14:41:33.590Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584390,'de_DE')
;

-- 2026-01-12T14:41:33.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584390,'de_DE')
;

-- Element: PickingInstruction
-- 2026-01-12T14:44:41.470Z
UPDATE AD_Element_Trl SET Description='Instructions for warehouse operators and manufacturing personnel on how to identify, select, and handle this material component', IsTranslated='Y', Name='Picking Instruction', PrintName='Picking Instruction',Updated=TO_TIMESTAMP('2026-01-12 14:44:41.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584390 AND AD_Language='en_US'
;

-- 2026-01-12T14:44:41.471Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-12T14:44:41.957Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584390,'en_US')
;

-- Column: PP_Product_BOMLine.PickingInstruction
-- 2026-01-12T14:48:19.281Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591855,584390,0,10,53019,'XX','PickingInstruction',TO_TIMESTAMP('2026-01-12 14:48:19.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','EE01',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kommissionierhinweis',0,0,TO_TIMESTAMP('2026-01-12 14:48:19.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-12T14:48:19.295Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-12T14:48:19.302Z
/* DDL */  select update_Column_Translation_From_AD_Element(584390)
;

-- 2026-01-12T14:48:20.626Z
/* DDL */ SELECT public.db_alter_table('PP_Product_BOMLine','ALTER TABLE public.PP_Product_BOMLine ADD COLUMN PickingInstruction VARCHAR(2000)')
;

-- Field: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> Kommissionierhinweis
-- Column: PP_Product_BOMLine.PickingInstruction
-- 2026-01-12T14:49:05.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591855,761008,0,542034,TO_TIMESTAMP('2026-01-12 14:49:04.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).',2000,'EE01','Y','N','N','N','N','N','N','N','Kommissionierhinweis',TO_TIMESTAMP('2026-01-12 14:49:04.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-12T14:49:05.274Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-12T14:49:05.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584390)
;

-- 2026-01-12T14:49:05.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761008
;

-- 2026-01-12T14:49:05.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761008)
;

-- UI Element: Stücklistenbestandteile(540720,EE01) -> Stücklistenbestandteile(542034,EE01) -> main -> 10 -> description.Kommissionierhinweis
-- Column: PP_Product_BOMLine.PickingInstruction
-- 2026-01-12T14:49:41.445Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761008,0,542034,543048,641331,'F',TO_TIMESTAMP('2026-01-12 14:49:41.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','Y','N','Y','N','N','Kommissionierhinweis',80,0,0,TO_TIMESTAMP('2026-01-12 14:49:41.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Kommissionierhinweis
-- Column: PP_Product_BOMLine.PickingInstruction
-- 2026-01-12T14:50:09.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591855,761009,0,53029,TO_TIMESTAMP('2026-01-12 14:50:08.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).',2000,'EE01','Y','N','N','N','N','N','N','N','Kommissionierhinweis',TO_TIMESTAMP('2026-01-12 14:50:08.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-12T14:50:09.171Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-12T14:50:09.175Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584390)
;

-- 2026-01-12T14:50:09.179Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761009
;

-- 2026-01-12T14:50:09.180Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761009)
;

-- UI Column: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10
-- UI Element Group: description
-- 2026-01-12T14:51:09.213Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540275,554124,TO_TIMESTAMP('2026-01-12 14:51:09.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','description',20,TO_TIMESTAMP('2026-01-12 14:51:09.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> description.Kommissionierhinweis
-- Column: PP_Product_BOMLine.PickingInstruction
-- 2026-01-12T14:51:20.339Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761009,0,53029,554124,641332,'F',TO_TIMESTAMP('2026-01-12 14:51:20.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','Y','N','Y','N','N','Kommissionierhinweis',10,0,0,TO_TIMESTAMP('2026-01-12 14:51:20.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

