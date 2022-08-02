-- 2021-11-29T10:27:59.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580333,0,'IsCustomUserRestriction',TO_TIMESTAMP('2021-11-29 12:27:59','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angekakt und diese Rolle ist bei einem Nutzer/Kontakt zugeordnet, dann können indivituell angepasste Einschränkungen implementiert werden (z.B. read-only-regeln)','D','Y','Nutzerbeschränkung','Nutzerbeschränkung',TO_TIMESTAMP('2021-11-29 12:27:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-29T10:27:59.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580333 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-29T10:29:02.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If checked and this role is assigned to a user/contact, then customised restrictions can be implemented (e.g. read-only rules).', Name='User restriction', PrintName='User restriction',Updated=TO_TIMESTAMP('2021-11-29 12:29:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580333 AND AD_Language='en_US'
;

-- 2021-11-29T10:29:02.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580333,'en_US') 
;

-- 2021-11-29T10:30:52.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578733,580333,0,20,541776,'IsCustomUserRestriction',TO_TIMESTAMP('2021-11-29 12:30:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn angekakt und diese Rolle ist bei einem Nutzer/Kontakt zugeordnet, dann können indivituell angepasste Einschränkungen implementiert werden (z.B. read-only-regeln)','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Nutzerbeschränkung',0,0,TO_TIMESTAMP('2021-11-29 12:30:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-29T10:30:52.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578733 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-29T10:30:52.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580333) 
;

-- 2021-11-29T10:30:56.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_User_Role','ALTER TABLE public.C_User_Role ADD COLUMN IsCustomUserRestriction CHAR(1) DEFAULT ''N'' CHECK (IsCustomUserRestriction IN (''Y'',''N''))')
;

-- 2021-11-29T11:15:56.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578734,580333,0,20,114,'IsCustomUserRestriction','( CASE WHEN EXISTS(SELECT 1                from c_user_assigned_role uar                         INNER JOIN c_user_role ur on uar.c_user_role_id = ur.c_user_role_id                where ur.IsCustomUserRestriction = ''Y''                  AND uar.ad_user_id = ad_user.ad_user_id) then ''Y'' else ''N'' end)',TO_TIMESTAMP('2021-11-29 13:15:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn angekakt und diese Rolle ist bei einem Nutzer/Kontakt zugeordnet, dann können indivituell angepasste Einschränkungen implementiert werden (z.B. read-only-regeln)','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Nutzerbeschränkung',0,0,TO_TIMESTAMP('2021-11-29 13:15:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-11-29T11:15:56.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578734 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-11-29T11:15:56.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580333) 
;

-- 2021-11-29T11:34:23.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578733,673328,0,544253,0,TO_TIMESTAMP('2021-11-29 13:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angekakt und diese Rolle ist bei einem Nutzer/Kontakt zugeordnet, dann können indivituell angepasste Einschränkungen implementiert werden (z.B. read-only-regeln)',0,'D',0,'Y','Y','Y','N','N','N','N','N','Nutzerbeschränkung',0,30,0,1,1,TO_TIMESTAMP('2021-11-29 13:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-29T11:34:23.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=673328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-11-29T11:34:23.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580333) 
;

-- 2021-11-29T11:34:23.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673328
;

-- 2021-11-29T11:34:23.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(673328)
;

-- 2021-11-29T11:34:56.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,673328,0,544253,546321,597454,'F',TO_TIMESTAMP('2021-11-29 13:34:56','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angekakt und diese Rolle ist bei einem Nutzer/Kontakt zugeordnet, dann können indivituell angepasste Einschränkungen implementiert werden (z.B. read-only-regeln)','Y','N','N','Y','N','N','N',0,'Nutzerbeschränkung',40,0,0,TO_TIMESTAMP('2021-11-29 13:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-29T12:43:19.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,578734,0,540055,114,TO_TIMESTAMP('2021-11-29 14:43:19','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',575528,578733,541776,TO_TIMESTAMP('2021-11-29 14:43:19','YYYY-MM-DD HH24:MI:SS'),100)
;

