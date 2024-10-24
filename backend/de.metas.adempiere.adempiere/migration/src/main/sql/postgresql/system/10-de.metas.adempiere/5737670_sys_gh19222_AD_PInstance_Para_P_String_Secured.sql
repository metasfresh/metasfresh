-- Run mode: SWING_CLIENT

-- 2024-10-24T11:12:05.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583347,0,'P_String_Secured',TO_TIMESTAMP('2024-10-24 11:12:04.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozess-Parameter','D','Y','Process String','P String',TO_TIMESTAMP('2024-10-24 11:12:04.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-24T11:12:05.272Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583347 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: P_String_Secured
-- 2024-10-24T11:14:26.957Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-24 11:14:26.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583347 AND AD_Language='en_US'
;

-- 2024-10-24T11:14:26.994Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583347,'en_US')
;

-- Element: P_String_Secured
-- 2024-10-24T11:14:46.805Z
UPDATE AD_Element_Trl SET Description='Process Parameter',Updated=TO_TIMESTAMP('2024-10-24 11:14:46.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583347 AND AD_Language='en_US'
;

-- 2024-10-24T11:14:46.807Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583347,'en_US')
;

-- Element: P_String_Secured
-- 2024-10-24T11:15:04.828Z
UPDATE AD_Element_Trl SET Description='Paramètre de processus', Name='Traiter la chaîne', PrintName='Traiter la chaîne',Updated=TO_TIMESTAMP('2024-10-24 11:15:04.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583347 AND AD_Language='fr_CH'
;

-- 2024-10-24T11:15:04.831Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583347,'fr_CH')
;

-- Column: AD_PInstance_Para.P_String_Secured
-- 2024-10-24T11:25:07.743Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589357,583347,0,10,283,'XX','P_String_Secured','(CASE WHEN Info=''********'' THEN ''********'' ELSE P_String END)',TO_TIMESTAMP('2024-10-24 11:25:07.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Prozess-Parameter','D',0,999999,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Process String','NP',0,0,TO_TIMESTAMP('2024-10-24 11:25:07.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-24T11:25:07.746Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589357 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-24T11:25:07.750Z
/* DDL */  select update_Column_Translation_From_AD_Element(583347)
;

-- Field: Prozess-Revision(332,D) -> Parameter-Revision(664,D) -> Process String
-- Column: AD_PInstance_Para.P_String_Secured
-- 2024-10-24T11:25:52.673Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589357,732007,0,664,TO_TIMESTAMP('2024-10-24 11:25:52.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozess-Parameter',999999,'D','Y','N','N','N','N','N','N','N','Process String',TO_TIMESTAMP('2024-10-24 11:25:52.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-24T11:25:52.724Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=732007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-24T11:25:52.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583347)
;

-- 2024-10-24T11:25:52.928Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732007
;

-- 2024-10-24T11:25:52.985Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732007)
;

-- UI Element: Prozess-Revision(332,D) -> Parameter-Revision(664,D) -> main -> 10 -> default.Process String
-- Column: AD_PInstance_Para.P_String
-- 2024-10-24T11:26:30.744Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547977
;

-- UI Element: Prozess-Revision(332,D) -> Parameter-Revision(664,D) -> main -> 10 -> default.Process String
-- Column: AD_PInstance_Para.P_String_Secured
-- 2024-10-24T11:27:06.658Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,732007,0,664,626237,541064,'F',TO_TIMESTAMP('2024-10-24 11:27:06.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozess-Parameter','Y','N','N','Y','N','N','N',0,'Process String',50,0,0,TO_TIMESTAMP('2024-10-24 11:27:06.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prozess-Revision(332,D) -> Parameter-Revision(664,D) -> main -> 10 -> default.Process String
-- Column: AD_PInstance_Para.P_String_Secured
-- 2024-10-24T11:27:29.335Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-24 11:27:29.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626237
;

