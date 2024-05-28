-- Run mode: SWING_CLIENT

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-28T13:53:38.180Z
UPDATE AD_Column SET DefaultValue='''Q''',Updated=TO_TIMESTAMP('2024-05-28 16:53:38.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588286
;

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-28T13:53:41.086Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-05-28 16:53:41.085','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588286
;

-- 2024-05-28T13:53:52.084Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom','VARCHAR(5)',null,'Q')
;

-- 2024-05-28T13:53:52.089Z
UPDATE M_ProductPrice SET ScalePriceQuantityFrom='Q' WHERE ScalePriceQuantityFrom IS NULL
;

-- 2024-05-28T13:53:52.097Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom',null,'NOT NULL',null)
;

-- UI Element: Vertrag(541798,de.metas.endcustomer.is184) -> Vertragsspezifische Preise(547537,de.metas.endcustomer.is184) -> 1000028 -> 10 -> main.Staffelpreis
-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-05-28T13:58:07.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728761,0,547537,551808,624778,'F',TO_TIMESTAMP('2024-05-28 16:58:07.712','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Staffelpreis',80,0,0,TO_TIMESTAMP('2024-05-28 16:58:07.712','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(541798,de.metas.endcustomer.is184) -> Vertragsspezifische Preise(547537,de.metas.endcustomer.is184) -> 1000028 -> 10 -> main.Mindestwert
-- Column: ModCntr_Specific_Price.MinValue
-- 2024-05-28T13:58:16.303Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728762,0,547537,551808,624779,'F',TO_TIMESTAMP('2024-05-28 16:58:16.167','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Mindestwert',90,0,0,TO_TIMESTAMP('2024-05-28 16:58:16.167','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_ProductPrice.ScalePriceQuantityFrom
-- 2024-05-28T14:05:29.078Z
UPDATE AD_Column SET DefaultValue='Q', IsMandatory='N',Updated=TO_TIMESTAMP('2024-05-28 17:05:29.078','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588286
;

-- 2024-05-28T14:05:32.990Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom','VARCHAR(5)',null,'Q')
;

-- 2024-05-28T14:05:32.995Z
INSERT INTO t_alter_column values('m_productprice','ScalePriceQuantityFrom',null,'NULL',null)
;

-- Column: ModCntr_Log.UserElementNumber1
-- 2024-05-28T14:53:45.415Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588324,583112,0,22,542338,'UserElementNumber1',TO_TIMESTAMP('2024-05-28 17:53:45.196','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'HL',0,0,TO_TIMESTAMP('2024-05-28 17:53:45.196','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-28T14:53:45.426Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588324 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-28T14:53:45.462Z
/* DDL */  select update_Column_Translation_From_AD_Element(583112)
;

-- 2024-05-28T14:54:11.499Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN UserElementNumber1 NUMERIC')
;

-- Column: ModCntr_Log.UserElementNumber2
-- 2024-05-28T14:54:33.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588325,583113,0,22,542338,'UserElementNumber2',TO_TIMESTAMP('2024-05-28 17:54:33.614','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Protein',0,0,TO_TIMESTAMP('2024-05-28 17:54:33.614','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-28T14:54:33.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588325 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-28T14:54:33.730Z
/* DDL */  select update_Column_Translation_From_AD_Element(583113)
;

-- 2024-05-28T14:54:35.670Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN UserElementNumber2 NUMERIC')
;

-- 2024-05-28T15:13:59.565Z
INSERT INTO t_alter_column values('modcntr_log','UserElementNumber2','NUMERIC',null,null)
;

-- 2024-05-28T15:14:02.927Z
INSERT INTO t_alter_column values('modcntr_log','UserElementNumber1','NUMERIC',null,null)
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Manufacturing Cost Collector
-- Column: ModCntr_Log.PP_Cost_Collector_ID
-- 2024-05-28T15:14:15.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587472,728765,0,547012,TO_TIMESTAMP('2024-05-28 18:14:14.89','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Manufacturing Cost Collector',TO_TIMESTAMP('2024-05-28 18:14:14.89','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-28T15:14:15.009Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-28T15:14:15.011Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53310)
;

-- 2024-05-28T15:14:15.023Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728765
;

-- 2024-05-28T15:14:15.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728765)
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Ursprüngliches Produkt
-- Column: ModCntr_Log.Initial_Product_ID
-- 2024-05-28T15:14:15.127Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588192,728766,0,547012,TO_TIMESTAMP('2024-05-28 18:14:15.03','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Ursprüngliches Produkt',TO_TIMESTAMP('2024-05-28 18:14:15.03','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-28T15:14:15.129Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728766 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-28T15:14:15.130Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583085)
;

-- 2024-05-28T15:14:15.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728766
;

-- 2024-05-28T15:14:15.133Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728766)
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> HL
-- Column: ModCntr_Log.UserElementNumber1
-- 2024-05-28T15:14:15.215Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588324,728767,0,547012,TO_TIMESTAMP('2024-05-28 18:14:15.136','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','HL',TO_TIMESTAMP('2024-05-28 18:14:15.136','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-28T15:14:15.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728767 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-28T15:14:15.218Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583112)
;

-- 2024-05-28T15:14:15.220Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728767
;

-- 2024-05-28T15:14:15.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728767)
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Protein
-- Column: ModCntr_Log.UserElementNumber2
-- 2024-05-28T15:14:15.309Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588325,728768,0,547012,TO_TIMESTAMP('2024-05-28 18:14:15.223','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Protein',TO_TIMESTAMP('2024-05-28 18:14:15.223','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-28T15:14:15.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728768 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-28T15:14:15.313Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583113)
;

-- 2024-05-28T15:14:15.316Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728768
;

-- 2024-05-28T15:14:15.316Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728768)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.HL
-- Column: ModCntr_Log.UserElementNumber1
-- 2024-05-28T15:15:31.106Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728767,0,547012,550777,624780,'F',TO_TIMESTAMP('2024-05-28 18:15:31.003','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'HL',60,0,0,TO_TIMESTAMP('2024-05-28 18:15:31.003','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Protein
-- Column: ModCntr_Log.UserElementNumber2
-- 2024-05-28T15:15:39.651Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728768,0,547012,550777,624781,'F',TO_TIMESTAMP('2024-05-28 18:15:39.528','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Protein',70,0,0,TO_TIMESTAMP('2024-05-28 18:15:39.528','YYYY-MM-DD HH24:MI:SS.US'),100)
;

