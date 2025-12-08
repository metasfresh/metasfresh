-- 2024-12-03T13:34:43.430Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583383,0,'IsAdditionalCustomQuery',TO_TIMESTAMP('2024-12-03 13:34:43.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn" Ja" können durch den metasfresh-Support weitere Daten zum jeweiligen Produktionsauftrag konfiguriert werden. Die jeweiligen Felder können dann in der PLU-Dateikonfig mit der Quelle "Zus. Prozess" konfiguriert werden.','de.metas.externalsystem','Y','Zus. Daten abrufen','Zus. Daten abrufen',TO_TIMESTAMP('2024-12-03 13:34:43.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-03T13:34:43.437Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583383 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAdditionalCustomQuery
-- 2024-12-03T13:34:47.990Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:34:47.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583383 AND AD_Language='de_CH'
;

-- 2024-12-03T13:34:48.016Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583383,'de_CH') 
;

-- Element: IsAdditionalCustomQuery
-- 2024-12-03T13:36:17.363Z
UPDATE AD_Element_Trl SET Description='If ‘Yes’, the metasfresh support team can configure additional data for the respective manufacturing order. The respective fields can then be configured in the PLU file config with the source ‘Additional process’.', IsTranslated='Y', Name='Query additional data', PrintName='Query additional data',Updated=TO_TIMESTAMP('2024-12-03 13:36:17.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583383 AND AD_Language='en_US'
;

-- 2024-12-03T13:36:17.366Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583383,'en_US') 
;

-- Element: IsAdditionalCustomQuery
-- 2024-12-03T13:36:22.538Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:36:22.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583383 AND AD_Language='de_DE'
;

-- 2024-12-03T13:36:22.540Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583383,'de_DE') 
;

-- 2024-12-03T13:36:22.541Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583383,'de_DE') 
;

-- 2024-12-03T13:36:43.950Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl','ALTER TABLE public.ExternalSystem_Config_LeichMehl ADD COLUMN IsAdditionalCustomQuery CHAR(1) DEFAULT ''N'' CHECK (IsAdditionalCustomQuery IN (''Y'',''N'')) NOT NULL')
;

-- 2024-12-03T13:37:26.348Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583384,0,'AD_Process_CustomQuery_ID',TO_TIMESTAMP('2024-12-03 13:37:26.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durch den metasfresh-Support anpassbarer postgREST-Prozess, der zusätzliche Daten zum jeweiligen Produktionsauftrag bereitstellen kann.','de.metas.externalsystem','Y','Prozess für zusätzliche Daten','Prozess für zusätzliche Daten',TO_TIMESTAMP('2024-12-03 13:37:26.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-03T13:37:26.351Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583384 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AD_Process_CustomQuery_ID
-- 2024-12-03T13:37:31.287Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:37:31.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583384 AND AD_Language='de_DE'
;

-- 2024-12-03T13:37:31.291Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583384,'de_DE') 
;

-- 2024-12-03T13:37:31.293Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583384,'de_DE') 
;

-- Element: AD_Process_CustomQuery_ID
-- 2024-12-03T13:38:35.866Z
UPDATE AD_Element_Trl SET Description='Customisable postgREST process via metasfresh support, which can provide additional data for the respective production order.', IsTranslated='Y', Name='Process for additional data', PrintName='Process for additional data',Updated=TO_TIMESTAMP('2024-12-03 13:38:35.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583384 AND AD_Language='en_US'
;

-- 2024-12-03T13:38:35.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583384,'en_US') 
;

-- Element: AD_Process_CustomQuery_ID
-- 2024-12-03T13:38:42.842Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:38:42.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583384 AND AD_Language='de_CH'
;

-- 2024-12-03T13:38:42.844Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583384,'de_CH') 
;



