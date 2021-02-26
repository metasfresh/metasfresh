-- 2021-02-16T08:45:44.409Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541258,TO_TIMESTAMP('2021-02-16 09:45:44','YYYY-MM-DD HH24:MI:SS'),100,'Guarantee time in months','D','Guarantee time in months','Y','N','GuaranteeMonths',TO_TIMESTAMP('2021-02-16 09:45:44','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-02-16T08:45:44.411Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541258 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-16T08:48:06.092Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Description='Garantiezeit in Monaten', Help='Garantiezeit in Monaten', IsTranslated='Y', Name='Garantiezeit Monaten',Updated=TO_TIMESTAMP('2021-02-16 09:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541258
;

-- 2021-02-16T08:48:26.647Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Description='Garantiezeit in Monaten', Help='Garantiezeit in Monaten', Name='Garantiezeit Monaten',Updated=TO_TIMESTAMP('2021-02-16 09:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541258
;

-- 2021-02-16T08:48:57.362Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Description='Garantiezeit in Monaten', Help='Garantiezeit in Monaten', IsTranslated='Y', Name='GarantiezeitMonaten',Updated=TO_TIMESTAMP('2021-02-16 09:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541258
;

-- 2021-02-16T08:49:07.753Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='GarantiezeitMonaten',Updated=TO_TIMESTAMP('2021-02-16 09:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541258
;

-- 2021-02-16T08:49:17.265Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='GarantiezeitMonaten',Updated=TO_TIMESTAMP('2021-02-16 09:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541258
;

-- 2021-02-16T08:49:25.793Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 09:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541258
;

-- 2021-02-16T08:53:27.342Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541258,542271,TO_TIMESTAMP('2021-02-16 09:53:27','YYYY-MM-DD HH24:MI:SS'),100,'12 months','D','Y','12',TO_TIMESTAMP('2021-02-16 09:53:27','YYYY-MM-DD HH24:MI:SS'),100,'12','12')
;

-- 2021-02-16T08:53:27.345Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542271 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T08:53:55.199Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541258,542272,TO_TIMESTAMP('2021-02-16 09:53:55','YYYY-MM-DD HH24:MI:SS'),100,'24 months','D','Y','24',TO_TIMESTAMP('2021-02-16 09:53:55','YYYY-MM-DD HH24:MI:SS'),100,'24','24')
;

-- 2021-02-16T08:53:55.202Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542272 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T08:54:18.178Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541258,542273,TO_TIMESTAMP('2021-02-16 09:54:18','YYYY-MM-DD HH24:MI:SS'),100,'36 months','D','Y','36',TO_TIMESTAMP('2021-02-16 09:54:18','YYYY-MM-DD HH24:MI:SS'),100,'36','36')
;

-- 2021-02-16T08:54:18.191Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542273 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-16T08:56:48.198Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578761,0,'GuaranteeMonths',TO_TIMESTAMP('2021-02-16 09:56:48','YYYY-MM-DD HH24:MI:SS'),100,'GuaranteeMonths','D','Y','GuaranteeMonths','GuaranteeMonths',TO_TIMESTAMP('2021-02-16 09:56:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-16T08:56:48.201Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578761 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-16T08:57:50.159Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Garantiezeit in Monaten', Name='Garantiezeit Monaten', PrintName='Garantiezeit Monaten',Updated=TO_TIMESTAMP('2021-02-16 09:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761 AND AD_Language='de_CH'
;

-- 2021-02-16T08:57:50.174Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578761,'de_CH')
;

-- 2021-02-16T08:58:06.709Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Garantiezeit in Monaten', IsTranslated='Y', Name='Garantiezeit Monaten', PrintName='Garantiezeit Monaten',Updated=TO_TIMESTAMP('2021-02-16 09:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761 AND AD_Language='de_DE'
;

-- 2021-02-16T08:58:06.710Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578761,'de_DE')
;

-- 2021-02-16T08:58:06.715Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578761,'de_DE')
;

-- 2021-02-16T08:58:06.716Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GuaranteeMonths', Name='Garantiezeit Monaten', Description='Garantiezeit in Monaten', Help=NULL WHERE AD_Element_ID=578761
;

-- 2021-02-16T08:58:06.717Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GuaranteeMonths', Name='Garantiezeit Monaten', Description='Garantiezeit in Monaten', Help=NULL, AD_Element_ID=578761 WHERE UPPER(ColumnName)='GUARANTEEMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-16T08:58:06.717Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GuaranteeMonths', Name='Garantiezeit Monaten', Description='Garantiezeit in Monaten', Help=NULL WHERE AD_Element_ID=578761 AND IsCentrallyMaintained='Y'
;

-- 2021-02-16T08:58:06.718Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Garantiezeit Monaten', Description='Garantiezeit in Monaten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578761) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578761)
;

-- 2021-02-16T08:58:06.727Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Garantiezeit Monaten', Name='Garantiezeit Monaten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578761)
;

-- 2021-02-16T08:58:06.727Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Garantiezeit Monaten', Description='Garantiezeit in Monaten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T08:58:06.728Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Garantiezeit Monaten', Description='Garantiezeit in Monaten', Help=NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T08:58:06.729Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Garantiezeit Monaten', Description = 'Garantiezeit in Monaten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T08:58:41.648Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Guarantee time in months', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-16 09:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761 AND AD_Language='en_US'
;

-- 2021-02-16T08:58:41.649Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578761,'en_US')
;

