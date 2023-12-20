-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T08:55:15.773358100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586511,582271,0,19,487,'S_HumanResourceTestGroup_ID',TO_TIMESTAMP('2023-05-02 11:55:15.622','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Test facility group',0,0,TO_TIMESTAMP('2023-05-02 11:55:15.622','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:55:15.774349100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:55:16.311383Z
/* DDL */  select update_Column_Translation_From_AD_Element(582271) 
;

-- Field: Ressource_OLD(236,D) -> Ressource(414,D) -> Test facility group
-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T08:56:28.136370300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586511,715055,0,414,TO_TIMESTAMP('2023-05-02 11:56:27.998','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Test facility group',TO_TIMESTAMP('2023-05-02 11:56:27.998','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:56:28.138366800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T08:56:28.141366600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582271) 
;

-- 2023-05-02T08:56:28.146370100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715055
;

-- 2023-05-02T08:56:28.151382100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715055)
;

-- UI Element: Ressource_OLD(236,D) -> Ressource(414,D) -> main -> 10 -> default.Test facility group
-- Column: S_HumanResourceTestGroup.S_HumanResourceTestGroup_ID
-- 2023-05-02T08:59:19.236673700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715049,0,414,617288,543924,'F',TO_TIMESTAMP('2023-05-02 11:59:19.086','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Test facility group',57,0,0,TO_TIMESTAMP('2023-05-02 11:59:19.086','YYYY-MM-DD HH24:MI:SS.US'),100)
;



-- Table: S_HumanResourceTestGroup
-- 2023-05-02T09:36:42.457203200Z
UPDATE AD_Table SET AD_Window_ID=541701,Updated=TO_TIMESTAMP('2023-05-02 12:36:42.457','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542326
;

-- Column: S_HumanResourceTestGroup.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:40:06.928582100Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=3,Updated=TO_TIMESTAMP('2023-05-02 12:40:06.928','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586505
;

-- 2023-05-02T09:40:08.806713500Z
INSERT INTO t_alter_column values('s_humanresourcetestgroup','S_HumanResourceTestGroup_ID','NUMERIC(10)',null,null)
;

-- 2023-05-02T09:41:08.567183400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715055
;

-- Field: Ressource_OLD(236,D) -> Ressource(414,D) -> Test facility group
-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:41:08.596707100Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=715055
;

-- 2023-05-02T09:41:08.603291100Z
DELETE FROM AD_Field WHERE AD_Field_ID=715055
;

-- 2023-05-02T09:41:08.626672800Z
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE S_Resource DROP COLUMN IF EXISTS S_HumanResourceTestGroup_ID')
;

-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:41:08.813777Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586511
;

-- 2023-05-02T09:41:08.815776800Z
DELETE FROM AD_Column WHERE AD_Column_ID=586511
;

-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:41:26.214286900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586512,582271,0,19,487,'S_HumanResourceTestGroup_ID',TO_TIMESTAMP('2023-05-02 12:41:26.054','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Test facility group',0,0,TO_TIMESTAMP('2023-05-02 12:41:26.054','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T09:41:26.220271800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586512 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T09:41:26.825604200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582271)
;

-- 2023-05-02T09:41:28.952359100Z
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE public.S_Resource ADD COLUMN S_HumanResourceTestGroup_ID NUMERIC(10)')
;

-- 2023-05-02T09:41:29.108596200Z
ALTER TABLE S_Resource ADD CONSTRAINT SHumanResourceTestGroup_SResource FOREIGN KEY (S_HumanResourceTestGroup_ID) REFERENCES public.S_HumanResourceTestGroup DEFERRABLE INITIALLY DEFERRED
;

-- Field: Ressource_OLD(236,D) -> Ressource(414,D) -> Test facility group
-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:42:15.679791400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586512,715056,0,414,TO_TIMESTAMP('2023-05-02 12:42:15.538','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Test facility group',TO_TIMESTAMP('2023-05-02 12:42:15.538','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T09:42:15.682795300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-02T09:42:15.686792900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582271)
;

-- 2023-05-02T09:42:15.691786600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715056
;

-- 2023-05-02T09:42:15.696181600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715056)
;

-- UI Element: Ressource_OLD(236,D) -> Ressource(414,D) -> main -> 10 -> default.Test facility group
-- Column: S_HumanResourceTestGroup.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:42:45.063490400Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617288
;

-- UI Element: Ressource_OLD(236,D) -> Ressource(414,D) -> main -> 10 -> default.Test facility group
-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:43:21.827066900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,715056,0,414,617289,543924,'F',TO_TIMESTAMP('2023-05-02 12:43:21.685','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Test facility group',57,0,0,TO_TIMESTAMP('2023-05-02 12:43:21.685','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: S_Resource.S_HumanResourceTestGroup_ID
-- 2023-05-02T09:44:19.579547100Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-05-02 12:44:19.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586512
;

-- 2023-05-02T09:44:22.333104Z
INSERT INTO t_alter_column values('s_resource','S_HumanResourceTestGroup_ID','NUMERIC(10)',null,null)
;

