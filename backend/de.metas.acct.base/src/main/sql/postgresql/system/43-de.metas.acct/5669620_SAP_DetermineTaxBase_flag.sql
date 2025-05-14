-- 2022-12-22T11:16:51.606Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581893,0,'SAP_DetermineTaxBase',TO_TIMESTAMP('2022-12-22 13:16:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Determine tax base','Determine tax base',TO_TIMESTAMP('2022-12-22 13:16:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T11:16:51.613Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581893 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_GLJournalLine.SAP_DetermineTaxBase
-- 2022-12-22T11:17:11.858Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585428,581893,0,20,542276,'SAP_DetermineTaxBase',TO_TIMESTAMP('2022-12-22 13:17:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.acct',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Determine tax base',0,0,TO_TIMESTAMP('2022-12-22 13:17:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-22T11:17:11.861Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585428 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-22T11:17:12.138Z
/* DDL */  select update_Column_Translation_From_AD_Element(581893) 
;

-- 2022-12-22T11:17:13.040Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN SAP_DetermineTaxBase CHAR(1) DEFAULT ''N'' CHECK (SAP_DetermineTaxBase IN (''Y'',''N'')) NOT NULL')
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Determine tax base
-- Column: SAP_GLJournalLine.SAP_DetermineTaxBase
-- 2022-12-22T11:17:31.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585428,710056,0,546731,TO_TIMESTAMP('2022-12-22 13:17:31','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.acct','Y','N','N','N','N','N','N','N','Determine tax base',TO_TIMESTAMP('2022-12-22 13:17:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T11:17:31.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-22T11:17:31.699Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581893) 
;

-- 2022-12-22T11:17:31.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710056
;

-- 2022-12-22T11:17:31.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710056)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Determine tax base
-- Column: SAP_GLJournalLine.SAP_DetermineTaxBase
-- 2022-12-22T11:18:44.365Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710056,0,546731,550195,614574,'F',TO_TIMESTAMP('2022-12-22 13:18:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Determine tax base',30,0,0,TO_TIMESTAMP('2022-12-22 13:18:44','YYYY-MM-DD HH24:MI:SS'),100)
;

