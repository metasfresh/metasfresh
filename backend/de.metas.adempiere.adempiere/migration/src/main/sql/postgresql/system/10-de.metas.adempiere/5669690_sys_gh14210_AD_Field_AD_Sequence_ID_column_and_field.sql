-- Column: AD_Field.AD_Sequence_ID
-- 2022-12-22T14:05:17.114Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585429,124,0,19,107,'AD_Sequence_ID',TO_TIMESTAMP('2022-12-22 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Nummernfolgen für Belege','D',0,10,'Der Nummernkreis bestimmt, welche Nummernfolge für eine Belegart verwendet wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2022-12-22 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-22T14:05:17.119Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585429 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-22T14:05:17.123Z
/* DDL */  select update_Column_Translation_From_AD_Element(124) 
;

-- 2022-12-22T14:05:18.118Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN AD_Sequence_ID NUMERIC(10)')
;

-- 2022-12-22T14:05:18.225Z
ALTER TABLE AD_Field ADD CONSTRAINT ADSequence_ADField FOREIGN KEY (AD_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- Field: Fenster Verwaltung -> Feld -> Reihenfolge
-- Column: AD_Field.AD_Sequence_ID
-- 2022-12-22T14:07:23.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585429,710061,0,107,0,TO_TIMESTAMP('2022-12-22 16:07:22','YYYY-MM-DD HH24:MI:SS'),100,'Bietet sequenzielle Standardwerte',0,'D','Bietet sequenzielle Standardwerte',0,'Y','Y','Y','N','N','N','N','N','Reihenfolge',0,280,0,1,1,TO_TIMESTAMP('2022-12-22 16:07:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T14:07:23.069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-22T14:07:23.072Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(124) 
;

-- 2022-12-22T14:07:23.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710061
;

-- 2022-12-22T14:07:23.077Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710061)
;

-- Field: Fenster Verwaltung -> Feld -> Reihenfolge
-- Column: AD_Field.AD_Sequence_ID
-- 2022-12-22T14:08:08.285Z
UPDATE AD_Field SET SeqNo=325,Updated=TO_TIMESTAMP('2022-12-22 16:08:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710061
;