-- Reference: ReplacementSourceList
-- Value: CP
-- ValueName: CustomProcessResult
-- 2024-12-03T13:41:24.725Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541598,543777,TO_TIMESTAMP('2024-12-03 13:41:24.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Zus. Daten',TO_TIMESTAMP('2024-12-03 13:41:24.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CP','CustomProcessResult')
;

-- 2024-12-03T13:41:24.728Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543777 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ReplacementSourceList -> CP_CustomProcessResult
-- 2024-12-03T13:41:28.810Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:41:28.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543777
;

-- Reference Item: ReplacementSourceList -> CP_CustomProcessResult
-- 2024-12-03T13:41:31.728Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:41:31.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543777
;

-- Reference Item: ReplacementSourceList -> CP_CustomProcessResult
-- 2024-12-03T13:41:39.925Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Custom data',Updated=TO_TIMESTAMP('2024-12-03 13:41:39.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543777
;

-- Element: Replacement
-- 2024-12-03T13:43:18.109Z
UPDATE AD_Element_Trl SET Description='JsonPath, mit dem in dem Quellobjekt der Ersetzungswert für das Zielfeld identifiziert wird.', IsTranslated='Y', Name='Ersetzt durch', PrintName='Ersetzt durch',Updated=TO_TIMESTAMP('2024-12-03 13:43:18.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581083 AND AD_Language='de_CH'
;

-- 2024-12-03T13:43:18.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581083,'de_CH') 
;

-- Element: Replacement
-- 2024-12-03T13:43:32.740Z
UPDATE AD_Element_Trl SET Description='JsonPath, mit dem in dem Quellobjekt der Ersetzungswert für das Zielfeld identifiziert wird.', IsTranslated='Y', Name='Ersetzt durch', PrintName='Ersetzt durch',Updated=TO_TIMESTAMP('2024-12-03 13:43:32.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581083 AND AD_Language='de_DE'
;

-- 2024-12-03T13:43:32.743Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581083,'de_DE') 
;

-- 2024-12-03T13:43:32.746Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581083,'de_DE') 
;

-- Element: ReplacementSource
-- 2024-12-03T13:44:19.537Z
UPDATE AD_Element_Trl SET Description='Gibt das Quellobjekt an, aus dem der Ersetzungswert für das Zielfeld übernommen wird.', IsTranslated='Y', Name='Quelle', PrintName='Quelle',Updated=TO_TIMESTAMP('2024-12-03 13:44:19.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='de_CH'
;

-- 2024-12-03T13:44:19.539Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'de_CH') 
;

-- Element: ReplacementSource
-- 2024-12-03T13:44:33.946Z
UPDATE AD_Element_Trl SET Description='Gibt das Quellobjekt an, aus dem der Ersetzungswert für das Zielfeld übernommen wird.', IsTranslated='Y', Name='Quelle', PrintName='Quelle',Updated=TO_TIMESTAMP('2024-12-03 13:44:33.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='de_DE'
;

-- 2024-12-03T13:44:33.948Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581089,'de_DE') 
;

-- 2024-12-03T13:44:33.957Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'de_DE') 
;

-- Element: ReplacementSource
-- 2024-12-03T13:44:39.237Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 13:44:39.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581089 AND AD_Language='en_US'
;

-- 2024-12-03T13:44:39.239Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581089,'en_US') 
;



