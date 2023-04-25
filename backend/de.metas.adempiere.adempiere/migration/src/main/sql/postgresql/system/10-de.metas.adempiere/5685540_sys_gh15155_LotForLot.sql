-- 2023-04-20T13:02:42.430Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582256,0,'IsLotForLot',TO_TIMESTAMP('2023-04-20 15:02:42.246','YYYY-MM-DD HH24:MI:SS.US'),100,'Forces quantity to be equal to ordered quantity.','D','Y','Lot for Lot','Lot for Lot',TO_TIMESTAMP('2023-04-20 15:02:42.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-20T13:02:42.437Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582256 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-04-20T13:03:03.202Z
UPDATE AD_Element_Trl SET Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.',Updated=TO_TIMESTAMP('2023-04-20 15:03:03.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582256 AND AD_Language='de_CH'
;

-- 2023-04-20T13:03:03.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582256,'de_CH') 
;

-- 2023-04-20T13:03:17.953Z
UPDATE AD_Element_Trl SET Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-20 15:03:17.951','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582256 AND AD_Language='de_DE'
;

-- 2023-04-20T13:03:17.954Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582256,'de_DE') 
;

-- 2023-04-20T13:03:17.964Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582256,'de_DE') 
;

-- 2023-04-20T13:03:17.966Z
UPDATE AD_Column SET ColumnName='IsLotForLot', Name='Lot for Lot', Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', Help=NULL WHERE AD_Element_ID=582256
;

-- 2023-04-20T13:03:17.967Z
UPDATE AD_Process_Para SET ColumnName='IsLotForLot', Name='Lot for Lot', Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', Help=NULL, AD_Element_ID=582256 WHERE UPPER(ColumnName)='ISLOTFORLOT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-04-20T13:03:17.975Z
UPDATE AD_Process_Para SET ColumnName='IsLotForLot', Name='Lot for Lot', Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', Help=NULL WHERE AD_Element_ID=582256 AND IsCentrallyMaintained='Y'
;

-- 2023-04-20T13:03:17.976Z
UPDATE AD_Field SET Name='Lot for Lot', Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582256) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582256)
;

-- 2023-04-20T13:03:17.996Z
UPDATE AD_Tab SET Name='Lot for Lot', Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582256
;

-- 2023-04-20T13:03:17.997Z
UPDATE AD_WINDOW SET Name='Lot for Lot', Description='Erzwingt, dass die Menge gleich der bestellten Menge ist.', Help=NULL WHERE AD_Element_ID = 582256
;

-- 2023-04-20T13:03:17.998Z
UPDATE AD_Menu SET   Name = 'Lot for Lot', Description = 'Erzwingt, dass die Menge gleich der bestellten Menge ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582256
;

-- 2023-04-20T13:03:20.648Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-20 15:03:20.646','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582256 AND AD_Language='de_CH'
;

-- 2023-04-20T13:03:20.649Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582256,'de_CH') 
;

-- 2023-04-20T13:03:25.046Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-20 15:03:25.045','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582256 AND AD_Language='en_US'
;

-- 2023-04-20T13:03:25.048Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582256,'en_US') 
;

-- Column: PP_Product_Planning.IsLotForLot
-- 2023-04-20T13:14:24.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586489,582256,0,20,53020,'IsLotForLot',TO_TIMESTAMP('2023-04-20 15:14:24.289','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Erzwingt, dass die Menge gleich der bestellten Menge ist.','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Lot for Lot',0,0,TO_TIMESTAMP('2023-04-20 15:14:24.289','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-04-20T13:14:24.429Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-20T13:14:24.434Z
/* DDL */  select update_Column_Translation_From_AD_Element(582256) 
;

-- 2023-04-20T13:15:33.580Z
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN IsLotForLot CHAR(1) DEFAULT ''Y'' CHECK (IsLotForLot IN (''Y'',''N'')) NOT NULL')
;

-- Column: MD_Candidate.IsLotForLot
-- 2023-04-20T13:17:25.995Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586490,582256,0,17,540528,540808,'IsLotForLot',TO_TIMESTAMP('2023-04-20 15:17:25.872','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','Erzwingt, dass die Menge gleich der bestellten Menge ist.','de.metas.material.dispo',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lot for Lot',0,0,TO_TIMESTAMP('2023-04-20 15:17:25.872','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-04-20T13:17:25.996Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586490 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-20T13:17:26Z
/* DDL */  select update_Column_Translation_From_AD_Element(582256) 
;

-- 2023-04-20T13:17:38.314Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate','ALTER TABLE public.MD_Candidate ADD COLUMN IsLotForLot CHAR(1)')
;

-- Field: Material Disposition -> Materialdispo -> Lot for Lot
-- Column: MD_Candidate.IsLotForLot
-- 2023-04-24T07:38:00.124Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586490,715028,0,540802,TO_TIMESTAMP('2023-04-24 09:37:59.912','YYYY-MM-DD HH24:MI:SS.US'),100,'Erzwingt, dass die Menge gleich der bestellten Menge ist.',1,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Lot for Lot',TO_TIMESTAMP('2023-04-24 09:37:59.912','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-24T07:38:00.128Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-24T07:38:00.161Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582256)
;

-- 2023-04-24T07:38:00.176Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715028
;

-- 2023-04-24T07:38:00.180Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715028)
;

-- UI Element: Material Disposition -> Materialdispo.Lot for Lot
-- Column: MD_Candidate.IsLotForLot
-- 2023-04-24T07:39:23.682Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715028,0,540802,540301,617259,'F',TO_TIMESTAMP('2023-04-24 09:39:23.562','YYYY-MM-DD HH24:MI:SS.US'),100,'Erzwingt, dass die Menge gleich der bestellten Menge ist.','Y','N','N','Y','N','N','N',0,'Lot for Lot',60,0,0,TO_TIMESTAMP('2023-04-24 09:39:23.562','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Material Disposition -> Materialdispo -> Lot for Lot
-- Column: MD_Candidate.IsLotForLot
-- 2023-04-24T07:40:29.452Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-04-24 09:40:29.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715028
;

-- Field: Produkt Plandaten -> Product Planning -> Lot for Lot
-- Column: PP_Product_Planning.IsLotForLot
-- 2023-04-24T07:43:59.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586489,715029,0,542102,TO_TIMESTAMP('2023-04-24 09:43:59.163','YYYY-MM-DD HH24:MI:SS.US'),100,'Erzwingt, dass die Menge gleich der bestellten Menge ist.',1,'D','Y','N','N','N','N','N','N','N','Lot for Lot',TO_TIMESTAMP('2023-04-24 09:43:59.163','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-24T07:43:59.310Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-24T07:43:59.311Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582256)
;

-- 2023-04-24T07:43:59.315Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715029
;

-- 2023-04-24T07:43:59.316Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715029)
;

-- UI Element: Produkt Plandaten -> Product Planning.Lot for Lot
-- Column: PP_Product_Planning.IsLotForLot
-- 2023-04-24T08:01:19.182Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715029,0,542102,543143,617260,'F',TO_TIMESTAMP('2023-04-24 10:01:19.072','YYYY-MM-DD HH24:MI:SS.US'),100,'Erzwingt, dass die Menge gleich der bestellten Menge ist.','Y','N','N','Y','N','N','N',0,'Lot for Lot',130,0,0,TO_TIMESTAMP('2023-04-24 10:01:19.072','YYYY-MM-DD HH24:MI:SS.US'),100)
;

