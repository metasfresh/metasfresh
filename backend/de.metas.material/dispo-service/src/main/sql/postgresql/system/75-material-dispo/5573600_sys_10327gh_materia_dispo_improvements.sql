-- 2020-11-27T14:51:12.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-11-27 15:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561296
;

-- 2020-11-27T14:51:50.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561296,0,540980,541356,575634,'F',TO_TIMESTAMP('2020-11-27 15:51:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_EventLog_Entry_ID',5,0,0,TO_TIMESTAMP('2020-11-27 15:51:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T14:52:29.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550159
;

-- 2020-11-27T14:52:29.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550158
;

-- 2020-11-27T14:52:29.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575634
;

-- 2020-11-27T14:52:29.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550165
;

-- 2020-11-27T14:52:29.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550166
;

-- 2020-11-27T14:52:29.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550163
;

-- 2020-11-27T14:52:29.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-11-27 15:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550164
;

-- 2020-11-27T14:54:44.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578584,0,'Processing_By_Handler_Done',TO_TIMESTAMP('2020-11-27 15:54:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Handing beendet','Handing beendet',TO_TIMESTAMP('2020-11-27 15:54:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T14:54:44.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578584 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-27T14:54:48.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 15:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578584 AND AD_Language='de_CH'
;

-- 2020-11-27T14:54:48.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578584,'de_CH') 
;

-- 2020-11-27T14:55:38.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 15:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578584 AND AD_Language='de_DE'
;

-- 2020-11-27T14:55:38.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578584,'de_DE') 
;

-- 2020-11-27T14:55:38.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578584,'de_DE') 
;

-- 2020-11-27T14:55:38.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Processing_By_Handler_Done', Name='Handing beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE AD_Element_ID=578584
;

-- 2020-11-27T14:55:38.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Processing_By_Handler_Done', Name='Handing beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL, AD_Element_ID=578584 WHERE UPPER(ColumnName)='PROCESSING_BY_HANDLER_DONE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-27T14:55:38.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Processing_By_Handler_Done', Name='Handing beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE AD_Element_ID=578584 AND IsCentrallyMaintained='Y'
;

-- 2020-11-27T14:55:38.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Handing beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578584) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578584)
;

-- 2020-11-27T14:55:38.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Handing beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578584
;

-- 2020-11-27T14:55:38.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Handing beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE AD_Element_ID = 578584
;

-- 2020-11-27T14:55:38.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Handing beendet', Description = 'Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578584
;

-- 2020-11-27T14:55:43.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.',Updated=TO_TIMESTAMP('2020-11-27 15:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578584 AND AD_Language='de_CH'
;

-- 2020-11-27T14:55:43.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578584,'de_CH') 
;

-- 2020-11-27T14:55:55.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=578584, Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL, Name='Handing beendet',Updated=TO_TIMESTAMP('2020-11-27 15:55:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561298
;

-- 2020-11-27T14:55:55.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578584) 
;

-- 2020-11-27T14:55:55.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=561298
;

-- 2020-11-27T14:55:55.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(561298)
;

-- 2020-11-27T14:56:16.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beendet', PrintName='Beendet',Updated=TO_TIMESTAMP('2020-11-27 15:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578584 AND AD_Language='de_CH'
;

-- 2020-11-27T14:56:16.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578584,'de_CH') 
;

-- 2020-11-27T14:56:21.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beendet', PrintName='Beendet',Updated=TO_TIMESTAMP('2020-11-27 15:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578584 AND AD_Language='de_DE'
;

-- 2020-11-27T14:56:21.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578584,'de_DE') 
;

-- 2020-11-27T14:56:21.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578584,'de_DE') 
;

-- 2020-11-27T14:56:21.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Processing_By_Handler_Done', Name='Beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE AD_Element_ID=578584
;

-- 2020-11-27T14:56:21.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Processing_By_Handler_Done', Name='Beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL, AD_Element_ID=578584 WHERE UPPER(ColumnName)='PROCESSING_BY_HANDLER_DONE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-27T14:56:21.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Processing_By_Handler_Done', Name='Beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE AD_Element_ID=578584 AND IsCentrallyMaintained='Y'
;

