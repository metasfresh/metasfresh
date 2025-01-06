




-- Column: ModCntr_Module.Description
-- 2024-05-08T16:28:22.167Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588229,275,0,10,542340,'Description',TO_TIMESTAMP('2024-05-08 19:28:22.048','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2024-05-08 19:28:22.048','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-08T16:28:22.172Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588229 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-08T16:28:22.176Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- Column: ModCntr_Module.Description
-- 2024-05-08T16:28:27.249Z
UPDATE AD_Column SET ColumnSQL='(SELECT Description from ModCntr_Type t where t.ModCntr_Type_ID = ModCntr_Module.ModCntr_Type_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-05-08 19:28:27.249','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588229
;






-- Column: ModCntr_Module.Description
-- 2024-05-08T16:29:19.410Z
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2024-05-08 19:29:19.41','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588229
;






-- Column: ModCntr_Module.Description
-- Source Table: ModCntr_Type
-- 2024-05-08T16:34:17.969Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,588229,0,540158,542340,TO_TIMESTAMP('2024-05-08 19:34:17.776','YYYY-MM-DD HH24:MI:SS.US'),100,'L','Y',588229,588229,542337,TO_TIMESTAMP('2024-05-08 19:34:17.776','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-05-08T16:34:56.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588229,728684,0,547014,TO_TIMESTAMP('2024-05-08 19:34:56.23','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'de.metas.contracts','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-05-08 19:34:56.23','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-08T16:34:56.431Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-08T16:34:56.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2024-05-08T16:34:56.476Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728684
;

-- 2024-05-08T16:34:56.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728684)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-05-08T16:35:06.694Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-05-08 19:35:06.694','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728684
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-05-08T16:35:24.551Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728684,0,547014,550783,624722,'F',TO_TIMESTAMP('2024-05-08 19:35:24.428','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Beschreibung',60,0,0,TO_TIMESTAMP('2024-05-08 19:35:24.428','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> main -> 10 -> default.Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-05-08T16:35:36.470Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-05-08 19:35:36.469','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624722
;







-- Column: ModCntr_Module.Description
-- 2024-05-08T20:36:48.917Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2024-05-08 23:36:48.917','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588229
;

