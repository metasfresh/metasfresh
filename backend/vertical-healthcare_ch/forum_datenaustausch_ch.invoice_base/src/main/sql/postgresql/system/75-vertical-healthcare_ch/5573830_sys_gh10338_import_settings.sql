-- 2020-12-02T15:09:03.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578593,0,'ImportedPartientBP_Group_ID',TO_TIMESTAMP('2020-12-02 16:09:03','YYYY-MM-DD HH24:MI:SS'),100,'Kundengruppe für einen Patienten, der beim Import neu angelegt oder aktualisiert wird','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Patienten-Kundengruppe','Patienten-Kundengruppe',TO_TIMESTAMP('2020-12-02 16:09:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:09:03.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578593 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-12-02T15:10:37.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572299,578593,0,18,540996,541145,'ImportedPartientBP_Group_ID',TO_TIMESTAMP('2020-12-02 16:10:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Kundengruppe für einen Patienten, der beim Import neu angelegt oder aktualisiert wird','de.metas.vertical.healthcare.forum_datenaustausch_ch',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Patienten-Kundengruppe',0,0,TO_TIMESTAMP('2020-12-02 16:10:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-12-02T15:10:37.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572299 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-12-02T15:10:37.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578593) 
;

-- 2020-12-02T15:10:40.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN ImportedPartientBP_Group_ID NUMERIC(10)')
;

-- 2020-12-02T15:10:40.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE HC_Forum_Datenaustausch_Config ADD CONSTRAINT ImportedPartientBPGroup_HCForumDatenaustauschConfig FOREIGN KEY (ImportedPartientBP_Group_ID) REFERENCES public.C_BP_Group DEFERRABLE INITIALLY DEFERRED
;

-- 2020-12-02T15:12:10.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578594,0,'ImportedMunicipalityBP_Group_ID',TO_TIMESTAMP('2020-12-02 16:12:09','YYYY-MM-DD HH24:MI:SS'),100,'Kundengruppe für eine Gemeinde, die beim Import neu angelegt oder aktualisiert wird','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Gemeinden-Kundengruppe','Gemeinden-Kundengruppe',TO_TIMESTAMP('2020-12-02 16:12:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:12:10.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578594 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-12-02T15:37:40.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572300,578594,0,18,540996,541145,'ImportedMunicipalityBP_Group_ID',TO_TIMESTAMP('2020-12-02 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Kundengruppe für eine Gemeinde, die beim Import neu angelegt oder aktualisiert wird','de.metas.vertical.healthcare.forum_datenaustausch_ch',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gemeinden-Kundengruppe',0,0,TO_TIMESTAMP('2020-12-02 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-12-02T15:37:40.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572300 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-12-02T15:37:40.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578594) 
;

-- 2020-12-02T15:37:42.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN ImportedMunicipalityBP_Group_ID NUMERIC(10)')
;

-- 2020-12-02T15:37:42.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE HC_Forum_Datenaustausch_Config ADD CONSTRAINT ImportedMunicipalityBPGroup_HCForumDatenaustauschConfig FOREIGN KEY (ImportedMunicipalityBP_Group_ID) REFERENCES public.C_BP_Group DEFERRABLE INITIALLY DEFERRED
;

-- 2020-12-02T15:39:54.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578595,0,'ImportedBPartnerLanguage',TO_TIMESTAMP('2020-12-02 16:39:54','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für einen Geschäftspartner, der beim Import neu angelegt oder aktualisiert wird','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Sprache','Sprache',TO_TIMESTAMP('2020-12-02 16:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:39:54.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578595 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-12-02T15:42:04.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572301,578595,0,18,327,541145,'ImportedBPartnerLanguage',TO_TIMESTAMP('2020-12-02 16:42:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Sprache für einen Geschäftspartner, der beim Import neu angelegt oder aktualisiert wird','de.metas.vertical.healthcare.forum_datenaustausch_ch',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sprache',0,0,TO_TIMESTAMP('2020-12-02 16:42:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-12-02T15:42:04.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572301 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-12-02T15:42:04.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578595) 
;

-- 2020-12-02T15:42:35.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN ImportedBPartnerLanguage VARCHAR(10)')
;

-- 2020-12-02T15:42:35.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE HC_Forum_Datenaustausch_Config ADD CONSTRAINT ImportedBPartnerLangu_HCForumDatenaustauschConfig FOREIGN KEY (ImportedBPartnerLanguage) REFERENCES public.AD_Language DEFERRABLE INITIALLY DEFERRED
;

-- 2020-12-02T15:43:02.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572299,626941,0,541327,TO_TIMESTAMP('2020-12-02 16:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Kundengruppe für einen Patienten, der beim Import neu angelegt oder aktualisiert wird',10,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Y','N','N','N','N','N','Patienten-Kundengruppe',TO_TIMESTAMP('2020-12-02 16:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:43:02.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-12-02T15:43:02.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578593) 
;

-- 2020-12-02T15:43:02.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626941
;

-- 2020-12-02T15:43:02.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(626941)
;

-- 2020-12-02T15:43:02.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572300,626942,0,541327,TO_TIMESTAMP('2020-12-02 16:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Kundengruppe für eine Gemeinde, die beim Import neu angelegt oder aktualisiert wird',10,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Y','N','N','N','N','N','Gemeinden-Kundengruppe',TO_TIMESTAMP('2020-12-02 16:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:43:02.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-12-02T15:43:02.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578594) 
;

-- 2020-12-02T15:43:02.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626942
;

-- 2020-12-02T15:43:02.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(626942)
;

-- 2020-12-02T15:43:02.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572301,626943,0,541327,TO_TIMESTAMP('2020-12-02 16:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für einen Geschäftspartner, der beim Import neu angelegt oder aktualisiert wird',10,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Y','N','N','N','N','N','Sprache',TO_TIMESTAMP('2020-12-02 16:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:43:02.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-12-02T15:43:02.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578595) 
;

-- 2020-12-02T15:43:02.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626943
;

-- 2020-12-02T15:43:02.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(626943)
;

-- 2020-12-02T15:44:04.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541236,544685,TO_TIMESTAMP('2020-12-02 16:44:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','importSettings',25,TO_TIMESTAMP('2020-12-02 16:44:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:44:25.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,626942,0,541327,544685,575652,'F',TO_TIMESTAMP('2020-12-02 16:44:25','YYYY-MM-DD HH24:MI:SS'),100,'Kundengruppe für eine Gemeinde, die beim Import neu angelegt oder aktualisiert wird','Y','N','N','Y','N','N','N',0,'Gemeinden-Kundengruppe',10,0,0,TO_TIMESTAMP('2020-12-02 16:44:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:45:01.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,626941,0,541327,544685,575653,'F',TO_TIMESTAMP('2020-12-02 16:45:01','YYYY-MM-DD HH24:MI:SS'),100,'Kundengruppe für einen Patienten, der beim Import neu angelegt oder aktualisiert wird','Y','N','N','Y','N','N','N',0,'ImportedPartientBP_Group_ID',20,0,0,TO_TIMESTAMP('2020-12-02 16:45:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T15:45:07.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='ImportedMunicipalityBP_Group_ID',Updated=TO_TIMESTAMP('2020-12-02 16:45:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575652
;

-- 2020-12-02T15:45:29.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,626943,0,541327,544685,575654,'F',TO_TIMESTAMP('2020-12-02 16:45:29','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für einen Geschäftspartner, der beim Import neu angelegt oder aktualisiert wird','Y','N','N','Y','N','N','N',0,'ImportedBPartnerLanguage',30,0,0,TO_TIMESTAMP('2020-12-02 16:45:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-02T16:27:01.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2020-12-02 17:27:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541950
;

-- 2020-12-02T16:27:10.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2020-12-02 17:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=544685
;

-- 2020-12-03T05:27:11.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='XML-Rechnungsempfänger', PrintName='XML-Rechnungsempfänger',Updated=TO_TIMESTAMP('2020-12-03 06:27:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575919 AND AD_Language='de_CH'
;

-- 2020-12-03T05:27:11.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'de_CH')
;

-- 2020-12-03T05:27:15.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='XML-Rechnungsempfänger', PrintName='XML-Rechnungsempfänger',Updated=TO_TIMESTAMP('2020-12-03 06:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575919 AND AD_Language='de_DE'
;

-- 2020-12-03T05:27:15.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'de_DE')
;

-- 2020-12-03T05:27:15.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575919,'de_DE')
;

-- 2020-12-03T05:27:15.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='XML-Rechnungsempfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE AD_Element_ID=575919
;

-- 2020-12-03T05:27:15.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='XML-Rechnungsempfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE AD_Element_ID=575919 AND IsCentrallyMaintained='Y'
;

-- 2020-12-03T05:27:15.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='XML-Rechnungsempfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575919) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575919)
;

-- 2020-12-03T05:27:15.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='XML-Rechnungsempfänger', Name='XML-Rechnungsempfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575919)
;

-- 2020-12-03T05:27:15.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='XML-Rechnungsempfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575919
;

-- 2020-12-03T05:27:15.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='XML-Rechnungsempfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE AD_Element_ID = 575919
;

-- 2020-12-03T05:27:15.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'XML-Rechnungsempfänger', Description = 'Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575919
;

-- 2020-12-03T05:27:35.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='XML Invoice recipient', PrintName='XML Invoice recipient',Updated=TO_TIMESTAMP('2020-12-03 06:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575919 AND AD_Language='en_US'
;

-- 2020-12-03T05:27:35.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'en_US')
;

