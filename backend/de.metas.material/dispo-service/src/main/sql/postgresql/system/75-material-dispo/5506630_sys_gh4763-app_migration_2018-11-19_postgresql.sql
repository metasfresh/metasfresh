-- 2018-11-19T09:19:22.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540707,541764,TO_TIMESTAMP('2018-11-19 09:19:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Inventar-Erhöhung',TO_TIMESTAMP('2018-11-19 09:19:22','YYYY-MM-DD HH24:MI:SS'),100,'INVENTORY_UP','INVENTORY_UP')
;

-- 2018-11-19T09:19:22.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541764 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-11-19T09:19:51.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540707,541765,TO_TIMESTAMP('2018-11-19 09:19:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Inventar-Verminderung',TO_TIMESTAMP('2018-11-19 09:19:51','YYYY-MM-DD HH24:MI:SS'),100,'INVENTORY_DOWN','INVENTORY_DOWN')
;

-- 2018-11-19T09:19:51.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541765 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-11-19T09:22:26.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2018-11-19 09:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540905
;

-- 2018-11-19T09:22:33.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2018-11-19 09:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540986
;

-- 2018-11-19T09:24:48.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540709,541766,TO_TIMESTAMP('2018-11-19 09:24:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Bestandsänderung',TO_TIMESTAMP('2018-11-19 09:24:48','YYYY-MM-DD HH24:MI:SS'),100,'INVENTORY','INVENTORY')
;

-- 2018-11-19T09:24:48.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541766 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
-- 2018-11-19T10:49:58.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='case    when md_candidate_type in (''DEMAND'', ''UNRELATED_DECREASE'', ''INVENTORY_DOWN'') then (select sum(qty) from md_candidate s where s. md_candidate_parent_id=md_candidate.md_candidate_id)    when md_candidate_type in (''SUPPLY'', ''UNRELATED_INCREASE'', ''INVENTORY_UP'') then (select qty  from md_candidate s where s.md_candidate_id = md_candidate.md_candidate_parent_id)    else /* md_candidate_type = STOCK */ null end',Updated=TO_TIMESTAMP('2018-11-19 10:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559457
;

-- 2018-11-19T10:50:22.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='case   when md_candidate_type in (''DEMAND'', ''UNRELATED_DECREASE'', ''INVENTORY_DOWN'') then -qty   when md_candidate_type in (''SUPPLY'', ''UNRELATED_INCREASE'', ''INVENTORY_UP'') then qty   else /* md_candidate_type = STOCK */ null end',Updated=TO_TIMESTAMP('2018-11-19 10:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559456
;



-- 2018-11-20T10:22:41.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575885,0,'AD_PInstance_ResetStock_ID',TO_TIMESTAMP('2018-11-20 10:22:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Prozesslauf "Lagerbestand zurücksetzen"','Prozesslauf "Lagerbestand zurücksetzen"',TO_TIMESTAMP('2018-11-20 10:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-20T10:22:41.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575885 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-11-20T10:22:44.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-20 10:22:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575885 AND AD_Language='de_CH'
;

-- 2018-11-20T10:22:44.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575885,'de_CH') 
;

-- 2018-11-20T10:22:48.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-20 10:22:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575885 AND AD_Language='de_DE'
;

-- 2018-11-20T10:22:48.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575885,'de_DE') 
;

-- 2018-11-20T10:22:48.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575885,'de_DE') 
;

-- 2018-11-20T10:26:55.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-20 10:26:55','YYYY-MM-DD HH24:MI:SS'),Name='Prozesslauf "Lagerbestand zurücksetzen' WHERE AD_Element_ID=575885 AND AD_Language='de_DE'
;

-- 2018-11-20T10:26:55.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575885,'de_DE') 
;

-- 2018-11-20T10:26:55.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575885,'de_DE') 
;

-- 2018-11-20T10:26:55.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Prozesslauf "Lagerbestand zurücksetzen', Description=NULL, Help=NULL WHERE AD_Element_ID=575885
;

-- 2018-11-20T10:26:55.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Prozesslauf "Lagerbestand zurücksetzen', Description=NULL, Help=NULL WHERE AD_Element_ID=575885 AND IsCentrallyMaintained='Y'
;

-- 2018-11-20T10:26:55.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prozesslauf "Lagerbestand zurücksetzen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575885) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575885)
;

-- 2018-11-20T10:26:55.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Prozesslauf "Lagerbestand zurücksetzen"', Name='Prozesslauf "Lagerbestand zurücksetzen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575885)
;

-- 2018-11-20T10:26:55.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Prozesslauf "Lagerbestand zurücksetzen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575885
;

-- 2018-11-20T10:26:55.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Prozesslauf "Lagerbestand zurücksetzen', Description=NULL, Help=NULL WHERE AD_Element_ID = 575885
;

-- 2018-11-20T10:26:55.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Prozesslauf "Lagerbestand zurücksetzen', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575885
;

-- 2018-11-20T10:26:58.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-20 10:26:58','YYYY-MM-DD HH24:MI:SS'),Name='Prozesslauf "Lagerbestand zurücksetzen"' WHERE AD_Element_ID=575885 AND AD_Language='de_DE'
;

