-- 2021-06-09T08:30:07.985Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574266,2468,0,20,562,'IsAlwaysUpdateable',TO_TIMESTAMP('2021-06-09 11:30:06','YYYY-MM-DD HH24:MI:SS'),100,'N','N','The column is always updateable, even if the record is not active or processed','D',0,1,'If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Always Updateable',0,0,TO_TIMESTAMP('2021-06-09 11:30:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-09T08:30:08.128Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574266 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-09T08:30:08.227Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2468) 
;

-- 2021-06-09T08:30:14.463Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Attribute','ALTER TABLE public.M_Attribute ADD COLUMN IsAlwaysUpdateable CHAR(1) DEFAULT ''N'' CHECK (IsAlwaysUpdateable IN (''Y'',''N'')) NOT NULL')
;

-- 2021-06-09T08:59:20.673Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574266,647478,0,462,0,TO_TIMESTAMP('2021-06-09 11:59:20','YYYY-MM-DD HH24:MI:SS'),100,'The column is always updateable, even if the record is not active or processed',0,'D','If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.',0,'Y','Y','Y','N','N','N','N','N','Always Updateable',160,220,0,1,1,TO_TIMESTAMP('2021-06-09 11:59:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-09T08:59:20.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-09T08:59:20.745Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2468) 
;

-- 2021-06-09T08:59:20.801Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647478
;

-- 2021-06-09T08:59:20.837Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647478)
;

-- 2021-06-09T09:01:51.269Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647478,0,462,540209,585690,'F',TO_TIMESTAMP('2021-06-09 12:01:50','YYYY-MM-DD HH24:MI:SS'),100,'The column is always updateable, even if the record is not active or processed','If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.','Y','N','N','Y','N','N','N',0,'Always Updateable',70,0,0,TO_TIMESTAMP('2021-06-09 12:01:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-09T09:03:40.041Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579314,0,TO_TIMESTAMP('2021-06-09 12:03:39','YYYY-MM-DD HH24:MI:SS'),100,'When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Palling" status.','D','Y','Attribute Always Updatable','Attribute Always Updatable',TO_TIMESTAMP('2021-06-09 12:03:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-09T09:03:40.081Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579314 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-09T09:05:12.079Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='cs_CZ'
;

-- 2021-06-09T09:05:12.117Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'cs_CZ') 
;

-- 2021-06-09T09:05:18.927Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='de_DE'
;

-- 2021-06-09T09:05:18.959Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'de_DE') 
;

-- 2021-06-09T09:05:19.082Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579314,'de_DE') 
;

-- 2021-06-09T09:05:19.114Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Attribute Always Updatable', Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.', Help=NULL WHERE AD_Element_ID=579314
;

-- 2021-06-09T09:05:19.152Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Attribute Always Updatable', Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.', Help=NULL WHERE AD_Element_ID=579314 AND IsCentrallyMaintained='Y'
;

-- 2021-06-09T09:05:19.184Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Attribute Always Updatable', Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579314) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579314)
;

-- 2021-06-09T09:05:19.261Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Attribute Always Updatable', Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T09:05:19.293Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Attribute Always Updatable', Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.', Help=NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T09:05:19.323Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Attribute Always Updatable', Description = 'When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T09:05:22.160Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='el_GR'
;

-- 2021-06-09T09:05:22.194Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'el_GR') 
;

-- 2021-06-09T09:05:24.979Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_GB'
;

-- 2021-06-09T09:05:25.013Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_GB') 
;

-- 2021-06-09T09:05:27.751Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_US'
;

-- 2021-06-09T09:05:27.781Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_US') 
;

-- 2021-06-09T09:05:30.267Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='fi_FI'
;

-- 2021-06-09T09:05:30.298Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'fi_FI') 
;

-- 2021-06-09T09:05:33.180Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='hu_HU'
;

-- 2021-06-09T09:05:33.212Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'hu_HU') 
;

-- 2021-06-09T09:05:36.225Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='ro_RO'
;

-- 2021-06-09T09:05:36.262Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'ro_RO') 
;

-- 2021-06-09T09:05:38.808Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='ru_RU'
;

-- 2021-06-09T09:05:38.843Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'ru_RU') 
;

-- 2021-06-09T09:05:41.099Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='sk_SK'
;

-- 2021-06-09T09:05:41.131Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'sk_SK') 
;

-- 2021-06-09T09:05:43.822Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='sv_SE'
;

-- 2021-06-09T09:05:43.854Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'sv_SE') 
;

-- 2021-06-09T09:05:47.508Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='When this flag is set on true it means the attribute''s value can be changed even after the handling unit is no longer in the "Planning" status.',Updated=TO_TIMESTAMP('2021-06-09 12:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='tr_TR'
;

-- 2021-06-09T09:05:47.544Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'tr_TR') 
;

-- 2021-06-09T09:49:05.079Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Name='Merkmal immer aktualisierbar', PrintName='Merkmal immer aktualisierbar',Updated=TO_TIMESTAMP('2021-06-09 12:49:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='de_DE'
;

-- 2021-06-09T09:49:05.116Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'de_DE') 
;

