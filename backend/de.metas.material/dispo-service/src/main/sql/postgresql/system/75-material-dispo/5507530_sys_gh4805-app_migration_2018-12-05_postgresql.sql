-- 2018-12-05T14:30:15.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564580,0,540802,540299,554433,'F',TO_TIMESTAMP('2018-12-05 14:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_Customer_ID',70,0,0,TO_TIMESTAMP('2018-12-05 14:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T14:30:36.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554433
;

-- 2018-12-05T14:30:36.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551162
;

-- 2018-12-05T14:30:36.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551163
;

-- 2018-12-05T14:30:36.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543332
;

-- 2018-12-05T14:30:36.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549316
;

-- 2018-12-05T14:30:36.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549318
;

-- 2018-12-05T14:30:36.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549317
;

-- 2018-12-05T14:30:36.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2018-12-05 14:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543333
;

-- 2018-12-05T15:03:38.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575896,0,'TransactionDate',TO_TIMESTAMP('2018-12-05 15:03:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Transaktionsdatum','Transaktionsdatum',TO_TIMESTAMP('2018-12-05 15:03:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T15:03:38.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575896 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-05T15:03:43.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 15:03:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575896 AND AD_Language='de_CH'
;

-- 2018-12-05T15:03:43.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575896,'de_CH') 
;

-- 2018-12-05T15:03:45.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 15:03:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575896 AND AD_Language='de_DE'
;

-- 2018-12-05T15:03:45.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575896,'de_DE') 
;

-- 2018-12-05T15:03:45.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575896,'de_DE') 
;

-- 2018-12-05T15:03:55.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 15:03:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Transaction date',PrintName='Transaction date' WHERE AD_Element_ID=575896 AND AD_Language='en_US'
;

-- 2018-12-05T15:03:55.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575896,'en_US') 
;

