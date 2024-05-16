-- 2021-12-06T14:04:19.743907600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580367,0,'IsPrintWhenPackingMaterial',TO_TIMESTAMP('2021-12-06 16:04:19','YYYY-MM-DD HH24:MI:SS'),100,'When activated, packing material products will be printed on documents only when also used as packing materials in the order. ','U','When activated, packing material products will be printed on documents only when also used as packing materials in the order. ','Y','Print as packing material','Print as packing material',TO_TIMESTAMP('2021-12-06 16:04:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-06T14:04:19.747557900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580367 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-12-06T14:04:42.490131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', IsTranslated='Y', Name='Als Packmittel drucken', PrintName='Als Packmittel drucken',Updated=TO_TIMESTAMP('2021-12-06 16:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580367 AND AD_Language='de_CH'
;

-- 2021-12-06T14:04:42.521558400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580367,'de_CH') 
;

-- 2021-12-06T14:04:55.583966800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', IsTranslated='Y', Name='Als Packmittel drucken', PrintName='Als Packmittel drucken',Updated=TO_TIMESTAMP('2021-12-06 16:04:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580367 AND AD_Language='de_DE'
;

-- 2021-12-06T14:04:55.584991300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580367,'de_DE') 
;

-- 2021-12-06T14:04:55.590687700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580367,'de_DE') 
;

-- 2021-12-06T14:04:55.592235400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPrintWhenPackingMaterial', Name='Als Packmittel drucken', Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.' WHERE AD_Element_ID=580367
;

-- 2021-12-06T14:04:55.593268900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintWhenPackingMaterial', Name='Als Packmittel drucken', Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', AD_Element_ID=580367 WHERE UPPER(ColumnName)='ISPRINTWHENPACKINGMATERIAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-06T14:04:55.600221100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPrintWhenPackingMaterial', Name='Als Packmittel drucken', Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.' WHERE AD_Element_ID=580367 AND IsCentrallyMaintained='Y'
;

-- 2021-12-06T14:04:55.601262900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Als Packmittel drucken', Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580367) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580367)
;

-- 2021-12-06T14:04:55.625232400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Als Packmittel drucken', Name='Als Packmittel drucken' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580367)
;

-- 2021-12-06T14:04:55.627892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Als Packmittel drucken', Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', CommitWarning = NULL WHERE AD_Element_ID = 580367
;

-- 2021-12-06T14:04:55.629454800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Als Packmittel drucken', Description='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', Help='Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.' WHERE AD_Element_ID = 580367
;

-- 2021-12-06T14:04:55.630487300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Als Packmittel drucken', Description = 'Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580367
;

-- 2021-12-06T14:05:19.806925700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578884,580367,0,20,208,'IsPrintWhenPackingMaterial',TO_TIMESTAMP('2021-12-06 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.','U',0,1,'Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Als Packmittel drucken',0,0,TO_TIMESTAMP('2021-12-06 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-06T14:05:19.809513200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578884 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-06T14:05:19.814693600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580367) 
;

-- 2021-12-06T14:05:25.890348700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-12-06 16:05:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578884
;

-- 2021-12-06T14:05:28.900447100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IsPrintWhenPackingMaterial CHAR(1) DEFAULT ''N'' CHECK (IsPrintWhenPackingMaterial IN (''Y'',''N'')) NOT NULL')
;

-- 2021-12-06T14:15:50.970004400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578884,673864,0,180,0,TO_TIMESTAMP('2021-12-06 16:15:50','YYYY-MM-DD HH24:MI:SS'),100,'Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.',0,'D','Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.',0,'Y','Y','Y','N','N','N','N','N','Als Packmittel drucken',0,510,0,1,1,TO_TIMESTAMP('2021-12-06 16:15:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-06T14:15:50.971555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=673864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-06T14:15:50.974643200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580367) 
;

-- 2021-12-06T14:15:50.985502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673864
;

-- 2021-12-06T14:15:50.988764100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(673864)
;

-- 2021-12-06T14:16:38.136439100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,673864,0,180,542064,597832,'F',TO_TIMESTAMP('2021-12-06 16:16:38','YYYY-MM-DD HH24:MI:SS'),100,'Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.','Bei Aktivierung werden Packmittelprodukte nur dann auf Dokumente gedruckt, wenn sie auch als Packmittel im Auftrag verwendet werden. Anderenfalls werden sie nicht gedruckt.','Y','Y','N','Y','N','N','N',0,'Als Packmittel drucken',30,0,0,TO_TIMESTAMP('2021-12-06 16:16:38','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2021-12-08T11:46:50.021372900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-12-08 13:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578884
;

-- 2021-12-08T11:46:51.999541600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product','IsPrintWhenPackingMaterial','CHAR(1)',null,'Y')
;

-- 2021-12-08T11:46:52.980693800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product SET IsPrintWhenPackingMaterial='Y' WHERE IsPrintWhenPackingMaterial IS NULL
;

