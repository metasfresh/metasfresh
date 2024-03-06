-- 2022-03-18T11:03:40.415109700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580717,0,'IsPrintedInDocument',TO_TIMESTAMP('2022-03-18 13:03:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Printed in document','Printed in document',TO_TIMESTAMP('2022-03-18 13:03:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-18T11:03:40.418711900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580717 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-18T11:03:46.139046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dokument gedruckt', PrintName='Dokument gedruckt',Updated=TO_TIMESTAMP('2022-03-18 13:03:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717 AND AD_Language='de_CH'
;

-- 2022-03-18T11:03:46.166833100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580717,'de_CH') 
;

-- 2022-03-18T11:03:51.214603800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dokument gedruckt', PrintName='Dokument gedruckt',Updated=TO_TIMESTAMP('2022-03-18 13:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717 AND AD_Language='de_DE'
;

-- 2022-03-18T11:03:51.215640400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580717,'de_DE') 
;

-- 2022-03-18T11:03:51.220792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580717,'de_DE') 
;

-- 2022-03-18T11:03:51.224779300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description=NULL, Help=NULL WHERE AD_Element_ID=580717
;

-- 2022-03-18T11:03:51.225819300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description=NULL, Help=NULL, AD_Element_ID=580717 WHERE UPPER(ColumnName)='ISPRINTEDINDOCUMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-18T11:03:51.234228600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description=NULL, Help=NULL WHERE AD_Element_ID=580717 AND IsCentrallyMaintained='Y'
;

-- 2022-03-18T11:03:51.235273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dokument gedruckt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580717) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580717)
;

-- 2022-03-18T11:03:51.255643700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dokument gedruckt', Name='Dokument gedruckt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580717)
;

-- 2022-03-18T11:03:51.257195900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dokument gedruckt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T11:03:51.258751800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dokument gedruckt', Description=NULL, Help=NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T11:03:51.259270400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dokument gedruckt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T11:05:24.502093800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582466,580717,0,20,562,'IsPrintedInDocument',TO_TIMESTAMP('2022-03-18 13:05:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Dokument gedruckt',0,0,TO_TIMESTAMP('2022-03-18 13:05:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-18T11:05:24.504175200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582466 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-18T11:05:24.507807600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580717) 
;

-- 2022-03-18T11:05:28.120093600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Attribute','ALTER TABLE public.M_Attribute ADD COLUMN IsPrintedInDocument CHAR(1) DEFAULT ''N'' CHECK (IsPrintedInDocument IN (''Y'',''N'')) NOT NULL')
;

-- 2022-03-18T11:08:33.655142500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.',Updated=TO_TIMESTAMP('2022-03-18 13:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717
;

-- 2022-03-18T11:08:33.658249700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.', Help=NULL WHERE AD_Element_ID=580717
;

-- 2022-03-18T11:08:33.662736500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.', Help=NULL, AD_Element_ID=580717 WHERE UPPER(ColumnName)='ISPRINTEDINDOCUMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-18T11:08:33.663773600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.', Help=NULL WHERE AD_Element_ID=580717 AND IsCentrallyMaintained='Y'
;

-- 2022-03-18T11:08:33.664292200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dokument gedruckt', Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580717) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580717)
;

-- 2022-03-18T11:08:33.672459800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dokument gedruckt', Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T11:08:33.673493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dokument gedruckt', Description='If this flag is set on Y, then means that the attribute will be shown in all document reports.', Help=NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T11:08:33.674527900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dokument gedruckt', Description = 'If this flag is set on Y, then means that the attribute will be shown in all document reports.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T12:02:00.770282600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_attribute','IsPrintedInDocument','CHAR(1)',null,'N')
;

-- 2022-03-18T12:02:01.044560900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsPrintedInDocument='N' WHERE IsPrintedInDocument IS NULL
;

-- 2022-03-18T12:02:15.011935400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582466,691408,0,462,TO_TIMESTAMP('2022-03-18 14:02:14','YYYY-MM-DD HH24:MI:SS'),100,'If this flag is set on Y, then means that the attribute will be shown in all document reports.',1,'D','Y','N','N','N','N','N','N','N','Dokument gedruckt',TO_TIMESTAMP('2022-03-18 14:02:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-18T12:02:15.013487500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691408 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-18T12:02:15.015559700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580717) 
;

-- 2022-03-18T12:02:15.026939100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691408
;

-- 2022-03-18T12:02:15.030757300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691408)
;

-- 2022-03-18T12:02:54.195408400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691408,0,462,540209,605247,'F',TO_TIMESTAMP('2022-03-18 14:02:54','YYYY-MM-DD HH24:MI:SS'),100,'If this flag is set on Y, then means that the attribute will be shown in all document reports.','Y','N','N','Y','N','N','N',0,'Dokument gedruckt',80,0,0,TO_TIMESTAMP('2022-03-18 14:02:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-18T12:15:00.687659800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt',Updated=TO_TIMESTAMP('2022-03-18 14:15:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717 AND AD_Language='de_CH'
;

-- 2022-03-18T12:15:00.688698500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580717,'de_CH') 
;

-- 2022-03-18T12:15:10.603408500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.',Updated=TO_TIMESTAMP('2022-03-18 14:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717 AND AD_Language='de_DE'
;

-- 2022-03-18T12:15:10.604962800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580717,'de_DE') 
;

-- 2022-03-18T12:15:10.612237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580717,'de_DE') 
;

-- 2022-03-18T12:15:10.613272500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.' WHERE AD_Element_ID=580717
;

-- 2022-03-18T12:15:10.614305800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', AD_Element_ID=580717 WHERE UPPER(ColumnName)='ISPRINTEDINDOCUMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-18T12:15:10.615342800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintedInDocument', Name='Dokument gedruckt', Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.' WHERE AD_Element_ID=580717 AND IsCentrallyMaintained='Y'
;

-- 2022-03-18T12:15:10.616379500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dokument gedruckt', Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580717) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580717)
;

-- 2022-03-18T12:15:10.624340200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dokument gedruckt', Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', CommitWarning = NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T12:15:10.625891500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dokument gedruckt', Description='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.' WHERE AD_Element_ID = 580717
;

-- 2022-03-18T12:15:10.626410900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dokument gedruckt', Description = 'Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580717
;

-- 2022-03-18T12:15:13.328009800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.',Updated=TO_TIMESTAMP('2022-03-18 14:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717 AND AD_Language='de_CH'
;

-- 2022-03-18T12:15:13.329070400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580717,'de_CH') 
;

-- 2022-03-18T12:15:25.847711500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, the attribute will be printed on all referenced documents.', Help='If ticked, the attribute will be printed on all referenced documents.',Updated=TO_TIMESTAMP('2022-03-18 14:15:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580717 AND AD_Language='en_US'
;

-- 2022-03-18T12:15:25.848752500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580717,'en_US') 
;

