-- Column: DD_Order_Candidate.QtyProcessed
-- Column: DD_Order_Candidate.QtyProcessed
-- 2024-08-01T11:20:07.065Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588897,580228,0,29,542424,'XX','QtyProcessed',TO_TIMESTAMP('2024-08-01 14:20:06','YYYY-MM-DD HH24:MI:SS'),100,'N','0','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitete Menge',0,0,TO_TIMESTAMP('2024-08-01 14:20:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-01T11:20:07.068Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588897 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-01T11:20:07.073Z
/* DDL */  select update_Column_Translation_From_AD_Element(580228) 
;

-- 2024-08-01T11:20:07.913Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN QtyProcessed NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: DD_Order_Candidate.QtyToProcess
-- Column: DD_Order_Candidate.QtyToProcess
-- 2024-08-01T11:20:23.537Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588898,580229,0,29,542424,'XX','QtyToProcess',TO_TIMESTAMP('2024-08-01 14:20:23','YYYY-MM-DD HH24:MI:SS'),100,'N','0','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zu verarbeitende Menge',0,0,TO_TIMESTAMP('2024-08-01 14:20:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-01T11:20:23.539Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588898 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-01T11:20:23.543Z
/* DDL */  select update_Column_Translation_From_AD_Element(580229) 
;

-- 2024-08-01T11:20:24.407Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN QtyToProcess NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- 2024-08-01T11:20:44.906Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588897,729783,0,547559,TO_TIMESTAMP('2024-08-01 14:20:44','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Verarbeitete Menge',TO_TIMESTAMP('2024-08-01 14:20:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-01T11:20:44.911Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-01T11:20:44.913Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580228) 
;

-- 2024-08-01T11:20:44.918Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729783
;

-- 2024-08-01T11:20:44.919Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729783)
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- 2024-08-01T11:20:45.035Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588898,729784,0,547559,TO_TIMESTAMP('2024-08-01 14:20:44','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Zu verarbeitende Menge',TO_TIMESTAMP('2024-08-01 14:20:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-01T11:20:45.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-01T11:20:45.040Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580229) 
;

-- 2024-08-01T11:20:45.043Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729784
;

-- 2024-08-01T11:20:45.044Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729784)
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- 2024-08-01T11:20:59.279Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-01 14:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729783
;

-- Field: Distributionsdisposition -> Distributionsdisposition -> Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- 2024-08-01T11:21:00.543Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-01 14:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729784
;

-- UI Element: Distributionsdisposition -> Distributionsdisposition.Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- UI Element: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> main -> 10 -> product&qty.Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2024-08-01T11:22:02.932Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2024-08-01 14:22:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625017
;

-- UI Element: Distributionsdisposition -> Distributionsdisposition.Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- UI Element: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> main -> 10 -> product&qty.Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- 2024-08-01T11:22:35.553Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729783,0,547559,551861,625248,'F',TO_TIMESTAMP('2024-08-01 14:22:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitete Menge',60,0,0,TO_TIMESTAMP('2024-08-01 14:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distributionsdisposition -> Distributionsdisposition.Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- UI Element: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> main -> 10 -> product&qty.Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- 2024-08-01T11:22:42.714Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729784,0,547559,551861,625249,'F',TO_TIMESTAMP('2024-08-01 14:22:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zu verarbeitende Menge',70,0,0,TO_TIMESTAMP('2024-08-01 14:22:42','YYYY-MM-DD HH24:MI:SS'),100)
;