-- Column: LeichMehl_PluFile_ConfigGroup.IsAdditionalCustomQuery
-- Column: LeichMehl_PluFile_ConfigGroup.IsAdditionalCustomQuery
-- 2024-12-03T13:49:46.510Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589473,583383,0,20,542378,'XX','IsAdditionalCustomQuery',TO_TIMESTAMP('2024-12-03 13:49:46.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn" Ja" können durch den metasfresh-Support weitere Daten zum jeweiligen Produktionsauftrag konfiguriert werden. Die jeweiligen Felder können dann in der PLU-Dateikonfig mit der Quelle "Zus. Prozess" konfiguriert werden.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zus. Daten abrufen',0,0,TO_TIMESTAMP('2024-12-03 13:49:46.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-03T13:49:46.512Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-03T13:49:46.514Z
/* DDL */  select update_Column_Translation_From_AD_Element(583383) 
;

-- 2024-12-03T13:49:48.141Z
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_ConfigGroup','ALTER TABLE public.LeichMehl_PluFile_ConfigGroup ADD COLUMN IsAdditionalCustomQuery CHAR(1) DEFAULT ''N'' CHECK (IsAdditionalCustomQuery IN (''Y'',''N'')) NOT NULL')
;

-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- 2024-12-03T13:50:16.651Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589474,583384,0,18,540706,542378,'XX','AD_Process_CustomQuery_ID',TO_TIMESTAMP('2024-12-03 13:50:16.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Durch den metasfresh-Support anpassbarer postgREST-Prozess, der zusätzliche Daten zum jeweiligen Produktionsauftrag bereitstellen kann.','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Prozess für zusätzliche Daten',0,0,TO_TIMESTAMP('2024-12-03 13:50:16.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-03T13:50:16.653Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-03T13:50:16.656Z
/* DDL */  select update_Column_Translation_From_AD_Element(583384) 
;

-- 2024-12-03T13:50:23.527Z
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_ConfigGroup','ALTER TABLE public.LeichMehl_PluFile_ConfigGroup ADD COLUMN AD_Process_CustomQuery_ID NUMERIC(10)')
;

-- 2024-12-03T13:50:23.537Z
ALTER TABLE LeichMehl_PluFile_ConfigGroup ADD CONSTRAINT ADProcessCustomQuery_LeichMehlPluFileConfigGroup FOREIGN KEY (AD_Process_CustomQuery_ID) REFERENCES public.AD_Process DEFERRABLE INITIALLY DEFERRED
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfiguration -> Zus. Daten abrufen
-- Column: LeichMehl_PluFile_ConfigGroup.IsAdditionalCustomQuery
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> Zus. Daten abrufen
-- Column: LeichMehl_PluFile_ConfigGroup.IsAdditionalCustomQuery
-- 2024-12-03T13:50:47.873Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589473,734059,0,547278,TO_TIMESTAMP('2024-12-03 13:50:47.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn" Ja" können durch den metasfresh-Support weitere Daten zum jeweiligen Produktionsauftrag konfiguriert werden. Die jeweiligen Felder können dann in der PLU-Dateikonfig mit der Quelle "Zus. Prozess" konfiguriert werden.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Zus. Daten abrufen',TO_TIMESTAMP('2024-12-03 13:50:47.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-03T13:50:47.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-03T13:50:47.880Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583383) 
;

-- 2024-12-03T13:50:47.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734059
;

-- 2024-12-03T13:50:47.896Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734059)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfiguration -> Prozess für zusätzliche Daten
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> Prozess für zusätzliche Daten
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- 2024-12-03T13:50:47.985Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589474,734060,0,547278,TO_TIMESTAMP('2024-12-03 13:50:47.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durch den metasfresh-Support anpassbarer postgREST-Prozess, der zusätzliche Daten zum jeweiligen Produktionsauftrag bereitstellen kann.',10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Prozess für zusätzliche Daten',TO_TIMESTAMP('2024-12-03 13:50:47.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-03T13:50:47.987Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-03T13:50:47.989Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583384) 
;

-- 2024-12-03T13:50:47.992Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734060
;

-- 2024-12-03T13:50:47.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734060)
;

-- UI Column: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10
-- UI Element Group: customQuery
-- 2024-12-03T13:51:29.499Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547160,552185,TO_TIMESTAMP('2024-12-03 13:51:29.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','customQuery',20,TO_TIMESTAMP('2024-12-03 13:51:29.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.IsAdditionalCustomQuery
-- Column: LeichMehl_PluFile_ConfigGroup.IsAdditionalCustomQuery
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10 -> customQuery.IsAdditionalCustomQuery
-- Column: LeichMehl_PluFile_ConfigGroup.IsAdditionalCustomQuery
-- 2024-12-03T13:51:49.547Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734059,0,547278,552185,627378,'F',TO_TIMESTAMP('2024-12-03 13:51:49.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn" Ja" können durch den metasfresh-Support weitere Daten zum jeweiligen Produktionsauftrag konfiguriert werden. Die jeweiligen Felder können dann in der PLU-Dateikonfig mit der Quelle "Zus. Prozess" konfiguriert werden.','Y','N','N','Y','N','N','N',0,'IsAdditionalCustomQuery',10,0,0,TO_TIMESTAMP('2024-12-03 13:51:49.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.AD_Process_CustomQuery_ID
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10 -> customQuery.AD_Process_CustomQuery_ID
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- 2024-12-03T13:52:05.466Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734060,0,547278,552185,627379,'F',TO_TIMESTAMP('2024-12-03 13:52:05.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durch den metasfresh-Support anpassbarer postgREST-Prozess, der zusätzliche Daten zum jeweiligen Produktionsauftrag bereitstellen kann.','Y','N','N','Y','N','N','N',0,'AD_Process_CustomQuery_ID',20,0,0,TO_TIMESTAMP('2024-12-03 13:52:05.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfiguration -> Prozess für zusätzliche Daten
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> Prozess für zusätzliche Daten
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- 2024-12-03T13:52:22.527Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-03 13:52:22.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734060
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfiguration -> Prozess für zusätzliche Daten
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> Prozess für zusätzliche Daten
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Process_CustomQuery_ID
-- 2024-12-03T13:52:42.171Z
UPDATE AD_Field SET DisplayLogic='@IsAdditionalCustomQuery/N@=Y',Updated=TO_TIMESTAMP('2024-12-03 13:52:42.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734060
;

-- Table: ExternalSystem_Config_LeichMehl
-- Table: ExternalSystem_Config_LeichMehl
-- 2024-12-03T13:54:10.761Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2024-12-03 13:54:10.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542129
;

