-- Run mode: SWING_CLIENT

-- 2025-10-22T19:42:59.918Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584138,0,'DefaultValueSQL',TO_TIMESTAMP('2025-10-22 19:42:59.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','','Y','Standardwert-Logik (SQL)','Standardwert-Logik (SQL)',TO_TIMESTAMP('2025-10-22 19:42:59.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T19:42:59.949Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584138 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DefaultValueSQL
-- 2025-10-22T19:43:05.400Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-22 19:43:05.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584138 AND AD_Language='de_CH'
;

-- 2025-10-22T19:43:05.622Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584138,'de_CH')
;

-- Element: DefaultValueSQL
-- 2025-10-22T19:43:06.942Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-22 19:43:06.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584138 AND AD_Language='de_DE'
;

-- 2025-10-22T19:43:06.945Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584138,'de_DE')
;

-- 2025-10-22T19:43:06.946Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584138,'de_DE')
;

-- Element: DefaultValueSQL
-- 2025-10-22T19:43:25.503Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Default Value (SQL)', PrintName='Default Value (SQL)',Updated=TO_TIMESTAMP('2025-10-22 19:43:25.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584138 AND AD_Language='en_US'
;

-- 2025-10-22T19:43:25.504Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-22T19:43:25.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584138,'en_US')
;

-- Column: M_Attribute.DefaultValueSQL
-- 2025-10-22T19:43:47.859Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591423,584138,0,36,562,'XX','DefaultValueSQL',TO_TIMESTAMP('2025-10-22 19:43:47.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,-727379969,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standardwert-Logik (SQL)',0,0,TO_TIMESTAMP('2025-10-22 19:43:47.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-22T19:43:47.864Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591423 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-22T19:43:47.869Z
/* DDL */  select update_Column_Translation_From_AD_Element(584138)
;

-- Column: M_Attribute.DefaultValueSQL
-- 2025-10-22T19:43:51.383Z
UPDATE AD_Column SET FieldLength=9999999,Updated=TO_TIMESTAMP('2025-10-22 19:43:51.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591423
;

-- 2025-10-22T19:43:54.979Z
/* DDL */ SELECT public.db_alter_table('M_Attribute','ALTER TABLE public.M_Attribute ADD COLUMN DefaultValueSQL TEXT')
;

-- Field: Merkmal(260,D) -> Merkmal(462,D) -> Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2025-10-22T19:44:14.690Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591423,755063,0,462,TO_TIMESTAMP('2025-10-22 19:44:14.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',9999999,'D','','Y','N','N','N','N','N','N','N','Standardwert-Logik (SQL)',TO_TIMESTAMP('2025-10-22 19:44:14.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T19:44:14.703Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T19:44:14.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584138)
;

-- 2025-10-22T19:44:14.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755063
;

-- 2025-10-22T19:44:14.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755063)
;

-- UI Element: Merkmal(260,D) -> Merkmal(462,D) -> main -> 10 -> technical.Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2025-10-22T19:46:20.801Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755063,0,462,540208,637929,'F',TO_TIMESTAMP('2025-10-22 19:46:20.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Standardwert-Logik (SQL)',40,0,0,TO_TIMESTAMP('2025-10-22 19:46:20.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(260,D) -> Merkmal(462,D) -> main -> 10
-- UI Element Group: technical
-- 2025-10-22T19:46:25.625Z
UPDATE AD_UI_ElementGroup SET SeqNo=5,Updated=TO_TIMESTAMP('2025-10-22 19:46:25.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540208
;

-- UI Column: Merkmal(260,D) -> Merkmal(462,D) -> main -> 10
-- UI Element Group: technical
-- 2025-10-22T19:46:30.873Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-10-22 19:46:30.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540208
;

-- UI Element: Merkmal(260,D) -> Merkmal(462,D) -> main -> 10 -> technical.Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2025-10-22T19:46:39.526Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2025-10-22 19:46:39.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637929
;