-- 2018-12-05T15:04:20.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563657,575896,0,16,540850,'TransactionDate',TO_TIMESTAMP('2018-12-05 15:04:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Transaktionsdatum',0,0,TO_TIMESTAMP('2018-12-05 15:04:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-12-05T15:04:20.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563657 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-12-05T15:04:41.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Transaction_Detail','ALTER TABLE public.MD_Candidate_Transaction_Detail ADD COLUMN TransactionDate TIMESTAMP WITH TIME ZONE')
;

-- 2018-12-05T15:09:06.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563657,570721,0,540885,TO_TIMESTAMP('2018-12-05 15:09:06','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Transaktionsdatum',TO_TIMESTAMP('2018-12-05 15:09:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T15:09:06.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-12-05T15:09:31.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570721,0,540885,541217,554434,'F',TO_TIMESTAMP('2018-12-05 15:09:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'TransactionDate',70,0,0,TO_TIMESTAMP('2018-12-05 15:09:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T15:09:49.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-12-05 15:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554434
;

-- 2018-12-05T15:09:49.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-12-05 15:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549143
;

-- 2018-12-05T15:09:49.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-12-05 15:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549144
;

-- 2018-12-05T15:09:49.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-12-05 15:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549135
;

-- 2018-12-05T16:34:40.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Qty_Planned_Display',Updated=TO_TIMESTAMP('2018-12-05 16:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543908
;

-- 2018-12-05T16:34:40.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Qty_Planned_Display', Name='Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=543908
;

-- 2018-12-05T16:34:40.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Qty_Planned_Display', Name='Menge', Description=NULL, Help=NULL, AD_Element_ID=543908 WHERE UPPER(ColumnName)='QTY_PLANNED_DISPLAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-05T16:34:40.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Qty_Planned_Display', Name='Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=543908 AND IsCentrallyMaintained='Y'
;

-- 2018-12-05T16:37:33.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575898,0,'QtyFullFilled',TO_TIMESTAMP('2018-12-05 16:37:32','YYYY-MM-DD HH24:MI:SS'),100,'Summe der bereits eingetretenden Materialbewegungen','de.metas.material.dispo','Y','Menge-Erl','Menge-Erl',TO_TIMESTAMP('2018-12-05 16:37:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:37:33.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575898 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-05T16:37:36.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 16:37:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575898 AND AD_Language='de_CH'
;

-- 2018-12-05T16:37:36.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575898,'de_CH') 
;

-- 2018-12-05T16:37:38.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 16:37:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575898 AND AD_Language='de_DE'
;

-- 2018-12-05T16:37:38.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575898,'de_DE') 
;

-- 2018-12-05T16:37:38.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575898,'de_DE') 
;

-- 2018-12-05T16:38:02.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 16:38:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Summed quantity from actual material transactions' WHERE AD_Element_ID=575898 AND AD_Language='en_US'
;

-- 2018-12-05T16:38:02.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575898,'en_US') 
;

-- 2018-12-05T16:38:18.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563659,575898,0,29,540808,'QtyFullFilled',TO_TIMESTAMP('2018-12-05 16:38:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Summe der bereits eingetretenden Materialbewegungen','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Menge-Erl',0,0,TO_TIMESTAMP('2018-12-05 16:38:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-12-05T16:38:18.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563659 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-12-05T16:38:20.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate','ALTER TABLE public.MD_Candidate ADD COLUMN QtyFullFilled NUMERIC')
;



/*
UPDATE MD_Candidate_Transaction_Detail td SET TransactionDate=t.Created FROM M_Transaction t WHERE t.M_Transaction_ID=td.M_Transaction_ID AND td.TransactionDate IS NULL;

UPDATE MD_Candidate_Transaction_Detail td SET TransactionDate=pi.Created FROM AD_PInstance pi WHERE pi.AD_PInstance_ID=td.AD_PInstance_ResetStock_ID AND td.TransactionDate IS NULL;

UPDATE MD_Candidate c SET QtyFullFilled = (select sum(coalesce(td.MovementQty, 0) from MD_Candidate_Transaction_Detail td where td.MD_Candidate_ID=)
*/-- 2018-12-05T16:45:50.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2018-12-05 16:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541271
;

-- 2018-12-05T16:45:51.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2018-12-05 16:45:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541270
;

-- 2018-12-05T16:45:51.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2018-12-05 16:45:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541267
;

-- 2018-12-05T16:45:53.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2018-12-05 16:45:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541268
;

-- 2018-12-05T16:46:36.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540715,541770,TO_TIMESTAMP('2018-12-05 16:46:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Geplant',TO_TIMESTAMP('2018-12-05 16:46:36','YYYY-MM-DD HH24:MI:SS'),100,'planned','planned')
;

-- 2018-12-05T16:46:36.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541770 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-12-05T16:47:07.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540715,541771,TO_TIMESTAMP('2018-12-05 16:47:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Verarbeitet',TO_TIMESTAMP('2018-12-05 16:47:07','YYYY-MM-DD HH24:MI:SS'),100,'processed','processed')
;

-- 2018-12-05T16:47:07.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541771 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-12-05T16:47:21.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='planned',Updated=TO_TIMESTAMP('2018-12-05 16:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556512
;

-- 2018-12-05T16:50:08.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563659,570722,0,540802,TO_TIMESTAMP('2018-12-05 16:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Summe der bereits eingetretenden Materialbewegungen',10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Menge-Erl',TO_TIMESTAMP('2018-12-05 16:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:50:08.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-12-05T16:50:35.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyFullfilled',Updated=TO_TIMESTAMP('2018-12-05 16:50:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575898
;

-- 2018-12-05T16:50:35.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyFullfilled', Name='Menge-Erl', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID=575898
;

-- 2018-12-05T16:50:35.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyFullfilled', Name='Menge-Erl', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL, AD_Element_ID=575898 WHERE UPPER(ColumnName)='QTYFULLFILLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-05T16:50:35.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyFullfilled', Name='Menge-Erl', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID=575898 AND IsCentrallyMaintained='Y'
;

-- 2018-12-05T16:51:03.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyFulfilled',Updated=TO_TIMESTAMP('2018-12-05 16:51:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575898
;

-- 2018-12-05T16:51:03.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyFulfilled', Name='Menge-Erl', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID=575898
;

-- 2018-12-05T16:51:03.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyFulfilled', Name='Menge-Erl', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL, AD_Element_ID=575898 WHERE UPPER(ColumnName)='QTYFULFILLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-05T16:51:03.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyFulfilled', Name='Menge-Erl', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID=575898 AND IsCentrallyMaintained='Y'
;

-- 2018-12-05T16:53:09.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=622084
;

-- 2018-12-05T16:53:09.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625032,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:09.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625032
;

-- 2018-12-05T16:53:10.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625033,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:12.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625033
;

-- 2018-12-05T16:53:12.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625034,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:13.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625034
;

-- 2018-12-05T16:53:13.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625035,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:15.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625035
;

-- 2018-12-05T16:53:15.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625036,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:15.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625036
;

-- 2018-12-05T16:53:15.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625037,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:17.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625037
;

-- 2018-12-05T16:53:17.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625038,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:18.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625038
;

-- 2018-12-05T16:53:18.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625039,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:19.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625039
;

-- 2018-12-05T16:53:19.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625040,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:20.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625040
;

-- 2018-12-05T16:53:20.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625041,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:21.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625041
;

-- 2018-12-05T16:53:21.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625042,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:22.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625042
;

-- 2018-12-05T16:53:22.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625043,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:23.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625043
;

-- 2018-12-05T16:53:23.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625044,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:24.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625044
;

-- 2018-12-05T16:53:24.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625045,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:29.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625045
;

-- 2018-12-05T16:53:29.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625046,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:30.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625046
;

-- 2018-12-05T16:53:30.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625047,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:53:57.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625047
;

-- 2018-12-05T16:53:57.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543908,625048,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:53:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:53:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:54:04.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=1000295, Name='Geplante Menge',Updated=TO_TIMESTAMP('2018-12-05 16:54:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563010
;

-- 2018-12-05T16:54:04.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625048
;

-- 2018-12-05T16:54:04.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1000295,625049,563010,0,540334,TO_TIMESTAMP('2018-12-05 16:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-05 16:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:54:04.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000295) 
;

-- 2018-12-05T16:55:08.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 16:55:08','YYYY-MM-DD HH24:MI:SS'),Name='Erledigte Menge',PrintName='Erledigte Menge' WHERE AD_Element_ID=575898 AND AD_Language='de_CH'
;

-- 2018-12-05T16:55:08.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575898,'de_CH') 
;

-- 2018-12-05T16:55:12.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 16:55:12','YYYY-MM-DD HH24:MI:SS'),Name='Erledigte Menge',PrintName='Erledigte Menge' WHERE AD_Element_ID=575898 AND AD_Language='de_DE'
;

-- 2018-12-05T16:55:12.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575898,'de_DE') 
;

-- 2018-12-05T16:55:12.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575898,'de_DE') 
;

-- 2018-12-05T16:55:12.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyFulfilled', Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID=575898
;

-- 2018-12-05T16:55:12.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyFulfilled', Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL, AD_Element_ID=575898 WHERE UPPER(ColumnName)='QTYFULFILLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-05T16:55:13.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyFulfilled', Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID=575898 AND IsCentrallyMaintained='Y'
;

-- 2018-12-05T16:55:13.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575898) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575898)
;

-- 2018-12-05T16:55:13.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erledigte Menge', Name='Erledigte Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575898)
;