-- 2020-11-27T14:56:21.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578584) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578584)
;

-- 2020-11-27T14:56:21.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beendet', Name='Beendet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578584)
;

-- 2020-11-27T14:56:21.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578584
;

-- 2020-11-27T14:56:21.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beendet', Description='Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', Help=NULL WHERE AD_Element_ID = 578584
;

-- 2020-11-27T14:56:21.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beendet', Description = 'Sagt aus, ob der Event-Handler den Event vollständig behandelt hat.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578584
;

-- 2020-11-27T14:56:28.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Done', PrintName='Done',Updated=TO_TIMESTAMP('2020-11-27 15:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578584 AND AD_Language='en_US'
;

-- 2020-11-27T14:56:28.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578584,'en_US') 
;

-- 2020-11-27T16:10:08.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='case     when md_candidate_type in (''DEMAND'', ''UNEXPECTED_DECREASE'', ''INVENTORY_DOWN'') then (select sum(qty) from md_candidate s where s. md_candidate_parent_id=md_candidate.md_candidate_id)     when md_candidate_type in (''SUPPLY'', ''UNEXPECTED_INCREASE'', ''INVENTORY_UP'') then (select qty  from md_candidate s where s.md_candidate_id = md_candidate.md_candidate_parent_id)     else /* md_candidate_type = STOCK */ null  end',Updated=TO_TIMESTAMP('2020-11-27 17:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559457
;


--------

-- 2020-11-27T18:44:39.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578585,0,'MD_Candidate_RebookedFrom_ID',TO_TIMESTAMP('2020-11-27 19:44:38','YYYY-MM-DD HH24:MI:SS'),100,'Material-Dispo-Datensatz, auf den sich die Material-Transaktion eigentlich bezieht. Wenn die Transaktion nicht abweichede Merkmale oder ein abweichendes Datum hätte, wäre sie diesem Datensatz direkt zugeordnet.','de.metas.material.dispo','Y','Umgebucht von','Umgebucht von',TO_TIMESTAMP('2020-11-27 19:44:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T18:44:39.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578585 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-27T18:44:43.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 19:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='de_CH'
;

-- 2020-11-27T18:44:43.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'de_CH') 
;

-- 2020-11-27T18:44:45.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-27 19:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='de_DE'
;

-- 2020-11-27T18:44:45.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'de_DE') 
;

-- 2020-11-27T18:44:45.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578585,'de_DE') 
;

-- 2020-11-27T18:44:57.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Rebooked from', PrintName='Rebooked from',Updated=TO_TIMESTAMP('2020-11-27 19:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578585 AND AD_Language='en_US'
;

-- 2020-11-27T18:44:58Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578585,'en_US') 
;

