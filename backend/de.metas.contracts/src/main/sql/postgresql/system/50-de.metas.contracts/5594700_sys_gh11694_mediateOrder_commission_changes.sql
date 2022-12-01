-- 2021-06-23T14:26:03.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574584,561,0,19,541405,'C_OrderLine_ID',TO_TIMESTAMP('2021-06-23 17:26:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftragsposition','de.metas.contracts.commission',0,10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition',0,0,TO_TIMESTAMP('2021-06-23 17:26:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-23T14:26:03.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574584 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-23T14:26:03.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- 2021-06-23T14:26:10.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Instance','ALTER TABLE public.C_Commission_Instance ADD COLUMN C_OrderLine_ID NUMERIC(10)')
;

-- 2021-06-23T14:26:10.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Commission_Instance ADD CONSTRAINT COrderLine_CCommissionInstance FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-23T14:26:47.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574584,649745,0,541942,0,TO_TIMESTAMP('2021-06-23 17:26:47','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',0,'D','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','Y','Y','N','N','N','N','N','Auftragsposition',0,10,0,1,1,TO_TIMESTAMP('2021-06-23 17:26:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-23T14:26:47.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649745 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-23T14:26:47.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2021-06-23T14:26:47.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649745
;

-- 2021-06-23T14:26:47.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649745)
;

-- 2021-06-23T14:27:26.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.contracts.commission',Updated=TO_TIMESTAMP('2021-06-23 17:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649745
;

-- 2021-06-23T14:31:15.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542678,541115,TO_TIMESTAMP('2021-06-23 17:31:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Mediated order',TO_TIMESTAMP('2021-06-23 17:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Mediated order','Mediated order')
;

-- 2021-06-23T14:31:15.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542678 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-23T14:31:38.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Bestellvermittlung',Updated=TO_TIMESTAMP('2021-06-23 17:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542678
;

-- 2021-06-23T14:31:41.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Bestellvermittlung',Updated=TO_TIMESTAMP('2021-06-23 17:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542678
;

-- 2021-06-23T14:31:44.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Bestellvermittlung',Updated=TO_TIMESTAMP('2021-06-23 17:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542678
;

-- 2021-06-23T17:41:47.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574602,579182,0,13,541406,'C_BPartner_Payer_ID',TO_TIMESTAMP('2021-06-23 20:41:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenträger',0,0,TO_TIMESTAMP('2021-06-23 20:41:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-23T17:41:47.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574602 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-23T17:41:47.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579182) 
;

-- 2021-06-23T17:41:52.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Share','ALTER TABLE public.C_Commission_Share ADD COLUMN C_BPartner_Payer_ID NUMERIC(10)')
;

-- 2021-06-23T17:42:41.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574602,649754,0,541943,0,TO_TIMESTAMP('2021-06-23 20:42:41','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.contracts.commission',0,'Y','Y','Y','N','N','N','N','N','Kostenträger',0,30,0,1,1,TO_TIMESTAMP('2021-06-23 20:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-23T17:42:41.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-23T17:42:41.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579182) 
;

-- 2021-06-23T17:42:41.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649754
;

-- 2021-06-23T17:42:41.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649754)
;

-- 2021-06-23T17:43:19.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579388,0,'CommissionGiver',TO_TIMESTAMP('2021-06-23 20:43:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Commission giver','Commission giver',TO_TIMESTAMP('2021-06-23 20:43:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-23T17:43:19.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579388 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-23T17:43:36.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Provisionsgeber', PrintName='Provisionsgeber',Updated=TO_TIMESTAMP('2021-06-23 20:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579388 AND AD_Language='de_CH'
;

-- 2021-06-23T17:43:36.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579388,'de_CH') 
;

-- 2021-06-23T17:43:40.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Provisionsgeber', PrintName='Provisionsgeber',Updated=TO_TIMESTAMP('2021-06-23 20:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579388 AND AD_Language='de_DE'
;

-- 2021-06-23T17:43:40.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579388,'de_DE') 
;

-- 2021-06-23T17:43:40.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579388,'de_DE') 
;

-- 2021-06-23T17:43:40.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CommissionGiver', Name='Provisionsgeber', Description=NULL, Help=NULL WHERE AD_Element_ID=579388
;

-- 2021-06-23T17:43:40.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CommissionGiver', Name='Provisionsgeber', Description=NULL, Help=NULL, AD_Element_ID=579388 WHERE UPPER(ColumnName)='COMMISSIONGIVER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T17:43:40.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CommissionGiver', Name='Provisionsgeber', Description=NULL, Help=NULL WHERE AD_Element_ID=579388 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T17:43:40.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Provisionsgeber', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579388) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579388)
;

-- 2021-06-23T17:43:40.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Provisionsgeber', Name='Provisionsgeber' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579388)
;

-- 2021-06-23T17:43:40.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Provisionsgeber', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579388
;

-- 2021-06-23T17:43:40.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Provisionsgeber', Description=NULL, Help=NULL WHERE AD_Element_ID = 579388
;

-- 2021-06-23T17:43:40.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Provisionsgeber', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579388
;

-- 2021-06-23T17:43:44.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Provisionsgeber', PrintName='Provisionsgeber',Updated=TO_TIMESTAMP('2021-06-23 20:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579388 AND AD_Language='nl_NL'
;

-- 2021-06-23T17:43:44.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579388,'nl_NL') 
;

-- 2021-06-23T17:44:09.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579388, Description=NULL, Help=NULL, Name='Provisionsgeber',Updated=TO_TIMESTAMP('2021-06-23 20:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649754
;

-- 2021-06-23T17:44:09.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579388) 
;

-- 2021-06-23T17:44:09.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649754
;

-- 2021-06-23T17:44:09.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649754)
;

-- 2021-06-23T17:46:11.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-06-23 20:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574602
;

-- 2021-06-23T17:47:30.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,649754,0,541943,586815,542899,'F',TO_TIMESTAMP('2021-06-23 20:47:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Provisionsgeber',70,0,0,TO_TIMESTAMP('2021-06-23 20:47:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-23T17:48:21.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561848
;

-- 2021-06-23T17:48:21.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561849
;

-- 2021-06-23T17:48:21.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561850
;

-- 2021-06-23T17:48:21.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563583
;

-- 2021-06-23T17:48:21.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563584
;

-- 2021-06-23T17:48:21.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561855
;

-- 2021-06-23T17:48:21.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586815
;

-- 2021-06-23T17:48:21.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-06-23 20:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563312
;

-- 2021-06-23T17:50:25.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-23 20:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568756
;

-- 2021-06-23T17:50:25.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-06-23 20:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570067
;

-- 2021-06-23T17:50:25.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2021-06-23 20:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568765
;

-- 2021-06-23T17:50:26.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-06-23 20:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568766
;

-- 2021-06-23T17:50:26.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-06-23 20:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568767
;

-- 2021-06-23T17:50:26.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-06-23 20:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568768
;

-- 2021-06-23T17:50:27.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-06-23 20:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569251
;

-- 2021-06-23T17:50:27.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2021-06-23 20:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569252
;

-- 2021-06-23T17:50:27.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2021-06-23 20:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568764
;

-- 2021-06-23T17:50:28.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2021-06-23 20:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574602
;

-- 2021-06-23T17:50:28.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2021-06-23 20:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569225
;

-- 2021-06-23T18:04:28.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-23 21:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561853
;

-- 2021-06-23T18:13:28.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2021-06-23 21:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='de_CH'
;

-- 2021-06-23T18:13:28.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'de_CH') 
;

-- 2021-06-23T18:13:33.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2021-06-23 21:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='de_DE'
;

-- 2021-06-23T18:13:33.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'de_DE') 
;

-- 2021-06-23T18:13:33.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541357,'de_DE') 
;

-- 2021-06-23T18:13:33.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_SalesRep_ID', Name='Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=541357
;

-- 2021-06-23T18:13:33.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_SalesRep_ID', Name='Vertriebspartner', Description=NULL, Help=NULL, AD_Element_ID=541357 WHERE UPPER(ColumnName)='C_BPARTNER_SALESREP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-23T18:13:33.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_SalesRep_ID', Name='Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=541357 AND IsCentrallyMaintained='Y'
;

-- 2021-06-23T18:13:33.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541357) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541357)
;

