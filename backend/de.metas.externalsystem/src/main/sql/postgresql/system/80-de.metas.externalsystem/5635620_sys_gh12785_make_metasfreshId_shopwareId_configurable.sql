-- 2022-04-19T09:34:52.774Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580786,0,'JSONPathMetasfreshID',TO_TIMESTAMP('2022-04-19 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path','Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path',TO_TIMESTAMP('2022-04-19 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T09:34:53.029Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580786 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-19T09:35:16.488Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Business partner mapping - metasfresh-ID JSON-Path', PrintName='Business partner mapping - metasfresh-ID JSON-Path',Updated=TO_TIMESTAMP('2022-04-19 12:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='en_US'
;

-- 2022-04-19T09:35:16.551Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'en_US')
;

-- 2022-04-19T09:58:57.604Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='JSON path that specifies where within a customized Shopware order the customer''s metasfresh ID (C_BPartner_ID) can be read.',Updated=TO_TIMESTAMP('2022-04-19 12:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='en_US'
;

-- 2022-04-19T09:58:57.639Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'en_US')
;

-- 2022-04-19T09:59:07.225Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.',Updated=TO_TIMESTAMP('2022-04-19 12:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='de_CH'
;

-- 2022-04-19T09:59:07.262Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'de_CH')
;

-- 2022-04-19T09:59:08.491Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.',Updated=TO_TIMESTAMP('2022-04-19 12:59:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='de_DE'
;

-- 2022-04-19T09:59:08.525Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'de_DE')
;

-- 2022-04-19T09:59:08.599Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580786,'de_DE')
;

-- 2022-04-19T09:59:08.633Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='JSONPathMetasfreshID', Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=580786
;

-- 2022-04-19T09:59:08.670Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='JSONPathMetasfreshID', Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', Help=NULL, AD_Element_ID=580786 WHERE UPPER(ColumnName)='JSONPATHMETASFRESHID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-19T09:59:08.707Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='JSONPathMetasfreshID', Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID=580786 AND IsCentrallyMaintained='Y'
;

-- 2022-04-19T09:59:08.740Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580786) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580786)
;

-- 2022-04-19T09:59:08.802Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580786
;

-- 2022-04-19T09:59:08.837Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', Help=NULL WHERE AD_Element_ID = 580786
;

-- 2022-04-19T09:59:08.871Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description = 'JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580786
;

-- 2022-04-19T09:59:11.989Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.',Updated=TO_TIMESTAMP('2022-04-19 12:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='nl_NL'
;

-- 2022-04-19T09:59:12.022Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'nl_NL')
;

-- 2022-04-19T10:01:56.731Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580787,0,'JSONPathShopwareID',TO_TIMESTAMP('2022-04-19 13:01:56','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die Shopware6-Referenz des Kunden ausgelesen werden kann.','D','Y','Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path','Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path',TO_TIMESTAMP('2022-04-19 13:01:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T10:01:56.958Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580787 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-19T10:02:20.672Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='JSON path that specifies where within a customized Shopware order the customer''s Shopware6 reference can be read.', Name='Business partner mapping - Shopware6-ID JSON-Path', PrintName='Business partner mapping - Shopware6-ID JSON-Path',Updated=TO_TIMESTAMP('2022-04-19 13:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580787 AND AD_Language='en_US'
;

-- 2022-04-19T10:02:20.707Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580787,'en_US')
;

-- 2022-04-19T10:04:02.830Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582801,580786,0,10,541585,'JSONPathMetasfreshID',TO_TIMESTAMP('2022-04-19 13:04:02','YYYY-MM-DD HH24:MI:SS'),100,'N','JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path',0,0,TO_TIMESTAMP('2022-04-19 13:04:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-19T10:04:02.939Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582801 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-19T10:04:03.011Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580786)
;

-- 2022-04-19T10:04:27.061Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582802,580787,0,10,541585,'JSONPathShopwareID',TO_TIMESTAMP('2022-04-19 13:04:26','YYYY-MM-DD HH24:MI:SS'),100,'N','JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die Shopware6-Referenz des Kunden ausgelesen werden kann.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path',0,0,TO_TIMESTAMP('2022-04-19 13:04:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-19T10:04:27.287Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582802 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-19T10:04:27.363Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(580787)
;

-- 2022-04-19T10:05:43.204Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582801,691712,0,543838,0,TO_TIMESTAMP('2022-04-19 13:05:42','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path',0,20,0,1,1,TO_TIMESTAMP('2022-04-19 13:05:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T10:05:43.428Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691712 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-19T10:05:43.470Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580786)
;

-- 2022-04-19T10:05:43.522Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691712
;

-- 2022-04-19T10:05:43.557Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(691712)
;

-- 2022-04-19T10:05:59.613Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582802,691713,0,543838,0,TO_TIMESTAMP('2022-04-19 13:05:58','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die Shopware6-Referenz des Kunden ausgelesen werden kann.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path',0,30,0,1,1,TO_TIMESTAMP('2022-04-19 13:05:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T10:05:59.732Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691713 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-19T10:05:59.768Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580787)
;

-- 2022-04-19T10:05:59.807Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691713
;

-- 2022-04-19T10:05:59.851Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(691713)
;

-- 2022-04-19T10:06:38.022Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691712,0,543838,545816,605359,'F',TO_TIMESTAMP('2022-04-19 13:06:37','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die metasfresh-ID des Kunden (C_BPartner_ID) ausgelesen werden kann.','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path',50,0,0,TO_TIMESTAMP('2022-04-19 13:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T10:06:49.427Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691713,0,543838,545816,605360,'F',TO_TIMESTAMP('2022-04-19 13:06:49','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Pfad, der angibt, wo innerhalb einer benutzerdefinierten Shopware-Bestellung die Shopware6-Referenz des Kunden ausgelesen werden kann.','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path',60,0,0,TO_TIMESTAMP('2022-04-19 13:06:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T11:06:21.207Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=597805
;

-- 2022-04-19T11:06:29.653Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=597806
;

-- 2022-04-19T11:07:13.568Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673829
;

-- 2022-04-19T11:07:13.608Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=673829
;

-- 2022-04-19T11:07:13.809Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=673829
;

-- 2022-04-19T11:07:17.951Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673830
;

-- 2022-04-19T11:07:17.984Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=673830
;

-- 2022-04-19T11:07:18.181Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=673830
;

-- 2022-04-19T11:12:58.080Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6Mapping','ALTER TABLE ExternalSystem_Config_Shopware6Mapping DROP COLUMN IF EXISTS BPartnerLookupVia')
;

-- 2022-04-19T11:12:58.082Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578849
;

-- 2022-04-19T11:12:58.291Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=578849
;

-- 2022-04-19T11:13:04.360Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6Mapping','ALTER TABLE ExternalSystem_Config_Shopware6Mapping DROP COLUMN IF EXISTS jsonpathconstantbpartnerid')
;

-- 2022-04-19T11:13:04.370Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=578850
;

-- 2022-04-19T11:13:04.578Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=578850
;

-- 2022-04-19T12:29:26.453Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN JSONPathMetasfreshID VARCHAR(255)')
;

-- 2022-04-19T12:29:33Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN JSONPathShopwareID VARCHAR(255)')
;