-- 2021-06-09T09:49:05.249Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579314,'de_DE') 
;

-- 2021-06-09T09:49:05.292Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.' WHERE AD_Element_ID=579314
;

-- 2021-06-09T09:49:05.326Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.' WHERE AD_Element_ID=579314 AND IsCentrallyMaintained='Y'
;

-- 2021-06-09T09:49:05.363Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579314) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579314)
;

-- 2021-06-09T09:49:05.443Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Merkmal immer aktualisierbar', Name='Merkmal immer aktualisierbar' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579314)
;

-- 2021-06-09T09:49:05.478Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', CommitWarning = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T09:49:05.515Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.' WHERE AD_Element_ID = 579314
;

-- 2021-06-09T09:49:05.549Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Merkmal immer aktualisierbar', Description = 'Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T09:49:09.619Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-09 12:49:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='de_DE'
;

-- 2021-06-09T09:49:09.650Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'de_DE') 
;

-- 2021-06-09T09:49:09.732Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579314,'de_DE') 
;

-- 2021-06-09T09:49:57.160Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', IsTranslated='Y', Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_GB'
;

-- 2021-06-09T09:49:57.195Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_GB') 
;

-- 2021-06-09T09:50:04.050Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_US'
;

-- 2021-06-09T09:50:04.083Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_US') 
;

-- 2021-06-09T09:50:08.915Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='fi_FI'
;

-- 2021-06-09T09:50:08.948Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'fi_FI') 
;

-- 2021-06-09T09:50:12.676Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='hu_HU'
;

-- 2021-06-09T09:50:12.706Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'hu_HU') 
;

-- 2021-06-09T09:50:16.889Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='ro_RO'
;

-- 2021-06-09T09:50:16.919Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'ro_RO') 
;

-- 2021-06-09T09:50:19.667Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='ru_RU'
;

-- 2021-06-09T09:50:19.699Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'ru_RU') 
;

-- 2021-06-09T09:50:22.406Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='sk_SK'
;

-- 2021-06-09T09:50:22.439Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'sk_SK') 
;

-- 2021-06-09T09:50:25.829Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='sv_SE'
;

-- 2021-06-09T09:50:25.861Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'sv_SE') 
;

-- 2021-06-09T09:50:29.291Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='tr_TR'
;

-- 2021-06-09T09:50:29.326Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'tr_TR') 
;

-- 2021-06-09T09:50:46.384Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:50:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='tr_TR'
;

-- 2021-06-09T09:50:46.418Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'tr_TR') 
;

-- 2021-06-09T09:50:49.535Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='sv_SE'
;

-- 2021-06-09T09:50:49.566Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'sv_SE') 
;

-- 2021-06-09T09:50:52.183Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='sk_SK'
;

-- 2021-06-09T09:50:52.216Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'sk_SK') 
;

-- 2021-06-09T09:50:55.478Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:50:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='ru_RU'
;

-- 2021-06-09T09:50:55.509Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'ru_RU') 
;

-- 2021-06-09T09:50:58.579Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='ro_RO'
;

-- 2021-06-09T09:50:58.611Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'ro_RO') 
;

-- 2021-06-09T09:51:01.449Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='hu_HU'
;

-- 2021-06-09T09:51:01.479Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'hu_HU') 
;

-- 2021-06-09T09:51:04.248Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='fi_FI'
;

-- 2021-06-09T09:51:04.289Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'fi_FI') 
;

-- 2021-06-09T09:51:09.025Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_US'
;

-- 2021-06-09T09:51:09.059Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_US') 
;

-- 2021-06-09T09:51:19.997Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='el_GR'
;

-- 2021-06-09T09:51:20.028Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'el_GR') 
;

-- 2021-06-09T09:51:27.442Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Attribute always updatable', PrintName='Attribute always updatable',Updated=TO_TIMESTAMP('2021-06-09 12:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='cs_CZ'
;

-- 2021-06-09T09:51:27.474Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'cs_CZ') 
;

-- 2021-06-09T09:52:44.213Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='cs_CZ'
;

-- 2021-06-09T09:52:44.247Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'cs_CZ') 
;

-- 2021-06-09T09:52:48.266Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status.',Updated=TO_TIMESTAMP('2021-06-09 12:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='el_GR'
;

-- 2021-06-09T09:52:48.298Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'el_GR') 
;

-- 2021-06-09T11:14:52.166Z
-- URL zum Konzept
UPDATE M_Attribute SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2021-06-09 14:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540020
;



-- 2021-06-09T14:16:57.653Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579314, Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet.', Name='Merkmal immer aktualisierbar',Updated=TO_TIMESTAMP('2021-06-09 17:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647478
;

-- 2021-06-09T14:16:57.783Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579314) 
;

-- 2021-06-09T14:16:57.824Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647478
;

-- 2021-06-09T14:16:57.859Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647478)
;