-- 2021-06-23T18:13:33.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertriebspartner', Name='Vertriebspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541357)
;

-- 2021-06-23T18:13:33.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541357
;

-- 2021-06-23T18:13:33.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 541357
;

-- 2021-06-23T18:13:33.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541357
;

-- 2021-06-23T18:13:44.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2021-06-23 21:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='en_GB'
;

-- 2021-06-23T18:13:44.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'en_GB') 
;

-- 2021-06-23T18:13:47.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2021-06-23 21:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='it_CH'
;

-- 2021-06-23T18:13:47.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'it_CH') 
;

-- 2021-06-23T18:13:54.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2021-06-23 21:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='fr_CH'
;

-- 2021-06-23T18:13:54.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'fr_CH') 
;

-- 2021-06-23T18:20:42.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=21,Updated=TO_TIMESTAMP('2021-06-23 21:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563312
;

-- 2021-06-23T18:20:46.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-06-23 21:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561855
;

-- 2021-06-23T18:21:54.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-06-23 21:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563312
;

-- 2021-06-23T18:22:00.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=21,Updated=TO_TIMESTAMP('2021-06-23 21:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561855
;

-- 2021-06-23T18:22:04.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-06-23 21:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563312
;

-- 2021-06-23T18:22:34.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-06-23 21:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561855
;

-- 2021-06-23T18:22:40.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=21,Updated=TO_TIMESTAMP('2021-06-23 21:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566552
;

-- 2021-06-23T18:22:42.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-06-23 21:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561855
;

-- 2021-06-23T18:22:46.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-06-23 21:22:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566552
;

-- 2021-06-23T18:23:20.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,649754,0,541943,586816,542899,'F',TO_TIMESTAMP('2021-06-23 21:23:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Provisionsgeber',25,0,0,TO_TIMESTAMP('2021-06-23 21:23:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-23T18:28:11.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Sales commission',Updated=TO_TIMESTAMP('2021-06-23 21:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542010
;

-- 2021-06-23T18:28:28.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Verkaufsprovision',Updated=TO_TIMESTAMP('2021-06-23 21:28:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542010
;

-- 2021-06-23T18:28:39.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Sales commission',Updated=TO_TIMESTAMP('2021-06-23 21:28:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542010
;

-- 2021-06-23T18:28:45.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Verkaufsprovision',Updated=TO_TIMESTAMP('2021-06-23 21:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542010
;

-- 2021-06-23T18:28:49.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Verkaufsprovision',Updated=TO_TIMESTAMP('2021-06-23 21:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542010
;

-- 2021-06-23T18:30:28.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542680,540271,TO_TIMESTAMP('2021-06-23 21:30:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Brokerage commission',TO_TIMESTAMP('2021-06-23 21:30:28','YYYY-MM-DD HH24:MI:SS'),100,'BrokerCommission','Brokerage commission')
;

-- 2021-06-23T18:30:28.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542680 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-23T18:30:49.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Externe Geschäftspartner zahlen Provisionen für vermittelte Aufträge.', Name='Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-06-23 21:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542680
;

-- 2021-06-23T18:31:05.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Externe Geschäftspartner zahlen Provisionen für vermittelte Aufträge.', Name='Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-06-23 21:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542680
;

-- 2021-06-23T18:31:14.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Other busisness partners pay commissions for mediated orders',Updated=TO_TIMESTAMP('2021-06-23 21:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542680
;

-- 2021-06-23T18:31:25.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Externe Geschäftspartner zahlen Provisionen für vermittelte Aufträge.', Name='Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-06-23 21:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542680
;

-- 2021-06-24T07:24:38.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='MediatedOrder',Updated=TO_TIMESTAMP('2021-06-24 10:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542678
;

-- 2021-06-24T07:26:18.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@CommissionTrigger_Type@!=''MediatedOrder''',Updated=TO_TIMESTAMP('2021-06-24 10:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598488
;

-- 2021-06-24T07:31:24.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName=NULL,Updated=TO_TIMESTAMP('2021-06-24 10:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579388
;

-- 2021-06-24T07:31:24.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Provisionsgeber', Description=NULL, Help=NULL WHERE AD_Element_ID=579388
;

-- 2021-06-24T07:31:24.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Provisionsgeber', Description=NULL, Help=NULL WHERE AD_Element_ID=579388 AND IsCentrallyMaintained='Y'
;

-- 2021-06-24T07:36:55.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_InvoiceLine_ID/0@!0 & @CommissionTrigger_Type@!=''MediatedOrder''',Updated=TO_TIMESTAMP('2021-06-24 10:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598487
;

-- 2021-06-24T07:37:14.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_Invoice_Candidate_ID/0@!0 & @CommissionTrigger_Type@!=''MediatedOrder''',Updated=TO_TIMESTAMP('2021-06-24 10:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586122
;

-- 2021-06-24T08:57:35.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Hierachy-Level of the respective record. The customer''s direct sales rep has level 0. The higher in the hierachy a participating sales rep is, the higher is their level.', Name='Level', PrintName='Level',Updated=TO_TIMESTAMP('2021-06-24 11:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540751 AND AD_Language='en_US'
;

-- 2021-06-24T08:57:35.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540751,'en_US') 
;

-- 2021-06-24T08:57:50.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Name='Ebene', PrintName='Ebene',Updated=TO_TIMESTAMP('2021-06-24 11:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540751 AND AD_Language='de_DE'
;

-- 2021-06-24T08:57:50.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540751,'de_DE') 
;

-- 2021-06-24T08:57:50.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(540751,'de_DE') 
;

-- 2021-06-24T08:57:50.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LevelHierarchy', Name='Ebene', Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=540751
;

-- 2021-06-24T08:57:50.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LevelHierarchy', Name='Ebene', Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Help=NULL, AD_Element_ID=540751 WHERE UPPER(ColumnName)='LEVELHIERARCHY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-24T08:57:50.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LevelHierarchy', Name='Ebene', Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=540751 AND IsCentrallyMaintained='Y'
;

-- 2021-06-24T08:57:50.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ebene', Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540751) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 540751)
;

-- 2021-06-24T08:57:50.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ebene', Name='Ebene' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540751)
;

-- 2021-06-24T08:57:50.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ebene', Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 540751
;

-- 2021-06-24T08:57:50.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ebene', Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID = 540751
;

-- 2021-06-24T08:57:50.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ebene', Description = 'Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 540751
;

-- 2021-06-24T08:58:02.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Name='Ebene', PrintName='Ebene',Updated=TO_TIMESTAMP('2021-06-24 11:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540751 AND AD_Language='nl_NL'
;

-- 2021-06-24T08:58:02.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540751,'nl_NL') 
;

-- 2021-06-24T08:58:16.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Hierarchie-Ebene des Buchauszug-Datensatzes. Der direkte Vertriebspartner eines Kunden hat die Ebene 0. Je höher in der Hierarchie ein indirekt partizipierender Vertriebspartner steht, desto höher ist die Ebene des jeweiligen Datensatzes', Name='Ebene', PrintName='Ebene',Updated=TO_TIMESTAMP('2021-06-24 11:58:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540751 AND AD_Language='de_CH'
;

-- 2021-06-24T08:58:16.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540751,'de_CH') 
;

-- 2021-06-24T08:58:31.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=540751 AND AD_Language='fr_CH'
;

-- 2021-06-24T08:58:34.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=540751 AND AD_Language='it_CH'
;

-- 2021-06-24T08:58:36.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=540751 AND AD_Language='en_GB'
;

-- 2021-06-24T09:12:51.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541018,TO_TIMESTAMP('2021-06-24 12:12:51','YYYY-MM-DD HH24:MI:SS'),100,'ARI',1,'de.metas.contracts.commission',1000003,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Brokerage commission','Brokerage commission',TO_TIMESTAMP('2021-06-24 12:12:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-24T09:12:51.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541018 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2021-06-24T09:12:51.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541018 AND rol.IsManual='N')
;

-- 2021-06-24T09:22:09.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='RD',Updated=TO_TIMESTAMP('2021-06-24 12:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541018
;

-- 2021-06-24T09:22:30.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2021-06-24 12:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541018
;

-- 2021-06-24T09:14:12.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542681,148,TO_TIMESTAMP('2021-06-24 12:14:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Rechnung Debitorenkonten',TO_TIMESTAMP('2021-06-24 12:14:11','YYYY-MM-DD HH24:MI:SS'),100,'RD','Rechnung Debitorenkonten')
;

-- 2021-06-24T09:14:12.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542681 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-24T09:16:57.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2021-06-24 12:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2021-06-24T09:19:54.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=542681
;

-- 2021-06-24T09:19:54.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=542681
;

-- 2021-06-24T09:20:35.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542682,148,TO_TIMESTAMP('2021-06-24 12:20:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Rechnung Debitorenkonten',TO_TIMESTAMP('2021-06-24 12:20:34','YYYY-MM-DD HH24:MI:SS'),100,'RD','RD')
;

-- 2021-06-24T09:20:35.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542682 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-06-24T11:19:34.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,649745,0,541942,586837,543528,'F',TO_TIMESTAMP('2021-06-24 14:19:33','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','Y','N','N','N',0,'Auftragsposition',60,0,0,TO_TIMESTAMP('2021-06-24 14:19:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-24T11:19:47.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2021-06-24 14:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586837
;

-- 2021-06-24T11:34:44.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=586815
;

-- 2021-06-24T11:35:30.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2021-06-24 14:35:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586816
;

-- 2021-06-24T12:24:42.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Other busisness partners pay commissions for mediated orders',Updated=TO_TIMESTAMP('2021-06-24 15:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542680
;

-- 2021-06-24T17:49:38.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-06-24 20:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541018
;
