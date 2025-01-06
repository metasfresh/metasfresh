-- Column: PP_Order.WorkStation_ID
-- Column: PP_Order.WorkStation_ID
-- 2024-03-06T14:06:37.975Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587968,583018,0,30,541855,53027,540669,'XX','WorkStation_ID',TO_TIMESTAMP('2024-03-06 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Arbeitsstation',0,0,TO_TIMESTAMP('2024-03-06 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T14:06:37.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587968 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T14:06:37.980Z
/* DDL */  select update_Column_Translation_From_AD_Element(583018) 
;

-- 2024-03-06T14:06:38.723Z
/* DDL */ SELECT public.db_alter_table('PP_Order','ALTER TABLE public.PP_Order ADD COLUMN WorkStation_ID NUMERIC(10)')
;

-- 2024-03-06T14:06:39.351Z
ALTER TABLE PP_Order ADD CONSTRAINT WorkStation_PPOrder FOREIGN KEY (WorkStation_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produktionsauftrag -> Produktionsauftrag -> Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- Field: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- 2024-03-06T14:07:14.770Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587968,726563,0,53054,TO_TIMESTAMP('2024-03-06 16:07:14','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Arbeitsstation',TO_TIMESTAMP('2024-03-06 16:07:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T14:07:14.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T14:07:14.775Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583018) 
;

-- 2024-03-06T14:07:14.780Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726563
;

-- 2024-03-06T14:07:14.782Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726563)
;