-- 2018-12-05T16:55:13.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575898
;

-- 2018-12-05T16:55:13.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', Help=NULL WHERE AD_Element_ID = 575898
;

-- 2018-12-05T16:55:13.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Erledigte Menge', Description='Summe der bereits eingetretenden Materialbewegungen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575898
;

-- 2018-12-05T16:55:33.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-05 16:55:33','YYYY-MM-DD HH24:MI:SS'),Name='Fulfilled quantity',PrintName='Fulfilled quantity' WHERE AD_Element_ID=575898 AND AD_Language='en_US'
;

-- 2018-12-05T16:55:33.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575898,'en_US') 
;

-- 2018-12-05T16:56:25.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Qty_Planned_Display',Updated=TO_TIMESTAMP('2018-12-05 16:56:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551162
;

-- 2018-12-05T16:56:46.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570722,0,540802,540301,554435,'F',TO_TIMESTAMP('2018-12-05 16:56:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyFulfilled',45,0,0,TO_TIMESTAMP('2018-12-05 16:56:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:57:33.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558150,0,540802,540301,554436,'F',TO_TIMESTAMP('2018-12-05 16:57:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'MD_Candidate_Status',47,0,0,TO_TIMESTAMP('2018-12-05 16:57:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-05T16:58:25.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554435
;

-- 2018-12-05T16:58:25.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554436
;

-- 2018-12-05T16:58:25.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551163
;

-- 2018-12-05T16:58:25.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543332
;

-- 2018-12-05T16:58:25.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549316
;

-- 2018-12-05T16:58:25.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549318
;

-- 2018-12-05T16:58:25.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549317
;

-- 2018-12-05T16:58:25.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2018-12-05 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543333
;

-- 2018-12-05T16:58:57.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2018-12-05 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556512
;

-- 2018-12-05T16:58:57.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2018-12-05 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556482
;

-- 2018-12-05T16:58:57.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2018-12-05 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557892
;

-- 2018-12-05T16:58:57.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2018-12-05 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557893
;

-- 2018-12-05T16:58:57.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2018-12-05 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557894
;

-- 2018-12-05T16:58:57.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2018-12-05 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556467
;

-- 2018-12-05T16:59:38.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556485
;

-- 2018-12-05T16:59:38.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556474
;

-- 2018-12-05T16:59:38.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556477
;

-- 2018-12-05T16:59:38.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558004
;

-- 2018-12-05T16:59:38.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556512
;

-- 2018-12-05T16:59:38.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556482
;

-- 2018-12-05T16:59:38.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557892
;

-- 2018-12-05T16:59:38.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557893
;

-- 2018-12-05T16:59:38.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557894
;

-- 2018-12-05T16:59:38.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2018-12-05 16:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556467
;

