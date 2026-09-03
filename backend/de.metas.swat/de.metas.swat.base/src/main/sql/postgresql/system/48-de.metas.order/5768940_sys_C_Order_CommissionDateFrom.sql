-- 2025-09-15T09:39:48.502Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583965,0,'Commission_DateFrom',TO_TIMESTAMP('2025-09-15 09:39:48.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','Auf Kommission bis','Auf Kommission bis',TO_TIMESTAMP('2025-09-15 09:39:48.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-15T09:39:48.508Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583965 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-09-15T09:39:55.650Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-09-15 09:39:55.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583965
;

-- 2025-09-15T09:39:55.676Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583965,'de_DE')
;

-- Element: Commission_DateFrom
-- 2025-09-15T09:40:22.905Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission Date From', PrintName='Commission Date From',Updated=TO_TIMESTAMP('2025-09-15 09:40:22.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583965 AND AD_Language='en_US'
;

-- 2025-09-15T09:40:22.907Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-15T09:40:23.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583965,'en_US')
;

-- Column: C_Order.Commission_DateFrom
-- 2025-09-15T09:41:19.540Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590904,583965,0,15,259,'XX','Commission_DateFrom',TO_TIMESTAMP('2025-09-15 09:41:19.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auf Kommission bis',0,0,TO_TIMESTAMP('2025-09-15 09:41:19.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-15T09:41:19.542Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590904 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-15T09:41:19.546Z
/* DDL */  select update_Column_Translation_From_AD_Element(583965)
;

-- 2025-09-15T09:41:26.720Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN Commission_DateFrom TIMESTAMP WITHOUT TIME ZONE')
;




-- Field: Auftrag_OLD(143,D) -> Auftrag(186,D) -> Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2025-09-15T09:47:37.903Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590904,753612,0,186,TO_TIMESTAMP('2025-09-15 09:47:37.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','Y','N','N','N','N','N','Auf Kommission bis',TO_TIMESTAMP('2025-09-15 09:47:37.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-15T09:47:37.906Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-15T09:47:37.909Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583965)
;

-- 2025-09-15T09:47:37.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753612
;

-- 2025-09-15T09:47:37.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753612)
;

-- Field: Auftrag_OLD(143,D) -> Auftrag(186,D) -> Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2025-09-15T09:55:56.066Z
UPDATE AD_Field SET DisplayLogic='@DocSubType@=OOC',Updated=TO_TIMESTAMP('2025-09-15 09:55:56.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753612
;

-- Field: Auftrag_OLD(143,D) -> Auftrag(186,D) -> Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2025-09-15T09:56:37.009Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-09-15 09:56:37.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753612
;

-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> main view -> 20 -> DocType.Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2025-09-15T09:57:57.796Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753612,0,186,1000001,637034,'F',TO_TIMESTAMP('2025-09-15 09:57:57.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Auf Kommission bis',55,0,0,TO_TIMESTAMP('2025-09-15 09:57:57.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order.Commission_DateFrom
-- 2025-09-15T10:13:11.885Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-15 10:13:11.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590904
;









-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> DocType.Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2025-09-16T12:39:23.182Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-09-16 12:39:23.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637034
;

