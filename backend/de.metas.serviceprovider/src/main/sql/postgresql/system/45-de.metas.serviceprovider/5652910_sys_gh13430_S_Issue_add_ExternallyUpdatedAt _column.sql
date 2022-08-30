-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:48:34.120Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584228,578985,0,16,541468,'ExternallyUpdatedAt',TO_TIMESTAMP('2022-08-25 04:48:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.serviceprovider',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externally updated at',0,0,TO_TIMESTAMP('2022-08-25 04:48:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-25T01:48:34.163Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584228 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-25T01:48:34.290Z
/* DDL */  select update_Column_Translation_From_AD_Element(578985) 
;

-- 2022-08-25T01:48:39.787Z
/* DDL */ SELECT public.db_alter_table('S_Issue','ALTER TABLE public.S_Issue ADD COLUMN ExternallyUpdatedAt TIMESTAMP WITH TIME ZONE')
;

-- Field: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:49:16.835Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584228,705540,0,542341,TO_TIMESTAMP('2022-08-25 04:49:16','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Externally updated at',TO_TIMESTAMP('2022-08-25 04:49:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T01:49:16.843Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-25T01:49:16.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578985) 
;

-- 2022-08-25T01:49:16.915Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705540
;

-- 2022-08-25T01:49:16.933Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705540)
;

-- UI Column: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Issue -> 20
-- UI Element Group: external-issue
-- 2022-08-25T01:50:52.797Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542525,549837,TO_TIMESTAMP('2022-08-25 04:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','external-issue',30,TO_TIMESTAMP('2022-08-25 04:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Issue -> 20 -> external-issue.Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:51:08.087Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705540,0,542341,612216,549837,'F',TO_TIMESTAMP('2022-08-25 04:51:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externally updated at',10,0,0,TO_TIMESTAMP('2022-08-25 04:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:52:52.578Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2022-08-25 04:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584228
;

-- 2022-08-25T01:52:55.290Z
INSERT INTO t_alter_column values('s_issue','ExternallyUpdatedAt','TIMESTAMP WITH TIME ZONE',null,null)
;

-- UI Column: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Issue -> 20
-- UI Element Group: external-issue
-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Issue -> 20 -> external-issue.Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:53:57.145Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612216
;

-- 2022-08-25T01:53:57.151Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549837
;

-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> main -> 10 -> default.Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:54:51.325Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705540,0,542341,612217,543568,'F',TO_TIMESTAMP('2022-08-25 04:54:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externally updated at',67,0,0,TO_TIMESTAMP('2022-08-25 04:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> main -> 10 -> default.Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:55:23.959Z
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2022-08-25 04:55:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612217
;

-- Field: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:56:46.951Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584228,705541,0,542340,TO_TIMESTAMP('2022-08-25 04:56:46','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Externally updated at',TO_TIMESTAMP('2022-08-25 04:56:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T01:56:46.952Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-25T01:56:46.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578985) 
;

-- 2022-08-25T01:56:46.960Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705541
;

-- 2022-08-25T01:56:46.961Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705541)
;

-- UI Element: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> main -> 10 -> default.Externally updated at
-- Column: S_Issue.ExternallyUpdatedAt
-- 2022-08-25T01:57:09.323Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705541,0,542340,612218,543562,'F',TO_TIMESTAMP('2022-08-25 04:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externally updated at',80,0,0,TO_TIMESTAMP('2022-08-25 04:57:09','YYYY-MM-DD HH24:MI:SS'),100)
;