-- 2018-11-20T10:26:58.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575885,'de_DE') 
;

-- 2018-11-20T10:26:58.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575885,'de_DE') 
;

-- 2018-11-20T10:26:58.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Prozesslauf "Lagerbestand zurücksetzen"', Description=NULL, Help=NULL WHERE AD_Element_ID=575885
;

-- 2018-11-20T10:26:58.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Prozesslauf "Lagerbestand zurücksetzen"', Description=NULL, Help=NULL WHERE AD_Element_ID=575885 AND IsCentrallyMaintained='Y'
;

-- 2018-11-20T10:26:58.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prozesslauf "Lagerbestand zurücksetzen"', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575885) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575885)
;

-- 2018-11-20T10:26:58.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Prozesslauf "Lagerbestand zurücksetzen"', Name='Prozesslauf "Lagerbestand zurücksetzen"' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575885)
;

-- 2018-11-20T10:26:58.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Prozesslauf "Lagerbestand zurücksetzen"', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575885
;

-- 2018-11-20T10:26:58.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Prozesslauf "Lagerbestand zurücksetzen"', Description=NULL, Help=NULL WHERE AD_Element_ID = 575885
;

-- 2018-11-20T10:26:58.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Prozesslauf "Lagerbestand zurücksetzen"', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575885
;

-- 2018-11-20T10:27:32.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-20 10:27:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='"Reset stock" process instance ',PrintName='"Reset stock" process instance' WHERE AD_Element_ID=575885 AND AD_Language='en_US'
;

-- 2018-11-20T10:27:32.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575885,'en_US') 
;

-- 2018-11-20T10:27:47.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563603,575885,0,30,540850,'AD_PInstance_ResetStock_ID',TO_TIMESTAMP('2018-11-20 10:27:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Prozesslauf "Lagerbestand zurücksetzen"',0,0,TO_TIMESTAMP('2018-11-20 10:27:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-11-20T10:27:47.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563603 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-11-20T10:30:15.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540414,TO_TIMESTAMP('2018-11-20 10:30:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_PInstance_ID','S',TO_TIMESTAMP('2018-11-20 10:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-20T10:30:25.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540414
;

-- 2018-11-20T10:30:38.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540169, AD_Val_Rule_ID=540270, DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2018-11-20 10:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563603
;

-- 2018-11-20T10:31:13.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='NoForeignKey=''Y'' to allow removal of old AD_PInstances',Updated=TO_TIMESTAMP('2018-11-20 10:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563603
;

-- 2018-11-20T10:31:28.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Transaction_Detail','ALTER TABLE public.MD_Candidate_Transaction_Detail ADD COLUMN AD_PInstance_ResetStock_ID NUMERIC(10)')
;

-- 2018-11-20T10:32:54.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,563604,543736,0,30,540850,'MD_Stock_ID',TO_TIMESTAMP('2018-11-20 10:32:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bestand',0,0,'NoForeignKey=''Y'' to allow removal/resets of MD_Stock records.
This column is inteneded to be more of a short-term info.',TO_TIMESTAMP('2018-11-20 10:32:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-11-20T10:32:54.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563604 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-11-20T10:33:08.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='NoForeignKey=''Y'' to allow removal of old AD_PInstances.
This column is inteneded to be more of a short-term info.',Updated=TO_TIMESTAMP('2018-11-20 10:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563603
;

-- 2018-11-20T10:33:15.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2018-11-20 10:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563603
;

-- 2018-11-20T10:33:36.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Transaction_Detail','ALTER TABLE public.MD_Candidate_Transaction_Detail ADD COLUMN MD_Stock_ID NUMERIC(10)')
;

-- 2018-11-20T12:34:32.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Candidate_Transaction_Detail',Updated=TO_TIMESTAMP('2018-11-20 12:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540850
;


-- 2018-11-20T12:35:03.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=543680, Name='Bestandsänderung',Updated=TO_TIMESTAMP('2018-11-20 12:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540885
;

-- 2018-11-20T12:35:03.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Org_ID,AD_Tab_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,543680,625023,0,540885,540334,TO_TIMESTAMP('2018-11-20 12:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-11-20 12:35:03','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- 2018-11-20T12:35:03.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(543680) 
;

-- 2018-11-20T12:36:29.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Candidate_Dist_Detail',Updated=TO_TIMESTAMP('2018-11-20 12:36:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540821
;

-- 2018-11-20T12:36:39.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Candidate_Prod_Detail',Updated=TO_TIMESTAMP('2018-11-20 12:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540810
;

-- 2018-11-20T12:36:59.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Candidate_Purchase_Detail',Updated=TO_TIMESTAMP('2018-11-20 12:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540983
;

-- 2018-11-20T12:37:07.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Candidate_Demand_Detail',Updated=TO_TIMESTAMP('2018-11-20 12:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540815
;

-- 2018-11-20T12:37:15.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='MD_Candidate',Updated=TO_TIMESTAMP('2018-11-20 12:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540808
;

