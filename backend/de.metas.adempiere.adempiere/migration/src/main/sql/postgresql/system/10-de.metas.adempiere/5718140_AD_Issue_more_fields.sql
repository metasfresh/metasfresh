-- Column: AD_Issue.UserAgent
-- Column: AD_Issue.UserAgent
-- 2024-02-28T11:11:09.395Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587965,1704,0,36,828,'XX','UserAgent',TO_TIMESTAMP('2024-02-28 13:11:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Browser Used','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'User Agent',0,0,TO_TIMESTAMP('2024-02-28 13:11:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-28T11:11:09.397Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587965 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-28T11:11:09.427Z
/* DDL */  select update_Column_Translation_From_AD_Element(1704) 
;

-- 2024-02-28T11:11:10.536Z
/* DDL */ SELECT public.db_alter_table('AD_Issue','ALTER TABLE public.AD_Issue ADD COLUMN UserAgent TEXT')
;

-- Reference: AD_Issue_Category
-- Value: MOBILEUI
-- ValueName: MOBILEUI
-- 2024-02-28T11:12:12.106Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541151,543633,TO_TIMESTAMP('2024-02-28 13:12:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobile UI',TO_TIMESTAMP('2024-02-28 13:12:11','YYYY-MM-DD HH24:MI:SS'),100,'MOBILEUI','MOBILEUI')
;

-- 2024-02-28T11:12:12.108Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543633 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Field: Probleme -> Probleme -> User Agent
-- Column: AD_Issue.UserAgent
-- Field: Probleme(363,D) -> Probleme(777,D) -> User Agent
-- Column: AD_Issue.UserAgent
-- 2024-02-28T11:40:42.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587965,725344,0,777,TO_TIMESTAMP('2024-02-28 13:40:42','YYYY-MM-DD HH24:MI:SS'),100,'Browser Used',2000,'D','Y','N','N','N','N','N','N','N','User Agent',TO_TIMESTAMP('2024-02-28 13:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-28T11:40:42.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-28T11:40:42.381Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1704) 
;

-- 2024-02-28T11:40:42.395Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725344
;

-- 2024-02-28T11:40:42.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725344)
;

-- UI Element: Probleme -> Probleme.User Agent
-- Column: AD_Issue.UserAgent
-- UI Element: Probleme(363,D) -> Probleme(777,D) -> advanced edit -> 10 -> advanced edit.User Agent
-- Column: AD_Issue.UserAgent
-- 2024-02-28T11:41:46.693Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,725344,0,777,541419,623013,'F',TO_TIMESTAMP('2024-02-28 13:41:46','YYYY-MM-DD HH24:MI:SS'),100,'Browser Used','Y','N','Y','N','N','User Agent',460,0,0,TO_TIMESTAMP('2024-02-28 13:41:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Probleme -> Probleme.User Agent
-- Column: AD_Issue.UserAgent
-- UI Element: Probleme(363,D) -> Probleme(777,D) -> advanced edit -> 10 -> advanced edit.User Agent
-- Column: AD_Issue.UserAgent
-- 2024-02-28T11:41:54.034Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-02-28 13:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623013
;

