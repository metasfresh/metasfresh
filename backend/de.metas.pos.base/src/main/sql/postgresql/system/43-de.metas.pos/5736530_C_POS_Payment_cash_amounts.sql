-- Element: AmountTendered
-- 2024-10-09T20:36:23.955Z
UPDATE AD_Element_Trl SET Name='Tendered Amount', PrintName='Tendered Amount',Updated=TO_TIMESTAMP('2024-10-09 23:36:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=52021 AND AD_Language='de_CH'
;

-- 2024-10-09T20:36:23.970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(52021,'de_CH') 
;

-- Element: AmountTendered
-- 2024-10-09T20:36:27.254Z
UPDATE AD_Element_Trl SET Name='Tendered Amount', PrintName='Tendered Amount',Updated=TO_TIMESTAMP('2024-10-09 23:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=52021 AND AD_Language='en_US'
;

-- 2024-10-09T20:36:27.257Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(52021,'en_US') 
;

-- Element: AmountTendered
-- 2024-10-09T20:36:31.258Z
UPDATE AD_Element_Trl SET Name='Tendered Amount', PrintName='Tendered Amount',Updated=TO_TIMESTAMP('2024-10-09 23:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=52021 AND AD_Language='de_DE'
;

-- 2024-10-09T20:36:31.260Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(52021,'de_DE') 
;

-- 2024-10-09T20:36:31.273Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(52021,'de_DE') 
;

-- Column: C_POS_Payment.AmountTendered
-- Column: C_POS_Payment.AmountTendered
-- 2024-10-09T20:37:09.876Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589295,52021,0,12,542437,'XX','AmountTendered',TO_TIMESTAMP('2024-10-09 23:37:09','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Tendered Amount',0,0,TO_TIMESTAMP('2024-10-09 23:37:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T20:37:09.879Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589295 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T20:37:09.882Z
/* DDL */  select update_Column_Translation_From_AD_Element(52021) 
;

-- 2024-10-09T20:37:10.527Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN AmountTendered NUMERIC DEFAULT 0 NOT NULL')
;

-- 2024-10-09T20:38:43.012Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583323,0,'ChangeBackAmount',TO_TIMESTAMP('2024-10-09 23:38:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Change','Change',TO_TIMESTAMP('2024-10-09 23:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T20:38:43.015Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583323 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Payment.ChangeBackAmount
-- Column: C_POS_Payment.ChangeBackAmount
-- 2024-10-09T20:39:17.701Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589296,583323,0,12,542437,'XX','ChangeBackAmount',TO_TIMESTAMP('2024-10-09 23:39:17','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Change',0,0,TO_TIMESTAMP('2024-10-09 23:39:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T20:39:17.703Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589296 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T20:39:17.706Z
/* DDL */  select update_Column_Translation_From_AD_Element(583323) 
;

-- 2024-10-09T20:39:18.314Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN ChangeBackAmount NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: POS Order -> POS Payment -> Tendered Amount
-- Column: C_POS_Payment.AmountTendered
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Tendered Amount
-- Column: C_POS_Payment.AmountTendered
-- 2024-10-09T20:39:33.731Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589295,731899,0,547593,TO_TIMESTAMP('2024-10-09 23:39:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Tendered Amount',TO_TIMESTAMP('2024-10-09 23:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T20:39:33.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T20:39:33.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52021) 
;

-- 2024-10-09T20:39:33.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731899
;

-- 2024-10-09T20:39:33.745Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731899)
;

-- Field: POS Order -> POS Payment -> Change
-- Column: C_POS_Payment.ChangeBackAmount
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Change
-- Column: C_POS_Payment.ChangeBackAmount
-- 2024-10-09T20:39:33.868Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589296,731900,0,547593,TO_TIMESTAMP('2024-10-09 23:39:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Change',TO_TIMESTAMP('2024-10-09 23:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T20:39:33.869Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T20:39:33.871Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583323) 
;

-- 2024-10-09T20:39:33.874Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731900
;

-- 2024-10-09T20:39:33.875Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731900)
;

-- UI Element: POS Order -> POS Payment.Tendered Amount
-- Column: C_POS_Payment.AmountTendered
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10 -> default.Tendered Amount
-- Column: C_POS_Payment.AmountTendered
-- 2024-10-09T20:40:43.729Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731899,0,547593,551951,626181,'F',TO_TIMESTAMP('2024-10-09 23:40:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tendered Amount',30,0,0,TO_TIMESTAMP('2024-10-09 23:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Change
-- Column: C_POS_Payment.ChangeBackAmount
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10 -> default.Change
-- Column: C_POS_Payment.ChangeBackAmount
-- 2024-10-09T20:40:49.799Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731900,0,547593,551951,626182,'F',TO_TIMESTAMP('2024-10-09 23:40:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Change',40,0,0,TO_TIMESTAMP('2024-10-09 23:40:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS Order -> POS Payment -> Tendered Amount
-- Column: C_POS_Payment.AmountTendered
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Tendered Amount
-- Column: C_POS_Payment.AmountTendered
-- 2024-10-09T20:41:41.850Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentMethod@=CASH',Updated=TO_TIMESTAMP('2024-10-09 23:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731899
;

-- Field: POS Order -> POS Payment -> Change
-- Column: C_POS_Payment.ChangeBackAmount
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Change
-- Column: C_POS_Payment.ChangeBackAmount
-- 2024-10-09T20:41:45.483Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentMethod@=CASH',Updated=TO_TIMESTAMP('2024-10-09 23:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731900
;

