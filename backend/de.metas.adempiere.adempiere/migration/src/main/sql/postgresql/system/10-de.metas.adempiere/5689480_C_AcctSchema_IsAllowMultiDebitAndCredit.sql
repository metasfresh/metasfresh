-- 2023-05-26T06:33:40.738Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582380,0,'IsAllowMultiDebitAndCredit',TO_TIMESTAMP('2023-05-26 09:33:40','YYYY-MM-DD HH24:MI:SS'),100,'Allow compound transactions with multiple bookings on Debit and on Credit. Enable it only if you know what are you doing. Your Account Balance reports won''t work.','D','Y','Allow Multi Debit and Credit bookings','Allow Multi Debit and Credit bookings',TO_TIMESTAMP('2023-05-26 09:33:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-26T06:33:40.740Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582380 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_AcctSchema.IsAllowMultiDebitAndCredit
-- 2023-05-26T06:33:50.984Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586712,582380,0,20,265,'IsAllowMultiDebitAndCredit',TO_TIMESTAMP('2023-05-26 09:33:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Allow compound transactions with multiple bookings on Debit and on Credit. Enable it only if you know what are you doing. Your Account Balance reports won''t work.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow Multi Debit and Credit bookings',0,0,TO_TIMESTAMP('2023-05-26 09:33:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-26T06:33:50.986Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586712 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-26T06:33:51.515Z
/* DDL */  select update_Column_Translation_From_AD_Element(582380) 
;

-- 2023-05-26T06:33:52.353Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema','ALTER TABLE public.C_AcctSchema ADD COLUMN IsAllowMultiDebitAndCredit CHAR(1) DEFAULT ''N'' CHECK (IsAllowMultiDebitAndCredit IN (''Y'',''N'')) NOT NULL')
;

-- Field: Accounting Schema(125,D) -> Accounting Schema(199,D) -> Allow Multi Debit and Credit bookings
-- Column: C_AcctSchema.IsAllowMultiDebitAndCredit
-- 2023-05-26T06:34:12.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586712,715985,0,199,TO_TIMESTAMP('2023-05-26 09:34:12','YYYY-MM-DD HH24:MI:SS'),100,'Allow compound transactions with multiple bookings on Debit and on Credit. Enable it only if you know what are you doing. Your Account Balance reports won''t work.',1,'D','Y','N','N','N','N','N','N','N','Allow Multi Debit and Credit bookings',TO_TIMESTAMP('2023-05-26 09:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-26T06:34:12.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-26T06:34:12.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582380) 
;

-- 2023-05-26T06:34:12.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715985
;

-- 2023-05-26T06:34:12.503Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715985)
;

-- UI Element: Accounting Schema(125,D) -> Accounting Schema(199,D) -> main -> 20 -> flags.Allow Multi Debit and Credit bookings
-- Column: C_AcctSchema.IsAllowMultiDebitAndCredit
-- 2023-05-26T06:35:22.483Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715985,0,199,541337,617883,'F',TO_TIMESTAMP('2023-05-26 09:35:22','YYYY-MM-DD HH24:MI:SS'),100,'Allow compound transactions with multiple bookings on Debit and on Credit. Enable it only if you know what are you doing. Your Account Balance reports won''t work.','Y','N','Y','N','N','Allow Multi Debit and Credit bookings',80,0,0,TO_TIMESTAMP('2023-05-26 09:35:22','YYYY-MM-DD HH24:MI:SS'),100)
;

