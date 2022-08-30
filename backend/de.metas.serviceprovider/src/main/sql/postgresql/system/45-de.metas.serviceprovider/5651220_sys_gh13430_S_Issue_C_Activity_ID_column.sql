-- Column: S_Issue.C_Activity_ID
-- 2022-08-16T14:58:08.949Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584051,1005,0,19,541468,'C_Activity_ID',TO_TIMESTAMP('2022-08-16 17:58:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Kostenstelle','de.metas.serviceprovider',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2022-08-16 17:58:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-16T14:58:08.961Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584051 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-16T14:58:09.006Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- 2022-08-16T14:58:11.676Z
/* DDL */ SELECT public.db_alter_table('S_Issue','ALTER TABLE public.S_Issue ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2022-08-16T14:58:11.708Z
ALTER TABLE S_Issue ADD CONSTRAINT CActivity_SIssue FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;

-- Field: Effort issue -> Issue -> Kostenstelle
-- Column: S_Issue.C_Activity_ID
-- 2022-08-17T08:42:25.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584051,704834,0,542340,TO_TIMESTAMP('2022-08-17 11:42:25','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',10,'de.metas.serviceprovider','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','N','Kostenstelle',TO_TIMESTAMP('2022-08-17 11:42:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-17T08:42:25.324Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-17T08:42:25.337Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2022-08-17T08:42:25.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704834
;

-- 2022-08-17T08:42:25.430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(704834)
;

-- UI Element: Effort issue -> Issue.Kostenstelle
-- Column: S_Issue.C_Activity_ID
-- 2022-08-17T08:43:04.723Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,704834,0,542340,611783,543562,'F',TO_TIMESTAMP('2022-08-17 11:43:04','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',65,0,0,TO_TIMESTAMP('2022-08-17 11:43:04','YYYY-MM-DD HH24:MI:SS'),100)
;