-- 2020-11-27T18:45:30.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572243,578585,0,30,540708,540850,'MD_Candidate_RebookedFrom_ID',TO_TIMESTAMP('2020-11-27 19:45:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Material-Dispo-Datensatz, auf den sich die Material-Transaktion eigentlich bezieht. Wenn die Transaktion nicht abweichede Merkmale oder ein abweichendes Datum hätte, wäre sie diesem Datensatz direkt zugeordnet.','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Umgebucht von',0,0,TO_TIMESTAMP('2020-11-27 19:45:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-11-27T18:45:30.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572243 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-27T18:45:30.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578585) 
;

-- 2020-11-27T18:45:36.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Transaction_Detail','ALTER TABLE public.MD_Candidate_Transaction_Detail ADD COLUMN MD_Candidate_RebookedFrom_ID NUMERIC(10)')
;

-- 2020-11-27T18:45:36.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MD_Candidate_Transaction_Detail ADD CONSTRAINT MDCandidateRebookedFrom_MDCandidateTransactionDetail FOREIGN KEY (MD_Candidate_RebookedFrom_ID) REFERENCES public.MD_Candidate DEFERRABLE INITIALLY DEFERRED
;

CREATE INDEX MD_Candidate_Transaction_Detail_MD_Candidate_RebookedFrom_ID
ON MD_Candidate_Transaction_Detail (MD_Candidate_RebookedFrom_ID, MD_Candidate_ID)
;
-- 2020-11-27T18:50:29.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572243,626900,0,540885,TO_TIMESTAMP('2020-11-27 19:50:29','YYYY-MM-DD HH24:MI:SS'),100,'Material-Dispo-Datensatz, auf den sich die Material-Transaktion eigentlich bezieht. Wenn die Transaktion nicht abweichede Merkmale oder ein abweichendes Datum hätte, wäre sie diesem Datensatz direkt zugeordnet.',10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','Umgebucht von',TO_TIMESTAMP('2020-11-27 19:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T18:50:29.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-27T18:50:29.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578585) 
;

-- 2020-11-27T18:50:29.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626900
;

-- 2020-11-27T18:50:29.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(626900)
;

-- 2020-11-27T18:51:03.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,626900,0,540885,541217,575635,'F',TO_TIMESTAMP('2020-11-27 19:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Material-Dispo-Datensatz, auf den sich die Material-Transaktion eigentlich bezieht. Wenn die Transaktion nicht abweichede Merkmale oder ein abweichendes Datum hätte, wäre sie diesem Datensatz direkt zugeordnet.','Y','N','N','Y','N','N','N',0,'MD_Candidate_RebookedFrom_ID',80,0,0,TO_TIMESTAMP('2020-11-27 19:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T18:51:52.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-11-27 19:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549135
;

-- 2020-11-27T18:51:52.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-11-27 19:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575635
;

-- 2020-11-27T18:53:33.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554403
;

-- 2020-11-27T18:53:33.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554402
;

-- 2020-11-27T18:53:33.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549142
;

-- 2020-11-27T18:53:33.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554434
;

-- 2020-11-27T18:53:33.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549143
;

-- 2020-11-27T18:53:33.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549144
;

-- 2020-11-27T18:53:33.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-11-27 19:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575635
;

-- 2020-11-27T18:54:04.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2020-11-27 19:54:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549142
;

-- 2020-11-27T18:54:28.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='M_Transaction_ID',Updated=TO_TIMESTAMP('2020-11-27 19:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549142
;

-- 2020-11-27T18:54:58.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2020-11-27 19:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554434
;

-- 2020-11-27T18:55:27.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2020-11-27 19:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549144
;

-- 2020-11-27T18:55:39.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2020-11-27 19:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575635
;

----
-- Relationtype
----

-- 2020-11-27T19:01:15.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541228,TO_TIMESTAMP('2020-11-27 20:01:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate',TO_TIMESTAMP('2020-11-27 20:01:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-11-27T19:01:15.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541228 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-11-27T19:04:38.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,556473,0,541228,540808,540334,TO_TIMESTAMP('2020-11-27 20:04:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','N',TO_TIMESTAMP('2020-11-27 20:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T19:06:41.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541229,TO_TIMESTAMP('2020-11-27 20:06:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Candidate_Transaction_Detail for MD_Candidate_Rebooked_ID',TO_TIMESTAMP('2020-11-27 20:06:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-11-27T19:06:41.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541229 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-11-27T19:16:32.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,556473,556473,0,541229,540808,540334,TO_TIMESTAMP('2020-11-27 20:16:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','N',TO_TIMESTAMP('2020-11-27 20:16:32','YYYY-MM-DD HH24:MI:SS'),100,'MD_Candidate_ID IN (select ctd.MD_Candidate_ID from MD_Candidate_Transaction_Detail ctd where MD_Candidate_RebookedFrom_ID=@MD_Candidate_ID/1@)')
;

-- 2020-11-27T19:17:33.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541228,541229,540273,TO_TIMESTAMP('2020-11-27 20:17:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','MD_Candidate_to_MD_Candidate_via_MD_Candidate_Rebooked_ID','Y','N','N','MD_Candidate -> MD_Candidate via MD_Candidate_Rebooked_ID',TO_TIMESTAMP('2020-11-27 20:17:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-27T19:17:39.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2020-11-27 20:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540273
;



-- 2020-11-27T21:00:40.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2020-11-27 22:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540808
;

-- 2020-11-27T21:05:19.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2020-11-27 22:05:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556473
;

