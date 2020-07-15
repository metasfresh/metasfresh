-- 2019-10-21T08:56:50.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-10-21 10:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548909
;

-- 2019-10-21T08:56:53.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-10-21 10:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548908
;

-- 2019-10-21T08:57:28.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548908,0,222,1000034,563581,'F',TO_TIMESTAMP('2019-10-21 10:57:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsCommissionToDefault',160,0,0,TO_TIMESTAMP('2019-10-21 10:57:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T08:57:53.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548909,0,222,1000034,563582,'F',TO_TIMESTAMP('2019-10-21 10:57:53','YYYY-MM-DD HH24:MI:SS'),100,'Provisionsabrechnungen werden hierhin geschickt','Y','N','N','Y','N','N','N',0,'IsCommissionTo',170,0,0,TO_TIMESTAMP('2019-10-21 10:57:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T08:58:16.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-10-21 10:58:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563582
;

-- 2019-10-21T08:58:16.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-10-21 10:58:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563581
;

-- 2019-10-21T08:58:16.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-10-21 10:58:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546539
;

-- 2019-10-21T08:59:57.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Provisionsadresse',Updated=TO_TIMESTAMP('2019-10-21 10:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540404 AND AD_Language='de_CH'
;

-- 2019-10-21T08:59:57.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540404,'de_CH') 
;

-- 2019-10-21T09:00:13.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission address', PrintName='Commission address',Updated=TO_TIMESTAMP('2019-10-21 11:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540404 AND AD_Language='en_US'
;

-- 2019-10-21T09:00:13.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540404,'en_US') 
;

-- 2019-10-21T09:00:21.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Provisionsadresse',Updated=TO_TIMESTAMP('2019-10-21 11:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540404 AND AD_Language='de_DE'
;

-- 2019-10-21T09:00:21.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540404,'de_DE') 
;

-- 2019-10-21T09:00:21.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(540404,'de_DE') 
;

-- 2019-10-21T09:00:21.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCommissionTo', Name='Provisionsadresse', Description='Provisionsabrechnungen werden hierhin geschickt', Help=NULL WHERE AD_Element_ID=540404
;

-- 2019-10-21T09:00:21.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCommissionTo', Name='Provisionsadresse', Description='Provisionsabrechnungen werden hierhin geschickt', Help=NULL, AD_Element_ID=540404 WHERE UPPER(ColumnName)='ISCOMMISSIONTO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-21T09:00:21.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCommissionTo', Name='Provisionsadresse', Description='Provisionsabrechnungen werden hierhin geschickt', Help=NULL WHERE AD_Element_ID=540404 AND IsCentrallyMaintained='Y'
;

-- 2019-10-21T09:00:21.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Provisionsadresse', Description='Provisionsabrechnungen werden hierhin geschickt', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540404) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 540404)
;

-- 2019-10-21T09:00:21.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Provisionsadresse', Name='Provisionsadresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540404)
;

-- 2019-10-21T09:00:21.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Provisionsadresse', Description='Provisionsabrechnungen werden hierhin geschickt', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 540404
;

-- 2019-10-21T09:00:21.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Provisionsadresse', Description='Provisionsabrechnungen werden hierhin geschickt', Help=NULL WHERE AD_Element_ID = 540404
;

-- 2019-10-21T09:00:21.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Provisionsadresse', Description = 'Provisionsabrechnungen werden hierhin geschickt', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 540404
;

-- 2019-10-21T10:57:31.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577232,0,'C_Invoice_Candidate_Commission_ID',TO_TIMESTAMP('2019-10-21 12:57:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Provisionsabrechnungskandidat','Provisionsabrechnungskandidat',TO_TIMESTAMP('2019-10-21 12:57:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T10:57:31.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577232 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-21T10:57:35.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 12:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577232 AND AD_Language='de_CH'
;

-- 2019-10-21T10:57:35.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577232,'de_CH') 
;

-- 2019-10-21T10:57:37.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 12:57:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577232 AND AD_Language='de_DE'
;

-- 2019-10-21T10:57:37.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577232,'de_DE') 
;

-- 2019-10-21T10:57:37.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577232,'de_DE') 
;

-- 2019-10-21T10:57:52.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission settlement candidate', PrintName='Commission settlement candidate',Updated=TO_TIMESTAMP('2019-10-21 12:57:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577232 AND AD_Language='en_US'
;

-- 2019-10-21T10:57:52.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577232,'en_US') 
;

-- 2019-10-21T10:58:13.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-21 12:58:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-21 12:58:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541407,'N',569248,'N','N','N','N','N','N','N','N',0,577232,'de.metas.contracts.commission','N','N','C_Invoice_Candidate_Commission_ID','N','Provisionsabrechnungskandidat',0)
;

-- 2019-10-21T10:58:13.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569248 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-21T10:58:13.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577232) 
;

-- 2019-10-21T10:58:28.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540266,Updated=TO_TIMESTAMP('2019-10-21 12:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569248
;

-- 2019-10-21T10:58:41.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2019-10-21 12:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569248
;

-- 2019-10-21T10:58:42.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Fact','ALTER TABLE public.C_Commission_Fact ADD COLUMN C_Invoice_Candidate_Commission_ID NUMERIC(10)')
;

-- 2019-10-21T10:59:31.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='We don''t create an FK constraint for this column, because users might still delete commission settlement invoice candidates for their own reasons.',Updated=TO_TIMESTAMP('2019-10-21 12:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569248
;

-- 2019-10-21T11:01:27.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541042,542050,TO_TIMESTAMP('2019-10-21 13:01:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Provision abgerechnet',TO_TIMESTAMP('2019-10-21 13:01:27','YYYY-MM-DD HH24:MI:SS'),100,'SETTLED','SETTLED')
;

-- 2019-10-21T11:01:27.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542050 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-10-21T11:01:57.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Prognostizierte Punkte, basierend auf beauftragten, aber noch nicht abzurechnenden (i.d.R. noch nicht ausgelieferten) Positionen.', Name='Beauftragt',Updated=TO_TIMESTAMP('2019-10-21 13:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542011
;

-- 2019-10-21T11:02:24.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='', Name='Fakturiert',Updated=TO_TIMESTAMP('2019-10-21 13:02:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542013
;

-- 2019-10-21T11:02:51.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Zu abgerechneten Punkten wurde schon eine Rechnungsposition für den jeweiligen Vertriebspartner erzeugt',Updated=TO_TIMESTAMP('2019-10-21 13:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542050
;

-- 2019-10-21T11:03:39.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Zu fakturierende Punkte basieren auf gelieferten Positionen, die noch nicht fakturiert wurden.', Name='Zu fakturieren',Updated=TO_TIMESTAMP('2019-10-21 13:03:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542012
;

-- 2019-10-21T11:04:59.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Basieren auf fakturierten Positionen, die dem Vertriebspartner als Provision zustehen',Updated=TO_TIMESTAMP('2019-10-21 13:04:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542013
;

-- 2019-10-21T11:05:10.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Basieren auf fakturierten Positionen, die dem Vertriebspartner als Provision zustehen',Updated=TO_TIMESTAMP('2019-10-21 13:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542013
;

-- 2019-10-21T11:05:17.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Basieren auf fakturierten Positionen, die dem Vertriebspartner als Provision zustehen', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 13:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542013
;

-- 2019-10-21T11:05:21.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Fakturiert',Updated=TO_TIMESTAMP('2019-10-21 13:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542013
;

-- 2019-10-21T11:05:31.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Fakturiert',Updated=TO_TIMESTAMP('2019-10-21 13:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542013
;

