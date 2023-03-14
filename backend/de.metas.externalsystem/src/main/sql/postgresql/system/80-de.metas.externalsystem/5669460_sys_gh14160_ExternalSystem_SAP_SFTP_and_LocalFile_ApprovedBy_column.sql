-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-20T16:50:33.537Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585420,543844,0,18,110,542258,'ApprovedBy_ID',TO_TIMESTAMP('2022-12-20 18:50:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Approved By',0,0,TO_TIMESTAMP('2022-12-20 18:50:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-20T16:50:33.540Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-20T16:50:33.563Z
/* DDL */  select update_Column_Translation_From_AD_Element(543844) 
;

-- 2022-12-20T16:50:34.482Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN ApprovedBy_ID NUMERIC(10)')
;

-- 2022-12-20T16:50:34.495Z
ALTER TABLE ExternalSystem_Config_SAP_LocalFile ADD CONSTRAINT ApprovedBy_ExternalSystemConfigSAPLocalFile FOREIGN KEY (ApprovedBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-20T16:51:00.332Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585421,543844,0,18,110,542257,'ApprovedBy_ID',TO_TIMESTAMP('2022-12-20 18:51:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Approved By',0,0,TO_TIMESTAMP('2022-12-20 18:51:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-20T16:51:00.336Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585421 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-20T16:51:00.342Z
/* DDL */  select update_Column_Translation_From_AD_Element(543844) 
;

-- 2022-12-20T16:51:00.950Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN ApprovedBy_ID NUMERIC(10)')
;

-- 2022-12-20T16:51:00.959Z
ALTER TABLE ExternalSystem_Config_SAP_SFTP ADD CONSTRAINT ApprovedBy_ExternalSystemConfigSAPSFTP FOREIGN KEY (ApprovedBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Approved By
-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-20T17:03:24.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585421,710050,0,546672,0,TO_TIMESTAMP('2022-12-20 19:03:24','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Approved By',0,180,0,1,1,TO_TIMESTAMP('2022-12-20 19:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T17:03:24.501Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-20T17:03:24.504Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543844) 
;

-- 2022-12-20T17:03:24.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710050
;

-- 2022-12-20T17:03:24.518Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710050)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: approval
-- 2022-12-20T17:03:50.980Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550196,TO_TIMESTAMP('2022-12-20 19:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','approval',15,TO_TIMESTAMP('2022-12-20 19:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> approval.Approved By
-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-20T17:04:06.976Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710050,0,546672,614568,550196,'F',TO_TIMESTAMP('2022-12-20 19:04:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Approved By',10,0,0,TO_TIMESTAMP('2022-12-20 19:04:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Approved By
-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-20T17:05:07.189Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585420,710051,0,546673,0,TO_TIMESTAMP('2022-12-20 19:05:07','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Approved By',0,150,0,1,1,TO_TIMESTAMP('2022-12-20 19:05:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T17:05:07.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-20T17:05:07.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543844) 
;

-- 2022-12-20T17:05:07.199Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710051
;

-- 2022-12-20T17:05:07.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710051)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: approval
-- 2022-12-20T17:05:20.776Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550197,TO_TIMESTAMP('2022-12-20 19:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','approval',15,TO_TIMESTAMP('2022-12-20 19:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> approval.Approved By
-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-20T17:05:32.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710051,0,546673,614569,550197,'F',TO_TIMESTAMP('2022-12-20 19:05:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Approved By',10,0,0,TO_TIMESTAMP('2022-12-20 19:05:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T07:10:45.535Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581889,0,TO_TIMESTAMP('2022-12-21 09:10:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Credit Limit Responsible User','Credit Limit Responsible User',TO_TIMESTAMP('2022-12-21 09:10:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-21T07:10:45.542Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581889 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-12-21T07:10:51.626Z
UPDATE AD_Element_Trl SET Name='Kreditlimit Verantwortlicher Nutzer', PrintName='Kreditlimit Verantwortlicher Nutzer',Updated=TO_TIMESTAMP('2022-12-21 09:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581889 AND AD_Language='de_CH'
;

-- 2022-12-21T07:10:51.646Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581889,'de_CH')
;

-- Element: null
-- 2022-12-21T07:10:56.177Z
UPDATE AD_Element_Trl SET Name='Kreditlimit Verantwortlicher Nutzer', PrintName='Kreditlimit Verantwortlicher Nutzer',Updated=TO_TIMESTAMP('2022-12-21 09:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581889 AND AD_Language='de_DE'
;

-- 2022-12-21T07:10:56.179Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581889,'de_DE')
;

-- 2022-12-21T07:10:56.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581889,'de_DE')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Kreditlimit Verantwortlicher Nutzer
-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-21T07:12:25.733Z
UPDATE AD_Field SET AD_Name_ID=581889, Description=NULL, Help=NULL, Name='Kreditlimit Verantwortlicher Nutzer',Updated=TO_TIMESTAMP('2022-12-21 09:12:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710051
;

-- 2022-12-21T07:12:25.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581889)
;

-- 2022-12-21T07:12:25.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710051
;

-- 2022-12-21T07:12:25.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710051)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Kreditlimit Verantwortlicher Nutzer
-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-21T07:12:52.381Z
UPDATE AD_Field SET AD_Name_ID=581889, Description=NULL, Help=NULL, Name='Kreditlimit Verantwortlicher Nutzer',Updated=TO_TIMESTAMP('2022-12-21 09:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710050
;

-- 2022-12-21T07:12:52.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581889)
;

-- 2022-12-21T07:12:52.412Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710050
;

-- 2022-12-21T07:12:52.413Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710050)
;

-- Element: null
-- 2022-12-21T07:13:46.448Z
UPDATE AD_Element_Trl SET Description='If set, synced credit limits will be automatically approved.',Updated=TO_TIMESTAMP('2022-12-21 09:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581889 AND AD_Language='en_US'
;

-- 2022-12-21T07:13:46.452Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581889,'en_US')
;

-- Element: null
-- 2022-12-21T07:13:47.418Z
UPDATE AD_Element_Trl SET Description='Falls eingestellt, werden synchronisierte Kreditlimits automatisch genehmigt.',Updated=TO_TIMESTAMP('2022-12-21 09:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581889 AND AD_Language='de_CH'
;

-- 2022-12-21T07:13:47.420Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581889,'de_CH')
;

-- Element: null
-- 2022-12-21T07:13:49.426Z
UPDATE AD_Element_Trl SET Description='Falls eingestellt, werden synchronisierte Kreditlimits automatisch genehmigt.',Updated=TO_TIMESTAMP('2022-12-21 09:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581889 AND AD_Language='de_DE'
;

-- 2022-12-21T07:13:49.427Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581889,'de_DE')
;

-- 2022-12-21T07:13:49.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581889,'de_DE')
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Approved By
-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-22T18:02:46.654Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550038, SeqNo=30,Updated=TO_TIMESTAMP('2022-12-22 20:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614568
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: approval
-- 2022-12-22T18:02:56.581Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550196
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> Credit-Limit.Approved By
-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-22T18:04:52.898Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550041, SeqNo=30,Updated=TO_TIMESTAMP('2022-12-22 20:04:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614569
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: approval
-- 2022-12-22T18:05:00.940Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550197
;

