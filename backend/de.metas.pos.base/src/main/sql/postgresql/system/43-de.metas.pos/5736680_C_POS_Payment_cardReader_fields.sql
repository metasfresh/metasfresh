-- 2024-10-10T13:39:01.577Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583326,0,'POS_CardReader_ExternalId',TO_TIMESTAMP('2024-10-10 16:39:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Card Reader External ID','Card Reader External ID',TO_TIMESTAMP('2024-10-10 16:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T13:39:01.582Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583326 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-10T13:39:21.679Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583327,0,'POS_CardReader_Name',TO_TIMESTAMP('2024-10-10 16:39:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Card Reader Name','Card Reader Name',TO_TIMESTAMP('2024-10-10 16:39:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T13:39:21.682Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583327 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- 2024-10-10T13:39:52.803Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589300,583326,0,10,542437,'XX','POS_CardReader_ExternalId',TO_TIMESTAMP('2024-10-10 16:39:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Card Reader External ID',0,0,TO_TIMESTAMP('2024-10-10 16:39:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-10T13:39:52.805Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589300 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-10T13:39:52.808Z
/* DDL */  select update_Column_Translation_From_AD_Element(583326) 
;

-- 2024-10-10T13:39:53.603Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN POS_CardReader_ExternalId VARCHAR(255)')
;

-- Column: C_POS_Payment.POS_CardReader_Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- 2024-10-10T13:40:21.015Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589301,583327,0,10,542437,'XX','POS_CardReader_Name',TO_TIMESTAMP('2024-10-10 16:40:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Card Reader Name',0,0,TO_TIMESTAMP('2024-10-10 16:40:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-10T13:40:21.018Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589301 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-10T13:40:21.025Z
/* DDL */  select update_Column_Translation_From_AD_Element(583327) 
;

-- 2024-10-10T13:40:22.134Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN POS_CardReader_Name VARCHAR(255)')
;

-- Field: POS Order -> POS Payment -> Card Reader External ID
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Card Reader External ID
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- 2024-10-10T13:40:40.952Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589300,731904,0,547593,TO_TIMESTAMP('2024-10-10 16:40:40','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Card Reader External ID',TO_TIMESTAMP('2024-10-10 16:40:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T13:40:40.954Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-10T13:40:40.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583326) 
;

-- 2024-10-10T13:40:40.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731904
;

-- 2024-10-10T13:40:40.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731904)
;

-- Field: POS Order -> POS Payment -> Card Reader Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Card Reader Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- 2024-10-10T13:40:41.126Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589301,731905,0,547593,TO_TIMESTAMP('2024-10-10 16:40:40','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Card Reader Name',TO_TIMESTAMP('2024-10-10 16:40:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T13:40:41.128Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-10T13:40:41.130Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583327) 
;

-- 2024-10-10T13:40:41.134Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731905
;

-- 2024-10-10T13:40:41.135Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731905)
;

-- UI Element: POS Order -> POS Payment.Card Reader External ID
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Card Reader External ID
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- 2024-10-10T13:41:26.414Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731904,0,547593,551964,626186,'F',TO_TIMESTAMP('2024-10-10 16:41:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Reader External ID',1000,0,0,TO_TIMESTAMP('2024-10-10 16:41:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Card Reader Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Card Reader Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- 2024-10-10T13:41:32.646Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731905,0,547593,551964,626187,'F',TO_TIMESTAMP('2024-10-10 16:41:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Reader Name',1010,0,0,TO_TIMESTAMP('2024-10-10 16:41:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20
-- UI Element Group: payment
-- 2024-10-10T13:41:39.517Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547554,552043,TO_TIMESTAMP('2024-10-10 16:41:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','payment',20,TO_TIMESTAMP('2024-10-10 16:41:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-10-10T13:52:21.700Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552043, SeqNo=10,Updated=TO_TIMESTAMP('2024-10-10 16:52:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625769
;

-- Field: POS Order -> POS Payment -> Card Reader External ID
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Card Reader External ID
-- Column: C_POS_Payment.POS_CardReader_ExternalId
-- 2024-10-10T13:52:45.626Z
UPDATE AD_Field SET DisplayLogic='@POS_CardReader_ExternalId@!''''',Updated=TO_TIMESTAMP('2024-10-10 16:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731904
;

-- Field: POS Order -> POS Payment -> Card Reader Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Card Reader Name
-- Column: C_POS_Payment.POS_CardReader_Name
-- 2024-10-10T13:52:55.114Z
UPDATE AD_Field SET DisplayLogic='@POS_CardReader_Name@!''''',Updated=TO_TIMESTAMP('2024-10-10 16:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731905
;

