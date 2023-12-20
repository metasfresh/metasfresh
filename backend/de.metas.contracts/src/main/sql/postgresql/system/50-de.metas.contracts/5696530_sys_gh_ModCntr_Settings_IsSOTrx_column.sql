-- Column: ModCntr_Settings.IsSOTrx
-- 2023-07-21T05:54:49.964288400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587164,1106,0,20,542339,'IsSOTrx',TO_TIMESTAMP('2023-07-21 08:54:49.633','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Dies ist eine Verkaufstransaktion','de.metas.contracts',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2023-07-21 08:54:49.633','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-07-21T05:54:50.467498700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-21T05:54:50.720122800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- 2023-07-21T05:54:53.889295200Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- UI Column: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20
-- UI Element Group: flags
-- 2023-07-21T05:56:25.893972400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546866,550849,TO_TIMESTAMP('2023-07-21 08:56:25.632','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',5,TO_TIMESTAMP('2023-07-21 08:56:25.632','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Settings.IsSOTrx
-- 2023-07-21T05:56:48.424613100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587164,717243,0,547013,TO_TIMESTAMP('2023-07-21 08:56:48.201','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion',1,'de.metas.contracts','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2023-07-21 08:56:48.201','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-07-21T05:56:48.428145100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-21T05:56:48.433155200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-07-21T05:56:48.465397900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717243
;

-- 2023-07-21T05:56:48.475398100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717243)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: ModCntr_Settings.IsSOTrx
-- 2023-07-21T05:57:10.692322700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717243,0,547013,618501,550849,'F',TO_TIMESTAMP('2023-07-21 08:57:10.46','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',10,0,0,TO_TIMESTAMP('2023-07-21 08:57:10.46','YYYY-MM-DD HH24:MI:SS.US'),100)
;