-- 2021-06-09T16:13:56.663Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status. If the attribute was not updatable for the panning HU, it will not be updatable for the active HU either.',Updated=TO_TIMESTAMP('2021-06-09 19:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_US'
;

-- 2021-06-09T16:13:56.823Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_US') 
;

-- 2021-06-09T18:04:58.528Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. Wenn das Merkmal bei einer HU im Status "Geplant" editierbar war und diese Einstellung aktiv ist, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.',Updated=TO_TIMESTAMP('2021-06-09 21:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='de_DE'
;

-- 2021-06-09T18:04:58.573Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'de_DE') 
;

-- 2021-06-09T18:04:58.689Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579314,'de_DE') 
;

-- 2021-06-09T18:04:58.737Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. Wenn das Merkmal bei einer HU im Status "Geplant" editierbar war und diese Einstellung aktiv ist, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE AD_Element_ID=579314
;

-- 2021-06-09T18:04:58.769Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. Wenn das Merkmal bei einer HU im Status "Geplant" editierbar war und diese Einstellung aktiv ist, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE AD_Element_ID=579314 AND IsCentrallyMaintained='Y'
;

-- 2021-06-09T18:04:58.806Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. Wenn das Merkmal bei einer HU im Status "Geplant" editierbar war und diese Einstellung aktiv ist, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579314) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579314)
;

-- 2021-06-09T18:04:58.860Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. Wenn das Merkmal bei einer HU im Status "Geplant" editierbar war und diese Einstellung aktiv ist, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', CommitWarning = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T18:04:58.893Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Merkmal immer aktualisierbar', Description='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', Help='Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. Wenn das Merkmal bei einer HU im Status "Geplant" editierbar war und diese Einstellung aktiv ist, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE AD_Element_ID = 579314
;

-- 2021-06-09T18:04:58.924Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Merkmal immer aktualisierbar', Description = 'Bei Aktivierung dieser Einstellung kann der Merkmalswert auch dann geändert werden, wenn sich die jeweilige Handling Unit nicht mehr im Status "Geplant" befindet. ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-09T18:05:59.836Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status. If the attribute was not updatable for the panning HU, it will not be updatable for the active HU either.',Updated=TO_TIMESTAMP('2021-06-09 21:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_US'
;

-- 2021-06-09T18:05:59.873Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_US') 
;

-- 2021-06-09T18:06:06.999Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status. If the attribute was not updatable for the panning HU, it will not be updatable for the active HU either.', Help='If this setting is activated, the attribute value can be changed even if the respective handling unit is no longer in "Planned" status. If the attribute was not updatable for the panning HU, it will not be updatable for the active HU either..',Updated=TO_TIMESTAMP('2021-06-09 21:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_GB'
;

-- 2021-06-09T18:06:07.031Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_GB') 
;













-- 2021-06-10T11:03:03.220Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If setting is activated and the attribute was editable for an HU in the status "Planned", then the attribute will remain editable even if the HU is no longer "Planned".', Help='If setting is activated and the attribute was editable for an HU in the status "Planned", then the attribute will remain editable even if the HU is no longer "Planned".',Updated=TO_TIMESTAMP('2021-06-10 14:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_US'
;

-- 2021-06-10T11:03:03.461Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_US') 
;

-- 2021-06-10T11:03:14.497Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If setting is activated and the attribute was editable for an HU in the status "Planned", then the attribute will remain editable even if the HU is no longer "Planned".', Help='If setting is activated and the attribute was editable for an HU in the status "Planned", then the attribute will remain editable even if the HU is no longer "Planned".',Updated=TO_TIMESTAMP('2021-06-10 14:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='en_GB'
;

-- 2021-06-10T11:03:14.535Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'en_GB') 
;

-- 2021-06-10T11:03:49.051Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', Help='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.',Updated=TO_TIMESTAMP('2021-06-10 14:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579314 AND AD_Language='de_DE'
;

-- 2021-06-10T11:03:49.089Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579314,'de_DE') 
;

-- 2021-06-10T11:03:49.221Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579314,'de_DE') 
;

-- 2021-06-10T11:03:49.252Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Merkmal immer aktualisierbar', Description='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', Help='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE AD_Element_ID=579314
;

-- 2021-06-10T11:03:49.290Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Merkmal immer aktualisierbar', Description='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', Help='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE AD_Element_ID=579314 AND IsCentrallyMaintained='Y'
;

-- 2021-06-10T11:03:49.337Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Merkmal immer aktualisierbar', Description='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', Help='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579314) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579314)
;

-- 2021-06-10T11:03:49.406Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Merkmal immer aktualisierbar', Description='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', Help='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', CommitWarning = NULL WHERE AD_Element_ID = 579314
;

-- 2021-06-10T11:03:49.453Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Merkmal immer aktualisierbar', Description='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', Help='Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.' WHERE AD_Element_ID = 579314
;

-- 2021-06-10T11:03:49.475Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Merkmal immer aktualisierbar', Description = 'Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579314
;




















