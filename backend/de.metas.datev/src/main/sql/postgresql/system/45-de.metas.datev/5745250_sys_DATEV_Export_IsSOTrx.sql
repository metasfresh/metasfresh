-- Column: DATEV_Export.IsSOTrx
-- Column: DATEV_Export.IsSOTrx
-- 2025-01-30T16:39:51.272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589628,1106,0,17,319,540934,'IsSOTrx',TO_TIMESTAMP('2025-01-30 18:39:50.604','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','Dies ist eine Verkaufstransaktion','de.metas.datev',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2025-01-30 18:39:50.604','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-01-30T16:39:51.314Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-30T16:39:51.454Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- 2025-01-30T16:42:56.825Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN IsSOTrx CHAR(1)')
;

-- Field: Buchungen Export_OLD -> Buchungen Export -> Verkaufstransaktion
-- Column: DATEV_Export.IsSOTrx
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Verkaufstransaktion
-- Column: DATEV_Export.IsSOTrx
-- 2025-01-30T16:46:22.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589628,735596,0,541036,TO_TIMESTAMP('2025-01-30 18:46:21.677','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion',1,'de.metas.datev','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2025-01-30 18:46:21.677','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-30T16:46:22.436Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=735596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-30T16:46:22.478Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2025-01-30T16:46:22.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=735596
;

-- 2025-01-30T16:46:22.599Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(735596)
;

-- UI Element: Buchungen Export_OLD -> Buchungen Export.Verkaufstransaktion
-- Column: DATEV_Export.IsSOTrx
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: DATEV_Export.IsSOTrx
-- 2025-01-30T16:47:40.805Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,735596,0,541036,541481,628432,'F',TO_TIMESTAMP('2025-01-30 18:47:39.977','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','Verkaufstransaktion',60,0,0,TO_TIMESTAMP('2025-01-30 18:47:39.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

