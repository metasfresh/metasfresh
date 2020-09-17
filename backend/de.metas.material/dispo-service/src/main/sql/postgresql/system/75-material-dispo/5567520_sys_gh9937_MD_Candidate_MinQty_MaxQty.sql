
UPDATE MD_Candidate SET Replenish_MinQty=0, Replenish_MaxQty=0;

-- 2020-09-15T07:13:46.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578107,0,'Replenish_MinQty',TO_TIMESTAMP('2020-09-15 09:13:46','YYYY-MM-DD HH24:MI:SS'),100,'Minimaler ATP. Bei Unterschreitung veranlasst die Meterial-Dispo eine Aufstockung auf die Maximalmenge.','D','Y','Mindestmenge','Mindestmenge',TO_TIMESTAMP('2020-09-15 09:13:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-15T07:13:47.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578107 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-15T07:13:51.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-15 09:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578107 AND AD_Language='de_CH'
;

-- 2020-09-15T07:13:51.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578107,'de_CH') 
;

-- 2020-09-15T07:13:54.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-15 09:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578107 AND AD_Language='de_DE'
;

-- 2020-09-15T07:13:54.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578107,'de_DE') 
;

-- 2020-09-15T07:13:54.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578107,'de_DE') 
;

-- 2020-09-15T07:14:30.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Minimal ATP', PrintName='Minimal ATP',Updated=TO_TIMESTAMP('2020-09-15 09:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578107 AND AD_Language='en_US'
;

-- 2020-09-15T07:14:30.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578107,'en_US') 
;

-- 2020-09-15T07:14:35.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.',Updated=TO_TIMESTAMP('2020-09-15 09:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578107 AND AD_Language='de_DE'
;

-- 2020-09-15T07:14:35.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578107,'de_DE') 
;

-- 2020-09-15T07:14:35.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578107,'de_DE') 
;

-- 2020-09-15T07:14:35.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Replenish_MinQty', Name='Mindestmenge', Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', Help=NULL WHERE AD_Element_ID=578107
;

-- 2020-09-15T07:14:35.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Replenish_MinQty', Name='Mindestmenge', Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', Help=NULL, AD_Element_ID=578107 WHERE UPPER(ColumnName)='REPLENISH_MINQTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-15T07:14:35.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Replenish_MinQty', Name='Mindestmenge', Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', Help=NULL WHERE AD_Element_ID=578107 AND IsCentrallyMaintained='Y'
;

-- 2020-09-15T07:14:35.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindestmenge', Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578107) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578107)
;

-- 2020-09-15T07:14:35.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mindestmenge', Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578107
;

-- 2020-09-15T07:14:35.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mindestmenge', Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', Help=NULL WHERE AD_Element_ID = 578107
;

-- 2020-09-15T07:14:35.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Mindestmenge', Description = 'Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578107
;

-- 2020-09-15T07:14:41.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.',Updated=TO_TIMESTAMP('2020-09-15 09:14:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578107 AND AD_Language='de_CH'
;

-- 2020-09-15T07:14:41.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578107,'de_CH') 
;

-- 2020-09-15T07:15:41.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578108,0,'Replenish_MaxQty',TO_TIMESTAMP('2020-09-15 09:15:41','YYYY-MM-DD HH24:MI:SS'),100,'Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.','de.metas.material.dispo','Y','Höchstmenge','Höchstmenge',TO_TIMESTAMP('2020-09-15 09:15:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-15T07:15:41.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578108 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-15T07:15:51.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.material.dispo',Updated=TO_TIMESTAMP('2020-09-15 09:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578107
;

-- 2020-09-15T07:16:18.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571508,578107,0,29,540808,'Replenish_MinQty',TO_TIMESTAMP('2020-09-15 09:16:18','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Mindestmenge',0,0,TO_TIMESTAMP('2020-09-15 09:16:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-15T07:16:18.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=571508 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-15T07:16:18.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578107) 
;

-- 2020-09-15T07:19:24.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571509,578108,0,29,540808,'Replenish_MaxQty',TO_TIMESTAMP('2020-09-15 09:19:24','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Höchstmenge',0,0,TO_TIMESTAMP('2020-09-15 09:19:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-15T07:19:24.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=571509 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-15T07:19:24.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578108) 
;
-- 2020-09-15T07:22:09.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571508,617170,0,540802,TO_TIMESTAMP('2020-09-15 09:22:09','YYYY-MM-DD HH24:MI:SS'),100,'Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.',10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Mindestmenge',TO_TIMESTAMP('2020-09-15 09:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-15T07:22:09.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=617170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-15T07:22:09.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578107) 
;

-- 2020-09-15T07:22:09.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617170
;

-- 2020-09-15T07:22:09.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(617170)
;

-- 2020-09-15T07:22:09.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571509,617171,0,540802,TO_TIMESTAMP('2020-09-15 09:22:09','YYYY-MM-DD HH24:MI:SS'),100,'Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.',10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Höchstmenge',TO_TIMESTAMP('2020-09-15 09:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-15T07:22:09.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=617171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-15T07:22:09.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578108) 
;

-- 2020-09-15T07:22:09.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617171
;

-- 2020-09-15T07:22:09.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(617171)
;

-- 2020-09-15T07:23:53.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,617170,0,540802,540298,571288,'F',TO_TIMESTAMP('2020-09-15 09:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Minimaler ATP. Bei Unterschreitung veranlasst die Material-Dispo eine Aufstockung auf die Maximalmenge.','Y','Y','N','Y','N','N','N',0,'Replenish_MinQty',80,0,0,TO_TIMESTAMP('2020-09-15 09:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-15T07:24:11.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,617171,0,540802,540298,571289,'F',TO_TIMESTAMP('2020-09-15 09:24:11','YYYY-MM-DD HH24:MI:SS'),100,'Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.','Y','Y','N','Y','N','N','N',0,'Replenish_MaxQty',90,0,0,TO_TIMESTAMP('2020-09-15 09:24:11','YYYY-MM-DD HH24:MI:SS'),100)
;

delete from ad_modelvalidator where modelvalidationclass='de.metas.material.planning.interceptor.Main';
delete from ad_modelvalidator where modelvalidationclass='de.metas.material.interceptor.Main';