-- 2021-02-16T08:58:59.795Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Garantiezeit in Monaten', Name='Garantiezeit Monaten', PrintName='Garantiezeit Monaten',Updated=TO_TIMESTAMP('2021-02-16 09:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761 AND AD_Language='nl_NL'
;

-- 2021-02-16T08:58:59.798Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578761,'nl_NL')
;

-- 2021-02-16T08:59:37.543Z
-- URL zum Konzept
UPDATE AD_Element SET Name='GuaranteeMonths',Updated=TO_TIMESTAMP('2021-02-16 09:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761
;

-- 2021-02-16T08:59:37.550Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GuaranteeMonths', Name='GuaranteeMonths', Description='Garantiezeit in Monaten', Help=NULL WHERE AD_Element_ID=578761
;

-- 2021-02-16T08:59:37.552Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GuaranteeMonths', Name='GuaranteeMonths', Description='Garantiezeit in Monaten', Help=NULL, AD_Element_ID=578761 WHERE UPPER(ColumnName)='GUARANTEEMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-16T08:59:37.556Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GuaranteeMonths', Name='GuaranteeMonths', Description='Garantiezeit in Monaten', Help=NULL WHERE AD_Element_ID=578761 AND IsCentrallyMaintained='Y'
;

-- 2021-02-16T08:59:37.558Z
-- URL zum Konzept
UPDATE AD_Field SET Name='GuaranteeMonths', Description='Garantiezeit in Monaten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578761) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578761)
;

-- 2021-02-16T08:59:37.589Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Garantiezeit Monaten', Name='GuaranteeMonths' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578761)
;

-- 2021-02-16T08:59:37.591Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='GuaranteeMonths', Description='Garantiezeit in Monaten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T08:59:37.593Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='GuaranteeMonths', Description='Garantiezeit in Monaten', Help=NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T08:59:37.594Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'GuaranteeMonths', Description = 'Garantiezeit in Monaten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T08:59:41.081Z
-- URL zum Konzept
UPDATE AD_Element SET PrintName='GuaranteeMonths',Updated=TO_TIMESTAMP('2021-02-16 09:59:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761
;

-- 2021-02-16T08:59:41.085Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='GuaranteeMonths', Name='GuaranteeMonths' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578761)
;

-- 2021-02-16T09:00:06.810Z
-- URL zum Konzept
UPDATE AD_Element SET Description='GuaranteeMonths',Updated=TO_TIMESTAMP('2021-02-16 10:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578761
;

-- 2021-02-16T09:00:06.811Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GuaranteeMonths', Name='GuaranteeMonths', Description='GuaranteeMonths', Help=NULL WHERE AD_Element_ID=578761
;

-- 2021-02-16T09:00:06.811Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GuaranteeMonths', Name='GuaranteeMonths', Description='GuaranteeMonths', Help=NULL, AD_Element_ID=578761 WHERE UPPER(ColumnName)='GUARANTEEMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-16T09:00:06.812Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GuaranteeMonths', Name='GuaranteeMonths', Description='GuaranteeMonths', Help=NULL WHERE AD_Element_ID=578761 AND IsCentrallyMaintained='Y'
;

-- 2021-02-16T09:00:06.812Z
-- URL zum Konzept
UPDATE AD_Field SET Name='GuaranteeMonths', Description='GuaranteeMonths', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578761) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578761)
;

-- 2021-02-16T09:00:06.817Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='GuaranteeMonths', Description='GuaranteeMonths', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T09:00:06.818Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='GuaranteeMonths', Description='GuaranteeMonths', Help=NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T09:00:06.819Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'GuaranteeMonths', Description = 'GuaranteeMonths', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578761
;

-- 2021-02-16T09:01:48.941Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572813,578761,0,17,541258,208,'GuaranteeMonths',TO_TIMESTAMP('2021-02-16 10:01:48','YYYY-MM-DD HH24:MI:SS'),100,'N','GuaranteeMonths','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'GuaranteeMonths',0,0,TO_TIMESTAMP('2021-02-16 10:01:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-16T09:01:48.945Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-16T09:01:48.949Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578761)
;

-- 2021-02-16T09:03:33.501Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,EntityType,Description,Name,AD_Org_ID,AD_Name_ID) VALUES (180,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-16 10:03:33','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-02-16 10:03:33','YYYY-MM-DD HH24:MI:SS'),100,631829,'Y',460,490,1,1,572813,'U','GuaranteeMonths','GuaranteeMonths',0,578761)
;

-- 2021-02-16T09:03:33.505Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-16T09:03:33.509Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578761)
;

-- 2021-02-16T09:03:33.527Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631829
;

-- 2021-02-16T09:03:33.532Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631829)
;

-- 2021-02-16T09:08:08.055Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-02-16 10:08:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551159
;

-- 2021-02-16T09:08:33.115Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631829,0,180,1000015,577951,'F',TO_TIMESTAMP('2021-02-16 10:08:33','YYYY-MM-DD HH24:MI:SS'),100,'GuaranteeMonths','Y','N','N','Y','N','N','N',0,'GuaranteeMonths',70,0,0,TO_TIMESTAMP('2021-02-16 10:08:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-16T09:20:01.563Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_product','GuaranteeMonths','VARCHAR(10)',null,null)
;

-- 2021-02-16T09:23:08.578Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2021-02-16 10:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577952
;

-- 2021-02-16T09:25:19.869Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2021-02-16 10:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577951
;

-- 2021-02-18T07:55:51.676Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-02-18 09:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551159
;

-- 2021-02-24T15:23:46.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2021-02-24 17:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=631829
;